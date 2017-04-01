package net.okdi.apiV1.controller;

import net.okdi.apiV1.service.NearInfoService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/queryNearCompInfo")
public class NearInfoController {
	@Autowired
	private NearInfoService nearInfoService;
	/**
	 * @author 韩爱军
	 * @api {post} /queryNearCompInfo/collectingPoints 查询附近5公里代收点
	 * @apiVersion 0.2.0
	 * @apiDescription 查询附近5公里代收站列表 -韩爱军
	 * @apiGroup 快递注册
	 * @apiParam {String} longitude 登录人经度
	 * @apiParam {String} latitude 登录人纬度
	 * @apiSampleRequest /queryNearCompInfo/collectingPoints
	 * @apiSuccess {String} compAddress  站点详细地址
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} distance  距离 
	 * @apiSuccess {String} longitude  站点经度
	 * @apiSuccess {String} latitude  站点纬度
	 * @apiSuccess {String} compImgUrl  站点门店照
	 * @apiSuccess {String} resPhone  负责人手机号
	 * @apiSuccess {String} unit   距离单位
	 * @apiSuccessExample Success-Response:
	{
    "data": [
        {
            "compAddress": "河南省-周口市-鹿邑县|",
            "compId": "33620",
            "compImgUrl": "",
            "compName": "鹿邑站",
            "compTypeNum": "1030",
            "distance": 1.224,
            "latitude": "39.974684",
            "longitude": "116.383879",
            "netId": 1523,
            "netName": "亚风速递",
            "resPhone": "null",
            "unit ": "km"
        },
        {
            "compAddress": "null",
            "compId": "0",
            "compImgUrl": "",
            "compName": "拉拉了",
            "compTypeNum": "1030",
            "distance": 111,
            "latitude": "39.984684",
            "longitude": "116.383879",
            "netId": 1514,
            "netName": "速尔快递",
            "resPhone": "null",
            "unit ": "m"
        }
    ],
    "success": true
}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	
	@ResponseBody
	@RequestMapping(value = "/collectingPoints", method = { RequestMethod.POST, RequestMethod.GET })
	public String collectingPoints(String longitude,String latitude){
		if(PubMethod.isEmpty(longitude) || PubMethod.isEmpty(latitude)){
			return PubMethod.paramError("publicapi.NearInfoController.queryCompInfo.001", "经纬度异常");
		}
		
		return this.nearInfoService.collectingPoints(longitude, latitude);
	}
}
