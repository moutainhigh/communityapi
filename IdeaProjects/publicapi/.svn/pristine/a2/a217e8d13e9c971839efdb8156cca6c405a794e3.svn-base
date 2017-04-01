package net.okdi.apiV4.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.okdi.api.entity.ReceiveMsg;
import net.okdi.apiV4.service.CustomerInfoNewService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.PayHttpClient;

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
@RequestMapping("/customerInfoNew")
public class CustomerInfoNewController extends BaseController{
	private static Logger logger = Logger.getLogger(CustomerInfoNewController.class);

	@Autowired
	CustomerInfoNewService customerInfoNewService;

	@Autowired
	private PayHttpClient payHttpClient;

	@Autowired
	private ReceivePackageService receivePackageService;
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
		return this.customerInfoNewService.queryContactsJDW(compId, quertCondition);
	}
	private String throwError(String code) {
//		Map map = new HashMap();
//		map.put("success", "false");
//		map.put("cause", code);
//		return JSON.toJSONString(map);
		return null;
	}
	/**
	 * @api {post} /customerInfoNew/queryCustomersJDW 查询客户的信息	
	 * @apiPermission user
	 * @apiDescription 查询客户的信息	 
	 * @apiparam {Long} memberId 收派员的memberId
	 * @apiparam {String} quertCondition 查询条件：姓名或者电话
	 * @apiGroup 客户管理
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
	public String queryCustomersJDW(Long memberId, String quertCondition) {
		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(quertCondition)) {
			throwError("compId ,quertCondition 不能为空");
		}
		return this.customerInfoNewService.queryCustomersJDW(memberId, quertCondition);	
		
	}
	/**
	 * @api {post} /customerInfoNew/valContactsExits 名称与存在客户是否相同
	 * @apiVersion 0.3.0
	 * @apiDescription 名称与存在客户是否相同
	 * @apiGroup 客户管理
	 * @apiParam {Long} memberId memberId
	 * @apiParam {String} customerName 客户姓名
	 * @apiSampleRequest /customerInfoNew/valContactsExits
	 * @apiSuccess {String} data 客户id
	 * @apiSuccessExample Success-Response:
	  {"data":"true","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":""
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/valContactsExits", method = { RequestMethod.POST })
	public String valContactsExits(Long memberId, String customerName) {
		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(customerName)) {
			return paramsFailure("openapi.CustomerInfoController.valContactsExits.001", "memberId能为空");
		}
		return this.customerInfoNewService.valContactsExits(memberId, customerName);
	}

	
//	/**
//	 * @apiParam {Long} compId 网点id
//	 * @apiParam {String} customerName 客户姓名
//	 * @apiParam {Byte} gender 客户性别 (1:男 2:女 "":未知)
//	 * @apiParam {Short} customerType 标签
//	 * @apiParam {String} customerPhone 客户电话
//	 * @apiParam {String} townName 地址
//	 * @apiParam {String} compName 公司名称
//	 * @apiParam {String} iphoneTwo 第二个手机号
//	 * @apiParam {String} iphoneThree 第三个手机号
//	 * @apiParam {String} addressTwo 第二个地址
//	 * @apiParam {String} addressThree 第三个地址
//	 * @apiParam {String} expMemberIds 暂定
//	 * @apiParam {Long} memberId memberId
//	 * @apiParam {Long } parentCustomerType 父标签id(没有父标签传-1)
//	 * @apiSampleRequest /callPhone/callPhoneByPhone
//	 * @apiSuccess {String} data 客户id
//	 * @apiSuccessExample Success-Response:
//	  {"data":"208621067493376","success":true}
//	 * @apiErrorExample Error-Response:
//	 *     HTTP/1.1 404 Not Found
//	 *     {
//	 *	     "success":	false,
//	 *	     "errCode":	"err.001",
//	 *	     "message":""
//	 *     }
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/insertCustomerV4", method = { RequestMethod.POST })
//	public String insertCustomerV3(Long compId, String customerName, Short customerType, String customerPhone, Byte gender,
//			String townName,String compName,String iphoneTwo,String iphoneThree,String addressTwo,
//			String addressThree,String expMemberIds,Long memberId,Short parentCustomerType) {
//
//		if (PubMethod.isEmpty(compId)) {
//			return paramsFailure("openapi.CustomerInfoController.insertCustomer.001", "网点Id不能为空");
//		}
//		if (PubMethod.isEmpty(customerName)) {
//			return paramsFailure("openapi.CustomerInfoController.insertCustomer.002", "客户名称不能为空");
//		}
//		if (PubMethod.isEmpty(customerPhone)) {
//			return paramsFailure("openapi.CustomerInfoController.insertCustomer.003", "customerPhone不能为空");
//		}
//		if (PubMethod.isEmpty(memberId)) {
//			return paramsFailure("openapi.CustomerInfoController.insertCustomer.004", "memberId不能为空");
//		}
//
//		try {
//			return this.customerInfoNewService.insertCustomerV4( compId,  customerName,  customerType,  customerPhone,gender,
//					 townName, compName, iphoneTwo, iphoneThree, addressTwo, addressThree, expMemberIds,memberId,parentCustomerType);
//		} catch (Exception e) {
//			return jsonFailure(e);
//		}
//	}
	
	/**
	 * @api {post} /customerInfoNew/insertCustomerV4 添加联系人
	 * @apiVersion 0.3.0
	 * @apiDescription 添加联系人
	 * @apiGroup 客户管理
	 * @apiParam {String} compId 网点id
	 * @apiParam {String} customerName 客户姓名
	 * @apiParam {String} gender 客户性别 (1:男 2:女 "":未知)
	 * @apiParam {String} labelIds   标签ID(字符串)
	 * @apiParam {String} labelNames 标签名称(我去,订单)  
	 * @apiParam {String} customerPhone 客户电话
	 * @apiParam {String} townName 地址
	 * @apiParam {String} compName 公司名称
	 * @apiParam {String} iphoneTwo 第二个手机号
	 * @apiParam {String} iphoneThree 第三个手机号
	 * @apiParam {String} addressTwo 第二个地址
	 * @apiParam {String} addressThree 第三个地址
	 * @apiParam {String} memberId  用户Id
	 * @apiParam {String} expMemberIds 暂定
	 * @apiParam {String } parentCustomerType 父标签id(没有父标签传-1)
	 * @apiSampleRequest /customerInfoNew/insertCustomerV4
	 * @apiSuccess {String} data 客户id
	 * @apiSuccessExample Success-Response:
	  {"data":"208621067493376","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":""
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/insertCustomerV4", method = { RequestMethod.POST ,RequestMethod.GET})
	public String insertCustomerV4(String compId, String customerName, String gender, String labelIds, String labelNames, String customerPhone,
			String townName, String compName, String iphoneTwo, String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, String memberId, String parentCustomerType) {
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
			return this.customerInfoNewService.insertCustomerV4(compId, customerName, gender, labelIds, labelNames, customerPhone,
					townName, compName, iphoneTwo, iphoneThree, addressTwo, addressThree, expMemberIds, memberId, parentCustomerType);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /customerInfoNew/deleteCustomerV4  删除联系人
	 * @apiVersion 0.3.0
	 * @apiDescription 删除联系人
	 * @apiGroup 客户管理
	 * @apiParam {Long} customerId 客户id
	 * @apiParam {Long} compId 网点id
	 * @apiParam {Long} memberId memberId
	 * @apiSampleRequest /customerInfo/deleteCustomerV4
	 * @apiSuccess {String} data 客户id
	 * @apiSuccessExample Success-Response:
	  {"data":"208621067493376","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCustomerV4", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteCustomerV4(Long customerId,Long compId,Long memberId) {
		
		if (PubMethod.isEmpty(customerId)) {
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "Id不能为空");
		}
		try {
			return this.customerInfoNewService.deleteCustomerV4( customerId, compId, memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @api {post} /customerInfoNew/selectCustomer  选择联系人
	 * @apiVersion 0.3.0
	 * @apiDescription 选择联系人
	 * @apiGroup 客户管理
	 * @apiParam {Long} compId 网点id
	 * @apiParam {Long} memberId memberId
	 * @apiSampleRequest /customerInfo/selectCustomer
	 * @apiSuccess {String} data 客户id
	 * @apiSuccessExample Success-Response:
	 {
    "data": [
        {
            "addressThress": "",
            "addressTwo": "",
            "agencyFee": 0,
            "casMemberId": "",
            "compAddress": "",
            "compId": 117513368428544,
            "cooperativeState": "",
            "createTime": 1420626602000,
            "customerName": "王苏格",
            "customerNameSpell": "wsg",
            "customerPhone": "18910424092",
            "customerType": "",
            "deleteFlag": "",
            "detailedAddress": "佟家坟村",
            "discountGroupId": "",
            "erpCustomerId": "",
            "id": 123464869797888,
            "iphoneThree": "",
            "iphoneTwo": "",
            "latitude": "",
            "longitude": "",
            "settleType": 1,
            "townId": 11238386,
            "townName": "北京-海淀区-四季青镇",
            "updateTime": 1461144663000
        }
    ],
    "success": true
}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/selectCustomer", method = { RequestMethod.POST })
	public String selectCustomer(Long compId,Long memberId) {
		
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "memberId不能为空");
		}
		
		try {
			return this.customerInfoNewService.selectCustomer(compId,memberId);
			
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @api {post} /customerInfoNew/editCustomer 编辑联系人
	 * @apiVersion 0.3.0
	 * @apiDescription 编辑联系人
	 * @apiGroup 客户管理
	 * @apiParam {Long} customerId 客户id
	 * @apiParam {Long} compId 网点id
	 * @apiParam {String} customerName 客户姓名
	 * @apiParam {Byte} gender 客户性别 (1:男 2:女 "":未知)
	 * @apiParam {String} labelIds   标签ID(字符串)
	 * @apiParam {String} labelNames 标签名称(字符串)
	 * @apiParam {String} customerPhone 客户电话
	 * @apiParam {String} townName 地址
	 * @apiParam {String} compName 公司名称
	 * @apiParam {String} iphoneTwo 第二个手机号
	 * @apiParam {String} iphoneThree 第三个手机号
	 * @apiParam {String} addressTwo 第二个地址
	 * @apiParam {String} addressThree 第三个地址
	 * @apiParam {String} expMemberIds 暂定
	 * @apiParam {Long } memberId memberId
	 * @apiParam {Long } parentCustomerType 父标签id(没有父标签传-1)
	 * @apiSampleRequest /customerInfo/editCustomer
	 * @apiSuccess {String} data 客户id
	 * @apiSuccessExample Success-Response:
	  {"data":"208621067493376","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/editCustomer", method = { RequestMethod.POST })
	public String editCustomer(Long customerId, Long compId,
			String customerName, String labelIds,String labelNames, String customerPhone,
			Byte gender, String townName, String compName, String iphoneTwo,
			String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, Long memberId,Short parentCustomerType) {
		if (PubMethod.isEmpty(compId)) {
			return paramsFailure("openapi.CustomerInfoController.deleteCustomerV4.001", "网点Id不能为空");
		}
		try {
			return this.customerInfoNewService.editCustomer(customerId, compId,  customerName,  labelIds, labelNames, customerPhone,gender,
					 townName, compName, iphoneTwo, iphoneThree, addressTwo, addressThree, expMemberIds,memberId,parentCustomerType);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /customerInfoNew/addCustomerType 添加标签成员
	 * @apiVersion 0.3.0
	 * @apiDescription 添加标签成员
	 * @apiGroup 客户管理
	 * @apiParam {Long} addCustomerIds 添加标签的客户id(格式2222,33333)
	 * @apiParam {Long} delCustomerIds 删除标签的客户id(格式2222,33333)
	 * @apiParam {Long} customerType 标签类型
	 * @apiParam {Long} compId 网点id
	 * @apiParam {Long} memberId memberId
	 * @apiSampleRequest /customerInfo/addCustomerType
	 * @apiSuccess {String} data 客户id
	 * @apiSuccessExample Success-Response:
	 {"data":"1","success":true}

	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/addCustomerType", method = { RequestMethod.POST })
	public String addCustomerType(String addCustomerIds,String delCustomerIds,String customerType,Long compId,Long memberId) {
		if (PubMethod.isEmpty(customerType)) {
			return paramsFailure("openapi.CustomerInfoController.addCustomerType.002", "customerType不能为空");
		}
		try {
			return this.customerInfoNewService.addCustomerType(addCustomerIds,delCustomerIds,customerType,compId,memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	 /**
		 * @api {post} /customerInfoNew/addContactsInfo 添加通讯录成员
		 * @apiVersion 0.3.0
		 * @apiDescription 添加通讯录成员
		 * @apiGroup 客户管理
	 * @apiParam {String} mobileData    手机通讯录--json字符串格式:{"data":[{"mobile":"123333338","name":"张三"}, {"mobile":"123333339","name":"李四"}, {"mobile":"123333339","name":""}]	
		 * @apiParam {Long} compId 网点id
		 * @apiParam {Long} memberId memberId
		 * @apiSampleRequest /customerInfo/addContactsInfo
		 * @apiSuccess {String} data 客户id
		 * @apiSuccessExample Success-Response:
		 {"data":"1","success":true}

		 * @apiErrorExample Error-Response:
		 *     HTTP/1.1 404 Not Found
		 *     {
		 *	     "success":	false,
		 *	     "errCode":	"err.001",
		 *	     "message":"XXX"
		 *     }
		 */
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
				return this.customerInfoNewService.addContactsInfo(compId,mobileData,memberId);
			} catch (Exception e) {
				return jsonFailure(e);
			}
		}
		/**
		 * @api {post} /customerInfoNew/saomaCreate 取件扫描支付
		 * @apiVersion 0.3.0
		 * @apiDescription 取件扫描支付
		 * @apiGroup 客户管理
		 * @apiParam {String} tradeNum 取件任务id（订单编号、或者外部交易唯一ID）
		 * @apiParam {Double} tradeTotalAmount 交易总金额
		 * @apiSampleRequest /customerInfo/saomaCreate
		 * @apiSuccess {String} random_trade_num 交易号
		 * @apiSuccess {String} prepayId 预约号id
		 * @apiSuccess {String} code_url 二维码url
		 * @apiSuccess {String} partnerid 商户号
		 * @apiSuccess {String} appid 微信分配的公众账号ID
		 * @apiSuccess {String} timestamp 时间戳
		 * @apiSuccess {String} package prepay_id=43432
		 * @apiSuccess {String} noncestr 随机字符串，不长于32位
		 * @apiSuccess {String} channelsCode 微信渠道
		 * @apiSuccess {String} headUrl 头像url
		 * @apiSuccess {String} compName  公司名称
		 * @apiSuccessExample Success-Response:
				 {
		    "data": {
		        "appid": "wxd11b57b3d88a4c21",
		        "channelsCode": "weixinpay",
		        "code_url": "weixin://wxpay/bizpayurl?pr=yWHe7zU",
		        "headUrl": "http://cas.okdit.net/nfs_data/mob/head/208244482293760.jpg",
		        "noncestr": "4v207pd0u8sgscug40gb6va15b3yhffc",
		        "package": "Sign=WXPay",
		        "partnerid": "1248131301",
		        "prepayId": "wx2016050515311385a01fe7b50011124036",
		        "sign": "26A1F2E87FB4D0EC81BAE9B332297F87",
		        "tid": "105570349213560832",
		        "timestamp": "1462433472"
		         "compName":"cesi"
		    },
		    "success": true
		}

		 * @apiErrorExample Error-Response:
		 *     HTTP/1.1 404 Not Found
		 *    {"success":false,"error_code":"err.trade.509","error_msg":"支付订单已创建","page":"0","hasnext":false,"rows":"0","total":"0","data":""}
		 */
		@ResponseBody
		@RequestMapping(value = "/saomaCreate", method = { RequestMethod.POST})
		public String saomaCreate(String tradeNum,Double tradeTotalAmount) {
			
			try {
				return this.customerInfoNewService.saomaCreate( tradeNum, tradeTotalAmount);
			} catch (Exception e) {
				return jsonFailure(e);
			}
		}
		@ResponseBody
		@RequestMapping(value = "/updateWeChatPayStatus", method = { RequestMethod.POST})
		public String updateWeChatPayStatus(String tradeNum,Double tradeTotalAmount) {
			
			return this.customerInfoNewService.updateWeChatPayStatus("105657241126842368", "20");
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
		      String tradeNo=receiveMsg.getOut_trade_no().substring(0, receiveMsg.getOut_trade_no().indexOf("D"));

		    
		      if ("SUCCESS".equals(receiveMsg.getReturn_code())) {
		        String payStatus = "";

		        if ("SUCCESS".equals(receiveMsg.getResult_code()))
		          payStatus = "1";
		        else {
		          payStatus = "2";
		          
		        }
		        //根据payNum 查询payStatus
		        
		        String resultPayStatus=  this.customerInfoNewService.findPayStatusByPayNum(tradeNo);
		        if(!resultPayStatus.equals("0")){
			        logger.info("微信已经回调过....return_code:"+receiveMsg.getResult_code()+"payNum:"+tradeNo+"sign:"+
			        receiveMsg.getAttach()+"mch_id"+receiveMsg.getMch_id()+"transaction_id:"+receiveMsg.getTransaction_id()+
			        "openid:"+receiveMsg.getOpenid());

		        }else{
			        String result = this.payHttpClient.invokePaymentCallback(receiveMsg.getOut_trade_no(), payStatus, 
			        		receiveMsg.getAttach(), receiveMsg.getSign(), receiveMsg.getMch_id(), receiveMsg.getTransaction_id(), "NATIVE",receiveMsg.getOpenid());
			        logger.info("调用财务系统返回结果:" + JSON.toJSONString(result));
			        if (JSON.parseObject(result).get("success").equals(Boolean.valueOf(true))) {
			          return_code = "SUCCESS";
			          return_msg = "OK";
			          
			          if("SUCCESS".equals(receiveMsg.getResult_code())){
			        	  logger.info("publicapi/customerInfo/paycallback-------updateWeChatPayStatus 20 ");
				          this.customerInfoNewService.updateWeChatPayStatus(tradeNo, "20");
				          
			          }else{
			        	  logger.info("publicapi/customerInfo/paycallback-------updateWeChatPayStatus 22 ");
				          this.customerInfoNewService.updateWeChatPayStatus(tradeNo, "22");
			          }
			        } else {
			          if("SUCCESS".equals(receiveMsg.getResult_code())){
				          logger.info("v4  publicapi/customerInfo/paycallback-------updateWeChatPayStatus 21 ");
	
				          this.customerInfoNewService.updateWeChatPayStatus(tradeNo, "21");
			          }else{
				          logger.info("v4  publicapi/customerInfo/paycallback-------updateWeChatPayStatus 23 ");
	
				          this.customerInfoNewService.updateWeChatPayStatus(tradeNo, "23");
			          }
			        }
	
			        logger.info(" v4  payNum>>>>>>>>>>>: " + tradeNo);
			        String result1 = this.receivePackageService.getTaskIdByOrderNum(tradeNo);
			        logger.info("根据orderNum查询到的result 数据 任务的 >>>>>>>>>>>: " + result1);
			        JSONObject parseObject = JSONObject.parseObject(result1);
			        JSONObject jsonObject = parseObject.getJSONObject("data");
	
			        String taskId = jsonObject.getString("uid");
			        String memberId = jsonObject.getString("actorMemberId");
			        String packageNum = jsonObject.getString("parEstimateCount");
			        String orderNum = jsonObject.getString("orderNum");//查询代收点取件订单(如果有订单就是微信支付的)
			        String sign = "25";
			        logger.info("v4  微信回调接口 确认取件 orderNum=======================:"+orderNum);
			        if(!PubMethod.isEmpty(orderNum)){//不是空代表代收点微信支付
			        	sign = "21";
			        }
			        logger.info("v4  根据微信订单号查询到的任务taskId 和 memberId和 phone ||||||||||||||||||||||| taskId" + taskId + " memberId: " + memberId+" sign: "+sign);
			        this.receivePackageService.takeSendPackage(taskId, memberId, packageNum, receiveMsg.getTotal_fee(),sign);
			      }
			      String xml = "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + 
			        return_msg + "]]></return_msg></xml>";
			      logger.info("同步告诉微信结果:" + xml);
			      pw.write(xml);
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		      String xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg>ERROR</return_msg></xml>";
		      logger.info("同步告诉微信结果:" + xml);
		      pw.write(xml);
		    } finally {
		      pw.close();
		    }
		  }
		 /**
			 * @api {post} /customerInfoNew/paycallback2  财务回调微信支付2
			 * @apiVersion 0.3.0
			 * @apiDescription 财务回调微信支付222222
			 * @apiGroup 客户管理
			 * @apiParam {String} tradeNo 外部交易号（不带D）
			 * @apiParam {String} returnCode 业务结果 SUCCESS/FAIL
			 * @apiParam {String} result 财务成功或失败(SUCCESS/FAIL)
			 * @apiParam {String} totalFee 总金额
			 * @apiSampleRequest /customerInfo/paycallback2
			 * @apiSuccessExample Success-Response:
					{"data":"success","success":true}
			 * @apiErrorExample Error-Response:
		 *     HTTP/1.1 404 Not Found
		 *    
		 */
		 @ResponseBody
		  @RequestMapping(value={"/paycallback2"}, method={org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
		  public String paycallback2( String tradeNo,String returnCode,String result,String totalFee)
		    throws IOException
		  {
		    logger.info("微信支付异步回调okdilifeapi.weixin.paycallback2");
		    try {
		      
		      if ("SUCCESS".equals(returnCode)) {

		        if (JSON.parseObject(result).get("SUCCESS").equals(Boolean.valueOf(true))) {
		          logger.info("publicapi/customerInfo/paycallback2-------updateWeChatPayStatus 20 ");

		          this.customerInfoNewService.updateWeChatPayStatus(tradeNo, "20");
		        } else {
		          logger.info("publicapi/customerInfo/paycallback2-------updateWeChatPayStatus 21 ");
		          this.customerInfoNewService.updateWeChatPayStatus(tradeNo, "21");
		        }

		        logger.info("payNum>>>>>>>>>>>: " + tradeNo);
		        String result1 = this.receivePackageService.getTaskIdByOrderNum(tradeNo);
		        logger.info("根据orderNum查询到的result 数据 任务的 >>>>>>>>>>>: " + result1);
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
		        logger.info("根据微信订单号查询到的任务taskId 和 memberId和 phone ||||||||||||||||||||||| taskId" + taskId + " memberId: " + memberId);
		        this.receivePackageService.takeSendPackage(taskId, memberId, packageNum, totalFee, sign);
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
		/**
		 * @api {post} /customerInfoNew/isExistPhone  手机号是否添加过
		 * @apiVersion 0.3.0
		 * @apiDescription 手机号是否添加过
		 * @apiGroup 客户管理
		 * @apiParam {String} memberId memberId
		 * @apiParam {String} phone 手机号(格式用,分开，如：158222222222，15888888888)
		 * @apiSampleRequest /customerInfo/paycallback2
		 * @apiSuccess {String} data (true:存在手机号，false：不存在手机号)
		 * @apiSuccessExample Success-Response:
				{"data":"true","success":true}
		 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *    
	 */
		@ResponseBody
		@RequestMapping(value = "/isExistPhone", method = {RequestMethod.GET, RequestMethod.POST })
		public String  isExistPhone(Long memberId,String phone) {
			return customerInfoNewService.isExistPhone(memberId,phone);
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
		@RequestMapping(value = "/customerDetail", method = { RequestMethod.GET, RequestMethod.POST })
		public String customerDetail(String memberId, String customerId) {
			if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(customerId)) {
				throwError("compId ,quertCondition 不能为空");
			}
			return this.customerInfoNewService.customerDetail(memberId, customerId);
		}
		
		
		public static void main(String[] args) {
			String a="105656668094738432D8197694784".substring(0, "105656668094738432D8197694784".indexOf("D"));
			System.out.println(a);
			
		}
		
		
}
