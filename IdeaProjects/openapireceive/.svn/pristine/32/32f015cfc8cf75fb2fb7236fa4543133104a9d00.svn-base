package net.okdi.core.sms;


import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @description: 公司内部调用webservice返回的处理结果BEAN
 * @description: copy from okdiweb
 * @author feng.wang
 * @date 2014-9-5
 * @version: 1.0.0
 */
@XmlRootElement(name="YctdSbt")
public class SmsSendResult {

    
	/**
	 * 0:推送到供应商短信平台成功；
	 * 非零参考：
	 *错误子码	错误描述
doSmsSend.err.001	渠道标识不能为空
doSmsSend.err.001_1	非法渠道标识
doSmsSend.err.002	手机号码非真实手机号码
doSmsSend.err.003	公司ID为空
doSmsSend.err.004	非真实公司ID 
doSmsSend.err.005	短信内容不能为空
smsSend.err.100	短信账户状态异常
smsSend.err.101	手机号码为空
smsSend.err.102	账户余额不足
	
smsSend.err.110	短信网关异常

smsSend.err.200	部分短信发送成功
smsSend.err.201	短信全部发送失败 

 
	 */
	private String status;  //0:推送到供应商短信平台成功；非零参考：
	
	private String oid;  //短信发送日志id
	
	private String phone;  //手机号
	
	private String msg=""; //发送状态解释
	
	private String spnumber="";
	private int messageNum=1;
	private String pushtime=org.apache.commons.lang.time.DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	
	public SmsSendResult() {}
	
	public SmsSendResult(String status,String phone,String oid) {
		super();
		 this.status = status;
		 this.phone = phone;
		 this.oid = oid;
	}

 
	public SmsSendResult(String phone, String msg, String spnumber, String pushtime) {
		super();
		this.phone = phone;
		this.msg = msg;
		this.spnumber = spnumber;
		this.pushtime = pushtime;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSpnumber() {
		return spnumber;
	}

	public void setSpnumber(String spnumber) {
		this.spnumber = spnumber;
	}

	public String getPushtime() {
		return pushtime;
	}

	public void setPushtime(String pushtime) {
		this.pushtime = pushtime;
	}

	public int getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}
	
	
}
