/**
 * 
 */
package net.okdi.mob.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author chuanshi.chai
 */
public interface RegisterService {
	/**
	 * 验证手机号码是否存在 
	 * @param phone	手机号码
	 * return {memberId:123321,registered:true/false} 
	 */
	public Map<String, Object> validate(String phone);

	/**
	 * 发送手机验证码功能
	 * @param phone 发送手机号
	 * @param type： findpwd找回密码 reg注册 默认reg
	 * @return {"data":{"result":"OverMaxLimited"},"success":true}
	 * 返回json数据{"result":"OverMaxLimited"} result:OverMaxLimited 超过当日限制发送次数 SendFalse发送短信失败 SendTrue发送成功
	 */
	public Map<String, Object> sendSmsCode(String phone, String type);

	/**
	 * 验证手机短信码是否正确
	 * @param phone 手机号
	 * @param smsCode 短信验证码
	 * @param type 验证短信码类型  findpwd找回密码 reg注册 默认reg
	 * @return {"pass":"no","success":true}pass false 不通过 true 通过 invalid 验证码已失效
	 */
	public Map<String, Object> validateVerify(String phone, String smsCode, String type);

	/**
	 * 个人端的注册登录功能
	 * 注册，存储登录信息，功能
	 * 1.先在通行证注册并保存信息：得到memberId
	 * 2.存储本地登录数据
	 * @param userName
	 * @param password 
	 * @param source  使用2：个人端， 3：接单王（具体平台来源类型，默认0系统注册，站点系统：1，个人端：2，接单王：3） 
	 * @param type  角色信息，个人端为空，但是接单王有三种 -1：后勤， 1:站长， 0:收派员 
	 * @return {memberId:123321};失败返回{memberId:null}
	 */
	public Map<String, Object> register(String weNumber,String weName, String userName, String password, String source,String deviceType,String nickName);
	public String updateaccountIdBymemberId(Integer x) ;
	/**
	 * 修改密码
	 * @param memberId 用户id
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @return {"data":{"success":true},"success":true} data内success true修改成功false修改失败
	 */
	public Map changepwd(String memberId, String oldPwd, String newPwd);

	/**
	 * 找回密码（直接调用此接口可以修改该手机号的密码）
	 * 这里仅提供设置新密码的功能，前台的流程应该这样：输入手机号->*验证手机号是否存在*->发送短信验证码->正确->设置新密码
	 * @param memberId	成员memberId
	 * @param password 新密码 (这个后台写的时候注意下,算法加密)
	 * @return {"success":true/false}
	 */
	public Map<String, Object> getBackPwd(String memberId, String password);
	Map<String, Object> login(String channelNo,String userName,String password,String deviceType,String deviceToken,String version,String address,Integer source);
	Map<String, Object> loginOut(Long memberId,String channelNo);
	
	//编辑个人资料
	 public String  userUpdate(Long memberId,String telephone,String idNum,String username,String realname,
			  String adderssName,String address,String countryId,String addressId,String zip,MultipartFile[] myfiles);
	 //查询个人资料
	 public String getMemberMsg(Long memberId);
	 public String bindClient(String version,String channelNo,String deviceToken,String channelId,String deviceType);
	 Map<String, Object> login_jdw(String userName, String password);
	 public String InsertCoordinateAndToken(Long memberId,String channelNo,String lng,String lat,HttpServletResponse response
				,String deviceType,String deviceToken,String version,String address);
	 public String getRegisterShortUrlAmssy(String shopId);
	 
	 
	 /**
	  * 	登录
	  * @param userName
	  * 		用户名
	  * @param password
	  * 		密码
	  * @param source
	  * @return
	  */
	 public Map<String,Object> loginSingleMember(String userName, String password,Integer source);
	 
	 
	 public void saveToken(Long memberId,String channelNo,String deviceType,String deviceToken,String version);
	 
	 /**
	  * 	修改姓名
	  * @param memberId
	  * 		送派员Id
	  * @param realname
	  * 		真实姓名
	  */
	 public void updateName(Long memberId,String realname);

	Map<String, Object> pushLogin(String channelNo, String version, String deviceType, String deviceToken);
//	/**
//	 * 添加微信
//	 * @param memberId
//	 * @param weNumber
//	 * @param weName
//	 * @return
//	 */
//	public String insertWeChat(String userName, String weNumber, String weName);
	
 
}
