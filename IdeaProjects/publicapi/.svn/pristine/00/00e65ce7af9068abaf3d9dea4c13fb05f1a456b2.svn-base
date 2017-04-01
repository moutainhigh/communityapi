package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.ExpressCommunityService;
import net.okdi.apiV4.vo.CommunityVO;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/community")
public class ExpressCommunityController extends BaseController{

	private @Autowired ExpressCommunityService expressCommunityService;
	
	/**
	 * @author hang.yu
	 * @api {POST} /community/create 创建群
	 * @apiVersion 0.4.0
	 * @apiDescription 创建群
	 * @apiGroup 快递圈-群
	 * @apiParam {String} communityName	群名字
	 * @apiParam {String} communityIntroduce 群介绍
	 * @apiParam {String} commlabels 群标签            2016-12-13 新需求
	 * @apiParam {String} communityProvince 群省份
	 * @apiParam {String} communityAddress  群详细地址
	 * @apiParam {Double} lng 经度
	 * @apiParam {Double} lat 经度
	 * @apiParam {Long} mid 群创建人id
	 * @apiParam {String} mname 群创建人姓名
	 * @apiParam {String} membs  群成员(json数组 [{"id": 1, "name": "张三"}, {"id": 2, "name": "李四"}])
	 * @apiParam {file} logo 群logo
	 * @apiSuccess {String} cid 群id
	 * @apiSuccess {String} logoUrl 群logoUrl
	 * 
	 * @apiSampleRequest /community/create
	 * @apiSuccessExample Success-Response:
	   {
		   "success":true
	   }
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"",
	 *    "message":"请求失败",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public String createCommunity(CommunityVO vo, MultipartFile logo) {
		
		Long mid = vo.getMid();
		String mname = vo.getMname();
		if (PubMethod.isEmpty(mid) || PubMethod.isEmpty(mname)) {
			return paramsFailure("", "创建人信息不完整");
		}
		
		if (logo == null) {
			return paramsFailure("", "logo未上传");
		}
		
		String membs = vo.getMembs();
//		if (PubMethod.isEmpty(membs)) {
//			return paramsFailure("", "请先选择群成员");
//		}
		if (!"".equals(membs)) {
			try {
				JSON.parseArray(membs);
//				if (array.size() == 0) {
//					return paramsFailure("", "请先选择群成员");
//				}
			} catch (Exception e1) {
				return paramsFailure("", "成员格式不正确");
			}
		}
		
		
		String name = vo.getCommunityName();
		if (PubMethod.isEmpty(name)) {
			return paramsFailure("", "群名称不能是空");
		}
		String communityIntroduce = vo.getCommunityIntroduce();
		if (PubMethod.isEmpty(communityIntroduce)) {
			return paramsFailure("", "群介绍不能是空");
		}
		
		String prov = vo.getCommunityProvince();
		if (PubMethod.isEmpty(prov)) {
			return paramsFailure("", "省不能是空");
		}
		
		String addr = vo.getCommunityAddress();
		System.out.println("群地址 {{{{{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}====== addr: "+addr);
		if (PubMethod.isEmpty(addr)) {
			System.out.println("判断地址是空的 addr: "+addr);
			return paramsFailure("", "地址不能为空");
		}
		Double lat = vo.getLat();
		Double lng = vo.getLng();
		if (PubMethod.isEmpty(lat) || PubMethod.isEmpty(lng)) {
			return paramsFailure("", "经纬度不能为空");
		}
		/*String label = vo.getCommlabels();
		if (PubMethod.isEmpty(label)) {
			return paramsFailure("", "标签不能为空");
		}*/
		
