package net.okdi.api.controller;

import java.util.List;
import java.util.Map;

import net.okdi.api.service.AddressService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 地址信息
 * @author shihe.zhai
 * @version V1.0 
 */
@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private EhcacheService ehcacheService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>地址控件联想六七级地址</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:45:23</dd>
	 * @param townId 乡镇（五级地址）ID
	 * @param keyword 地址关键字
	 * @param count 查询数量
	 * @return [{"addName":"田村路43号","addId":"300204889"},{"addName":"田村路43号京粮物流","addId":"300204890"}]
     * <dd>addId -  地址ID </dd>
     * <dd>addName -  地址 </dd>
	 * @since v1.0
	 */
    @ResponseBody
    @RequestMapping(value = "/queryRelevantAddressList", method = {RequestMethod.POST, RequestMethod.GET})
    public String queryRelevantAddressList(Long townId, String keyword, Integer count) {
        try {
            return this.addressService.queryRelevantAddressList(townId, keyword, count);
        } catch (Exception e) {
            return jsonFailure(e);
        }
    }
    /**
     * 
     * <dt><span class="strong">方法描述:</span></dt><dd>查询网络覆盖超区地址</dd>
     * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
     * <dt><span class="strong">时间:</span></dt><dd>2014-11-22 下午5:03:47</dd>
     * @param netId 网络id
     * @param addressId 地址id
     * @return  {"data":{"coverAddress":[{"ADDRESS_ID":110101,"ADDRESS_NAME":"东城区"},
     * {"ADDRESS_ID":110102,"ADDRESS_NAME":"西城区"},{"ADDRESS_ID":110105,"ADDRESS_NAME":"朝阳区"},
     * {"ADDRESS_ID":110106,"ADDRESS_NAME":"丰台区"},{"ADDRESS_ID":110107,"ADDRESS_NAME":"石景山区"},
     * {"ADDRESS_ID":110108,"ADDRESS_NAME":"海淀区"},{"ADDRESS_ID":110109,"ADDRESS_NAME":"门头沟区"},
     * {"ADDRESS_ID":110111,"ADDRESS_NAME":"房山区"},{"ADDRESS_ID":110112,"ADDRESS_NAME":"通州区"},
     * {"ADDRESS_ID":110113,"ADDRESS_NAME":"顺义区"},{"ADDRESS_ID":110114,"ADDRESS_NAME":"昌平区"},
     * {"ADDRESS_ID":110115,"ADDRESS_NAME":"大兴区"}],"exceedAddress":[{"ADDRESS_ID":110116,"ADDRESS_NAME":"怀柔区"},
     * {"ADDRESS_ID":110117,"ADDRESS_NAME":"平谷区"}]},"success":true} ADDRESS_ID 地址id  ADDRESS_NAME 地址名称
     * @since v1.0
     */
    @ResponseBody
    @RequestMapping(value = "/getCoverExceedAddress", method = {RequestMethod.POST, RequestMethod.GET})
    public String getCoverExceedAddress(Long netId,Long addressId) {
        try {
        	Map cacheMap = ehcacheService.get("netCoverExceedAddress", netId+"-"+addressId, Map.class);
        	if(!PubMethod.isEmpty(cacheMap)){
        		jsonSuccess(cacheMap);
        	}
            return jsonSuccess(this.addressService.getCoverExceedAddress(netId,addressId));
        } catch (Exception e) {
            return jsonFailure(e);
        }
    }
}
