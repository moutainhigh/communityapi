package net.okdi.api.dao;

import java.util.List;

import net.okdi.api.entity.VisitShortUrlDsLog;

public interface VisitShortUrlDsLogMapper {

    int insert(VisitShortUrlDsLog record);

    List<VisitShortUrlDsLog> selectByShortKey(String shortKey);

    int updateByPrimaryKey(VisitShortUrlDsLog record);
}