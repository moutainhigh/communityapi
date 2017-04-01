package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasExpressPriceMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.service.NetInfoService;
import net.okdi.api.vo.VO_BasNetInfo;
import net.okdi.api.vo.VO_ExpressPrice;
import net.okdi.apiV1.dao.BasCompInfoMapperV1;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.DateUtil;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

/**
 * 
 * @Description: 网络信息
 * @author 翟士贺
 * @date 2014-10-18下午3:13:31
 */
@Service
public class NetInfoServiceImpl extends BaseServiceImpl<BasNetInfo> implements NetInfoService {
	@Autowired
	private BasNetInfoMapper netInfoMapper;

	@Autowired
	private BasCompInfoMapperV1 basCompInfoMapperV1;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	RedisService redisService;
	
	@Autowired
	private BasExpressPriceMapper basExpressPriceMapper;
	
	@Value("${net.info.pic.url}")
	private String pictrueReadUrl; 
	
	@Value("${wuliu.comppicture.filename}")
	private String readFilename; 
	@Value("${wuliu.nets.filename}")
	private String readnetsFilename; 
	@Override
	public BaseDao getBaseDao() {
		return netInfoMapper;
	}

	/**
	 * 
	 * @Method: getNetFirstLetter 
	 * @Description: 首字母分组查询所有快递网络
	 * @return Map<String, List<VO_BasNetInfo>>
	 * @see net.okdi.api.service.NetInfoService#getNetFirstLetter() 
	 * @since jdk1.6
	 */
	@Override
	public Map<String, List<VO_BasNetInfo>> getNetFirstLetter() {
		ehcacheService.removeAll("netCache");
		List<BasNetInfo> netInfoList = ehcacheService.getAll("netCache", BasNetInfo.class);
		 if(PubMethod.isEmpty(netInfoList)){
			 netInfoList = this.netInfoMapper.queryNetInfo();
		 }
		return sortNetFirstLetter(netInfoList);
	}

