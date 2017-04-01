package net.okdi.api.service;

import java.math.BigDecimal;

public interface ExpressTaskService {
	
	String createTaskSimple(String accessType,String memberId, String data, 
			String address, String packagenum,
			String packageweight, String sendermobile, String sendername,String erpid,
			String allsms,String takeTime);

	String cancelTask(String memberId, String taskId);
	
	String saveParcelInfo(String taskid,String expwaybillnum,String receivername,String receivermobile,
			String receiveraddressId,String receiveraddress,String sendname,String sendmobile,String sendaddressid,String sendaddress,String parweight,String freight,String iscod,
			String codamount,String insureamount,String pricepremium,String packingcharges,String freightpaymentmethod,String goodsdesc,String parcelremark,String serviceid,
			String actualtakemember);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加电商引流信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * @param erpJsonInfo		引流JSON格式
	 * @return
	 * @since v1.0
	 */
	public String saveExpOftenData(String erpJsonInfo);

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改电商引流信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * @param processJsonInfo		引流JSON格式
	 * @return
	 * @since v1.0
	 */
	public String updateArticleProcessData(String processJsonInfo);
}
