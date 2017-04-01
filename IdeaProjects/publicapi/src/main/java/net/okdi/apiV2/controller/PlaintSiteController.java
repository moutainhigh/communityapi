package net.okdi.apiV2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.apiV2.service.PlaintSiteService;
import net.okdi.core.base.BaseController;
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
	 * @apiParam {String} compName 申诉站点(或营业分部)的名称
	 * @apiparam {Short} compType 公司类型，1：站点,2：营业分部
	 * @apiparam {String} plaintPhone 申诉人手机号
	 * @apiparam {String} headPhone 站点负责人手机号
	 * @apiparam {String} responsible 负责人姓名
	 * @apiparam {String} idNum 负责人身份证号
	 * @apiparam {String} business 营业执照号
	 * @apiparam {String} netId 网络id
	 * @apiparam {String} businessLicenseImg 营业执照、系统截屏、快递许可证照片三选一
	 * @apiparam {String} shopImg 站点门店照片
	 * @apiSampleRequest /plaintSite/savePlaintSiteInfo
	 * @apiSuccess data 001:申请成功, 002:申请失败
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
			String plaintPhone,String headPhone, String responsible, String idNum, String business, String netId,
			String businessLicenseImg, String shopImg){
		
		String result = plaintSiteService.savePlaintSiteInfo(memberId, compId, compName, compType, 
				plaintPhone, headPhone, responsible, idNum, business, netId, businessLicenseImg, shopImg);
		return result;
	}
}
