package net.okdi.apiV2.controller;

import java.util.Map;

import net.okdi.apiV2.service.PlaintSiteService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 
 * @author 站点或者营业分部申诉
 *
 */
@Controller
@RequestMapping("/plaintSite")
public class PlaintSiteController extends BaseController{

	@Autowired
	private PlaintSiteService plaintSiteService;
	
	/**
	 * @author 郑炯
	 * @api {post} /plaintSite/savePlaintSiteInfo 申诉申请
	 * @apiVersion 0.2.0
	 * @apiDescription 申诉申请 -郑炯
	 * @apiGroup 申诉
	 * @apiParam {Long} memberId 用户的id
	 * @apiParam {Long} compId 公司的id
	 * @apiParam {String} compName 公司的名称
	 * @apiparam {Short} compType 公司类型,1:站点,2：营业分部
	 * @apiparam {String} plaintPhone 申诉人人手机号
	 * @apiparam {String} headPhone 站点负责人手机号
	 * @apiparam {String} responsible 负责人姓名
	 * @apiparam {String} headPhone 负责人姓名
	 * @apiparam {String} idNum 负责人身份证号
	 * @apiparam {String} netId 网点id
	 * @apiparam {String} businessLicenseImg 营业执照、系统截屏、快递许可证照片三选一
	 * @apiparam {String} shopImg 站点门店照片
	 * @apiparam {String} head_phone 站点负责人姓名
	 * @apiSampleRequest /plaintSite/savePlaintSiteInfo
	 * @apiSuccessExample Success-Response:
	{
    "data": ,
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
	@RequestMapping(value="/savePlaintSiteInfo",method={RequestMethod.POST,RequestMethod.GET})
	public String savePlaintSiteInfo(Long memberId, Long compId, String compName, Short compType, 
			String plaintPhone, String headPhone, String responsible, String idNum, String business, String netId,
			String businessLicenseImg, String shopImg){
		
		String result = null;
		try {
			if(PubMethod.isEmpty(memberId)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(compId)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.002", "compId不能为空");
			}
			if(PubMethod.isEmpty(compType)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.003", "compType不能为空");
			}
			if(PubMethod.isEmpty(plaintPhone)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.004", "plaintPhone不能为空");
			}
			if(PubMethod.isEmpty(responsible)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.005", "responsible不能为空");
			}
			if(PubMethod.isEmpty(headPhone)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.006", "headPhone不能为空");
			}
			/*if(PubMethod.isEmpty(idNum)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.007", "idNum不能为空");
			}*/
			if(PubMethod.isEmpty(netId)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.008", "netId不能为空");
			}
			if(PubMethod.isEmpty(businessLicenseImg)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.0010", "businessLicenseImg不能为空");
			}
			if(PubMethod.isEmpty(shopImg)){
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.savePlaintSiteInfo.0011", "shopImg不能为空");
			}
			result = plaintSiteService.savePlaintSiteInfo(memberId, compId, compName, compType, 
					plaintPhone, headPhone, responsible,  idNum, business, netId, businessLicenseImg, shopImg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonSuccess(result);
	}
	
	/**
	 * 查询申请人的列表信息
	 */
	@ResponseBody
	@RequestMapping(value="/queryPlaintSiteInfo", method={RequestMethod.POST, RequestMethod.GET})
	public String queryPlaintSiteInfo(String currentPage,String pageSize,String startTime, String endTime, String plaintPhone, String status){
		
		Map<String,Object> result = plaintSiteService.queryPlaintSiteInfo(currentPage, pageSize, startTime, endTime, plaintPhone, status);
		return jsonSuccess(result);
	}
	/**
	 * @api {post} /plaintSite/queryPlaintSiteInfoDetail 查询申诉人详情
	 * @apiVersion 0.2.0
	 * @apiDescription 查询申诉人详情 (郑炯)
	 * @apiGroup ACCOUNT 申诉
	 * @apiParam {String} id 申诉id
	 * @apiSampleRequest /plaintSite/queryPlaintSiteInfoDetail
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "auditDesc": "给你同意了",//审核备注
		        "auditTime": 1449566357000, 审核时间
		        "auditUser": 4381409032185002,审核人
		        "compId": 136519254089728, //公司id
		        "compName": "haohaohao7", 公司名称
		        "createTime": 1444954373000, 申诉时间
		        "frontImgUrl": "http://expnew.okdit.net/nfs_data/comp/123.jpg",
		        "head_phone": "15810885212", 负责人手机号
		        "holdImgUrl": "http://expnew.okdit.net/nfs_data/comp/456.jpg",
		        "id": 183599936970752,
		        "idNum": "123123123", 身份证
		        "memberId": 111111, 人员id
		        "omemberId": 222222, 人员id 原站长memberId
		        "memberName": "", 人员名称
		        "plaintPhone": 18511586957, 申诉手机号
		        "responsible": "zj09090909", 负责人名称
		        "reverseImgUrl": "http://expnew.okdit.net/nfs_data/comp/789.jpg",
		        "sitePhone": "18511586909", 站长手机号
		        "status": 1 状态
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
	@RequestMapping(value="/queryPlaintSiteInfoDetail", method={RequestMethod.POST, RequestMethod.GET})
	public String queryPlaintSiteInfoDetail(String id){
		
		Map<String,Object> result = plaintSiteService.queryPlaintSiteInfoDetail(id);
		return jsonSuccess(result);
	}
	
	/**
	 * @author 郑炯
	 * @api {post} /plaintSite/agreedReplaceSite 同意更换站长
	 * @apiVersion 0.2.0
	 * @apiDescription 同意更换站长 -郑炯
	 * @apiGroup 申诉
	 * @apiParam {String} id 申诉id
	 * @apiParam {String} compId 公司id
	 * @apiParam {String} memberId 用户id
	 * @apiparam {auditUser} 审核人id
	 * @apiparam {desc} 审核备注
	 * @apiparam {idNum} 负责人身份证
	 * @apiparam {responsible} 负责人姓名
	 * @apiparam {responsibleTelephone} 负责人手机号
	 * @apiparam {String} data--001:更换站长成功,002：驳回成功, 003：操作失败
	 * @apiSampleRequest /plaintSite/queryPlaintSiteInfo
	 * @apiSuccessExample Success-Response:
		{
	    "data": ,
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
	@RequestMapping(value="/agreedReplaceSite", method={RequestMethod.POST, RequestMethod.GET})
	public String agreedReplaceSite(String id, String compId, String memberId, String flag, String auditUser, 
			String desc, String idNum, String responsible, String responsibleTelephone, String business){
		if(PubMethod.isEmpty(id)){
			return paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.agreedReplaceSite.001", "申诉id不能为空!!!");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.agreedReplaceSite.002", "compId不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.agreedReplaceSite.003", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.agreedReplaceSite.004", "flag 不能为空!!!");
		}
		String result = plaintSiteService.agreedReplaceSite(id, compId, memberId, flag, auditUser, desc, idNum, responsible, responsibleTelephone, business);
		return jsonSuccess(result);
	}
	@ResponseBody
	@RequestMapping(value="/queryIsNotHaveBasEmployee", method={RequestMethod.POST, RequestMethod.GET})
	public String queryIsNotHaveBasEmployee(String compId, String memberId){
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.agreedReplaceSite.001", "compId不能为空!!!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV2.controller.PlaintSiteController.agreedReplaceSite.002", "memberId 不能为空!!!");
		}
		String result = plaintSiteService.queryIsNotHaveBasEmployee(compId, memberId);
		return jsonSuccess(result);
	}
	
}
