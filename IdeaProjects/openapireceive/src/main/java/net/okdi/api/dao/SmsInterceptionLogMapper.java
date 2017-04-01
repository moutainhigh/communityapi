package net.okdi.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.SmsInterceptionLog;

import org.apache.ibatis.annotations.Param;

public interface SmsInterceptionLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_interception_log
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_interception_log
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    int insert(SmsInterceptionLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_interception_log
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    int insertSelective(SmsInterceptionLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_interception_log
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    SmsInterceptionLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_interception_log
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    int updateByPrimaryKeySelective(SmsInterceptionLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sms_interception_log
     *
     * @mbggenerated Mon Sep 14 17:45:17 CST 2015
     */
    int updateByPrimaryKey(SmsInterceptionLog record);
    
    
    /**
     * 	查询短信拦截列表 
     * @author chunyang.tan
     * @param sendPhone	发送人号码
     * @param createTime	创建时间
     * @param sendContent	发送内容
     * @return
     */
    List<SmsInterceptionLog> querySmsInterceptionLog(Map<String,Object> param);
    
    int querySmsInterceptionLogCount(Map<String,Object> param);
    
    SmsInterceptionLog queryIfHas(String sendPhone);
    
}