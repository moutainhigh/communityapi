package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasExpressserviceMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.ParParceladdressMapper;
import net.okdi.api.dao.ParParcelconnectionMapper;
import net.okdi.api.dao.ParParcelinfoMapper;
import net.okdi.api.dao.ParTaskDisposalRecordMapper;
import net.okdi.api.dao.ParTaskInfoMapper;
import net.okdi.api.dao.SendTaskMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasExpressservice;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.entity.ParParcelconnection;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.api.entity.ParTaskInfo;
import net.okdi.api.service.DispatchParService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.api.vo.TaskVo;
import net.okdi.api.vo.VO_ParParcelInfo;
import net.okdi.api.vo.VO_ParcelInfoAndAddressInfo;
import net.okdi.api.vo.VO_ParcelList;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.ReflectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

@Service
public class ParcelInfoServiceImpl  extends BaseServiceImpl<ParParcelinfo> implements ParcelInfoService {
	
	@Autowired
	private ParParcelinfoMapper parParcelinfoMapper; 
	@Autowired
	private ParParceladdressMapper parParceladdressMapper;
	@Autowired
	private EhcacheService ehcacheService;	
	@Autowired
	private BasNetInfoMapper basNetInfoMapper;
	@Autowired
	private BasCompInfoMapper basCompInfoMapper; 
	@Autowired
	private BasExpressserviceMapper basExpressserviceMapper;
	@Autowired
	private ParTaskInfoMapper parTaskInfoMapper;
	@Autowired
	private TakeTaskService takeTaskService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Value("${save.courier.head}")
	private String imageUrl;
	@Autowired
	private ParParcelconnectionMapper parParcelconnectionMapper;
	@Autowired
	DispatchParService dispatchParService;
	@Autowired
	ParTaskDisposalRecordMapper parTaskDisposalRecordMapper;
	@Autowired
	SendTaskMapper sendTaskMapper;
	public BaseDao getBaseDao() {
		return parParcelinfoMapper;
	}

	public String parseResult(String info) {
		return info;
	}
	
