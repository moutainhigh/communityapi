package net.okdi.apiV4.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="parcelSignInfo")
public class ParcelSignInfo {

    @Id
    private Long id;
    private Long netId;
    private String netName;
    //1：取件   2：派件
    private Integer signType;
    //标记内容
    private String signContent;
    //编号
    private String signNum;
    private Date createTime;
    private Date updateTime;
    //0：启用  1：停用
    private Integer flag;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getNetId() {
        return netId;
    }
    public void setNetId(Long netId) {
        this.netId = netId;
    }
    public Integer getSignType() {
        return signType;
    }
    public void setSignType(Integer signType) {
        this.signType = signType;
    }
    public String getSignContent() {
        return signContent;
    }
    public void setSignContent(String signContent) {
        this.signContent = signContent;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getFlag() {
        return flag;
    }
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
    public String getSignNum() {
        return signNum;
    }
    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }
    public String getNetName() {
        return netName;
    }
    public void setNetName(String netName) {
        this.netName = netName;
    }


}
