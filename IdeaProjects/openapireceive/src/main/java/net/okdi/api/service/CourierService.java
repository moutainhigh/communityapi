/**  
 * @Project: openapi
 * @Title: CourierService.java
 * @Package net.okdi.api.service
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-11-1 下午03:23:02
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @ClassName CourierService
 * @Description TODO
 * @author mengnan.zhang
 * @date 2014-11-1
 * @since jdk1.6
 */
public interface CourierService {
	 /**
	  * @Method: queryNearMember 
	  * @Description: 查询附近收派员
	  * @param lng 经度
	  * @param lat 纬度
	  * @param townId 收件人乡镇ID
	  * @param streetId 发件人街道ID
	  * @param netId 网络Id
	  * @param sortFlag 排序Id
	  * @param howFast 搜索附近多远
	  * @return
	  * @author mengnan.zhang
	  * @date 2014-11-1 下午05:13:01
	  * @since jdk1.6
	  */
	 public List <Map<String,Object>> queryNearMember(Double lng,Double lat,Long townId,Long streetId,Long netId,Integer sortFlag,Integer howFast);
//	     #{id},
//		 #{netId},
//		 #{netName},
//		 #{compId},
//		 #{compName},
//		 #{memberId},
//		 #{memberName},
//		 #{memberMobile},
//		 #{lng},
//		 #{lat},
//		 #{modifyTime},
//		 #{createTime},
//		 #{memo},
//		 #{id}
	 public void saveOnLineMember(Long netId,
			 String netName ,
			 Long compId,
			 String compName,
			 Long memberId,
			 String memberName,
			 String memberMobile,
			 Double lng,
			 Double lat,
			 String memo);
	 public List<Map> autoSuggest(Long compId,Double lat,Double lng);
     /**
      * @Method: updateOnLineMember 
      * @Description: TODO
      * @param memberId
      * @param lng
      * @param lat
      * @author mengnan.zhang
      * @date 2014-11-6 下午03:24:15
      * @since jdk1.6
      */
	 public void updateOnLineMember(Long memberId, Double lng, Double lat);
	 /**
	  * @Method: deleteOnLineMember 
	  * @Description: TODO
	  * @param memberId
	  * @author mengnan.zhang
	  * @date 2014-11-6 下午03:24:39
	  * @since jdk1.6
	  */
	 public void deleteOnLineMember(Long memberId);
	 /**
	  * @Method: queryNearComp 
	  * @Description: TODO
	  * @return
	  * @author mengnan.zhang
	  * @date 2014-11-7 下午06:01:07
	  * @since jdk1.6
	  */
	 public  Map<String,Object> queryNearComp(Double lng,Double lat,Long recProvince,Long sendProvince,Double weight,Long netId);
	 
	 /**
	  * @Method: queryNearCompAndMember 
	  * @Description: 查询附近站点和收派员
	  * @param lng
	  * @param lat
	  * @param recProvince
	  * @param sendProvince
	  * @param weight
	  * @param netId
	  * @param sendTownId
	  * @param streetId
	  * @return
	  * @author mengnan.zhang
	  * @date 2014-11-10 上午11:31:30
	  * @since jdk1.6
	  */
	 public String queryNearCompAndMember(Double lng,Double lat,Long recProvince,Long sendProvince,Double weight,Long netId,Long sendTownId,Long streetId);
     /**
      * <dt><span class="strong">方法描述:</span></dt><dd>查询附近站点距离版</dd>
      * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang </dd>
      * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午10:11:48</dd>
      * @param lng
      * @param lat
      * @param recProvince
      * @param sendProvince
      * @param weight
      * @param netId
      * @param distance
      * @return
      * @since v1.0
      */
	 public Map<String,Object> queryNearComp(Double lng,Double lat,Long recProvince,Long sendProvince,Double weight,Long netId,Long distance);
	 
	 /**
	  * @Method: queryNearMember 
	  * @Description: 查询附近收派员
	  * @param lng 经度
	  * @param lat 纬度
	  * @param townId 收件人乡镇ID
	  * @param streetId 发件人街道ID
	  * @param netId 网络Id
	  * @param sortFlag 排序Id
	  * @param howFast 搜索附近多远
	  * @return
	  * @author mengnan.zhang
	  * @date 2014-11-1 下午05:13:01
	  * @since jdk1.6
	  */
	 public List <Map<String,Object>> queryNearMemberRob(double lng,double lat,Long townId,Long streetId,Long netId,int sortFlag,int howFast);

	 /**
	  * 查询所有在线收派员（用于运营数据）
	  * @return
	  */
	 public String getAllOnlineMember();
	 
	List<Map<String, Object>> queryNearMemberForWechat(Double lng, Double lat, Integer sortFlag, Integer howFast,Long assignNetId, String roleType);
}
