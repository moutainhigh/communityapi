/**  
 * @Project: openapi
 * @Title: ContactTest.java
 * @Package net.okdi.test
 * @Description: TODO
 * @author amssy
 * @date 2014-10-30 下午05:56:06
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.service.ContactService;
import net.okdi.api.service.CourierService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;


/**
 * @ClassName ContactTest
 * @Description TODO
 * @author amssy
 * @date 2014-10-30
 * @since jdk1.6
 */

public class ContactTest extends BaseTest {
@Autowired
ContactService contactService;
@Autowired
CourierService courierService;
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
	@org.junit.Test
	public void addGroupTag(){
		System.out.println(
		contactService.addGroupTag(13800760894816256l, "张梦楠测试分组2")
		);
	}
	/**
	 * @Method: getContactGroup 
	 * @Description:获得操作用户自己的联系人分组信息
	 * @param memberId 操作用户memberid
	 * @return
	 * @author mengnan.zhang
	 * @date 2014-10-28 下午05:18:45
	 * @since jdk1.6
	 */
	@org.junit.Test
	public void getContactGroup(){
		System.out.println(contactService.getContactGroup(13800760894816256l));
	}
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
	@org.junit.Test
	public void  deleteContactGroup(){
		contactService.deleteContactGroup(13800760894816256l, 13871112921023488l);
	}
    /**
     * @Method: getAddressGroup 
     * @Description: 获得登陆人所持有的地址种类
     * @param memberId 操作人Id
     * @return json
     * @author mengnan.zhang
     * @date 2014-10-28 下午05:20:00
     * @since jdk1.6
     */
	@org.junit.Test
	public void getAddressGroup(){
		System.out.println(contactService.getAddressGroup(13800760894816256l));
	}
	/**
	 * @Method: addAddressTag 
	 * @Description: 用户添加所持有的联系人通讯地址分类
	 * @param tagName 标签名
	 * @param memberId 登陆人Id
	 * @return
	 * @since jdk1.6
	 */
	@org.junit.Test
	public void addAddressGroup(){
		contactService.addAddressGroup("常用地址", null);
		contactService.addAddressGroup("办公", null);
		contactService.addAddressGroup("家庭", null);
		contactService.addAddressGroup("其他", null);
		contactService.addAddressGroup("未分类", null);
	
	}
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
	@org.junit.Test
	public void deleteAddressGroup(){
		contactService.deleteAddressGroup(13871369940894720l, 13800760894816256l);
		
	}
    /**
     * @Method: addContactCommunicationTag 
     * @Description: 用户添加联系人的通讯标签
     * @param tagName 要添加的通讯标签名称
     * @param memberId 用户ID
     * @param tagType 添加的通讯方式标签属于的打类型  0：联系电话 1：电子邮件 2：电子邮件
     * @return json
     * @since jdk1.6
     */
	@org.junit.Test
	public void addContactCommunicationTag(){
		contactService.addContactCommunicationTag("常用邮箱", null, 7, (short)1);
		contactService.addContactCommunicationTag("备用邮箱", null, 7, (short)1);
		contactService.addContactCommunicationTag("微信", null, 7, (short)2);
		contactService.addContactCommunicationTag("qq", null, 7, (short)2);
		contactService.addContactCommunicationTag("阿里旺旺", null, 7, (short)2);
		contactService.addContactCommunicationTag("办公", null, 7, (short)0);
		contactService.addContactCommunicationTag("家庭", null, 7, (short)0);
		contactService.addContactCommunicationTag("公司", null, 7, (short)0);
		contactService.addContactCommunicationTag("备用", null, 7, (short)0);
		contactService.addContactCommunicationTag("未分类", null, 7, (short)0);

	}
	/**
	 * @Method: deleteCommunicationTag 
	 * @Description: 删除指定的通讯类型标签(级联删除)
	 * @param tagId 要删除的标签ID
	 * @param memberId 操作人ID
	 * @return
	 * @since jdk1.6
	 */
	@org.junit.Test
    public void deleteCommunicationTag(){
		contactService.deleteCommunicationTag(13871882543039488l, 13800760894816256l);
	}
	 /**
     * @Method: getContactConmunicationTypeList 
     * @Description: 获得联系人列表
     * @param memberId 操作人ID
     * @return json
     * @since jdk1.6
     */
	@org.junit.Test
    public void getContactConmunicationTypeList(){
		System.out.println(contactService.getContactConmunicationTypeList(13800760894816256l));
	}
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
	@org.junit.Test
    public void addContactBaseMsg(){/*
		for(int i = 0;i<10;i++){
		System.out.println(contactService.addContactBaseMsg(
				13800760894816256l, 
				"张梦楠", 
				(short)0, 
				"13161340623", 
				null, 
				null, 
				null, 
				new Date().getTime()+"", 
				"背影嵌入逆光", 
				null,
				null,
				"z"));
		}
	*/}
    /**
     * @Method: addContact 
     * @Description: 添加联系人
     * @param memberId 操作人ID
     * @param msg 联系人所有信息
     * @return
     * @since jdk1.6
     */
	@org.junit.Test
    public void addContact(){/*
		String msg = "{\"addr\": [{\"addressId\": \"1100210\",\"detaileDisplay\": \"北京市海淀区城区\",\"detailedAddress\": \"田村路138号\",\"latitude\": \"120.121\",\"longitude\": \"120.121\",\"zipCode\": \"11111\",\"addressTagId\":\"13871351189472256\"},{\"addressId\": \"1100210\",\"detaileDisplay\": \"北京市海淀区城区\",\"detailedAddress\": \"田村路138号\",\"latitude\": \"120.121\",\"longitude\": \"120.121\",\"zipCode\": \"11111\",\"addressTagId\":\"13871351189472256\"},{\"addressId\": \"1100210\",\"detaileDisplay\": \"北京市海淀区城区\",\"detailedAddress\": \"田村路138号\",\"latitude\": \"120.121\",\"longitude\": \"120.121\",\"zipCode\": \"11111\",\"addressTagId\":\"13871351189472256\"}],\"comm\": [{\"isDefault\": \"0\",\"num\": \"13161340623\",\"tagId\": \"13908027187135488\"},{\"isDefault\": \"0\",\"num\": \"572354908@QQ.COM\",\"tagId\": \"13908027187135488\"},{\"isDefault\": \"0\",\"num\": \"572354908\",\"tagId\": \"13908027187135488\"}],\"createUserId\": \"1314\",\"defaultPhone\": \"13161340623\",\"defaultAddressId\": \"11211\",\"gender\": \"0\",\"groupTag\": \"123\",\"birthday\": \"123123123123\",\"contactDetaileDisplay\": \"北京市海淀\",\"contactDetailedDddress\": \"田村路18号\",\"reMark\": \"备注\",\"nameAbbr\": \"z\",\"contactName\": \"张梦楠\"}";
		System.out.println(contactService.addContact(3111399625520007l, msg));;
	*/}
   
