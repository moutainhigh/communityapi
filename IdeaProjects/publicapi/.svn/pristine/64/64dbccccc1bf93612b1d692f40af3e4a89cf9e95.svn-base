package net.okdi.apiV4.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amssy.common.util.primarykey.IdWorker;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import net.okdi.apiV4.service.CardService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.CreatePh;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;
@Service
public class CardServiceImpl extends AbstractHttpClient implements CardService {

	@Autowired
	private ConstPool constPool;
	
	private @Autowired OpenApiHttpClient openApiHttpClient;
	
	private Logger logger=Logger.getLogger(CardServiceImpl.class);

	@Override
	public String cardReadCountAdd(Long cardId) {
		Map<String,String> params = new HashMap<String,String>();
		String methodName = "cardController/cardReadCountAdd";
		String url=constPool.getCommunityApiUrl()+methodName;
		params.put("cardId", String.valueOf(cardId));
		return Post(url, params);
	}

	@Override
	public String cardLikeCountAdd(Long cardId, Long memberId,String type) {
		Map<String,String> params = new HashMap<String,String>();
		String methodName = "cardController/cardLikeCountAdd";
		String url=constPool.getCommunityApiUrl()+methodName;
		params.put("cardId", String.valueOf(cardId));
		params.put("memberId", String.valueOf(memberId));
		params.put("type", type);
		return Post(url, params);
	}

	@Override
	public String queryCardDetial(Long cardId, Long memberId,Integer currentPage,Integer pageCount) {
		Map<String,String> params = new HashMap<String,String>();
		String methodName = "cardController/queryCardDetial";
		String url=constPool.getCommunityApiUrl()+methodName;
		params.put("cardId", String.valueOf(cardId));
		params.put("memberId", String.valueOf(memberId));
		params.put("currentPage", String.valueOf(currentPage));
		params.put("pageCount", String.valueOf(pageCount));
		return Post(url, params);
	}

