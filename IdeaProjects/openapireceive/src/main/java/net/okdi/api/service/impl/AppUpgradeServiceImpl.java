package net.okdi.api.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.okdi.api.service.AppUpgradeService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;
@Service
public class AppUpgradeServiceImpl implements AppUpgradeService{
	
public static final Log logger = LogFactory.getLog(AppVersionInfoServiceImpl.class);
	
	@Value("${app_type_express}")
	private String appTypeExpress;
	@Value("${app_version_express}")
	private String appVersionExpress;

	@Value("${isUpdate_express}")
	private String isUpdateExpress;
	@Value("${download_url_express}")
	private String downloadUrlExpress;
	
	@Value("${app_type_person}")
	private String appTypePerson;
	@Value("${app_version_person}")
	private String appVersionPerson;
	@Value("${isUpdate_person}")
	private String isUpdatePerson;
	@Value("${download_url_person}")
	private String downloadUrlPerson;
	
	
	@Autowired
	private EhcacheService ehcacheService;
	/**
	 * phoneType   0：安卓系统  1：苹果系统
	 * selfVersion   本机版本号
	 */
	@Override
	public Map<String,Object> queryAppUpgrade(Short appType,String selfVersion,Short phoneType) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(phoneType==0){
			if(appType==1){//好递快递员
				logger.info("查询好递快递员版本信息方法开始");
				map = this.ehcacheService.get("appVersionInfoCacheOfExpress", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, Map.class);
				logger.info("缓存中的map===="+map);
				if(PubMethod.isEmpty(map)){
					logger.info("好递快递员版本信息缓存为空，查询配置文件");
					Properties prop = new Properties();
					String path = Thread.currentThread().getContextClassLoader().getResource("appVersionInfo.properties").getPath();
					InputStream is = new FileInputStream(path);
					prop.load(is);
					map = new HashMap<String, Object>();
					map.put("appType", prop.getProperty("app_type_express").trim());
					map.put("appVersion", prop.getProperty("app_version_express").trim());
					
					//线上版本
					String[] theVersion=prop.getProperty("version_name_express").trim().split("\\.");
					//次新
					String[] adviceVersion=prop.getProperty("version_name_adviceExpress").trim().split("\\.");
					//手机
					String[] phoneVersion=selfVersion.split("\\.");
					if(check(phoneVersion,adviceVersion)==true){
						map.put("isUpdate","1");
					}					
					else if(check(phoneVersion,adviceVersion)==false&&check(phoneVersion,theVersion)==true){
						map.put("isUpdate","0");
					}else if(check(phoneVersion,adviceVersion)==false&&check(phoneVersion,theVersion)==false){
						map.put("isUpdate","2");
					}
					
					
					//map.put("isUpdate", prop.getProperty("isUpdate_express").trim());
					map.put("downloadUrl", prop.getProperty("download_url_express").trim());
					map.put("versionDescription", prop.getProperty("version_description").trim());
					map.put("versionName",prop.getProperty("version_name_express").trim());
					map.put("projectName",prop.getProperty("project_name_express").trim());
					this.ehcacheService.put("appVersionInfoCacheOfExpress", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, map);
					logger.info("map========"+map);
					logger.info("缓存=="+this.ehcacheService.get("appVersionInfoCacheOfExpress", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, Map.class));
					return map;
				}
			}else if(appType==2){//好递个人端
				logger.info("查询好递个人端版本信息方法开始");
				map = this.ehcacheService.get("appVersionInfoCacheOfPerson", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, Map.class);
				logger.info("缓存中的map===="+map);
				if(PubMethod.isEmpty(map)){
					logger.info("好递个人端版本信息缓存为空，查询配置文件");
					Properties prop = new Properties();
					String path = Thread.currentThread().getContextClassLoader().getResource("appVersionInfo.properties").getPath();
					InputStream is = new FileInputStream(path);
					prop.load(is);
					map = new HashMap<String, Object>();
					map.put("appType", prop.getProperty("app_type_person").trim());
					map.put("appVersion", prop.getProperty("app_version_person").trim());
					map.put("isUpdate", prop.getProperty("isUpdate_person").trim());
					map.put("downloadUrl", prop.getProperty("download_url_person").trim());
					map.put("versionDescription", prop.getProperty("version_description").trim());
					this.ehcacheService.put("appVersionInfoCacheOfPerson", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, map);
					logger.info("map========"+map);
					logger.info("缓存=="+this.ehcacheService.get("appVersionInfoCacheOfPerson", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, Map.class));
					return map;
				}
			}
			return map;
			
		}
		
		
		
