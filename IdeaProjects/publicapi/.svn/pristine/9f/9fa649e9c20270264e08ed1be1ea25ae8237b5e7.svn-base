package net.okdi.apiV1.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.okdi.api.exception.ServiceException;
import net.okdi.apiV1.controller.ExpressUserController;
import net.okdi.apiV1.service.ExpressUserService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.GetPicConPath;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

@Service
public class ExpressUserServiceImpl implements ExpressUserService {
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private ConstPool constPool;
	Logger log = Logger.getLogger(ExpressUserServiceImpl.class);
	@Override
	public String updateMemberInfoAndAudit(String memberName, String idNum,String memberId,String compId,String roleType,String gender, String birthday,String address) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberName",memberName);
		map.put("idNum",idNum);
		map.put("memberId",memberId);
		map.put("compId",compId);
		map.put("roleType",roleType);
		map.put("gender", gender);
		map.put("birthday", birthday);
		map.put("address", address);
		String response = openApiHttpClient.doPassSendStr("expressUser/updateMemberInfoAndAudit/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.updateMemberInfoAndAudit.001","数据请求异常");
		}
		return response;
	}
	
	@Override
	public String upLoadPhotoInfo(String type, String memberId,MultipartFile[] myfiles) {
		log.info("开始上传文件！！！！！！！！！！！！！！！！！！！！！！！！！！！");
		if(myfiles==null||myfiles.length==0){
			log.info("文件为空！");
			return "";
		}
		String typePath = "";
		String filePath = constPool.getSavePath();
		String readPath = constPool.getReadPath();
		if ("contact".equals(type)) {
			typePath += constPool.getContact();
		}else if("front".equals(type)){
			typePath += constPool.getFront();
		}else if("back".equals(type)){
			typePath += constPool.getBack();
		}else if("inHand".equals(type)){
			typePath += constPool.getInHand();
		}else if("head".equals(type)){
			typePath += constPool.getHead();
		}else if("photoPar".equals(type)){
			typePath += constPool.getPhotoPar();
		}else if("net".equals(type)){
			typePath += constPool.getNet();
		}else if("me".equals(type)){
			typePath += constPool.getMe();
		}else{
			Map map = new HashMap();
			map.put("msg", "更新类型不匹配");
			return JSON.toJSONString(map);
		}
		log.info("图片路径："+typePath+"-"+filePath+"-"+readPath);
		//String filePath = constPool.getSavePath()+realPath;
		File f = new File(filePath+typePath);
		if(!f.exists()){
			log.info("目录不存在创建目录："+filePath+typePath);
			f.mkdirs();
		}
		Map m = new HashMap();
		log.info("文件名字："+myfiles.length);
		for (MultipartFile myfile : myfiles) {
			String	picName = GetPicConPath.getPicPath(filePath+typePath+memberId+".jpg", "write");
			System.out.println(picName);
			log.info("文件名字："+picName);
			if (myfile.isEmpty()) {
				System.out.println("文件为空");
			} else {
				try {
					FileUtils.copyInputStreamToFile(myfile.getInputStream(),
							new File(picName));
					m.put("upload", "true");
//					System.out.println(readPath+typePath+MemberId+".jpg");
					File ifExist = new File(filePath+typePath+memberId+".jpg");
					log.info("文件copy成功："+ifExist);
					if(ifExist.exists()){
						m.put("url",readPath+typePath+memberId+".jpg");
					} else {
						m.put("url","");
					}
					log.info("返回结果true："+m);
					return JSON.toJSONString(m);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		m.put("upload", "false");
		log.info("返回结果false："+m);
		log.info("结束上传文件！！！！！！！！！！！！！！！！！！！！！！！！！！！");
		return JSON.toJSONString(m);
	}
	
	@Override
	public String updateMemberInfoAndAuditJump(String memberId,String compId,String roleType,String memberName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("memberId", memberId);
		params.put("compId", compId);
		params.put("roleType", roleType);
		params.put("memberName", memberName);
		String response = openApiHttpClient.doPassSendStr("expressUser/updateMemberInfoAndAuditJump/", params);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.updateMemberInfoAndAuditJump.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String isIdNumRepeat(String idNum,String memberPhone) {
		if (PubMethod.isEmpty(idNum)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.isIdNumRepeat.001", "idNum参数异常");
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("idNum", idNum);
		params.put("memberPhone", memberPhone);
		String response = openApiHttpClient.doPassSendStr("expressUser/isIdNumRepeat/", params);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.isIdNumRepeat.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String guishuAudit(String memberId, String status) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.guishuAudit.001", "memberId参数异常");
		}
		if (PubMethod.isEmpty(status)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.guishuAudit.002", "status参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("status", status);
		String response = openApiHttpClient.doPassSendStr("expressUser/guishuAudit/", param);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.guishuAudit.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String queryNetInfo() {
		String response = openApiHttpClient.doPassSendStr("expressUser/queryNetInfo/", null);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.queryNetInfo.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String saveExpressAudit(String memberId, String expressNumStr,String compId,String roleType) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAudit.001", "memberId参数异常");
		}
		if (PubMethod.isEmpty(expressNumStr)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAudit.002", "expressNumStr参数异常");
		}
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAudit.003", "compId参数异常");
		}
		if (PubMethod.isEmpty(roleType)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAudit.004", "roleType参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("expressNumStr", expressNumStr);
		param.put("compId", compId);
		param.put("roleType", roleType);
		String response = openApiHttpClient.doPassSendStr("expressUser/saveExpressAudit/", param);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAudit.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String saveExpressAuditJump(String memberId, String compId,String roleType) {
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAudit.001", "memberId参数异常");
		}
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAudit.002", "compId参数异常");
		}
		if (PubMethod.isEmpty(roleType)) {
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAudit.003", "roleType参数异常");
		}
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		param.put("compId", compId);
		param.put("roleType", roleType);
		String response = openApiHttpClient.doPassSendStr("expressUser/saveExpressAuditJump/", param);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.saveExpressAuditJump.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String queryExpressAuditStatusByMemberId(String memberId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		String response = openApiHttpClient.doPassSendStr("expressUser/queryExpressAuditStatusByMemberId/", param);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.queryExpressAuditStatusByMemberId.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String queryRealNameAuditInfo(String memberId) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("memberId", memberId);
		String response = openApiHttpClient.doPassSendStr("expressUser/queryRealNameAuditInfo/", param);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV1.service.impl.ExpressUserServiceImpl.queryRealNameAuditInfo.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String completeMemberInfo(String memberId, String nickName, String gender, String birthday) {
		log.info("--------------completeMemberInfo()---------完善个人信息---------");
		Map<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("nickName", nickName);
		map.put("gender", gender);
		map.put("birthday", birthday);	
		String methodName = "expressUser/completeMemberInfo";
		String result = openApiHttpClient.doPassSendStr(methodName,map);
		return result;
	}
	
	@Override
	public String isBoundWechat(String weNumber) {
		log.info("微信登录:publicapi-->ExpressUserServiceImpl--->isBoundWechat()");
		Map<String , String> map = new HashMap<>();
		map.put("weNumber", weNumber);
		String methodName = "expressUser/isBoundWechat";
		String result = openApiHttpClient.doPassSendStr(methodName, map);
		return result;
	}
	
	@Override
	public String unlockWechat(String memberId) {
		log.info("解绑微信号:publicapi-->ExpressUserServiceImpl--->unlockWechat()");
		Map<String , String> map = new HashMap<>();
		map.put("memberId", memberId);
		String methodName = "expressUser/unlockWechat";
		String result = openApiHttpClient.doPassSendStr(methodName, map);
		return result;
	}

	@Override
	public String isBoundWx(String memberId) {
		log.info("是否绑定微信:publicapi-->ExpressUserServiceImpl--->isBoundWechat()");
		Map<String , String> map = new HashMap<>();
		map.put("memberId", memberId);
		String methodName = "expressUser/isBoundWx";
		String result = openApiHttpClient.doPassSendStr(methodName, map);
		return result;
	}

	@Override
	public String insertWeChat(String memberId, String weNumber, String weName) {
		log.info("绑定微信:publicapi-->ExpressUserServiceImpl--->insertWeChat()");
		Map<String , String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("weNumber", weNumber);
		map.put("weName", weName);
		String methodName = "expressUser/insertWeChat";
		String result = openApiHttpClient.doPassSendStr(methodName, map);
		return result;
	}

	@Override
	public String insertPhoneWeChat(String memberPhone, String weNumber, String weName) {
		log.info("已注册绑定微信:publicapi-->ExpressUserServiceImpl--->insertPhoneWeChat()");
		Map<String , String> map = new HashMap<>();
		map.put("memberPhone", memberPhone);
		map.put("weNumber", weNumber);
		map.put("weName", weName);
		String methodName = "expressUser/insertPhoneWeChat";
		String result = openApiHttpClient.doPassSendStr(methodName, map);
		return result;
	}
	
	
}
