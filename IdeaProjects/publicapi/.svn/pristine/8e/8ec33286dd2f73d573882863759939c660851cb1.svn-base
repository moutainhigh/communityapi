/**  
 * @Project: mob
 * @Title: CommonServiceImpl.java
 * @Package net.okdi.mob.service.impl
 * @Description: TODO
 * @author chuanshi.chai
 * @date 2014-11-8 上午9:14:42
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.okdi.api.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.GetPicConPath;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.CommonService;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName CommonServiceImpl
 * @Description TODO
 * @author chuanshi.chai
 * @date 2014-11-8
 * @since jdk1.6
*/
@Service("commonService")
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Autowired
	private ConstPool constPool;
	
	private static Logger logger = Logger.getLogger(CommonServiceImpl.class);
	/**
	 * @Method: uploadPic 
	 * @Description: TODO
	 * @param realPath contact：联系人 front：身份证前身照  back：身份证背面照 inHand：手持 head：接单王头像 photoPar:包裹拍照
	 * @param memberId
	 * @param myfiles
	 * @return 
	 * @see net.okdi.mob.service.ExpParGatewayService#uploadPic(java.lang.Long, org.springframework.web.multipart.MultipartFile[]) 
	 * @since jdk1.6
	*/
	@Override
	public String uploadPic(String type, Long MemberId, MultipartFile[] myfiles) {
		if(myfiles==null||myfiles.length==0)
			return "";
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
		}else{
			Map map = new HashMap();
			map.put("msg", "更新类型不匹配");
			return JSON.toJSONString(map);
		}
		//String filePath = constPool.getSavePath()+realPath;
		File f = new File(filePath+typePath);
		if(!f.exists()){
			f.mkdirs();
		}
		Map m = new HashMap();
		for (MultipartFile myfile : myfiles) {
			String	picName = GetPicConPath.getPicPath(filePath+typePath+MemberId+".jpg", "write");
//			System.out.println(picName);
			if (myfile.isEmpty()) {
				System.out.println("文件为空");
			} else {
				try {
					FileUtils.copyInputStreamToFile(myfile.getInputStream(),
							new File(picName));
					m.put("upload", "true");
//					System.out.println(readPath+typePath+MemberId+".jpg");
					File ifExist = new File(filePath+typePath+MemberId+".jpg");
					if(ifExist.exists()){
						m.put("url",readPath+typePath+MemberId+".jpg");
					} else {
						m.put("url","");
					}
					return JSON.toJSONString(m);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		m.put("upload", "false");
		return JSON.toJSONString(m);
	}
	
	@Override
	public String delFile(Long MemberId,String type) {
		Map<String,Object> ResultMessageMap = new HashMap<String,Object>();
		String filePath = constPool.getSavePath();
		if ("contact".equals(type)) {
			filePath += constPool.getContact();
		}else if("front".equals(type)){
			filePath += constPool.getFront();
		}else if("back".equals(type)){
			filePath += constPool.getBack();
		}else if("inHand".equals(type)){
			filePath += constPool.getInHand();
		}else if("head".equals(type)){
			filePath += constPool.getHead();
		}else if("photoPar".equals(type)){
			filePath += constPool.getPhotoPar();
		}else if("net".equals(type)){
			filePath += constPool.getNet();
		}else{
			ResultMessageMap.put("success",false);
			ResultMessageMap.put("msg","文件类型不匹配");
			return JSON.toJSONString(ResultMessageMap);
		}
		File file = new File(filePath+MemberId+".jpg");  
		if (!file.exists()) {
			ResultMessageMap.put("success",false);
			ResultMessageMap.put("msg","文件不存在");
			return JSON.toJSONString(ResultMessageMap);
		  } else {  
				   if (file.delete()) {
					 	ResultMessageMap.put("success",true);
						return JSON.toJSONString(ResultMessageMap);
				   }else {
						ResultMessageMap.put("success",false);
						ResultMessageMap.put("msg","删除失败");
						return JSON.toJSONString(ResultMessageMap);
				   }
		  	}
	}

	@Override
	public String queryPhotoByid(Long memberId, String type) {
		Map<String,Object> ResultMessageMap = new HashMap<String,Object>();
		String ReadPath = constPool.getReadPath();
		if ("contact".equals(type)) {
			ReadPath += constPool.getContact();
		}else if("front".equals(type)){
			ReadPath += constPool.getFront();
		}else if("back".equals(type)){
			ReadPath += constPool.getBack();
		}else if("inHand".equals(type)){
			ReadPath += constPool.getInHand();
		}else if("head".equals(type)){
			ReadPath += constPool.getHead();
		}else if("photoPar".equals(type)){
			ReadPath += constPool.getPhotoPar();
		}else if("net".equals(type)){
			ReadPath += constPool.getNet();
		}else if("Requestphoto_jdw".equals(type)){
			ResultMessageMap.put("success",true);
			if(ifExsit(ReadPath+constPool.getFront()+memberId+".jpg")){
				ResultMessageMap.put("Front",ReadPath+constPool.getFront()+memberId+".jpg");
			} else {
				ResultMessageMap.put("Front","");
			}
			System.out.println(ReadPath+constPool.getBack()+memberId+".jpg");
			if(ifExsit(ReadPath+constPool.getBack()+memberId+".jpg")){
				ResultMessageMap.put("Back",ReadPath+constPool.getBack()+memberId+".jpg");
			} else {
				ResultMessageMap.put("Back","");
			}
			if(ifExsit(ReadPath+constPool.getInHand()+memberId+".jpg")){
				ResultMessageMap.put("InHand",ReadPath+constPool.getInHand()+memberId+".jpg");
			} else {
				ResultMessageMap.put("InHand","");
			}
			return JSON.toJSONString(ResultMessageMap);
		}else{
			ResultMessageMap.put("success",false);
			ResultMessageMap.put("msg","文件类型不匹配");
			return JSON.toJSONString(ResultMessageMap);
		}
		ResultMessageMap.put("success",true);
		ResultMessageMap.put("msg",ReadPath+memberId+".jpg");
		return JSON.toJSONString(ResultMessageMap);
	}
	public boolean ifExsit(String url)  {
		try {  
			URL urlStr = new URL(url);  
			HttpURLConnection connection = (HttpURLConnection) urlStr.openConnection();  
			int state = connection.getResponseCode();  
			if (state == 200) {  
				return true;
			} else {
			 return false;
			} 
		} catch (Exception ex) {  
	 		throw new ServiceException("查询图片路径异常");
		}
		}  
	
	@Override
	public String uploadSound(Long memberId, MultipartFile[] myfiles) {
		if(myfiles==null||myfiles.length==0)
			return "";
		String typePath = "";
		String filePath = constPool.getSavePath();
		String readPath = constPool.getReadPath();
		typePath += constPool.getSound();
		//String filePath = constPool.getSavePath()+realPath;
		File f = new File(filePath+typePath);
		if(!f.exists()){
			f.mkdirs();
		}
		Date now = new Date();
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = sdf.format(now);
		//String fileName = memberId+"-"+dateString;
		
		String basSoundName = filePath + typePath + memberId +dateString;
		
		for (MultipartFile myfile : myfiles) {
			String fileName = myfile.getOriginalFilename();
			String fileExt = fileName.substring(fileName.length()-3, fileName.length());
			String sourceName = GetPicConPath.getPicPath(basSoundName+"."+ fileExt, "write");
			try {
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(sourceName));
				
				File rmaFile = new File(basSoundName +"."+ fileExt);
				if(rmaFile.exists()){
					//String targetName = basSoundName+ ".mp3";
					//converMap3(rmaFile,new File(targetName),"mp3");
					boolean upSuccess = toMp3(basSoundName +"."+ fileExt);
					if(upSuccess)
						return readPath + typePath + memberId +dateString+ ".mp3"+"***"+readPath + typePath + memberId +dateString+ ".mp3";
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	@Override
	public String delSound(Integer day) {
		if(PubMethod.isEmpty(day)){
//			day = 7;
			day = 31;
		}
		final long delTime =7 * 24 * 60 * 60 * 1000;
		
		String filePath = constPool.getSavePath();//D:/data/nfs_data/mob/
		String typePath = constPool.getSound();//sound/
		File f = new File(filePath+typePath);
		f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				long start = pathname.lastModified();
				long end = System.currentTimeMillis();
				long t = end - start;
				if (t > delTime) {
					try {
						pathname.delete();
					} catch (Exception e) {
					}     
					return true;
				}
				return false;
			}
		});

		return "";

	}
	
	public static boolean toMp3(String sourcePath){  

	      String targetPath = sourcePath.substring(0,sourcePath.length()-3)+"mp3";//转换后文件的存储地址，直接将原来的文件名后加mp3后缀名  
	      Runtime run = null;    
	      try {    
	          run = Runtime.getRuntime();    
	          long start=System.currentTimeMillis();
	          logger.debug("源文件路径："+sourcePath);
	          logger.debug("目标路径："+targetPath);
	          logger.debug("执行的命令：： "+"/usr/local/ffmpeg/bin/ffmpeg -i "+sourcePath+" -acodec libmp3lame "+targetPath);
	          Process p =null;
	          try{
	        	  //p=run.exec("E:/WorkMyElps10/ZMusicTest/files/ffmpeg -i "+sourcePath+" -acodec libmp3lame "+targetPath);//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
	        	  p=run.exec("/usr/local/ffmpeg/bin/ffmpeg -i "+sourcePath+" -acodec libmp3lame "+targetPath);//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame  
	          //释放进程    
	          }catch(Exception e){
	        	  e.printStackTrace();
	          }
	          //p.getOutputStream().close();    
	          //p.getInputStream().close();    
	          //p.getErrorStream().close();    
	          //p.waitFor();    
	          int waitFor = p.waitFor();
	          logger.debug("程序运行状态："+waitFor);
	          long end=System.currentTimeMillis();    
	          logger.debug(sourcePath+" convert success, costs:"+(end-start)+"ms");    
	          //删除原来的文件    
	          //if(file.exists()){    
	              //file.delete();    
	          //}    
	      } catch (Exception e) {    
	          e.printStackTrace();    
	          return false;
	      }finally{    
	          run.freeMemory();    
	      }  
	      return true;
	  }
	
	public static void main(String[] args) {
		toMp3("D:/data/nfs_data/mob/sound/12071113619865620150522201055.caf");
	}
}