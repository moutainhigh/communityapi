package net.okdi.mob.controller;

import net.okdi.core.base.BaseController;
import net.okdi.mob.service.ShopMemberPushSetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/shopMemberPushSet")
public class ShopMemberPushSetController extends BaseController {
	
	@Autowired
	ShopMemberPushSetService shopMemberPushSetService;

    /**
     * @api {post} /shopMemberPushSet/pushStatus 设置(修改抢单模式) caina.sun
     * @apiPermission user
     * @apiDescription 接口描述  设置(修改抢单模式)  
     * @apiparam {Long} memberId  人员ID
     * @apiparam {Short} pushStatus 状态
     * @apiGroup  社区配送
     * @apiSampleRequest /shopMemberPushSet/pushStatus
     * @apiSuccess {String} result true
     * @apiError {String} result false
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *   {"success":true}
     *
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 200 OK
     *   {"errCode":0,"errSubcode":"openapi.shopMemberPushSet.pushStatus","message":"memberId不能为空","success":false}
     * @apiVersion 0.2.0
     */
	@ResponseBody
	@RequestMapping(value = "/pushStatus", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String pushStatus(Long memberId, Short pushStatus){
		try {
			shopMemberPushSetService.pushStatus(memberId, pushStatus);
		} catch (Exception e) {
			jsonFailure(e);
		}
		return jsonSuccess(null);
	}
	
	/**
     * @api {post} /shopMemberPushSet/queryShopMemberPushSetById 查询抢单模式状态 caina.sun
     * @apiPermission user
     * @apiDescription 接口描述 查询抢单模式状态
     * @apiparam {string} memberId  人员ID
     * @apiGroup 社区配送
     * @apiSampleRequest /shopMemberPushSet/queryShopMemberPushSetById
     * @apiSuccess {String} result true
     * @apiError {String} result false
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *   {"data":1,"success":true}
     *
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 200 OK
     *   {"errCode":0,"errSubcode":"openapi.shopMemberPushSet.queryShopMemberPushSetById","message":"memberId不能为空","success":false}
     * @apiVersion 0.2.0
     */
	@ResponseBody
	@RequestMapping(value = "/queryShopMemberPushSetById", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String queryShopMemberPushSetById(Long memberId){
		try {
			return shopMemberPushSetService.queryShopMemberPushSetById(memberId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
