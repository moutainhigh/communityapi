package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.SmsTempleAudit;

public interface SmsTempleAuditMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SmsTempleAudit record);

    int insertSelective(SmsTempleAudit record);

    List<SmsTempleAudit> findSmsTemple(Long memberId);

    SmsTempleAudit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsTempleAudit record);

    int updateByPrimaryKey(SmsTempleAudit record);
    int editSmsTemple(Map<String,Object> map);

	void auditSmsTemple(Map<String, Object> map);

	List<SmsTempleAudit> findSmsTemplePT(Map<String, Object> map);
}