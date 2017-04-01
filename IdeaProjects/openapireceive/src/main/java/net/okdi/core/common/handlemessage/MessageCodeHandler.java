package net.okdi.core.common.handlemessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.okdi.apiV1.entity.PushMessageInfo;
import net.okdi.apiV1.entity.PushMessageInfoItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.amssy.common.util.primarykey.IdWorker;

  
public class MessageCodeHandler{
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static final Log logger = LogFactory.getLog(MessageCodeHandler.class);
	
	@Transactional(rollbackFor={Exception.class})
	public void handleMessage(String message) {  
		logger.debug("推送任务记录进入队列,开始执行");
		logger.debug("message:>>>>>>>>>>>>: "+message);
		long taskStartTime = System.currentTimeMillis();
		//String result = id+"-"+arrPhones.get(i);		
		try {
			String[] arr = message.split("-");
			String id = arr[0];//id
			String phone = arr[1];//手机号
			List<PushMessageInfoItem> listItem = new ArrayList<PushMessageInfoItem>();
			Date date = new Date();
			//保存发送记录详细表
			PushMessageInfoItem messageInfoItem = new PushMessageInfoItem();
			messageInfoItem.setBatchId(Long.valueOf(id));
			messageInfoItem.setCreateTime(date);
			messageInfoItem.setModifyTime(null);
			messageInfoItem.setOpenType(null);
			messageInfoItem.setPushPhone(phone);
			messageInfoItem.setReciveType(null);
			messageInfoItem.setSendType((short)1);
			//listItem.add(messageInfoItem);
			long mongoStartTime = System.currentTimeMillis();
			mongoTemplate.insert(messageInfoItem);
			long taskEndTime = System.currentTimeMillis();
			logger.info("task execute time:"+(taskEndTime-taskStartTime)+"------mongo execute time:"+(taskEndTime-mongoStartTime));
			logger.info("插入成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
