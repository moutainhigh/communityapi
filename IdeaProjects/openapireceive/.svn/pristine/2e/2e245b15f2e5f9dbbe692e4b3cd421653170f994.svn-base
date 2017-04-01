package net.okdi.api.controller;

import java.util.Map;

import net.okdi.api.service.DicAddressService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 根据经纬度查询地址信息
 * 
 * @ClassName expGateway
 * @Description TODO
 * @author xiangwei.liu
 * @date 2014-10-31
 * @since jdk1.6
 */
@Controller
@RequestMapping("/expGateway")
public class DicAddressController extends BaseController{
	
	@Autowired
	DicAddressService parseService;
	
	/**
	 * 根据经纬度解析地址
	 * @Method: parseAddrNew 
	 * @Description: TODO
	 * @param lat  纬度
	 * @param lng  经度
	 * @author xiangwei.liu
	 * @date 2014-10-31 下午5:05:25
	 * @since jdk1.6
	 * @return addressId 最后一级地址ID addressName 解析地址名
	 * {"data":{"addressId":,"addressName":"","lastLevelId":,"lastLevelName":""},"success":}
	 */
	@RequestMapping(value = "/parseAddrNew", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String parseAddrNew(String lat,String lng){
		try {
							   //在地址表中(dic_address),根据经纬度查询出addressId存入map
			Map<String, Object> Resultmap = parseService.parseAddrByList(lat,lng);
							   //在地址辅助表(DIC_ADDRESSAID)中查询出详细信息
			return jsonSuccess(parseService.getObjectByPrimaryKey(Long.parseLong(Resultmap.get("addressId").toString())));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}