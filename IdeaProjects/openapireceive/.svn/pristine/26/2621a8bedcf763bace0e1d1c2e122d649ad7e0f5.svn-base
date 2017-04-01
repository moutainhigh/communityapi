package net.okdi.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParLogisticSearch;
import net.okdi.api.service.ParLogisticSearchService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author dong.zhang
 * @version V1.0
 */
@Controller
@RequestMapping("/logisticSearch")
public class ParLogisticSearchController extends BaseController {
	/**
	 * 渠道编号02代表好递个人端
	 */
	private final String CHANNEL_NO_PERSONNEL = "02";
	/**
	 * 快递类型  1代表收快递
	 */
	private final String EXP_TYPE_RECEIVE = "1";
	/**
	 * 快递类型  0代表发快递
	 */
	private final String EXP_TYPE_SEND = "0";
	@Autowired
	private ParLogisticSearchService parLogisticSearchService;

	//@Autowired
	//private EhcacheService ehcacheService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端添加到我发</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:01:16</dd>
	 * @param channelId	渠道ID  也就是用户ID
	 * @param netId	快递公司ID
	 * @param expWaybillNum	运单号
	 * @param traceStatus	签收类型  0未签收  1已签收
	 * @param traceDetail	物流明细信息
	 * @param appointId	包裹id
	 * @param recMobile	收件人电话
	 * @param systemMark	系统标识 0个人端  1 电商管家 2 电商管家推送
	 * @return	JSON	成功success:true  失败：success:false
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.ParLogisticSearchController.saveSend.001 -  用户channelId为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.saveSend.002 -  expWaybillNum为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.saveSend.023 -  netId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/save/send", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String saveSend(Long channelId, Long netId, String expWaybillNum, String traceStatus, String traceDetail,
			Long appointId, String recMobile, String systemMark) {
		if (PubMethod.isEmpty(channelId)) {
			return paramsFailure("ParLogisticSearchController.saveSend.001", "用户channelId为必填项");
		}
		if (PubMethod.isEmpty(expWaybillNum)) {
			return paramsFailure("ParLogisticSearchController.saveSend.002", "expWaybillNum为必填项");
		}
		if (PubMethod.isEmpty(netId)) {
			return paramsFailure("ParLogisticSearchController.saveSend.023", "netId为必填项");
		}
		try {
			parLogisticSearchService.saveSend(channelId, netId, expWaybillNum, traceStatus, traceDetail, appointId,
					recMobile, systemMark, CHANNEL_NO_PERSONNEL, EXP_TYPE_SEND);
			//		List<String> keyList=this.ehcacheService.get("parlogisticSearchCache",channelId+"", List.class);
			//		if(!PubMethod.isEmpty(keyList)){
			//			this.ehcacheService.remove("parlogisticSearchCache", keyList);
			//		}
			return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端添加到我收</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:03:40</dd>
	 * @param channelId	渠道ID  也就是用户ID
	 * @param netId	快递公司ID
	 * @param expWaybillNum	运单号
	 * @param traceStatus	签收类型  0未签收  1已签收
	 * @param traceDetail	物流明细信息
	 * @param appointId	包裹id
	 * @param recMobile	收件人电话
	 * @param systemMark	系统标识 0个人端  1 电商管家 2 电商管家推送
	 * @return	JSON	成功success:true 失败：success:false
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.ParLogisticSearchController.saveReceive.003 -  用户channelId为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.saveReceive.004 -  expWaybillNum为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.saveReceive.024 -  netId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/save/receive", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String saveReceive(Long channelId, Long netId, String expWaybillNum, String traceStatus, String traceDetail,
			Long appointId, String recMobile, String systemMark) {
		if (PubMethod.isEmpty(channelId)) {
			return paramsFailure("ParLogisticSearchController.saveReceive.003", "用户channelId为必填项");
		}
		if (PubMethod.isEmpty(expWaybillNum)) {
			return paramsFailure("ParLogisticSearchController.saveReceive.004", "expWaybillNum为必填项");
		}
		if (PubMethod.isEmpty(netId)) {
			return paramsFailure("ParLogisticSearchController.saveReceive.024", "netId为必填项");
		}

		try {
			parLogisticSearchService.saveSend(channelId, netId, expWaybillNum, traceStatus, traceDetail, appointId,
					recMobile, systemMark, CHANNEL_NO_PERSONNEL, EXP_TYPE_RECEIVE);
			//		       List<String> keyList=this.ehcacheService.get("parlogisticSearchCache",channelId+"", List.class);
			//				if(!PubMethod.isEmpty(keyList)){
			//					this.ehcacheService.remove("parlogisticSearchCache", keyList);
			//				}
			return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端更新</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午3:03:40</dd>
	 * @param id 主键Id
	 * @param channelId	渠道ID  也就是用户ID
	 * @param netId	快递公司ID
	 * @param expWaybillNum	运单号
	 * @param traceStatus	签收类型  0未签收  1已签收
	 * @param traceDetail	物流明细信息
	 * @param appointId	包裹id
	 * @param recMobile	收件人电话
	 * @param systemMark	系统标识 0个人端  1 电商管家 2 电商管家推送
	 * @return	JSON	成功success:true 失败：success:false
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.ParLogisticSearchController.update.033 -  id为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String update(Long id, String channelId, String expWaybillNum, String traceStatus, String traceDetail) {
		if (PubMethod.isEmpty(id)) {
			return paramsFailure("ParLogisticSearchController.update.033", "id为必填项");
		}
		try {
			parLogisticSearchService.update(id, expWaybillNum, traceStatus, traceDetail);
			//		       List<String> keyList=this.ehcacheService.get("parlogisticSearchCache",channelId+"", List.class);
			//				if(!PubMethod.isEmpty(keyList)){
			//					this.ehcacheService.remove("parlogisticSearchCache", keyList);
			//				}
			return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断是否添加到我的收发快递列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:05:00</dd>
	 * @param channelId 用户ID
	 * @param expWaybillNum 运单号  
	 * @param netId  快递公司Id
	 * @return yes代表添加  no代表没添加
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.ParLogisticSearchController.decideGoods.005 -  用户channelId为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.decideGoods.006 -  expWaybillNum为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.decideGoods.007 -  netId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/decideGoods", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String decideGoods(Long channelId, String expWaybillNum, Long netId) {
		if (PubMethod.isEmpty(channelId)) {
			return paramsFailure("ParLogisticSearchController.decideGoods.005", "用户channelId为必填项");
		}
		if (PubMethod.isEmpty(expWaybillNum)) {
			return paramsFailure("ParLogisticSearchController.decideGoods.006", "expWaybillNum为必填项");
		}
		if (PubMethod.isEmpty(netId)) {
			return paramsFailure("ParLogisticSearchController.decideGoods.007", "netId为必填项");
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			int i = parLogisticSearchService.decideGoods(channelId, expWaybillNum, netId);
			if (i == 0) {
				map.put("exists", "no");
			} else {
				map.put("exists", "yes");
			}
			return this.jsonSuccess(map);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端查询所有我收（发）的快递</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:06:15</dd>
	 * @param channelId	用户ID
	 * @param expType	0发快递  1收快递
	 * @return	{"data":[{"netName":"韵达快递",---快递公司名称
	 * "parLogisticSearch":{"channelId":14319912592870400,----渠道ID  也就是用户ID
	 * "channelNo":"02",----渠道编号 01 好递个人端   02 好递接单王
	 * "createdTime":1416389801000,----创建时间
	 * "expType":"0",---快递类型 0:发快递  1:收快递
	 * "expWaybillNum":"3100116782440",-----运单号
	 * "id":14322515221151744,----主键
	 * "modifiedTime":1416389801000,---修改时间
	 * "netCode":"yunda",----快递100快递公司代码
	 * "netId":1504,----快递公司ID 
	 * "systemMark":"01",---系统标识 0个人端  1 电商管家
	 * "traceDetail":"{\"nu\":\"3100116782440\",\"updatetime\":\"2014-11-19 17:36:38\",\"condition\":\"H100\",\"message\":\"ok\",\"data\":[{\"context\":\"北京海淀区北影公司,进行派件扫描；派送业务员：赵帅；联系电话：15699710961\",\"ftime\":\"2014-11-19 08:01:39\",\"time\":\"2014-11-19 08:01:39\"},{\"context\":\"北京海淀区北影公司,到达目的地网点，快件将很快进行派送\",\"ftime\":\"2014-11-19 06:47:03\",\"time\":\"2014-11-19 06:47:03\"},{\"context\":\"北京海淀区北影公司,进行快件扫描\",\"ftime\":\"2014-11-19 04:28:40\",\"time\":\"2014-11-19 04:28:40\"},{\"context\":\"北京分拨中心,从站点发出，本次转运目的地：北京海淀区北影公司,北京海淀区北影公司\",\"ftime\":\"2014-11-18 09:04:14\",\"time\":\"2014-11-18 09:04:14\"},{\"context\":\"北京分拨中心,进行大包拆包扫描\",\"ftime\":\"2014-11-18 06:58:05\",\"time\":\"2014-11-18 06:58:05\"},{\"context\":\"北京分拨中心,在分拨中心进行卸车扫描\",\"ftime\":\"2014-11-18 05:28:04\",\"time\":\"2014-11-18 05:28:04\"},{\"context\":\"福建晋江分拨中心,进行装车扫描，即将发往：北京分拨中心,北京分拨中心\",\"ftime\":\"2014-11-14 20:27:12\",\"time\":\"2014-11-14 20:27:12\"},{\"context\":\"福建晋江分拨中心,在分拨中心进行称重扫描\",\"ftime\":\"2014-11-14 20:23:44\",\"time\":\"2014-11-14 20:23:44\"},{\"context\":\"福建龙岩公司,进行下级地点扫描，将发往：北京网点包,北京网点包\",\"ftime\":\"2014-11-13 15:35:13\",\"time\":\"2014-11-13 15:35:13\"},{\"context\":\"福建龙岩公司,进行揽件扫描\",\"ftime\":\"2014-11-12 22:45:14\",\"time\":\"2014-11-12 22:45:14\"}],\"state\":\"0\",\"ischeck\":\"0\",\"com\":\"yunda\",\"status\":\"200\"}",-----详情
	 * "traceStatus":"0"---签收类型  0未签收  1已签收
	 * }}],"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.ParLogisticSearchController.list.008 -  用户channelId为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.list.011 -  expType为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String list(Long channelId, String expType) {
		if (PubMethod.isEmpty(channelId)) {
			return paramsFailure("ParLogisticSearchController.list.008", "用户channelId为必填项");
		}
		if (PubMethod.isEmpty(expType)) {
			return paramsFailure("ParLogisticSearchController.list.011", "expType为必填项");
		}
		List<ParLogisticSearch> list = null;
		try {
			list = parLogisticSearchService.list(channelId, expType);
			return this.jsonSuccess(list);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>(分页)个人端查询所有我收（发）的快递</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10下午3:06:15</dd>
	 * @param channelId	用户ID
	 * @param expType	0发快递  1收快递
	 * @param page
	 * @return	{"data":[{"netName":"韵达快递",---快递公司名称
	 * "parLogisticSearch":{"channelId":14319912592870400,----渠道ID  也就是用户ID
	 * "channelNo":"02",----渠道编号 01 好递个人端   02 好递接单王
	 * "createdTime":1416389801000,----创建时间
	 * "expType":"0",---快递类型 0:发快递  1:收快递
	 * "expWaybillNum":"3100116782440",-----运单号
	 * "id":14322515221151744,----主键
	 * "modifiedTime":1416389801000,---修改时间
	 * "netCode":"yunda",----快递100快递公司代码
	 * "netId":1504,----快递公司ID 
	 * "systemMark":"01",---系统标识 0个人端  1 电商管家
	 * "traceDetail":"{\"nu\":\"3100116782440\",\"updatetime\":\"2014-11-19 17:36:38\",\"condition\":\"H100\",\"message\":\"ok\",\"data\":[{\"context\":\"北京海淀区北影公司,进行派件扫描；派送业务员：赵帅；联系电话：15699710961\",\"ftime\":\"2014-11-19 08:01:39\",\"time\":\"2014-11-19 08:01:39\"},{\"context\":\"北京海淀区北影公司,到达目的地网点，快件将很快进行派送\",\"ftime\":\"2014-11-19 06:47:03\",\"time\":\"2014-11-19 06:47:03\"},{\"context\":\"北京海淀区北影公司,进行快件扫描\",\"ftime\":\"2014-11-19 04:28:40\",\"time\":\"2014-11-19 04:28:40\"},{\"context\":\"北京分拨中心,从站点发出，本次转运目的地：北京海淀区北影公司,北京海淀区北影公司\",\"ftime\":\"2014-11-18 09:04:14\",\"time\":\"2014-11-18 09:04:14\"},{\"context\":\"北京分拨中心,进行大包拆包扫描\",\"ftime\":\"2014-11-18 06:58:05\",\"time\":\"2014-11-18 06:58:05\"},{\"context\":\"北京分拨中心,在分拨中心进行卸车扫描\",\"ftime\":\"2014-11-18 05:28:04\",\"time\":\"2014-11-18 05:28:04\"},{\"context\":\"福建晋江分拨中心,进行装车扫描，即将发往：北京分拨中心,北京分拨中心\",\"ftime\":\"2014-11-14 20:27:12\",\"time\":\"2014-11-14 20:27:12\"},{\"context\":\"福建晋江分拨中心,在分拨中心进行称重扫描\",\"ftime\":\"2014-11-14 20:23:44\",\"time\":\"2014-11-14 20:23:44\"},{\"context\":\"福建龙岩公司,进行下级地点扫描，将发往：北京网点包,北京网点包\",\"ftime\":\"2014-11-13 15:35:13\",\"time\":\"2014-11-13 15:35:13\"},{\"context\":\"福建龙岩公司,进行揽件扫描\",\"ftime\":\"2014-11-12 22:45:14\",\"time\":\"2014-11-12 22:45:14\"}],\"state\":\"0\",\"ischeck\":\"0\",\"com\":\"yunda\",\"status\":\"200\"}",-----详情
	 * "traceStatus":"0"---签收类型  0未签收  1已签收
	 * }}],"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.ParLogisticSearchController.pgeList.018 -  用户channelId为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.pageList.021 -  expType为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/pageList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String pageList(Page page, Long channelId, String expType) {
		if (PubMethod.isEmpty(channelId)) {
			return paramsFailure("ParLogisticSearchController.pageList.018", "用户channelId为必填项");
		}
		if (PubMethod.isEmpty(expType)) {
			return paramsFailure("ParLogisticSearchController.pageList.021", "expType为必填项");
		}
		if (PubMethod.isEmpty(page)) {
			page = new Page();
			page.setPageSize(1000);
		}
		List<ParLogisticSearch> list = null;
		try {
			List keyList = null;
			String myKey = channelId + "_" + page.getCurrentPage() + "_" + page.getPageSize() + "_" + expType;
			list = parLogisticSearchService.listByPage(page, channelId, expType);
			page.setItems(list);
			if (PubMethod.isEmpty(keyList)) {
				keyList = new ArrayList();
			}
			keyList.add(myKey);
			return this.jsonSuccess(page);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除单条记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:07:12</dd>
	 * @param id	主键ID
	 * @param channelId	用户ID
	 * @param netId		快递公司Id
	 * @param expType	0发快递  1收快递
	 * @return	JSON	成功success:true 失败：success:false
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>openapi.ParLogisticSearchController.deleteParcelTrack.009 -  用户channelId为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.deleteParcelTrack.010 -  Id为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.deleteParcelTrack.012 -  netId为必填项 </dd>
	 * <dd>openapi.ParLogisticSearchController.deleteParcelTrack.013 -  expType为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteParcelTrack(Long id, Long channelId, Long netId, String expType) {
		if (PubMethod.isEmpty(channelId)) {
			return paramsFailure("ParLogisticSearchController.deleteParcelTrack.009", "用户channelId为必填项");
		}
		if (PubMethod.isEmpty(id)) {
			return paramsFailure("ParLogisticSearchController.deleteParcelTrack.010", "Id为必填项");
		}
		if (PubMethod.isEmpty(netId)) {
			return paramsFailure("ParLogisticSearchController.deleteParcelTrack.012", "netId为必填项");
		}
		if (PubMethod.isEmpty(expType)) {
			return paramsFailure("ParLogisticSearchController.deleteParcelTrack.013", "expType为必填项");
		}
		try {
			parLogisticSearchService.deleteById(id, channelId, netId, expType);

			//			List<String> keyList=this.ehcacheService.get("parlogisticSearchCache",channelId+"", List.class);
			//			if(!PubMethod.isEmpty(keyList)){
			//				this.ehcacheService.remove("parlogisticSearchCache", keyList);
			//			}
			return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}

	}

	@RequestMapping(value = "/findIdListPerFourHour", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String findIdListPerFourHour() {
		try {
			List<Long> listl = parLogisticSearchService.findIdListPerFourHour();
			return this.jsonSuccess(listl);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}

	/**
	 * 查询给定id的包裹物流信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-3 上午9:24:56</dd>
	 * @param ids	使用,分割开
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/findParLogistic", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String findParLogistic(String ids) {
		try {
			List<ParLogisticSearch> listl = parLogisticSearchService.findParLogistic(ids);
			return this.jsonSuccess(listl);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}

	/**
	 * 4小时定时推送更新物流信息
	 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-3-3 上午9:24:32</dd>
	 * @param jsonData
	 * @since v1.0
	 */
	@RequestMapping(value = "/batchUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void batchUpdate(String jsonData) {
		try {
			parLogisticSearchService.batchUpdate(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/insertOrUpdateByMemberIdNetBill", method = { RequestMethod.GET, RequestMethod.POST })
	public void insertParLogic(String memberId,String netId,String expWaybillNum,String recMobile,String traceStatus,String traceDetail) {
		try {
			parLogisticSearchService.insertOrUpdateByMemberIdNetBill(memberId,netId,expWaybillNum,recMobile,traceStatus,traceDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 寻找未推送的推送数据(push_mark = 1)
	 */
	@ResponseBody
	@RequestMapping(value = "/findUnPushed", method = { RequestMethod.GET, RequestMethod.POST })
	public String findUnPushed() {
		try {
			List<ParLogisticSearch> lsPar = parLogisticSearchService.findUnPushed();
			return jsonSuccess(lsPar);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 根据传过来的id更新掉推送的标记
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePushData", method = { RequestMethod.GET, RequestMethod.POST })
	public String updatePushData(String ids) {
		try {
			parLogisticSearchService.updatePushData(ids);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
