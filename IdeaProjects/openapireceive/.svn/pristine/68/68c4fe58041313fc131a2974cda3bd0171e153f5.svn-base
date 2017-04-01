package net.okdi.api.controller;

import java.math.BigDecimal;

import net.okdi.api.service.DispatchParService;
import net.okdi.api.vo.VO_DispatchSingleParInfo;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dispatchPar")
public class DispatchParController extends BaseController {

	@Autowired
	DispatchParService dispatchParService;

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-14 上午11:27:04</dd>
	 * @param compId			站点id
	 * @param actualSendMember  实际派件人ID memberId
	 * @param signMember		签收人姓名
	 * @param addresseeMobile	地址的电话
	 * @param parcelStatus		包裹状态
	 * @param startDate			开始时间：20110101 
	 * @param endDate			开始时间：20110101 
	 * @param expWaybillNum		包裹运单号	
	 * @param currentPage		当前页
	 * @param pageSize			页面大小
	 * @param tackingStatus		查询标记，0--待提，1--在派，2--已签收，3--异常：客户拒收
	 * @return		
	 * @since v1.0
	 */
	@RequestMapping(value = "/findPars", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String findPars(Long compId,Long actualSendMember, String addresseeName, String addresseeMobile,
			String startDate, String endDate, String expWaybillNum, Integer currentPage, Integer pageSize, Short tackingStatus
			) {
		try {
			if(PubMethod.isEmpty(compId)){
				return paramsFailure(this.getClass().getName()+"001", "compId不能为空！");
			}
			if(PubMethod.isEmpty(currentPage)){
				return paramsFailure(this.getClass().getName()+"002", "currentPage不能为空！");
			}
			if(PubMethod.isEmpty(pageSize)){
				return paramsFailure(this.getClass().getName()+"003", "pageSize不能为空！");
			}
			if(PubMethod.isEmpty(tackingStatus)){
				return paramsFailure(this.getClass().getName()+"004", "tackingStatus不能为空！");
			}
			
			Page p = this.dispatchParService.findPars(compId, actualSendMember, addresseeName, addresseeMobile,
					startDate, endDate, expWaybillNum, currentPage, pageSize, tackingStatus);
			return jsonSuccess(p);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-14 上午11:29:52</dd>
	 * @param id		包裹的Id
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/findParById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String findParById(Long id) {
		try {
			if(PubMethod.isEmpty(id)){
				return paramsFailure(this.getClass().getName()+"001", "id不能为空！");
			}
			VO_DispatchSingleParInfo vo = dispatchParService.findParById(id);
			return jsonSuccess(vo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-14 上午11:30:35</dd>
	 * @param createUserId			当前创建人的memberId
	 * @param actualSendMember		实际派件人IDmemberId
	 * @param compId				站点的id
	 * @param netId					网络Id
	 * @param expWaybillNum			包裹运单号
	 * @param codAmount				代收货款金额
	 * @param addresseeAddressId	收件人地区代码
	 * @param addresseeAddress		详细地址
	 * @param addresseeMobile		收件人电话
	 * @param addresseeName			收件人
	 * @param sendAddressId			发件人地区代码	
	 * @param sendAddress			发件详细地址
	 * @param sendMobile			发件电话
	 * @param sendName				发件人
	 * @param freightPaymentMethod	结算方式--'应收运费支付方式 0：发件方现结,1：发件方月节,2：收件方到付',
	 * @param freight				运费
	 * @param packingCharges		包装费--包装费
	 * @param chareWeightForsender	包裹重
	 * @param insureAmount			保价金额		
	 * @param pricePremium			保价费
	 * @param parcelRemark			备注
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/addPar", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String addPar(Long createUserId,String actualSendMember,Long compId, Long netId,String expWaybillNum, BigDecimal codAmount,
			Long addresseeAddressId, String addresseeAddress, String addresseeMobile,
			String addresseeName, Long sendAddressId, String sendAddress, String sendMobile,
			String sendName, Short freightPaymentMethod, BigDecimal freight, BigDecimal packingCharges,
			BigDecimal chareWeightForsender, BigDecimal insureAmount, BigDecimal pricePremium, String parcelRemark) {
		try {
			if(PubMethod.isEmpty(createUserId)){
				return paramsFailure(this.getClass().getName()+"001", "createUserId不能为空！");
			}
			if(PubMethod.isEmpty(compId)){
				return paramsFailure(this.getClass().getName()+"002", "compId不能为空！");
			}
			if(PubMethod.isEmpty(netId)){
				return paramsFailure(this.getClass().getName()+"003", "netId不能为空！");
			}
			if(PubMethod.isEmpty(expWaybillNum)){
				return paramsFailure(this.getClass().getName()+"004", "expWaybillNum不能为空！");
			}
			if(PubMethod.isEmpty(codAmount)){
				return paramsFailure(this.getClass().getName()+"005", "codAmount不能为空！");
			}
			dispatchParService.addPar(createUserId,actualSendMember,compId,netId, expWaybillNum,
					codAmount, addresseeAddressId, addresseeAddress, addresseeMobile,
					addresseeName, sendAddressId, sendAddress, sendMobile, sendName,
					freightPaymentMethod, freight, packingCharges, chareWeightForsender, insureAmount, pricePremium,
					parcelRemark);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-14 上午11:30:35</dd>
	 * @param id 					包裹的Id
	 * @param actualSendMember		实际派件人IDmemberId
	 * @param expWaybillNum			包裹运单号
	 * @param codAmount				代收货款金额
	 * @param addresseeAddressId	收件人地区代码
	 * @param addresseeAddress		详细地址
	 * @param addresseeMobile		收件人电话
	 * @param addresseeName			收件人
	 * @param sendAddressId			发件人地区代码	
	 * @param sendAddress			发件详细地址
	 * @param sendMobile			发件电话
	 * @param sendName				发件人
	 * @param freightPaymentMethod	结算方式--'应收运费支付方式 0：发件方现结,1：发件方月节,2：收件方到付',
	 * @param freight				运费
	 * @param packingCharges		包装费--包装费
	 * @param chareWeightForsender	包裹重
	 * @param insureAmount			保价金额		
	 * @param pricePremium			保价费
	 * @param parcelRemark			备注
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/updatePar", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String updatePar(Long id, Long netId,String actualSendMember,String expWaybillNum, BigDecimal codAmount,
			Long addresseeAddressId, String addresseeAddressInfo, String addresseeAddress, String addresseeMobile,
			String addresseeName, Long sendAddressId, String sendAddressInfo, String sendAddress, String sendMobile,
			String sendName, Short freightPaymentMethod, BigDecimal freight, BigDecimal packingCharges,
			BigDecimal chareWeightForsender, BigDecimal insureAmount, BigDecimal pricePremium, String parcelRemark) {
		try {
			if(PubMethod.isEmpty(id)){
				return paramsFailure(this.getClass().getName()+"001", "id不能为空！");
			}
			dispatchParService.updatePar(id, netId,actualSendMember, expWaybillNum,
					codAmount, addresseeAddressId, addresseeAddress, addresseeMobile,
					addresseeName, sendAddressId, sendAddress, sendMobile, sendName,
					freightPaymentMethod, freight, packingCharges, chareWeightForsender, insureAmount, pricePremium,
					parcelRemark);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
