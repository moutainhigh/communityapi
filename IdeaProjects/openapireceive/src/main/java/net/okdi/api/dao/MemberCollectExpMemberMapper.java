package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.MemberCollectExpMember;
import net.okdi.api.entity.MemberCollectExpMemberExample;

import org.apache.ibatis.annotations.Param;

public interface MemberCollectExpMemberMapper {
    int countByExample(MemberCollectExpMemberExample example);

    int deleteByExample(MemberCollectExpMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MemberCollectExpMember record);

    int insertSelective(MemberCollectExpMember record);

    List<MemberCollectExpMember> selectByExample(MemberCollectExpMemberExample example);

    MemberCollectExpMember selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MemberCollectExpMember record, @Param("example") MemberCollectExpMemberExample example);

    int updateByExample(@Param("record") MemberCollectExpMember record, @Param("example") MemberCollectExpMemberExample example);

    int updateByPrimaryKeySelective(MemberCollectExpMember record);

    int updateByPrimaryKey(MemberCollectExpMember record);
    
    List<MemberCollectExpMember> selectByMemberId(Long memberId);
    
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>根据手机号查找ID</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午04:35:37</dd>
     * @param createUserId
     * @param expMemberPhone
     * @return
     * @since v1.0
     */
    public List getMemberIdByPhone(@Param("createUserId")Long createUserId,@Param("expMemberPhone")String expMemberPhone );
    
    public Map<String,Object>getMemberByPhone(@Param("memberPhone")String memberPhone);
    

}