		return expressCommunityService.createCommunity(vo, logo);
	}
	
	/**
	 * @author hang.yu
	 * @api {GET} /community/myjoin 我加入的群
	 * @apiVersion 0.4.0
	 * @apiDescription 我加入的群
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} mid	登录人id
	 * @apiParam {Double} lng 经度
	 * @apiParam {Double} lat 经度
	 * 
	 * @apiSuccess {String} id 群id
	 * @apiSuccess {String} name 群名字
	 * @apiSuccess {Integer} memnum 群成员个数
	 * @apiSuccess {String} dis 距离 km为单位
	 * @apiSuccess {String} baseUrl 群logourl logo全路径为  baseUrl + id + ".jpg"
	 * 
	 * @apiSuccess {String} rc 001-正常响应   101-用户未注册(除系统级异常外的全局响应码)
	 * 
	 * @apiSampleRequest /community/myjoin
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
				"baseUrl" : "http://publicapi.okdit.net/nfs_data/complogo/",
				"myjoin": [
					{
						"id" : 11,
						"name": "群",
						"memnum": 3,
						"dis": 0.23
					},
					{
						"id" : 22,
						"name": "群",
						"memnum": 29,
						"dis": 1.45
					},
				]
			},
			"success": true
		}
	* @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",		--响应码 101
				"err": "用户未注册",	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
				"baseUrl" : "http://publicapi.okdit.net/nfs_data/complogo/",
				"myjoin": [
					
				]
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/myjoin", method = {RequestMethod.POST, RequestMethod.GET})
	public String myJoinCommunity(Long mid, Double lng, Double lat) {
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		if (lng == null) {
			return paramsFailure("", "经度不能为空");
		}
		if (lat == null) {
			return paramsFailure("", "纬度不能为空");
		}
		return expressCommunityService.myJoinCommunity(mid, lng, lat);
	}
	
	/**
	 * @author hang.yu
	 * @api {GET} /community/near 附近群
	 * @apiVersion 0.4.0
	 * @apiDescription 附近群
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} mid	登录人id
	 * @apiParam {Double} lng 经度
	 * @apiParam {Double} lat 经度
	 * @apiParam {Integer} pn 每页个数
	 * @apiParam {Integer} p 当前页
	 * @apiParam {Integer} index 0: 过滤已加入 1: 全部状态
	 * 
	 * @apiSuccess {String} id 群id
	 * @apiSuccess {String} name 群名字
	 * @apiSuccess {Integer} num 群成员个数
	 * @apiSuccess {String} dis 距离 km为单位
	 * @apiSuccess {Integer} in 是否加入群  0:未加入  1:已加入  2:申请中
	 * @apiSuccess {String} baseUrl 群logourl logo全路径为  baseUrl + id + ".jpg"
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/near
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
				"baseUrl" : "http://publicapi.okdit.net/nfs_data/complogo/",
				"near": [
					{
						"id" : 11,
						"name": "群",
						"num": 3,
						"dis": 0.23,
						"in": 0
					},
					{
						"id" : 22,
						"name": "群",
						"num": 29,
						"dis": 1.45,
						"in": 1
					},
				]
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/near", method = {RequestMethod.POST, RequestMethod.GET})
	public String nearCommunity(Long mid, Double lng, Double lat, 
			@RequestParam("pn") Integer pageSize, @RequestParam("p") Integer currPage, Integer index) {
		
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		if (PubMethod.isEmpty(lng)) {
			return paramsFailure("", "经度不能为空");
		}
		if (PubMethod.isEmpty(lat)) {
			return paramsFailure("", "纬度不能为空");
		}
		if (PubMethod.isEmpty(pageSize) || PubMethod.isEmpty(currPage)) {
			return paramsFailure("", "分页信息不正确");
		}
		if (PubMethod.isEmpty(index)) {
			return paramsFailure("", "标识不能为空");
		}
		return expressCommunityService.nearCommunity(mid, lng, lat, pageSize, currPage, index);
	}
	
	/**
	 * @author hang.yu
	 * @api {GET} /community/detail 群主页
	 * @apiVersion 0.4.0
	 * @apiDescription 群主页
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} id 群id
	 * @apiParam {Long} mid	登录人id
	 * 
	 * @apiSuccess {Integer} in 是否在群  0否1是 2申请中
	 * @apiSuccess {Integer} cm 是否是管理员  0否1是
	 * @apiSuccess {Integer} num 群成员个数
	 * @apiSuccess {Integer} top 是否置顶  0否1是
	 * @apiSuccess {Integer} interru 是否消息免打扰  0否1是
	 * @apiSuccess {String} commUrl 群logourl
	 * @apiSuccess {String} headUrl 成员头像url
	 * @apiSuccess {Long} updateTime 群最后修改时间
	 * 
	 * 
	 * @apiSuccess {String} rc 001-正常响应   101-用户未注册(除系统级异常外的全局响应码)
	 * 
	 * @apiSampleRequest /community/detail
	 * @apiSuccessExample Success-Response: 正常响应
		{
	    	"data":{
		        "cm":1,
		        "commUrl":"http://publicapi.okdit.net/nfs_data/complogo/",
		        "detail":{
		            "addressId":11, --省id
		            "communityAddress":"海淀区花园北路环星大厦",
		            "communityIntroduce":"群的介绍",
		            "communityName":"快递圈测试",
		            "communityNotice":"",		--群公告
		            "communityProvince":"北京",
		            "communityTown":"海淀区",
		            "createTime":1465961290898,
		            "creator":4381409032185002,
		            "creatorName":"admin",
		            "id":218539070062592,	--群id
		            "iden":1,				--0系统默认群 1用户创建的群
		            "loc":[
		                116.384237,
		                39.985334
		            ],
		            "master":{	--群管理员信息			
		                "mid":159514953588736,
		                "mname":"docker",
		                "updateTime":1465961290898
		            },
		            "memberNum":1,	--群成员个数
		            "members":[		--群成员
		                {
		                    "communityId":218539070062592,
		                    "ism":1,					--是否是管理员
		                    "joinTime":1465961290898,	--加入时间
		                    "mid":159514953588736,		--成员id
		                    "mname":"docker"			--成员姓名
		                }
		            ],
		            "stat":0	--默认状态
		        },
		        "err":"",	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
		        "headUrl":"http://publicapi.okdit.net/nfs_data/mob/",
		        "in":1,
		        "interru":0,
		        "num":1,
		        "rc":"001",
		        "top":0
	    	},
	    	"success":true
		}
	 * @apiErrorExample Error-Response:
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"201",
	 *    	"message":"群不存在",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = {RequestMethod.POST, RequestMethod.GET})
	public String communityDetail(Long id, Long mid) {
		if (PubMethod.isEmpty(id)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		return expressCommunityService.communityDetail(id, mid);
	}
	
	/**
	 * @author hang.yu
	 * @api {GET} /community/memlist 群成员列表
	 * @apiVersion 0.4.0
	 * @apiDescription 群成员列表
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} id 群id
	 * @apiParam {Integer} p 当前页
	 * @apiParam {Integer} index 0:过滤群主, 1:包括群主
	 * 
	 * @apiSuccess {String} id 群id
	 * @apiSuccess {String} name 群名字
	 * @apiSuccess {Integer} memnum 群成员个数
	 * @apiSuccess {String} dis 距离 km为单位
	 * @apiSuccess {String} headUrl 成员头像url
	 * 
	 * @apiSuccess {String} rc 001-正常响应 
	 * 
	 * @apiSampleRequest /community/memlist
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
				"baseUrl" : "http://publicapi.okdit.net/nfs_data/complogo/",
				"memlist": [
					{
						"mid" : 11,			--成员id
						"mname": "xx",		--成员姓名
						"joinTime": xxx,	--加入时间
						"isMaster": 1,		--是否是管理员 0否1是
						"communityId": xxx  --群id
					}
				]
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"201",
	 *    	"message":"群不存在",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/memlist", method = {RequestMethod.POST, RequestMethod.GET})
	public String communityMemberList(Long id, @RequestParam("p") Integer currPage, Integer index) {
		if (PubMethod.isEmpty(id)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(currPage)) {
			return paramsFailure("", "分页信息不正确");
		}
		return expressCommunityService.communityMemberList(id, currPage, index);
	}
	
	/**
	 * @author hang.yu
	 * @api {POST} /community/join/apply 申请加入群
	 * @apiVersion 0.4.0
	 * @apiDescription 申请加入群
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} id 群id
	 * @apiParam {Long} mid 登录人id
	 * 
	 * @apiSuccess {String} rc 001-正常响应   101-用户未注册
	 * 
	 * @apiSampleRequest /community/join/apply
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",	--响应码
				"err": "用户未注册"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"201",
	 *    	"message":"群不存在",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/join/apply", method = {RequestMethod.POST, RequestMethod.GET})
	public String applyJoinCommunity(Long id, Long mid) {
		if (PubMethod.isEmpty(id)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		return expressCommunityService.applyJoinCommunity(id, mid);
	}
	
	/**
	 * @author hang.yu
	 * @api {POST} /community/join/agree 同意加入群
	 * @apiVersion 0.4.0
	 * @apiDescription 同意加入群
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 管理员id, 也就是处理这个申请的用户id
	 * @apiParam {Long} reqId 申请人id
	 * 
	 * @apiSuccess {String} rc 001-正常响应   303-没有加群申请  304-只有群主才可以处理申请
	 * 
	 * @apiSampleRequest /community/join/agree
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",	--响应码
				"err": "用户未注册"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "301",		--响应码
				"err": "已经在群中"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "303",	--响应码
				"err": "没有申请"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "304",				--响应码
				"err": "只有群主才可以处理申请"		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"201",
	 *    	"message":"群不存在",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/join/agree", method = {RequestMethod.POST, RequestMethod.GET})
	public String handleJoinCommunity(Long cid, Long mid, Long reqId) {
		if (PubMethod.isEmpty(cid)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		if (PubMethod.isEmpty(reqId)) {
			return paramsFailure("", "申请人不能为空");
		}
		return expressCommunityService.handleJoinCommunityReq(cid, mid, reqId);
	}
	
	
	
	/**
	 * @author erlong.pei
	 * @api {POST} /community/top 设置群置顶
	 * @apiVersion 0.4.0
	 * @apiDescription 设置群置顶聊天
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 登录人id
	 * @apiParam {Integer} isTop 是否置顶
	 * @apiParam {Long} ts 最后修改时间
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/top
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",	--响应码
				"err": "用户未注册"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "204",		--响应码
				"err": "用户不在群中"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "002",		--响应码
				"err": "请求失败"		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"201",
	 *    	"message":"群不存在",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/top", method = {RequestMethod.POST, RequestMethod.GET})
	public String topCommunity(Long cid, Long mid, int isTop){
		if (PubMethod.isEmpty(cid)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		if (PubMethod.isEmpty(isTop)){
			return paramsFailure("", "置顶条件不能为空");
		}
		return expressCommunityService.topCommunity(cid, mid, isTop);
	}
	
	/**
	 * @author erlong.pei
	 * @api {POST} /community/inter 设置群消息免打扰
	 * @apiVersion 0.4.0
	 * @apiDescription 设置群消息免打扰
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 登录人id
	 * @apiParam {Integer} isInter 是否免打扰  0否1是
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/inter
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",	--响应码
				"err": "用户未注册"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"201",
	 *    	"message":"群不存在",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/inter", method = {RequestMethod.POST, RequestMethod.GET})
	public String interruptCommunity(Long cid, Long mid, int isInter) {
		if (PubMethod.isEmpty(cid)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		if (PubMethod.isEmpty(isInter)){
			return paramsFailure("", "免打扰条件不能为空");
		}
		return expressCommunityService.interruptCommunity(cid, mid, isInter);
	}
	
	/**
	 * @author hang.yu
	 * @api {POST} /community/notice-name 设置群公告或者重命名
	 * @apiVersion 0.4.0
	 * @apiDescription 设置群公告或者重命名
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 登录人id
	 * @apiParam {String} noticeOrName 群的公告或者群新名字或者群介绍
	 * @apiParam {Integer} type 1:设置公告  2:重命名 3:群介绍
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/notice-name
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "202",			--响应码
				"err": "只有群主可以设置公告"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "208",			--响应码
				"err": "只有群主可以重命名群"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "209",			--响应码
				"err": "只有群主可以编辑群介绍"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"201",
	 *    	"message":"群不存在",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/notice-name", method = {RequestMethod.POST, RequestMethod.GET})
	public String setNoticeOrRename(Long cid, Long mid, String noticeOrName, Integer type) {
		System.out.println("进入到publicapi中了, 设置群公告和冲重命名或者介绍noticeOrName: "+noticeOrName +" ==type:  "+type);
		if (PubMethod.isEmpty(cid)) {
			System.out.println("==设置群公告和冲重命名或者介绍===========cid=============");
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			System.out.println("===设置群公告和冲重命名或者介绍==========mid=============");
			return paramsFailure("", "用户id不能为空");
		}
		if(2 == type||3 == type){//重命名不能空
			System.out.println("2 或者 3 == type=====设置群重命名或者介绍不能为空===============");
			if (PubMethod.isEmpty(noticeOrName)) {
				return paramsFailure("", "群介绍和公告不能为空!!!");
			}
		}
		if (PubMethod.isEmpty(type)) {
			System.out.println(" ====设置群公告和冲重命名或者介绍=========type====================");
			return paramsFailure("", "参数不正确");
		}
		if (!"123".contains(String.valueOf(type))) {
			System.out.println(" =======设置群公告和冲重命名或者介绍 ======type=====!123===============");
			return paramsFailure("", "参数不正确");
		}
		return expressCommunityService.setNoticeOrRename(mid, cid, noticeOrName, type);
	}
	
	/**
	 * @author erlong.pei
	 * @api {POST} /community/change 群主管理权转让
	 * @apiVersion 0.4.0
	 * @apiDescription 群管理员更换
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 登录人id
	 * @apiParam {Long} newMasterId 新群主id
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/change
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "203",	--响应码
				"err": "只有群主可以转让管理权"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "207",	--响应码
				"err": "管理权不能转让给自己"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "204",	--响应码
				"err": "用户不在群中"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"201",
	 *    	"message":"群不存在",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/change", method = {RequestMethod.POST, RequestMethod.GET})
	public String changeMaster(Long cid, Long mid,Long newMasterId){
		if (PubMethod.isEmpty(cid)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		if (PubMethod.isEmpty(newMasterId)) {
			return paramsFailure("", "新群主id不能为空");
		}
		return expressCommunityService.changeMaster(cid, mid, newMasterId);
	}
	
	/**
	 * @author erlong.pei
	 * @api {POST} /community/trunclog 清空聊天记录
	 * @apiVersion 0.4.0
	 * @apiDescription 群或联系人聊天记录清空
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} mid 登录人id
	 * @apiParam {Long} targetId 群id或者联系人id
	 * @apiParam {Integer} op 1:联系人聊天记录 2:群聊天记录
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/trunclog
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",	--响应码
				"err": "用户未注册"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"002",
	 *    	"message":"系统繁忙  请稍后再试",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/trunclog", method = {RequestMethod.POST, RequestMethod.GET})
	public String truncLog(Long mid, Long targetId, Integer op){
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		if (PubMethod.isEmpty(targetId)) {
			return paramsFailure("", "群id或联系人id不能为空");
		}
		if (PubMethod.isEmpty(op)) {
			return paramsFailure("", "op标识不能为空");
		}
		return expressCommunityService.truncLog(mid, targetId, op);
	}

	/**
	 * @author erlong.pei
	 * @api {POST} /community/quit 退出群
	 * @apiVersion 0.4.0
	 * @apiDescription 退出群
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 登录人id
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/quit
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"002",
	 *    	"message":"系统繁忙  请稍后再试",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/quit", method = {RequestMethod.POST, RequestMethod.GET})
	public String quitCommunity(Long cid, Long mid){
		if (PubMethod.isEmpty(cid)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		return expressCommunityService.quitCommunity(cid, mid);
	}
	
	/**
	 * @author erlong.pei
	 * @api {POST} /community/interruptcontact 设置联系人消息免打扰
	 * @apiVersion 0.4.0
	 * @apiDescription 设置联系人消息免打扰
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} contactId 目标联系人id
	 * @apiParam {Long} mid 用户id
	 * @apiParam {int} isInter 是否免打扰 0: 否, 1: 是
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/interruptcontact
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",	--响应码
				"err": "用户未注册"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
		{
			"data" : {
				"rc": "102",	--响应码
				"err": "置顶联系人时，联系人不存在"	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"002",
	 *    	"message":"系统繁忙  请稍后再试",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/interruptcontact", method = {RequestMethod.POST, RequestMethod.GET})
	public String interruptContact(Long contactId, Long mid, int isInter){
		if (PubMethod.isEmpty(contactId)) {
			return paramsFailure("", "目标联系人id不能为空");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(isInter)) {
			return paramsFailure("", "参数不能为空");
		}
		return expressCommunityService.interruptContact(contactId, mid, isInter);
	}

	/**
	 * @author erlong.pei
	 * @api {POST} /community/join/reqlist 群通知列表
	 * @apiVersion 0.4.0
	 * @apiDescription 群通知列表
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} master 群主id
	 * @apiParam {Integer} currPage 当前页
	 * 
	 * @apiSuccess {String} baseUrl	头像url
	 * @apiSuccess {String} commMaster 群管理员id
	 * @apiSuccess {String} commnuity 群名字
	 * @apiSuccess {String} communityId 群id
	 * @apiSuccess {String} proposeTime 申请时间
	 * @apiSuccess {String} proposer 申请人id
	 * @apiSuccess {String} proposerName 申请人姓名
	 * @apiSuccess {String} status 状态  0:申请中
	 * 
	 * @apiSampleRequest /community/join/reqlist
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"baseUrl": "http://publicapi.okdit.net/nfs_data/mob/",
        		"reqList": [
					{
	    				"commMaster": 159514953588736,
	    				"commnuity": "快递圈测试1",
	    				"communityId": 218539221057536,
	    				"proposeTime": 1466671459948,
	    				"proposer": 3435341,
	    				"proposerName": "测试",
	    				"status": 0
					}
				]
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"002",
	 *    	"message":"系统繁忙  请稍后再试",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/join/reqlist", method = {RequestMethod.POST, RequestMethod.GET})
	public String joinCommunityReqList(Long master, int currPage){
		if (PubMethod.isEmpty(master)) {
			return paramsFailure("", "群主id不能为空");
		}
		if (PubMethod.isEmpty(currPage)) {
			return paramsFailure("", "当前页不能为空");
		}
		return expressCommunityService.joinCommunityReqList(master, currPage);
	}
	
	/**
	 * @author jiong.zheng
	 * @api {GET} /community/join/del 删除加群申请
	 * @apiVersion 0.4.0
	 * @apiDescription 删除加群申请
	 * @apiGroup 快递圈-群
	 * @apiParam {String} communId	群id
	 * @apiParam {String} masterId	管理员id
	 * @apiParam {String} reqId	申请人id
	 * 
	 * @apiSuccess {String}  str:返回参数还没加,等有数据在添加
	 * 
	 * @apiSampleRequest /community/join/del
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
				"baseUrl" : "http://publicapi.okdit.net/nfs_data/complogo/",
				"myjoin": [
					{
						"id" : 11,
						"name": "群",
						"memnum": 3,
						"dis": 0.23
					},
					{
						"id" : 22,
						"name": "群",
						"memnum": 29,
						"dis": 1.45
					},
				]
			},
			"success": true
		}
	* @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",		--响应码 101
				"err": "用户未注册",	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
				"baseUrl" : "http://publicapi.okdit.net/nfs_data/complogo/",
				"myjoin": [
					
				]
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/join/del", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteJoinCommunityReq(String communId, String masterId, String reqId) {
		
		return expressCommunityService.deleteJoinCommunityReq(communId, masterId, reqId);
	}
	
	
	/**
	 * @author jiong.zheng
	 * @api {GET} /community/topContast 设置联系人置顶
	 * @apiVersion 0.4.0
	 * @apiDescription 设置联系人置顶
	 * @apiGroup 快递圈-群
	 * @apiParam {String} contactId 联系人id
	 * @apiParam {String} mid 操作人id
	 * @apiParam {Integer} isTop 0置顶 1取消置顶
	 * @apiParam {Long} ts 最后修改时间
	 * 
	 * @apiSuccess {String} rc 001-正常响应   101-用户未注册(除系统级异常外的全局响应码)
	 * 
	 * @apiSampleRequest /community/topContast
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiSuccessExample Success-Response: 业务异常
		{
			"data" : {
				"rc": "101",		--响应码 101
				"err": "用户未注册",	--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
				"baseUrl" : "http://publicapi.okdit.net/nfs_data/complogo/",
				"myjoin": [
					
				]
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/topContast", method = {RequestMethod.POST, RequestMethod.GET})
	public String topContast(String contactId, String mid, int isTop) {
		if (PubMethod.isEmpty(contactId)) {
			return paramsFailure("", "联系人信息不正确");
		}
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户信息不正确");
		}
		if (PubMethod.isEmpty(isTop)) {
			return paramsFailure("", "参数不正确");
		}
		return expressCommunityService.topContast(contactId, mid, isTop);
	}
	/**
	 * @author jiong.zheng
	 * @api {GET} /community/chatSetting 单聊设置页面--是否置顶聊天是否消息免打扰
	 * @apiVersion 0.4.0
	 * @apiDescription 单聊设置页面--是否置顶聊天是否消息免打扰
	 * @apiGroup 快递圈-群
	 * @apiParam {String} mid 操作人memberId
	 * @apiParam {String} targetId 群id或者联系人id
	 * 
	 * @apiSuccess {Integer} top 是否置顶  0否1是
	 * @apiSuccess {Integer} interru 是否消息免打扰  0否1是
	 * @apiSuccess {Long} modifyTime 最后置顶时间(时间戳)
	 * 
	 * @apiSampleRequest /community/chatSetting
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": "",		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
				"top" : 1,
				"interru": 0
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/chatSetting", method = {RequestMethod.POST, RequestMethod.GET})
	public String chatSetting(String mid, String targetId) {
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户信息不完整");
		}
		if (PubMethod.isEmpty(targetId)) {
			return paramsFailure("", "联系人信息不完整");
		}
		return expressCommunityService.chatSetting(mid, targetId);
	}
	
	/**
	 * @author erlong.pei
	 * @api {POST} /community/addmember 群主页添加成员
	 * @apiVersion 0.4.0
	 * @apiDescription 群主页添加成员
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {String} mjson 选择的联系人  json数组  [{"id": 1, "name": "xx"}, {"id": 2, "name": "xx"}]
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/addmember
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/addmember", method = {RequestMethod.POST, RequestMethod.GET})
	public String addMember(Long cid, String mjson){
		if (PubMethod.isEmpty(cid)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(mjson)) {
			return paramsFailure("", "用户信息不能为空");
		}
		return expressCommunityService.addMember(cid, mjson);
	}
	
	
	/**
	 * @author erlong.pei
	 * @api {POST} /community/searchmember 群主转让界面搜索群成员
	 * @apiVersion 0.4.0
	 * @apiDescription 群主转让界面搜索群成员
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {String} name 搜索关键字
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/searchmember
	 * @apiSuccessExample Success-Response: 正常响应
		{
		    "data": {
		        "err": "",
		        "headUrl": "http://cas.okdit.net/nfs_data/mob/head/",
		        "rc": "001",
		        "searchCommunityMember": [
		            {
		                "communityId": 218539221057536,
		                "ism": 0,
		                "joinTime": 1465961362937,
		                "mid": 212192209453056,
		                "mname": "禹航",
		            }
		        ]
		    },
		    "success": true
		}
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/searchmember", method = {RequestMethod.POST, RequestMethod.GET})
	public String searchCommunityMember(Long cid, String name){
		if (PubMethod.isEmpty(cid)) {
			return paramsFailure("", "群id不能为空");
		}
		if (PubMethod.isEmpty(name)) {
			return paramsFailure("", "搜索条件不能为空");
		}
		return expressCommunityService.searchCommunityMember(cid, name);
	}

	/**
	 * @author erlong.pei
	 * @api {POST} /community/removeCommunityMember 群主删除群成员
	 * @apiVersion 0.4.0
	 * @apiDescription 群主删除群成员
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} masterId 群主id
	 * @apiParam {Long} reqId 成员id
	 *
	 * @apiSuccess {String} rc 001-正常响应
	 *
	 * @apiSampleRequest /community/removeCommunityMember
	 * @apiSuccessExample Success-Response: 正常响应
    {
    "data" : {
    "rc": "001",	--响应码 001代表正常响应
    "err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
    },
    "success": true
    }
	 * @apiErrorExample Error-Response:
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/removeCommunityMember", method = {RequestMethod.POST, RequestMethod.GET})
	public String removeCommunityMember(Long cid, Long masterId, Long reqId){
        if (PubMethod.isEmpty(cid)){
            return paramsFailure("001", "群id不能为空");
        }
        if (PubMethod.isEmpty(masterId)){
            return paramsFailure("002", "群主id不能为空");
        }
        if (PubMethod.isEmpty(reqId)){
            return paramsFailure("003", "被删除人id不能为空");
        }
		return expressCommunityService.removeCommunityMember(cid, masterId, reqId);
	}
	
	/**
	 * @author song
	 * @api {GET,POST} /community/queryCommLabelList 查询群标签
	 * @apiDescription 设置群标签
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 登录人id
	 * 
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/queryCommLabelList
	 * @apiSuccessExample Success-Response: 正常响应
	 {
		 "data": {
	     "commLabelList": [
			    "玉田",
			    "青春"
			 ],
		      "err": "",
			  "rc": "001"
		    },
		 "success": true
		}
			 
	  @apiErrorExample Error-Response: 
	     {
	    	"errCode":0,
	     	"errSubcode":"201",
	     	"message":"群不存在",
	    	"success":false
	     }
	 */
	
	@ResponseBody
	@RequestMapping(value = "/queryCommLabelList", method = {RequestMethod.POST, RequestMethod.GET})
	public String queryCommLabelList(Long cid, Long mid){
        if (PubMethod.isEmpty(cid)){
            return paramsFailure("001", "群cid不能为空");
        }
        if (PubMethod.isEmpty(mid)){
            return paramsFailure("002", "群主id不能为空");
        }
		return expressCommunityService.queryCommLabelList(cid, mid);
	}
	
	/**
	 * @author song
	 * @api {POST，GET} /community/insertCommLabelList 群主添加标签
	 * @apiDescription 群主页添加标签
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 登录人id
	 * @apiParam {String} labels 标签
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/insertCommLabelList
	 * @apiSuccessExample Success-Response: 正常响应
		{
			"data" : {
				"rc": "001",	--响应码 001代表正常响应
				"err": ""		--错误信息 rc为001时是空字符串, 其他rc值 err不是空串
			},
			"success": true
		}
			
		{
          "data": {
             "err": "只有群主可以编辑",
              "rc": "001"
           },
           "success": true
        }
        
        {
          "data": {
             "err": "群标签已经存在",
             "rc": "001"
         },
           "success": true
        }
        
	 * @apiErrorExample Error-Response: 
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 *    
	 *    {
           "errCode": 0,
           "errSubcode": "",
            "message": "群cid不能为空",
            "success": false
           }
	 */	
	@ResponseBody
	@RequestMapping(value = "/insertCommLabelList", method = {RequestMethod.POST, RequestMethod.GET})
	public String insertCommLabelList(Long cid, Long mid,String labels){
        if (PubMethod.isEmpty(cid)){
            return paramsFailure("", "群cid不能为空");
        }
        if (PubMethod.isEmpty(mid)){
            return paramsFailure("", "群主id不能为空");
        }
        if (PubMethod.isEmpty(labels)) {
			return paramsFailure("", "群标签不能为空");
		}
		return expressCommunityService.insertCommLabelList(cid, mid,labels);
	}
	
	/**
	 * @author song
	 * @api {POST，GET} /community/deleteCommLabelList 群主删除标签
	 * @apiDescription 群主页删除标签
	 * @apiGroup 快递圈-群
	 * @apiParam {Long} cid 群id
	 * @apiParam {Long} mid 登录人id
	 * @apiParam {String} labels 标签
	 * @apiSuccess {String} rc 001-正常响应
	 * 
	 * @apiSampleRequest /community/deleteCommLabelList
	 * @apiSuccessExample Success-Response: 正常响应
		{
          "data": {
             "err": "",
              "rc": "001"
            },
          "success": true
       }
        
	 * @apiErrorExample Error-Response: 
	 * 
	 * {
         "data": {
            "err": "只有群主可以编辑",
            "rc": "001"
          },
          "success": true
        }
	 * 
	 * {
          "errCode": 0,
          "errSubCode": "201",
          "message": "群不存在或已解散",
           "success": false
        }
	 *    
	 *    {
           "errCode": 0,
           "errSubcode": "",
            "message": "群cid不能为空",
            "success": false
           }
	 */	
	
	@ResponseBody
	@RequestMapping(value = "/deleteCommLabelList", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteCommLabelList(Long cid, Long mid,String labels){
        if (PubMethod.isEmpty(cid)){
            return paramsFailure("", "群cid不能为空");
        }
        if (PubMethod.isEmpty(mid)){
            return paramsFailure("", "群主id不能为空");
        }
        if (PubMethod.isEmpty(labels)) {
			return paramsFailure("", "群标签不能为空");
		}
		return expressCommunityService.deleteCommLabelList(cid, mid,labels);
	}
	
}

















