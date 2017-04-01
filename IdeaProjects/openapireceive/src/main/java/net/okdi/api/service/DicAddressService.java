package net.okdi.api.service;

import java.util.List;
import java.util.Map;

import net.okdi.api.dao.DicAddressMapper;
import net.okdi.api.entity.DicAddressaid;

import org.springframework.beans.factory.annotation.Autowired;

public interface DicAddressService {
	
	Map<String,Object> parseAddrByList(String lat, String lng);
	
    Map<String,Object> getObjectByPrimaryKey(Long addressId);
}
