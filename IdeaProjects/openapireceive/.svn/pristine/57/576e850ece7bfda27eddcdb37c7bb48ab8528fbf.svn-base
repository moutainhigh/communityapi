package net.okdi.core.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @ClassName: Page.java
 * @description: 分页插件
 * @author wxd
 * @date 2013-9-4
 * @version: 1.0.0
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 7000615940755255428L;

	private int currentPage = 1; // 当前页数

	private int pageSize = 10; // 每页显示记录的条数

	private int total; // 总的记录条数

	private int pageCount; // 总的页数

	private int offset; // 开始位置，从0开始

	private boolean hasFirst;// 是否有首页

	private boolean hasPre;// 是否有前一页

	private boolean hasNext;// 是否有下一页

	private boolean hasLast;// 是否有最后一页
	private List items = new ArrayList();
	public Page() {
	}

	public Page(int totalCount, int currentPage) {
		setCurrentPage(currentPage);
		setTotal(totalCount);
	}

	public int getPageCount() {
		pageCount = getTotal() / getPageSize();
		return (total % pageSize == 0) ? pageCount : pageCount + 1;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		this.pageCount = getPageCount();
		if (this.currentPage > this.pageCount&&this.pageCount!=0)
			this.currentPage = this.pageCount;
	}

	public int getOffset() {
		this.offset = (currentPage - 1) * pageSize;
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * 是否是第一页
	 * 
	 * @return
	 */
	public boolean isHasFirst() {
		hasFirst = (currentPage == 1) ? false : true;
		return hasFirst;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}

	public boolean isHasPre() {
		// 如果有首页就有前一页，因为有首页就不是第一页
		hasPre = isHasFirst() ? true : false;
		return hasPre;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}

	public boolean isHasNext() {
		// 如果有尾页就有下一页，因为有尾页表明不是最后一页
		hasNext = isHasLast() ? true : false;
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasLast() {
		// 如果不是最后一页就有尾页
		hasLast = (currentPage == getPageCount()) ? false : true;
		return hasLast;
	}

	public void setHasLast(boolean hasLast) {
		this.hasLast = hasLast;
	}

	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
	
	
	private List rows;
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
