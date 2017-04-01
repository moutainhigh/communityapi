package net.okdi.outersrv.util;

import net.okdi.core.common.quartz.ScheduleJobInitListener;
import net.okdi.outersrv.service.CooperationCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OuterCompanySerivceInitListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleJobInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());

        Map<Long, CooperationCompanyService> services = new HashMap<>();
        Map<String, CooperationCompanyService> serviceMap = ctx.getBeansOfType(CooperationCompanyService.class);
        for (Iterator<String> iter = serviceMap.keySet().iterator(); iter.hasNext();) {
            CooperationCompanyService ccService = serviceMap.get(iter.next());
            Long netId = ccService.getNetId();
            if (netId == null) {
                continue;
            }
            services.put(netId, ccService);
            LOGGER.info("接入快递公司, netId: " + netId + ", service: " + ccService);
        }
        CoopCompServiceFactory.set(services);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
