package net.okdi.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.HandleDateMapper;
import net.okdi.api.service.HandleDataService;
import net.okdi.core.util.DesEncrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

@Service
public class HandleDataServiceImpl implements HandleDataService {

	@Autowired
	private HandleDateMapper handleDateMapper;
	
	@Override
	public void handleData() {

		//先查询出表中 relatin表中存在关系 但是audit审核表中没有这条审核关系的数据
		List<Map<String,Object>> list = this.handleDateMapper.queryAllRelationData();
		for(Map<String,Object> map : list){//把每一个没有的 更新回去
			Long id = IdWorker.getIdWorker().nextId();
			Long memberId = Long.parseLong(String.valueOf(map.get("memberId")));
			Long compId = Long.parseLong(String.valueOf(map.get("compId")));
			Long roleId = Long.parseLong(String.valueOf(map.get("roleId")));
			handleDateMapper.insert(id,memberId,compId,roleId,new Date());
		}
	}

	@Override
	public String crackPassword(String password) {
		DesEncrypt desEncrypt = new DesEncrypt();
		return desEncrypt.convertPwd(password, "ABC");
	}

}
