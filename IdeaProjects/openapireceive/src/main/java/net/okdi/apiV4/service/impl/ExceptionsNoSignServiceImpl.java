package net.okdi.apiV4.service.impl;

import net.okdi.apiV4.service.ExceptionsNoSignService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ExceptionsNoSignServiceImpl implements ExceptionsNoSignService {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Long query(Long memberId) {
		Query query=new Query();
		Criteria criteria=new Criteria("actualSendMember");
		query.addCriteria(criteria.is(memberId).and("exceptionTime").ne(null).and("parcelEndMark").is("0"));
		Long account=mongoTemplate.count(query, "parParcelinfo");
		return account;
	}

	
}
