package net.okdi.api.service.impl;

import java.util.Date;

import net.okdi.api.dao.MemberInvitationRelationMapper;
import net.okdi.api.entity.MemberInvitationRelation;
import net.okdi.api.service.MemberInvitationService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;


@Service
public class MemberInvitationServiceImpl extends BaseServiceImpl<MemberInvitationRelation> implements MemberInvitationService {
	@Autowired
	private MemberInvitationRelationMapper memberInvitationRelationMapper;
	@Override
	public BaseDao getBaseDao() {
		// TODO Auto-generated method stub
		return memberInvitationRelationMapper;
	}
	
	@Override
	public void createRelation(Long memberId,String phone,String shopId){
		if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.MemberInvitationServiceImpl.createRelation.001", "创建收派员推荐关系,memberId参数非空异常");
		}else if (PubMethod.isEmpty(phone)) {
			throw new ServiceException("openapi.MemberInvitationServiceImpl.createRelation.002", "创建收派员推荐关系,phone参数非空异常");
		}else if (PubMethod.isEmpty(shopId)) {
			throw new ServiceException("openapi.MemberInvitationServiceImpl.createRelation.003", "创建收派员推荐关系,shopId参数非空异常");
		}
		this.memberInvitationRelationMapper.insert(this.setMemberInvitationRelation(memberId, phone, shopId));
	}
	@Override
	public int updateregisterFlag(Long memberId){
		return this.memberInvitationRelationMapper.updateregisterFlag(memberId);
	}
	@Override
	public MemberInvitationRelation getRelation(Long memberId){
		return this.memberInvitationRelationMapper.getRelation(memberId);
	}
	public MemberInvitationRelation setMemberInvitationRelation(Long memberId,String phone,String shopId){
		MemberInvitationRelation memberInvitationRelation = new MemberInvitationRelation();
		memberInvitationRelation.setRelationId(IdWorker.getIdWorker().nextId());
		memberInvitationRelation.setMemberId(memberId);
		memberInvitationRelation.setMemberPhone(phone);
		memberInvitationRelation.setShopId(shopId);
		memberInvitationRelation.setRegisterFlag((short)0);
		memberInvitationRelation.setCreateTime(new Date());
		return memberInvitationRelation;
	}
	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
