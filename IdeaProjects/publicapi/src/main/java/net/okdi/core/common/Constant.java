package net.okdi.core.common;

public class Constant {
	// session中保存的登录用户
	public static final String SESSION_LOGIN_USER = "loginUser";

	/**** 任务类型 0：分配任务 *****/
	public static final Integer TASK_ASSIGN = 0;
	/**** 任务类型 1：取消任务 *****/
	public static final Integer TASK_CANCEL = 1;
	/**** 任务类型 2：更换任务 *****/
	public static final Integer TASK_MODIFY = 2;

	/**** 任务来源 0：个人端 *****/
	public static final Integer TASK_SOURCE_PERSONAL = 0;
	/**** 任务来源 1：好递网 *****/
	public static final Integer TASK_SOURCE_OKDI = 1;
	/**** 任务来源 2：电商管家 *****/
	public static final Integer TASK_SOURCE_WIKI = 2;
	/**** 任务来源 3：站点系统 *****/
	public static final Integer TASK_SOURCE_EXP = 3;

	/**** 发送短信参数 CHANNEL_NO *****/
	public static final String SMS_CHANNEL_NO = "02";
	/**** 发送短信参数 CHANNEL_ID *****/
	public static final String SMS_CHANNEL_ID = "0";

}
