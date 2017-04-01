package net.okdi.api.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import net.okdi.api.exception.ServiceException;

/**
 * 
 * Java的Map类型转换实现的转换器
 * 
 * @author  文超
 * @version  [版本号, 2013-12-28 上午9:28:05 ]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@XmlType(name = "MapConvertor")  
@XmlAccessorType(XmlAccessType.FIELD)  
public class MapConvertor {  
    private List<MapEntry> entries = new ArrayList<MapEntry>();  
  
    public void addEntry(MapEntry entry) {  
        entries.add(entry);  
    }  
  
    public List<MapEntry> getEntries() {  
        return entries;  
    }  
    
    public static MapConvertor fromMap(Map<String,Object> map) throws ServiceException {
    	MapConvertor mc=new MapConvertor();
    	Iterator<String> it =map.keySet().iterator();
    	while(it.hasNext()){
    		String key=it.next();
    		Object val=map.get(key);
    		if(val instanceof Map){
    			throw new ServiceException("Value 不支持Map类型");
    		}
    		MapConvertor.MapEntry e = new MapConvertor.MapEntry();  
    		e.setKey(key);
    		e.setValue(val);
    		mc.addEntry(e);
    	}
    	return mc;
    }
    
    public static Map<String,Object> toMap(MapConvertor map) throws ServiceException {
    	  Map<String, Object> result = new HashMap<String, Object>();  
          for (MapConvertor.MapEntry e : map.getEntries()) {
              result.put(e.getKey(), e.getValue());  
          }  
          return result;  
    }
      
    public static class MapEntry {  
  
        private String key;  
  
        private Object value;  
          
        public MapEntry() {  
            super();  
        }  
  
        public MapEntry(Map.Entry<String, Object> entry) {  
            super();  
            this.key = entry.getKey();  
            this.value = entry.getValue();  
        }  
  
        public MapEntry(String key, Object value) {  
            super();  
            this.key = key;  
            this.value = value;  
        }  
  
        public String getKey() {  
            return key;  
        }  
  
        public void setKey(String key) {  
            this.key = key;  
        }  
  
        public Object getValue() {  
            return value;  
        }  
  
        public void setValue(Object value) {  
            this.value = value;  
        }  
    }  
}  