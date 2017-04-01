package net.okdi.circle.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.circle.entity.CircleInfo;
import net.okdi.circle.service.ExpressCircleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ExpressCircleServiceImpl implements ExpressCircleService {

	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Override
	public String queryCircleList(String memberId, Double circleLongitude,
			Double circleLatitude) {
		
		Map<String, Object> tudeMap = this.queryFromPosition(circleLongitude , circleLatitude);
		//上下距离
		Double bottomLat = Double.valueOf(tudeMap.get("bottomLat")+"");
		Double topLat = Double.valueOf(tudeMap.get("topLat")+"");
		//左右距离
		Double leftLng = Double.valueOf(tudeMap.get("leftLng")+"");
		Double rightLng = Double.valueOf(tudeMap.get("rightLng")+"");
		Query query = new Query();
		//通过审核的圈子 1
		query.addCriteria(Criteria.where("circleStatus").is((short)1));//通过审核的,并且
		query.addCriteria(Criteria.where("circleLongitude").gte(bottomLat).lte(topLat));
		query.addCriteria(Criteria.where("circleLatitude").gte(leftLng).lte(rightLng));
		List<CircleInfo> listCircle = mongoTemplate.find(query, CircleInfo.class);
		
		return null;
	}

	
	public Map<String,Object> queryFromPosition(Double longitude, Double latitude) {
		int dis=5;//设置5公里范围内
		double EARTH_RADIUS = 6378.137 * 1000;
		double dlng = Math.abs(2 * Math.asin(Math.sin(dis*1000 / (2 * EARTH_RADIUS)) / Math.cos(latitude)));
		dlng = dlng*180.0/Math.PI;        //弧度转换成角度
		double dlat = Math.abs(dis*1000 / EARTH_RADIUS);
		dlat = dlat*180.0/Math.PI;     //弧度转换成角度
		double bottomLat=latitude - dlat;
		double topLat=latitude + dlat;
		double leftLng=longitude - dlng;
		double rightLng=longitude + dlng;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("bottomLat", bottomLat);
		params.put("topLat", topLat);
		params.put("leftLng", leftLng);
		params.put("rightLng", rightLng);
		return params;
		
	}

	
}