	/**
	 * 
	 * @Method: sortNetFirstLetter 
	 * @Description: 网络信息按首字母分组排序
	 * @param netInfoList List<BasNetInfo>网络信息
	 * @return Map<String, List<VO_BasNetInfo>>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:41:18
	 * @since jdk1.6
	 */
	public Map<String, List<VO_BasNetInfo>> sortNetFirstLetter(List<BasNetInfo> netInfoList) {
		String[] typeArray = { "HOT", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "OTHER" };
		Map<String, List<VO_BasNetInfo>> allNet = new HashMap<String, List<VO_BasNetInfo>>();
		for (String type : typeArray) {
			allNet.put(type, new ArrayList<VO_BasNetInfo>());
		}
		String tempPath=pictrueReadUrl; //图片路径
		for (BasNetInfo per : netInfoList) {
			String letter = PubMethod.isEmpty(per.getFirstLetter()) ? null : per.getFirstLetter().trim();
			VO_BasNetInfo data = new VO_BasNetInfo();
			data.setNetId(per.getNetId());
			data.setNetNum(per.getNetNum());
			data.setNetName(per.getNetName());
			data.setTelephone(per.getTelephone());
			data.setFirstLetter(per.getFirstLetter());
			data.setCode(per.getCode());
			data.setIsPartners(per.getIsPartners());
			data.setUrl(tempPath+per.getNetId().toString()+".png");
			if (per.getNetName().indexOf("圆通") > -1 || per.getNetName().indexOf("中通") > -1 || per.getNetName().indexOf("申通") > -1 || per.getNetName().indexOf("汇通") > -1
					|| per.getNetName().indexOf("韵达") > -1 || per.getNetName().indexOf("宅急送") > -1 || per.getNetName().indexOf("顺丰") > -1 || per.getNetName().indexOf("EMS") > -1) {
				VO_BasNetInfo hotData = new VO_BasNetInfo();
				hotData.setNetId(per.getNetId());
				hotData.setNetNum((per.getNetNum()));
				hotData.setNetName(per.getNetName());
				hotData.setTelephone(per.getTelephone());
				hotData.setFirstLetter(per.getFirstLetter());
				hotData.setCode(per.getCode());
				hotData.setIsPartners(per.getIsPartners());
				hotData.setUrl(tempPath+per.getNetId().toString()+".png");
				allNet.get("HOT").add(hotData); // 热门
			}
			List<VO_BasNetInfo> list = allNet.get(letter);
			if (list == null) {
				allNet.get("OTHER").add(data);
			} else {
				allNet.get(letter.trim()).add(data);
			}
		}
		for (Map.Entry<String, List<VO_BasNetInfo>> entry : allNet.entrySet()) {
			Collections.sort(entry.getValue());
		}
		return allNet;
	}

	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description: 根据网络ID和省份ID获取网络报价
	 * @author feng.wang
	 * @param netId
	 *            网络ID
	 * @param provinceId
	 *            收件地址省份ID
	 * @return
	 */
	@Override
	public List<VO_ExpressPrice> getNetQuote(Long netId, Long provinceId) {
		List<VO_ExpressPrice> netPriceList=this.ehcacheService.get("netPriceCache", netId+"_"+provinceId,List.class);
		if(netPriceList==null){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("netId", netId);
			params.put("provinceId", provinceId);
			netPriceList=this.basExpressPriceMapper.getNetQuote(params);
			this.ehcacheService.put("netPriceCache", netId+"_"+provinceId, netPriceList);
		}
		return netPriceList;
	}
	/**
	 * 
	 * @Description: 查询网络信息(所有)
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	@Override
	public List<BasNetInfo> queryNetInfoAll(){
		List<BasNetInfo>  listBasInfo = this.netInfoMapper.queryNetInfoAll();
		return listBasInfo;
	}
	/**
	 * 
	 * @Description: 修改网络信息
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	@Override
	public int updateNetInfo(Map<String,Object> map){
				Long netId=Long.valueOf(map.get("netId").toString());
				List<BasNetInfo> list=new ArrayList<BasNetInfo>();
				list=this.netInfoMapper.queryNetInfoById(netId);
				if(list.size()<1){
					throw new ServiceException("openapi.CompInfoServiceImpl.makeBaseNetInfo.001", "系统错误，请联系管理员");
				}
				BasNetInfo basNetInfo=list.get(0);
				if(!PubMethod.isEmpty(map.get("netName"))){
					String name=map.get("netName").toString();
					if(!name.equals(basNetInfo.getNetName())){
					basNetInfo.setNetName(name);
					Integer count=netInfoMapper.queryNetName(map.get("netName").toString());
					if(count>0){
						throw new ServiceException("openapi.CompInfoServiceImpl.insertBasNetInfo.002", "网络名称重复");
					}
					map.put("firstLetter",PiyinUtil.cn2py(map.get("netName").toString()).substring(0,1).toUpperCase());
					}
				}
				if(!PubMethod.isEmpty(map.get("netType"))){
					basNetInfo.setNetType((Short)map.get("netType"));
				}
				if(!PubMethod.isEmpty(map.get("description"))){
					basNetInfo.setDescription(map.get("description").toString());
				}
				if(!PubMethod.isEmpty(map.get("netNum"))){
					basNetInfo.setNetNum(map.get("netNum").toString());
				}
				if(!PubMethod.isEmpty(map.get("url"))){
					basNetInfo.setUrl(map.get("url").toString());
				}
				if(!PubMethod.isEmpty(map.get("telephone"))){
					basNetInfo.setTelephone(map.get("telephone").toString());
				}
				if(!PubMethod.isEmpty(map.get("code"))){
					basNetInfo.setCode(map.get("code").toString());
				}
				if(!PubMethod.isEmpty(map.get("verifyCodeFlag"))){
					basNetInfo.setCode(map.get("verifyCodeFlag").toString());
				}
				basNetInfo.setFirstLetter(PiyinUtil.cn2py(map.get("netName").toString()).substring(0,1).toUpperCase());
				if(basNetInfo.getDeleteMark()==1 && basNetInfo.getNetStatus()==1 && !PubMethod.isEmpty(basNetInfo.getCode())){
					ehcacheService.put("netCache",map.get("netId").toString(), basNetInfo);					
				}
				//手机端网络更新标志
				int update=netInfoMapper.updateNetInfo(map);
				String date = DateUtil.format(new Date(),"yyyyMMddHHmmss");
				redisService.put("myvalid_validNetIsChanged", "validDate", date);
		return update;
	}
	@Override
	public  BasNetInfo insertBasNetInfo(String netName,Short netType,String description,String netNum,String url,String telephone,String code,String verifyCodeFlag){
		if(PubMethod.isEmpty(netName)){
			throw new ServiceException("openapi.CompInfoServiceImpl.insertBasNetInfo.001", "保存网络信息参数异常,netName参数异常");
		}else if(PubMethod.isEmpty(netType)){
			throw new ServiceException("openapi.CompInfoServiceImpl.insertBasNetInfo.002", "保存网络信息参数异常,netType参数异常");
		}else if(PubMethod.isEmpty(code)){
			throw new ServiceException("openapi.CompInfoServiceImpl.insertBasNetInfo.003", "保存网络信息参数异常,code参数异常");
		}else if(PubMethod.isEmpty(url)){
			throw new ServiceException("openapi.CompInfoServiceImpl.insertBasNetInfo.004", "保存网络信息参数异常,url参数异常");
		}else if(PubMethod.isEmpty(telephone)){
			throw new ServiceException("openapi.CompInfoServiceImpl.insertBasNetInfo.005", "保存网络信息参数异常,telephone参数异常");
		}
		Integer count=netInfoMapper.queryNetName(netName);
		if(count>0){
			throw new ServiceException("openapi.CompInfoServiceImpl.insertBasNetInfo.006", "网络名称重复");
		}
		Long netId=IdWorker.getIdWorker().nextId();
		BasNetInfo basNetInfo=new BasNetInfo();
		basNetInfo.setNetId(netId);
		basNetInfo.setNetName(netName);
		basNetInfo.setNetType(netType);
		basNetInfo.setDescription(description);
		basNetInfo.setBoundCompId(netId);
		basNetInfo.setNetNum(netNum);
		basNetInfo.setDeleteMark((short) 1);
		basNetInfo.setNetStatus((short)1);
		basNetInfo.setNetRegistWay((short)1);
		basNetInfo.setFirstLetter(PiyinUtil.cn2py(netName).substring(0,1).toUpperCase());
		basNetInfo.setVerifyCodeFlag(verifyCodeFlag);
		basNetInfo.setUrl(url);
		basNetInfo.setTelephone(telephone);
		basNetInfo.setModifiedTime(new Date());
		basNetInfo.setCode(code);
		this.netInfoMapper.insert(basNetInfo);
		
		/**插入虚拟站点**/
		BasCompInfo basCompInfo = new BasCompInfo();
		basCompInfo.setCompId(IdWorker.getIdWorker().nextId());
		basCompInfo.setCompName("未标记站点");
		basCompInfo.setCompRegistWay((short)-1);//做个标记
		basCompInfo.setBelongToNetId(netId);//快递公司
		basCompInfo.setCompTypeNum("1006");
		basCompInfo.setCompStatus((short)2);
		basCompInfo.setCreateTime(new Date());
		basCompInfo.setBelongToCompId(-1L);
		basCompInfo.setWriteSendStatus((short)-1);
		basCompInfo.setGoodsPaymentStatus((short)-1);
		basCompInfo.setTaoGoodsPaymentStatus((short)-1);
		/**插入虚拟代收点**/
		this.basCompInfoMapperV1.insert(basCompInfo);
		BasCompInfo basCompInfoV2 = new BasCompInfo();
		basCompInfoV2.setCompId(IdWorker.getIdWorker().nextId());
		basCompInfoV2.setCompName("未标记代收站");
		basCompInfoV2.setCompRegistWay((short)-1);//做个标记
		basCompInfoV2.setBelongToNetId(netId);//快递公司
		basCompInfoV2.setCompTypeNum("1040");
		basCompInfoV2.setCompStatus((short)2);
		basCompInfoV2.setCreateTime(new Date());
		basCompInfoV2.setBelongToCompId(-1L);
		basCompInfoV2.setWriteSendStatus((short)-1);
		basCompInfoV2.setGoodsPaymentStatus((short)-1);
		basCompInfoV2.setTaoGoodsPaymentStatus((short)-1);
		this.basCompInfoMapperV1.insert(basCompInfoV2);
		
