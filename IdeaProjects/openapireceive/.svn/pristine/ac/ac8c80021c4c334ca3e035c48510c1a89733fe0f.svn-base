/**  
 * @Project: openapi
 * @Title: CourierController.java
 * @Package net.okdi.api.controller
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-11-4 上午10:25:04
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.okdi.api.service.CourierService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author mengnan.zhang
 * @version V1.0
 */
@Controller
@RequestMapping("/Courier")
public class CourierController extends BaseController{
	 @Autowired
	 CourierService courierService;
	 @Autowired
	 EhcacheService ehcacheService;
	 /**
	  * <dt><span class="strong">方法描述:</span></dt><dd>保存收派员经纬度信息</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午10:07:27</dd>
	  * @param id 保留字
	  * @param netId 网络ID
	  * @param netName 网络名称
	  * @param compId 公司ID
	  * @param compName 公司名称
	  * @param memberId 收派员ID
	  * @param memberName 收派员姓名
	  * @param memberMobile 收派员手机
	  * @param lng 经度
	  * @param lat 纬度
	  * @param memo 备注
	  * @return	JSON true
      * <dt><span class="strong">异常:</span></dt>
      * <dd>CourierController.saveOnLineMember.001 -  compId收派员公司ID不能为空</dd>
      * <dd>CourierController.saveOnLineMember.002 -  memberId收派员ID不能为空 </dd>
      * <dd>CourierController.saveOnLineMember.003 -  收派员经度不能为空</dd>
      * <dd>CourierController.saveOnLineMember.004 -  收派员纬度不能为空 </dd>
	  * @since v1.0
	  */
	 @RequestMapping("saveOnLineMember")
	 @ResponseBody
	 public String saveOnLineMember(Long id,Long netId,String netName,Long compId,
			 String compName,Long memberId,String memberName,String memberMobile,
			 Double lng,Double lat,String memo){
		 if(PubMethod.isEmpty(compId)){return this.paramsFailure("CourierController.saveOnLineMember.001", "compId收派员公司ID不能为空");}
		 if(PubMethod.isEmpty(memberId)){return this.paramsFailure("CourierController.saveOnLineMember.002", "memberId收派员ID不能为空");}
		 if(PubMethod.isEmpty(lng)){return this.paramsFailure("CourierController.saveOnLineMember.003", "收派员经度不能为空");}
		 if(PubMethod.isEmpty(lat)){return this.paramsFailure("CourierController.saveOnLineMember.004", "收派员纬度不能为空");}
		 if(PubMethod.isEmpty(memberMobile)){return this.paramsFailure("CourierController.memberMobile.004", "收派员手机号不能为空");}
		 try{
		 courierService.saveOnLineMember(netId, netName, compId, compName, memberId, memberName, memberMobile, lng, lat, memo);
		 return jsonSuccess(new HashMap<String,Object>());
		 }catch(RuntimeException e){
			 return this.jsonFailure(e);
		 }
	 }
	 /**
	  * <dt><span class="strong">方法描述:</span></dt><dd>删除收派员在线信息</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午10:13:42</dd>
	  * @param memberId 用户ID
	  * @return JSON true
	  * @since v1.0
	  */
	 @RequestMapping("deleteOnLineMember")
	 @ResponseBody
	 public String deleteOnLineMember(Long memberId){
		 try{
		 courierService.deleteOnLineMember(memberId);
		 return jsonSuccess(new HashMap<String,Object>());
		 }catch(RuntimeException e){
			 return this.jsonFailure(e);
		 }
	 }
	 /**
	  * <dt><span class="strong">方法描述:</span></dt><dd>修改收派员在线信息</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午10:14:23</dd>
	  * @param memberId 收派员ID
	  * @param lng 经度
	  * @param lat 纬度
	  * @return JSON TRUE
      * <dd>CourierController.updateOnLineMember.005 -  memberId收派员ID不能为空</dd>
	  * @since v1.0
	  */
	 @RequestMapping("updateOnLineMember")
	 @ResponseBody
	 public String updateOnLineMember(Long memberId, Double lng, Double lat) {
		 if(PubMethod.isEmpty(memberId)){return paramsFailure("CourierController.updateOnLineMember.005", "memberId收派员ID不能为空");}
		 try{
			 courierService.updateOnLineMember(memberId, lng, lat);
			 return jsonSuccess(new HashMap<String,Object>());
			 }catch(RuntimeException e){
				 return this.jsonFailure(e);
			 }
	 }
	 /**
	  * <dt><span class="strong">方法描述:</span></dt><dd>查询附近可派送站点</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午10:15:05</dd>
	  * @param lng 经度
	  * @param lat 纬度
	  * @param recProvince 收件省ID
	  * @param sendProvince 发件省ID
	  * @param weight 包裹重量
	  * @param netId 网络ID
	  * @param sendTownId 发件城镇ID
	  * @param streetId 发件街道ID
	  * @return  {"data":{"compNets":{可派送网络列表网络ID：网络名称"1001":"顺丰速运","1500":"申通快递","1503":"百世汇通","1504":"韵达快递","1512":"中铁快运","1514":"速尔快递","1515":"安信达快递","1516":"飞康达物流","1518":"快捷快递","1519":"联昊通速递","1520":"龙邦速递","1521":"全峰快递","1524":"UC优速快递","1526":"AAE全球专递","1531":"全一快递","1534":"爱彼西商务配送","1536":"国通快递"},"expCompList":[公司集合列表按认证距离排序{"compId":14384014995751936,公司名"compMobile":" ",公司手机"compName":"我爱申通1",公司名"compPhone":"010-66162328",公司电话"continueFreight":4,"cooperationLogo":"1",是否认证标示1已认证0未认证"distance":8496.443,距离m"firstFreight":"8",首重价格"firstWeight":"1.00",首重KG"lats":"40.177712,40.191823,39.56281,39.57349",片区纬度串"lngs":"116.04604,116.844023,116.885417,116.036842",片区经度串"netId":1500,网络ID"netImage":"http://www.okdi.net/nfs_data/comp/1500.png",网络图标路径"netName":"申通快递",网络名称"netPhone":"400-889-5543",网络400电话"price":8,价格"responsible":"吕帅",负责人"responsibleTelephone":"13520932909",负责人电话"totalPrice":"8",总价"userMobile":" ",联系人电话"userName":"吕帅",联系人姓名"userPhone":"13520932909"联系人手机}],"memberList":[在线收派员列表{"approveFlag":"0",收派员认证通过标示0已认证1未认证"compId":2051400835760004,公司ID"compName":"测试网点",公司名"compTel":"13500002222",公司电话"distance":3.167,收派员距自己距离KM"headImg":"http://cas.okdit.net/nfs_data/mob/head/14320110364265472.png",收派员头像路径"id":14320119691871232,条目ID"lat":39.930728,纬度"lng":116.257164,经度"memberId":14320110364265472,收派员memberId"memberMobile":"1551111111",收派员电话"memberName":"风声雨声",收派员名"netId":108,收派员所属网络ID"netName":"邮政包裹"网络名称}]},"success":true}
	  * <dt><span class="strong">异常:</span></dt>
      * <dd>CourierController.queryNearComp.006 -  lng经度不能为空</dd>
      * <dd>CourierController.queryNearComp.007 -  lat纬度不能为空</dd>
      * <dd>CourierController.queryNearComp.008 -  sendTownId发件城市</dd>
	  * @since v1.0
	  */
	 @RequestMapping("queryNearComp")
	 @ResponseBody
	 public String queryNearComp(Double lng,Double lat,Long recProvince,Long sendProvince,Double weight,Long netId,Long sendTownId,Long streetId) {
	    if(PubMethod.isEmpty(lng)){return paramsFailure("CourierController.queryNearComp.006", "经度不能为空");}
	    if(PubMethod.isEmpty(lat)){return paramsFailure("CourierController.queryNearComp.007", "纬度不能为空");}
//	    if(PubMethod.isEmpty(sendTownId)){return paramsFailure("CourierController.queryNearComp.008", "发件城镇不能为空");}
		 try{
			 return  courierService.queryNearCompAndMember(lng, lat, recProvince, sendProvince, weight, netId,sendTownId,streetId);
			 }catch(RuntimeException e){
				 return this.jsonFailure(e);
			 }
	 }
	 /**
	  * <dt><span class="strong">方法描述:</span></dt><dd>查询附近收派员</dd>
	  * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
	  * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午10:16:21</dd>
	  * @param lng 经度
	  * @param lat 纬度
	  * @param townId 发件城镇ID
	  * @param streetId 发件街道ID
	  * @param netId 网络ID
	  * @param sortFlag 传1
	  * @param howFast 搜索附近公里数
	  * @return [{compId=13820806923812864, compName=田村顺丰分部, compTel=15652370000,, lng=116.221542, id=14116114255840256, distance=3.369, headImg=http://cas.okdit.net/nfs_data/mob/head/1811399885919546.png, 
	  * netName=顺丰快递, memberMobile=15652370002, netId=1811399885919546, memberId=1811399885919546, memberName=张梦楠测试收派员, lat=39.938716}]
	  * compId 快递站点ID  compName快递站点名称 compTel电话  lng精度   distance距离   headImg头像图片路径 
	  * netName网点名称  memberMobile人员手机 netId网点ID  memberId人员ID memberName收派员名字 纬度 approveFlag 收派员是否认证 0 未认证 1 已认证
	  * <dt><span class="strong">异常:</span></dt>
      * <dd>CourierController.queryNearComp.009 -  lng经度不能为空</dd>
      * <dd>CourierController.queryNearComp.010 -  lat纬度不能为空</dd>
	  * @since v1.0
	  */
	 @RequestMapping("queryNearMember")
	 @ResponseBody
	 public String queryNearMember(Double lng, Double lat, Long townId,
				Long streetId, Long netId, Integer sortFlag,Integer howFast) {
		 if(PubMethod.isEmpty(lng)){return paramsFailure("CourierController.queryNearComp.009", "经度不能为空");}
		 if(PubMethod.isEmpty(lat)){return paramsFailure("CourierController.queryNearComp.010", "纬度不能为空");}
		 try{
			 return jsonSuccess(courierService.queryNearMember(lng, lat, townId, streetId, netId, sortFlag, howFast));
			 }catch(RuntimeException e){
				 return this.jsonFailure(e);
			 }
	 }
	 /*
	  * 微信查询附近快递员
	  */
	 @RequestMapping("queryNearMemberForWechat")
	 @ResponseBody
	 public String queryNearMemberForWechat(Double lng, Double lat, Integer sortFlag,Integer howFast,Long assignNetId, String roleType) {
		 if(PubMethod.isEmpty(lng)){return paramsFailure("CourierController.queryNearMemberForWechat.001", "经度不能为空");}
		 if(PubMethod.isEmpty(lat)){return paramsFailure("CourierController.queryNearMemberForWechat.002", "纬度不能为空");}
		 if(PubMethod.isEmpty(lat)){return paramsFailure("CourierController.queryNearMemberForWechat.003", "查询距离不能为空");}
		 try{
			 return jsonSuccess(courierService.queryNearMemberForWechat(lng, lat,sortFlag, howFast,assignNetId, roleType));
		 }catch(RuntimeException e){
			 return this.jsonFailure(e);
		 }
	 }
	 @RequestMapping("removeFromCache")
	 @ResponseBody
	 public String removeFromCache(String cacheName,String key,HttpServletRequest request){
		 System.out.println(request.getRemoteHost());
		 if("192.168.53.226".equals(request.getRemoteHost())||"192.168.53.83".equals(request.getRemoteHost())){
		 String data = ehcacheService.get(cacheName, key, String.class);
		 ehcacheService.remove(cacheName, key);
		 if(PubMethod.isEmpty(cacheName)){
			 cacheName = "onLineMember";
		 }
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("msg", "数据     :   "+data+"已经移除");
		 return jsonSuccess(map);
		 }
		 else{
			 Map map = new HashMap();
			 map.put("msg", "非法操作 找张梦楠");
			 return jsonSuccess(map);
		 }
	 }
	 /**
	  * 查询所有在线收派员用于运营数据
	  * @return
	  */
	 @RequestMapping("getAllOnLineMember")
	 @ResponseBody
	 public String getAllOnLineMember(){
		 try {
			 return  courierService.getAllOnlineMember();
		} catch (Exception e) {
            return jsonFailure(e);
		}
	 }
}
