package net.okdi.api.adapter;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * 
 * CXF的SOAP消息对java的Map类型的转换适配器
 * 
 * @author  文超
 * @version  [版本号, 2013-12-28 上午9:26:16 ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MapAdapter extends XmlAdapter<MapConvertor, Map<String, Object>> {  
	  
	/**
	 * 将Java的Map类型转为绑定类型
	 * 创 建 人:  文超
	 * 创建时间:  2013-12-28 上午9:24:40  
	 * @param map  Java的Map类型
	 * @return
	 * @throws Exception
	 * @see [类、类#方法、类#成员]
	 */
    @Override  
    public MapConvertor marshal(Map<String, Object> map) throws Exception {  
        MapConvertor convertor = new MapConvertor();  
        if(map==null){
        	return convertor;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {  
            MapConvertor.MapEntry e = new MapConvertor.MapEntry(entry);  
            convertor.addEntry(e);  
        }  
        return convertor;  
    }  
  
   /**
    * 将Java的绑定类型转换成为Java的Map类型
    * 创 建 人:  文超
    * 创建时间:  2013-12-28 上午9:25:51  
    * @param map 
    * @return
    * @throws Exception
    * @see [类、类#方法、类#成员]
    */
    @Override  
    public Map<String, Object> unmarshal(MapConvertor map) throws Exception {  
        Map<String, Object> result = new HashMap<String, Object>();  
        for (MapConvertor.MapEntry e : map.getEntries()) {
//        	System.out.println("22222");
            result.put(e.getKey(), e.getValue());  
        }  
        return result;  
    }  
}  