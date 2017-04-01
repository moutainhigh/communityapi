package net.okdi.api.dao;

import java.util.Map;

import net.okdi.api.entity.MemberCommInfo;
import net.okdi.core.base.BaseDao;


public interface MemberCommInfoMapper extends BaseDao {

	/**
	 * 
	 * @Method: insertMemberComInfo 
	 * @Description: 插入memberinfo表
	 * @param memberCommInfo
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-10 下午8:08:12
	 * @since jdk1.6
	 */
	int insertMemberComInfo(MemberCommInfo memberCommInfo);

	/**
	 * @Method: checkTel 
	 * @Description: 站点添加手机号的时候检查是否存在
	 * @param associatedNumber
	 * @return int
	 * @throws
	 */
	int checkTel(Map<String, Object> map);

	/**
	 * 数据中存在这个手机号的记录需要判定是否绑定公司以及是本公司还是外部公司
	 * @Method: checkTelInfo 
	 * @Description: TODO
	 * @param  associatedNumber 手机号
	 * @param  compId 网点id
	 * @return MemberCommInfo
	 * @throws
	 */
	MemberCommInfo checkTelInfo(Map<String, Object> map);

	void doAddComm(Map<String, Object> map);

	int checkMemberPhone(Map<String, Object> map);

	
}