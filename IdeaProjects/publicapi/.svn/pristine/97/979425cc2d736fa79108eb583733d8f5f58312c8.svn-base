package net.okdi.apiV4.service.impl;

import com.alibaba.fastjson.JSON;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV3.service.impl.WalletNewServiceImpl;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class SendPackageServiceImpl implements SendPackageService {

	@Autowired
    private ConstPool constPool;
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	public static final Log logger = LogFactory.getLog(WalletNewServiceImpl.class);
	
	/**
	 * 查询待提包裹列表
	 */
	@Override
	public String queryParcelToBeTakenList(Long memberId, Integer currentPage, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("currentPage",currentPage);
		map.put("pageSize",pageSize);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/queryParcelToBeTakenList/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.queryParcelToBeTakenList.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 待提包裹信息详情
	 */
	@Override
	public String queryParcelDetail(String parId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parId",parId);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/queryParcelDetail/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.queryParcelDetail.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 待提包裹转单-批量
	 */
	@Override
	public String changeSendPerson(String newMemberId, String oldMemberId,String parIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newMemberId",newMemberId);
		map.put("oldMemberId",oldMemberId);
		map.put("parIds",parIds);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/changeSendPerson/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.changeSendPerson.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 确认提货-批量
	 */
	@Override
	public String pickUpParcel(String parIds, Long memberId, String memberPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parIds",parIds);
		map.put("memberId",memberId);
		map.put("memberPhone",memberPhone);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/pickUpParcel/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.pickUpParcel.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 查询待派任务列表
	 */
	@Override
	public String querySendTaskList(Long memberId, Integer currentPage,Integer pageSize,String mobilePhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("currentPage",currentPage);
		map.put("pageSize",pageSize);
		map.put("mobilePhone",mobilePhone);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/querySendTaskList/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendTaskList.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 编辑派件包裹信息
	 */
	@Override
	public String updateParcelInfo(String parId, String netId, String expNum,
			String mobile, String address, String codAmount, String freight,
			String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parId",parId);
		map.put("netId",netId);
		map.put("expNum",expNum);
		map.put("mobile",mobile);
		map.put("address",address);
		map.put("codAmount",codAmount);
		map.put("freight",freight);
		map.put("name",name);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/updateParcelInfo/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.updateParcelInfo.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 派件正常签收
	 */
	@Override
	public String normalSignParcel(Long taskId, String parcelId,
			Double totalCodAmount, Double totalFreight, Long memberId,
			String mobile, String name, 
			String address, String sex,String signType,String signFlag,String custlabel,String custParentLabel,String compName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId",taskId);
		map.put("parcelId",parcelId);
		map.put("totalCodAmount",totalCodAmount);
		map.put("totalFreight",totalFreight);
		map.put("memberId",memberId);
		map.put("mobile",mobile);
		map.put("name",name);
		map.put("address",address);
		map.put("sex",sex);
		map.put("signType",signType);
		map.put("signFlag",signFlag);
		
		map.put("custlabel",custlabel);
		map.put("custParentLabel",custParentLabel);
		map.put("compName",compName);
		
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/normalSignParcel/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.normalSignParcel.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 派件正常签收--批量
	 */
	@Override
	public String normalSignParcelBatch(String taskIds,String signType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskIds",taskIds);
		map.put("signType",signType);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/normalSignParcelBatch/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.normalSignParcelBatch.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 派件异常签收
	 */
	@Override
	public String exceptionSignParcel(String taskId, String parcelId,
			String memberId, String exceptionType, String textValue,String compId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId",taskId);
		map.put("parcelId",parcelId);
		map.put("memberId",memberId);
		map.put("exceptionType",exceptionType);
		map.put("textValue",textValue);
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/exceptionSignParcel/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.exceptionSignParcel.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 派件记录列表
	 */
	@Override
	public String querySendRecordList(Long memberId, String signDate,String mobilePhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("mobilePhone",mobilePhone);
		map.put("signDate",signDate);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/querySendRecordList/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecordList.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 派件记录列表 2016-12-24
	 */
	@Override
	public String querySendRecords(Long memberId,String signDate,String mobilePhone,String expWaybillNum,Short signResult,String compId,Integer currentPage,Integer pageSize) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("mobilePhone",mobilePhone);
		map.put("signDate",signDate);
		map.put("expWaybillNum",expWaybillNum);
		map.put("signResult",signResult);	
		map.put("compId",compId);	
		
		map.put("currentPage",currentPage);	
		map.put("pageSize",pageSize);	
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/querySendRecord", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecord.001","数据请求异常");
		}
		return response;
	}
	//签收详情
	@Override
	public String querySendRecordDetail(Long memberId, Long parId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("parId",parId);		
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/querySendRecordDetail", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecordDetail.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String createParcel(Long memberId, String parNum, String mobile, String receName, String wayBill, Long companyId, Integer ali) {

		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("parNum", parNum);
		map.put("mobile", mobile);
		map.put("receName", receName);
		map.put("wayBill", wayBill);
		map.put("companyId", companyId);
		map.put("ali", ali);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/create/sms", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecordDetail.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String updateParcelForSmsOrAli(String upInfo) {
		Map<String, Object> map = new HashMap<>();
		map.put("upinfo", upInfo);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/update/sms", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecordDetail.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String smsCreatePracleByWayBill(Long memberId, Long compId, String wayBill, Long companyId) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("wayBill", wayBill);
		map.put("companyId", companyId);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/create/sms/waybill", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecordDetail.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String smsCreatePracleByWayBillForAli(Long memberId, Long compId, String wayBill, Long companyId, String parNum) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("wayBill", wayBill);
		map.put("companyId", companyId);
		map.put("parNum", parNum);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/create/sms/waybill/ali", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecordDetail.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String deleteParcelByParId(Long memberId, Long parId) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("parId", parId);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/delete", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecordDetail.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String smsCreateParcelWhenSelectNoPhone(Long memberId, Long compId, String mobile, String parNum,
												   String wayBill, Long companyId, Integer ali) {

		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("mobile", mobile);
		map.put("parNum", parNum);
		map.put("wayBill", wayBill);
		map.put("companyId", companyId);
		map.put("ali", ali);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/create/sms/phone/nooneselect", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecordDetail.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String querySendForms(Long memberId, String signDate, Short signResult, String compId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("signDate",signDate);
		map.put("signResult",signResult);	
		map.put("compId",compId);	
		
		//String response = openApiHttpClient.doPassSendStrParcel("sendPackage/querySendForms", map);老接口
		String response = openApiHttpClient.doPassSendStrParcel("sendPackageReport/querySendForms", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendForms.001","数据请求异常");
		}
		return response;
	}
	
	@Override
	public String queryNearCompInfo(Double longitude, Double latitude,
			Short agentType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("longitude",longitude);
		map.put("latitude",latitude);
		map.put("agentType",agentType);
		return openApiHttpClient.doPassSendStrParcel("sendPackage/queryNearCompInfo", map);
	}
	@Override
	public String saveNearComp(String agentType, String compName, String compMobile, String compAddress, Long actorMemberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agentType",agentType);
		map.put("compAddress",compAddress);
		map.put("compName",compName);
		map.put("actorMemberId",actorMemberId);
		map.put("compMobile",compMobile);
		return openApiHttpClient.doPassSendStrParcel("sendPackage/saveNearComp", map);
	}
	@Override
	public String changeAccept(Long newMemberId, Long newCompId, Long newNetId,
			Long oldMemberId, String parIds,Short flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newMemberId",newMemberId);
		map.put("newCompId",newCompId);
		map.put("newNetId",newNetId);
		map.put("oldMemberId",oldMemberId);
		map.put("parIds",parIds);
		map.put("flag",flag);
		return openApiHttpClient.doPassSendStrParcel("sendPackage/changeAccept", map);
	}
	@Override
	public String createSendTask(String actorPhone, Long memberId,String expWaybillNum, Long compId, Long netId, String addresseeName, 
			String addresseeMobile,Long addresseeAddressId,String cityName,String addresseeAddress, BigDecimal freight,BigDecimal codAmount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("actorPhone",actorPhone);
		map.put("memberId",memberId);
		map.put("expWaybillNum",expWaybillNum);
		map.put("compId",compId);
		map.put("netId",netId);
		map.put("addresseeName",addresseeName);
		map.put("addresseeMobile",addresseeMobile);
		map.put("addresseeAddressId",addresseeAddressId);
		map.put("cityName",cityName);
		map.put("addresseeAddress",addresseeAddress);
		map.put("freight",freight);
		map.put("codAmount",codAmount);
		return openApiHttpClient.doPassSendStrParcel("sendPackage/createSendTask", map);
	}

	@Override
	public String createSendTask(String info, Long memberId, Long netId, Long compId) {
		Map<String, Object> map = new HashMap<>();
		map.put("info", info);
		map.put("memberId", memberId);
		map.put("netId", netId);
		map.put("compId", compId);
		return openApiHttpClient.doPassSendStrParcel("sendPackage/createSendTaskBatch", map);
	}

	/**
	 * 判断该手机号是否注册-0否1是
	 */
	@Override
	public String isRegisterByPhone(String memberPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberPhone",memberPhone);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/isRegisterByPhone/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.isRegisterByPhone.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 查询待派数量和待提数量
	 */
	@Override
	public String querySendCountAll(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/querySendCountAll/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendCountAll.001","数据请求异常");
		}
		return response;
	}
	/**
	 * 快递员离职
	 */
	@Override
	public String leaveOffice(Long memberId, Long compId, String memberName,String memberPhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("compId",compId);
		map.put("memberName",memberName);
		map.put("memberPhone",memberPhone);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/leaveOffice/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.leaveOffice.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String ifCompExist(String compName, String compMobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compName",compName);
		map.put("compMobile",compMobile);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/ifCompExist", map);
		return response;
	}
	@Override
	public String updateNumByParId(String jsonData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("jsonData",jsonData);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/updateNumByParId/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.updateNumByParId.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String saveParcels(String memberId, String phone, String phoneData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("phone",phone);
		map.put("phoneData",phoneData);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/saveParcels/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.saveParcels.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String saveParcelsNew(String memberId, String phone, String jsonData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("phone",phone);
		map.put("jsonData",jsonData);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/saveParcelsNew/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.saveParcelsNew.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String updatePhoneInWrongList(String msgId, String taskId,String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msgId",msgId);
		map.put("taskId",taskId);
		map.put("mobile",mobile);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/updatePhoneInWrongList/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.updatePhoneInWrongList.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String queryOpenId(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile",mobile);
		String response = openApiHttpClient.doPassSendStr("weixin/queryOpenId/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.queryOpenId.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String ifExistedCustomer(String mobilePhone, String memberId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobilePhone",mobilePhone);
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStr("sendPackage/ifExistedCustomer/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.SendPackageServiceImpl.ifExistedCustomer.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String queryOpenIdByMobiles(String mobiles) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobiles",mobiles);
		String response = openApiHttpClient.doPassSendStr("weixin/queryOpenIdByMobiles/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.queryOpenIdByMobiles.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String queryOpenIdState(String phone) {
		logger.info("根据手机号查询客户是否关注并且绑定微信....phone: "+phone);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile",phone);
		String response = openApiHttpClient.doPassSendStr("weixin/queryOpenIdState/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.queryOpenIdState.001","数据请求异常");
		}
		return response;
	}
	  
    /** 
     * @discription 更换手机号
     * @author zhaohu       
     * @created 2016-10-20 下午3:21:42      
     * @param oldPhone
     * @param newPhone
     * @param memberId
     * @return     
     * @see net.okdi.apiV4.service.SendPackageService#replacePhone(java.lang.String, java.lang.String, java.lang.String)     
     */  
	@Override
	public String replacePhone(String oldPhone, String newPhone, String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("oldPhone",oldPhone);
		map.put("newPhone",newPhone);
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStr("sendPackage/replacePhone/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.replacePhone.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String signPkg(Long memberId, String parInfo) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("parInfo", parInfo);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/sign/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.sign.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String signPkgConfirmNobound(Long opsId, Long memberId, String ops, String phone) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("opsId", opsId);
		map.put("ops", ops);
		map.put("phone", phone);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/confirm_nobound/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.sign.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String signPkgConfirmBound(Long opsId, String type) {
		Map<String, Object> map = new HashMap<>();
		map.put("opsId", opsId);
		map.put("type", type);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/confirm_bound/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.sign.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String updateWaybillNum(String json) {
		Map<String, Object> map = new HashMap<>();
		map.put("json", json);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/waybill/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.sign.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String findParcelByWaybillNo(Long memberId, String wayBill) {
		Map<String, Object> map = new HashMap<>();
		map.put("wayBill", wayBill);
		map.put("memberId", memberId);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/parexist/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.sign.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String haveSignPkgList(Long memberId, Integer currentPage) {
		Map<String, Object> map = new HashMap<>();
		map.put("currentPage", currentPage);
		map.put("memberId", memberId);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/signedlist/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.sign.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String submitPackage(Long memberId, String taskIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("taskIds", taskIds);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/submit/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.sign.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String staySign(String memberId, String expNum, String netId,String compId, String netName,String receName,
			String mobile, String actualSendMember, String contactAddress, String contactAddrLongitude, String contactAddrLatitude,
			String numType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("expNum",expNum);
		map.put("netId",netId);
		map.put("compId",compId);
		map.put("netName",netName);
		map.put("receName",receName);
		map.put("mobile",mobile);
		map.put("actualSendMember",actualSendMember);
		map.put("contactAddress",contactAddress);
		map.put("contactAddrLongitude",contactAddrLongitude);
		map.put("contactAddrLatitude",contactAddrLatitude);
		map.put("numType",numType);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/turnSign/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.staySign.001","数据请求异常");
		}
		return response;
	}
	@Override
	public Long querySendRecords(Long memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date date=new Date();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String today = sim.format(date);
		map.put("memberId",memberId);
		//map.put("mobilePhone",mobilePhone);
		map.put("signDate",today);
		logger.info("查询投递的数量22222：参数是：memberid："+memberId+"，signDate:"+today);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/querySendRecords", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.querySendRecords.001","数据请求异常");
		}
		//对数据进行解析
		//对数据进行解析
		Map<String, Object>returnmap = (Map<String, Object>) JSON.parse(response);
		logger.info("查询投递的返回的map："+returnmap);
//		if ("true".equals(returnmap.get("success"))) {
//			//return (Long) returnmap.get("data");
//		}
		long count = Long.parseLong(String.valueOf(returnmap.get("data")));
		return count;
		//return 0l;
	}
	@Override
	public String updatePhoWay(String memberId, String uid, String expNum, String mobile, String netId,
			String netName,String compId, String receName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("uid",uid);
		map.put("expNum",expNum);
		map.put("mobile",mobile);
		map.put("netId",netId);
		map.put("netName",netName);
		map.put("compId",compId);
		map.put("receName",receName);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/updatePhoWay/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.staySign.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String signRecord(String memberId, String signTime, String signResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("signTime",signTime);
		map.put("signResult",signResult);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackageReport/signRecord/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.staySign.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String createData(String memberId, String expNum, String netId,String compId,String netName,String receName,
			String mobile, String actualSendMember, String contactAddress, String contactAddrLongitude, String contactAddrLatitude) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("expNum",expNum);
		map.put("netId",netId);
		map.put("expNum",expNum);
		map.put("compId",compId);
		map.put("netName",netName);
		map.put("receName",receName);
		map.put("mobile",mobile);
		map.put("actualSendMember",actualSendMember);
		map.put("contactAddress",contactAddress);
		map.put("contactAddrLongitude",contactAddrLongitude);
		map.put("contactAddrLatitude",contactAddrLatitude);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/createData/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.createData.001","数据请求异常");
		}
		return response;
	}
	//查询待派重的待提和待派包裹数量
	@Override
	public String newQuerySendCountAll(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		logger.info("查询待派重的待提和待派包裹数量========:memberId:"+memberId);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/newQuerySendCountAll/", map);
		logger.info("查询待派重的待提和待派包裹数量返回的结果是111111========:"+response);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.newQuerySendCountAll.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String delePack(String uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid",uid);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/delePack/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.delePack.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String signPackageBatch(Long memberId, Long netId, Long compId, String parcelIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("netId", netId);
		map.put("compId", compId);
		map.put("parcelIds", parcelIds);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/batch_sign/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("001","数据请求异常");
		}
		return response;
	}

	@Override
	public String smsCreateParcelBatchByPhone(Long memberId, Long compId, String jsonParam) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("jsonParam", jsonParam);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/create/sms/batch", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("001","数据请求异常");
		}
		return response;
	}

	@Override
	public String deleteParcelBatchByParId(Long memberId, String parIds) {
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("parIds", parIds);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/delete_batch/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("001","数据请求异常");
		}
		return response;
	}
	@Override
	public String updateAddress(String memberId, String uid, String contactAddress, String contactAddrLongitude, String contactAddrLatitude) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("uid",uid);
		map.put("contactAddress",contactAddress);
		map.put("contactAddrLongitude",contactAddrLongitude);
		map.put("contactAddrLatitude",contactAddrLatitude);
		String response = openApiHttpClient.doPassSendStrParcel("sendPackage/updateAddress/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SendPackageServiceImpl.updateAddress.001","数据请求异常");
		}
		return response;
	}
}
