package net.okdi.apiV4.controller;

import java.util.Map;

import net.okdi.apiV4.service.CustomerInfoNewTwoService;
import net.okdi.apiV4.service.ExpCustomerInfoNewService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customerNewInfoTwo")
public class CustomerInfoNewTwoController extends BaseController {

	@Autowired
	CustomerInfoNewTwoService customerInfoNewTwoService;

	@Autowired
	ExpCustomerInfoNewService expCustomerInfoNewService;
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王查询客户信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:36:48</dd>
	 * @param compId	网点id
	 * @param quertCondition	查询客户的条件，姓名或者电话
	 * @return	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCustomersJDW", method = { RequestMethod.POST, RequestMethod.GET})
	public String queryCustomersJDW(Long memberId, String quertCondition) {
		
		if (PubMethod.isEmpty(memberId) ) {
			return paramsFailure("openapi.CustomerInfoController.queryCustomersJDW.001", "memberId能为空");
		}
		if("".equals(quertCondition))
			quertCondition = null;
		try {
			return jsonSuccess(customerInfoNewTwoService.queryCustomersJDW(memberId,quertCondition));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
	//添加联系人
	@ResponseBody
	@RequestMapping(value = "/insertCustomerV4", method = { RequestMethod.POST })
	public String insertCustomerV4(Long compId, String customerName, Byte gender,Short customerType, String customerPhone,
			String townName,String compName,String iphoneTwo,String iphoneThree,
			String addressTwo,String addressThree,String expMemberIds,Long memberId) {
		if (PubMethod.isEmpty(compId)) {
			return paramsFailure("openapi.CustomerInfoController.insertCustomer.001", "网点Id不能为空");
		}
		if (PubMethod.isEmpty(customerName)) {
			return paramsFailure("openapi.CustomerInfoController.insertCustomer.002", "客户名称不能为空");
		}
		if (PubMethod.isEmpty(customerPhone)) {
			return paramsFailure("openapi.CustomerInfoController.insertCustomer.003", "customerPhone不能为空");
		}
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("openapi.CustomerInfoController.insertCustomer.004", "memberId不能为空");
		}

		try {
			return jsonSuccess(this.customerInfoNewTwoService.insertCustomerV4( compId,  customerName, gender, customerType,  customerPhone,
					 townName, compName, iphoneTwo, iphoneThree, addressTwo, addressThree, expMemberIds,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	//删除联系人
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerV4", method = { RequestMethod.POST })
	public String deleteCustomerV4(Long customerId,Long compId,Long memberId) {
		if (PubMethod.isEmpty(customerId)) {
			return paramsFailure("openapi.CustomerInfoNewTwoController.deleteCustomerV4.001", "Id不能为空");
		}
		try {
			return jsonSuccess(this.customerInfoNewTwoService.deleteCustomerV4( customerId,compId,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	//选择联系人
	@ResponseBody
	@RequestMapping(value = "/selectCustomer", method = { RequestMethod.POST })
	public String selectCustomer(Long compId,Long memberId) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("openapi.CustomerInfoNewTwoController.deleteCustomerV4.001", "memberId不能为空");
		}
		try {
			return jsonSuccess(this.customerInfoNewTwoService.selectCustomer(compId, memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	//编辑联系人
	@ResponseBody
	@RequestMapping(value = "/editCustomer", method = { RequestMethod.POST })
	public String editCustomer(Long customerId,Long compId, String customerName, Short customerType, String customerPhone,Byte gender,
			String townName,String compName,String iphoneTwo,String iphoneThree,
			String addressTwo,String addressThree,String expMemberIds,Long memberId) {
		if (PubMethod.isEmpty(compId)) {
			return paramsFailure("openapi.CustomerInfoNewTwoController.deleteCustomerV4.001", "网点Id不能为空");
		}
		try {
			return jsonSuccess(this.customerInfoNewTwoService.editCustomer(customerId, compId,  customerName,  customerType,  customerPhone,gender,
					 townName, compName, iphoneTwo, iphoneThree, addressTwo, addressThree, expMemberIds,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	//添加标签成员
	@ResponseBody
	@RequestMapping(value = "/addCustomerType", method = { RequestMethod.POST })
	public String addCustomerType(String addCustomerIds,String delCustomerIds,String customerType,Long compId,Long memberId) {
		if (PubMethod.isEmpty(customerType)) {
			return paramsFailure("openapi.CustomerInfoController.addCustomerType.002", "customerType不能为空");
		}
		try {
			return jsonSuccess(this.customerInfoNewTwoService.addCustomerType(addCustomerIds,delCustomerIds,customerType,compId,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	//添加通讯录好友
	@ResponseBody
	@RequestMapping(value = "/addContactsInfo", method = { RequestMethod.POST })
	public String addContactsInfo(Long compId,String mobileData,Long memberId) {
		if (PubMethod.isEmpty(compId)) {
			return paramsFailure("openapi.CustomerInfoController.addContacts.001", "compId不能为空");
		}
		if (PubMethod.isEmpty(mobileData)) {
			return paramsFailure("openapi.CustomerInfoController.addContacts.002", "mobileData不能为空");
		}
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("openapi.CustomerInfoController.addContacts.002", "memberId不能为空");
		}
		try {
			return jsonSuccess(this.customerInfoNewTwoService.addContactsInfo(compId,mobileData,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/valContactsExits", method = { RequestMethod.POST })
	public String valContactsExits(Long memberId, String customerName) {
		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(customerName)) {
			return paramsFailure("openapi.CustomerInfoController.valContactsExits.001", "memberId能为空");
		}
		return jsonSuccess(this.customerInfoNewTwoService.valContactsExits(memberId, customerName));
	}
	
	@ResponseBody
	@RequestMapping(value = "/saomaCreate", method = { RequestMethod.POST})
	public String saomaCreate(String tradeNum,Double tradeTotalAmount) {
		try {
			Map list = 	this.expCustomerInfoNewService.saomaCreate( tradeNum, tradeTotalAmount);
			System.out.println("-========返回数据："+list.toString());
			return jsonSuccess(list);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

}
