package net.okdi.core.util;

import java.io.File;
import java.util.Properties;

public class GetPicConPath {
	/**
	 * 
	 * @param imgType deli or  comp
	 * @param pathType read or write 
	 * @return
	 */
	public static String  getPicPath(String path,String pathType){
		Properties props=System.getProperties(); //系统属性
		//String path =  ConstPool.getSystemValue("wuliu."+pathType+".picture.path")+ConstPool.getSystemValue("wuliu."+imgType+"picture.filename");
//		System.out.println(path);
		if(props.getProperty("os.name").toLowerCase().indexOf("windows") > -1){
			if("write".equals(pathType)){				
				if(path.endsWith("/")){
					path = path.substring(0, path.length()-1)+File.separator;
				}else if(!path.endsWith(File.separator)){
					path += File.separator;
				}
			}else if("read".equals(pathType)){				
				if(path.endsWith(File.separator)){
					path = path.substring(0, path.length()-1)+"/";
				}else if(!path.endsWith("/")){
					path += "/";
				}
			}
		}else{
			if(path.endsWith("\\")){
				path = path.substring(0, path.length()-1)+File.separator;
			}else if(!path.endsWith(File.separator)){
				path += File.separator;
			}
		}
		return path;
	}
}
