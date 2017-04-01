package net.okdi.apiV4.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpCustomerInfo;
import net.okdi.apiV4.service.ExpCustomerInfoNewService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customerNewInfo")
public class CustomerInfoNewController extends BaseController {

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
			List<ExpCustomerInfo> list=new ArrayList<ExpCustomerInfo>();
			return jsonSuccess(list);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	
	@ResponseBody
	@RequestMapping(value = "/queryContactsJDW", method = { RequestMethod.POST })
	public String queryContactsJDW(Long compId, String quertCondition) {
		
		if (PubMethod.isEmpty(compId) ) {
			return paramsFailure("openapi.CustomerInfoController.queryContactsJDW.001", "compId能为空");
		}
		if("".equals(quertCondition))
			quertCondition = null;
		try {
			return jsonSuccess(this.expCustomerInfoNewService.queryContactsJDW(compId, quertCondition));
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
		return jsonSuccess(this.expCustomerInfoNewService.valContactsExits(memberId, customerName));
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加客户的联系人</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:51:50</dd>
	 * @param compId	公司名称
	 * @param customerId	客户id
	 * @param contactsName	联系人姓名
	 * @param contactsPhone	联系人电话
	 * @return	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addContacts", method = { RequestMethod.POST })
	public String addContacts(Long compId, Long customerId, String contactsName, String contactsPhone,Long memberId) {
		try {
			if (PubMethod.isEmpty(compId)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.001", "compId不能为空");
			}
			if (PubMethod.isEmpty(contactsName)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.002", "contactsName不能为空");
			}
			if (PubMethod.isEmpty(contactsPhone)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.003", "contactsPhone不能为空");
			}
			return jsonSuccess(this.expCustomerInfoNewService.addContacts(compId, customerId, contactsName, contactsPhone,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改联系人信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午10:00:24</dd>
	 * @param compId	网点id
	 * @param contactId	联系人id
	 * @param contactsName	联系人姓名
	 * @param contactsPhone	联系人电话
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateContacts", method = { RequestMethod.POST })
	public String updateContacts(Long compId, Long contactId, String contactsName, String contactsPhone) {
		try {
			if (PubMethod.isEmpty(compId)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.001", "compId不能为空");
			}
			if (PubMethod.isEmpty(contactId)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.002", "contactId不能为空");
			}
			if (PubMethod.isEmpty(contactsName)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.003", "contactsName不能为空");
			}
			if (PubMethod.isEmpty(contactsPhone)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.004", "contactsPhone不能为空");
			}
			return jsonSuccess(this.expCustomerInfoNewService.updateContacts(compId, contactId, contactsName, contactsPhone));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除联系人信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午10:01:22</dd>
	 * @param compId	网点id
	 * @param contactId	客户id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteContacts", method = { RequestMethod.POST })
	public String deleteContacts(Long compId, Long contactId) {
		try {
			if (PubMethod.isEmpty(compId)) {
				return paramsFailure("openapi.CustomerInfoController.deleteContacts.001", "compId不能为空");
			}
			if (PubMethod.isEmpty(contactId)) {
				return paramsFailure("openapi.CustomerInfoController.deleteContacts.002", "contactId不能为空");
			}
			return jsonSuccess(this.expCustomerInfoNewService.deleteContacts(compId, contactId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>验证同一个客户下联系人手机号是否存在</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-30 下午5:55:46</dd>
	 * @param customerId
	 * @param phone
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/valPhoneExist", method = { RequestMethod.POST })
	public String valPhoneExist(Long compId,Long customerId,String phone){
		try {
			if (PubMethod.isEmpty(compId)) {
				return paramsFailure("openapi.CustomerInfoController.valPhoneExist.001", "compId不能为空");
			}
			if (PubMethod.isEmpty(customerId)){
				return paramsFailure("openapi.CustomerInfoController.valPhoneExist.002", "customerId不能为空");
			}
			if (PubMethod.isEmpty(phone)) {
				return paramsFailure("openapi.CustomerInfoController.valPhoneExist.003", "phone不能为空");
			}
			return jsonSuccess(this.expCustomerInfoNewService.valPhoneExist(compId,customerId, phone));
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
			return jsonSuccess(this.expCustomerInfoNewService.insertCustomerV4( compId,  customerName, gender, customerType,  customerPhone,
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
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "Id不能为空");
		}
		
		try {
			return jsonSuccess(this.expCustomerInfoNewService.deleteCustomerV4( customerId,compId,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	//选择联系人
	@ResponseBody
	@RequestMapping(value = "/selectCustomer", method = { RequestMethod.POST })
	public String selectCustomer(Long compId,Long memberId) {
		
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "memberId不能为空");
		}
		
		try {
			List<ExpCustomerInfo> list=new ArrayList<ExpCustomerInfo>();
			return jsonSuccess(list);
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
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "网点Id不能为空");
		}
		try {
			return jsonSuccess(this.expCustomerInfoNewService.editCustomer(customerId, compId,  customerName,  customerType,  customerPhone,gender,
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
			return jsonSuccess(this.expCustomerInfoNewService.addCustomerType(addCustomerIds,delCustomerIds,customerType,compId,memberId));
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
			return jsonSuccess(this.expCustomerInfoNewService.addContactsInfo(compId,mobileData,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
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
