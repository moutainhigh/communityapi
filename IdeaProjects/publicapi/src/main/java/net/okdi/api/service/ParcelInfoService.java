package net.okdi.api.service;

public interface ParcelInfoService {
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>扫描单号后判断是否为系统中已存在的包裹，如果已存在则返回包裹ID</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 上午11:24:43</dd>
	 * @param netId 网络id
	 * @param expWayBillNum  运单号
	 * @return {"parcelId":118025415647232,"success":true} 存在包裹    {"success":false}  不存在包裹 
	 * @since v1.0
	 */
	public String queryParcelInfoByExpWayBillNumAndNetId(Long netId, String expWayBillNum);

}
