package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/receivePackage")
public class ReceivePackageController extends BaseController{

	
	private Logger logger=Logger.getLogger(ReceivePackageController.class);
	
	@Autowired
	private ReceivePackageService packageService;
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryTakePackList 查询取件订单列表--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 查询取件订单列表
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 快递员id(不能为空)
	 * @apiParam {String} currentPage 当前页(不能为空)
	 * @apiParam {String} pageSize 每页显示多少条(不能为空)
	 * @apiSampleRequest /receivePackage/queryTakePackList
	 * @apiSuccess {String} contactAddress  联系人地址
	 * @apiSuccess {String} contactMobile  联系人手机号
	 * @apiSuccess {String} createTime  创建时间
	 * @apiSuccess {String} taskId 任务id
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "currentPage": 1,
		        "hasFirst": false,
		        "hasLast": false,
		        "hasNext": false,
		        "hasPre": false,
		        "items": [
		            {
		                "contactAddress": "北京海淀区",
		                "contactMobile": "13521283934",
		                "createTime": 1461662044233,
		                "taskId": 209522898026496
		            },
		            {
		                "contactAddress": "北京海淀区",
		                "contactMobile": "13521283934",
		                "createTime": 1461651708859,
		                "taskId": 209501221863424
		            }
		        ],
		        "offset": 0,
		        "pageCount": 1,
		        "pageSize": 15,
		        "rows": [],
		        "total": 6
		    },
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
	@RequestMapping(value = "/queryTakePackList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakePackList(String memberId, String currentPage, String pageSize){
		
		String result = packageService.queryTakePackList(memberId, currentPage, pageSize);
		return result;
	}
	
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryTakeConsigOrderList 查询包裹交寄订单列表-代收点--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 查询包裹交寄订单列表
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 快递员id(不能为空)
	 * @apiParam {String} netId 网络id(不能为空)
	 * @apiParam {String} currentPage 当前页(不能为空)
	 * @apiParam {String} pageSize 每页显示多少条(不能为空)
	 * @apiSampleRequest /receivePackage/queryTakeConsigOrderList
	 * @apiSuccess {String} headImg  头像
	 * @apiSuccess {String} contactName  名字
	 * @apiSuccess {String} contactMobile  手机号
	 * @apiSuccess {String} netName 网络名称
	 * @apiSuccess {String} estimateCount 包裹数量
	 * @apiSuccess {String} sendAddress 发件人地址
	 * @apiSuccess {String} sendMobile 发件人手机号
	 * @apiSuccess {String} addresseeAddress 收件人地址
	 * @apiSuccess {String} addresseeMobile 收件人手机号
	 * @apiSuccess {String} taskId 任务id
	 * @apiSuccess {String} parcelId 包裹id
	 * @apiSuccessExample Success-Response:
	   {
		    "data": {
		        "currentPage": 1,
		        "hasFirst": false,
		        "hasLast": false,
		        "hasNext": false,
		        "hasPre": false,
		          "items": [
		            {
		                "contactMobile": "15600215117",
		                "contactName": "你好",
		                "estimateCount": 1,
		                "headImg": "http://cas.okdit.net/nfs_data/mob/head/202580871659520.jpg",
		                "netName": "EMS速递",
		                "taskId": "11111111111111111",
		                "recordInfo": [
		                    {
		                        "parcelId": "111111111",
		                        "addresseeAddress": "13566699666",
		                        "addresseeMobile": "13566699666",
		                        "sendAddress": "北京市海淀区",
		                        "sendMobile": "13521225785"
		                    },
		                    {
		                     	"parcelId": "111111111",
		                        "addresseeAddress": "13566699666",
		                        "addresseeMobile": "13566699666",
		                        "sendAddress": "北京市海淀区",
		                        "sendMobile": "13521225785"
		                    }
		                ]
		            },
		            {
		                "contactMobile": "15600215117",
		                "contactName": "你好",
		                "estimateCount": 1,
		                "headImg": "http://cas.okdit.net/nfs_data/mob/head/202608765878272.jpg",
		                "netName": "EMS速递",
		                "recordInfo": [
		                	{
		                		"parcelId": "111111111",
		                        "addresseeAddress": "13566699666",
		                        "addresseeMobile": "13566699666",
		                        "sendAddress": "北京市海淀区",
		                        "sendMobile": "13521225785"
		                    },
		                    {
		                    	 "parcelId": "111111111",
		                        "addresseeAddress": "13566699666",
		                        "addresseeMobile": "13566699666",
		                        "sendAddress": "北京市海淀区",
		                        "sendMobile": "13521225785"
		                    }
		                ]
		            }
		        ],
		        "offset": 0,
		        "pageCount": 1,
		        "pageSize": 20,
		        "rows": [],
		        "total": 1
		    },
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
	@RequestMapping(value = "/queryTakeConsigOrderList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeConsigOrderList(String memberId, String netId, String currentPage, String pageSize){
		
		String result = packageService.queryTakeConsigOrderList(memberId, netId, currentPage, pageSize);
		return result;
	}
	
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryTakeTaskDetailByTaskId 查询取件订单详情(收派员)--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 查询取件订单详情
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 快递员id(不能为空)
	 * @apiParam {String} taskId 任务id(不能为空)
	 * @apiSampleRequest /receivePackage/queryTakeTaskDetailByTaskId
	 * @apiSuccess {String} contactName  联系人名字
	 * @apiSuccess {String} contactMobile  联系人手机号
	 * @apiSuccess {String} contactAddress  联系人地址
	 * @apiSuccess {String} appointDesc 备注
	 * @apiSuccess {String} parEstimateCount 数量
	 * @apiSuccess {String} taskId 任务taskId
	 * @apiSuccess {String} taskStatus :包裹状态 0：待取件;1：已取件;10：待派件;11：已派件;12:派件异常;
	 * @apiSuccess {String} thirdId 微信id
	 * @apiSuccess {String} tagContent 标记备注
	 * @apiSuccessExample Success-Response:
	   {
		    "data": {
		        "appointDesc": "iphone5se",
		        "contactAddress": "北京海淀区",
		        "contactMobile": "13521283934",
		        "contactName": "郑炯",
		        "parEstimateCount": "",
		        "taskId": 209499629862912,
		        "taskStatus": 1,
		        "thirdId": "osyqutwiVplVQPu27e-kDzbtJO8g",
				"tagContent":标注内容1|标注内容2|标注内容3
		    },
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
	@RequestMapping(value = "/queryTakeTaskDetailByTaskId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeTaskDetailByTaskId(String memberId, String taskId){
		
		String result = packageService.queryTakeTaskDetailByTaskId(memberId, taskId);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/takeSendPackage 收派员确认取件(从任务过来的)--取件(弃用)
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员确认取件(从任务过来的)
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务id(不能为空)
	 * @apiParam {String} memberId 收派员的memberId(不能为空)
	 * @apiParam {String} packageNum 包裹数量
	 * @apiParam {Double} freightMoney 应收运费
	 * @apiParam {String} sign 标识 21:标识代收点微信支付 25:代表其他(除代收点微信支付的,都是传25就行)
	 * @apiSampleRequest /receivePackage/takeSendPackage
	 * @apiSuccess {String} data: 001:收现金成功,002:确认取件失败, 22:弹付款二维码, 21:不弹二维码,但是给提示(财务同步失败,请联系客服); 会给一个推送,然后跳转页面到已取包裹
	 * @apiSuccessExample Success-Response:
	   {
		    "data": 22,
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
	@RequestMapping(value = "/takeSendPackage", method = { RequestMethod.POST, RequestMethod.GET })
	public String takeSendPackage(String taskId, String memberId, String packageNum, String freightMoney, String sign){
		
		String result = packageService.takeSendPackage(taskId, memberId, packageNum, freightMoney, sign);
		return result;
	}
	
	

	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/takeSendPackageByMember 收派员或者代收点添加取件(自己添加的取件)--取件(弃用)
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员或者代收点添加取件(自己添加的取件)
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 人员memberId(不能为空)
	 * @apiParam {String} sendName 发件人name
	 * @apiParam {String} sendPhone 发件人手机号
	 * @apiParam {String} sendAddress 发件人地址
	 * @apiParam {String} netId 网络Id
	 * @apiParam {String} packageNum 包裹数量
	 * @apiParam {Double} freightMoney 应收运费
	 * @apiParam {String} flag 0:现金支付, 1:微信支付
	 * @apiParam {BigDecimal} weightFortransit 包裹重量
	 * @apiParam {String} packageJson 包裹json 串格式:{"data":[{"receiveProvince":"beijing","receivePhone":"15810885211","receiveName":"zhangsan","expWaybillNum":"123"},{"receiveProvince":"shanghai","receivePhone":"15810885212","receiveName":"lisi","expWaybillNum":"456"},{"receiveProvince":"guangzhou","receivePhone":"15810885213","receiveName":"wanger","expWaybillNum":"789"},{"receiveProvince":"shenzhen","receivePhone":"15810885214","receiveName":"mazi","expWaybillNum":"110"}]} (不能为空)
	 * @apiSampleRequest /receivePackage/takeSendPackageByMember
	 * @apiSuccess {String} data: 如果flag是0 则返回的是 001:代表成功, 002:代表失败;还有一种情况,如果flag是 1 微信支付的话,返回的是/customerInfo/saomaCreate(取件扫描支付)的数据
	 * @apiSuccessExample Success-Response:
	   {
		    "data": {"data":"001"},
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
	@RequestMapping(value = "/takeSendPackageByMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String takeSendPackageByMember(String memberId, String sendName, String sendPhone, String sendAddress, String netId, 
			String packageNum, Double freightMoney, String packageJson, String flag){
		
		String result = packageService.takeSendPackageByMember(memberId, sendName, sendPhone, sendAddress, netId, 
				packageNum, freightMoney, packageJson, flag);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryHasTakeList 查询已取包裹列表--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 查询已取包裹列表
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 快递员memberId(不能为空)
	 * @apiParam {String} roleId 角色id(不能为空, 0,-1,1(属于收派员); 2,3(属于代收点))
	 * @apiParam {String} netName 网络名称
	 * @apiParam {String} date 格式时间 yyyy-MM-dd 如:2016-12-12 (目前不用)
	 * @apiParam {String} currentPage 当前页(不能为空)
	 * @apiParam {String} pageSize 每页显示多少条(不能为空)
	 * @apiSampleRequest /receivePackage/queryHasTakeList
	 * @apiSuccess {String} addresseeAddress:收件人地址
	 * @apiSuccess {String} addresseeMobile:收件人手机号
	 * @apiSuccess {String} parcelId:包裹id
	 * @apiSuccess {String} sendAddress:发件人地址
	 * @apiSuccess {String} sendMobile:发件人手机号
	 * @apiSuccess {String} createTime:时间
	 * @apiSuccess {String} netId:网络id(角色是代收代点时才有这个字段,叫快递根据这个网络是否可以叫快递,(住:同一个netId才可以叫快递))
	 * @apiSuccess {String} netName:网络名称(角色是代收代点时才有这个字段)
	 * @apiSuccess {BigDecimal} weightFortransit: 包裹重量
	 * @apiSuccessExample Success-Response:
	  {
		    "data": {
		        "currentPage": 1,
		        "hasFirst": false,
		        "hasLast": false,
		        "hasNext": false,
		        "hasPre": false,
		        "items": [
		            {
		                "addresseeAddress": "佺",
		                "addresseeMobile": "鑰",
		                "parcelId": 209714126823426,
		                "sendAddress": "",
		                "sendMobile": "",
		                "createTime" :11111111111,
		                "weightFortransit" :0.11
		            }
		        ],
		        "offset": 0,
		        "pageCount": 1,
		        "pageSize": 15,
		        "rows": [],
		        "total": 1
		    },
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
	@RequestMapping(value = "/queryHasTakeList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryHasTakeList(String memberId, String roleId, String netName, String currentPage, String pageSize){
		
		String result = packageService.queryHasTakeList(memberId, roleId, "", netName, currentPage, pageSize);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryHasTakePackDetailByParcelId 查询已取包裹详情--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 查询已取包裹详情
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} parcelId 包裹parcelId(不能为空)
	 * @apiSampleRequest /receivePackage/queryHasTakePackDetailByParcelId
	 * @apiSuccess {String} addresseeAddress:收件人地址
	 * @apiSuccess {String} addresseeMobile:收件人手机号
	 * @apiSuccess {String} addresseeName:收件地址
	 * @apiSuccess {String} parcelRemark:备注
	 * @apiSuccess {String} expWaybillNum:运单号
	 * @apiSuccess {String} sendAddress:发件人地址
	 * @apiSuccess {String} sendMobile:发件人手机号
	 * @apiSuccess {String} sendName:发件人姓名
	 * @apiSuccess {BigDecimal} weightFortransit:包裹重量
	 * @apiSuccessExample Success-Response:
	  {
		    "data": {
		        "addresseeAddress": "佺",
		        "expWaybillNum": "111111111111111111111",
		        "addresseeMobile": "鑰",
		        "addresseeName": "",
		        "parcelRemark": "没有描述",
		        "sendAddress": "",
		        "sendMobile": "",
		        "sendName": "",
		        "weightFortransit": ""
		    },
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
	@RequestMapping(value = "/queryHasTakePackDetailByParcelId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryHasTakePackDetailByParcelId(String parcelId){
		
		String result = packageService.queryHasTakePackDetailByParcelId(parcelId);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/querySiteMemberByMemberId 查询该站点下的其他收派员--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 查询该站点下的其他收派员
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId :收派员memberId(不能为空)
	 * @apiSampleRequest /receivePackage/querySiteMemberByMemberId
	 * @apiSuccess {String} headImgPath:头像
	 * @apiSuccess {String} memberId:人员memberId
	 * @apiSuccess {String} memberName:人员名字
	 * @apiSuccess {String} memberPhone:人员电话
	 * @apiSuccessExample Success-Response:
	  {
		    "data": {
		        "countMember": 25,
		        "memberList": [
		            {
		                "headImgPath": "http://cas.okdit.net/nfs_data/mob/head/2051401181502004.jpg",
		                "memberId": 2051401181502004,
		                "memberName": "磁扣",
		                "memberPhone": "15110282193",
		                "roleId": 2
		            },
		            {
		                "headImgPath": "http://cas.okdit.net/nfs_data/mob/head/158626780356608.jpg",
		                "memberId": 158626780356608,
		                "memberName": "看监控",
		                "memberPhone": "17111100012",
		                "roleId": 2
		            }
		        ]
		    },
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
	@RequestMapping(value = "/querySiteMemberByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySiteMemberByMemberId(String memberId){
		
		String result = packageService.querySiteMemberByMemberId(memberId);
		return result;
	}
	/**
	 * @author song
	 * @api {post} /receivePackage/consignationInfo 查询代理点交寄信息    --交寄 2016-12-26
	 * @apiVersion 0.2.0
	 * @apiDescription 查询代理点交寄信息（取件记录）
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId :代理点memberId(不能为空)
	 * @apiParam {String} parcelIds ：包裹id 格式:209298850717696,209298850717696,209298915729410,209298915729410 中间用逗号隔开(不能为空)
	 * @apiSampleRequest /receivePackage/consignationInfo
	 * @apiSuccess {String} compId:代理点 id
	 * @apiSuccess {String} compName:代理点 名称
	 * @apiSuccess {String} compTelephone:代理点电话
	 * @apiSuccess {String} memberName:  人员名称
	 * @apiSuccess {String} roleId:角色id
	 * @apiSuccess {String} netName: 
	 * @apiSuccess {String} createTime: 时间
	 * @apiSuccess {String} sendAddress: 发件人地址
	 * @apiSuccess {String} sendMobile:发件人电话
	 * @apiSuccess {String} sendName:发件人名称
	 * @apiSuccess {String} mapListNum: 交寄件数
	 * @apiSuccessExample Success-Response:
	 {
    "data": {
        "map": {
            "compId": "213460646674432",
            "compName": "十八",
            "compTelephone": "13510850006",
            "memberName": "李毅",
            "roleId": "2"
        },
        "mapList": [
            {
                "createTime": 1464956047529,
                "netName": "",
                "sendAddress": "北京市海淀区花园北路14号",
                "sendMobile": "13244555588",
                "sendName": "黄瓜"
            },
            {
                "createTime": 1464954357122,
                "netName": "",
                "sendAddress": "北京市海淀区花园北路14号",
                "sendMobile": "13511211444",
                "sendName": "阿魏"
              }
          ],
           "mapListNum": 2
       },
         "success": true
     }
	
	 * @apiErrorExample Error-Response:
	 * {
	 *	  "errCode": 0,
	 *	  "errSubcode": "001",
	 *	  "message": "memberId 不能为空!!!",
	 *	  "success": false
	 *	}
	 *{
	 *	  "errCode": 0,
	 *	  "errSubcode": "002",
	 *	  "message": "parcelIds 不能为空!!!",
	 *	  "success": false
	 *	}
	 */
	@ResponseBody
	@RequestMapping(value = "/consignationInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String consignationInfo(String memberId,String parcelIds){
		
		String result = packageService.consignationInfo(memberId,parcelIds);
		return result;
	}
	
	/**
	 * @author panke.sun
	 * @api {post} /receivePackage/turnTakePackageToMember 转单--new
	 * @apiVersion 0.2.0
	 * @apiDescription 转单
	 * @apiGroup 订单
	 * @apiParam {String} taskId 任务taskId(不能为空)
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {String} toMemberId 被转单人员memberId(不能为空)
	 * @apiParam {String} toMemberPhone 被转单人员phone
	 * @apiSampleRequest /receivePackage/rurnTakePackageToMember
	 * @apiSuccess {String} data:001 成功, 002:失败
	 * @apiSuccessExample Success-Response:
	  {"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/turnTakePackageToMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String turnTakePackageToMember(String taskId, String memberId, String toMemberId, String toMemberPhone){
		
		String result = packageService.turnTakePackageToMember(taskId, memberId, toMemberId, toMemberPhone);
		return result;
	}
	
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/consignationToSiteByMemberId 交寄(收派员把包裹交寄给站点)--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 交寄(收派员把包裹交寄给站点)
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} parcelIds 包裹id 格式:209298850717696,209298850717696,209298915729410,209298915729410 中间用逗号隔开(不能为空)
	 * @apiSampleRequest /receivePackage/consignationToSiteByMemberId
	 * @apiSuccess {String} data:001 成功, 002:失败
	 * @apiSuccessExample Success-Response:
	  {"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/consignationToSiteByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String consignationToSiteByMemberId(String parcelIds){
		
		String result = packageService.consignationToSiteByMemberId(parcelIds);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryNearMemberByTude 代收点查询附近5公里的收派员--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 代收点查询附近5公里的收派员
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId :代收点的 memberId(不能为空)
	 * @apiParam {String} netId :包裹上的netId,选择的包裹肯定是同一个netId,要么是空,要么有值
	 * @apiSampleRequest /receivePackage/queryNearMemberByTude
	 * @apiSuccess {String} memberId :人员memberId     
	 * @apiSuccess {String} headImg: 头像          
	 * @apiSuccess {String} memberName :人员名称         
	 * @apiSuccess {String} memberMobile :人员手机号      
	 * @apiSuccess {String} netName :网络名称            
	 * @apiSuccess {String} distance :距离             
	 * @apiSuccess {String} lng :经度            
	 * @apiSuccess {String} lat :纬度            
	 * @apiSuccessExample Success-Response:
	   {
		    "data": [
		        {
		            "compId": 207346307743744,
		            "compName": "北京厂洼路站",
		            "createTime": 1462435969215,
		            "distance": 0.005,
		            "distanceNew": 5,
		            "distanceNumber": 5,
		            "distanceUnit": "m",
		            "headImg": "http://publicapi.okdit.net/nfs_data/mob/head//179145206538240.jpg",
		            "id": 211145936125952,
		            "lat": 39.9849540567301,
		            "lng": 116.3838509323339,
		            "memberId": 179145206538240,
		            "memberMobile": "15011232453",
		            "memberName": "赵虎",
		            "memo": "",
		            "netId": 1502,
		            "netName": "中通快递",
		            "unit": "m"
		        },
		        {
		            "compId": 135033470132224,
		            "compName": "北七家",
		            "createTime": 1462436650165,
		            "distance": 0.008,
		            "distanceNew": 8,
		            "distanceNumber": 8,
		            "distanceUnit": "m",
		            "headImg": "http://publicapi.okdit.net/nfs_data/mob/head//202580871659520.jpg",
		            "id": 211147364286464,
		            "lat": 39.98499182319306,
		            "lng": 116.3838281981215,
		            "memberId": 202580871659520,
		            "memberMobile": "13155667788",
		            "memberName": "",
		            "memo": "",
		            "netId": 2000,
		            "netName": "ANE安能物流",
		            "unit": "m"
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
	@RequestMapping(value = "/queryNearMemberByTude", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNearMemberByTude(String memberId, String netId){
		
		String result = packageService.queryNearMemberByTude(memberId, netId);
		return result;
//		Map<String, Object> map = new HashMap<>();
//		return jsonSuccess(map);
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/consignationToMemberByParcelIds 代收点把包裹交寄给收派员--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 代收点添加新的快递员
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} parcelIds 包裹id 格式:209298850717696,209298850717696,209298915729410,209298915729410 中间用逗号隔开(不能为空)
	 * @apiParam {String} toMemberId 被交寄收派员的memberId(不能为空)
	 * @apiSampleRequest /receivePackage/consignationToMemberByParcelIds
	 * @apiSuccess {String} data: 001:代表成功, 002:代表失败
	 * @apiSuccessExample Success-Response:
	   {
		    "data": 001,
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
	@RequestMapping(value = "/consignationToMemberByParcelIds", method = { RequestMethod.POST, RequestMethod.GET })
	public String consignationToMemberBy(String parcelIds, String toMemberId){
		
		String result = packageService.consignationToMemberByParcelIds(parcelIds, toMemberId);
		return result;
	}
	
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/saveMemberInfoToNewMemberInfo 代收点添加新的快递员--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 代收点添加新的快递员
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} parcelIds 包裹id 格式:209298850717696,209298850717696,209298915729410,209298915729410 中间用逗号隔开(不能为空)
	 * @apiParam {String} newMemberName 新用户name(不能为空)
	 * @apiParam {String} compName 公司名称(不能为空)
	 * @apiParam {String} newMemberPhone 新用户手机号(不能为空)
	 * @apiSampleRequest /receivePackage/saveMemberInfoToNewMemberInfo
	 * @apiSuccess {String} data: 003:已经注册过, 不是003 的都是没注册过的 返回的是一个url 是一个链接(下载)http://promo.okdi.net/promo/lgrg/fenxiang-courier.jsp
	 * @apiSuccessExample Success-Response:
	   {
		    "data": http://promo.okdi.net/promo/lgrg/fenxiang-courier.jsp,
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
	@RequestMapping(value = "/saveMemberInfoToNewMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveMemberInfoToNewMemberInfo(String parcelIds, String newMemberName, String compName, String newMemberPhone){
		
		
		String result = packageService.saveMemberInfoToNewMemberInfo(parcelIds, newMemberName, compName, newMemberPhone);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/calledCourierToMember 代收点叫快递;取消叫快递(不叫了)--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 代收点叫快递,取消叫快递(不叫了)
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务taskId(叫快递时传空,不叫了必传taskId)
	 * @apiParam {String} memberId 代收点的memberId(不能为空)
	 * @apiParam {String} parcelIds 包裹id 格式:209298850717696,209298850717696,209298915729410,209298915729410 中间用逗号隔开(不能为空)
	 * @apiParam {String} flag 0:取消叫快递(不叫了), 1:叫快递(不能为空)
	 * @apiSampleRequest /receivePackage/calledCourierToMember
	 * @apiSuccess {String} data:taskId 任务taskId(格式:209330203140096) 叫快递成功, 003:代表不叫了, 002:代表叫快递失败, "004":已经有快递员接单,继续点击不叫了
	 * @apiSuccessExample Success-Response:
	   {
		    "data": xxxxxx,
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
	@RequestMapping(value = "/calledCourierToMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String calledCourierToMember(String taskId, String memberId, String parcelIds, String flag){
		
		String result = packageService.calledCourierToMember(taskId, memberId, parcelIds, flag);
		return result;
//		return jsonSuccess("004");
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/memberConfirmOrder 收派员点击确定(推送的消息点击)--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员点击接单
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务taskId(不能为空)
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {String} phone 收派员电话
	 * @apiSampleRequest /receivePackage/memberConfirmOrder
	 * @apiSuccess {String}  003:代表任务取消, 001:抢单成功, "004":该订单已被抢
	 * @apiSuccessExample Success-Response:
	   {"data":"该订单已被抢!!!","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/memberConfirmOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String memberConfirmOrder(String taskId, String memberId, String phone){
		
		String result = packageService.memberConfirmOrder(taskId, memberId, phone);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/collectPointsConfirmSend 确认交寄或者取消交寄--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 确认交寄或者取消交寄
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务taskId(不能为空)
	 * @apiParam {String} memberId 代收点的memberId(不能为空)
	 * @apiParam {String} flag 0:取消交寄, 1:确认交寄(不能为空)
	 * @apiSampleRequest /receivePackage/collectPointsConfirmSend
	 * @apiSuccess {String} data: 001:代表确认交寄成功或者取消交寄成功, 002:代表失败, 005:代表快递员已经接单并且付款完成
	 * @apiSuccessExample Success-Response:
	   {"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/collectPointsConfirmSend", method = { RequestMethod.POST, RequestMethod.GET })
	public String collectPointsConfirmSend(String taskId, String memberId, String flag){
		
		String result = packageService.collectPointsConfirmSend(taskId, memberId, flag);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryTakeRecordList 取件记录--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 取件记录
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {String} date 选择的日期格式yyyy-mm-dd(不能为空)
	 * @apiParam {String} phone 发件人手机号
	 * @apiSampleRequest /receivePackage/queryTakeRecordList
	 * @apiSuccess {String} parcelRemark: 包裹备注
	 * @apiSuccess {String} weightFortransit: 计费重量(销售)
	 * @apiSuccess {String} codAmount : 代收货款金额
	 * @apiSuccess {String} expWaybillNum : 运单号
	 * @apiSuccess {String} freight :  应收运费
	 * @apiSuccess {String} freightPaymentMethod : 应收运费支付方式
	 * @apiSuccess {String} insureAmount :  保价金额
	 * @apiSuccess {String} netName :  网络名称
	 * @apiSuccess {String} packingCharges : 包装费
	 * @apiSuccess {String} pricePremium :  保价费
	 * @apiSuccess {String} sendAddress : 发件人地址
	 * @apiSuccess {String} sendMobile : 发件人电话
	 * @apiSuccess {String} sendName : 发件人名称
	 * @apiSuccess {Long} actualTakeMember : 任务id
	 * @apiSuccess {Long} addresseeName : 收件人姓名
	 * @apiSuccess {Long} addresseeMobile : 收件人电话
	 * @apiSuccess {Long} addresseeAddress : 收件人地址
	 * @apiSuccessExample Success-Response:		               
	   {
		    "data": [
		        {
		            "parcelRemark": "",
		            "chareWeightFortransit": "",
		            "codAmount": 0,
		            "createTime": 1461921506816,
		            "expWaybillNum": "238998",
		            "freight": "",
		            "freightPaymentMethod": 0,
		            "insureAmount": "",
		            "netName": "",
		            "packingCharges": "",
		            "pricePremium": "",
		            "sendAddress": "北京市海淀区",
		            "sendMobile": "13569899568",
		            "sendName": "job"
		        },
		        {
		            "parcelRemark": "",
		            "chareWeightFortransit": "",
		            "codAmount": 0,
		            "createTime": 1461921472685,
		            "expWaybillNum": "538966",
		            "freight": "",
		            "freightPaymentMethod": 0,
		            "insureAmount": "",
		            "netName": "",
		            "packingCharges": "",
		            "pricePremium": "",
		            "sendAddress": "北京市海淀区",
		            "sendMobile": "13568656596",
		            "sendName": "某天"
		        },
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
	@RequestMapping(value = "/queryTakeRecordList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeRecordList(String memberId, String date, String phone){
		
		String result = packageService.queryTakeRecordList(memberId, date, phone);
		return result;
	}
	/**
	 * @author song
	 * @api {post} /receivePackage/queryTakeRecordListk 取件记录--取件 2017-1-4
	 * @apiVersion 0.2.0
	 * @apiDescription 取件记录（new1-4）
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)  {站长可以是下拉选  其它收派员}
	 * @apiParam {String} date 选择的日期格式yyyy-mm-dd(不能为空)
	 * @apiParam {String} phone 发件人手机号   (搜索条件)
	 * @apiParam {String} expWaybillNum 运单号   (搜索条件)
	 * @apiParam {String} all all=all 站点全部包裹
	 * @apiParam {Integer} currentPage
	 * @apiParam {Integer} pageSize  
	 * @apiSampleRequest /receivePackage/queryTakeRecordListk
     * @apiSuccess {String} expWaybillNum : 运单号 
	 * @apiSuccess {String} netName :  网络名称
	 * @apiSuccess {String} sendAddress : 发件人地址
	 * @apiSuccess {String} sendMobile : 发件人电话
	 * @apiSuccess {String} sendName : 发件人名称
	 * @apiSuccess {Date} createTime : 取件时间
	 * @apiSuccess {Long} actualTakeMember : 取件人
	 * @apiSuccess {Long} uid  :     包裹id
	 
	 * @apiSuccessExample Success-Response:		               
	{
    "data": {
        "listMap": [
            {
                 "actualTakeMember": 208419800006656,
				"expWaybillNum": "70567200043203",
				"netName": "AAE全球专递",
				"parcelRemark": "",
				"pickupTime": 1482833545837,
				"sendAddress": "北京市海淀区花园北路",
				"sendMobile": "13888899999",
				"sendName": "艾玛",
				"uid": 253922753454081
            },
            {
              "actualTakeMember": 208419800006656,			   
			    "expWaybillNum": "",
			    "freight": "",
			    "freightPaymentMethod": 0,
			    "netName": "百世汇通",
			    "parcelRemark": "",
			    "pickupTime": 1482825717002,
			    "sendAddress": "北京市海淀区花园北路14号",
			    "sendMobile": "13681296635",
			    "sendName": "港货会",
			    "uid": 253906336948225
            }
        ],
        "listMapNum": 2
    },
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
	@RequestMapping(value = "/queryTakeRecordListk", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeRecordListk(String memberId, String date, String phone,String expWaybillNum,String all,Integer currentPage,Integer pageSize){
		
		String result = packageService.queryTakeRecordListk(memberId, date, phone,expWaybillNum,all,currentPage,pageSize);
		return result;
	}
	/**
	 * @author song
	 * @api {post} /receivePackage/queryRecRecordList 揽收记录--取件 2017-2-18
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收记录（new1-5）
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)  {站长可以是下拉选  其它收派员}
	 * @apiParam {String} date 选择的日期格式yyyy-mm-dd(不能为空)
	 * @apiParam {String} phone 发件人手机号   (搜索条件)
	 * @apiParam {String} expWaybillNum 运单号   (搜索条件)
	 * @apiParam {String} compId  站点全部包裹
	 * @apiParam {Integer} currentPage
	 * @apiParam {Integer} pageSize  
	 * @apiSampleRequest /receivePackage/queryRecRecordList
     * @apiSuccess {String} expWaybillNum : 运单号 
	 * @apiSuccess {String} netName :  网络名称
	 * @apiSuccess {String} code :  取件标号
	 * @apiSuccess {String} deliveryName : 交付人名称
	 * @apiSuccess {String} deliveryUnits : 交付人单位
	 * @apiSuccess {Date} createTime : 交付时间
	 * @apiSuccess {Long} parId  :     包裹id	 				      					 	 
	 * @apiSuccessExample Success-Response:		               
	{
    "data": {
        "listMap": [
            {
                 "deliveryName": "张三",
                 "deliveryUnits": "前台",
				"expWaybillNum": "70567200043203",
				"netName": "AAE全球专递",
				"createTime": 1482833545837,
				"code" : "124324"
				"uid": 253922753454081
            },
            {
             	"deliveryName": "张三",
                 "deliveryUnits": "前台",
				"expWaybillNum": "70567200043203",
				"netName": "AAE全球专递",
				"createTime": 1482833545837,
				"code" : "124324"
				"uid": 253922753454081
            }
        ],
        "listMapNum": 2
    },
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
	@RequestMapping(value = "/queryRecRecordList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRecRecordList(String memberId, String date, String phone,String expWaybillNum,String compId,Integer currentPage,Integer pageSize){
		
		String result = packageService.queryRecRecordList(memberId, date, phone,expWaybillNum,compId,currentPage,pageSize);
		return result;
	}
	
	
	/**
	 * @author song
	 * @api {post} /receivePackage/queryTakeRecordDetailed 揽收详情-取件 2017-2-18
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收详情（new1-5）
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空) 
	 * @apiParam {Long}   uid   包裹id  （不能为空） 
	 * @apiSampleRequest /receivePackage/queryTakeRecordDetailed
	 * @apiSuccess {String} parcelRemark: 包裹备注
	 * @apiSuccess {String} expWaybillNum : 运单号
	 * @apiSuccess {String} netName :  网络名称
	 * @apiSuccess {String} sendAddress : 发件人地址
	 * @apiSuccess {String} sendMobile : 发件人电话
	 * @apiSuccess {String} sendName : 发件人名称
	 * @apiSuccess {Date} pickupTime : 取件时间
	 * @apiSuccess {Long} actualTakeMember : 取件人
	 * @apiSuccess {String} addresseeName : 收件人姓名
	 * @apiSuccess {String} addresseeMobile : 收件人电话
	 * @apiSuccess {String} addresseeAddress : 收件人地址
	 * @apiSuccess {Long} uid  :     包裹id
	 * @apiSuccess {Long} num  :     包裹数量
	 * @apiSuccess {String} shipmentRemark : 已发运    1是   0否
	 * @apiSuccess {String} pacelWeight : 包裹重量
	 * @apiSuccess {String} parcelType : 物品类型
	 * @apiSuccess {String} serviceName : 服务产品
	 * @apiSuccess {Date} shipmentOrPeerTime : 发运时间
	 * @apiSuccess {String} comment   标记备注
	 * @apiSuccess {String} deliveryAddress : 交付地址
	 * @apiSuccess {String} deliveryMobile : 交付电话
	 * @apiSuccess {String} deliveryName ：交付姓名
	 * @apiSuccess {String} deliveryUnits : 交付单位
	 * @apiSuccess {Date} createTime : 交付时间
	 * @apiSuccessExample Success-Response:		               
	{
    "data": {
                 "addresseeAddress": "北京朝阳区",
                 "addresseeMobile": "13588899999",
                 "addresseeName": "哈哈哈",
				"expWaybillNum": "70567200043203",
				"netName": "AAE全球专递",
				"parcelRemark": "",
				"pickupTime": 1482833545837,
				"sendAddress": "北京市海淀区花园北路",
				"sendMobile": "13888899999",
				"sendName": "艾玛",
				"uid": 253922753454081
				        
     },
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
	@RequestMapping(value = "/queryTakeRecordDetailed", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeRecordDetailed(String memberId, Long uid){		
		String result = packageService.queryTakeRecordDetailed(memberId, uid);
		return result;
	}
	
	
	
	/**
	 * @author song
	 * @api {post} /receivePackage/queryTakeforms 揽收报表--取件 2016-12-28
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收报表（new）
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)  {站长可以是下拉选  其它收派员 提供}
	 * @apiParam {String} date 选择的日期格式yyyy-mm(不能为空)	
	 * @apiParam {String} all    al 站点全部包裹	 
	 * @apiSampleRequest /receivePackage/queryTakeforms	
	 * @apiSuccessExample Success-Response:	
    {
   "data": {
        "mapList": [
            {
                "date": "6月9日",
                "count": 1
            },
            {
                "date": "6月4日",
                "count": 17
            },
            {
                "date": "6月3日",
                "count": 4
            },
            {
                "date": "6月1日",
                "count": 3
            }
          ],
          "sum": 25
      },
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
	@RequestMapping(value = "/queryTakeforms", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakeforms(String memberId, String date,String all){
		
		String result = packageService.queryTakeforms(memberId, date, all);
		return result;
	}
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryTakePackageList 添加取件查询包裹信息--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 添加取件查询包裹信息
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务taskId(不能为空)
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiSampleRequest /receivePackage/queryTakePackageList
	 * @apiSuccess {String} addresseeAddress: 收件城市
	 * @apiSuccess {String} addresseeMobile : 收件人电话
	 * @apiSuccess {String} addresseeName : 收件人姓名
	 * @apiSuccess {String} expWaybillNum :  运单号
	 * @apiSuccess {String} parcelId : 包裹parcelId
	 * @apiSuccess {String} weightFortransit : 包裹重量
	 * @apiSuccessExample Success-Response:
	 	{
		    "data": [
		        {
		            "addresseeAddress": "李",
		            "addresseeMobile": "老",
		            "addresseeName": "",
		            "expWaybillNum": "",
		            "parcelId": 210034087059458
		        },
		        {
		            "addresseeAddress": "李",
		            "addresseeMobile": "老",
		            "addresseeName": "",
		            "expWaybillNum": "",
		            "parcelId": 210034087059458
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
	@RequestMapping(value = "/queryTakePackageList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakePackageList(String taskId, String memberId){
		
		String result = packageService.queryTakePackageList(taskId, memberId);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/deleteParcelByParcelId 删除包裹--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 删除包裹
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务taskId(不能为空)
	 * @apiParam {String} parcelId 包裹parcelId(不能为空)
	 * @apiSampleRequest /receivePackage/deleteParcelByParcelId
	 * @apiSuccess {String} data : 001:删除成功, 002 删除失败
	 * @apiSuccessExample Success-Response:
	 	{"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteParcelByParcelId", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteParcelByParcelId(String taskId, String parcelId){
		
		String result = packageService.deleteParcelByParcelId(taskId, parcelId);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/deleteTaskByTaskId 删除任务--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 删除任务
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务taskId(不能为空)
	 * @apiSampleRequest /receivePackage/deleteTaskByTaskId
	 * @apiSuccess {String} data : 001:删除成功, 002 删除失败
	 * @apiSuccessExample Success-Response:
	 	{"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteTaskByTaskId", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteTaskByTaskId(String taskId){

		String result = packageService.deleteTaskByTaskId(taskId);
		return result;
	}
	@ResponseBody
	@RequestMapping(value = "/takeSendPackageByMemberAdd", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeSendPackageByMemberAdd(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, Double freightMoney, String packageJson){
		
		String result = packageService.takeSendPackageByMemberAdd(memberId, sendName,sendPhone, sendAddress, netId,
				packageNum, freightMoney, packageJson);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/queryRecordDetailByTaskId 查询取件记录详情--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 查询取件记录详情
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务id(不能为空)
	 * @apiSampleRequest /receivePackage/queryRecordDetailByTaskId
	 * @apiSuccess {String} contactName  联系人名字
	 * @apiSuccess {String} contactMobile  联系人手机号
	 * @apiSuccess {String} contactAddress  联系人地址
	 * @apiSuccess {String} appointDesc 备注
	 * @apiSuccess {String} parEstimateCount 数量
	 * @apiSuccess {String} taskId 任务taskId
	 * @apiSuccess {String} taskStatus :包裹状态 0：待取件;1：已取件;10：待派件;11：已派件;12:派件异常;
	 * @apiSuccessExample Success-Response:
	   {
		    "data": {
		        "appointDesc": "æå¨è¦æ¬§å§ä¼",
		        "contactAddress": "åäº¬æµ·æ·åº",
		        "contactMobile": "13521283934",
		        "contactName": "éç¯",
		        "parEstimateCount": 1,
		        "taskId": 216176164913152,
		        "taskSource": 6,
		        "taskStatus": 0
		    },
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
	@RequestMapping(value = "/queryRecordDetailByTaskId", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryRecordDetailByTaskId(String taskId){
		
		String result = packageService.queryRecordDetailByTaskId(taskId);
		return result;
	}
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/addParcelToTaskId 收派员从取件订单中确认取件--新取件-新
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员从取件订单中确认取件
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} taskId 任务id(不能为空)
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {int} packageNum 包裹个数
	 * @apiParam {String} sign 传空就行
	 * @apiParam {String} packageJson 新添加的包裹json串{"data":[{"receiveProvince":"beijing","receivePhone":"15810885211","receiveName":"zhangsan","expWaybillNum":"123","chareWeightFortransit":11},{"receiveProvince":"shanghai","receivePhone":"15810885212","receiveName":"lisi","expWaybillNum":"456","chareWeightFortransit":11},{"receiveProvince":"guangzhou","receivePhone":"15810885213","receiveName":"wanger","expWaybillNum":"789","chareWeightFortransit":11},{"receiveProvince":"shenzhen","receivePhone":"15810885214","receiveName":"mazi","expWaybillNum":"110","chareWeightFortransit":11}]}, 没有新加的包裹传空 
	 * @apiSampleRequest /receivePackage/addParcelToTaskId
	 * @apiSuccess {String} data  001:代表确认取件成功, 002:代表失败
	 * @apiSuccessExample Success-Response:
	   {
		    "data": "001",
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
	@RequestMapping(value = "/addParcelToTaskId", method = {RequestMethod.POST, RequestMethod.GET })
	public String addParcelToTaskId(String taskId, String memberId, int packageNum, String sign, String packageJson){
		
		String result = packageService.addParcelToTaskId(taskId, memberId, packageNum, sign, packageJson);
		return result;
	}
	
	/**
	 * @author zhengjiong
	 * @api {post} /receivePackage/takeReceivePackageByMember 收派员或者代收点添加取件(自己添加的取件)--新取件-新
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员或者代收点添加取件(自己添加的取件)
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 人员memberId(不能为空)
	 * @apiParam {String} sendName 发件人name
	 * @apiParam {String} sendPhone 发件人手机号
	 * @apiParam {String} sendAddress 发件人地址
	 * @apiParam {String} netId 网络Id
	 * @apiParam {String} packageNum 包裹数量
	 * @apiParam {String} packageJson 包裹json 串格式:{"data":[{"receiveProvince":"beijing","receivePhone":"15810885211","receiveName":"zhangsan","expWaybillNum":"123","chareWeightFortransit":11},{"receiveProvince":"shanghai","receivePhone":"15810885212","receiveName":"lisi","expWaybillNum":"456","chareWeightFortransit":11},{"receiveProvince":"guangzhou","receivePhone":"15810885213","receiveName":"wanger","expWaybillNum":"789","chareWeightFortransit":11},{"receiveProvince":"shenzhen","receivePhone":"15810885214","receiveName":"mazi","expWaybillNum":"110","chareWeightFortransit":11}]} (不能为空)
	 * @apiSampleRequest /receivePackage/takeReceivePackageByMember
	 * @apiSuccess {String} data: 返回的是 001:代表成功, 002:代表失败;
	 * @apiSuccessExample Success-Response:
	   {
		    "data": "001",
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
	@RequestMapping(value = "/takeReceivePackageByMember", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageByMember(String memberId, String sendName,
			String sendPhone, String sendAddress, String netId,
			String packageNum, String packageJson){
		
		String result = packageService.takeReceivePackageByMember(memberId, sendName,
				sendPhone, sendAddress, netId,
				packageNum, packageJson);
		return result;
	}
	
	/**
	 * @author song
	 * @api {post} /receivePackage/queryPackage 揽收运单号查询 2017-2-18
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收运单号查询（new1-5）
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)  
	 * @apiParam {String} sendMobile 发件人手机号   (查询条件)
	 * @apiParam {String} expWaybillNum 运单号    (查询条件)
	 * @apiParam {String} code     取件码    (查询条件)
	 * @apiParam {String} netName   网络名称  (查询条件)
	 * @apiParam {String} netId   网络id  (查询条件)
	 * @apiSampleRequest /receivePackage/queryPackage
	 * @apiSuccess {String} netName :  网络名称
	 * @apiSuccess {String} netId :  网络id 
	 * @apiSuccess {String} sendAddress : 发件人地址
	 * @apiSuccess {String} sendMobile : 发件人电话
	 * @apiSuccess {String} sendName : 发件人名称
	 * @apiSuccess {String} addresseeName : 收件人姓名
	 * @apiSuccess {String} addresseeMobile : 收件人电话
	 * @apiSuccess {String} addresseeAddress : 收件人地址
	 * @apiSuccess {Long} parcelId  :     包裹id
	 * @apiSuccess {String} code     取件码
	 * @apiSuccess {String} expWaybillNum : 运单号
	 * @apiSuccess {String} comment   标记备注
	 * @apiSuccess {String} pacelWeight : 包裹重量
	 * @apiSuccess {String} parcelType : 物品类型
	 * @apiSuccess {String} serviceName : 服务产品
	 * @apiSuccess {String} flag   0 ：未揽收，1已揽收
	 * @apiSuccessExample Success-Response:		               
	 {
	    "data": {
	     	"flag":  0
	        "listMap": [
	            {
	               "addresseeAddress": "北京朝阳区",
	                "addresseeMobile": "13588899999",
	                "addresseeName": "哈哈哈",
					"expWaybillNum": "70567200043203",
					"netName": "AAE全球专递",
					"sendAddress": "北京市海淀区花园北路",
					"sendMobile": "13888899999",
					"sendName": "艾玛",
					"code": "234",
					"comment": "易燃",
					"parcelId": 253922753454081
	            },
	        ]
	    },
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
	@RequestMapping(value = "/queryPackage", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryPackage(String memberId, String expWaybillNum, String netId,String netName,String sendMobile,String code){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}						
		return packageService.queryPackage(memberId,sendMobile,netId,netName,expWaybillNum,code);
	}
	/**
	 * @author song
	 * @api {post} /receivePackage/queryPackageMobile 揽收手机号查询 2017-2-23
	 * @apiVersion 0.2.0
	 * @apiDescription 揽收手机号查询（new1-5）
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)  
	 * @apiParam {String} sendMobile 发件人手机号   (查询条件)
	 * @apiParam {String} netName   网络名称  (查询条件)
	 * @apiParam {String} netId   网络id  (查询条件)
	 * @apiSampleRequest /receivePackage/queryPackageMobile
	 * @apiSuccess {String} netName :  网络名称
	 * @apiSuccess {String} netId :  网络id 
	 * @apiSuccess {String} sendAddress : 发件人地址
	 * @apiSuccess {String} sendMobile : 发件人电话
	 * @apiSuccess {String} sendName : 发件人名称
	 * @apiSuccess {String} addresseeName : 收件人姓名
	 * @apiSuccess {String} addresseeMobile : 收件人电话
	 * @apiSuccess {String} addresseeAddress : 收件人地址
	 * @apiSuccess {Long} parcelId  :     包裹id
	 * @apiSuccess {String} code     取件码
	 * @apiSuccess {String} expWaybillNum : 运单号
	 * @apiSuccess {String} comment   标记备注
	 * @apiSuccess {String} pacelWeight : 包裹重量
	 * @apiSuccess {String} parcelType : 物品类型
	 * @apiSuccess {String} serviceName : 服务产品
	 * @apiSuccess {String} flag   0 ：未揽收，1已揽收
	 * @apiSuccessExample Success-Response:		               
		{
		    "data": {
		     	"flag":  0
		        "listMap": [
		            {
		               "addresseeAddress": "北京朝阳区",
		                "addresseeMobile": "13588899999",
		                "addresseeName": "哈哈哈",
						"expWaybillNum": "70567200043203",
						"netName": "AAE全球专递",
						"sendAddress": "北京市海淀区花园北路",
						"sendMobile": "13888899999",
						"sendName": "艾玛",
						"code": "234",
						"comment": "易燃",
						"parcelId": 253922753454081
		            },
		            {
					    "addresseeAddress": "北京昌平区",
					    "addresseeMobile": "13720083479",
					    "addresseeName": "",
					    "expWaybillNum": "",
					    "netName": "百世汇通",
					    "sendAddress": "北京市海淀区花园北路14号",
					    "sendMobile": "13681296635",
					    "sendName": "港货会",
					    "parcelId": 253906336948225
		            }
		        ]
		    },
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
	@RequestMapping(value = "/queryPackageMobile", method = {RequestMethod.POST, RequestMethod.GET })
	public String queryPackageMobile(String memberId, String netId,String netName,String sendMobile){
		
		return packageService.queryPackageMobile(memberId,sendMobile,netId,netName);
	}
	
		
	/**
	 * @author song
	 * @api {post} /receivePackage/takeReceivePackageByMembers 添加取件（或添加订单中包裹）--新取件-揽收
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员或者代收点添加取件(揽收  拍照)
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 人员memberId(不能为空)
	 * @apiParam {String} roleId   角色id
	 * @apiParam {String} compId   站点或代理点id
	 * @apiParam {String} sendName 发件人name
	 * @apiParam {String} sendPhone 发件人手机号(不能为空)
	 * @apiParam {String} sendAddress 发件人地址(不能为空)
	 * @apiParam {String} netId 网络Id
	 * @apiParam {String} netName 网络名称
	 * @apiParam {String} addresseeName  收件人姓名
	 * @apiParam {String} addresseePhone  收件人电话(不能为空)
	 * @apiParam {String} addresseeAddress 收件人地址(不能为空)
	 * @apiParam {String} expWaybillNum   运单号  (不能为空)
	 * @apiParam {String} code     取件码     
	 * @apiParam {String} deliveryAddress    当前交付地址     
	 * @apiParam {String} comment   标记备注
	 * @apiParam {String} pacelWeight   包裹重量
	 * @apiParam {String} parcelType   物品类型
	 * @apiParam {String} serviceName   服务产品
	 * @apiParam {Long} parcelId  包裹parcelId（标示是否订单包裹）  
	 * @apiSampleRequest /receivePackage/takeReceivePackageByMembers
	 * @apiSuccess {String} data: 返回的是 001:代表成功, 002:代表失败，003：运单号已存在;  004:取件码已存在
	 * @apiSuccessExample Success-Response:
	   {
		    "data": "001",
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
	@RequestMapping(value = "/takeReceivePackageByMembers", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageByMembers(String memberId, String sendName,String sendPhone, String sendAddress, String deliveryAddress,
			String netId, String addresseeName, String addresseeAddress,String addresseePhone,String netName,String roleId,
			String expWaybillNum ,String code,String comment,Long parcelId,String compId,String pacelWeight,String parcelType,String serviceName){
		
		String result = packageService.takeReceivePackageByMembers(memberId, sendName, sendPhone, sendAddress, deliveryAddress,
				netId, addresseeName, addresseeAddress,addresseePhone,netName,roleId ,expWaybillNum,code,comment,parcelId,compId,
				pacelWeight, parcelType,serviceName);
		return result;
	}
	/**
	 * @author song
	 * @api {post} /receivePackage/content 标记备注  --揽收-待派标记
	 * @apiVersion 0.2.0
	 * @apiDescription 标记备注 揽收-标记
	 * @apiGroup 新版-取件V4	
	 * @apiParam {Long} netId   网络Id
	 * @apiParam {Integer} signType  标记类型 1：取件   2：派件
	 * @apiSampleRequest /receivePackage/content
	 * @apiSuccessExample Success-Response:
	  {
	    "data": [
	        "易燃易爆物品",
	        "易碎物品",
	        "化学物品"
	    ],
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *     {
		    "errCode": 0,
		    "errSubcode": "001",
		    "message": "invalid params",
		    "success": false
			}
	 */	
	@ResponseBody
	@RequestMapping(value = "/content", method = {RequestMethod.POST, RequestMethod.GET })
	public String markContent(Long netId,Integer signType){	
		String result = packageService.markContent(netId,signType);
		return result;
	}
	
	
	/**
	 * @author song
	 * @api {post} /receivePackage/serviceType 服务类型--揽收
	 * @apiVersion 0.2.0
	 * @apiDescription 服务类型 -揽收
	 * @apiGroup 新版-取件V4	
	 * @apiParam {Long} netId   网络Id
	 * @apiSampleRequest /receivePackage/serviceType
	 * @apiSuccessExample Success-Response:
	  {
	    "data": [
	        "当日达",
	        "今日达",
	        "次日达"
	    ],
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *     {
		    "errCode": 0,
		    "errSubcode": "001",
		    "message": "invalid params",
		    "success": false
			}
	 */	
	@ResponseBody
	@RequestMapping(value = "/serviceType", method = {RequestMethod.POST, RequestMethod.GET })
	public String serviceType(Long netId){	
		String result = packageService.serviceType(netId);
		return result;
	}
	
	/**
	 * @author panke.sun
	 * @api {post} /receivePackage/takeReceivePackageDetailInfo 查询订单详情---new
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员从取件订单确认取件查询包裹详细信息
	 * @apiGroup 订单
	 * @apiParam {String} taskId 任务taskId(不能为空)
	 * @apiParam {String} memberId 人员memberId(不能为空)
	 * @apiSampleRequest /receivePackage/takeReceivePackageDetailInfo
	 * @apiSuccess {String} contactAddress: 寄件人地址
	 * @apiSuccess {String} contactMobile: 寄件人手机号
	 * @apiSuccess {String} contactName: 寄件人姓名
	 * @apiSuccess {String} netId: 网络id
	 * @apiSuccess {String} parcelNetName: 包裹的网络名称
	 * @apiSuccess {String} NetName: 快递员的网络名称
	 * @apiSuccess {String} parEstimateCount: 包裹预估数 
	 * @apiSuccess {String} addresseeAddress: 收件人地址
	 * @apiSuccess {String} addresseeMobile: 收件人手机
	 * @apiSuccess {String} addresseeName: 收件人姓名
	 * @apiSuccess {String} chareWeightFortransit: 包裹重量
	 * @apiSuccess {String} expWaybillNum: 运单号
	 * @apiSuccess {String} parcelId: 包裹id
	 * @apiSuccess {String} taskId: 任务id
	 * @apiSuccess {Date} createTime: 包裹创建时间
	 * @apiSuccess {String} isMark: 是否标记
	 * @apiSuccess {String} isFinished: false  //是否完善 true:完善,  false为未完善.
	 * @apiSuccessExample Success-Response:
	   {
	    "data": {
	        "contactAddress": "北京",
	        "contactMobile": "18640016307",
	        "contactName": "哈哈",
	        "listMap": [
	            {
	                "addresseeAddress": "aa",
	                "addresseeMobile": "13460892567",
	                "addresseeName": "aa",
	                "expWaybillNum": "aa",
	                "parcelId": 216423240900608,
	                "createTime": 1464964229391,
	                "isMark":"true",
	                "isFinished": "false",
	                "parcelNetName": "百世汇通",
	                
	            }
	        ],
	        "netId": 1503,
	        "netName": "百世汇通",
	        "parEstimateCount": 1,
	        "taskId": 216421888212992
	    },
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
	@RequestMapping(value = "/takeReceivePackageDetailInfo", method = {RequestMethod.POST, RequestMethod.GET })
	public String takeReceivePackageDetailInfo(String taskId, Long memberId){
		logger.info("查询订单详情---new=========taskId:"+taskId+",memberid:"+memberId);
		String result = packageService.takeReceivePackageDetailInfo(taskId, memberId);
		logger.info("查询订单详情返回的数据:"+result);
		return result;
	}
	/**
	 * @author song
	 * @api {post} /receivePackage/queryCompMemberByMemberId 站点下所有成员--取件 2017-1-14
	 * @apiVersion 0.2.0
	 * @apiDescription 站点下所有成员（本站全部成员）
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)  {站长可以是下拉选  其它收派员}
	 * @apiSampleRequest /receivePackage/queryCompMemberByMemberId
	 * @apiSuccess {Long} compId:  站点id
	 * @apiSuccess {Long} memberId : 用户id
	 * @apiSuccess {String} memberName :  姓名
	 * @apiSuccess {String} memberPhone : 电话
	 * @apiSuccess {short} roleId :   角色id
	 * @apiSuccessExample Success-Response:		               
	 [
	     {
	        "compId": 257127478509568,
	        "memberId": 255553268137984,
	        "memberName": "海海",
	        "memberPhone": "13535350000",
	        "roleId": 1
	      },
	      {
	        "compId": 257127478509568,
	        "memberId": 257125899354112,
	        "memberName": "实施",
	        "memberPhone": "13636360000",
	        "roleId": 0
	      }
     ]
          
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	
	@ResponseBody
	@RequestMapping(value = "/queryCompMemberByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompMemberByMemberId(String memberId){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		 String result = packageService.queryCompMemberByMemberId(memberId);
		return jsonSuccess(result);
	}

	/**
	 * @author song
	 * @api {post} /receivePackage/queryNewTakePackList 查询取件订单-new
	 * @apiVersion 0.2.0
	 * @apiDescription 查询待取包裹列表-new
	 * @apiGroup 订单
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {String} currentPage 当前页(不能为空)
	 * @apiParam {String} pageSize 每页显示多少条(不能为空)
	 * @apiSampleRequest /receivePackage/queryNewTakePackList
	 * @apiSuccess {String} contactAddress:  联系人地址
	 * @apiSuccess {String} contactMobile : 联系人手机号
	 * @apiSuccess {String} createTime :  创建时间(格式为时间戳,请自行转化)
	 * @apiSuccess {String} tagFalg : 1: 标记, 0 或者 空都是未标记
	 * @apiSuccess {Long} taskId : 任务id
	 * @apiSuccess {Integer} packNum : 包裹数量
	 * @apiSuccessExample Success-Response:
		{
		"data": {
		"currentPage": 1,
		"hasFirst": false,
		"hasLast": false,
		"hasNext": false,
		"hasPre": false,
		"items": [
			{
			"contactAddress": "北京市海淀区花园北路14号",
			"contactMobile": "15810885277",
			"createTime": 1481522480656,
			"tagFalg": "",
			"taskId": 251173250867200,
			"packNum":2
			},
			{
			"contactAddress": "北京市海淀区花园北路14号",
			"contactMobile": "1580885211",
			"createTime": 1481378883337,
			"tagFalg": "",
			"taskId": 250872106131456,
			"packNum":2
			}
		],
		"offset": 0,
		"pageCount": 1,
		"pageSize": 20,
		"rows": [],
		"total": 5
		},
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
	@RequestMapping(value = "/queryNewTakePackList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNewTakePackList(String memberId, String currentPage, String pageSize){
		logger.info("查询待取包裹列表-new;=======memberId:"+memberId+",currentPage:"+currentPage+",pageSize"+pageSize);
		String result = packageService.queryNewTakePackList(memberId, currentPage, pageSize);
		logger.info("查询待取包裹列表-new====="+result);
		return result;
	}

	/**
	 * @author zj
	 * @api {post} /receivePackage/addNewReceiveOrder 收派员自己添加取件订单-new
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员自己添加取件订单-new
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {String} senderName 寄件人姓名
	 * @apiParam {String} senderPhone 寄件人手机号(不能为空)
	 * @apiParam {String} addressDetail 详细地址(不能为空)
	 * @apiParam {String} packNum 包裹数量
	 * @apiParam {String} tagContent 标记内容
	 * @apiSampleRequest /receivePackage/addNewReceiveOrder
	 * @apiSuccess data : 001: 成功, 002 :失败
	 * @apiSuccessExample Success-Response:
		{"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/addNewReceiveOrder", method = {RequestMethod.POST, RequestMethod.GET })
	public String addNewReceiveOrder(String memberId, String senderName, String senderPhone, String addressDetail, Integer packNum, String tagContent){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(senderPhone)){
			return paramsFailure("002", "senderPhone 不能为空!!!");
		}
		if(PubMethod.isEmpty(addressDetail)){
			return paramsFailure("003", "addressDetail 不能为空!!!");
		}
		return packageService.addNewReceiveOrder(memberId, senderName, senderPhone, addressDetail, packNum, tagContent);
	}

	/**
	 * @author song
	 * @api {post} /receivePackage/tagOrder 标记订单-new
	 * @apiVersion 0.2.0
	 * @apiDescription 标记订单-new
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {Long} taskId 任务id(不能为空)
	 * @apiParam {String} tagContent 标记原因(如果标记多条,要按照这个格式传内容,例如: 1: 易碎,2:太膨胀,3:太丑)
	 * @apiSampleRequest /receivePackage/tagOrder
	 * @apiSuccess {String} data:  001: 成功, 002:失败
	 * @apiSuccessExample Success-Response:
		{"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/tagOrder", method = {RequestMethod.POST, RequestMethod.GET })
	public String tagOrder(String memberId, Long taskId, String tagContent){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(taskId)){
			return paramsFailure("002", "taskId 不能为空!!!");
		}
		if(PubMethod.isEmpty(tagContent)){
			return paramsFailure("003", "tagContent 不能为空!!!");
		}
		return packageService.tagOrder(memberId, taskId, tagContent);
	}


	/**
	 * @author song
	 * @api {post} /receivePackage/operationOrderStatus 取消订单-new
	 * @apiVersion 0.2.0
	 * @apiDescription 订单取消-new
	 * @apiGroup 订单
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {Long} taskId 任务id(不能为空)
	 * @apiParam {String} ancelReason 取消原因
	 * @apiSampleRequest /receivePackage/operationOrderStatus
	 * @apiSuccess {String} data:  001: 成功, 002:失败
	 * @apiSuccessExample Success-Response:
		{"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
    @ResponseBody
    @RequestMapping(value = "/operationOrderStatus", method = {RequestMethod.POST, RequestMethod.GET })
	public String operationOrderStatus(String memberId, Long taskId, String ancelReason){

		String result = packageService.operationOrderStatus(memberId, taskId,  ancelReason);
		return result;
	}  
    /**
	 * @author song
	 * @api {post} /receivePackage/finishOrder 结束订单-new
	 * @apiVersion 0.2.0
	 * @apiDescription 结束订单-new
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {Long} taskId 任务id(不能为空)
	 * @apiParam {String} parcelIds 包裹id
	 * @apiParam {String} sendName 发件人name
	 * @apiParam {String} sendPhone 发件人手机号 
	 * @apiParam {String} sendAddress 发件人地址
	 * @apiParam {String} netId 网络Id
	 * @apiParam {String} addresseeName  收件人姓名
	 * @apiParam {String} addresseePhone  收件人电话 
	 * @apiParam {String} addresseeAddress 收件人地址 
	 * @apiParam {String} expWaybillNum   运单号   
	 * @apiParam {String} marker   大头笔   
	 * @apiParam {String} bourn    目的地    省市
	 * @apiParam {String} code     取件码
	 * @apiParam {String} comment   标记备注
	 * @apiSampleRequest /receivePackage/finishOrder
	 * @apiSuccess {String} data:  001: 成功, 002:失败
	 * @apiSuccessExample Success-Response:
		{"data":"001","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/finishOrder", method = {RequestMethod.POST, RequestMethod.GET })
	public String finishOrder(String memberId, Long taskId, String parcelIds, String sendName,
			String sendPhone, String sendAddress, String netId,
			String addresseeName, String addresseePhone,String addresseeAddress, 
			String expWaybillNum,String marker,String bourn,String code,String comment){

	    if(PubMethod.isEmpty(memberId)){
	        return paramsFailure("001" , " memberId 不能为空!!!");
        }
        if(PubMethod.isEmpty(taskId)){
	        return paramsFailure("002" , " taskId 不能为空!!!");
        }
        String result = packageService.finishOrder(memberId, taskId, parcelIds, sendName,
				 sendPhone,  sendAddress, netId,
				addresseeName,  addresseePhone, addresseeAddress, 
				expWaybillNum,marker, bourn, code, comment);
		return jsonSuccess(result);
	} 
	/**
	 * @author panke.sun
	 * @api {post} /receivePackage/checkParcelDetail 查询包裹详情-new
	 * @apiVersion 0.2.0
	 * @apiDescription 查询订单详情-new
	 * @apiGroup 订单
	 * @apiParam {Long} netId 网络id
	 * @apiParam {String} contactAddress 发件人地址
	 * @apiParam {String} netName 网络名称
	 * @apiParam {Long} parcelId 包裹Id
	 * @apiParam {String} contactName  收件人姓名
	 * @apiParam {String} contactMobile  收件人电话 
	 * @apiSampleRequest /receivePackage/checkParcelDetail
	 * @apiSuccessExample Success-Response:
		{
	    "data": {
	        "addresseeAddress": "北京市海淀区 ",
	        "addresseeMobile": "13477577800",
	        "addresseeName": "",
	        "code": "",
	        "contactAddress": "",
	        "contactMobile": "",
	        "contactName": "",
	        "expWaybillNum": "",
	        "netId": "",
	        "netName": "",
	        "parcelId": 216448081682434
	    },
	    "success": true
	}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"001",
	 *	     "message":"parcelId 不能为空!!!"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/checkParcelDetail", method = {RequestMethod.POST, RequestMethod.GET })
	public String checkParcelDetail(Long parcelId,String contactAddress,Long netId,String netName,String contactMobile,String contactName){
		logger.info("查询包裹详情-new=======parcelId:"+parcelId+",contactAddress:"+contactAddress+",netId:"+netId+",netName:"+netName+",contactMobile:"+contactMobile+",contactName:"+contactName);
	    if(PubMethod.isEmpty(parcelId)){
	        return paramsFailure("001" , " parcelId 不能为空!!!");
        }
        String result = packageService.checkParcelDetail(parcelId, contactAddress, netId, netName, contactMobile, contactName);
		logger.info("查询包裹详情返回的结果:"+result);
        return result;
	}  
	
	/**
	 * @author panke.sun
	 * @api {post} /receivePackage/saveParcelInfo 保存包裹详情信息-new
	 * @apiVersion 0.2.0
	 * @apiDescription 保存包裹详情信息-new
	 * @apiGroup 订单
	 * @apiParam {String} mark 标记备注
	 * @apiParam {Long} parcelId 包裹id(不能为空)
	 * @apiParam {String} contactAddress 发件人地址
	 * @apiParam {Long} netId 网络id'
	 * @apiParam {String} netName 网络名称
	 * @apiParam {String} contactMobile 发件人手机
	 * @apiParam {String} contactName  发件人姓名
	 * @apiParam {String} expWaybillNum  运单号 
	 * @apiParam {String} code 取件标号
	 * @apiParam {String} addresseeName   收件人名称   
	 * @apiParam {String} addresseeAddress   收件人地址  
	 * @apiParam {String} addresseePhone    收件人手机
	 * @apiSampleRequest /receivePackage/saveParcelInfo
	 * @apiSuccess {String} data:  001: 成功, 002:失败
	 * @apiSuccessExample Success-Response:
	  {
	     	"data": "001",
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
	@RequestMapping(value = "/saveParcelInfo", method = {RequestMethod.POST, RequestMethod.GET })
	public String saveParcelInfo(String mark,Long parcelId,String contactAddress,Long netId,String netName,String contactMobile,String contactName,String expWaybillNum,String code,String addresseeName,String addresseeAddress,String addresseePhone){
		logger.info("保存包裹信息==new=========参数是:mark:"+mark+",parcelid:"+parcelId+"contactAddress:"+contactAddress+",netId:"+netId+",netName:"+netName+",contactMobile:"+contactMobile+",contactName:"+contactName+",expWaybillNum:"+expWaybillNum+",code:"+code+",addresseeName:"+addresseeName+",addresseeAddress"+addresseeAddress+",addresseePhone:"+addresseePhone);
	    if(PubMethod.isEmpty(parcelId)){
	        return paramsFailure("001" , " parcelId 不能为空!!!");
        }
       String result = packageService.saveParcelInfo(mark, parcelId, contactAddress, netId, netName, contactMobile, contactName, expWaybillNum, code, addresseeName, addresseeAddress, addresseePhone);
       logger.info("保存包裹信息返回的结果:"+result);
       return result;//001 成功;002 失败;
	}  
	/**
	 * @author panke.sun
	 * @api {post} /receivePackage/confirmTakeParcel  确认取件-new
	 * @apiVersion 0.2.0
	 * @apiDescription 确认取件-new
	 * @apiGroup 订单
	 * @apiParam {String} uids 包裹id(不能为空)  格式是:111-111-111
	 * @apiParam {String} taskId 订单id(不能为空)
	 * @apiParam {Long} memberId 快递员id(不能为空)
	 * @apiParam {String} flag flag=1时，说明是订单列表直接签收：此时uids 传空
	 * @apiSampleRequest /receivePackage/confirmTakeParcel
	 * @apiSuccess {String} data 001  成功   002  失败
	 * @apiSuccessExample Success-Response:
	  {
     	"data": "001",
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
    @RequestMapping(value = "/confirmTakeParcel", method = { RequestMethod.POST, RequestMethod.GET })
    public String confirmTakeParcel(String uids,Long memberId,String taskId,String flag){
        logger.info("订单中查询包裹详情:uids: " + uids);
       if (!"1".equals(flag)){
    	   if(PubMethod.isEmpty(uids)){
    		   return paramsFailure("001", "uids 不能为空!!!");
    	   }
       }
        if(PubMethod.isEmpty(taskId)){
        	return paramsFailure("001", "taskId 不能为空!!!");
        }
        if(PubMethod.isEmpty(memberId)){
        	return paramsFailure("001", "memberId 不能为空!!!");
        }
    	String result=packageService.confirmTakeParcel(uids,memberId,taskId,flag);
    	logger.info("订单中确认取件返回的结果:"+result);
    	return jsonSuccess(result);
        
    }
    /**
	 * @author panke.sun
	 * @api {post} /receivePackage/checkPidIsUnique  运单号唯一验证=new
	 * @apiVersion 0.2.0
	 * @apiDescription 运单号唯一验证==new
	 * @apiGroup 订单
	 * @apiParam {String} expWaybillNum   运单号
	 * @apiSampleRequest /receivePackage/checkPidIsUnique
	 * @apiSuccess {String} data 001 : 没有重复的运单号  002:有重复的运单号
	 * @apiSuccessExample Success-Response:
	  {
    	"data": "001",
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
    @RequestMapping(value = "/checkPidIsUnique", method = { RequestMethod.POST, RequestMethod.GET })
    public String checkPidIsUnique(String expWaybillNum){
    	logger.info("订单中查询运单号是否唯一:expWaybillNum: " + expWaybillNum);
    	String result=packageService.checkPidIsUnique(expWaybillNum);
    	logger.info("订单中保存包裹详细信息返回的结果:"+result);
    	return jsonSuccess(result);
    	
    }
}
