package net.okdi.apiV4.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.entity.ParcelSignInfo;
import net.okdi.core.common.page.Page;

public interface ReceiveOrderFromWebsiteService {

	
	/**调用国通接口查询包裹
	 * @param userCode
	 * @param orgCode
	 * @param memberId
	 * @param netId
	 * @return
	 */
	public Map<String, Object>  queryTakePackListFromWebsite(String userCode, String orgCode,String memberId,String netId);

	/**保存来自国通推送的包裹
	 * @param parcels
	 * @param memberId
	 * @param netId
	 */
	public void saveParcelFromGT(String parcels, String memberId, String netId);

	/**查询来自国通的包裹列表(电商件)
	 * @param parseLong
	 * @param netId
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	public Page QueryParcelFromGT(long parseLong, long netId, int parseInt, int parseInt2);

	/**订单中搜索包裹
	 * @param mobile
	 * @param parseLong
	 * @param netId
	 * @param parseInt
	 * @param parseInt2
	 * @param flag
	 * @return
	 */
	public Page huntParcelGT(String mobile, long parseLong, long netId, int parseInt,int parseInt2, String flag);

	/**国通的包裹确认取件
	 * @param uids
	 * @param memberId
	 * @param netId 
	 * @param terminalId
	 * @param versionId
	 * @return
	 */
	public String confirmTakeParcelGT(String uids, Long memberId, Long netId, String terminalId, String versionId );
	
	/**查看当前登录人是否授权
	 * @param memberId
	 * @param netId
	 * @return
	 */
	public boolean checkIsAuthorized(Long memberId,Long netId);
	
	/**查询订单中电商件的包裹列表时,判断是否有查询的权限
	 * @param netId
	 * @return
	 */
	public Boolean IsRightToApi(String netId);

	public Map<String, Object> uploadParcelReicevedinfo(Long memberId, Long recNetId,
			Date createtime, String expWaybillNum, String terminalId,
			String versionId);
}
