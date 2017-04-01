package net.okdi.api.dao;

import net.okdi.api.entity.SmsInterception;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface SmsInterceptionMapper {

	 /**
     * 用户第一次发送短信，给用户添加一条记录
     */
    int insert(SmsInterception record);

    /**
     * 判断用户是否已存在
     */
    int selectByPrimaryKey(@Param("memberId")Long memberId);

    /**
     * 更新累计错误次数
     */
    int updateByPrimaryKey(@Param("memberId")Long memberId);

    /**
     * 更新错误类型
     */
    int updateErrorType(SmsInterception record);
//    /**
//     * 更新错误类型
//     */
//    int updateErrorType(@Param("memberId")Long memberId,@Param("disableType")int disableType,@Param("disableTime")Date disableTime);
    
    /**
     * 短信错误次数达到三次，更新禁用时间，累计错误次数+1
     */
    int updateErrorNumAndTime(@Param("memberId")Long memberId,@Param("disableTime")Date disableTime);
    
    /**
     * 	账户查询
     * @author chunyang.tan
     * @param memberId	发送人id
     * @param sendPhone	发送人电话
     * @return
     */
    SmsInterception queryAccountInquiry(String memberId);
}