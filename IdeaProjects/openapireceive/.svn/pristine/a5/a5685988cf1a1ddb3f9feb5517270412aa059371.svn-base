package net.okdi.apiV1.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV1.dao.MemberInfoMapperV1;
import net.okdi.apiV1.service.IAttributionService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.util.PubMethod;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IAttributionServiceImpl implements IAttributionService {
	@Autowired
	private RedisService redisService;
	@Autowired
	private MemberInfoMapperV1 memberInfoMapper;
	@Value("${compPic.readPath}")
	public String readPath;

	@Value("${headImgPath}")
	public String headImgPath;

	public Map<String, Object> findShopowner(String memberId, String roleId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map = null;
		if (!PubMethod.isEmpty(roleId)) {// 2 代收站店长 3
			if ("2".equals(roleId) || "3".equals(roleId)) {
				map = redisService.get("findShopowner1xxx", memberId + roleId,
						Map.class);
				if (PubMethod.isEmpty(map)) {
					map = memberInfoMapper.findShopowner(memberId, roleId);
					if (!PubMethod.isEmpty(map)) {
						map.put("headImgPath", headImgPath + memberId + ".jpg");
						redisService.put("findShopowner1xxx",
								memberId + roleId, map);
					}
				}
				map.put("description_msg", "");
			}
			if ("0".equals(roleId) || "-1".equals(roleId)) { // 0 收派员 -1 后勤
				map = redisService.get("findShopowner2xxx", memberId + roleId,
						Map.class);
				if (PubMethod.isEmpty(map)) {
					map = memberInfoMapper.findToreceiveAndLogistic(memberId,
							roleId);
					if (!PubMethod.isEmpty(map)) {
					map.put("headImgPath", headImgPath + memberId + ".jpg");
					redisService.put("findShopowner2xxx", memberId + roleId,
							map);
				}
				}

			}
			if ("1".equals(roleId)) { // 站长
				map = redisService.get("findShopowner3xxx", memberId + roleId,
						Map.class);
				if (PubMethod.isEmpty(map)) {
					map = memberInfoMapper.findToreceiveAndLogistic(memberId,
							roleId);
					if (!PubMethod.isEmpty(map)) {
						map.put("description_msg", PubMethod.isEmpty(map.get("description_msg"))?null:map.get("description_msg"));
					if(map.get("comp_status")!="0"||map.get("comp_status")!="-1"){//提交待审核状态，不查审核表
					List<Map<String, Object>> listCompaudit = memberInfoMapper.findbasCompaudit(memberId);
					if(listCompaudit.size()>0){
						Map<String,Object>map1 = listCompaudit.get(0);
						map.put("audit_opinion", map1.get("audit_opinion"));
						map.put("audit_desc", PubMethod.isEmpty(map1.get("audit_desc"))?"":map1.get("audit_desc"));
					}
					}
					List<Map<String, Object>> listImage = memberInfoMapper
							.findStationmaster(memberId, roleId);
					map.put("image_type_2", "");
					map.put("image_type_5", "");
					map.put("image_type_8", "");
					map.put("image_type_9", "");
//					if (listImage.size() > 0 && listImage.size() == 3) {// 上传了身份
						if (listImage.size() > 0 ) {// 上传了身份
						for (int i = 0; i < listImage.size(); i++) {
							Map<String, Object> map1 = listImage.get(i);
							if (map1 != null) {
								map.put("image_type_" + map1.get("image_type"),
										readPath + map1.get("image_url"));
							}

						}
						if(PubMethod.isEmpty(String.valueOf(map.get("image_type_2")))){
							map.put("image_type_2", String.valueOf(map.get("image_type_9")));
						}
					} else {// 未上传身份
						map.put("image_type_2", "");
						map.put("image_type_5", "");
						map.put("image_type_8", "");
						map.put("image_type_9", "");
					}

					map.put("headImgPath", headImgPath + memberId + ".jpg");
					redisService.put("findShopowner3xxx", memberId + roleId,
							map);
				}
				}
			}
		}
		return map;
	}
}
