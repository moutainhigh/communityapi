package net.okdi.apiV4.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

//添加的新的快递员
@Document(collection="newMemberInfo")
public class NewMemberInfo {

	private Long uid;//主键
	
	private String newMemberName;//快递员名称
	
	private String compName;//公司名称
	
	private String newMemberPhone;//快递员手机号
	
	private List<String> listPracelIds;//包裹id集合


	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getNewMemberName() {
		return newMemberName;
	}

	public void setNewMemberName(String newMemberName) {
		this.newMemberName = newMemberName;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getNewMemberPhone() {
		return newMemberPhone;
	}

	public void setNewMemberPhone(String newMemberPhone) {
		this.newMemberPhone = newMemberPhone;
	}

	public List<String> getListPracelIds() {
		return listPracelIds;
	}

	public void setListPracelIds(List<String> listPracelIds) {
		this.listPracelIds = listPracelIds;
	}
	
}
