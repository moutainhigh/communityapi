package net.okdi.api.controller;

import net.okdi.api.service.AppVersionInfoService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/appVersionInfo")
public class AppVserionInfoController extends BaseController{

	@Autowired
	private AppVersionInfoService appVersionInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/queryAppVersionInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAppVersionInfo(Short appType){
		try {
			return jsonSuccess(this.appVersionInfoService.queryAppVersionInfo(appType));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryAppVersionInfoIos", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAppVersionInfoIos(){
		try {
			return jsonSuccess(this.appVersionInfoService.queryAppVersionInfoIos());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/updateAppVersionInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAppVersionInfo(Short appType){
		try {
			this.appVersionInfoService.updateAppVersionInfo(appType);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
