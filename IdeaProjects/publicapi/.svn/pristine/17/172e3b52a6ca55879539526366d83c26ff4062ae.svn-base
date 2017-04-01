/**
 * 
 */
package net.okdi.mob.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.okdi.core.base.BaseController;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.RegexUtils;
import net.okdi.mob.service.CommonService;
import net.okdi.mob.service.ExpParGatewayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;


/**
 * 个人端的核心业务接口，包括：创建任务,取消任务等
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/expParGateway")
public class ExpParGatewayController extends BaseController {

	@Autowired
	private ExpParGatewayService expParGatewayService;
	@Autowired
	private CommonService commonService;
	

	/**
	 * 创建任务
	 * @Method: createAppoint 
	 * @Description: 个人端创建任务
	 * @param fromCompId 任务受理方站点
	 * @param fromMemberId 任务受理人员
	 * @param toCompId 营业分部
	 * @param toMemberId 收派员	与actorMemberId相同
	 * @param coopNetId 任务受理方网络
	 * @param appointTime 约定取件时间	格式要求yyyy-MM-dd HH:mm
	 * @param appointDesc 取件备注
	 * @param actorMemberId 执行人员
	 * @param contactName 联系人姓名
	 * @param contactMobile 联系人手机
	 * @param contactTel 联系人电话
	 * @param contactAddressId 联系人地址ID
	 * @param contactAddress 联系人详细地址
	 * @param customerId 客户ID
	 * @param createUserId 创建人ID
	 * @param contactAddrLongitude 联系人地址的经度信息
	 * @param contactAddrLatitude 联系人地址的纬度信息
	 * @param parEstimateWeight 包裹重量
	 * @param parEstimateCount 包裹数量
	 * @param broadcastId		广播id
	 * @return	{"success":true}
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-4 下午4:52:32
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/createAppoint", method = { RequestMethod.POST })
	public String createAppoint(Long fromCompId, Long fromMemberId, Long toCompId, Long toMemberId, Long coopNetId,
			String appointTime, String appointDesc, Long actorMemberId, String contactName, String contactMobile,
			String contactTel, Long contactAddressId, String contactAddress, Long customerId, Long createUserId,
			BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude,BigDecimal parEstimateWeight,Byte parEstimateCount,Long broadcastId) throws ServiceException {
//		if(!RegexUtils.checkMobile(contactMobile)&&!RegexUtils.checkPhone(contactMobile)){
//			Map map = new HashMap();
//			map.put("success", "false");
//			map.put("msg", "数据校验失败");
//			return JSON.toJSONString(map);
//		}
		if(contactAddress!=null){
			if(!contactAddress.contains(" ")){
				Map map = new HashMap();
				map.put("success", "false");
				map.put("msg", "地址详情中请包含空格");
				return JSON.toJSONString(map);
			}
		}
		return expParGatewayService.createAppoint(fromCompId, fromMemberId, toCompId, toMemberId, coopNetId,
				appointTime, appointDesc, actorMemberId, contactName, contactMobile, contactTel, contactAddressId,
				contactAddress, customerId, createUserId, contactAddrLongitude, contactAddrLatitude,parEstimateWeight,parEstimateCount,broadcastId);
	}

	/**
	 * 取消任务
	 * @Method: cancelMember 
	 * @Description: 客户取消
	 * @param taskId 任务id
	 * @param memberId 操作人id
	 * @return		{"success":true}
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-10 上午11:10:26
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelAppoint", method = { RequestMethod.POST })
	public String cancelAppoint(String taskId, Long memberId) throws ServiceException {
		if(memberId==null||"".equals(taskId)||taskId==null){
			Map map = new HashMap();
			map.put("success", "false");
			map.put("msg", "taskId不能为空");
			return JSON.toJSONString(map);
		}
		return expParGatewayService.cancelMember(taskId, memberId );
	}
	/**
	 * 查找原来被拒绝预约的任务
	 * @Method: queryRefusetask 
	 * @Description: TODO
	 * @param senderName	发件人
	 * @param startTime		开始日期
	 * @param endTime		结束日期
	 * @param senderPhone	发件人手机号
	 * @param operatorCompId	操作人公司id
	 * @param currentPage	当前页
	 * @param pageSize		每页显示记录的条数
	 * @return			{"data":{"page":{"currentPage":1,"hasFirst":false,"hasLast":false,"hasNext":false,"hasPre":false,"items":[{"appointDesc":"dcx xcv xc","appointTime":1415683800000,"cancelTime":1415777975000,"compId":13999092876705792,"compName":"营业分部1号站","contactAddress":"北京-昌平区 顺沙路68号纳帕溪谷","contactMobile":"13599999999","contactName":"xcvcvxcv","createTime":1415601887000,"disposalDesc":"123","id":"14115968386596864","memberName":"营业部","spacetime":"2天54分","taskId":"14115968279904256","taskProcessDesc":"123","taskTransmitCause":3}],"offset":0,"pageCount":1,"pageSize":10,"total":1}},"success":true}
	 * 	appointDesc预约描述  appointTime预约时间	cancelTime取消时间 compId公司ID  compName公司名称  contactAddress详细地址   contactMobile联系电话 contactName收派员姓名  createTime创建时间  disposalDesc处理详情 memberName名称  spacetime间隔时间 taskId任务ID  taskProcessDesc任务进程详情  taskTransmitCause任务转单原因 1:超出本网点范围,2:网点任务太多忙不过来,3:客户拒绝发件,4:多次联系不上客户上门无人,5:其它原因
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-10 下午1:16:47
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRefuseAppoint", method = { RequestMethod.POST })
	public String queryRefusetask(String senderName, String startTime, String endTime, String senderPhone,
			 Long operatorCompId, Integer currentPage, Integer pageSize) throws ServiceException {
		if(PubMethod.isEmpty(senderName)){
			Map errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "senderName参数不能为空");
			return JSON.toJSONString(errorMap);
		}
		return expParGatewayService.queryRefusetask(senderName, startTime, endTime, senderPhone,
				 operatorCompId, currentPage, pageSize);
	}

	/**
	 * 获取某个收派员的信息
	 * @Method: getMemberInfoById 
	 * @Description: 
	 * @param memberId	人员id
	 * @return {
			  "data": {
			    "areaColor": "#c2c2c2", 
			    "employeeWorkStatusFlag": 1, 
			    "memberId": 13954038301985792, 
			    "memberName": "离职", 
			    "memberPhone": "13177770045", 
			    "memberSource": 1, 
			    "roleId": 0
			  }, 
			  "success": true
			}
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-10 下午2:46:10
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberInfoById", method = { RequestMethod.POST })
	public String getMemberInfoById(Long memberId) throws ServiceException {
		return expParGatewayService.getMemberInfoById(memberId) ;
	}
	
	/**
	 * 任务详情
	 * @Method: findAppointInfo 
	 * @Description: 其实就是任务详情
	 * @param taskId	任务Id
	 * @return {"data":{"resultlist":[{"compName":"营业分部1号站","createTime":1415601887000,"memberName":"营业部","taskProcessDesc":"网点指定任务给：营业部"},{"compName":"营业分部1号站","createTime":1415777975000,"memberName":"营业部","taskProcessDesc":"任务已被取消：123"}],"taskinfo":{"actorMemberName":"营业部","actorMemberPhone":"13177700001","appointDesc":"dcx xcv xc","appointTime":1415683800000,"compId":13999092876705792,"contactAddress":"北京-昌平区 顺沙路68号纳帕溪谷","contactAddressId":11000211,"contactMobile":"13599999999","contactName":"xcvcvxcv","contactTel":"","coopCompName":"营业分部1号站","createTime":1415601887000,"taskFlag":0,"taskId":14115968279904256,"taskIsEnd":0,"taskSource":2,"taskStatus":3,"taskType":0}},"success":true}
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-10 下午3:09:57
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/findAppointInfo", method = { RequestMethod.POST })
	public String findAppointInfo(Long taskId) throws ServiceException {
		return expParGatewayService.queryTaskDetail(taskId) ;
	}
	
	/**
	 * 确认 -- 交寄确认
	 * @Method: doPersonAffirmTake 
	 * @Description: 新版是完成任务
	 * @param taskId	任务记录id
	 * @param memberId	操作人id
	 * @param compId	操作网点id
	 * @return		{"success":true}
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-10 下午3:31:34
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/doPersonAffirmTake", method = { RequestMethod.POST })
	public String doPersonAffirmTake(Long taskId, Long memberId, Long compId) throws ServiceException {
		return expParGatewayService.finishTask(taskId,memberId, compId) ;
	}

	
	/**
	 * 站点详情
	 * @Method: queryCompBasicInfo 
	 * @Description: 站点详情
	 * @param compId	登录网点ID
	 * @return	{"data":{"address":"北京-朝阳区|测试","addressId":11000203,"compId":13816306628820992,"compName":"DC城分部",
	 * "compStatus":-1,"compTelephone":"15999990000","compTypeNum":"1006","latitude":39.958953,"longitude":116.521695,"netId":1503,
	 * "netName":"百世汇通","useCompId":59514},"success":true}
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-10 下午4:17:05
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getCompInfo", method = { RequestMethod.POST })
	public String getCompInfo(Long compId) throws ServiceException {
		return expParGatewayService.queryCompBasicInfo(compId);
	}
	
	/**
	 * 判断是否加到我收或我发（获得快递相关信息，查快递物流动态页面返回参数）
	 * @Method: getMyExpress 
	 * @Description: TODO
	 * @param channelId		用户ID
	 * @param expWaybillNum		运单号  
	 * @param netId			快递公司Id
	 * @return	{"success":"no"}	yes代表添加  no代表没添加
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-5 下午3:32:27
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyExpress", method = { RequestMethod.POST })
	public String getMyExpress(Long channelId,String expWaybillNum,Long netId) throws ServiceException {
		if(PubMethod.isEmpty(expWaybillNum)){
			Map<String,String> errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "senderName参数不能为空");
			return JSON.toJSONString(errorMap);
		}
		return expParGatewayService.decideGoods(channelId, expWaybillNum, netId);
	}
	
	/**
	 * 加到我发的快递或加到我收的快递
	 * @Method: addParLogisticSearch 
	 * @Description: TODO
	 * @param channelId		用户ID
	 * @param netId		快递公司ID
	 * @param expWaybillNum	运单号
	 * @param traceDetail	物流明细信息
	 * @param expType 0:发快递  1:收快递
	 * @return	JSON	
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-5 下午4:23:14
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/addParLogisticSearch", method = { RequestMethod.POST })
	public String addParLogisticSearch(Long channelId,Long netId,String expWaybillNum,
			String traceDetail,String expType) throws ServiceException {
		if(PubMethod.isEmpty(expWaybillNum)||PubMethod.isEmpty(expType)){
			Map<String,String> errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "expWaybillNum,expType参数不能为空");
			return JSON.toJSONString(errorMap);
		}
		return expParGatewayService.addParLogisticSearch(channelId,netId,expWaybillNum,
				"0",traceDetail,null,null,"0",expType);
	}
	/**
	 * 查找包裹物流流转信息(快递列表)
	 * @Method: findParLogList 
	 * @Description: 		
	 * @param channelId		用户ID
	 * @param expType		0发快递  1收快递
	 * @return		list,如果是发快递，目前会返回任务列表(多一个返回字段)
	 * 		1：	{"data":[],"success":true,"taskData":{"currentPage":1,"hasFirst":false,"hasLast":true,"hasNext":true,"hasPre":false,"items":[],"offset":0,"pageCount":0,"pageSize":100,"total":0}}
	 * 		0：	{"data":[{"channelId":14319900495186944,"channelNo":"02","createdTime":1416451162000,"expType":"0","expWaybillNum":"210604419022","id":14338600563900416,"modifiedTime":1416451162000,"netCode":"huitongkuaidi","netId":1503,"systemMark":"01","traceDetail":"{\"message\":\"ok\",\"nu\":\"210604419022\",\"ischeck\":\"0\",\"com\":\"huitongkuaidi\",\"updatetime\":\"2014-11-20 10:39:16\",\"status\":\"200\",\"condition\":\"H100\",\"data\":[{\"time\":\"2014-11-14 15:23:58\",\"location\":\"\",\"context\":\"北京市|派件|北京市【杏石口分部】，【小王13611344430】正在派件\",\"ftime\":\"2014-11-14 15:23:58\"},{\"time\":\"2014-11-14 14:52:44\",\"location\":\"\",\"context\":\"北京市|到件|到北京市【杏石口分部】\",\"ftime\":\"2014-11-14 14:52:44\"},{\"time\":\"2014-11-14 12:53:25\",\"location\":\"\",\"context\":\"北京市|发件|北京市【北京金盏分拨中心】，正发往【杏石口分部】\",\"ftime\":\"2014-11-14 12:53:25\"},{\"time\":\"2014-11-14 12:07:29\",\"location\":\"\",\"context\":\"北京市|到件|到北京市【北京金盏分拨中心】\",\"ftime\":\"2014-11-14 12:07:29\"},{\"time\":\"2014-11-13 20:48:30\",\"location\":\"\",\"context\":\"北京市|签收|北京市【杏石口分部】，王喜伟 已签收\",\"ftime\":\"2014-11-13 20:48:30\"},{\"time\":\"2014-11-13 00:58:39\",\"location\":\"\",\"context\":\"上海市|发件|上海市【上海分拨中心】，正发往【北京金盏分拨中心】\",\"ftime\":\"2014-11-13 00:58:39\"},{\"time\":\"2014-11-13 00:56:14\",\"location\":\"\",\"context\":\"上海市|到件|到上海市【上海分拨中心】\",\"ftime\":\"2014-11-13 00:56:14\"},{\"time\":\"2014-11-12 18:51:20\",\"location\":\"\",\"context\":\"上海市|发件|上海市【松江新桥】，正发往【上海分拨中心】\",\"ftime\":\"2014-11-12 18:51:20\"},{\"time\":\"2014-11-12 12:49:21\",\"location\":\"\",\"context\":\"上海市|收件|上海市【松江新桥】，【果果木】已揽收\",\"ftime\":\"2014-11-12 12:49:21\"}],\"state\":\"5\"}","traceStatus":"0"}],"success":true}
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-7 下午12:44:22
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/findParLogList", method = { RequestMethod.POST })
	public String findParLogList(Long channelId, String expType) throws ServiceException {
		if(PubMethod.isEmpty(expType)){
			Map<String,String> errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "expWaybillNum,expType参数不能为空");
			return JSON.toJSONString(errorMap);
		}
		return expParGatewayService.findParLogList(channelId, expType);
	}
	
	/**
	 * 附近收派员
	 * @Method: queryNearMember 
	 * @Description: TODO
	 * @param lng	经度
	 * @param lat	维度
	 * @param townId	城市Id
	 * @param streetId	街道Id
	 * @param netId		网络Id
	 * @param sortFlag	定死 传1
	 * @param howFast	方圆多少公里
	 * @return	
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-11 下午8:17:40
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNearMember", method = { RequestMethod.POST })
	public String queryNearMember(double lng, double lat, Long townId,
			Long streetId, Long netId, int sortFlag,int howFast) throws ServiceException {
		return expParGatewayService.queryNearMember(lng, lat, townId,
				streetId, netId, sortFlag,howFast);
	}
	
	/**
	 * 附近收派员和站点
	 * @Method: getMemberOrCompNearby 
	 * @Description: 
	 * @param lng	经度
	 * @param lat	维度
	 * @param recProvince	收件省ID（这个是计算价格的时候使用），不能为空，要传0
	 * @param sendProvince	发件省ID（这个是计算价格的时候使用），不能为空，要传0
	 * @param weight	包裹重量
	 * @param netId		网络Id
	 * @param sendTownId	发件城市
	 * @param streetId		发件街道ID
	 * @return	{compNets={1001=顺丰速运, 2071=微特派, 1587=广东EMS, 1514=速尔快递, 1512=中铁快运, 1524=UC优速快递, 1501=圆通速递, 1500=申通快递, 1521=全峰快递, 1503=百世汇通, 1520=龙邦速递, 1531=全一快递, 1504=韵达快递, 1519=联昊通速递, 1518=快捷快递, 1536=国通快递, 1526=AAE全球专递, 1516=飞康达物流, 1534=爱彼西商务配送, 1515=安信达快递}, expCompList=[{compId=1000215573, netPhone=021-59218889, lats=39.913563,39.906124,39.906563,39.913754,40.006668,40.02194,39.930065, responsible= , continueFreight=4, compName=海淀二十部, lngs=116.1848,116.23215,116.281906,116.290375,116.359474,116.35926,116.18072, compMobile= , distance=15414.615, price=9, netName=龙邦速递, responsibleTelephone= , firstWeight=1.00, netImage=http://www.okdi.net/nfs_data/comp/1520.png, userMobile= , cooperationLogo=0, userName= , userPhone= , netId=1520, compPhone= , firstFreight=9, totalPrice=9}, {compId=1000283580, netPhone=021-59218889, lats=39.950623,39.90002,39.90665,39.91306,39.93581,39.974163,40.067944,40.070053,40.05627,40.004456, responsible= , continueFreight=4, compName=海淀四季青, lngs=116.106415,116.27928,116.31623,116.33501,116.36006,116.39636,116.36222,116.3521,116.31072,116.21009, compMobile= , distance=15414.615, price=9, netName=龙邦速递, responsibleTelephone= , firstWeight=1.00, netImage=http://www.okdi.net/nfs_data/comp/1520.png, userMobile= , cooperationLogo=0, userName= , userPhone= , netId=1520, compPhone= , firstFreight=9, totalPrice=9}]} compId公司ID netPhone电话 lats纬度compName站点名称 responsible负责人distance距离lngs精度firstWeight起重netImage网点图片路径totalPrice总价格
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-12 下午5:29:58
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemberOrCompNearby", method = { RequestMethod.POST })
	public String getMemberOrCompNearby(Double lng,Double lat,Long recProvince,Long sendProvince,Double weight,Long netId,Long sendTownId,Long streetId)  throws ServiceException {
		return expParGatewayService.queryNearComp(lng,lat,recProvince,sendProvince,weight,netId,sendTownId,streetId);
	}
	
	/**
	 * @api {post} /queryRelevantAddressList/parseAddrNew 地址联想功能
	 * @apiPermission user
	 * @apiDescription 接口描述 地址联想功能
	 * @apiparam {Long} townId 城市乡镇Id
	 * @apiparam {string} keyword 关键字
	 * @apiparam {Integer} count 联想数量
	 * @apiGroup ADDRESS 地址解析
	 * @apiSampleRequest /expParGateway/parseAddrNew
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   [{"addName":"前门西大街1号","addId":"310145399"},{"addName":"前门西大街41号","addId":"310145400"}]
	 *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"message":"Parameter is not correct or Parameter format error","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRelevantAddressList", method = { RequestMethod.POST })
	public String queryRelevantAddressList(Long townId,String keyword,Integer count) throws ServiceException {
		if(PubMethod.isEmpty(townId)||PubMethod.isEmpty(keyword)||PubMethod.isEmpty(count)){
			return paramsFailure();
		}
		return expParGatewayService.queryRelevantAddressList(townId,keyword, count);
	}
	
	
	/**
	 * 联系人图片上传功能
	 * @Method: uploadPic 
	 * @Description: TODO
	 * @param memberId 相关的memberId
	 * @param myfiles 上传文件名
	 * @param request (http参数，不用传）
	 * @return	{"upload", "true"}成功	{"upload", "false"}失败
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-7 下午8:05:28
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadPic", method = { RequestMethod.POST })
	public String uploadPic(Long memberId,@RequestParam(value = "myfiles", required = false)  MultipartFile[] myfiles, HttpServletRequest request) throws ServiceException {
		return commonService.uploadPic("contact",memberId,myfiles);
	}
	
	/**
	 * 查询站点在线收派员信息
	 * @Method: uploadPic 
	 * @Description: 得到方式，从附近在线收派员中筛选
	 * @param compId	站点信息
	 * @return
	 * @throws ServiceException
	 * @author chuanshi.chai
	 * @date 2014-11-25 上午11:07:56
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getStationMember", method = { RequestMethod.POST })
	public String getStationMember(Long compId) throws ServiceException {
		return expParGatewayService.getStationMember(compId);
	}
	

	/**
	 * @api {post} /expParGateway/parseAddrNew 根据经纬度解析地址
	 * @apiPermission user
	 * @apiDescription 根据经纬度解析地址  xiangwei.liu
 	 * @apiparam {string} lat 纬度
	 * @apiparam {string} lng 经度
 	 * @apiGroup 取件
 	 * @apiSampleRequest /expParGateway/parseAddrNew
 	 * @apiSuccess {Long} addressId 地址id
 	 * @apiSuccess {String} addressName  所属省名称+所属市名称+所属区县名称
 	 * @apiSuccess {Long} lastLevelId 地址id
 	 * @apiSuccess {String} lastLevelName  所属乡镇名称+所属乡名称
 	 * @apiSuccess {Long} townId  乡镇Id
 	 * @apiError {String} result false
 	 * @apiSuccessExample {json} Success-Response:
 	 *     HTTP/1.1 200 OK
	 *   {"data":
	 *   {"addressId":300205388,
	 *   "addressName":"北京市海淀区",
	 *   "lastLevelId":300205388,
	 *   "lastLevelName":"牡丹园北里",
	 *   "townId":11000206},
	 *   "success":true}
 	 * @apiErrorExample {json} Error-Response:
 	 *     HTTP/1.1 200 OK
 	 *   {"message":"Parameter is not correct or Parameter format error","success":false}
 	 * @apiVersion 0.2.0
 	 */
	@RequestMapping(value = "/parseAddrNew", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String parseAddrNew(String lat,String lng){
		if(PubMethod.isEmpty(lat)||PubMethod.isEmpty(lng)){
			return paramsFailure();
		}
		return expParGatewayService.parseAddrNew(lat,lng);
	}
	
	/**
	 * 查询个人任务列表
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/queryPersonalTask", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String queryPersonalTask(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		return expParGatewayService.queryPersonalTask(memberId);
	}
	
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询发件包裹列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>pengfei.xia</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-23 下午8:29:22</dd>
	 * @param memberId
	 * @return
	 * @since v1.0
	 */
	@RequestMapping(value = "/queryPersonalSendParcelList", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String queryPersonalSendParcelList(Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure();
		}
		return expParGatewayService.queryPersonalParcelList(memberId);
	}
	
	
	@RequestMapping(value = "/queryParcelDetail", method = {RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String queryParcelDetail(Long parcelId){
		if(PubMethod.isEmpty(parcelId)){
			return paramsFailure();
		}
		return expParGatewayService.queryParcelDetail(parcelId);
	}
	
	/**
	 * 手动更新包裹物流流转信息(快递列表)
	 * @Method: updateParLogList 
	 * @param id				需要更新记录的id
	 * @param traceStatus		查询快递100获得的状态
	 * @param traceDetail		更新快递100的物流信息
	 * @author chuanshi.chai
	 * @date 2014-11-7 下午12:44:22
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/updateParLogList", method = { RequestMethod.POST })
	public String updateParLogList(Long id,String traceStatus, String traceDetail) throws ServiceException {
		if(PubMethod.isEmpty(id)||PubMethod.isEmpty(traceStatus)||PubMethod.isEmpty(traceDetail)){
			Map<String,String> errorMap = new HashMap<String,String>();
			errorMap.put("success", "false");
			errorMap.put("msg", "id,traceStatus,traceDetail参数不能为空");
			return JSON.toJSONString(errorMap);
		}
		return expParGatewayService.updateParLogList(id,traceStatus, traceDetail);
	}
}