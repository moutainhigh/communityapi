package net.okdi.apiV1.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.okdi.apiV1.service.QueryNearInfoService;
import net.okdi.core.util.PubMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/queryNearCompInfo")
public class QueryNearInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryNearInfoController.class);

    @Autowired
	private QueryNearInfoService queryNearInfoService;
	/**
	 * @author 贺海峰
	 * @api {post} /queryNearCompInfo/queryCompInfoByRoleId 查询附近5公里的快递站点或代收站
	 * @apiVersion 0.2.0
	 * @apiDescription 查询附近5公里的快递站点或代收站列表 -贺海峰
	 * @apiGroup 快递注册
	 * @apiParam {String} longitude 登录人经度
	 * @apiParam {String} latitude 登录人纬度
	 * @apiParam {String} roleId 角色id  1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiSampleRequest /queryNearCompInfo/queryCompInfoByRoleId
	 * @apiSuccess {String} compAddress  站点详细地址
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} distance  距离 单位m
	 * @apiSuccess {String} longitude  站点经度
	 * @apiSuccess {String} latitude  站点纬度
	 * @apiSuccess {String} compImgUrl  站点门店照
	 * @apiSuccess {String} resPhone  负责人手机号
	 * @apiSuccessExample Success-Response:
	{
	    "data": [
	        {
	            "compAddress": "广西壮族自治区-南宁市-宾阳县-武陵镇|宾阳县武陵镇武陵街                                     ",
	            "compId": 98713,
	            "compName": "宾阳县邮政局武陵邮政网点",
	            "distance": 4429,
	            "compPicUrl":http://
	            "latitude": 23.144049,
	            "longitude": 108.906224
	        },
	        {
	            "compAddress": "广西壮族自治区-南宁市-宾阳县-大桥镇|宾阳县大桥镇大桥街                                     ",
	            "compId": 98710,
	            "compName": "宾阳县邮政局大桥邮政网点",
	            "distance": 4454,
	            "latitude": 23.207965,
	            "longitude": 108.934413
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
	//查询附近站点 通过不同的角色id
	//如果是站长角色(1) 查询5公里范围未被领用的1003的站点，如果是(0)收派员或(-1)后勤 查询系统中被领用的或者入驻的待审核和审核通过的站点
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoByRoleId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByRoleId(String longitude,String latitude,String roleId){
		if(PubMethod.isEmpty(longitude) || PubMethod.isEmpty(latitude)){
			return PubMethod.paramError("publicapi.QueryNearInfoController.queryCompInfoByRoleId.001", "经纬度异常");
		}
		if(PubMethod.isEmpty(roleId)){
			return PubMethod.paramError("publicapi.QueryNearInfoController.queryCompInfoByRoleId.002", "请填写角色");
		}
		return this.queryNearInfoService.queryCompInfoByRoleId( longitude, latitude, roleId);
	}
	/**
	 * @author 贺海峰
	 * @api {post} /queryNearCompInfo/queryCompInfoByCompName 通过站点名称(代收站名称) 查询附近3级地址匹配的站点
	 * @apiVersion 0.2.0
	 * @apiDescription 通过站点名称(代收站名称) 查询附近3级地质下的站点-贺海峰
	 * @apiGroup 快递注册
	 * @apiParam {String} longitude 登录人经度
	 * @apiParam {String} latitude 登录人纬度
	 * @apiParam {String} roleId 角色id  1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiParam {String} compName 站点名称
	 * @apiSampleRequest /queryNearCompInfo/queryCompInfoByCompName
	 * @apiSuccess {String} addressId  地址id
	 * @apiSuccess {String} compImgUrl  公司门店照片
	 * @apiSuccess {String} netId  网络id
	 * @apiSuccess {String} netName  网络名称
	 * @apiSuccess {String} compAddress  站点详细地址
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} distance  距离 单位m
	 * @apiSuccess {String} longitude  站点经度
	 * @apiSuccess {String} latitude  站点纬度
	 * @apiSuccessExample Success-Response:
		{
		    "data": [
		        {
		            "addressId": 11000206,
		            "compAddress": "北京-海淀区|花园北路14号计算机一厂",
		            "compId": 121435492499456,
		            "compImgUrl": "",
		            "compName": "零时站点",
		            "compTypeNum": "1006",
		            "netId": 1503,
		            "netName": "百世汇通"
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
	@RequestMapping(value = "/queryCompInfoByCompName", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByCompName(Double longitude, Double latitude, String roleId,String compName){
		if(PubMethod.isEmpty(longitude) || PubMethod.isEmpty(latitude)){
			return PubMethod.paramError("publicapi.QueryNearInfoController.queryCompInfoByCompName.001", "经纬度异常");
		}
		if(PubMethod.isEmpty(roleId)){
			return PubMethod.paramError("publicapi.QueryNearInfoController.queryCompInfoByCompName.002", "请填写角色");
		}
		if(PubMethod.isEmpty(compName)){
			return PubMethod.paramError("publicapi.QueryNearInfoController.queryCompInfoByCompName.003", "请输入查询条件");
		}
		return this.queryNearInfoService.queryCompInfoByCompName(longitude, latitude, roleId, compName);
		
	}
	
	/**
	 * @author 贺海峰
	 * @api {post} /queryNearCompInfo/queryVerifyCode  获取图形验证码-贺海峰
	 * @apiVersion 0.2.0
	 * @apiDescription 获取图形验证码-贺海峰
	 * @apiGroup 快递注册
	 * @apiParam {String} mobile 手机号
	 * @apiSampleRequest /queryNearCompInfo/queryVerifyCode
	 * @apiSuccessExample Success-Response:
	{
	    "data": [
	  
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
	@RequestMapping(value = "/queryVerifyCode", method = { RequestMethod.POST, RequestMethod.GET })
	public void queryVerifyCode(String mobile ,HttpServletRequest req, HttpServletResponse resp){
		if(PubMethod.isEmpty(mobile)){
			PubMethod.paramsFailure("queryVerifyCode.001", "手机号不可以为空");
		}this.queryNearInfoService.queryVerifyCode(mobile,req, resp);
		
	}
	/**
	 * @author 贺海峰
	 * @api {post} /queryNearCompInfo/queryVerifyCodeIsRight  验证码是否正确-贺海峰
	 * @apiVersion 0.2.0
	 * @apiDescription 验证码是否正确-贺海峰
	 * @apiGroup 快递注册
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} verifyCode 验证码
	 * @apiSampleRequest /queryNearCompInfo/queryVerifyCodeIsRight
	 * @apiSuccess {String} isRight  0不正确 1正确 2没有此验证码
	 * @apiSuccessExample Success-Response:
		{
		    "data": [
		        {
		            "isRight": 0,

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
	@RequestMapping(value = "/queryVerifyCodeIsRight", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryVerifyCodeIsRight(String mobile,String verifyCode){
        LOGGER.info("验证图形验证码是否正确 --> mobile:{}, verifyCode:{}", mobile, verifyCode);

        if(PubMethod.isEmpty(mobile)){
			return PubMethod.paramsFailure("queryVerifyCodeIsRight.001", "手机号不可以为空");
		}
		if(PubMethod.isEmpty(verifyCode)){
			return PubMethod.paramsFailure("queryVerifyCodeIsRight.002", "验证码不可以为空");
		}
		return this.queryNearInfoService.queryVerifyCodeIsRight(mobile,verifyCode);
	}
	/**
	 * @author 贺海峰
	 * @api {post} /queryNearCompInfo/queryShareInfo  获取分享信息-贺海峰
	 * @apiVersion 0.2.0
	 * @apiDescription 获取分享信息-贺海峰
	 * @apiGroup 快递注册
	 * @apiParam {String} compTypeNum 1006邀请站长 1040 邀请店长
	 * @apiSampleRequest /queryNearCompInfo/queryShareInfo
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "logoUrl": "http://expnew.okdit.net/nfs_data/share/dsz.png",
		        "shareContent": "开通即可进行快递员任务分配，管理客户、免费短信群发等，大幅提升站点收、派效率.",
		        "shareTitle": "您的快递员，邀请您开通站长管理权限.",
		        "shareUrl": "http://promo.okdit.net/promo/inviteWebmaster/webmaster.jsp"
		    },
		    "success": true
		}
	 * @apiSuccess {String} logoUrl  logo图标地址(大钥匙)
	 * @apiSuccess {String} shareContent  分享内容
	 * @apiSuccess {String} shareTitle  分享标题
	 * @apiSuccess {String} shareUrl  分享链接
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/queryShareInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryShareInfo(String compTypeNum){
		return this.queryNearInfoService.queryShareInfo(compTypeNum);
		
	}
	/**
	 * @author 贺海峰
	 * @api {post} /queryNearCompInfo/invitationIntoCompInfo  邀请站长或店长(没有选择快递公司)-贺海峰
	 * @apiVersion 0.2.0
	 * @apiDescription 邀请站长或店长(没有选择快递公司)-贺海峰
	 * @apiGroup 快递注册
	 * @apiParam {String} fromMemberPhone 你的手机号
	 * @apiParam {String} toMemberPhone 你邀请的那个人的手机号
	 * @apiParam {String} invitationType 1006 收派员或后勤邀请站长 1040 店员邀请店长
	 * @apiSampleRequest /queryNearCompInfo/invitationIntoCompInfo
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
	@RequestMapping(value = "/invitationIntoCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String invitationIntoCompInfo(String fromMemberPhone,String toMemberPhone,String invitationType){
		return this.queryNearInfoService.invitationIntoCompInfo(fromMemberPhone,toMemberPhone,invitationType);
		
	}
	/**
	 * @author 贺海峰
	 * @api {post} /queryNearCompInfo/invitationHaveNetInfo  邀请站长(选择快递公司)-贺海峰
	 * @apiVersion 0.2.0
	 * @apiDescription 邀请站长(选择快递公司)-贺海峰
	 * @apiGroup 快递注册
	 * @apiParam {String} memberId 登录人的id
	 * @apiParam {String} netId 选择的快递网络id
	 * @apiParam {String} roleId 角色id 0 收派员 -1 后勤
	 * @apiSampleRequest /queryNearCompInfo/invitationHaveNetInfo
	 * @apiSuccess {String} compId   站点id
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "compId": "111111111111111111111"
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
	@RequestMapping(value = "/invitationHaveNetInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String invitationHaveNetInfo(String memberId,String netId,String roleId){
		return this.queryNearInfoService.invitationHaveNetInfo(memberId,netId,roleId);
		
	}
	@ResponseBody
	@RequestMapping(value = "/initVirtualCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String initVirtualCompInfo(){
		return this.queryNearInfoService.initVirtualCompInfo();
		
	}
	//查询这个手机号的各种认证信息
	@ResponseBody
	@RequestMapping(value = "/queryAuthenticationInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAuthenticationInfo(String mobile){
		return this.queryNearInfoService.queryAuthenticationInfo(mobile);
		
	}
	//删除有问题的数据
	@ResponseBody
	@RequestMapping(value = "/deleteWrongData", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteWrongData(String auditId){
		return this.queryNearInfoService.deleteWrongData(auditId);
		
	}
	//清除缓存
	@ResponseBody
	@RequestMapping(value = "/deleteRedisInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteRedisInfo(String redisName,String key){
		return this.queryNearInfoService.deleteRedisInfo(redisName,key);
	}
	//通过手机号查询密码
	@ResponseBody
	@RequestMapping(value = "/queryPasswordByMobile", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryPasswordByMobile(String mobile){
		return this.queryNearInfoService.queryPasswordByMobile(mobile);
	}
	//通过手机号查询脏数据
	@ResponseBody
	@RequestMapping(value = "/queryWrongDataByMobile", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryWrongDataByMobile(String mobile){
		return this.queryNearInfoService.queryWrongDataByMobile(mobile);
	}
	//通过手机号查询未读公告数量
	/**
	 * @author 贺海峰
	 * @api {post} /queryNearCompInfo/queryUnReadMessage  通过手机号查询未读公告数量-贺海峰
	 * @apiVersion 0.2.0
	 * @apiDescription 通过手机号查询未读公告数量-贺海峰
	 * @apiGroup 公告管理
	 * @apiParam {String} mobile 手机号
	 * @apiSampleRequest /queryNearCompInfo/queryUnReadMessage
	 * @apiSuccessExample Success-Response:
		{
		   	"data": {
		        "unReadCount": 10,
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
	@RequestMapping(value = "/queryUnReadMessage", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryUnReadMessage(String mobile){
		return this.queryNearInfoService.queryUnReadMessage(mobile);
	}
}
