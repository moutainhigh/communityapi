/**  
 * @Project: openapi
 * @Title: CourierMapper.java
 * @Package net.okdi.api.dao
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-11-1 下午03:41:13
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CourierMapper
 * @Description TODO
 * @author mengnan.zhang
 * @date 2014-11-1
 * @since jdk1.6
 */
public interface CourierMapper {
/**
 * @Method: queryNearMember 
 * @Description: 查询附近收派员
 * @param map
 * @return
 * @author mengnan.zhang
 * @date 2014-11-5 下午01:42:45
 * @since jdk1.6
 */
public List<Map<String,Object>> queryNearMember(Map<String,Object>map);
/**
 * @Method: autoSuggest 
 * @Description: 自动分配
 * @param map
 * @return
 * @author mengnan.zhang
 * @date 2014-11-5 下午01:45:25
 * @since jdk1.6
 */
public List<Map> autoSuggest(Map<String,Object>map);
/**
 * @Method: updateOnLineMemember 
 * @Description: 修改人员经纬度
 * @param map
 * @author mengnan.zhang
 * @date 2014-11-6 下午03:20:26
 * @since jdk1.6
 */
public void updateOnLineMemember(Map <String,Object>map);
/**
 * @Method: deleteOnLineMember 
 * @Description: 删除在线人员
 * @param map
 * @author mengnan.zhang
 * @date 2014-11-6 下午03:23:04
 * @since jdk1.6
 */
public void deleteOnLineMember(Map<String,Object> map);
/**
 * @Method: queryNearComp 
 * @Description: 查询附近站点
 * @param map
 * @author mengnan.zhang
 * @date 2014-11-8 上午08:56:09
 * @since jdk1.6
 */
public List<Map<String,Object>> queryNearComp(Map<String,Object> map);

}

