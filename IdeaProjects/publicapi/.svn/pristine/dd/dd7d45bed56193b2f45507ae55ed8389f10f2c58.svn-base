package net.okdi.apiExt.controller;

import net.okdi.apiExt.service.ExtCompInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("extCompInfo")
public class ExtCompInfoController extends BaseController{

	@Autowired
	private ExtCompInfoService extCompInfoService;
	
	/**
	 * @author 贺海峰
	 * @api {post} /extCompInfo/queryCompInfoByAddressAndRange 查询该地址附近的制定范围内的站点
	 * @apiVersion 0.2.0
	 * @apiDescription 查询该地址附近的制定范围内的站点 -贺海峰
	 * @apiGroup For电商管家
	 * @apiParam {String} sendAddress 发货地址
	 * @apiParam {String} range 搜索范围 1 2 单位km
	 * @apiSampleRequest /extCompInfo/queryCompInfoByAddressAndRange
	 * @apiSuccess {String} compAddress  站点地址
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} compTelephone  站点电话
	 * @apiSuccess {String} compTypeNum  站点类型  1006站点 1050营业分部
	 * @apiSuccess {String} distance  距离 单位m
	 * @apiSuccess {String} netId  快递公司id
	 * @apiSuccess {String} netName  快递公司名称
	 * @apiSuccessExample Success-Response:
		{
		    "data": [
		        {
		            "compAddress": "北京-海淀区|花园北路14号计算机一厂",
		            "compId": "121435492499456",
		            "compName": "零时站点",
		            "compTelephone": "15633355555",
		            "compTypeNum": "1006",
		            "distance": 15,
		            "netId": 1503,
		            "netName": "百世汇通"
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
	@RequestMapping(value = "/queryCompInfoByAddressAndRange", method = { RequestMethod.POST, RequestMethod.GET })
	 public String queryCompInfoByAddressAndRange(String sendAddress,String range){
		if(PubMethod.isEmpty(sendAddress)){
			return PubMethod.paramError("publicapi.ExtCompInfoController.queryCompInfoByAddressAndRange.001", "查询条件异常");
		}
		if(PubMethod.isEmpty(range)){
			return PubMethod.paramError("publicapi.ExtCompInfoController.queryCompInfoByAddressAndRange.002", "查询条件异常");
		}
		return extCompInfoService.queryCompInfoByAddressAndRange(sendAddress,range);
	}
	/**
	 * @author 贺海峰
	 * @api {post} /extCompInfo/queryMemberInfoByCompId 查询该站点下的收派员列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询该站点下的收派员列表 -贺海峰
	 * @apiGroup For电商管家
	 * @apiParam {String} compId 站点id
	 * @apiSampleRequest /extCompInfo/queryMemberInfoByCompId
	 * @apiSuccess {String} memberId  收派员id
	 * @apiSuccess {String} memberName  收派员姓名
	 * @apiSuccess {String} memberPhone  站收派员手机号
	 * @apiSuccessExample Success-Response:
		{
		    "data": [
		        {
		            "memberId": 174688229965824,
		            "memberName": "管理员",
		            "memberPhone": "14900000818"
		        },
		        {
		            "memberId": 174703539175424,
		            "memberName": "一一",
		            "memberPhone": "14900000819"
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
	@RequestMapping(value = "/queryMemberInfoByCompId", method = { RequestMethod.POST, RequestMethod.GET })
	 public String queryMemberInfoByCompId(String compId){
		if(PubMethod.isEmpty(compId)){
			return PubMethod.paramError("publicapi.ExtCompInfoController.queryMemberInfoByCompId.001", "查询条件异常");
		}
		return extCompInfoService.queryMemberInfoByCompId(compId);
	}
	/**
	 * @author 贺海峰
	 * @api {post} /extCompInfo/queryBatchCompInfoByAddressAndRange 批量查询该地址附近的制定范围内的7天内新增站点
	 * @apiVersion 0.2.0
	 * @apiDescription 批量查询该地址附近的制定范围内的7天内新增站点 -贺海峰
	 * @apiGroup For电商管家
	 * @apiParam {String} sendAddress 发货地址 (批量的话用,分隔 例如:环星大厦A座,环星大厦B座,环星大厦C座)
	 * @apiParam {String} range 搜索范围 1 2 单位km
	 * @apiSampleRequest /extCompInfo/queryBatchCompInfoByAddressAndRange
	 * @apiSuccess {String} compAddress  站点地址
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} compTelephone  站点电话
	 * @apiSuccess {String} compTypeNum  站点类型  1006站点 1050营业分部
	 * @apiSuccess {String} distance  距离 单位m
	 * @apiSuccess {String} netId  快递公司id
	 * @apiSuccess {String} netName  快递公司名称
	 * @apiSuccessExample Success-Response:
		{
		    "data": [
		        {
		            "compInfoList": [
		               {
            "compAddress": "北京-海淀区|花园北路14号计算机一厂",
            "compId": "121435492499456",
            "compName": "零时站点",
            "compTelephone": "15633355555",
            "compTypeNum": "1006",
            "distance": 15,
            "netId": 1503,
            "netName": "百世汇通"
        }
		            ],
		            "sendAddress": "环星大厦A座"
		        },
		        {
		            "compInfoList": [],
		            "sendAddress": "环星大厦B座"
		        },
		        {
		            "compInfoList": [],
		            "sendAddress": "环星大厦C座"
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
	@RequestMapping(value = "/queryBatchCompInfoByAddressAndRange", method = { RequestMethod.POST, RequestMethod.GET })
	 public String queryBatchCompInfoByAddressAndRange(String sendAddress,String range){
		if(PubMethod.isEmpty(sendAddress)){
			return PubMethod.paramError("publicapi.ExtCompInfoController.queryBatchCompInfoByAddressAndRange.001", "查询条件异常");
		}
		if(PubMethod.isEmpty(range)){
			return PubMethod.paramError("publicapi.ExtCompInfoController.queryBatchCompInfoByAddressAndRange.002", "查询条件异常");
		}
		return extCompInfoService.queryBatchCompInfoByAddressAndRange(sendAddress,range);
	}
	
	/**
	 * @author 贺海峰
	 * @api {post} /extCompInfo/queryBatchCompInfoByAddress 查询该站点信息以及和多个发货地址的距离等信息
	 * @apiVersion 0.2.0
	 * @apiDescription 查询该站点信息以及和多个发货地址的距离等信息-贺海峰
	 * @apiGroup For电商管家
	 * @apiParam {String} sendAddress 发货地址 (批量的话用,分隔 例如:环星大厦A座,环星大厦B座,环星大厦C座)
	 * @apiParam {String} compId 搜站点id
	 * @apiSampleRequest /extCompInfo/queryBatchCompInfoByAddress
	 * @apiSuccess {String} compAddress  站点地址
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} compTelephone  站点电话
	 * @apiSuccess {String} compTypeNum  站点类型  1006站点 1050营业分部
	 * @apiSuccess {String} distance  距离 单位m
	 * @apiSuccess {String} netId  快递公司id
	 * @apiSuccess {String} netName  快递公司名称
	 * @apiSuccessExample Success-Response:
			{
			    "data": {
			        "compAddress": "北京昌平区城区",
			        "compId": "174688232062976",
			        "compName": "啦啦啦撸",
			        "compTelephone": "14900000818",
			        "listResult": [
			            {
			                "compId": 174688232062976,
			                "compName": "啦啦啦撸",
			                "distance": 6296900,
			                "sendAddress": "环星大厦A座"
			            },
			            {
			                "compId": 174688232062976,
			                "compName": "啦啦啦撸",
			                "distance": 6296900,
			                "sendAddress": "环星大厦B座"
			            },
			            {
			                "compId": 174688232062976,
			                "compName": "啦啦啦撸",
			                "distance": 6296900,
			                "sendAddress": "环星大厦C座"
			            }
			        ],
			        "netId": 999,
			        "netName": "EMS速递"
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
	@RequestMapping(value = "/queryBatchCompInfoByAddress", method = { RequestMethod.POST, RequestMethod.GET })
	 public String queryBatchCompInfoByAddress(String sendAddress,String compId){
		if(PubMethod.isEmpty(sendAddress)){
			return PubMethod.paramError("publicapi.ExtCompInfoController.queryBatchCompInfoByAddress.001", "查询条件异常");
		}
		if(PubMethod.isEmpty(compId)){
			return PubMethod.paramError("publicapi.ExtCompInfoController.queryBatchCompInfoByAddress.003", "查询条件异常");
		}
		return extCompInfoService.queryBatchCompInfoByAddress(sendAddress,compId);
	}
}
