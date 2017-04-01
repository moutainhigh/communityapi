/**  
 * @Project: openapi
 * @Title: BasOnLineMemberMapper.java
 * @Package net.okdi.api.dao
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-11-4 上午09:45:01
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.dao;

import java.util.Map;

/**
 * @ClassName BasOnLineMemberMapper
 * @Description TODO
 * @author mengnan.zhang
 * @date 2014-11-4
 * @since jdk1.6
 */
public interface BasOnLineMemberMapper {
	public void insert(Map <String,Object>map);
	
	public Map<String,Object> selectLngLat(Long memberId);

}
