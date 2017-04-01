package net.okdi.mob.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV1.service.impl.ExpressUserServiceImpl;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.PassportHttpClient;
import net.okdi.mob.service.NetDotService;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NetDotServiceImpl implements NetDotService{

	@Autowired
	PassportHttpClient ucenterHttpClient;
	
	@Autowired
	OpenApiHttpClient openApiHttpClient;
	
	@Value("${compPic.savePath}")
	public String savePath;
	@Value("${compPic.readPath}")
	public String readPath;
	Logger log = Logger.getLogger(NetDotServiceImpl.class);
	@Override
	public String saveBelongingAuthentictaInfo(Long id, Long netId,
			String netName, Long compId, String compName, Long memberId,
			String memberName, String memberMobile, Double lng, Double lat,
			String memo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		map.put("netId",netId);
		map.put("netName",netName);
		map.put("compId",compId);
		map.put("compName",compName);
		map.put("memberId",memberId);
		map.put("memberName",memberName);
		map.put("memberMobile",memberMobile);
		map.put("lng",lng);
		map.put("lat",lat);
		map.put("memo",memo);
		return openApiHttpClient.doPassSendStr("netDotInfo/saveBelongingAuthentictaInfo", map);
	}
	

	@Override
	public String saveOrUpdateNetDotInfo(Long loginMemberId, Long loginCompId,
			String responsible, String responsibleTelephone,
			String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg,
			String holdImg, String firstSystemImg, String secondSystemImg,
			String thirdSystemImg, Short verifyType, Short compStatus) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loginMemberId",loginMemberId);
		map.put("loginCompId",loginCompId);
		map.put("responsible",responsible);
		map.put("responsibleTelephone",responsibleTelephone);
		map.put("responsibleNum",responsibleNum);
		map.put("businessLicenseImg",businessLicenseImg);
		map.put("frontImg",frontImg);
		map.put("reverseImg",reverseImg);
		map.put("holdImg",holdImg);
		map.put("firstSystemImg",firstSystemImg);
		map.put("secondSystemImg",secondSystemImg);
		map.put("thirdSystemImg",thirdSystemImg);
		map.put("verifyType",verifyType);
		map.put("compStatus",compStatus);
		return openApiHttpClient.doPassSendStr("netDotInfo/saveOrUpdateNetDotInfo", map);
	}

	@Override
	public String queryNetDotInfo(Long compId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("compId",compId);
		return openApiHttpClient.doPassSendStr("netDotInfo/queryNetDotInfo", map);
	}

	@Override
	public String updateNetDotStatus(Long compId, Short compStatus, Long auditId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("compId",compId);
		map.put("compStatus",compStatus);
		map.put("auditId",auditId);
		return openApiHttpClient.doPassSendStr("netDotInfo/updateNetDotStatus", map);
	}

//	@Override
//	public String record(Long memberId, String memberPhone, String channelNo, String regip, String deviceType,
//			String deviceToken) {
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("memberId",memberId.toString());
//		map.put("memberPhone",memberPhone);
//		map.put("channelNo",channelNo);
//		map.put("regip",regip);
//		map.put("deviceType",deviceType);
//		map.put("deviceToken",deviceToken);
//		return ucenterHttpClient.doPassSendStr("mobDeviceRecord/record", map);
//	}

	/**
	 * 	上传图片
	 */
	@Override
	public Map<String, Object> uploadImage(MultipartFile myfile) {
		try{
			log.info("上传站点认证照片开始！！！！！！！！！！！！！！");
			if (myfile.isEmpty()){
				throw new ServiceException("net.okdi.express.CompInfoServiceImpl.uploadImage.001", "图片上传参数异常");
			}else{
				Date date=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
				String time=sdf.format(date);
			    int random=(int)(Math.random()*60*1000);//随机数
			    time=time+random;
			    
//			    String fileName = myfile.getOriginalFilename();
//				String imgFileType = fileName.substring(fileName.lastIndexOf("."));
			    
			    String imgFileType = ".jpg";
			    
				String fileSavePath = savePath + File.separator + time + imgFileType;
				String fileShowPath = readPath + time + imgFileType;
				
				File file = new File(fileSavePath);
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), file);
				
				Map<String,Object> dataMap=new HashMap<String, Object>();
				dataMap.put("imageUrl", time+ imgFileType);
				log.info("站点认证照片imageUrl:"+time+ imgFileType);
			    dataMap.put("fileShowPath", fileShowPath);
			    log.info("上传站点认证照片结束！！！！！！！！！！！！！！");
			    return dataMap;
			}
		}catch(Exception e){
			throw new ServiceException("net.okdi.express.CompInfoServiceImpl.uploadImage.002", "图片上传异常");
		}
	}
	 /**
	  * 判断网络列表是否更新
	  */
	@Override
	public String validNetIsChanged(){
		Map<String,Object> map = new HashMap<String,Object>();
		return openApiHttpClient.doPassSendStr("netInfo/validNetIsChanged", map);
	}
}
