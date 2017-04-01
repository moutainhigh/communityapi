package net.okdi.mob.entity;

/*
 * 文 件 名:  NoticeMsg.java
 * 版    权:  Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 创 建 人:  文超
 * 创建时间:  Mar 20, 2013 5:32:29 PM  
 * 
 * 修改内容:  <修改内容>
 * 修改时间:  <修改时间>
 * 修改人:    <修改人>
 */

/**
 * 基础信息类
 * 
 * @author  文超
 * @version  [版本号, Mar 20, 2013 5:32:29 PM ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NoticeMsg {
	
	/**设定TCP服务器端的端口号 30001 40001*/
	public static final int PUSH_SERVER_PORT=40001;
//	
//	public static final int PUSH_SERVER_PORT=30001;//测试

	/**服务器端返回给客户端，表示建立长连接成功*/
	public static final int PUSH_SERVER_CONNECT=0;
	/**服务端发送消息给客户端 1*/
	public static final int PUSH_SERVER_CONTENT=1;
	/**服务器端向客户端发送心跳包的状态，5=服务器向客户端推送心跳包,此参数暂时没有启用 */
	public static  final int PUSH_SERVER_HEART=5;
	/**服务器端收到客户端请求类型，2=请求建立长连接 */
	public static final int PUSH_CLIENT_CONNECT=2;
	/**服务器端收到客户端请求类型，3=推送客户端消息成功 */
	public static  final int PUSH_CLIENT_CONTENT=3;
	/**客户端向服务器发送心跳包的，4=客户端发送心跳包 */
	public static  final int PUSH_CLIENT_HEART=4;
	/**服务器端收到客户端的心跳后通知客户端已成功接收心跳的状态，41=通知客户端已经收到心跳 */
	public static  final int PUSH_CLIENT_HEART_RETURN=41;
	
	
