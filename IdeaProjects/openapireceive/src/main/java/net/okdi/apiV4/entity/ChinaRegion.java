package net.okdi.apiV4.entity;

/**
 * 34个省市自治区以及下属城市
 * @ClassName: ChinaRegion
 * @Description: TODO
 * @author hang.yu
 * @date 2016年5月24日 下午12:41:15
 * @version V1.0
 */
public class ChinaRegion {

	/**
	 * 区域id
	 */
	private int regionId;
	
	/**
	 * 
	 */
	private String name;
	
	
	private String allName;
	
	private String code;
	
	private int rank;
	
	private int parent;
	
	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAllName() {
		return allName;
	}

	public void setAllName(String allName) {
		this.allName = allName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}
	
}
