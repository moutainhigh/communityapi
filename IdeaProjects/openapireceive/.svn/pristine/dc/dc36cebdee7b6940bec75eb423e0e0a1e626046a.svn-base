package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompbusiness;
import net.okdi.api.entity.ExpCompAuditLog;
import net.okdi.api.entity.ExpCompRelation;
import net.okdi.core.base.BaseService;

public interface BusinessBranchService extends BaseService<ExpCompRelation>{
	
	public Map<String,Object> queryCompInfoByParentCompId(String compName,String memberPhone,Long parentCompId,Long netId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-6 下午2:51:14</dd>
	 * @param compName
	 * @param memberPhone
	 * @return
		{
		    "data": {
		        "compAddress": "北京-海淀区-苏家坨镇|大街",
		        "compId": 14460135155171328,
		        "compName": "贺海峰站点测试1",
		        "compTypeNum": "1006",
		        "memberId": 14460120576302080,
		        "responsible": "贺海峰"
		    },
		    "success": true
		}
	 * @since v1.0
	 */
	public Map<String,Object> queryCompInfo(String compName,String memberPhone,String compAddress, Long netId);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>是否符合关联条件</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-9 上午10:29:53</dd>
	 * @param compTypeNum
	 * @param compAddress
	 * @param netId
	 * @param compId
	 * @param parentCompId
	 * @return
	 * @since v1.0
	 */
	public boolean isRightConditions(Long compId);
	
	public boolean isCorrectConditions(Long parentCompId,Long compId);
	
	public BasCompbusiness queryBasCompbusiness(Long compId);
	public void sendInvitationMessage(String compTypeNum,String memberPhone, String compName,Long compId);
	
	public Map<String,Object> queryCompRelationInfo(Long compId,Long userId);
	
	public void updateCompRelation(Long compId,Long parentCompId);
	
	public void deleteCompRelation(Long logId,Long compId,Long parentCompId);
	
	public void createCompRelation(Long userId,Long parentCompId,Long compId,Long netId);
	
	public ExpCompAuditLog queryStatus(Long parentCompId,Long compId);

	public ExpCompRelation queryCompRelationByBusinessId(Long compId);

	public List<Map<String,Object>> queryConsumingBranch(Long parentCompId);

	public List<Map<String,Object>> queryUnConsumingBranch(Long parentCompId);

	public void auditBranch(Long logId, Short flag, Long compId, Long parentCompId,Long netId,Long userId,String refuseDesc);

	public void relieveRelation(Long relationId, Long compId, Long parentCompId);

	public void editAreaColor(Long relationId, String areaColor,Long parentCompId);

	public Map<String,Object> queryRelationInfo(Long compId);

	public Map<String,Object> queryCompInfoByCompId(String compName, String memberPhone, Long compId, Long netId);

	public void addCompRelation(Long logId,Long userId, Long parentCompId, Long compId, Long netId);
	
	public void  clearCache();

	public int isThrough(Long compId);
}
