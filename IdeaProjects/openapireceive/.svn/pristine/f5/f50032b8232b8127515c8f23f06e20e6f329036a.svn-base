package net.okdi.apiV4.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
@Document(collection="parParcelMark")
public class ParParcelMark {
	//-----------------------2017-2-15-------------------------------------------//
	  private Long parId;//包裹id 
	  private String expWaybillNum; //包裹运单号	  
	  private String  code;   //取件标号--揽收码
	  private List<ContentHis> content;// 标记备注内容   如：时间   人   内容 
	  private Long markMemberId; //标记人id
	  private Short roleId;  //角色id
	  private String markName; // 标记人姓名
	  @DateTimeFormat
	  private Date markTime;//标记时间 	  
	  private String comment; // 内容
	  	  
	  
	public String getMarkName() {
		return markName;
	}
	public void setMarkName(String markName) {
		this.markName = markName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getMarkTime() {
		return markTime;
	}
	public void setMarkTime(Date markTime) {
		this.markTime = markTime;
	}
	public Long getParId() {
		return parId;
	}
	public void setParId(Long parId) {
		this.parId = parId;
	}
	public String getExpWaybillNum() {
		return expWaybillNum;
	}
	public void setExpWaybillNum(String expWaybillNum) {
		this.expWaybillNum = expWaybillNum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<ContentHis> getContent() {
		return content;
	}
	public void setContent(List<ContentHis> content) {
		this.content = content;
	}
	
	public Long getMarkMemberId() {
		return markMemberId;
	}
	public void setMarkMemberId(Long markMemberId) {
		this.markMemberId = markMemberId;
	}
	public Short getRoleId() {
		return roleId;
	}
	public void setRoleId(Short roleId) {
		this.roleId = roleId;
	}
	  
	 public void addContent(String contentStr, String name) {
        if (content == null) {
            content = new ArrayList<>();
        }
        ContentHis contentHis = new ContentHis(contentStr, name, System.currentTimeMillis());
        content.add(contentHis);
	 }  
}
