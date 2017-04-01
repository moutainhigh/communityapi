/**  
 * @Project: openapi
 * @Title: BroadcastListServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @author amssy
 * @date 2015-1-13 下午6:54:14
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasCompbusinessMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.dao.ParParceladdressMapper;
import net.okdi.api.dao.ParParcelinfoMapper;
import net.okdi.api.dao.ParTaskDisposalRecordMapper;
import net.okdi.api.dao.ParTaskInfoMapper;
import net.okdi.api.dao.ParTaskProcessMapper;
import net.okdi.api.dao.RobBroadcastInfoMapper;
import net.okdi.api.dao.RobParcelRelationMapper;
import net.okdi.api.dao.RobQuotationInfoMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasCompbusiness;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.MemberInfo;
import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.entity.ParParcelconnection;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.api.entity.ParTaskDisposalRecord;
import net.okdi.api.entity.ParTaskInfo;
import net.okdi.api.entity.ParTaskProcess;
import net.okdi.api.entity.RobBroadcastInfo;
import net.okdi.api.entity.RobQuotationInfo;
import net.okdi.api.service.BroadcastListService;
import net.okdi.api.service.BroadcastService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParLogisticSearchService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.api.service.RobInfoService;
import net.okdi.api.service.SendNoticeService;
import net.okdi.api.service.TakeTaskService;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * @author amssy
 * @version V1.0
 */
@Service
public class BroadcastListServiceImpl implements BroadcastListService {
	
	final Short isNew = 0; //广播报价未读
	final Short isOld = 1; //广播报价已读
	final Short broadcast_status_unresponse = 1; //广播待响应
	final Short broadcast_status_cancel = 2; //广播取消
	final Short broadcast_status_unsend = 3; //广播待交寄
	final Short broadcast_status_finish = 4; //广播完成
	final Short broadcast_status_timeout = 5; //广播超时
	
	final Short success_flag_true = 1; //抢单成功
	final Short success_flag_false = 2; //抢单失败
	//任务来源
	final Byte task_source_ec = 3; //电商管家
	final Byte task_source_app_personal = 4; //好递个人端
	//任务状态
	final Byte task_status_untake = 0; //待处理
	final Byte task_status_distribute = 1; //已分配
	final Byte task_status_finish = 2; //已完成
	final Byte task_status_cancel = 3; //已取消
	final Byte task_status_delete = 10; //任务删除
	//任务类型
	final Byte task_type_take = 0; //取件
	//处理方类型
	final Byte disposal_object_member = 0; //派送员
	//抢单类型
	final Short rob_status_true = 1; //已抢
	final Short rob_status_false = 2; //未抢
	
	final String CHANNEL_NO_PERSONNEL = "02";
	final String EXP_TYPE_RECEIVE = "1";
	
	private static List<String> list = new ArrayList<String>();
	static {
		list.add("北京市");
		list.add("天津市");
		list.add("河北省");
		list.add("山西省");
		list.add("内蒙古自治区");
		list.add("辽宁省");
		list.add("吉林省");
		list.add("黑龙江省");
		list.add("上海市");
		list.add("江苏省");
		list.add("浙江省");
		list.add("安徽省");
		list.add("福建省");
		list.add("江西省");
		list.add("山东省");
		list.add("河南省");
		list.add("湖北省");
		list.add("湖南省");
		list.add("广东省");
		list.add("广西壮族自治区");
		list.add("海南省");
		list.add("重庆市");
		list.add("四川省");
		list.add("贵州省");
		list.add("云南省");
		list.add("西藏自治区");
		list.add("陕西省");
		list.add("甘肃省");
		list.add("青海省");
		list.add("宁夏回族自治区");
		list.add("新疆维吾尔自治区");
		list.add("台湾省");
		list.add("香港特别行政区");
		list.add("澳门特别行政区");
		list.add("海外");
	}
	
