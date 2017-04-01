/**  
 * @Project: openapi
 * @Title: VO_queryTask.java
 * @Package net.okdi.task.vo
 * @Description: 查询条件
 * @author xpf
 * @date 2014-10-24 下午2:42:42
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.vo;

public class VO_queryTask {
	
	private String senderName; //发件人
	
	private String startTime; //开始日期
    
    private String endTime; //结束日期
    
    private String senderPhone; //发件人手机号
    
    private String spacetime; //持续时间

    private Byte taskStatus; //任务状态 0：待处理 1：已分配 2：已完成 3：已取消
    
    private Long memberId; //收派员

    private Long compId; //营业分部

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSenderPhone() {
		return senderPhone;
	}

	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}

	public String getSpacetime() {
		return spacetime;
	}

	public void setSpacetime(String spacetime) {
		this.spacetime = spacetime;
	}

	public Byte getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Byte taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}
}
