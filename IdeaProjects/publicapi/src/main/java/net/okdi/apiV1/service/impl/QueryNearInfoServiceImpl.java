package net.okdi.apiV1.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV1.service.QueryNearInfoService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.PassportHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
@Service
public class QueryNearInfoServiceImpl extends AbstractHttpClient implements QueryNearInfoService{

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private PassportHttpClient passportHttpClient;
	@Autowired
	private ConstPool constPool; 

	private int width = 90;//定义图片的width  
	private int height = 24;//定义图片的height  
	private int codeCount = 4;//定义图片上显示验证码的个数  
	private int xx = 15;
	private int fontHeight = 18;
	private int codeY = 18;
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	@Override
	public String queryCompInfoByRoleId(String longitude, String latitude, String roleId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("longitude", longitude); 
		map.put("latitude", latitude);
		map.put("roleId", roleId); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/queryCompInfoByRoleId/", map);
	}

	@Override
	public String queryCompInfoByCompName(Double longitude, Double latitude, String roleId, String compName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("longitude", longitude); 
		map.put("latitude", latitude);
		map.put("compName", compName);
		map.put("roleId", roleId); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/queryCompInfoByCompName/", map);
	}

	@Override
	public String queryVerifyCode(String mobile,HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(2);
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。  
		StringBuffer randomCode = new StringBuffer();

		// 定义图像buffer  
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类  
		Random random = new Random();
		// 将图像填充为白色  
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。  
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		// 设置字体。  
		gd.setFont(font);

		// 画边框。  
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。  
		gd.setColor(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(6);
			int yl = random.nextInt(6);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		int red = 0, green = 0, blue = 0;

		// 随机产生codeCount数字的验证码。  
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。  
			String code = String.valueOf(codeSequence[random.nextInt(34)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。  
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);

			// 用随机产生的颜色将验证码绘制到图像中。  
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, (i + 1) * xx, codeY);

			// 将产生的四个随机数组合在一起。  
			randomCode.append(code);
		}
		//System.out.println(randomCode.toString());
		// 禁止图像缓存。  
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		resp.setContentType("image/gif");

		// 将图像输出到Servlet输出流中。  
		try {
			ServletOutputStream sos = resp.getOutputStream();
			ImageIO.write(buffImg, "gif", sos);
			sos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(33);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile); 
		map.put("randomCode", randomCode.toString().toUpperCase());
		String result = passportHttpClient.doPassSendStr("verifyCodeInfo/queryVerifyCode/", map);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("操作异常！");
		}
		String success=String.valueOf(JSON.parseObject(result).get("success"));
		if(!"true".equals(success)){
			throw new ServiceException("返回异常！");
		}
		return randomCode.toString();
	}

	@Override
	public String queryShareInfo(String compTypeNum) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("compTypeNum", compTypeNum); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/queryShareInfo/", map);
	}

	@Override
	public String queryVerifyCodeIsRight(String mobile, String verifyCode) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile); 
		map.put("verifyCode", verifyCode.toUpperCase()); 
		//String result = passportHttpClient.doPassSendStr("verifyCodeInfo/queryVerifyCode/", map);
		return passportHttpClient.doPassSendStr("verifyCodeInfo/queryVerifyCodeIsRight/", map);
	}

	@Override
	public String invitationIntoCompInfo(String fromMemberPhone, String toMemberPhone, String invitationType) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("fromMemberPhone", fromMemberPhone); 
		map.put("toMemberPhone", toMemberPhone); 
		map.put("invitationType", invitationType); 
		String url = constPool.getOpenApiUrl()+"queryNearInfo/invitationIntoCompInfo";
		String result = Post(url, map);
		//String result = passportHttpClient.doPassSendStr("verifyCodeInfo/queryVerifyCode/", map);
		return result;
	}

	@Override
	public String initVirtualCompInfo() {
		Map<String,Object> map = new HashMap<String,Object>();
		return openApiHttpClient.doPassSendStr("queryNearInfo/initVirtualCompInfo/", map);
	}

	@Override
	public String invitationHaveNetInfo(String memberId, String netId,String roleId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId); 
		map.put("netId", netId); 
		map.put("roleId", roleId); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/invitationHaveNetInfo/", map);
	}

	@Override
	public String queryAuthenticationInfo(String mobile) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/queryAuthenticationInfo/", map);
	}

	@Override
	public String deleteWrongData(String auditId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("auditId", auditId); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/deleteWrongData/", map);
	}

	@Override
	public String deleteRedisInfo(String redisName,String key) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("redisName", redisName); 
		map.put("key", key); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/deleteRedisInfo/", map);
	}

	@Override
	public String queryPasswordByMobile(String mobile) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/queryPasswordByMobile/", map);
	}

	@Override
	public String queryWrongDataByMobile(String mobile) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/queryWrongDataByMobile/", map);
	}

	@Override
	public String queryUnReadMessage(String mobile) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile); 
		return openApiHttpClient.doPassSendStr("queryNearInfo/queryUnReadMessage/", map);
	}
	
}
