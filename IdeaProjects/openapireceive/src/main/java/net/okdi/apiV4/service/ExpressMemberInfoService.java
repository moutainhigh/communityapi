package net.okdi.apiV4.service;

import java.util.Map;

import net.okdi.apiV4.entity.ExpressMemberInfo;
import net.okdi.apiV4.vo.CommonResponse;

public interface ExpressMemberInfoService {

	ExpressMemberInfo findByMid(Long memberId);

	Map<String, Object> isTopOrInterru(Long mid, Long cid);

	CommonResponse truncLog(Long mid, Long targetId);

	CommonResponse topCommunity(Long cid, ExpressMemberInfo member, boolean isTop);

	CommonResponse topContast(Long mid, Long targetMid, boolean isTop);

	CommonResponse interrupt(Long targetId, ExpressMemberInfo member, boolean isInter);

}
