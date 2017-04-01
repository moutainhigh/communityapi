package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.ExpAreaElectronicFenceMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.ExpAreaElectronicFence;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.service.AreaElectronicFenceService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;
/**
 * 
 * @Description: 片区
 * @author 翟士贺
 * @date 2014-10-18下午3:13:31
 */
@Service
public class AreaElectronicFenceServiceImpl extends BaseServiceImpl<ExpAreaElectronicFence> implements AreaElectronicFenceService {
	@Autowired
	private ExpAreaElectronicFenceMapper areaCompElectronicFenceMapper;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private BasCompInfoMapper compInfoMapper;
	@Override
	public BaseDao getBaseDao() {
		return areaCompElectronicFenceMapper;
	}
	/**
	 * 
	 * @Method: queryAreaFence 
	 * @Description: 查询网点片区围栏
	 * @param compId 网点ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @return List<Map<String,Object>>
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.AreaElectronicFenceService#queryAreaFence(java.lang.Long, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public List queryAreaFence(Long compId,String compTypeNum){
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("compId", compId);
		//paras.put("compTypeNum", compTypeNum);
		List<Object> list = ehcacheService.get("areaFenceCache", compId.toString(), ArrayList.class);
		List<Map> memberFenceList=new ArrayList<Map>();//用于存放站点所有片区
		if(PubMethod.isEmpty(list)){
			List<Map<String,Object>> expMemberElectronicFenceList = this.areaCompElectronicFenceMapper.queryMemberAreaFence(paras);
			if("1006".equals(compTypeNum)){
				List<Map<String,Object>> expBrachElectronicFenceList = this.areaCompElectronicFenceMapper.queryBranchAreaFence(paras);
				if(!PubMethod.isEmpty(expBrachElectronicFenceList)){
					expMemberElectronicFenceList.addAll(this.areaCompElectronicFenceMapper.queryBranchAreaFence(paras));
				}
			}
			for(Map<String,Object> memberFenceMap:expMemberElectronicFenceList){
				String[] longitudeArr=memberFenceMap.get("longitude_str").toString().split(",");
				String[] latitudeArr=memberFenceMap.get("latitude_str").toString().split(",");
				memberFenceMap.remove("longitude_str");
				memberFenceMap.remove("latitude_str");
				List<Map<String,Object>> pointList=new ArrayList<Map<String,Object>>();//用于存放一个围栏所有点
				for(int i=0;i<longitudeArr.length;i++){
					Map<String,Object> pointMap=new HashMap<String,Object>();
					pointMap.put("longitude", longitudeArr[i]);
					pointMap.put("latitude", latitudeArr[i]);
					pointList.add(pointMap);
				}
				memberFenceMap.put("pointList", pointList);
				Object memberId = memberFenceMap.get("memberId");
				if("0".equals(memberFenceMap.get("typeNum"))){
					if(PubMethod.isEmpty(memberId)){
						memberFenceMap.put("color", "#808080");
					}else{
						MemberInfo memberInfo = memberInfoService.getMemberInfoById(Long.parseLong(memberId.toString()));
						memberFenceMap.put("memberName", memberInfo.getMemberName());
						memberFenceMap.put("color", memberInfo.getAreaColor());
					}
				}else{
					BasCompInfo compInfo = ehcacheService.get("compCache", String.valueOf(memberId), BasCompInfo.class);
					if (PubMethod.isEmpty(compInfo)) {
						compInfo = this.compInfoMapper.findById(Long.parseLong(memberId.toString()));
						ehcacheService.put("compCache", String.valueOf(memberId), ehcacheService);
					}
					memberFenceMap.put("memberName", compInfo.getCompName());
				}
				memberFenceList.add(memberFenceMap);
			}
			ehcacheService.put("areaFenceCache", compId.toString(), memberFenceList);
		}else{
			for(Object obj:list){
				Map map = JSON.parseObject(String.valueOf(obj), Map.class);
				Object memberId = map.get("memberId");
				if("0".equals(map.get("typeNum"))){
					if(PubMethod.isEmpty(memberId)){
						map.put("color", "#808080");
					}else{
						MemberInfo memberInfo = memberInfoService.getMemberInfoById(Long.parseLong(memberId.toString()));
						map.put("memberName", memberInfo.getMemberName());
						map.put("color", memberInfo.getAreaColor());
					}
				}else{
					BasCompInfo compInfo = ehcacheService.get("compCache", String.valueOf(memberId), BasCompInfo.class);
					if (PubMethod.isEmpty(compInfo)) {
						compInfo = this.compInfoMapper.findById(Long.parseLong(memberId.toString()));
						ehcacheService.put("compCache", String.valueOf(memberId), ehcacheService);
					}
					map.put("memberName", compInfo.getCompName());
				}
				memberFenceList.add(map);
			}
			return memberFenceList;
		}
		return memberFenceList;
	}
	/**
	 * 
	 * @Method: addAreaFence 
	 * @Description: TODO
	 * @param areaFenceId 片区围栏ID
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitudeStr 经度串
	 * @param latitudeStr 纬度串
	 * @return ExpAreaElectronicFence
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.AreaElectronicFenceService#addAreaFence(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public ExpAreaElectronicFence addAreaFence(Long areaFenceId,Long netId,Long compId,String longitudeStr,String latitudeStr)
			throws ServiceException{
		if(!PubMethod.isEmpty(longitudeStr)&& !PubMethod.isEmpty(latitudeStr)){
			String[] longitudeArr=longitudeStr.split(",");
			String[] latitudeArr=latitudeStr.split(",");
			if(longitudeArr.length<3 || longitudeArr.length!=latitudeArr.length){//如果每个围栏所包含的坐标点经纬度个数不同，抛出异常
				throw new ServiceException("openapi.AreaElectronicFenceServiceImpl.addAreaFence.001",
						"添加片区电子围栏数据参数异常，围栏坐标点经纬度对应错误");
			}
			BigDecimal[] longitudeNumArr = new BigDecimal[longitudeArr.length];
			BigDecimal[] latitudeNumArr = new BigDecimal[latitudeArr.length];
			for(int j=0;j<longitudeArr.length;j++){//循环判断是否为double数据
				try{
					longitudeNumArr[j] = BigDecimal.valueOf(Double.parseDouble(longitudeArr[j]));
					latitudeNumArr[j] = BigDecimal.valueOf(Double.parseDouble(latitudeArr[j]));
				}catch(Exception e){
					throw new ServiceException("openapi.AreaElectronicFenceServiceImpl.addAreaFence.002",
							"添加片区电子围栏数据参数异常，围栏坐标点数据格式错误");
				}
			}
			Arrays.sort(longitudeNumArr);
			Arrays.sort(latitudeNumArr);
			BigDecimal minLongitude=longitudeNumArr[0];
			BigDecimal maxLongitude=longitudeNumArr[longitudeNumArr.length-1];
			BigDecimal minLatitude=latitudeNumArr[0];
			BigDecimal maxLatitude=latitudeNumArr[latitudeNumArr.length-1];
//			BigDecimal minLongitude=BigDecimal.valueOf(Double.parseDouble(longitudeArr[0]));
//			BigDecimal maxLongitude=BigDecimal.valueOf(Double.parseDouble(longitudeArr[longitudeArr.length-1]));
//			BigDecimal minLatitude=BigDecimal.valueOf(Double.parseDouble(latitudeArr[0]));
//			BigDecimal maxLatitude=BigDecimal.valueOf(Double.parseDouble(latitudeArr[latitudeArr.length-1]));
			ExpAreaElectronicFence expAreaElectronicFence=new ExpAreaElectronicFence();
//			if(!PubMethod.isEmpty(areaFenceId)){
//				expAreaElectronicFence=this.areaCompElectronicFenceMapper.findById(areaFenceId);
//			}else{
//				expAreaElectronicFence.setNetId(netId);
//				expAreaElectronicFence.setCompId(compId);
//			}
			
			expAreaElectronicFence.setId(areaFenceId);
			expAreaElectronicFence.setNetId(netId);
			expAreaElectronicFence.setCompId(compId);
			
			expAreaElectronicFence.setMinLongitude(minLongitude);
			expAreaElectronicFence.setMinLatitude(minLatitude);
			expAreaElectronicFence.setMaxLongitude(maxLongitude);
			expAreaElectronicFence.setMaxLatitude(maxLatitude);
			expAreaElectronicFence.setLongitudeStr(longitudeStr);
			expAreaElectronicFence.setLatitudeStr(latitudeStr);
			if(PubMethod.isEmpty(expAreaElectronicFence.getId())){
				expAreaElectronicFence.setId(IdWorker.getIdWorker().nextId());
				this.areaCompElectronicFenceMapper.insert(expAreaElectronicFence);
			}else{
				this.areaCompElectronicFenceMapper.update(expAreaElectronicFence);
			}
			String keys = ehcacheService.get("autoSuggestKey", compId+"", String.class);
			removeAll("autoSuggest",keys);
			ehcacheService.remove("areaFenceCache", compId.toString());
			return expAreaElectronicFence;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @Method: deleteAreaFence 
	 * @Description: 删除片区围栏
	 * @param areaFenceId 片区围栏ID
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.AreaElectronicFenceService#deleteAreaFence(java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public void deleteAreaFence(Long areaFenceId,Long compId)throws ServiceException{
		this.areaCompElectronicFenceMapper.delete(areaFenceId);
		String keys = ehcacheService.get("autoSuggestKey", compId+"", String.class);
		removeAll("autoSuggest",keys);
		ehcacheService.remove("areaFenceCache", compId.toString());
	}
	/**
	 * 
	 * @Method: updateAreaMember 
	 * @Description: 更新片区收派员/营业分部（分配片区）
	 * @param areaFenceId 片区围栏ID
	 * @param memberId 收派员/营业分部ID
	 * @param compTypeNum 类型 1050营业分部 0收派员
	 * @param labelLongitude 标注经度
	 * @param labelLatitude 标注纬度
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.AreaElectronicFenceService#updateAreaMember(java.lang.Long, java.lang.Long, java.lang.String, java.math.BigDecimal, java.math.BigDecimal) 
	 * @since jdk1.6
	 */
	@Override
	public void updateAreaMember(Long areaFenceId,Long compId,Long memberId,String compTypeNum,BigDecimal labelLongitude,
			BigDecimal labelLatitude){
//		ExpAreaElectronicFence expAreaElectronicFence=this.areaCompElectronicFenceMapper.findById(areaFenceId);
//		if("0".equals(compTypeNum)){
//			expAreaElectronicFence.setMemberId(memberId);
//			expAreaElectronicFence.setBranchCompId(null);
//		}else if("1050".equals(compTypeNum)){
//			expAreaElectronicFence.setMemberId(null);
//			expAreaElectronicFence.setBranchCompId(memberId);
//		}else{
//			expAreaElectronicFence.setMemberId(null);
//			expAreaElectronicFence.setBranchCompId(null);
//		}
//		expAreaElectronicFence.setLabelLongitude(labelLongitude);
//		expAreaElectronicFence.setLabelLatitude(labelLatitude);
//		this.areaCompElectronicFenceMapper.update(expAreaElectronicFence);
//		ehcacheService.remove("areaFenceCache", compId.toString());
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("areaFenceId", areaFenceId);
		if("0".equals(compTypeNum)){
			paras.put("memberId", memberId);
			paras.put("branchCompId", null);
		}else if("1050".equals(compTypeNum)){
			paras.put("memberId", null);
			paras.put("branchCompId", memberId);
		}else{
			paras.put("memberId", null);
			paras.put("branchCompId", null);
		}
		paras.put("labelLongitude", labelLongitude);
		paras.put("labelLatitude", labelLatitude);
		this.areaCompElectronicFenceMapper.updateAreaMember(paras);
		String keys = ehcacheService.get("autoSuggestKey", compId+"", String.class);
		removeAll("autoSuggest",keys);
		ehcacheService.remove("areaFenceCache", compId.toString());
	}
	/**
	 * 
	 * @Method: updateAreaBranch 
	 * @Description: 更新片区营业分部（解除关系）
	 * @param compId 站点ID
	 * @param branchCompId 营业分部ID
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.AreaElectronicFenceService#updateAreaBranch(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public void updateAreaBranch(Long compId,Long branchCompId)throws ServiceException{
		if(PubMethod.isEmpty(compId)||PubMethod.isEmpty(branchCompId)){
			throw new ServiceException("openapi.AreaElectronicFenceServiceImpl.updateFenceBranch.001"
					,"更新片区收派员参数异常，参数不能为空");
		}
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("compId", compId);
		paras.put("branchCompId", branchCompId);
		this.areaCompElectronicFenceMapper.updateAreaBranch(paras);
		String keys = ehcacheService.get("autoSuggestKey", compId+"", String.class);
		removeAll("autoSuggest",keys);
		ehcacheService.remove("areaFenceCache", compId.toString());
	}
	/**
	 * 
	 * 功能描述: 更新片区收派员
	 * 创建人:  翟士贺
	 * 创建时间：Jul 31, 2014 1:41:23 PM
	 * 修改人：翟士贺
	 * 修改时间：Jul 31, 2014 1:41:23 PM
	 * 修改备注：
	 * @param compId站点id
	 * @param memberId 收派员id
	 * @throws ServiceException
	 *
	 */
	@Override
	public void removeAreaMember(Long compId,Long memberId){
		if(PubMethod.isEmpty(compId)||PubMethod.isEmpty(memberId)){
			throw new ServiceException("openapi.AreaElectronicFenceServiceImpl.updateAreaMember.001"
					,"更新片区收派员参数异常，参数不能为空");
		}
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("compId", compId);
		paras.put("memberId", memberId);
		this.areaCompElectronicFenceMapper.removeAreaMember(paras);
		String keys = ehcacheService.get("autoSuggestKey", compId+"", String.class);
		removeAll("autoSuggest",keys);
		ehcacheService.remove("areaFenceCache", compId.toString());
	}
	private void removeAll(String cacheName,String keys){
		if(keys!=null){
		String [] allKey = keys.split(",");
	    for (int i = 0;i<allKey.length;i++){
	    	ehcacheService.remove(cacheName, allKey[i]);
	    }
		}
	}
	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
}
