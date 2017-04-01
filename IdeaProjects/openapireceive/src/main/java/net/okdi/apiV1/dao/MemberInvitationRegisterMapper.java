package net.okdi.apiV1.dao;

import java.util.List;

import net.okdi.apiV1.entity.MemberInvitationRegister;
import net.okdi.apiV1.entity.MemberInvitationRegisterExample;

import org.apache.ibatis.annotations.Param;

public interface MemberInvitationRegisterMapper {
    int countByExample(MemberInvitationRegisterExample example);

    int deleteByExample(MemberInvitationRegisterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MemberInvitationRegister record);

    int insertSelective(MemberInvitationRegister record);

    List<MemberInvitationRegister> selectByExample(MemberInvitationRegisterExample example);

    MemberInvitationRegister selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MemberInvitationRegister record, @Param("example") MemberInvitationRegisterExample example);

    int updateByExample(@Param("record") MemberInvitationRegister record, @Param("example") MemberInvitationRegisterExample example);

    int updateByPrimaryKeySelective(MemberInvitationRegister record);

    int updateByPrimaryKey(MemberInvitationRegister record);

    MemberInvitationRegister queryIsRegistered(@Param("memberId")Long memberId);
}