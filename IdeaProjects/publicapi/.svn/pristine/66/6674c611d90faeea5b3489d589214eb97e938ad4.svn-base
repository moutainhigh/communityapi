package net.okdi.core.common.handleMessage;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageSenderRabbit {
	// amqpTemplate 消息模板
	@Autowired
	private AmqpTemplate amqpTemplate;
	// exchange和queues链接的Key
	private String routingKey;

	public AmqpTemplate getAmqpTemplate() {
		return amqpTemplate;
	}

	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	public void sendDataToQueue(String str) {
		// 通过amqpTemplate模板来访问监听容器
		// MessageProperties messageProperties = new MessageProperties();
		// messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
		// Message message = new Message(str.getBytes(), messageProperties);
		// amqpTemplate.convertAndSend("this.routingKey",message);
		System.out.println("登录消息队列更新设备号等信息:"+str);
		amqpTemplate.convertAndSend(this.routingKey, str);
	}
}