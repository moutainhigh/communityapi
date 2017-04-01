package net.okdi.core.common.handleMessage;

  
import java.util.Map;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.entity.CommonMessage;

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
  

public class MessageHandler {
	 
	 @Autowired
	 private OpenApiHttpClient openApiHttpClient;
	 
//	    private Log log = LogFactory.getLog(getClass());
  
	 public void handleMessage(String message) {  
        try{  
            Map<String,Object>  paraMap = JSON.parseObject(message);
			openApiHttpClient.doPassSendStr("expressTask/saveParcelInfo",paraMap);
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }   
}
