package net.okdi.apiV3.controller;

import net.okdi.apiV3.service.QueryAddressInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wechat")
public class WechatAddressController extends BaseController {

	@Autowired
	QueryAddressInfoService queryAddressInfoService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>微信端根据父Id查询所属地址列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jianxin.ma</dd>
	 * <dt><span class="strong">时间:</span></dt><dd></dd>
	 * @param memerId 
	 * @param taskId
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAddressInfo", method = {RequestMethod.GET , RequestMethod.POST })
	public String queryAddressInfo(Long parentId) {
		if (PubMethod.isEmpty(parentId)) {
			return paramsFailure("WechatAddressController.queryAddressInfo.parentId", "parentId不能为空");
		}
		try{
			return jsonSuccess(queryAddressInfoService.queryAddressInfo(parentId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	
	/**
	 * 
	 * @Description: 微信端添加地址薄
	 * @param memberId 
	 * @param senderName
	 * @param senderPhone
	 * @param addresseeTownId
	 * @param addresseeTownName
	 * @param addresseeAddress
	 * @param defaultMark 0默认  1非默认
	 * @param flag 地址类型 1:收件地址 2:发件地址 
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-12
	 */
	@ResponseBody
	@RequestMapping(value = "/addWechatAddressInfo", method = {RequestMethod.GET , RequestMethod.POST })
	public String addWechatAddressInfo(Long memberId,String senderName,
			String senderPhone,Long addresseeTownId,String addresseeTownName,
			String addresseeAddress,Short defaultMark,Short flag) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("WechatAddressController.addWechatAddressInfo.memberId", "memberId不能为空");
		}
		try{
			return jsonSuccess(queryAddressInfoService.addWechatAddressInfo(memberId, senderName, senderPhone, addresseeTownId, addresseeTownName, addresseeAddress, defaultMark,flag));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	/**
	 * 
	 * @Description: 删除地址
	 * @param memberId
	 * @param addressId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-12
	 */
	@ResponseBody
	@RequestMapping("/deleteWechatAddressInfo")
	public String deleteWechatAddressInfo(Long memberId,Long addressId){
		if(PubMethod.isEmpty(addressId)){
			return paramsFailure("WechatAddresseeController.deleteAddressInfo.001","地址Id不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("WechatAddresseeController.deleteAddressInfo.001","memberId不能为空");
		}
		try{
			return jsonSuccess(queryAddressInfoService.deleteWechatAddressInfo(memberId, addressId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	/**
	 * 
	 * @Description: 查看默认地址
	 * @param memberId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-12
	 */
	@ResponseBody
	@RequestMapping("/queryWechatDefaultAddress")
	public String queryWechatDefaultAddress(Long memberId,Short flag){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("WechatAddresseeController.queryDefaultAddress.memberId","memberId不能为空");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("WechatAddresseeController.queryDefaultAddress.flag","flag不能为空");
		}
		try{
			return jsonSuccess(queryAddressInfoService.queryWechatDefaultAddress(memberId,flag));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	/**
	 * 
	 * @Description: 查询地址薄
	 * @param memberId
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-12
	 */
	@ResponseBody
	@RequestMapping("/getWechatAddressInfo")
	public String getWechatAddressInfo(Long memberId,Short flag){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("WechatAddresseeController.getAddressInfo.memberId","memberId不能为空");
		}
		try{
			return jsonSuccess(queryAddressInfoService.getWechatAddressInfo(memberId,flag));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	/**
	 * 
	 * @Description: 修改地址
	 * @param memberId
	 * @param addressId
	 * @param addresseeName
	 * @param addresseeTownId
	 * @param addresseePhone
	 * @param addresseeTownName
	 * @param addresseeAddress
	 * @param longitude
	 * @param latitude
	 * @param defaultMark
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-12
	 */
	@ResponseBody
	@RequestMapping("/editWechatAddressInfo")
	public String editWechatAddressInfo(Long memberId,Long addressId,String senderName,
			String senderPhone,Long addresseeTownId,String addresseeTownName,
			String addresseeAddress,Short defaultMark ,Short flag){
		if(PubMethod.isEmpty(addressId)){
			return paramsFailure("WechatAddresseeController.editWechatAddressInfo.001","地址id不能为空");
		}
		try{
			return jsonSuccess(queryAddressInfoService.editWechatAddressInfo(memberId, addressId, senderName, senderPhone, addresseeTownId, addresseeTownName, addresseeAddress, defaultMark ,flag));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	/**
	 * 
	 * @Description: 设置默认地址
	 * @param memberId
	 * @param flag
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-5-3
	 */
	@ResponseBody
	@RequestMapping("/modifyDefault")
	public String modifyDefault(Long memberId ,Long addressId,Short flag){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("WechatAddresseeController.modifyDefault.001","memberId不能为空");
		}
		if(PubMethod.isEmpty(addressId)){
			return paramsFailure("WechatAddresseeController.modifyDefault.002","addressId不能为空");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("WechatAddresseeController.modifyDefault.003","flag不能为空");
		}
		try{
			queryAddressInfoService.modefyDefaultAddress(memberId, addressId, flag);
			return jsonSuccess();
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}
	
}