package net.okdi.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConstantsConfig {
	/**
	 * 存储图片路径
	 */
	@Value("${avatar.savePath}")
	public String picPath;
    /**
     * 读取图片路径
     */
	@Value("${avatar.readPath}")
	public String readPath;
	/**
	 * cas 登录地址
	 */
	@Value("${cas.login}")
	public String casLoginUrl;
	/**
	 * cas 退出登录地址
	 */
	@Value("${cas.logout}")
	public String casLogoutUrl;
	
	/**
	 * 首页新闻咨询条数
	 */
	@Value("${index.article.pageSize}")
	public int indexArticlePageSize;
	
	/**
	 * 首页新手指南条数
	 */
	@Value("${index.newbie.pageSize}")
	public int indexNewbiePageSize;
	
	/**
	 * 首页最新加盟站点条数
	 */
	@Value("${index.newExpress}")
	public int indexnewExpress;
	
	@Value("${wuliu.read.picture.path}")
	public String pictrueReadUrl; 
	
	@Value("${wuliu.comppicture.filename}")
	public String readFilename; 
	
	/**
	 * 接单王安卓客户端下载地址
	 */
	@Value("${okdi.client.jdw.android.download.url}")
	public String jdwAdroidDloadUrl;
	/**
	 * 接单王苹果客户端下载地址
	 */
	@Value("${okdi.client.jdw.ios.download.url}")
	public String jdwIosDloadUrl;
	/**
	 * 去好递通行证验证手机号是否注册
	 */
	@Value("${ucenter.mobile.validate.url}")
	public String mobileValidateUrl;
	/**
	 * 保存收派员头像路径
	 */
	@Value("${save.courier.head}")
	public String saveCourierHead;
}

