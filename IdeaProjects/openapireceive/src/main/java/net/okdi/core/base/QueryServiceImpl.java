package net.okdi.core.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.core.common.page.Page;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class QueryServiceImpl implements QueryService {
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int findCount(String sqlId, Map<String, Object> paras) throws DataAccessException {
		validateSqlId("findCount", sqlId);
		return sqlSessionTemplate.selectOne(sqlId, paras);
	}

	@Override
	public <T> T findOne(String sqlId, Map<String, Object> paras) throws DataAccessException, NullPointerException {
		validateSqlId("findAEntity", sqlId);
		return sqlSessionTemplate.selectOne(sqlId, paras);
	}
	@Override
	public <T> T findOneByPk(String sqlId, Object pk) throws DataAccessException, NullPointerException {
		validateSqlId("findOneByPk", sqlId);
		return sqlSessionTemplate.selectOne(sqlId, pk);
	}
	@Override
	public <T> List<T> findList(String sqlId, Map<String, Object> paras) throws DataAccessException, NullPointerException {
		validateSqlId("findList", sqlId);
		return sqlSessionTemplate.selectList(sqlId, paras);
	}

	@Override
	public <T> List<T> findPage(String sqlId, Page page, Map<String, Object> paras) throws DataAccessException, NullPointerException {
		validateSqlId("findPage", sqlId);
		if (page == null) {
			page = new Page();
		}
		page.setTotal(this.findCount(sqlId + "_Count", paras));
		if (paras == null) {
			paras = new HashMap<String, Object>();
		}
		paras.put("page", page);
		return sqlSessionTemplate.selectList(sqlId, paras);
	}

	private void validateSqlId(String method, String sqlId) throws NullPointerException {
		if (sqlId == null || "".equals(sqlId))
			throw new NullPointerException(getClass() + "." + method + " 方法中，参数sqlId 不能为空!");
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}



}
