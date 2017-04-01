package net.okdi.apiV2.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.NetInfoApplyService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class NetInfoApplyServiceImpl implements NetInfoApplyService{
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private ConstPool constPool;
	@Override
	public String insertNetInfoApply(String memberId,String netName,String telphone,MultipartFile file){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("netName", netName);
		map.put("telphone", telphone);
		
		String rs= this.openApiHttpClient.doPassSendStr("/netInfoApply/insertNetInfoApply", map);
		String filePath = constPool.getNetSavePath();
		String savePath = filePath + rs +".png"; 
		uploadImage(file, savePath, constPool.getNetSavePath());
		return rs;
	}
	
	private void uploadImage(MultipartFile addFile,String savePath,String filePath){
		if (addFile == null || addFile == null || addFile.isEmpty()) {
			System.out.println("文件为空");
		} else {
			try {
				File f = new File(filePath);
				if (!f.exists()) {
					f.mkdirs();
				}
				FileUtils.copyInputStreamToFile(addFile.getInputStream(),
						new File(savePath));
				System.out.println(savePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
