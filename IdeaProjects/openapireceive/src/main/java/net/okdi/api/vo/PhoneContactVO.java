/**  
 * @Project: openapi
 * @Title: PhoneContactVO.java
 * @Package net.okdi.api.vo
 * @author amssy
 * @date 2014-12-27 上午10:55:35
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.vo;

import java.util.List;

//手机联系人信息类
/**
 * @author amssy
 * @version V1.0
 */
public class PhoneContactVO {
    //手机联系人唯一标示
	private String id;
	//手机联系人姓名
	private String name;
	//手机联系人电话串
	private List <String>phone;
	//是否注册好递网  0未注册 1 已注册
	private String flag;
	//首字母
	private String letter;
	//联系人ID
	private Long contactId = 0l;
	//是否添加标示 0未添加 1 已添加
	@Deprecated
	private String isAdd = "0";
	
	
	public String getIsAdd() {
		return isAdd;
	}
	public void setIsAdd(String isAdd) {
		this.isAdd = isAdd;
	}
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List getPhone() {
		return phone;
	}
	public void setPhone(List phone) {
		this.phone = phone;
	}
	@Override
	public boolean equals(Object obj) {
        PhoneContactVO pvo = (PhoneContactVO) obj;
		return this.id.equals(pvo.getId());
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}

	

	

}
