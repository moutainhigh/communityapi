/**  
 * @Project: openapi
 * @Title: MemberCourierService.java
 * @Package net.okdi.api.service
 * @author amssy
 * @date 2015-1-13 下午07:22:45
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service;

import net.okdi.api.entity.MemberCourier;

/**
 * @author amssy
 * @version V1.0
 */
public interface MemberCourierService {
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加御用快递哥</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午09:59:09</dd>
	 * @param memberCourier
	 * @return
	 * @since v1.0
	 */
	public String addMemberCourier(MemberCourier memberCourier);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改御用快递哥</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午10:00:14</dd>
	 * @param memberCourier
	 * @return
	 * @since v1.0
	 */
	public String updateMemberCourier(MemberCourier memberCourier);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除御用快递哥</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午10:02:00</dd>
	 * @param memberCourier
	 * @return
	 * @since v1.0
	 */
    public String deleteMemberCourier(MemberCourier memberCourier);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>查询御用快递哥列表</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午10:08:21</dd>
     * @param memberId
     * @return
     * @since v1.0
     */
    public String getMemberCourierList(String memberId);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>查询御用快递哥详情</dd>
     * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-14 上午10:09:51</dd>
     * @param memberId
     * @return
     * @since v1.0
     */
    public String getMemberCourierInfo(String memberId);
}
