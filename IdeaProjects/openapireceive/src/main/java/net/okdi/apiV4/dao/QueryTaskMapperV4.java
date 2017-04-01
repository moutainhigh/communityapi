package net.okdi.apiV4.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface QueryTaskMapperV4 {

	String getMemberName(Long actorMemberId);

	String getNetName(Long coopNetId);

	String getCompName(Long coopCompId);

	Long getNetIdByName(String netName);

}
