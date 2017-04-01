package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.ExpCompElectronicFence;
import net.okdi.api.vo.VO_CompInfo;
import net.okdi.core.base.BaseService;
import net.okdi.core.exception.ServiceException;

/**
 * 
 * @ClassName CompInfoService
 * @Description 网点信息
 * @author ShiHe.Zhai
 * @date 2014-11-11
 * @since jdk1.6
 */
public interface CompInfoService extends BaseService<BasCompInfo> {
	/**
	 * 
	 * @Method: getPromptComp 
	 * @Description: 获取网点提示信息
	 * @param netId 网络ID
	 * @param compName 网点名称关键字
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param addressId 地址ID（四级）
	 * @return List<Map<String, Object>>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:52:45
	 * @since jdk1.6
	 */
	public List getPromptComp(Long netId, String compName, String compTypeNum,Long addressId);
	/**
	 * 
	 * @Method: getPromptCompForMobile 
	 * @Description: 获取注册网点列表（手机端）
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param addressId 地址ID
	 * @param roleId 注册角色 -1 后勤 0收派员 1站长
	 * @return List<Map<String, Object>>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:54:21
	 * @since jdk1.6
	 */
	public List getPromptCompForMobile(Long netId, String compTypeNum,Long addressId,Short roleId);
	/**
	 * 
	 * @Method: getSameCompName 
	 * @Description: 获取同网络下重名网点信息(1006已注册的网点信息 1050已注册网点及未领用1003)
	 * @param loginCompId 登录网点ID
	 * @param netId 网络ID
	 * @param compName 网点名称 
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param flag 查询标识 0 不匹配1003未被领用数据 1匹配1003未被领用数据
	 * @return Map<String, Object>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:53:58
	 * @since jdk1.6
	 */
	public Map<String, Object> getSameCompName(Long loginCompId, Long netId, String compName, String compTypeNum, Short flag);
	/**
	 * 
	 * @Method: getSameCompNameForMobile 
	 * @Description: 查询同网络下网点重名信息（手机端 包含未注册信息）
	 * @param netId 网络ID
	 * @param compName 网点名称
	 * @return Map<String, Object>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:58:25
	 * @since jdk1.6
	 */
	public Map<String, Object> getSameCompNameForMobile(Long netId,String compName);
	/**
	 * 
	 * @Method: saveCompInfo 
	 * @Description: 保存未注册网点信息
	 * @param memberId 创建人ID
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部 
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @param compRegistWay 注册方式 6 手机端
	 * @return BasCompInfo
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午10:59:26
	 * @since jdk1.6
	 */
	public BasCompInfo saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Long addressId, String address, Short compRegistWay);
	/**
	 * 
	 * @Method: saveOrUpdateCompBasicInfo 
	 * @Description: 保存/更新网点基础信息
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录公司ID
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param useCompId 领用网点ID
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @param longitude 网点经度
	 * @param latitude 网点纬度
	 * @param compRegistWay 网点注册方式 5站点 6手机端
	 * @return BasCompInfo
	 * @author ShiHe.Zhai
	 * @throws Exception 
	 * @date 2014-11-11 上午11:00:59
	 * @since jdk1.6
	 */
	public BasCompInfo saveOrUpdateCompBasicInfo(Long loginMemberId, Long loginCompId, Long netId, String compTypeNum, Long useCompId, String compName, String compTelephone,
			Long addressId, String address, Double longitude, Double latitude, Short compRegistWay) throws Exception;

	/**
	 * 
	 * @Method: saveOrUpdateCompVerifyInfo 
	 * @Description: 保存/更新网点认证信息
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录公司ID
	 * @param responsible 负责人姓名
	 * @param responsibleTelephone 负责人电话
	 * @param responsibleNum 负责人身份证号
	 * @param businessLicenseImg 营业执照
	 * @param expressLicenseImg 快递许可证
	 * @param frontImg 负责人身份证正面
	 * @param reverseImg 负责人身份证反面
	 * @param holdImg 手持身份证
	 * @param firstSystemImg 系统截图一
	 * @param secondSystemImg 系统截图二
	 * @param thirdSystemImg 系统截图三
	 * @param verifyType 认证方式 3营业及许可证 2证件及截图
	 * @param compStatus 网点状态 -1创建 0提交待审核 1审核通过 2审核失败
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午11:14:23
	 * @since jdk1.6
	 */
	public void saveOrUpdateCompVerifyInfo(Long loginMemberId,Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, String businessLicenseImg,
			String expressLicenseImg, String frontImg, String reverseImg, String holdImg, String firstSystemImg, String secondSystemImg, String thirdSystemImg, Short verifyType,
			Short compStatus);

	/**
	 * 
	 * @Method: queryCompBasicInfo 
	 * @Description: 查询网点基础信息
	 * @param loginCompId 网点ID
	 * @return Map<String, Object>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午11:19:35
	 * @since jdk1.6
	 */
	public Map<String, Object> queryCompBasicInfo(Long loginCompId);

	/**
	 * 
	 * @Method: queryCompVerifyInfo 
	 * @Description: 查询网点认证信息
	 * @param loginCompId 网点ID
	 * @return Map<String, Object>
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午11:19:59
	 * @since jdk1.6
	 */
	public Map<String, Object> queryCompVerifyInfo(Long loginCompId);
