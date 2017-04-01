package net.okdi.api.vo;

public class DeliverMapVo {
    private Long townId;
    private Double longitude;
    private Double latitude;
    private Long deliverNetId;
    private Long lev6AddressId;
    private String address;// 北京市-北京市区-海淀区-城区 田村路43号院7楼
    public Long getTownId() {
        return townId;
    }
    public void setTownId(Long townId) {
        this.townId = townId;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Long getDeliverNetId() {
        return deliverNetId;
    }
    public void setDeliverNetId(Long deliverNetId) {
        this.deliverNetId = deliverNetId;
    }
    public Long getLev6AddressId() {
        return lev6AddressId;
    }
    public void setLev6AddressId(Long lev6AddressId) {
        this.lev6AddressId = lev6AddressId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
   
   
    

     

}
