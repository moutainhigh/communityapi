package net.okdi.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasEmployeeRelationMapper;
import net.okdi.api.service.CompInfoService;
import net.okdi.api.service.CourierService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.NetDotInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class NetDotInfoServiceImpl implements NetDotInfoService{

	@Autowired
	CompInfoService compInfoService;
	
	@Autowired
	private BasEmployeeRelationMapper basEmployeeRelationMapper;
	
	@Autowired
	private CourierService courierService;
	
	/**
	 * 	保存归属认证信息
	 */
	@Override
	public void saveBelongingAuthentictaInfo(Long id, Long netId,
			String netName, Long compId, String compName, Long memberId,
			String memberName, String memberMobile, Double lng, Double lat,
			String memo) {
		// TODO Auto-generated method stub
		courierService.saveOnLineMember(netId, netName, compId, compName, memberId, memberName, memberMobile, lng, lat, memo);
	}
	
	/**
	 * 	保存/修改 站点认证信息
	 */
	@Override
	public void saveOrUpdateNetDotInfo(Long loginMemberId,Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg, String secondSystemImg, String thirdSystemImg, Short verifyType, Short compStatus) {
		
		compInfoService.saveOrUpdateCompVerifyInfo(loginMemberId, loginCompId, responsible, responsibleTelephone, responsibleNum, businessLicenseImg, expressLicenseImg, frontImg, reverseImg, holdImg, firstSystemImg, secondSystemImg, thirdSystemImg, verifyType, compStatus);
	}
	
	/**
	 * 	查询网点的基本信息
	 */
	@Override
	public Map<String, Object> queryNetDotBasicInfo(Long compId) {
		return this.compInfoService.queryCompBasicInfo(compId);
	}
	
	/**
	 * 	查询网点的认证信息
	 */
	@Override
	public Map<String, Object> queryNetDotVerifyInfo(Long compId) {
		// TODO Auto-generated method stub
		return this.compInfoService.queryCompVerifyInfo(compId);
	}
	
	/**
	 * 	查询站长信息
	 */
	@Override
	public List<Map<String, Object>> queryMemberForComp(Long compId, Long roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compId", compId);
		map.put("roleId", roleId);
		List<Map<String, Object>> list = this.basEmployeeRelationMapper.queryMemberForComp(map);
		return list;
	}

	/**
	 * 	修改网店状态
	 */
	@Override
	public void updateNetDotStatus(Long compId, Short compStatus, Long auditId) {
		compInfoService.updateCompStatus(compId, compStatus, auditId);
	}
}
