package net.okdi.api.controller;

import java.math.BigDecimal;

import net.okdi.api.service.ExpressTaskService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/expressTask")
public class ExpressTaskOpenController extends BaseController {

	@Autowired
	ExpressTaskService expressTaskService;

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午10:27:45</dd>
	 * @param accessType	"0,1,2"分别代表0 手机,1compId ,2 memberId
	 * @param data			收派员电话/compId/memberId
	 * @param expMemberId	发起人memberId
	 * @param address		地址详细信息
	 * @param packageNum	包裹数量
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发送人手机号
	 * @param senderName	发送人姓名
	 * @param erpId			电商id
	 * @param allsms		0 全部发短信 1 未注册收派员发短信，注册收派员发推送（存在推送不成功的情况）
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/createTaskSimple", method = { RequestMethod.POST, RequestMethod.GET })
	public String createTaskSimple(String accessType, String data, String memberId, String address, String packageNum,
			String packageWeight, String senderMobile, String senderName, String erpId, String allsms,String takeTime) {
		try {
			return jsonSuccess(expressTaskService.createTaskSimple(accessType, data, memberId, address, packageNum,
					packageWeight, senderMobile, senderName, erpId,allsms,takeTime));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>插入包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午2:09:21</dd>
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
	@ResponseBody
	@RequestMapping(value = "/saveParcelInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveParcelInfo(Long id, Long takeTaskId, Long sendTaskId, Long senderUserId, Long sendCustomerId,
			Long addresseeCustomerId, Long sendCasUserId, Long addresseeCasUserId, String expWaybillNum, Long compId,
			Long netId, String addresseeName, String addresseeMobile, Long addresseeAddressId, String addresseeAddress,
			String sendName, String sendMobile, Long sendAddressId, String sendAddress,
			BigDecimal chareWeightForsender, BigDecimal freight, Short goodsPaymentMethod, BigDecimal codAmount,
			BigDecimal insureAmount, BigDecimal pricePremium, BigDecimal packingCharges, Short freightPaymentMethod,
			BigDecimal sendLongitude, BigDecimal sendLatitude, BigDecimal addresseeLongitude,
			BigDecimal addresseeLatitude, String goodsDesc, String parcelRemark, Long serviceId, String signMember,
			Long createUserId, String parcelEndMark, Long actualTakeMember, Long actualSendMember, Long receiptId,
			Short parcelStatus, BigDecimal actualCodAmount) {
		try {
			String str = jsonSuccess(expressTaskService.saveParcelInfo(id, takeTaskId, sendTaskId, senderUserId,
					sendCustomerId, addresseeCustomerId, sendCasUserId, addresseeCasUserId, expWaybillNum, compId,
					netId, addresseeName, addresseeMobile, addresseeAddressId, addresseeAddress, sendName, sendMobile,
					sendAddressId, sendAddress, chareWeightForsender, freight, goodsPaymentMethod, codAmount,
					insureAmount, pricePremium, packingCharges, freightPaymentMethod, sendLongitude, sendLatitude,
					addresseeLongitude, addresseeLatitude, goodsDesc, parcelRemark, serviceId, signMember,
					createUserId, parcelEndMark, actualTakeMember, actualSendMember, receiptId, parcelStatus,
					actualCodAmount));
			return str;
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
