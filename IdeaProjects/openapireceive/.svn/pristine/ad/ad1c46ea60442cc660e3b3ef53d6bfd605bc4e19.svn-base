package net.okdi.api.controller;

import java.util.List;
import java.util.Map;

import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/findCache")
public class FindCacheController extends BaseController{
	@Autowired
	private EhcacheService ehcacheService;
	/*查询所有的缓存名称*/
	@RequestMapping(value = "/allName", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String allName(){
		try {
			String[] allName=this.ehcacheService.findAllName();
			return JSON.toJSONString(allName);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
		
	}
	/*根据一个缓存名称查询所有的key和对应的value*/
	@RequestMapping(value = "/byName", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String byName(String cacheName,int numPerPage,int pageNum,Long totalCount){
		try {
			List<Map<String,String>> keyValueList=this.ehcacheService.getAllKeyAndValue(cacheName,numPerPage,pageNum,totalCount);
			return JSON.toJSONString(keyValueList);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
		
	}
	/*根据一个缓存名称和key查询对应的VALUE*/
		@RequestMapping(value = "/byKey", method = { RequestMethod.GET, RequestMethod.POST })
		@ResponseBody
		public String byKey(String cacheName,String key){
			try {
				List<Map<String,String>> keyValueList=this.ehcacheService.getValueByKey(cacheName, key);
				return JSON.toJSONString(keyValueList);
			} catch (Exception e) {
				return this.jsonFailure(e);
			}
			
		}
	/*根据一个名称查询list的长度*/
		@RequestMapping(value = "/countByName", method = { RequestMethod.GET, RequestMethod.POST })
		@ResponseBody
		public String countByName(String cacheName){
			try {
				Long keyCount=this.ehcacheService.getKeyList(cacheName);
				return JSON.toJSONString(keyCount);
			} catch (Exception e) {
				return this.jsonFailure(e);
			}
			
		}
	/*根据cache名称和key判断key是否存在*/
		@RequestMapping(value = "/justKey", method = { RequestMethod.GET, RequestMethod.POST })
		@ResponseBody
		public String justKey(String cacheName,String key){
			try {
				Boolean keyExist=this.ehcacheService.getByKey(cacheName, key);
				return JSON.toJSONString(keyExist);
			} catch (Exception e) {
				return this.jsonFailure(e);
			}
			
		}
		/*只返回value*/
		@RequestMapping(value = "/onlyValue", method = { RequestMethod.GET, RequestMethod.POST })
		@ResponseBody
		public String onlyValue(String cacheName,String key){
			try {
				String value=this.ehcacheService.onlyValue(cacheName, key);
				return JSON.toJSONString(value);
			} catch (Exception e) {
				return this.jsonFailure(e);
			}
		}
}
