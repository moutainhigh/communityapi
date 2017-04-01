package net.okdi.apiV2.controller;

import net.okdi.apiV2.service.TextToSpeechService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/textToSpeech")
public class TextToSpeechController extends BaseController{

	@Autowired
	private TextToSpeechService textToSpeechService;
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description  文字转语音
	 * @data 2015-12-28 下午2:24:26
	 * @param text
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toSpeech", method = { RequestMethod.POST, RequestMethod.GET })
	public String  TextToSpeech(String text){
		if(PubMethod.isEmpty(text)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.TextToSpeechController.TextToSpeech.001", "text不能为空");
		}
		try {
			return JSON.toJSONString(textToSpeechService.textToSpeech(text));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
}
