package net.okdi.apiV2.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.okdi.apiV2.dao.TaskRecordMapper;
import net.okdi.apiV2.service.TaskRecordService;
import net.okdi.apiV4.entity.ParParcelinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class TaskRecordServiceImpl implements TaskRecordService {
	
	@Autowired
	TaskRecordMapper taskRecordMapper;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Override
	public int queryReceiveCount(String memberId) {
		int count=0;
		//DateFormat df1 = DateFormat.getDateInstance();
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = dateF.format(new Date());
		System.out.println("===格式化后的日期形式："+d1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = sdf.parse(d1+" 00:00:00");
			Date endDate = sdf.parse(d1+" 23:59:59");
			
			count = taskRecordMapper.queryReceiveCount(memberId, startDate, endDate);
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public static void main(String[] args){
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = dateF.format(new Date());
		System.out.println("===格式化后的日期形式："+d1);
	
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(d1+" 00:00:00");
			System.out.println(sdf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int querySendCount(String memberId) {
		int count=0;
		/*DateFormat df1 = DateFormat.getDateInstance();
		String d1 = df1.format(new Date());*/
		
		SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = dateF.format(new Date());
		System.out.println("===格式化后的日期形式："+d1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date startDate = sdf.parse(d1+" 00:00:00");
			Date endDate = sdf.parse(d1+" 23:59:59");
			
			count = taskRecordMapper.querySendCount(memberId, startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return count;
	}
}