	@Autowired
	private EhcacheService ehcacheService; //缓存
	@Autowired
	private RobBroadcastInfoMapper robBroadcastInfoMapper;
	@Autowired
	private RobQuotationInfoMapper robQuotationInfoMapper;
	@Autowired
	private RobInfoService robInfoService;
	@Autowired
	private TakeTaskService takeTaskService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private RobParcelRelationMapper robParcelRelationMapper;
	@Autowired
	private ParParcelinfoMapper parParcelinfoMapper;
	@Autowired
	private ParParceladdressMapper parParceladdressMapper;
	@Autowired
	private BasCompInfoMapper basCompInfoMapper;
	@Autowired
	private ParTaskInfoMapper parTaskInfoMapper;
	@Autowired
	private ParTaskDisposalRecordMapper parTaskDisposalRecordMapper;
	@Autowired
    private BasNetInfoMapper basNetInfoMapper;
	@Autowired
	private BroadcastService broadcastService;
	@Autowired
	private SendNoticeService sendNoticeService;  //消息推送服务
	@Autowired
	private RobBroadcastInfoMapper broadcastMapper;
	@Autowired
	private ParTaskProcessMapper parTaskProcessMapper;
	@Autowired
	private ParLogisticSearchService parLogisticSearchService;
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private BasCompbusinessMapper basCompbusinessMapper;
	@Autowired
	private ParcelInfoService parcelInfoService;
	@Value("${save.courier.head}")
	private String imageUrl;
	
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * @Method: queryBroadcastList 
	 * @param memberId 登录人id
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#queryBroadcastList(java.lang.Long) 
	 */
	@Override
	public List<Map<String,Object>> queryBroadcastList(Long memberId) throws ServiceException {
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.queryBroadcastList.001", "无广播信息，请先登录");
		}
		//广播列表map
		List<Map<String,Object>> broadcastList = new ArrayList<Map<String,Object>>();
		//memberid对应广播id结果集
		List<RobBroadcastInfo> broadcastObjList = loadBroadcastCacheByMemberId(memberId);
		if(!PubMethod.isEmpty(broadcastObjList)) {
			for(RobBroadcastInfo rbi : broadcastObjList) {
				//广播id查询报价列表
				//List<RobQuotationInfo> quotationInfoObjList = loadQuotationInfoByCastId(rbi.getId(), 1);
				//广播id查询包裹列表
				//List<Map<String,Object>> parcelInfoObjList = loadParcelInfoByCastId(rbi.getId());
				//组装数据
				broadcastList.add(broadAssembleData(rbi));
			}
		}
		return broadcastList;
	}
	
	private Map<String,Object> broadAssembleData(RobBroadcastInfo rbi) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("broadcastId", rbi.getId());
		resultMap.put("broadcastType", rbi.getBroadcastType());
		resultMap.put("totalCount", rbi.getTotalCount());
		resultMap.put("totalWeight", rbi.getTotalWeight());
		resultMap.put("broadcastRemark", rbi.getBroadcastRemark());
		resultMap.put("broadcastStatus", rbi.getBroadcastStatus());
		resultMap.put("senderName", rbi.getSenderName());
		resultMap.put("senderMobile", rbi.getSenderMobile());
		resultMap.put("senderAddressId", rbi.getSenderAddressId());
		resultMap.put("senderAddressName", rbi.getSenderAddressName());
		resultMap.put("senderDetailAddressName", rbi.getSenderDetailAddressName());
		resultMap.put("senderLongitude", rbi.getSenderLongitude());
		resultMap.put("senderLatitude", rbi.getSenderLatitude());
		resultMap.put("createTime", rbi.getCreateTime());
		resultMap.put("addresseeAddress", rbi.getAddresseeAddress());
		resultMap.put("newFlag", queryNewFlag(rbi.getId()));
		return resultMap;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询抢单中是否有新报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-3 下午4:56:29</dd>
	 * @param robId
	 * @return
	 * @since v1.0
	 */
	private boolean queryNewFlag(Long robId) {
		boolean flag = false;
		String flagstr = ehcacheService.get("newQuotationCache", String.valueOf(robId), String.class);
		Integer numflag = 1;
		if(!PubMethod.isEmpty(flagstr)) {
			numflag = JSON.parseObject(flagstr, Integer.class);
		} else {
			List<Long> quotateData = robQuotationInfoMapper.selectByNewQuotate(robId);
			if(!PubMethod.isEmpty(quotateData)) {
				numflag = 0;
				ehcacheService.put("newQuotationCache", String.valueOf(robId), numflag);
			} else {
				numflag = 1;
				ehcacheService.put("newQuotationCache", String.valueOf(robId), numflag);
			}
		}
		if(numflag.intValue() == 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>memberid查询对应的广播对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午10:52:16</dd>
	 * @param memberId 操作人id
	 * @return
	 * @since v1.0
	 */
	private List<RobBroadcastInfo> loadBroadcastCacheByMemberId(Long memberId) throws ServiceException {
		List<Long> broadcastIdList = null;
		//缓存查询
		String broadcastStr = ehcacheService.get("robBroadcastCache", String.valueOf(memberId), String.class);
		if(!PubMethod.isEmpty(broadcastStr)) {
			broadcastIdList = JSON.parseArray(broadcastStr, Long.class);
		} else {
			//数据库查询
			broadcastIdList = robBroadcastInfoMapper.selectByLoginMemberId(memberId);
			//放入缓存
			ehcacheService.put("robBroadcastCache", String.valueOf(memberId), broadcastIdList);
		}
		List<RobBroadcastInfo> broadcastObjList = new ArrayList<RobBroadcastInfo>();
		if(!PubMethod.isEmpty(broadcastIdList)) {
			//根据广播id查询广播对象
			RobBroadcastInfo castinfo = null;
			for(Long castId : broadcastIdList) {
				//读取缓存
				String broadcastObjStr = ehcacheService.get("robBroadcastCache", String.valueOf(castId), String.class);
				if(!PubMethod.isEmpty(broadcastObjStr)) {
					castinfo = JSON.parseObject(broadcastObjStr, RobBroadcastInfo.class);
				} else {
					//查询数据库
					castinfo = robBroadcastInfoMapper.selectByPrimaryKey(castId);
					//放入缓存
					ehcacheService.put("robBroadcastCache", String.valueOf(castId), castinfo);
				}
				if(!PubMethod.isEmpty(castinfo)) {
					broadcastObjList.add(castinfo);
				}
			}
		}
		//返回广播列表对象
		return broadcastObjList;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>广播id查询对应报价对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午1:59:40</dd>
	 * @param castId 广播id
	 * @param type 1已抢 2未抢
	 * @return
	 * @since v1.0
	 */
	private List<RobQuotationInfo> loadQuotationInfoByCastId(Long castId, int type) throws ServiceException {
		List<Long> quotationInfoIdList = null;
		//缓存查询
		String quotationInfoStr = ehcacheService.get("robQuotationInfoCache", String.valueOf(castId), String.class);
		if(!PubMethod.isEmpty(quotationInfoStr)) {
			quotationInfoIdList = JSON.parseArray(quotationInfoStr, Long.class);
		} else {
			//查询数据库
			quotationInfoIdList = robQuotationInfoMapper.selectByRobId(castId);
			//放入缓存
			ehcacheService.put("robQuotationInfoCache", String.valueOf(castId), quotationInfoIdList);
		}
		List<RobQuotationInfo> quotationInfoObjList = new ArrayList<RobQuotationInfo>();
		if(!PubMethod.isEmpty(quotationInfoIdList)) {
			RobQuotationInfo rqi = null;
			for(Long quotId : quotationInfoIdList) {
				//读取缓存
				String quotationInfoObjStr = ehcacheService.get("robQuotationInfoCache", String.valueOf(quotId), String.class);
				if(!PubMethod.isEmpty(quotationInfoObjStr)) {
					rqi = JSON.parseObject(quotationInfoObjStr, RobQuotationInfo.class);
				} else {
					rqi = robQuotationInfoMapper.selectByPrimaryKey(quotId);
					//放入缓存
					ehcacheService.put("robQuotationInfoCache", String.valueOf(quotId), rqi);
				}
				if(!PubMethod.isEmpty(rqi)) {
					if(type == 1) {
						if(rob_status_true.equals(rqi.getRobStatus())) {
							quotationInfoObjList.add(rqi);
						}
					} else {
						quotationInfoObjList.add(rqi);
					}
				}
			}
		}
		return quotationInfoObjList;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>广播id查询包裹对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午2:46:01</dd>
	 * @param castId 广播id
	 * @return
	 * @since v1.0
	 */
	private List<Map<String,Object>> loadParcelInfoByCastId(Long castId) throws ServiceException {
		List<Map<String,Object>> parcelInfoObjList = robInfoService.queryParcelInfo(castId);
		return parcelInfoObjList;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>组装广播数据</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 下午7:53:56</dd>
	 * @param rbi 广播对象
	 * @param quotationInfoObjList 抢单列表对象
	 * @param parcelInfoObjList 包裹列表对象
	 * @return
	 * @since v1.0
	 */
	private Map<String,Object> assembleData(RobBroadcastInfo rbi, List<RobQuotationInfo> quotationInfoObjList, List<Map<String,Object>> parcelInfoObjList) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("broadcastId", rbi.getId());
		resultMap.put("broadcastType", rbi.getBroadcastType());
		resultMap.put("totalCount", rbi.getTotalCount());
		resultMap.put("totalWeight", rbi.getTotalWeight());
		resultMap.put("broadcastRemark", rbi.getBroadcastRemark());
		resultMap.put("broadcastStatus", rbi.getBroadcastStatus());
		resultMap.put("senderName", rbi.getSenderName());
		resultMap.put("senderMobile", rbi.getSenderMobile());
		resultMap.put("senderAddressId", rbi.getSenderAddressId());
		resultMap.put("senderAddressName", rbi.getSenderAddressName());
		resultMap.put("senderDetailAddressName", rbi.getSenderDetailAddressName());
		resultMap.put("senderLongitude", rbi.getSenderLongitude());
		resultMap.put("senderLatitude", rbi.getSenderLatitude());
		resultMap.put("createTime", rbi.getCreateTime());
		resultMap.put("addresseeAddress", rbi.getAddresseeAddress());
		resultMap.put("newFlag", false);
		List<Map<String, Object>> quotationInfo = new ArrayList<Map<String, Object>>();
		if(!PubMethod.isEmpty(quotationInfoObjList)) {
			Map<String,Object> map = null;
			BasNetInfo netInfo = null;
			BasCompInfo compinfo = null;
			Long coopNetId = null;
			MemberInfo memberinfo = null;
			for(RobQuotationInfo rqi : quotationInfoObjList) {
				map = new HashMap<String,Object>();
				map.put("id", rqi.getId());
				map.put("memberId", rqi.getMemberId());
				map.put("robId", rqi.getRobId());
				map.put("compId", rqi.getCompId());
				map.put("robStatus", rqi.getRobStatus());
				map.put("successFlag", rqi.getSuccessFlag());
				map.put("quotationAmt", rqi.getQuotationAmt());
				map.put("quotationType", rqi.getQuotationType());
				map.put("isNew", rqi.getIsNew());
				map.put("createTime", rqi.getCreateTime());
				compinfo = getComp(rqi.getCompId());
				if(!PubMethod.isEmpty(compinfo)) {
					coopNetId = compinfo.getBelongToNetId();
					map.put("compName", compinfo.getCompName());
				} else {
					map.put("compName", "");
				}
				netInfo = ehcacheService.get("netCache", String.valueOf(coopNetId), BasNetInfo.class);
				if(PubMethod.isEmpty(netInfo)){
					netInfo = basNetInfoMapper.findById(coopNetId);
				}
				if(!PubMethod.isEmpty(netInfo)) {
					map.put("netName", netInfo.getNetName());
				} else {
					map.put("netName", "");
				}
				memberinfo = getMember(rqi.getMemberId());
				if(!PubMethod.isEmpty(memberinfo)) {
					map.put("memberName", memberinfo.getMemberName());
					map.put("memberPhone", memberinfo.getMemberPhone());
					map.put("imageUrl", imageUrl+memberinfo.getMemberId()+".jpg");
					map.put("memberDetaileDisplay", memberinfo.getMemberDetaileDisplay());
					map.put("memberDetailedAddress", memberinfo.getMemberDetailedAddress());
				} else {
					map.put("memberName", "");
					map.put("memberPhone", "");
					map.put("imageUrl", "");
					map.put("memberDetaileDisplay", "");
					map.put("memberDetailedAddress", "");
				}
				quotationInfo.add(map);
				if(isNew.equals(rqi.getIsNew())) {
					resultMap.put("newFlag", true);
				}
			}
		}
		resultMap.put("quotateData", quotationInfo);
		resultMap.put("parcelData", parcelInfoObjList);
		return resultMap;
	}

	/**
	 * 
	 * @Method: queryBroadcastDetail 
	 * @param robId 广播id
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#queryBroadcastDetail(java.lang.Long)
	 */
	@Override
	public Map<String, Object> doQueryBroadcastDetail(Long robId) throws ServiceException {
		if(PubMethod.isEmpty(robId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.queryBroadcastDetail.001", "请点击广播数据查看详情");
		}
		//广播id查询广播对象
		RobBroadcastInfo rbci = queryBroadcastInfo(robId);
		if(!PubMethod.isEmpty(rbci)) {
			//广播id查询报价列表
			List<RobQuotationInfo> quotationInfoObjList = loadQuotationInfoByCastId(rbci.getId(), 1);
			//更新为已读状态
			if(!PubMethod.isEmpty(quotationInfoObjList)) {
				Map<String,Object> updateMap = new HashMap<String,Object>();
				updateMap.put("isNew", isOld);
				for(RobQuotationInfo rqi : quotationInfoObjList) {
					if(isNew.equals(rqi.getIsNew())) {
						//更新为已读状态
						updateMap.put("id", rqi.getId());
						robQuotationInfoMapper.updateReadStatusByPrimaryKey(updateMap);
						//重新赋值更新缓存
						RobQuotationInfo temp = new RobQuotationInfo();
						temp.setId(rqi.getId());
						temp.setRobId(rqi.getRobId());
						temp.setCompId(rqi.getCompId());
						temp.setMemberId(rqi.getMemberId());
						temp.setRobStatus(rqi.getRobStatus());
						temp.setSuccessFlag(rqi.getSuccessFlag());
						temp.setQuotationAmt(rqi.getQuotationAmt());
						temp.setQuotationType(rqi.getQuotationType());
						temp.setIsNew(isOld);
						temp.setCreateTime(rqi.getCreateTime());
						//更新缓存
						ehcacheService.put("robQuotationInfoCache", String.valueOf(rqi.getId()), temp);
					}
				}
				//更新抢单缓存
				ehcacheService.put("newQuotationCache", String.valueOf(robId), 1);
			}
			//广播id查询包裹列表
			List<Map<String,Object>> parcelInfoObjList = loadParcelInfoByCastId(rbci.getId());
			//组装数据
			return assembleData(rbci, quotationInfoObjList, parcelInfoObjList);
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.queryBroadcastDetail.002", "该广播无详细数据");
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>广播id查询广播对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-15 下午7:28:22</dd>
	 * @return
	 * @since v1.0
	 */
	public RobBroadcastInfo queryBroadcastInfo(Long robId) {
		RobBroadcastInfo rbci = null;
		//读取缓存
		String broadcastObjStr = ehcacheService.get("robBroadcastCache", String.valueOf(robId), String.class);
		if(!PubMethod.isEmpty(broadcastObjStr)) {
			rbci = JSON.parseObject(broadcastObjStr, RobBroadcastInfo.class);
		} else {
			//查询数据库
			rbci = robBroadcastInfoMapper.selectByPrimaryKey(robId);
			//放入缓存
			ehcacheService.put("robBroadcastCache", String.valueOf(robId), rbci);
		}
		return rbci;
	}

	
	/**
	 * 
	 * @Method: cancelBroadcast 
	 * @param robId
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#cancelBroadcast(java.lang.Long)
	 */
	@Override
	public String cancelBroadcast(Long robId) throws ServiceException {
		if(PubMethod.isEmpty(robId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.cancelBroadcast.001", "请选择要取消的广播数据");
		}
		//广播id查询广播对象
		RobBroadcastInfo rbci = queryBroadcastInfo(robId);
		if(!PubMethod.isEmpty(rbci)) {
			//更改取消状态
			rbci.setBroadcastStatus(broadcast_status_cancel);
			robBroadcastInfoMapper.updateByPrimaryKeySelective(rbci);
			//放入缓存t 
			ehcacheService.put("robBroadcastCache", String.valueOf(robId), rbci);
			//清空广播列表缓存
			ehcacheService.remove("robBroadcastCache", String.valueOf(rbci.getLoginMemberId()));
			this.sendMessageToCancelCouriser(robId, -1l);
			return null;
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.cancelBroadcast.002", "广播不存在，取消失败");
		}
	}

	
	/**
	 * 
	 * @Method: timeoutBroadcast 
	 * @param robId
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#timeoutBroadcast(java.lang.Long)
	 */
	@Override
	public String timeoutBroadcast(Long robId) throws ServiceException {
		if(PubMethod.isEmpty(robId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.timeoutBroadcast.001", "请选择要超时的广播数据");
		}
		//广播id查询广播对象
		RobBroadcastInfo rbci = queryBroadcastInfo(robId);
		if(!PubMethod.isEmpty(rbci)) {
			//更改取消状态
			rbci.setBroadcastStatus(broadcast_status_timeout);
			robBroadcastInfoMapper.updateByPrimaryKeySelective(rbci);
			//放入缓存
			ehcacheService.put("robBroadcastCache", String.valueOf(robId), rbci);
			//清空广播列表缓存
			ehcacheService.remove("robBroadcastCache", String.valueOf(rbci.getLoginMemberId()));
			return null;
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.timeoutBroadcast.002", "广播不存在，超时失败");
		}
	}

	
	/**
	 * 
	 * @Method: broadcastRestart 
	 * @param robId
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#broadcastRestart(java.lang.Long)
	 */
	@Override
	public String broadcastRestart(Long robId) throws ServiceException {
		if(PubMethod.isEmpty(robId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastRestart.001", "请选择要重新发起的广播数据");
		}
		//广播id查询广播对象
		RobBroadcastInfo rbci = queryBroadcastInfo(robId);
		if(!PubMethod.isEmpty(rbci)) {
			//查询报价id列表
			List<Long> quotationInfoIdList = null;
			//缓存查询
			String quotationInfoStr = ehcacheService.get("robQuotationInfoCache", String.valueOf(rbci.getId()), String.class);
			if(!PubMethod.isEmpty(quotationInfoStr)) {
				quotationInfoIdList = JSON.parseArray(quotationInfoStr, Long.class);
				//清理抢单结果集缓存
				ehcacheService.removeAll("robQuotationInfoCache");
			} else {
				//查询数据库
				quotationInfoIdList = robQuotationInfoMapper.selectByRobId(rbci.getId());
			}
			if(!PubMethod.isEmpty(quotationInfoIdList)) {
				for(Long qilist : quotationInfoIdList) {
					robQuotationInfoMapper.deleteByPrimaryKey(qilist);
					//清理抢单对象缓存
					ehcacheService.removeAll("robQuotationInfoCache");
				}
			}
			//更改广播状态
			rbci.setBroadcastStatus(broadcast_status_unresponse);
			rbci.setCreateTime(new Date());
			robBroadcastInfoMapper.updateByPrimaryKeySelective(rbci);
			//放入缓存
			ehcacheService.put("robBroadcastCache", String.valueOf(robId), rbci);
			//清空广播列表缓存
			ehcacheService.remove("robBroadcastCache", String.valueOf(rbci.getLoginMemberId()));
			//搜索周边收派员并发通知
			List<RobQuotationInfo> list = broadcastService.getNearCompAndCourier(rbci.getSenderLongitude(), rbci.getSenderLatitude(), rbci.getId(),rbci.getSenderAddressName());
			if (list!=null&&list.size()>0) {
				robInfoService.addbatchRobQuotationInfo(list);
			}
			//清查询取件任务列表缓存
			cleanQueryTaskList(rbci.getLoginMemberId());
			return null;
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastRestart.002", "广播不存在，重新发起失败");
		}
	}

	/**
	 * 
	 * @Method: createTakeTask 
	 * @param robId 广播id
	 * @param quotateId 报价id
	 * @param fromCompId  任务受理方站点  n 选中收派员时为收派员所在站点id  选中站点时为站点id
	 * @param fromMemberId  任务受理人员
	 * @param toCompId  营业分部
	 * @param toMemberId  收派员  选中收派员时填写
	 * @param coopNetId  任务受理方网络  n
	 * @param appointTime 约定取件时间  格式(yyyy-MM-dd HH:mm)
	 * @param actorMemberId 执行人员  n
	 * @param contactTel 发件人电话
	 * @param taskSource 取件任务创建类型  个人端和电商
	 * @return
	 * @throws Exception 
	 * @see net.okdi.api.service.BroadcastListService#createTakeTask(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Long, java.lang.Long, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.lang.Byte)
	 */
	@Override
	public Map<String, Object> createTakeTask(Long robId, Long quotateId, String appointTime, String contactTel, Byte taskSource, Byte taskFlag, String senderPhone) throws ServiceException, Exception {
		if(PubMethod.isEmpty(robId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.createTakeTask.001", "请选择要创建预约的广播数据");
		}
		if(PubMethod.isEmpty(quotateId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.createTakeTask.002", "请选择要创建预约的报价数据");
		}
		if(PubMethod.isEmpty(senderPhone) && taskFlag == 2) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.createTakeTask.005", "请填写发件人电话");
		}
		//广播id查询广播对象
		RobBroadcastInfo rbci = queryBroadcastInfo(robId);
		//报价id查询报价对象
		RobQuotationInfo rqi = queryQuotationInfo(quotateId);
		if(!PubMethod.isEmpty(rbci)) {
			//更改报价状态，获取报价金额
			BigDecimal parEstimatePrice = null;
			Long taskId = null;
			Map<String, Object> taskMap = null;
			if(!PubMethod.isEmpty(rqi)) {
				rbci.setQuotationId(rqi.getId());
				//修改报价状态
				rqi.setSuccessFlag(success_flag_true);
				robQuotationInfoMapper.updateByPrimaryKeySelective(rqi);
				ehcacheService.put("robQuotationInfoCache", String.valueOf(rqi.getId()), rqi);
				parEstimatePrice = rqi.getQuotationAmt();
				
				//准备创建取件任务的数据
				Long fromCompId = rqi.getCompId();
				Long fromMemberId = (rqi.getMemberId()!=null)?rqi.getMemberId():null;
				Long toMemberId = fromMemberId;
				String senderMobile = null;
				if(taskFlag == 2) {
					senderMobile = senderPhone;
				} else {
					senderMobile = rbci.getSenderMobile();
				}
				BasCompInfo compinfo = getComp(rqi.getCompId());
				Long coopNetId = null;
				if(!PubMethod.isEmpty(compinfo)) {
					coopNetId = compinfo.getBelongToNetId();
				}
				Long actorMemberId = toMemberId;
				String[] address = rbci.getSenderAddressName().split("\\|");
				String contactAddress = "";
				if(address != null && address.length > 0) {
					if(address.length > 1) {
						contactAddress = address[0] +" "+ address[1];
					} else {
						contactAddress = address[0];
					}
				}
				//创建取件任务
				taskMap = takeTaskService.create(fromCompId, fromMemberId, null, toMemberId, coopNetId, appointTime, rbci.getBroadcastRemark(), actorMemberId, rbci.getSenderName(), senderMobile,
						contactTel, rbci.getSenderAddressId(), contactAddress, rbci.getLoginMemberId(), rbci.getLoginMemberId(), new BigDecimal(rbci.getSenderLongitude()), 
						new BigDecimal(rbci.getSenderLatitude()), taskSource, task_type_take, null, null, new BigDecimal(rbci.getTotalWeight()), Byte.valueOf(rbci.getTotalCount().toString()), parEstimatePrice, (byte) 1, robId, "");
				if(taskMap != null && taskMap.get("taskId") != null && !"".equals(taskMap.get("taskId").toString())) {
					taskId = Long.parseLong(taskMap.get("taskId").toString());
				} else {
					throw new ServiceException("openapi.BroadcastListServiceImpl.createTakeTask.003", "创建取件任务失败");
				}
				MemberInfo member = getMember(rqi.getMemberId());
				String memberPhone = "";
				if(!PubMethod.isEmpty(member)) {
					memberPhone = member.getMemberPhone();
				}
				//调用推送
				sendNoticeService.bidSuccessToExp(rbci.getId(), rqi.getMemberId(), memberPhone, taskId, contactAddress);
				//TODO
				//edit by zmn 2015/05/25 客户选中收派员后推送给抢单失败的收派员
				sendMessageToFailedCouriser(robId,quotateId);
			} else {
				throw new ServiceException("openapi.BroadcastListServiceImpl.createTakeTask.005", "报价信息不存在，创建预约失败");
			}
			//修改广播状态
		 	rbci.setTaskId(taskId);
			rbci.setBroadcastStatus(broadcast_status_finish);
			robBroadcastInfoMapper.updateByPrimaryKeySelective(rbci);
			//放入缓存
			ehcacheService.put("robBroadcastCache", String.valueOf(rbci.getId()), rbci);
			//清空广播列表缓存
			ehcacheService.remove("robBroadcastCache", String.valueOf(rbci.getLoginMemberId()));
			//清查询取件任务缓存
			String queryresult = ehcacheService.get("TakeTaskCacheRecordResultByMemberId", String.valueOf(rbci.getLoginMemberId()), String.class);
			List<String> querylist = null;
			if(!PubMethod.isEmpty(queryresult)) {
				querylist = JSON.parseArray(queryresult, String.class);
			}
			if(!PubMethod.isEmpty(querylist)) {
				ehcacheService.remove("TakeTaskCacheRecordResultByMemberId", querylist);
			}
			ehcacheService.removeAll("TakeTaskCacheQueryResult");
			
			//返回taskId的map
			return taskMap;
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.createTakeTask.004", "广播不存在，创建预约失败");
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>报价id查询报价对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-16 上午11:36:06</dd>
	 * @param quotateId 报价id
	 * @return
	 * @since v1.0
	 */
	private RobQuotationInfo queryQuotationInfo(Long quotateId) throws ServiceException {
		RobQuotationInfo rqi = null;
		//读取缓存
		String quotationInfoObjStr = ehcacheService.get("robQuotationInfoCache", String.valueOf(quotateId), String.class);
		if(!PubMethod.isEmpty(quotationInfoObjStr)) {
			rqi = JSON.parseObject(quotationInfoObjStr, RobQuotationInfo.class);
		} else {
			rqi = robQuotationInfoMapper.selectByPrimaryKey(quotateId);
			//放入缓存
			ehcacheService.put("robQuotationInfoCache", String.valueOf(quotateId), rqi);
		}
		return rqi;
	}

	
	/**
	 * 
	 * @Method: deleteBroadcast 
	 * @param broadcastId
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#deleteBroadcast(java.lang.Long)
	 */
	@Override
	public String deleteBroadcast(Long broadcastId, Long memberId) throws ServiceException {
		if(PubMethod.isEmpty(broadcastId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteBroadcast.001", "请选择要删除的广播数据");
		}
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteBroadcast.004", "无广播信息，请先登录");
		}
		//广播id查询广播对象
		RobBroadcastInfo rbci = queryBroadcastInfo(broadcastId);
		boolean deleteFlag = false;
		if(!PubMethod.isEmpty(rbci)) {
			//判断取消和超时状态可以删除
			deleteFlag = broadcastStatusOK(rbci);
			if(deleteFlag) {
				//广播id查询报价列表
				List<RobQuotationInfo> quotationInfoObjList = loadQuotationInfoByCastId(rbci.getId(), 2);
				//广播id查询包裹列表
				List<Map<String,Object>> parcelInfoObjList = loadParcelInfoByCastId(rbci.getId());
				//广播id删除广播包裹关系缓存
				ehcacheService.removeAll("robParcelRelationCache");
				//广播id删除广播报价列表缓存
				ehcacheService.removeAll("robQuotationInfoCache");
				if(!PubMethod.isEmpty(quotationInfoObjList)) {
					//删除报价表数据
					deleteQuotationInfo(quotationInfoObjList);
				}
				if(!PubMethod.isEmpty(parcelInfoObjList)) {
					//删除包裹表数据和包裹广播关系表数据
					deleteParcelInfo(parcelInfoObjList, rbci);
					//清除包裹缓存
					ehcacheService.removeAll("takeParcelIdsCacheByTaskId");
					ehcacheService.removeAll("takeParcelIdsCacheByMemberId");
					ehcacheService.removeAll("robParcelRelationCache");
					ehcacheService.remove("robParcelRelationCache", String.valueOf(rbci.getId()));
				}
				//删除广播对象
				robBroadcastInfoMapper.deleteByPrimaryKey(rbci.getId());
				//清除广播对象缓存
				ehcacheService.remove("robBroadcastCache", String.valueOf(rbci.getId()));
				//清除登录人列表缓存
				ehcacheService.remove("robBroadcastCache", String.valueOf(memberId));
				return null;
			} else {
				throw new ServiceException("openapi.BroadcastListServiceImpl.deleteBroadcast.002", "广播状态错误，删除失败");
			}
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteBroadcast.003", "广播不存在，删除失败");
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断广播类型(取消和超时返回true)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午10:41:55</dd>
	 * @param rbci
	 * @return
	 * @since v1.0
	 */
	private boolean broadcastStatusOK(RobBroadcastInfo rbci) throws ServiceException {
		boolean flag = false;
		//取消和超时状态
		if(broadcast_status_cancel.equals(rbci.getBroadcastStatus()) || broadcast_status_timeout.equals(rbci.getBroadcastStatus())) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除报价信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午10:57:37</dd>
	 * @param quotationInfoObjList
	 * @throws ServiceException
	 * @since v1.0
	 */
	private void deleteQuotationInfo(List<RobQuotationInfo> quotationInfoObjList) throws ServiceException {
		for(RobQuotationInfo rqi : quotationInfoObjList) {
			//报价id删除报价表信息
			robQuotationInfoMapper.deleteByPrimaryKey(rqi.getId());
			//清理抢单对象缓存
			//ehcacheService.remove("robQuotationInfoCache", String.valueOf(rqi.getId()));
		}
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除包裹信息和广播包裹关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午11:28:44</dd>
	 * @param parcelInfoObjList
	 * @since v1.0
	 */
	private void deleteParcelInfo(List<Map<String,Object>> parcelInfoObjList, RobBroadcastInfo rbci) throws ServiceException {
		//删除包裹关系表
		robParcelRelationMapper.deleteByRobId(rbci.getId());
		for(Map<String,Object> map : parcelInfoObjList) {
			if(map != null && map.get("parcelId") != null && !"".equals(map.get("parcelId").toString())) {
				//删除包裹信息
				parParcelinfoMapper.deleteParcel(Long.parseLong(map.get("parcelId").toString()));
				parParceladdressMapper.deleteParceladdress(Long.parseLong(map.get("parcelId").toString()));
			}
		}
	}
	
	/**
	 * 
	 * @Method: deleteTakeTask 
	 * @param taskId 任务id
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#deleteTakeTask(java.lang.Long)
	 */
	@Override
	public String deleteTakeTask(Long taskId, Long memberId) throws ServiceException {
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteTakeTask.001", "请选择要删除的任务数据");
		}
		if(PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteTakeTask.002", "无任务信息，请先登录");
		}
		//更新任务信息表状态
		ParTaskInfo taskInfo = cacheTaskInfo(String.valueOf(taskId));
		if(!PubMethod.isEmpty(taskInfo)) {
			taskInfo.setTaskId(taskId);
			taskInfo.setTaskStatus(task_status_delete);
			parTaskInfoMapper.updateParTaskInfoBySelective(taskInfo);
			ehcacheService.put("TakeTaskCacheTaskInfo", String.valueOf(taskInfo.getTaskId()), taskInfo);
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteTakeTask.003", "取件任务不存在，删除失败");
		}
		//更新任务记录表状态
		ParTaskDisposalRecord taskRecord = new ParTaskDisposalRecord();
		taskRecord.setTaskId(taskId);
		taskRecord.setDisposalType(task_status_delete);
		parTaskDisposalRecordMapper.updateByTaskIdSelective(taskRecord);
		//删除包裹记录
		parcelInfoService.deleteParcelByTakeTaskId(taskId, taskInfo.getCoopNetId());
		ehcacheService.removeAll("TakeTaskCacheTaskRecord");
		//清缓存
		ehcacheService.removeAll("TakeTaskCacheQueryResult");
		ehcacheService.removeAll("TakeTaskCacheTaskRecordResult");
		cleanQueryTaskList(memberId);
		String queryresult = ehcacheService.get("TakeTaskCacheQueryResult", String.valueOf(memberId), String.class);
		List<String> querylist = null;
		if(!PubMethod.isEmpty(queryresult)) {
			querylist = JSON.parseArray(queryresult, String.class);
		}
		if(!PubMethod.isEmpty(querylist)) {
			ehcacheService.remove("TakeTaskCacheQueryResult", String.valueOf(memberId)+"Count");
			ehcacheService.remove("TakeTaskCacheQueryResult", querylist);
		}
		return null;
	}
	
	
	/**
	 * 
	 * @Method: queryTaskDetail 
	 * @param taskId 任务id
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#toFinishTask(java.lang.Long)
	 */
	@Override
	public Map<String, Object> queryTaskDetail(Long taskId) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.queryTaskDetail.001", "请选择要待交寄的任务数据");
		}
		//查询任务信息
		ParTaskInfo taskInfo = cacheTaskInfo(String.valueOf(taskId));
		if(!PubMethod.isEmpty(taskInfo)) {
			if(task_status_finish.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.queryTaskDetail.003", "任务已完成");
			}
			/*if(task_status_cancel.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.toFinishTask.004", "任务已取消");
			}
			if(task_status_untake.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.toFinishTask.005", "任务未分配给收派员");
			}
			if(task_status_distribute.equals(taskInfo.getTaskStatus())) {
			}*/
			if(task_status_delete.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.queryTaskDetail.006", "任务已删除");
			}
			//查询任务记录表
			ParTaskDisposalRecord memberTask = cacheDisposalRecord(taskInfo);
			resultmap = assembleTaskData(taskInfo, memberTask);
			/*if(!PubMethod.isEmpty(memberTask)) {
				
			} else {
				throw new ServiceException("openapi.BroadcastListServiceImpl.toFinishTask.005", "任务未分配给收派员");
			}*/
			return resultmap;
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.queryTaskDetail.002", "取件任务不存在，查询待交寄任务失败");
		}
	}
	
	private Map<String, Object> assembleTaskData(ParTaskInfo taskInfo, ParTaskDisposalRecord memberTask) {
		Map<String, Object> resultmap = new HashMap<String, Object>();
		BasNetInfo netInfo = null;
		resultmap.put("contactName", taskInfo.getContactName());
		resultmap.put("contactMobile", taskInfo.getContactMobile());
		resultmap.put("contactAddressId", taskInfo.getContactAddressId());
		//resultmap.put("contactAddress", taskInfo.getContactAddress().split(" ")[0]);
		//resultmap.put("contactAddressDetail", taskInfo.getContactAddress().substring(taskInfo.getContactAddress().indexOf(" ")+1, taskInfo.getContactAddress().length()));
		resultmap.put("netId", taskInfo.getCoopNetId());
		resultmap.put("netImageUrl", "http://www.okdi.net/nfs_data/comp/"+taskInfo.getCoopNetId()+".png");
		netInfo = ehcacheService.get("netCache", String.valueOf(taskInfo.getCoopNetId()), BasNetInfo.class);
		if(PubMethod.isEmpty(netInfo)){
			netInfo = basNetInfoMapper.findById(taskInfo.getCoopNetId());
		}
		if(!PubMethod.isEmpty(netInfo)) {
			resultmap.put("netName", netInfo.getNetName());
			resultmap.put("netCode", netInfo.getCode());
		} else {
			resultmap.put("netName", "");
			resultmap.put("netCode", "");
		}
		resultmap.put("taskStatus", taskInfo.getTaskStatus());
		if(!PubMethod.isEmpty(taskInfo.getActorMemberId())) {
			resultmap.put("sendStatus", true);
		} else {
			resultmap.put("sendStatus", false);
		}
		Long broadcastId = broadcastMapper.getBroadcastIdByTaskId(taskInfo.getTaskId());
		if(!PubMethod.isEmpty(broadcastId)) {
			resultmap.put("isBroad", true);
		} else {
			resultmap.put("isBroad", false);
		}
		BasCompInfo compinfo = getComp(taskInfo.getCoopCompId());
		if(!PubMethod.isEmpty(compinfo)) {
			resultmap.put("compName", compinfo.getCompName());
            resultmap.put("compPhone", compinfo.getCompTelephone());
            resultmap.put("compMobile", compinfo.getCompMobile());
            BasCompbusiness basCompbusiness = this.ehcacheService.get("compBusinessCache", String.valueOf(compinfo.getCompId()), BasCompbusiness.class);
			if(PubMethod.isEmpty(basCompbusiness)){
				basCompbusiness = basCompbusinessMapper.findById(Long.valueOf(String.valueOf(compinfo.getCompId())));
				this.ehcacheService.put("compBusinessCache", String.valueOf(compinfo.getCompId()), basCompbusiness);
			}
			if(!PubMethod.isEmpty(basCompbusiness)) {
				resultmap.put("responsible", basCompbusiness.getResponsible());
				resultmap.put("responsibleTelePhone", basCompbusiness.getResponsibleTelephone());
			} else {
				resultmap.put("responsible", "");
				resultmap.put("responsibleTelePhone", "");
			}
			if(compinfo.getCompStatus() != null && "1".equals(compinfo.getCompStatus().toString())) {
				resultmap.put("compType", "已认证");
			} else {
				resultmap.put("compType", "");
			}
			String[] addr = compinfo.getCompAddress().split("\\|");
			if(addr != null && addr.length > 0) {
				resultmap.put("contactAddress", addr[0]);
				if(addr.length > 1) {
					resultmap.put("contactAddressDetail", addr[1]);
				} else {
					resultmap.put("contactAddressDetail", "");
				}
			} else {
				resultmap.put("contactAddress", "");
				resultmap.put("contactAddressDetail", "");
			}
		} else {
			resultmap.put("compName", "");
			resultmap.put("compPhone", "");
			resultmap.put("compMobile", "");
			resultmap.put("compType", "");
			resultmap.put("responsible", "");
			resultmap.put("contactAddress", "");
			resultmap.put("contactAddressDetail", "");
		}
		if(taskInfo.getTaskStatus() != null && !"".equals(taskInfo.getTaskStatus().toString()) && "3".equals(taskInfo.getTaskStatus().toString())) {
			String cachelogparam = "okdi_" + taskInfo.getTaskId();
			//判断是否为站点方取消
			//读取缓存信息
			String taskProcessList = ehcacheService.get("TakeTaskCacheTaskProcessResult", cachelogparam, String.class);
			List<ParTaskProcess> tasklist = null;
			if(!PubMethod.isEmpty(taskProcessList) && !"null".equals(taskProcessList)) {
				tasklist = JSON.parseArray(taskProcessList, ParTaskProcess.class); 
			}
			if(PubMethod.isEmpty(tasklist)) {
				tasklist = parTaskProcessMapper.selectMaxCancelTaskByTaskId(taskInfo.getTaskId());
				ehcacheService.put("TakeTaskCacheTaskProcessResult", cachelogparam, tasklist);
			}
			if(tasklist != null && tasklist.size() > 0) {
				resultmap.put("taskTransmitCause", tasklist.get(0).getTaskTransmitCause());
				resultmap.put("taskProcessDesc", tasklist.get(0).getTaskProcessDesc());
			} else {
				resultmap.put("taskTransmitCause", "");
				resultmap.put("taskProcessDesc", "");
			}
		} else {
			resultmap.put("taskTransmitCause", "");
			resultmap.put("taskProcessDesc", "");
		}
		resultmap.put("parEstimateWeight", taskInfo.getParEstimateWeight().setScale(2, BigDecimal.ROUND_HALF_UP));
		resultmap.put("parEstimateCount", taskInfo.getParEstimateCount());
		resultmap.put("parEstimatePrice", taskInfo.getParEstimatePrice());
		resultmap.put("createTime", taskInfo.getCreateTime());
		if(!PubMethod.isEmpty(memberTask)) {
			MemberInfo memberinfo = getMember(memberTask.getMemberId());
			if(!PubMethod.isEmpty(memberinfo)) {
				resultmap.put("memberId", memberinfo.getMemberId());
				resultmap.put("imageUrl", imageUrl+memberinfo.getMemberId()+".jpg");
				resultmap.put("memberName", memberinfo.getMemberName());
				resultmap.put("memberPhone", memberinfo.getMemberPhone());
				resultmap.put("memberDetaileDisplay", memberinfo.getMemberDetaileDisplay());
				resultmap.put("memberDetailedAddress", memberinfo.getMemberDetailedAddress());
			} else {
				resultmap.put("memberId", "");
				resultmap.put("imageUrl", "");
				resultmap.put("memberName", "");
				resultmap.put("memberPhone", "");
				resultmap.put("memberDetaileDisplay", "");
				resultmap.put("memberDetailedAddress", "");
			}
		} else {
			resultmap.put("memberId", "");
			resultmap.put("imageUrl", "");
			resultmap.put("memberName", "");
			resultmap.put("memberPhone", "");
			resultmap.put("memberDetaileDisplay", "");
			resultmap.put("memberDetailedAddress", "");
		}
		//查询包裹信息
		List<Map> parcelList = null;
		//读取缓存
		String parcelStr = ehcacheService.get("takeParcelIdsCacheByTaskId", String.valueOf(taskInfo.getTaskId()), String.class);
		if(!PubMethod.isEmpty(parcelStr)) {
			parcelList = JSON.parseArray(parcelStr, Map.class);
		} else {
			parcelList = parParcelinfoMapper.queryParcelInfoByTaskId(taskInfo.getTaskId());
			//加缓存
			ehcacheService.put("takeParcelIdsCacheByTaskId", String.valueOf(taskInfo.getTaskId()), parcelList);
		}
		if(!PubMethod.isEmpty(parcelList)) {
			String[] address = null;
			for(Map map : parcelList) {
				if(map != null && map.get("chareWeightForsender") != null && !"".equals(map.get("chareWeightForsender").toString())) {
					map.put("chareWeightForsender", map.get("chareWeightForsender").toString());
				} else {
					map.put("chareWeightForsender", "");
				}
				if(map != null && map.get("addresseeAddress") != null && !"".equals(map.get("addresseeAddress").toString())) {
					address = map.get("addresseeAddress").toString().split("\\|");
					if(address != null && address.length > 0) {
						if(address.length > 1) {
							map.put("addresseeAddressDetail", address[1]);
						} else {
							map.put("addresseeAddressDetail", "");
						}
						map.put("addresseeAddress", address[0]);
					} else {
						map.put("addresseeAddressDetail", "");
						map.put("addresseeAddress", "");
					}
				} else {
					map.put("addresseeAddressDetail", "");
					map.put("addresseeAddress", "");
				}
				if(map != null && map.get("addresseeAddressId") != null && !"".equals(map.get("addresseeAddressId").toString())) {
					map.put("addresseeAddressId", map.get("addresseeAddressId").toString());
				} else {
					map.put("addresseeAddressId", "");
				}
				if(PubMethod.isEmpty(map.get("addresseeMobile"))){
					map.put("addresseeMobile","");
				}
				if(PubMethod.isEmpty(map.get("addresseeName"))){
					map.put("addresseeName","");
				}
			}
		} else {
			parcelList = new ArrayList<Map>();
		}
		resultmap.put("parcelData", parcelList);
		return resultmap;
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询任务记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午1:19:04</dd>
	 * @param taskId
	 * @return
	 * @since v1.0
	 */
	private ParTaskDisposalRecord cacheDisposalRecord(ParTaskInfo taskInfo) {
		ParTaskDisposalRecord memberTask = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("taskId", taskInfo.getTaskId());
		map.put("disposalObject", disposal_object_member);
		String cacheKey = "taskId="+taskInfo.getTaskId()+"disposalObject="+disposal_object_member;
		String strrecord = ehcacheService.get("TakeTaskCacheTaskRecordResult", cacheKey, String.class);
		if(!PubMethod.isEmpty(strrecord)) {
			memberTask = JSON.parseObject(strrecord, ParTaskDisposalRecord.class);
		} else {
			memberTask = parTaskDisposalRecordMapper.queryTaskToMember(map);
			//放入缓存
			ehcacheService.put("TakeTaskCacheTaskRecordResult", cacheKey, memberTask);
			List<String> tasklist = null;
			tasklist = ehcacheService.get("TakeTaskCacheTaskRecordResult", String.valueOf(taskInfo.getTaskId()), ArrayList.class);
			if(PubMethod.isEmpty(tasklist)){
				tasklist = new ArrayList<String>();
			}
			if(!tasklist.contains(cacheKey)){
				tasklist.add(cacheKey);
			}
			//存入缓存
			ehcacheService.put("TakeTaskCacheTaskRecordResult", String.valueOf(taskInfo.getTaskId()), tasklist);
		}
		return memberTask;
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询任务信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 上午11:05:53</dd>
	 * @param key
	 * @return
	 * @throws ServiceException
	 * @since v1.0
	 */
	public ParTaskInfo cacheTaskInfo(String key) throws ServiceException {
		ParTaskInfo cacheTaskInfo = null;
		String taskInfo = ehcacheService.get("TakeTaskCacheTaskInfo", key, String.class);
		if(!PubMethod.isEmpty(taskInfo)) {
			cacheTaskInfo = JSON.parseObject(taskInfo, ParTaskInfo.class);
		}
		if(PubMethod.isEmpty(cacheTaskInfo)) {
			cacheTaskInfo = parTaskInfoMapper.selectByPrimaryKey(Long.parseLong(key));
			ehcacheService.put("TakeTaskCacheTaskInfo", key, cacheTaskInfo);
		}
		return cacheTaskInfo;
	}
	
	/**
	 * 
	 * @Method: scanParcel 
	 * @param id 包裹id
	 * @param taskId 任务id
	 * @param expWaybillNum 运单号
	 * @param phone 收件人电话
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#ScanParcel(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public String scanParcel(Long id, Long taskId, String expWaybillNum, String phone) {
		if(PubMethod.isEmpty(id)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.001", "请选择要扫描的包裹");
		}
		if(PubMethod.isEmpty(expWaybillNum)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.002", "请输入运单号");
		}
//		if(PubMethod.isEmpty(phone)) {
//			throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.003", "请输入收件人手机号");
//		}
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.007", "取件任务不存在，扫描包裹运单号失败");
		}
		//查询任务信息
		ParTaskInfo taskInfo = cacheTaskInfo(String.valueOf(taskId));
		if(!PubMethod.isEmpty(taskInfo)) {
			if(task_status_finish.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.004", "任务已完成");
			}
			if(task_status_cancel.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.005", "任务已取消");
			}
			if(task_status_untake.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.006", "任务未分配给收派员");
			}
			if(task_status_delete.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.007", "任务已删除");
			}
			if(task_status_distribute.equals(taskInfo.getTaskStatus())) {
				//查询任务记录表
				ParTaskDisposalRecord memberTask = cacheDisposalRecord(taskInfo);
				if(!PubMethod.isEmpty(memberTask)) {
					//查询包裹id是否存在
					Long tempParcelId = parcelInfoService.queryParcelInfoByExpWayBillNumAndNetId(taskInfo.getCoopNetId(), expWaybillNum);
					if(!PubMethod.isEmpty(tempParcelId) && !tempParcelId.equals(id)) {
						throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.008", "运单号已存在，交寄失败");
					}
					ParParcelinfo parcel = new ParParcelinfo();
					parcel.setId(id);
					parcel.setCreateUserId(taskInfo.getCreateUserId());
					parcel.setExpWaybillNum(expWaybillNum);
					parcel.setParcelStatus((short) 1);
					//更新包裹信息
					parParcelinfoMapper.updateParcelSelective(parcel);
					ParParceladdress paraddr = new ParParceladdress();
					paraddr.setId(id);
					paraddr.setSendAddressId(taskInfo.getContactAddressId());
					paraddr.setSendAddress(taskInfo.getContactAddress());
					paraddr.setSendName(taskInfo.getContactName());
					paraddr.setSendMobile(taskInfo.getContactMobile());
					paraddr.setAddresseeMobile(phone);
					parParceladdressMapper.updateParceladdressSelective(paraddr);
					
					System.out.println("--------取件包裹记录开始------");
					//取件包裹记录
					ParParcelconnection connect = new ParParcelconnection();
					connect.setParId(id);
					connect.setCompId(taskInfo.getCoopCompId());
					connect.setNetId(taskInfo.getCoopNetId());
					connect.setExpMemberId(taskInfo.getActorMemberId());
					connect.setTaskId(taskInfo.getTaskId());
					connect.setCreateTime(new Date());
					connect.setCosignFlag((short) 1);
					connect.setExpMemberSuccessFlag((short) 1);
					parcelInfoService.addParcelConnection(connect);
					System.out.println("--------取件包裹记录完成------");
					
					//清理缓存
					ehcacheService.remove("takeParcelIdsCacheByTaskId", String.valueOf(taskInfo.getTaskId()));
					ehcacheService.remove("takeParcelIdsCacheByMemberId", String.valueOf(taskInfo.getTaskId()));
					ehcacheService.removeAll("robParcelRelationCache");
					//更新后查询对象
					parcel = parParcelinfoMapper.findById(id);
					paraddr = parParceladdressMapper.findParceladdress(id);
					//更新对象缓存
					ehcacheService.put("parcelInfoCache", String.valueOf(id), parcel);
					ehcacheService.put("parcelAddressCache", String.valueOf(id), paraddr);
					String addrLongitude = PubMethod.isEmpty(taskInfo.getContactAddrLongitude())?"":taskInfo.getContactAddrLongitude().toString();
					String addrLatitude = PubMethod.isEmpty(taskInfo.getContactAddrLatitude())?"":taskInfo.getContactAddrLatitude().toString();
					//给收件人推送消息
					if(!PubMethod.isEmpty(phone)) {
						MemberInfo memberInfo = getMemberByMemberPhone(phone);
						if(!PubMethod.isEmpty(memberInfo)) {
							parLogisticSearchService.saveSend(memberInfo.getMemberId(), taskInfo.getCoopNetId(), expWaybillNum, "0", "", id, phone, "0", CHANNEL_NO_PERSONNEL,EXP_TYPE_RECEIVE);
						}
						MemberInfo memberInfo0 = getMember(taskInfo.getActorMemberId());
						if(!PubMethod.isEmpty(memberInfo0)) {
							sendNoticeService.sendToRecpt(id, expWaybillNum, taskInfo.getCoopNetId(), taskInfo.getContactName(), phone, addrLongitude, addrLatitude, memberInfo0.getMemberPhone());
						}
					}
					return null;
				} else {
					throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.006", "任务未分配给收派员");
				}
			} else {
				throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.007", "取件任务不存在，扫描包裹运单号失败");
			}
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.ScanParcel.007", "取件任务不存在，扫描包裹运单号失败");
		}
	}
	
	
	
	/**
	 * 
	 * @Method: broadcastCreateParcel 
	 * @param taskId 任务id
	 * @param expWaybillNum 运单号
	 * @param phone 收件人电话
	 * @param addresseeAddressId 收件地址id
	 * @param addresseeAddress 收件地址文字
	 * @param sendType 发件人类型 1:发货商家,2:好递个人
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#broadcastCreateParcel(java.lang.Long, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.Short)
	 */
	@Override
	public Map<String, Object> broadcastCreateParcel(Long taskId, String expWaybillNum, String phone, Long addresseeAddressId, String addresseeAddress, Short sendType) {
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.001", "取件任务不存在，添加新包裹失败");
		}
		if(PubMethod.isEmpty(expWaybillNum)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.002", "请填写包裹运单号");
		}
//		if(PubMethod.isEmpty(phone)) {
//			throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.003", "请填写收件人电话");
//		}
		if(PubMethod.isEmpty(addresseeAddressId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.004", "请选择收件地址");
		}
		if(PubMethod.isEmpty(addresseeAddress)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.004", "请选择收件地址");
		}
		//查询任务信息
		ParTaskInfo taskInfo = cacheTaskInfo(String.valueOf(taskId));
		Map<String,Object> parcelId = null;
		if(!PubMethod.isEmpty(taskInfo)) {
			if(task_status_finish.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.005", "任务已完成");
			}
			if(task_status_cancel.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.006", "任务已取消");
			}
			if(task_status_untake.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.007", "任务未分配给收派员");
			}
			if(task_status_delete.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.008", "任务已删除");
			}
			if(task_status_distribute.equals(taskInfo.getTaskStatus())) {
				//查询任务记录表
				ParTaskDisposalRecord memberTask = cacheDisposalRecord(taskInfo);
				if(!PubMethod.isEmpty(memberTask)) {
					//查询包裹id是否存在
					Long tempParcelId = parcelInfoService.queryParcelInfoByExpWayBillNumAndNetId(taskInfo.getCoopNetId(), expWaybillNum);
					if(!PubMethod.isEmpty(tempParcelId)) {
						throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.009", "运单号已存在，交寄失败");
					}
					parcelId = new HashMap<String,Object>();
					Long pId = IdWorker.getIdWorker().nextId();
					//更新包裹
					ParParcelinfo parcelInfo = new ParParcelinfo();
					parcelInfo.setId(pId);
					parcelId.put("parcelId", parcelInfo.getId());
					parcelInfo.setSenderType(sendType);
					parcelInfo.setExpWaybillNum(expWaybillNum);
					parcelInfo.setCreateUserId(taskInfo.getCreateUserId());
					parcelInfo.setCreateTime(new Date());
					parcelInfo.setNetId(taskInfo.getCoopNetId());
					parcelInfo.setCompId(taskInfo.getCoopCompId());
					parcelInfo.setTakeTaskId(taskInfo.getTaskId());
					parcelInfo.setActualTakeMember(taskInfo.getActorMemberId());
					parcelInfo.setParcelStatus((short) 1);
					parParcelinfoMapper.insertParcel(parcelInfo);
					//更新包裹地址信息
					ParParceladdress parceladdr = new ParParceladdress();
					parceladdr.setId(parcelInfo.getId());
					parceladdr.setSendAddressId(taskInfo.getContactAddressId());
					parceladdr.setSendAddress(taskInfo.getContactAddress());
					parceladdr.setSendName(taskInfo.getContactName());
					parceladdr.setSendMobile(taskInfo.getContactMobile());
					parceladdr.setAddresseeMobile(phone);
					parceladdr.setAddresseeAddressId(addresseeAddressId);
					parceladdr.setAddresseeAddress(addresseeAddress);
					parceladdr.setSendCasUserId(taskInfo.getCreateUserId());
					parParceladdressMapper.insertParceladdress(parceladdr);
					//清理缓存
					ehcacheService.remove("takeParcelIdsCacheByTaskId", String.valueOf(taskInfo.getTaskId()));
					ehcacheService.remove("takeParcelIdsCacheByMemberId", String.valueOf(taskInfo.getTaskId()));
					ehcacheService.removeAll("robParcelRelationCache");
					//更新后查询对象
					parcelInfo = parParcelinfoMapper.findById(pId);
					parceladdr = parParceladdressMapper.findParceladdress(pId);
					//更新对象缓存
					ehcacheService.put("parcelInfoCache", String.valueOf(pId), parcelInfo);
					ehcacheService.put("parcelAddressCache", String.valueOf(pId), parceladdr);
					String addrLongitude = PubMethod.isEmpty(taskInfo.getContactAddrLongitude())?"":taskInfo.getContactAddrLongitude().toString();
					String addrLatitude = PubMethod.isEmpty(taskInfo.getContactAddrLatitude())?"":taskInfo.getContactAddrLatitude().toString();
					//给收件人推送消息
					if(!PubMethod.isEmpty(phone)) {
						MemberInfo memberInfo0 = getMember(taskInfo.getActorMemberId());
						if(!PubMethod.isEmpty(memberInfo0)) {
							sendNoticeService.sendToRecpt(parcelInfo.getId(), parcelInfo.getExpWaybillNum(), taskInfo.getCoopNetId(), taskInfo.getContactName(), phone, addrLongitude, addrLatitude, memberInfo0.getMemberPhone());
						}
					}

					System.out.println("--------取件包裹记录开始------");
					//取件包裹记录
					ParParcelconnection connect = new ParParcelconnection();
					connect.setParId(pId);
					connect.setCompId(taskInfo.getCoopCompId());
					connect.setNetId(taskInfo.getCoopNetId());
					connect.setExpMemberId(taskInfo.getActorMemberId());
					connect.setTaskId(taskInfo.getTaskId());
					connect.setCreateTime(new Date());
					connect.setCosignFlag((short) 1);
					connect.setExpMemberSuccessFlag((short) 1);
					parcelInfoService.addParcelConnection(connect);
					System.out.println("--------取件包裹记录结束------");
					
					//清空查询列表缓存
					cleanQueryInfo("takeParcelIdsCacheByMemberId", String.valueOf(taskInfo.getCoopCompId()));
				} else {
					throw new ServiceException("openapi.BroadcastListServiceImpl.broadcastCreateParcel.007", "任务未分配给收派员");
				}
			}
			return parcelId;
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.toFinishTask.001", "取件任务不存在，添加新包裹失败");
		}
	}
	
	/**
	 * 
	 * @Method: deleteParcel 
	 * @param id 包裹id
	 * @param taskId 任务id
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#deleteParcel(java.lang.Long, java.lang.Long)
	 */
	@Override
	public String deleteParcel(Long id, Long taskId) {
		if(PubMethod.isEmpty(id)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteParcel.001", "请选择要取消的包裹");
		}
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteParcel.002", "取件任务不存在，取消包裹失败");
		}
		//查询任务信息
		ParTaskInfo taskInfo = cacheTaskInfo(String.valueOf(taskId));
		if(!PubMethod.isEmpty(taskInfo)) {
			if(task_status_finish.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.deleteParcel.003", "任务已完成");
			}
			/*if(task_status_cancel.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.deleteParcel.004", "任务已取消");
			}
			if(task_status_untake.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.deleteParcel.005", "任务未分配给收派员");
			}*/
			if(task_status_delete.equals(taskInfo.getTaskStatus())) {
				throw new ServiceException("openapi.BroadcastListServiceImpl.deleteParcel.006", "任务已删除");
			}
			//更改任务表中包裹数量重量
			updateTaskInfo(taskInfo, id);
			//删除包裹信息和包裹地址
			parParcelinfoMapper.deleteParcel(id);
			parParceladdressMapper.deleteParceladdress(id);
			robParcelRelationMapper.deleteByParcelId(id);
			//清理缓存
			ehcacheService.remove("takeParcelIdsCacheByTaskId", String.valueOf(taskInfo.getTaskId()));
			ehcacheService.remove("takeParcelIdsCacheByMemberId", String.valueOf(taskInfo.getTaskId()));
			ehcacheService.removeAll("robParcelRelationCache");
			//清空查询列表缓存
			cleanQueryInfo("takeParcelIdsCacheByMemberId", String.valueOf(taskInfo.getCoopCompId()));
			/*if(task_status_distribute.equals(taskInfo.getTaskStatus())) {
				//查询任务记录表
				ParTaskDisposalRecord memberTask = cacheDisposalRecord(taskInfo);
				if(!PubMethod.isEmpty(memberTask)) {
				} else {
					throw new ServiceException("openapi.BroadcastListServiceImpl.deleteParcel.005", "任务未分配给收派员");
				}
			}*/
			return null;
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.deleteParcel.002", "取件任务不存在，取消包裹失败");
		}
	}
	
	private void updateTaskInfo(ParTaskInfo taskInfo, Long id) {
		//查询包裹信息
		ParParcelinfo parcelInfo = parcelInfoService.getParcelInfoById(id);
		BigDecimal weight = taskInfo.getParEstimateWeight();
		Byte num = taskInfo.getParEstimateCount();
		if(!PubMethod.isEmpty(parcelInfo)) {
			weight = weight.subtract(parcelInfo.getChareWeightForsender());
			num--;
		}
		taskInfo.setParEstimateWeight(weight);
		taskInfo.setParEstimateCount(num);
		parTaskInfoMapper.updateParTaskInfoBySelective(taskInfo);
		ehcacheService.put("TakeTaskCacheTaskInfo", String.valueOf(taskInfo.getTaskId()), taskInfo);
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点id查询站点对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午4:23:41</dd>
	 * @param compId 站点id
	 * @return
	 * @throws ServiceException
	 * @since v1.0
	 */
	private BasCompInfo getComp(Long compId) throws ServiceException {
		BasCompInfo basCompInfo = null;
		basCompInfo = ehcacheService.get("compCache", PubMethod.isEmpty(compId)?null:compId.toString(), BasCompInfo.class);
		if(basCompInfo == null) {
			basCompInfo = basCompInfoMapper.findById(compId);
		}
		return basCompInfo;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>收派员id查询收派员对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 上午11:34:58</dd>
	 * @param memberId 收派员id
	 * @return
	 * @throws ServiceException
	 * @since v1.0
	 */
	private MemberInfo getMember(Long memberId) throws ServiceException {
		MemberInfo memberinfo = memberInfoService.getMemberInfoById(memberId);
		return memberinfo;
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>手机号查询member对象</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-24 下午4:55:16</dd>
	 * @param memberId
	 * @return
	 * @throws ServiceException
	 * @since v1.0
	 */
	private MemberInfo getMemberByMemberPhone(String memberPhone) throws ServiceException {
		MemberInfo memberInfo = null; 
		memberInfo = this.ehcacheService.get("memberInfochCache", memberPhone, MemberInfo.class);
		if(PubMethod.isEmpty(memberInfo)){
			memberInfo = this.memberInfoMapper.queryMemberByMemberPhone(memberPhone);
			this.ehcacheService.put("memberInfochCache", memberPhone, memberInfo);
		}
	
		return memberInfo;
	}

	/**
	 * @Method: broadcastRestartForTask 
	 * @param taskId
	 * @return 
	 * @see net.okdi.api.service.BroadcastListService#broadcastRestartForTask(java.lang.Long) 
	*/
	@Override
	public String broadcastRestartForTask(Long taskId) throws ServiceException {
		ParTaskInfo parTaskInfo = cacheTaskInfo(String.valueOf(taskId));
		List<Long> parcelList = parParcelinfoMapper.queryParcelInfoListByTaskId(taskId);
		Byte count = null;
		BigDecimal weight = new BigDecimal(0);
		Long createUserId = null;
		if(!PubMethod.isEmpty(parTaskInfo)) {
			count = parTaskInfo.getParEstimateCount();
			weight = parTaskInfo.getParEstimateWeight();
			createUserId = parTaskInfo.getCreateUserId();
			parTaskInfo.setTaskStatus(task_status_delete);
			parTaskInfoMapper.updateByPrimaryKeySelective(parTaskInfo);
			ehcacheService.put("TakeTaskCacheTaskInfo", String.valueOf(parTaskInfo.getTaskId()), parTaskInfo);
			cleanQueryTaskList(parTaskInfo.getCreateUserId());
		}
		ParTaskDisposalRecord taskRecord = new ParTaskDisposalRecord();
		taskRecord.setTaskId(taskId);
		taskRecord.setDisposalType(task_status_delete);
		parTaskDisposalRecordMapper.updateByTaskIdSelective(taskRecord);
		ehcacheService.removeAll("TakeTaskCacheTaskRecord");
		ehcacheService.removeAll("TakeTaskCacheTaskRecordResult");
		ehcacheService.removeAll("TakeTaskCacheQueryResult");
		Long broadcastId = broadcastMapper.getBroadcastIdByTaskId(taskId);
		RobBroadcastInfo broadcastInfo = queryBroadcastInfo(broadcastId);
		if(!PubMethod.isEmpty(broadcastInfo)) {
			broadcastInfo.setId(broadcastId);
			broadcastInfo.setTaskId(0l);
			broadcastInfo.setQuotationId(0l);
			broadcastInfo.setTotalWeight(weight.doubleValue());
			broadcastInfo.setTotalCount(count==null?0:Integer.parseInt(count.toString()));
			String addresseeAddress = getAddrStr(parcelList);
			broadcastInfo.setAddresseeAddress(addresseeAddress);
			broadcastMapper.updateByPrimaryKeySelective(broadcastInfo);
			ehcacheService.put("robBroadcastCache", String.valueOf(broadcastId), broadcastInfo);
			ehcacheService.remove("robBroadcastCache", String.valueOf(createUserId));
			this.broadcastRestart(broadcastId);
		}
		return null;
	}
	
	private String getAddrStr(List<Long> parcelList) {
		StringBuffer sb = new StringBuffer();
		if(!PubMethod.isEmpty(parcelList)) {
			ParParceladdress address = null;
			int num = 0;
			for(Long id : parcelList) {
				address = parcelInfoService.getParParceladdressById(id);
				if(!PubMethod.isEmpty(address) && num < 3) {
					if(PubMethod.isEmpty(sb)) {
						sb.append(this.subbStringProvience(address.getAddresseeAddress()));
					} else {
						sb.append("、"+this.subbStringProvience(address.getAddresseeAddress()));
					}
				}
				num++;
			}
		}
		return sb.toString();
	}
	
	private String subbStringProvience(String addressName){
    	String result = null; 
    	for (int i = 0;i<list.size();i++){
    		if(addressName.contains(list.get(i).toString())){
    			result = list.get(i).toString();
    		}
    	}
    	if(result == null){
    		return "";
    	}else{
    		return result;
    	}
    }
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单指定站点或收派员时修改广播状态</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-26 下午4:35:32</dd>
	 * @param taskId  任务id
	 * @param broadcastId  广播id
	 * @return
	 * @throws ServiceException
	 * @since v1.0
	 */
	@Override
	public String finishBroadcastByCreateTask(Long taskId, Long broadcastId,Long memberId) throws ServiceException {
		if(PubMethod.isEmpty(broadcastId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.finishBroadcastByCreateTask.001", "请选择要完成的广播数据");
		}
		if(PubMethod.isEmpty(taskId)) {
			throw new ServiceException("openapi.BroadcastListServiceImpl.finishBroadcastByCreateTask.002", "无取件任务id,广播完成失败");
		}
		//广播id查询广播对象
		RobBroadcastInfo rbci = queryBroadcastInfo(broadcastId);
		
		if(!PubMethod.isEmpty(rbci)) {
			//更改完成状态
			rbci.setTaskId(taskId);
			rbci.setBroadcastStatus(broadcast_status_finish);
			robBroadcastInfoMapper.updateByPrimaryKeySelective(rbci);
			
			Long qid = robBroadcastInfoMapper.getQuotationIdByBroadcastIdAndMemberId(broadcastId, memberId);
			logger.debug("张梦楠=============选中收派员推送调用   memberId = "+memberId+" qid = "+qid);
			this.sendMessageToFailedCouriser(broadcastId, qid==null?0:qid);
			//放入缓存
			ehcacheService.put("robBroadcastCache", String.valueOf(broadcastId), rbci);
			//清空广播列表缓存
			ehcacheService.remove("robBroadcastCache", String.valueOf(rbci.getLoginMemberId()));
			return "ok";
		} else {
			throw new ServiceException("openapi.BroadcastListServiceImpl.cancelBroadcast.002", "广播不存在，完成失败");
		}
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>清除查询取件任务列表缓存</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-27 下午2:29:25</dd>
	 * @since v1.0
	 */
	private void cleanQueryTaskList(Long memberId) {
		String queryresult = ehcacheService.get("TakeTaskCacheRecordResultByMemberId", String.valueOf(memberId), String.class);
		List<String> querylist = null;
		if(!PubMethod.isEmpty(queryresult)) {
			querylist = JSON.parseArray(queryresult, String.class);
		}
		if(!PubMethod.isEmpty(querylist)) {
			ehcacheService.remove("TakeTaskCacheRecordResultByMemberId", querylist);
			
		}
		ehcacheService.removeAll("TakeTaskCacheQueryResult");
		ehcacheService.removeAll("TakeTaskCacheTaskProcessResult");
	}
	
	
	private void cleanQueryInfo(String cacheName, String key) throws ServiceException {
		String queryresult = ehcacheService.get(cacheName, key, String.class);
		List<String> querylist = null;
		if(!PubMethod.isEmpty(queryresult)) {
			querylist = JSON.parseArray(queryresult, String.class);
		}
		if(!PubMethod.isEmpty(querylist)) {
			ehcacheService.remove(cacheName, querylist);
		}
	}
	
	public void sendMessageToFailedCouriser(Long broadcastId,Long qId){
		List <Map<String,Object>> failedList = new ArrayList<Map<String,Object>>();
		//TODO
		failedList = robQuotationInfoMapper.getRobQuotationInfoByBroadcastId(broadcastId);
		if (failedList!=null&&failedList.size()>0) {
			for (int i = 0; i < failedList.size(); i++) {
				if (!qId.equals(Long.parseLong(failedList.get(i).get("id").toString()))) {
					logger.debug("内部调用推送方法");
					sendNoticeService.bidFailToExp(broadcastId, Long.parseLong(failedList.get(i).get("memberId")==null?"0":failedList.get(i).get("memberId").toString()), null);
				}
			}
		}
	}
	public void sendMessageToCancelCouriser(Long broadcastId,Long qId){
		List <Map<String,Object>> failedList = new ArrayList<Map<String,Object>>();
		//TODO
		failedList = robQuotationInfoMapper.getRobQuotationInfoByBroadcastId(broadcastId);
		if (failedList!=null&&failedList.size()>0) {
			for (int i = 0; i < failedList.size(); i++) {
				if (!qId.equals(Long.parseLong(failedList.get(i).get("id").toString()))) {
					sendNoticeService.cancelBroadcastToMember(broadcastId, Long.parseLong(failedList.get(i).get("memberId")==null?"0":failedList.get(i).get("memberId").toString()), null);
				}
			}
		}
	}
}