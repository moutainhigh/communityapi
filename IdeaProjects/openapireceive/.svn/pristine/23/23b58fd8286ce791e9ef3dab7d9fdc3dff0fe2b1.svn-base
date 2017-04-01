package net.okdi.outersrv.util;

import net.okdi.outersrv.service.CooperationCompanyService;

import java.util.HashMap;
import java.util.Map;

public class CoopCompServiceFactory {

    private static final Map<Long, CooperationCompanyService> cooperationCompanyServices = new HashMap<>();

    private CoopCompServiceFactory() {

    }

    public static CooperationCompanyService getService(Long netId) {
        return cooperationCompanyServices.get(netId);
    }

    public static void set(Long netId, CooperationCompanyService service) {
        cooperationCompanyServices.put(netId, service);
    }

    public static void set(Map<Long, CooperationCompanyService> serviceMap) {
        cooperationCompanyServices.putAll(serviceMap);
    }

}

