package net.okdi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.api.service.AppUpgradeService;
import net.okdi.core.base.BaseController;
@Controller
@RequestMapping("/appUpgrade")
public class AppUpgradeController extends BaseController{
	
	@Autowired
	private AppUpgradeService appUpgradeService;
	/**
	 * @api {post} /appUpgrade/queryAppUpgrade 应用程序版本信息查询
	 * @apiPermission user
	 * @apiDescription 应用程序版本信息查询   aijun.Han
	 * @apiGroup 应用程序版本信息
	 * @apiSampleRequest /appUpgrade/queryAppUpgrade
	 * @apiparam   {Short}  appType  默认为1就好
	 * @apiparam   {String} selfVersion app版本号
	 * @apiparam   {Short}  phoneType   手机系统标识  0：安卓   1：ios
	 * @apiSuccess {String} appVersion 版本标识
	 * @apiSuccess {String} downloadUrl 下载地址
	 * @apiSuccess {String} isUpdate 强制更新标识   0：建议更新   1：强制更新 2：不更新 
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
	@RequestMapping(value = "/queryAppUpgrade", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAppUpgrade(Short appType,String selfVersion,Short phoneType){
		try {
			return jsonSuccess(this.appUpgradeService.queryAppUpgrade(appType, selfVersion, phoneType));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/reCache", method = { RequestMethod.POST, RequestMethod.GET })
	public void removeCache(){
		this.appUpgradeService.remove();
	}
}
