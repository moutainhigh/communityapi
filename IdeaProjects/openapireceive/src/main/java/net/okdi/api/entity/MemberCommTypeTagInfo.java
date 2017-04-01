package net.okdi.api.entity;
/**
 * @ClassName MemberCommTypeTagInfo
 * @Description 用户通讯信息类型
 * @author mengnan.zhang
 * @date 2014-10-28
 * @since jdk1.6
 */
public class MemberCommTypeTagInfo {
    //主键
	private Long id;
    //持有人ID
    private Long memberId;
    //主通讯类型
    private Short dataType;
    //通讯类型名称 
    private String customName;
    //细分通讯类型
    private Integer tagType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Short getDataType() {
        return dataType;
    }

    public void setDataType(Short dataType) {
        this.dataType = dataType;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
    }
    public MemberCommTypeTagInfo(){}
	public MemberCommTypeTagInfo(Long id, Long memberId, Short dataType,
			String customName, Integer tagType) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.dataType = dataType;
		this.customName = customName;
		this.tagType = tagType;
	}
    
}