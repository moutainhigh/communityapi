package net.okdi.apiV4.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParParceladdress;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.apiV4.entity.MemberInfo;
import net.okdi.core.base.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public interface AssignPackageMapper extends BaseDao{

	List<MemberInfo> queryEmployeeByCompId(Long compId);
	/**查询站点下其他快递员的接口
	 * @param compId
	 * @param memberId
	 * @return
	 */
	List<MemberInfo> queryOtherEmployeeByCompId(Map<String, Object>paramap);

	public void insertParcel(ParParcelinfo parcelInfo);

	public void insertParceladdress(ParParceladdress parceladdress);

	void updateParcelSelective(ParParcelinfo parcelInfo);

	void updateParceladdressSelective(ParParceladdress parceladdress);

	Long getParcelId(Map<String, Object> map);

	Long getParcelIdByMobile(Map<String, Object> map);

	List<Long> querySendMemberByMoblie(String addresseeMobile);

	List<MemberInfo> queryMemberByCompId(Long compId);//站点下所有成员

}
