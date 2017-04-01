package net.okdi.apiV1.service;

import java.util.List;
import java.util.Map;

public interface NearComInfoService {

	

	List<Map<String, Object>> queryCompInfo(Double longitude, Double latitude);

}
