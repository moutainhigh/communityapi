package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.AttentionService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * <p>Title: </p>
 * <p>Description:关注Controller </p>
 * <p>Company: net.okdit</p> 
 * @author jianxin.ma
 * @date 2016-5-23
 */
@Controller
@RequestMapping("/attention")
public class AttentionController extends BaseController {
	
	@Autowired 
	private AttentionService attentionService;

	/**
	 * 
	 * @Description: 查询关注通知列表
	 * @param memberId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-24
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAttentionList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAttentionList(Long memberId) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryAttentionList.001", "memberId不能为空");
		}
		try {
			return jsonSuccess(attentionService.queryAttentionList(memberId));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 查询联系人列表
	 * @param memberId 用户id
	 * @param roleId '角色 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员'
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-24
	 */
	@ResponseBody
	@RequestMapping(value = "/queryContactList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryContactList(Long memberId,Short roleId,Long compId,Integer currentPage,Integer pageSize) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(roleId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.002", "roleId不能为空");
		}
		if(1==roleId){
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.003", "compId不能为空");
			}
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.004", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryContactList.005", "pageSize不能为空");
		}
		try {
			return jsonSuccess(attentionService.queryContactList(memberId,roleId,compId,currentPage,pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 取消关注
	 * @param memberId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-24
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelAttention", method = { RequestMethod.POST, RequestMethod.GET })
	public String cancelAttention(Long fromMemberId, Long toMemberId) {
		if(PubMethod.isEmpty(fromMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.cancelAttention.001", "fromMemberId不能为空");
		}
		if(PubMethod.isEmpty(toMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.cancelAttention.002", "toMemberId不能为空");
		}
		try {
			attentionService.cancelAttention(fromMemberId,toMemberId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 查询动态通知列表
	 * @param memberId
	 * @param currentPage
	 * @param pageSize
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-25
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDynamicNotice", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryDynamicNotice(Long memberId, Integer currentPage, Integer pageSize) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryDynamicNotice.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryDynamicNotice.002", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.queryDynamicNotice.003", "pageSize不能为空");
		}
		try {
			return jsonSuccess(attentionService.queryDynamicNotice(memberId,currentPage,pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 查询排行榜
	 * @param memberId 
	 * @param tagType 标签类型 1.派件数 2.接单数 3.外快
	 * @param currentPage
	 * @param pageSize
	 * @param filter 排序筛选条件 1 国内 2 省内 3 城市
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-25
	 */
	@ResponseBody
	@RequestMapping(value = "/leaderboard", method = { RequestMethod.POST, RequestMethod.GET })
	public String leaderboard(Long memberId,Short tagType, Integer currentPage, Integer pageSize,Short filter) {
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(tagType)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.0021", "tagType不能为空");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.003", "currentPage不能为空");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.004", "pageSize不能为空");
		}
		if(PubMethod.isEmpty(filter)){
			return paramsFailure("net.okdi.apiV4.controller.AttentionController.leaderboard.004", "filter不能为空");
		}
		try {
			return jsonSuccess(attentionService.leaderboard(memberId,tagType,currentPage,pageSize,filter));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

}