package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.MemberTag;

/**
 * @ClassName MemberTagMapper
 * @Description TODO
 * @author mengnan.zhang
 * @date 2014-10-28
 * @since jdk1.6
*/
public interface MemberTagMapper {
    /**
     * @Method: deleteByPrimaryKey 
     * @Description: TODO
     * @param id
     * @return
     * @since jdk1.6
     */
    int deleteByPrimaryKey(MemberTag memberTag);
    /**
     * @Method: insert 
     * @Description: TODO
     * @param record
     * @return int
     * @author mengnan.zhang
     * @date 2014-10-28 下午01:20:24
     * @since jdk1.6
     */
    int insert(MemberTag record);
    /**
     * @Method: getMemberTagByMemberId 
     * @Description: TODO
     * @param memberId
     * @return
     * @author 
     * @date 2014-10-29 上午09:57:03
     * @since jdk1.6
     */
    List<Map<String,Object>> getMemberTagByMemberId(Long memberId);
    /**
     * @Method: updateMemberTag 
     * @Description: 修改用户持有联系人分组名称
     * @param memberId
     * @param tagName
     * @author mengnan.zhang
     * @date 2014-11-4 下午01:22:13
     * @since jdk1.6
     */
    public void updateMemberGroupName(Map<String,Object>map);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
     * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-15 下午04:37:11</dd>
     * @param memberId
     * @since v1.0
     */
    public List<Map<String,Object>> getMemberGroupJDW(Long memberId);
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>判断手机号是否注册cas</dd>
     * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-12-26 上午11:45:18</dd>
     * @param phone
     * @return
     * @since v1.0
     */
    public Long getMemberId(String phone);
   }