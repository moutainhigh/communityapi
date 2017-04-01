package net.okdi.apiV1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.okdi.apiV1.entity.PushMessageInfoItem;
import net.okdi.apiV1.service.PushMessageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//运营平台推送
@Controller
@RequestMapping("/pushMessage")
public class PushMessageController extends BaseController{

	@Autowired
	private PushMessageService pushMessageService;
	
	
	//回调接收推送类型
	@ResponseBody
	@RequestMapping(value = "/receiveSendTypeValue", method = { RequestMethod.POST, RequestMethod.GET })
	public String receiveSendTypeValue(Long id, String sendPhone, Short flag){
		String result = pushMessageService.receiveSendTypeValue(id, sendPhone, flag);
		return jsonSuccess();
	}
	
	
	//推送管理-->任务记录
	@ResponseBody
	@RequestMapping(value="/queryPushMessageListInfo", method={RequestMethod.POST, RequestMethod.GET})
	public String queryPushMessageListInfo(String startTime, String endTime, String title, String content, String currentPage, String pageSize){
		Integer currentPage1;
		Integer pageSize1;
		if(PubMethod.isEmpty(currentPage)){
			currentPage1 = 1;
		}else{
			currentPage1 = Integer.valueOf(currentPage);
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize1 = 15;
		}else{
			pageSize1 = Integer.valueOf(pageSize);
		}
		Map<String,Object> listMap = pushMessageService.queryPushMessageListInfo(startTime, endTime, title, content, currentPage1, pageSize1);
		return jsonSuccess(listMap);
	}
	//公告管理-->公告列表
	@ResponseBody
	@RequestMapping(value="/queryAnnounceMessageListInfo", method={RequestMethod.POST, RequestMethod.GET})
	public String queryAnnounceMessageListInfo(String startTime, String endTime, String title, String content, String currentPage, String pageSize){
		Integer currentPage1;
		Integer pageSize1;
		if(PubMethod.isEmpty(currentPage)){
			currentPage1 = 1;
		}else{
			currentPage1 = Integer.valueOf(currentPage);
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize1 = 15;
		}else{
			pageSize1 = Integer.valueOf(pageSize);
		}
		Map<String,Object> listMap = pushMessageService.queryAnnounceMessageListInfo(startTime, endTime, title, content, currentPage1, pageSize1);
		return jsonSuccess(listMap);
	}
	//公告管理-->公告列表-->停用
	@ResponseBody
	@RequestMapping(value="/updateAnnounceStatus", method={RequestMethod.POST, RequestMethod.GET})
	public String updateAnnounceStatus(Long id){
		this.pushMessageService.updateAnnounceStatus(id);
		return jsonSuccess();
	}
	//导出查询所有的推送的手机号
	@ResponseBody
	@RequestMapping(value="/queryPushPhoneAllById", method={RequestMethod.POST, RequestMethod.GET})
	public String queryPushPhoneAllById(String id){
		
		List<PushMessageInfoItem> result = pushMessageService.queryPushPhoneAllById(id);
		
		return jsonSuccess(result);
	}
}
