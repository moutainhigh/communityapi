package net.okdi.apiV4.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV4.entity.ParParcelRelation;
import net.okdi.apiV4.entity.ParParceladdress;
import net.okdi.apiV4.entity.ParParcelconnection;
import net.okdi.apiV4.entity.ParParcelinfo;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.entity.ParTaskProcess;
import net.okdi.apiV4.service.ProblemPackageService;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;
@Service
public class ProblemPackageServiceImpl implements ProblemPackageService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SendPackageService sendPackageService;

	public static final Logger logger=LoggerFactory.getLogger(ProblemPackageServiceImpl.class);
	@Override
	public List<Map<String, Object>> queryProblemPackageList(Long actualSendMember) {

		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>(); 
		logger.info("根据异常时间和parcelStatus查询异常件列表,异常时间不为空--actualSendMember="+actualSendMember);
		Criteria criteria=where("actualSendMember").is(actualSendMember).and("exceptionTime").ne(null).and("parcelEndMark").is("0");//未结束
		Query query = Query.query(criteria).with(new Sort(Direction.DESC, "exceptionTime"));//按照异常时间倒序排列
		logger.info("进入异常未签收列表：query="+query);
		List<ParParcelinfo> problemParcelList = mongoTemplate.find(query, ParParcelinfo.class);
		for (ParParcelinfo parParcelinfo : problemParcelList) {
			Map<String,Object> map=new HashMap<>();
			Long id = parParcelinfo.getUid();
			int size = problemParcelList.size();
			map.put("problemCount", size);//返回问题件数量
			map.put("uid", id);
			map.put("errorMessage", parParcelinfo.getErrorMessage());
			map.put("parcelNum", parParcelinfo.getParcelNum());
			map.put("sendSmsType", parParcelinfo.getSendSmsType());
			map.put("sendSmsStatus", parParcelinfo.getSendSmsStatus());
			map.put("callBackStatus", parParcelinfo.getCallBackStatus());
			map.put("replyStatus", parParcelinfo.getReplyStatus());
			Criteria criteria2 = where("uid").is(id);
			Query query2=Query.query(criteria2);
//			logger.info("查询异常件列表的地址actualSendMember="+actualSendMember);
			List<ParParceladdress> problemAddressList = mongoTemplate.find(query2, ParParceladdress.class);
			for (ParParceladdress parParceladdress : problemAddressList) {
				map.put("addresseeMobile", parParceladdress.getAddresseeMobile());
				String addresseeAddress = parParceladdress.getAddresseeAddress();
				if(!PubMethod.isEmpty(addresseeAddress)){
					String[] split = addresseeAddress.split(" ");
					StringBuilder sb = new StringBuilder();
					
					if(split.length > 1){
						for(int i = 1; i < split.length ; i++){
							sb.append(split[i]);
						}
//						logger.info("经过分割的详细地址:"+sb);
						map.put("addresseeAddress", sb);
				}
			}else{
				map.put("addresseeAddress", addresseeAddress);
			}
			returnList.add(map);
		}
	}
		return returnList;
 }


	/**
	 * 问题件重派
	 */
	@Override
	public void probPackAssignAgain(String parIds, Long newMemberId,String memberPhone) {
		String[] split = parIds.split(",");
		for (String parId : split) {
			Long newId = IdWorker.getIdWorker().nextId();//新包裹Id
			Query query = Query.query(where("uid").is(Long.valueOf(parId)));
			ParParcelinfo parcel = mongoTemplate.findOne(query, ParParcelinfo.class);
			if(PubMethod.isEmpty(parcel)){
				throw new ServiceException("包裹id有误,无法获取包裹信息--parId:"+parId);
			}
			Long compId = parcel.getCompId();
			Long netId = parcel.getNetId();
			Long actualSendMember = parcel.getActualSendMember();
			//1.复制包裹和包裹地址
			this.copyParcel(parId, newId, compId, netId, actualSendMember,"1");
			logger.info("问题件重派--复制包裹和包裹地址");
			Update update=new Update();
			update.set("parcelEndMark", "1");
			//2.修改原包裹状态为已完成
			Query queryPar = new Query();
			queryPar.addCriteria(Criteria.where("uid").is(Long.valueOf(parId)));
			queryPar.addCriteria(Criteria.where("mobilePhone").is(parcel.getMobilePhone()));//片键条件
			mongoTemplate.updateFirst(queryPar, update, ParParcelinfo.class);
			logger.info("问题件重派--修改原包裹状态为已完成");
			//3.添加包裹关系表|||  原包裹ID,新包裹ID,操作类型（1：转代收 2：重派），时间，操作人
			this.addParcelRelation(parcel.getUid(),newId,(short) 2,actualSendMember,actualSendMember);
			logger.info("问题件重派--添加包裹关系表");
			//4.添加任务流程处理表
			Long sendTaskId = parcel.getSendTaskId();
			ParTaskInfo taskInfo = mongoTemplate.findOne( Query.query(where("uid").is(sendTaskId)), ParTaskInfo.class);
			Long operatorId = taskInfo.getCreateUserId();//创建人id
			String taskProcessDesc="问题件重派";
			this.addParTaskProcess(sendTaskId, compId, actualSendMember, compId, actualSendMember,0,5,operatorId,compId,taskProcessDesc,taskProcessDesc);
			logger.info("问题件重派--添加任务流程处理表");
			//5.进行提货操作,重新生成派件任务
			logger.info("异常未签收件进行提货操作,重新生成派件任务开始parIds=("+parIds+"),memberId="+newMemberId+",memberPhone="+memberPhone);
			sendPackageService.pickUpParcel(newId.toString(), newMemberId, memberPhone);//进行提货操作
			logger.info("异常未签收件进行提货操作,重新生成派件任务结束'''''");
		}
		
	}
	/**
	 * 
	 * @Description: 添加包裹关系表
	 * @param oldParId
	 * @param newParId
	 * @param operationType 操作类型（1：转代收 2：重派）
	 * @param oldMemberId
	 * @param newMemberId void
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-22
	 */
	@Override
	public void addParcelRelation(Long oldParId, Long newParId, Short operationType,Long oldMemberId,Long newMemberId) {
		ParParcelRelation parcelRelation = new ParParcelRelation();
		parcelRelation.setUid(IdWorker.getIdWorker().nextId());
		parcelRelation.setCreateTime(new Date());
		parcelRelation.setOperationType(operationType);
		parcelRelation.setNewMemberId(newMemberId);
		parcelRelation.setNewParId(newParId);
		parcelRelation.setOldMemberId(oldMemberId);
		parcelRelation.setOldParId(oldParId);
		mongoTemplate.insert(parcelRelation);
		
	}


	/**
	 * 
	 * @Description: 复制包裹
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-21
	 */
	@Override
	public void copyParcel(String parId,Long newId,Long compId,Long netId,Long actualSendMember,String type){
		
		ParParcelinfo parcel = mongoTemplate.findOne(Query.query(where("uid").is(Long.valueOf(parId))), ParParcelinfo.class);
		ParParceladdress add = mongoTemplate.findOne(Query.query(where("uid").is(Long.valueOf(parId))), ParParceladdress.class);
		ParParcelinfo parcelInfo = new ParParcelinfo();
		ParParceladdress parceladdress = new ParParceladdress();
		ParParcelconnection connect = new ParParcelconnection();
		
		parcelInfo.setUid(newId);  //包裹id
		parceladdress.setUid(newId);  //包裹地址表id

		parcelInfo.setExpWaybillNum(parcel.getExpWaybillNum()); //运单号
		parcelInfo.setCompId(compId);  //公司id
		parcelInfo.setNetId(parcel.getNetId());    //网络id
		
		parceladdress.setAddresseeName(add.getAddresseeName()); //收件人姓名  
		parceladdress.setAddresseeMobile(add.getAddresseeMobile()); //收件人手机号码
		parceladdress.setAddresseeAddressId(add.getAddresseeAddressId());//收件人乡镇id
		parceladdress.setAddresseeAddress(add.getAddresseeAddress());//收件人详细地址
		parcelInfo.setFreight(parcel.getFreight()); //包裹应收运费
		parcelInfo.setCodAmount(parcel.getCodAmount());//代收货款金额
		
		parcelInfo.setCreateUserId(parcel.getCreateUserId()); //创建人id
		
		parcelInfo.setActualSendMember(actualSendMember); //派件人id
		parcelInfo.setTackingStatus((short)0);//包裹当前状态 0:在途,未签收 1:已签收
		parcelInfo.setGoodsPaymentMethod((short) 1);
		parcelInfo.setFreightPaymentMethod((short)2);
		parcelInfo.setParcelStatus((short) 10);//包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'
		parcelInfo.setParcelEndMark("0");//包裹结束标志  0：未结束 1：结束'
		parcelInfo.setSignResult((short) 0);
		parcelInfo.setCreateTime(parcel.getCreateTime()); //设置包裹创建时间
		if("1".equals(type)){
			parcelInfo.setIsAgainSend((short)1);//是否是重派的标识，空值为正常待派，1为重派待派
		}
		parcelInfo.setIsSendMsg(parcel.getIsSendMsg());//是否发过短信，0否1是
		parcelInfo.setReplyStatus((short) 0);//0 未回复
		parcelInfo.setMobilePhone(parcel.getMobilePhone());//片键
		parcelInfo.setParcelNum(parcel.getParcelNum());
		parcelInfo.setSendSmsType(parcel.getSendSmsType());
		parcelInfo.setSendSmsStatus(parcel.getSendSmsStatus());
		parcelInfo.setCallBackStatus(parcel.getCallBackStatus());
		parcelInfo.setReplyStatus(parcel.getReplyStatus());
		
		connect.setParId(newId);
		connect.setCompId(compId);
		connect.setNetId(parcel.getNetId());
		connect.setCreateTime(parcel.getCreateTime());
		connect.setCosignFlag((short) 2);
		connect.setExpMemberSuccessFlag((short) 1);//'收派员取/派成功标志 0：失败1：成功',
		connect.setUid(IdWorker.getIdWorker().nextId());
		connect.setMobilePhone(parcel.getMobilePhone());//片键
		mongoTemplate.insert(connect);//插入 ParcelConnection表(收派过程监控表) 
		mongoTemplate.insert(parcelInfo);
		mongoTemplate.insert(parceladdress);
	}

	/**
	 * 异常未签收退回站点
	 */
	@Override
	public void probPackBackComp(String parIds) {
		String[] split = parIds.split(",");
		for (String parId : split) {
			Update update=new Update();
			update.set("parcelEndMark", 1);//包裹结束标志 0：未结束 1：结束
			//1.清除异常未签收生成的和修改的包裹表中的字段
			ParParcelinfo parInfo = this.mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(parId))), ParParcelinfo.class);
			Query query = new Query();
			query.addCriteria(Criteria.where("uid").is(Long.valueOf(parId)));
			query.addCriteria(Criteria.where("mobilePhone").is(parInfo.getMobilePhone()));//片键条件
			mongoTemplate.updateFirst(query, update, ParParcelinfo.class);
			//2.添加任务流程处理表
			ParParcelinfo parcel = mongoTemplate.findOne(Query.query(where("uid").is(Long.valueOf(parId))), ParParcelinfo.class);
				try {
					Long sendTaskId = parcel.getSendTaskId();
					Long compId = parcel.getCompId();
					Long actualSendMember = parcel.getActualSendMember();
					ParTaskInfo taskInfo = mongoTemplate.findOne( Query.query(where("uid").is(sendTaskId)), ParTaskInfo.class);
					Long operatorId = taskInfo.getCreateUserId();//创建人id
					String taskProcessDesc="异常未签收退回站点";
					this.addParTaskProcess(sendTaskId, compId, actualSendMember, compId, actualSendMember,0,5,operatorId,compId,taskProcessDesc,taskProcessDesc);
				} catch (Exception e) {
					throw new RuntimeException("parIds参数有误,未查到相关包裹信息");
				}
		}
		logger.info("清除异常未签收生成的和修改的包裹表中的字段,并插入任务流程处理表");
		
	}
	/**
	 * 
	 * @Description: 添加任务处理流程表
	 * @param taskId
	 * @param fromCompId
	 * @param fromMemberId
	 * @param toCompId
	 * @param toMemberId
	 * @param taskStatus
	 * @param taskTransmitCause
	 * @param operatorId
	 * @param operatorCompId
	 * @param operatorDesc
	 * @param taskProcessDesc void
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-22
	 */
	@Override
	public void addParTaskProcess(Long taskId, Long fromCompId,Long fromMemberId, Long toCompId, Long toMemberId,
			Integer taskStatus, Integer taskTransmitCause, Long operatorId, Long operatorCompId, String operatorDesc,String taskProcessDesc) {
		ParTaskProcess parTaskProcess = new ParTaskProcess();
		parTaskProcess.setCreateTime(new Date());
		parTaskProcess.setFromCompId(fromCompId);
		parTaskProcess.setFromMemberId(fromMemberId);
		parTaskProcess.setUid(IdWorker.getIdWorker().nextId());
		parTaskProcess.setOperatorCompId(operatorCompId);
		parTaskProcess.setOperatorDesc(operatorDesc);
		parTaskProcess.setOperatorId(operatorId);
		parTaskProcess.setTaskId(taskId);
		parTaskProcess.setTaskProcessDesc(taskProcessDesc);
		parTaskProcess.setTaskStatus(taskStatus.byteValue());
		parTaskProcess.setTaskTransmitCause(taskTransmitCause.byteValue());
		parTaskProcess.setToCompId(toCompId);
		parTaskProcess.setToMemberId(toMemberId);
		mongoTemplate.insert(parTaskProcess);
	}
}
