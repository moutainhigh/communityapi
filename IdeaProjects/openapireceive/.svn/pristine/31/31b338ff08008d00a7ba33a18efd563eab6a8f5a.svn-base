package net.okdi.apiV4.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.okdi.apiV4.dao.AttentionMapper;
import net.okdi.apiV4.entity.Attention;
import net.okdi.apiV4.entity.Card;
import net.okdi.apiV4.entity.CardComment;
import net.okdi.apiV4.entity.DynamicNotice;
import net.okdi.apiV4.entity.MemberInfo;
import net.okdi.apiV4.entity.ParParcelinfo;
import net.okdi.apiV4.entity.Vo_Card;
import net.okdi.apiV4.service.AttentionService;
import net.okdi.apiV4.service.CardService;
import net.okdi.core.common.page.PageUtil;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
@Service
public class AttentionServiceImpl implements AttentionService {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CardService cardService;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private AttentionMapper attentionMapper;
	
	public static final Log logger = LogFactory.getLog(AttentionServiceImpl.class);
	/**
	 * 查询关注通知列表
	 */
	@Override
	public List<Map<String, Object>> queryAttentionList(Long memberId) {
		Query query=Query.query(Criteria.where("toMemberId").is(memberId)).with(new Sort(Direction.DESC, "createTime"));
		List<Attention> attentionList = mongoTemplate.find(query, Attention.class);
		List<Map<String, Object>> list=new ArrayList<>();
		for (Attention attention : attentionList) {
			Map<String, Object> dataMap=new HashMap<>();
			Long fromMemberId = attention.getFromMemberId();
			dataMap.put("fromMemberId", fromMemberId);
			dataMap.put("fromMemberUrl", getHeadPath(fromMemberId));
			String userName = attentionMapper.findNameById(fromMemberId);
			dataMap.put("fromMemberName", userName);
			dataMap.put("isAttention", attention.getIsAttention());
			attention.setIsRead((short)2);//查询遍历列表时,将所有未读的数据变成已读
			list.add(dataMap);
		}
		Query query2=Query.query(Criteria.where("toMemberId").is(memberId).and("isRead").is((short)1));
		List<Attention> unReadList = mongoTemplate.find(query2, Attention.class);
		if(!PubMethod.isEmpty(unReadList)){//未读消息不为空,将未读状态改为已读
			mongoTemplate.upsert(query2, new Update().set("isRead", (short)2), Attention.class);
			logger.info("修改关注通知列表消息为已读状态");
		}
		return list;
	}
	/**
	 * 查询联系人(关注的人,站长角色附加查询人员管理)列表
	 */
	@Override
	public List<Map<String, Object>> queryContactList(Long memberId,Short roleId,Long compId,Integer currentPage,Integer pageSize) {
		List<Map<String, Object>> list=new ArrayList<>();
		if(roleId==1){
			//如果是站长角色,查询人员管理列表
			Map<String, Object> dataMap=new HashMap<>();
			List<MemberInfo> memberList = attentionMapper.queryEmployeeByCompId(compId);
				for(MemberInfo vom:memberList){
					Long memberId1 = vom.getMemberId();
					dataMap.put("memberId", memberId1);
//					//处理姓名
					String name="";
					if(PubMethod.isEmpty(vom.getMemberName())){
						if("0".equals(vom.getRoleId().toString())){
							name = "快递员"+"-"+vom.getMemberPhone();
						}else if("1".equals(vom.getRoleId().toString())){
							name = "站长"+"-"+vom.getMemberPhone();
						}else if("-1".equals(vom.getRoleId().toString())){
							name = "后勤"+"-"+vom.getMemberPhone();
						}else if("2".equals(vom.getRoleId().toString())){
							name = "店长"+"-"+vom.getMemberPhone();
						}else if("3".equals(vom.getRoleId().toString())){
							name = "店员"+"-"+vom.getMemberPhone();
						}
					}else {
						name = vom.getMemberName();
					}
					dataMap.put("headPath", getHeadPath(memberId1));
					dataMap.put("memberName", name);
					Card latestCard = cardService.findLatestCard(memberId1);
					dataMap.put("latestCard", latestCard);
					list.add(dataMap);
				}
				logger.info("站长角色,查询人员管理列表");
		}
		Query query=Query.query(Criteria.where("fromMemberId").is(memberId)).with(new Sort(Direction.DESC, "createTime"));
		    int skip = (currentPage-1)*pageSize;  
	        query.skip(skip);
	        query.limit(pageSize);  
		List<Attention> contactList = mongoTemplate.find(query, Attention.class);
		for (Attention attention : contactList) {
			Map<String, Object> map=new HashMap<>();
			Long toMemberId = attention.getToMemberId();
			map.put("memberId", toMemberId);
			map.put("headPath", getHeadPath(toMemberId));
			String userName = attentionMapper.findNameById(toMemberId);
			map.put("memberName", userName);
			Card latestCard = cardService.findLatestCard(toMemberId);
			map.put("latestCard", latestCard);
			list.add(map);
		}
		
		return list;
	}
	/**
	 * 取消关注
	 */
	@Override
	public void cancelAttention(Long fromMemberId, Long toMemberId) {
		Query query=Query.query(Criteria.where("fromMemberId").is(fromMemberId).and("toMemberId").is(toMemberId));
		Attention atten=mongoTemplate.findOne(query, Attention.class);
		if(PubMethod.isEmpty(atten)){
			throw new ServiceException("没有关注过此人,不能取消关注!");
		}
		mongoTemplate.remove(query, Attention.class);
		logger.info("取消关注-->fromMemberId:"+fromMemberId+"取消了对toMemberId:"+toMemberId+"的关注");
	}
	/**
	 * 查询动态通知列表
	 */
	@Override
	public List<Map<String, Object>> queryDynamicNotice(Long memberId,
			Integer currentPage, Integer pageSize) {
		List<Map<String, Object>> list=new ArrayList<>();
		Query query=Query.query(Criteria.where("toMemberId").is(memberId)).with(new Sort(Direction.DESC, "createTime"));
		    int skip = (currentPage-1)*pageSize;  
	        query.skip(skip);
	        query.limit(pageSize);  
	        //进入动态通知列表,将当前列表的所有动态标注为已读状态
	        mongoTemplate.upsert(query, new Update().set("isRead", (short)2), DynamicNotice.class);
		List<DynamicNotice> notices = mongoTemplate.find(query, DynamicNotice.class);
		for (DynamicNotice dynamicNotice : notices) {
			Map<String, Object> dataMap = new HashMap<>();
			Long cardId = dynamicNotice.getCardId();
			Vo_Card vo_Card = cardService.queryCardDetial(cardId, memberId);
			dataMap.put("card", vo_Card);
			dataMap.put("type", dynamicNotice.getType());//动态通知类型
			Long fromMemberId = dynamicNotice.getFromMemberId();
			dataMap.put("fromMemberId", fromMemberId);
			dataMap.put("fromMemberHeadUrl", getHeadPath(fromMemberId));
			String memberName = attentionMapper.findNameById(fromMemberId);
			dataMap.put("fromMemberName", memberName);
			Date createTime = dynamicNotice.getCreateTime();
			dataMap.put("createTime", createTime);
			//查询评论内容,根据评论时间区分一个人对同一帖子的多条评论
			Query query2=Query.query(Criteria.where("toMemberId").is(memberId).and("fromMemberId").is(fromMemberId).and("commentTime").is(createTime)); 
			CardComment cardComment = mongoTemplate.findOne(query2, CardComment.class);
			dataMap.put("comment", cardComment.getContent());
			list.add(dataMap);
		}
		return list;
	}

