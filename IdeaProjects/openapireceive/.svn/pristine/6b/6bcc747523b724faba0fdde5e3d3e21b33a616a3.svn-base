package net.okdi.express.controller;

import java.math.BigDecimal;

import net.okdi.api.entity.ExpCompElectronicFence;
import net.okdi.api.service.CompElectronicFenceService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 取派区域围栏
 * @author shihe.zhai
 * @version V1.0
 */
@Controller
@RequestMapping("/compElectronicFence")
public class CompElectronicFenceController extends BaseController {
	@Autowired
	private CompElectronicFenceService compElectronicFenceService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询网点取派区域围栏</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午1:10:45</dd>
	 * @param compId 网点id
	 * @return {"data":{"compCoordinateMap":{"latitude":39.989418,"longitude":116.464346,"mapLevel":16},
	 * "compFenceList":[{"id":14499399632158720,"pointList":[{"latitude":"39.992652","longitude":"116.460142"},
	 * {"latitude":"39.993619","longitude":"116.473509"},{"latitude":"39.98928","longitude":"116.460932"}]}]},"success":true}
	 * compCoordinateMap 初始化中心点 mapLevel地图放大等级 compFenceList取派范围 id取派范围主键 pointList取派范围坐标点 longitude 经度  latitude纬度
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompElectronicFenceServiceImpl.queryCompFence.001","查询网点取派范围异常，compId参数不能为空"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompFence", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompFence(Long compId){
		try{
			return jsonSuccess(this.compElectronicFenceService.queryCompFence(compId));
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加/更新网点取派区域</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午1:14:46</dd>
	 * @param id 取派区域ID
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitudeStr 经度串
	 * @param latitudeStr 纬度串
	 * @return {"data":{"compId":14431820821045248,"id":14499648467894272,"latitudeStr":"39.990303,39.990247,39.988091",
	 * "longitudeStr":"116.474335,116.479078,116.475269","maxLatitude":39.990303,"maxLongitude":116.479078,"minLatitude":39.988091,
	 * "minLongitude":116.474335,"netId":1500},"success":true}
	 * compId 网点ID id取派区域ID latitudeStr纬度串 longitudeStr经度串 netId网络ID
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.CompElectronicFenceController.addCompFence.001","添加取派范围电子围栏数据参数异常，netId不能为空"</dd>
     * <dd>"openapi.CompElectronicFenceController.addCompFence.002","添加取派范围电子围栏数据参数异常，compId不能为空"</dd>
     * <dd>"openapi.CompElectronicFenceServiceImpl.addCompFence.001","添加取派范围电子围栏数据参数异常，围栏坐标点经纬度对应错误"</dd>
     * <dd>"openapi.CompElectronicFenceServiceImpl.addCompFence.002","添加取派范围电子围栏数据参数异常，围栏坐标点数据格式错误"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addCompFence", method = { RequestMethod.POST, RequestMethod.GET })
	public String addCompFence(Long id,Long netId,Long compId,String longitudeStr,String latitudeStr){
		try{
			if(PubMethod.isEmpty(netId)){
				return paramsFailure("openapi.CompElectronicFenceController.addCompFence.001","添加取派范围电子围栏数据参数异常，netId不能为空");
			}else if(PubMethod.isEmpty(compId)){
				return paramsFailure("openapi.CompElectronicFenceController.addCompFence.002","添加取派范围电子围栏数据参数异常，compId不能为空");
			}
			ExpCompElectronicFence expCompElectronicFence=this.compElectronicFenceService
					.addCompFence(id, netId, compId,longitudeStr,latitudeStr);
			if(PubMethod.isEmpty(id)){
				return jsonSuccess(expCompElectronicFence);
			}else{
				return jsonSuccess(null);
			}
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加围栏初始化数据</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午1:23:39</dd>
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitude 中心点经度
	 * @param latitude 中心点纬度
	 * @param mapLevel 放到级别
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompElectronicFenceController.addCompFenceCenter.001","保存围栏初始化中心点参数异常，netId不能为空"</dd>
     * <dd>"openapi.CompElectronicFenceController.addCompFenceCenter.002","保存围栏初始化中心点参数异常，compId不能为空"</dd>
     * <dd>"openapi.CompElectronicFenceController.addCompFenceCenter.003","保存围栏初始化中心点参数异常，longitude不能为空"</dd>
     * <dd>"openapi.CompElectronicFenceController.addCompFenceCenter.004","保存围栏初始化中心点参数异常，latitude不能为空"</dd>
     * <dd>"openapi.CompElectronicFenceController.addCompFenceCenter.005","保存围栏初始化中心点参数异常，mapLevel不能为空"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addCompFenceCenter", method = { RequestMethod.POST, RequestMethod.GET })
	public String addCompFenceCenter(Long netId,Long compId,
			BigDecimal longitude,BigDecimal latitude,Byte mapLevel){
		try{
			if(PubMethod.isEmpty(netId)){
				return paramsFailure("openapi.CompElectronicFenceController.addCompFenceCenter.001","保存围栏初始化中心点参数异常，netId不能为空");
			}else if(PubMethod.isEmpty(compId)){
				return paramsFailure("openapi.CompElectronicFenceController.addCompFenceCenter.002","保存围栏初始化中心点参数异常，compId不能为空");
			}else if(PubMethod.isEmpty(longitude)){
				return paramsFailure("openapi.CompElectronicFenceController.addCompFenceCenter.003","保存围栏初始化中心点参数异常，longitude不能为空");
			}else if(PubMethod.isEmpty(latitude)){
				return paramsFailure("openapi.CompElectronicFenceController.addCompFenceCenter.004","保存围栏初始化中心点参数异常，latitude不能为空");
			}else if(PubMethod.isEmpty(mapLevel)){
				return paramsFailure("openapi.CompElectronicFenceController.addCompFenceCenter.005","保存围栏初始化中心点参数异常，mapLevel不能为空");
			}
			this.compElectronicFenceService.addCompFenceCenter(netId, compId,longitude,latitude,mapLevel);
			return jsonSuccess(null);
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除取派区域</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午1:24:46</dd>
	 * @param id 取派区域ID
	 * @param compId 网点ID
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>"openapi.CompElectronicFenceController.deleteCompFence.001","删除取派区域电子围栏参数异常，compId不能为空"</dd>
     * <dd>"openapi.CompElectronicFenceController.deleteCompFence.002","删除取派区域电子围栏参数异常，围栏主键不能为空"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCompFence", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteCompFence(Long id,Long compId){
		try{
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("openapi.CompElectronicFenceController.deleteCompFence.001","删除取派区域电子围栏参数异常，compId不能为空");
			}else if(PubMethod.isEmpty(id)){
				return paramsFailure("openapi.CompElectronicFenceController.deleteCompFence.002","删除取派区域电子围栏参数异常，围栏主键不能为空");
			}
			this.compElectronicFenceService.deleteCompFence(id,compId);
			return jsonSuccess(null);
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
}
