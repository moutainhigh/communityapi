package net.okdi.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BilTemplateSetMapper;
import net.okdi.api.entity.BilTemplateSet;
import net.okdi.api.service.BilTemplateSetService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

@Service
public class BilTemplateSetServiceImpl extends BaseServiceImpl<BilTemplateSet>
  implements BilTemplateSetService{

	@Autowired
	private BilTemplateSetMapper  bilTemplateSetMapper;
	@Override
	public BaseDao getBaseDao() {
		return bilTemplateSetMapper;
	}

	@Override
	public String parseResult(String info) {
		return null;
	}

	@Override
	public int insert(String templateType, Long compId, Long memberId, Long accountId, String templateContent, String templateSign, Long createUser,
			 Short deleteMark) {
		BilTemplateSet bl=new BilTemplateSet();
		bl.setId(IdWorker.getIdWorker().nextId());
		bl.setTemplateType(templateType);
		bl.setCompId(compId);
		bl.setMemberId(memberId);
		bl.setAccountId(accountId);
		bl.setTemplateContent(templateContent);
		bl.setTemplateSign(templateSign);
		bl.setCreateUser(createUser);
		bl.setCreateTime(new Date().toString().substring(0, 18));
		bl.setDeleteMark(deleteMark);
		return bilTemplateSetMapper.insert(bl);
	}

	@Override
	public int update(Long id, String templateType, Long compId, Long memberId, Long accountId, String templateContent, String templateSign,
			Long createUser, Short deleteMark) {
		if(PubMethod.isEmpty(id)){
        	throw new ServiceException(0,"template.err.001","id不能为空");
        }
		if(PubMethod.isEmpty(compId)){
        	throw new ServiceException(0,"template.err.003","站点信息不能为空");
        }
		if(PubMethod.isEmpty(memberId)){
        	throw new ServiceException(0,"template.err.004","收派员不能为空");
        }
		BilTemplateSet bl=new BilTemplateSet();
		bl.setId(id);
		bl.setTemplateType(templateType);
		bl.setCompId(compId);
		bl.setMemberId(memberId);
		bl.setAccountId(accountId);
		bl.setTemplateContent(templateContent);
		bl.setTemplateSign(templateSign);
		bl.setCreateUser(createUser);
		bl.setCreateTime(new Date().toString().substring(0, 18));
		bl.setDeleteMark(deleteMark);
		return bilTemplateSetMapper.updateByPrimaryKeySelective(bl);
	}

	@Override
	public int delet(Long id,Long memberId) {
		Map<String, Object> paras=new HashMap<String, Object>();
		paras.put("id", id);
		paras.put("memberId", memberId);
		return bilTemplateSetMapper.deleteByMap(paras);
	}

	@Override
	public List<BilTemplateSet> list(Long compId, Long memberId) {
		Map<String, Object> paras=new HashMap<String, Object>();
		paras.put("compId", compId);
		paras.put("memberId", memberId);
		return bilTemplateSetMapper.list(paras);
	}

}
