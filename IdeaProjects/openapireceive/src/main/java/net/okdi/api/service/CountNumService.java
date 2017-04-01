/**  
 * @Project: openapi
 * @Title: CountNumService.java
 * @Package net.okdi.api.service
 * @author amssy
 * @date 2015-4-28 下午6:28:59
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
 */
package net.okdi.api.service;

import java.util.Map;

/**
 * @author amssy
 * @version V1.0
 */
public interface CountNumService {

	/**
	 * 统计站点、收派员、好递超市、好递生活注册数量
	 * @param countType 1：统计总数 2：当前月注册总数 3：当前月每日注册总数
	 * @return
	 */
	public Object getRegisterNum(Integer countType);

}
