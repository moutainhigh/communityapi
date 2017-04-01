/**  
 * @Project: openapi
 * @Title: MemberCollectExpMemberService.java
 * @Package net.okdi.api.service
 * @author amssy
 * @date 2015-1-17 下午01:38:11
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service;

import net.okdi.mob.entity.MemberCollectExpMember;

/**
 * @author amssy
 * @version V1.0
 */
public interface MemberCollectExpMemberService {
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>addMemberCollectExpMember添加  保存收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午02:26:18</dd>
	 * @param memberCollectExpMember
	 * @since v1.0
	 */
	public String addMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember);
     /**
      * <dt><span class="strong">方法描述:</span></dt><dd>修改保存收派员信息</dd>
      * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
      * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午02:55:00</dd>
      * @param memberCollectExpMember
      * @since v1.0
      */
	public String updateMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>获得保存收派员详细信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午02:56:07</dd>
	 * @param id
	 * @return
	 * @since v1.0
	 */
	public String getMemberCollectExpMember(Long id,Double lng,Double lat);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>获取保存收派员列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午02:58:45</dd>
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public String getMemberCollectExpMemberList(Long memberId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除保存收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午02:59:47</dd>
	 * @param id
	 * @since v1.0
	 */
	public String deleteMemberCollectExpMember(Long id);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断收派员是否被收藏和注册</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午06:50:15</dd>
	 * @param memberId
	 * @param memberPhone
	 * @return
	 * @since v1.0
	 */
	public String ifCollectionAndRegister(Long memberId,String memberPhone,Double lng,Double lat);
}
