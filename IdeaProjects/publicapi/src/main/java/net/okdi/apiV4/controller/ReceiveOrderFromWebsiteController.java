package net.okdi.apiV4.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import net.okdi.apiV4.service.ReceiveOrderFromWebsiteService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/receiveOrderFromWebsite")
public class ReceiveOrderFromWebsiteController extends BaseController{

	private static Logger logger = Logger.getLogger(ReceiveOrderFromWebsiteController.class);
	@Autowired
	private ReceiveOrderFromWebsiteService receiveOrderFromWebsiteService;
	
	
	
	
	

	
	/**调用国通的接口查询包裹
	 * @param userCode
	 * @param orgCode
	 * @param memberId
	 * @param NetId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTakePackListFromWebsite", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakePackListFromWebsite(String userCode,String orgCode,String memberId,String NetId){
		logger.info("查询取件订单(电商件)-new;=======userCode:"+userCode+",orgCode"+orgCode);
		String result = receiveOrderFromWebsiteService.queryTakePackListFromWebsite(userCode,orgCode,memberId,NetId);
		logger.info("查询取件订单(电商件)-new====="+result);
		return result;
	}
	
	
	
	/**保存国通推送过来的包裹(电商件)
	 * @param userCode
	 * @param orgCode
	 * @return
	 */
	/**
	 * @author panke.sun
	 * @api {post} /receiveOrderFromWebsite/saveParcelFromGT 保存国通推送过来的包裹(电商件)
	 * @apiVersion 0.2.0
	 * @apiDescription 保存国通推送过来的包裹(电商件)
	 * @apiGroup 订单
	 * @apiParam {String} parcels 国通推送过来的包裹信息
	 * @apiParam {String} memberId 快递员的memberid
	 * @apiParam {String} netId 快递员的netid
	 * @apiSampleRequest /receiveOrderFromWebsite/saveParcelFromGT
	 * @apiSuccess {String} data:  001: 成功, 002:失败
	 * @apiSuccessExample Success-Response:
		{"data":   ,
		"success": true
		}
	 */
	@ResponseBody
	@RequestMapping(value = "/saveParcelFromGT", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveParcelFromGT(String parcels,String memberId, String netId){
		logger.info("保存包裹(电商件国通);=======parcels:"+parcels+",memberId:"+memberId+",netId:"+netId);
		String result=receiveOrderFromWebsiteService.saveParcelFromGT(parcels,memberId,netId);
		return result;
	}
	
	
	
	/**
	 * @author panke.sun
	 * @api {post} /receiveOrderFromWebsite/QueryParcelFromGT 查询电商件检列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询电商件检列表
	 * @apiGroup 订单
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {String} netId 收派员netId(不能为空)
	 * @apiParam {String} currentPage 当前页(不能为空)
	 * @apiParam {String} pageSize 每页显示多少条(不能为空)
	 * @apiSampleRequest /receiveOrderFromWebsite/QueryParcelFromGT 
	 * @apiSuccess {String} sendMobile:  联系人地址
	 * @apiSuccess {String} sendAddress : 联系人手机号
	 * @apiSuccess {String} createTime :  创建时间(格式为时间戳,请自行转化)
	 * @apiSuccess {String} uid : 包裹id
	 * @apiSuccess {Integer} packNum : 包裹数量
	 * @apiSuccessExample Success-Response:
		{
		
		"data": [
			{
			"sendAddress": "北京市海淀区花园北路14号",
			"sendMobile": "15810885277",
			"createTime": 1481522480656,
			"taskId": 251173250867200,
			
			},
			{
			"contactAddress": "北京市海淀区花园北路14号",
			"contactMobile": "1580885211",
			"createTime": 1481378883337,
			"tagFalg": "",
			"taskId": 250872106131456,
			
			}
		],		
		"packNum": 2
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
	@RequestMapping(value = "/QueryParcelFromGT", method = { RequestMethod.POST, RequestMethod.GET })
	public String QueryParcelFromGT(String memberId,String netId,String currentPage, String pageSize){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("002", "currentPage 不能为空!!!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("003", "pageSize 不能为空!!!");
		}
		logger.info("保存包裹(电商件国通);=======memberId:"+memberId+",currentPage:"+currentPage+",pageSize:"+pageSize);
		String result =receiveOrderFromWebsiteService.QueryParcelFromGT(Long.parseLong(memberId),Long.parseLong(netId),Integer.parseInt(currentPage),Integer.parseInt(pageSize));
		return result;
	}
	
	/**
	 * @author panke.sun
	 * @api {post} /receiveOrderFromWebsite/confirmTakeParcelGT  电商件==取件
	 * @apiVersion 0.2.0
	 * @apiDescription 电商件==取件
	 * @apiGroup 订单
	 * @apiParam {String} uids 包裹id(不能为空)  格式是:111-111-111
	 * @apiParam {String} netId 网络id
	 * @apiParam {String} memberId 快递员id(不能为空)
	 * @apiParam {String} terminalId 设备号
	 * @apiParam {String} versionId 版本号
	 * @apiSampleRequest /receiveOrderFromWebsite/confirmTakeParcelGT
	 * @apiSuccess {String} 001  成功
	 * @apiSuccessExample Success-Response:
	 * {
     *	"success": true
	 *}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmTakeParcelGT", method = { RequestMethod.POST, RequestMethod.GET })
	public String confirmTakeParcelGT(String uids,String memberId,String netId,String terminalId,String versionId){
		if(PubMethod.isEmpty(uids)){
			return paramsFailure("001", "uids 不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("002", "memberId 不能为空!!!");
		}
		logger.info("保存包裹(电商件国通);=======memberId:"+memberId+",uids:"+uids+",netId:"+netId+",terminalId:"+terminalId+",versionId:"+versionId);
		String result =receiveOrderFromWebsiteService.confirmTakeParcelGT(uids,memberId,netId,terminalId,versionId);
		return result;
	}
	/**
	 * @author panke.sun
	 * @api {post} /receiveOrderFromWebsite/huntParcelGT 订单(电商件和散件)中搜索当前包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 查询待取包裹列表-new
	 * @apiGroup 订单
	 * @apiParam {String} memberId 收派员memberId(不能为空)
	 * @apiParam {String} netId 收派员netId(不能为空)
	 * @apiParam {String} currentPage 当前页(不能为空)
	 * @apiParam {String} pageSize 每页显示多少条(不能为空)
	 * @apiParam {String} flag  允许值: null 或者 1  //若为空,则为揽散件中的搜索包裹. 若为1,则为电商件中的搜索包裹
	 * @apiParam {String} mobile 搜索的手机号(不能为空)
	 * @apiSampleRequest /receiveOrderFromWebsite/huntParcelGT
	 * @apiSuccess {String} sendMobile:  联系人地址
	 * @apiSuccess {String} sendAddress : 联系人手机号
	 * @apiSuccess {String} createTime :  创建时间(格式为时间戳,请自行转化)
	 * @apiSuccess {String} uid : 包裹id
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
			"sendAddress": "北京市海淀区花园北路14号",
			"sendMobile": "15810885277",
			"createTime": 1481522480656,
			"uid": 251173250867200,			
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
		*/
	@ResponseBody
	@RequestMapping(value = "/huntParcelGT", method = { RequestMethod.POST, RequestMethod.GET })
	public String huntParcelGT(String mobile,String memberId,String netId,String currentPage, String pageSize,String flag){
		logger.info("订单(电商件)中搜索当前包裹(电商件国通);=======mobile:"+mobile+",memberId:"+memberId);
		String result =receiveOrderFromWebsiteService.huntParcelGT(mobile,Long.parseLong(memberId),Long.parseLong(netId),Integer.parseInt(currentPage),Integer.parseInt(pageSize),flag);
		return result;
	}
}
