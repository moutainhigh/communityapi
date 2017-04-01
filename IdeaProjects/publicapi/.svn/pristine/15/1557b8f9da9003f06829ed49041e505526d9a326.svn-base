package net.okdi.api.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.okdi.api.adapter.MapMapConvertor.MapMapEntry;

/**
 * 
 *CXF的SOAP消息对java的Map<K,Map<String,Object>类型的转换适配器
 * 
 * @author  文超
 * @version  [版本号, 2013-12-28 下午12:48:56 ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MapMapAdapter extends XmlAdapter<MapMapConvertor, Map<String,Map<String,Object>>> {

	@Override
	public MapMapConvertor marshal(Map<String, Map<String,Object>> mapmap)throws Exception {
		MapMapConvertor mapMapConvertor = new MapMapConvertor();  
		if(mapmap==null){
			return mapMapConvertor;
		}
		Iterator<String> itMapMap=mapmap.keySet().iterator();
		while(itMapMap.hasNext()){
			String key=itMapMap.next();
			Map<String,Object> val=mapmap.get(key);
			MapConvertor convertor = new MapConvertor();  
	        Iterator<String> itMap=val.keySet().iterator();
	        
	        while(itMap.hasNext()){
	        	String keyMap=itMap.next();
	        	Object keyVal=val.get(keyMap);
	        	MapConvertor.MapEntry mapentry=new MapConvertor.MapEntry(keyMap, keyVal);
	        	convertor.addEntry(mapentry);
	        }
	        
			MapMapConvertor.MapMapEntry e=new MapMapConvertor.MapMapEntry(key, convertor);
			mapMapConvertor.addEntry(e);
		}
		return mapMapConvertor;
	}  
	
	@Override
	public Map<String, Map<String,Object>> unmarshal(MapMapConvertor mapmap)throws Exception {
		Map<String, Map<String,Object>> result = new HashMap<String, Map<String,Object>>();  
		for(MapMapEntry mapMapEntry:mapmap.getEntries()){
			String key=mapMapEntry.getKey();
			Map<String,Object> map=new HashMap<String, Object>();
			MapConvertor value=mapMapEntry.getValue();
			for (MapConvertor.MapEntry e : value.getEntries()) {  
	            map.put(e.getKey(), e.getValue());  
	        } 
			result.put(key, map);
		}
		
         
		return result;
	}

	
	  
  
}  