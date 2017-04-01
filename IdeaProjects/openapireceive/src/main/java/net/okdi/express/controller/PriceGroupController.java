package net.okdi.express.controller;

import java.math.BigDecimal;

import net.okdi.api.entity.ExpPriceGroup;
import net.okdi.api.service.PriceGroupService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 优惠组信息
 * @author shihe.zhai
 * @version V1.0
 */
@Controller
@RequestMapping("/priceGroup")
public class PriceGroupController extends BaseController {
	@Autowired
	private PriceGroupService priceGroupService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>新增优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午12:47:46</dd>
	 * @param groupName 优惠组名称
	 * @param discountPercentage 折扣信息
	 * @param compId 网带Id
	 * @param netId 网络ID
	 * @return {"data":{"compId":14383265874968576,"discountPercentage":12.00,"groupName":"打折优惠价格组",
	 * "id":124716039495680,"netId":1500},"success":true}
	 * compId 网点ID discountPercentage打折信息 null为不打折 groupName优惠组名 id优惠组ID netId网络ID
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.PriceGroupServiceImpl.savePriceGroup.001", "新增优惠组，获取登录信息异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.savePriceGroup.002", "新增优惠组，groupName参数非空异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.savePriceGroup.003", "新增优惠组，discountPercentage参数异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.savePriceGroup.004", "新增优惠组，已存在同名优惠组"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/savePriceGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public String savePriceGroup(String groupName,Double discountPercentage,Long compId,Long netId) {
		try {
			return jsonSuccess(this.priceGroupService.savePriceGroup(groupName, discountPercentage, compId, netId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:56:17</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.PriceGroupServiceImpl.deletePriceGroup.001", "删除优惠组，获取登录信息异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.deletePriceGroup.002", "删除优惠组，groupId参数非空异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.deletePriceGroup.003", "删除优惠组，默认优惠组不能删除"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePriceGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public String deletePriceGroup(Long groupId,Long compId) {
		try {
			this.priceGroupService.deletePriceGroup(groupId, compId);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新优惠组</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:56:28</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @param groupName 优惠组名称
	 * @param discountPercentage 折扣信息
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.PriceGroupServiceImpl.updatePriceGroup.001", "更新优惠组，获取登录信息异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.updatePriceGroup.002", "更新优惠组，groupId参数非空异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.updatePriceGroup.003", "更新优惠组，groupName参数非空异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.updatePriceGroup.004", "更新优惠组，discountPercentage参数异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.updatePriceGroup.004", "更新优惠组，已存在同名优惠组"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.updatePriceGroup.005", "更新优惠组，默认优惠组不能修改"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceGroupById.002", "获取优惠组，优惠组信息获取异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePriceGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public String updatePriceGroup(Long groupId,Long compId,String groupName,Double discountPercentage) {
		try {
			this.priceGroupService.updatePriceGroup(groupId, compId, groupName, discountPercentage);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询网点优惠组列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:23:55</dd>
	 * @param compId 网点ID
	 * @return {"data":[{"group_name":"网络公开报价","id":88888888},{"group_name":"打折优惠价格组","id":124716039495680}],"success":true}
	 * 			id优惠组id  group_name优惠组名称
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.PriceGroupServiceImpl.getPriceGroupList.001", "查询网点优惠组列表，获取登录信息异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceGroupById.001", "获取优惠组，groupId参数非空异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceGroupById.002", "获取优惠组，优惠组信息获取异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPriceGroupList", method = { RequestMethod.POST, RequestMethod.GET })
	public String getPriceGroupList(Long compId) {
		try {
			return jsonSuccess(this.priceGroupService.getPriceGroupList(compId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断是否存在重名优惠组（自身除外）</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:51:53</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @param groupName 优惠组名称
	 * @return {"data":false,"success":true}  true存在  false不存在
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.PriceGroupServiceImpl.isExistSameNamePriceGroup.001", "判断是否存在重名优惠组，获取登录信息异常"</dd>
	 * <dd>"openapi.PriceGroupServiceImpl.isExistSameNamePriceGroup.002", "判断是否存在重名优惠组，groupName参数非空异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceGroupById.001", "获取优惠组，groupId参数非空异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceGroupById.002", "获取优惠组，优惠组信息获取异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/isExistSameNamePriceGroup", method = { RequestMethod.POST, RequestMethod.GET })
	public String isExistSameNamePriceGroup(Long groupId,Long compId,String groupName){
		try {
			return jsonSuccess(this.priceGroupService.isExistSameNamePriceGroup(groupId, compId, groupName));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据ID获取优惠组详细信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:44:33</dd>
	 * @param groupId 优惠组ID
	 * @return {"data":{"compId":14383265874968576,"discountPercentage":12.00,"groupName":"打折优惠价格组",
	 * "id":124716039495680,"netId":1500},"success":true}
	 * compId 网点ID discountPercentage打折信息 null为不打折 groupName优惠组名 id优惠组ID netId网络ID
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceGroupById.001", "获取优惠组，groupId参数非空异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceGroupById.002", "获取优惠组，优惠组信息获取异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPriceGroupById", method = { RequestMethod.POST, RequestMethod.GET })
	public String getPriceGroupById(Long groupId){
		try {
			return jsonSuccess(this.priceGroupService.getPriceGroupById(groupId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据优惠组ID获取报价信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:31:21</dd>
	 * @param groupId 优惠组ID
	 * @param compId 网点ID
	 * @return {"data":[{"city":",10,","continueInfo":"[{\"PRICE\":\"6.00/1.00\",\"WEIGHT\":\"1.01-10.00\"},
	 * {\"PRICE\":\"3.00/1.00\",\"WEIGHT\":\"10.01-\"}]","firstFreight":10.00,"firstPriceId":1,"firstWeight":1.00},
	 * {"city":",110108,13,","continueInfo":"[{\"PRICE\":\"5.00/1.00\",\"WEIGHT\":\"1.01-\"}]",
	 * "firstFreight":12.00,"firstPriceId":2,"firstWeight":1.00}],"success":true}
	 * city达到城市 continueInfo续重信息   PRICE续重价格(元/kg) WEIGHT续重范围（小值-大值）
	 * firstFreight首重价格 firstPriceId首重价格id firstWeight首重重量
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.PriceGroupServiceImpl.getPriceListByGroupId.001", "查询优惠组价格信息，获取登录信息异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceListByGroupId.002", "查询优惠组价格信息，groupId参数非空异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getPriceListByGroupId", method = { RequestMethod.POST, RequestMethod.GET })
	public String getPriceListByGroupId(Long groupId,Long compId){
		try {
			return jsonSuccess(this.priceGroupService.getPriceListByGroupId(groupId, compId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>判断是否存在网络公开报价优惠组价格</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 上午11:36:41</dd>
	 * @param compId 网点ID
	 * @return {"data":true,"success":true} true存在  false不存在
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.PriceGroupServiceImpl.isExistNetDefaultPrice.001", "判断是否存在网络公开报价优惠组价格，获取登录信息异常"</dd>
	 * <dd>"openapi.PriceGroupServiceImpl.getPriceListByGroupId.001", "查询优惠组价格信息，获取登录信息异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getPriceListByGroupId.002", "查询优惠组价格信息，groupId参数非空异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/isExistNetDefaultPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String isExistNetDefaultPrice(Long compId){
		try {
			return jsonSuccess(this.priceGroupService.isExistNetDefaultPrice(compId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询网络公开报价信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午12:50:42</dd>
	 * @param compId 网点ID
	 * @return {"data":{"fromAddressId":34,"fromAddressName":"安徽省",
	 * "priceInfo":[{"ADDRESS_NAME":"安徽省","continue_freight":5,"first_freight":10,"to_address_id":34},
	 * {"ADDRESS_NAME":"北京市","continue_freight":9,"first_freight":12,"to_address_id":11}]},"success":true}
	 * fromAddressId出港省市ID  fromAddressName出港省市名称   priceInfo价格信息   ADDRESS_NAME进港省市名称   continue_freight续重费用
	 * first_freight首重费用   to_address_id进港省市ID
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.PriceGroupServiceImpl.getNetOpenPrice.001", "查询网络公开报价列表，获取登录信息异常"</dd>
	 * <dd>"openapi.PriceGroupServiceImpl.getNetOpenPrice.002", "查询网络公开报价列表，获取录信息登异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getNetOpenPrice.003", "查询网络公开报价列表，获取网点地址信息异常"</dd>
     * <dd>"openapi.PriceGroupServiceImpl.getNetOpenPrice.004", "查询网络公开报价列表，获取网点地址信息异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/getNetOpenPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String getNetOpenPrice(Long compId){
		try {
			return jsonSuccess(this.priceGroupService.getNetOpenPrice(compId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>导入网络公开报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午12:56:52</dd>
	 * @param compId 网点ID
	 * @param netId 网络ID
	 * @return {"success":true}
	 * <dd>"openapi.PriceGroupServiceImpl.exportNetPrice.001", "导入快递公司网络报价，获取登录信息异常</dd>
	 * <dd>"openapi.PriceGroupServiceImpl.exportNetPrice.002", "导入快递公司网络报价，已存在网络报价不能导入"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/importNetPrice", method = { RequestMethod.POST, RequestMethod.GET })
	public String importNetPrice(Long compId,Long netId){
		try {
			this.priceGroupService.importNetPrice(compId,netId);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>设置价格</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-20 下午1:07:33</dd>
	 * @param compId 网点ID
	 * @param netId 网络ID
	 * @param groupId 优惠组ID
	 * @param firstFreight 首重费用
	 * @param firstWeight 首重重量
	 * @param continueInfo 续重信息  [{"PRICE":"6.00/1.00","WEIGHT":"1.01-10.00"},{"PRICE":"3.00/1.00","WEIGHT":"10.01-"}]"
	 * @param citys 城市ID “,”分隔
	 * @return {"success":true}
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/savePriceInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String savePriceInfo(Long compId,Long netId,Long groupId,BigDecimal firstFreight,BigDecimal firstWeight,String continueInfo,String citys){
		try {
			this.priceGroupService.savePriceInfo(groupId, compId, netId, firstFreight, firstWeight, continueInfo, citys);
			return jsonSuccess();
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
