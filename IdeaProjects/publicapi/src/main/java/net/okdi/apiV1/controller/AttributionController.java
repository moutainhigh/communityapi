package net.okdi.apiV1.controller;


import net.okdi.apiV1.service.IAttributionService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/attribution")
public class AttributionController extends BaseController {

	@Autowired
  private IAttributionService attributionService;
	/**
	 * @api {post} /attribution/findShopowner 归属信息-店长店员站长收派员后勤-胡宣化
	 * @apiVersion 0.2.0
	 * @apiDescription 归属信息认证查询
	 * @apiGroup ACCOUNT 归属信息（分解看下方）
	 * @apiParam {String} memberId 人员Id
	 * @apiParam {String} roleId 角色
	 * @apiSampleRequest /attribution/findShopowner
	 * @apiSuccess {String} application_role_type  //1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiSuccess {String} audit_opinion  //'审核结果 :-1 为完善 0 待审核  1通过 2拒绝',
	 * @apiSuccess {String} comp_address  店铺地址
	 * @apiSuccess {String} comp_name  店铺名字
	 * @apiSuccess {String} net_name  所属公司
	 * @apiSuccess {String} responsible  负责人
	 * @apiSuccess {String} member_id  人员id
	 * @apiSuccess {String} headImgPath log图片
	 * @apiSuccess {String} image_type_2 营业执照
	 * @apiSuccess {String} image_type_5 身份证正面照
	 * @apiSuccess {String} image_type_8 站点门店照片
	 * @apiSuccess {String} responsible_telephone  负责人电话
	 * @apiSuccess {String} responsible_id_num  负责人身份证
	 * @apiSuccess {String} business_license_num  营业执照号
	 * @apiSuccess {String} audit_reject_reason  审核拒绝原因 1:照片不清楚,2:身份证与本人不一致,3:其他
	 * @apiSuccess {String} audit_desc  备注信息
	 * @apiSuccess {String} description_msg  营业分部的上级站点名称
	 * @apiSuccess {String} comp_status 公司状态 -1:创建,0:提交待审核,1:审核通过,2: 审核不通过4:再次提交待审核
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "application_role_type": "2", 
		        "audit_opinion": "1", 
		        "comp_address": "北京市海淀区|牡丹园北里", 
		        "comp_name": "途径", 
		        "member_id": "175639978360832", 
		        "responsible": "", 
		        "responsible_id_num": "", 
		        "responsible_telephone": "13633332247"
		        "headImgPath":"http://cas.okdit.net/nfs_data/mob/head/175639978360832.jpg"
		    }, 
		    "success": true
		}
		
		{
		    "data": {
		        "application_role_type": "0", 
		        "audit_opinion": "1", 
		        "comp_address": "河南省-南阳市-邓州市|北环路环保局东隔墙", 
		        "comp_name": "邓州圆通速递", 
		        "member_id": "1521392185909865", 
		        "net_name": "圆通速递", 
		        "responsible": "许星凯", 
		        "responsible_id_num": "411303199005095971", 
		        "responsible_telephone": ""
		        "headImgPath":"http://cas.okdit.net/nfs_data/mob/head/175639978360832.jpg"
		    }, 
		    "success": true
		}
		{
		    "data": {
		        "application_role_type": "1", 
		        "audit_opinion": "1", 
		        "comp_address": "北京市-北京市区-海淀区-城区|永定路 1号院", 
		        "comp_name": "宅急送营业分部（生产环境营业分部测试）", 
		        "image_type_5": "http://expnew.okdit.net/nfs_data/comp/2014101416245814470.png", 
		        "member_id": "1671409897612004", 
		        "net_name": "宅急送", 
		        "responsible": "赵海洋", 
		        "responsible_id_num": "110101199101018399", 
		        "responsible_telephone": "15001286350"
		        "headImgPath":"http://cas.okdit.net/nfs_data/mob/head/175639978360832.jpg"
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
	@RequestMapping(value = "/findShopowner", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String findShopowner(String memberId,String roleId) {
		return attributionService.findShopowner(memberId,roleId);
	}

}
