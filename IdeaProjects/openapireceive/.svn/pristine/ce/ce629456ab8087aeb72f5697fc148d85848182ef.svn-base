package net.okdi.core.sms;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.okdi.core.httpclient.RawHttpClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class OpenPlatformHttpClient {

    private @Autowired RawHttpClient openPlatformHttpClient;

    private @Value("${openListParcelUrl}") String openListParcelUrl;
    private @Value("${openReceivedParcelUrl}") String openReceivedParcelUrl;
    /**
	 * 国通快递接口 - 包裹上传接口
	 */	
	private @Value("${openUploadParcelUrl}") String openUploadParcelUrl;
	
    private static Logger logger = Logger.getLogger(OpenPlatformHttpClient.class);
    /**查询订单中电商件的包裹
	 * @param param
	 * @return
	 */
	public String queryFromWebsite(String userCode, String orgCode){
		logger.info("查询订单中电商件的包裹===============调用运营平台的接口:url:"+openListParcelUrl);
		JSONObject jsonObj = new JSONObject();
        jsonObj.put("orgCode", orgCode);
        jsonObj.put("userCode", userCode);
		String result = openPlatformHttpClient.post(openListParcelUrl, jsonObj.toJSONString());
		logger.info("查询订单中电商件的包裹===============:"+result);
		return result;
	}
	/**调用国通的包裹签收接口 panke.sun
	 * @param listmap
	 * @return
	 */
	public String uploadParcelTakenInfo(String listmap){
		logger.info("国通的包裹签收进行上传1111111===============调用运营平台的接口:url:"+openReceivedParcelUrl+",listmap:"+listmap);
		String result = openPlatformHttpClient.post(openReceivedParcelUrl,listmap);
		logger.info("国通的包裹签收进行上传2222222===============:"+result);
		return result;
	}
	/**调用国通的揽收包裹上传（散件）
	 * @param listmap
	 * @return
	 */
	public String uploadPackagenInfo(String listmap){
		logger.info("国通的揽收进行上传123===============调用运营平台的接口:url:"+openUploadParcelUrl+",listmap:"+listmap);
		String result = openPlatformHttpClient.post(openUploadParcelUrl,listmap);
		logger.info("国通的揽收进行上传结束===============:"+result);
		return result;
	}


}
