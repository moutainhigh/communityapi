package net.okdi.core.common.handlemessage;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageSenderRabbit {
	// amqpTemplate 消息模板
	@Autowired
	private AmqpTemplate amqpTemplate;
	// exchange和queues链接的Key
	private String pushRecordKey;

	public AmqpTemplate getAmqpTemplate() {
		return amqpTemplate;
	}

	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	

	public String getPushRecordKey() {
		return pushRecordKey;
	}

	public void setPushRecordKey(String pushRecordKey) {
		this.pushRecordKey = pushRecordKey;
	}

	public void sendDataToQueue(String str) {
		// 通过amqpTemplate模板来访问监听容器
		// MessageProperties messageProperties = new MessageProperties();
		// messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		// Message message = new Message(str.getBytes(), messageProperties);
		// amqpTemplate.convertAndSend("this.routingKey",message);
		amqpTemplate.convertAndSend(this.pushRecordKey, str);
	}
}