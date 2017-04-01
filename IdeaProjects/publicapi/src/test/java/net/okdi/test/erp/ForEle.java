package net.okdi.test.erp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

@SuppressWarnings({"unchecked","rawtypes"})
public class ForEle {
	ConnClient cc = new ConnClient();
	String MyURL = "http://localhost:8080/publicapi/";
	//String method = "okdiTask/createTaskByPhone";
	
	//@Test
	public void createTask(Map map,String method) {
		//String MyURL = "http://localhost:8080/publicapi/";
		//MyURL = "http://192.168.35.38:8080/publicapi/";
		//String method = "okdiTask/createTask";
		//method = "okdiTask/createTaskByPhone";
		//method="okdiTask/createTaskByCompId";
		//method="okdiTask/createTaskByMemberId";
		String url = MyURL + method;
		
		//map.put("publickey", "QpDoAWa6jcpeLIpijBHxfA==");
		//map.put("secret","21bc0238907141da80472c0a3039841c" );
		//map.put("timestamp", String.valueOf(new Date().getTime()));

		//map.put("phone", "13261658330");
		//map.put("compid", "122026564304896");
		//map.put("memberid", "1671411110875008");

		map.put("address", "中国北京 海淀区");
		map.put("packagenum", "2");
		map.put("packageweight", "2");
		map.put("sendermobile", "13588881111");
		map.put("sendername", "aaaaa");
		map.put("erpid", "1111222");

		addSecretMap(map);

		String str = cc.Post(url, map);
		System.out.println(str);
	}

	
	@Test
	public void testAll(){
		createTaskByMemberId();
		createTaskByCompId();
		createTaskByPhone();
	}
	
	@Test
	public void createTaskByMemberId(){
		Map map = new HashMap<String, String>();
		map.put("memberid", "1671411110875008");
		String method="okdiTask/createTaskByMemberId";
		createTask(map,method);
	}
	
	@Test
	public void createTaskByCompId(){
		Map map = new HashMap<String, String>();
		map.put("compid", "122026564304896");
		String method="okdiTask/createTaskByCompId";
		createTask(map,method);
	}
	
	@Test
	public void createTaskByPhone(){
		Map map = new HashMap<String, String>();
		map.put("phone", "13261658330");
		String method = "okdiTask/createTaskByPhone";
		createTask(map,method);
	}
	
	@Test
	public void cancelTask(){
		ConnClient cc = new ConnClient();
		String MyURL="http://localhost:8080/publicapi/";
		String method="okdiTask/cancelTask";
		String url = MyURL+method;                    
		Map<String,String> map = new HashMap<String,String>();
		map.put("taskid","126312187027456");//117095688642560
		//map.put("memberId","117435573518336");//13884678355815424
		addSecretMap(map);
		String str = cc.Post(url,map);
		System.out.println("result:"+str);
	}
	
	@Test
	public void addParInfo(){
		Map map = new HashMap();
		
		map.put("taskid", "11");
		map.put("expwaybillnum", "22222");
		map.put("receivername", "aaa");
		map.put("receivermobile", "13261658330");
		map.put("receiveraddressid", "");
		map.put("receiveraddress", "aaaa");
		map.put("sendname", "222");
		map.put("sendmobile", "13261658330");
		map.put("sendaddressid", "");
		map.put("sendaddress", "1.1");
		map.put("parweight", "2.2");
		map.put("freight", "");
		map.put("iscod", "1");
		map.put("codamount", "");
		map.put("insureamount", "");
		map.put("pricepremium", "");
		map.put("packingcharges", "");
		map.put("freightpaymentmethod", "222");
		map.put("goodsdesc", "");
		map.put("parcelremark", "");
		map.put("serviceid", "");
		map.put("actualtakemember", "");
		
		addSecretMap(map);
		
		String MyURL = "http://localhost:8080/publicapi/";
		String method = "okdiTask/addParInfo";
		String url = MyURL + method;
		String str = cc.Post(url, map);
		System.out.println(str);
	}
	
	
	private void addSecretMap(Map map) {
		map.put("publickey", "QpDoAWa6jcpeLIpijBHxfA==");
		map.put("timestamp", String.valueOf(new Date().getTime()));
		List ls = new ArrayList();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String str = iter.next().toString();
			ls.add(str);
		}
		Collections.sort(ls);
		StringBuffer strb = new StringBuffer();
		for (int i = 0; i < ls.size(); i++) {
			String par = ls.get(i).toString();
			strb.append(par).append("=").append(map.get(par));
		}
		String privateKey = "21bc0238907141da80472c0a3039841c";
		String secret = GenMD5.generateMd5(strb.toString(), privateKey);
		map.put("secret", secret);
	}
}