//	/**检查长连接心跳包超时的时间间隔，单位（毫秒）,60秒*/
//	public static  long PUSH_PANT_INSTANCE=SysParamPool.getLongValue("mob.notice.pushPantInstance",1*60*1000);
//	/**长连接心跳包超时时间，超过此时间时，服务器会主动断开长连接。单位（毫秒）,5*60秒*/
//	public static  long PUSH_PANT_TIMEOUT=SysParamPool.getLongValue("mob.notice.pushPantTimeOut",15*60*1000);
//	/**长连接心跳包发送时间间隔 ,60(单位 秒)*/
//	public static  int PUSH_HEART_TIME=SysParamPool.getIntValue("mob.notice.pushHeartTime",60);
	
	//public static final long PUSH_INSTANCE=1*60*1000;
	
	/***/
	public static final String PUSH_SERVER_NAME="pushNotice";
	
	/**消息服务器已收到待推送的消息 00*/
	public static final String NOTICE_STATUS_NEW="00";
	/**待推送的消息客户端未发现长连接，不推送 01*/
	public static final String NOTICE_STATUS_STOP="01";
	/**待推送的消息已推送 02*/
	public static final String NOTICE_STATUS_SEND="02";
	/**待推送的消息已推送成功 03*/
	public static final String NOTICE_STATUS_SUCC="03";
	/**待推送的消息客户端发现长连接，做了推送但是推送失败*/
	public static final String NOTICE_STATUS_SEND_FAIL="04";
	
	public static final String DEVICE_TYPE_ANDROID="android";
	/**Iphone客户端 iphone的AppStore版本*/
	public static final String DEVICE_TYPE_IPONE_P="IP";
	/**Iphone客户端 iphone的企业版本*/
	public static final String DEVICE_TYPE_IPONE_E="IE";
	/**消息类型 01：新任务*/
	public static final String MSG_TYPE_TASK_NEW="00";
	/**消息类型 02：任务的状态发生了更改*/
	public static final String MSG_TYPE_TASK_UPDATE="01";
	/**消息类型 030：收派员首次成功通过审核*/
	public static final String MSG_TYPE_MEMBER_AUTH_OK="030";
	/**消息类型 031：收派员非首次通过审核*/
	public static final String MSG_TYPE_MEMBER_MORE_AUTH_OK="031";
	
	/**消息类型 032：收派员实名未通过审核*/
	public static final String MSG_TYPE_MEMBER_AUTH_NO="032";
	/**消息类型 033：收派员归属认证未通过审核*/
	public static final String MSG_TYPE_MEMBER_AUTH_COMP_NO="033";
	/**消息类型 050：站点将收派员做离职操作*/
	public static final String MSG_TYPE_MEMBER_LEAVE="050";
	/**消息类型 060：举报处理结果*/
	public static final String MSG_TYPE_REPORT="060";
	
	/**消息类型 040：站点成功通过审核*/
	public static final String MSG_TYPE_COMP_AUTH_OK="040";
	
	/**消息类型 041：站点未通过审核*/
	public static final String MSG_TYPE_COMP_AUTH_NO="041";
	
	/**消息类型100，新建抢单后通知收派员**/
	public static final String MSG_TYPE_APPOINT_NEW="100";
	/**消息类型101，已被成功抢单，此时会通知收派员和个人客户**/
    public static final String MSG_TYPE_APPOINT_SUCC="101";
    /**消息类型102，抢单失效**/
    public static final String MSG_TYPE_APPOINT_INVALID="102";
    /**消息类型103，客户取消**/
    public static final String MSG_TYPE_APPOINT_CANCEL="103";
    /***消息类型104，收派员取件后通知客户**/
    public static final String MSG_TYPE_APPOINT_FULFILTASK= "104";
    /***消息类型105，4小时物流状态变化定时推送**/
    public static final String MSG_TYPE_MESSAGE_CHANGE= "105";
    
    //收派员决绝接单
    public static final String  MSG_TYPE_REFUSE= "106";
    
    //
    public static final String  MSG_TYPE_WAITTAKE= "107";
    
    
    
	/**收派员系统的手机客户端 01*/
	public static final String MSG_CHANNELNO_MOB_MEMBER="01";
	/**好递个人的手机客户端 02*/
	public static final String MSG_CHANNELNO_MOB_PERSON="02";
	/**PC客户端 05*/
	public static final String MSG_CHANNELNO_MOB_PC="05";
	
	/**好递发货王PC客户端10*/
	public static final String MSG_CHANNELNO_PC_OKDI="10";
	
	/**收派员离职发推送消息**/
	public static final String MSG_TYPE_MEMBER_COMP_DEL="050";
	
	/**好递个人物流信息有变更**/
	public static final String MSG_TYPE_TRACE_CHANGE="100";
	
	/**Push推送类型选择，socket，选择通过第三方通道方式推送**/
	public static final String PUSH_TYPE_EXT="ext";
	/**Push推送类型选择，socket，选择通过socket方式推送**/
	public static final String PUSH_TYPE_SOCKET="socket";
	
	/**PC站点系统客户端 05**/
	public static final String MSG_CHANNELNO_PC_COMP="05";
	/**PC站点系统唯一标识前缀 88**/
	public static final String MSG_CHANNELID_PC_COMP_PREFIX="88";
	
	/**推送消息的唯一ID*/
	private Long id;
	/**渠道编号 01：收派员手机客户端 05：PC客户端*/
	private String channelNo;
	
	/**收派员ID*/
	private Long channelId;
	/**消息类型*/
	private String type;
	/**消息对应的业务ID*/
	private Long documentId;
	/**消息对应的标题*/
	private String title;
	/**消息对应的内容*/
	private String content;
	/**额外参数**/
	private String extraParam;
	/**消息响应状态 0：服务器返回给客户端的状态，标识建立长连接成功，
	 *            1:服务器推送消息给客户端
	 *            2:客户端请求建立长连接
	 *            3:客户端推送消息给服务器端*/
	private int status;
	
	private String deviceType;
	
	private String clientId;
	
	/****经度****/
	private String log;
	/****纬度******/
	private String lat;
	
	private long DocumentMemberId;
	private long createUser;
	
	/**push通道的选择，ext：通过第三方通道,socket：通过socket协议 */
	private String pushType;
	
	public NoticeMsg(){}
	public NoticeMsg(Long channelId, String type, Long documentId,
			String content) {
		super();
		this.channelId = channelId;
		this.type = type;
		this.documentId = documentId;
		this.content = content;
	}
	public NoticeMsg(Long channelId, String type, Long documentId,
			String content, String extraParam) {
		super();
		this.channelId = channelId;
		this.type = type;
		this.documentId = documentId;
		this.content = content;
		this.extraParam = extraParam;
	}
	
	
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getExtraParam() {
		return extraParam;
	}
	public void setExtraParam(String extraParam) {
		this.extraParam = extraParam;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * ,push通道的选择，ext：通过第三方通道,socket：通过socket协议 
	 * 创 建 人:  文超
	 * 创建时间:  2014-5-4 下午4:09:06  
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
    public String getPushType() {
        return pushType;
    }
    public void setPushType(String pushType) {
        this.pushType = pushType;
    }
    public String getLog() {
        return log;
    }
    public void setLog(String log) {
        this.log = log;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
	public long getDocumentMemberId() {
		return DocumentMemberId;
	}
	public void setDocumentMemberId(long documentMemberId) {
		DocumentMemberId = documentMemberId;
	}
	public long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(long createUser) {
		this.createUser = createUser;
	}
	 
	
	
}
