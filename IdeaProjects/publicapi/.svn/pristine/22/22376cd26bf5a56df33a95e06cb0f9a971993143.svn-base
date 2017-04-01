/**  
 * @Project: openapi
 * @Title: MemberCollectExpMemberController.java
 * @Package net.okdi.api.controller
 * @author amssy
 * @date 2015-1-17 下午04:33:33
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.controller;


import net.okdi.core.base.BaseController;
import net.okdi.mob.entity.MemberCollectExpMember;
import net.okdi.mob.service.MemberCollectExpMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 个人端的业务模块
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/memberCollectExpMemberController")
public class MemberCollectExpMemberController extends BaseController {
	@Autowired
	MemberCollectExpMemberService memberCollectExpMemberService;
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加御用快递哥</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午06:49:26</dd>
	 * @param memberCollectExpMember 
	 * @return
	 * @since v1.0
	 */
    @RequestMapping("addMemberCollectExpMember")
    @ResponseBody
    public String addMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember){
    	try {
    		return memberCollectExpMemberService.addMemberCollectExpMember(memberCollectExpMember);	
		} catch (Exception e) {
            return jsonFailure(e);
		}
    	
    }
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>修改御用快递哥</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午06:50:44</dd>
     * @param memberCollectExpMember
     * @return
     * @since v1.0
     */
    @RequestMapping("updateMemberCollectExpMember")
    @ResponseBody
    public String updateMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember){
    	try {
    		return memberCollectExpMemberService.updateMemberCollectExpMember(memberCollectExpMember);
		} catch (Exception e) {
            return jsonFailure(e);
		}
    	
    }
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>获取人员御用快递哥列表</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午06:51:10</dd>
     * @param memberId
     * @return
     * @since v1.0
     */
    @RequestMapping("getMemberCollectExpMemberList")
    @ResponseBody
    public String getMemberCollectExpMemberList(Long memberId){
    	try {
    		return	memberCollectExpMemberService.getMemberCollectExpMemberList(memberId);	
		} catch (Exception e) {
            return jsonFailure(e);
		}
    	
    	
    }
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>删除御用快递哥</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午06:51:33</dd>
     * @param MemberCollectExpMemberId
     * @return
     * @since v1.0
     */
    @RequestMapping("deleteMemberCollectExpMember")
    @ResponseBody
    public String deleteMemberCollectExpMember(Long memberCollectExpMemberId){
    	try {
    		return memberCollectExpMemberService.deleteMemberCollectExpMember(memberCollectExpMemberId);
		} catch (Exception e) {
            return jsonFailure(e);      
		}
    }
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>获取御用快递哥详情</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午06:52:00</dd>
     * @param MemberCollectExpMemberId
     * @return
     * @since v1.0
     */
    @RequestMapping("getMemberCollectExpMember")
    @ResponseBody
    public String getMemberCollectExpMember(Long memberCollectExpMemberId,Double lng,Double lat){
    	try {
    		return memberCollectExpMemberService.getMemberCollectExpMember(memberCollectExpMemberId, lng, lat);
		} catch (Exception e) {
            return jsonFailure(e);
		}
    }
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>获取御用快递哥详情</dd>
     * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午06:52:00</dd>
     * @param MemberCollectExpMemberId
     * @return
     * @since v1.0
     */
    @RequestMapping("ifCollectionAndRegister")
    @ResponseBody
    public String ifCollectionAndRegister(Long memberId,String expMemberPhone,Double lng,Double lat){
    	try {
    		String result = memberCollectExpMemberService.ifCollectionAndRegister(memberId, expMemberPhone,lng,lat);
    		return result;
    	} catch (Exception e) {
            return jsonFailure(e); 
		}
    
    }
}
