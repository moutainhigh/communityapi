/**  
 * @Project: openapi
 * @Title: ContactService.java
 * @Package net.okdi.api.service
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-10-27 下午03:35:34
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * @ClassName ContactService
 * @Description 联系人业务服务层
 * @author mengnan.zhang
 * @date 2014-10-27
 * @since jdk1.6
 */

public interface ContactService {
	
    /**
     * 
     * @Method: addGroupTag 
     * @Description: 用户添自己加联系人分组标签
     * @param memberId 操作用户memberid
     * @param groupName 新添加的组信息
     * @return json
     * @author mengnan.zhang
     * @date 2014-10-28 下午05:17:03
     * @since jdk1.6
     */
	public String addGroupTag(Long memberId,String groupName );
	/**
	 * @Method: getContactGroup 
	 * @Description:获得操作用户自己的联系人分组信息
	 * @param memberId 操作用户memberid
	 * @return
	 * @author mengnan.zhang
	 * @date 2014-10-28 下午05:18:45
	 * @since jdk1.6
	 */
	public String getContactGroup(Long memberId);
	/**
	 * @Method: deleteContactGroup 
	 * @Description: 用户删除自己的联系人分组(同时删除标签下所有数据)
	 * @param memberId 操作用户ID
	 * @param tagId 分组Id
	 * @return
	 * @author mengnan.zhang
	 * @date 2014-10-28 下午05:19:07
	 * @since jdk1.6
	 */
	public String  deleteContactGroup(Long memberId,Long tagId);
    /**
     * @Method: getAddressGroup 
     * @Description: 获得登陆人所持有的地址种类
     * @param memberId 操作人Id
     * @return json
     * @author mengnan.zhang
     * @date 2014-10-28 下午05:20:00
     * @since jdk1.6
     */
	public String getAddressGroup(Long memberId);
	/**
	 * @Method: addAddressTag 
	 * @Description: 用户添加所持有的联系人通讯地址分类
	 * @param tagName 标签名
	 * @param memberId 登陆人Id
	 * @return
	 * @since jdk1.6
	 */
	public String addAddressGroup(String tagName,Long memberId);
    /**
	 * @Method: deleteAddressGroup 
	 * @Description: 删除用户持有联系人通讯地址类型标签(同时删除标签下所有数据)
	 * @param tagId  要删除的地址标签ID
	 * @param memberId 操作人memberID
	 * @return json
     * @author mengnan.zhang
     * @date 2014-10-28 下午07:27:33
     * @since jdk1.6
     */
	public String deleteAddressGroup(Long tagId,Long memberId);
    /**
     * @Method: addContactCommunicationTag 
     * @Description: 用户添加联系人的通讯标签
     * @param tagName 要添加的通讯标签名称
     * @param memberId 用户ID
     * @param tagType 添加的通讯方式标签属于的打类型  0：联系电话 1：电子邮件 2：电子邮件
     * @return json
     * @since jdk1.6
     */
	public String addContactCommunicationTag(String tagName,Long memberId,Integer tagType,Short dataType);
	/**
	 * @Method: deleteCommunicationTag 
	 * @Description: 删除指定的通讯类型标签(级联删除)
	 * @param tagId 要删除的标签ID
	 * @param memberId 操作人ID
	 * @return
	 * @since jdk1.6
	 */
    public String deleteCommunicationTag(Long tagId,Long memberId);
    /**
     * @Method: addContactBaseMsg 
	 * @Description: 添加联系人基本信息
	 * @param createUserId 创建联系人用户ID
	 * @param contactName 联系人姓名
	 * @param gender 性别
	 * @param contactPhone信息人默认电话
	 * @param contactAddressId 联系人默认地址ID
     * @param contactDetaileDisplay 联系人默认省市县地址
     * @param contactDetailedDddress 联系人默认详细地址
     * @param brithday 生日
     * @param remark 备注
     * @param casMemberId cas通行证memberId
     * @param erpCustomerId 客户ERP主键ID
     * @param nameAbbr 姓名拼音缩写
     * @return json
     * @since jdk1.6
     */
    public String addContactBaseMsg(
			Long createUserId,
			String contactName,
			short gender,
			String contactPhone,
			Long contactAddressId,
			String contactDetaileDisplay,
			String contactDetailedDddress,
			String brithday,
			String remark,
			Long casMemberId,
			Long erpCustomerId,
			String nameAbbr,
			Long contactId
			);
    /**
     * @Method: addContact 
     * @Description: 添加联系人
     * @param memberId 操作人ID
     * @param msg 联系人所有信息
     * @return
     * @since jdk1.6
     */
    public String addContact(Long memberId,String msg,Long contactId);
    /**
     * @Method: getContactConmunicationTypeList 
     * @Description: 获得联系人列表
     * @param memberId 操作人ID
     * @return json
     * @since jdk1.6
     */
    public String getContactConmunicationTypeList(Long memberId);
    /**
     * @Method: getContactMsg 
     * @Description: 获得联系人全部信息
     * @param contactId 联系人ID
     * @param memberId 操作人ID
     * @return json
     * @since jdk1.6
     */
    public String getContactMsg(Long contactId,Long memberId);
    /**
     * @Method: moveContact 
     * @Description:  联系人移动分组
     * @param memberId  操作人ID
     * @param tagId  目标分组
     * @param contactId 联系人ID
     * @return json
     * @since jdk1.6
     */
    public String moveContact(Long memberId,Long tagId,Long contactId);
    /**
     * @Method: getMemberContactList 
     * @Description: 查询个人全部联系人
     * @param memberId 用户ID
     * @return json
     * @since jdk1.6
     */
    public String getMemberContactList(Long memberId,String groupId);
    /**
     * @Method: getMemberContactListByPage 
     * @Description: 获得用户所有联系人列表
     * @param memberId
     * @param pageSize
     * @param CurrentPage
     * @return
     * @since jdk1.6
     */
    public String getMemberContactListByPage(Long memberId,Integer pageSize,Integer CurrentPage,boolean byPage,String groupId);
    /**
     * @Method: deleteMemberConcate 
     * @Description: 删除联系人
     * @param memberId 操作人ID
     * @param ContactId 要删除的联系人ID
     * @return json
     * @since jdk1.6
     */
    public String deleteMemberConcate(Long memberId,Long ContactId);
    /**
     * @Method: addContactAddressInfo 
     * @Description: 批量添加联系人地址信息
     * @return
     * @author mengnan.zhang
     * @date 2014-10-29 下午03:26:52
     * @since jdk1.6
     */
    public String addContactAddressInfo(List<Map<String,Object>>list);
    /**
     * @Method: deleteAddressInfoByTagId 
     * @Description: 删除联系人指定标签地址信息/删除指定标签所有地址信息
     * @param tagId
     * @return
     * @date 2014-10-29 下午04:14:51
     * @since jdk1.6
     */
    public String deleteAddressInfoByTagId(Long tagId,Long contactId);
     /**
      * @Method: addCommTag 
      * @Description: 批量添加通讯信息
      * @param list
      * @return
      * @author mengnan.zhang
      * @date 2014-10-29 下午04:48:43
      * @since jdk1.6
      */
    public String addCommInfo(List<Map<String,Object>>list);
    /**
     * @Method: deleteMemberContactComm 
     * @Description: 查询通讯信息|删除标签时删除指定用户的标签下所有信息|删除用户时删除用户的所有通讯信息
     * @param map
     * @author mengnan.zhang
     * @date 2014-10-29 下午07:33:14
     * @since jdk1.6
     */
    public void deleteMemberContactComm(Long tagId,Long contactId) ;
    /**
     * @Method: getContactCommInfo 
     * @Description: 查询联系人所有通讯方式信息
     * @param memberId
     * @return
     * @author mengnan.zhang
     * @date 2014-11-1 上午11:43:58
     * @since jdk1.6
     */
    public List<Map<String,Object>> getContactCommInfo(Long memberId);
    /**
     * @Method: getContactAddressInfo 
     * @Description: 查询联系人地址信息
     * @param contactId
     * @return
     * @author mengnan.zhang
     * @date 2014-11-1 下午02:04:40
     * @since jdk1.6
     */
    public List<Map<String,Object>> getContactAddressInfo(Long contactId);
    /**
     * @Method: addContactToGroup 
     * @Description: 添加联系人到指定分组
     * @param contactId
     * @param tagId
     * @return
     * @author mengnan.zhang
     * @date 2014-11-4 上午11:29:32
     * @since jdk1.6
     */
    public String addContactToGroup(Long memberId,Long contactId,Long tagId);
      
