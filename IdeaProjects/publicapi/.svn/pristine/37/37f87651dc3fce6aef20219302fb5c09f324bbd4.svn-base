package net.okdi.mob.service;

public interface WalletService {
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>设置密码/修改密码</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-5-22 下午1:45:29</dd>
     * @param accountCard 钱包账户
     * @param password    钱包密码
     * @return
     * @since v1.0
     */
	public String setWalletPassword(String accountCard,String password);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>检查账户号是否存在</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-21 下午5:26:03</dd>
	 * @param accountCard
	 * @param password
	 * @return
	 * @throws Exception 
	 * @since v1.0
	 */
	public String accountCardExist(String accountCard) throws Exception;
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>创建账户和账本</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-21 下午5:49:43</dd>
	 * @param nick
	 * @param userPhone
	 * @param catId
	 * @param accountCard
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public String createAccount(String nick, String userPhone, String catId, Long accountCard, Long memberId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>按账户名和日期返回交易明细</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-22 上午11:09:50</dd>
	 * @param accountCard
	 * @param created
	 * @return
	 * @since v1.0
	 */
	public String cashTradeDetail(String accountCard, String created);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询余额及提现成功次数及总次数</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-5-22 上午11:20:27</dd>
	 * @param accountCard
	 * @param memberId
	 * @param catId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @since v1.0
	 */
	public String getBalance(Long accountCard, Long memberId, String catId, String startTime, String endTime);
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>修改密码/验证旧密码是否正确</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-5-22 下午1:49:11</dd>
     * @param accountCard 钱包账户
     * @param memberId 钱包所属memberId
     * @param password 钱包密码 （前台加密）
     * @return
     * @since v1.0
     */
    public String validatePassword(String accountCard,Long memberId,String password);


    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>发送短信验证码</dd>
     * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 上午11:40:34</dd>
     * @param mobile
     * @param busineType
     * @param validTimes
     * @return
     * @since v1.0
     */
    public String sendVerifyCode(String mobile, Long busineType, String validTimes);
    
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>验证验证码</dd>
     * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-5-25 上午11:56:55</dd>
     * @param mobile
     * @param busineType
     * @param verifyCode
     * @return
     * @since v1.0
     */
    public String validVerifyCode(String mobile, Long busineType, String verifyCode);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>验证钱包账户密码是否存在</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-5-26 下午9:03:23</dd>
     * @param memberId
     * @return
     * @since v1.0
     */
    public String checkAccountPwdIsExist(Long memberId);
    public String getBankInfoList(String cardType);

	public String getWithdrawLimit(String accountCard, String memberId, String catId, String startTime, String endTime);
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>按账户号和交易类型查询好递生活账单</dd>
     * <dt><span class="strong">作者:</span></dt><dd>kai.yang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-8-11 上午10:03:25</dd>
     * @param mobile
     * @param busineType
     * @param verifyCode
     * @return
     * @since v1.0
     */
	public String getOKdiLifeCashTradeByType(String accountCard,int currentPage, int type);
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>按账户号查询好递生活账单</dd>
     * <dt><span class="strong">作者:</span></dt><dd>kai.yang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-8-11 上午10:03:25</dd>
     * @param mobile
     * @param busineType
     * @param verifyCode
     * @return
     * @since v1.0
     */
	public String getOKdiLifeCashTrade(String accountCard, int currentPage);
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>查询交易类型</dd>
     * <dt><span class="strong">作者:</span></dt><dd>kai.yang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-8-11 上午10:03:25</dd>
     * @param mobile
     * @param busineType
     * @param verifyCode
     * @return
     * @since v1.0
     */
	public String getTradeType(String catId);
}