    /**
     * @Method: getContactMsg 
     * @Description: 获得联系人全部信息
     * @param contactId 联系人ID
     * @param memberId 操作人ID
     * @return json
     * @since jdk1.6
     */@org.junit.Test
    public void getContactMsg(){
    	System.out.println( contactService.getContactMsg(14165701517115392l, 3111399625520007l));;
     }
    /**
     * @Method: moveContact 
     * @Description:  联系人移动分组
     * @param memberId  操作人ID
     * @param tagId  目标分组
     * @param contactId 联系人ID
     * @return json
     * @since jdk1.6
     */
     @org.junit.Test
    public void moveContact(){
    	 List list = new ArrayList();
    	 Map map = new HashMap();
    	 map.put("success", true);
    	 map.put("data", list);
    	System.out.println( JSON.toJSON(map));
     }
    /**
     * @Method: getMemberContactList 
     * @Description: 查询个人全部联系人
     * @param memberId 用户ID
     * @return json
     * @since jdk1.6
     */
     @org.junit.Test
    public void getMemberContactList(){
    	 System.out.println(contactService.getMemberContactList(13800760894816256l,null));
     }
    /**
     * @Method: getMemberContactListByPage 
     * @Description: 获得用户所有联系人列表
     * @param memberId
     * @param pageSize
     * @param CurrentPage
     * @return
     * @since jdk1.6
     */@org.junit.Test
    public void getMemberContactListByPage(){
    	 System.out.println(contactService.getMemberContactListByPage(13800760894816256l, 2, 9, true,null));
    	 
     }
    /**
     * @Method: deleteMemberConcate 
     * @Description: 删除联系人
     * @param memberId 操作人ID
     * @param ContactId 要删除的联系人ID
     * @return json
     * @since jdk1.6
     */
     @org.junit.Test
    public void deleteMemberConcate(){}
    /**
     * @Method: addContactAddressInfo 
     * @Description: 批量添加联系人地址信息
     * @return
     * @author amssy
     * @date 2014-10-29 下午03:26:52
     * @since jdk1.6
     */
     @org.junit.Test
    public void addContactAddressInfo(){
//    	 #{item.id},
//         #{item.contactId},
//         #{item.addressTagId},
//         #{item.addressId},
//         #{item.detailedAddress},
//         #{item.detaileDisplay},
//         #{item.longitude},
//         #{item.latitude},
//         #{item.zipCode},
//         #{item.isDefault},
//         #{item.createTime}
    	 List list = new ArrayList();
         Map <String,Object>map = new HashMap<String,Object>();
        	 map.put("id", 6l);
        	 map.put("addressTagId", 13871351189472256l);
        	 map.put("contactId", 13872143007745024l);
        	 map.put("detailedAddress", 13161340623l);
        	 map.put("addressId", 10);
        	 map.put("detaileDisplay", "0");
        	 map.put("longitude", "111");
        	 map.put("latitude", "222");
        	 map.put("zipCode", "100000");
        	 map.put("isDefault", "0");
        	 map.put("createTime", new Date());
        	 Map <String,Object>map2= new HashMap<String,Object>();
        	 map2.put("addressId", 10);
        	 map2.put("id", 5l);
        	 map2.put("addressTagId", 13871351189472256l);
        	 map2.put("contactId", 13872143007745024l);
        	 map2.put("detailedAddress", 13161340623l);
        	 map2.put("detaileDisplay", "0");
        	 map2.put("longitude", "111");
        	 map2.put("latitude", "222");
        	 map2.put("zipCode", "100000");
        	 map2.put("isDefault", "0");
        	 map2.put("createTime", new Date());
        	 list.add(map);
        	 list.add(map2);
    	 try{contactService.addContactAddressInfo(list);
    	 }catch(Exception e ){e.printStackTrace();}
     }
    /**
     * @Method: deleteAddressInfoByTagId 
     * @Description: 删除联系人指定标签地址信息/删除指定标签所有地址信息
     * @param tagId
     * @return
     * @date 2014-10-29 下午04:14:51
     * @since jdk1.6
     */@org.junit.Test
    public void deleteAddressInfoByTagId(){}
     /**
      * @Method: addCommTag 
      * @Description: 批量添加通讯信息
      * @param list
      * @return
      * @author amssy
      * @date 2014-10-29 下午04:48:43
      * @since jdk1.6
      */
     @org.junit.Test
    public void addCommInfo(){
     List list = new ArrayList();
     Map <String,Object>map = new HashMap<String,Object>();
    	 map.put("id", 1l);
    	 map.put("tagId", 13908027187135488l);
    	 map.put("contactId", 13872143007745024l);
    	 map.put("num", 13161340623l);
    	 map.put("isDefault", "0");
    	 map.put("createTime", new Date());
    	 Map <String,Object>map2= new HashMap<String,Object>();
    	 map2.put("id", 2l);
    	 map2.put("tagId", 13908027187135488l);
    	 map2.put("contactId", 13872143007745024l);
    	 map2.put("num", 13161340623l);
    	 map2.put("isDefault", "0");
    	 map2.put("createTime", new Date());
    	 list.add(map2);
    	 try {
    		 contactService.addCommInfo(list);	
		} catch (Exception e) {
         e.printStackTrace();		}
    	
     }
    /**
     * @Method: deleteMemberContactComm 
     * @Description: 查询通讯信息|删除标签时删除指定用户的标签下所有信息|删除用户时删除用户的所有通讯信息
     * @param map
     * @author mengnan.zhang
     * @date 2014-10-29 下午07:33:14
     * @since jdk1.6
     */
     @org.junit.Test
    public void deleteMemberContactComm() {
    contactService.deleteMemberContactComm(13908027187135488l, 13872143007745024l);	 
     }
     
     
    public List<Map<String, Object>> createaddContactAddressInfo(Long contactId,String contactInfo,String key){
     	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
     	JSONObject  obj = JSON.parseObject(contactInfo);
     	JSONArray array = obj.getJSONArray(key);
     	for(int i = 0;i<array.size();i++){
     		Map<String, Object> map = array.getJSONObject(i);
     		map.put("id", IdWorker.getIdWorker().nextId());
     		map.put("contactId", 13872143007745024l);
     		list.add(map);
     	}
     	return list;
     }
    @Test
    public void getContactCommInfo(){
    	System.out.println(contactService.getContactCommInfo(13872143007745024l));
    }
    @Test
    public void queryNearMember(){
    	System.out.println(courierService.queryNearMember(116.260846, 39.935841, 11000206l, 11000206l, null, 0, 5)
    	);
    }
    @Test
    public void updateContactInfo(){
    	String msg = "{\"addr\": [{\"addressId\": \"1100210\",\"detaileDisplay\": \"北京市海淀区城区\",\"detailedAddress\": \"田村路138号\",\"latitude\": \"120.121\",\"longitude\": \"120.121\",\"zipCode\": \"11111\",\"addressTagId\":\"13871351189472256\"},{\"addressId\": \"1100210\",\"detaileDisplay\": \"北京市海淀区城区\",\"detailedAddress\": \"田村路138号\",\"latitude\": \"120.121\",\"longitude\": \"120.121\",\"zipCode\": \"11111\",\"addressTagId\":\"13871351189472256\"},{\"addressId\": \"1100210\",\"detaileDisplay\": \"北京市海淀区城区\",\"detailedAddress\": \"田村路138号\",\"latitude\": \"120.121\",\"longitude\": \"120.121\",\"zipCode\": \"11111\",\"addressTagId\":\"13871351189472256\"}],\"comm\": [{\"isDefault\": \"0\",\"num\": \"13161340623\",\"tagId\": \"13908027187135488\"},{\"isDefault\": \"0\",\"num\": \"572354908@QQ.COM\",\"tagId\": \"13908027187135488\"},{\"isDefault\": \"0\",\"num\": \"572354908\",\"tagId\": \"13908027187135488\"}],\"createUserId\": \"1314\",\"defaultPhone\": \"13161340623\",\"defaultAddressId\": \"11211\",\"gender\": \"0\",\"groupTag\": \"123\",\"birthday\": \"123123123123\",\"contactDetaileDisplay\": \"北京市海淀\",\"contactDetailedDddress\": \"田村路18号\",\"reMark\": \"备注\",\"nameAbbr\": \"z\",\"contactName\": \"张梦楠\"}";
    	System.out.println(contactService.updateContactInfo(13800760894816256l, 13872143007745024l, msg));
    }
    @Test
    public void CourierService(){
    	System.out.println(courierService.autoSuggest(13818449455742976l, 39.968606, 116.357401).get(0).get("compId"));
    }
    @Test
    public void saveBaseOnLineMemmber(){
    	courierService.saveOnLineMember(1811399885919546l, "顺丰快递", 13820806923812864l, "田村顺丰分部", 1811399885919546l, "张梦楠测试收派员", "15652370002", 116.221542,39.938716, "1");
    }
    @Test
    public void updateBaseOnLineMember(){
    	courierService.updateOnLineMember(32l, 20.2, 20.2);
    }
    @Test
    public void deleteBaseOnLineMember(){
    	courierService.deleteOnLineMember(32l);
    }
    @Test
    public void queryNearComp(){
    	try {
    		System.out.println(courierService.queryNearComp(116.221542,39.938716, 11l, 11l,0.5,1520l));	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @Test
    public void autosuggest(){
    	System.out.println(courierService.autoSuggest(13999092876705792l, 40.033162,116.239678));
    }
    @Test
    public void a(){
    	System.out.println(contactService.getContactConmunicationTypeList(3111399625520007l));
    }
}
