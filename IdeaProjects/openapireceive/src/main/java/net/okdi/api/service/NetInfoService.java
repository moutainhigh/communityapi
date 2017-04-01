package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.vo.VO_BasNetInfo;
import net.okdi.api.vo.VO_ExpressPrice;
import net.okdi.core.base.BaseService;

/**
 * 
 * @ClassName NetInfoService
 * @Description 网络信息
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
public interface NetInfoService extends BaseService<BasNetInfo> {
	/**
	 * 
	 * @Method: getNetFirstLetter 
	 * @Description: 首字母分组查询所有快递网络
	 * @return Map<String,List<VO_BasNetInfo>>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:37:19
	 * @since jdk1.6
	 */
	public Map<String, List<VO_BasNetInfo>> getNetFirstLetter();

	/**
	 * @Description: 根据网络ID和省份ID获取网络报价
	 * @author feng.wang
	 * @param netId
	 *            网络ID
	 * @param provinceId
	 *            收件地址省份ID
	 * @return
	 */
	public List<VO_ExpressPrice> getNetQuote(Long netId, Long provinceId);
	/**
	 * 
	 * @Description: 查询网络信息(所有)
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public List<BasNetInfo> queryNetInfoAll();
	/**
	 * 
	 * @Description: 修改网络信息
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public int updateNetInfo(Map<String,Object> map);

	/**
	 * @Method 增加bas_netinfo 表
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-13 下午3:29:32
	 * @param netName
	 * @param netType
	 * @param description
	 * @param boundCompId
	 * @param netNum
	 * @param deleteMark
	 * @param netStatus
	 * @param netRegistWay
	 * @param firstLetter
	 * @param verifyCodeFlag
	 * @param url
	 * @param telephone
	 * @param modifiedTime
	 * @param code
	 * @return
	 */
	public  BasNetInfo insertBasNetInfo(String netName,Short netType,String description,String netNum,String url,String telephone,String code,String verifyCodeFlag);
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-13 下午5:14:43
	 * @param netId 网络id
	 * @param status 传过来的状态
	 * @return
	 */
	public int makeBaseNetInfo(long netId,Short deleteMark);
	/**
	 * 
	 * @Method 判断网络名称是否存在
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-14 上午9:23:00
	 * @param netName
	 * @return
	 */
	public boolean ifHasNetInfo(String netName);
	/**
	 * 
	 * @Description: 查询网络信息详情
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public BasNetInfo queryNetInfoById(Long netId);
	

	/**
	 * 
	 * @Description: 更新快递网络的合作伙伴
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	public Integer updatePartners(Long netId,Short isPartners);

}
