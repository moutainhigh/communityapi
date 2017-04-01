package net.okdi.api.controller;

import java.math.BigDecimal;
import java.util.Map;

import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/customerInfo")
public class CustomerInfoController extends BaseController {

	@Autowired
	ExpCustomerInfoService expCustomerInfoService;

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询客户信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:36:48</dd>
	 * @param compId	网点id
	 * @param customerName	客户姓名
	 * @param customerType	客户类型 0:电商客户,1:企业客户,2:零散客户
	 * @param expMemberId	收派员id
	 * @param currentPage	当前页
	 * @param pageSize		每页的大小
	 * @return	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCustomers", method = { RequestMethod.POST })
	public String queryCustomers(Long compId, String customerName, Short customerType, Long expMemberId,
			Integer currentPage, Integer pageSize) {

		if (PubMethod.isEmpty(compId) ) {
			return paramsFailure("openapi.CustomerInfoController.queryCustomers.001", "compId能为空");
		}
		if("".equals(customerName))
			customerName = null;
		try {
			Page page = new Page();
			if(currentPage==null)
				currentPage = 1;
			page.setCurrentPage(currentPage);
			if(pageSize==null)
				pageSize=10;
			page.setPageSize(pageSize);
			return jsonSuccess(this.expCustomerInfoService.queryCustomers(compId, customerName, customerType,null,
					expMemberId, page));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
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
	public String queryCustomersJDW(Long compId, String quertCondition) {
		
		if (PubMethod.isEmpty(compId) ) {
			return paramsFailure("openapi.CustomerInfoController.queryCustomersJDW.001", "compId能为空");
		}
		if("".equals(quertCondition))
			quertCondition = null;
		try {
			return jsonSuccess(this.expCustomerInfoService.queryCustomersJDW(compId, quertCondition));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询客户的所有信息，包括联系人,收派员信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午11:17:28</dd>
	 * @param customerId
	 * @param 网点Id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCustomerById", method = { RequestMethod.POST })
	public String queryCustomerById(Long compId,Long customerId) {

		if(customerId == null){
			return paramsFailure("openapi.CustomerInfoController.queryCustomerById.001", "compId只能为数字");
		}
		try {
			return jsonSuccess(this.expCustomerInfoService.getAllByCustomerId(compId,customerId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询客户的联系人信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午11:17:28</dd>
	 * @param customerId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryExpMembers", method = { RequestMethod.POST })
	public String queryExpMembers(Long compId,Long customerId) {
		
		if(customerId == null){
			return paramsFailure("openapi.CustomerInfoController.queryExpMembers.001", "customerId只能为数字");
		}
		if(compId == null){
			return paramsFailure("openapi.CustomerInfoController.queryExpMembers.002", "compId只能为数字");
		}
		try {
			return jsonSuccess(this.expCustomerInfoService.queryExpMembers(compId,customerId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>得到站点下所有的客户列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-30 下午2:35:39</dd>
	 * @param compId
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getCustomerIdName", method = { RequestMethod.POST })
	public String getCustomerIdName(Long compId) {
		
		if(compId == null){
			return paramsFailure("openapi.CustomerInfoController.queryExpMembers.001", "customerId只能为数字");
		}
		try {
			return jsonSuccess(this.expCustomerInfoService.getCustomerIdName(compId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>验证当前网点下使用的客户姓名是否重复</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 下午1:54:32</dd>
	 * @param compId	网点id
	 * @param customerName	客户姓名
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/valContactsExits", method = { RequestMethod.POST })
	public String valContactsExits(Long compId,String customerName) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("openapi.CustomerInfoController.valContactsExits.001", "compId只能为数字");
		}
		if(PubMethod.isEmpty(customerName)){
			return paramsFailure("openapi.CustomerInfoController.valContactsExits.002", "customerName不能为空");
		}
		try {
			return jsonSuccess(this.expCustomerInfoService.valContactsExits(compId,customerName));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>得到某个客户下已有的联系人的数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 下午2:27:13</dd>
	 * @param compId	网点id
	 * @param customerId	客户id
	 * @return	
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getContactsCount", method = { RequestMethod.POST })
	public String getContactsCount(Long compId,Long customerId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("openapi.CustomerInfoController.getContactsCount.001", "compId只能为数字");
		}
		if(PubMethod.isEmpty(customerId)){
			return paramsFailure("openapi.CustomerInfoController.getContactsCount.002", "customerId不能为空");
		}
		try {
			return jsonSuccess(this.expCustomerInfoService.getContactsCount(compId,customerId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加客户</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:39:42</dd>
	 * @param compId	网点Id
	 * @param customerName	客户姓名
	 * @param customerType	客户类型 0:电商客户,1:企业客户,2:零散客户
	 * @param customerPhone	客户电话
	 * @param townId	乡镇id
	 * @param detailedAddress	详细地址
	 * @param discountGroupId	优惠组id
	 * @param agencyFee		代收款费率
	 * @param settleType	结算方式 1、月结 2、到付 3、现结
	 * @param parceId包裹Id
	 * @param expMemberIds	[{"expmemberId":"111","expMemberName":"收派员1","sort":1}，{"expmemberId":"222","expMemberName":"收派员2","sort":2}]    
	 * @param contactMsgs [{"name":"张三00","phone":"13261652222"},{"name":"张三11","phone":"13261652222"},{"name":"张三22","phone":"13261652222"}]
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/insertCustomer", method = { RequestMethod.POST })
	public String insertCustomer(Long compId, String customerName, Short customerType, String customerPhone,
			Long townId,String townName, String detailedAddress, Long discountGroupId, BigDecimal agencyFee, Short settleType,
			String expMemberIds, String contactMsgs,Long userId,String parceId) {

		if (PubMethod.isEmpty(compId)) {
			return paramsFailure("openapi.CustomerInfoController.insertCustomer.001", "网点Id不能为空");
		}
		if (PubMethod.isEmpty(customerName)) {
			return paramsFailure("openapi.CustomerInfoController.insertCustomer.002", "客户名称不能为空");
		}
		if (PubMethod.isEmpty(customerPhone)) {
			return paramsFailure("openapi.CustomerInfoController.insertCustomer.003", "customerPhone不能为空");
		}

		try {
			return jsonSuccess(this.expCustomerInfoService.insertCustomer(compId, customerName, customerType,
					customerPhone, 
					null,
					townId, townName,detailedAddress, discountGroupId, agencyFee, settleType, expMemberIds,
					contactMsgs,userId,parceId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新客户信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:44:18</dd>
	 * @param customerId	客户id
	 * @param compId		网点id
	 * @param customerName	客户名称
	 * @param customerType	客户类型 0:电商客户,1:企业客户,2:零散客户
	 * @param customerPhone	客户电话
	 * @param townId		乡镇id
	 * @param townName		乡镇名称
	 * @param detailedAddress	详细地址
	 * @param discountGroupId	优惠组id
	 * @param agencyFee		代收款费率
	 * @param settleType	结算方式 1、月结 2、到付 3、现结
	 * @param expMemberIds	[{"expmemberId":"111","expMemberName":"收派员1","sort":1}，{"expmemberId":"222","expMemberName":"收派员2","sort":2}]   
	 * @param contactMsgs [{"name":"张三00","phone":"13261652222"},{"name":"张三11","phone":"13261652222"},{"name":"张三22","phone":"13261652222"}]
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCustomer", method = { RequestMethod.POST })
	public String updateCustomer(Long customerId, Long compId, String customerName, Short customerType,
			String customerPhone, Long townId,String townName, String detailedAddress, Long discountGroupId, BigDecimal agencyFee,
			Short settleType, String expMemberIds, String contactMsgs) {

		if (PubMethod.isEmpty(compId)) {
			return paramsFailure("openapi.CustomerInfoController.updateCustomer.001", "网点Id不能为空");
		}
		if (PubMethod.isEmpty(customerName)) {
			return paramsFailure("openapi.CustomerInfoController.updateCustomer.002", "客户名称不能为空");
		}
		if (PubMethod.isEmpty(customerPhone)) {
			return paramsFailure("openapi.CustomerInfoController.updateCustomer.003", "customerPhone不能为空");
		}
		if (PubMethod.isEmpty(customerId)) {
			return paramsFailure("openapi.CustomerInfoController.updateCustomer.004", "customerId不能为空");
		}

		try {
			return jsonSuccess(this.expCustomerInfoService.updateCustomer(customerId, compId, customerName,
					customerType, customerPhone, townId,townName, detailedAddress, discountGroupId, agencyFee, settleType,
					expMemberIds, contactMsgs));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除客户信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:47:30</dd>
	 * @param customerId	客户id
	 * @param compId		网点id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomer", method = { RequestMethod.POST })
	public String deleteCustomer(Long customerId, Long compId) {
		try {
			if (PubMethod.isEmpty(customerId)) {
				return paramsFailure("openapi.CustomerInfoController.deleteCustomer.001", "客户id不能为空");
			}
			if (PubMethod.isEmpty(compId)) {
				return paramsFailure("openapi.CustomerInfoController.deleteCustomer.002", "compId不能为空");
			}
			return jsonSuccess(this.expCustomerInfoService.deleteCustomer(customerId, compId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询客户下的联系人的信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:47:59</dd>
	 * @param customerId	客户id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryContactsByCustomerId", method = { RequestMethod.POST })
	public String queryContactsByCustomerId(Long compId,Long customerId) {
		try {
			if (PubMethod.isEmpty(compId)) {
				return paramsFailure("openapi.CustomerInfoController.queryContactsByCustomerId.001", "compId不能为空");
			}
			if (PubMethod.isEmpty(customerId)) {
				return paramsFailure("openapi.CustomerInfoController.queryContactsByCustomerId.002", "customerId不能为空");
			}
			return jsonSuccess(this.expCustomerInfoService.queryContactsByCustomerId(compId,customerId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * 查询联系人的信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:31:30</dd>
	 * @param compId	网点Id
	 * @param customerName	客户姓名
	 * @param contactsName	联系人姓名
	 * @param contactsPhone	联系人电话
	 * @param currentPage	当前页
	 * @param pageSize		页面大小
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryContacts", method = { RequestMethod.POST })
	public String queryContacts(Long compId, String customerName, String contactsName, String contactsPhone,
			Integer currentPage, Integer pageSize) {
		try {
			if (PubMethod.isEmpty(compId)) {
				return paramsFailure("openapi.CustomerInfoController.queryContacts.001", "compId不能为空");
			}
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setPageSize(pageSize);
			return jsonSuccess(this.expCustomerInfoService.queryContacts(compId, customerName, contactsName,
					contactsPhone, page));
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
			return jsonSuccess(this.expCustomerInfoService.queryContactsJDW(compId, quertCondition));
		} catch (Exception e) {
			return jsonFailure(e);
		}
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
			return jsonSuccess(this.expCustomerInfoService.addContacts(compId, customerId, contactsName, contactsPhone,memberId));
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
			return jsonSuccess(this.expCustomerInfoService.updateContacts(compId, contactId, contactsName, contactsPhone));
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
			return jsonSuccess(this.expCustomerInfoService.deleteContacts(compId, contactId));
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
			return jsonSuccess(this.expCustomerInfoService.valPhoneExist(compId,customerId, phone));
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
			return jsonSuccess(this.expCustomerInfoService.insertCustomerV4( compId,  customerName, gender, customerType,  customerPhone,
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
			return jsonSuccess(this.expCustomerInfoService.deleteCustomerV4( customerId,compId,memberId));
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
			return jsonSuccess(this.expCustomerInfoService.selectCustomer(compId, memberId));
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
			return jsonSuccess(this.expCustomerInfoService.editCustomer(customerId, compId,  customerName,  customerType,  customerPhone,gender,
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
			return jsonSuccess(this.expCustomerInfoService.addCustomerType(addCustomerIds,delCustomerIds,customerType,compId,memberId));
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
			return jsonSuccess(this.expCustomerInfoService.addContactsInfo(compId,mobileData,memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/saomaCreate", method = { RequestMethod.POST})
	public String saomaCreate(String tradeNum,Double tradeTotalAmount) {
		try {
			Map list = 	this.expCustomerInfoService.saomaCreate( tradeNum, tradeTotalAmount);
			System.out.println("-========返回数据："+list.toString());
			return jsonSuccess(list);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	

}
