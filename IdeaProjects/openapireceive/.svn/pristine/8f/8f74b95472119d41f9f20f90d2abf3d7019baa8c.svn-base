/**  
 * @Project: openapi
 * @Title: MemberCollectExpMemberService.java
 * @Package net.okdi.api.service.impl
 * @author amssy
 * @date 2015-1-17 下午02:26:08
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.MemberCollectExpMemberMapper;
import net.okdi.api.dao.MemberTagMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.MemberCollectExpMember;
import net.okdi.api.service.MemberInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.DistanceUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * @author amssy
 * @version V1.0
 */

@Service
public class MemberCollectExpMemberService implements net.okdi.api.service.MemberCollectExpMemberService {
    @Autowired
	private MemberCollectExpMemberMapper mcemmMapper;
    @Autowired
	private MemberTagMapper memberTagMapper;
    @Autowired
    EhcacheService ehcacheService;
    @Autowired
    private BasNetInfoMapper netInfoMapper;
    @Autowired
    private BasCompInfoMapper compInfoMapper;
    @Autowired
    private MemberInfoService memberInfoService;
    @Value("${save.courier.head}")
    private String headUrl;
    /**
	 * @Method: addMemberCollectExpMember 
	 * @param memberCollectExpMember 
	 * @see net.okdi.api.service.MemberCollectExpMemberService#addMemberCollectExpMember(net.okdi.api.entity.MemberCollectExpMember) 
	 */
	@Override
	public String addMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember) {
		Long casMemberId = this.ifRegister(memberCollectExpMember.getExpMemberPhone());
		Long id = IdWorker.getIdWorker().nextId();
		memberCollectExpMember.setCasMemberId(casMemberId);
		memberCollectExpMember.setId(id);
		if(memberCollectExpMember.getNetId()==null||"".equals(memberCollectExpMember.getNetId())){
			memberCollectExpMember.setNetId("-1");
		}
		mcemmMapper.insert(memberCollectExpMember);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		System.out.println(jsonSuccess(map));
		return jsonSuccess(map);
	}

	/**
	 * @Method: getMemberCollectExpMember 
	 * @param id
	 * @return 
	 * @see net.okdi.api.service.MemberCollectExpMemberService#getMemberCollectExpMember(java.lang.Long) 
	 */
	@Override
	public String getMemberCollectExpMember(Long id,Double lng,Double lat) {
		MemberCollectExpMember memberCollectExpMember =  mcemmMapper.selectByPrimaryKey(id);
		
		 BasCompInfo	compInfo = this.ehcacheService.get("compCache", memberCollectExpMember.getCompId()+"", BasCompInfo.class);
     	  if (compInfo == null) {//缓存里面没有从数据库里面读取公司信息
 			compInfo = this.compInfoMapper.findById(memberCollectExpMember.getCompId());
 		 }
     	 BasNetInfo netInfo = this.ehcacheService.get("netCache",memberCollectExpMember.getNetId()+"", BasNetInfo.class);
		  if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
			netInfo = this.netInfoMapper.findById(Long.parseLong(memberCollectExpMember.getNetId()));
	     }
		  memberCollectExpMember.setNetName(netInfo==null?"":netInfo.getNetName());
		  memberCollectExpMember.setCompName(compInfo==null?"":compInfo.getCompName());
     	 if(memberCollectExpMember!=null){
     		memberCollectExpMember.setImgUrl(headUrl+memberCollectExpMember.getCasMemberId()+".jpg");
     	 }
     	 if(memberCollectExpMember!=null&&memberCollectExpMember.getCasMemberId()!=null&&memberCollectExpMember.getCasMemberId()!=0l){
		    	if("0".equals(this.ifRegister(memberCollectExpMember.getCasMemberId()))){
		    		memberCollectExpMember.setCasMemberId(0l);
		    	}
		    }
		  Map <String,Object>map = new HashMap<String,Object>();
 		 map.put("memberCollectExpMember", memberCollectExpMember);
 		 map.put("distance", 0);
 		 Map ehMap =  ehcacheService.get("onLineMember",memberCollectExpMember.getCasMemberId()+"" ,Map.class);
 		 if(lng!=null&&lat!=null&&ehMap!=null&&ehMap.get("lng")!=null&&ehMap.get("lat")!=null){
 		 Double distance =   DistanceUtil.getDistance(lat, lng, Double.parseDouble(ehMap.get("lat").toString()), Double.parseDouble(ehMap.get("lng").toString()));
 		 map.put("distance", new DecimalFormat("#0.0").format(distance/1000));
 		 }
		return jsonSuccess(map);
	}

	/**
	 * @Method: getMemberCollectExpMemberList 
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.MemberCollectExpMemberService#getMemberCollectExpMemberList(java.lang.String) 
	 */
	@Override
	public String getMemberCollectExpMemberList(Long memberId) {
		List<MemberCollectExpMember> list = mcemmMapper.selectByMemberId(memberId);
		if (list!=null&&list.size()>0) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setImgUrl((list.get(i).getCasMemberId()==null||list.get(i).getCasMemberId()==0)?"":headUrl+list.get(i).getCasMemberId()+".jpg");
			  BasNetInfo netInfo = this.ehcacheService.get("netCache",list.get(i).getNetId()+"", BasNetInfo.class);
			  if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
				netInfo = this.netInfoMapper.findById(Long.parseLong("".equals(list.get(i).getNetId().toString())?"-1":list.get(i).getNetId().toString()));
		     }
			list.get(i).setNetName(netInfo==null?"":netInfo.getNetName());
		    if(list.get(i).getCasMemberId()!=null&&list.get(i).getCasMemberId()!=0l){
		    	if("0".equals(this.ifRegister(list.get(i).getCasMemberId()))){
		    		list.get(i).setCasMemberId(0l);
		    	}
		    }
			
		}
		
		}
		return jsonSuccess(list);
	}

	/**
	 * @Method: updateMemberCollectExpMember 
	 * @param memberCollectExpMember 
	 * @see net.okdi.api.service.MemberCollectExpMemberService#updateMemberCollectExpMember(net.okdi.api.entity.MemberCollectExpMember) 
	 */
	@Override
	public void updateMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember) {
		mcemmMapper.updateByPrimaryKey(memberCollectExpMember);
	}
	
    private String jsonSuccess(Object map) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != map) {
			allMap.put("data", map);
		}
		return JSON.toJSONString(allMap,BaseController.s);
	}

	/**
	 * @Method: deleteMemberCollectExpMemberList 
	 * @param id 
	 * @see net.okdi.api.service.MemberCollectExpMemberService#deleteMemberCollectExpMemberList(java.lang.Long) 
	*/
	@Override
	public void deleteMemberCollectExpMember(Long id) {
		mcemmMapper.deleteByPrimaryKey(id);
	}

	public long ifRegister(String phone){
		String result = this.queryExpMemberInfo(phone, 0.0, 0.0);
		JSONObject obj = JSON.parseObject(result);
		JSONObject jbj = obj.getJSONObject("data");
		if(jbj==null){
			return 0l;
		}else{
			return jbj.getLongValue("memberId");
		}
	}

	/**
	 * @Method: ifCollection 
	 * @param memberId
	 * @param memberCollectExpMemberId
	 * @return 
	 * @see net.okdi.api.service.MemberCollectExpMemberService#ifCollection(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public Map<String,Object> ifCollection(Long createUserId, String expMemberPhone) {
		boolean flag = true;
		List list = mcemmMapper.getMemberIdByPhone(createUserId, expMemberPhone);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", "");
		if(list == null || list.size() == 0 ){
			flag = false;
		}else{
			map.put("id",list.get(0) );
			
		}
		map.put("flag", flag);
		return map;
	}

	/**
	 * @Method: queryExpMemberInfo 
	 * @param expMemberPhone
	 * @return 
	 * @see net.okdi.api.service.MemberCollectExpMemberService#queryExpMemberInfo(java.lang.String) 
	*/
	@Override
	public String queryExpMemberInfo(String expMemberPhone,Double lng,Double lat) {
		Map <String,Object>map = mcemmMapper.getMemberByPhone(expMemberPhone);
		if(map!=null){
		 BasCompInfo	compInfo = this.ehcacheService.get("compCache", map.get("compId")+"", BasCompInfo.class);
    	  if (compInfo == null) {//缓存里面没有从数据库里面读取公司信息
			compInfo = this.compInfoMapper.findById(map.get("compId")==null?null:Long.parseLong(map.get("compId").toString()));
		 }
    	 BasNetInfo netInfo = this.ehcacheService.get("netCache",compInfo==null?-1+"":compInfo.getBelongToNetId()+"", BasNetInfo.class);
		  if (netInfo == null) {////缓存里面没有从数据库里面读取网络信息
			netInfo = this.netInfoMapper.findById(compInfo==null?null:compInfo.getBelongToNetId());
	     }map.put("imageUrl", map.get("memberId")==null||"0".equals(map.get("memberId").toString())?"":headUrl+map.get("memberId").toString()+".jpg");
		  map.put("netName", netInfo==null?"":netInfo.getNetName());
		  map.put("compName", compInfo==null?"":compInfo.getCompName());
		  map.put("netId",netInfo==null?"":netInfo.getNetId() );
		  map.put("distance", 0);
		  map.put("compAddressId", compInfo==null?"":compInfo.getCompAddressId());
		  map.put("compAddress", compInfo==null?"":compInfo.getCompAddress());
		  Map ehMap =  ehcacheService.get("onLineMember",map.get("memberId")+"" ,Map.class);
	 		 if(lng!=null&&lat!=null&&ehMap!=null&&ehMap.get("lng")!=null&&ehMap.get("lat")!=null){
	 		 Double distance =   DistanceUtil.getDistance(lat, lng, Double.parseDouble(ehMap.get("lat").toString()), Double.parseDouble(ehMap.get("lng").toString()));
	 		 map.put("distance", new DecimalFormat("#.0").format(distance/1000));
		}
		
	}
		return jsonSuccess(map);
	}
	private String ifRegister(Long memberId){
		Map<String,Object> memberMap = memberInfoService.getValidationStatus(memberId);
		if("1".equals(String.valueOf(memberMap.get("relationFlag")))&&"1".equals(String.valueOf(memberMap.get("veriFlag")))){
			return "1";
		}
		else{
		    return "0";
		}
	}

	
}
