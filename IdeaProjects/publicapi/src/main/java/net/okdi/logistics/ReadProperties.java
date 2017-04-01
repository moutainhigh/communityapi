package net.okdi.logistics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class ReadProperties {
	private static Properties props = new Properties();
	//private static Properties proNum = new Properties();

	public  ReadProperties(String filePath) {

		try {
			props.load(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}

	public String getKeyValue(String key) {
		return props.getProperty(key);
	}
	
	public static void main(String[] args){
		
		//ReadProperties rp = new ReadProperties("config/YTO.prototies");
		ReadProperties rp = new ReadProperties("taobao_proxy.properties");
		System.out.println(rp.getKeyValue("proxy_url"));
	}
}