	@Override
	public String queryHotCard(Long memberId, Integer currentPage,
			Integer pageCount) {
		Map<String,String> params = new HashMap<String,String>();
		String methodName = "cardController/queryHotCard";
		String url=constPool.getCommunityApiUrl()+methodName;
		params.put("memberId", String.valueOf(memberId));
		params.put("currentPage", String.valueOf(currentPage));
		params.put("pageCount", String.valueOf(pageCount));
		return Post(url, params);
	}
	@Override
	public String publishCard1(String createorMemberId, String type,
			String source, String content, List<MultipartFile> video,String creatorName) {
		logger.info("createorMemberId:"+createorMemberId+"type:"+type+"content:"+content);
		//上传四张图片的路径
		String filePath = constPool.getSavePath();// /data/nfs_data/okdiLife/showBar/
		String cardPath=constPool.getCardUrl();
		String readPath=constPool.getReadPath();
		//上传四张图片的文件名
		File cardFile = new File(filePath+cardPath);
		
		//判断文件是否存在，如果文件不存在则创建一个文件夹
		if(!cardFile.exists()){
			cardFile.mkdirs();
		}
		StringBuilder imageUrls=new StringBuilder();
		StringBuilder simageUrls=new StringBuilder();
		Long cardId=IdWorker.getIdWorker().nextId();
		//四张原始图片保存路径
		String videoPath="";
		String videoPicture="";
		String firstSavePath1=filePath+cardPath+3+"_"+cardId+"_1.jpg";
		String firstSavePath2=filePath+cardPath+3+"_"+cardId+"_2.jpg";
		String firstSavePath3=filePath+cardPath+3+"_"+cardId+"_3.jpg";
		String firstSavePath4=filePath+cardPath+3+"_"+cardId+"_4.jpg";
		String[] savepath={firstSavePath1,firstSavePath2,firstSavePath3,firstSavePath4};
		
		String firstSavePath11=filePath+cardPath+"s_"+cardId+"_1.jpg";
		String firstSavePath22=filePath+cardPath+"s_"+cardId+"_2.jpg";
		String firstSavePath33=filePath+cardPath+"s_"+cardId+"_3.jpg";
		String firstSavePath44=filePath+cardPath+"s_"+cardId+"_4.jpg";
		String[] smallSavePath={firstSavePath11,firstSavePath22,firstSavePath33,firstSavePath44};
//		logger.info("UtilGm方法初始化之前。。。。。。。。。。。。。。。。。。");
//		UtilGm imageUtil = new UtilGm();
		logger.info("UtilGm方法初始化之后。。。。。。。。。。。。。。。。。。");
		int i=0;
		for(MultipartFile mpf:video){
			logger.info("打印下上传的是图片还是视频。。。。。。。。。。"+mpf.getContentType());
			if(mpf.getContentType().contains("image")){//图片上传
				uploadPic(mpf, savepath[i], filePath+cardPath, true);
				//改变图形大小:压缩原图片一定比例:变成最小图片
				logger.info("图片1："+savepath[i]+",图片2："+smallSavePath[i]);
//				Boolean resulet=imageUtil.zoomImage(savepath[i], smallSavePath[i], 200, 200);
//				Boolean resulet=CreatePh.transfer(savepath[i], smallSavePath[i], "0");
//				logger.info("剪切图片返回值："+resulet);
				yasuoPicture(savepath[i], smallSavePath[i], mpf);
				i++;
				imageUrls.append(readPath+cardPath+3+"_"+cardId+"_"+i+".jpg"+",");
				simageUrls.append(readPath+cardPath+"s_"+cardId+"_"+i+".jpg"+",");
			}else if(mpf.getContentType().contains("video")){
				
				if(source.equalsIgnoreCase("ios")){
					videoPath=filePath+cardPath+"_video_"+cardId+"_.mov";
				}else{
					videoPath=filePath+cardPath+"_video_"+cardId+"_.mp4";
				}
				uploadPic(mpf, videoPath, filePath+cardPath, false);
				String ffmpeg=filePath+cardPath+"_video_"+cardId+"_.jpg";
				
//				boolean flag = CreatePh.processImg(videoPath, "/usr/local/bin/ffmpeg");
				boolean flag = CreatePh.transfer(videoPath, ffmpeg,"0");
				
				videoPicture=readPath+cardPath+"_video_"+cardId+"_.jpg";
				logger.info("视频截图图片url："+videoPicture);
				if(source.equalsIgnoreCase("ios")){
					videoPath=readPath+cardPath+"_video_"+cardId+"_.mov";
				}else{
					videoPath=readPath+cardPath+"_video_"+cardId+"_.mp4";
				}
			}
		}
		Map<String,String> params = new HashMap<String,String>();
		String methodName = "cardController/publishCard";
		String url=constPool.getCommunityApiUrl()+methodName;
		params.put("cardId", String.valueOf(cardId));
		params.put("createorMemberId",createorMemberId);
		params.put("type", type);
		params.put("content", content);
		params.put("imageUrl",imageUrls.toString()==null ? "" : imageUrls.toString());
		params.put("videoUrl", videoPath==null?"":videoPath);
		params.put("creatorName", creatorName);
		params.put("videoPicture", videoPicture==null?"":videoPicture);
		params.put("simageUrl",simageUrls.toString()==null ? "" : simageUrls.toString());
		return Post(url, params);
		
	}
	@Override
	public String publishCard(Long createorMemberId, String type,String source,
			String content, MultipartFile image1, MultipartFile image2,
			MultipartFile image3, MultipartFile image4, MultipartFile video) {
		//上传四张图片的路径
				String filePath = constPool.getSavePath();// /data/nfs_data/okdiLife/showBar/
				String cardPath=constPool.getCardUrl();
				String readPath=constPool.getReadPath();
				//上传四张图片的文件名
				File cardFile = new File(filePath+cardPath);
				
				//判断文件是否存在，如果文件不存在则创建一个文件夹
				if(!cardFile.exists()){
					cardFile.mkdirs();
				}
				StringBuilder imageUrls=new StringBuilder();
				Long cardId=IdWorker.getIdWorker().nextId();
				//四张原始图片保存路径
				String videoPath="";
				String firstSavePath=filePath+cardPath+3+"_"+cardId+"_1.jpg";
				String secondSavePath=filePath+cardPath+3+"_"+cardId+"_2.jpg";
				String thirdSavePath=filePath+cardPath+3+"_"+cardId+"_3.jpg";
				String fourthSavePath=filePath+cardPath+3+"_"+cardId+"_4.jpg";
				
				if(!image1.isEmpty()){
					uploadPic(image1, firstSavePath, filePath+cardPath, true);
					imageUrls.append(readPath+cardPath+3+"_"+cardId+"_1.jpg"+",");
				}
				if(!image2.isEmpty()){
					uploadPic(image2, secondSavePath, filePath+cardPath, true);
					imageUrls.append(readPath+cardPath+3+"_"+cardId+"_2.jpg"+",");
				}
				if(!image3.isEmpty()){
					uploadPic(image3, thirdSavePath, filePath+cardPath, true);
					imageUrls.append(readPath+cardPath+3+"_"+cardId+"_3.jpg"+",");
				}
				if(!image4.isEmpty()){
					uploadPic(image4, fourthSavePath, filePath+cardPath, true);
					imageUrls.append(readPath+cardPath+3+"_"+cardId+"_4.jpg");
				}
				if(!video.isEmpty()){
					if(source.equalsIgnoreCase("ios")){
						videoPath=filePath+cardPath+"_video_"+cardId+"_.mov";
					}else{
						videoPath=filePath+cardPath+"_video_"+cardId+"_.mp4";
					}
					uploadPic(video, videoPath, filePath+cardPath, false);
					
					if(source.equalsIgnoreCase("ios")){
						videoPath=readPath+cardPath+"_video_"+cardId+"_.mov";
					}else{
						videoPath=readPath+cardPath+"_video_"+cardId+"_.mp4";
					}
				}
				Map<String,String> params = new HashMap<String,String>();
				String methodName = "cardController/publishCard";
				String url=constPool.getCommunityApiUrl()+methodName;
				params.put("cardId", String.valueOf(cardId));
				params.put("createorMemberId",String.valueOf( createorMemberId));
				params.put("type", type);
				params.put("imageUrl",imageUrls.toString()==null ? "" : imageUrls.toString());
				params.put("videoUrl", videoPath==null?"":videoPath);
				return Post(url, params);
	}
	