		else if(phoneType==1){
			
			logger.info("查询好递快递员版本信息方法开始");
			map = this.ehcacheService.get("appVersionInfoCacheOfExpress", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, Map.class);
			logger.info("缓存中的map===="+map);
			if(PubMethod.isEmpty(map)){
				logger.info("好递快递员版本信息缓存为空，查询配置文件");
				Properties prop = new Properties();
				String path = Thread.currentThread().getContextClassLoader().getResource("appVersionInfo.properties").getPath();
				InputStream is = new FileInputStream(path);
				prop.load(is);
				map = new HashMap<String, Object>();
				map.put("appVersion", prop.getProperty("app_version_express_ios").trim());
				map.put("versionNum", prop.getProperty("version_num_ios").trim());
				//线上
				String[] theVersion=prop.getProperty("version_num_ios").trim().split("\\.");
				//次新
				String[] adviceVersion=prop.getProperty("version_advicenum_ios").trim().split("\\.");
				//手机
				String[] phoneVersion=selfVersion.split("\\.");
				if(check(phoneVersion,adviceVersion)==true){
					map.put("isUpdate","1");
				}					
				else if(check(phoneVersion,adviceVersion)==false&&check(phoneVersion,theVersion)==true){
					map.put("isUpdate","0");
				}else if(check(phoneVersion,adviceVersion)==false&&check(phoneVersion,theVersion)==false){
					map.put("isUpdate","2");
				}
				
				
				//map.put("isUpdate", prop.getProperty("isUpdate_express_ios").trim());
				map.put("downloadUrl", prop.getProperty("download_url_express_ios").trim());
				map.put("versionDescription", prop.getProperty("version_description_ios").trim());
				map.put("projectName",prop.getProperty("project_name_express").trim());
				this.ehcacheService.put("appVersionInfoCacheOfExpress", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, map);
				logger.info("map========"+map);
				logger.info("缓存=="+this.ehcacheService.get("appVersionInfoCacheOfExpress", String.valueOf(appType)+"%"+String.valueOf(phoneType)+"%"+selfVersion, Map.class));
				return map;
			}
			return map;
		}
		return map;
	}
	private  boolean check(String[] a,String[] b){
		if(a.length>=b.length){
			for(int i=0;i<b.length;i++){
				if(a[i].length()<b[i].length()){
					return true;
				}else if(a[i].length()>b[i].length()){
					return false;
				}else{
					for(int j=0;j<a[i].length();j++){
						if(Integer.valueOf(a[i].charAt(j))>Integer.valueOf(b[i].charAt(j))){
							
							return false;
						}else if(Integer.valueOf(a[i].charAt(j))<Integer.valueOf(b[i].charAt(j))){
							
							return true;
						}
					}
				}
			}
		for(int i=0;i<(a.length-b.length);i++){
			if(Integer.valueOf(a[a.length-i-1])!=0){
				
				return false;
			}
		}
		}else{
			for(int i=0;i<a.length;i++){
				if(a[i].length()<b[i].length()){
					
					return true;
				}else if(a[i].length()>b[i].length()){
					
					return false;
				}else{
					for(int j=0;j<a[i].length();j++){
						if(Integer.valueOf(a[i].charAt(j))>Integer.valueOf(b[i].charAt(j))){
							
							return false;
						}else if(Integer.valueOf(a[i].charAt(j))<Integer.valueOf(b[i].charAt(j))){
							
							return true;
						}
					}
				}
			}
		for(int i=0;i<(b.length-a.length);i++){
			if(Integer.valueOf(b[b.length-i-1])!=0){
				
				return true;
			}
		}
		}
		
		return false;
		
	}
	public void  remove(){
		this.ehcacheService.removeAll("appVersionInfoCacheOfExpress");
	}
}
