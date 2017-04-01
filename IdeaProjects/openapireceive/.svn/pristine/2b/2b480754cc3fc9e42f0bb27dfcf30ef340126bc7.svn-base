package net.okdi.apiV1.controller;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.apiV1.service.ExpressSiteService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 快递站点类
 * @author jingguoqiang
 * @version V1.0
 */
@Controller
@RequestMapping("/expressSite")
public class ExpressSiteController extends BaseController {
	
	@Autowired 
	ExpressSiteService expressSiteService;


	

	  

	
	/**
	 * 第1个接口：登录网点ID 查询(站长或收派员)网点详细信息
	
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompVerifyInfo(Long loginCompId) {
		try {
			Map<String, Object> compInfoMap = expressSiteService.queryCompBasicInfo(loginCompId);
			return jsonSuccess(compInfoMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
/*-----------------------------------快递员操作的--------------------------------*/
	/**
	 * 
	 * 第2个接口：快递员 --申请入站
	 * @apiParam {String} member_id 人员ID
	 * @apiParam {String} audit_comp 所属公司id
	 * @apiParam {String} application_role_type 申请人角色类型 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiParam {String} audit_item 审核项 1:身份审核,2:归属审核
	 */
	@ResponseBody
	@RequestMapping(value = "/applyJoin", method = { RequestMethod.POST, RequestMethod.GET })
	
