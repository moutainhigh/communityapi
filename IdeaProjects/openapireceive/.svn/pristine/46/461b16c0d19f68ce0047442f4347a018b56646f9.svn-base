package net.okdi.api.service;

import java.util.List;

import net.okdi.api.entity.BilTemplateSet;
import net.okdi.core.base.BaseService;

public interface BilTemplateSetService extends BaseService<BilTemplateSet>{

	public int insert(String templateType, Long compId, Long memberId, Long accountId, String templateContent, String templateSign, Long createUser,
			 Short deleteMark);

	public int update(Long id, String templateType, Long compId, Long memberId, Long accountId, String templateContent, String templateSign,
			Long createUser, Short deleteMark);

	public int delet(Long id, Long memberId);

	public List list(Long compId, Long memberId);

	
}