    /**
     * @Method: deleteContactGroupRela 
     * @Description: 删除联系人分组
     * @param contactId联系人ID
     * @param groupId分组ID
     * @return 
     * @author mengnan.zhang
     * @date 2014-11-4 下午12:58:30
     * @since jdk1.6
     */
    public String deleteContactGroupRela(Long contactId,Long groupId);

    /**
     * @Method: updateMemberGroupName 
     * @Description: 修改联系人
     * @param memberId
     * @param groupId
     * @param newGroupName
     * @return
     * @author mengnan.zhang
     * @date 2014-11-4 下午02:27:07
     * @since jdk1.6
     */
    public String updateMemberGroupName(Long memberId,Long groupId,String newGroupName);
    /**
     * @Method: updateContactInfo 
     * @Description: 修改联系人
     * @param contactId
     * @param msg
     * @return
     * @author mengnan.zhang
     * @date 2014-11-4 下午02:33:11
     * @since jdk1.6
     */
    public String updateContactInfo(Long memberId,Long contactId,String msg);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>批量删除联系人</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-13 上午10:02:54</dd>
     * @param memberId
     * @param ContactIds
     * @return
     * @since v1.0
     */
    public String deleteMemberContactBatch(Long memberId,String contactIds);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>接单王获得联系人分组</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-15 下午04:43:17</dd>
     * @param memberId
     * @return
     * @since v1.0
     */
    public String getMemberGroupJDW(Long memberId);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>接单王查询联系人是否重名</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-20 上午10:53:40</dd>
     * @param memberId 操作人ID
     * @param contactName 联系人ID
     * @return
     * @since v1.0
     */
    public String ifExitJDW(Long memberId,String contactName);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>修改通讯标签名称</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-1 下午02:51:41</dd>
     * @param tagId
     * @return
     * @since v1.0
     */
    public String updateCommTagName(Long tagId,Long memberId,String tagName);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>好递个人类似微信移动联系人</dd>
     * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-24 上午10:32:12</dd>
     * @param contactIds
     * @param groupId
     * @param memberId
     * @return
     * @since v1.0
     */
    public String moveContactAsWx(String contactIds,Long groupId,Long memberId);
    
    
    String queryAllContact(Long memberId);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>判断是否已经添加为我的联系人</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-26 下午03:40:33</dd>
     * @param memberId
     * @param json
     * @return
     * @since v1.0
     */
    public String judgeHasAlreadyAdd(Long memberId,String json)throws BadHanyuPinyinOutputFormatCombination;
     /**
      * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
      * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
      * <dt><span class="strong">时间:</span></dt><dd>2014-12-29 下午01:33:00</dd>
      * @param tagId
      * @param memberId
      * @param tagName
      * @return
      * @since v1.0
      */
    public String  updateMemberContactAddress(Long tagId,Long memberId,String tagName);

    /**
	 * @Method: deleteAddressGroup 
	 * @Description: 删除用户持有联系人通讯地址类型标签(同时删除标签下所有数据)
	 * @param tagId  要删除的地址标签ID
	 * @param memberId 操作人memberID
	 * @return json
     * @author mengnan.zhang
     * @date 2014-10-28 下午07:27:33
     * @since jdk1.6
     */
	public String deleteAddressGroup(Long tagId,Long memberId,Boolean Delete);
}



