package net.okdi.api.dao;


import net.okdi.api.entity.BasClientPushJurisdiction;

import org.springframework.stereotype.Repository;

@Repository
public interface BasClientPushJurisdictionMapper {
	BasClientPushJurisdiction query(Long memberId);
	void update(BasClientPushJurisdiction basClientPushJurisdiction);
	void insert(BasClientPushJurisdiction basClientPushJurisdiction);
}