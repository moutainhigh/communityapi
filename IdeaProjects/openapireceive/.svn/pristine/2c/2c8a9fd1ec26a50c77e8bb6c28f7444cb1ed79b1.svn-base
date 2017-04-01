package net.okdi.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.service.NetInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.common.page.PageUtil;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 网络信息
 * @author shihe.zhai
 * @version V1.0
 */
@Controller
@RequestMapping("/netInfo")
public class NetInfoController extends BaseController {
	@Autowired
	private NetInfoService netInfoService;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private RedisService redisService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>首字母分组查询所有快递网络</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:57:00</dd>
	 * @return {"data":{"A":[{"code":"aae","firstLetter":"A","netId":1526,"netName":"AAE全球专递"},
	 * {"code":"annengwuliu","firstLetter":"A","netId":2000,"netName":"ANE安能物流"}],
	 * "B":[{"code":"balunzhi","firstLetter":"B","netId":2004,"netName":"巴伦支快递"},
	 * {"code":"xiaohongmao","firstLetter":"B","netId":2005,"netName":"北青小红帽"},
	 * {"code":"baifudongfang","firstLetter":"B","netId":2002,"netName":"百福东方物流"},
	 * {"code":"bangsongwuliu","firstLetter":"B","netId":2003,"netName":"邦送物流"},
	 * {"code":"huitongkuaidi","firstLetter":"B","netId":1503,"netName":"百世汇通"},
	 * {"code":"bht","firstLetter":"B","netId":2001,"netName":"BHT国际快递"}]...},"success":true}
		code:代码标识
		firstLetter:首字母
		netId:所属网络id
		netName:所属网络名称
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getNetFirstLetter", method = { RequestMethod.POST, RequestMethod.GET })
	public String getNetFirstLetter() {
		try {
			return jsonSuccess(this.netInfoService.getNetFirstLetter());
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据网络ID和省份ID获取网络报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:39:53</dd>
	 * @param netId 网络ID
	 * @param provinceId 收件地址省份ID
	 * @return {"success":true,"data":{"netPriceList":[{"continueWeight":5,"firstFreight":10,"province":"甘肃省"}]}}
	 * <dd>firstFreight -  首重价格 </dd>
	 * <dd>continueWeight -  续重价格 </dd>
	 * <dd>province -  派件省份名称 </dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>NetInfoController.getNetPrice.001 - 网络ID为必填项</dd>
     * <dd>NetInfoController.getNetPrice.002 - 省份ID为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/getNetPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String getNetPrice(Long netId, Long provinceId) {
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("NetInfoController.getNetPrice.001","网络ID为必填项");
		}
		if(PubMethod.isEmpty(provinceId)){
			return paramsFailure("NetInfoController.getNetPrice.002","省份ID为必填项");
		}
		try {
			return jsonSuccess(this.netInfoService.getNetQuote(netId,provinceId));
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新网络信息缓存</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-28 下午04:37:35</dd>
	 * @param netId 网络ID
	 * @param netValue 网络信息
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>NetInfoController.putNetCache.001 - 网络ID为必填项</dd>
     * <dd>NetInfoController.putNetCache.002 - 网络信息为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/putNetCache", method = { RequestMethod.POST, RequestMethod.GET })
	public String putNetCache(Long netId, String netValue) {
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("NetInfoController.putNetCache.001","网络ID为必填项");
		}
		if(PubMethod.isEmpty(netValue)){
			return paramsFailure("NetInfoController.putNetCache.002","网络信息为必填项");
		}
		try {
			this.ehcacheService.put("netCache", netId.toString(), netValue);
			return jsonSuccess();
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 查询网络信息(所有)
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNetInfoAll", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNetInfoAll() {
		try {
			List<BasNetInfo> listBasInfo=new ArrayList<BasNetInfo>();
			listBasInfo=netInfoService.queryNetInfoAll();
			return jsonSuccess2(listBasInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Description: 修改网络信息(所有)
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/updateNetInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateNetInfo(Long netId,String netName,Short netType,String description,String netNum,String url,String telephone,String code,String verifyCodeFlag ) {
		
		Map<String,Object> map=new HashMap<String,Object>();		
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("CompInfoController.updateNetInfo.001","netId为必填项");
		}
		map.put("netId",netId);
		if(!PubMethod.isEmpty(netName)){
			map.put("netName",netName);
		}
		if(!PubMethod.isEmpty(netType)){
			map.put("netType",netType);
		}
		if(!PubMethod.isEmpty(description)){
			map.put("description",description);
		}
		if(!PubMethod.isEmpty(netNum)){
			map.put("netNum",netNum);
		}
		if(!PubMethod.isEmpty(url)){
			map.put("url",url);
		}
		if(!PubMethod.isEmpty(telephone)){
			map.put("telephone",telephone);
		}
		if(!PubMethod.isEmpty(code)){
			map.put("code",code);
		}
		if(!PubMethod.isEmpty(verifyCodeFlag)){
			if ( !"0".equals(verifyCodeFlag) && !"1".equals(verifyCodeFlag)){
				return paramsFailure("CompInfoController.updateNetInfo.001","verifyCodeFlag只能为0或1");
			}
			map.put("verifyCodeFlag",verifyCodeFlag);
		}
		try {
			netInfoService.updateNetInfo(map);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Method 添加网络信息
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-13 下午5:49:05
	 * @param netName
	 * @param netType
	 * @param description
	 * @param netNum
	 * @param url
	 * @param telephone
	 * @param code
	 * @param verifyCodeFlag
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertBasNetInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String insertBasNetInfo(String netName,Short netType,String description,String netNum,String url,String telephone,String code,String verifyCodeFlag) {
		if(PubMethod.isEmpty(netName)){
			return paramsFailure("NetInfoController.insertBasNetInfo.001","网络名称为必填项");
		}
		if(PubMethod.isEmpty(netType)){
			return paramsFailure("NetInfoController.insertBasNetInfo.002","网络类型为必填项");
		}
		if(PubMethod.isEmpty(code)){
			return paramsFailure("NetInfoController.insertBasNetInfo.003","快递100使用的代码字段为必填项");
		}
		if(PubMethod.isEmpty(url)){
			return paramsFailure("NetInfoController.insertBasNetInfo.004","网址URL为必填项");
		}
		if(PubMethod.isEmpty(telephone)){
			return paramsFailure("NetInfoController.insertBasNetInfo.005","网址电话为必填项");
		}
		try {
			return jsonSuccess(this.netInfoService.insertBasNetInfo(netName, netType, description, netNum, url, telephone, code,verifyCodeFlag));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Method 启用/停用 网络信息
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-14 下午2:15:22
	 * @param netId
	 * @param deleteMark
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/makeBasNetInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String makeBasNetInfo(long netId,Short deleteMark){
		System.out.println(deleteMark);
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("NetInfoController.makeBasNetInfo.001","网络ID为必填项");
		}
		if (PubMethod.isEmpty(deleteMark) || (deleteMark != 0 && deleteMark != 1)) {
			return paramsFailure("NetInfoController.makeBasNetInfo.002","删除标示为必填项");
		}
		try{
		this.netInfoService.makeBaseNetInfo(netId, deleteMark);
		return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * @Description: 查询网络信息详情
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNetInfoById", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNetInfoById(Long netId) {
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("NetInfoController.queryNetInfoById.001","网络ID为必填项");
		}
		try {
			BasNetInfo basNetInfo=netInfoService.queryNetInfoById(netId);
			return jsonSuccess2(basNetInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @Method  判断网络列表是否更新
	 * @author AiJun.Han
	 * @Description
	 * @data 2015-10-14 下午7:16:13
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validNetIsChanged", method = { RequestMethod.POST, RequestMethod.GET })
	public String validNetIsChanged() {
		try {
			String date = redisService.get("myvalid_validNetIsChanged", "validDate",String.class);
			return jsonSuccess2(date);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * @Description: 更新快递网络的合作伙伴
	 * @author 杨凯
	 * @date 2015-10-13下午15:23:24
	 * @return BasNetInfo
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePartners", method = { RequestMethod.POST, RequestMethod.GET })
	public String updatePartners(Long netId, Short isPartners) {
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("NetInfoController.updatePartners.001","网络ID为必填项");
		}
		if(PubMethod.isEmpty(isPartners)){
			return paramsFailure("NetInfoController.queryNetInfoById.002","合作关系为必填项");
		} else if (0 != isPartners && 1 != isPartners) {
			return paramsFailure("NetInfoController.queryNetInfoById.003","合作关系只能为1或0");
		}
		try {
			Integer flag=this.netInfoService.updatePartners(netId, isPartners);
			return jsonSuccess2(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}
