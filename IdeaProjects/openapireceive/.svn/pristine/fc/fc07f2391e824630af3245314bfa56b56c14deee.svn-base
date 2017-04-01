package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.DicAddressaidMapper;
import net.okdi.api.dao.ExpContinuePriceMapper;
import net.okdi.api.dao.ExpFirstPriceMapper;
import net.okdi.api.dao.ExpPriceAddressMapper;
import net.okdi.api.dao.ExpPriceGroupMapper;
import net.okdi.api.dao.NetOpenPriceMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.entity.ExpContinuePrice;
import net.okdi.api.entity.ExpFirstPrice;
import net.okdi.api.entity.ExpPriceAddress;
import net.okdi.api.entity.ExpPriceGroup;
import net.okdi.api.service.ExpCustomerInfoService;
import net.okdi.api.service.PriceGroupService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * 优惠组信息
 * @author shihe.zhai
 * @version V1.0
 */
@Service
public class PriceGroupServiceImpl extends BaseServiceImpl<ExpPriceGroup> implements PriceGroupService {
	@Autowired
	private ExpPriceGroupMapper expPriceGroupMapper;
	@Autowired
	private ExpFirstPriceMapper expFirstPriceMapper;
	@Autowired
	private ExpContinuePriceMapper expContinuePriceMapper;
	@Autowired
	private ExpPriceAddressMapper expPriceAddressMapper;
	@Autowired
	private NetOpenPriceMapper netOpenPriceMapper;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private DicAddressaidMapper dicAddressaidMapper;
	@Autowired
	private BasCompInfoMapper compInfoMapper;
	@Autowired
	private ExpCustomerInfoService expCustomerInfoService;
	@Override
	public BaseDao getBaseDao() {
		return expPriceGroupMapper;
	}
	/**
	 * 新增优惠组
	 * @Method: savePriceGroup 
	 * @param groupName 优惠组名称
	 * @param discountPercentage 报价折扣
	 * @param compId 网点ID
	 * @param netId 网络Id
	 * @return ExpPriceGroup
	 */
	@Override
	public ExpPriceGroup savePriceGroup(String groupName,Double discountPercentage,Long compId,Long netId){
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(netId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceGroup.001", "新增优惠组，获取登录信息异常");
		}else if (PubMethod.isEmpty(groupName)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceGroup.002", "新增优惠组，groupName参数非空异常");
		}else if (null != discountPercentage && (discountPercentage<=0 || discountPercentage>=100)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceGroup.003", "新增优惠组，discountPercentage参数异常");
		}
		if(this.isExistSameNamePriceGroup(null, compId, groupName)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceGroup.004", "新增优惠组，已存在同名优惠组");
		}
		ExpPriceGroup expPriceGroup = this.setExpPriceGroup(groupName.trim(), discountPercentage, compId, netId);
		this.expPriceGroupMapper.insert(expPriceGroup);
		if(!PubMethod.isEmpty(discountPercentage)){//数据为网络公开报价打折
			this.discountNetDefaultPrice(expPriceGroup.getId(), compId, netId, discountPercentage);
		}
		//缓存处理
		List list = ehcacheService.get("compPriceGroupRelation", compId.toString(), ArrayList.class);
		if(!PubMethod.isEmpty(list)){
			list.add(expPriceGroup.getId());
			ehcacheService.put("compPriceGroupRelation", compId.toString(), list);
		}
		ehcacheService.put("priceGroupInfo", expPriceGroup.getId().toString(), expPriceGroup);
		return expPriceGroup;
	}
	/**
	 * 删除优惠组
	 * @Method: deletePriceGroup 
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 */
	@Override
	public void deletePriceGroup(Long groupId,Long compId){
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.deletePriceGroup.001", "删除优惠组，获取登录信息异常");
		}else if (PubMethod.isEmpty(groupId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.deletePriceGroup.002", "删除优惠组，groupId参数非空异常");
		}
		if(groupId.equals(this.getDefaultPriceGroup().getId())){
			throw new ServiceException("openapi.PriceGroupServiceImpl.deletePriceGroup.003", "删除优惠组，默认优惠组不能删除");
		}
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("groupId", groupId);
		paras.put("compId", compId);
		this.deletePriceByGroupId(groupId, compId); //删除组下价格信息
		this.expPriceGroupMapper.deletePriceGroup(paras); // 删除优惠组
		this.expCustomerInfoService.clearDiscountGroupIdByCompId(compId, groupId);
		//缓存处理
		ehcacheService.remove("priceGroupPriceInfo", compId+"-"+groupId);
		ehcacheService.remove("priceGroupInfo", groupId.toString());
		ehcacheService.remove("compPriceGroupRelation", compId.toString());
	}
	/**
	 * 更新优惠组信息
	 * @Method: updatePriceGroup 
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @param groupName 优惠组名称
	 * @param discountPercentage 折扣信息
	 */
	@Override
	public void updatePriceGroup(Long groupId,Long compId,String groupName,Double discountPercentage){
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.updatePriceGroup.001", "更新优惠组，获取登录信息异常");
		}else if (PubMethod.isEmpty(groupId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.updatePriceGroup.002", "更新优惠组，groupId参数非空异常");
		}else if (PubMethod.isEmpty(groupName)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.updatePriceGroup.003", "更新优惠组，groupName参数非空异常");
		}else if (null != discountPercentage && (discountPercentage<=0 || discountPercentage>=100)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.updatePriceGroup.004", "更新优惠组，discountPercentage参数异常");
		}
		if(this.isExistSameNamePriceGroup(groupId, compId, groupName)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.updatePriceGroup.004", "更新优惠组，已存在同名优惠组");
		}
		if(groupId.equals(this.getDefaultPriceGroup().getId())){
			throw new ServiceException("openapi.PriceGroupServiceImpl.updatePriceGroup.005", "更新优惠组，默认优惠组不能修改");
		}
		ExpPriceGroup expPriceGroup = this.getPriceGroupById(groupId);
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("groupId", groupId);
		paras.put("compId", compId);
		paras.put("groupName", groupName.trim());
		paras.put("discountPercentage", discountPercentage);
		this.expPriceGroupMapper.updatePriceGroup(paras);
		if(!PubMethod.isEmpty(discountPercentage) && !discountPercentage.equals(PubMethod.isEmpty(expPriceGroup.getDiscountPercentage()) ? null :expPriceGroup.getDiscountPercentage().doubleValue())){
			this.deletePriceByGroupId(groupId,compId);
			this.discountNetDefaultPrice(groupId, compId, expPriceGroup.getNetId(), discountPercentage);
		}
		/*else if(PubMethod.isEmpty(discountPercentage) && !PubMethod.isEmpty(expPriceGroup.getDiscountPercentage())){
			this.deletePriceByGroupId(groupId,compId);
		}*/
		/*else if(PubMethod.isEmpty(discountPercentage) && !PubMethod.isEmpty(expPriceGroup.getDiscountPercentage())){
			paras = new HashMap<String, Object>();
			paras.put("groupId",groupId);
			paras.put("compId", compId);
			this.expPriceGroupMapper.updateDiscountPercentage(paras);
		}*/
		//缓存处理
		expPriceGroup.setDiscountPercentage(PubMethod.isEmpty(discountPercentage) ? null : BigDecimal.valueOf(discountPercentage));
		expPriceGroup.setGroupName(groupName.trim());
		ehcacheService.put("priceGroupInfo", groupId.toString(),expPriceGroup);
	}
	/**
	 * 查询网点优惠组列表
	 * @Method: getPriceGroupList 
	 * @param compId 网点Id
	 * @return List<Map<String,Object>>
	 */
	@Override
	public List<Map<String,Object>> getPriceGroupList(Long compId){
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.getPriceGroupList.001", "查询网点优惠组列表，获取登录信息异常");
		}
		List<Map<String,Object>> groupList = new ArrayList<Map<String,Object>>();
		List list = ehcacheService.get("compPriceGroupRelation", compId.toString(), ArrayList.class);
		if(!PubMethod.isEmpty(list)){
			for(Object obj:list){
				ExpPriceGroup expPriceGroup = this.getPriceGroupById(Long.parseLong(obj.toString()));
				Map<String,Object> group =new HashMap<String,Object>();
				group.put("id", expPriceGroup.getId());
				group.put("group_name", expPriceGroup.getGroupName());
				groupList.add(group);
			}
		}else{
			groupList = this.expPriceGroupMapper.getPriceGroupList(compId);
			List groupIdList = new ArrayList();
			for(Map<String,Object> groupInfo:groupList){
				groupIdList.add(groupInfo.get("id"));
			}
			ehcacheService.put("compPriceGroupRelation", compId.toString(), groupIdList);
		}
		return groupList;
	}
	/**
	 * 根据ID获取优惠组详细信息
	 * @Method: getPriceGroupById 
	 * @param groupId 优惠组ID
	 * @return ExpPriceGroup
	 */
	@Override
	public ExpPriceGroup getPriceGroupById(Long groupId){
		if (PubMethod.isEmpty(groupId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.getPriceGroupById.001", "获取优惠组，groupId参数非空异常");
		}
		ExpPriceGroup expPriceGroup = ehcacheService.get("priceGroupInfo", groupId.toString(), ExpPriceGroup.class);
		if(PubMethod.isEmpty(expPriceGroup)){
			expPriceGroup = this.expPriceGroupMapper.findById(groupId);
			ehcacheService.put("priceGroupInfo", groupId.toString(), expPriceGroup);
		}
		if(PubMethod.isEmpty(expPriceGroup) || PubMethod.isEmpty(expPriceGroup.getId())){
			throw new ServiceException("openapi.PriceGroupServiceImpl.getPriceGroupById.002", "获取优惠组，优惠组信息获取异常");
		}
		return expPriceGroup;
	}
	/**
	 * 判断是否存在重名优惠组
	 * @Method: isExistSameNamePriceGroup 
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @param groupName 优惠组名称
	 * @return boolean true 存在  false 不存在
	 * @see net.okdi.api.service.PriceGroupService#isExistSameNamePriceGroup(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public boolean isExistSameNamePriceGroup(Long groupId,Long compId,String groupName){
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.isExistSameNamePriceGroup.001", "判断是否存在重名优惠组，获取登录信息异常");
		}else if (PubMethod.isEmpty(groupName)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.isExistSameNamePriceGroup.002", "判断是否存在重名优惠组，groupName参数非空异常");
		}
		List<Map<String,Object>> groupList = this.getPriceGroupList(compId);
		if(!PubMethod.isEmpty(groupList)){
			for(Map<String,Object> groupMap:groupList){
				if(groupMap.get("group_name").equals(groupName.trim()) && !groupMap.get("id").equals(groupId)){
					return true;
				}
			}
		}
		return false;
		/*Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("groupId", groupId);
		paras.put("compId", compId);
		paras.put("groupName", groupName.trim());
		List<Map<String,Object>> priceGroupList = this.expPriceGroupMapper.getSameNamePriceGroup(paras);
		if(PubMethod.isEmpty(priceGroupList)){
			return false;
		}else{
			return true;
		}*/
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>设置优惠组对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午8:11:07</dd>
	 * @param groupName 优惠组名称
	 * @param discountPercentage 折扣信息
	 * @param compId 网点ID
	 * @param netId 网络ID
	 * @return ExpPriceGroup
	 * @since v1.0
	 */
	private ExpPriceGroup setExpPriceGroup(String groupName,Double discountPercentage,Long compId,Long netId){
		ExpPriceGroup expPriceGroup = new ExpPriceGroup();
		expPriceGroup.setId(IdWorker.getIdWorker().nextId());
		expPriceGroup.setGroupName(groupName);
		expPriceGroup.setDiscountPercentage(PubMethod.isEmpty(discountPercentage) ? null : BigDecimal.valueOf(discountPercentage));
		expPriceGroup.setCompId(compId);
		expPriceGroup.setNetId(netId);
		return expPriceGroup;
	}
	/**
	 * 根据优惠组ID获取报价信息
	 * @Method: getPriceListByGroupId 
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @return  List
	 */
	@Override
	public List<Map> getPriceListByGroupId(Long groupId,Long compId){
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.getPriceListByGroupId.001", "查询优惠组价格信息，获取登录信息异常");
		}else if (PubMethod.isEmpty(groupId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.getPriceListByGroupId.002", "查询优惠组价格信息，groupId参数非空异常");
		}
		if(ehcacheService.getByKey("priceGroupPriceInfo", compId+"-"+groupId)){
			return JSON.parseArray(ehcacheService.get("priceGroupPriceInfo", compId+"-"+groupId, String.class),Map.class);
		}else{
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("groupId", groupId);
			paras.put("compId", compId);
			List<Map> priceList = this.expFirstPriceMapper.getPriceListByGroupId(paras);
			ehcacheService.put("priceGroupPriceInfo", compId+"-"+groupId, priceList);
			return priceList;
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>获取公共优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午9:54:49</dd>
	 * @return ExpPriceGroup
	 * @since v1.0
	 */
	public ExpPriceGroup getDefaultPriceGroup(){
		ExpPriceGroup expPriceGroup = ehcacheService.get("compPriceGroupRelation", "default", ExpPriceGroup.class);
		if(PubMethod.isEmpty(expPriceGroup)){
			expPriceGroup = this.expPriceGroupMapper.getDefaultPriceGroup();
		}
		return expPriceGroup;
	}
	/**
	 * 判断是否存在网络公开报价优惠组价格
	 * @Method: isExistNetDefaultPrice 
	 * @param compId 网点ID
	 * @return boolean true存在 false 不存在
	 */
	@Override
	public boolean isExistNetDefaultPrice(Long compId){
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.isExistNetDefaultPrice.001", "判断是否存在网络公开报价优惠组价格，获取登录信息异常");
		}
		if(PubMethod.isEmpty(this.getPriceListByGroupId(this.getDefaultPriceGroup().getId(),compId))){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 查询网络公开报价信息
	 * @Method: getNetOpenPrice 
	 * @param compId 网点Id
	 * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> getNetOpenPrice(Long compId){
		if (PubMethod.isEmpty(compId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.getNetOpenPrice.001", "查询网络公开报价列表，获取登录信息异常");
		}
		BasCompInfo compInfo = ehcacheService.get("compCache", compId.toString(), BasCompInfo.class);
		if (PubMethod.isEmpty(compInfo)) {
			compInfo = this.compInfoMapper.findById(compId);
		}
		if(PubMethod.isEmpty(compInfo)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.getNetOpenPrice.002", "查询网络公开报价列表，获取登录信息异常");
		}
		Long addressId = compInfo.getCompAddressId();
		if(PubMethod.isEmpty(addressId)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.getNetOpenPrice.003", "查询网络公开报价列表，获取网点地址信息异常");
		}
		DicAddressaid address = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
		if(PubMethod.isEmpty(address)){
			address = this.dicAddressaidMapper.findById(addressId);
		}
		if(PubMethod.isEmpty(address)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.getNetOpenPrice.004", "查询网络公开报价列表，获取网点地址信息异常");
		}
		if(ehcacheService.getByKey("netOpenPrice", address.getProvinceId().toString())){
			return ehcacheService.get("netOpenPrice", address.getProvinceId().toString(),Map.class);
		}else{
			Map<String,Object> priceMap = new HashMap<String,Object>();
			priceMap.put("fromAddressId", address.getProvinceId());
			priceMap.put("fromAddressName", address.getProvinceName());
			priceMap.put("priceInfo", this.netOpenPriceMapper.getNetOpenPrice(address.getProvinceId()));
			ehcacheService.put("netOpenPrice", address.getProvinceId().toString(),priceMap);
			return priceMap;
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除优惠组价格信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:11:21</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @since v1.0
	 */
	public void deletePriceByGroupId(Long groupId,Long compId){
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("groupId", groupId);
		paras.put("compId", compId);
		List idList = this.expFirstPriceMapper.getIdByGroupId(paras); //查询后删除，不做缓存处理
		if(!PubMethod.isEmpty(idList)){
			this.expPriceAddressMapper.batchDeleteByFirstPriceId(idList);
			this.expContinuePriceMapper.batchDeleteByFirstPriceId(idList);
			this.expFirstPriceMapper.batchDeleteById(idList);
		}
		ehcacheService.remove("priceGroupPriceInfo", compId+"-"+groupId);//移除缓存
	}
	/**
	 * 设置价格
	 * @Method: savePriceInfo 
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @param netId 网络ID
	 * @param firstFreight 首重价格
	 * @param firstWeight 首重重量
	 * @param continueInfo 续重信息
	 * @param citys 到达城市
	 */
	@Override
	public void savePriceInfo(Long groupId,Long compId,Long netId,BigDecimal firstFreight,BigDecimal firstWeight,String continueInfo,String citys){
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(netId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceInfo.001", "设置价格，获取登录信息异常");
		}else if (PubMethod.isEmpty(groupId)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceInfo.002", "设置价格，groupId参数非空异常");
		}else if (PubMethod.isEmpty(firstFreight)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceInfo.003", "设置价格，firstFreight参数非空异常");
		}else if (PubMethod.isEmpty(firstWeight)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceInfo.004", "设置价格，firstWeight参数非空异常");
		}else if (PubMethod.isEmpty(continueInfo)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceInfo.005", "设置价格，continueInfo参数非空异常");
		}else if (PubMethod.isEmpty(citys)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceInfo.006", "设置价格，citys参数非空异常");
		}
		List priceAddressIds = new ArrayList();
		List firstPriceIds =new ArrayList();
		for(String city:citys.split(",")){
			if(PubMethod.isEmpty(city)){
				continue;
			}
			//查询出设置地址的详细信息  
			Long addressId = Long.parseLong(city);
			DicAddressaid address = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
			if(PubMethod.isEmpty(address)){
				address = this.dicAddressaidMapper.findById(addressId);
			}
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("groupId", groupId);
			paras.put("compId", compId);
			paras.put("addressLevel", address.getAddressLevel());
			paras.put("addressId", addressId);
			//查询地址及子地址报价(查询后删除，不做缓存处理)
			List<Map<String,Object>> idList = this.expPriceAddressMapper.getChildAddress(paras);
			if(PubMethod.isEmpty(idList)){//不存在报价
				continue;
			}
			for(Map<String,Object> temp : idList){
				priceAddressIds.add(temp.get("id"));
				firstPriceIds.add(temp.get("firstPriceId"));
			}
		}
		if(!PubMethod.isEmpty(priceAddressIds)){
			this.expPriceAddressMapper.batchDeleteById(priceAddressIds);
		}
		if(!PubMethod.isEmpty(firstPriceIds)){
			List existFirstPriceId = this.expPriceAddressMapper.getFirstPriceIdByFirstPriceId(firstPriceIds);//仍存在的首重id
			firstPriceIds.removeAll(existFirstPriceId);//判断不再使用的首重id
		}
		//删除首重续重数据（不再使用）
		if(!PubMethod.isEmpty(firstPriceIds)){
			this.expContinuePriceMapper.batchDeleteByFirstPriceId(firstPriceIds);
			this.expFirstPriceMapper.batchDeleteById(firstPriceIds);
		}
		//存数据首重
		ExpFirstPrice expFirstPrice = this.setFirstPrice(groupId, compId, netId, firstFreight, firstWeight);
		this.expFirstPriceMapper.insert(expFirstPrice);
		//保存续重
		List<ExpContinuePrice> expContinuePriceList = new ArrayList<ExpContinuePrice>();
		try{
			List<JSONObject> jsonObjectList = JSON.parseArray(continueInfo, JSONObject.class);
			for(JSONObject jsonObject:jsonObjectList){
				String[] price = jsonObject.getString("PRICE").split("/");
				String[] weight = jsonObject.getString("WEIGHT").split("-");
				BigDecimal continueFreight=BigDecimal.valueOf(Double.parseDouble(price[0]));
				BigDecimal continueWeight=BigDecimal.valueOf(Double.parseDouble(price[1]));
				BigDecimal weightMin=BigDecimal.valueOf(Double.parseDouble(weight[0]));
				BigDecimal weightMax=weight.length == 1 ? null :BigDecimal.valueOf(Double.parseDouble(weight[1]));
				expContinuePriceList.add(this.setContinuePrice(expFirstPrice.getId(), continueFreight, continueWeight, weightMin, weightMax));
			}
		}catch(Exception e){
			throw new ServiceException("openapi.PriceGroupServiceImpl.savePriceInfo.007", "设置价格，续重信息解析异常");
		}
		this.expContinuePriceMapper.batchSaveContinuePrice(expContinuePriceList);
		//保存地址
		List<ExpPriceAddress> expPriceAddressList = new ArrayList<ExpPriceAddress>();
		for(String city:citys.split(",")){
			if(PubMethod.isEmpty(city)){
				continue;
			}
			expPriceAddressList.add(this.setPriceAddress(expFirstPrice.getId(),  Long.parseLong(city)));
		}
		this.expPriceAddressMapper.batchSavePriceAddress(expPriceAddressList);
		
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("groupId",groupId.equals(this.getDefaultPriceGroup().getId()) ? null : groupId);
		paras.put("compId", compId);
		this.expPriceGroupMapper.updateDiscountPercentage(paras);
		if(groupId.equals(this.getDefaultPriceGroup().getId())){
			for(Map<String,Object> group : this.getPriceGroupList(compId)){
				ehcacheService.remove("priceGroupInfo", group.get("id").toString());
			}
		}else{
			ehcacheService.remove("priceGroupInfo", groupId.toString());
		}
		ehcacheService.remove("priceGroupPriceInfo", compId+"-"+groupId);//移除缓存
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>设置报价首重表对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:22:52</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @param netId 网络ID
	 * @param firstFreight 首重价格
	 * @param firstWeight 首重重量
	 * @return ExpFirstPrice
	 * @since v1.0
	 */
	private ExpFirstPrice setFirstPrice(Long groupId,Long compId,Long netId,BigDecimal firstFreight,BigDecimal firstWeight){
		ExpFirstPrice expFirstPrice = new ExpFirstPrice();
		expFirstPrice.setId(IdWorker.getIdWorker().nextId());
		expFirstPrice.setGroupId(groupId);
		expFirstPrice.setFirstFreight(firstFreight);
		expFirstPrice.setFirstWeight(firstWeight);
		expFirstPrice.setCompId(compId);
		expFirstPrice.setNetId(netId);
		return expFirstPrice;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>设置续重价格表对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:23:44</dd>
	 * @param firstPriceId 首重表id
	 * @param continueFreight 续重费用
	 * @param continueWeight 续重价格
	 * @param weightMin 续重范围最小值
	 * @param weightMax 续重范围最大值
	 * @return ExpContinuePrice
	 * @since v1.0
	 */
	private ExpContinuePrice setContinuePrice(Long firstPriceId,BigDecimal continueFreight,BigDecimal continueWeight,BigDecimal weightMin,BigDecimal weightMax){
		ExpContinuePrice expContinuePrice = new ExpContinuePrice();
		expContinuePrice.setId(IdWorker.getIdWorker().nextId());
		expContinuePrice.setFirstPriceId(firstPriceId);
		expContinuePrice.setContinueFreight(continueFreight);
		expContinuePrice.setContinueWeight(continueWeight);
		expContinuePrice.setWeightMin(weightMin);
		expContinuePrice.setWeightMax(weightMax);
		return expContinuePrice;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>设置报价地址表对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:26:49</dd>
	 * @param firstPriceId 首重表id
	 * @param arriveAddressId 到达地址ID
	 * @return ExpPriceAddress
	 * @since v1.0
	 */
	private ExpPriceAddress setPriceAddress(Long firstPriceId,Long arriveAddressId){
		ExpPriceAddress expPriceAddress = new ExpPriceAddress();
		expPriceAddress.setId(IdWorker.getIdWorker().nextId());
		expPriceAddress.setFirstPriceId(firstPriceId);
		expPriceAddress.setArriveAddressId(arriveAddressId);
		return expPriceAddress;
	}
	/**
	 * 导入网络公开报价
	 * @Method: importNetPrice 
	 * @param compId 网点ID
	 * @param netId  网络ID
	 */
	@Override
	public void importNetPrice(Long compId,Long netId){
		if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(netId)) {
			throw new ServiceException("openapi.PriceGroupServiceImpl.exportNetPrice.001", "导入快递公司网络报价，获取登录信息异常");
		}
		if(this.isExistNetDefaultPrice(compId)){
			throw new ServiceException("openapi.PriceGroupServiceImpl.exportNetPrice.002", "导入快递公司网络报价，已存在网络报价不能导入");
		}
		Long groupId = this.getDefaultPriceGroup().getId();
		Map<String,Object> netOpenPriceMap = this.getNetOpenPrice(compId);
		List<Map> priceInfoList = (List<Map>)netOpenPriceMap.get("priceInfo");
		List<ExpFirstPrice> expFirstPriceList = new ArrayList<ExpFirstPrice>();
		List<ExpContinuePrice> expContinuePriceList = new ArrayList<ExpContinuePrice>();
		List<ExpPriceAddress> expPriceAddressList = new ArrayList<ExpPriceAddress>();
		for(Map priceInfo : priceInfoList){
			ExpFirstPrice expFirstPrice = this.setFirstPrice(groupId, compId, netId, BigDecimal.valueOf(Double.parseDouble(priceInfo.get("first_freight").toString())), BigDecimal.valueOf(1.00));
			expFirstPriceList.add(expFirstPrice);
			expContinuePriceList.add(this.setContinuePrice(expFirstPrice.getId(), BigDecimal.valueOf(Double.parseDouble(priceInfo.get("continue_freight").toString())),  BigDecimal.valueOf(1.00),  BigDecimal.valueOf(1.01), null));
			expPriceAddressList.add(this.setPriceAddress(expFirstPrice.getId(),  Long.parseLong(priceInfo.get("to_address_id").toString())));
		}
//		this.deletePriceGroup(groupId, compId);
		this.expFirstPriceMapper.batchSaveFristPrice(expFirstPriceList);
		this.expContinuePriceMapper.batchSaveContinuePrice(expContinuePriceList);
		this.expPriceAddressMapper.batchSavePriceAddress(expPriceAddressList);
		
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("groupId",null);
		paras.put("compId", compId);
		this.expPriceGroupMapper.updateDiscountPercentage(paras); 
		ehcacheService.remove("priceGroupInfo", groupId.toString());
		ehcacheService.remove("priceGroupPriceInfo", compId+"-"+groupId);//移除缓存
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>优惠组打折网络报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午10:37:07</dd>
	 * @param groupId
	 * @param compId
	 * @param netId
	 * @param discountPercentage
	 * @since v1.0
	 */
	public void discountNetDefaultPrice(Long groupId,Long compId,Long netId,Double discountPercentage){
//		Map<String, Object> paras = new HashMap<String, Object>();
//		paras.put("groupId", this.getDefaultPriceGroup().getId());
//		paras.put("compId", compId);
//		List<Map<String,Object>> netDefaultPriceList = this.expFirstPriceMapper.getPriceListByGroupId(paras);
//		if(PubMethod.isEmpty(netDefaultPriceList)){
//			return;
//		}
//		List<ExpFirstPrice> expFirstPriceList = new ArrayList<ExpFirstPrice>();
//		List<ExpContinuePrice> expContinuePriceList = new ArrayList<ExpContinuePrice>();
//		List<ExpPriceAddress> expPriceAddressList = new ArrayList<ExpPriceAddress>();
//		for(Map<String,Object> priceInfo : netDefaultPriceList){
//			ExpFirstPrice expFirstPrice = this.setFirstPrice(groupId, compId, netId,
//					BigDecimal.valueOf(Double.parseDouble(priceInfo.get("firstFreight").toString())).multiply(BigDecimal.valueOf(discountPercentage)).setScale(0,BigDecimal.ROUND_UP), 
//					BigDecimal.valueOf(Double.parseDouble(priceInfo.get("firstWeight").toString())));
//			expFirstPriceList.add(expFirstPrice);
//			List<JSONObject> jsonObjectList = JSON.parseArray(priceInfo.get("continueInfo").toString(), JSONObject.class);
//			for(JSONObject jsonObject:jsonObjectList){
//				String[] price = jsonObject.getString("PRICE").split("/");
//				String[] weight = jsonObject.getString("WEIGHT").split("-");
//				BigDecimal continueFreight=BigDecimal.valueOf(Double.parseDouble(price[0])).multiply(BigDecimal.valueOf(discountPercentage)).setScale(0,BigDecimal.ROUND_UP);
//				BigDecimal continueWeight=BigDecimal.valueOf(Double.parseDouble(price[1]));
//				BigDecimal weightMin=BigDecimal.valueOf(Double.parseDouble(weight[0]));
//				BigDecimal weightMax=weight.length == 1 ? null :BigDecimal.valueOf(Double.parseDouble(weight[1]));
//				expContinuePriceList.add(this.setContinuePrice(expFirstPrice.getId(), continueFreight, continueWeight, weightMin, weightMax));
//			}
//			for(String city:priceInfo.get("city").toString().split(",")){
//				expPriceAddressList.add(this.setPriceAddress(expFirstPrice.getId(),  Long.parseLong(city)));
//			}
//		}
//		this.expFirstPriceMapper.batchSaveFristPrice(expFirstPriceList);
//		this.expContinuePriceMapper.batchSaveContinuePrice(expContinuePriceList);
//		this.expPriceAddressMapper.batchSavePriceAddress(expPriceAddressList);
		List<Map> netDefaultPriceList = this.getPriceListByGroupId(this.getDefaultPriceGroup().getId(),compId);
		if(PubMethod.isEmpty(netDefaultPriceList)){
			return;
		}
		List<ExpFirstPrice> expFirstPriceList = new ArrayList<ExpFirstPrice>();
		List<ExpContinuePrice> expContinuePriceList = new ArrayList<ExpContinuePrice>();
		List<ExpPriceAddress> expPriceAddressList = new ArrayList<ExpPriceAddress>();
		for(Map priceInfo : netDefaultPriceList){
			ExpFirstPrice expFirstPrice = this.setFirstPrice(groupId, compId, netId,
					BigDecimal.valueOf(Double.parseDouble(priceInfo.get("firstFreight").toString())).multiply(BigDecimal.valueOf(discountPercentage).divide(BigDecimal.valueOf(100))).setScale(0,BigDecimal.ROUND_UP), 
					BigDecimal.valueOf(Double.parseDouble(priceInfo.get("firstWeight").toString())));
			expFirstPriceList.add(expFirstPrice);
			List<JSONObject> jsonObjectList = JSON.parseArray(priceInfo.get("continueInfo").toString(), JSONObject.class);
			for(JSONObject jsonObject:jsonObjectList){
				String[] price = jsonObject.getString("PRICE").split("/");
				String[] weight = jsonObject.getString("WEIGHT").split("-");
				BigDecimal continueFreight=BigDecimal.valueOf(Double.parseDouble(price[0])).multiply(BigDecimal.valueOf(discountPercentage).divide(BigDecimal.valueOf(100))).setScale(0,BigDecimal.ROUND_UP);
				BigDecimal continueWeight=BigDecimal.valueOf(Double.parseDouble(price[1]));
				BigDecimal weightMin=BigDecimal.valueOf(Double.parseDouble(weight[0]));
				BigDecimal weightMax=weight.length == 1 ? null :BigDecimal.valueOf(Double.parseDouble(weight[1]));
				expContinuePriceList.add(this.setContinuePrice(expFirstPrice.getId(), continueFreight, continueWeight, weightMin, weightMax));
			}
			for(String city:priceInfo.get("city").toString().split(",")){
				if(PubMethod.isEmpty(city)){
					continue;
				}
				expPriceAddressList.add(this.setPriceAddress(expFirstPrice.getId(),  Long.parseLong(city)));
			}
		}
		this.expFirstPriceMapper.batchSaveFristPrice(expFirstPriceList);
		this.expContinuePriceMapper.batchSaveContinuePrice(expContinuePriceList);
		this.expPriceAddressMapper.batchSavePriceAddress(expPriceAddressList);
		ehcacheService.remove("priceGroupPriceInfo", compId+"-"+groupId);//移除缓存
	}
	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
}
