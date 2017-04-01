/**  
 * @Project: openapi
 * @Title: ContactController.java
 * @Package net.okdi.api.controller
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-10-27 下午03:29:14
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.controller;



import net.okdi.api.service.ContactService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.Constant;
import net.okdi.core.util.PubMethod;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/contact")
public class ContactController extends BaseController{
	@Autowired
	private ContactService contactService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>好递网添加联系人</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午02:41:27</dd>
	 * @param memberId 操作人ID
	 * @param json 联系人详细信息串
	 * @return {"data":[],"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>ContactController.addContactWeb.001 - 操作人ID不能为空</dd>
	 * @since v1.0
	 */
	@RequestMapping(value="/add/okdi")
	@ResponseBody
	public String addContactWeb(Long memberId,String json){
		String param = "memberId="+memberId+"&json="+json;
		if(PubMethod.isEmpty(memberId)){return paramsFailure("ContactController.addContactWeb.001", "操作人ID不能为空");}
		try{
			return contactService.addContact(memberId, json,0l);
		}catch(RuntimeException re){
			return this.jsonFailure(re);
		}
		}
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>手机端添加联系人</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午02:44:05</dd>
     * @param memberId 操作人ID
	 * @param json 联系人详细信息串
     * @return {"data":[],"success":true}
     * <dt><span class="strong">异常:</span></dt>
     * <dd>ContactController.addContactMob.002 -  操作人ID不能为空</dd>
     * @since v1.0
     */
	@RequestMapping(value="/add/mob")
	@ResponseBody
	public String addContactMob(Long memberId,String json){
		if(PubMethod.isEmpty(memberId)){return paramsFailure("ContactController.addContactMob.002", "操作人ID不能为空");}
		try{
			return contactService.addContact(memberId, json,0l);
		}catch(RuntimeException re){
			return this.jsonFailure(re);
		}
		}
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>获得登陆人的组信息标签</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午02:46:14</dd>
     * @param memberId 登陆人Id
     * @return {"data":{"groupList":[{"createTime":1414668231000,"createUserId":13800760894816256,"groupId":13871215943615488,"groupName":"张梦楠测试分组2"},{"createTime":1414811744000,"createUserId":13800760894816256,"groupId":13908836990845952,"groupName":"张梦楠测试分组2"},{"createTime":1415932434000,"createUserId":13800760894816256,"groupId":14202619182973952,"groupName":"张梦楠测试分组2"},{"createTime":1415932456000,"createUserId":13800760894816256,"groupId":14202624913441792,"groupName":"张梦楠测试分组2"}]},"success":true}
     * createTime 创建时间  createUserId创建人ID	groupId组ID	groupName组名称
     * <dt><span class="strong">异常:</span></dt>
     * <dd>ContactController.getContactGroup.003 -  操作人ID不能为空</dd>
     * @since v1.0
     */
	@RequestMapping(value="/getGroups")
	@ResponseBody
	public String getContactGroup(Long memberId){
		if(PubMethod.isEmpty(memberId)){return paramsFailure("ContactController.getContactGroup.003", "操作人ID不能为空");}
		try{
			return contactService.getContactGroup(memberId);
		}catch(RuntimeException re){
			return this.jsonFailure(re);
		}	
	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加联系人分组信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午02:47:05</dd>
	 * @param memberId 操作人ID||组添加后的组的持有人
	 * @param groupName 组名字
	 * @return {"data":[],"success":true}
     * <dt><span class="strong">异常:</span></dt>
     * <dd>ContactController.addGroupTag.004 -  操作人ID不能为空</dd>
	 * @since v1.0 
	 */
	@RequestMapping(value="/addGroup")
	@ResponseBody
		public String addGroupTag(Long memberId,String groupName ){
		if(PubMethod.isEmpty(memberId)){return paramsFailure("ContactController.addGroupTag.004", "操作人ID不能为空");}	
		if(PubMethod.isEmpty(groupName)){return paramsFailure("ContactController.addGroupTag.005", "组名字不能为空");}
		try{
				return contactService.addGroupTag(memberId, groupName);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
		}

	}
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除联系人分组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午02:47:36</dd>
	 * @param memberId 操作人ID||组的持有人
	 * @param tagId 组ID
	 * @return {"data":[],"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>ContactController.deleteContactGroup.005 -  操作人ID不能为空</dd>
	 * @since v1.0
	 */
	@RequestMapping(value="/deleteGroup")
	@ResponseBody
		public String  deleteContactGroup(Long memberId,Long tagId){
		if(PubMethod.isEmpty(memberId)){return paramsFailure("ContactController.addGroupTag.004", "操作人ID不能为空");}	
		try{
				return contactService.deleteContactGroup(memberId, tagId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
		}
	}  
       /**
        * <dt><span class="strong">方法描述:</span></dt><dd>获得联系人通讯标签列表</dd>
        * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
        * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午02:49:28</dd>
        * @param memberId 操作人ID
        * @return {"data":{"email":[{"customName":"常用邮箱","dataType":1,"id":14186061332481024},{"customName":"备用邮箱","dataType":1,"id":14186061377569792}],"phone":[{"dataType":0,"id":1},{"customName":"办公","dataType":0,"id":14186061385696256},{"customName":"家庭","dataType":0,"id":14186061385696257},{"customName":"公司","dataType":0,"id":14186061389628416},{"customName":"备用","dataType":0,"id":14186061389628417},{"customName":"未分类","dataType":0,"id":14186061393822720}],"so":[{"customName":"微信","dataType":2,"id":14186061381501952},{"customName":"qq","dataType":2,"id":14186061381501953},{"customName":"阿里旺旺","dataType":2,"id":14186061381501954}]},"succes":true}
        * customName自定义标签名称	 dataType数据类型 0代表联系电话  1电子邮件 2社交账号	id标签ID
        * <dt><span class="strong">异常:</span></dt>
        * <dd>ContactController.getContactConmunicationTypeList.006 -  操作人ID不能为空</dd>
        * @since v1.0
        */
		@RequestMapping(value="/getContactConmunicationTypeList")
		@ResponseBody
	   public String getContactConmunicationTypeList(Long memberId){
			if(PubMethod.isEmpty(memberId)){return paramsFailure("ContactController.getContactConmunicationTypeList.005", "操作人ID不能为空");}
			try{
				return contactService.getContactConmunicationTypeList(memberId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
		}
	   }
       /**
        * <dt><span class="strong">方法描述:</span></dt><dd>获得联系人详细信息</dd>
        * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
        * <dt><span class="strong">时间:</span></dt><dd>2014-11-13 下午04:04:48</dd>
        * @param contactId 联系人ID
        * @param memberId 操作人ID
        * @return {"data":{"addr":[{"addressId":11000206,五级地址ID"addressTagId":1,地址标签ID"contactId":14451584278462464,联系人ID"detailDisplay":"北京-海淀区",地址信息省市县"detailedAddress":"田村路 118号",地址详细信息"id":14451584282656768,地址信息主键"isDefault":0,是否是默认地址0是1否"lat":0,纬度"lng":0经度}],"comm":[{"comm_code":"1",通讯号码"comm_tag_id":1,通讯标签ID"contact_id":14451584278462464,联系人ID"id":14186061332481024,通讯信息主键"is_default":0是否为默认信息0是1否}],"groupList":[],联系人组信息里面存的是组ID"memberContact":{"birthday":1416882160000,生日毫秒数"casMemberId":0,联系人的casmemberiD"contactDetaileDisplay":"北京-海淀区",联系人常用地址省市县"contactDetailedAddress":"田村路 118号",联系人常用地址"contactName":"111111",联系人姓名"contactPhone":"13111111111",联系人电话"createTime":1416882160000,联系人基本信息创建时间毫秒数"createUserId":3435117,联系人用户ID"dataMode":0,数据类型（暂时没用上）"erpCustomerId":0,联系人erp"gender":-1,性别0男1女-1没选"id":14451584278462464,联系人基本信息主键"nameAbbr":"",联系人首字母"remark":""联系人信息备注}},"success":true成功标示}
        * @since v1.0
        */
		@RequestMapping(value="/getContact")
		@ResponseBody
	   public String getContactMsg(Long contactId,Long memberId){
			try{
				return contactService.getContactMsg(contactId, memberId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
		}
	   }
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>联系人移动分组</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-11-13 下午04:05:41</dd>
	   * @param memberId 联系人持有人ID
	   * @param tagId 目标分组ID
	   * @param contactId 联系人ID
	   * @return {"data":[],"success":true}
	   * @since v1.0
	   */
		@Deprecated
		@RequestMapping(value="/moveContact")
		@ResponseBody
	   public String moveContact(Long memberId,Long tagId,Long contactId){
		   try{
				return contactService.moveContact(memberId, tagId, contactId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
		}
	   }
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>添加地址标签</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>mengna.zhang</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午10:06:37</dd>
		 * @param tagName 标签名
		 * @param memberId 用户名
		 * @return {"id":1231322121,"success":true}
		 * @since v1.0
		 */
		@RequestMapping(value="/addAddressTag")
		@ResponseBody
		public String addAddressTag(String tagName,Long memberId){
			try{
				return contactService.addAddressGroup(tagName, memberId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
		}
	}
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>删除联系人地址信息标签同时删除所有数据</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:55:02</dd>
		 * @param tagId
		 * @param memberId
		 * @return {"success":true}
		 * @since v1.0
		 */
		@RequestMapping(value="/deleteAddressTag")
		@ResponseBody
		public String deleteAddressGroup(Long tagId,Long memberId){
			try{
				return contactService.deleteAddressGroup(tagId, memberId,true);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
		}
	}
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>添加电话类型标签</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:52:19</dd>
		 * @param tagName 标签名
		 * @param memberId 操作ID
		 * @return {"id"123123123:,"success":true}
		 * @since v1.0
		 */
		@RequestMapping(value="/addPhoneTagType")
		@ResponseBody
        public String addPhoneTagType(String tagName,Long memberId){
        	try{
				return contactService.addContactCommunicationTag(tagName, memberId,Constant.TAGTYPE_PHONE_7, Constant.TAGTYPE_PHONE);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
        }
   }
       /**
        * <dt><span class="strong">方法描述:</span></dt><dd>删除通讯标签类型</dd>
        * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
        * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:49:57</dd>
        * @param tagId 标签ID
        * @param memberId 操作人ID
        * @return {"data":{"contactId":117122726674432,"tagId":5},"success":true}
        * @since v1.0
        */
		@RequestMapping(value="/deleteCommunicationTagType")
		@ResponseBody
        public String deleteCommunicationTagType(Long tagId,Long memberId){
        	try{
				return contactService.deleteCommunicationTag(tagId, memberId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
        }
    }
    	/**
    	 * <dt><span class="strong">方法描述:</span></dt><dd>添加邮箱类型标签</dd>
    	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
    	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:49:20</dd>
    	 * @param tagName 标签名
    	 * @param memberId 用户ID
    	 * @return {"id":12313213213212,"success":true}
    	 * @since v1.0
    	 */
		@RequestMapping(value="/addEmailTagType")
		@ResponseBody
        public String addEmailTagType(String tagName,Long memberId){
        	try{
				return contactService.addContactCommunicationTag(tagName, memberId,Constant.TAGTYPE_EMAIL_13 ,Constant.TAGTYPE_EMAIL);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
        }
   }
        
        /**
         * <dt><span class="strong">方法描述:</span></dt><dd>添加社交类型标签</dd>
         * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
         * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:48:35</dd>
         * @param tagName 标签名
         * @param memberId 操作人ID
         * @return {"data":[],"success":true}
         * @since v1.0
         */
		@RequestMapping(value="/addSocialTagType")
		@ResponseBody
        public String addSocialTagType(String tagName,Long memberId){
        	try{
				return contactService.addContactCommunicationTag(tagName, memberId,Constant.TAGTYPE_SOCIAL_23 ,Constant.TAGTYPE_SOCIAL);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
        }
   }   
        /**
         * <dt><span class="strong">方法描述:</span></dt><dd>获得联系人列表不分页</dd>
         * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
         * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:47:27</dd>
         * @param memberId 操作人ID
         * @param groupId 组ID
         * @return {"data":{"PAGER":{"currentPage":1,当前页数"hasFirst":false,是否有前一页"hasLast":true,是否有后一页"hasNext":true,是否有下一页"hasPre":false,"items":[联系人列表信息{"birthday":1416882160000,联系人生日"casMemberId":0,联系人casMemberID"contactDetaileDisplay":"北京-海淀区",联系是地址信息省市县"contactDetailedAddress":"田村路 118号",联系人详细信息"contactName":"111111",联系人姓名"contactPhone":"13111111111",联系人电话"createTime":1416882160000,联系人信息创建时间"createUserId":3435117,联系人持有人ID"dataMode":0,信息类型（暂时没用）"erpCustomerId":0,联系人erpCustomerId"gender":-1,联系人性别0男1女-1没选"id":14451584278462464,联系人基本信息ID"nameAbbr":"",联系人姓名首字母"remark":""联系人备注}],"offset":0,"pageCount":1,总页数"pageSize":10,每页显示条数"total":5总条数}},"success":true}
         * @since v1.0
         */
		@RequestMapping(value="/getMemberContactList")
		@ResponseBody
       public String getMemberContactList(Long memberId,String groupId){
    	   try{
				return contactService.getMemberContactList(memberId,groupId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
       }
   }   
      /**
       * <dt><span class="strong">方法描述:</span></dt><dd>获得联系人列表分页</dd>
       * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
       * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:45:36</dd>
       * @param memberId 操作人ID
       * @param pageSize 每页容量
       * @param currentPage 当前页码 从0 开始
       * @param groupId 组ID 不传不按组分类 有值查当前组内联系人
       * @return {"data":{"PAGER":{"currentPage":1,当前页数"hasFirst":false,是否有前一页"hasLast":true,是否有后一页"hasNext":true,是否有下一页"hasPre":false,"items":[联系人列表信息{"birthday":1416882160000,联系人生日"casMemberId":0,联系人casMemberID"contactDetaileDisplay":"北京-海淀区",联系是地址信息省市县"contactDetailedAddress":"田村路 118号",联系人详细信息"contactName":"111111",联系人姓名"contactPhone":"13111111111",联系人电话"createTime":1416882160000,联系人信息创建时间"createUserId":3435117,联系人持有人ID"dataMode":0,信息类型（暂时没用）"erpCustomerId":0,联系人erpCustomerId"gender":-1,联系人性别0男1女-1没选"id":14451584278462464,联系人基本信息ID"nameAbbr":"",联系人姓名首字母"remark":""联系人备注}],"offset":0,"pageCount":1,总页数"pageSize":10,每页显示条数"total":5总条数}},"success":true}
       * @since v1.0
       */
		@RequestMapping(value="/getMemberContactListByPage")
		@ResponseBody
       public String getMemberContactListByPage(Long memberId,Integer pageSize,Integer currentPage,String groupId){
    	   try{
				return contactService.getMemberContactListByPage(memberId, pageSize, currentPage,true,groupId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
      }
  }
       /**
        * <dt><span class="strong">方法描述:</span></dt><dd>单个删除联系人</dd>
        * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
        * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:44:13</dd>
        * @param memberId 联系人持有人ID
        * @param ContactId 联系人ID
        * @return {"data":[],"success":true}
        * @since v1.0
        */
		@RequestMapping(value="/deleteMemberContact")
		@ResponseBody
       public String deleteMemberConcate(Long memberId,Long ContactId){
    	   try{
				return contactService.deleteMemberConcate(memberId, ContactId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
     }
  }
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>添加联系人到某组中</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:43:15</dd>
	   * @param contactId 联系人ID
	   * @param tagId 组ID
	   * @return {"data":[],"success":true}
	   * @since v1.0
	   */
		@RequestMapping(value="/addContactToGroup")
		@ResponseBody
       public String addContactToGroup(Long memberId,Long contactId,Long tagId){
    	   try{
				return contactService.addContactToGroup( memberId,contactId,tagId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
     }
  }
        /**
         * <dt><span class="strong">方法描述:</span></dt><dd>删除联系人组信息</dd>
         * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
         * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:42:29</dd>
         * @param contactId 联系人ID
         * @param groupId 组ID
         * @return {"data":{"contactId":117122726674432,"tagId":5},"success":true}
         * @since v1.0
         */
		@RequestMapping(value="/deleteContactGroup")
		@ResponseBody
       public String deleteContactGroupRela(Long contactId,Long groupId){
    	   try{
				return contactService.deleteContactGroupRela(contactId, groupId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
     }
  }
        /**
         * <dt><span class="strong">方法描述:</span></dt><dd>修改联系人</dd>
         * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
         * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:40:16</dd>
         * @param memberId 联系人持有用户ID 
         * @param contactId 联系人ID
         * @param msg 联系人详细信息
         * @return {"data":[],"success":true}
         * @since v1.0
         */
		@RequestMapping(value="/updateContactInfo")
		@ResponseBody
       public String updateContactInfo(Long memberId,Long contactId,String msg){
    	   try{
				return contactService.updateContactInfo( memberId, contactId, msg);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
     }
  }
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>修改组名称</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:39:14</dd>
		 * @param memberId 组持有人ID
		 * @param groupId 组ID
		 * @param newGroupName 新组名
		 * @return {"data":[],"success":true}
		 * @since v1.0
		 */
		
		@RequestMapping(value="/updateMemberGroupName")
		@ResponseBody
       public String updateMemberGroupName(Long memberId, Long groupId,
   			String newGroupName){
    	   try{
				return contactService.updateMemberGroupName( memberId,  groupId,
						 newGroupName);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
     }
  }
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>添加联系人基本信息</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午09:24:10</dd>
		 * @param createUserId 操作人ID
		 * @param contactName 联系人姓名
		 * @param gender 联系人性别 0 男 1女
		 * @param contactPhone 联系人常用电话
		 * @param contactAddressId 联系人常用地址ID 
		 * @param contactDetaileDisplay 联系人省市地址
		 * @param contactDetailedDddress 联系人详细地址（**街道**号）
		 * @param brithday 联系人生日 毫秒数
		 * @param remark 备注
		 * @param casMemberId 联系人在通行证系统中对应的memberID
		 * @param erpCustomerId 客户ERP主键ID
		 * @param nameAbbr 联系人首字母
		 * @return 返回主键   117480123456
		 * @since v1.0
		 */
		@RequestMapping(value="/addContactWeb")
		@ResponseBody
        public String addContactWeb(
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
		String nameAbbr){
    	   try{
				return contactService.addContactBaseMsg(createUserId, contactName, (short)-1, contactPhone, contactAddressId, contactDetaileDisplay, contactDetailedDddress, brithday, remark, casMemberId, null, null,0l);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
     }
  }
		
		/***
		 * <dt><span class="strong">方法描述:</span></dt><dd>获得联系人地址标签</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-13 上午10:38:52</dd>
		 * @param memberId
		 * @return {"data":[{"address_type_name":"常用地址",地址标签名称"id":14186057666397184地址标签ID},{"address_type_name":"办公",地址标签名称"id":14186057715680256地址标签ID},{"address_type_name":"家庭",地址标签名称"id":14186057715680256地址标签ID},{"address_type_name":"其他",地址标签名称"id":14186057719612416地址标签ID},{"address_type_name":"未分类",地址标签名称"id":14186057719612416地址标签ID}],"success":true}
		 * @since v1.0
		 */
		 @RequestMapping(value="/getAddressTypeList")
		 @ResponseBody
         public String getAddressTypeList(Long memberId){
    	   try{
				return contactService.getAddressGroup(memberId);
			}catch(RuntimeException re){
				return this.jsonFailure(re);
     }
			
  } 
		
	  /**
	   *
	   * <dt><span class="strong">方法描述:</span></dt><dd>批量删除联系人</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-11-13 上午10:38:32</dd>
	   * @param memberId 操作人ID
	   * @param contactIds 联系人ID串 , 隔开
	   * @return {"data":[],"success":true}
	   * @since v1.0
	   */
	  @RequestMapping(value="/batchDeleteContact")
	  @ResponseBody
	  public String batchDeleteContact(Long memberId,String contactIds){
		  try{
				return contactService.deleteMemberContactBatch(memberId, contactIds);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
	  }
 }
	 /**
	  * <dt><span class="strong">方法描述:</span></dt><dd>接单王获得联系人分组列表</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2014-11-22 下午05:58:06</dd>
	  * @param memberId
	  * @return {"data":{"groupList":[{"createTime":1416646791000,创建时间毫秒"groupId":1,组ID"groupName":"电商组"组名},{"createTime":1416647900000,创建时间毫秒"groupId":2,组ID"groupName":"企业组"组名},{"createTime":1416801085000,创建时间毫秒"groupId":3,组ID"groupName":"个人组"组名},{"createTime":1416806946000,创建时间毫秒"groupId":4,组ID"groupName":"其他"组名}]},"success":true}
	  * @since v1.0
	  */
	  @RequestMapping(value="/getMemberGroupJDW")
	  @ResponseBody
	  public String getMemberGroupJDW(Long memberId){
		  try{
				return contactService.getMemberGroupJDW(memberId);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
	  }
 }    
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>接单王判断联系人是否存在</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-11-22 下午05:58:38</dd>
	   * @param memberId
	   * @param contactName
	   * @return {"data":{"ifExist":false},"success":true}
	   * @since v1.0
	   */
	  @RequestMapping(value="/ifExitJDW")
	  @ResponseBody
	  public String ifExitJDW(Long memberId,String contactName){
		  try{
				return contactService.ifExitJDW(memberId, contactName);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
	  }
 }
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>修改联系人通讯标签</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-11-22 下午05:58:38</dd>
	   * @param memberId 操作人ID
	   * @param tagName 组名字
	   * @param tagId 组ID
	   * @return {"data":[],"success":true}
	   * @since v1.0
	   */
	  @RequestMapping(value="/updateCommTagName")
	  @ResponseBody
	  public String updateCommTagName(Long memberId,String tagName,Long tagId){
		  try{
				return contactService.updateCommTagName(tagId, memberId, tagName);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
	  }
 }
	  
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>修改联系人通讯标签</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-11-22 下午05:58:38</dd>
	   * @param memberId 操作人ID
	   * @param tagName 组名字
	   * @param tagId 组ID
	   * @return {"data":[],"success":true}
	   * @since v1.0
	   */
	  @RequestMapping(value="/queryAllContact")
	  @ResponseBody
	  public String queryAllContact(Long memberId){
		  try{
				return contactService.queryAllContact(memberId);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
	  }
 }    
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-12-24 上午11:07:55</dd>
	   * @param contactIds
	   * @param groupId
	   * @param memberId
	   * @return
	   * @since v1.0
	   */
	  @RequestMapping(value="/moveContactAsWx")
	  @ResponseBody
      public String moveContactAsWx(String contactIds,Long groupId,Long memberId){
		  try{
				return contactService.moveContactAsWx(contactIds, groupId, memberId);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
      }
}   
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-12-24 上午11:07:55</dd>
	   * @param contactIds
	   * @param groupId
	   * @param memberId
	   * @return
	   * @since v1.0
	   */
	  @RequestMapping(value="/judgeHasAlreadyAdd")
	  @ResponseBody
      public String judgeHasAlreadyAdd(Long memberId,String json){
		  try{
				return contactService. judgeHasAlreadyAdd( memberId, json);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
      } catch (BadHanyuPinyinOutputFormatCombination e) {
    	  return this.jsonFailure(e);
			}
      }  
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>修改联系人通讯标签</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2014-11-22 下午05:58:38</dd>
	   * @param memberId 操作人ID
	   * @param tagName 组名字
	   * @param tagId 组ID
	   * @return {"data":[],"success":true}
	   * @since v1.0
	   */
	  @RequestMapping(value="/updateMemberContactAddress")
	  @ResponseBody
	  public String updateMemberContactAddress(Long memberId,String tagName,Long tagId){
		  try{
				return contactService.updateMemberContactAddress(tagId, memberId, tagName);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
	  }
 }
	  
	  /**
	   * <dt><span class="strong">方法描述:</span></dt><dd>获得联系人地址串</dd>
	   * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	   * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 上午10:27:48</dd>
	   * @param contactId
	   * @return
	   * @since v1.0
	   */
	  @RequestMapping(value="/getContactAddressInfo")
	  @ResponseBody
	  public String getContactAddressInfo(Long contactId){
		  try{
				return jsonSuccess(contactService.getContactAddressInfo(contactId));
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
	  }
 }
	  
	  
}
