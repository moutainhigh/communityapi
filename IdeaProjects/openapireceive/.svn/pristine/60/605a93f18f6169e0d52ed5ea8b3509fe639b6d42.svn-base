package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.DicAddressaidMapper;
import net.okdi.api.dao.ExpCompAreaAddressMapper;
import net.okdi.api.dao.ExpExceedareaAddressMapper;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.service.AddressService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 
 * @ClassName AddressServiceImpl
 * @Description 地址信息
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
@Service
public class AddressServiceImpl extends BaseServiceImpl<DicAddressaid> implements AddressService {
	@Autowired
	private DicAddressaidMapper dicAddressaidMapper;
	@Autowired
	private ExpCompAreaAddressMapper expCompAreaAddressMapper;
	@Autowired
	private ExpExceedareaAddressMapper expExceedareaAddressMapper;
	@Autowired
	private EhcacheService ehcacheService;
	@Override
	public BaseDao getBaseDao() {
		return dicAddressaidMapper;
	}
	/**
	 * 
	 * @Method: queryRelevantAddressList 
	 * @Description: 联想地址六七级地址
	 * @param townId 乡镇（五级）地址ID
	 * @param keyword 地址关键字
	 * @param count 显示数量
	 * @return Json
	 * @see net.okdi.api.service.AddressService#queryRelevantAddressList(java.lang.Long, java.lang.String, java.lang.Integer) 
	 * @since jdk1.6
	 */
	@Override
	public String queryRelevantAddressList(Long townId,String keyword,Integer count){
		if(PubMethod.isEmpty(townId) || PubMethod.isEmpty(keyword)){
			return "";
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("townId", townId);
		param.put("keyword", keyword);
		if(PubMethod.isEmpty(count) || count<=0){
			count=10;
		}
		param.put("count", count);
		String str = ehcacheService.get("relevantAddressCache", townId+"-"+keyword+"-"+count, String.class);
		if(!PubMethod.isEmpty(str)){
			return str;
		}else if(ehcacheService.getByKey("relevantAddressCache", townId+"-"+keyword+"-"+count)){
			return "";
		}
		List<Map<String,Object>> list = this.dicAddressaidMapper.queryRelevantAddressList(param); // 根据关键字查询5级地址下的6/7级地址
		
		StringBuffer sb = new StringBuffer();
		
		if(PubMethod.isEmpty(list)){
			ehcacheService.put("relevantAddressCache", townId+"-"+keyword+"-"+count,"");
			return "";
		} else {
			sb.append("[");
			for (int i=0;i<list.size();i++) { // 循环省
				
				Map<String,Object> obj = list.get(i);
				String villageName = (PubMethod.isEmpty(obj.get("VILLAGE_NAME")) ? "" : obj.get("VILLAGE_NAME").toString());
				String addName = (PubMethod.isEmpty(obj.get("ADDRESS_NAME")) ? "" : obj.get("ADDRESS_NAME").toString());
				String level = (PubMethod.isEmpty(obj.get("ADDRESS_LEVEL")) ? "" : obj.get("ADDRESS_LEVEL").toString());
				String addId = (PubMethod.isEmpty(obj.get("ADDRESS_ID")) ? "" : obj.get("ADDRESS_ID").toString());
				sb.append("{");
				sb.append("\"addName\":\"");
				if(level.equals("6")){
					sb.append(villageName);
				}else if(level.equals("7")){
					sb.append(villageName);
//					sb.append(" ");
					sb.append(addName);
				}
				sb.append("\",");
				sb.append("\"addId\":\"");
				sb.append(addId);
				sb.append("\"}");
				
				if(i!=list.size()-1){
					sb.append(",");
				}
			}
			sb.append("]");
		}
		ehcacheService.put("relevantAddressCache", townId+"-"+keyword+"-"+count,sb.toString());
		return sb.toString();
	}
	/**
	 * 查询快递网络某级地址下覆盖超区地址信息
	 * @Method: getCoverExceedAddress 
	 * @param netId 快递网络ID，必填
	 * @param addressId 上级地址ID
	 * @return Map<String,List<Map>>
	 * @see net.okdi.api.service.AddressService#getCoverExceedAddress(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Map<String,List<Map>> getCoverExceedAddress(Long netId,Long addressId){
		Map<String,List<Map>> addressMap=new HashMap<String,List<Map>>();
		if(PubMethod.isEmpty(netId)){
			addressMap.put("coverAddress",new ArrayList<Map>());
			addressMap.put("exceedAddress",new ArrayList<Map>());
			return addressMap;
		}
		DicAddressaid dicAddress = null;
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("netId", netId);
		if(!PubMethod.isEmpty(addressId)){//获取上级地址信息
			dicAddress = ehcacheService.get("addressCache",addressId.toString(), DicAddressaid.class);
			if(PubMethod.isEmpty(dicAddress)){
				dicAddress = this.dicAddressaidMapper.findById(addressId);
			}
			paras.put("addressId", addressId);
			paras.put("addressLevel", dicAddress.getAddressLevel());
		}else{
			paras.put("addressId", 10);
			paras.put("addressLevel", 1);
		}
		addressMap.put("coverAddress", this.expCompAreaAddressMapper.getCoverAddress(paras));//覆盖地址
		if(!PubMethod.isEmpty(dicAddress) && dicAddress.getAddressLevel()<=3 ){
		addressMap.put("exceedAddress", this.expExceedareaAddressMapper.getExceedAddress(paras));//超区地址
		}else{
			addressMap.put("exceedAddress",new ArrayList<Map>());
		}
		return addressMap;
	}
	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}
}
