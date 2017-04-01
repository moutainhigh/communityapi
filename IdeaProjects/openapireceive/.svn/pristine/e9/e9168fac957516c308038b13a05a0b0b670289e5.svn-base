package net.okdi.express.controller;

import java.math.BigDecimal;
import java.util.Map;

import net.okdi.api.entity.ExpAreaElectronicFence;
import net.okdi.api.service.AreaElectronicFenceService;
import net.okdi.api.service.CompElectronicFenceService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 片区围栏信息
 * @author shihe.zhai
 * @version V1.0
 */
@Controller
@RequestMapping("/areaElectronicFence")
public class AreaElectronicFenceController extends BaseController {
	@Autowired
	private AreaElectronicFenceService areaElectronicFenceService;
	@Autowired
	private CompElectronicFenceService compElectronicFenceService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询网点片区围栏</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午1:37:38</dd>
	 * @param compId 网点ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @return {"data":{"compCoordinateMap":{"latitude":39.989418,"longitude":116.464346,"mapLevel":16},
	 * "compFenceList":[{"id":14499399632158720,"pointList":[{"latitude":"39.992652","longitude":"116.460142"},
	 * {"latitude":"39.993619","longitude":"116.473509"},{"latitude":"39.98928","longitude":"116.460932"}]}],
	 * "memberFenceList":[{"color":"#c2c2c2","id":14500322529771520,"labelLatitude":39.991436,"labelLongitude":116.462945,
	 * "memberId":14389862963876864,"memberName":"恒远7","pointList":[{"latitude":"39.991629","longitude":"116.461867"},
	 * {"latitude":"39.992597","longitude":"116.463807"},{"latitude":"39.990385","longitude":"116.462945"}]}]},"success":true}
	 * compCoordinateMap 初始化中心点 mapLevel地图放大等级 compFenceList取派范围 id取派范围主键 pointList取派范围坐标点 longitude 经度  latitude纬度
	 * memberFenceList 片区集合  memberId 收派员ID memberName收派员姓名 color片区颜色
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.AreaElectronicFenceController.queryAreaFence.001","查询网点片区围栏参数异常，网点ID不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceController.queryAreaFence.002","查询网点片区围栏参数异常，网点类型异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAreaFence", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAreaFence(Long compId,String compTypeNum){
		try{
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("openapi.AreaElectronicFenceController.queryAreaFence.001","查询网点片区围栏参数异常，网点ID不能为空");
			}else if(!"1006".equals(compTypeNum) && !"1050".equals(compTypeNum)){
				return paramsFailure("openapi.AreaElectronicFenceController.queryAreaFence.002","查询网点片区围栏参数异常，网点类型异常");
			}
			Map<String,Object> dataMap=this.compElectronicFenceService.queryCompFence(compId);
			dataMap.put("memberFenceList", this.areaElectronicFenceService.queryAreaFence(compId,compTypeNum));
			return jsonSuccess(dataMap);
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加更新片区围栏</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午2:40:42</dd>
	 * @param id 片区围栏ID
	 * @param netId 网络ID
	 * @param compId 网点ID
	 * @param longitudeStr 经度串
	 * @param latitudeStr 纬度串
	 * @return {"data":{"compId":14431820821045248,"id":14500938107060224,"latitudeStr":"39.991933,39.991878,39.990441",
	 * "longitudeStr":"116.461256,116.465424,116.461543","maxLatitude":39.991933,"maxLongitude":116.465424,"minLatitude":39.990441,
	 * "minLongitude":116.461256,"netId":1500},"success":true}
	 * compId 网点ID id取派区域ID latitudeStr纬度串 longitudeStr经度串 netId网络ID
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.AreaElectronicFenceController.addAreaFence.001","添加片区电子围栏数据参数异常，netId不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceController.addAreaFence.002","添加片区电子围栏数据参数异常，compId不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceServiceImpl.addAreaFence.001","添加片区电子围栏数据参数异常，围栏坐标点经纬度对应错误"</dd>
     * <dd>"openapi.AreaElectronicFenceServiceImpl.addAreaFence.002","添加片区电子围栏数据参数异常，围栏坐标点数据格式错误"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addAreaFence", method = { RequestMethod.POST, RequestMethod.GET })
	public String addAreaFence(Long id,Long netId,Long compId,String longitudeStr,String latitudeStr){
		try{
			if(PubMethod.isEmpty(netId)){
				return paramsFailure("openapi.AreaElectronicFenceController.addAreaFence.001","添加片区电子围栏数据参数异常，netId不能为空");
			}else if(PubMethod.isEmpty(compId)){
				return paramsFailure("openapi.AreaElectronicFenceController.addAreaFence.002","添加片区电子围栏数据参数异常，compId不能为空");
			}
			ExpAreaElectronicFence expAreaElectronicFence=this.areaElectronicFenceService
					.addAreaFence(id, netId, compId,longitudeStr,latitudeStr);
			if(PubMethod.isEmpty(id)){
				return jsonSuccess(expAreaElectronicFence);
			}else{
				return jsonSuccess(null);
			}
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新片区收派员</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午2:42:13</dd>
	 * @param id 片区围栏ID
	 * @param compId 网点ID
	 * @param memberId 收派员（或营业分部）ID
	 * @param compTypeNum 类型 1050营业分部 0收派员
	 * @param labelLongitude 标注经度
	 * @param labelLatitude 标注纬度
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.AreaElectronicFenceController.updateAreaMember.001","更新片区收派员参数异常，片区唯一标示不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceController.updateAreaMember.002","更新片区收派员参数异常，标注经度不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceController.updateAreaMember.003","更新片区收派员参数异常，标注纬度不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceController.updateAreaMember.004","更新片区收派员参数异常，类型参数异常"</dd>
     * <dd>"openapi.AreaElectronicFenceController.updateAreaMember.005","更新片区收派员参数异常，类型参数异常"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAreaMember", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAreaMember(Long id,Long compId,Long memberId,String compTypeNum,BigDecimal labelLongitude,BigDecimal labelLatitude){
		try{
			if(PubMethod.isEmpty(id)){
				return paramsFailure("openapi.AreaElectronicFenceController.updateAreaMember.001","更新片区收派员参数异常，片区唯一标示不能为空");
			}else if(PubMethod.isEmpty(labelLongitude)){
				return paramsFailure("openapi.AreaElectronicFenceController.updateAreaMember.002","更新片区收派员参数异常，标注经度不能为空");
			}else if(PubMethod.isEmpty(labelLatitude)){
				return paramsFailure("openapi.AreaElectronicFenceController.updateAreaMember.003","更新片区收派员参数异常，标注纬度不能为空");
			}else if(!PubMethod.isEmpty(memberId) && !"0".equals(compTypeNum) && !"1050".equals(compTypeNum)){
				return paramsFailure("openapi.AreaElectronicFenceController.updateAreaMember.004","更新片区收派员参数异常，类型参数异常");
			}else if(PubMethod.isEmpty(memberId) && !PubMethod.isEmpty(compTypeNum)){
				return paramsFailure("openapi.AreaElectronicFenceController.updateAreaMember.005","更新片区收派员参数异常，类型参数异常");
			}
			this.areaElectronicFenceService.updateAreaMember(id,compId,memberId,compTypeNum,labelLongitude,labelLatitude);
			return jsonSuccess(null);
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除片区围栏</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午2:42:59</dd>
	 * @param id 片区围栏ID
	 * @param compId 网点ID
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.AreaElectronicFenceController.deleteAreaFence.001","删除片区电子围栏参数异常，compId不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceController.deleteAreaFence.002","删除片区电子围栏参数异常，唯一标示不能为空"</dd>
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAreaFence", method = { RequestMethod.POST, RequestMethod.GET })
	public String deleteAreaFence(Long id,Long compId){
		try{
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("openapi.AreaElectronicFenceController.deleteAreaFence.001","删除片区电子围栏参数异常，compId不能为空");
			}else if(PubMethod.isEmpty(id)){
				return paramsFailure("openapi.AreaElectronicFenceController.deleteAreaFence.002","删除片区电子围栏参数异常，唯一标示不能为空");
			}
			this.areaElectronicFenceService.deleteAreaFence(id,compId);
			return jsonSuccess(null);
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新片区营业分部（解除关系）</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-27 下午2:43:35</dd>
	 * @param compId 网点ID
	 * @param loginCompId 登录网点ID
	 * @param compTypeNum 网点类型1050营业分部 1006站点
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
	 * <dd>"openapi.AreaElectronicFenceController.updateAreaBranch.001","更新片区收派员参数异常，被解除网点ID不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceController.updateAreaBranch.002","更新片区收派员参数异常，登录ID不能为空"</dd>
     * <dd>"openapi.AreaElectronicFenceController.updateAreaBranch.003","更新片区收派员参数异常，网点类型异常"</dd>
	 * @since v1.0
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/updateAreaBranch", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAreaBranch(Long compId,Long loginCompId,String compTypeNum){
		try{
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("openapi.AreaElectronicFenceController.updateAreaBranch.001","更新片区收派员参数异常，被解除网点ID不能为空");
			}else if(PubMethod.isEmpty(loginCompId)){
				return paramsFailure("openapi.AreaElectronicFenceController.updateAreaBranch.002","更新片区收派员参数异常，登录ID不能为空");
			}else if(!"1006".equals(compTypeNum) && !"1050".equals(compTypeNum)){
				return paramsFailure("openapi.AreaElectronicFenceController.updateAreaBranch.003","更新片区收派员参数异常，网点类型异常");
			}
			if("1006".equals(compTypeNum)){
				this.areaElectronicFenceService.updateAreaBranch(loginCompId,compId);
			}else if("1050".equals(compTypeNum)){
				this.areaElectronicFenceService.updateAreaBranch(compId,loginCompId);
			}
			return jsonSuccess(null);
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
}
