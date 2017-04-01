package net.okdi.apiExt.controller;


import net.okdi.apiExt.service.ExtCompInfoService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *查询附近站点接口 - 电商使用
 */

@Controller
@RequestMapping("extCompInfo")
public class ExtCompInfoController extends BaseController{
	
	@Autowired
	private ExtCompInfoService extCompInfoService;
	
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoByAddressAndRange", method = { RequestMethod.POST, RequestMethod.GET })
	 public String queryCompInfoByAddressAndRange(Double longitude,Double latitude, String range){
		try {
			return jsonSuccess(this.extCompInfoService.queryCompInfoByAddressAndRange(longitude,latitude,range));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryMemberInfoByCompId", method = { RequestMethod.POST, RequestMethod.GET })
	 public String queryMemberInfoByCompId(String compId){
		try {
			return jsonSuccess(this.extCompInfoService.queryMemberInfoByCompId(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryBatchCompInfoByAddressAndRange", method = { RequestMethod.POST, RequestMethod.GET })
	 public String queryBatchCompInfoByAddressAndRange(String listParam,String range){
		try {
			return jsonSuccess(this.extCompInfoService.queryBatchCompInfoByAddressAndRange(listParam,range));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryBatchCompInfoByAddress", method = { RequestMethod.POST, RequestMethod.GET })
	 public String queryBatchCompInfoByAddress(String listParam,String compId){
		try {
			return jsonSuccess(this.extCompInfoService.queryBatchCompInfoByAddress(listParam,compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
