/**  
 * @Project: openapi
 * @Title: QueryTaskServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @author amssy
 * @date 2015-3-2 下午04:51:17
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.ParParcelconnectionMapper;
import net.okdi.api.service.QueryTaskService;
import net.okdi.core.common.page.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author amssy
 * @version V1.0
 */
@Service
public class QueryTaskServiceImpl implements QueryTaskService{
	@Autowired
	private ParParcelconnectionMapper parcelConnectionMapper;
	
	private static SerializerFeature[] features = {SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect};
	/**
	 * @Method: queryTask 
	 * @param netId
	 * @param compId
	 * @param memberId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return 
	 * @see net.okdi.api.service.QueryTaskService#queryTask(java.lang.Long, java.lang.Long, java.lang.Long, java.util.Date, java.util.Date, net.okdi.core.common.page.Page) 
	*/
	@Override
	public String queryTask(Long netId, Long compId, Long memberId,
			Date startTime, Date endTime, Page page) {
		    Integer num = this.getStartPage(page);
		    List<Map<String,Object>>detailList = new ArrayList<Map<String,Object>>();
		    List<Map<String,Object>>list = parcelConnectionMapper.queryTask(netId, compId, memberId, startTime, endTime, num,page.getPageSize());
		    Long memberId2 = -1l;
		    if(list!=null&&list.size()!=0){
		    	 memberId2 = Long.parseLong(list.get(0).get("memberId").toString());
		    	detailList = parcelConnectionMapper.queryTaskDetail(memberId2, startTime, endTime, 0,page.getPageSize());	
		    }
		    Long leftCount = parcelConnectionMapper.queryTaskCount(netId, compId, memberId, startTime, endTime, num);
		    Long rightCount = parcelConnectionMapper.queryTaskDetailCount(memberId2, startTime, endTime, 0);
//		    leftCount = getTotalPage(leftCount, 10);
//		    rightCount = getTotalPage(rightCount, 10);
		    Map<String,Object>dateMap = new HashMap<String,Object>(); 
		    dateMap.put("leftList", list);
		    dateMap.put("rightList", detailList);
		    dateMap.put("leftCount", leftCount);
		    dateMap.put("rightCount", rightCount);
		    String result = jsonSuccess(dateMap);
		    return result ;
	}

	/**
	 * @Method: queryTaskDetail 
	 * @param memberId
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @return 
	 * @see net.okdi.api.service.QueryTaskService#queryTaskDetail(java.lang.Long, java.util.Date, java.util.Date, net.okdi.core.common.page.Page) 
	*/
	@Override
	public String queryTaskDetail(Long memberId, Date startTime, Date endTime,
			Page page) {
        Integer num = this.getStartPage(page);
        List<Map<String,Object>>list = parcelConnectionMapper.queryTaskDetail(memberId, startTime, endTime, num,page.getPageSize());
        Long rightCount = parcelConnectionMapper.queryTaskDetailCount(memberId, startTime, endTime, 0);
//        rightCount = getTotalPage(rightCount, 10);
        Map <String,Object>map = new HashMap<String,Object>();
        map.put("rightList", list);
        map.put("rightCount", rightCount);
        String result = jsonSuccess(map);
        return result;
	}
	
	private Integer getStartPage(Page page){
		return (page.getCurrentPage()-1)*page.getPageSize();
	}
	private Long getTotalPage(Long totalCount, int pageSize){
		if(totalCount%pageSize == 0){
			return totalCount/pageSize;
		}else{
			return totalCount/pageSize +1;
		}
	}
    private String jsonSuccess(Object map) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != map) {
			allMap.put("data", map);
		}
		return JSON.toJSONString(allMap,features);
	}

}
