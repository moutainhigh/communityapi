package net.okdi.core.common;

import java.io.UnsupportedEncodingException;


import net.okdi.mob.entity.CommonMessage;

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
import org.springframework.amqp.core.Message;  
import org.springframework.amqp.core.MessageProperties;  
import org.springframework.amqp.support.converter.AbstractMessageConverter;  
import org.springframework.amqp.support.converter.MessageConversionException;  

import com.alibaba.fastjson.JSON;

/**
 * rabbitMQ转换json格式编码
 * 
 * @date 2015-01-30
 * @author xiangwei.liu
 * @version 1.0
 * 
 */
public class FastJsonMessageConverter extends AbstractMessageConverter{

	 @SuppressWarnings("unused")  
	    private static Log log = LogFactory.getLog(FastJsonMessageConverter.class);  
	  
	    public static final String DEFAULT_CHARSET = "UTF-8";  
	  
	    private volatile String defaultCharset = DEFAULT_CHARSET;  
	  
	    public FastJsonMessageConverter() {  
	        super();  
	    }  
	  
	    public void setDefaultCharset(String defaultCharset) {  
	        this.defaultCharset = (defaultCharset != null) ? defaultCharset  
	                : DEFAULT_CHARSET;  
	    }  
	  
	    public Object fromMessage(Message message)  
	            throws MessageConversionException {  
	    	String value = "";
			try {
				value = new String(message.getBody(), defaultCharset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
	        return value;  
	    }  
	  
	    @SuppressWarnings("unchecked")  
	    public <T> T fromMessage(Message message, T t) {  
	        String json = "";  
	        try {  
	        	//rabbitMQ预处理类
	            json = new String(message.getBody(), defaultCharset);  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	        return (T) JSON.parseObject(json);  
	    }  
	  
	    protected Message createMessage(Object objectToConvert,  
	            MessageProperties messageProperties)  
	            throws MessageConversionException {  
	        byte[] bytes = null;  
	        try {  
	            bytes = String.valueOf(objectToConvert).getBytes(this.defaultCharset);  
	        } catch (UnsupportedEncodingException e) {  
	            throw new MessageConversionException(  
	                    "Failed to convert Message content", e);  
	        }  
	        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);  
	        messageProperties.setContentEncoding(this.defaultCharset);  
	        if (bytes != null) {  
	            messageProperties.setContentLength(bytes.length);  
	        }  
	        return new Message(bytes, messageProperties);  
	  
	    }  
}