	public String applyJoin(String member_id,String audit_comp,String application_role_type,
			  String audit_item) {
		try {
			expressSiteService.applyJoin(member_id,audit_comp,application_role_type, audit_item);
			
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * 第2个接口：快递员 --申请入未标记站点
	 * @apiParam {String} member_id 人员ID
	 * @apiParam {String} belongToNetId 所属快递公司id
	 *  @apiParam {String} application_role_type 申请人角色类型 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 *  toRoleId 邀请人角色
	 */
	/*@ResponseBody
	@RequestMapping(value = "/applyJoinNoSign", method = { RequestMethod.POST, RequestMethod.GET })
	
	public String applyJoinNoSign(String member_id,String belongToNetId,String application_role_type, 
			 String toMemberPhone) {
		try {
			expressSiteService.applyJoinNoSign(member_id,belongToNetId,application_role_type,toMemberPhone);
			
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}*/
	
	
	/**
	 * 
	 * 第3个接口：快递员通过手机号查询站长入住状态
	 */
	@ResponseBody
	@RequestMapping(value = "/queryJoinState", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryJoinState(String member_phone) {
		try {
			Map<String, Object> arrayMap =expressSiteService.queryJoinState(member_phone);
			
			return jsonSuccess(arrayMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**

	 * @apiParam {String} fromMemberId 邀请人的ID
	 * @apiParam {String} fromMemberPhone 邀请人的手机号
	 * @apiParam {Integer} fromMemberRoleid 邀请人的角色
	 * @apiParam {Integer} toRoleId 被邀请人的角色
	 * @apiParam {String} toMemberPhone 被邀请人的手机号
	 * @apiSuccessExample {json} Success-Response: HTTP/1.1 200 OK
	 *                    {"RESULT":"success","DATA":{"check":"ok"}}
	 * @apiErrorExample {json} Error-Response: HTTP/1.1 200 OK
	 *                  {"RESULT":"error","DATA":{"accountCard":"账户号不能为空<\/a>"}}
	 * @apiVersion 0.1.0
	 */
	/*@ResponseBody
	@RequestMapping(value = "/invite", method = { RequestMethod.POST,RequestMethod.GET })
	public String invite(String fromMemberId, String fromMemberPhone,
			  Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId) {
		if (PubMethod.isEmpty(responsibleTelephone)) {
			return PubMethod.paramError("publicapi.OutletsController.invite",
					"店长手机号不能为空!");
		}
	  try {
		   String insertStatus = expressSiteService.invite(fromMemberId,fromMemberPhone, fromMemberRoleid, toMemberPhone,toRoleId);
		  return jsonSuccess("已成功邀请店长");
	  } catch (Exception e) {
		e.printStackTrace();
		return jsonFailure(e);
	  }
	}*/
   
/*--------------------------------以下是站长的操作接口*/	
	/**
	 * 
	 * 第1个接口：提交网点基本信息

	 * @param loginCompId	登录网点ID
	 *        compName   网点名称
	 * @param belongToNetId  	归属快递id
	 * @param longitude 精度
	 * @param latitude 纬度
	 * @param county  区县
	 * @param address 网点详细地址
       @param responsibleTelephone  负责人电话
       @apiParam {String} member_id 人员ID
	 * @apiParam {String} audit_comp 所属公司
	 * @return  新生成的compinfo信息的compid
	 */
	@ResponseBody
	@RequestMapping(value = "/submitBasCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitBasCompInfo(Long loginCompId, String compName, String belongToNetId, String county, 
			Double longitude, Double latitude,String address,String responsibleTelephone,String member_id) {
		try {
			String compid =expressSiteService.submitBasCompInfo(loginCompId, compName, belongToNetId, county, longitude, 
					latitude,address,responsibleTelephone,member_id);
			return jsonSuccess(compid);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	

	
	
	/**
	 * 
	 * 第2个接口：提交网点认证信息xxx

	 * @param loginCompId	登录网点ID
	 * 
	 * @param responsible	负责人姓名
	 * @param responsibleTelephone	负责人电话
	 * @param responsibleNum	负责人身份证号
	 * @param frontImg		负责人身份证正面   5
	 *  @param holdImg	营业执照    2
	 * 
	 * @param reverseImg	店铺门面照照片  8
       @apiParam {Long} memberId 人员ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitCompVerifyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitCompVerifyInfo(Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, 
			String frontImg,String holdImg, String reverseImg,Long memberId) {
		try {
			Map map =expressSiteService.submitCompVerifyInfo(loginCompId, responsible, responsibleTelephone, responsibleNum,
					frontImg,holdImg, reverseImg,memberId);
			
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * 第3个接口：
	 * 方法描述:>添加网点/营业部信息
	 * <dt><span class="strong">作者:</span></dt><dd>guoqiang.jing</dd>
	 * @param memberId 创建人ID
	 * @param netId 网络ID-快递公司id
	 * @param compTypeNum 网点类型 1006站点 1050营业分部 
	 * @param compName 网点名称
	 * @param compTelephone 网点电话-负责人电话
	 * @param longitude 精度
	 * @param latitude 纬度
	 * @param county  区县
	 * @param address 网点详细地址
	 * @return	 
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Double longitude, Double latitude, String county,String address) {
		try {
			BasCompInfo basCompInfo=expressSiteService.saveCompInfo(memberId, netId, compTypeNum, compName, compTelephone,
					longitude,latitude, county,address);
			return jsonSuccess(basCompInfo.getCompId());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
/*----------------------------------运营平台*/
	/**
	 * 第1个接口：
	 * 方法描述:>审核站点
	 * <dt><span class="strong">作者:</span></dt><dd>guoqiang.jing</dd>
	 * @param memberId 创建人ID
	 * @param netId 网络ID-快递公司id
	 * @param compTypeNum 网点类型 1006站点 1050营业分部 
	 * @param compName 网点名称
	 * @param compTelephone 网点电话-负责人电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @return	 
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/auditSite", method = { RequestMethod.POST, RequestMethod.GET })
	public String auditSite(Long compId, String compStatus) {
		try {
			expressSiteService.auditSite(compId, compStatus);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * 更新网点审核状态为通过
	 * @param compId 网点ID
	 * @param businessCode 选中的信息
	 *  @param  relationType  对应的信息类型  1:营业执照  2:站点  3:营业许可证
	 */
	@ResponseBody
	@RequestMapping(value = "/auditCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateCompStatus(Long compId, String businessCode, String relationType) {
		try {
			Map map=expressSiteService.updateCompStatus(compId, businessCode, relationType);
			return JSON.toJSONString(map);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * 更新网点审核状态为不通过
	 * @param compId 网点ID
	 * @param refuseDesc 拒绝的原因
	 *  @param  
	 */
	@ResponseBody
	@RequestMapping(value = "/refuseCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateCompStatusToRefuse(Long compId, String refuseDesc,String mob,String memberId) {
		try {
			expressSiteService.updateCompStatusToRefuse(compId, refuseDesc);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * 查询网点名称
	 * @param compId 网点ID
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompName", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompName(Long compId) {
		try {
			String compName = expressSiteService.queryCompName(compId);
			return jsonSuccess(compName);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * 邀请奖励--插入员工审核表
	 * @param compId 
	 */
	@ResponseBody
	@RequestMapping(value = "/saveEmployeeaudit", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveEmployeeaudit(String memberId,String compId,String roleType) {
		try {
			expressSiteService.saveEmployeeaudit(memberId,compId,roleType);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * 
	 * 临时推广,只给登陆的人发推送
	 * @param phone 手机号
	 */
	@ResponseBody
	@RequestMapping(value = "/sendTS", method = { RequestMethod.POST, RequestMethod.GET })
	public String sendTS(String title,String content,String extraParam,String idkey, String pushWay,String platform,String useType,String userType,String startTime1,String endTime1) {
		try {
			expressSiteService.sendTS(title,content,extraParam,idkey,pushWay, platform, useType, userType, startTime1, endTime1);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * 公告推送,所有人发推送
	 * @param phone 手机号
	 */
	@ResponseBody
	@RequestMapping(value = "/sendAnnounceTS", method = { RequestMethod.POST, RequestMethod.GET })
	public String sendAnnounceTS(String announceType,String title,String content,String creator,String idkey, String pushWay) {
		try {
			System.out.println("系统公告");
			 expressSiteService.sendAnnounceTS(announceType,title,content,creator,idkey,pushWay);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/** 
	* @Description: 查询电话号
	* @author: 静国强
	* param：phone 电话
	* 
	* @date 2015-8-13 下午4:51:39 
	*/ 
	@ResponseBody
	@RequestMapping(value = "/queryPhones", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryPhones(String phone,Integer currentPage,Integer pageSize) {  
		
		try {
			Map map = expressSiteService.queryPhones(phone, currentPage, pageSize);
			return jsonSuccess(map);
		} catch (Exception e) {
			return jsonFailure();
		}
	     
    }
	
	/** 
	* @Description: 导出电话号
	* @author: 静国强
	* param：phone 电话
	* 
	* @date 2015-8-13 下午4:51:39 
	*/ 
	@ResponseBody
	@RequestMapping(value = "/exportPhones", method = { RequestMethod.POST,RequestMethod.GET })
	public String exportPhones(String phone) {  
		
		try {
			List listp = expressSiteService.exportPhones(phone);
			return jsonSuccess(listp);
		} catch (Exception e) {
			return jsonFailure();
		}
	     
    }
	//查看我收到的公告
	@ResponseBody
	@RequestMapping(value = "/queryMyMessage", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryMyMessage(String phone) {  
		
		try {
			return jsonSuccess(expressSiteService.queryMyMessage(phone));
		} catch (Exception e) {
			return jsonFailure();
		}
	     
    }
	//清空所有公告
	@ResponseBody
	@RequestMapping(value = "/deleteMyAllMessage", method = { RequestMethod.POST,RequestMethod.GET })
	public String deleleMyAllMessage(String memberPhone) {  
		
		try {
			expressSiteService.deleleMyAllMessage(memberPhone);
			return jsonSuccess(null);
		} catch (Exception e) {
			return jsonFailure();
		}
	     
    }
	
	//删除某一条公告
	@ResponseBody			  
	@RequestMapping(value = "/deleteMyOneMessage", method = { RequestMethod.POST,RequestMethod.GET })
	public String deleleMyOneMessage(Long pushId) {  
		
		try {
			expressSiteService.deleleMyOneMessage(pushId);
			return jsonSuccess(null);
		} catch (Exception e) {
			return jsonFailure();
		}
	     
    }
	
	//阅读某一条公告
	@ResponseBody			  
	@RequestMapping(value = "/readOneMessage", method = { RequestMethod.POST,RequestMethod.GET })
	public String readOneMessage(Long pushId) {  
		
		try {
			expressSiteService.readOneMessage(pushId);
			return jsonSuccess(null);
		} catch (Exception e) {
			return jsonFailure();
		}
    }
}