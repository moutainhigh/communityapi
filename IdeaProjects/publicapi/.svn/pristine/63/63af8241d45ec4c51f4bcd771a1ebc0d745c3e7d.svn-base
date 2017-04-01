package net.okdi.apiV4.service.impl;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.serializer.LongArraySerializer;


import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.apiV4.service.ReceiveOrderFromWebsiteService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.apiV4.service.TaskRemindService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;


@Service
public class ReceiveOrderFromWebsiteServiceImpl extends BaseServiceImpl implements
		ReceiveOrderFromWebsiteService {

	private static Logger logger = Logger
			.getLogger(ReceiveOrderFromWebsiteServiceImpl.class);
	

	@Value("${courier.down.http}")
	private String courierDownHttp;

	// 叫快递调用取派件新项目
	@Value("${opencall}")
	private String opencall;
	

	@Autowired
	private RedisService redisService;

	@Autowired
	private OpenApiHttpClient openApiHttpClient;

	// 用户头像
	@Value("${headImgPath}")
	private String headImgPath;

	@Autowired
	private AssignPackageService assignPackage;

	@Autowired
	private ConstPool constPool;
	@Override
	public BaseDao getBaseDao() {
		// TODO Auto-generated method stub
		return null;
	}

	//查询(电商件)
	@Override
	public String queryTakePackListFromWebsite(String userCode, String orgCode, String memberId, String netId) {
		//判断快递员的账号是否有授权
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userCode", userCode);
		map.put("orgCode", orgCode);
		map.put("memberId", memberId);
		map.put("netId", netId);
		String result = openApiHttpClient.ParcelFromGT("ReceiveOrderFromWebsite/queryTakePackListFromWebsite", map);
		return result;
	}

	//保存来自国通的电商件
	@Override
	public String saveParcelFromGT(String parcels,String memberId, String netId) {
		//判断快递员的账号是否有授权
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("netId", netId);
		map.put("parcels", parcels);
		
		String result = openApiHttpClient.ParcelFromGT("ReceiveOrderFromWebsite/saveParcelFromGT", map);
		return result;
	}
	//保存来自国通的电商件
	@Override
	public String QueryParcelFromGT(long memberId, long netId, int currentPage, int pageSize) {
		//判断快递员的账号是否有授权
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("netId", netId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String result = openApiHttpClient.ParcelFromGT("ReceiveOrderFromWebsite/QueryParcelFromGT", map);
		return result;
	}
	//确认取件(电商件)
	@Override
	public String confirmTakeParcelGT(String uids, String memberId, String netId, String terminalId, String versionId) {
		//判断快递员的账号是否有授权
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("netId", netId);
		map.put("uids", uids);
		map.put("terminalId", terminalId);
		map.put("versionId", versionId);
		String result = openApiHttpClient.ParcelFromGT("ReceiveOrderFromWebsite/confirmTakeParcelGT", map);
		return result;
	}
	//
	@Override
	public String huntParcelGT(String mobile, long memberId, long netId, int currentPage, int pageSize, String flag) {
		//判断快递员的账号是否有授权
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile);
		map.put("netId", netId);
		map.put("memberId", memberId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		map.put("flag", flag);
		String result = openApiHttpClient.ParcelFromGT("ReceiveOrderFromWebsite/huntParcelGT", map);
		return result;
	}

	


	
}