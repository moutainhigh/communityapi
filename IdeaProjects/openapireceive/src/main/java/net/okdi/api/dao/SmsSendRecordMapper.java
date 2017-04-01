package net.okdi.api.dao;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.SmsSendRecord;
import net.okdi.core.base.BaseDao;

public interface SmsSendRecordMapper extends BaseDao{

    List<SmsSendRecord> querySmsSendRecord(@Param("memberId")Long memberId,@Param("time")String time);

    public void saveSmsSendRecordBatch(List<SmsSendRecord> smsSendRecordList);
}