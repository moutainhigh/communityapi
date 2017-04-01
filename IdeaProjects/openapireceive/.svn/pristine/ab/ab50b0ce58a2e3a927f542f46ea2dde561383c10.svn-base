package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.ExpCompElectronicFenceMapper;
import net.okdi.api.dao.ExpCompFenceCenterMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.ExpCompElectronicFence;
import net.okdi.api.entity.ExpCompFenceCenter;
import net.okdi.api.service.CompElectronicFenceService;
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
 * @ClassName CompElectronicFenceServiceImpl
 * @Description 取派范围
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
@Service
public class CompElectronicFenceServiceImpl extends BaseServiceImpl<ExpCompElectronicFence> implements CompElectronicFenceService {
	@Autowired
	private ExpCompElectronicFenceMapper expCompElectronicFenceMapper;
	@Autowired
	private ExpCompFenceCenterMapper expCompFenceCenterMapper;
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;
	@Autowired
	private EhcacheService ehcacheService;
	@Override
	public BaseDao getBaseDao() {
		return expCompElectronicFenceMapper;
	}
	/**
	 * 
	 * @Method: queryCompFence 
	 * @Description: 查询网点取派范围
	 * @param compId 网点ID
	 * @return Map<String,Object>
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.CompElectronicFenceService#queryCompFence(java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public Map<String,Object> queryCompFence(Long compId){
		if(PubMethod.isEmpty(compId)){
			throw new ServiceException("openapi.CompElectronicFenceServiceImpl.queryCompFence.001",
					"查询网点取派范围异常，compId参数不能为空");
		}
		List<Object> list = ehcacheService.get("compFenceCache", compId.toString(), ArrayList.class);
		List<Map<String,Object>> compFenceList=new ArrayList<Map<String,Object>>();//用于存放站点所有围栏
		List<ExpCompElectronicFence> expCompElectronicFenceList = new ArrayList<ExpCompElectronicFence>();
		if(PubMethod.isEmpty(list) && !ehcacheService.getByKey("compFenceCache", compId.toString())){
			expCompElectronicFenceList=this.expCompElectronicFenceMapper.queryCompFence(compId);
			ehcacheService.put("compFenceCache", compId.toString(), expCompElectronicFenceList);
		}else if(!PubMethod.isEmpty(list)){
			expCompElectronicFenceList = JSON.parseArray(list.toString(), ExpCompElectronicFence.class);
		}else{
			expCompElectronicFenceList = new ArrayList<ExpCompElectronicFence>();
		}
		for(ExpCompElectronicFence expCompElectronicFence:expCompElectronicFenceList){
			String[] longitudeArr=expCompElectronicFence.getLongitudeStr().split(",");
			String[] latitudeArr=expCompElectronicFence.getLatitudeStr().split(",");
			Map<String,Object> compFenceMap=new HashMap<String,Object>();
			List<Map<String,Object>> pointList=new ArrayList<Map<String,Object>>();//用于存放一个围栏所有点
			for(int i=0;i<longitudeArr.length;i++){
				Map<String,Object> pointMap=new HashMap<String,Object>();
				pointMap.put("longitude", longitudeArr[i]);
				pointMap.put("latitude", latitudeArr[i]);
				pointList.add(pointMap);
			}
			compFenceMap.put("id", expCompElectronicFence.getId());
			compFenceMap.put("pointList", pointList);
			compFenceList.add(compFenceMap);
		}
		Map<String,Object> dataMap=new HashMap<String, Object>();
		dataMap.put("compFenceList", compFenceList);
		dataMap.put("compCoordinateMap", this.getCompCoordinate(compId));
		return dataMap;
	}
	/**
	 * 
	 * @Method: getCompCoordinate 
	 * @Description: 获取网点围栏初始化数据
	 * @param compId 网点ID
	 * @return Map<String,Object>
	 * @throws ServiceException
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午9:05:18
	 * @since jdk1.6
	 */
	public Map<String,Object> getCompCoordinate(Long compId){
		if(PubMethod.isEmpty(compId)){
			throw new ServiceException("openapi.CompElectronicFenceServiceImpl.getCompCoordinate.001",
					"获取网点围栏初始化数据异常，compId参数不能为空");
		}
		Map<String,Object> compCoordinateMap = ehcacheService.get("compCoordinateCache",compId.toString(), Map.class);
		if(PubMethod.isEmpty(compCoordinateMap) && !ehcacheService.getByKey("compCoordinateCache",compId.toString())){
			compCoordinateMap=new HashMap<String,Object>();
			List<ExpCompFenceCenter> expCompFenceCenterList=this.expCompFenceCenterMapper.queryCompMapCenter(compId);
			if(expCompFenceCenterList.size()>0){
				compCoordinateMap.put("id", expCompFenceCenterList.get(0).getId());
				compCoordinateMap.put("longitude", expCompFenceCenterList.get(0).getLongitude());
				compCoordinateMap.put("latitude", expCompFenceCenterList.get(0).getLatitude());
				compCoordinateMap.put("mapLevel", expCompFenceCenterList.get(0).getMapLevel());
				ehcacheService.put("compCoordinateCache", compId.toString(), compCoordinateMap);
				return compCoordinateMap;
			}else{
				ehcacheService.put("compCoordinateCache", compId.toString(), compCoordinateMap);
			}
		}else if(!PubMethod.isEmpty(compCoordinateMap)){
			return compCoordinateMap;
		}
		BasCompInfo compInfo = ehcacheService.get("compCache",compId.toString(), BasCompInfo.class);
		if(PubMethod.isEmpty(compInfo)){
			compInfo=this.basCompInfoMapper.findById(compId);
		}
		compCoordinateMap.put("id", null);
		compCoordinateMap.put("longitude", compInfo.getLongitude());
		compCoordinateMap.put("latitude", compInfo.getLatitude());
		compCoordinateMap.put("mapLevel", 16);//地图放大级别设置为16
		return compCoordinateMap;
	}
	/**
	 * 
	 * @Method: addCompFence 
	 * @Description: 添加/更新网点取派范围
	 * @param compFenceId 取派范围ID
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitudeStr 经度串
	 * @param latitudeStr 纬度串
	 * @return ExpCompElectronicFence
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.CompElectronicFenceService#addCompFence(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public ExpCompElectronicFence addCompFence(Long compFenceId,Long netId,Long compId,String longitudeStr,String latitudeStr) 
			throws ServiceException{
		if(!PubMethod.isEmpty(longitudeStr)&& !PubMethod.isEmpty(latitudeStr)){
			String[] longitudeArr=longitudeStr.split(",");
			String[] latitudeArr=latitudeStr.split(",");
			if(longitudeArr.length<3 || longitudeArr.length!=latitudeArr.length){//如果每个围栏所包含的坐标点经纬度个数不同，抛出异常
				throw new ServiceException("openapi.CompElectronicFenceServiceImpl.addCompFence.001",
						"添加取派范围电子围栏数据参数异常，围栏坐标点经纬度对应错误");
			}
			BigDecimal[] longitudeNumArr = new BigDecimal[longitudeArr.length];
			BigDecimal[] latitudeNumArr = new BigDecimal[latitudeArr.length];
			for(int j=0;j<longitudeArr.length;j++){//循环判断是否为double数据
				try{
					longitudeNumArr[j] = BigDecimal.valueOf(Double.parseDouble(longitudeArr[j]));
					latitudeNumArr[j] = BigDecimal.valueOf(Double.parseDouble(latitudeArr[j]));
				}catch(Exception e){
					throw new ServiceException("openapi.CompElectronicFenceServiceImpl.addCompFence.002",
							"添加取派范围电子围栏数据参数异常，围栏坐标点数据格式错误");
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
			ExpCompElectronicFence expCompElectronicFence=new ExpCompElectronicFence();
//			if(!PubMethod.isEmpty(compFenceId)){
//				expCompElectronicFence=this.expCompElectronicFenceMapper.findById(compFenceId);
//			}else{
//				expCompElectronicFence.setNetId(netId);
//				expCompElectronicFence.setCompId(compId);
//			}
			
			expCompElectronicFence.setId(compFenceId);
			expCompElectronicFence.setNetId(netId);
			expCompElectronicFence.setCompId(compId);
			
			expCompElectronicFence.setMinLongitude(minLongitude);
			expCompElectronicFence.setMinLatitude(minLatitude);
			expCompElectronicFence.setMaxLongitude(maxLongitude);
			expCompElectronicFence.setMaxLatitude(maxLatitude);
			expCompElectronicFence.setLongitudeStr(longitudeStr);
			expCompElectronicFence.setLatitudeStr(latitudeStr);
			if(PubMethod.isEmpty(expCompElectronicFence.getId())){
				expCompElectronicFence.setId(IdWorker.getIdWorker().nextId());
				this.expCompElectronicFenceMapper.insert(expCompElectronicFence);
			}else{
				this.expCompElectronicFenceMapper.update(expCompElectronicFence);
			}
			ehcacheService.remove("compFenceCache", compId.toString());
			ehcacheService.removeAll("nearCompAndPersionCache");
			ehcacheService.removeAll("compElectronicFenceCache");
			return expCompElectronicFence;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @Method: addCompFenceCenter 
	 * @Description: 添加/更新围栏初始化数据
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitude 中心点经度
	 * @param latitude 中心点纬度
	 * @param mapLevel 地图放大级别
	 * @return ExpCompFenceCenter
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.CompElectronicFenceService#addCompFenceCenter(java.lang.Long, java.lang.Long, java.math.BigDecimal, java.math.BigDecimal, java.lang.Byte) 
	 * @since jdk1.6
	 */
	@Override
	public ExpCompFenceCenter addCompFenceCenter(Long netId,Long compId,BigDecimal longitude,BigDecimal latitude,Byte mapLevel) throws ServiceException{
		ExpCompFenceCenter expCompFenceCenter=new ExpCompFenceCenter();
		Map centerMap = ehcacheService.get("compCoordinateCache", compId.toString(), Map.class);
		if(!PubMethod.isEmpty(centerMap)){
			expCompFenceCenter.setId(PubMethod.isEmpty(centerMap.get("id")) ? null : Long.parseLong(centerMap.get("id").toString()));
		}else{
			List<ExpCompFenceCenter> expCompFenceCenterList=this.expCompFenceCenterMapper.queryCompMapCenter(compId);
			if(expCompFenceCenterList.size()>0){
				expCompFenceCenter=expCompFenceCenterList.get(0);
			}
		}
		
//		List<ExpCompFenceCenter> expCompFenceCenterList=this.expCompFenceCenterMapper.queryCompMapCenter(compId);
//		if(expCompFenceCenterList.size()>0){
//			expCompFenceCenter=expCompFenceCenterList.get(0);
//		}else{
//			expCompFenceCenter.setNetId(netId);
//			expCompFenceCenter.setCompId(compId);
//		}
		
		expCompFenceCenter.setNetId(netId);
		expCompFenceCenter.setCompId(compId);
		
		expCompFenceCenter.setLongitude(longitude);
		expCompFenceCenter.setLatitude(latitude);
		expCompFenceCenter.setMapLevel(mapLevel);
		if(PubMethod.isEmpty(expCompFenceCenter.getId())){
			expCompFenceCenter.setId(IdWorker.getIdWorker().nextId());
			this.expCompFenceCenterMapper.insert(expCompFenceCenter);
		}else{
			this.expCompFenceCenterMapper.update(expCompFenceCenter);
		}
		Map<String,Object> compCoordinateMap=new HashMap<String,Object>();
		compCoordinateMap.put("id", expCompFenceCenter.getId());
		compCoordinateMap.put("longitude", longitude);
		compCoordinateMap.put("latitude", latitude);
		compCoordinateMap.put("mapLevel", mapLevel);
		ehcacheService.put("compCoordinateCache", compId.toString(), compCoordinateMap);
		return expCompFenceCenter;
	}
	/**
	 * 
	 * @Method: deleteCompFence 
	 * @Description: 删除取派范围
	 * @param compFenceId 取派范围ID
	 * @param compId 网点ID
	 * @throws ServiceException 
	 * @see net.okdi.express.electronicFence.service.CompElectronicFenceService#deleteCompFence(java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public void deleteCompFence(Long compFenceId,Long compId)throws ServiceException{
//			if(this.memberElectronicFenceDao.queryMemberFence(compId).size()>0){
//				throw new ServiceException("express.CompElectronicFenceServiceImpl.deleteCompFence.002",
//						"取派区域已分配片区，不允许删除");
//			}
		this.expCompElectronicFenceMapper.delete(compFenceId);
		ehcacheService.remove("compFenceCache", compId.toString());
		ehcacheService.removeAll("nearCompAndPersionCache");
		ehcacheService.removeAll("compElectronicFenceCache");
	}
	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
}
