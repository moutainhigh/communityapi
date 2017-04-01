package net.okdi.api.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "MapMapConvertor")  
@XmlAccessorType(XmlAccessType.FIELD)  
public class MapMapConvertor {  
    private List<MapMapEntry> entries = new ArrayList<MapMapEntry>();

	public List<MapMapEntry> getEntries() {
		return entries;
	}

	 public void addEntry(MapMapEntry entry) {  
	        entries.add(entry);  
	    }  
      
	 public static class MapMapEntry {  
		 private String key;  
		  
	     private MapConvertor value;
       
	     
	    public MapMapEntry(){}
	    
	    public MapMapEntry(String key,MapConvertor value){
	    	this.key=key;
	    	this.value=value;
	    }
	     
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public MapConvertor getValue() {
			return value;
		}

		public void setValue(MapConvertor value) {
			this.value = value;
		}  
	     
	     
	 }


}  