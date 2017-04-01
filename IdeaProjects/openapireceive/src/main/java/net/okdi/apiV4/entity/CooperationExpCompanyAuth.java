package net.okdi.apiV4.entity;

/**
 * 接入快递公司授权
 */
public class CooperationExpCompanyAuth {

    private Long memberId;

    private Long netId;

    private String netName;

    /**
     * 网点代码
     */
    private String orgCode;

    /**
     * 账号
     */
    private String userCode;

    /**
     * 电话号码
     */
    private String telNum;

    /**
     * 在合作快递公司系统中的用户名
     */
    private String userName;

    /**
     * 合作快递公司系统中的网点名
     */
    private String orgName;

    /**
     * 授权时间
     */
    private Long authTime;

    /**
     * 下次授权时间
     */
    private Long nextAuthTime;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public Long getAuthTime() {
        return authTime;
    }

    public void setAuthTime(Long authTime) {
        this.authTime = authTime;
    }

    public Long getNextAuthTime() {
        return nextAuthTime;
    }

    public void setNextAuthTime(Long nextAuthTime) {
        this.nextAuthTime = nextAuthTime;
    }

    public Long getNetId() {
        return netId;
    }

    public void setNetId(Long netId) {
        this.netId = netId;
    }

    public String getNetName() {
        return netName;
    }

    public void setNetName(String netName) {
        this.netName = netName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
