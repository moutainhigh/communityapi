package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.okdi.api.service.ExpressTaskService;
import net.okdi.core.common.handleMessage.MessageSenderRabbit;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.ExpressPriceHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 电商推送
 * @author ccs
 * @date 2014-10-18下午3:13:31
 */
@Service
public class ExpressTaskServiceImpl implements ExpressTaskService {
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	MessageSenderRabbit messageSenderRabbit;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private ExpressPriceHttpClient expressPriceHttpClient;

	/**
	 * 创建任务
	 * @Method: createTaskSimple 
	 * @param accessType	访问类型
	 * @param memberId		发件人memberId
	 * @param data			手机号/memberId/compId，由accessType决定类型
	 * @param address		收件地址
	 * @param packageNum	包裹数量
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发件人电话
	 * @param senderName	发件人姓名
	 * @param erpid			电商Id
	 * @return 
	 * @see net.okdi.api.service.ExpressTaskService#createTaskSimple(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String createTaskSimple(String accessType, String memberId, String data, String address, String packageNum,
			String packageWeight, String senderMobile, String senderName,String erpid,String allsms,String takeTime) {

		//创建任务，并且发短信的api都做在openapi中
		Map map = new HashMap();
		map.put("memberId", memberId);

		map.put("accessType", accessType);
		map.put("data", data);

		map.put("address", address);
		map.put("packageNum", packageNum);
		map.put("packageWeight", packageWeight);
		map.put("senderMobile", senderMobile);
		map.put("senderName", senderName);
		map.put("erpId", erpid);
		map.put("allsms",allsms);
		map.put("takeTime",takeTime);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("expressTask/createTaskSimple", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}


	/**
	 * @Method: cancelTask 	取消任务
	 * @param memberId		取消任务的memberId
	 * @param taskId		任务Id
	 * @return 
	 * @see net.okdi.api.service.ExpressTaskService#cancelTask(java.lang.String, java.lang.String)
	 */
	@Override
	public String cancelTask(String memberId, String taskId) {
		Map map = new HashMap();
		map.put("taskId", taskId);
		map.put("memberId", memberId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("task/cancelTaskOkdi", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	/**
	 * 字段太多，简单描述下，如果有id，则包裹根据id更新包裹，如果有taskTaskId，则插入新表中
	 * @Method: saveParcelInfo 
	 * @param id				包裹id
	 * @param takeTaskId		取件任务id
	 * @param sendTaskId		派件任务id
	 * @param senderUserId		发货方memberID
	 * @param sendCustomerId	发件客户ID
	 * @param addresseeCustomerId	地址客户id
	 * @param sendCasUserId			发件人CASID
	 * @param addresseeCasUserId	收件人CASID 
	 * @param expWaybillNum			包裹运单号
	 * @param compId				公司id
	 * @param netId					网络id
	 * @param addresseeName			收件人姓名
	 * @param addresseeMobile		收件人手机号码
	 * @param addresseeAddressId	收件人乡镇id
	 * @param addresseeAddress		收件人详细地址
	 * @param sendName				发件人姓名
	 * @param sendMobile			发件人手机
	 * @param sendAddressId			发件人乡镇id
	 * @param sendAddress			发件人详细地址	
	 * @param chareWeightForsender	包裹重量
	 * @param freight				包裹应收运费
	 * @param goodsPaymentMethod	支付方式	
	 * @param codAmount				代收货款金额
	 * @param insureAmount			保价金额
	 * @param pricePremium			保价费
	 * @param packingCharges		包装费	
	 * @param freightPaymentMethod	运费支付方式
	 * @param sendLongitude			发件人地址经度
	 * @param sendLatitude			发件人地址纬度
	 * @param addresseeLongitude	收件人地址经度
	 * @param addresseeLatitude		收件人地址纬度
	 * @param goodsDesc				产品描述
	 * @param parcelRemark			包裹备注
	 * @param serviceId				服务产品ID
	 * @param signMember			签收人
	 * @param createUserId			创建人id
	 * @param parcelEndMark			包裹结束标志
	 * @param actualTakeMember		收件人id
	 * @param actualSendMember		派件人id
	 * @param receiptId				收据id
	 * @param parcelStatus			包裹状态
	 * @param actualCodAmount		代收货款实际收到的货款金额
	 * @return 
	 */
	@Override
	public String saveParcelInfo(String taskid,String expwaybillnum,String receivername,String receivermobile,
			String receiveraddressId,String receiveraddress,String sendname,String sendmobile,String sendaddressid,String sendaddress,String parweight,String freight,String iscod,
			String codamount,String insureamount,String pricepremium,String packingcharges,String freightpaymentmethod,String goodsdesc,String parcelremark,String serviceid,
			String actualtakemember) {
		Map<String,Object> resultSetParcelMap =resultSetParcelMap(taskid, expwaybillnum, receivername, receivermobile, receiveraddressId, receiveraddress, sendname, sendmobile, sendaddressid, sendaddress, parweight, freight, iscod, codamount, insureamount, pricepremium, packingcharges, freightpaymentmethod, goodsdesc, parcelremark, serviceid, actualtakemember);
		try {
			//获取routingkey根据exchange来访问queues
			messageSenderRabbit.setRoutingKey(constPool.getRoutingKey());
			//发送数据
			messageSenderRabbit.sendDataToQueue(JSON.toJSONString(resultSetParcelMap));
			return	PubMethod.jsonSuccess(null);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
	}
	private Map<String,Object> resultSetParcelMap(String taskid, String expwaybillnum,
			String receivername, String receivermobile,
			String receiveraddressId, String receiveraddress, String sendname,
			String sendmobile, String sendaddressid, String sendaddress,
			String parweight, String freight, String iscod, String codamount,
			String insureamount, String pricepremium, String packingcharges,
			String freightpaymentmethod, String goodsdesc, String parcelremark,
			String serviceid, String actualtakemember){
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("takeTaskId", taskid);
		paraMap.put("expWaybillNum", expwaybillnum);
		paraMap.put("addresseeName", receivername);
		paraMap.put("addresseeMobile", receivermobile);
		paraMap.put("addresseeAddressId", receiveraddressId);
		paraMap.put("addresseeAddress", receiveraddress);
		paraMap.put("sendName", sendname);
		paraMap.put("sendMobile", sendmobile);
		paraMap.put("sendAddressId", sendaddressid);
		paraMap.put("sendAddress", sendaddress);
		paraMap.put("chareWeightForsender", parweight);
		paraMap.put("freight", freight);
		paraMap.put("goodsPaymentMethod", iscod);
		paraMap.put("codAmount", codamount);
		paraMap.put("insureAmount", insureamount);
		paraMap.put("pricePremium", pricepremium);
		paraMap.put("packingCharges", packingcharges);
		paraMap.put("freightPaymentMethod", freightpaymentmethod);
		paraMap.put("goodsDesc", goodsdesc);
		paraMap.put("parcelRemark", parcelremark);
		paraMap.put("serviceId", serviceid);
		paraMap.put("actualTakeMember", actualtakemember);
		return paraMap;
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加电商引流信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * @param erpJsonInfo		引流JSON格式
	 * @return
	 * @since v1.0
	 */
	@Override
	public String saveExpOftenData(String erpJsonInfo) {
		Map<String,String> parMap = new HashMap<String,String>();
		parMap.put("erpJsonInfo", erpJsonInfo);
		return this.expressPriceHttpClient.expImpData(parMap,"expGateway/saveExpOftenData");
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改电商引流信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * @param processJsonInfo		引流JSON格式
	 * @return
	 * @since v1.0
	 */
	@Override
	public String updateArticleProcessData(String processJsonInfo) {
		Map<String,String> parMap = new HashMap<String,String>();
		parMap.put("processJsonInfo", processJsonInfo);
		return this.expressPriceHttpClient.expImpData(parMap,"expGateway/updateArticleProcessData");
	}
}