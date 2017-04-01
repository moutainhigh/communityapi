package net.okdi.apiV4.vo;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;

import net.okdi.apiV4.vo.ResultCode.Success;

public class CommonResponse extends HashMap<String, Object>{

	private static final long serialVersionUID = -3249228464778661971L;

	public static final String RC = "rc";
	
	public static final String ERR = "err";
	
	private CommonResponse() {
		
	}
	
	public static CommonResponse get() {
		CommonResponse resp = new CommonResponse();
		resp.put(RC, Success.OK);
		resp.put(ERR, "");
		return resp;
	}
	
	public static CommonResponse get(String rc) {
		CommonResponse resp = new CommonResponse();
		resp.put(RC, rc);
		resp.put(ERR, "");
		return resp;
	}
	
	public static CommonResponse get(String rc, String err) {
		CommonResponse resp = new CommonResponse();
		resp.put(RC, rc);
		resp.put(ERR, err);
		return resp;
	}
	
	public CommonResponse put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	public CommonResponse rc(Object value) {
		put(RC, value);
		return this;
	}
	
	public CommonResponse err(Object value) {
		put(ERR, value);
		return this;
	}
	
	public CommonResponse append(Map<String, Object> map) {
		super.putAll(map);
		return this;
	}
	
	public static void main(String[] args) {
		CommonResponse resp = CommonResponse.get();
		CommunityVO vo = new CommunityVO();
		vo.setCommunityAddress("北京市");
		vo.setCommunityIntroduce("群公告");
		vo.setCreator(IdWorker.getIdWorker().nextId());
		resp.put("member", vo);
		
		System.out.println(JSON.toJSONString(resp));
	}
}