		if(basNetInfo.getDeleteMark()==1 && basNetInfo.getNetStatus()==1 && !PubMethod.isEmpty(basNetInfo.getCode())){
			ehcacheService.put("netCache", netId.toString(),basNetInfo);					
		}
		//手机端网络更新标志
		String date = DateUtil.format(new Date(),"yyyyMMddHHmmss");
		redisService.put("myvalid_validNetIsChanged", "validDate", date);
		
		return basNetInfo;
	}
	@Override
	public int makeBaseNetInfo(long netId,Short deleteMark){
		
		//传过来的状态为1为停用，0为启用
		Short status;
		
		List<BasNetInfo> list=new ArrayList<BasNetInfo>();
		list=this.netInfoMapper.queryNetInfoById(netId);
		if(list.size()<1){
			throw new ServiceException("openapi.CompInfoServiceImpl.makeBaseNetInfo.001", "系统错误，请联系管理员");
		}
		BasNetInfo basNetInfo=list.get(0);
		if(1==deleteMark){
			status = 0;
			this.netInfoMapper.makeBaseNetInfo(netId,status);
			basNetInfo.setDeleteMark(status);
		}
		if(0==deleteMark){
			status = 1;
			this.netInfoMapper.makeBaseNetInfo(netId,status);
			basNetInfo.setDeleteMark(status);
		}
		if(basNetInfo.getDeleteMark()==1 && basNetInfo.getNetStatus()==1 && !PubMethod.isEmpty(basNetInfo.getCode())){
			ehcacheService.put("netCache",String.valueOf(netId),basNetInfo);					
		}else{
			ehcacheService.remove("netCache",String.valueOf(netId));
		}
		//手机端网络更新标志
		String date = DateUtil.format(new Date(),"yyyyMMddHHmmss");
		redisService.put("myvalid_validNetIsChanged", "validDate", date);
		
		return 1;
	}
	@Override
	public boolean ifHasNetInfo(String netName){
		Long netId=this.netInfoMapper.ifHasNetInfo(netName);
		if(PubMethod.isEmpty(netId)){
			return false;
		}else{
		return true;
		}
	}
	/**
	 * 
	 * @Description: 查询网络信息详情
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public BasNetInfo queryNetInfoById(Long netId){
		List<BasNetInfo> list=new ArrayList<BasNetInfo>();
		list=this.netInfoMapper.queryNetInfoById(netId);
		BasNetInfo basNetInfo= list.get(0);
		return basNetInfo;
	}

	/**
	 * 
	 * @Description: 更新快递网络的合作伙伴
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	@Override
	public Integer updatePartners(Long netId, Short isPartners) {
		List<BasNetInfo> list=new ArrayList<BasNetInfo>();
		list=this.netInfoMapper.queryNetInfoById(netId);
		if(list.size()<1){
			throw new ServiceException("openapi.CompInfoServiceImpl.makeBaseNetInfo.001", "系统错误，请联系管理员");
		}
		BasNetInfo basNetInfo=list.get(0);
		basNetInfo.setIsPartners(isPartners);
		if(basNetInfo.getDeleteMark()==1 && basNetInfo.getNetStatus()==1 && !PubMethod.isEmpty(basNetInfo.getCode())){
			ehcacheService.put("netCache",netId+"", basNetInfo);					
		}
		Integer flag=this.netInfoMapper.updatePartners(netId, isPartners);
		return null;
	}
}
