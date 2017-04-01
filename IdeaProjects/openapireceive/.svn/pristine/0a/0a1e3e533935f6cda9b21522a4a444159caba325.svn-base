package net.okdi.apiV4.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="subJoinComp")
public class SubJoinComp {
	@Id
	private String _id;
	private Long id;
	private String compName; //代收点名称
	private String compMobile; //代收点电话(手机)
	private Long actorMemberId; //添加此代收点的收派员id
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getCompMobile() {
		return compMobile;
	}
	public void setCompMobile(String compMobile) {
		this.compMobile = compMobile;
	}
	public Long getActorMemberId() {
		return actorMemberId;
	}
	public void setActorMemberId(Long actorMemberId) {
		this.actorMemberId = actorMemberId;
	}
	
}