	private String uploadPic(MultipartFile myfile, String savePath, String filePath, boolean isPic){
		/*if (myfile.isEmpty()) {
			System.out.println("文件为空");
			return "fasle";
		} else {
			try {
				File f = new File(filePath);
				if(!f.exists()){
					f.mkdirs();
				}
				FileUtils.copyInputStreamToFile(myfile.getInputStream(),
						new File(savePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "true";
		}*/
		if (myfile.isEmpty()) {
			System.out.println("文件为空");
			return "fasle";
		}
		if (!isPic) {
			try {
				File f = new File(filePath);
				if(!f.exists()){
					f.mkdirs();
				}
				FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(savePath));
			} catch (IOException e) {
				e.printStackTrace();
				return "false";
			}
			return "true";
		}
		try {
			byte[] bs = myfile.getBytes();
	
			// 原图
			logger.info("原图大小 => " + bs.length);
			
			boolean isGt4Mb = bs.length >= 4194304;
			boolean isGt2Mb = bs.length >= 2097152 && bs.length < 4194304;
			boolean isGt1mb = bs.length < 2097152 && bs.length >= 1048576;
		
			InputStream ins = myfile.getInputStream();
			if (isGt1mb) {
                doCompress(ins, 0.4f, savePath);
            } else if (isGt2Mb) {
                doCompress(ins, 0.2f, savePath);
            } else if (isGt4Mb) {
				doCompress(ins, 0.1f, savePath);
			} else { //1m一下原图
				myfile.transferTo(new File(savePath));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("图片压缩异常, 转为原图存储");
			try {
				myfile.transferTo(new File(savePath));
			} catch (Exception e1) {
				e1.printStackTrace();
				return "fasle";
			}
		}
		return "true";
	}

	@Override
	public String replyCard(Long cardId, String type, Long fromMemberId,
			Long toMemberId, String fromMemberName, String toMemberName,String content,Long forCommentId) {
		Map<String,Object> params = new HashMap<String,Object>();
		String methodName = "cardController/replyCard";
		String url=constPool.getCommunityApiUrl()+methodName;
		params.put("cardId", cardId);
		params.put("type",type);
		params.put("fromMemberId", fromMemberId);
		params.put("toMemberId", toMemberId);
		params.put("fromMemberName",fromMemberName);
		params.put("toMemberName", toMemberName);
		params.put("content", content);
		params.put("forCommentId", forCommentId);
		return Post(url, params);
	}

