package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.DicAddressaidMapper;
import net.okdi.api.dao.NetLowestPriceMapper;
import net.okdi.api.dao.RobBroadcastInfoMapper;
import net.okdi.api.dao.RobInfoMapper;
import net.okdi.api.dao.RobParcelRelationMapper;
import net.okdi.api.dao.RobPushRelationMapper;
import net.okdi.api.dao.RobQuotationInfoMapper;
import net.okdi.api.dao.VoiceSetMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.NetLowestPrice;
import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.api.entity.RobBroadcastInfo;
import net.okdi.api.entity.RobInfo;
import net.okdi.api.entity.RobParcelRelation;
import net.okdi.api.entity.RobPushRelation;
import net.okdi.api.entity.RobQuotationInfo;
import net.okdi.api.entity.VoiceSet;
import net.okdi.api.service.CourierService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.RobInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.api.vo.VO_ParcelInfoAndAddressInfo;
import net.okdi.api.vo.VO_RobInfo;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DistanceUtil;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * 抢单信息openapi接口
 * 
 * @author haifeng.he
 * @version V1.0
 */
@Service("robInfoService")
public class RobInfoServiceImpl extends BaseServiceImpl<RobBroadcastInfo> implements RobInfoService {

	public static final Log logger = LogFactory.getLog(RobInfoServiceImpl.class);
	private static final Short BROADCAST_STATUS_YCS = 5;//已超时
	private static final Short BROADCAST_STATUS_YBQ = 4;//已被抢
	private static final Short BROADCAST_STATUS_YQX=  2;//已取消
	private static final Short BROADCAST_STATUS_YBJ=  1;//已取消
	private static final Short QUOTATION_TYPE_COMP = 1;//站点系统报价
	private static final Short QUOTATION_TYPE_MEMB = 2;//手机端报价
	private static final int TIME = 3 ;//分钟
	@Autowired
	private EhcacheService ehcacheService;//缓存
	@Autowired
	private RobBroadcastInfoMapper robBroadcastInfoMapper;//广播
	@Autowired
	private ParcelInfoService ParcelInfoService;//包裹
	@Autowired
	private RobQuotationInfoMapper robQuotationInfoMapper;//报价
	@Autowired
	private RobParcelRelationMapper robParcelRelationMapper;//广播包裹关系
	@Autowired
	private DicAddressaidMapper dicAddressaidMapper;//地址
	@Autowired
	private NetLowestPriceMapper netLowestPriceMapper;//最低报价
	@Autowired
	private MemberInfoService memberInfoService;//人员
	@Autowired
	private SendNoticeService sendNoticeService;//推送
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;//站点
	@Autowired
	private BasNetInfoMapper basNetInfoMapper;//网络
	@Autowired
	private ConstPool constPool;
	@Autowired
	private VoiceSetMapper voiceSetMapper;
	@Autowired
    private CourierService courierService; //附近站点收派员
	@Autowired
	private RobInfoMapper robInfoMapper;
	@Autowired
	private RobPushRelationMapper robPushRelationMapper;
	@Autowired
	private ParcelInfoService parcelInfoService;
	@Autowired
	private TakeTaskService takeTaskService;
	@Autowired
	private BasNetInfoMapper netInfoMapper;
	/**
	 * 查询抢单广播信息
	 * @Method: doQueryRobInfo 
	 * @param compId											--站点id
	 * @param systemType										--系统类型 1是站点 2是手机端
	 * @param memberId											--人员id （手机端必传）
	 * @return 
	 * @see net.okdi.api.service.RobInfoService#doQueryRobInfo(java.lang.Long, java.lang.Short, java.lang.Long)
	 */
	@Override
	public List<VO_RobInfo> doQueryRobInfo(Long compId,Short systemType,Long memberId) {
		List<VO_RobInfo> listResult = new ArrayList<VO_RobInfo>();
		List<Map<String,Object>> listParcelInfo = new ArrayList<Map<String,Object>>();
		List<RobQuotationInfo> listRobQuotationInfo = this.queryRobQuotationInfo(compId,systemType,memberId);//通过条件查询报价表中所推送的数据
		if(!PubMethod.isEmpty(listRobQuotationInfo)){/**如果查询出的推送给本网点或者本快递员的信息不为空**/
			List<RobBroadcastInfo> updatetemp = new ArrayList<>();
			for(RobQuotationInfo robQuotationInfo : listRobQuotationInfo){
				VO_RobInfo robInfo = new VO_RobInfo();
				RobBroadcastInfo robBroadcastInfo = this.queryRobBroadcastInfo(robQuotationInfo.getRobId());//抢单广播id查询抢单广播信息
				if(robBroadcastInfo.getBroadcastStatus() == 1){
					if(PubMethod.getLastSecond(robBroadcastInfo.getCreateTime())!=0){//如果是待响应状态才去查询包裹信息 
					listParcelInfo = this.queryParcelInfo(robBroadcastInfo.getId());//抢单广播id查询包裹信息列表
					robInfo = this.createRobInfo(robBroadcastInfo,listParcelInfo,robQuotationInfo);//组装返回给前台展示的抢单广播列表信息
					listResult.add(robInfo);
				}else{//超过3小时 去修改状态为已超时
					updatetemp.add(robBroadcastInfo);
					if(systemType == 1){
						this.ehcacheService.remove("robQuotationInfoCache", String.valueOf(compId));
					}else if(systemType == 2){
						this.ehcacheService.remove("robQuotationInfoCache", String.valueOf(memberId));
					}
				  }
				}
			}
			if(!PubMethod.isEmpty(updatetemp)) {
				this.robBroadcastInfoMapper.updateStatusCancelBroadcast(updatetemp);
			}
		}
		return listResult;
	}
	//组装返回的实体信息
	private VO_RobInfo createRobInfo(RobBroadcastInfo robBroadcastInfo, List<Map<String, Object>> listParcelInfo,
			RobQuotationInfo robQuotationInfo) {
		VO_RobInfo robInfo = new VO_RobInfo();
		robInfo.setBroadcastType(robBroadcastInfo.getBroadcastType());//广播来源1:个人端2电商管家
		robInfo.setBroadcastId(robBroadcastInfo.getId());//广播id
		robInfo.setQuotationId(robQuotationInfo.getId());//报价id
		robInfo.setLoginMemberId(robBroadcastInfo.getLoginMemberId());//发抢单的客户id
		robInfo.setQuotation(robQuotationInfo.getQuotationAmt() == null ? 0 :robQuotationInfo.getQuotationAmt().longValue());//报价金额
		robInfo.setTakeMemberId(robQuotationInfo.getMemberId());//指定的收件员 
		robInfo.setParcelData(listParcelInfo);//广播包裹
		robInfo.setSenderAddressId(robBroadcastInfo.getSenderAddressId());//发件地址id
		if(!PubMethod.isEmpty(robBroadcastInfo.getSenderAddressName())){
			robInfo.setSenderAddressName(robBroadcastInfo.getSenderAddressName().split("\\|")[0]);//发件地址
			//robInfo.setSenderDetailAddressName(robBroadcastInfo.getSenderAddressName().split("\\|").length>1?(robBroadcastInfo.getSenderAddressName().split("\\|")[1].length()<=5?robBroadcastInfo.getSenderAddressName().split("\\|")[1]:robBroadcastInfo.getSenderAddressName().split("\\|")[1].substring(0, robBroadcastInfo.getSenderAddressName().split("\\|")[1].length()-5)+"....."):"");//详细地址
			robInfo.setSenderDetailAddressName(robBroadcastInfo.getSenderAddressName().split("\\|").length>1?robBroadcastInfo.getSenderAddressName().split("\\|")[1]:"");//详细地址
		}
		robInfo.setTotalCount(robBroadcastInfo.getTotalCount());//包裹总数量 
		robInfo.setTotalWeight(robBroadcastInfo.getTotalWeight());//包裹总重量 
		robInfo.setDistance(robQuotationInfo.getDistance());//记录(m)
		robInfo.setBroadcastRemark(PubMethod.isEmpty(robBroadcastInfo.getBroadcastRemark())?"":robBroadcastInfo.getBroadcastRemark());//备注
		robInfo.setCreateTime(robBroadcastInfo.getCreateTime());//广播创建时间(毫秒数) 
		robInfo.setCountdown((robBroadcastInfo.getCreateTime().getTime()+10800000-new Date().getTime())/1000);//创建时间与现在的秒数之差
		robInfo.setBroadcastStatus(robQuotationInfo.getRobStatus());//报价状态1 已报价 2 待响应 
		if(!PubMethod.isEmpty(robQuotationInfo.getMemberId())){//如果报价表中memberId不为空 返回名称
			MemberInfo MemberInfo = this.memberInfoService.getMemberInfoById(robQuotationInfo.getMemberId());
			robInfo.setTakeMemberName(MemberInfo.getMemberName());	
		}
		return robInfo;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过systemType与条件查询站点|手机端抢单报价信息列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:06:27</dd>
	 * @param compId 站点id
	 * @param systemType 系统类型 1是站点 2是手机端
	 * @param memberId 人员id
	 * @return
	 * @since v1.0
	 */
	private List<RobQuotationInfo> queryRobQuotationInfo(Long compId, Short systemType, Long memberId) {
		List<RobQuotationInfo> listRobQuotationInfo = new ArrayList<RobQuotationInfo>();
		if(systemType == QUOTATION_TYPE_MEMB ){//0是手机端
			listRobQuotationInfo = this.queryRobQuotationInfoByMemberId(memberId);//根据memberId查询快递哥显示的抢单广播列表信息
		}else if(systemType == QUOTATION_TYPE_COMP){//1是站点
			listRobQuotationInfo = this.queryRobQuotationInfoByCompId(compId);//根据compId查询站点显示的抢单广播列表信息
		}
		return listRobQuotationInfo;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过广播id查询抢单广播信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-15 下午7:33:29</dd>
	 * @param broadcastId  --广播id
	 * @return
	 * @since v1.0
	 */
	private RobBroadcastInfo queryRobBroadcastInfo(Long broadcastId) {
		RobBroadcastInfo robBroadcastInfo = this.ehcacheService.get("robBroadcastCache", String.valueOf(broadcastId), RobBroadcastInfo.class);
		if(PubMethod.isEmpty(robBroadcastInfo)){
			robBroadcastInfo = this.robBroadcastInfoMapper.selectByPrimaryKey(broadcastId);
			this.ehcacheService.put("robBroadcastCache", String.valueOf(broadcastId), robBroadcastInfo);
		}
		return robBroadcastInfo;
	}
	/**
	 * 通过站点id查询抢单广播信息
	 * @Method: queryRobQuotationInfoByCompId 
	 * @param compId
	 * @return 
	 * @see net.okdi.api.service.RobInfoService#queryRobQuotationInfoByCompId(java.lang.Long)
	 */
	@Override
	public List<RobQuotationInfo> queryRobQuotationInfoByCompId(Long compId) {
		//根据compId取出缓存中的站点显示的抢单广播列表信息
		List<RobQuotationInfo> list = null;
		String val = ehcacheService.get("robQuotationInfoCache", String.valueOf(compId), String.class);
		if(!PubMethod.isEmpty(val)){
			list = JSON.parseArray(val, RobQuotationInfo.class);
		}
		if(PubMethod.isEmpty(list)){
			list = this.robQuotationInfoMapper.queryRobQuotationInfoByCompId(compId);
			this.ehcacheService.put("robQuotationInfoCache", String.valueOf(compId), list);
		}
		return list;
	}
	@Override
	public BaseDao getBaseDao() {
		return null;
	}
	@Override
	public String parseResult(String info) {
		return info;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>批量添加广播报价信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-15 下午7:12:54</dd>
	 * @param list
	 * @since v1.0
	 */
	@Override
	public void addbatchRobQuotationInfo(List<RobQuotationInfo> list) {
		this.robQuotationInfoMapper.addbatchRobQuotationInfo(list);
	}
	/**
	 * 通过广播id查询对应广播下的所有包裹的信息
	 * @Method: queryParcelInfo 
	 * @param robId --广播id
	 * @return 
	 * @see net.okdi.api.service.RobInfoService#queryParcelInfo(java.lang.Long)
	 */
	@Override
	public List<Map<String,Object>> queryParcelInfo(Long robId) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<RobParcelRelation> listRobParcelRelation = null;
		String val = ehcacheService.get("robParcelRelationCache", String.valueOf(robId), String.class);
		if(!PubMethod.isEmpty(val)){
			listRobParcelRelation = JSON.parseArray(val, RobParcelRelation.class);
		}
		if(PubMethod.isEmpty(listRobParcelRelation)){
			listRobParcelRelation = this.robParcelRelationMapper.queryRobParcelRelationByRobId(robId);
			this.ehcacheService.put("robParcelRelationCache", String.valueOf(robId), listRobParcelRelation);
		}
		if(!PubMethod.isEmpty(listRobParcelRelation)){
			for(RobParcelRelation robParcelRelation :listRobParcelRelation){
				Map<String,Object> map = new HashMap<String, Object>();
				ParParcelinfo parParcelinfo = this.ParcelInfoService.getParcelInfoById(robParcelRelation.getParcelId());
				ParParceladdress ParParceladdress = this.ParcelInfoService.getParParceladdressById(robParcelRelation.getParcelId());
				map.put("parcelId", robParcelRelation.getParcelId());
				if(!PubMethod.isEmpty(ParParceladdress)&&!PubMethod.isEmpty(parParcelinfo)){
					map.put("parcelAddressId", ParParceladdress.getAddresseeAddressId());
					map.put("parcelAddress", PubMethod.isEmpty(ParParceladdress.getAddresseeAddress())?"":ParParceladdress.getAddresseeAddress().replace("\\|", "").replace("-", ""));
					map.put("parcelWeight", PubMethod.isEmpty(parParcelinfo.getChareWeightForsender()) ? "0.00" : parParcelinfo.getChareWeightForsender().setScale(2,BigDecimal.ROUND_HALF_UP));
					map.put("parcelFreight", PubMethod.isEmpty(parParcelinfo.getFreight()) ? "0.00" : parParcelinfo.getFreight().setScale(2,BigDecimal.ROUND_HALF_UP));
				}
			
				list.add(map);
			}
		}
		return list;
	}
	/**
	 * 根据memberId取出缓存中的站点显示的抢单广播列表信息
	 * @Method: queryRobQuotationInfoByMemberId 
	 * @param memberId  -推送到的人员id
	 * @return 
	 * @see net.okdi.api.service.RobInfoService#queryRobQuotationInfoByMemberId(java.lang.Long)
	 */
	@Override
	public List<RobQuotationInfo> queryRobQuotationInfoByMemberId(Long memberId) {
		//根据memberId取出缓存中的站点显示的抢单广播列表信息
		List<RobQuotationInfo> list = null;
		String val = ehcacheService.get("robQuotationInfoCache", String.valueOf(memberId), String.class);
		
		if(!PubMethod.isEmpty(val)){
			System.out.println("走缓存=======================================================");
			list = JSON.parseArray(val, RobQuotationInfo.class);
		}
		if(PubMethod.isEmpty(list)){
			System.out.println("周数据库=================================================");
			list = this.robQuotationInfoMapper.queryRobQuotationInfoByMemberId(memberId);
			this.ehcacheService.put("robQuotationInfoCache", String.valueOf(memberId), list);
		}
		return list;
	}
	/**
	 * 报价抢单
	 * @Method: robExpress 
	 * @param userId			--广播推送给人的id
	 * @param broadcastType		--广播来源 1:个人端 2:电商管家
	 * @param loginMemberId		--发布抢单广播的客户id(													
	 * @param compId			--站点id
	 * @param broadcastId		--广播id
	 * @param quotationAmt		--报价金额
	 * @param takeMemberId		--指定的取件员
	 * @param quotationType		--报价类型 1站点报价、2收派员报价
	 * @see net.okdi.api.service.RobInfoService#robExpress(java.lang.Long, java.lang.Long, java.math.BigDecimal, java.lang.Long, java.lang.Short)
	 */
	@Override
	public void robExpress(Long userId,Long quotationId,Short broadcastType,Long loginMemberId,Long compId, Long broadcastId, BigDecimal quotationAmt, Long takeMemberId, Short quotationType) {
		BasCompInfo basCompInfo =  this.queryBasCompInfo(compId);//公司实体
		BasNetInfo basNetInfo = this.queryBasNetInfo(basCompInfo.getBelongToNetId());//网络实体
		RobQuotationInfo robQuotationInfo = new RobQuotationInfo();
		robQuotationInfo.setId(quotationId);//报价id
		robQuotationInfo.setCompId(compId);//站点id
		robQuotationInfo.setMemberId(takeMemberId);//取件员id
		robQuotationInfo.setRobId(broadcastId);//广播id
		robQuotationInfo.setQuotationAmt(quotationAmt);//抢单报价金额
		robQuotationInfo.setQuotationType(quotationType);//报价类型:（1站点报价、2收派员报价）
		this.robQuotationInfoMapper.robExpress(robQuotationInfo);
		this.ehcacheService.removeAll("robQuotationInfoCache");
		this.ehcacheService.put("newQuotationCache", String.valueOf(broadcastId), 0);
		MemberInfo MemberInfo = this.memberInfoService.getMemberInfoById(takeMemberId);
		 logger.info("以下推送开始  : =======broadcastId="+broadcastId+" loginMemberId=="+loginMemberId+"  MemberInfo.getMemberName()=="+MemberInfo.getMemberName());
		if(broadcastType == 1){//抢单来源是个人端
			 if(quotationType == 1){//站点报价推送给个人端的信息
				 logger.info("站点报价推送给个人端开始");
					sendNoticeService.siteQuoteToCustomer(broadcastId, loginMemberId, basCompInfo.getCompName()); 
					logger.info("站点报价推送给个人端结束");	
			 }else if(quotationType == 2){ //快递哥报价推送给个人端的信息
				 logger.info("快递员报价推送给个人端开始");	
				 sendNoticeService.expQuoteToCustomer(broadcastId, loginMemberId, MemberInfo.getMemberName(), basNetInfo.getNetName()); 
				 logger.info("快递员报价推送给个人端结束");	
			 }
		}else if(broadcastType == 2){//抢单来源是电商管家 需要callback电商管家
			 logger.info("电商管家回调开始");
			this.callBackInfo(basCompInfo,basNetInfo,MemberInfo,broadcastId,quotationAmt,quotationId);
		}
	}
	private String callBackInfo(BasCompInfo basCompInfo, BasNetInfo basNetInfo, MemberInfo memberInfo, Long broadcastId,
			BigDecimal quotationAmt,Long quotationId) {
		if(PubMethod.isEmpty(broadcastId)){
			throw new ServiceException("openapi.RobInfoServiceImpl.callBackInfo.001","broadcastId不能为空");			
		}
		if(PubMethod.isEmpty(quotationAmt)){
			throw new ServiceException("openapi.RobInfoServiceImpl.callBackInfo.002","quotationAmt不能为空");			
		}
		if(PubMethod.isEmpty(quotationId)){
			throw new ServiceException("openapi.RobInfoServiceImpl.callBackInfo.003","quotationId不能为空");			
		}
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,String> mapResult = new HashMap<String,String>();
		map.put("broadcastId", String.valueOf(broadcastId));//广播id
		map.put("quotationAmt", String.valueOf(quotationAmt));//报价金额
		map.put("compId", String.valueOf(basCompInfo.getCompId()));//站点id
		map.put("compName", String.valueOf(basCompInfo.getCompName()));//站点名称
		map.put("distance", String.valueOf(this.robQuotationInfoMapper.selectByPrimaryKey(quotationId).getDistance()));//距离
		map.put("compAddressId", String.valueOf(basCompInfo.getCompAddressId()));//站点区县id
		map.put("compAddress", String.valueOf(basCompInfo.getCompAddress().replace("-", "").replace("|", "")));//站点区县名称
		map.put("netId", String.valueOf(basNetInfo.getNetId()));//网络id
		map.put("netName", String.valueOf(basNetInfo.getNetName()));//网络名称
		map.put("memberId", String.valueOf(memberInfo.getMemberId()));//人员id
		map.put("memberName", String.valueOf(memberInfo.getMemberName()));//人员名称
		map.put("memberPhone", String.valueOf(memberInfo.getMemberPhone()));//人员手机号
		map.put("quotationId", String.valueOf(quotationId));//报价id
        map.put("imageUrl", String.valueOf(constPool.getHeadImgPath()+memberInfo.getMemberId()+".jpg"));//人员头像
		String param = JSON.toJSONString(map);
		mapResult.put("jsonData", param);
		 logger.info("电商管家回调数据==="+mapResult);
		try {
			String url = constPool.getCallBackFhwPath();//openapi调用接口
			 logger.info("电商管家回调数据url==="+url);
			String response = Post(url, mapResult);
			 logger.info("电商管家回调数据返回==="+response);
			return response;
		} catch (Exception e) {
			return PubMethod.paramsFailure("openapi.RobInfoServiceImpl.callBackInfo.004", "回调电商管家接口发生异常");
		}
	}
	/**
	 * 根据广播id计算最低报价
	 * @Method: getLowestPrice 
	 * @param broadcastId  --广播id
	 * @return 
	 * @see net.okdi.api.service.RobInfoService#getLowestPrice(java.lang.Long)
	 */
	@Override
	public Long getLowestPrice(Long broadcastId) {
		Long lowestPrice = 0L;
		RobBroadcastInfo robBroadcastInfo = this.queryRobBroadcastInfo(broadcastId);
		String sendAddressName = robBroadcastInfo.getSenderAddressName();//发件地址
		List<Map<String,Object>> listParcelInfo = this.queryParcelInfo(broadcastId);//该广播对应的包裹信息
		for(Map<String,Object> map : listParcelInfo){
			String parcelAddressName = String.valueOf(map.get("parcelAddress"));//单个包裹的收件地址
			BigDecimal parcelWeight = (BigDecimal) (PubMethod.isEmpty(map.get("parcelWeight"))?0:map.get("parcelWeight"));
			lowestPrice +=lowestPrice(sendAddressName,parcelAddressName,parcelWeight);
		}
		return lowestPrice;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过发件地址与收件地址计算运费</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午5:17:15</dd>
	 * @param sendAddressName --发件地址
	 * @param parcelAddressName  -- 收件地址
	 * @param parcelWeight  -包裹重量
	 * @return
	 * @since v1.0
	 */
	private Long lowestPrice(String sendAddressName, String parcelAddressName,BigDecimal parcelWeight) {
		if(PubMethod.isEmpty(sendAddressName)){
			throw new ServiceException("openapi.RobInfoServiceImpl.lowestPrice.001","sendAddressName不能为空");	
		}
		if(PubMethod.isEmpty(parcelAddressName)){
			throw new ServiceException("openapi.RobInfoServiceImpl.lowestPrice.002","parcelAddressName不能为空");	
		}
		Long lowestPrice = 0L;
		//通过地址前2位去数据中查询所对应的省id
		Long sendProvinceId = this.queryDicAddressaid(sendAddressName.substring(0, 2)).getProvinceId();
		Long recProvinceId = this.queryDicAddressaid(parcelAddressName.substring(0, 2)).getProvinceId();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sendProvinceId", sendProvinceId);
		map.put("recProvinceId", recProvinceId);
		NetLowestPrice netLowestPrice = this.netLowestPriceMapper.queryLowestPrice(map);//通过发件省与收件省计算首重续重
		if(PubMethod.isEmpty(netLowestPrice)){//港澳台和海外的报价 数据库中暂时空缺 所以写成定值
			netLowestPrice = new NetLowestPrice();
			netLowestPrice.setFirstFreight((short)60);	
			netLowestPrice.setContinueFreight((short)40);
		}
		Short firstFreight = netLowestPrice.getFirstFreight();//首重
		Short continueFreight = netLowestPrice.getContinueFreight();//续重
		if(parcelWeight.longValue() <=1){
			lowestPrice = lowestPrice+ firstFreight.longValue();
		}else if(parcelWeight.longValue() >1){
			
			lowestPrice = firstFreight + (parcelWeight.longValue()-1)*continueFreight;
		}
		return lowestPrice;
	}
	/**
	 * 通过省名字查询省id
	 * @Method: queryDicAddressaid 
	 * @param addressName  -- 省名字
	 * @return 
	 * @see net.okdi.api.service.RobInfoService#queryDicAddressaid(java.lang.String)
	 */
	@Override
	public DicAddressaid queryDicAddressaid(String addressName) {
		DicAddressaid dicAddressaid = null;
		String val = this.ehcacheService.get("dicAddressaidCache", String.valueOf(addressName), String.class);
		if(!PubMethod.isEmpty(val)){
			dicAddressaid = (DicAddressaid) JSON.parseArray(val, DicAddressaid.class);
		}
		if(PubMethod.isEmpty(dicAddressaid)){
			dicAddressaid =  this.dicAddressaidMapper.queryDicAddressaid(addressName+"%");
			this.ehcacheService.put("dicAddressaidCache", String.valueOf(addressName), dicAddressaid);
		}
		return dicAddressaid;
	}
	/**
	 * 抢单之前校验状态
	 * @Method: isRightStatus 
	 * @param robId
	 * @param quotationAmt
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.RobInfoService#isRightStatus(java.lang.Long, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public Short isRightConditions(Long quotationId,Long broadcastId, BigDecimal quotationAmt) {
		if (this.queryRobBroadcastInfo(broadcastId).getBroadcastType() == 2) {
			this.robBroadcastInfoMapper.updateStatus2(broadcastId);//如果超时则去修改状态为已超时 
			robBroadcastInfoMapper.finishQuotation(quotationId);
			this.ehcacheService.removeAll("robQuotationInfoCache");
			return 4;
			
		}else{
		RobQuotationInfo RobQuotationInfo = this.robQuotationInfoMapper.selectByPrimaryKey(quotationId);
		Short flag = 0;
		if(quotationAmt.longValue()<this.getLowestPrice(broadcastId)){
			flag = 3;//报价低于最低报价 
		}else 
		if(this.queryRobBroadcastInfo(broadcastId).getBroadcastStatus()==BROADCAST_STATUS_YBQ){
			flag = BROADCAST_STATUS_YBQ ;//已被抢4
		}else 
		if (PubMethod.getLastSecond(queryRobBroadcastInfo(broadcastId).getCreateTime())==0){
			flag = BROADCAST_STATUS_YCS ;//已超时 5
			this.robBroadcastInfoMapper.updateStatus(broadcastId);//如果超时则去修改状态为已超时 
			this.ehcacheService.removeAll("robQuotationInfoCache");
		}else
		if (this.queryRobBroadcastInfo(broadcastId).getBroadcastStatus()==BROADCAST_STATUS_YQX){
			flag = BROADCAST_STATUS_YQX ;//已取消2
		}else if(RobQuotationInfo.getRobStatus() == 1){//已报价
			flag = BROADCAST_STATUS_YBJ ;//已报价
		}	
		return flag;
		}
	}
	@Override
	public BasCompInfo queryBasCompInfo(Long compId) {
		BasCompInfo basCompInfo = this.ehcacheService.get("compCache", String.valueOf(compId), BasCompInfo.class);
		if(PubMethod.isEmpty(basCompInfo)){
			basCompInfo = this.basCompInfoMapper.findById(compId);
			this.ehcacheService.put("compCache", String.valueOf(compId), basCompInfo);
		}
		return basCompInfo;
	}
	
	@Override
	public BasNetInfo queryBasNetInfo(Long netId) {
		BasNetInfo basNetInfo = this.ehcacheService.get("netCache", String.valueOf(netId), BasNetInfo.class);
		if(PubMethod.isEmpty(basNetInfo)){
			basNetInfo = this.basNetInfoMapper.findById(netId);
			this.ehcacheService.put("netCache", String.valueOf(netId), basNetInfo);
		}
		return basNetInfo;
	}

	
	
	/**
	 * 抢单广播列表声音开关设置
	 * @Method: voiceSet 
	 * @param flag 0 关闭 1 开启
	 * @param memberId 当前登录人员id
	 * @see net.okdi.api.service.RobInfoService#voiceSet(java.lang.Long, java.lang.Short)
	 */
	@Override
	public void voiceSet(Long memberId,Short flag) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("flag", flag);
		VoiceSet voiceSet = this.queryVoiceSet(memberId);
		if(PubMethod.isEmpty(voiceSet)){//insert
			this.voiceSetMapper.insertVoiceSet(map);
		}else{
			this.voiceSetMapper.updateVoiceSet(map);
			this.ehcacheService.remove("voiceSetCache", String.valueOf(memberId));
		}
	}
	/**
	 * 查询站点当前登录人的声音开关设置信息
	 * @Method: queryVoiceSet 
	 * @param memberId  当前登录人的memberId
	 * @return 
	 * @see net.okdi.api.service.RobInfoService#queryVoiceSet(java.lang.Long)
	 */
	@Override
	public VoiceSet queryVoiceSet(Long memberId) {
		VoiceSet voiceSet = this.ehcacheService.get("voiceSetCache", String.valueOf(memberId), VoiceSet.class);
		if(PubMethod.isEmpty(voiceSet)){
			voiceSet = this.voiceSetMapper.queryVoiceSet(memberId);
			this.ehcacheService.put("voiceSetCache", String.valueOf(memberId), voiceSet);
		}
		return voiceSet;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public Map<String,Object> createRob(String senderName,String senderMobile,Long senderAddressId,String senderAddressName,
			String senderDetailAddressName,BigDecimal senderLongitude,BigDecimal senderLatitude,Integer parcelTotalCount,
			BigDecimal parcelTotalWeight,String broadcastRemark,BigDecimal quotationAmount,short broadcastSource,
			String parcelsJsonStr,Long createUser,String appointTime){
		//数据有效性验证
		if(PubMethod.isEmpty(senderName)){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.001","创建广播，senderName参数非空异常");			
		}
		if(PubMethod.isEmpty(senderMobile)){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.002","创建广播，senderMobile参数非空异常");			
		}
		if(PubMethod.isEmpty(senderAddressId)){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.003","创建广播，senderAddressId参数非空异常");			
		}
		if(PubMethod.isEmpty(senderAddressName)){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.004","创建广播，senderAddressName参数非空异常");			
		}
		if(PubMethod.isEmpty(senderDetailAddressName)){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.005","创建广播，senderDetailAddressName参数非空异常");			
		}
		if(PubMethod.isEmpty(senderLongitude) || senderLongitude.compareTo(new BigDecimal(0))!= 1){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.006","创建广播，senderLongitude参数异常");			
		}
		if(PubMethod.isEmpty(senderLatitude) || senderLatitude.compareTo(new BigDecimal(0))!= 1){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.007","创建广播，senderLatitude参数异常");			
		}
		if(PubMethod.isEmpty(parcelTotalCount) || parcelTotalCount < 1){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.008","创建广播，parcelTotalCount参数异常");			
		}
		if(PubMethod.isEmpty(parcelTotalWeight) || parcelTotalWeight.compareTo(new BigDecimal(0))!= 1){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.009","创建广播，parcelTotalWeight参数异常");			
		}
		if(PubMethod.isEmpty(quotationAmount)  || quotationAmount.compareTo(new BigDecimal(0))!= 1){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.010","创建广播，quotationAmount参数异常");			
		}
		if(PubMethod.isEmpty(broadcastSource) || (broadcastSource != 0 && broadcastSource != 1)){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.011","创建广播，broadcastSource参数异常");			
		}
		if(PubMethod.isEmpty(parcelsJsonStr)){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.012","创建广播，parcelsJsonStr参数非空异常");			
		}
		if(PubMethod.isEmpty(createUser) && broadcastSource != 1){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.013","创建广播，parcelsJsonStr参数非空异常");			
		}
		Date appointDate = null;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		if(!PubMethod.isEmpty(appointTime)){
			String[] timeArr = appointTime.split(":");
			if(timeArr.length == 2){
				try{
					cal.setTime(date);
					cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]));
					cal.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]));
					cal.set(Calendar.SECOND, 0);
					appointDate = cal.getTime();	
				}catch(Exception e){
					throw new ServiceException("openapi.RobInfoServiceImpl.createRob.016","创建广播，appointTime参数异常");
				}
			}else{
				throw new ServiceException("openapi.RobInfoServiceImpl.createRob.017","创建广播，appointTime参数异常");
			}
		}
		//组装广播信息
		RobInfo robInfo = this.setRobInfo(senderName, senderMobile, senderAddressId, senderAddressName, senderDetailAddressName,
				senderLongitude, senderLatitude, parcelTotalCount, parcelTotalWeight, broadcastRemark, quotationAmount, broadcastSource,
				createUser,appointDate);
		//处理包裹信息
		List <VO_ParcelInfoAndAddressInfo> parcelList = null;
		try{
			parcelList = JSON.parseArray(parcelsJsonStr, VO_ParcelInfoAndAddressInfo.class);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.014","创建广播，包裹集合json解析转换异常");
		}
		if(PubMethod.isEmpty(parcelList)){
			throw new ServiceException("openapi.RobInfoServiceImpl.createRob.015","创建广播，包裹集合非空异常");
		}
		List<RobParcelRelation> robParcelRelationList = new ArrayList<RobParcelRelation>(parcelList.size());
		for (int i = 0;i<parcelList.size();i++){
			RobParcelRelation robParcelRelation = new RobParcelRelation();
			Long parcelId = IdWorker.getIdWorker().nextId();
			parcelList.get(i).setId(parcelId);
			parcelList.get(i).setSenderUserId(createUser);
			parcelList.get(i).setCreateUserId(createUser);
			parcelList.get(i).setSendName(senderName);
			parcelList.get(i).setSendMobile(senderMobile);
			parcelList.get(i).setSendAddressId(senderAddressId);
			parcelList.get(i).setSendAddress((PubMethod.isEmpty(senderAddressName) ?  "" : senderAddressName)+ (PubMethod.isEmpty(senderDetailAddressName) ?  "" : senderDetailAddressName));
			parcelList.get(i).setSendLongitude(senderLongitude);
			parcelList.get(i).setSendLatitude(senderLatitude);
			robParcelRelation.setId(IdWorker.getIdWorker().nextId());
			robParcelRelation.setRobId(robInfo.getBroadcastId());
			robParcelRelation.setParcelId(parcelId);
			robParcelRelationList.add(robParcelRelation);
		}
		//查询附近收派员
		List<Map<String,Object>> couriserList = courierService.queryNearMemberRob(senderLongitude.doubleValue(), senderLatitude.doubleValue(), null, null, null, 0, 20);
		
		//??????假数据
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", 14637530924711936l);
		map.put("compId", 14368104662172672L);
		map.put("distance", 200);
		couriserList.add(map);*/
		
		List<RobPushRelation> robPushRelationList = new ArrayList<RobPushRelation>(couriserList.size());
		for(Map<String,Object> couriser : couriserList){
			this.ehcacheService.remove("robDataCache", couriser.get("memberId").toString());
			try{
				robPushRelationList.add(this.setRobPushRelation(robInfo.getBroadcastId(), Long.parseLong(couriser.get("compId").toString()), Long.parseLong(couriser.get("memberId").toString())));
			}catch(Exception e){
				continue;
			}
		}
		this.parcelInfoService.addbatchSaveParcelInfo(parcelList);
		this.robParcelRelationMapper.addBroadcastParcelRelation(robParcelRelationList);
		if(!PubMethod.isEmpty(robPushRelationList)){
			this.robPushRelationMapper.batchRobPushRelation(robPushRelationList);
		}
		this.robInfoMapper.insert(robInfo);
		
		try{//捕获推送消息异常，发生异常正常创建抢单数据
			for(Map<String,Object> couriser : couriserList){
				try{
					sendNoticeService.broadNoticeToExp(robInfo.getBroadcastId(), Long.parseLong(couriser.get("memberId").toString()), couriser.get("distance").toString(), senderDetailAddressName, parcelTotalCount);
				}catch(Exception e){
					logger.error("给附近收派员发送推送消息异常"+e);
					continue;
				}
			}
		}catch(Exception e){
			logger.error("给附近收派员发送推送消息异常");
		}
		
		Map<String,Object> returnData = new HashMap<String,Object>();
		returnData.put("broadcastId", robInfo.getBroadcastId());
		return returnData;
	}
	public RobInfo setRobInfo(String senderName,String senderMobile,Long senderAddressId,String senderAddressName,
			String senderDetailAddressName,BigDecimal senderLongitude,BigDecimal senderLatitude,Integer parcelTotalCount,
			BigDecimal parcelTotalWeight,String broadcastRemark,BigDecimal quotationAmount,short broadcastSource,
			Long createUser,Date appointTime){
		RobInfo robInfo = new RobInfo();
		robInfo.setBroadcastId(IdWorker.getIdWorker().nextId());
		robInfo.setBroadcastSource(broadcastSource);
		robInfo.setParcelTotalCount(parcelTotalCount);
		robInfo.setParcelTotalWeight(parcelTotalWeight);
		robInfo.setBroadcastRemark(broadcastRemark);
		robInfo.setBroadcastStatus((short)0);//广播状态 0待响应、1已响应、2已超时、3已取消
		robInfo.setSenderName(senderName);
		robInfo.setSenderMobile(senderMobile);
		robInfo.setSenderAddressId(senderAddressId);
		robInfo.setSenderAddressName(senderAddressName);
		robInfo.setSenderDetailAddressName(senderDetailAddressName);
		robInfo.setSenderLongitude(senderLongitude);
		robInfo.setSenderLatitude(senderLatitude);
		robInfo.setAppointTime(appointTime);
		robInfo.setQuotationAmount(quotationAmount);
		robInfo.setViewFlag((short)0); //显示标识 0：显示，1不显示
		robInfo.setCreateUser(createUser);
		Date date = new Date();
		robInfo.setCreateTime(date);
		robInfo.setModityTime(date);
		return robInfo;
	}
	public RobPushRelation setRobPushRelation(Long broadcastId , Long compId ,Long memberId){
		RobPushRelation robPushRelation = new RobPushRelation();
		robPushRelation.setRelationId(IdWorker.getIdWorker().nextId());
		robPushRelation.setBroadcastId(broadcastId);
		robPushRelation.setCompId(compId);
		robPushRelation.setMemberId(memberId);
		robPushRelation.setCreateTime(new Date());
		return robPushRelation;
	}
	@Override
	public void cancelRob(Long broadcastId,Long memberId){
		if(PubMethod.isEmpty(broadcastId)){
			throw new ServiceException("openapi.RobInfoServiceImpl.cancelRob.001","取消广播，broadcastId参数非空异常");			
		}
		//清除缓存
		clearRobCache(broadcastId);
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -TIME);
		String dateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
		int count = this.robInfoMapper.cancelRob(broadcastId, memberId, dateStr);
		if(count != 1){
			RobInfo robInfo = this.robInfoMapper.selectByPrimaryKey(broadcastId);
			if(PubMethod.isEmpty(robInfo)){
				throw new ServiceException("openapi.RobInfoServiceImpl.cancelRob.002","取消广播，获取广播信息异常");		
			}
			this.ehcacheService.put("robInfoCache", String.valueOf(broadcastId),robInfo);
			 if(robInfo.getBroadcastStatus() == 1){
				throw new ServiceException("openapi.RobInfoServiceImpl.cancelRob.003","取消广播，广播信息已被抢");
			 }else if(robInfo.getBroadcastStatus() == 2){
				throw new ServiceException("openapi.RobInfoServiceImpl.cancelRob.004","取消广播，广播信息已超时");
			 }
			 Long countdown = TIME*60*1000-(date.getTime()- robInfo.getCreateTime().getTime());
			 if(countdown <= 0){
				//执行方法更新超时状态
				clearRobCache(broadcastId);
				this.robInfoMapper.updateTimeoutStatus(broadcastId);
				throw new ServiceException("openapi.RobInfoServiceImpl.cancelRob.005","取消广播，广播信息已超时");
			 }
			 throw new ServiceException("openapi.RobInfoServiceImpl.cancelRob.006","取消广播，广播取消异常");			
		 }
	}
	@Override
	public List<Map<String,Object>> queryRob(Long memberId,double longitude,double latitude) throws ParseException{
		if(PubMethod.isEmpty(memberId)){
			throw new ServiceException("openapi.RobInfoServiceImpl.queryRob.001","查询广播列表，memberId参数非空异常");	
		}
		if(PubMethod.isEmpty(longitude)){
			throw new ServiceException("openapi.RobInfoServiceImpl.queryRob.002","查询广播列表，longitude参数异常");	
		}
		if(PubMethod.isEmpty(latitude)){
			throw new ServiceException("openapi.RobInfoServiceImpl.queryRob.003","查询广播列表，latitude参数异常");	
		}
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -TIME);
		String dateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
		List<Map<String,Object>> returnData = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> robDataList = this.ehcacheService.get("robDataCache", String.valueOf(memberId),ArrayList.class);
		if(PubMethod.isEmpty(robDataList)){
			robDataList = this.robInfoMapper.queryRob(memberId, dateStr);
			this.ehcacheService.put("robDataCache", String.valueOf(memberId), robDataList);
		}
		for(Map<String,Object> map : robDataList){
			String address = map.get("address").toString();
			Date createDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(map.get("createTime").toString());
			Long countdown = TIME*60*1000-(date.getTime()- createDate.getTime());
			/*Date createDate = new Date(map.get("createTime").toString());
			Long countdown = TIME*60*1000-(date.getTime()- createDate.getTime());*/
			//Long countdown = TIME*60*1000-(date.getTime()- Long.parseLong(map.get("createTime").toString()));
//			map.put("address", address.length() > 5 ? address.substring(0, address.length()-5)+"……" : address);
			map.put("address", address);
			if(countdown < 1000){ //不到1s不再返回   Modify 20150706  由<=0  改为 <1000
				continue;
			}
			map.put("countdown", countdown > 0 ? countdown : 0);
			map.remove("createTime");
			double distance = DistanceUtil.getDistance(latitude, longitude,
					Double.parseDouble(map.get("senderLatitude").toString()), Double.parseDouble(map.get("senderLongitude").toString()));
			DecimalFormat df = new DecimalFormat("#####0.00");
	        df.setRoundingMode(RoundingMode.HALF_UP);
			if(distance>1000){
				map.put("distance", df.format(distance/1000)+"km");
			}else{
				map.put("distance", df.format(distance)+"m");
			}
			map.put("quotationAmount", df.format(Double.valueOf(map.get("quotationAmount").toString())));
			//parcelTotalCount  parcelTotalWeight  quotationAmount
			map.put("averageWeight",df.format(Double.parseDouble(map.get("parcelTotalWeight").toString())/Integer.parseInt(map.get("parcelTotalCount").toString())));
			map.put("averagePrice", df.format(Double.parseDouble(map.get("quotationAmount").toString())/Integer.parseInt(map.get("parcelTotalCount").toString())));
			map.remove("senderLatitude");
			map.remove("senderLongitude");
			if(PubMethod.isEmpty(map.get("appointTime"))){
				map.put("appoint","立即");
			}else{
				Date appointDate;
				if(map.get("appointTime").toString().matches("^[0-9]+$")){
					appointDate = new Date(Long.parseLong(map.get("appointTime").toString()));
				}else{
					appointDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(map.get("appointTime").toString());
				}
				map.put("appoint",(appointDate.getHours()<10 ? "0" : "") +appointDate.getHours()+":"+(appointDate.getMinutes()<10 ? "0" : "")+appointDate.getMinutes());
			}
			map.remove("appointTime");
			returnData.add(map);
		}
		System.out.println("************************执行了查询，总数量为："+returnData.size());
		System.out.println("************************执行了查询，数据为："+JSON.toJSONString(returnData));
		return returnData;
	}
	@Override
	public Map<String,Object> queryRobByBroadcastId(Long broadcastId){
		if(PubMethod.isEmpty(broadcastId)){
			throw new ServiceException("openapi.RobInfoServiceImpl.queryRobByBroadcastId.001","查询广播详情，broadcastId参数非空异常");	
		}
		RobInfo robInfo = this.ehcacheService.get("robInfoCache", String.valueOf(broadcastId),RobInfo.class);
		if(PubMethod.isEmpty(robInfo)){
			robInfo = this.robInfoMapper.selectByPrimaryKey(broadcastId);
			this.ehcacheService.put("robInfoCache", String.valueOf(broadcastId),robInfo);
		}
		if(PubMethod.isEmpty(robInfo)){
			throw new ServiceException("openapi.RobInfoServiceImpl.queryRobByBroadcastId.002","查询广播详情，获取广播信息异常");
		}else{
			//状态异常可以查询广播信息  Modify 20150706
			/*if(robInfo.getBroadcastStatus() == 1){
				throw new ServiceException("openapi.RobInfoServiceImpl.queryRobByBroadcastId.003","查询广播详情，广播信息已被抢");
			}else if(robInfo.getBroadcastStatus() == 2){
				throw new ServiceException("openapi.RobInfoServiceImpl.queryRobByBroadcastId.004","查询广播详情，广播信息已超时");
			}else if(robInfo.getBroadcastStatus() == 3){
				throw new ServiceException("openapi.RobInfoServiceImpl.queryRobByBroadcastId.005","查询广播详情，广播信息已取消");
			}*/
		}
		Date date = new Date();
		Long countdown = TIME*60*1000-(date.getTime()- robInfo.getCreateTime().getTime());
		if(countdown <= 0){
			clearRobCache(broadcastId);
			//执行方法更新超时状态
			this.robInfoMapper.updateTimeoutStatus(broadcastId);
			//状态异常可以查询广播信息  Modify 20150706
			/*throw new ServiceException("openapi.RobInfoServiceImpl.queryRobByBroadcastId.006","查询广播详情，广播信息已超时");*/
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("broadcastId", robInfo.getBroadcastId());
		returnMap.put("broadcastRemark", robInfo.getBroadcastRemark());
		returnMap.put("parcelTotalCount", robInfo.getParcelTotalCount());
		returnMap.put("quotationAmount", robInfo.getQuotationAmount().setScale(2,BigDecimal.ROUND_HALF_UP));
		returnMap.put("averagePrice", (robInfo.getQuotationAmount().divide(new BigDecimal(robInfo.getParcelTotalCount()))).setScale(2,BigDecimal.ROUND_HALF_UP));
		String address = (PubMethod.isEmpty(robInfo.getSenderAddressName()) ? "" : robInfo.getSenderAddressName()) 
				+ (PubMethod.isEmpty(robInfo.getSenderDetailAddressName()) ? "" : robInfo.getSenderDetailAddressName());
//		returnMap.put("address", address.length() > 5 ? address.substring(0, address.length()-5)+"……" : address);
		returnMap.put("address", address);
		List<Map<String,Object>> parcelInfoList  = this.queryParcelInfo(broadcastId);//抢单广播id查询包裹信息列表
		if(PubMethod.isEmpty(parcelInfoList)){
			throw new ServiceException("openapi.RobInfoServiceImpl.queryRobByBroadcastId.007","查询广播详情，获取包裹列表异常");
		}
		returnMap.put("parcelInfoList", parcelInfoList);
		if(PubMethod.isEmpty(robInfo.getAppointTime())){
			returnMap.put("appoint","立即");
		}else{
			returnMap.put("appoint",(robInfo.getAppointTime().getHours()<10 ? "0" : "") +robInfo.getAppointTime().getHours()+":"+(robInfo.getAppointTime().getMinutes()<10 ? "0" : "")+robInfo.getAppointTime().getMinutes());
		}
		return returnMap;
	}
	@Override
	public Map<String,Object> rob(Long broadcastId,Long memberId) throws NumberFormatException, Exception{
		if(PubMethod.isEmpty(broadcastId)){
			throw new ServiceException("openapi.RobInfoServiceImpl.rob.001","抢单，broadcastId参数非空异常");	
		}
		if(PubMethod.isEmpty(memberId)){
			throw new ServiceException("openapi.RobInfoServiceImpl.rob.002","抢单，memberId参数非空异常");	
		}
		//清除缓存
		clearRobCache(broadcastId);
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -TIME);
		String dateStr = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
		Map<String, Object> memberMap = memberInfoService.findMemberInfoFromAudit(memberId);
		if(PubMethod.isEmpty(memberMap) || PubMethod.isEmpty(memberMap.get("compId"))){
			throw new ServiceException("openapi.RobInfoServiceImpl.rob.003","抢单，获取人员归属信息异常");
		}
		Long compId = Long.parseLong(memberMap.get("compId").toString());
		int count  = this.robInfoMapper.rob(broadcastId, compId, memberId, dateStr);
		RobInfo robInfo = this.robInfoMapper.selectByPrimaryKey(broadcastId);
		if(PubMethod.isEmpty(robInfo)){
			throw new ServiceException("openapi.RobInfoServiceImpl.rob.004","抢单，获取广播信息异常");		
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(count != 1){
			if(robInfo.getBroadcastStatus() == 1){
				throw new ServiceException("openapi.RobInfoServiceImpl.rob.005","抢单，广播信息已被抢");
			}else if(robInfo.getBroadcastStatus() == 2){
				throw new ServiceException("openapi.RobInfoServiceImpl.rob.006","抢单，广播信息已超时");
			}else if(robInfo.getBroadcastStatus() == 3){
				throw new ServiceException("openapi.RobInfoServiceImpl.rob.007","抢单，广播信息已取消");
			}
			/*if(robInfo.getBroadcastStatus() == 1 || robInfo.getBroadcastStatus() == 2 || robInfo.getBroadcastStatus() == 3){
				returnMap.put("broadcastStatus", robInfo.getBroadcastStatus());
				return returnMap;
				throw new ServiceException("openapi.RobInfoServiceImpl.rob",robInfo.getBroadcastStatus().toString());
			}*/
			Long countdown = TIME*60*1000-(date.getTime()- robInfo.getCreateTime().getTime());
			if(countdown <= 0){
				clearRobCache(broadcastId);
				//执行方法更新超时状态
				this.robInfoMapper.updateTimeoutStatus(broadcastId);
				/*returnMap.put("broadcastStatus", (short)2);
				return returnMap;*/
				//throw new ServiceException("openapi.RobInfoServiceImpl.rob","2");
				throw new ServiceException("openapi.RobInfoServiceImpl.rob.008","查询广播详情，广播信息已超时");
			}
			throw new ServiceException("openapi.RobInfoServiceImpl.rob.009","抢单，抢单异常");	
		}
		BasCompInfo basCompInfo = ehcacheService.get("compCache", PubMethod.isEmpty(compId)?null:compId.toString(), BasCompInfo.class);
		if(PubMethod.isEmpty(basCompInfo)) {
			basCompInfo = basCompInfoMapper.findById(compId);
		}
		if(PubMethod.isEmpty(basCompInfo)) {
			throw new ServiceException("openapi.RobInfoServiceImpl.rob.010","抢单，获取网点信息异常");
		}
		String address = (PubMethod.isEmpty(robInfo.getSenderAddressName()) ? "" : robInfo.getSenderAddressName()) 
				+" "+ (PubMethod.isEmpty(robInfo.getSenderDetailAddressName()) ? "" : robInfo.getSenderDetailAddressName());
		byte taskSource;
		if(robInfo.getBroadcastSource() == 1){
			taskSource = 3;
		}else{
			taskSource = 4;
		}
		//创建取件任务
		Map<String, Object> taskMap = takeTaskService.create(compId, memberId, null, memberId, basCompInfo.getBelongToNetId(), PubMethod.isEmpty(robInfo.getAppointTime()) ? null :(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(robInfo.getAppointTime()), robInfo.getBroadcastRemark(), memberId, robInfo.getSenderName(), robInfo.getSenderMobile(),
				robInfo.getSenderMobile(), robInfo.getSenderAddressId(), address, memberId, memberId, robInfo.getSenderLongitude(), 
				robInfo.getSenderLatitude(), taskSource, (byte)0, null, null, robInfo.getParcelTotalWeight(),  Byte.valueOf(robInfo.getParcelTotalCount().toString()), robInfo.getQuotationAmount(), (byte) 1, broadcastId, "");
		if(taskMap == null || taskMap.get("taskId") == null || "".equals(taskMap.get("taskId").toString())) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.rob.011", "抢单，创建取件任务异常");
		} 
		returnMap.put("address", robInfo.getSenderDetailAddressName());
		if(robInfo.getBroadcastSource() == 1){//来源电商管家
			String memberName = PubMethod.isEmpty(memberMap.get("memberName"))? "" :memberMap.get("memberName").toString();
			String compName = basCompInfo.getCompName();
			/*String compPhone = basCompInfo.getCompMobile();
			if(PubMethod.isEmpty(compPhone)){
				compPhone = basCompInfo.getCompTelephone();
			}*/
			BasNetInfo netInfo = ehcacheService.get("netCache", basCompInfo.getBelongToNetId().toString(), BasNetInfo.class);
			if (PubMethod.isEmpty(netInfo)) {
				netInfo=this.netInfoMapper.findById(basCompInfo.getBelongToNetId());
			}
			String netName = netInfo.getNetName();
			try {
				MemberInfo memberInfo = memberInfoService.getMemberInfoById(memberId);
				Map<String,String> map = new HashMap<String,String>();
				map.put("takeMember", memberName);
				map.put("takeMemberMobile", memberInfo.getMemberPhone());
				map.put("siteName", compName);
				//map.put("siteMobile",compPhone);
				map.put("expNetId",basCompInfo.getBelongToNetId().toString());
				map.put("expNetName",netName);
				map.put("broadcastId",String.valueOf(broadcastId));
				map.put("taskId",taskMap.get("taskId").toString());
				String url = constPool.getErpUrl() + "pullCallExpResultServiceChooseExpressAction.aspx";
				//???????回调电商管家接口
				String response = Post(url, map);
				Map<String,Object> result = JSON.parseObject(response);
				if(PubMethod.isEmpty(result) || !"success".equals(result.get("RESULT").toString())){
					throw new ServiceException("openapi.BroadcastListServiceImpl.rob.012", "抢单，回调ERP异常");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("openapi.BroadcastListServiceImpl.rob.013", "抢单，回调ERP异常");
			}
		}else{
			//推送个人端用户
		}
		return returnMap;
	}
	public void clearRobCache(Long broadcastId){
		try{
			this.ehcacheService.remove("robInfoCache", String.valueOf(broadcastId));
			  List<Object> robDataRelation = this.ehcacheService.get("robDataRelationCache", String.valueOf(broadcastId), ArrayList.class);
			  if(PubMethod.isEmpty(robDataRelation)){
				  robDataRelation = this.robPushRelationMapper.selectMemberIdsByBroadcastId(broadcastId);
				  this.ehcacheService.put("robDataRelationCache", String.valueOf(robDataRelation), robDataRelation);
			  }
			  for(Object obj : robDataRelation){
				  if(PubMethod.isEmpty(obj)){
					  continue;
				  }else{
					  this.ehcacheService.remove("robDataCache", String.valueOf(obj));
				  }
			  }
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException("openapi.BroadcastListServiceImpl.clearRobCache.001", "清除缓存，清除缓存异常");
		}
	}
}
