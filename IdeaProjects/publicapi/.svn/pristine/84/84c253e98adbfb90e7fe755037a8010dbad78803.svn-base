package net.okdi.apiV4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.apiV4.service.IMService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

@Controller
@RequestMapping(value = "/im")
public class IMController extends BaseController{

	private @Autowired IMService iMService;
	
	
	/**
	 * @author hang.yu
	 * @api {POST} /im/allocate 1-分配长连接地址(HTTP)
	 * @apiVersion 0.4.0
	 * @apiDescription 分配长连接地址, 建立长连接之前请求接口获取地址
	 * @apiGroup 快递圈-即时通讯
	 * @apiParam {Long} mid 用户登录id memberId
	 * 
	 * @apiSampleRequest /im/allocate
	 * @apiSuccessExample Success-Response:
	  {
    	"data":{
        	"receiver":"17080032629",
        	"socketIp":"192.168.31.202",
        	"socketPort":"4002",
        	"httpPort": "8088"
    	},
    	"success":true
	 }
	 * @apiErrorExample Error-Response:
	  {
    	"errCode":0,
    	"errSubcode":"",
    	"message":"请求失败",
    	"success":false
	  }
	 */
	@ResponseBody
	@RequestMapping("/allocate")
	public String allocate(Long mid) {
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户id不能为空");
		}
		return iMService.allocate(mid);
	}
	
	/**
	 * @author hang.yu
	 * @api {POST} / 2-建立长连接(TCP)
	 * @apiVersion 0.4.0
	 * @apiDescription 建立长连接
	 * @apiGroup 快递圈-即时通讯
	 * @apiParam {String} key client_bind 建立长连接
	 * @apiParam {Long} timestamp 当前时间戳
	 * @apiParam {String} name 用户姓名
	 * @apiParam {Long} account	用户id memberId
	 * @apiParam {String} channel IE/IP/android
	 * @apiParam {String} deviceId 设备号
	 * @apiParam {String} source app标识 1: 快递员 2: 个人端
	 * 
	 * @apiSuccessExample {xml} 长连接建立消息格式:
		<?xml version="1.0" encoding="utf-8"?>
		<sent>
		  <key>client_bind</key>
		  <timestamp>1466998428263</timestamp>
		  <data>
		    <name>okdi-0</name>
		    <account>0</account>
		    <channel>IE/IP/android</channel>
		    <deviceId>865863026958020</deviceId>
		    <source>app标识 1: 快递员 2: 个人端</source>
		  </data>
		</sent>
	 * @apiSuccessExample {xml} Response:
	  <?xml version="1.0" encoding="utf-8"?>
		<reply>
		  <key>client_bind</key>
		  <timestamp>1466998428263</timestamp>
		  <data></data>
		  <code></code>
		</reply>
	 */
	public String bind() {
		return "";
	}
	
	/**
	 * @author hang.yu
	 * @api {POST} /3 3-发送心跳包(TCP)
	 * @apiVersion 0.4.0
	 * @apiDescription 发送心跳包
	 * @apiGroup 快递圈-即时通讯
	 * @apiParam {String} key client_heartbeat 客户端发送心跳包
	 * @apiParam {Long} timestamp 当前时间戳
	 * @apiParam {Long} account	用户id memberId
	 * 
	 * @apiSuccessExample {xml} 心跳包消息格式:
		<?xml version="1.0" encoding="utf-8"?>
		<sent>
		  <key>client_heartbeat</key>
		  <timestamp>1466998428263</timestamp>
		  <data>
		    <account>0</account>
		  </data>
		</sent>
	 * @apiSuccessExample {xml} 心跳回执:
	  <?xml version="1.0" encoding="utf-8"?>
		<reply>
		  <key>client_heartbeat</key>
		  <timestamp>1466998428263</timestamp>
		  <data></data>
		  <code>200</code>  --200正常响应  500服务器错误
		</reply>
	 */
	public String heartbeat() {
		return "";
	}
	
	/**
	 * @api {POST} /4 4-发送聊天消息(TCP)
	 * @apiVersion 0.4.0
	 * @apiDescription 发送聊天消息
	 * @apiGroup 快递圈-即时通讯
	 * 
	 * @apiParam {String} key client_send_message 客户端发送聊天消息
	 * @apiParam {Long} timestamp 当前时间戳
	 * @apiParam {Long} sender	发送人id memberId
	 * @apiParam {String} senderName 发送人姓名(单聊可以省略)
	 * @apiParam {Long} receiver 接收人id memberId
	 * @apiParam {String} receiverName 接收人姓名(单聊可以省略)
	 * @apiParam {Integer} type 1-单聊  2-群聊
	 * @apiParam {Integer} fileType 1-静态图片  2-语音 3-gif图片 4-视频文件 5-普通文本消息
	 * @apiParam {String} content 文本消息的内容
	 * @apiParam {String} file 文本消息(fileType=5):空字符串    多媒体消息(fileType=1, 2, 3, 4): 转码后的字符串,不能为空
	 * @apiParam {String} revoke 消息撤回标识 (如果不是撤回消息, 可不传) 1: 撤回
	 * @apiParam {Long} mid 消息id, 撤回消息时需要. 跟revoke字段一起传
	 * 
	 * @apiSuccessExample {xml} 单聊消息格式:
		<?xml version="1.0" encoding="utf-8"?>
		<sent>
		  <key>client_send_message</key>
		  <timestamp>1466843490902</timestamp>
		  <data>
		    <sender>159514953588736</sender>
		    <content>呵呵呵呵呵呵</content>
		    <fileType>5</fileType>
		    <senderName>禹航</senderName>
		    <receiver>211506300239872</receiver>
		    <receiverName>吕布</receiverName>
		    <type>1</type>
		    <fileType>1</fileType>
		    <file>dd</file>
		    <speechTimeLength>语音时长</speechTimeLength>
		    <revoke>消息撤回标识 (如果不是撤回消息, 可不传) 1: 撤回</revoke>
		    <mid>消息id, 撤回消息时需要. 跟revoke字段一起传</mid>
		  </data>
		</sent>
	 * @apiSuccessExample {xml} 群聊消息格式:
		<?xml version="1.0" encoding="utf-8"?>
		<sent>
		  <key>client_send_message</key>
		  <timestamp>1466843490902</timestamp>
		  <data>
		    <sender>159514953588736</sender>
		    <content>呵呵呵呵呵呵</content>
		    <fileType>5</fileType>
		    <senderName>禹航</senderName>
		    <receiver>211506300239872</receiver>
		    <groupId>群聊时的群id</groupId>
		    <receiverName>吕布</receiverName>
		    <type>1</type>
		    <fileType>1</fileType>
		    <file>dd</file>
		    <speechTimeLength>语音时长</speechTimeLength>
		    <revoke>消息撤回标识 (如果不是撤回消息, 可不传) 1: 撤回</revoke>
		    <mid>消息id, 撤回消息时需要. 跟revoke字段一起传</mid>
		  </data>
		</sent>
	 * @apiSuccessExample {xml} 服务端发送的聊天消息:
		<?xml version=\"1.0\" encoding=\"UTF-8\"?>
		<message>
			<mid>消息id</mid>
			<type>1-单聊  2-群聊(包括设置公告) 3-系统消息(加群/群更换管理员) 4-群解散</type>
			<title></title>
			<content><![CDATA[普通文本消息内容]]></content>
			<file>多媒体消息的url地址</file>
			<thumb>图片缩略图/视频第一帧url</thumb>
			<fileType>对于消息发送的5个fileType</fileType>
			<logo>群头像url</logo>
			<header>用户头像url</header>
			<sender>发送人id</sender>
			<senderName>姓名</senderName>
			<src>消息发送者app标识 1: 快递员  2: 个人端 	3: 微信H5</src>
			<receiver>接收人id</receiver>
			<receiverName>姓名</receiverName>
			<format>txt</format>
			<groupId>群聊时的群id</groupId>
			<group>群名字</group>
			<speechTimeLength>语音时长</speechTimeLength>
			<inter>是否消息免打扰0否1是</inter>
			<top>是否置顶0否1是</top>
			<topts>置顶时间</topts>
			<net>快递员快递公司</net>
			<timestamp></timestamp>
		</message>
		
	 * @apiSuccessExample {xml} 全局响应码:
		<?xml version="1.0" encoding="utf-8"?>
		<reply>
		  <key>client_heartbeat</key>
		  <timestamp>0</timestamp>
		  <code>200 - 消息发送成功</code>
		  <code>201 - 文本消息包含敏感字</code>
		  <code>203 - 无法找到对方登录ip</code>
		  <code>500 - 服务器错误</code>
		  <code>501 - memberId无效</code>
		  <code>504 - 群聊时 群组不存在</code>
		  <code>506 - 用户未注册</code>
		  <code>507 - 用户不在群中</code>
		  <code>508 - 消息超过一分钟, 不能撤回</code>
		  <code>509 - 黑名单用户</code>
		  <data/>
		</reply>

	 */
	public String sendMsg() {
		return "";
	}
	
	
	/**
	 * @api {GET} /im/offline 5-拉取离线消息(HTTP)
	 * @apiVersion 0.4.0
	 * @apiDescription 发送聊天消息
	 * @apiGroup 快递圈-即时通讯
	 * 
	 * @apiParam {Long} mid 用户登录id memberId
	 * 
	 * @apiSuccess {Map} count已经排好序, 置顶的在上, 没有置顶的在下. 都按照消息时间倒序
	 * 
	 * @apiSampleRequest /im/offline
	 * 
	 * @apiSuccessExample Success-Response:
	 {
	 	"data": {
	 		"count": [
	 			{
	 				"fileType": "1",  	-- 1-静态图片  2-语音 3-gif图片 4-视频文件 5-普通文本消息
	 				"last": "asdf",	  	--发送人id-1最后的一条消息
	 				"count": "5",      	--消息个数
	 				"type": 1,			--1单聊 2群聊 (已 detail 中的type字段来区分消息类型)
	 				"name": "xx",		--发送人名字(用户姓名  或者  群名字)
	 				"senderId": 11,		--发送人id
	 				"headUrl": "",		--头像url
	 				"top": 1,			--是否置顶  0否1是
	 				"topts": 124,		--置顶的时间  top = 0时  字段值是null
	 				"interru": 0,		--是否消息免打扰  0否1是
	 				"msgts": 234,		--消息时间
	 				"groupId": 111
	 			}, 
	 			{
	 				"fileType": "1",  	-- 1-静态图片  2-语音 3-gif图片 4-视频文件 5-普通文本消息
	 				"last": "asdf",	  	--发送人id-2最后的一条消息
	 				"count": "1",      	--消息个数
	 				"type": 1,			--1单聊 2群聊
	 				"name": "xx",		--发送人名字(用户姓名  或者  群名字)
	 				"senderId": 11,		--发送人id
	 				"headUrl": "",		--头像url
	 				"top": 1,			--是否置顶  0否1是
	 				"topts": 124,		--置顶的时间  top = 0时  字段值是null
	 				"interru": 0,		--是否消息免打扰  0否1是
	 				"msgts": 234,		--消息时间
	 				"groupId": 111
	 			}
	 		],
	 		"detail": {
	 			"发送人id-1": {[
	 				{
	 					"baseUrl": "http://", 	--用户 或者 群头像url, 需要拼接字符串  baseUrl + id + .jpg
		 				"mid": 111,			--消息id
		 				"sender": 222,		--发送人id
		 				"senderName": "xxx"	--发送人姓名
		 				"fileType": "5",
		 				"content": "",
		 				"ts": 111,			--时间
		 				"file": "http://",	--图片 视频的url  此时content是空串
		 				"thumb": "http://",	--图片缩略图url  视频第一帧url
		 				"group": "群名字",
		 				"groupId": 11,
		 				"speechTimeLength": "2",	--语音时长
		 				"type": "1" --1: 单聊  2: 群聊  3: 系统消息  4: 群解散  5: 群公告
		 		    }
	 			]}, 
	 			"发送人id-2": {[
	 				{
		 				"mid": 111,			--消息id
		 				"sender": 222,		--发送人id
		 				"senderName": "xxx"	--发送人姓名
		 				"fileType": "5",
		 				"content": "",
		 				"ts": 111			--时间
		 			}
	 			]}
	 		}
	 	},
	 	"success": true
	 }
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping("/offline")
	public String offline(Long mid) {
		if (PubMethod.isEmpty(mid)) {
			return paramsFailure("", "用户信息不正确");
		}
		return iMService.offlineMsg(mid);
	}
	
	/**
	 * @api {GET} /ichat/message/upload 6-上传图片/视频(HTTP)
	 * @apiVersion 0.4.0
	 * @apiDescription 发送图片视频消息时, 先调用这个接口上传图片/视频. 发送消息时, 原先的file字段改为接口返回的url. 接口的ip端口是allocate接口返回的ip和httpPort
	 * @apiGroup 快递圈-即时通讯
	 * 
	 * @apiParam {String} fileType 1: 图片  2:语音  4:视频
	 * @apiParam {String} source ios/android
	 * @apiParam {Stream} file 流
	 * 
	 * @apiSampleRequest /ichat/message/upload
	 * 
	 * @apiSuccessExample Success-Response:
	 {
	 	"data": "http://ichat.okdit.net/o2o_data/ichat/orig/D63A40403E123404B89DFB1B90A9385E.jpg"
	 	"success": true
	 }
	 * @apiErrorExample Error-Response: 系统级异常
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"",
	 *    	"message":"请求失败",
	 *    	"success":false
	 *    }
	 */
	public String upload() {
		return "";
	}
	
}


























