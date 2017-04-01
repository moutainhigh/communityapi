package net.okdi.core.common.ehcache;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasExpressPrice;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.service.InitCacheService;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

public class EhcacheListener implements ServletContextListener {

	private Logger logger = Logger.getLogger(this.getClass());

	private TaskExecutor taskExecutor;// 注入Spring封装的异步执行器

	private EhcacheService ehcacheService;

	// 获取spring注入的bean对象
	private WebApplicationContext springContext;

	public void contextInitialized(ServletContextEvent event) {
		springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		taskExecutor = (TaskExecutor) springContext.getBean("taskExecutor");
		ehcacheService = (EhcacheService) springContext.getBean("ehcacheService");
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					// 缓存网络信息
					Long netStartTime = System.currentTimeMillis();
					List<BasNetInfo> netList = ((InitCacheService) springContext.getBean("initCacheService")).getNetList();
					for (int i = 0; i < netList.size(); i++) {
						BasNetInfo basNetInfo = netList.get(i);
						ehcacheService.put("netCache", String.valueOf(basNetInfo.getNetId()), JSON.toJSONString(basNetInfo));
					}
					Long netEndTime = System.currentTimeMillis();
					logger.info("-------------网络信息缓存执行时间-------------：" + (netEndTime - netStartTime) / 1000 + "秒");
					// 缓存公司信息
					Long compStartTime = System.currentTimeMillis();
					List<BasCompInfo> compList = ((InitCacheService) springContext.getBean("initCacheService")).getCompList();
					for (int i = 0; i < compList.size(); i++) {
						BasCompInfo entity = compList.get(i);
						ehcacheService.put("compCache", String.valueOf(entity.getCompId()), JSON.toJSONString(entity));
					}
					Long compEndTime = System.currentTimeMillis();
					logger.info("-------------公司信息缓存执行时间-------------：" + (compEndTime - compStartTime) / 1000 + "秒");
					// 缓存地址信息
					Long adresStartTime = System.currentTimeMillis();
					List<DicAddressaid> addressList = ((InitCacheService) springContext.getBean("initCacheService")).getAddressList();
					for (int i = 0; i < addressList.size(); i++) {
						DicAddressaid entity = addressList.get(i);
						ehcacheService.put("addressCache", String.valueOf(entity.getAddressId()), JSON.toJSONString(entity));
					}
					Long adresEndTime = System.currentTimeMillis();
					logger.info("-------------地址信息缓存执行时间-------------：" + (adresEndTime - adresStartTime) / 1000 + "秒");
					// 缓存网络价格信息
					Long priceStartTime = System.currentTimeMillis();
					List<BasExpressPrice> priceList = ((InitCacheService) springContext.getBean("initCacheService")).queryExpressPrice();
					for (int i = 0; i < priceList.size(); i++) {
						BasExpressPrice entity = priceList.get(i);
						ehcacheService.put("expressPriceCache", String.valueOf(entity.getNetId()+"-"+entity.getTakeProvince()+"-"+entity.getSendProvince()), JSON.toJSONString(entity));
					}
					Long priceEndTime = System.currentTimeMillis();
					logger.info("-------------网络运费缓存执行时间-------------：" + (priceEndTime - priceStartTime) / 1000 + "秒");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

}
