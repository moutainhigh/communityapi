package net.okdi.mob.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.okdi.api.entity.ReceiveMsg;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.PayHttpClient;
import net.okdi.mob.service.CustomerInfoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 客户信息(包括客户下的联系人)
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/customerInfo")
public class CustomerInfoController extends BaseController{
	private static Logger logger = Logger.getLogger(CustomerInfoController.class);

	@Autowired
	CustomerInfoService customerInfoService;

	@Autowired
	private PayHttpClient payHttpClient;
	

	  @Autowired
	  private ReceivePackageService receivePackageService;
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询客户的收派员信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午11:17:28</dd>
	 * @param customerId 客户id
	 * @param compId 网点id
	 * @return	{"data":[{"compId":2,"customerId":121452926459904,"expMemberId":111,"expMemberName":"","sort":1},{"compId":2,"customerId":121452926459904,"expMemberId":222,"expMemberName":"","sort":2}],"success":true}
	 * <br/>返回值解释：compId 网点id	, customerId 客户id, expMemberId	收派员ID,	sort 收派员排序
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryExpMembers.001","message":"customerId只能为数字","success":false}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryExpMembers.002","message":"compId只能为数字","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryExpMembers", method = { RequestMethod.POST })
	public String queryExpMembers(Long compId, Long customerId) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(customerId)) {
			throwError("compId ,customerId 不能为空");
		}
//		return this.customerInfoService.queryExpMembers(compId, customerId);
		List list=new ArrayList<>();
		return jsonSuccess(list);
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>验证当前网点下使用的客户姓名是否重复</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 下午1:54:32</dd>
	 * @param compId	网点id
	 * @param customerName	客户姓名
	 * @return	{"data":"false","success":true} data:false 为注册  ，data:true 已经注册
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.valContactsExits.001","message":"compId只能为数字","success":false}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.valContactsExits.002","message":"customerName不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/valContactsExits", method = { RequestMethod.POST })
	public String valContactsExits(Long compId, String customerName) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(customerName)) {
			throwError("compId ,customerName 不能为空");
		}
//		return this.customerInfoService.valContactsExits(compId, customerName);
		return jsonSuccess("true");
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>得到某个联系人的数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 下午2:27:13</dd>
	 * @param compId	网点id
	 * @param customerId	客户id
	 * @return	{"data":"3","success":true}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.getContactsCount.001","message":"compId只能为数字","success":false}
	 * @throws	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.getContactsCount.002","message":"customerId不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getContactsCount", method = { RequestMethod.POST })
	public String getContactsCount(Long compId, Long customerId) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(customerId)) {
			throwError("compId ,customerId 不能为空");
		}
		return this.customerInfoService.getContactsCount(compId, customerId);

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
	 * @param townName	乡镇名称
	 * @param detailedAddress	详细地址
	 * @param discountGroupId	优惠组id
	 * @param agencyFee		代收款费率
	 * @param settleType	结算方式 1、月结 2、到付 3、现结
	 * @param expMemberIds	[{"expmemberId":"111","expMemberName":"收派员1","sort":1}，{"expmemberId":"222","expMemberName":"收派员2","sort":2}]   
	 * @param contactMsgs [{"name":"张三00","phone":"13261652222"},{"name":"张三11","phone":"13261652222"},{"name":"张三22","phone":"13261652222"}]
	 * @param userId	登录人的memberId
	 * @return	{"data":"true","success":true}
	 * @throws 	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryCustomers.001","message":"compId能为空","success":false}
	 * @throws 	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryCustomers.002","message":"客户名称不能为空","success":false}
	 * @throws 	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryCustomers.003","message":"customerPhone不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/insertCustomer", method = { RequestMethod.POST })
	public String insertCustomer(Long compId, String customerName, Short customerType, String customerPhone,
			Long townId, String townName, String detailedAddress, Short discountGroupId, BigDecimal agencyFee,
			Short settleType, String expMemberIds, String contactMsgs, Long userId ,String parceId) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(customerName) || PubMethod.isEmpty(customerPhone) ) {
			throwError("compId ,customerName 不能为空");
		}
