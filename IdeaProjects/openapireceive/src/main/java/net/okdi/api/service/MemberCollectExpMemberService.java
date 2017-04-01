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
package net.okdi.api.service;

import java.util.Map;

import net.okdi.api.entity.MemberCollectExpMember;

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
	public void updateMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember);
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
	public void deleteMemberCollectExpMember(Long id);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据手机号判断是否完成</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午04:32:26</dd>
	 * @param memberId
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public Map<String,Object>  ifCollection(Long memberId,String expMemberPhone);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据手机号获取收派员信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午04:53:35</dd>
	 * @param expMemberPhone
	 * @return
	 * @since v1.0
	 */
	public String queryExpMemberInfo(String expMemberPhone,Double lng,Double lat);
}
