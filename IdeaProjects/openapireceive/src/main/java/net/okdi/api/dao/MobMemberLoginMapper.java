package net.okdi.api.dao;

import java.util.List;

import net.okdi.api.entity.MobMemberLogin;
import net.okdi.core.base.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public interface MobMemberLoginMapper extends BaseDao {

	List<MobMemberLogin> findList(MobMemberLogin mobMemberLogin);
	
	/**
	 * 根据channelId 和  deviceToken查询,  or 关系
	 * @Method: findListByChanIdOrChanNO
	 * @param mobMemberLogin
	 * @return
	 */
	List<MobMemberLogin> findListByChanIdOrToken(MobMemberLogin mobMemberLogin);
	
	List<MobMemberLogin> findPushList(MobMemberLogin mobMemberLogin);
	
	List<MobMemberLogin> findPushListBatch(MobMemberLogin mobMemberLogin);
	
	void update(MobMemberLogin mobMemberLogin);
	
	void insert(MobMemberLogin mobMemberLogin);
	
	void delete(MobMemberLogin mobMemberLogin);
	
	void deleteBatch(List<Long> ids);
	
	MobMemberLogin getLastLogin(MobMemberLogin mobMemberLogin);
}