//		return this.customerInfoService.insertCustomer(compId, customerName, customerType, customerPhone, townId,
//				townName, detailedAddress, discountGroupId, agencyFee, settleType, expMemberIds, contactMsgs, userId,parceId);
		return jsonSuccess("215504155238400");
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
	 * @return	{"data":"true","success":true}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.updateCustomer.001","message":"网点Id不能为空","success":false}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.updateCustomer.002","message":"客户名称不能为空","success":false}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.updateCustomer.003","message":"customerPhone不能为空","success":false}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.updateCustomer.004","message":"customerId不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCustomer", method = { RequestMethod.POST })
	public String updateCustomer(Long customerId, Long compId, String customerName, Short customerType,
			String customerPhone, Long townId, String townName, String detailedAddress, Long discountGroupId, BigDecimal agencyFee,
			Short settleType, String expMemberIds, String contactMsgs) {

		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(customerId) || PubMethod.isEmpty(customerName)
				|| PubMethod.isEmpty(customerPhone)) {
			throwError("compId ,customerId,customerName, customerPhone 不能为空");
		}

//		return this.customerInfoService.updateCustomer(customerId, compId, customerName, customerType, customerPhone,
//				townId, townName, detailedAddress, discountGroupId, agencyFee, settleType, expMemberIds, contactMsgs);
		return jsonSuccess("215504155238400");
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除客户信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:47:30</dd>
	 * @param customerId	客户id
	 * @param compId		网点id
	 * @return	{"data":"true","success":true}
	 * @throws  {"errCode":0,"errSubcode":"openapi.CustomerInfoController.deleteCustomer.002","message":"compId不能为空","success":false}
	 * @throws 	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.deleteCustomer.001","message":"客户id不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomer", method = { RequestMethod.POST })
	public String deleteCustomer(Long customerId, Long compId) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(customerId)) {
			throwError("compId ,customerId 不能为空");
		}
		return this.customerInfoService.deleteCustomer(customerId, compId);
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询客户下的联系人的信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:47:59</dd>
	 * @param customerId	客户id
	 * @param compId 网点id
	 * @return	{"data":[{"contactsName":"张三00","contactsNameSpell":"zs00","contactsPhone":"13261652222","customerId":121452926459904,"customerName":"","id":121452928557056}],"success":true}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryContactsByCustomerId.001","message":"compId不能为空","success":false}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryContactsByCustomerId.002","message":"customerId不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryContactsByCustomerId", method = { RequestMethod.POST })
	public String queryContactsByCustomerId(Long compId, Long customerId) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(customerId)) {
			throwError("compId ,customerId 不能为空");
		}
