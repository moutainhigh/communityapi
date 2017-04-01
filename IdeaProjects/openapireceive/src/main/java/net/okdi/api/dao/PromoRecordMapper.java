package net.okdi.api.dao;

import net.okdi.api.entity.PromoRecord;

public interface PromoRecordMapper {

    int insert(PromoRecord record);

    PromoRecord selectByPrimaryKey(Long id);

}