	@Override
	public String deleteCardComment(Long cardCommentId,Long cardId) {
		Map<String,String> params = new HashMap<String,String>();
		String methodName = "cardController/deleteCardComment";
		String url=constPool.getCommunityApiUrl()+methodName;
		params.put("cardCommentId", String.valueOf(cardCommentId));
		params.put("cardId", String.valueOf(cardId));
		return Post(url, params);
	}

	@Override
	public String taHomePage(Long memberId, Long taMemberId,Integer currentPage,Integer pageCount) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("memberId", String.valueOf(memberId));
		map.put("taMemberId",String.valueOf(taMemberId));
		map.put("currentPage", String.valueOf(currentPage));
		map.put("pageCount", String.valueOf(pageCount));
		String methodName="cardController/taHomePage";
		String url=constPool.getCommunityApiUrl()+methodName;
		return Post(url, map);
	}

	@Override
	public String addAttention(Long fromMemberId, Long toMemberId,String fromMemberName,String toMemberName) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("fromMemberId", String.valueOf(fromMemberId));
		map.put("toMemberId",String.valueOf(toMemberId));
		map.put("fromMemberName",fromMemberName);
		map.put("toMemberName",toMemberName);
		String methodName="cardController/addAttention";
		String url=constPool.getCommunityApiUrl()+methodName;
		return Post(url, map);
	}

	@Override
	public String cardReport(Long informerId,Long beReportId,String informerName,String beReportName,Long cardId,
			Long reasonId, String resonText,String reportCont) {
		Map<String,Object> map = new HashMap<>();
		map.put("informerId", informerId);
		map.put("beReportId", beReportId);
		map.put("informerName", informerName);
		map.put("beReportName", beReportName);
		map.put("cardId", cardId);
		map.put("reasonId", reasonId);
		map.put("resonText", resonText);
		map.put("reportCont", reportCont);
		String methodName="cardController/cardReport";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}

	@Override
	public String queryReasonList() {
		String methodName="cardController/queryReasonList";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, null);
	}

	public void yasuoPicture(String startUrl,String endUrl, MultipartFile mpf) {
		try{
			InputStream ins = new FileInputStream(startUrl);
			BufferedImage bi = ImageIO.read(ins);
			int width = bi.getWidth();
			int height = bi.getHeight();
			BufferedImage dest = new BufferedImage(width / 2, height / 2, BufferedImage.TYPE_3BYTE_BGR);
			dest.getGraphics().drawImage(bi, 0, 0, width / 2, height / 2, null);
			JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(new FileOutputStream(endUrl));
			jpeg.encode(dest);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void doCompress(InputStream in, float quality, String dest) throws Exception {
		BufferedImage inImg = ImageIO.read(in);

		OutputStream os = new FileOutputStream(new File(dest));

		Iterator<ImageWriter> formats = ImageIO.getImageWritersByFormatName("jpg");
		ImageWriter writer = formats.next();

		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);

		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);
		writer.write(null, new IIOImage(inImg, null, null), param);
	}

	@Override
	public String deleteCard(Long creatorMemberId, Long cardId) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("creatorMemberId",String.valueOf(creatorMemberId));
		map.put("cardId", String.valueOf(cardId));
		String methodName="cardController/deleteCard";
		String url=constPool.getCommunityApiUrl()+methodName;
		return Post(url, map);
	}

	@Override
	public String queryCardReader(Long cardId, Long memberId,Integer currentPage,Integer pageCount) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("memberId",String.valueOf(memberId));
		map.put("cardId", String.valueOf(cardId));
		map.put("currentPage", String.valueOf(currentPage));
		map.put("pageCount", String.valueOf(pageCount));
		String methodName="cardController/queryCardReader";
		String url=constPool.getCommunityApiUrl()+methodName;
		return Post(url, map);
	}

	@Override
	public String queryCardLike(Long cardId, Long memberId,
			Integer currentPage, Integer pageCount) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("memberId",String.valueOf(memberId));
		map.put("cardId", String.valueOf(cardId));
		map.put("currentPage", String.valueOf(currentPage));
		map.put("pageCount", String.valueOf(pageCount));
		String methodName="cardController/queryCardLike";
		String url=constPool.getCommunityApiUrl()+methodName;
		return Post(url, map);
	}
	
}










