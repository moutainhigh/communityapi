package net.okdi.httpClient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

public class ParcelInfoHttpClient extends AbstractHttpClient{

	@Autowired
	private ConstPool constPool; 
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>扫描单号后判断是否为系统中已存在的包裹，如果已存在则返回包裹ID</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 上午11:08:51</dd>
	 * @param netId 网络id
	 * @param expWayBillNum  运单号
	 * @return {"parcelId":118025415647232,"success":true} 存在包裹    {"success":false}  不存在包裹 
	 * @since v1.0
	 */
	public String queryParcelInfoByExpWayBillNumAndNetId(Long netId,String expWayBillNum){
		if(PubMethod.isEmpty(netId)){
			return PubMethod.paramsFailure();
		}
		if(PubMethod.isEmpty(expWayBillNum)){
			return PubMethod.paramsFailure();
		}
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("netId", String.valueOf(netId));
			map.put("expWayBillNum", expWayBillNum);
			String url = constPool.getOpenApiUrl() + "parcelInfo/queryParcelInfoByExpWayBillNumAndNetId";
			String response = Post(url, map);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.jsonFailure();
		}
	}
	
	/** 
	 * 取件记录查询（包裹）
	 * @param memberId 用户ID
	 * @param date 日期
	 * @return
	 * @since v1.0
	*/
	public String takeRecordList(Long memberId,String date){
		Map<String,String> map = new HashMap<String,String>();
		map.put("memberId", memberId==null?"":memberId.toString());
		map.put("date", date);
		String url = constPool.getOpenApiUrl() + "parcelInfo/takeRecordList";
		return Post(url, map);
	}
	public String sendRecordList(Long memberId,Long date){
		Map<String,String> map = new HashMap<String,String>();
		map.put("memberId", memberId==null?"":memberId.toString());
		map.put("date", date+"");
		String url = constPool.getOpenApiUrl() + "parcelInfo/sendRecordList";
		return Post(url, map);
	}
}
