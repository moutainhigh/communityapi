package net.okdi.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParParcelinfo;
import net.okdi.api.vo.VO_ParParcelInfo;
import net.okdi.api.vo.VO_ParcelInfoAndAddressInfo;
import net.okdi.api.vo.VO_ParcelList;
import net.okdi.core.base.BaseDao;

import org.apache.ibatis.annotations.Param;

public interface ParParcelinfoMapper extends BaseDao {

	
	/**
	 * 根据运单号与网络id获取包裹内容信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据运单号与网络id获取包裹内容信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-4 下午5:04:05</dd>
	 * @param parParcelinfo
	 * @return
	 * @since v1.0
	 */
	public ParParcelinfo findByWaybillNumAndNetId(ParParcelinfo parParcelinfo);
	
	public Map queryWaybillNumById(Long parcelId);
	
	/**
	 * 保存包裹
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午2:27:01</dd>
	 * @param parParcelinfo
	 * @return
	 * @since v1.0
	 */
	public int insertParcel(ParParcelinfo parParcelinfo);

	/**
	 * 修改包裹
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午2:27:14</dd>
	 * @param parParcelinfo
	 * @return
	 * @since v1.0
	 */
	public int updateParcel(ParParcelinfo parParcelinfo);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午5:41:22</dd>
	 * @param parParcelinfo
	 * @return
	 * @since v1.0
	 */
	public int updateParcelSelective(ParParcelinfo parParcelinfo);
	
	/**
	 * 根据包裹id删除包裹
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午6:10:12</dd>
	 * @param id
	 * @return
	 * @since v1.0
	 */
	public int deleteParcel(Long id);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过包裹id删除对应包裹内容信息与包裹地址信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 下午6:28:46</dd>
	 * @param expWayBillNum  包裹运单号 
	 * @return
	 * @since v1.0
	 */
	public ParParcelinfo findParcelInfoByExpWayBillNum(Map<String, Object> map);


	public Long getParcelId(Map<String, Object> map);
	public List<VO_ParParcelInfo>  queryParcelList(ParParcelinfo parParcelinfo);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>通过实际派件人memberId查询包裹ids/dd>
	 * <dt><span class="strong">作者:</span></dt><dd>haifeng.he</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:59:19</dd>
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	public List<Long> queryParcelIdsByMemberId(Map<String,Object> parMap);
	public Long queryParcelIdsByMemberIdCount(Long memberId);

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询取件任务(只查询该收派员一天的取件任务)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param actualTakeMember 实际取件人Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	public List queryTakeTaskList(Long queryTakeByWaybillNum);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询一个单号的包裹</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param actualTakeMember 实际取件人Id
	 * @param receiptId 运单Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	public List  queryTakeByWaybillNum(ParParcelinfo parcelInfo);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>更改发件任务Id状态</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param memberId 收派员Id
	 * @param parceId 包裹Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	public void updateParcelStatus(Map<String,Object> map);
	
	public Long queryTaskByReceipt(Long parcelId);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询该取件任务下的包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param memberId 收派员Id
	 * @param takeTaskId 取件任务Id
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	List queryTakeTaskParcel(@Param("takeTaskId")Long  takeTaskId);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>解除取件任务和包裹的关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
	 * @param parcelIds 包裹Id多个用,分隔
	 * @return
	 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
	 * @since v1.0
	 */
	public void delTakeTaskRelationParcel(String [] parcelIds);


	public void addbatchSaveParcelInfo(List<VO_ParcelInfoAndAddressInfo> list);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>任务id查询包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午2:57:21</dd>
	 * @param taskId
	 * @return
	 * @since v1.0
	 */
	public List<Map> queryParcelInfoByTaskId(Long taskId);
	
	
	public List<Long> queryParcelIdsByCreateUserId(Long memberId);
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>条件查询补录包裹列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-29 上午10:14:57</dd>
	 * @param param
	 * @return
	 * @since v1.0
	 */
	public List<Map> queryParcelInfoList(Map<String, Object> param);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>不录包裹数量统计</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-29 上午10:15:18</dd>
	 * @param param
	 * @return
	 * @since v1.0
	 */
	int queryParcelInfoListCount(Map<String, Object> param);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>任务id查询包裹id列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-2-2 下午6:24:17</dd>
	 * @param taskId
	 * @return
	 * @since v1.0
	 */
	public List<Long> queryParcelInfoListByTaskId(Long taskId);
	
	
	
	int updateCompIdByTakeTaskId(ParParcelinfo parcelInfo);
	
	/**
	 * @Method: queryAlreadySignList 
	 * @Description: 以提包裹列表
	 * @param sendMemberId
	 * @param currentPage
	 * @param pageSize
	 * @return 
	 * @see net.okdi.api.service.ParcelInfoService#queryAlreadySignList(java.lang.Long, java.lang.Integer, java.lang.Integer) 
	 * @since jdk1.6
	 */
	public List<Map<String,Object>> queryAlreadySignList(Map parMap);

	public Long queryAlreadySignCount(@Param(value="sendMemberId")Long sendMemberId);
	
	public Long querySendTaskIdById(Long id);


	public Long getParcelListBySendMemberIdCount(@Param(value="memberId")Long memberId);
	
	public ParParcelinfo getParcelInfoByIdNoEnd(Long id);
	
	public List<Long>  querySendMemberByExpWaybillNum(String expWaybillNum);
	
	public List<VO_ParcelList> takeRecordList(Map<String,Object> parMap);
    /**
     * 查询签收的包裹信息
     * @param memberId
     * @param queryTime
     * @return
     */
    public List<Map<String,Object>>sendRecordListFinish(@Param("memberId") Long memberId,@Param("queryTime") Date queryTime);
    /**
     * 查询异常的包裹信息
     * @param memberId
     * @param queryTime
     * @return
     */
    public List<Map<String,Object>>sendRecordListException(@Param("memberId") Long memberId,@Param("queryTime") Date queryTime);

    public int updatePickupTime(Long taskId);
    
    public int updateExceptionTime(Long parcelId);
    
}
