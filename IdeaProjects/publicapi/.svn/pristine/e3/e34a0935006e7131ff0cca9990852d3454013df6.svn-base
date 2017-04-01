/**
 * 
 */
package net.okdi.mob.controller;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.ContactPersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 联系人（目前在个人端使用）
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/expParGateway")
public class ContactPersonController extends BaseController {

	@Autowired
	private ContactPersonService contactPersonService;
	
	
	/**
	 * 获取联系人信息
	 * @Method: 
	 * @Description: 
	 * @param contactId	 联系人Id
	 * @param memberId	人员Id
	 * @return
	 * @author xiangwei.liu
	 * @date 2014-11-4 下午6:54:16
	 * @since jdk1.6
	 */
	@RequestMapping(value="/getContactMsg")
	@ResponseBody
   public String getContactMsg(Long contactId,Long memberId){
		if(PubMethod.isEmpty(contactId) || PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		return contactPersonService.getContactMsg(contactId,memberId);
	}
	
	 /**
     * 查询所有的联系人 或者 查询一个分组下的全部联系人
     * @Method: getMemberContactList 
     * @Description: 查询一个分组下的全部联系人
     * @param memberId 用户ID
     * @param groupId  分组ID 可以为空，为空表示查询所有的
     * @author xiangwei.liu
     * @return json
     * @since jdk1.6
     */
	@RequestMapping(value="/getMemberContactList")
	@ResponseBody
   public String getMemberContactList(Long memberId,String groupId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		return contactPersonService.getMemberContactList(memberId, groupId);
	}
	
	/**
	 * 查询联系人分组
	 * @Method: getMemberGroupJDW 
	 * @Description: 
	 * @param memberId	
	 * @return
	 * @author xiangwei.liu
	 * @date 2014-11-4 下午6:54:16
	 * @since jdk1.6
	 */
	 @RequestMapping(value="/getMemberGroupJDW")
	  @ResponseBody
	  public String getMemberGroupJDW(Long memberId){
		 if(PubMethod.isEmpty(memberId))
			return paramsFailure();
		 return contactPersonService.getMemberGroupJDW(memberId);
	 }
	/**
	 * 添加分组标签
	 * @Method: addGroupTag 
	 * @Description:  
	 * @param memberId	个人memberId
	 * @param groupName 分组名称
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午10:26:17
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/addGroupTag", method = {RequestMethod.POST })
	public String addGroupTag(Long memberId,String groupName,String contactIds){
		String result =  contactPersonService.addGroupTag(memberId,groupName);
		JSONObject jo = (JSONObject) JSON.parseObject(result).get("data");
		Long id = jo.getLong("id");
		if(!PubMethod.isEmpty(contactIds)){
			contactPersonService.moveContactAsWx(contactIds, id, memberId);
		}
		return result;
	}
	
	/**
	 * 得到分组（获得登陆人的组信息标签）
	 * @Method: getContactGroup 
	 * @Description:  
	 * @param memberId	个人memberId
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午10:26:17
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/getContactGroup")
	public String getContactGroup(Long memberId){
		return contactPersonService.getContactGroup(memberId);
	}
	/**
	 * 删除分组
	 * @Method: deleteContactGroup 
	 * @Description:  
	 * @param memberId	个人memberId
	 * @param tagId 标签Id
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午10:26:17
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/deleteContactGroup", method = {RequestMethod.POST })
	public String deleteContactGroup(Long memberId,Long tagId){
		return contactPersonService.deleteContactGroup(memberId,tagId);
	}
	
	/**
	 * 添加联系人信息
	 * @Method: addContact 
	 * @Description:  
	 * @param memberId	个人memberId
	 * @param json 格式为	{"comm":"[{\"isDefault\":0,\"tagId\":1,\"num\":\"8681377554\"},{\"isDefault\":0,\"tagId\":1,\"num\":\"8558886656\"}]","addr":"[{\"addressTagId\":1,\"detailedAddress\":\"海淀区玉泉路\",\"isDefault\":0,\"detaileDisplay\":\"北京市北京市区城区\",\"addressId\":\"310167157\"},
	 * {\"addressTagId\":1,\"detailedAddress\":\"刚好哈哈姐姐\",\"isDefault\":0,\"detaileDisplay\":\"北京市崇文区\",\"addressId\":\"11004001\"}]","nameAbbr":"C","contactName":"初号机","defaultPhone":"8681377554","createUserId":"2021400835739011"}
	 * @param myfiles 图片文件输入流
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午11:00:47
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/addContact", method = {RequestMethod.POST})
	public String addContact(Long memberId, String json,@RequestParam(value = "myfiles", required = false)  MultipartFile[] myfiles){
		System.out.println(memberId+" ___>> "+json);
		return contactPersonService.addContactMob(memberId,json,myfiles);
	}
	/**
	 * 删除联系人
	 * @Method: deleteMemberConcate 
	 * @Description:  
	 * @param memberId	个人memberId
	 * @param contactId		联系人Id
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午11:21:06
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/deleteMemberConcate", method = {RequestMethod.POST })
	public String deleteMemberConcate(Long memberId,Long contactId){
		return contactPersonService.deleteMemberConcate(memberId,contactId);
	}
	/**
	 * 获得通讯信息列表(获取联系人标签列表)
	 * @Method: getContactConmunicationTypeList 
	 * @Description:  
	 * @param memberId
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 上午11:34:42
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/getContactConmunicationTypeList", method = {RequestMethod.POST })
	public String getContactConmunicationTypeList(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		return contactPersonService.getContactConmunicationTypeList(memberId);
	}
	/**
	 * 增加手机号类型标签
	 * @Method: addPhoneTagType 
	 * @Description: 增加手机号类型标签
	 * @param tagName	标签名
	 * @param memberId	个人memberId
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 下午2:58:34
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/addPhoneTagType", method = {RequestMethod.POST })
	public String addPhoneTagType(String tagName,Long memberId){
		if(PubMethod.isEmpty(tagName)||PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		return contactPersonService.addPhoneTagType(tagName,memberId);
	}
	
	/**
	 * 删除手机号类型标签
	 * @Method: deleteCommunicationTagType 
	 * @Description:  
	 * @param tagId		标签Id	
	 * @param memberId	个人memberId
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 下午2:59:53
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/deleteCommunicationTagType", method = {RequestMethod.POST })
	public String deleteCommunicationTagType(Long tagId,Long memberId){
		if(PubMethod.isEmpty(tagId)||PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		return contactPersonService.deleteCommunicationTagType(tagId,memberId);
	}

	/**
	 * 添加联系人到某个分组(重复添加）
	 * @Method: addContactToGroup 
	 * @Description: TODO
	 * @param contactId	联系人Id
	 * @param tagId		标签Id
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 下午3:35:01
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/addContactToGroup", method = {RequestMethod.POST })
	public String addContactToGroup(Long contactId,Long tagId){
		return contactPersonService.addContactToGroup(contactId,tagId);
	}
	/**
	 * 取消某个联系人的分组
	 * @Method: deleteContactGroupRela 
	 * @Description: 
	 * @param contactId		联系人Id
	 * @param groupId	分组Id
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 下午3:34:50
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/deleteContactGroupRela", method = {RequestMethod.POST })
	public String deleteContactGroupRela(Long contactId,Long groupId){
		return contactPersonService.deleteContactGroupRela(contactId,groupId);
	}
	/**
	 * 修改联系人信息
	 * @Method: updateContactInfo 
	 * @Description: 修改的方式是先删除原有的，然后添加现在的，所以传输的时候要把所有现在的都传输过来
	 * @param memberId
	 * @param contactId	联系人Id
	 * @param msg	{"addr": [{"addressId": "1100210","detaileDisplay": "北京市海淀区城区","detailedAddress": "田村路138号","latitude": "120.121","longitude": "120.121","zipCode": "11111","addressTagId":"13871351189472256"},{"addressId": "1100210","detaileDisplay": "北京市海淀区城区","detailedAddress": "田村路138号","latitude": "120.121","longitude": "120.121","zipCode": "11111","addressTagId":"13871351189472256"},{"addressId": "1100210","detaileDisplay": "北京市海淀区城区","detailedAddress": "田村路138号","latitude": "120.121","longitude": "120.121","zipCode": "11111","addressTagId":"13871351189472256"}],"comm": [{"isDefault": "0","num": "13161340623","tagId": "13908027187135488"},{"isDefault": "0","num": "572354908@QQ.COM","tagId": "13908027187135488"},{"isDefault": "0","num": "572354908","tagId": "13908027187135488"}],"createUserId": "1314","defaultPhone": "13161340623","defaultAddressId": "11211","gender": "0","groupIds": "123","birthday": "123123123123","contactDetaileDisplay": "北京市海淀","contactDetailedDddress": "田村路18号","reMark": "备注","nameAbbr": "z","contactName": "张梦楠"}
	 * @param myfiles 图片输入流
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 下午4:47:21
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/updateContactInfo")
	public String updateContactInfo(Long memberId,Long contactId,String msg,@RequestParam(value = "myfiles", required = false)  MultipartFile[] myfiles){
		return contactPersonService.updateContactInfo(memberId,contactId,msg,myfiles);
	}
	/**
	 * 修改分组名
	 * @Method: updateMemberGroupName 
	 * @Description: 
	 * @param memberId	
	 * @param groupId	分组Id
	 * @param newGroupName	分组名
	 * @return
	 * @author chuanshi.chai
	 * @date 2014-11-4 下午6:54:16
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/updateMemberGroupName", method = {RequestMethod.POST })
	public String updateMemberGroupName(Long memberId, Long groupId, String newGroupName){
		return contactPersonService.updateMemberGroupName(memberId,groupId,newGroupName);
	}
	
	
	/***
	 * <dt><span class="strong">方法描述:</span></dt><dd>获得联系人地址标签</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-13 上午10:38:52</dd>
	 * @param memberId
	 * @return {"data":[{"address_type_name":"常用地址",地址标签名称"id":14186057666397184地址标签ID},{"address_type_name":"办公",地址标签名称"id":14186057715680256地址标签ID},{"address_type_name":"家庭",地址标签名称"id":14186057715680256地址标签ID},{"address_type_name":"其他",地址标签名称"id":14186057719612416地址标签ID},{"address_type_name":"未分类",地址标签名称"id":14186057719612416地址标签ID}],"success":true}
	 * @since v1.0
	 */
	 @RequestMapping(value="/getAddressTypeList")
	 @ResponseBody
     public String getAddressTypeList(Long memberId){
		return contactPersonService.getAddressTypeList(memberId);
	 }
	 
	 /**
	  * 更新标签名字
	  * @Method: updateCommTagName 
	  * @Description: 更新标签名字
	  * @param memberId	登录人Id
	  * @param tagName	tagName
	  * @param tagId	原tagId
	  * @return
	  * @author chuanshi.chai
	  * @date 2014-12-1 下午5:00:03
	  * @since jdk1.6
	  */
	 @RequestMapping(value="/updateCommTagName")
	 @ResponseBody
	 public String updateCommTagName(Long memberId, String tagName, Long tagId){
		 return contactPersonService.updateCommTagName(memberId,tagName, tagId);
	 }
	 /**
	  * 得到联系人的所有信息
	  * @Method: queryAllContact 
	  * @Description: 更新标签名字
	  * @param memberId	登录人Id
	  * @param tagName	tagName
	  * @param tagId	原tagId
	  * @return
	  * @author chuanshi.chai
	  * @date 2014-12-1 下午5:00:03
	  * @since jdk1.6
	  */
	 @RequestMapping(value="/queryAllContact")
	 @ResponseBody
	 public String queryAllContact(Long memberId){
		 return contactPersonService.queryAllContact(memberId);
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
	 @RequestMapping(value="/moveContact")
	 @ResponseBody
	 public String moveContact(Long memberId,Long tagId,Long contactId){
		 return contactPersonService.moveContact(memberId,tagId,contactId);
	 }
	 /**
	  * <dt><span class="strong">方法描述:</span></dt><dd>类似微信的编辑联系人分组</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2014-12-24 上午11:19:56</dd>
	  * @param contactIds
	  * @param groupId
	  * @param memberId
	  * @param groupName 分组名
	  * @return
	  * @since v1.0
	  */
	 @RequestMapping(value="/moveContactAsWx")
	  @ResponseBody
     public String moveContactAsWx(String contactIds,Long groupId,Long memberId,String groupName){
		  try{  contactPersonService.updateMemberGroupName(memberId, groupId, groupName);
				return contactPersonService.moveContactAsWx(contactIds, groupId, memberId);
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
				return contactPersonService.judgeHasAlreadyAdd( memberId, json);
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
	  @RequestMapping(value="/getContactAddressInfo")
	  @ResponseBody
     public String getContactAddressInfo(Long contactId){
		  try{
				return contactPersonService.getContactAddressInfo(contactId);
			}catch(RuntimeException re){
		  return this.jsonFailure(re);
     }
	 }
}