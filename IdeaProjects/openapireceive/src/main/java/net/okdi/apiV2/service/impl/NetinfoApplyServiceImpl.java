package net.okdi.apiV2.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV2.dao.MemberInfoMapperV2;
import net.okdi.apiV2.dao.NetinfoApplyMapper;
import net.okdi.apiV2.service.NetinfoApplyService;
import net.okdi.core.util.service.ConstPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @Method
 * @Description 新建申请快递网络
 * @return
 * @author AiJun.Han
 * @data 2015-12-4 下午5:44:10
 */
@Service
public class NetinfoApplyServiceImpl implements NetinfoApplyService{
	@Autowired
	private MemberInfoMapperV2 memberInfoMapperV2;
	@Autowired
	private NetinfoApplyMapper netinfoApplyMapper;
	@Autowired
	private ConstPool constPool;

	
	/**
	 * 添加快递网络申请
	 */
	@Override
	public int insertNetInfoApply(String memberId,String netName,String telphone){
		
		String memberPhone=this.memberInfoMapperV2.findMemberPhoneByMemberId(Long.valueOf(memberId));
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("netName", netName);
		map.put("telphone", telphone);
		map.put("memberPhone", memberPhone);
		map.put("createTime",new Date());
		int rs=this.netinfoApplyMapper.insertNetInfoApply(map);
		Long id=this.netinfoApplyMapper.selectMaxId();
		
	
		
		return Integer.parseInt(String.valueOf(id));
	}
	
	/**
	 * 查询快递网络申请列表(运营平台)
	 */
	@Override
	public Map queryNetInfoApply(String memberPhone,String netName,String netStatus,Integer pageNo, Integer pageSize){
		Map<String,Object> mapSql=new HashMap<String,Object>();
		mapSql.put("memberPhone", memberPhone);
		mapSql.put("netName", netName);
		mapSql.put("netStatus", netStatus);
		mapSql.put("pageNo", (pageNo-1)*pageSize);
		mapSql.put("pageSize", pageSize);
		Map<String,Object> mapSqll=new HashMap<String,Object>();
		mapSqll.put("memberPhone", memberPhone);
		mapSqll.put("netName", netName);
		mapSqll.put("netStatus", netStatus);
		List<Map> list=this.netinfoApplyMapper.queryNetinfoApply(mapSql);
		Integer total=this.netinfoApplyMapper.queryNetinfoApplyCount(mapSqll);
//		Long total=Long.valueOf(list.size());
		
		
		List<Map> resList=new ArrayList<Map>();
		Map<String,Object> rs=new HashMap<String,Object>();
		//rs.put("total", total);
		
		
		for(Map maprsMap:list){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id",maprsMap.get("id"));
			map.put("netName", maprsMap.get("netName"));
			map.put("telphone",maprsMap.get("telphone"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String dataString=sdf.format(maprsMap.get("createTime"));
			map.put("createTime",dataString);
			map.put("memberPhone",maprsMap.get("memberPhone"));
			map.put("netStatus",maprsMap.get("netStatus"));
					
			
			map.put("picUrl",constPool.getNetPicUrl() +maprsMap.get("id")+ ".png");
			resList.add(map);
		}
		if(total%pageSize!=0){
			rs.put("pageCount", (total/pageSize)+1);
		}else{
			rs.put("pageCount", total/pageSize);
		}
		rs.put("total", total);
		rs.put("resultList", resList);
		return rs;
	}
	/**
	 * 处理   快递申请网络  (运营平台)
	 */
	@Override
	public int dealWith(Long id,String note,String operatorName){
		Map<String,Object> mapSql=new HashMap<String,Object>();
		mapSql.put("id",id);
		mapSql.put("note",note);
		mapSql.put("operatorName",operatorName);
		return 	this.netinfoApplyMapper.dealWithById(mapSql);
		
	}
	/**
	 * 查询快递网络申请详情(运营平台)
	 */
	@Override
	public Map queryDeal(Long id){
		Map<String,Object> mapRes=new HashMap<String,Object>();
		Map map= this.netinfoApplyMapper.queryDeal(id);
		mapRes.put("note", map.get("note"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String dataString=sdf.format(map.get("updateTime"));
		mapRes.put("updateTime", dataString);
		mapRes.put("operatorName",map.get("operatorName"));
		return mapRes;
	}
}
