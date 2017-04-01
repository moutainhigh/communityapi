package net.okdi.apiV4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.apiV2.dao.BasEmployeeAuditMapperV2;
import net.okdi.apiV4.entity.ParTaskInfo;
import net.okdi.apiV4.service.ExpCustomerInfoNewService;
import net.okdi.apiV4.service.ReceiveOrderFromWebsiteService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/ReceiveOrderFromWebsite")
public class ReceiveOrderFromWebsiteController extends BaseController{

	private static Logger logger = Logger.getLogger(ReceiveOrderFromWebsiteController.class);
	@Autowired
	private ReceiveOrderFromWebsiteService receiveOrderFromWebsiteService;
	
	
	/**查询取件订单(电商件)
	 * @param userCode
	 * @param orgCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTakePackListFromWebsite", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryTakePackListFromWebsite(String userCode,String orgCode,String memberId,String netId){
		logger.info("查询取件订单(电商件)-new;=======userCode:"+userCode+",orgCode"+orgCode);
		Map<String, Object> result = receiveOrderFromWebsiteService.queryTakePackListFromWebsite(userCode,orgCode,memberId,netId);
		logger.info("查询取件订单(电商件)-new====="+result);
		return JSON.toJSONString(result);
	}
	/**保存国通推送过来的包裹(电商件)
	 * @param userCode
	 * @param orgCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveParcelFromGT", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveParcelFromGT(String parcels,String memberId,String netId){
		logger.info("保存包裹(电商件国通);=======parcels:"+parcels+",memberId:"+memberId+",netId:"+netId);
		boolean isAuthorized = receiveOrderFromWebsiteService.checkIsAuthorized(Long.parseLong(memberId), Long.parseLong(netId));
		logger.info("查看是否授权:"+isAuthorized);
		if (false==isAuthorized) {
			return jsonFailure();
		}
		receiveOrderFromWebsiteService.saveParcelFromGT(parcels,memberId,netId);
		return jsonSuccess();
	}
	/**查询国通推送过来的包裹列表(电商件)
	 * @param memberId
	 * @param netId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/QueryParcelFromGT", method = { RequestMethod.POST, RequestMethod.GET })
	public String QueryParcelFromGT(String memberId,String netId,String currentPage, String pageSize){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("002", "netId 不能为空!!!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return paramsFailure("003", "currentPage 不能为空!!!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return paramsFailure("004", "pageSize 不能为空!!!");
		}
		
		logger.info("查询国通推送过来的包裹列表(电商件国通);=======memberId:"+memberId+",currentPage:"+currentPage+",pageSize:"+pageSize);
		//boolean isAuthorized = receiveOrderFromWebsiteService.checkIsAuthorized(Long.parseLong(memberId), Long.parseLong(netId));
		
//		Boolean isRightToApi = this.receiveOrderFromWebsiteService.IsRightToApi(netId);
//		logger.info("查看是否授权:"+isRightToApi);
//		if (false==isRightToApi) {
//			return jsonFailure();
//		}
		Page result =receiveOrderFromWebsiteService.QueryParcelFromGT(Long.parseLong(memberId),Long.parseLong(netId),Integer.parseInt(currentPage),Integer.parseInt(pageSize));
		return jsonSuccess(result);
	}
	
	/**订单(电商件和散件)中搜索当前包裹
	 * @param mobile //查询的手机号
	 * @param memberId //快递员的memberid
	 * @param netId //快递员的netid
	 * @param flag //
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/huntParcelGT", method = { RequestMethod.POST, RequestMethod.GET })
	public String huntParcelGT(String mobile,String memberId,String netId,String currentPage, String pageSize,String flag){
		logger.info("订单(电商件)中搜索当前包裹(电商件国通);=======mobile:"+mobile+",memberId:"+memberId);
		Page result =receiveOrderFromWebsiteService.huntParcelGT(mobile,Long.parseLong(memberId),Long.parseLong(netId),Integer.parseInt(currentPage),Integer.parseInt(pageSize),flag);
		return jsonSuccess(result);
	}
    /**确认取件(电商件国通)
     * @param uids  111-111
     * @param memberId
     * @param netId
     * @param terminalId
     * @param versionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/confirmTakeParcelGT", method = { RequestMethod.POST, RequestMethod.GET })
    public String confirmTakeParcelGT(String uids,String memberId,String netId,String terminalId,String versionId){
        logger.info("订单中查询包裹详情:parcelId: " + uids);
        if(PubMethod.isEmpty(uids)){
            return paramsFailure("001", "uids 不能为空!!!");
        }
	    logger.info("查询国通推送过来的包裹列表(电商件国通);=======memberId:"+memberId+",netId:"+netId+",terminalId:"+terminalId+",versionId:"+versionId);
		boolean isAuthorized = receiveOrderFromWebsiteService.checkIsAuthorized(Long.parseLong(memberId), Long.parseLong(netId));
		logger.info("查看是否授权:"+isAuthorized);
		if (false==isAuthorized) {
			return jsonFailure();
		}
    	String result=receiveOrderFromWebsiteService.confirmTakeParcelGT(uids,Long.parseLong(memberId),Long.parseLong(netId),terminalId,versionId);
    	logger.info("订单中保存包裹详细信息返回的结果:"+result);
        	return result;
        
    }
	
	
	
	
}