	//任务状态
		final Byte task_status_untake = 0; //待处理
		final Byte task_status_distribute = 1; //已分配
		final Byte task_status_finish = 2; //已完成
		final Byte task_status_cancel = 3; //已取消
		final Byte task_status_delete = 10; //任务删除
	/**
	 * 通过运单号和网络id查询包裹信息结果集
	 * @Method: findParcelDetailByWaybillNumAndNetId 
	 * @param wayBillNum
	 * @param netId
	 * @return 
	 * @see net.okdi.api.service.ParcelInfoService#findParcelDetailByWaybillNumAndNetId(java.lang.String, java.lang.Long)
	 */
	@Override
	public Map<Object, Object> findParcelDetailByWaybillNumAndNetId(   //提前ParcelInfo有未派件的信息
			String wayBillNum, Long netId) {
		Long parcelId = this.getParcelId(wayBillNum,netId);//1.先通过运单号和网络id从ParcelInfo表取出包裹id   --jinggq
		if(PubMethod.isEmpty(parcelId)) {
			return null;
		}
		ParParcelinfo parParcelinfo = this.getParcelInfoById(parcelId);//通过包裹id取出包裹内容信息  --jinggq
		ParParceladdress parParceladdress = this.getParParceladdressById(parcelId);//通过包裹id取出包裹地址信息  --jinggq
		VO_ParParcelInfo vo = this.createVO(parParcelinfo.getCompId(),parParcelinfo.getServiceId(),netId);//组装公司网络服务产品信息
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			ReflectUtil.getFileValue(map, parParcelinfo);
			ReflectUtil.getFileValue(map, vo);
			ReflectUtil.getFileValue(map, parParceladdress);
		} catch (Exception e) {
			throw new ServiceException("openapi.ParcelInfoServiceImpl.findParcelDetailByWaybillNumAndNetId.001","实体转换map异常");
		}
		return map;
	}

	
	
	private VO_ParParcelInfo createVO(Long compId, Long serviceId,Long netId) {
		VO_ParParcelInfo vo = new VO_ParParcelInfo();
		String netName = this.getNetName(netId);
		String compName = this.getCompName(compId);
		String serviceName = this.getServiceName(serviceId);
		vo.setNetName(netName);
		vo.setCompName(compName);
		vo.setServiceName(serviceName);
		return vo;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据服务产品id获取服务产品名称</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午3:06:43</dd>
	 * @param serviceId 
	 * @return
	 * @since v1.0
	 */
	private String getServiceName(Long serviceId) {
		BasExpressservice basExpressservice = this.ehcacheService.get("expressserviceCache", String.valueOf(serviceId), BasExpressservice.class);
		if(PubMethod.isEmpty(basExpressservice)){
			basExpressservice = this.basExpressserviceMapper.findById(serviceId);
			this.ehcacheService.put("expressserviceCache", String.valueOf(serviceId), basExpressservice);
		}
//		if(PubMethod.isEmpty(basExpressservice)){
//			throw new ServiceException("openapi.ParcelInfoServiceImpl.getServiceName.001","根据服务产品id获取服务产品名称操作异常 ");	
//		}
		return PubMethod.isEmpty(basExpressservice)?null:basExpressservice.getServiceName();
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据站点id获取站点名称</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午3:03:57</dd>
	 * @param compId
	 * @return
	 * @since v1.0
	 */
	private String getCompName(Long compId) {
		BasCompInfo basCompInfo = this.ehcacheService.get("compCache", String.valueOf(compId), BasCompInfo.class);
		if(PubMethod.isEmpty(basCompInfo)){
			basCompInfo = this.basCompInfoMapper.findById(compId);
			this.ehcacheService.put("compCache", String.valueOf(compId), basCompInfo);
		}
		if(PubMethod.isEmpty(basCompInfo)){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.getCompName.001","根据站点id获取站点名称操作异常 ");	
		}
		return basCompInfo.getCompName();
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据网络id获取网络名称</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午3:03:08</dd>
	 * @param netId
	 * @return
	 * @since v1.0
	 */
	private String getNetName(Long netId) {
		BasNetInfo basNetInfo = this.ehcacheService.get("netCache", String.valueOf(netId), BasNetInfo.class);
		if(PubMethod.isEmpty(basNetInfo)){
			basNetInfo = this.basNetInfoMapper.findById(netId);
			this.ehcacheService.put("netCache", String.valueOf(netId), basNetInfo);
		}
		if(PubMethod.isEmpty(basNetInfo)){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.getNetName.001","根据网络id获取网络名称操作异常 ");
		}
		return basNetInfo.getNetName();
	}
   /**
    * @Method: saveParcelInfo 
    * @param id
    * @param takeTaskId
    * @param sendTaskId
    * @param senderUserId
    * @param sendCustomerId
    * @param addresseeCustomerId
    * @param sendCasUserId
    * @param addresseeCasUserId
    * @param expWaybillNum
    * @param compId
    * @param netId
    * @param addresseeName
    * @param addresseeMobile
    * @param addresseeAddressId
    * @param addresseeAddress
    * @param sendName
    * @param sendMobile
    * @param sendAddressId
    * @param sendAddress
    * @param chareWeightForsender
    * @param freight
    * @param goodsPaymentMethod
    * @param codAmount
    * @param insureAmount
    * @param pricePremium
    * @param packingCharges
    * @param freightPaymentMethod
    * @param sendLongitude
    * @param sendLatitude
    * @param addresseeLongitude
    * @param addresseeLatitude
    * @param goodsDesc
    * @param parcelRemark
    * @param serviceId
    * @param signMember
    * @param createUserId
    * @param parcelEndMark
    * @param actualTakeMember
    * @param actualSendMember
    * @param receiptId
    * @param parcelStatus
    * @param actualCodAmount
    * @param disposalDesc
    * @return 
    * @see net.okdi.api.service.ParcelInfoService#saveParcelInfo(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.lang.Short, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.lang.Short, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Short, java.math.BigDecimal, java.lang.String)
    */
	public Map<String, Object> saveParcelInfo(Long id, Long takeTaskId, Long sendTaskId, Long senderUserId,
			Long sendCustomerId, Long addresseeCustomerId, Long sendCasUserId, Long addresseeCasUserId,
			String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile, 
			Long addresseeAddressId, String addresseeAddress, String sendName, String sendMobile, 
			Long sendAddressId, String sendAddress, BigDecimal chareWeightForsender, BigDecimal freight, Short goodsPaymentMethod, 
			BigDecimal codAmount, BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges,
			Short freightPaymentMethod, BigDecimal sendLongitude, BigDecimal sendLatitude, BigDecimal addresseeLongitude,
			BigDecimal addresseeLatitude, String goodsDesc, String parcelRemark, Long serviceId, String signMember,
			Long createUserId, String parcelEndMark, Long actualTakeMember, Long actualSendMember, Long receiptId,
			Short parcelStatus,BigDecimal actualCodAmount, String disposalDesc
			){
		if(!PubMethod.isEmpty(expWaybillNum) && expWaybillNum.length() > 32){
			expWaybillNum = expWaybillNum.substring(0,32);
		}
		//System.out.println(expWaybillNum);
		//System.out.println(expWaybillNum.length());
		Long idReturn = null;
		
		ParParcelinfo parcelInfo = new ParParcelinfo();
		
		ParParceladdress parceladdress = new ParParceladdress();
		
		ParParcelconnection connect = new ParParcelconnection();
		
		if(id == null || "".equals(id)){
			
			//最后需返回
			idReturn =  IdWorker.getIdWorker().nextId();
			
			parcelInfo.setId(idReturn);  //包裹id
			parceladdress.setId(idReturn);  //包裹地址id
		}else{
			idReturn = id;
			
			parcelInfo.setId(id);  //包裹id
			parceladdress.setId(id); //包裹地址id
		}
		

		//个人端发起的包裹,是一定有sendMobile(发件人电话)的.sendCustomerId等同于member_info表中的memberId
		if(!PubMethod.isEmpty(sendMobile) && PubMethod.isEmpty(sendCustomerId)){
			sendCustomerId = memberInfoService.getMemberId(sendMobile);
		}
		parcelInfo.setTakeTaskId(takeTaskId); //取件任务id
		parcelInfo.setSendTaskId(sendTaskId); //派件任务id
		parcelInfo.setSenderUserId(senderUserId); //发货方客户ID
		
		parceladdress.setSendCustomerId(sendCustomerId);  //发件客户ID
		parceladdress.setAddresseeCustomerId(addresseeCustomerId); //收件客户ID
		parceladdress.setSendCasUserId(sendCasUserId); //发件人CASID
		parceladdress.setAddresseeCasUserId(addresseeCasUserId); //收件人CASID 
		
		parcelInfo.setExpWaybillNum(expWaybillNum); //运单号
		parcelInfo.setCompId(compId);  //公司id
		parcelInfo.setNetId(netId);    //网络id
		
		parceladdress.setAddresseeName(addresseeName); //收件人姓名  
		parceladdress.setAddresseeMobile(addresseeMobile); //收件人手机号码
		parceladdress.setAddresseeAddressId(addresseeAddressId); //收件人乡镇id 
		parceladdress.setAddresseeAddress(addresseeAddress); //收件人详细地址
		
		parceladdress.setSendName(sendName); //发件人姓名
		parceladdress.setSendMobile(sendMobile); //发件人手机
		parceladdress.setSendAddressId(sendAddressId); //发件人乡镇id
		parceladdress.setSendAddress(sendAddress); //发件人详细地址   
		
		parcelInfo.setChareWeightForsender(chareWeightForsender); //包裹重量
		parcelInfo.setFreight(freight); //包裹应收运费
		parcelInfo.setGoodsPaymentMethod(goodsPaymentMethod); //支付方式
		parcelInfo.setCodAmount(codAmount);//代收货款金额
		parcelInfo.setInsureAmount(insureAmount); //保价金额
		parcelInfo.setPricePremium(pricePremium); //保价费
		parcelInfo.setPackingCharges(packingCharges); //包装费
		parcelInfo.setFreightPaymentMethod(freightPaymentMethod);//运费支付方式
		parcelInfo.setActualCodAmount(actualCodAmount);//代收货款实际收到的货款金额
		
		parceladdress.setSendLongitude(sendLongitude); //发件人地址经度
		parceladdress.setSendLatitude(sendLatitude);   //发件人地址纬度
		parceladdress.setAddresseeLongitude(addresseeLongitude); //收件人地址经度
		parceladdress.setAddresseeLatitude(addresseeLatitude);  //收件人地址纬度
		
		parcelInfo.setGoodsDesc(goodsDesc); //产品描述
		parcelInfo.setParcelRemark(parcelRemark); //包裹备注
		
		parcelInfo.setServiceId(serviceId);  //服务产品ID  
		parcelInfo.setSignMember(signMember); //签收人
		parcelInfo.setCreateUserId(createUserId); //创建人id
		
		parcelInfo.setParcelEndMark(parcelEndMark); //包裹结束标志
		parcelInfo.setActualTakeMember(actualTakeMember); //取件人id
		if(actualTakeMember!=null){
			parcelInfo.setPickupTime(new Date());//取件时间
		}
		parcelInfo.setActualSendMember(actualSendMember); //派件人id
		parcelInfo.setReceiptId(receiptId); //收据id
		parcelInfo.setSignResult((short)0); //签收结果 0：未签收/拒签 1：签收
		parcelInfo.setTackingStatus((short)0);//包裹当前状态 0:在途,未签收 1:已签收
		parcelInfo.setParcelStatus(parcelStatus);
		parcelInfo.setDisposalDesc(disposalDesc);
		if(!PubMethod.isEmpty(disposalDesc)){
			parcelInfo.setExceptionTime(new Date ());//如果异常原因不为空则添加异常时间	
		}
		connect.setParId(parcelInfo.getId());
		connect.setCompId(compId);
		connect.setNetId(netId);
		connect.setCreateTime(new Date());
		connect.setExpMemberSuccessFlag((short) 1);
		//1.通过运单号查询实际派件人id   ---jinggq
		List<Long> querySendMemberByExpWaybillNum = this.parParcelinfoMapper.querySendMemberByExpWaybillNum(expWaybillNum);
		if(!PubMethod.isEmpty(querySendMemberByExpWaybillNum)){
			for (Long sendMember : querySendMemberByExpWaybillNum) {
				ehcacheService.remove("parcelIdsCacheByMemberId",PubMethod.isEmpty(sendMember)?"":String.valueOf(sendMember));
			}
		}
		if(!PubMethod.isEmpty(actualTakeMember)){
			connect.setExpMemberId(actualTakeMember);
			if(!PubMethod.isEmpty(takeTaskId)){
				connect.setTaskId(takeTaskId);
			}
			connect.setCosignFlag((short) 1);
		} else {
			connect.setExpMemberId(actualSendMember);
			if(!PubMethod.isEmpty(sendTaskId)){
				connect.setTaskId(sendTaskId);
			}
			connect.setCosignFlag((short) 2);
		}
		if(id == null || "".equals(id)){   //2.如果原来Parcelinfo表中没有这个记录 v   --jinggq
			addParcelConnection(connect);  //3. 插入 ParcelConnection表(收派过程监控表)  --jinggq
			parcelInfo.setCreateTime(new Date()); //设置包裹创建时间
			//进行包裹信息和包裹地址信息的插入操作
			parParcelinfoMapper.insertParcel(parcelInfo);    //4. 插入 ParcelInfo表  --jinggq
			parParceladdressMapper.insertParceladdress(parceladdress);   //5. 插入 Parceladdress表  --jinggq
			//
		}else{
			if(!PubMethod.isEmpty(parcelStatus) && 1 == parcelStatus){
				//个人端,抢单来的数据,是带包裹的,在接单王中,只有最后一次完成时访问后台,编辑写到内存里,  5558888666
				//但是不调用这个接口,所以在更新的方法中录入,在取件完成时包裹记录表中添加数据。
				addParcelConnection(connect);
			} 
			//6.进行包裹信息和包裹地址信息的更新操作  --jinggq
			parParcelinfoMapper.updateParcelSelective(parcelInfo);
			parParceladdressMapper.updateParceladdressSelective(parceladdress);
		}
		
		//放置包裹信息和包通过运单号和网络Id 查询包裹信息裹地址信息到缓存
		this.ehcacheService.remove("parcelInfoCache", String.valueOf(parcelInfo.getId()));
		System.out.println(parcelInfo.getId());
		this.ehcacheService.remove("parcelAddressCache", String.valueOf(parcelInfo.getId()));
		this.ehcacheService.remove("parcelIdCacheByExpWayBillNumAndNetId",String.valueOf(parcelInfo.getExpWaybillNum()+parcelInfo.getNetId()));
		this.ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(createUserId));
		this.ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(actualSendMember));
		this.ehcacheService.remove("takeIdsCacheByMemberId", String.valueOf(createUserId));
		this.ehcacheService.remove("takeIdsCacheByMemberId", String.valueOf(takeTaskId));
		this.ehcacheService.remove("queryAlreadySignList", String.valueOf(actualSendMember));
		this.ehcacheService.remove("queryAlreadySignList", String.valueOf(createUserId));
		this.ehcacheService.remove("sendTaskCache", String.valueOf(createUserId));
		clearQueryTaskByReceipt(createUserId, parcelInfo.getId());  
		cleanQueryInfo("takeParcelIdsCacheByMemberId", String.valueOf(compId));
		dispatchParService.clearDispatchCache();
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("id", idReturn);
		return map;
	}
	private void cleanQueryInfo(String cacheName, String key) throws ServiceException {
		String queryresult = ehcacheService.get(cacheName, key, String.class);
		List<String> querylist = null;
		if(!PubMethod.isEmpty(queryresult)) {
			querylist = JSON.parseArray(queryresult, String.class);
		}
		if(!PubMethod.isEmpty(querylist)) {
			ehcacheService.remove(cacheName, querylist);
		}
	}
	/**
	 * 
	 * @Method: deleteParcelInfoByParcelId 
	 * @param id  包裹id
	 * @see net.okdi.api.service.ParcelInfoService#deleteParcelInfoByParcelId(java.lang.Long)
	 */
	@Override
	public void deleteParcelInfoByParcelId(Long id,String expWayBillNum,Long netId) throws ServiceException {
		if(PubMethod.isEmpty(id)){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.001","包裹id不能为空");
		}
		if(PubMethod.isEmpty(expWayBillNum)){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.002","包裹expWayBillNum不能为空");
		}
		if(PubMethod.isEmpty(netId)){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.003","包裹netId不能为空");
		}
		ParParcelinfo parParcelinfo = this.ehcacheService.get("parcelInfoCacheByParcelId", String.valueOf(expWayBillNum+netId), ParParcelinfo.class);
		if(PubMethod.isEmpty(parParcelinfo)){
			parParcelinfo = this.parParcelinfoMapper.findById(id);
		}
		if(PubMethod.isEmpty(parParcelinfo)){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.004","通过id查询包裹内容信息异常");
		}else if(!id.equals(parParcelinfo.getId())){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.005","参数不匹配");			
		}
		//删除包裹内容信息
		int a = this.parParcelinfoMapper.deleteParcel(id);
		if(a == 0){//删除失败
			throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.006","删除包裹内容信息异常");				
		}
		//删除包裹地址信息
		int b = this.parParceladdressMapper.deleteParceladdress(id);
		if(b == 0){//删除失败
			throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.007","删除包裹地址信息异常");				
		}
		this.ehcacheService.remove("parcelIdCacheByExpWayBillNumAndNetId", String.valueOf(expWayBillNum+netId));
		//清除包裹内容信息缓存 key parParcelinfo.getExpWaybillNum()+parParcelinfo.getNetId()
		this.ehcacheService.remove("parcelInfoCache", String.valueOf(id));
		//清除包裹地址信息缓存 key
		this.ehcacheService.remove("parcelAddressCache", String.valueOf(id));
		dispatchParService.clearDispatchCache();
	}
	/**
	 * 扫描单号后判断是否为系统中已存在的包裹，如果已存在则返回包裹ID
	 * @Method: queryParcelInfoByExpWayBillNum 
	 * @param expWayBillNum  包裹运单号 
	 * @return 
	 * @see net.okdi.api.service.ParcelInfoService#queryParcelInfoByExpWayBillNum(java.lang.String)
	 */
	@Override
	public Long queryParcelInfoByExpWayBillNumAndNetId(Long netId,String expWayBillNum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("netId", netId);
		map.put("expWayBillNum", expWayBillNum);
		if(PubMethod.isEmpty(netId)){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.queryParcelInfoByExpWayBillNumAndNetId.001","网络netId不能为空");
		}
		if(PubMethod.isEmpty(expWayBillNum)){
			throw new ServiceException("openapi.ParcelInfoServiceImpl.queryParcelInfoByExpWayBillNumAndNetId.002","运单号expWayBillNum不能为空");
		}
		Long parcelId = this.ehcacheService.get("parcelIdCacheByExpWayBillNumAndNetId", String.valueOf(expWayBillNum+netId), Long.class);
		if(PubMethod.isEmpty(parcelId)){
			parcelId = this.parParcelinfoMapper.getParcelId(map);
		}
		return parcelId;
	}
	
	/**
	 * 通过运单号和网络id取出包裹id
	 * @Method: getParcelId 
	 * @param expWayBillNum
	 * @param netId
	 * @return 
	 * @see net.okdi.api.service.ParcelInfoService#getParcelId(java.lang.String, java.lang.Long)
	 */
	@Override
	public Long getParcelId(String expWayBillNum, Long netId) {
		Long parcelId = this.ehcacheService.get("parcelIdCacheByExpWayBillNumAndNetId", String.valueOf(expWayBillNum+netId), Long.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("expWayBillNum", expWayBillNum);
		map.put("netId", netId);
		if(PubMethod.isEmpty(parcelId)){
			parcelId = this.parParcelinfoMapper.getParcelId(map);
			this.ehcacheService.put("parcelIdCacheByExpWayBillNumAndNetId", String.valueOf(expWayBillNum+netId), parcelId);
		}
		return parcelId;
	}
	/**
	 * 通过包裹id查询包裹内容信息
	 * @Method: getParcelInfoById 
	 * @param id
	 * @return 
	 * @see net.okdi.api.service.ParcelInfoService#getParcelInfoById(java.lang.Long)
	 */
	@Override
	public ParParcelinfo getParcelInfoById(Long id) {
		ParParcelinfo parParcelinfo = this.parParcelinfoMapper.findById(id);
//		ParParcelinfo parParcelinfo = this.ehcacheService.get("parcelInfoCache", String.valueOf(id), ParParcelinfo.class);
//		if(PubMethod.isEmpty(parParcelinfo)){
//			this.ehcacheService.put("parcelInfoCache", String.valueOf(id), parParcelinfo);
//		}
		return parParcelinfo;
	}
	
	@Override
	public ParParcelinfo getParcelInfoByIdNoEnd(Long id) {
		return this.parParcelinfoMapper.getParcelInfoByIdNoEnd(id);
	}
	
	/**
	 * 通过包裹id查询包裹地址信息
	 * @Method: getParParceladdressById 
	 * @param id
	 * @return 
	 * @see net.okdi.api.service.ParcelInfoService#getParParceladdressById(java.lang.Long)
	 */
	@Override
	public ParParceladdress getParParceladdressById(Long id) {
		ParParceladdress parParceladdress = this.ehcacheService.get("parcelAddressCache", String.valueOf(id), ParParceladdress.class);
		if(PubMethod.isEmpty(parParceladdress)){
			parParceladdress = this.parParceladdressMapper.findParceladdress(id);
			this.ehcacheService.put("parcelAddressCache", String.valueOf(id), parParceladdress);
		}
		return parParceladdress;
	}
	/**
	 * 根据登录人ID查询 待派包裹列表
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:07:41</dd>
	 * @param memberId  收派员ID 
	 * @return
	 * @since v1.0
	 */
	@Override
	public Long getParcelListBySendMemberIdCount(Long memberId) {
		return this.parParcelinfoMapper.getParcelListBySendMemberIdCount(memberId);	
	}
	
	@Override
	public List<Map<String, Object>> queryParcelListBySendMemberId(Long memberId,Integer currentPage,Integer pageSize) {
		List<Long> list = this.getParcelListBySendMemberId(memberId,currentPage,pageSize);//1.通过派件人id查询待派包裹的ids --jinggq
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		if(!PubMethod.isEmpty(list)){
			for(Long id :list){  //循环每一个id，单表一个一个的查  --jinggq
				Map<String,Object> map = new HashMap<String,Object>();
				ParParcelinfo parParcelinfo = this.getParcelInfoById(id);    //查询parcel详情 --jinggq
				ParParceladdress parParceladdress = this.getParParceladdressById(id);    //查询parcel地址详情 --jinggq
				if(!PubMethod.isEmpty(parParcelinfo)&&!PubMethod.isEmpty(parParceladdress)){
					map.put("waybillNum", parParcelinfo.getExpWaybillNum());//运单号
					map.put("parcelId", parParcelinfo.getId());//包裹id
					map.put("createTime", parParcelinfo.getCreateTime());//包裹创建时间
					map.put("freight", parParcelinfo.getFreight());//应收运费
//					map.put("totalGoodsAmount", parParcelinfo.getTotalGoodsAmount());//应收货款
					map.put("totalGoodsAmount", parParcelinfo.getCodAmount());//应收货款
					map.put("addresseeName", parParceladdress.getAddresseeName());//收件人姓名
					map.put("addresseeMobile", parParceladdress.getAddresseeMobile());//收件人手机
					map.put("addresseePhone", parParceladdress.getAddresseePhone());//收件人电话
					map.put("addresseeAddressId", parParceladdress.getAddresseeAddressId());//收件人地址id
					map.put("sendCustomerId", parParceladdress.getSendCustomerId());//发件客户id/发件人id
					
					String addresseeAddress = parParceladdress.getAddresseeAddress();
					if(PubMethod.isEmpty(addresseeAddress)){
						map.put("addresseeAddress", "");//发件人详细地址  
						map.put("addresseeAddressmsg", ""); 
					} else {
						StringBuffer sb = new StringBuffer();
						String[] split = addresseeAddress.split(" ");
						map.put("addresseeAddress",split[0]);
						for(int i = 1; i < split.length ; i++){
							sb.append(split[i]);
						}
						map.put("addresseeAddressmsg",sb);
					}
					listResult.add(map);	
				}
			}	
		}
		return listResult;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过派件人id查询待派包裹的ids</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午7:57:17</dd>
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	@SuppressWarnings("unchecked")
	private List<Long> getParcelListBySendMemberId(Long memberId,Integer currentPage,Integer pageSize) {
		
		//通过memberId取出缓存中的待派包裹ids列表
		Map<String,List<Long>> cacheMap = this.ehcacheService.get("parcelIdsCacheByMemberId", String.valueOf(memberId), Map.class); 
		if(null == cacheMap){
			cacheMap = new HashMap<String,List<Long>>();
		}
		if(PubMethod.isEmpty(cacheMap)  || PubMethod.isEmpty(cacheMap.get(memberId+"_"+currentPage+"_"+pageSize)) ){
			Map<String,Object> parMap = new HashMap<String,Object>();
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			parMap.put("memberId", memberId);
			parMap.put("page", page);
			List<Long> resultList = this.parParcelinfoMapper.queryParcelIdsByMemberId(parMap);
			cacheMap.put(memberId+"_"+currentPage+"_"+pageSize, resultList);
			this.ehcacheService.put("parcelIdsCacheByMemberId", String.valueOf(memberId), cacheMap);
		}
		return cacheMap.get(memberId+"_"+currentPage+"_"+pageSize);
	}
	
	/**
	 * 更新包裹状态及结算状态
	 * @Method: updateParcelSettleAccounts 
	 * @param parcelId 包裹ID
	 * @param accSrecpayvouchersId 收款凭证ID
	 * @param memberId 收派员ID
	 * @param type 0取件 1派件
	 * @see net.okdi.api.service.ParcelInfoService#updateParcelSettleAccounts(java.lang.Long, java.lang.Long, java.lang.Long, short)
	 */
	public void updateParcelSettleAccounts(Long parcelId,Double totalCodAmount,Double totalFreight,Long accSrecpayvouchersId,Long memberId,short type){
		ParParcelinfo parParcelinfo = this.ehcacheService.get("parcelInfoCache", String.valueOf(parcelId), ParParcelinfo.class);
		if(PubMethod.isEmpty(parParcelinfo)){
			parParcelinfo = this.parParcelinfoMapper.findById(parcelId);
		}
		if(!PubMethod.isEmpty(parParcelinfo)){
			if(totalFreight != null){
				parParcelinfo.setFreightPaymentStatus((short)1);//运费已收
				parParcelinfo.setFreightPaymentTime(new Date());//费用支付时间
			}
			parParcelinfo.setReceiptId(accSrecpayvouchersId);
			if(type==1){
				if(totalCodAmount != null){
					parParcelinfo.setCodIsRecovery((short)1);//代收货款已收回
				}
				parParcelinfo.setTackingStatus((short)1);//已签收
				parParcelinfo.setSignResult((short)1);//已签收
				parParcelinfo.setParcelEndMark("1");//结束
				parParcelinfo.setSignTime(new Date ());
			}
			parParcelinfoMapper.updateParcel(parParcelinfo);
		   clearQueryTaskByReceipt(memberId, parcelId);
		    dispatchParService.clearDispatchCache();
			this.ehcacheService.put("parcelInfoCache", String.valueOf(parcelId), parParcelinfo);
		}
	}
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询取件任务(只查询该收派员一天的取件任务)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param actualTakeMember 实际取件人Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@Override
	public List queryTakeTaskList(Long actualTakeMember) {
		List resultList = new ArrayList();
		ParParceladdress parParceladdressthis = null;
		List<String>  TakeTaskList = this.ehcacheService.get("takeIdsCacheByMemberId", String.valueOf(actualTakeMember), List.class);
		if(PubMethod.isEmpty(TakeTaskList)  || "[]".equals(TakeTaskList)){
			TakeTaskList = new ArrayList();
			TakeTaskList = this.parParcelinfoMapper.queryTakeTaskList(actualTakeMember);
			this.ehcacheService.put("takeIdsCacheByMemberId", String.valueOf(actualTakeMember),TakeTaskList);
		} else {
			//如果有一个包裹不是今天的,就在查询一遍
			if(!IfTime(TakeTaskList)){
				TakeTaskList = new ArrayList(); 
				TakeTaskList = this.parParcelinfoMapper.queryTakeTaskList(actualTakeMember);
				this.ehcacheService.put("takeIdsCacheByMemberId", String.valueOf(actualTakeMember),TakeTaskList);
			}
		}
		if(PubMethod.isEmpty(TakeTaskList) || "[]".equals(TakeTaskList)){
			return resultList;
		}
		for (Object object : TakeTaskList) {
			    Map<String,Object> resultMap = new HashMap<String,Object>();
		 	    JSONObject parseObject = JSON.parseObject(object.toString().replaceAll("=",":"));
		 	    String id =  parseObject.getString("id");
			    parParceladdressthis = ehcacheService.get("parcelAddressCache", id, ParParceladdress.class);
			if(PubMethod.isEmpty(parParceladdressthis)){
				parParceladdressthis = this.parParceladdressMapper.findParceladdress(Long.parseLong(id));
				this.ehcacheService.put("parcelAddressCache", id, parParceladdressthis);
			}
			resultMap.put("taskCount",parseObject.getLong("task"));//已取件数
			resultMap.put("receiptId",parseObject.getLong("receiptId"));//已取件数
			resultMap.put("sendName" ,parParceladdressthis.getSendName());//发件人姓名
			resultMap.put("sendMobile", parParceladdressthis.getSendMobile());//发件人手机
			resultMap.put("sendPhone",parParceladdressthis.getSendPhone());//发件人电话
			String sendAddress =  parParceladdressthis.getSendAddress();
			if(PubMethod.isEmpty(sendAddress)){
				resultMap.put("sendAddress", "");//发件人详细地址 
				resultMap.put("sendAddressMsg", ""); 
			} else {
				StringBuffer sb = new StringBuffer();
				String[] split = sendAddress.split(" ");
				resultMap.put("sendAddress",split[0]);
				for(int i = 1; i < split.length ; i++){
					sb.append(split[i]);
				}
				resultMap.put("sendAddressMsg",sb);
			}
			resultMap.put("Id", parParceladdressthis.getId());//主键Id
			resultMap.put("time", parseObject.getLong("time"));//时间
			resultMap.put("freight", parseObject.getLong("freight"));//时间
//			resultMap.put("sendAddressId", parParceladdressthis.getSendAddressId());//发件乡镇ID
//			resultMap.put("addresseeAddress", parParceladdressthis.getAddresseeAddress());//取件详细地址
//			resultMap.put("addresseeAddressId", parParceladdressthis.getAddresseeAddressId());//取件地址Id
//			resultMap.put("expWayBillNum", parseObject.getLong("expWayBillNum"));//运单号
			resultList.add(resultMap);
		}
		return resultList;
	} 
	private boolean IfTime(List taketaskList){
        try {
        	for (Object object : taketaskList) {
        		Long time  = JSON.parseObject(object.toString()).getLong("time");
//        		System.out.println("任务创建时间:"+time);
//        		System.out.println("当天12点时间:"+dayss());
        		if(time <= dayss()){
        			 return false;
        		 }
			}
        } catch (Exception e) {
            e.printStackTrace(); 
        }
		return true;
	}
 	private Long dayss(){
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
 		return calendar.getTimeInMillis();
 	}
 	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询一个运单号的包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param actualTakeMember 实际取件人Id
	 * @param receiptId 运单Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
 	@Override
 	public List  queryTakeByWaybillNum(Long actualTakeMember,Long receiptId){
 		ParParcelinfo parcelInfo = new ParParcelinfo();
 		parcelInfo.setActualTakeMember(actualTakeMember);
 		parcelInfo.setReceiptId(receiptId);
 		List<String>  queryTakeList = this.ehcacheService.get("queryTakeByWaybillNum", String.valueOf(actualTakeMember)+String.valueOf(receiptId), List.class);
 		if(PubMethod.isEmpty(queryTakeList)){
 			queryTakeList = this.parParcelinfoMapper.queryTakeByWaybillNum(parcelInfo);
			this.ehcacheService.put("queryTakeByWaybillNum",String.valueOf(actualTakeMember) + String.valueOf(receiptId), queryTakeList);
		} else {
			if(!IfTime(queryTakeList)){
				queryTakeList = this.parParcelinfoMapper.queryTakeByWaybillNum(parcelInfo);
				this.ehcacheService.put("queryTakeByWaybillNum",String.valueOf(actualTakeMember) + String.valueOf(receiptId), queryTakeList);
			}
		}
 		return queryTakeList;
 	}
 	public void clearQueryTaskByReceipt(Long memberId, Long parcelId){
 		Long receiptId =parParcelinfoMapper.queryTaskByReceipt(parcelId);
 		if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(parcelId) || PubMethod.isEmpty(receiptId)){
 			return;
 		}
 		this.ehcacheService.remove("queryTakeByWaybillNum", String.valueOf(memberId)+String.valueOf(receiptId));
 	}
// 	private List resultList(List m){
// 		Map<String,Object> map = new HashMap<String,Object>();
// 		BigDecimal b =  new BigDecimal(0);
// 		for(int i = 0; i < m.size(); i++){
// 		   b = b.add(new BigDecimal( JSON.parseObject(String.valueOf(m.get(i)).replaceAll("=",":")).getDoubleValue("insureAmount"))
//	 			.add(new BigDecimal( JSON.parseObject(String.valueOf(m.get(i)).replaceAll("=",":")).getDoubleValue("freight")))
//	 			.add(new BigDecimal( JSON.parseObject(String.valueOf(m.get(i)).replaceAll("=",":")).getDoubleValue("packingCharges")))
//	 			.add(new BigDecimal( JSON.parseObject(String.valueOf(m.get(i)).replaceAll("=",":")).getDoubleValue("pricePremium"))));
// 		}
// 		map.put("sumPrice",String.valueOf(b));
// 		m.add(map);
// 		return m;
// 	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>更改收件人</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param memberId 收派员Id
	 * @param parceId 包裹Id
	 * @param TaskId 任务Id
	 * @param AddressId 收件人地址Id
	 * @param takePersonName 收件人姓名
	 * @param takePersonMoble 收件人电话
	 * @param takePersonAddress 收件人地址
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@Override
	public void modyfyTaskInfo(Long memberId, Long parceId, Long TaskId,
			Long AddressId, String takePersonName, String takePersonMoble,
			String takePersonAddress) {
		ParTaskInfo ParTaskInfo = new ParTaskInfo();
		ParParceladdress parceladdress = this.getParParceladdressById(parceId);
		parceladdress.setId(parceId);
		parceladdress.setAddresseeAddressId(AddressId);
		parceladdress.setAddresseeName(takePersonName);
		parceladdress.setAddresseeMobile(takePersonMoble);
		parceladdress.setAddresseeAddress(takePersonAddress);
		ParTaskInfo.setTaskId(TaskId);
		ParTaskInfo.setContactName(takePersonName);
		ParTaskInfo.setContactMobile(takePersonMoble);
		ParTaskInfo.setContactAddressId(AddressId);
		ParTaskInfo.setContactAddress(takePersonAddress);
		parParceladdressMapper.updateParceladdress(parceladdress);
		parTaskInfoMapper.updateTakeTask(ParTaskInfo);
		ehcacheService.put("parcelAddressCache", String.valueOf(parceladdress.getId()), parceladdress);
		clearQueryTaskByReceipt(memberId, parceId);
		ehcacheService.remove("takeIdsCacheByMemberId", String.valueOf(memberId));
		ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(memberId));
		ehcacheService.remove("TakeTaskCacheTaskInfo", String.valueOf(TaskId));
		ehcacheService.remove("sendTaskCache",String.valueOf(memberId));
		this.ehcacheService.remove("queryAlreadySignList", String.valueOf(memberId));
		dispatchParService.removeTaskCache();
		dispatchParService.clearDispatchCache();
		
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>将发件任务Id制空</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @author chuanshi.chai 更改掉这个方法，改成同时将错误原因置入
	 * @param memberId 收派员Id
	 * @param parceId 包裹Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@Override
	public void updateParcelStatus(Long parcelId,Long memberId,String errorMessage) {
		ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(memberId));
		dispatchParService.clearDispatchCache();
		if(!PubMethod.isEmpty(parcelId)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("parcelId", parcelId);
			map.put("errorMessage", errorMessage);
			map.put("disposalDesc", errorMessage);
			if ("3".equals(errorMessage)) {//errorMessage是3 表示直接取消提货 没有取消原因
				map.put("exceptionTime", null);
				map.put("errorMessage", null);
				map.put("disposalDesc", null);
			}else{
				map.put("exceptionTime", new Date ());
			}
			parParcelinfoMapper.updateParcelStatus(map);
		}
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询该取件任务下的包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param memberId 收派员Id
	 * @param takeTaskId 取件任务Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@Override
	public Map<String,Object>  queryTakeTaskParcel( Long takeTaskId) {
		List resultList = new ArrayList();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultList = this.ehcacheService.get("takeParcelIdsCacheByMemberId",String.valueOf(takeTaskId), List.class);
		if(PubMethod.isEmpty(resultList) || "[]".equals(resultList)){
			resultList =  parParcelinfoMapper.queryTakeTaskParcel(takeTaskId);
			ehcacheService.put("takeParcelIdsCacheByMemberId",String.valueOf(takeTaskId), resultList);
		}
		resultMap.put("resultList",resultList);
		return resultMap;
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>解除取件任务和包裹的关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param parcelIds 包裹Id多个用,分隔
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	@Override
	public void delTakeTaskRelationParcel(String parcelIds,Long takeId) {
		String [] ids =  parcelIds.split(",");
		parParcelinfoMapper.delTakeTaskRelationParcel(ids);
		this.ehcacheService.remove("takeParcelIdsCacheByMemberId", String.valueOf(takeId));
		this.ehcacheService.remove("takeParcelIdsCacheByTaskId",String.valueOf(takeId));
		dispatchParService.clearDispatchCache();
	}

	@Override
	public void addbatchSaveParcelInfo(List<VO_ParcelInfoAndAddressInfo> list) {
		this.parParcelinfoMapper.addbatchSaveParcelInfo(list);
		this.parParceladdressMapper.addbatchSaveParcelAddressInfo(list);
		this.ehcacheService.removeAll("parcelInfoCache");
		this.ehcacheService.removeAll("parcelAddressCache");
		dispatchParService.clearDispatchCache();
	}

	@Override
	public String deleteParcelByTakeTaskId(Long taskTaskId,Long netId) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		resultList = this.ehcacheService.get("takeParcelIdsCacheByMemberId",String.valueOf(taskTaskId), List.class);
		if(PubMethod.isEmpty(resultList) || "[]".equals(resultList)){
			resultList =  parParcelinfoMapper.queryTakeTaskParcel(taskTaskId);
			ehcacheService.put("takeParcelIdsCacheByMemberId",String.valueOf(taskTaskId), resultList);
		}
		for (Map<String, Object> map : resultList) {
			if(PubMethod.isEmpty(map)){
				continue;
			}
			if(PubMethod.isEmpty(map.get("exp_waybill_num"))){
				String id =  String.valueOf(map.get("id"));
				//删除包裹内容信息
				int a = this.parParcelinfoMapper.deleteParcel(Long.parseLong(id));
				if(a == 0){//删除失败
					throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelByTakeTaskId.002","删除包裹内容信息异常");				
				}
				//删除包裹地址信息
				int b = this.parParceladdressMapper.deleteParceladdress(Long.parseLong(id));
				if(b == 0){//删除失败
					throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelByTakeTaskId.003","删除包裹地址信息异常");				
				}
				this.ehcacheService.remove("parcelIdCacheByExpWayBillNumAndNetId", String.valueOf(netId));
				//清除包裹内容信息缓存 key parParcelinfo.getExpWaybillNum()+parParcelinfo.getNetId()
				this.ehcacheService.remove("parcelInfoCache", id);
				//清除包裹地址信息缓存 key
				this.ehcacheService.remove("parcelAddressCache", id);
			}
		}
		dispatchParService.clearDispatchCache();
		this.ehcacheService.remove("takeParcelIdsCacheByMemberId",String.valueOf(taskTaskId));
		return null;
	}

	@Override
	public Map<String,Object> queryByTakeTaskStatus(Long takeTaskId) {
		ParTaskInfo taskInfo = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		taskInfo = this.ehcacheService.get("TakeTaskCacheTaskInfo", String.valueOf(takeTaskId), ParTaskInfo.class);
		if(PubMethod.isEmpty(taskInfo)) {
			 taskInfo = takeTaskService.cacheTaskInfo(String.valueOf(takeTaskId));
		}
		Byte taskStatus = taskInfo.getTaskStatus();
		resultMap.put("status", "001");
		if(task_status_finish.equals(taskStatus)) {
			resultMap.put("status", "005");
			resultMap.put("message", "任务已完成");
		}
		if(task_status_cancel.equals(taskStatus)) {
			resultMap.put("status", "006");
			resultMap.put("message", "任务已取消");
		}
		if(task_status_delete.equals(taskStatus)) {
			resultMap.put("status", "007");
			resultMap.put("message", "任务已删除");
		}
		return resultMap;
	}
	
	
	@Override
	public List<Map<String, Object>> querySendParcelList(Long memberId) {
		List<Long> list = parParceladdressMapper.queryParcelIdsBySendUserId(memberId);
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		if(!PubMethod.isEmpty(list)){
			Map<String,Object> map = null;
			for(Long id :list){
				map = new HashMap<String,Object>();
				ParParcelinfo parParcelinfo = this.getParcelInfoById(id);
				ParParceladdress parParceladdress = this.getParParceladdressById(id);
				map.put("id", parParcelinfo.getId());
				map.put("expWaybillNum", parParcelinfo.getExpWaybillNum());
				map.put("senderType", parParcelinfo.getSenderType());
				map.put("senderCompId", parParcelinfo.getSenderCompId());
				map.put("senderUserId", parParcelinfo.getSenderUserId());
				map.put("ordOfSellerId", parParcelinfo.getOrdOfSellerId());
				map.put("totalGoodsAmount", parParcelinfo.getTotalGoodsAmount());
				map.put("insureAmount", parParcelinfo.getInsureAmount());
				map.put("tipAmount", parParcelinfo.getTipAmount());
				map.put("chareWeightForsender", parParcelinfo.getChareWeightForsender());
				map.put("chareWeightFortransit", parParcelinfo.getChareWeightFortransit());
				map.put("parcelVolume", parParcelinfo.getParcelVolume());
				map.put("goodsNum", parParcelinfo.getGoodsNum());
				map.put("parcelRemark", parParcelinfo.getParcelRemark());
				map.put("goodsPaymentMethod", parParcelinfo.getGoodsPaymentMethod());
				map.put("codAmount", parParcelinfo.getCodAmount());
				map.put("codIsRecovery", parParcelinfo.getCodIsRecovery());
				map.put("actualCodAmount", parParcelinfo.getActualCodAmount());
				map.put("freightPaymentMethod", parParcelinfo.getFreightPaymentMethod());
				map.put("freight", parParcelinfo.getFreight());
				map.put("freightPaymentStatus", parParcelinfo.getFreightPaymentStatus());
				map.put("paymentMethod", parParcelinfo.getPaymentMethod());
				map.put("goodsPaymentStatus", parParcelinfo.getGoodsPaymentStatus());
				map.put("freightPaymentTime", parParcelinfo.getFreightPaymentTime());
				map.put("serviceId", parParcelinfo.getServiceId());
				map.put("tackingStatus", parParcelinfo.getTackingStatus());
				map.put("signResult", parParcelinfo.getSignResult());
				map.put("signMember", parParcelinfo.getSignMember());
				map.put("signTime", parParcelinfo.getSignTime());
				map.put("signGoodsTotal", parParcelinfo.getSignGoodsTotal());
				map.put("createUserId", parParcelinfo.getCreateUserId());
				map.put("compId", parParcelinfo.getCompId());
				map.put("netId", parParcelinfo.getNetId());
				BasNetInfo netInfo = ehcacheService.get("netCache", String.valueOf(parParcelinfo.getNetId()), BasNetInfo.class);
				if(PubMethod.isEmpty(netInfo)){
					netInfo = basNetInfoMapper.findById(parParcelinfo.getNetId());
				}
				if(!PubMethod.isEmpty(netInfo)) {
					map.put("netName", netInfo.getNetName());
					map.put("netImage", "http://www.okdi.net/nfs_data/comp/" + netInfo.getNetId() + ".png");
					map.put("netCode", netInfo.getCode());
				} else {
					map.put("netName", "");
					map.put("netImage", "");
					map.put("netCode", "");
				}
				map.put("createTime", parParcelinfo.getCreateTime());
				map.put("signImgUrl", parParcelinfo.getSignImgUrl());
				map.put("parcelType", parParcelinfo.getParcelType());
				map.put("parcelEndMark", parParcelinfo.getParcelEndMark());
				map.put("takeTaskId", parParcelinfo.getTakeTaskId());
				map.put("sendTaskId", parParcelinfo.getSendTaskId());
				map.put("actualTakeMember", parParcelinfo.getActualTakeMember());
				map.put("actualSendMember", parParcelinfo.getActualSendMember());
				map.put("receiptId", parParcelinfo.getReceiptId());
				map.put("printFlag", parParcelinfo.getPrintFlag());
				map.put("noFly", parParcelinfo.getNoFly());
				map.put("packingCharges", parParcelinfo.getPackingCharges());
				map.put("pricePremium", parParcelinfo.getPricePremium());
				map.put("goodsDesc", parParcelinfo.getGoodsDesc());
				map.put("parcelStatus", parParcelinfo.getParcelStatus());
				map.put("sendName", parParceladdress.getSendName());
				map.put("sendAddressId", parParceladdress.getSendAddressId());
				map.put("sendAddress", parParceladdress.getSendAddress());
				map.put("sendMobile", parParceladdress.getSendMobile());
				map.put("sendPhone", parParceladdress.getSendPhone());
				map.put("sendZipcode", parParceladdress.getSendZipcode());
				map.put("sendCasUserId", parParceladdress.getSendCasUserId());
				map.put("sendCustomerId", parParceladdress.getSendCustomerId());
				map.put("sendContactId", parParceladdress.getSendContactId());
				map.put("addresseeName", parParceladdress.getAddresseeName());
				map.put("addresseeAddressId", parParceladdress.getAddresseeAddressId());
				map.put("addresseeAddress", parParceladdress.getAddresseeAddress());
				map.put("addresseeMobile", parParceladdress.getAddresseeMobile());
				map.put("addresseePhone", parParceladdress.getAddresseePhone());
				map.put("addresseeZipcode", parParceladdress.getAddresseeZipcode());
				map.put("addresseeCasUserId", parParceladdress.getAddresseeCasUserId());
				map.put("addresseeCustomerId", parParceladdress.getAddresseeCustomerId());
				map.put("addresseeContactId", parParceladdress.getAddresseeContactId());
				map.put("agencySiteId", parParceladdress.getAgencySiteId());
				map.put("sendLongitude", parParceladdress.getSendLongitude());
				map.put("sendLatitude", parParceladdress.getSendLatitude());
				map.put("addresseeLongitude", parParceladdress.getAddresseeLongitude());
				map.put("addresseeLatitude", parParceladdress.getAddresseeLatitude());
				if(!PubMethod.isEmpty(parParcelinfo.getNetId())) {
					listResult.add(map);
				}
			}
			map = new HashMap<String,Object>();
			map.put("Count", list.size());
			listResult.add(map);
		}
		return listResult;
	}
	
	@Override
	public void addParcelConnection(ParParcelconnection parParcelconnection) throws ServiceException {
		if(PubMethod.isEmpty(parParcelconnection)) {
			throw new ServiceException("openapi.ParcelInfoServiceImpl.addParcelConnection.001", "收派记录添加失败");
		}
		parParcelconnection.setId(IdWorker.getIdWorker().nextId());
		parParcelconnectionMapper.insertSelective(parParcelconnection);
	}

	@Override
	public void removeParcelConnection(Long parcelId) throws ServiceException {
		if(PubMethod.isEmpty(parcelId)) {
			throw new ServiceException("openapi.ParcelInfoServiceImpl.removeParcelConnection.001", "收派记录删除失败");
		}
		parParcelconnectionMapper.deleteByPrimaryKey(parcelId);
	}

	@Override
	public List<ParParcelconnection> queryConnectionListByParId(Long parcelId) throws ServiceException {
		if(PubMethod.isEmpty(parcelId)) {
			throw new ServiceException("openapi.ParcelInfoServiceImpl.queryConnectionListByParId.001", "收派记录查询失败");
		}
		List<ParParcelconnection> resultList = parParcelconnectionMapper.queryParcelConnectionByParId(parcelId);
		if(PubMethod.isEmpty(resultList)) {
			return null;
		}
		return resultList;
	}

	@Override
	public void deleteParcelConnectionByMemberId(Long memberId) {
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.ParcelInfoServiceImpl.deleteParcelConnectionByMemberId.001", "收派记录删除失败");
		}
		parParcelconnectionMapper.deleteByExpMemberId(memberId);
	}
	
	@Override
	public void removeParcelCache(Long parcelId,Long memberId){
		this.ehcacheService.remove("parcelInfoCache", String.valueOf(parcelId));
		this.ehcacheService.remove("parcelAddressCache", String.valueOf(parcelId));
		this.ehcacheService.remove("parcelIdsCacheByMemberId", String.valueOf(memberId));
		this.ehcacheService.removeAll("queryTakeByWaybillNum");
	}

	@Override
	public void editAddresseeInfo(Long customerId,String customerName,String customerMobile,String customerAddress,Long memberId,String parcelId) {
		ParParceladdress parAddr = new ParParceladdress();
//		parAddr.setSendAddress(customerAddress);
//		parAddr.setSendMobile(customerMobile);
		parAddr.setSendCustomerId(customerId);
		//parAddr.setSendContactId(customerId);
		if(!PubMethod.isEmpty(parcelId) && parcelId.length() > 0){
			String[] split = String.valueOf(parcelId).split(",");
			for(int i = 0 ; i< split.length ;i++){
				parAddr.setId(Long.parseLong(split[i]));
				parParceladdressMapper.updateParceladdressSelective(parAddr);
				removeParcelCache(Long.parseLong(split[i]),memberId);
			}
	   }
   }

	
	/**
	 * @Method: queryAlreadySignList 
	 * @Description: 以提包裹列表
	 * @param sendMemberId
	 * @param currentPage
	 * @param pageSize
	 * @return 
	 * @see net.okdi.api.service.ParcelInfoService#queryAlreadySignList(java.lang.Long, java.lang.Integer, java.lang.Integer) 
	 * @since jdk1.6
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> queryAlreadySignList(Long sendMemberId,Integer currentPage, Integer pageSize) {
		Long count = null;
		Map<String,Object> parMap = new HashMap<String,Object>();
		List<Map<String,Object>> resultList  = new ArrayList<Map<String,Object>>();
		Map<String,List<Map<String,Object>>> cacheMap  = ehcacheService.get("queryAlreadySignList",String.valueOf(sendMemberId), Map.class);
		if(null == cacheMap){
			cacheMap = new HashMap<String,List<Map<String,Object>>>(); 
		}
		if(PubMethod.isEmpty(cacheMap) || PubMethod.isEmpty(cacheMap.get(sendMemberId+"_"+currentPage+"_"+pageSize)) ){
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			parMap.put("sendMemberId", sendMemberId);
			parMap.put("page", page);
			resultList =  parParcelinfoMapper.queryAlreadySignList(parMap);     //2.联表查询提货的派件包裹信息及地址信息  --jinggq
			cacheMap.put(sendMemberId+"_"+currentPage+"_"+pageSize, resultList);
			ehcacheService.put("queryAlreadySignList",String.valueOf(sendMemberId), cacheMap);
		}
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>(); 
		List<Map<String,Object>> list = cacheMap.get(sendMemberId+"_"+currentPage+"_"+pageSize);
 		for (Map<String, Object> map : list) {   //3.拆分地址  --jinggq
			if(!PubMethod.isEmpty(map.get("addresseeAddress"))){
				StringBuilder sb = new StringBuilder();
				String[] split = map.get("addresseeAddress").toString().split(" ");
				if(split.length > 0){
					map.put("addresseeAddress",split[0]);
				}
				if(split.length > 1){
					for(int i = 1; i < split.length ; i++){
						sb.append(split[i]);
					}
					map.put("addresseeAddressmsg",sb);
				}
			}
			returnList.add(map);
		}
		return returnList;
	}
	
	@Override
	public Long queryAlreadySignCount(Long sendMemberId) {
		  return  parParcelinfoMapper.queryAlreadySignCount(sendMemberId);
	}
	@Override
	public void cancelParcelBatche(Long memberId,
			String parcelId,  Long compId, String compName){
		ehcacheService.remove("sendTaskCache", String.valueOf(memberId));
		dispatchParService.removeTaskCache();  
		ehcacheService.remove("queryAlreadySignList", String.valueOf(memberId));
		String [] parcelArray = parcelId.split(",");
		TaskVo task = new TaskVo();
		task.setMemberId(memberId);
		task.setCompId(compId);
		task.setDisposalObject((byte)0);
		task.setShowFlag((byte)0);
		task.setTaskErrorFlag(((byte)1));
		task.setDisposalDesc(compName+":提货取消"); 
		for(int i = 0 ;i < parcelArray.length; i++) {  //1.循环包裹列表  --jinggq
			Long parcel_id =   Long.parseLong(parcelArray[i]);
			Long sendTaskId = this.parParcelinfoMapper.querySendTaskIdById(parcel_id);    //2.通过parcel_id查询sendTaskId  --jinggq
			if(PubMethod.isEmpty(sendTaskId)){
				throw new ServiceException("该包裹未被提货");
			}
			task.setTaskId(sendTaskId);
			task.setCreateTime(new Date());
			task.setTaskStatus((byte)3);
			task.setId(IdWorker.getIdWorker().nextId());
			this.parTaskDisposalRecordMapper.insert(task);    //3.向记录表插入数据
			this.updateParcelStatus(parcel_id, memberId,"3");    //4.更新包裹信息,sendTaskId=NULL,parcel_status=10,tacking_status=0
 
			sendTaskMapper.updateTaskStatus(sendTaskId); //5.更新par_task_info设置TASK_IS_END=1通过sendTaskId
		}
	}

	/**
	 * 取件记录查询（包裹）
	 * @Method: takeRecordList 
	 * @param memberId 用户ID
 	 * @param date 日期
	 * @return 
	 * @see net.okdi.api.service.ParcelInfoService#takeRecordList(java.lang.Long, java.lang.String) 
	*/
	@Override
	public List<VO_ParcelList> takeRecordList(Long memberId, String date) {
		Map<String,Object> parMap = new HashMap<String,Object>();
		parMap.put("memberId",memberId);
		parMap.put("date",date);
		return this.parParcelinfoMapper.takeRecordList(parMap);
	}

	@Override
	public Map<String, Object> sendRecordList(Long memberId, Date date) {
		List<Map<String,Object>> finishList = parParcelinfoMapper.sendRecordListFinish(memberId, date);
		List<Map<String,Object>>exceptionList = parParcelinfoMapper.sendRecordListException(memberId, date);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("finishList", finishList);
		map.put("finishCount", finishList.size());
		map.put("exceptionList",exceptionList);
		map.put("exceptionCount", exceptionList.size());
		return map;
	}
}