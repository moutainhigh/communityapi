package net.okdi.mob.controller;

import net.okdi.mob.service.AppVersionInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/appVersionInfo")
public class AppVersionInfoController {
	@Autowired
	private AppVersionInfoService appVersionInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/queryAppVersionInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAppVersionInfo(Short appType){
			return this.appVersionInfoService.queryAppVersionInfo(appType);
	}
	/**
	 * @api {post} /appVersionInfo/queryAppVersionInfoIos iOS应用程序版本信息查询
	 * @apiPermission user
	 * @apiDescription iOS应用程序版本信息查询shihe.zhai
	 * @apiGroup 应用程序版本信息
	 * @apiSampleRequest /appVersionInfo/queryAppVersionInfoIos
	 * @apiSuccess {String} appVersion 版本标识
	 * @apiSuccess {String} downloadUrl 下载地址
	 * @apiSuccess {String} isUpdate 强制更新标识  0不强制更新   1强制更新
	 * @apiSuccess {String} projectName 项目名称
	 * @apiSuccess {String} versionDescription 版本更新说明
	 * @apiSuccess {String} versionNum 版本号
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *{
	 *   "data": {
	 *      "appVersion": "1",
	 *     "downloadUrl": "http://www.okdi.net/okdi.html",
	 *     "isUpdate": "0",
	 *     "projectName": "好递快递员",
	 *     "versionDescription": "1、免费短信可以自定义初始编号了\r\n\r2、短信记录增加了客户拒收和重复内容拦截两个失败状态\r\n\r3、免费短信每条现在可以输入50个字",
	 *     "versionNum": "2.7.2"
	 * },
	 * "success": true
	 * 
	 *}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"message":"Parameter is not correct or Parameter format error","success":false}
	 * @apiVersion 0.2.0
	 */
	
	@ResponseBody
	@RequestMapping(value = "/queryAppVersionInfoIos", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAppVersionInfoIos(){
			return this.appVersionInfoService.queryAppVersionInfoIos();
	}
	@ResponseBody
	@RequestMapping(value = "/updateAppVersionInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAppVersionInfo(Short appType){
			return this.appVersionInfoService.updateAppVersionInfo(appType);
	}

}