//		return this.customerInfoService.queryContactsByCustomerId(compId, customerId);
		List list=new ArrayList<>();
		return jsonSuccess(list);
	}

	/**
	 * 查询联系人的信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询联系人的信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:31:30</dd>
	 * @param compId	网点Id
	 * @param quertCondition	查询条件：姓名或者电话
	 * @return	{"data":[{"contactsName":"三geren","contactsNameSpell":"","contactsPhone":"13261651111","customerId":121452926459904,"customerName":"测试名字","id":121484419391488},{"contactsName":"三geren","contactsNameSpell":"","contactsPhone":"13261651111","customerId":121452926459904,"customerName":"测试名字","id":121484908027904}],"success":true}
	 * <br/>返回值解释：id 联系人的id，contactsName 客户名称	, customerId 客户id, contactsNameSpell 联系人名称全拼，contactsPhone 联系人电话
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryContactsJDW.001","message":"compId能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryContactsJDW", method = { RequestMethod.POST })
	public String queryContactsJDW(Long compId, String quertCondition) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(quertCondition)) {
			throwError("compId ,quertCondition 不能为空");
		}
//		return this.customerInfoService.queryContactsJDW(compId, quertCondition);
		List list=new ArrayList<>();
		return jsonSuccess(list);
	}

	/**
	 * @api {post} /customerInfo/queryCustomersJDW 查询客户的信息	
	 * @apiPermission user
	 * @apiDescription 查询客户的信息	 chuanshi.chai
	 * @apiparam {Long} memberId 收派员的memberId
	 * @apiparam {String} quertCondition 查询条件：姓名或者电话
	 * @apiGroup 取件
	 * @apiSampleRequest /customerInfo/queryCustomersJDW
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"data":[{"agencyFee":0.12,"casMemberId":0,"compAddress":"的确不知道是不是","compId":2,"cooperativeState":0,"createTime":1419667007000,"customerName":"测试名字","customerNameSpell":"","customerPhone":"13261520000","customerType":2,"deleteFlag":0,"detailedAddress":"12500","discountGroupId":2,"erpCustomerId":0,"gender":"","id":121452926459904,"latitude":0,"longitude":0,"settleType":2,"townId":121,"townName":"","updateTime":1419667007000,"isOkdit":0}],"success":true}
     *	<br/>返回值解释：id 客户的id，agencyFee 代收款费率, casMemberId 客户对应CAS_ID,compAddress 公司名称  compId 网点id，cooperativeState	合作状态 0:已合作,1:未合作
	 * 		createTime 创建时间（long型可以转date），customerName 客户名字,"customerNameSpell":客户名称拼音,"customerPhone":客户电话,
	 * 		"customerType":客户类型 0:电商客户,1:企业客户,2:零散客户,"deleteFlag":删除标记，目前不用,"detailedAddress":详细地址,
	 * 		"discountGroupId":优惠组id,"erpCustomerId":客户ERP主键id,gender 性别 1 男 2 女  "" 未知
	 * 		"latitude":经度,"longitude":纬度,"settleType":结算方式 1、月结 2、到付 3、现结,"townId":乡镇id,"townName":乡镇名称：省-市-区县-乡镇,"updateTime":修改时间（long型可以转date）,"isOkdit" 是否是好递快递员  0否 1 是
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"errSubcode":"openapi.CustomerInfoController.queryCustomersJDW.001","message":"compId能为空","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCustomersJDW", method = { RequestMethod.POST })
	public String queryCustomersJDW(Long compId, String quertCondition) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(quertCondition)) {
			throwError("compId ,quertCondition 不能为空");
		}
//		return this.customerInfoService.queryCustomersJDW(compId, quertCondition);
		List list=new ArrayList<>();
		return jsonSuccess(list);
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加客户的联系人</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午9:51:50</dd>
	 * @param compId	公司名称
	 * @param customerId	客户id
	 * @param contactsName	联系人姓名
	 * @param contactsPhone	联系人电话
	 * @param memberId		登录人的Id
	 * @return	{"data":"true","success":true}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.addContacts.001","message":"compId不能为空","success":false}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.addContacts.002","message":"contactsName不能为空","success":false}
	 * @throws {"errCode":0,"errSubcode":"openapi.CustomerInfoController.addContacts.003","message":"contactsPhone不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addContacts", method = { RequestMethod.POST })
	public String addContacts(Long compId, Long customerId, String contactsName, String contactsPhone, Long memberId) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(contactsName) || PubMethod.isEmpty(contactsPhone)) {
			throwError("compId ,contactsName ,contactsPhone 不能为空");
		}
//		return this.customerInfoService.addContacts(compId, customerId, contactsName, contactsPhone, memberId);
		return jsonSuccess("215504155238400");
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改联系人信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午10:00:24</dd>
	 * @param compId	网点id
	 * @param contactId	联系人id
	 * @param contactsName	联系人姓名
	 * @param contactsPhone	联系人电话
	 * @return	{"data":"true","success":true}
	 * @throws	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.addContacts.001","message":"compId不能为空","success":false}
	 * @throws	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.addContacts.002","message":"contactId不能为空","success":false}
	 * @throws	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.addContacts.003","message":"contactsName不能为空","success":false}	
	 * @throws	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.addContacts.004","message":"contactsPhone不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateContacts", method = { RequestMethod.POST })
	public String updateContacts(Long compId, Long contactId, String contactsName, String contactsPhone) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(contactId) || PubMethod.isEmpty(contactsName)
				|| PubMethod.isEmpty(contactsPhone)) {
			throwError("compId ,contactId,contactsName ,contactsPhone 不能为空");
		}

//		return this.customerInfoService.updateContacts(compId, contactId, contactsName, contactsPhone);
		return jsonSuccess("215504155238400");
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除联系人信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 上午10:01:22</dd>
	 * @param compId	网点id
	 * @param contactId	客户id
	 * @return	{"data":"true","success":true}
	 * @throws 	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.deleteContacts.001","message":"compId不能为空","success":false}
	 * @throws	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.deleteContacts.002","message":"contactId不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteContacts", method = { RequestMethod.POST })
	public String deleteContacts(Long compId, Long contactId) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(contactId)) {
			throwError("compId ,contactId,contactsName ,contactsPhone 不能为空");
		}
//		return this.customerInfoService.deleteContacts(compId, contactId);
		return jsonSuccess("215504155238400");
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>验证同一个客户下联系人手机号是否存在</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-30 下午5:55:46</dd>
	 * @param compId 		网点id
	 * @param customerId	客户id
	 * @param phone			手机是否存在
	 * @return	{"data":true,"success":true}
	 * @throws  {"errCode":0,"errSubcode":"openapi.CustomerInfoController.valPhoneExist.001","message":"compId不能为空","success":false}	
	 * @throws 	{"errCode":0,"errSubcode":"openapi.CustomerInfoController.valPhoneExist.002","message":"customerId不能为空","success":false}
	 * @throws  {"errCode":0,"errSubcode":"openapi.CustomerInfoController.valPhoneExist.003","message":"phone不能为空","success":false}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/valPhoneExist", method = { RequestMethod.POST })
	public String valPhoneExist(Long compId, Long customerId, String phone) {
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(customerId)) {
			throwError("compId ,customerId ,phone 不能为空");
		}
		return this.customerInfoService.valPhoneExist(compId, customerId, phone);
	}

	//暂时注释掉手机端的验证，直接使用openapi的验证，看一下，如果以后要求，再加上
	private String throwError(String code) {
//		Map map = new HashMap();
//		map.put("success", "false");
//		map.put("cause", code);
//		return JSON.toJSONString(map);
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/insertCustomerV4", method = { RequestMethod.POST })
	public String insertCustomerV4(Long compId, String customerName, Short customerType, String customerPhone, Byte gender,
			String townName,String compName,String iphoneTwo,String iphoneThree,String addressTwo,
			String addressThree,String expMemberIds,Long memberId) {

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
			return this.customerInfoService.insertCustomerV4( compId,  customerName,  customerType,  customerPhone,gender,
					 townName, compName, iphoneTwo, iphoneThree, addressTwo, addressThree, expMemberIds,memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerV4", method = { RequestMethod.POST })
	public String deleteCustomerV4(Long customerId,Long compId,Long memberId) {
		
		if (PubMethod.isEmpty(customerId)) {
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "Id不能为空");
		}
		try {
			return this.customerInfoService.deleteCustomerV4( customerId,compId, memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/selectCustomer", method = { RequestMethod.POST })
	public String selectCustomer(Long compId,Long memberId) {
		
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "memberId不能为空");
		}
		
		try {
			return this.customerInfoService.selectCustomer(compId,memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/editCustomer", method = { RequestMethod.POST })
	public String editCustomer(Long customerId,Long compId, String customerName, Short customerType, String customerPhone,Byte gender,
			String townName,String compName,String iphoneTwo,String iphoneThree,
			String addressTwo,String addressThree,String expMemberIds,Long memberId) {
		if (PubMethod.isEmpty(compId)) {
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "网点Id不能为空");
		}
		try {
			return this.customerInfoService.editCustomer(customerId, compId,  customerName,  customerType,  customerPhone,gender,
					 townName, compName, iphoneTwo, iphoneThree, addressTwo, addressThree, expMemberIds,memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	

	@ResponseBody
	@RequestMapping(value = "/addCustomerType", method = { RequestMethod.POST,RequestMethod.GET })
	public String addCustomerType(String addCustomerIds,String delCustomerIds,Long labelId,Long memberId,String labelName) {
		if (PubMethod.isEmpty(labelId)) {
			return paramsFailure("openapi.CustomerInfoController.addCustomerType.003", "labelId不能为空");
		}
		try {
			return this.customerInfoService.addCustomerType(addCustomerIds,delCustomerIds,labelId, memberId,labelName);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	
		@ResponseBody
		@RequestMapping(value = "/addContactsInfo", method = { RequestMethod.POST})
		public String addContactsInfo(Long compId,String mobileData,Long memberId) {
			if (PubMethod.isEmpty(compId)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.001", "compId不能为空");
			}
			if (PubMethod.isEmpty(mobileData)) {
				return paramsFailure("openapi.CustomerInfoController.addContacts.002", "mobileData不能为空");
			}
			try {
				return this.customerInfoService.addContactsInfo(compId,mobileData,memberId);
			} catch (Exception e) {
				return jsonFailure(e);
			}
		}
		
		@ResponseBody
		@RequestMapping(value = "/saomaCreate", method = { RequestMethod.POST})
		public String saomaCreate(String tradeNum,Double tradeTotalAmount) {
			
			try {
				return this.customerInfoService.saomaCreate( tradeNum, tradeTotalAmount);
			} catch (Exception e) {
				return jsonFailure(e);
			}
		}
		@ResponseBody
		@RequestMapping(value = "/updateWeChatPayStatus", method = { RequestMethod.POST})
		public String updateWeChatPayStatus(String tradeNum,Double tradeTotalAmount) {
			
			return this.customerInfoService.updateWeChatPayStatus("105657241126842368", "20");
		}
		
		 @ResponseBody
		  @RequestMapping(value={"/paycallback"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
		  public void paycallback(HttpServletRequest request, HttpServletResponse response, InputStream is)
		    throws IOException
		  {
		    PrintWriter pw = response.getWriter();
		    logger.info("微信支付异步回调/customerInfo/paycallback");
		    try {
		      String return_code = "FAIL";
		      String return_msg = "ERROR";

		      ReceiveMsg receiveMsg = new ReceiveMsg();
		      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		      DocumentBuilder builder = factory.newDocumentBuilder();
		      Document doc = builder.parse(is);
		      Element root = doc.getDocumentElement();
		      NodeList msgs = root.getChildNodes();

		      for (int i = 0; i < msgs.getLength(); i++) {
		        Node msg = msgs.item(i);
		        if (msg.getNodeType() == 1) {
		          String nodeName = msg.getNodeName().trim();
		          String nodeValue = msg.getTextContent();
		          setReceiveMsg(receiveMsg, nodeName, nodeValue);
		        }
		      }
		      logger.info("微信支付异步回调返回的参数/customerInfo/paycallback:" + JSON.toJSONString(receiveMsg));

		      if ("SUCCESS".equals(receiveMsg.getReturn_code())) {
		        String payStatus = "";

		        if ("SUCCESS".equals(receiveMsg.getResult_code()))
		          payStatus = "1";
		        else {
		          payStatus = "2";
		        }
		        String tradeNo=receiveMsg.getOut_trade_no().substring(0, receiveMsg.getOut_trade_no().indexOf("D"));
		        String result = this.payHttpClient.invokePaymentCallback(receiveMsg.getOut_trade_no(), payStatus, 
		        		receiveMsg.getAttach(), receiveMsg.getSign(), receiveMsg.getMch_id(), receiveMsg.getTransaction_id(), "NATIVE",receiveMsg.getOpenid());
		        logger.info("调用财务系统返回结果:" + JSON.toJSONString(result));
		        if (JSON.parseObject(result).get("success").equals(Boolean.valueOf(true))) {
		          return_code = "SUCCESS";
		          return_msg = "OK";
		          logger.info("publicapi/customerInfo/paycallback-------updateWeChatPayStatus 20 ");

		          this.customerInfoService.updateWeChatPayStatus(tradeNo, "20");
		        } else {
		          logger.info("publicapi/customerInfo/paycallback-------updateWeChatPayStatus 21 ");
		          this.customerInfoService.updateWeChatPayStatus(tradeNo, "21");
		        }

		        logger.info(" api  payNum>>>>>>>>>>>: " + tradeNo);
		        String result1 = this.receivePackageService.getTaskIdByOrderNum(tradeNo);
		        logger.info(" api  根据orderNum查询到的result 数据 任务的 >>>>>>>>>>>: " + result1);
		        JSONObject parseObject = JSONObject.parseObject(result1);
		        JSONObject jsonObject = parseObject.getJSONObject("data");

		        String taskId = jsonObject.getString("uid");
		        String memberId = jsonObject.getString("actorMemberId");
		        String packageNum = jsonObject.getString("parEstimateCount");
		        String orderNum = jsonObject.getString("orderNum");//查询代收点取件微信支付的 id
		        logger.info(" api  orderNum=================: "+orderNum);
		        String sign = "25";
		        if(!PubMethod.isEmpty(orderNum)){//不是空代表代收点微信支付
		        	sign = "21";
		        }
		        logger.info(" api  根据微信订单号查询到的任务taskId 和 memberId和 phone ||||||||||||||||||||||| taskId" + taskId + " memberId: " + memberId+" sign:"+sign);
		        this.receivePackageService.takeSendPackage(taskId, memberId, packageNum, receiveMsg.getTotal_fee(),sign);
		      }
		      String xml = "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + 
		        return_msg + "]]></return_msg></xml>";
		      logger.info("同步告诉微信结果:" + xml);
		      pw.write(xml);
		    } catch (Exception e) {
		      e.printStackTrace();
		      String xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg>ERROR</return_msg></xml>";
		      logger.info("同步告诉微信结果:" + xml);
		      pw.write(xml);
		    } finally {
		      pw.close();
		    }
		  }
		
		 @ResponseBody
		  @RequestMapping(value={"/paycallback2"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
		  public String paycallback2( String tradeNo,String returnCode,String result,String totalFee)
		    throws IOException
		  {
		    logger.info("微信支付异步回调okdilifeapi.weixin.paycallback2");
		    try {
		      
		      if ("SUCCESS".equals(returnCode)) {

		        if (JSON.parseObject(result).get("SUCCESS").equals(Boolean.valueOf(true))) {
		          logger.info(" api  publicapi/customerInfo/paycallback2-------updateWeChatPayStatus 20 ");

		          this.customerInfoService.updateWeChatPayStatus(tradeNo, "20");
		        } else {
		          logger.info(" api  publicapi/customerInfo/paycallback2-------updateWeChatPayStatus 21 ");
		          this.customerInfoService.updateWeChatPayStatus(tradeNo, "21");
		        }

		        logger.info("payNum>>>>>>>>>>>: " + tradeNo);
		        String result1 = this.receivePackageService.getTaskIdByOrderNum(tradeNo);
		        logger.info(" api  根据orderNum查询到的result 数据 任务的 >>>>>>>>>>>: " + result1);
		        JSONObject parseObject = JSONObject.parseObject(result1);
		        JSONObject jsonObject = parseObject.getJSONObject("data");

		        String taskId = jsonObject.getString("uid");
		        String memberId = jsonObject.getString("actorMemberId");
		        String packageNum = jsonObject.getString("parEstimateCount");
		        String orderNum = jsonObject.getString("orderNum");//查询代收点取件微信支付的 id
		        String sign = "25";
		        if(!PubMethod.isEmpty(orderNum)){//不是空代表代收点微信支付
		        	sign = "21";
		        }
		        logger.info(" api  根据微信订单号查询到的任务taskId 和 memberId和 phone ||||||||||||||||||||||| taskId" + taskId + " memberId: " + memberId+" sign:"+sign);
		        this.receivePackageService.takeSendPackage(taskId, memberId, packageNum, totalFee,sign);
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
			return jsonSuccess("success"); 
		  }
		 
		 
		private void setReceiveMsg(ReceiveMsg msg, String name, String value) {
			if (name.equals("return_code"))
				msg.setReturn_code(value);
			if (name.equals("return_msg"))
				msg.setReturn_msg(value);
			if (name.equals("appid"))
				msg.setAppid(value);
			if (name.equals("mch_id"))
				msg.setMch_id(value);
			if (name.equals("nonce_str"))
				msg.setNonce_str(value);
			if (name.equals("sign"))
				msg.setSign(value);
			if (name.equals("result_code"))
				msg.setResult_code(value);
			if (name.equals("openid"))
				msg.setOpenid(value);
			if (name.equals("is_subscribe"))
				msg.setIs_subscribe(value);
			if (name.equals("trade_type"))
				msg.setTrade_type(value);
			if (name.equals("bank_type"))
				msg.setBank_type(value);
			if (name.equals("total_fee"))
				msg.setTotal_fee(value);
			if (name.equals("cash_fee"))
				msg.setCash_fee(value);
			if (name.equals("transaction_id"))
				msg.setTransaction_id(value);
			if (name.equals("out_trade_no"))
				msg.setOut_trade_no(value);
			if (name.equals("time_end"))
				msg.setTime_end(value);
			if (name.equals("attach"))
				msg.setAttach(value);

		}
		public static void main(String[] args) {
			String a="105656668094738432D8197694784".substring(0, "105656668094738432D8197694784".indexOf("D"));
			System.out.println(a);
		}
}
