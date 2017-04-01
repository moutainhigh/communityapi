package net.okdi.apiV2.controller;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.CollectionService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 代收业务
 * 
 * @author xuanhua.hu@amssy.com
 * @version 1.0.0
 * @2015-12-2
 * @return {@link CollectionController.java}
 */
@Controller
@RequestMapping("/collection")
public class CollectionController extends BaseController {

	@Autowired
	private CollectionService collectionService;

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
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		String id = collectionService.insertClerkAudit1(compName, compAddress,
				memberName, idNum, roleId, latitude, longitude, memberId,
				compId, auditId);
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
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		String id = this.collectionService.insertClerkAudit2(compName,
				compAddress, memberName, idNum, roleId, latitude, longitude,
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
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String phone) {

		String id = this.collectionService.insertShopkeeperAudit1(compName,
				compAddress, memberName, idNum, roleId, latitude, longitude,
				memberId, compId, auditId,phone);
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
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {

		Map<String, Object> map = this.collectionService
				.insertShopkeeperAudit2(compName, compAddress, memberName,
						idNum, roleId, latitude, longitude, memberId, compId,
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
	public String findIdentity(String idNum) {
		Map<String, Object> map = this.collectionService.findIdentity(idNum);
		try {
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
