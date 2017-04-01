package net.okdi.apiV2.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.TextToSpeechService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
		
	/**
	 * 
	 * @Method
	 * @Description 文字转语音
	 * @return
	 * @author AiJun.Han
	 * @data 2015-12-28 下午2:03:42
	 */
	@Service
	public class TextToSpeechServiceImpl implements TextToSpeechService{
		//apikey
		private static String apikey = "0efdd0e476221878d7055f102467ac8c";

		//url
		private static String httpUrl = "http://apis.baidu.com/apistore/baidutts/tts";
		
		public static final Log logger = LogFactory.getLog(TextToSpeechServiceImpl.class);
		
		
		
		@Override
		public Map textToSpeech(String text){
			try {
				Map<String,Object> map=new HashMap<String,Object>();
				//urlencode编码
				String	httpArg = "text="+java.net.URLEncoder.encode(text, "utf-8")+"&ctp=1&per=1";
				JSONObject jsonObject=JSON.parseObject(request(httpUrl, httpArg));
				
				if("0".equals(jsonObject.get("errNum").toString())){
				String musString=jsonObject.get("retData").toString();
				map.put("success",true);
				map.put("resString", musString);
				}else {
					map.put("success",false);
					map.put("resString", "转换失败，请联系管理员");
				}
				
				return map;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * @param urlAll
		 *            :请求接口
		 * @param httpArg
		 *            :参数
		 * @return 返回结果
		 */
		public  String request(String httpUrl, String httpArg) {
			BufferedReader reader = null;
			String result = null;
			StringBuffer sbf = new StringBuffer();
			httpUrl = httpUrl + "?" + httpArg;

			try {
				URL url = new URL(httpUrl);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				// 填入apikey到HTTP header
				connection.setRequestProperty("apikey", apikey);
				connection.connect();
				InputStream is = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String strRead = null;
				while ((strRead = reader.readLine()) != null) {
					sbf.append(strRead);
					sbf.append("\r\n");
				}
				reader.close();
				result = sbf.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

	

	}
