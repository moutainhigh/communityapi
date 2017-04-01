package net.okdi.api.service;

public interface SmsWhiteListService {
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>是否在白名单中</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:39:10</dd>
	 * @param memberId		收派员id
	 * @return
	 */
	Boolean isInWhiteList(Long memberId);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>加入到白名单中</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:40:51</dd>
	 * @param memberId		收派员id
	 * @return
	 */
	String addInWhiteList(String memberPhone);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>加入到白名单中</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:40:51</dd>
	 * @param memberId		收派员id
	 * @return
	 */
	String deleteFromWhiteList(Long memberId);
}
