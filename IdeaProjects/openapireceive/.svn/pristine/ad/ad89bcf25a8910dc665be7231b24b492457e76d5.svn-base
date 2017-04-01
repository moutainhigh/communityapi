package net.okdi.apiV4.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import net.okdi.api.entity.ParParcelinfo;
import net.okdi.core.base.BaseDao;

public interface ParParcelinfoMapperV4 extends BaseDao {

	List<HashMap<String, Object>> queryParcelToBeTakenList(HashMap<String, Object> paramMap);

	Integer queryParcelToBeTakenCount(HashMap<String, Object> paramMap);

	ParParcelinfo queryParcelInfo(String parId);

	void updateExceptionTimeByParId(String id);

	Map queryWaybillNumById(Long parcelId);

	ParParcelinfo getParcelInfoByIdNoEnd(Long parcelId);

	void updateParcel(@Param("taskId")Long taskId,@Param("parcelId")Long parcelId,@Param("createUserId")Long createUserId);

	public List<Long> queryTakeIdByMemberId(String memberId);

	List<Map<String,Object>> ifCompExist(@Param("compName")String compName, @Param("compMobile")String compMobile);

    
}
