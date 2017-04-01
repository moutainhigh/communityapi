package net.okdi.api.service;

import java.util.Date;

import net.okdi.api.entity.SmsInterception;

public interface SmsInterceptionService {
   
    /**
     * 用户第一次发送短信，给用户添加一条记录
     */
    int insert(SmsInterception record);

    /**
     * 判断用户是否已存在
     */
    int selectByPrimaryKey(Long memberId);

    /**
     * 更新累计错误次数,+1
     */
    int updateByPrimaryKey(Long memberId);

    /**
     * 更新错误类型
     */
    int updateErrorType(Long memberId,int disableType);
    /**
     * 短信错误次数达到三次，添加禁用时间，累计错误次数+1
     */
    int updateErrorNumAndTime(Long memberId,Date disableTime);
}