	/**
	 * 查询排行榜
	 * @param tagType 标签类型 1.派件数 2.接单数 3.外快
	 * @param filter 排序筛选条件 1 国内 2 省内 3 城市
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> leaderboard(Long memberId,Short tagType,Integer currentPage, Integer pageSize,Short filter) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		Map<String,Object> resMap = new HashMap<>();
			Calendar calendar = Calendar.getInstance();// 获取当前日期  
			Date now = calendar.getTime();
			calendar.add(Calendar.MONTH, 0);  
			calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天  
			calendar.set(Calendar.HOUR_OF_DAY, 0);  
			calendar.set(Calendar.MINUTE, 0);  
			calendar.set(Calendar.SECOND, 0);  
			calendar.set(Calendar.MILLISECOND, 0);  
			Date startTime = calendar.getTime();
			Query query=null;
			Map<String, Object> dataMap=null;
			Map<String, Object> myMemberInfo = attentionMapper.finCompInfoByMemberId(memberId);
			Object myCompName = myMemberInfo.get("compName");
			String mycompAddress1 = myMemberInfo.get("compAddress").toString().substring(0, 5);
			String mycompAddress = myMemberInfo.get("compAddress").toString().substring(0, 2);
		if(1==tagType){//派件数排行榜
			Criteria criteria = Criteria.where("signResult").in((short)1,(short)2,(short)3).and("signTime").gte(startTime).lt(now);
			 GroupBy groupBy = GroupBy.key("actualSendMember").initialDocument("{count:0}")  
			            .reduceFunction("function(key, values){values.count+=1;}"); 
			GroupByResults<Map> resultMap = mongoTemplate.group(criteria, "parParcelinfo", groupBy, Map.class);
			Criteria criteria2 = criteria.and("actualSendMember").is(memberId);//查询本人排行信息
			query = Query.query(criteria2);

			Iterator<Map> iterator = resultMap.iterator();
			while(iterator.hasNext()){
				Map<String, Object> map = iterator.next();
				dataMap = new HashMap<>();
				Long actualSendMember = (Long)map.get("actualSendMember");
				if(PubMethod.isEmpty(actualSendMember)){
					continue;
				}
				Map<String,Object> compInfo = attentionMapper.finCompInfoByMemberId(actualSendMember);
				if(PubMethod.isEmpty(compInfo)){//如果站点信息为空,说明该收派员处于离职状态,不对其进行排名
					continue;
				}
				Object compName = compInfo.get("compName");
				if(2==filter){//省内排行榜
					String province = compInfo.get("compAddress").toString().substring(0, 2);
					if(!province.endsWith(mycompAddress)){
						continue;
					}
				}else if(3==filter){//城市排行榜---由于地址格式不统一,有些结果不精确
					String province = compInfo.get("compAddress").toString().substring(0, 5);
					if(!province.endsWith(mycompAddress1)){
						continue;
					}
				}
				String memberName = attentionMapper.findNameById(actualSendMember);
				dataMap.put("memberId", actualSendMember);
				dataMap.put("memberName", memberName);
				dataMap.put("headImgPath", getHeadPath(actualSendMember));
				dataMap.put("compName", compName);
				dataMap.put("count", map.get("count"));
				dataList.add(dataMap);
			}
			logger.info("查询派件数排行榜tagType="+tagType);
		}
		if(2==tagType){//接单数排行榜
			Criteria criteria = Criteria.where("parcelEndMark").is("1").and("pickupTime").gte(startTime).lt(now);
			 GroupBy groupBy = GroupBy.key("actualTakeMember").initialDocument("{count:0}")  
			            .reduceFunction("function(key, values){values.count+=1;}"); 
			GroupByResults<Map> resultMap = mongoTemplate.group(criteria, "parParcelinfo", groupBy, Map.class);
			Criteria criteria2 = criteria.and("actualTakeMember").is(memberId);//查询本人排行信息
			query = Query.query(criteria2);
			Iterator<Map> iterator = resultMap.iterator();
			while(iterator.hasNext()){
				Map<String, Object> map = iterator.next();
				dataMap = new HashMap<>();
				Long actualTakeMember = (Long)map.get("actualTakeMember");
				if(PubMethod.isEmpty(actualTakeMember)){
					continue;
				}
				Map<String,Object> compInfo = attentionMapper.finCompInfoByMemberId(actualTakeMember);
				if(PubMethod.isEmpty(compInfo)){//如果站点信息为空,说明该收派员处于离职状态,不对其进行排名
					continue;
				}
				Object compName = compInfo.get("compName");
				if(2==filter){//省内排行榜
					String province = compInfo.get("compAddress").toString().substring(0, 2);
					if(!province.endsWith(mycompAddress)){
						continue;
					}
				}else if(3==filter){//城市排行榜---由于地址格式不统一,有些结果不精确
					String province = compInfo.get("compAddress").toString().substring(0, 5);
					if(!province.endsWith(mycompAddress1)){
						continue;
					}
				}
				String memberName = attentionMapper.findNameById(actualTakeMember);
				dataMap.put("memberId", actualTakeMember);
				dataMap.put("memberName", memberName);
				dataMap.put("headImgPath", getHeadPath(actualTakeMember));
				dataMap.put("compName", compName);
				dataMap.put("count", map.get("count"));
				dataList.add(dataMap);
			}
			logger.info("查询接单数排行榜tagType="+tagType);
		}
		if(3==tagType){//外快排行榜 TODO
			
		}
		 Collections.sort(dataList, new Comparator<Map<String, Object>>() {  
			public int compare(Map<String, Object> a, Map<String, Object> b) {
	        	   Double prestigeOne = Double.parseDouble(a.get("count").toString());  
	        	   Double prestigeTwo = Double.parseDouble(b.get("count").toString());
	        	   int k = (int) (prestigeTwo - prestigeOne);
	              return k  ;  
	           }  
	        });
		Map<String,Object> myself = new HashMap<String, Object>();
		myself.put("myMemberId", memberId);
		myself.put("myheadImgPath", this.getHeadPath(memberId));
		String memberName = attentionMapper.findNameById(memberId);
		myself.put("myMemberName", memberName);
		long count = mongoTemplate.count(query, ParParcelinfo.class);
		myself.put("myCompName", myCompName);
		myself.put("myCount", count);
		myself.put("myRank", dataList.indexOf(dataMap)+1);
		resMap.put("myself", myself);
		resMap.put("dataList", PageUtil.getPageData(currentPage, pageSize, dataList));
		return resMap;
	}
	
	private String getHeadPath(Long memberId) {
		return constPool.getHeadImgPath()+memberId+".jpg";
	}
	
  }




