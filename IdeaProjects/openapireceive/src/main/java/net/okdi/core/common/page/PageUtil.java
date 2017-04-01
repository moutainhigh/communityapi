package net.okdi.core.common.page;

import java.util.ArrayList;
import java.util.List;

public class PageUtil  {
	
	public static Page bulidPage(int currentPage, int pageSize, int total, List list){
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		page.setTotal(total);
		page.setItems(list);
		return page;
	} 
	
	public static Page getPageData(int currentPage, int pageSize, List list){
		
		
		
		
		if(list ==null || list.size() == 0){
			return bulidPage(0, pageSize, 0, new ArrayList());
		}else if(pageSize >= list.size()){
			return bulidPage(1, pageSize, list.size(), list);
		}else if(pageSize < list.size()) {
			List listData = new ArrayList();
			for (int i = (currentPage-1)*pageSize; i < currentPage*pageSize; i++) {
				if(i == list.size()){
					break;
				}
				listData.add(list.get(i));
			}			
			return bulidPage(currentPage, pageSize, list.size(), listData);
			
		}
		return bulidPage(0, pageSize, 0, new ArrayList());
	}
	
	public static Page buildPage(int currentPage, int pageSize){
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setPageSize(pageSize);
		return page;
	}
	
	
}
