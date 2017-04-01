package net.okdi.api.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.okdi.api.entity.ExpCustomerMemberRelation;

@Repository
public interface ExpCustomerMemberRelationMapper {
    int deleteByPrimaryKey(ExpCustomerMemberRelation key);
    int deleteByMemberId(Long memberId);

    int insert(ExpCustomerMemberRelation record);
    int insertExpList(List<ExpCustomerMemberRelation> record);

    int insertSelective(ExpCustomerMemberRelation record);

    List<ExpCustomerMemberRelation> selectByCustomerId(Long customerId);

    int updateByPrimaryKeySelective(ExpCustomerMemberRelation record);

    int updateByPrimaryKey(ExpCustomerMemberRelation record);
}