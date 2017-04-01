package net.okdi.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.service.NetDotInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/netDotInfo")
public class NetDotInfoController extends BaseController{

	@Autowired
	NetDotInfoService netDotInfoService;
	
	/**
	 * 	保存归属认证信息
	 * @param netId 网络ID
	 * @param netName 网络名称
	 * @param compId 公司ID
	 * @param compName 公司名称
	 * @param memberId 收派员ID
	 * @param memberName 收派员姓名
	 * @param memberMobile 收派员手机
	 * @param lng 经度
	 * @param lat 纬度
	 * @param memo 备注
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveBelongingAuthentictaInfo" , method={RequestMethod.POST,RequestMethod.GET})
	public String saveBelongingAuthentictaInfo(Long id,Long netId,String netName,Long compId,
			 String compName,Long memberId,String memberName,String memberMobile,
			 Double lng,Double lat,String memo)
	{
		if(PubMethod.isEmpty(netId) || PubMethod.isEmpty(memberId)){
			return paramsFailure("saveBelongingAuthentictaInfo","参数不能为空");
		}
		try{
			netDotInfoService.saveBelongingAuthentictaInfo(id, netId, netName, compId, compName, memberId, memberName, memberMobile, lng, lat, memo);
			return jsonSuccess();
		}catch(Exception e)
		{
			return jsonFailure();
		}
	}
	
	/**
	 * 	保存站点认证信息
	 * @param loginMemberId	登录人员ID
	 * @param loginCompId	登录网点ID
	 * @param responsible	负责人姓名
	 * @param responsibleTelephone	负责人电话
	 * @param responsibleNum	负责人身份证号
	 * @param businessLicenseImg	营业执照
	 * @param expressLicenseImg		快递许可证
	 * @param frontImg		负责人身份证正面
	 * @param reverseImg	负责人身份证反面
	 * @param holdImg		负责人身份证反面
	 * @param firstSystemImg	系统截图一
	 * @param secondSystemImg	系统截图二
	 * @param thirdSystemImg	系统截图三
	 * @param verifyType	 认证方式 3营业及许可证 2证件及截图
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdateNetDotInfo" , method={RequestMethod.POST,RequestMethod.GET})
	public String saveOrUpdateNetDotInfo(Long loginMemberId,Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg, String secondSystemImg, String thirdSystemImg, Short verifyType)
	{
		try {
			this.netDotInfoService.saveOrUpdateNetDotInfo(loginMemberId,loginCompId, responsible, responsibleTelephone,responsibleNum, businessLicenseImg,
					expressLicenseImg, frontImg, reverseImg, holdImg, firstSystemImg, secondSystemImg, thirdSystemImg, verifyType, (short) 0);
			Map<String, Object> compInfo = new HashMap<String, Object>();
			compInfo.put("compStatus", (short) 0);
			return jsonSuccess(compInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * 	查询认证信息
	 * @param compId
	 * @param loginCompId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNetDotInfo" , method={RequestMethod.POST,RequestMethod.GET})
	public String queryNetDotInfo(Long compId)
	{
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("net.okdi.api.controller.MemberInfoController.getMasterPhone.002", "compId不能为空！");
		}
		try {
			
			Map<String, Object> netDotBasicInfoMap = this.netDotInfoService.queryNetDotBasicInfo(compId);	//查询网点的基本信息
			Map<String, Object> netDotVerifyInfoMap = this.netDotInfoService.queryNetDotVerifyInfo(compId);	//查询网点的认证信息
			if(!PubMethod.isEmpty(netDotVerifyInfoMap)){
				netDotBasicInfoMap.putAll(netDotVerifyInfoMap);
			}
			
			List<Map<String, Object>> basicInfo = this.netDotInfoService.queryMemberForComp(compId, null);	//查询网点的人员基本信息
			List<Map<String, Object>> saveBasicInfo = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : basicInfo) {
				if (map.get("roleId").toString().equals("1")) {
					saveBasicInfo.add(map);
				}
			}
			
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("netDotBasicInfoMap", netDotBasicInfoMap);
			result.put("saveBasicInfo", saveBasicInfo);
			return jsonSuccess(result);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 	修改网店状态
	 * @param compId 网点ID
	 * @param compStatus 网点状态 -1创建 0提交待审核 1审核成功 2审核失败
	 * @param auditId 最后一次审核ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateNetDotStatus", method={RequestMethod.POST,RequestMethod.GET})
	public String updateNetDotStatus(Long compId, Short compStatus, Long auditId)
	{
		try{
			netDotInfoService.updateNetDotStatus(compId,compStatus,auditId);
			return jsonSuccess();
		}catch(Exception e)
		{
			return jsonFailure(e);
		}
	}
}
