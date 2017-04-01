package net.okdi.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.ExpCompElectronicFence;
import net.okdi.api.service.CompInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.CheckCID;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 网点信息
 * @author shihe.zhai
 * @version V1.0
 */
@Controller
@RequestMapping("/compInfo")
public class CompInfoController extends BaseController {
	@Autowired
	private CompInfoService compInfoService;
	
	@Autowired
	private EhcacheService ehcacheService;

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>获取网点提示信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午4:03:01</dd>
	 * @param netId 网络ID
	 * @param compName 网点名称关键字
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param addressId 乡镇（五级地址）ID
	 * @return {"data":[{"compId":1000418655,"compName":"BEX北京CBD分部","firstLetter":"B"}...],"success":true}
	 * 	compId:公司id
	 *  compName:公司名称
	 *  firstLetter:首指母
     * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.getPromptComp.001", "获取网点提示信息,netId参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.getPromptComp.002", "获取网点提示信息,addressId参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.getPromptComp.003", "获取网点提示信息,compTypeNum参数异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPromptComp", method = { RequestMethod.POST, RequestMethod.GET })
	public String getPromptComp(Long netId, String compName, String compTypeNum,Long addressId) {
		try {
			return jsonSuccess(this.compInfoService.getPromptComp(netId, compName, compTypeNum,addressId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>获取注册网点列表（手机端）</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午4:53:51</dd>
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param addressId 乡镇（五级地址）ID
	 * @param roleId 注册角色 -1 后勤 0收派员 1站长
	 * @return {"data":[{"compId":1000418655,"compName":"BEX北京CBD分部","firstLetter":"B"}...],"success":true} 
	 * 	compId:公司id
	 * 	compName:公司名称			
	 * 	firstLetter:首指母
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.getPromptCompForMobile.001", "获取网点提示信息,netId参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.getPromptCompForMobile.002", "获取网点提示信息,addressId参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.getPromptCompForMobile.003", "获取网点提示信息,compTypeNum参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.getPromptCompForMobile.004", "获取网点提示信息,roleId参数异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPromptCompForMobile", method = { RequestMethod.POST, RequestMethod.GET })
	public String getPromptCompForMobile(Long netId, String compTypeNum,Long addressId,Short roleId) {
		try {
			return jsonSuccess(this.compInfoService.getPromptCompForMobile(netId, compTypeNum,addressId,roleId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>获取同网络下重名网点信息(1006已注册的网点信息 1050已注册网点及未领用1003)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午4:56:35</dd>
	 * @param loginCompId 登录网点ID
	 * @param netId 网络ID
	 * @param compName 网点名称 
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param flag 查询标识 0 不匹配1003未被领用数据 1匹配1003未被领用数据
	 * @return {"data":{"compId":13816306628820992,"compName":"DC城分部","firstLetter":"D"},"success":true}
	 * 	compId:公司id
	 * 	compName:公司名称			
	 * 	firstLetter:首指母
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.getSameCompName.001", "获取同网络下重名网点信息,netId参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.getSameCompName.002", "获取同网络下重名网点信息,compTypeNum参数异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getSameCompName", method = { RequestMethod.POST, RequestMethod.GET })
	public String getSameCompName(Long loginCompId, Long netId, String compName, String compTypeNum, Short flag) {
		try {
			return jsonSuccess(this.compInfoService.getSameCompName(loginCompId, netId, compName, compTypeNum, flag));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询同网络下网点重名信息（手机端 包含未注册信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午4:58:42</dd>
	 * @param netId 网络ID
	 * @param compName 网点名称
	 * @return {"data":{"compId":13816306628820992,"compName":"DC城分部","firstLetter":"D"},"success":true}
	 * 	compId:公司id
	 * 	compName:公司名称			
	 * 	firstLetter:首字母
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.getSameCompNameForMobile.001", "获取同网络下重名网点信息,netId参数异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getSameCompNameForMobile", method = { RequestMethod.POST, RequestMethod.GET })
	public String getSameCompNameForMobile(Long netId, String compName) {
		try {
			return jsonSuccess(this.compInfoService.getSameCompNameForMobile(netId, compName));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>保存未注册网点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:00:35</dd>
	 * @param memberId 创建人ID
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部 
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @return {"data":{"belongToCompId":-1,"belongToNetId":1503,"compAddress":"北京-朝阳区|测试","compAddressId":11000203,
	 * "compId":13816306628820992,"compName":"DC城分部","compRegistWay":6,"compStatus":-1,"compTel":"15999990000,",
	 * "compTelephone":"15999990000","compTypeNum":"1006","createTime":1414458768000,"createUser":121410572747047,
	 * "firstLetter":"D","goodsPaymentStatus":0,"modifiyTime":1415782892671,"registFlag":0,"relationCompId":-1,
	 * "taoGoodsPaymentStatus":-1,"writeSendStatus":0},"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.001", "保存公司信息参数异常,memberId参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.002", "保存公司信息参数异常,netId参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.003", "保存公司信息参数异常,compTypeNum参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.004", "保存公司信息参数异常,compName参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.005", "保存公司信息参数异常,compTelephone参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.006", "保存公司信息参数异常,addressId参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.007", "保存公司信息参数异常,address参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.008", "保存公司信息参数异常,compTelephone参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompInfo.009", "网点名称已存在"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Long addressId, String address) {
		try {
			return jsonSuccess(this.compInfoService.saveCompInfo(memberId, netId, compTypeNum, compName, compTelephone,
					addressId, address, (short)6));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断身份证号格式是否正确</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:16:38</dd>
	 * @param idNum 身份证号
	 * @return {"data":{"mark":true},"success":true}
	 * mark=true 格式正确
	 * mark=false 格式不正确
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/checkIdNum", method = { RequestMethod.POST, RequestMethod.GET })
	public String checkIdNum(String idNum) {
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			boolean mark = CheckCID.isIdCard(idNum);
			dataMap.put("mark", mark);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>保存/更新网点基础信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:09:14</dd>
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录公司ID
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param useCompId 领用网点ID
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @param longitude 网点经度
	 * @param latitude 网点纬度
	 * @param compRegistWay 网点注册方式 5站点 6手机端
	 * @return {"data":{"belongToCompId":-1,"belongToNetId":1503,"compAddress":"北京-朝阳区|测试","compAddressId":11000203,
	 * "compId":13816306628820992,"compName":"DC城分部","compRegistWay":-1,"compStatus":-1,"compTel":"15999990000,",
	 * "compTelephone":"15999990000","compTypeNum":"1006","createTime":1414458768000,"createUser":121410572747047,
	 * "firstLetter":"D","goodsPaymentStatus":0,"latitude":39.958953,"longitude":116.521695,"modifiyTime":1415782892671,
	 * "registFlag":1,"relationCompId":59514,"taoGoodsPaymentStatus":-1,"writeSendStatus":0},"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.001", "获取登录用户信息异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.002", "获取登录网点信息异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.003", "网点所处状态禁止修改信息"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.004", "存在重名网点禁止修改"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.005", "compTelephone参数格式异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.006", "compName参数非空异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.007", "compTelephone参数非空异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.008", "netId、compTypeNum、addressId、address、compRegistWay参数非空异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.009", "网络禁止修改"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompInfo.001", "领用网点信息类型异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompInfo.002", "存在重名网点禁止新增"</dd>
     * <dd>"openapi.CompInfoServiceImpl.getSameCompName.001", "获取同网络下重名网点信息,参数异常"</dd>
     * <dd>"openapi.MemberInfoServiceImpl.doAddMemberToComp.001","该人员已于其他站点建立关系 "</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdateCompBasicInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveOrUpdateCompBasicInfo(Long loginMemberId, Long loginCompId, Long netId, String compTypeNum, Long useCompId, String compName, String compTelephone,
			Long addressId, String address, Double longitude, Double latitude, Short compRegistWay) {
		try {
			BasCompInfo compInfo = this.compInfoService.saveOrUpdateCompBasicInfo(loginMemberId, loginCompId, netId, compTypeNum, useCompId, compName.trim(), compTelephone,
					addressId, address, longitude, latitude, compRegistWay);
			return jsonSuccess(compInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>新增/更新认证信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:29:01</dd>
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录网点ID
	 * @param responsible 负责人姓名
	 * @param responsibleTelephone 负责人电话
	 * @param responsibleNum 负责人身份证号
	 * @param businessLicenseImg 营业执照
	 * @param expressLicenseImg 快递许可证
	 * @param frontImg 负责人身份证正面
	 * @param reverseImg 负责人身份证反面
	 * @param holdImg 负责人身份证反面
	 * @param firstSystemImg 系统截图一
	 * @param secondSystemImg 系统截图二
	 * @param thirdSystemImg 系统截图三
	 * @param verifyType 认证方式 3营业及许可证 2证件及截图
	 * @return {"data":{"compStatus":-1},"success":true}
	 * compStatus=-1 网点状态变更为未提交
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.001", "保存认证信息参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.005", "保存认证获取网点异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.006", "网点状态禁止修改信息"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompImageInfo.001", "认证类型异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/saveOrUpdateCompVerifyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveOrUpdateCompVerifyInfo(Long loginMemberId,Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg, String secondSystemImg, String thirdSystemImg, Short verifyType) {
		try {
			this.compInfoService.saveOrUpdateCompVerifyInfo(loginMemberId,loginCompId, responsible, responsibleTelephone, responsibleNum, businessLicenseImg, expressLicenseImg, frontImg,
					reverseImg, holdImg, firstSystemImg, secondSystemImg, thirdSystemImg, verifyType, (short) -1);
			Map<String, Object> compInfo = new HashMap<String, Object>();
			compInfo.put("compStatus", (short) -1);
			return jsonSuccess(compInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询网点基础信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:19:59</dd>
	 * @param loginCompId 登录网点ID
	 * @return {"data":{"address":"北京-朝阳区|测试","addressId":11000203,"compId":13816306628820992,"compName":"DC城分部",
	 * "compStatus":-1,"compTelephone":"15999990000","compTypeNum":"1006","latitude":39.958953,"longitude":116.521695,"netId":1503,
	 * "netName":"百世汇通","useCompId":59514},"success":true}
	 * 	address:地址名称
	 *  addressId:地址id
	 *  compId:公司id
	 *  compName:公司名称
	 *  compStatus:公司状态
			-1:创建（未认证）
			0:提交待审核,（未认证）
			1:审核通过,（已认证）
			2: 审核不通过（未认证）
	 *  compTelephone:公司电话
	 *  compTypeNum:公司分类代码
			1006 加盟公司
			1050 营业分部
	 *  latitude:纬度
	 *  longitude:经度
	 *  netId:快递网络ID
	 *  netName:快递网络名称
	 *  useCompId:领用网点id 
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.queryCompBasicInfo.001", "获取网点基础信息参数异常"</dd>  
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompBasicInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompBasicInfo(Long loginCompId) {
		try {
			Map<String, Object> compInfoMap = this.compInfoService.queryCompBasicInfo(loginCompId);
			return jsonSuccess(compInfoMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询网点认证信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:28:15</dd>
	 * @param loginCompId 登录网点ID
	 * @return {"data":{"firstSystemImg":"2014103115405813143.png",
	 * "firstSystemImgUrl":"http://localhost:8080/image/comp/2014103115405813143.png","frontImg":"2014103115404448036.png",
	 * "frontImgUrl":"http://localhost:8080/image/comp/2014103115404448036.png","holdImg":"2014103115405423722.png",
	 * "holdImgUrl":"http://localhost:8080/image/comp/2014103115405423722.png","responsible":"风驰六",
	 * "responsibleIdNum":"110101199101018794","responsibleTelephone":"18311110006","reverseImg":"2014103115404854610.png",
	 * "reverseImgUrl":"http://localhost:8080/image/comp/2014103115404854610.png","verifyType":2},"success":true}
		firstSystemImgUrl:第一张系统截图url
		frontImgUrl:正面照
		holdImgUrl:手持照
		responsibleIdNum:负责人身份证号
		reverseImgUrl:反面照
		responsible：负责人
		responsibleTelephone：负责人手机
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompVerifyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompVerifyInfo(Long loginCompId) {
		try {
			Map<String, Object> compInfoMap = this.compInfoService.queryCompVerifyInfo(loginCompId);
			return jsonSuccess(compInfoMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * (2015-11月 新快递)根据id查询站点详细信息
	 * @param loginCompId 登录网点ID
	 * @return {"data":{"address":"北京-朝阳区|安定路20号东方燕都商务楼","addressId":11000203,"auditDesc":"","compId":13847259081342976,
	 * "compName":"风驰申通快递三站","compStatus":1,"compTelephone":"010-87954421","compTypeNum":"1006",
	 * "firstSystemImg":"2014103109525312580.png","firstSystemImgUrl":"http://localhost:8080/image/comp/2014103109525312580.png",
	 * "frontImg":"2014103109524245328.png","frontImgUrl":"http://localhost:8080/image/comp/2014103109524245328.png",
	 * "holdImg":"2014103109524949228.png","holdImgUrl":"http://localhost:8080/image/comp/2014103109524949228.png",
	 * "latitude":39.921518,"longitude":116.554823,"netId":1500,"netName":"申通快递","responsible":"风驰三",
	 * "responsibleIdNum":"110101199101012798","responsibleTelephone":"18311110003","reverseImg":"2014103109524532195.png",
	 * "reverseImgUrl":"http://localhost:8080/image/comp/2014103109524532195.png","secondSystemImg":"2014103109525918867.png",
	 * "secondSystemImgUrl":"http://localhost:8080/image/comp/2014103109525918867.png","verifyType":2},"success":true}
 		 addressId:地址id
 		 auditDesc:审核意见
 		 compId:公司id
 		 compName:公司名称
 		 compStatus:公司状态
 		 	-1:创建（未认证）
			0:提交待审核,（未认证）
			1:审核通过,（已认证）
			2: 审核不通过（未认证）
 		 compTelephone:公司电话
 		 compTypeNum:公司分类代码
			1006 加盟公司
			1050 营业分部 		 
        holdImg:    营业执照
        holdImgUrl:  营业执照url
		frontImg:负责人正面照名称
		frontImgUrl:负责人正面照url
	    latitude:纬度
	    longitude:经度		
	    netId:快递网络id
	    netName:快递网络名称
	    responsible:负责人
	    responsibleIdNum:负责人身份证号
	    responsibleTelephone:负责人电话
	    reverseImg:门面照名称
	    reverseImgUrl:门面照所属url
	    verifyType:消息类型
			0：短信
			1：邮件
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.queryCompBasicInfo.001", "获取网点基础信息参数异常"</dd>	    
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfo(Long loginCompId) {
		try {
			Map<String, Object> compBasicInfoMap = this.compInfoService.queryCompBasicInfo(loginCompId);
			Map<String, Object> compVerifyInfoMap = this.compInfoService.queryCompVerifyInfo(loginCompId);
			if(!PubMethod.isEmpty(compVerifyInfoMap)){
				compBasicInfoMap.putAll(compVerifyInfoMap);
			}
			return jsonSuccess(compBasicInfoMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 6．通过ID查找站点人员明细信息
	 * @param compId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoById", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoById(Long compId) {
		try {
			Map<String, Object> compBasicInfoMap = this.compInfoService.queryCompBasicInfo(compId);
			Map<String, Object> compVerifyInfoMap = this.compInfoService.queryCompVerifyInfo(compId);
			Map<String,Object> memberInfoMap=this.compInfoService.queryMemberByCompId(compId);
			if(PubMethod.isEmpty(compVerifyInfoMap.get("businessLicenseImgUrl"))){
				compVerifyInfoMap.put("businessLicenseImgUrl", "");
			}
			if(PubMethod.isEmpty(compVerifyInfoMap.get("expressLicenseImgUrl"))){
				compVerifyInfoMap.put("expressLicenseImgUrl", "");
			}
			if(!PubMethod.isEmpty(compVerifyInfoMap)){
				compBasicInfoMap.putAll(compVerifyInfoMap);
			}
			if(!PubMethod.isEmpty(memberInfoMap)){
				compBasicInfoMap.putAll(memberInfoMap);
			}
			String addressAdd="";
			if(!PubMethod.isEmpty(compBasicInfoMap.get("address"))){
				String address=(String) compBasicInfoMap.get("address");
				String add[]=address.split("\\|");
				addressAdd=add[0];
				compBasicInfoMap.put("addressDq", addressAdd);
			}
			return jsonSuccess(compBasicInfoMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>提交网点认证信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:20:46</dd>
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录网点ID
	 * @param responsible 负责人姓名
	 * @param responsibleTelephone 负责人电话
	 * @param responsibleNum  负责人身份证号
	 * @param businessLicenseImg 营业执照
	 * @param expressLicenseImg 快递许可证
	 * @param frontImg 负责人身份证正面
	 * @param reverseImg 负责人身份证反面
	 * @param holdImg 手持身份证
	 * @param firstSystemImg 系统截图一
	 * @param secondSystemImg 系统截图二
	 * @param thirdSystemImg 系统截图三
	 * @param verifyType  认证方式 3营业及许可证 2证件及截图
	 * @return {"data":{"compStatus":0},"success":true}
	 * compStatus=0 网点状态标记为提交待审核
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.001", "保存认证信息参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.002", "资料未完善禁提交"-负责人姓名、电话、许可证</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.003", "资料未完善禁提交"-营业及许可证</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompVerifyInfo.004", "资料未完善禁提交"-证件及截图</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.005", "保存认证获取网点异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveOrUpdateCompBasicInfo.006", "网点状态禁止修改信息"</dd>
     * <dd>"openapi.CompInfoServiceImpl.saveCompImageInfo.001", "认证类型异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/submitCompVerifyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitCompVerifyInfo(Long loginMemberId,Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg, String secondSystemImg, String thirdSystemImg, Short verifyType) {
		try {
			this.compInfoService.saveOrUpdateCompVerifyInfo(loginMemberId,loginCompId, responsible, responsibleTelephone, responsibleNum, businessLicenseImg, expressLicenseImg, frontImg,
					reverseImg, holdImg, firstSystemImg, secondSystemImg, thirdSystemImg, verifyType, (short) 0);
			Map<String, Object> compInfo = new HashMap<String, Object>();
			compInfo.put("compStatus", (short) 0);
			return jsonSuccess(compInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新网点信息（审核通过后）</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:26:27</dd>
	 * @param loginCompId 登录网点ID
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 地址ID
	 * @param address 详细地址
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param responsibleTelephone 负责人电话
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.updateCompInfo.001", "更新网点信息参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.updateCompInfo.002", "存在重名网点禁止修改"</dd>		 
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateCompInfo(Long loginCompId, String compName, String compTelephone, Long addressId, String address, Double longitude, Double latitude,
			String responsibleTelephone) {
		try {
			this.compInfoService.updateCompInfo(loginCompId, compName, compTelephone, addressId, address, longitude, latitude, responsibleTelephone);
			return jsonSuccess(null);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新网点状态</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:22:15</dd>
	 * @param compId 网点ID
	 * @param compStatus 网点状态 -1创建 0提交待审核 1审核成功 2审核失败
	 * @param auditId 最后一次审核ID
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompInfoServiceImpl.updateCompStatus.001", "审核站点参数异常"</dd>
     * <dd>"openapi.CompInfoServiceImpl.updateCompStatus.002", "审核站点站点信息异常"</dd>		
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCompStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateCompStatus(Long compId, Short compStatus, Long auditId) {
		try {
			this.compInfoService.updateCompStatus(compId, compStatus, auditId);
			return jsonSuccess(null);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据经纬度获取网络下的站点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:37:58</dd>
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param netId 网络ID
	 * @return {"data":{"expCompList":[{"compAddress":"北京海淀区田村路43号","compAddressId":11000206,"compId":14384014995751936,"compMobile":"13520932909","compName":"我爱申通1","compTel":"010-66162328,13520932909","compTelephone":"010-66162328","cooperationLogo":"1","distance":3.603,"latitude":39.93566,"longitude":116.263574,"netName":"申通快递","netTel":"400-889-5543","responsible":"吕帅","responsibleTelephone":"13520932909"}],"netList":[{"id":"1500","name":"申通快递","total":8}]},"success":true}
	 * <dd>expCompList -  站点信息List </dd>
	 * <dd>compAddress -  公司详细地址 </dd>
	 * <dd>compAddressId -  公司地址ID(最后一级) </dd>
	 * <dd>compId -  公司ID </dd>
	 * <dd>compMobile -  公司手机 </dd>
	 * <dd>compName -  公司名称 </dd>
	 * <dd>compTel -  公司座机加手机 </dd>
	 * <dd>compTelephone -  公司座机 </dd>
	 * <dd>cooperationLogo -  认证状态 1:已认证 0:未认证 </dd>
	 * <dd>distance -  距离 </dd>
	 * <dd>latitude -  公司经度 </dd>
	 * <dd>longitude -  公司纬度 </dd>
	 * <dd>netName -  公司所属网络名称 </dd>
	 * <dd>netTel -  公司所属网络电话</dd>
	 * <dd>responsible -  公司负责人姓名 </dd>
	 * <dd>responsibleTelephone -  公司负责人电话 </dd>
	 * <dd>netList -  网络信息List </dd>
	 * <dd>id -  网络ID </dd>
	 * <dd>name -  网络名称 </dd>
	 * <dd>total -  网络总数</dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>CompInfoController.getExpSites.001 - 经度为必填项</dd>
     * <dd>CompInfoController.getExpSites.002 - 纬度为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getExpSites", method = { RequestMethod.POST, RequestMethod.GET })
	public String getExpSites(String longitude, String latitude, Long netId) {
		if(PubMethod.isEmpty(latitude)){
			return paramsFailure("CompInfoController.getExpSites.001","经度为必填项");
		}
		if(PubMethod.isEmpty(longitude)){
			return paramsFailure("CompInfoController.getExpSites.002","纬度为必填项");
		}
		Map map = null;
		try {
			map = this.compInfoService.getExpSites(longitude, latitude,netId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		return jsonSuccess(map);
	}

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据公司ID获取公司派送范围</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:38:24</dd>
	 * @param compId 公司ID
	 * @return {"success":true,"data":["回龙观西大街北店时代广场","朱辛庄319号","北农路华北电力大学","龙禧三街骊龙园","三合庄村马家地","欧德宝汽车城"]}
	 * <dd>data -  派送范围 </dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>CompInfoController.getCompareaList.001 - 公司ID为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getCompareaList", method = { RequestMethod.POST, RequestMethod.GET })
	public String getCompareaList(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("CompInfoController.getCompareaList.001","公司ID为必填项");
		}
		try {
			return jsonSuccess(this.compInfoService.getCompareaList(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据公司ID获取公司不派送范围</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:37:05</dd>
	 * @param compId 公司ID
	 * @return {"success":true,"data":["回龙观西大街北店时代广场","朱辛庄319号","北农路华北电力大学","龙禧三街骊龙园","三合庄村马家地","欧德宝汽车城"]}
	 * <dd>data -  不派送范围 </dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>CompInfoController.getExceedareaList.001 - 公司ID为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getExceedareaList", method = { RequestMethod.POST, RequestMethod.GET })
	public String getExceedareaList(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("CompInfoController.getExceedareaList.001","公司ID为必填项");
		}
		try {
			return jsonSuccess(this.compInfoService.getExceedareaList(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据经纬度和查询方圆5公里站点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:36:53</dd>
	 * @param latitude 经度
	 * @param longitude 纬度
	 * @return {"data":[{"compAddress":"北京市顺义区李遂镇沟北村潮白河宾馆底商8号","compId":1000236919,"compName":"北京顺义区振华公司李遂分部","contactTel":"400-821-6789","distance":50448,"provinceId":11}],"success":true}
	 * <dd>compAddress -  公司详细地址 </dd>
	 * <dd>compId -  公司ID </dd>
	 * <dd>compName -  公司名称 </dd>
	 * <dd>contactTel -  联系电话  </dd>
	 * <dd>distance -  距离 </dd>
	 * <dd>provinceId -  公司省份ID </dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>CompInfoController.getNearSitsList.001 - 经度为必填项</dd>
     * <dd>CompInfoController.getNearSitsList.002 - 纬度为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getNearSitsList", method = { RequestMethod.POST, RequestMethod.GET })
	public String getExceedareaList(String latitude, String longitude) {
		if(PubMethod.isEmpty(latitude)){
			return paramsFailure("CompInfoController.getNearSitsList.001","经度为必填项");
		}
		if(PubMethod.isEmpty(longitude)){
			return paramsFailure("CompInfoController.getNearSitsList.002","纬度为必填项");
		}
		try {
			return jsonSuccess(this.compInfoService.getNearSitsList(latitude, longitude));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询最新加盟站点(根据查询数量)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:36:38</dd>
	 * @param total 查询数量
	 * @return {"data":[{"compId":14452262879109120,"compMobile":"","compName":"布海圆通速递","compTel":"","compTelephone":"","imageUrl":"http://www.okdi.net/nfs_data/comp/1501.png","netId":1501,"netName":"圆通速递","responsible":"","responsibleTelephone":""}],"success":true}
	 * <dd>compId -  公司ID </dd>
	 * <dd>compMobile -  公司手机 </dd>
	 * <dd>compName -  公司名称 </dd>
	 * <dd>compTel -  公司座机加手机 </dd>
	 * <dd>compTelephone -  公司座机 </dd>
	 * <dd>imageUrl -  公司所属网络LOGO </dd>
	 * <dd>netId -  公司所属网络ID </dd>
	 * <dd>netName -  公司所属网络名称 </dd>
	 * <dd>responsible -  公司负责人姓名 </dd>
	 * <dd>responsibleTelephone -  公司负责人电话 </dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>CompInfoController.getNewComp.001 - 查询数量为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getNewComp", method = { RequestMethod.POST, RequestMethod.GET })
	public String getNewComp(Long total) {
		if(PubMethod.isEmpty(total)){
			return paramsFailure("CompInfoController.getNewComp.001","查询数量为必填项");
		}
		try {
			return jsonSuccess(this.compInfoService.getNewCompList(total));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据公司ID获取电子围栏信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:36:29</dd>
	 * @param compId 公司ID
	 * @return {"data":{"list":[{"latitudeStr":"42.354688,34.067737,34.738716,41.806462","longitudeStr":"104.961634,104.078563,122.770217,123.35893"}]},"success":true}
	 * <dd>latitudeStr -  纬度字符串 </dd>
	 * <dd>longitudeStr -  经度字符串 </dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>CompInfoController.getExpCompElectronicLaction.001 - 公司ID为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getExpCompElectronicLaction", method = { RequestMethod.POST, RequestMethod.GET })
	public String getExpCompElectronicLaction(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("CompInfoController.getExpCompElectronicLaction.001","公司ID为必填项");
		}
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<ExpCompElectronicFence> list=this.compInfoService.getExpCompElectronicLaction(compId);
			dataMap.put("list", list);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据公司ID获取公司详细信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:36:10</dd>
	 * @param compId 公司ID
	 * @return {"data":{"compAddress":"北京朝阳区常营乡常营三村","compId":14383352632049664,"compMobile":"13698745236","compName":"常营三村","compTel":"0312-98741412,13698745236","compTelephone":"0312-98741412","imageUrl":"http://www.okdi.net/nfs_data/comp/1001.png","latitude":39.933318,"longitude":116.593001,"netId":1001,"netName":"顺丰速运","provinceId":11,"responsible":"孙一","responsibleTelephone":""},"success":true}
	 * <dd>compAddress -  公司详细地址 </dd>
	 * <dd>compId -  公司ID </dd>
	 * <dd>compMobile -  公司手机 </dd>
	 * <dd>compName -  公司名称 </dd>
	 * <dd>compTel -  公司座机加手机 </dd>
	 * <dd>compTelephone -  公司座机 </dd>
	 * <dd>imageUrl -  公司所属网络 </dd>
	 * <dd>latitude -  公司纬度 </dd>
	 * <dd>longitude -  公司经度 </dd>
	 * <dd>netId -  公司所属网络ID </dd>
	 * <dd>provinceId -  公司省份ID </dd>
	 * <dd>responsible -  公司负责人姓名 </dd>
	 * <dd>responsibleTelephone -  公司负责人电话 </dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>CompInfoController.getSiteDetail.001 - 公司ID为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getSiteDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String getSiteDetail(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("CompInfoController.getSiteDetail.001","公司ID为必填项");
		}
		try {
			return jsonSuccess(this.compInfoService.getSiteDetail(compId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}

	}
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据公司ID获取公司派送范围和不可派送范围</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-18 上午09:47:14</dd>
	 * @param compId (Long) 公司ID
	 * @return {"success":true,"data":{"addressList":["回龙观西大街北店时代广场","朱辛庄319号","北农路华北电力大学","龙禧三街骊龙园","三合庄村马家地","欧德宝汽车城"],"exceedareaList":["回龙观西大街北店时代广场","朱辛庄319号","北农路华北电力大学","龙禧三街骊龙园","三合庄村马家地","欧德宝汽车城"]}}
	 * <dd>addressList -  派送范围 </dd>
     * <dd>exceedareaList -  不可派送范围 </dd>
     * <dt><span class="strong">异常:</span></dt>
     * <dd>CompInfoController.getExpSendRange.001 - 公司ID为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getExpSendRange", method = { RequestMethod.POST, RequestMethod.GET })
	public String getExpSendRange(Long compId) {
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("CompInfoController.getExpSendRange.001","公司ID为必填项");
		}
		Map<String, Object> allMap = new HashMap<String, Object>();
		try {
			//查询派送范围
			List compareaList=this.compInfoService.getCompareaList(compId);
			//查询非派送范围
			List exceedareaList=this.compInfoService.getExceedareaList(compId);
			allMap.put("addressList",compareaList==null?"":compareaList);
			allMap.put("exceedareaList",exceedareaList==null?"":exceedareaList);
			return jsonSuccess(allMap);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 查找所有的快递网络
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllNetInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAllNetInfo() {
		try {
		List<BasNetInfo> basNetInfo = this.ehcacheService.getAll("netCache", BasNetInfo.class);
			return jsonSuccess(basNetInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * (2015-11 新快递)  查找站点信息列表
	 *netId（ 快递网络ID ）
	 *compType(网点类型1：营业分布，2：站点)
	 *compName(网点名称) ,
	 *memberPhone(创建账户) 
	 *createType创建类型：1：新增，2：领用
	 *beginTime开始申请日期 (endTime)开始申请日期,status(0：待审核，1:通过，2：拒绝)
	 * provinceId 省份id
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAllCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAllCompInfo(Integer pageNum,Integer pageSize,String netId,Short compType,String compName,String memberPhone,
			Short createType,String beginTime,String endTime,Short status,String provinceId) {
		try {
		Map<String, Object> map=compInfoService.queryAllCompInfo(pageNum,pageSize,netId, compType, compName,
				memberPhone, createType, beginTime, endTime, status,provinceId);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/expBasCompInfoList", method = { RequestMethod.POST, RequestMethod.GET })
	public String expBasCompInfoList(Integer pageNum,Integer pageSize,String netId,Short compType,String compName,String memberPhone,
			Short createType,String beginTime,String endTime,Short status,String provinceId) {
		try {
		Map<String, Object> map=compInfoService.expBasCompInfoList(pageNum,pageSize,netId, compType, compName,
				memberPhone, createType, beginTime, endTime, status,provinceId);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	
	
	
}
