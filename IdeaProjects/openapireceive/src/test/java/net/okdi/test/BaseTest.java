package net.okdi.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml","classpath*:/spring-mail.xml" })
//@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/classes/applicationContext.xml"}) 
public class BaseTest {
	public static SerializerFeature[] s = {SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullBooleanAsFalse,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteNullStringAsEmpty};

	@Test
	public void test() {
	}
	protected String map(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		String result = JSON.toJSONString(allMap,s).replaceAll(":null", ":\"\"");
		return result;
	}
	protected String jsonSuccess(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		String result = JSON.toJSONString(allMap,s).replaceAll(":null", ":\"\"");
		return result;
	}
}