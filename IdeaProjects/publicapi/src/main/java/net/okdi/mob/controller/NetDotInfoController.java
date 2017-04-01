package net.okdi.mob.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.NetDotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/netDotInfo")
public class NetDotInfoController extends BaseController{

	@Autowired
	NetDotService netDotService;

	/**
	 * @api {post} /netDotInfo/saveOrUpdateNetDotInfo 新增/更新认证信息
	 * @apiPermission user
	 * @apiDescription 站点认证 新增/修改认证信息  chunyang.tan
	 * @apiparam {Long} loginMemberId 登录人员ID
	 * @apiparam {Long} loginCompId 登录网点ID
	 * @apiparam {String} responsible 负责人姓名
	 * @apiparam {String} responsibleTelephone 负责人电话
	 * @apiparam {String} responsibleNum 负责人身份证号
	 * @apiparam {String} holdImg 营业执照
	 * @apiparam {String} reverseImg 站点门店照片
	 * @apiparam {String} frontImg 负责人身份证照片
	 * @apiGroup 个人中心
	 * @apiSampleRequest /netDotInfo/saveOrUpdateNetDotInfo
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"data":{"compStatus":-1},"success":true}
     *	compStatus=-1 网点状态变更为未提交
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"java.lang.NullPointerException","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdateNetDotInfo")
	public String saveOrUpdateNetDotInfo(Long loginMemberId,Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, String frontImg, String reverseImg, String holdImg)
	{
		try {
			this.netDotService.saveOrUpdateNetDotInfo(loginMemberId,loginCompId, responsible, responsibleTelephone, responsibleNum, null, null, frontImg,
					reverseImg, holdImg, holdImg, null, null, (short)2, (short) -1);
			Map<String, Object> compInfo = new HashMap<String, Object>();
			compInfo.put("compStatus", (short) -1);
			return jsonSuccess(compInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * @api {post} /netDotInfo/queryNetDotInfo 查询网点认证信息
	 * @apiPermission user
	 * @apiDescription 站点认证 查询网点认证信息  chunyang.tan
	 * @apiparam {Long} compId 公司ID
	 * @apiGroup 个人中心
	 * @apiSampleRequest /netDotInfo/queryNetDotInfo
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {
	 *	    "data": {
	 *	        "netDotBasicInfoMap": {
	 *	            "address": "福建南平市建阳市城郊镇",
	 *	            "addressId": 300454945,
	 *	            "auditDesc": "-",
	 *	            "compId": 170581142323200,
	 *	            "compName": "建阳站",
	 *	            "compStatus": -1,
	 *	            "compTelephone": "15100215119",
	 *	            "compTypeNum": "1006",
	 *	            "firstSystemImgUrl" = "http://expnew.okdit.net/nfs_data/comp/2015092517251545538.jpg";
     *       		"frontImgUrl" = "http://expnew.okdit.net/nfs_data/comp/2015092517250722825.jpg";
     *       		"holdImgUrl" = "http://expnew.okdit.net/nfs_data/comp/2015092517251545538.jpg";
     *       		"reverseImgUrl" = "http://expnew.okdit.net/nfs_data/comp/2015092517251128481.jpg";
	 *	            "latitude": "",
	 *	            "longitude": "",
	 *	            "netId": 1503,
	 *	            "netName": "百世汇通",
	 *	            "responsible": "啦啦啦",
	 *	            "responsibleIdNum": "511702198101111956",
	 *	            "responsibleTelephone": "15100215119",
	 *	            "reverseImg": "2015092419150356684.jpg",
	 *	            "reverseImgUrl": "http://expnew.okdit.net/nfs_data/comp/2015092419150356684.jpg",
	 *	            "useCompId": 67951,
	 *	            "verifyType": 2
	 *	        },
	 *	        "saveBasicInfo": [
	 *	            {
	 *	                "areaColor": "#c2c2c2",
	 *	                "compId": 170581142323200,
	 * 	                "memberId": 170581127643136,
	 *	                "memberName": "管理员",
	 *	                "memberPhone": "15100215119",
	 *	                "roleId": 1
	 *	            }
	 *	        ]
	 *	    },
	 *  		"success": true
	 *	}
	 *		"address" : 快递公司地址,
	 *		"addressId" : 快递公司地址,
	 *		"auditDesc" : 审核意见
	 *		"frontImgUrl" 手持身份证正面照片: 
	 *		"reverseImgUrl" : 站点门店照片,
	 *		"holdImgUrl" : 营业执照",
	 *		"compId" : 公司ID,
	 *		"compName": 公司名称 ,
	 *		"compStatus":公司状态 -1:创建,0:提交待审核,1:审核通过,2: 审核不通过4:再次提交待审核,
	 *		"compTelephone":公司电话,
	 *		"compTypeNum":"公司分类代码 1000:网络,1003:网络直营站点,1006:加盟公司,1008:加盟公司站点,1030:快递代理点,1050:营业分部",
	 *		"latitude" : 纬度
	 *		"longitude" : 经度
	 *		"netId" : 网络ID
	 *		"netName" : 网络名称
	 *		"responsible" : 负责人,
	 *		"responsibleIdNum" : 负责人身份证号,
	 *		"responsibleTelephone" : 负责人电话,
	 * 		"useCompId" : 站点id,
	 *		"verifyType" : 3
	 *		"areaColor":片区颜色,
	 *		"compId":站点id
	 *		"memberId":用户id,
	 *		"memberName":姓名,
	 *		"memberPhone":用户默认手机号,
	 *		"roleId":角色 -1: 后勤0 : 收派员1 : 大站长 -2：小站长
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"java.lang.NullPointerException","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("/queryNetDotInfo")
	public String queryNetDotInfo(Long compId)
	{
		try {
			return this.netDotService.queryNetDotInfo(compId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /netDotInfo/updateNetDotStatus 更新网点状态
	 * @apiPermission user
	 * @apiDescription 站点认证 查询网点认证信息  chunyang.tan
	 * @apiparam {Long} compId 公司ID
	 * @apiparam {Short} compStatus 网点状态 -1创建 0提交待审核 1审核成功 2审核失败
	 * @apiparam {Long} auditId 最后一次审核ID
	 * @apiGroup 个人中心
	 * @apiSampleRequest /netDotInfo/updateNetDotStatus
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"java.lang.NullPointerException","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("/updateNetDotStatus")
	public String updateNetDotStatus(Long compId, Short compStatus, Long auditId)
	{
		try {
			this.netDotService.updateNetDotStatus(compId,compStatus,auditId);
			return jsonSuccess(null);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /netDotInfo/uploadImage 上传图片
	 * @apiPermission user
	 * @apiDescription 站点认证 上传图片 chunyang.tan
	 * @apiparam {MultipartFile} myfile 图片文件
	 * @apiGroup 个人中心
	 * @apiSampleRequest /netDotInfo/uploadImage
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"data":{"imageUrl":"2015092419150838850.jpg","fileShowPath":"http://expnew.okdit.net/nfs_data/comp/2015092419150838850.jpg"},"success":true}}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"java.lang.NullPointerException","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadImage", method = { RequestMethod.GET, RequestMethod.POST })
	public String uploadImage(@RequestParam(value = "myfile", required = false) MultipartFile myfile) {
		try{
			Map<String,Object> result=this.netDotService.uploadImage(myfile);
			return jsonSuccess(result);
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	/**
	 * @author 韩爱军
	 * @api {post} /netDotInfo/validNetIsChanged 判断网络是否更新
	 * @apiVersion 0.2.0
	 * @apiDescription 判断网络是否更新
	 * @apiGroup 快递注册
	 * @apiSampleRequest /netDotInfo/validNetIsChanged
	 * @apiSuccess {String} data  时间戳
	 * @apiSuccessExample Success-Response:
		{
		    "data": "20151015113712",
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
	@RequestMapping(value = "/validNetIsChanged", method = { RequestMethod.GET, RequestMethod.POST })
	public String validNetIsChanged() {
		try{
			String result=this.netDotService.validNetIsChanged();
			return result;
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
}
