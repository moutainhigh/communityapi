/**
 * 
 */
package net.okdi.mob.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author chuanshi.chai
 */
public interface CommonService {
		String uploadPic(String type,Long memberId,MultipartFile[] myfiles);
	    
		String delFile(Long MemberId,String type);

		String queryPhotoByid(Long memberId,String type);
		
		String uploadSound(Long MemberId, MultipartFile[] myfiles);
		
		String delSound(Integer day);
		
}
