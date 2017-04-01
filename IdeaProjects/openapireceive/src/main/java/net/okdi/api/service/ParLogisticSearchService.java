package net.okdi.api.service;

import java.util.List;
import java.util.Map;
import net.okdi.api.entity.ParLogisticSearch;
import net.okdi.core.base.BaseService;
import net.okdi.core.common.page.Page;

public interface ParLogisticSearchService extends BaseService<ParLogisticSearch>{

	public int saveSend(Long channelId, Long netId, String expWaybillNum, String traceStatus, String traceDetail, Long appointId,
			String recMobile, String systemMark, String channelNo, String expType);

	public int decideGoods(Long channelId, String expWaybillNum, Long netId);
	public String findCode(Long netId);

	public List<ParLogisticSearch> list(Long channelId, String expType);

	public void deleteById(Long id, Long netId, Long channelId, String expType);

	public List<ParLogisticSearch> listByPage(Page page, Long channelId,
			String expType);

	void update(Long id,String expWaybillNum, String traceStatus, String traceDetail);
	List<Long> findIdListPerFourHour();
	List<ParLogisticSearch> findParLogistic(String ids);
	void batchUpdate(String json);

	public void insertOrUpdateByMemberIdNetBill(String memberId, String netId, String expWaybillNum, String recMobile,
			String traceStatus, String traceDetail);

	public List<ParLogisticSearch> findUnPushed();

	public void updatePushData(String ids);
}
