package net.okdi.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParLogisticTrace;
import net.okdi.api.service.ParLogisticTraceService;
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
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author dong.zhang
 * @version V1.0
 */
@Controller
@RequestMapping("/logisticTrace")
public class ParLogisticTraceController extends BaseController {
	/**
	 * 1：需要推送
	 */
	private final String SEND_NOTICE_PUSH = "1";
	/**
	 * 0：不需要推送
	 */
	private final String SEND_NOTICE_NOTPUSH = "0";

	@Autowired
	private ParLogisticTraceService parLogisticTraceService;
	@Autowired
	private EhcacheService ehcacheService;

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>个人端添加或更新查件记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:09:44</dd>
	 * @param param	多个快递单号 物流名称 时间（已经没用）物流明细信息连接起来的字符串
	 * @param casMemberId	用户ID
	 * @return	JSON 成功 success:true 失败 success:false
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.ParLogisticTraceController.saveParcelTracskMobile.001 -  param为必填项 </dd>
     * <dd>openapi.ParLogisticTraceController.saveParcelTracskMobile.002 -  用户IDcasMemberId为必填项 </dd>
	 * @since v1.0
	 */
	@Deprecated
	@RequestMapping(value = "/save/mobile", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String saveParcelTracskMobile(String param, Long casMemberId) {
		if(PubMethod.isEmpty(param)){
			return paramsFailure("ParLogisticTraceController.saveParcelTracskMobile.001","param为必填项");
		}
		if(PubMethod.isEmpty(casMemberId)){
			return paramsFailure("ParLogisticTraceController.saveParcelTracskMobile.002","用户IDcasMemberId为必填项");
		}
		try {
			parLogisticTraceService.saveOrUpdate(param, casMemberId, SEND_NOTICE_PUSH);
			return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}

	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>好递网添加或更新查件记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:09:44</dd>
	 * @param param	多个快递单号 物流名称 时间（已经没用）物流明细信息连接起来的字符串
	 * @param casMemberId	用户ID
	 * @return	JSON	成功 success:true 失败 success:false
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.ParLogisticTraceController.saveParcelTracskOkdi.004 -  param为必填项 </dd>
     * <dd>openapi.ParLogisticTraceController.saveParcelTracskOkdi.005 -  用户IDcasMemberId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/save/okdi", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String saveParcelTracskOkdi(String param, Long casMemberId) {
		if(PubMethod.isEmpty(param)){
			return paramsFailure("ParLogisticTraceController.saveParcelTracskOkdi.004","param为必填项");
		}
		if(PubMethod.isEmpty(casMemberId)){
			return paramsFailure("ParLogisticTraceController.saveParcelTracskOkdi.005","用户IDcasMemberId为必填项");
		}
		try {
			parLogisticTraceService.saveOrUpdate(param, casMemberId, SEND_NOTICE_NOTPUSH);
			List<String> keyList=this.ehcacheService.get("parlogisticTraceCache",casMemberId+"", List.class);
			if(!PubMethod.isEmpty(keyList)){
				this.ehcacheService.remove("parlogisticTraceCache", keyList);
			}
			return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}

	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据IDS批量删除查件记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:11:29</dd>
	 * @param ids	id用逗号拼接起来的字符串
	 * @return	JSON 成功 success:true 失败 success:false
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.ParLogisticTraceController.deleteParcelTrack.007 -  ids为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String deleteParcelTrack(String ids,Long casMemberId) {
		if(PubMethod.isEmpty(ids)){
			return paramsFailure("ParLogisticTraceController.deleteParcelTrack.007","ids为必填项");
		}
		try {
			parLogisticTraceService.deleteByIds(ids);
			List<String> keyList=this.ehcacheService.get("parlogisticTraceCache",casMemberId+"", List.class);
			if(!PubMethod.isEmpty(keyList)){
				this.ehcacheService.remove("parlogisticTraceCache", keyList);
			}
			return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}

	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>不分页查询查件记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:12:09</dd>
	 * @param casMemberId	用户ID
	 * @param traceStatus	0未签收 1签收
	 * @return	{"data":[{"SEND_NOTICE_FLAG":"0",----0不推送  1推送
	 * "casMemberId":13985164134973440,-----人ID
	 * "clientNetNum":"yuantong",----物流名称
	 * "clientTraceStatu":"1",-----0未签收 1签收
	 * "expWaybillNum":"5717095188",----快递单号
	 * "id":14366810924646400,---主键id
	 * "modifyTime":1416814943000,----更新时间
	 * "netId":1,-----网路ID
	 * "traceDetail":"客户 签收人 : 王 已签收",-----物流明细信息
	 * "traceStatus":"1"----0未签收   1签收
	 * }],"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.ParLogisticTraceController.list.008 -  用户IDcasMemberId为必填项 </dd>
     * <dd>openapi.ParLogisticTraceController.list.009 -  状态traceStatus为必填项 </dd>
	 * @since v1.0
	 */
	@Deprecated
	@RequestMapping(value = "/list",method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String list(Long casMemberId, String traceStatus) {
		if(PubMethod.isEmpty(casMemberId)){
			return paramsFailure("ParLogisticTraceController.list.008","用户IDcasMemberId为必填项");
		}
		if(PubMethod.isEmpty(traceStatus)){
			return paramsFailure("ParLogisticTraceController.list.009","状态traceStatus为必填项");
		}
		try {
			List<ParLogisticTrace> list = parLogisticTraceService.list(casMemberId, traceStatus);
			return this.jsonSuccess(list);

		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>分页查询插件记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:12:42</dd>
	 * @param page currentPage当前页    hasFirst是否有首页  hasLast是否有尾页  hasNext是否有下一页   hasPre是否有上一页
	 * offset开始位置   pageCount总的页数   pageSize每页显示记录的条数    total总的记录条数
	 * @param casMemberId	用户ID
	 * @param traceStatus	0未签收 1签收
	 * @return	{"data":{"currentPage":1,----当前页
	 * "hasFirst":false,---是否有首页
	 * "hasLast":false,---是否有尾页
	 * "hasNext":false,----是否有下一页
	 * "hasPre":false,----是否有上一页
	 * "items":[{"clientNetNum":"yuantong",---物流名称
	 * "clientTraceStatu":"1",------0未签收 1签收
	 * "expWaybillNum":"5717095188",---快递单号
	 * "id":14438480206626816,---主键
	 * "modifyTime":1416832172000----更改时间
	 * }],"offset":0,---开始位置
	 * "pageCount":1,--总的页数
	 * "pageSize":10,---每页显示记录的条数
	 * "total":1---总的记录条数
	 * },"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.ParLogisticTraceController.pageList.010 -  状态traceStatus为必填项 </dd>
     * <dd>openapi.ParLogisticTraceController.pageList.011 -  casMemberId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/pageList",method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String listByPage(Page page, Long casMemberId, String traceStatus) {
		if(PubMethod.isEmpty(traceStatus)){
			return paramsFailure("ParLogisticTraceController.pageList.010","状态traceStatus为必填项");
		}
		if(PubMethod.isEmpty(casMemberId)){
			return paramsFailure("ParLogisticTraceController.pageList.011","casMemberId为必填项");
		}

		List<ParLogisticTrace> list = null;
		try {
			List keyList=null;
			String myKey=casMemberId+"_"+page.getCurrentPage()+"_"+page.getPageSize()+"_"+traceStatus;
		    String pageString = this.ehcacheService.get("parlogisticTraceCache",myKey, String.class);
		    Page ehpage =  JSON.parseObject(pageString, Page.class);
		    if(PubMethod.isEmpty(ehpage)){
			list = parLogisticTraceService.listByPage(page, casMemberId, traceStatus);
			 page.setItems(list);
			this.ehcacheService.put("parlogisticTraceCache",myKey,page);
			keyList=this.ehcacheService.get("parlogisticTraceCache", casMemberId+"",List.class);
			if(PubMethod.isEmpty(keyList)){
				keyList=new ArrayList();
			}
			keyList.add(myKey);
			this.ehcacheService.put("parlogisticTraceCache",casMemberId+"",keyList);
			}else{
			page=ehpage;
			}
		    Map<String,Object> map = new HashMap<String,Object>();
		    map.put("success", true);
		    map.put("data", page);
			return JSON.toJSONStringWithDateFormat(map, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
}
