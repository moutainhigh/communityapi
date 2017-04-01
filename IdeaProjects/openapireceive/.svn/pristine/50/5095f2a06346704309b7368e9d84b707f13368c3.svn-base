package net.okdi.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasClientPushJurisdiction;
import net.okdi.api.entity.MobMemberLogin;
import net.okdi.api.service.BasClientPushJurisdictionService;
import net.okdi.api.service.MobMemberLoginService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.Constant;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/mobPush")
public class MobPushController extends BaseController {

	@Autowired
	private MobMemberLoginService mobMemberLoginService;
	
	@Autowired
	private BasClientPushJurisdictionService basClientPushJurisdictionService;
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查看是否可以推送</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午2:58:29</dd>
	 * @param channelNo 推送的类型（01：接单王，02:个人端） (提示：改方法不做socket是否推送的判断)
	 * @param memberId	用户Id:memberId	
	 * @return	{"canPush":"success/false","memberId":"12345655"}
	 */
	@ResponseBody
	@RequestMapping(value = "/canPush", method = { RequestMethod.POST })
	public String canPush(String channelNo, Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MobPushController.canPush.001", "用户id不能为空！");
		}
		if(PubMethod.isEmpty(channelNo)){
			return paramsFailure("net.okdi.api.controller.MobPushController.canPush.002", "推送类型不能为空！");
		}
		String result = mobMemberLoginService.isOnlineMember(channelNo,memberId);
		boolean flag = false;
		if(Constant.YESPUSH_NOSMS_ANDRIOD.equals(result)||Constant.YESPUSH_NOSMS_APPLE.equals(result)){
			flag = true;
		}
		Map m = new HashMap();
		m.put("canPush", flag);
		m.put("memberId", memberId);
		return JSON.toJSONString(m);
	}
	
	/**
	 * 手机端登录绑定设备
	 * @Method: phoneLoginIn 
	 * @Description: 包括个人端和接单王的登录，如果原来库中有数据，则只进行更新操作，其中推送类型置为ext
	 * @param channelNo 01：接单王，02:个人端
	 * @param memberId	个人的memberId
	 * @param deviceType	设备类型
	 * @param deviceToken	设备唯一标识
	 * @param version	版本
	 * @param address	地址（这个应该存ip地址，目前这个字段没有启用业务)
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-10-31 下午7:17:10
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/phoneLoginIn", method = { RequestMethod.POST })
	public String phoneLoginIn(String channelNo, Long memberId, String deviceType, String deviceToken, String version,
			String address) {
		if(PubMethod.isEmpty(channelNo)||PubMethod.isEmpty(deviceType)){
			Map errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "channelNo或deviceType为空");
			return JSON.toJSONString(errorMap);
		}
		System.out.println("open里的deviceToken："+deviceToken+",deviceType:"+deviceType+",memberId:"+memberId);
		mobMemberLoginService.saveOrUpdateMobMemberLogin(channelNo, memberId, deviceType, deviceToken, version, address);
		Map<String, String> m = new HashMap<String, String>();
		m.put("loginIn", "success");
		return JSON.toJSONString(m);
		
	}

	
	
	/**
	 * 手机登出，解绑设备信息
	 * @Method: phoneLoginOut 
	 * @Description: 直接删除掉手机在线表中的数据
	 * @param channelNo	
	 * @param memberId	
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-10-31 下午7:20:35
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/phoneLoginOut", method = { RequestMethod.POST })
	public String phoneLoginOut(String channelNo, Long memberId) {
		if(PubMethod.isEmpty(channelNo)||PubMethod.isEmpty(memberId)){
			Map errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "channelNo或memberId为空");
			return JSON.toJSONString(errorMap);
		}
		MobMemberLogin mobMemberLogin = new MobMemberLogin();
		mobMemberLogin.setChannelNo(channelNo);
		mobMemberLogin.setChannelId(memberId);
		
		mobMemberLoginService.deleteMobMemberLogin(mobMemberLogin);
		Map m = new HashMap();
		m.put("loginOut", "success");
		return JSON.toJSONString(m);
	}
	
	/**
	 * 验证手机是否登录
	 * @Method: valOnePhoneLogin 
	 * @Description: 使用登录设备类型，登录人memberId,手机设备号三个来判断
	 * @param channelNo	登录设备类型
	 * @param memberId	登录人memberId
	 * @param deviceToken	手机设备号
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-1 上午9:20:25
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/valOnePhoneLogin", method = { RequestMethod.POST })
	public String valOnePhoneLogin(String channelNo, Long memberId, String deviceToken) {
		if(PubMethod.isEmpty(channelNo)||PubMethod.isEmpty(memberId)||PubMethod.isEmpty(deviceToken)){
			Map errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "参数不能为空");
			return JSON.toJSONString(errorMap);
		}
		boolean flag = mobMemberLoginService.valOnePhoneLogin(channelNo,memberId,deviceToken);
		Map m = new HashMap();
		m.put("channelNo",channelNo );
		m.put("memberId", memberId);
		m.put("deviceToken", deviceToken);
		Map result = new HashMap();
		result.put("phoneMsg", m);
		result.put("isThisPhoneLogin", flag);
		return JSON.toJSONString(result);
	}
	
	/**
	 * 设置账号的推送开关
	 * @Method: editPushSwitch 
	 * @Description: TODO
	 * @param memberId 用户memberId
	 * @param type on:开，off:关
	 * @return	{"success":"true"}
	 * @author chuanshi.chai
	 * @date 2014-11-1 下午4:25:24
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/editPushSwitch", method = { RequestMethod.POST })
	public String editPushSwitch(Long memberId,String type) {
		BasClientPushJurisdiction basClientPushJurisdiction = new BasClientPushJurisdiction();
		basClientPushJurisdiction.setMemberId(memberId);
		if("on".equals(type)){
			basClientPushJurisdiction.setPhoneClientPushSwitch("1");
		}else if("off".equals(type)){
			basClientPushJurisdiction.setPhoneClientPushSwitch("0");
		}else{
			Map m = new HashMap();
			m.put("success", "false");
			m.put("msg", "参数type值不是 'on' or 'off'");
			return JSON.toJSONString(m);
		}
		basClientPushJurisdictionService.saveOrupdateBasClientPushJurisdiction(basClientPushJurisdiction);
		Map result = new HashMap();
		result.put("success", "true");
		return JSON.toJSONString(result);
	}
	
	/**
	 * 设置账号的推送开关
	 * @Method: editPushSwitch 
	 * @Description: TODO
	 * @param memberId 用户memberId
	 * @param type on:开，off:关
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-1 下午4:25:24
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getPushSwitch", method = { RequestMethod.POST })
	public String getPushSwitch(Long memberId) {
		BasClientPushJurisdiction basClientPushJurisdiction = basClientPushJurisdictionService.findByBasClientPushJurisdiction(memberId);
		Map<String, String> result = new HashMap<String, String>();
		result.put("success", "true");
		if(basClientPushJurisdiction==null){//默认开启
			result.put("status", "on");
		}
		if("1".equals(basClientPushJurisdiction.getPhoneClientPushSwitch())){
			result.put("status", "on");
		}else if("0".equals(basClientPushJurisdiction.getPhoneClientPushSwitch())){
			result.put("status", "off");
		}
		return JSON.toJSONString(result);
	}
	
	/**
	 * 查找推送列表
	 * @Method: findByMobMemberLogin 
	 * @Description: 
	 * @param channelNo	设备类型
	 * @param memberId	个人的memberId
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-12-11 上午11:48:47
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/findPushList", method = { RequestMethod.POST })
	public String findByMobMemberLogin(String channelNo,Long memberId){
		if(PubMethod.isEmpty(channelNo)||PubMethod.isEmpty(memberId)){
			Map errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "参数不能为空");
			return JSON.toJSONString(errorMap);
		}
		MobMemberLogin mobMemberLogin = new MobMemberLogin();
		mobMemberLogin.setChannelId(memberId);
		mobMemberLogin.setChannelNo(channelNo);
		List<MobMemberLogin> lsm = mobMemberLoginService.findByMobMemberLogin(mobMemberLogin);
		return JSON.toJSONString(lsm);
	}
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查看是否可以推送</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午2:58:29</dd>
	 * @param channelNo 推送的类型（01：接单王，02:个人端） (提示：改方法不做socket是否推送的判断)
	 * @param memberId	用户Id:memberId	支持多个id，需要逗号分隔
	 * @param type 消息类型
	 * @param content 消息内容
	 * @param extraParam 额外参数
	 * @return	
	 */
	@ResponseBody
	@RequestMapping(value = "/pushExt", method = { RequestMethod.POST })
	public String push(String pushType,String channelNo,String memberId, String type, String title, String content,
			String extraParam,String msgType) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MobPushController.canPush.001", "用户id不能为空！");
		}
		if(PubMethod.isEmpty(channelNo)){
			return paramsFailure("net.okdi.api.controller.MobPushController.canPush.002", "应用类型不能为空！");
		}
		if(PubMethod.isEmpty(type)){
			return paramsFailure("net.okdi.api.controller.MobPushController.canPush.002", "推送类型不能为空！");
		}
		if(PubMethod.isEmpty(title)){
			return paramsFailure("net.okdi.api.controller.MobPushController.canPush.002", "推送内容标题不能为空！");
		}
		MobMemberLogin mobMemberLogin = new MobMemberLogin();
		mobMemberLogin.setChannelId(Long.valueOf(memberId));
		mobMemberLogin.setChannelNo(channelNo);
		//List<MobMemberLogin> lsm = mobMemberLoginService.findByMobMemberLogin(mobMemberLogin);
		mobMemberLoginService.pushExt(pushType,channelNo, memberId, type, title, content, extraParam,msgType);
		Map m = new HashMap();
		m.put("pushflag", "success");
		return JSON.toJSONString(m);
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>ios推送初始化应用数量</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午2:58:29</dd>
	 * @param deviceToken 消息类型
	 * @return	
	 */
	@ResponseBody
	@RequestMapping(value = "/initBadge", method = { RequestMethod.POST })
	public String initBadge(String deviceToken){
		mobMemberLoginService.initBadge(deviceToken);
		return jsonSuccess();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/isDeliveryOnlineMember", method = { RequestMethod.POST })
	public String isDeliveryOnlineMember(String channelNo, Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.api.controller.MobPushController.canPush.001", "用户id不能为空！");
		}
		if(PubMethod.isEmpty(channelNo)){
			return paramsFailure("net.okdi.api.controller.MobPushController.canPush.002", "推送类型不能为空！");
		}
		String result = mobMemberLoginService.isDeliveryOnlineMember(channelNo,memberId);
		return JSON.toJSONString(result);
	}
	
	
	/**
	 *  客户推送 chunyang.tan add
	 *  TODO
	 */
	@ResponseBody
	@RequestMapping(value = "/customerReply", method = { RequestMethod.POST,RequestMethod.GET })
	public void customerReply(Long memberId,String mob){
		mobMemberLoginService.customerReply(memberId,mob);
	}

	/**
	 *  取件推送 杨凯
	 *  TODO
	 */
	@ResponseBody
	@RequestMapping(value = "/taskPush", method = { RequestMethod.POST,RequestMethod.GET })
	public void taskPush(Long memberId,String mob){
		mobMemberLoginService.taskPush(memberId,mob);
	}
}