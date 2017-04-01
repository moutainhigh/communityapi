package net.okdi.apiV2.controller;

import java.util.Map;

import net.okdi.apiV2.service.NetinfoApplyService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/netInfoApply")
public class NetInfoApplyController extends BaseController{
	@Autowired
	private NetinfoApplyService netinfoApplyService;
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description   添加快递网络申请
	 * @data 2015-12-5 下午1:33:10
	 * @param memberId
	 * @param netName
	 * @param telphone
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertNetInfoApply", method = { RequestMethod.POST, RequestMethod.GET })
	public String insertNetInfoApply(String memberId,String netName,String telphone){
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.NetInfoApplyController","memberId不能为空");
		}
		if(PubMethod.isEmpty(netName)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.NetInfoApplyController","netName不能为空");
		}
		if(PubMethod.isEmpty(telphone)){
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.NetInfoApplyController","telphone不能为空");
		}
		
		int  rs=this.netinfoApplyService.insertNetInfoApply(memberId, netName, telphone);
		return String.valueOf(rs);
	}
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description 查询快递网络申请  列表 (运营平台)
	 * @data 2015-12-5 下午1:36:41
	 * @param memberPhone
	 * @param netName
	 * @param netStatus
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNetInfoApply", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNetInfoApply(String memberPhone,String netName,String netStatus,Integer pageNo, Integer pageSize){
		if(PubMethod.isEmpty(pageNo)){
			pageNo=1;
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize=10;
		}
		try {
			Map result=this.netinfoApplyService.queryNetInfoApply(memberPhone, netName, netStatus, pageNo, pageSize);
			return jsonSuccess(result);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description   处理快递申请网络 (运营平台)
	 * @data 2015-12-5 下午1:41:12
	 * @param id
	 * @param note
	 * @param operatorId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dealWith", method = { RequestMethod.POST, RequestMethod.GET })
	public String dealWith(Long id,String note,String operatorName){
	try {
		int res=this.netinfoApplyService.dealWith(id, note, operatorName);
		return jsonSuccess(res);
	} catch (Exception e) {
		e.printStackTrace();
		return jsonFailure(e);
	}
	}
	/**
	 * 
	 * @Method
	 * @author AiJun.Han
	 * @Description   查询快递网络申请详情(运营平台)
	 * @data 2015-12-5 下午1:43:03
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDeal", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryDeal(Long id){
		try {
			Map map=this.netinfoApplyService.queryDeal(id);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
}
