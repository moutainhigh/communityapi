package net.okdi.api.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.okdi.api.service.AppVersionInfoService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppVersionInfoServiceImpl implements AppVersionInfoService{
	
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

	@Override
	public Map<String,Object> queryAppVersionInfo(Short appType) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		if(appType==1){//好递快递员
			logger.info("查询好递快递员版本信息方法开始");
			map = this.ehcacheService.get("appVersionInfoCacheOfExpress", String.valueOf(appType), Map.class);
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
				map.put("isUpdate", prop.getProperty("isUpdate_express").trim());
				map.put("downloadUrl", prop.getProperty("download_url_express").trim());
				map.put("versionDescription", prop.getProperty("version_description").trim());
				map.put("versionName",prop.getProperty("version_name_express").trim());
				map.put("projectName",prop.getProperty("project_name_express").trim());
				this.ehcacheService.put("appVersionInfoCacheOfExpress", String.valueOf(appType), map);
				logger.info("map========"+map);
				logger.info("缓存=="+this.ehcacheService.get("appVersionInfoCacheOfExpress", String.valueOf(appType), Map.class));
				return map;
			}
		}else if(appType==2){//好递个人端
			logger.info("查询好递个人端版本信息方法开始");
			map = this.ehcacheService.get("appVersionInfoCacheOfPerson", String.valueOf(appType), Map.class);
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
				this.ehcacheService.put("appVersionInfoCacheOfPerson", String.valueOf(appType), map);
				logger.info("map========"+map);
				logger.info("缓存=="+this.ehcacheService.get("appVersionInfoCacheOfPerson", String.valueOf(appType), Map.class));
				return map;
			}
		}
		return map;
	}
	@Override
	public Map<String,Object> queryAppVersionInfoIos() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		logger.info("查询好递快递员版本信息方法开始");
		map = this.ehcacheService.get("appVersionInfoCacheOfExpress", "3", Map.class);
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
			map.put("isUpdate", prop.getProperty("isUpdate_express_ios").trim());
			map.put("downloadUrl", prop.getProperty("download_url_express_ios").trim());
			map.put("versionDescription", prop.getProperty("version_description_ios").trim());
			map.put("projectName",prop.getProperty("project_name_express").trim());
			this.ehcacheService.put("appVersionInfoCacheOfExpress", "3", map);
			logger.info("map========"+map);
			logger.info("缓存=="+this.ehcacheService.get("appVersionInfoCacheOfExpress", "3", Map.class));
			return map;
		}
		return map;
	}
	@Override
	public void updateAppVersionInfo(Short appType) {
		if(appType==1){//好递快递员
			this.ehcacheService.removeAll("appVersionInfoCacheOfExpress");
		}else if(appType==2){//好递个人端
			this.ehcacheService.removeAll("appVersionInfoCacheOfPerson");
		}
	}
}
