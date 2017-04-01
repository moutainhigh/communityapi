package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 分派包裹Controller
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: net.okdit</p> 
 * @author jianxin.ma
 * @date 2016-4-23
 */
@Controller
@RequestMapping("/assignPackage")
public class AssignPackageController extends BaseController {
	/**
	 * @author jianxin.ma
	 * @api {post} /assignPackage/querySiteMemberListV2 查询站点(代收点)成员列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询站点(代收点)成员列表
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} compId 站点id
	 * @apiSampleRequest /assignPackage/querySiteMemberListV2
	 * @apiSuccess {String} countMember  站点人员总数
	 * @apiSuccess {String} memberId  站点人员id
	 * @apiSuccess {String} headImgPath  站点人员头像
	 * @apiSuccess {String} memberPhone  站点人员手机号
	 * @apiSuccess {String} memberName  站点人员名字
	 * @apiSuccess {String} roleId  站点人员角色id
	 * @apiSuccessExample Success-Response:
	   {
	   "data":{
		   "countMember":2,
		   "memberList":[
			   {
				   "headImgPath":"http://publicapi.okdit.net/nfs_data/mob/head/1521392185909873.jpg",
				   "memberPhone":"13343691888",
				   "memberId":1521392185909873,
				   "memberName":"董庆凡",
				   "roleId":0
			   },
			   {
				   "headImgPath":"http://publicapi.okdit.net/nfs_data/mob/head/1521392185909865.jpg",
				   "memberPhone":"13343691801",
				   "memberId":1521392185909865,
				   "memberName":"王显",
				   "roleId":0
			   }
		   ]
	   },
	   "success":true
	   }
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"compId不能为空",
	 *    "success":false}
	 */
	@Autowired
	private AssignPackageService assignPackageService;
	@ResponseBody
	@RequestMapping(value = "/querySiteMemberListV2", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySiteMemberList(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.querySiteMemberList", "compId不能为空！");
		}
		try {
		return assignPackageService.queryEmployeeByCompId(compId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /assignPackage/saveParcelInfo 保存包裹信息
	 * @apiVersion 0.2.0
	 * @apiDescription 保存包裹信息
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} expWaybillNum 运单号
	 * @apiParam {Long} compId 站点id
	 * @apiParam {Long} netId 网络id
	 * @apiParam {String} addresseeName 收件人姓名
	 * @apiParam {String} addresseeMobile 收件人手机号码      
	 * @apiParam {Long} addresseeAddressId 收件人地址乡镇id  
	 * @apiParam {String} cityName 城市名称 如:北京市海淀区
	 * @apiParam {String} addresseeAddress 收件人详细地址
	 * @apiParam {BigDecimal} freight 包裹应收运费                                 
	 * @apiParam {BigDecimal} codAmount 代收货款金额                               
	 * @apiParam {Long} createUserId 创建人id                             
	 * @apiParam {Long} actualSendMember 派件人id                         
	 * @apiSampleRequest /assignPackage/saveParcelInfo
	 * @apiSuccess {String} parcelId  包裹id(-1 :该运单号已存在,不进行保存包裹操作.正常保存返回包裹id)
	 * @apiSuccessExample Success-Response:
	   {
	   "data":
		   {
			   "parcelId":208961735794688,
		   },
		"success":true
	   }

	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"addresseeMobile不能为空!",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/saveParcelInfo", method = { RequestMethod.POST ,RequestMethod.GET })	
	public String saveParcelInfo(String expWaybillNum, Long compId, Long netId, String addresseeName, String addresseeMobile,Long addresseeAddressId,String cityName,
			String addresseeAddress, BigDecimal freight,BigDecimal codAmount,Long createUserId, Long actualSendMember){
		if(PubMethod.isEmpty(addresseeMobile)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.saveParcelInfo", "addresseeMobile不能为空！");
		}
		try {
			return assignPackageService.saveParcelInfo(expWaybillNum, compId, netId, addresseeName, addresseeMobile,addresseeAddressId,cityName,
							addresseeAddress,freight, codAmount, createUserId, actualSendMember);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * @Description: 判断手机号今天是否添加过包裹
	 * @param addresseeMobile
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-27
	 */
	/**
	 * @author jianxin.ma
	 * @api {post} /assignPackage/parcelIsExist 判断手机号今天是否添加过包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 判断手机号今天是否添加过包裹
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} addresseeMobile 收件人手机号
	 * @apiParam {Long} compId 站点id
	 * @apiSampleRequest /assignPackage/parcelIsExist
	 * @apiSuccess {Boolean} mobileFlag  该手机号今天是否添加过包裹
	 * @apiSuccessExample Success-Response:
	   {
	   "data":
		   {
			   "mobileFlag":false,
		   },
		"success":true
	   }

	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"addresseeMobile不能为空!",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/parcelIsExist", method = { RequestMethod.POST ,RequestMethod.GET })	
	public String parcelIsExist(String addresseeMobile,Long compId){
		if(PubMethod.isEmpty(addresseeMobile)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.parcelIsExist", "addresseeMobile不能为空！");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.apiV4.controller.AssignPackageController.parcelIsExist", "compId不能为空！");
		}
		try {
			return this.assignPackageService.parcelIsExist(addresseeMobile,compId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/createSendTaskBatch 添加代签收包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 添加代签收包裹
	 * @apiGroup 新版-派件
	 * @apiParam {String} info json数组。格式：[{"expWaybillNum":"单号", "addresseeMobile": "手机号", "parcelNum": "编号"}]
	 * @apiParam {Long} memberId 用户id
	 * @apiParam {Long} netId 网络id
	 * @apiParam {Long} compId 站点id
	 * @apiSampleRequest /sendPackage/createSendTaskBatch
	 * @apiSuccessExample Success-Response:
	{

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
	@RequestMapping(value = "/saveParcelInfoBatch", method = { RequestMethod.POST ,RequestMethod.GET })
	public String saveParcelInfoBatch(String info, Long memberId){
		if(PubMethod.isEmpty(info) || PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "参数不正确");
		}

		return this.assignPackageService.saveParcelInfo(info, memberId);
	}


}
