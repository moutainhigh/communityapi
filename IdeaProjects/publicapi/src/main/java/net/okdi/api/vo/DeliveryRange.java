package net.okdi.api.vo;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.okdi.api.adapter.MapAdapter;

/**
 * 
 * 项目名称：ProductModel
 * 包名称：com.pml.service.outer.ws.domain   
 * 类名称：DeliveryRange   
 * 类描述：派送范围接口返回多个数据封装对象
 * 创建人：翟士贺   
 * 创建时间：Dec 28, 2013 4:25:08 PM   
 * 修改人：翟士贺   
 * 修改时间：Dec 28, 2013 4:25:08 PM   
 * 修改备注： 
 * @version v1.0   
 *
 */
@XmlRootElement(name="DeliveryRange")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="DeliveryRange")
public class DeliveryRange {
	/**
	 * 覆盖地址
	 */
	@XmlJavaTypeAdapter(MapAdapter.class)
	private List<Map> coverAddress;
	/**
	 * 超区地址
	 */
	@XmlJavaTypeAdapter(MapAdapter.class)
	private List<Map> exceedAddress;
	
	/**
	 * 信息集合
	 */
	@XmlJavaTypeAdapter(MapAdapter.class)
	private List<Map> itemList;
	
	/**
	 * 总数量
	 */
	@XmlElement(nillable=true)
	private int totalCount;
	
	/**
	 * 总页数
	 */
	@XmlElement(nillable=true)
	private int totalPage;
	public DeliveryRange() {
		super();
	}

	public DeliveryRange(List<Map> coverAddress, List<Map> exceedAddress) {
		super();
		this.coverAddress = coverAddress;
		this.exceedAddress = exceedAddress;
	}
	
	
	public DeliveryRange(List<Map> itemList, int totalCount, int totalPage) {
		super();
		this.itemList = itemList;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
	}

	public List<Map> getCoverAddress() {
		return coverAddress;
	}

	public void setCoverAddress(List<Map> coverAddress) {
		this.coverAddress = coverAddress;
	}

	public List<Map> getExceedAddress() {
		return exceedAddress;
	}

	public void setExceedAddress(List<Map> exceedAddress) {
		this.exceedAddress = exceedAddress;
	}

	public List<Map> getItemList() {
		return itemList;
	}

	public void setItemList(List<Map> itemList) {
		this.itemList = itemList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
}
