package net.okdi.apiV4.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import net.okdi.apiV3.service.SmallBellService;
import net.okdi.apiV4.service.AttentionService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <p>Title: </p>
 * <p>Description:关注Controller </p>
 * <p>Company: net.okdit</p> 
 * @author jianxin.ma
 * @date 2016-5-23
 */
@Controller
@RequestMapping("/attention")
public class AttentionController extends BaseController {
	 
	private @Autowired AttentionService attentionService;
	private @Autowired SmallBellService smallBellService;
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/queryAttentionList  查询关注通知列表-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  查询关注通知列表
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiSampleRequest /attention/queryAttentionList
	 * @apiSuccess {String} attentionId  关注Id
	 * @apiSuccess {String} fromMemberId  关注列表人员memberId
	 * @apiSuccess {String} fromMemberName  关注列表人员姓名
	 * @apiSuccess {String} fromMemberUrl  关注列表人员头像
	 * @apiSuccess {String} isAttention  是否已关注对方 (0 否 1 是)
	 * @apiSuccessExample Success-Response:
	   {
		"data": [
		{
			"attentionId": 220187674869760,
			"fromMemberId": 3435341,
			"fromMemberName": "刘井超",
			"fromMemberUrl": "http://cas.okdit.net/nfs_data/mob/head/3435341.jpg",
			"isAttention": 1
		}
		],
		"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	
	@ResponseBody
	@RequestMapping(value = "/queryAttentionList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAttentionList(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryAttentionList.001", "memberId不能为空");
		}
		try {
			return attentionService.queryAttentionList(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/queryContactList  查询联系人(关注)列表-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  查询联系人(关注)列表
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiParam {Long} visitorMemberId 访问者memberId (不能为空,查看自己的传快递员自己的memberId)
	 * @apiParam {Integer} currentPage 当前页
	 * @apiParam {Integer} pageSize 每页条数
	 * @apiParam {String} searchKey 搜索关键字
	 * @apiSampleRequest /attention/queryContactList
	 * @apiSuccess {String} cardContent  最新一条动态的内容
	 * @apiSuccess {String} cardContentType  帖子类型: 0 没有最新动态 1 包含文字 2 只有图片 3 只有视频
	 * @apiSuccess {String} headPath  关注列表人员头像
	 * @apiSuccess {String} memberId  关注列表人员Id
	 * @apiSuccess {String} memberName  关注列表人员名字
	 * @apiSuccess {String} isAttention  是否关注对方(0 否 1是)--查看别人的联系人列表显示
	 * @apiSuccessExample Success-Response:
		{
			"data": [
				{
					"cardContent": "",
					"cardContentType": 2,
					"headPath": "http://cas.okdit.net/nfs_data/mob/head/4381409032185002.jpg",
					"memberId": 4381409032185002,
					"memberName": ""
					"isAttention": 1
				},
				{
					"cardContent": "",
					"cardContentType": 0,
					"headPath": "http://cas.okdit.net/nfs_data/mob/head/124149317296128.jpg",
					"memberId": 124149317296128,
					"memberName": "纪海庆"
					"isAttention": 1
				}
				],
			"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryContactList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryContactList(Long memberId,String visitorMemberId,Integer currentPage,Integer pageSize,String searchKey) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(visitorMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.002", "visitorMemberId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.003", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.004", "pageSize不能为空");
		}
		try {
			return attentionService.queryContactList(memberId,visitorMemberId,currentPage,pageSize,searchKey,(short)1);//关注的人
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/queryFansList  查询粉丝列表-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  查询粉丝列表
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiParam {Long} visitorMemberId 访问者memberId (不能为空,查看自己的传快递员自己的memberId)
	 * @apiParam {Integer} currentPage 当前页
	 * @apiParam {Integer} pageSize 每页条数
	 * @apiParam {String} searchKey 搜索关键字
	 * @apiSampleRequest /attention/queryFansList
	 * @apiSuccess {String} cardContent  最新一条动态的内容
	 * @apiSuccess {String} cardContentType  帖子类型: 0 没有最新动态 1 包含文字 2 只有图片 3 只有视频
	 * @apiSuccess {String} headPath  粉丝列表人员头像
	 * @apiSuccess {String} memberId  粉丝列表人员Id
	 * @apiSuccess {String} memberName  粉丝列表人员名字
	 * @apiSuccess {String} isAttention  是否关注对方(0 否 1是)
	 * @apiSuccessExample Success-Response:
		{
			"data": [
				{
					"cardContent": "",
					"cardContentType": 2,
					"headPath": "http://cas.okdit.net/nfs_data/mob/head/4381409032185002.jpg",
					"memberId": 4381409032185002,
					"memberName": ""
					"isAttention": 1
				},
				{
					"cardContent": "",
					"cardContentType": 0,
					"headPath": "http://cas.okdit.net/nfs_data/mob/head/124149317296128.jpg",
					"memberId": 124149317296128,
					"memberName": "纪海庆"
					"isAttention": 0
				}
				],
			"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryFansList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryFansList(Long memberId,String visitorMemberId,Integer currentPage,Integer pageSize,String searchKey) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.002", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.003", "pageSize不能为空");
		}
		try {
			return attentionService.queryContactList(memberId,visitorMemberId,currentPage,pageSize,searchKey,(short)2);//粉丝
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/queryFriendList  查询好友列表-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  查询好友列表
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiParam {Long} visitorMemberId 访问者memberId (不能为空,查看自己的传快递员自己的memberId)
	 * @apiParam {Integer} currentPage 当前页
	 * @apiParam {Integer} pageSize 每页条数
	 * @apiParam {String} searchKey 搜索关键字
	 * @apiSampleRequest /attention/queryFriendList
	 * @apiSuccess {String} cardContent  最新一条动态的内容
	 * @apiSuccess {String} cardContentType  帖子类型: 0 没有最新动态 1 包含文字 2 只有图片 3 只有视频
	 * @apiSuccess {String} headPath  好友列表人员头像
	 * @apiSuccess {String} memberId  好友列表人员Id
	 * @apiSuccess {String} memberName  好友列表人员名字
	 * @apiSuccess {String} isAttention  是否关注对方(0 否 1是)--查看别人的好友列表显示
	 * @apiSuccessExample Success-Response:
		{
			"data": [
				{
					"cardContent": "",
					"cardContentType": 2,
					"headPath": "http://cas.okdit.net/nfs_data/mob/head/4381409032185002.jpg",
					"memberId": 4381409032185002,
					"memberName": ""
					"isAttention": 1
				},
				{
					"cardContent": "",
					"cardContentType": 0,
					"headPath": "http://cas.okdit.net/nfs_data/mob/head/124149317296128.jpg",
					"memberId": 124149317296128,
					"memberName": "纪海庆"
					"isAttention": 1
				}
				],
			"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryFriendList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryFriendList(Long memberId,String visitorMemberId,Integer currentPage,Integer pageSize,String searchKey) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(visitorMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.002", "visitorMemberId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.003", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.004", "pageSize不能为空");
		}
		try {
			return attentionService.queryContactList(memberId,visitorMemberId,currentPage,pageSize,searchKey,(short)3);//好友
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/deleteAttention  删除关注通知-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription 删除关注通知
	 * @apiGroup 快递圈
	 * @apiParam {Long} attentionId 关注Id
	 * @apiSampleRequest /attention/deleteAttention
	 * @apiSuccessExample Success-Response:
		{
			"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"attentionId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAttention", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteAttention(Long attentionId) {
		if(PubMethod.isEmpty(attentionId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.deleteAttention.001", "attentionId不能为空");
		}
		try {
			return attentionService.deleteAttention(attentionId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/chooseContactList  创建群选择联系人列表-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  创建群选择联系人列表
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiParam {Long} communityId 群Id(创建群时选择联系人传 -1)
	 * @apiParam {Integer} currentPage 当前页
	 * @apiParam {Integer} pageSize 每页条数
	 * @apiSampleRequest /attention/chooseContactList
	 * @apiSuccess {String} cardContent  最新一条动态的内容
	 * @apiSuccess {String} cardContentType  帖子类型: 0 没有最新动态 1 包含文字 2 只有图片 3 只有视频
	 * @apiSuccess {String} contains  该联系人是否加入这个群 0 否 1 是
	 * @apiSuccess {String} headPath  关注列表人员头像
	 * @apiSuccess {String} memberId  关注列表人员Id
	 * @apiSuccess {String} memberName  关注列表人员名字
	 * @apiSuccess {String} firstLetter  关注列表人员名字首字母
	 * @apiSuccessExample Success-Response:
		{
		  "data": {
		    "#": [
		      {
		        "cardContent": "", 
		        "cardContentType": 2, 
		        "contains": 0, 
		        "headPath": "http://cas.okdit.net/nfs_data/mob/head/4381409032185002.jpg", 
		        "memberId": 4381409032185002, 
		        "memberName": ""
		        "firstLetter": "#"
		      }
		    ], 
		    "j": [
		      {
		        "cardContent": "", 
		        "cardContentType": 0, 
		        "contains": 0, 
		        "headPath": "http://cas.okdit.net/nfs_data/mob/head/124149317296128.jpg", 
		        "memberId": 124149317296128, 
		        "memberName": "纪海庆"
		         "firstLetter": "j"
		      }
		    ], 
		    "z": [
		      {
		        "cardContent": "", 
		        "cardContentType": 0, 
		        "contains": 0, 
		        "headPath": "http://cas.okdit.net/nfs_data/mob/head/124172325150720.jpg", 
		        "memberId": 124172325150720, 
		        "memberName": "张文"
		        "firstLetter": "z"
		      }
		    ]
		  }, 
		  "success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/chooseContactList", method = { RequestMethod.POST, RequestMethod.GET })
	public String chooseContactList(Long memberId,Long communityId,Integer currentPage,Integer pageSize) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.chooseContactList.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(communityId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.chooseContactList.002", "communityId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.chooseContactList.003", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.chooseContactList.004", "pageSize不能为空");
		}
		try {
			return attentionService.chooseContactList(memberId,communityId,currentPage,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/cancelAttention  取消关注-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  取消关注
	 * @apiGroup 快递圈
	 * @apiParam {Long} fromMemberId 关注者(快递员自己)memberId
	 * @apiParam {Long} toMemberId 被关注者(被关注的人)memberId
	 * @apiSampleRequest /attention/cancelAttention
	 * @apiSuccessExample Success-Response:
		{
		"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"fromMemberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelAttention", method = { RequestMethod.POST, RequestMethod.GET })
	public String cancelAttention(Long fromMemberId, Long toMemberId) {
		if(PubMethod.isEmpty(fromMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.cancelAttention.001", "fromMemberId不能为空");
		}
		if(PubMethod.isEmpty(toMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.cancelAttention.002", "toMemberId不能为空");
		}
		try {
			return attentionService.cancelAttention(fromMemberId,toMemberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * @author jianxin.ma
	 * @api {post} /attention/queryDynamicNotice  查询动态通知列表-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  查询动态通知列表
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiParam {Integer} currentPage 当前页
	 * @apiParam {Integer} pageSize 每页条数
	 * @apiSampleRequest /attention/queryDynamicNotice
	 * @apiSuccess {String} baseUrl 拼接头像地址用
	 * @apiSuccess {String} cardId 帖子Id
	 * @apiSuccess {String} content  帖子内容
	 * @apiSuccess {String} createTime  帖子创建时间
	 * @apiSuccess {String} createorMemberId 帖子创建人id  
	 * @apiSuccess {String} imageUrl 帖子图片url
	 * @apiSuccess {String} readCount 阅读数量
	 * @apiSuccess {String} type 帖子类型  1单独的文字,2单独图,3单独小视频 4 文字+图片组合 5文字+小视频组合
	 * @apiSuccess {String} upCount 点赞数量
	 * @apiSuccess {String} videoPictureUrl 视频图片的url
	 * @apiSuccess {String} videoUrl 视频url
	 * @apiSuccess {String} createorMemberName  帖子创建人名字
	 * @apiSuccess {String} commentId  评论Id
	 * @apiSuccess {String} comment  评论内容
	 * @apiSuccess {String} dynamicTime 帖子评论(点赞)时间
	 * @apiSuccess {String} fromMemberHeadUrl 评论(点赞)人头像
	 * @apiSuccess {String} fromMemberId  评论(点赞)人id
	 * @apiSuccess {String} fromMemberName  评论(点赞)人姓名
	 * @apiSuccess {String} noticeType 动态通知类型 1：点赞   2：关注,3:评论 ,4:关注人发帖
	 * @apiSuccess {String} oldComment 原评论或回复(只有在回复评论时不为空)
	 * @apiSuccess {String} oldContent 原评论内容
	 * @apiSuccess {String} oldFromMemberId 原评论人memberId
	 * @apiSuccess {String} oldFromMemberName 原评论人名字
	 * @apiSuccess {String} oldToMemberId 原被评论人memberId
	 * @apiSuccess {String} oldToMemberName 原被评论人名字
	 * @apiSuccess {String} toMemberId 本次被评论人memberId(当oldComment不为空时这个参数取oldFromMemberId的值)
	 * @apiSuccess {String} toMemberName 本次被评论人名字(当oldComment不为空时这个参数取oldFromMemberName的值)
	 * @apiSuccessExample Success-Response:
		{
		  "data": [
		    {
		      "baseUrl": "http://cas.okdit.net/nfs_data/mob/head/", 
		      "card": {
		        "cardId": 220225109024768, 
		        "content": "我是帖子内容", 
		        "createTime": "2016-06-24 18:47:27", 
		        "createorMemberId": 126487725662208, 
		        "imageUrl": [ ], 
		        "readCount": 14, 
		        "status": "0", 
		        "type": "1", 
		        "upCount": 1, 
		        "videoPictureUrl": "", 
		        "videoUrl": ""
		      }, 
		      "createorMemberName": "", 
		      "comment": "", 
		      "commentId": 220225109024998, 
		      "dynamicTime": "2016-06-25 10:26:14", 
		      "fromMemberHeadUrl": "http://cas.okdit.net/nfs_data/mob/head/117991649107968.jpg", 
		      "fromMemberId": 117991649107968, 
		      "fromMemberName": "", 
		      "noticeType": 3, 
		      "oldComment": [
		        {
		          "oldContent": "我是评论内容哦", 
		          "oldFromMemberId": 117648219496448, 
		          "oldFromMemberName": "那谁", 
		          "oldToMemberId": 126487725662208, 
		          "oldToMemberName": "乐乐"
		        }
		      ], 
		      "toMemberId": "", 
		      "toMemberName": ""
		    }, 
		    {
		      "baseUrl": "http://cas.okdit.net/nfs_data/mob/head/", 
		      "card": {
		        "cardId": 220225109024768, 
		        "content": "我是帖子内容", 
		        "createTime": "2016-06-24 18:47:27", 
		        "createorMemberId": 126487725662208, 
		        "imageUrl": [ ], 
		        "readCount": 14, 
		        "status": "0", 
		        "type": "1", 
		        "upCount": 1, 
		        "videoPictureUrl": "", 
		        "videoUrl": ""
		      }, 
		      "createorMemberName": "", 
		      "comment": "我是评论内容哦", 
		      "commentId": 220225109024998, 
		      "dynamicTime": "2016-06-25 10:21:11", 
		      "fromMemberHeadUrl": "http://cas.okdit.net/nfs_data/mob/head/117648219496448.jpg", 
		      "fromMemberId": 117648219496448, 
		      "fromMemberName": "那谁", 
		      "noticeType": 3, 
		      "oldComment": [ ], 
		      "toMemberId": 126487725662208, 
		      "toMemberName": "乐乐"
		    }
		  ], 
		  "success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDynamicNotice", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryDynamicNotice(Long memberId, Integer currentPage, Integer pageSize) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryDynamicNotice.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryDynamicNotice.002", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryDynamicNotice.003", "pageSize不能为空");
		}
		try {
			return attentionService.queryDynamicNotice(memberId,currentPage,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/leaderboard  查询排行榜-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  查询排行榜
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiParam {Short} tagType 标签类型 1.派件数 2.接单数 3.外快 (赚钱榜)4.省钱榜
	 * @apiParam {Integer} currentPage 当前页
	 * @apiParam {Integer} pageSize 每页条数
	 * @apiParam {Short} filter 排序筛选条件 1 当天 2 本周 3 当月
	 * @apiSampleRequest /attention/leaderboard
	 * @apiSuccess {String} currentPage 当前页
	 * @apiSuccess {String} compName 公司名
	 * @apiSuccess {String} count 取(派)件/赚外快数/省钱总金额
	 * @apiSuccess {String} wechatCount 微信省钱条数(只有tagType传4时返回该字段)
	 * @apiSuccess {String} headImgPath 头像地址
	 * @apiSuccess {String} memberId 人员Id
	 * @apiSuccess {String} memberName  人员名字
	 * @apiSuccess {String} myCompName  我的公司名
	 * @apiSuccess {String} myCount  我的取(派)件/赚外快数/省钱总金额
	 * @apiSuccess {String} myWechatCount  我的微信省钱条数(只有tagType传4时返回该字段)
	 * @apiSuccess {String} myMemberId  我的memberId
	 * @apiSuccess {String} myMemberName 我的姓名
	 * @apiSuccess {String} myheadImgPath  我的头像
	 * @apiSuccessExample Success-Response:
		{
			"data": {
			"dataList": [
				{
					"compName": "快乐哈哈",
					"count": 1,
					"headImgPath": "http://cas.okdit.net/nfs_data/mob/head/211868314812416.jpg",
					"memberId": 211868314812416,
					"memberName": "郑炯哼哼"
				}
			],
				"myself": {
				"myCompName": "",
				"myCount": 0,
				"myMemberId": 150242899337216,
				"myMemberName": "律师事务所",
				"myheadImgPath": "http://cas.okdit.net/nfs_data/mob/head/150242899337216.jpg"
				}
			},
			"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/leaderboard", method = { RequestMethod.POST, RequestMethod.GET })
	public String leaderboard(Long memberId,Short tagType, Integer currentPage, Integer pageSize,Short filter) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(tagType)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.002", "tagType不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.003", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.004", "pageSize不能为空");
		}
		if(PubMethod.isEmpty(filter)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.004", "filter不能为空");
		}
		try {
			return attentionService.leaderboard(memberId,tagType,currentPage,pageSize,filter);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/messageList  消息列表接口-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  消息列表接口
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiSampleRequest /attention/messageList
	 * @apiSuccess {String} attName 最新一条关注通知人姓名
	 * @apiSuccess {String} commnuity 群名字
	 * @apiSuccess {String} commnuityCount 未读群通知数量
	 * @apiSuccess {String} dynamicCount 未读动态通知数量 
	 * @apiSuccess {String} dynamicName 最新一条动态通知人名字
	 * @apiSuccess {String} dynamicType 最新一条动态通知类型(-1 没有查到动态通知 1点赞 3 评论 4 我关注的人发表新动态) 
	 * @apiSuccess {String} proposerName 申请加入群人名字 
	 * @apiSuccess {String} unreadAttCount 未读关注通知数量
	 * @apiSuccess {String} bellCount 提醒数量
	 * @apiSuccess {String} announceTitle 公告标题
	 * @apiSuccess {String} unreadCount 公告未读数量
	 * @apiSuccess {Integer} unReadOrderCount 抢单未读数量
	 * @apiSuccess {String} latestAddress 抢单最新地址
	 * @apiSuccessExample Success-Response:
		{
			"data": {
				"attName": "张建军",
				"commnuity": "degdlkgf",
				"commnuityCount": 2,
				"dynamicCount": 0,
				"dynamicName": "",
				"dynamicType": -1,
				"proposerName": "申请人00005",
				"unreadAttCount": 0
				"bellCount": 12
				"announceTitle": "公告标题公告标题"
				"unreadCount": 1
			},
		"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/messageList", method = { RequestMethod.POST, RequestMethod.GET })
	public String messageList(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.messageList.001", "memberId不能为空");
		}
		try {
			Map map = new HashMap<>();
			Long bellCount = smallBellService.queryCount(memberId);
			JSONObject taskRobResult = null;
            try {
                String s = smallBellService.queryUnReadTaskRob(memberId);
                taskRobResult = JSON.parseObject(s);
            } catch (Exception e) {}

            String res = attentionService.messageList(memberId);
			JSONObject jsonObject = JSONObject.parseObject(res);
			if(jsonObject.get("success").equals(true)){
				map = jsonObject.getObject("data", Map.class);
				map.put("bellCount", bellCount);
                map.put("unReadOrderCount", taskRobResult == null ? 0 : taskRobResult.getIntValue("unReadCount"));
                map.put("latestAddress", taskRobResult == null ? "" : taskRobResult.getString("latestAddress"));
            }
			return jsonSuccess(map);
			
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/searchMember  按照姓名检索加关注-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  按照姓名检索加关注
	 * @apiGroup 快递圈
	 * @apiParam {String} searchName 检索关键字(姓名)
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiSampleRequest /attention/searchMember
	 * @apiSuccess {String} headPath  结果列表人员头像
	 * @apiSuccess {String} memberId  结果列表人员Id
	 * @apiSuccess {String} memberName  结果列表人员名字
	 * @apiSuccess {String} isAttention  是否关注对方(0 否 1是)
	 * @apiSuccessExample Success-Response:
		{
			"data": [
				{
					"headPath": "http://cas.okdit.net/nfs_data/mob/head/4381409032185002.jpg",
					"memberId": 4381409032185002,
					"memberName": ""
					"isAttention": 1
				},
				{
					"headPath": "http://cas.okdit.net/nfs_data/mob/head/124149317296128.jpg",
					"memberId": 124149317296128,
					"memberName": "纪海庆"
					"isAttention": 0
				}
				],
			"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/searchMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchMember(String searchName,Long memberId) {
		if(PubMethod.isEmpty(searchName)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.searchMember.001", "searchName不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.searchMember.001", "memberId不能为空");
		}
		try {
			return attentionService.searchMember(searchName,memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /attention/queryAttCount  个人中心查询关注/粉丝/好友数量-mjx
	 * @apiVersion 0.2.0
	 * @apiDescription  个人中心查询关注/粉丝/好友数量
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员Id
	 * @apiSampleRequest /attention/queryAttCount
	 * @apiSuccess {Long} attentionCount  关注数
	 * @apiSuccess {Long} fansCount  粉丝数
	 * @apiSuccess {Long} friendCount  好友数
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "attentionCount": 5,
		        "fansCount": 5,
		        "friendCount": 6
		    },
		    "success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAttCount", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAttCount(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryAttCount.001", "memberId不能为空");
		}
		try {
			return attentionService.queryAttCount(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author penghui.song        
	 * @api {post} /attention/searchCommunity  按照群名称或者群标签检索
	 * @apiDescription  2016-12-16    新增    按照群名称或者群标签搜索
	 * @apiGroup 快递圈
	 * @apiParam {String} searchName 检索关键字(群名，  群标签)
	 * @apiParam {Long} memberId 快递员memberId
	 * @apiSampleRequest /attention/searchCommunity
	 * @apiSuccess {String} headPath  结果列表群头像
	 * @apiSuccess {String} communityId  结果列表群Id
	 * @apiSuccess {String} communityName  结果列表群名
	 * @apiSuccess {String} isJoin  是否已经加入群   (0 否 1是)
	 * @apiSuccess {String} memberNum 群成员数量
	 * @apiSuccessExample Success-Response:
		{
		 "data": [
		   {
            "communityId": 251692467544064,
            "communityName": "玉田",
            "headPath": "http://publicapi.okdit.net/nfs_data/complogo/251692467544064.jpg",
            "isJoin": 0
            "memberNum": 0
          },
          {
            "communityId": 251867924070400,
            "communityName": "玉渊潭分部",
            "headPath": "http://publicapi.okdit.net/nfs_data/complogo/251867924070400.jpg",
            "isJoin": 0
            "memberNum": 0
          }
         ],
         "success": true
      }
	 * @apiErrorExample Error-Response:
	 *   {
           "errCode": 0,
           "errSubcode": "net.okdi.apiV4.controller.AttentionController.searchMember.001",
            "message": "memberId不能为空",
             "success": false
          }
       */   
	@ResponseBody
	@RequestMapping(value = "/searchCommunity", method = { RequestMethod.POST, RequestMethod.GET })
	public String searchCommunity(String searchName,Long memberId) {
		if(PubMethod.isEmpty(searchName)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.searchMember.001", "searchName不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.searchMember.001", "memberId不能为空");
		}
		try {
			return attentionService.searchCommunity(searchName,memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
}