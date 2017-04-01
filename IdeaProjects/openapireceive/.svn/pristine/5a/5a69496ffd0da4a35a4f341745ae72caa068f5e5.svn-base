package net.okdi.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasEmployeeRelationMapper;
import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasEmployeeRelation;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.LoginInfo;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.service.MemberInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * 客户信息
 * @author xianxian.chang
 * @version V1.0
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {
	
	private Logger logger = Logger.getLogger(UserInfoController.class);
	
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private EhcacheService ehcacheService;	
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private BasEmployeeRelationMapper basEmployeeRelationMapper;
	@ResponseBody
	@RequestMapping(value = "/getLoginInfoByMemberId", method = { RequestMethod.POST})
	public String getLoginInfoByMemberId(String memberId) {
		LoginInfo loginInfo = ehcacheService.get("userInfoCache", memberId, LoginInfo.class);//用户信息缓存中取出登陆实体
		if(PubMethod.isEmpty(loginInfo)){//如果为空 需要查询重组
			loginInfo = new LoginInfo();
			/****************通过memberId取出用户信息*/
			MemberInfo memberInfo = this.memberInfoService.getMemberInfoById(Long.parseLong(memberId));
			if(PubMethod.isEmpty(memberInfo)){
				loginInfo.setMemberName(null);
			}else{
				loginInfo.setMemberName(memberInfo.getMemberName());
			}
			/***从缓存中取出人员的归属关系信息**//*
			BasEmployeeRelation relation = ehcacheService.get("employeeRelationCache", memberId, BasEmployeeRelation.class); 
			if(PubMethod.isEmpty(relation)){
				relation = basEmployeeRelationMapper.queryCompIdByMemberId(Long.parseLong(memberId));
				ehcacheService.put("employeeRelationCache", memberId, relation);
			}*/
			Map<String, Object> mapV = this.memberInfoMapper.getRelationInfo(Long.parseLong(memberId));
			/*map.put("relationFlag", PubMethod.isEmpty(mapV)?"":String.valueOf(mapV.get("auditOpnion")));
			map.put("compId", PubMethod.isEmpty(mapV)?"":String.valueOf(mapV.get("compId")));
			map.put("roleId", PubMethod.isEmpty(mapV)?"":String.valueOf(mapV.get("roleId")));*/
			if(!PubMethod.isEmpty(mapV) && !PubMethod.isEmpty((mapV.get("compId"))) && !"2".equals(String.valueOf(mapV.get("auditOpnion")))){
				System.out.println("******************************roleid："+mapV.get("roleId"));
				loginInfo.setRoleId(Short.parseShort(mapV.get("roleId").toString()));//人员的角色
				Long compId = Long.parseLong(String.valueOf(mapV.get("compId")));//站点id
				if(compId != null && !"".equals(compId)){
					loginInfo.setCompId(compId);
					BasCompInfo compInfo = this.ehcacheService.get("compCache", compId+"", BasCompInfo.class);
					if(PubMethod.isEmpty(compInfo)){
						compInfo = this.basCompInfoMapper.findById(compId);
					}
					if(!PubMethod.isEmpty(compInfo)){
						loginInfo.setCompanyName(compInfo.getCompName());
						loginInfo.setCompTypeNum(compInfo.getCompTypeNum());
						loginInfo.setCompStatus(compInfo.getCompStatus()+"");
						Long netId = compInfo.getBelongToNetId();
						loginInfo.setNetId(netId);
						BasNetInfo netInfo = this.ehcacheService.get("netCache", netId+"", BasNetInfo.class);
						if(!PubMethod.isEmpty(netInfo)){
							loginInfo.setNetId(netInfo.getNetId());
							loginInfo.setNetName(netInfo.getNetName());
							loginInfo.setNetNum(netInfo.getNetNum());
						}
					}
				}
			}
		}
		//---------------调通行证接口 组装loginInfo------------------
		Map<String, String> mapParam = new HashMap<String, String>();
		mapParam.put("memberId", memberId + "");
		
		//调用通行证接口
		String response = Post(constPool.getUserCenterUrl(), mapParam);
		if(response == null || "".equals(response)){
			logger.info("登录时通行证调用异常");
		}else{
			response=new DesEncrypt().convertPwd(response,"");
			List<JSONObject> jsonObjectList = JSON.parseArray(response, JSONObject.class);
			JSONObject jsonObject = jsonObjectList.get(0);
			loginInfo.setUserName(jsonObject.getString("username"));
			loginInfo.setImgUrl(jsonObject.getString("imgUrl"));
			loginInfo.setLoginPwd(jsonObject.getString("loginPwd"));
		}
		//---------------调通行证接口loginInfo------------------
		
		
//		c.comp_id, c.comp_name,
//		c.comp_type_num, c.comp_status,
//		n.net_id, n.net_name, n.net_num
//-----------------------------------------------------------------------------		
		
//		BasCompInfo basCompInfo = compInfoService.g



//		if(loginInfo == null){
//			// 根据memberId获取单个memberInfo
//			loginInfo = memberInfoMapper.getLoginInfoByMemberId(Long
//					.parseLong(memberId));
//			
//			// 如果不存在人员信息数据，那么构造一个空的loginInfo实体
//			if (loginInfo == null) {
//				loginInfo = new LoginInfo();
//			}
//			
//			//---------------调通行证接口 组装loginInfo------------------
//			Map<String, String> mapParam = new HashMap<String, String>();
//			mapParam.put("memberId", memberId + "");
//			
//			//调用通行证接口
//			String response = Post(constPool.getUserCenterUrl(), mapParam);
//			response=new DesEncrypt().convertPwd(response,"");
//			
//			List<JSONObject> jsonObjectList = JSON.parseArray(response, JSONObject.class);
//			JSONObject jsonObject = jsonObjectList.get(0);
//			loginInfo.setUserName(jsonObject.getString("username"));
//			loginInfo.setImgUrl(jsonObject.getString("imgUrl"));
//			loginInfo.setLoginPwd(jsonObject.getString("loginPwd"));
//			//---------------调通行证接口loginInfo------------------
//			
//			//放入缓存
//			ehcacheService.put("userInfoCache", memberId, loginInfo);
//		}
		
		return JSON.toJSONString(loginInfo);
	}
}
