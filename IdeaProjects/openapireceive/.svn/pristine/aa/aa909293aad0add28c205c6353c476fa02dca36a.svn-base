package net.okdi.apiV2.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import net.okdi.core.util.PubMethod;

/**
 * 站点列表信息VO
 * @Project Name:springmvc 
 * @Package net.okdi.apiV2.vo  
 * @Title: VO_BasCompInfo.java 
 * @ClassName: VO_BasCompInfo <br/> 
 * @date: 2015-12-2 下午8:22:25 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
public class VO_BasCompInfo implements Serializable,Comparable<VO_BasCompInfo> {

	private Long compId;//站点id
    private String compName;//站点名称
    private String firstLetter;//首字母
    private String compTypeNum;//站点类型
    private BigDecimal longitude;//经度
    private BigDecimal latitude;//纬度
    
	
	public Long getCompId() {
		return compId;
	}

	public void setCompId(Long compId) {
		this.compId = compId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter == null ? null : firstLetter.trim();
	}

	public String getCompTypeNum() {
		return compTypeNum;
	}

	public void setCompTypeNum(String compTypeNum) {
		this.compTypeNum = compTypeNum;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}


	@Override
	public int compareTo(VO_BasCompInfo o) {
		if (!PubMethod.isEmpty(o.getFirstLetter())) {
			return this.getFirstLetter().compareTo(o.getFirstLetter());
		}
		return 0;
	}

}
