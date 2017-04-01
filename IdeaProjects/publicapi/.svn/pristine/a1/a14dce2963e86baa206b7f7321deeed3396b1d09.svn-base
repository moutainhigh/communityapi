package net.okdi.core.common.handleMessage;

import net.okdi.mob.service.BroadcastService;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class housekeeperHandler {
@Autowired
BroadcastService broadcastService; 
	public void handleMessage(String message) {  
		JSONObject value = JSON.parseObject(message);
		String arrayString = value.getString("jsondata");
		JSONArray jsonArray = JSON.parseArray(arrayString);
		for(int i = 0;i<jsonArray.size();i++){
		Long memberId = value.getLong("memberId");
        try{ 
        	String jsonString = String.valueOf(jsonArray.get(i));
        	System.out.println(broadcastService.addBroadcastOperation(jsonString, memberId));
        }catch(Exception e){  
          continue;
        }  
		}
    }   
}