//	public Map<String, Object> queryCompVerifyInfo2(Long loginCompId);

	/**
	 * 
	 * @Method: updateCompInfo 
	 * @Description: 更新网络信息（审核通过后）
	 * @param loginCompId 登录网点ID
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 地址ID
	 * @param address 网点详细地址
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param responsibleTelephone 负责人电话
	 * @author ShiHe.Zhai
	 * @throws Exception 
	 * @date 2014-11-11 上午11:22:04
	 * @since jdk1.6
	 */
	public void updateCompInfo(Long loginCompId, String compName, String compTelephone, Long addressId, String address, Double longitude, Double latitude,
			String responsibleTelephone) throws Exception;
	/**
	 * 
	 * @Method: updateCompStatus 
	 * @Description: 更新网点状态
	 * @param compId 网点ID
	 * @param compStatus 网点状态 -1创建 0提交待审核 1审核成功 2审核失败
	 * @param auditId 最后一次审核ID
	 * @author ShiHe.Zhai
	 * @date 2014-11-11 上午11:23:44
	 * @since jdk1.6
	 */
	public void updateCompStatus(Long compId, Short compStatus, Long auditId);

	/**
	 * 
	 * @Description: 根据经纬度获取网络下的站点信息
	 * @author feng.wang
	 * @date 2014-11-1下午2:23:17
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 */
	public Map getExpSites(String longitude, String latitude,Long netId);

	/**
	 * 
	 * @Description: 公司信息获取公司派送范围
	 * @author feng.wang
	 * @date 2014-11-3下午13:23:17
	 * @param compId
	 *            公司ID
	 */
	public List getCompareaList(Long compId);
	
	/**
	 * 
	 * @Description: 根据公司ID获取公司不派送范围
	 * @author feng.wang
	 * @date 2014-11-3下午13:40:17
	 * @param compId
	 *            公司ID
	 */
	public List getExceedareaList(Long compId);
	
	
	/**
	 * @Method: getExceedareaList
	 * @Description: 根据经纬度和查询方圆5公里站点信息
	 * @param latitude
	 *            地址纬度
	 * @param longitude
	 *            地址经度
	 * @return
	 */
	List getNearSitsList(String latitude, String longitude);
	
	 /** 
     * @Method: getNewCompList 
     * @Description: 查询最新加盟站点
     * @param total 总数
     * @return
     * @since jdk1.6
    */
	public List<VO_CompInfo> getNewCompList(Long total);
	
	/** 
     * @Method: getExpCompElectronicLaction 
     * @Description: 根据公司ID获取电子围栏信息
     * @param compId 公司ID
     * @return
     * @since jdk1.6
    */
	public List<ExpCompElectronicFence> getExpCompElectronicLaction(Long compId);
	
	
	/** 
     * @Method: getSiteDetail 
     * @Description: 根据公司ID获取站点详情相关信息
     * @param compId 公司ID
     * @return
     * @since jdk1.6
    */
	public VO_CompInfo getSiteDetail(Long compId);
	
	public Map<String, Object> queryAllCompInfo(Integer pageNum,Integer pageSize,String netId, Short compType,
			String compName, String memberPhone, Short createType,
			String beginTime, String endTime, Short status ,String province);
	
	public Map<String, Object> expBasCompInfoList(Integer pageNum,Integer pageSize,String netId, Short compType,
			String compName, String memberPhone, Short createType,
			String beginTime, String endTime, Short status ,String province);
	
	public Map<String, Object> queryMemberByCompId(Long compId);
}
