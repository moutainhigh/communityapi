package net.okdi.apiV4.controller;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.CollectionNewService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/collectionNew")
public class CollectionNewController extends BaseController{

	
	
	@Autowired
	private CollectionNewService collectionService;

	/**
	 * 查询个人信息，及其身份认证信息
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberInfo", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getMemberInfo(String memberId) {
		Map<String, Object> map = this.collectionService.getMemberInfo(memberId);
		try {
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 判断是否代收点存在，0：不存在，1存在
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param compName
	 * @param compAddress
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isHasCollection", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String isHasCollection(String compName, String compAddress) {
		try {
			return jsonSuccess(this.collectionService.isHasCollection(compName,
					compAddress));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 店员信息(代收点不存在)
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param compName
	 * @param compAddress
	 * @param memberName
	 * @param idNum
	 * @param roleId
	 * @param latitude
	 * @param longitude
	 * @param memberId
	 * @param compId
	 * @param auditId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertClerkAudit1", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String insertClerkAudit1(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId, String agentType) {
		String id = collectionService.insertClerkAudit1(compName, compAddress,
				memberName, roleId, latitude,
				longitude, memberId, compId, auditId, agentType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", id);
		try {
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * * 店员信息(代收点存在)
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param compName
	 * @param compAddress
	 * @param memberName
	 * @param idNum
	 * @param roleId
	 * @param latitude
	 * @param longitude
	 * @param memberId
	 * @param compId
	 * @param auditId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertClerkAudit2", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String insertClerkAudit2(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		String id = this.collectionService.insertClerkAudit2(compName,
				compAddress, memberName, roleId, latitude, longitude,
				memberId, compId, auditId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", id);
		try {
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 店长信息(店铺不存在)
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param compName
	 * @param compAddress
	 * @param memberName
	 * @param idNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertShopkeeperAudit1", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String insertShopkeeperAudit1(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String phone, String agentType) {

		String id = this.collectionService.insertShopkeeperAudit1(compName,
				compAddress, memberName, roleId, latitude, longitude,
				memberId, compId, auditId,phone, agentType);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", id);
		try {
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 店长信息(店铺存在)
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param compName
	 * @param compAddress
	 * @param memberName
	 * @param idNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertShopkeeperAudit2", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String insertShopkeeperAudit2(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {

		Map<String, Object> map = this.collectionService
				.insertShopkeeperAudit2(compName, compAddress, memberName,
						 roleId, latitude, longitude, memberId, compId,
						auditId);
		try {
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 判断身份证是否已经存在
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Map<String,Object>}
	 * @param idNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findIdentity", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String findIdentity(String idNum,String memberPhone) {
		Map<String, Object> map = this.collectionService.findIdentity(idNum,memberPhone);
		try {
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
