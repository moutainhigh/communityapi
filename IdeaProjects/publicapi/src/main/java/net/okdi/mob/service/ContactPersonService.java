/**  
 * @Project: mob
 * @Title: ExpParGatewayService.java
 * @Package net.okdi.mob.service
 * @Description: TODO
 * @author chuanshi.chai
 * @date 2014-11-4 上午9:17:48
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ExpParGatewayService
 * @Description TODO
 * @author chuanshi.chai
 * @date 2014-11-4
 * @since jdk1.6
*/
public interface ContactPersonService {
	
	
	/**
	 * 获取联系人信息
	 * @Method: 
	 * @Description: 
	 * @param memberId	
	 * @param groupId	分组Id
	 * @param newGroupName	分组名
	 * @return
	 * @author xiangwei.liu
	 * @date 2014-11-4 下午6:54:16
	 * @since jdk1.6
	 */
	 public String getContactMsg(Long contactId,Long memberId);
	
	/**
	 * 增加标签
	 * @Method: addGroupTag 
	 * @Description: TODO
	 * @param memberId
	 * @param groupName
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午10:21:38
	 * @since jdk1.6
	 */
	String addGroupTag(Long memberId,String groupName);
	/**
	 * 获取分组
	 * @Method: getContactGroup 
	 * @Description: TODO
	 * @param memberId
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午10:21:49
	 * @since jdk1.6
	 */
	String getContactGroup(Long memberId);
	
	String  deleteContactGroup(Long memberId,Long tagId);
	/**
	 * 添加联系人
	 * @Method: addContactMob 
	 * @Description: TODO
	 * @param memberId
	 * @param json
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午10:39:38
	 * @since jdk1.6
	 */
	String  addContactMob(Long memberId,String json,MultipartFile[] myfiles);
	
	String deleteMemberConcate(Long memberId,Long contactId);
	String getContactConmunicationTypeList(Long memberId);
    String getMemberContactList(Long memberId,String groupId);
	String addPhoneTagType(String tagName,Long memberId);
	String deleteCommunicationTagType(Long tagId,Long memberId);
	String addContactToGroup(Long contactId,Long tagId);
	String deleteContactGroupRela(Long contactId,Long groupId);
	String updateContactInfo(Long memberId,Long contactId,String msg, MultipartFile[] myfiles);
	String updateMemberGroupName(Long memberId, Long groupId, String newGroupName);
	String getMemberGroupJDW(Long memberId);
	String getAddressTypeList(Long memberId);
	String getGroups(Long memberId);
	
	String updateCommTagName(Long memberId,String tagName,Long tagId);
	String queryAllContact(Long memberId);
	String moveContact(Long memberId,Long tagId,Long contactId);
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>类似微信的移动联系人</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-24 上午11:50:33</dd>
	 * @param contactIds
	 * @param groupId
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	String moveContactAsWx(String contactIds,Long groupId,Long memberId);
     /**
      * <dt><span class="strong">方法描述:</span></dt><dd>判断联系人是否已经添加</dd>
      * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
      * <dt><span class="strong">时间:</span></dt><dd>2014-12-27 下午01:43:03</dd>
      * @param memberId
      * @param json
      * @return
      * @since v1.0
      */
	 public String judgeHasAlreadyAdd(Long memberId,String json);
	 /**
	  * <dt><span class="strong">方法描述:</span></dt><dd>获取联系人地址信息</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午03:37:01</dd>
	  * @param contactId
	  * @return
	  * @since v1.0
	  */
	 public String getContactAddressInfo(Long contactId);
	 
	 
}
