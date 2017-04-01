package net.okdi.mob.controller;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.okdi.apiV2.service.AlisendNoticeService;
import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.apiV3.service.WalletNewService;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.apiV4.service.TaskMassNoticeService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.VerifyUtil;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.CommonService;
import net.okdi.mob.service.ExpGatewayService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 接单王的核心业务，包括：站点信息，网点判断是否重名，收派员,站长,后勤更新角色,上传照片，查询任务，签收包裹，提货，派件等
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/expGateway")
public class ExpGatewayController extends BaseController{

	Logger logger = Logger.getLogger(ExpGatewayController.class);
	@Autowired
	ExpGatewayService expGatewayService;
	@Autowired
	CommonService commonService;
	@Autowired
	private WalletNewService walletNewService;
	@Autowired
	private TelephoneRewardService telephoneRewardService;
	@Autowired
	private SendPackageService sendPackageService;//注入service
	@Autowired
	TaskMassNoticeService taskMassNoticeService;

	@Autowired
	ConstPool constPool;
	@Autowired
	private AlisendNoticeService alisendNoticeService;
	
	/**
	 * <dt><span>方法描述:</span></dt><dd></dd>
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @Method: querySameComp 
	 * @Description: TODO查询一个网络下所有的站点
	 * @param Long loginCompId 公司ID
	 * @return {"data":{"address":"北京-昌平区-北七家镇|拜拜","addressId":3000549,"businessLicenseImg":"2014102715552436474.jpg",
	 * 		   "businessLicenseImgUrl":"http://exp.okdit.net/nfs_data/comp/2014102715552436474.jpg",
	 * 		   "compId":13799954183618560,"compName":"百世汇通分部四","compStatus":-1,"compTelephone":"13255556666",
	 * 		   "compTypeNum":"1050","expressLicenseImg":"2014102715560528179.jpg","expressLicenseImgUrl":"http://exp.okdit.net/nfs_data/comp/2014102715560528179.jpg",
	 * 		   "latitude":40.123970,"longitude":116.430694,"netId":1503,"netName":"百世汇通","responsible":"测试分部负责人","responsibleIdNum":"152122198602130983","responsibleTelephone":"13255556666","verifyType":3},
	 * 		   "success":true}
	 * @author xiangwei.liu
	 * @date 2014-11-3 下午2:16:24
	 * @since jdk1.6 b
	 */
	@ResponseBody
	@RequestMapping(value = "/querySameComp", method = { RequestMethod.POST })
	public String querySameComp(Long loginCompId) {
		if(PubMethod.isEmpty(loginCompId))
		return paramsFailure();
		return expGatewayService.querySameComp(loginCompId);
	}
	/**
	 * <h2>查询站点详细信息接口</h2>
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @Method: getCompInfo 
	 * @Description: 查询站点详细信息接口
	 * @param Long loginCompId 公司ID
	 * @return  {"data":{"address":"北京-昌平区|1","addressId":11000211,"compId":13752423284211712,"compName":"测试","compStatus":0,"compTelephone":"18999999999","compTypeNum":"1006","latitude":40.221724,"longitude":116.216456,"netId":1503,"netName":"百世汇通","responsible":"测试","responsibleIdNum":"152122198602130983","responsibleTelephone":"18999999999","verifyType":3},"success":true}
	 *          |{"success":false}
	 *          responsibleTelephone
	 */
	@ResponseBody
	@RequestMapping(value = "/getCompInfo", method = { RequestMethod.POST })
	public String getCompInfo(Long loginCompId){
		if(PubMethod.isEmpty(loginCompId))
		return paramsFailure();
		return expGatewayService.getCompInfo(loginCompId);
	}
	
	/**
	 * <h2>获取网点列表</h2>
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @Method: getPromptCompForMobile 
	 * @Description: TODO    获取网点列表
	 * @param  Long netId 网络id
	 * @param  String compTypeNum注册类型  网点类型 1006站点 1050营业分部
	 * @param  Long addressId地址id 四级地址id 
	 * @param  Short roleId角色
	 * @return {"data":{"belongToCompId":-1},"success":true}
	 * 		   {"message":"","success":false}
	 * @date 2014-11-3 下午2:16:24
	 * @since jdk1.6
	 * 001 获取网点提示信息,参数异常
	 * 002 获取网点提示信息异常
	 */
	@ResponseBody
	@RequestMapping(value = "/getPromptCompForMobile", method = { RequestMethod.POST })
	public String getPromptCompForMobile(Long netId, String compTypeNum,Long addressId,Short roleId) {
		if(PubMethod.isEmpty(netId) || PubMethod.isEmpty(compTypeNum) || PubMethod.isEmpty(addressId) ){
			return paramsFailure();
		}
		return expGatewayService.getPromptCompForMobile(netId, compTypeNum, addressId, roleId);
	}
	
	/**
	 * <h2>添加网点判断是否重名</h2>
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @Method: getSameCompNameForMobile 
	 * @Description: TODO添加网点判断是否重名
	 * @param  Long netId 网络id
	 * @param  String compName名称
	 * @return {"success":true/false}
	 * @date 2014-11-3 下午2:16:24
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/getSameCompNameForMobile", method = { RequestMethod.POST })
	public String getSameCompNameForMobile(Long netId, String compName) {
		if(PubMethod.isEmpty(netId) || PubMethod.isEmpty(compName)){
			return paramsFailure();
		}
		return expGatewayService.getSameCompNameForMobile(netId, compName);
	}
	
	/**
	 *  <h2> 添加网点    第一次注册站点的时候</h2>
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @Method: saveCompInfo
	 * @Description: TODO第一次注册站点的时候
	 * @param  memberId 人员id
	 * @param  Long netId 网络
	 * @param  String compTypeNum网点类型
	 * @param  String compName名称
	 * @param  String compTelephone电话
	 * @param  Long addressId 地址id ||
	 * @param  String address地址信息 ||
	 * @return {"data":{"belongToCompId":-1,"belongToNetId":999,.....},"success":true}
	 * @date 2014-11-3 下午2:16:24
	 * @since jdk1.6
	 * 001 获取网点提示信息,参数异常
	 * 002 获取网点提示信息异常
	 */ 
	@ResponseBody
	@RequestMapping(value = "/saveCompInfo", method = { RequestMethod.POST })
	public String saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Long addressId, String address) {
		if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(netId) || PubMethod.isEmpty(compTypeNum) || PubMethod.isEmpty(compName)
				|| PubMethod.isEmpty(compTelephone)){
			return paramsFailure();
		}
		if(!VerifyUtil.isMobile(compTelephone) && !VerifyUtil.isTel(compTelephone)){
			return paramsFailure();
		}
		String saveCompInfo = expGatewayService.saveCompInfo(memberId, netId, compTypeNum, compName, compTelephone, addressId, address);
		//Edit by ccs 20150716
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("compId", saveCompInfo);
		return jsonSuccess(map);
		//Edit by ccs 20150716
	}
	/**
	 * <h2>保存更新网点基础信息 </h2>
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @Description: 保存更新网点基础信息
	 * @date 2014-10-20下午4:21:10
	 * @param loginMemberId 登录人员ID
	 * @param loginCompId 登录公司ID
	 * @param netId 网络ID
	 * @param compTypeNum 网点类型 1006站点 1050营业分部
	 * @param useCompId 领用网点ID
	 * @param compName 网点名称
	 * @param compTelephone 网点电话
	 * @param addressId 网点地址ID
	 * @param address 网点详细地址
	 * @param longitude 网点经度
	 * @param latitude 网点纬度
	 * @param compRegistWay 网点注册方式 5站点 6手机端
	 * @return Json
	 */
	@ResponseBody 
	@RequestMapping(value = "/saveOrUpdateCompBasicInfo", method = { RequestMethod.POST })
	public String saveOrUpdateCompBasicInfo(Long loginMemberId, Long loginCompId, Long netId, String compTypeNum, Long useCompId, String compName, String compTelephone,
			Long addressId, String address, Double longitude, Double latitude, Short compRegistWay) {
			if(PubMethod.isEmpty(loginMemberId) || PubMethod.isEmpty(netId) || PubMethod.isEmpty(compTypeNum) 
												|| PubMethod.isEmpty(compName) || PubMethod.isEmpty(compTelephone)
												|| PubMethod.isEmpty(longitude) || PubMethod.isEmpty(latitude)){
				return paramsFailure();
			}
//			如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
			if(!VerifyUtil.isTel(compTelephone) && !VerifyUtil.isMobile(compTelephone)){
				return paramsFailure();
			}
//			expGatewayService.saveOrUpdateCompBasicInfo(loginMemberId, loginCompId, netId, compTypeNum, useCompId, compName, compTelephone, addressId, address, longitude, latitude, compRegistWay);
			return  null;
	}
	
	 /**
	  	 * <h2>登陆之后更新收派员位置信息</h2>
		 * @Method: updateAddrByMember 
		 * @Description: 登陆之后更新收派员位置信息
		 * @param memberId 人员id
		 * @param lng 经度
		 * @param lat 纬度
		 * @return{"data":{},"success":true}
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-11-3 下午6:56:51
		 * @since jdk1.6
		 */
	 @RequestMapping("updateAddrByMember")
	 @ResponseBody
	 public String updateAddrByMember(Long memberId, Double lng, Double lat) {
		 if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(lng) || PubMethod.isEmpty(lat)){
			 return paramsFailure();
		   }
		 return expGatewayService.updateAddrByMember(memberId, lng, lat);
	 }
 	/**
  	 * <h2>登出时删除收派员的地址信息</h2>
	 * @Method: deleteOnLineMember 
	 * @Description: 出时删除收派员的地址信息
	 * @param memberId 人员id
	 * @return{"data":{},"success":true}
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @date 2014-11-3 下午6:56:51
	 * @since jdk1.6
	 */
	 @RequestMapping("deleteOnLineMember")
	 @ResponseBody
	 public String deleteOnLineMember(Long memberId){
		 Map<String,Object> map = new HashMap<String,Object>();
		 if(PubMethod.isEmpty(memberId)){
			 return paramsFailure();
		   }
		 Map<String, Object> allMap = new HashMap<String, Object>();
		 
		 allMap.put("success", true);
		 allMap.put("data",map);
		 return JSON.toJSONString(allMap);
//		 return expGatewayService.deleteOnLineMember(memberId);
	 }
	 
	//头像
	/**
	 * <h2>修改右侧人员信息(更新在线人员信息) ?  更新角色(收派员,站长,后勤的时候调用)</h2>
	 * @Method: updateOnlineMember 
	 * @Description: 修改右侧人员信息(更新在线人员信息) ?  更新角色(收派员,站长,后勤的时候调用)
	 * @param memberId 人员id
	 * @param roleId 角色id
	 * @param employeeWorkStatusFlag 工作状态标识
	 * @param areaColor 片区颜色
	 * @return{"data":{},"success":true}
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @date 2014-11-3 下午6:56:51
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value = "/updateOnlineMember", method = { RequestMethod.POST })
	public String updateOnlineMember(Long memberId,Short roleId,Short employeeWorkStatusFlag,String areaColor) {
		if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(roleId) || PubMethod.isEmpty(employeeWorkStatusFlag)){
			 return paramsFailure();
		   }
		return expGatewayService.updateOnlineMember(memberId, roleId, employeeWorkStatusFlag, areaColor);
	}
	
	/**
	 * <h2>上传照片时调用 正面照 反面照...</h2>
	 * @Method: upLoadPhoto 
	 * @Description: TODO上传照片时调用 正面照 反面照...
	 * @param type:  contact：联系人 front：身份证前身照  back：身份证背面照 inHand：手持 head：接单王头像 photoPar:包裹拍照
	 * @param memberId
	 * @param uplo adImages
	 * @return {"upload":"success","url":"http://localhost:8080/mob/id/inHand/1234567.jpg/"}
	 * @see net.okdi.mob.service.ExpParGatewayService#uploadPic(java.lang.Long, org.springframework.web.multipart.MultipartFile[]) 
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value="/upLoadPhoto")
	public String upLoadPhoto(Long memberId,@RequestParam(value = "uploadImage", required = false) MultipartFile[] uploadImage,String type){
		if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(uploadImage) || PubMethod.isEmpty(type)){
			 return paramsFailure();
		  }
		return commonService.uploadPic(type, memberId, uploadImage);
	}
	/**
	 * <h2>查询照片时调用</h2>
	 * @Method: queryPhotoByid 
	 * @Description: TODO查询照片时调用
	 * @param Type:  contact：联系人 front：身份证前身照  back：
	 * 				  身份证背面照 inHand：手持 head：接单王头像 photoPar:包裹拍照
	 * 				 Requestphoto_jdw 接单王调用返回正面,反面,手持		 
	 * @param memberId 人员ID
	 * @return {"Back":"http://localhost:8080/mob/id/back/13753198095106048.jpg","Front":"http://localhost:8080/mob/id/front/13753198095106048.jpg","InHand":"http://localhost:8080/mob/id/inHand/13753198095106048.jpg","success":true}
			   {"msg":"文件类型不匹配","success":false}
	 * @see net.okdi.mob.service.ExpParGatewayService#uploadPic(java.lang.Long, org.springframework.web.multipart.MultipartFile[]) 
	 *<dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @since jdk1.6
	*/
	@ResponseBody
	@RequestMapping(value="/queryPhotoByid")
	public String queryPhotoByid(Long memberId,String type){
		if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(type)){
			 return paramsFailure();
		  }
		return commonService.queryPhotoByid(memberId,type);
	}
	/**
	 * <h2>删除照片时调用</h2>
	 * @Method: delFile 
	 * @Description: TODO删除照片时调用
	 * @param Type:  contact：联系人 front：身份证前身照  back：身份证背面照 inHand：手持 head：接单王头像 photoPar:包裹拍照
	 * @param MemberId 人员ID
	 * @return {"success":true}，{"msg":"文件不存在","success":false}
	 * @see net.okdi.mob.service.ExpParGatewayService#uploadPic(java.lang.Long, org.springframework.web.multipart.MultipartFile[]) 
	 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
	 * @since jdk1.6
	*/
	@ResponseBody
	@RequestMapping(value="/delFile")
	public String delFile(Long MemberId,String type){
		if(PubMethod.isEmpty(MemberId) || PubMethod.isEmpty(type)){
			 return paramsFailure();
		  }
		return commonService.delFile(MemberId, type);
	}
	//-任务
	
	/**
	 * @api {post} /expGateway/getTakesByStatus 查询收派员任务(不分页)
	 * @apiPermission user
	 * @apiDescription 查询收派员任务(不分页) xiangwei.liu
 	 * @apiparam {Long} memberId 人员Id
 	 * @apiparam {Integer} taskStatus 任务状态0:全部任务,1:未完成任务,2:已完成任务
 	 * @apiparam {String} taskType 任务类型   0:取件 1:派件 2:自取件
 	 * @apiGroup 派件
 	 * @apiSampleRequest /expGateway/getTakesByStatus
 	 * @apiSuccess {String} AddressMsg 地址信息
 	 * @apiSuccess {String} appointDesc 预约描述
 	 * @apiSuccess {Date} appointTime 预约取件时间
 	 * @apiSuccess {String} contactAddress  详细地址
 	 * @apiSuccess {String} contactMobile  联系人手机
 	 * @apiSuccess {String} contactName 联系人姓名
 	 * @apiSuccess {Date} createTime 创建时间 
 	 * @apiSuccess {Long} id 主键id
 	 * @apiSuccess {Long} memberId 人员id 
 	 * @apiSuccess {Long} taskId 任务id
 	 * @apiError {String} result false
 	 * @apiSuccessExample {json} Success-Response:
 	 *     HTTP/1.1 200 OK
 	 * {"data":[{"AddressMsg":"天使","appointDesc":"人生得意需尽欢，莫使金樽空对月","appointTime":1416621600000,
	 * "contactAddress":"北京门头沟区","contactMobile":"15323252223","contactName":"凯瑟拉","createTime":1416453848000,
	 * "id":14339304704966656,"memberId":13867278975369216,"taskId":14339304703918080}],"success":true}
 	 * @apiErrorExample {json} Error-Response:
 	 *     HTTP/1.1 200 OK
 	 *   {"message":"Parameter is not correct or Parameter format error","success":false}
 	 * @apiVersion 0.2.0
 	 */
		@ResponseBody
		@RequestMapping(value = "/getTakesByStatus", method = { RequestMethod.POST })
		public String getTakesByStatus(Long memberId,Integer taskStatus,String taskType){ 
			if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(taskStatus)){
				 return paramsFailure();
			  }
			return 	expGatewayService.getStatusBytasks(memberId,taskStatus,taskType);
			
		}
		/**
		 * <h2>查询任务明细 </h2>
		 * @Method: queryTaskDetail 
		 * @Description: 查询任务明细
		 * @param id id
		 * @return     {"success":true,"taskinfo":{"actorMemberName":"后勤二","appointDesc":"费大富大贵","appointTime":1415030400000,"contactAddress":"北京-昌平区 满白路101号","contactAddressId":11000211,"contactMobile":"13011114444","contactName":"简洁大方","contactTel":"","coopCompName":"风驰申通快递总站","createTime":1415091531000,"taskFlag":0,"taskId":13982181555699712,"taskIsEnd":0,"taskSource":2,"taskStatus":1,"taskType":0}}
		 * 			   {"message":"查询任务信息失败","success":false}
		 * @author xiangwei.liu
		 * @date 2014-10-28 下午2:01:18
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @since jdk1.6    
		 */ 
		@ResponseBody
		@RequestMapping(value = "/queryTaskDetail", method = { RequestMethod.POST })
		public String queryTaskDetail(Long id) {
			if(PubMethod.isEmpty(id)){
				 return paramsFailure();
			  }
			return expGatewayService.queryTaskDetail(id);
		}
		/**
		 * <h2>接单王更新客户数据（更改发件人）</h2>
		 * @Method: updateContacts 
		 * @Description: 接单王更新客户数据（更改发件人）
		 * @param taskId 任务id
		 * @param contactName 发件人姓名
		 * @param contactMobile 发件人手机
		 * @param contactTel 发件人座机
		 * @param contactAddressId 发件人地址id
		 * @param contactAddress 地址详情 (北京-海淀区 田村路)
		 * @param customerId 客户id
		 * @return
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-11-12 上午9:59:13
		 * @since jdk1.6
		 */
		@ResponseBody
		@RequestMapping(value = "/updateContacts", method = { RequestMethod.POST })
		public String updateContacts(Long taskId, String contactName, String contactMobile, String contactTel, Long contactAddressId, String contactAddress, Long customerId) {
		
			if(PubMethod.isEmpty(taskId) || PubMethod.isEmpty(contactName) || PubMethod.isEmpty(contactMobile)
										 || PubMethod.isEmpty(customerId)){
				 return paramsFailure();
			  }
			 if(PubMethod.isEmpty(contactAddressId) && PubMethod.isEmpty(contactAddress)){
				 return paramsFailure();
			 }
			 if(!VerifyUtil.isTel(contactTel) && !VerifyUtil.isMobile(contactMobile)){
					return paramsFailure();
				}
			return  expGatewayService.updateContacts(taskId, contactName, contactMobile, contactTel, contactAddressId, contactAddress, customerId);
		}
		/**
		 * <h2>分配收派员</h2>
		 * @Method:toMember
		 * @Description: 分配收派员
		 * @param id id
		 * @param formCompId 分配方公司id
		 * @param fromMemberId 分配方人员id
		 * @param toCompId 接收方营业分部id
		 * @param toMemberId 接收方人员id
		 * @return{"data":{"resultlist":[{"compName":"AA1","createTime":"2014-11-04 17:19:35","memberName":"jack1","taskProcessDesc":"网点指定任务给：jack1"},{"compName":"AA1","createTime":"2014-11-04 17:41:30","memberName":"jack1","taskProcessDesc":"任务已被取消：多次联系不上客户，上门无人"}],"taskinfo":{"actorMemberName":"jack1","appointDesc":"","appointTime":"","contactAddress":"北京-海淀区 翠微路10号","contactAddressId":11000206,"contactMobile":"13422200001","contactName":"涨到","contactTel":"","coopCompName":"AA1","createTime":"2014-11-04 17:19:35","taskFlag":0,"taskId":13982507694817280,"taskIsEnd":0,"taskSource":2,"taskStatus":3,"taskType":0}},"success":true}
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-10-29 下午3:15:32
		 * @since jdk1.6
		 */
		@ResponseBody
		@RequestMapping(value = "/toMember", method = {RequestMethod.GET , RequestMethod.POST })
		public String toMember(String id, Long formCompId, Long fromMemberId, Long toMemberId) {
			if(PubMethod.isEmpty(id) || PubMethod.isEmpty(formCompId) || PubMethod.isEmpty(fromMemberId)){
					 return paramsFailure();
			}
			return expGatewayService.toMember(id, formCompId, fromMemberId, toMemberId);
		}
		
		/**
		 * @api {post} /expGateway/memberToMember 收派员指派收派员
		 * @apiPermission user
		 * @apiDescription 接口描述 抢送货(进行中,以完成)
		 * @apiparam {Short} id id
		 * @apiparam {Long} fromMemberId 分配方人员id
		 * @apiparam {Integer} toMemberId 接收方人员id
		 * @apiGroup 取件
		 * @apiSampleRequest /expGateway/memberToMember
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"success":"false"}
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/memberToMember", method = {RequestMethod.GET , RequestMethod.POST })
		public String memberToMember(String id, Long fromMemberId, Long toMemberId) {
			if(PubMethod.isEmpty(id) || PubMethod.isEmpty(fromMemberId) || PubMethod.isEmpty(toMemberId)){
				 return paramsFailure();
			}
			return expGatewayService.memberToMember(id, fromMemberId, toMemberId);
		}
		/**
		 * <h2>收派员取消</h2>
		 * @Method: cancelMember 
		 * @Description: 收派员取消
		 * @param taskId 任务id
		 * @param memberId 操作人id
		 * @param taskTransmitCause 任务转单原因  1、客户拒绝发件  2、多次联系不上客户，上门无人 3、其它原因 10、超出本网点范围  11、网点任务太多，忙不过来 12、超出本人收派范围 13、本人任务太多，忙不过来
		 * @param disposalDesc 取消备注
		 * @return {"success":true}
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-10-28 上午11:51:31
		 * @since jdk1.6
		 */
		@ResponseBody
		@RequestMapping(value = "/cancelMember", method = {RequestMethod.GET , RequestMethod.POST })
		public String cancelMember(String taskId, Long memberId, Byte taskTransmitCause, String disposalDesc , String expFlag) {
			if(PubMethod.isEmpty(taskId) || PubMethod.isEmpty(memberId) || PubMethod.isEmpty(taskTransmitCause)){
				 return paramsFailure();
			}
			return expGatewayService.cancelMember(taskId, memberId, taskTransmitCause, disposalDesc , expFlag);
		}
		/**
		 * <h2>查询取消任务</h2>
		 * @Method: queryRefuseTask 
		 * @Description: 查询取消任务
		 * @param senderName 发件人
		 * @param startTime 开始日期
		 * @param endTime 结束日期
		 * @param senderPhone 发件人手机号
		 * @param operatorCompId 操作人公司id
		 * @param page  offset 开始位置，从0开始   pageSize 每页显示记录的条数
		 * @return
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-10-29 下午3:18:11
		 * @since jdk1.6  
		 */
		@ResponseBody
		@RequestMapping(value = "/queryRefuseTask", method = { RequestMethod.POST })
		public String queryRefuseTask(String senderName, String startTime, String endTime, String senderPhone,
				 Long operatorCompId, Page page) {
			if(PubMethod.isEmpty(senderName) || PubMethod.isEmpty(startTime) || PubMethod.isEmpty(endTime) || PubMethod.isEmpty(operatorCompId)){
				 return paramsFailure();
			}
			return expGatewayService.queryRefuseTask(senderName, startTime, endTime, senderPhone, operatorCompId, page);
		}
		
		/**
		 * @api {post} /expGateway/finishTask 任务完结
		 * @apiPermission user
		 * @apiDescription 任务完结	 xiangwei.liu
		 * @apiparam {Long} id 主键id
		 * @apiparam {String} memberId 操作人id
		 * @apiparam {String} compId 操作网点id
		 * @apiGroup 派件
		 * @apiSampleRequest /expGateway/finishTask
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"openapi.ExpGatewayController.finishTask.001","message":"id能为空","success":false}
		 * @apiVersion 0.2.0
		 */ 
		@ResponseBody
		@RequestMapping(value = "/finishTask", method = { RequestMethod.POST })
		public String finishTask(Long id, Long memberId, Long compId, Long netId,Long takeTaskId) {
			if(PubMethod.isEmpty(id) || PubMethod.isEmpty(compId) || PubMethod.isEmpty(netId)){
				 return paramsFailure();
			}
			return expGatewayService.finishTask(id, memberId, compId ,netId,takeTaskId);
		}
		/**
		 * <h2>任务完结</h2>
		 * @Method: finishTask 
		 * @Description: 任务完结
		 * @param id 主键id
		 * @param memberId 操作人id
		 * @param compId 操作网点id
		 * @return {"success":true}
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-10-31 上午10:17:59
		 * @since jdk1.6
		 */
		@ResponseBody
		@RequestMapping(value = "/finishTaskAmssy", method = { RequestMethod.POST })
		public String finishTaskAmssy(Long taskId) {
			if(PubMethod.isEmpty(taskId)){
				 return paramsFailure();
			}
			return expGatewayService.finishTask(taskId);
		}
		/**
		 * <h2>接单王创建任务</h2>
		 * @Method: doParTaskrecord
		 * @Description: 接单王创建任务
		 * @param fromCompId 任务受理方站点 
		 * @param memberId 任务受理人员  --收派员
		 * @param toCompId 营业分部   站点id
		 * @param coopNetId 任务受理方网络       
		 * @param appointTime 约定取件时间 
		 * @param appointDesc 取件备注         
		 * @param contactName 客户姓名       
		 * @param contactMobile 客户手机 
		 * @param contactTel 客户电话 
		 * @param contactAddressId 客户地址ID(没有不写)  
		 * @param contactAddress 客户详细地址 
		 * @param customerId 客户ID (没有不写)
		 * @param contactAddrLongitude 客户地址的经度信息 
		 * @param contactAddrLatitude 客户地址的纬度信息
		 * @return{"success":true}
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-10-28 上午11:48:32
		 * @since jdk1.6
		 */
		@ResponseBody
		@RequestMapping(value = "/doParTaskrecord", method = { RequestMethod.POST })
		public String doParTaskrecord(Long fromCompId, Long memberId, Long toCompId, Long coopNetId, String appointTime, String appointDesc, String contactName, String contactMobile,
				String contactTel, Long contactAddressId, String contactAddress, Long customerId, BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude){
			if(PubMethod.isEmpty(fromCompId) || PubMethod.isEmpty(memberId) || PubMethod.isEmpty(toCompId) ||
			  PubMethod.isEmpty(coopNetId) || PubMethod.isEmpty(contactName) || PubMethod.isEmpty(contactMobile) ||
			  PubMethod.isEmpty(contactAddrLongitude) || PubMethod.isEmpty(contactAddrLatitude)){
				 return paramsFailure();
			}
			
			 if(PubMethod.isEmpty(contactAddressId) && PubMethod.isEmpty(contactAddress)){
				 return paramsFailure();
			 }
			if(!PubMethod.isEmpty(contactTel)){
				if(!VerifyUtil.isTel(contactTel))
					return paramsFailure();
			}
			if(!VerifyUtil.isMobile(contactMobile))
				return paramsFailure();
			
			return expGatewayService.doParTaskrecord(fromCompId, memberId, toCompId, memberId, coopNetId, appointTime, appointDesc, memberId, contactName, contactMobile, contactTel, contactAddressId, contactAddress, customerId, memberId, contactAddrLongitude, contactAddrLatitude);
		}
		//收派员管理
 		/**
		 * <h2>加人员的时候检查手机号是否已存在</h2>
		 * @Method: checkTel 
		 * @Description: 添加人员的时候检查手机号是否已存在
		 * @param associatedNumber 手机号
		 * @param compId 网点id
		 * @return	数据库中不存在这个手机号 flag = 2(只有返回2时才可以添加);
		 * 			flag = 1; 本站点存在
		 * 		    flag = 5; 非本站点存在
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-11-3 下午6:57:48
		 * @since jdk1.6
		 */
		@ResponseBody
		@RequestMapping(value = "/checkTel", method = { RequestMethod.POST, RequestMethod.GET })
		public String checkTel(String associatedNumber,Long compId){
			if(PubMethod.isEmpty(associatedNumber) || PubMethod.isEmpty(compId)){
				 return paramsFailure();
			}
			if(!VerifyUtil.isMobile(associatedNumber))
				return paramsFailure();
			return expGatewayService.checkTel(associatedNumber,compId);
		}
		/**
		 * <h2>站点添加的收派员的删除操作(包括手机端的离职接口 )</h2>
		 * @Method: deleteMemberInfo 
		 * @Description: 站点添加的收派员的删除操作(包括手机端的离职接口 )
		 * @param memberId 人员id
		 * @param compId 站点id
		 * @param memberName 人员姓名
		 * @param memberPhone 人员手机号
		 * @param userId 操作人Id
	     * @return{"data":{"isFinish":0},"success":true}
		 * 			isFinish 0 说明删除关系成功
		 * 			isFinish 1 说明该人员有任务存在不能删除
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @throws
		 */
		@ResponseBody
		@RequestMapping(value = "/deleteMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
		public String  deleteMemberInfo(Long memberId,Long compId,String memberName,String memberPhone,Long userId){
			if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(compId) || PubMethod.isEmpty(memberPhone)){
				 return paramsFailure();
			}
			if(!VerifyUtil.isMobile(memberPhone))
				return paramsFailure();
			return expGatewayService.deleteMemberInfo(memberId, compId, memberName, memberPhone ,userId);
		}
		
		/** <dt><span class="strong">方法描述:</span></dt><dd>
		 * 从api缓存中查询人员列表,如果缓存中没有则去数据库中查询</dd> <dt><span class="strong">作者:</span></dt>
		 * <dd>haifeng.he</dd> <dt><span class="strong">时间:</span></dt><dd>
		 * 2014-11-12 下午1:19:04</dd>
		 * 
		 * @param logMemberId
		 *            当前登录用户的memberId
		 * @param compId
		 *            网点id
		 * @return { "areaColor": "#c2c2c2", "compId": 13867330511306752,
		 *         "employeeWorkStatusFlag": 1, 工作状态 "id_num": "230121198203215698",
		 *         "memberId":13867278975369216, "memberName": "管理员", "memberPhone":
		 *         "13177770001", "memberSource": 1, "roleId": 1 } <dd>areaColor -
		 *         片区颜色</dd> <dd>compId - 网点id</dd> <dd>employeeWorkStatusFlag -
		 *         工作状态 1．在岗2．下班3．休假4．任务已满，暂不接单</dd> <dd>id_num - 身份证号</dd> <dd>
		 *         memberId - 人员id</dd> <dd>memberName - 人员姓名</dd> <dd>memberPhone -
		 *         人员手机号</dd> <dd>memberSource - 人员来源 0:手机端1:站点</dd> <dd>roleId -
		 *         角色id -1: 后勤0 : 收派员1 : 大站长 -2：小站长</dd>
		 * @since v1.0
		 */
		@ResponseBody
		@RequestMapping(value = "/getStationMember", method = { RequestMethod.POST, RequestMethod.GET })
		public String  getStationMember(Long logMemberId,Long compId){
			if(PubMethod.isEmpty(compId) || PubMethod.isEmpty(logMemberId)){
				 return paramsFailure();
			}
			Map<String, Object> stationMember = expGatewayService.getStationMember(logMemberId,compId);
			return JSON.toJSONString(stationMember);
		}
		
		/**
		 * <h2>查询收派员详细信息(工作状态)  可以返回compId</h2>
		 * @Method: getMemberInfoById memberId查询收派员详细信息(工作状态)  可以返回compId
		 * @Description: TODO
		 * @param memberId 人员id
		 * @return{"data":{"areaColor":"#c2c2c2","employeeWorkStatusFlag":1,"memberId":13753198095106048,"memberName":"之之之","roleId":1},"success":true}
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @date 2014-11-10 上午9:35:54
		 * @since jdk1.6
		 */
		@ResponseBody
		@RequestMapping(value = "/getMemberInfoById", method = { RequestMethod.POST, RequestMethod.GET })
		public String  getMemberInfoById(Long memberId) {
			if(PubMethod.isEmpty(memberId)){
				 return paramsFailure();
			}
			return expGatewayService.getMemberInfoById(memberId);
		}
		//注册收派员时添加relation的接口
		
		
		
		
		//注册收派员的接口
		/**
		 * <h2>保存人员详细信息（添加,注册收派员关系）</h2>
		 * @Method: saveCompPersInfo   保存人员详细信息（注册,并保存收派员关系）
		 * @Description: 
		 * @param memberId 人员id
		 * @param compId 网点id
		 * @param memberName 人员姓名
		 * @param roleId 角色id
		 * @param memberPhone 人员电话
		 * @param applicationDesc  上传5个单号
		 * @date 2014-11-5 下午12:42:18
		 * @since jdk1.6 
		 * @return  {"data":"","success":true}|{"message":"操作异常 ","success":false}
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 */
		@ResponseBody
		@RequestMapping(value = "/saveCompPersInfo", method = { RequestMethod.POST })
		public String saveCompPersInfo(Long memberId ,Long compId,String memberName,short roleId,String memberPhone,String applicationDesc) {
			if(PubMethod.isEmpty(compId) || PubMethod.isEmpty(roleId) || PubMethod.isEmpty(memberPhone) || PubMethod.isEmpty(memberId)){
				 return paramsFailure();
			}
			return expGatewayService.saveCompPersInfo(memberId,compId,memberName, roleId,memberPhone,applicationDesc);
		}
//		添加收派员可以调用
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>添加人员接口</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:06:54</dd>
		 * @param compId 网点id
		 * @param associatedNumber	手机号
		 * @param memberName	人员姓名
		 * @param roleId	注册角色id
		 * @param areaColor	片区颜色
		 * @param userId	审核人
		 * @param memberSource	来源 0手机端1站点
		 * @return {"data":"","success":true}
		 * @since v1.0
		 */
		@ResponseBody			  
		@RequestMapping(value = "/addMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
		public String addMemberInfo(Long compId,String associatedNumber,String memberName,Short roleId,String areaColor,Long userId,Short memberSource){
			if(PubMethod.isEmpty(compId) || PubMethod.isEmpty(roleId) || PubMethod.isEmpty(associatedNumber) || PubMethod.isEmpty(memberName)
			   || PubMethod.isEmpty(userId) || PubMethod.isEmpty(memberSource)	){
				 return paramsFailure();
			}
			return expGatewayService.addMemberInfo(compId, associatedNumber, memberName, roleId, areaColor, userId, memberSource);
		}
		
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>修改人员所在站点 从旧站点修改到新站点</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:03:05</dd>
		 * @param compIdOld 旧网点id
		 * @param compIdNew	新网点id
		 * @return {"data":"","success":true}
		 * @since v1.0
		 */
		@ResponseBody
		@RequestMapping(value = "/doEditComp", method = { RequestMethod.POST, RequestMethod.GET })
		public String doEditComp(Long compIdOld,Long compIdNew){
			return expGatewayService.doEditComp(compIdOld, compIdNew);
		}
		
		/**
		* <dt><span class="strong">方法描述:</span></dt><dd>后端已调用,前段无需再次调用</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:03:05</dd>
		 * @Method: getAuditInfo   
		 * @Description:
		 * @param memberId 人员id
		 * @since jdk1.6
		 * @return
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 */
		@ResponseBody
		@RequestMapping(value = "/getAuditInfo", method = { RequestMethod.POST })
		public String getAuditInfo(Long memberId) {
			if(PubMethod.isEmpty(memberId)){
				 return paramsFailure();
			}
			return expGatewayService.getAuditInfo(memberId);
		}
		//短信
		/**
		 * <h2>单发,群发短信</h2>
		 * @Description: 
		 * @param channelNO 00
		 * @param extraCommonParam 商家或者客户端的ID(可为空)
		 * @param netNames 网络名称
		 * @param BasicContent 基本信息
		 * @param sendAddress  所在地址
		 * @param netId  网络Id
		 * @param memberPhone 收派员电话
		 * @param usePhones 客户电话用,分隔
		 * @param waybillNum 运单号多串单号用|分隔
		 * @return  {"fail":"222|18530000002|1|56736650830962688|smsSend.err.110",	"success":"111|18530000001|1|56736650830962689","status":"smsS	end.err.200"}
					全部失败:
					{"fail":"111|18530000001|1|56736650830962688|smsSend.err.110^2	22|18530000002|1|56736650830962689|smsSend.err.110","success":	"","status":"smsSend.err.201"}
					全部成功：
					{"fail":"","success":"111|18530000001|1|56736650830962688^222|	18530000002|1|56736650830962688","status":"0"}
		   <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @since jdk1.6
		 * @return
		 */
	/*	@ResponseBody
		@RequestMapping(value = "/doSmsSend", method = { RequestMethod.POST })
		public String doSmsSend(String channelNO,Long extraCommonParam, String  usePhones,
				String  BasicContent,String netNames,String waybillNum,String memberPhone,String lat,String lng,String compId,String  version){
			if(PubMethod.isEmpty(usePhones) || PubMethod.isEmpty(BasicContent) || PubMethod.isEmpty(netNames) ||
               PubMethod.isEmpty(memberPhone) || PubMethod.isEmpty(lat) || PubMethod.isEmpty(lng)){
						 return paramsFailure();
			}
			if(!VerifyUtil.isMobile(memberPhone))
				 return paramsFailure();
			return expGatewayService.doSmsSend( channelNO, extraCommonParam,  usePhones,
					  BasicContent, netNames, waybillNum, memberPhone, lat, lng,compId,version);
		}
		@ResponseBody
		@RequestMapping(value = "/sendSms", method = { RequestMethod.POST })
		public String sendSms(String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat){
			try{
				if(PubMethod.isEmpty(smsTemplate) || PubMethod.isEmpty(phoneAndWaybillNum) || PubMethod.isEmpty(memberId) ||
			               PubMethod.isEmpty(lng) || PubMethod.isEmpty(lat)){
									 return paramsFailure();
						}
//						if(!VerifyUtil.isMobile(memberPhone))
//							 return paramsFailure();
						return expGatewayService.sendSms(smsTemplate, phoneAndWaybillNum, memberId, lng, lat,true,true,null,null);
			}catch(Exception e){
				e.printStackTrace();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				return 	JSON.toJSONString(map);
			}
		}*/
		/**
		 * @api {post} /expGateway/sendSmsNew 短信聊天
		 * @apiPermission user
		 * @apiDescription  短信聊天 kai.yang
		 * @apiparam {String} phoneAndWaybillNum 客户的手机号，订单号，编号,这三个字段用"—"拼接成一个整体，每个整体用"|"分隔开
		 * @apiparam {String} smsTemplate 短信模板
		 * @apiparam {Long} memberId 当前收派员的memberId
		 * @apiparam {String} lng 经度
		 * @apiparam {String} lat 纬度
		 * @apiparam {String} msgId 短信id 免费短信中为空，聊天界面发送不为空
		 * @apiparam {String} accountId 钱包id
		 * @apiparam {String} pickupTime 取件时间
		 * @apiparam {String} pickupAddr 取件地址
		 * @apiparam {String} compName 公司名称
		 * @apiparam {String} flag 标识, 普通单聊天传 4,淘宝单传 9
		 * @apiGroup 通知管理
		 * @apiSampleRequest /expGateway/sendSmsNew
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 * 		{"success":"true"}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   	
		 * @apiVersion 0.2.0
		 */
		
		@ResponseBody
		@RequestMapping(value = "/sendSmsNew", method = { RequestMethod.POST,RequestMethod.GET})
		public String sendSmsNew(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,String msgId, 
				String pickupTime, String pickupAddr, String compName){
			logger.info("短信聊天请求方法sendSmsNew参数：accountId="+accountId+",smsTemplate="+smsTemplate+",phoneAndWaybillNum="+phoneAndWaybillNum+"," +
					"memberId="+memberId+",msgId="+msgId);
			try{
				if(PubMethod.isEmpty(smsTemplate) || PubMethod.isEmpty(phoneAndWaybillNum) || PubMethod.isEmpty(memberId) ||
			               PubMethod.isEmpty(lng) || PubMethod.isEmpty(lat)){
									 return paramsFailure();
						}
				if(PubMethod.isEmpty(accountId)){
					accountId = this.walletNewService.getAccountId(memberId);//根据memberId查询accountId
				}
				String memberInfo = this.expGatewayService.getMemberInfoById(memberId); 
				String isWx="1";
				Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
				String memberPhone = memberMap.get("memberPhone").toString();
				String sendNum = telephoneRewardService.getSMSOrPhoneNum(accountId, memberPhone);//郑炯的查询可用电话、短信和免费短信条数
				JSONObject jsons=JSONObject.parseObject(sendNum);
				String dataAndStr=taskMassNoticeService.queryIsExist(memberId,phoneAndWaybillNum.split("-")[0]);
				JSONObject jsonAndMoile=JSONObject.parseObject(dataAndStr);
				logger.info("dataAndStr:"+dataAndStr);//{"data":1,"str":"15810885211-1","success":true}
				Short isCharge=0;
				Long count=0l;
				if(jsonAndMoile.getBoolean("success")){
					count=jsonAndMoile.getLong("data");
					if(count>0){
						isCharge=1;
					}
				}
				
				String str=sendPackageService.queryOpenIdState(phoneAndWaybillNum.split("-")[0]);
				logger.info("聊天---根据手机号查询该用户关注微信状态str: "+str+" ,该手机号phone: "+memberPhone);
				JSONObject jsonWx=JSONObject.parseObject(str);
				if(jsonWx.getBooleanValue("success")){
					//isWx="1";
					logger.info("聊天---解析得到的json串为jsonWx: "+jsonWx);
					JSONObject jsonObject = jsonWx.getJSONObject("data");
					if(!PubMethod.isEmpty(jsonObject.getString("isRemove"))){
						isWx = jsonObject.getString("isRemove");//0:关注, 1:手动解绑, 2:取消关注解绑
					}
				}
			if (jsons.getBooleanValue("success")) { // 调用接口成功
				String data = jsons.getString("data");
				jsons = JSONObject.parseObject(data);
				Integer one = jsons.getInteger("one");
				Integer len = smsTemplate.length();
				Integer feeCount = 1;
				if (len > one) {
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("cause", "短信内容不能超过" + one + "个字");
					errorMap.put("flag", "");
					return JSON.toJSONString(errorMap);
				}
				Integer preferentialNum = 0;
				if (!"no".equals(jsons.getString("preferentialNum"))) { // 查出免费条数
					preferentialNum = Integer.valueOf(jsons.getString("preferentialNum"));
				}
				if (!"no".equals(jsons.getString("lenSms"))) {
					Integer lenSms = Integer.valueOf(jsons.getString("lenSms"));
					Integer all=0;
					if(isCharge==0){
						all=lenSms + preferentialNum;
					}else{
						all=lenSms;
					}
					if (feeCount > all) {
//					if (feeCount > lenSms) {
						Map<String, Object> errorMap = new HashMap<String, Object>();
						errorMap.put("success", false);
						errorMap.put("cause", "余额不足，群发通知失败");
						errorMap.put("flag", "1");
						return JSON.toJSONString(errorMap);
					}
				}

			} else {
				Map<String, Object> errorMap = new HashMap<String, Object>();
				errorMap.put("success", false);
				errorMap.put("cause", "发送短信失败");
				return JSON.toJSONString(errorMap);
			}
			String smsStartTime=constPool.getSmsStartTime();//短信开始时间
			String smsEndTime = constPool.getSmsEndTime();//短信结束时间
			String smsCause = constPool.getSmsCause();//提示语
			logger.info("聊天通知短信的开始时间smsStartTime: "+smsStartTime+" ,结束时间smsEndTime: "+smsEndTime+" ,提示语smsCause: "+smsCause);
			
				Date date=new Date();
				String start=new SimpleDateFormat(smsStartTime).format(date);
				String end=new SimpleDateFormat(smsEndTime).format(date);
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date startTime=sim.parse(start);
					Date endTime=sim.parse(end);
					if(date.after(startTime)&&date.before(endTime)){
						Map<String,Object> errorMap = new HashMap<String,Object>();
						errorMap.put("success",false);
						errorMap.put("cause", smsCause);
						errorMap.put("flag", "");
						return JSON.toJSONString(errorMap);
					}
				} catch (ParseException e) {
					e.printStackTrace();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("success", false);
					return 	JSON.toJSONString(map);
				}
				logger.info("最初的格式phoneAndWaybillNum: "+phoneAndWaybillNum);
				//根据firstMsgId查询包裹的编号
				String number = expGatewayService.queryParcelNumber(msgId);
				String[] strings = phoneAndWaybillNum.split("-");
				phoneAndWaybillNum = strings[0]+"-"+isWx+"-"+number+"-"+isCharge;//手机号-是否关注微信-编号-是否扣费
				logger.info("短信聊天拼装之后的数据phoneAndWaybillNum: "+phoneAndWaybillNum);
				//phoneAndWaybillNum+"-"+isWx+"-"+isCharge
				return expGatewayService.sendSms(accountId,smsTemplate, phoneAndWaybillNum, memberId, lng, lat,false,true,null,msgId,(short)0, pickupTime, pickupAddr, compName, (short)4, memberPhone, "");//
			}catch(Exception e){
				e.printStackTrace();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				return 	JSON.toJSONString(map);
			}
		}
		/**
		 * @api {post} /expGateway/aliSendSmsNew 阿里短信聊天
		 * @apiPermission user
		 * @apiDescription  短信聊天 jiong.zheng
		 * @apiparam {String} phoneAndWaybillNum 客户的运单号，订单号，编号, 例如 运单号-订单号-编号  这样, 注:没有订单号拼上空
		 * @apiparam {String} smsTemplate 短信模板
		 * @apiparam {Long} memberId 当前收派员的memberId
		 * @apiparam {String} lng 经度
		 * @apiparam {String} lat 纬度
		 * @apiparam {String} msgId 短信id 免费短信中为空，聊天界面发送不为空
		 * @apiparam {String} accountId 钱包id
		 * @apiparam {String} pickupTime 取件时间
		 * @apiparam {String} pickupAddr 取件地址
		 * @apiparam {String} compName 公司名称
		 * @apiparam {String} netId 网络id
		 * @apiparam {String} netName 网络名称
		 * @apiparam {String} versionCode 版本号
		 * @apiGroup 通知管理
		 * @apiSampleRequest /expGateway/aliSendSmsNew
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 * 		{"success":"true"}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   	
		 * @apiVersion 0.2.0
		 */
		
		@ResponseBody
		@RequestMapping(value = "/aliSendSmsNew", method = { RequestMethod.POST,RequestMethod.GET})
		public String aliSendSmsNew(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,
				String msgId, String pickupTime, String pickupAddr, String compName, String netId, String netName){
			logger.info("短信聊天请求方法aliSendSmsNew参数：accountId="+accountId+",smsTemplate="+smsTemplate+",phoneAndWaybillNum="+phoneAndWaybillNum+"," +
					"memberId="+memberId+",msgId="+msgId+" ,netId: "+netId +" ,netName: "+netName);
			try{
				if(PubMethod.isEmpty(smsTemplate) || PubMethod.isEmpty(phoneAndWaybillNum) || PubMethod.isEmpty(memberId) ||
						PubMethod.isEmpty(lng) || PubMethod.isEmpty(lat)){
					return paramsFailure();
				}
				if(PubMethod.isEmpty(accountId)){
					accountId = this.walletNewService.getAccountId(memberId);//根据memberId查询accountId
				}
				String memberInfo = this.expGatewayService.getMemberInfoById(memberId); 
				Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
				String memberPhone = memberMap.get("memberPhone").toString();
				String sendNum = telephoneRewardService.getSMSOrPhoneNum(accountId, memberPhone);//郑炯的查询可用电话、短信和免费短信条数
				JSONObject jsons=JSONObject.parseObject(sendNum);
				String dataAndStr=taskMassNoticeService.queryIsExist(memberId,phoneAndWaybillNum.split("-")[0]);
				JSONObject jsonAndMoile=JSONObject.parseObject(dataAndStr);
				logger.info("阿里聊天dataAndStr:"+dataAndStr);//{"data":1,"str":"15810885211-1","success":true}
				Long chargeNum=0l;
				if(!PubMethod.isEmpty(jsonAndMoile.getLong("data"))){
					chargeNum = jsonAndMoile.getLong("data");
				}
				Short isCharge=0;
				Long count=0l;
				if(jsonAndMoile.getBoolean("success")){
					count=jsonAndMoile.getLong("data");
					if(count>0){
						isCharge=1;
					}
				}
				/*String str=sendPackageService.queryOpenIdState(memberPhone);
				logger.info("聊天---根据手机号查询该用户关注微信状态str: "+str+" ,该手机号phone: "+memberPhone);
				JSONObject jsonWx=JSONObject.parseObject(str);
				if(jsonWx.getBooleanValue("success")){
					//isWx="1";
					logger.info("聊天---解析得到的json串为jsonWx: "+jsonWx);
					JSONObject jsonObject = jsonWx.getJSONObject("data");
					if(!PubMethod.isEmpty(jsonObject.getString("isRemove"))){
						isWx = jsonObject.getString("isRemove");//0:关注, 1:手动解绑, 2:取消关注解绑
					}
				}*/
				if (jsons.getBooleanValue("success")) { // 调用接口成功
					String data = jsons.getString("data");
					jsons = JSONObject.parseObject(data);
					Integer one = jsons.getInteger("one");
					Integer len = smsTemplate.length();
					Integer feeCount = 1;
					if (len > one) {
						Map<String, Object> errorMap = new HashMap<String, Object>();
						errorMap.put("success", false);
						errorMap.put("cause", "短信内容不能超过" + one + "个字");
						errorMap.put("flag", "");
						return JSON.toJSONString(errorMap);
					}
					Integer preferentialNum = 0;
					if (!"no".equals(jsons.getString("preferentialNum"))) { // 查出免费条数
						preferentialNum = Integer.valueOf(jsons
								.getString("preferentialNum"));
					}
					if (!"no".equals(jsons.getString("lenSms"))) {
						Integer lenSms = Integer.valueOf(jsons.getString("lenSms"));
						Integer all=0;
						if(isCharge==0){
							all=lenSms + preferentialNum;
						}else{
							all=lenSms;
						}
						if (feeCount > all) {
//						if (feeCount > lenSms) {
							Map<String, Object> errorMap = new HashMap<String, Object>();
							errorMap.put("success", false);
							errorMap.put("cause", "余额不足，群发通知失败");
							errorMap.put("flag", "1");
							return JSON.toJSONString(errorMap);
						}
					}
					
				} else {
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("cause", "发送短信失败");
					errorMap.put("flag", "");
					return JSON.toJSONString(errorMap);
				}
				//*******************发送短信的时间限制******************************
				String smsStartTime=constPool.getSmsStartTime();//短信开始时间
				String smsEndTime = constPool.getSmsEndTime();//短信结束时间
				String smsCause = constPool.getSmsCause();//提示语
				logger.info("普通聊天的开始时间smsStartTime: "+smsStartTime+" ,结束时间smsEndTime: "+smsEndTime+" ,提示语smsCause: "+smsCause);
				Date date=new Date();
				String start=new SimpleDateFormat(smsStartTime).format(date);
				String end=new SimpleDateFormat(smsEndTime).format(date);
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date startTime=sim.parse(start);
					Date endTime=sim.parse(end);
					if(date.after(startTime)||date.before(endTime)){
						Map<String,Object> errorMap = new HashMap<String,Object>();
						errorMap.put("success",false);
						errorMap.put("cause", smsCause);
						errorMap.put("flag", "");
						return JSON.toJSONString(errorMap);
					}
				} catch (ParseException e) {
					e.printStackTrace();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("success", false);
					return 	JSON.toJSONString(map);
				}
				logger.info("最初的格式phoneAndWaybillNum: "+phoneAndWaybillNum);
				//根据firstMsgId查询包裹的编号
				//String number = expGatewayService.queryParcelNumber(msgId);
				//String[] strings = phoneAndWaybillNum.split("-");
				//运单号，订单号，编号
				String[] arr = phoneAndWaybillNum.split("-");
				String phoneAndWaybillNumNew = arr[0]+"-"+arr[2]+"-"+netId+"-"+netName+"-"+isCharge;//运单号-编号-netId-网络名称-是否扣费
				logger.info("阿里大于短信聊天拼装之后的数据phoneAndWaybillNum: "+phoneAndWaybillNum);
				//phoneAndWaybillNum+"-"+isWx+"-"+isCharge
				return alisendNoticeService.sendAliSms(accountId,smsTemplate, phoneAndWaybillNumNew, memberId, lng, lat,false,true,null,msgId,(short)0, pickupTime, pickupAddr, compName, (short)4, memberPhone, "","");
			}catch(Exception e){
				e.printStackTrace();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				return 	JSON.toJSONString(map);
			}
		}
		/**
		 * @api {post} /expGateway/findMassnotificationrecord 群发通知记录
		 * @apiPermission user
		 * @apiDescription  查询短信记录新-胡宣化
		 * @apiparam {int} pageNo  页码
		 * @apiparam {int} pageSize 每页显示数量
		 * @apiparam {Long} memberId 当前收派员的memberId
		 * @apiparam {String} date 查询时间   实例 2015-08-31
		 * @apiparam {Short} status  状态值 全部状态:0; 呼叫中:1; 已接听:2;未接听:3; 呼叫失败:4;已发送:5;已收到:6; 发送失败:7;客户回复:8;客户拒绝:9;重复内容拦截:10;微信成功:12;微信失败:13;微信回复未读:14
		 * @apiparam {Short} type  类型 电话优先:2; 仅电话:3; 仅短信:1; 群呼+短信 :7;微信:8;大于短信:9
		 * @apiparam {String} receiverPhone 手机号
		 * @apiparam {String} sendContent 关键字
		 * @apiparam {String} number 编号   精确查询两个月以内的
		 * @apiparam {String} billNum 运单号(模糊查询最近一周的)
		 * @apiparam {String} name 姓名( 精确查询两个月以内的)
		 * @apiGroup 通知记录
		 * @apiSampleRequest /expGateway/findMassnotificationrecord
		 * @apiSuccess {String} result true
		 * @apiSuccess {String} createTime 发送时间
		 * @apiSuccess {String} id 主键id
		 * @apiSuccess {String} pathUrl 语音源文件路径（语音短信） 当为空的时候代表是文本短信
		 * @apiSuccess {String} receiverPhone 收件人手机号(或者是阿里大于的运单号)
		 * @apiSuccess {String} sendContent 短信内容
		 * @apiSuccess {String} sendResult 发送状态 0:已发送, 1:短信成功, 2:短信失败, 3:呼叫中, 4:呼叫成功, 5:呼叫失败, 6:微信成功, 7:微信失败
		 * @apiSuccess {String} sendStatus 状态值  0:发送成功, repeat:重复, reject:用户退订, intercept:拦截, -99:发送失败, 1:呼叫成功, 3:呼叫失败 ,4:微信成功, 5:微信失败
		 * @apiSuccess {Short} flag;  类型 -- 电话优先:2; 仅电话:3; 仅短信:1; 群呼+短信 :7;微信:8;大于短信:9
		 * @apiSuccess {String} msgId 发送的msgId
		 * @apiSuccess {String} memberPhone 发送人的手机号
		 * @apiSuccess {Short} replyStatus;  回复状态 0.未回复  1.未读 2.已读
		 * @apiSuccess {String} number; 编号
		 * @apiSuccess {Short} whatFlag; 0:失败, 1:成功
		 * @apiSuccess {String} waybill; 运单号
		 * @apiSuccess {String} netName; 网络名称
		 * @apiSuccess {String} netId; 网络Id
		 * @apiSuccess {String} parcelId; 包裹Id
		 * @apiError {String} updateTime ;更新时间
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 {
		    "data": {
		        "pageNo": 1, 
		        "pageSize": 20, 
		        "smsList": [
		            {}, 
		            {
		                "createTime": 1451544863573, 
		                "feeCount": 1, 
		                "flag": 3, 
		                "id": "5684d11f240a3e18c4ff9b8e", 
		                "memberId": 162781961986048, 
		                "memberPhone": "13521283934", 
		                "msgId": "188305631657984", 
		                "pathUrl": "", 
		                "receiverPhone": "13521283934", 
		                "replyResult": "{"result":"0","uniqueId":"be3ee65f8334a75c186875e92a81cd81","clid":"4006869009","description":"提交成功"}", 
		                "replyStatus": 0, 
		                "sendAccount": "5000116", 
		                "sendContent": "测试取快递短信3", 
		                "sendResult": 3, 
		                "sendStatus": "1", 
		                "updateTime": "",
		                "whatFlag": "0",
		                "waybill": "0",
		                "netName": "0",
		                "netId": "32423543546",
		                "parcelId": "32423543546"
		            }, 
		             
		            {
		                "createTime": 1451544499604, 
		                "feeCount": 1, 
		                "flag": 2, 
		                "id": "5684cfb3240a1d5686ab2309", 
		                "memberId": 162781961986048, 
		                "memberPhone": "13521283934", 
		                "msgId": "188304868294656", 
		                "pathUrl": "", 
		                "receiverPhone": "13521283934", 
		                "replyResult": "{\"result\":\"0\",\"uniqueId\":\"be3ee65f8334a75c186875e92a81cd81\",\"clid\":\"4006869009\",\"description\":\"提交成功\"}", 
		                "replyStatus": 0, 
		                "sendAccount": "5000116", 
		                "sendContent": "测试取快递短信3", 
		                "sendResult": 3, 
		                "sendStatus": "1", 
		                "updateTime": "",
		                "whatFlag": "0",
		                "waybill": "0",
		                "netName": "0",
		                "netId": "32423543546",
		                "parcelId": "32423543546"
		            }
		        ]
		    }, 
		    "success": true
      }
		 *   全部状态                status                 sendResult          sendstatus                 replaystatus
		 *   呼叫中                         1                       3       
		 *   已接听                         2                       4         and         1
		 *   已接收                          6                      1         and          0
		 *   发送失败                      7                      (2        and          0)or(sendstatus=-99)
		 *   未接听                          3                      5          or          3
		 *   呼叫失败                      4                      5          or          3
		 *   客服回复                      8                                                                        1
		 *   已发送                          5                      1          or          0
		 *   客服拒收                      9                                          reject
		 *   重复内容拦截               10                                         repeat
		 *  
		 *  
		 *  全部类型                         flag                   type
		 *   类型                                    0                      0
		 *   电话优先                             2                      2
		 *   仅电话                               3                       3
		 *   仅短信                                1                       1
		 *  
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   	
		 * @apiVersion 0.3.0
		 */
		@ResponseBody
		@RequestMapping(value = "/findMassnotificationrecord", method = { RequestMethod.POST,RequestMethod.GET })
		public String findMassnotificationrecord(Long memberId, String date,
				Integer pageSize, Integer pageNo, Short status, Short type,
				String receiverPhone,  String sendContent, String number, String billNum,String name){
			if (PubMethod.isEmpty(memberId)) {
				return paramsFailure("net.okdi.mob.controller.ExpGatewayController.findMassnotificationrecord.001","memberId不能为空");
			}
			return expGatewayService.findMassnotificationrecord(memberId, date,	pageSize, pageNo, status, type, receiverPhone, sendContent ,number, billNum,name);
		}
		/**
		 * @api {post} /expGateway/queryCountAndCost 通知记录-查询数量和费用
		 * @apiPermission user
		 * @apiDescription 通知记录-查询数量和费用
		 * @apiparam {String} queryDate  查询日期，如2016-09-08
		 * @apiparam {String} memberId  快递员id
		 * @apiGroup 通知记录
		 * @apiSampleRequest /expGateway/queryCountAndCost
		 * @apiSuccess {int} weChatCount 微信数量
		 * @apiSuccess {int} msgCount 短信数量
		 * @apiSuccess {int} phoneCount 群呼数量
		 * @apiSuccess {Double} weChatCost 微信共计费用
		 * @apiSuccess {Double} msgAndPhoneCost 短信+群呼共计费用
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
			{
			    "data":{
			        "weChatCount":12,
			        "msgCount":32,
			        "phoneCount":2,
			        "weChatCost":0,
			        "msgAndPhoneCost":0.12
			    },
			    "success":true
			}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   	
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/queryCountAndCost", method = { RequestMethod.POST })
		public String queryCountAndCost(String queryDate,String memberId){
			if (PubMethod.isEmpty(memberId)) {
				return paramsFailure("net.okdi.mob.controller.ExpGatewayController.queryCountAndCost.001","memberId不能为空");
			}
			if (PubMethod.isEmpty(queryDate)) {
				return paramsFailure("net.okdi.mob.controller.ExpGatewayController.queryCountAndCost.002","queryDate不能为空");
			}
			return expGatewayService.queryCountAndCost(queryDate, memberId);
		}
		
		/**
		 * @api {post} /expGateway/querySmsChat 短信聊天
		 * @apiPermission user
		 * @apiDescription  查询短信聊天 chunyang.tan
		 * @apiparam {String} msgId  短信id
		 * @apiparam {int} pageNo  页数
		 * @apiparam {int} pageSize  每页显示条数
		 * @apiGroup 短信聊天
		 * @apiSampleRequest /expGateway/querySmsChat
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 * 		{"data":[{\"id\":\"01\",\"msgId\":\"01\",\"mobile\":\"18200000001\",\"content\":\"请稍等\",\"createTime\":\"2015-9-23 13:12:12\",\"type\":\"1\",\"smsSource\":\"2\",\"status\":\"1\",\"firstMsgId\":\"01\",}],"success":true}
		 * 		mobile : 客户电话
		 * 		content : 客户回复的内容
		 * 		createTime : 客户回复的时间
		 * 		type : 消息类型{1.文本 2.语音 3:微信}
		 * 		smsSource : 消息来源{1.派送员 2.客户}
		 * 		status : 消息状态{0.未知 1.成功 2.失败 3.重复 4.退订}
		 * 		{0.未知 1.成功 2.失败 3.重复 4.退订 5.呼叫中 6.呼叫失败 7.呼叫失败转短信 8.拦截 9.微信成功, 10,微信失败}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   	
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping("/querySmsChat")
		public String querySmsChat(String msgId,int pageNo,int pageSize){
			if(PubMethod.isEmpty(msgId)){
				return paramsFailure("sms.ReCallController.querySmsChat.001","短信聊天，msgId参数非空异常");
			}
			return expGatewayService.querySmsChat(msgId,pageNo,pageSize);
		}
		
		/**
		 * @api {post} /expGateway/newQuerySmsChat 新版查询短信聊天 
		 * @apiPermission zj
		 * @apiDescription  新版查询短信聊天 
		 * @apiparam {String} msgId  短信会话id
		 * @apiparam {Long} memberId  快递员memberId
		 * @apiparam {Integer} pageNo  页数
		 * @apiparam {Integer} pageSize  每页显示条数
		 * @apiGroup 新版-短信
		 * @apiSampleRequest /expGateway/newQuerySmsChat
		 * @apiSuccess {String} mobile 电话
		 * @apiSuccess {String} content 内容
		 * @apiSuccess {String} createTime 创建时间
		 * @apiSuccess {String} type 消息类型{1.文本 2.语音 3:微信}
		 * @apiSuccess {String} smsSource 消息来源{1.派送员 2.客户}
		 * @apiSuccess {String} status {0.未知 1.成功 2.失败 3.重复 4.退订 5.呼叫中 6.呼叫失败 7.呼叫失败转短信 8.拦截 9.微信成功, 10,微信失败}
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 * 		{
			    "data": [
			        {
			            "id": "01",
			            "msgId": "01",
			            "mobile": "18200000001",
			            "content": "请稍等",
			            "createTime": "2015-9-23 13:12:12",
			            "type": "1",
			            "smsSource": "2",
			            "status": "1",
			            "firstMsgId": "01",
			            
			        },
			        {
			           "id": "01",
			           "msgId": "01",
			           "mobile": "18200000001",
			           "content": "请稍等",
			           "createTime": "2015-9-23 13:12:12",
			           "type": "1",
			           "smsSource": "2",
			           "status": "1",
			           "firstMsgId": "01",
			            
			       }
			    ],
			    "success": true
			}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   	
		 * @apiVersion 5.0.0
		 */
		@ResponseBody
		@RequestMapping(value="/newQuerySmsChat", method={ RequestMethod.POST, RequestMethod.GET})
		public String newQuerySmsChat(Long memberId, String msgId,Integer pageNo,Integer pageSize){
			if(PubMethod.isEmpty(msgId)){
				return paramsFailure("net.okdi.mob.controller.ExpGatewayController","短信聊天，msgId参数非空异常");
			}
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.mob.controller.ExpGatewayController","短信聊天，memberId参数非空异常");
			}
			if(PubMethod.isEmpty(pageNo)){
				pageNo=1;
			}
			if(PubMethod.isEmpty(pageSize)){
				pageSize=20;
			}
			return expGatewayService.newQuerySmsChat(memberId, msgId,pageNo,pageSize);
		}
		
		
		@ResponseBody
		@RequestMapping(value = "/queryInviteInfo", method = { RequestMethod.POST, RequestMethod.GET })
		public String queryInviteInfo(String phone, String firstMsgId){
			
			if(PubMethod.isEmpty(phone)){
				return paramsFailure("001","phone 不能为空!!!");
			}
			String result = expGatewayService.queryInviteInfo(phone,firstMsgId);
			return result;
		}
		
		
		
		@ResponseBody
		@RequestMapping(value = "/doExpSmsSend", method = { RequestMethod.POST })
		public String doExpSmsSend(String userPhone , String content , String extraCommonParam){
			if(PubMethod.isEmpty(userPhone) || PubMethod.isEmpty(content) ){
					return paramsFailure();
			}
			if(!VerifyUtil.isMobile(userPhone))
				 return paramsFailure();
			return expGatewayService.doExpSmsSend(userPhone, content, extraCommonParam);
		}
		/**
		 * <h2>在添加联系的时候判断电话是否重复,如果多个电话用逗号分隔</h2>
		 * @Method: ifExitJDW 
		 * @param memberId  创建人id
		 * @param contactPhone  验证电话
		 * @return {"data":{"isExist":true},"success":true} 
		 * 			(isExist为true表明电话一重复)
		 * @see net.okdi.api.service.ContactService#ifExitJDW(java.lang.Long, java.lang.Long) 
		*/
		@ResponseBody
		@RequestMapping(value = "/ifExitJDW", method = { RequestMethod.POST })
		public String ifExitJDW(Long memberId,String contactPhone){
			if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(contactPhone)){
						 return paramsFailure();
					}
			return expGatewayService.ifExitJDW(memberId, contactPhone);
		}
		
		/**
		 * <h2>反馈信息</h2>
		 * @Method: adviceInfo 
		 * @param compId  网点id
		 * @param memberId  创建人id
		 * @param channelNo  渠道号 01.手机客户端  02：快递站点  04：好递网 05：erp 06:客服
		 * @param content  反馈内容
		 * @param tel  电话
		 * @param adviceUser  反馈人姓名
		 * @param memo  备注
		 * @param loginId  注册号(人员电话)
		 * @param compName   公司名
		 * @return 
		*/
		@ResponseBody
		@RequestMapping(value = "/adviceInfo", method = { RequestMethod.POST })
		public String adviceInfo(Long compId,Long memberId,String channelNo,String content,String adviceUser
				,String tel,String memo,String loginId,String compName){
			if(PubMethod.isEmpty(compId) || PubMethod.isEmpty(memberId) || PubMethod.isEmpty(content)){
					return paramsFailure();
				}
			expGatewayService.adviceInfo(compId, memberId, channelNo, content, adviceUser, tel, memo, loginId, compName);	
			return jsonSuccess(null);
		}
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>手机端注册接口</dd> <dt><span
		 * class="strong">时间:</span></dt><dd>2014-11-12 下午3:18:41</dd>
		 * @param memberId
		 *            人员id
		 * @param memberName
		 *            人员姓名
		 * @param compId
		 *            网点id
		 * @param roleId 
		 *            角色id 0 收派员 -1 后勤 -2 站长
		 * @param applicationDesc
		 *            5张单号
		 * @param flag
		 *            1：第一步调用归属验证 2：第二部调用身份验证
		 * @param memberPhone
		 *            手机号 
		 * @param idNum
		 *            身份证 
		 * @param memberSourceFlag
		 * 	      在归属认证的时候进行调用 -1没有进行完善,0待审核
		 * @param addressName
		 * 			地址名称
		 * @param  realname
		 * 			真实姓名(可以为空)
		 * @param netId 
		 * 			网络ID
		 * @param compTypeNum 
		 * 			网点类型 1006站点 1050营业分部
		 * @param compName 
		 * 		          网点名称
		 * @param compTelephone 
		 * 			网点电话
		 * @param addressId 
		 * 			网点地址ID
		 * @param address 
		 * 			网点详细地址
		 * @param longitude 
		 * 			网点经度
		 * @param latitude 
		 * 			网点纬度
		 * 
		 * @param existAdd
		 * 		是否存在添加站点
		 * 
		 * @param stationPhone
		 * 		站长电话
		 * @return {"success":true}
	     * <dd>openapi.MemberInfoServiceImpl.mobileRegistration.001 -  手机端注册memberId不能为空</dd>
	     * <dd>openapi.MemberInfoServiceImpl.mobileRegistration.002 -  手机端注册compId不能为空</dd>
	     * <dd>openapi.MemberInfoServiceImpl.mobileRegistration.003 -  手机端注册roleId不能为空</dd>
	     * <dd>openapi.MemberInfoServiceImpl.mobileRegistration.004 -  手机端注册flag不能为空</dd>
	     * <dd>openapi.MemberInfoServiceImpl.mobileRegistration.005 -  手机端注册memberName不能为空</dd>
	     * <dd>openapi.MemberInfoServiceImpl.mobileRegistration.006 -  手机端注册applicationDesc不能为空</dd>
		 * @since v1.0
		 */
		@ResponseBody
		@RequestMapping(value = "/mobileRegistration", method = { RequestMethod.POST, RequestMethod.GET })
		public String mobileRegistration(Long memberId, String memberName,
				String memberPhone, String idNum, Long compId, Short roleId,
				String applicationDesc, Short flag,String addressName,String memberSourceFlag
				,Long netId,String compName , String compTelephone , Long addressId , String address
				 ,Double longitude , Double latitude,String compTypeNum, Short existAdd, String stationPhone) {
			if( PubMethod.isEmpty(memberId) || PubMethod.isEmpty(memberPhone)
										 || PubMethod.isEmpty(roleId)   || PubMethod.isEmpty(flag)){
				 return paramsFailure();
			}
			return expGatewayService.mobileRegistration(memberId, memberName, memberPhone, idNum, compId, roleId, applicationDesc, flag, addressName, memberSourceFlag, netId, compName, compTelephone, addressId, address, longitude, latitude, compTypeNum,existAdd, stationPhone);
		}
		
		/**
		 * @Method: getStationHostPhone
		 * @Description:  公司分类代码 1000:网络,1003:网络直营站点,1006:加盟公司,1008:加盟公司站点,1030:快递代理点,1050:营业分部
		 * @param Long compId 网点id
		 * @param Long comptypeId 1006站点   
		 * @return {"msg":"13343691801","success":true}
		 *
		 * <dt><span>作者:</span></dt><dd>xiangwei.liu</dd>
		 * @throws   
		 */
		@ResponseBody
		@RequestMapping(value = "/getStationHostPhone", method = { RequestMethod.POST, RequestMethod.GET })
		public String  getStationHostPhone(Long logMemberId, Long compId){
			if(PubMethod.isEmpty(compId)){
				 return paramsFailure();
			}
			return  expGatewayService.getStationHostPhone(logMemberId, compId);
			
		}
		
		/**
		 * @api {post} /expGateway/findParcelDetailByWaybillNumAndNetId 根据运单号与快递网络id查询包裹详情
		 * @apiPermission user
		 * @apiDescription 查询包裹详情	xianxian.chang
		 * @apiparam {String} wayBillNum 运单号
		 * @apiparam {Long} netId 快递网络id
		 * @apiGroup 包裹
		 * @apiSampleRequest /expGateway/findParcelDetailByWaybillNumAndNetId
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {
		 *	    "data": {
		 * 	        "actualCodAmount": 1,
		 *	        "actualSendMember": 3435096,
		 *	        "actualTakeMember": 3373043,
		 *	        "chareWeightForsender": 1,
		 *	        "chareWeightFortransit": 1,
		 *	        "codAmount": 1,
		 *	        "codIsRecovery": 1,
		 *	        "compId": 5981,
		 *	        "compName": "广西北海公司",
		 *	        "createTime": 1418805887000,
		 *	        "createUserId": 3435096,
		 *	        "expWaybillNum": "sadf001",
		 * 	        "freight": 1,
		 *	        "freightPaymentMethod": 1,
		 *	        "freightPaymentStatus": 1,
		 *	        "freightPaymentTime": 1419065060000,
		 *	        "goodsNum": 1,
		 *	        "goodsPaymentMethod": 1,
		 *	        "goodsPaymentStatus": 1,
		 *	        "id": 10001,
		 *	        "insureAmount": 1,
		 *	        "netId": 999,
		 *	        "netName": "EMS速递",
		 *	        "noFly": 1,
		 *	        "ordOfSellerId": "",
		 *	        "packingCharges": 1,
		 *	        "parcelEndMark": "1",
		 *	        "parcelRemark": "1",
		 *	        "parcelType": "1",
		 *	        "parcelVolume": 1,
		 *	        "paymentMethod": 1,
		 *       	"pricePremium": 1,
		 *        	"printFlag": 1,
		 *	        "receiptId": 1,
		 *	        "sendTaskId": 117095508287488,
		 *	        "senderCompId": 5980,
		 *	        "senderType": 1,
		 *	        "senderUserId": 3435096,
		 *	        "serviceId": 2005,
		 *	        "serviceName": "当日达",
		 *	        "signGoodsTotal": 1,
		 *	        "signImgUrl": "1",
		 *	        "signMember": "1",
		 *	        "signResult": 1,
		 *	        "signTime": 1418114672000,
		 *	        "tackingStatus": 1,
		 *	        "takeTaskId": 117095539744768,
		 *	        "tipAmount": 1,
		 *	        "totalGoodsAmount": 1
		 *  	    },
		 *	    "success": true
		 *	}
		 *
		 *		参数解释
		 *	 		"actualCodAmount"    代收货款实际收到的货款金额
		 *	        "actualSendMember"     实际派件人ID
		 *	        "actualTakeMember"     实际取件人ID
		 *	        "addresseeAddress"	收件详细地址
		 *	        "addresseeAddressId      收件乡镇ID
		 *	        "addresseeCasUserId"  	  收件人CASID
		 *	        "addresseeContactId"   	 收件联系人ID
		 *	        "addresseeCustomerId"	   收件客户ID
		 *	        "addresseeLatitude"	   收件人纬度
		 *	        "addresseeLongitude"      收件人经度
		 *	        "addresseeMobile"     收件人手机
		 *	        "addresseeName"     收件人姓名
		 *	        "addresseePhone"       收件人电话
		 *	        "addresseeZipcode"       收件地址邮编
		 *	        "agencySiteId"     包裹代收点ID
		 *	        "chareWeightForsender"       计费重量（销售）
		 *	        "chareWeightFortransit"       计费重量（采购）
		 *	        "codAmount"       代收货款金额
		 *	        "codIsRecovery"       代收货款是否收回 0：代收货款未收回 1：代收货款已收回
		 *	        "compId"      所属站点(所属公司)
		 *	        "compName"       公司名称（站点名称）
		 *	        "createTime"       包裹录入时间
		 *	        "createUserId"       包裹创建人
		 *	        "expWaybillNum"      包裹运单号
		 * 	        "freight"      应收运费
		 *	        "freightPaymentMethod"     应收运费支付方式 0：发件方现结,1：发件方月节,2：收件方到付  
		 *	        "freightPaymentStatus"       费用结算状态 1：yes 运费已收 0:no 运费未收
		 *	        "freightPaymentTime"       /费用支付时间 费用支付时间；取件时以收派员完成取件时间，派件时以派件签收时间；
		 *	        "goodsDesc"     产品描述 
		 *	        "goodsNum"       产品个数
		 *	        "goodsPaymentMethod"    货款支付方式 0:不代收付货款,1:上门代收付(COD)   
		 *	        "goodsPaymentStatus"       货款与发件人结算状态  1：yes 货款已结给发件人 0:no 货款未付给发件人
		 *	        "id"      包裹id或包裹地址信息id
		 *	        "insureAmount"       保价金额
		 *	        "netId"     快递网络id 
		 *	        "netName"      快递网络名称
		 *	        "noFly"      禁航件 1：yes 禁航 0:no 非禁航
		 *	        "ordOfSellerId"    发货商订单号
		 *	        "packingCharges"       包装费
		 * 	        "parcelEndMark"    包裹结束标志 0：未结束 1：结束
		 * 	        "parcelRemark"      包裹备注
		 * 	        "parcelType"       包裹类型，1：包裹，2：文件
		 * 	        "parcelVolume"       包裹初始体积
		 *  	        "paymentMethod"       费用付款方式 0：现金,1：POS机
		 *	        "pricePremium"       保价费
		 * 	        "printFlag"      打印标记 0：未打印，1：已打印
		 *	        "receiptId"       付款收据ID
		 *	        "sendAddress"    发件详细地址
		 *	        "sendAddressId"    发件乡镇ID  
		 *	        "sendCasUserId"     发件人CASID  
		 *	        "sendContactId"       发件联系人ID
		 *	        "sendCustomerId"       发件客户ID
	 	 *	        "sendLatitude"       发件人纬度
	 	 *	        "sendLongitude"    发件人经度   
	 	 *	        "sendMobile"      发件人手机 
	 	 *	        "sendName"      发件人姓名
		 *	        "sendPhone"       发件人电话
		 *	        "sendTaskId"       派件任务ID
		 *	        "sendZipcode"       发件地址邮编
		 *	        "senderCompId"     发货方商家ID  
		 *	        "senderType"      发件人类型 1:发货商家,2:好递个人
		 *	        "senderUserId"      
		 *	        "serviceId"      快递公司服务产品ID 快递产品ID，结算用
		 *	        "serviceName"       服务产品名称
		 *	        "signGoodsTotal"       签收金额
		 * 	        "signImgUrl"       上传的图片路径
		 * 	        "signMember"       签收人
		 *	        "signResult"      签收结果 0：未签收/拒签 1：签收
		 *	        "signTime"     签收时间  
		 *	        "tackingStatus"       包裹当前状态 0:在途,未签收 1:已签收
		 *	        "takeTaskId"       取件任务ID
		 *	        "tipAmount"       抢单发快递的小费
		 *	        "totalGoodsAmount"      商品货款合计
		 *	        "parcelStatus"     包裹状态 0：待取件;1：已取件;10：待派件;11：已派件'
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"openapi.ExpGatewayController.findParcelDetailByWaybillNumAndNetId.001","message":"netId能为空","success":false}
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/findParcelDetailByWaybillNumAndNetId", method = { RequestMethod.POST })
		public String findParcelDetailByWaybillNumAndNetId(String wayBillNum, Long netId) {
			if(PubMethod.isEmpty(wayBillNum) || PubMethod.isEmpty(netId)){
				 return paramsFailure();
			}
			String resultStr  = expGatewayService.findParcelDetailByWaybillNumAndNetId(wayBillNum,netId);
			return resultStr;
		}
		
		
		/**
		 * 保存包裹信息
		 * <dt><span class="strong">方法描述:</span></dt><dd>方法描述具体信息</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xianxian.chang</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-5 下午3:29:33</dd>
		 * @param id 包裹id
		 * @param takeTaskId 取件任务id
		 * @param sendTaskId 派件任务id
		 * @param senderUserId 发货方客户ID 
		 * @param sendCustomerId 发件客户ID (与senderUserId是同一值，前者存在于包裹表里，后者存在于包裹地址信息表里)
		 * @param addresseeCustomerId  收件客户ID
		 * @param sendCasUserId  发件人CASID
		 * @param addresseeCasUserId 收件人CASID
		 * @param expWaybillNum 运单号
		 * @param compId 公司id
		 * @param netId  网络id
		 * @param addresseeName 收件人姓名
		 * @param addresseeMobile 收件人手机号码
		 * @param addresseeAddressId 收件人乡镇id
		 * @param addresseeAddress 收件人详细地址
		 * @param sendName 发件人姓名
		 * @param sendMobile 发件人手机
		 * @param sendAddressId 发件人乡镇id
		 * @param sendAddress 发件人详细地址
		 * @param chareWeightForsender 包裹重量 
		 * @param freight 包裹应收运费
		 * @param goodsPaymentMethod 支付方式
		 * @param codAmount 代收货款金额
		 * @param insureAmount 保价金额
		 * @param pricePremium 保价费
		 * @param packingCharges 包装费
		 * @param freightPaymentMethod 运费支付方式
		 * @param sendLongitude 发件人地址经度
		 * @param sendLatitude 发件人地址纬度
		 * @param addresseeLongitude 收件人地址经度
		 * @param addresseeLatitude 收件人地址纬度
		 * @param goodsDesc 产品描述
		 * @param parcelRemark 包裹备注
		 * @param serviceId 服务产品ID
		 * @param signMember 签收人
		 * @param createUserId 创建人id
		 * @param actualTakeMember 实际收件人id
		 * @param actualSendMember 实际派件人id
		 * @param receiptId 收据id	
		 * @return
		 * @since v1.0
		 */
		
		/**
		 * @api {post} /expGateway/saveParcelInfo 保存包裹信息
		 * @apiPermission user
		 * @apiDescription 保存包裹信息	 xianxian.chang
		 * @apiparam {short} ParceTypeFlag 包裹类型标识 1为取件,支付方式在前台显示,以不用判断，2为提货,提供的方式不全,如果有钱就说明要给收件人要支付方式就是未收
		 * @apiparam {String} parmJSON 取件时候传的参数 {
		 *"data": [
		 *    {
		 *        "id": "",
		 *        "expWaybillNum": "135212839342",
  		 *        "compId": 174542305935360,
   	 	 *        "netId": 999,
    	 *        "createUserId": 174542599536640,
    	 *        "addresseeName": "谷雨",
    	 *        "addresseeMobile": "13521283695",
    	 *        "addresseeAddressId": 11000201,
    	 *        "addresseeAddress": "北京东城区 ",
    	 *        "addresseeLongitude": 0,
    	 *        "addresseeLatitude": 0,
    	 *        "sendName": "谷雨",
    	 *        "sendMobile": "13521285865",
    	 *        "sendAddressId": 300204920,
    	 *        "sendAddress": "北京市海淀区 花园北路",
    	 *        "sendLongitude": 0,
    	 *        "sendLatitude": 0,
    	 *        "chareWeightForsender": "1",
    	 *        "freight": 20,
    	 *        "codAmount": 0,
    	 *        "insureAmount": 0,
    	 *        "pricePremium": 0,
    	 *        "packingCharges": 0,
    	 *        "freightPaymentMethod": "0",
    	 *        "goodsDesc": "",
    	 *        "parcelRemark": "",
    	 *        "serviceId": "",
    	 *        "serviceName": "",
    	 *        "parcelEndMark": 0,
    	 *        "parcelStatus": 1,
    	 *        "takeTaskId": "",
    	 *        "actualTakeMember": 174542599536640,
    	 *        "sendCustomerId": "175082027139072"
    	 *    }
    	 *]
    	 *}
    	 *其他时候传的参数
    	 *{
    	 *"data": [
     	 *   {
      	 *      "id": 175083862147072,
      	 *      "expWaybillNum": "135212839342",
      	 *      "compId": 174542305935360,
      	 *      "netId": 999,
      	 *      "createUserId": 174542599536640,
      	 *      "addresseeName": "谷雨",
      	 *      "addresseeMobile": "13521283695",
      	 *      "addresseeAddressId": 11000201,
      	 *      "addresseeAddress": "北京东城区 门牌号",
      	 *      "addresseeLongitude": 0,
      	 *      "addresseeLatitude": 0,
      	 *      "freight": 12,
      	 *      "freightPaymentMethod": "2",
      	 *      "codAmount": 13,
      	 *      "parcelEndMark": 0,
      	 *      "parcelStatus": 10,
      	 *      "actualSendMember": "174542301741056"
      	 *  }
      	 *]
    	 *}
		 * @apiparam {Long} actualAcount 运费
		 * @apiparam {Long} memberId 用户id
		 * @apiGroup 包裹
		 * @apiSampleRequest /expGateway/saveParcelInfo
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"data":{"id":172865855406080},"success":true}
		 *   
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"openapi.ExpGatewayController.saveParcelInfo.001","message":"ParceTypeFlag能为空","success":false}
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/saveParcelInfo", method = { RequestMethod.POST })					      //actualAcount 运费
		public String saveParcelInfo(HttpServletResponse response ,short ParceTypeFlag,String parmJSON,Long actualAcount,
				Long memberId){
			if(PubMethod.isEmpty(ParceTypeFlag) || PubMethod.isEmpty(parmJSON)
			  || PubMethod.isEmpty(parmJSON)	){
				 return paramsFailure();
			}
			return expGatewayService.saveParcelInfo(response, ParceTypeFlag, parmJSON, actualAcount, memberId);
		}
		
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>通过包裹id删除对应包裹内容信息与包裹地址信息</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-8 下午7:29:17</dd>
		 * @param id 包裹id
		 * @param  expWayBillNum 运单号
		 * @param  netId 网络Id
		 * @return {"success": true/false} true 操作成功 false 操作异常
		 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.deleteParcelInfoByParcelId.001 -参数id不能为空！</dd>
		 *<dd>openapi.ParcelInfoController.deleteParcelInfoByParcelId.002 -参数expWayBillNum不能为空！</dd>
		 *<dd>openapi.ParcelInfoController.deleteParcelInfoByParcelId.003 -参数netId不能为空！</dd>
		 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.001 -包裹id不能为空！</dd>
		 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.002 -包裹expWayBillNum不能为空！</dd>
		 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.003 -包裹netId不能为空！</dd>
		 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.004 -通过id查询包裹内容信息异常！</dd>
		 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.005 -参数不匹配！</dd>
		 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.006 -删除包裹内容信息异常！</dd>
		 *<dd>openapi.ParcelInfoServiceImpl.deleteParcelInfoByParcelId.007 -删除包裹地址信息异常！</dd>
		 * @since v1.0
		 */
		@ResponseBody
		@RequestMapping(value = "/deleteParcelInfoByParcelId", method = { RequestMethod.POST })
		public String deleteParcelInfoByParcelId(Long id,String expWayBillNum,Long netId){
			if(PubMethod.isEmpty(id) || PubMethod.isEmpty(expWayBillNum)
					  || PubMethod.isEmpty(netId)	){
						 return paramsFailure();
					}
			return expGatewayService.deleteParcelInfoByParcelId(id, expWayBillNum, netId);
		}
		
		
		/**
		 * api {post} /expGateway/querySendTaskList 派件任务列表
		 * apiPermission user
		 * apiDescription 派件任务列表	 chuanshi.chai
		 * apiparam {Long} memberId 派送员Id
		 * apiGroup 派件
		 * apiSampleRequest /expGateway/querySendTaskList
		 * apiSuccess {String} result true
		 * apiError {String} result false
		 * apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *     {
		 *	    "data": {
		 *        	"resultlist": [
		 *	            {
		 *	                "codAmount": 9,
		 *	                "contactAddress": "",
		 *	                "contactMobile": "13865555656",
		 *	                "contactName": "",
		 *	                "createTime": 1421300135000,
		 *	                "expWayBillNum": "655566555",
		 *	                "freight": 9,
		 *	                "id": "",
		 *	                "parcelId": 124877837484032,
		 *	                "taskFlag": 0,
		 *	                "taskId": 124877839581184,
		 *	                "taskType": 1
		 *	            },
		 *	            {
		 * 	                "codAmount": 0,
		 *	                "contactAddress": "江苏徐州市贾汪区大吴镇 哦拒绝摸到咯嗯家乐福困难桶噢噢噢哦哦摸摸弄摸摸摸噢噢噢哦哦",
		 *	                "contactAddressId": 3108726,
		 *	                "contactMobile": "13565585555",
		 *	                "contactName": "",
		 *	                "createTime": 1421233017000,
		 *	                "expWayBillNum": "55525525633",
		 *	                "freight": 0,
		 *	                "id": "",
		 *	                "parcelId": 124737082302464,
		 *	                "taskFlag": 0,
		 *	                "taskId": 124737084399616,
		 *	                "taskType": 1
		 *	            },
		 *	            {
		 *	                "codAmount": 0,
		 *	                "contactAddress": "江苏淮安市金湖县夹沟镇",
		 *	                "contactAddressId": 1286748,
		 *	                "contactMobile": "13562865655",
		 *	                "contactName": "",
		 *	                "createTime": 1420008112000,
		 *	                "expWayBillNum": "935356556",
		 *	                "freight": 0,
		 *	                "id": "",
		 *	                "parcelId": 122142678786048,
		 *	                "taskFlag": 0,
		 *	                "taskId": 122168270962688,
		 *	                "taskType": 1
		 *	            }
		 *	        ]
		 *	    },
		 *	    "success": true
		 *	}
	     * apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"openapi.ExpGatewayController.querySendTaskList.001","message":"memberId能为空","success":false}
		 * apiVersion 0.2.0
		 */
		@RequestMapping("/querySendTaskList")
	    @ResponseBody
		public String querySendTaskList(Long memberId) {
			try {
				if(PubMethod.isEmpty(memberId)	){
				    return paramsFailure();
			    }
				return jsonSuccess(expGatewayService.querySendTaskList(memberId));	
			} catch (RuntimeException e) {
				return jsonFailure(e);
			}
		}
		
		/**
		 * @api {post} /expGateway/ifHasPickUp 判断该包裹是否已经提货  
		 * @apiPermission user
		 * @apiDescription 判断该包裹是否已经提货 mengnan.zhang
		 * @apiparam {String} expWayBillNum 运单号
		 * @apiparam {Long} netId 网络Id
		 * @apiGroup 	包裹
		 * @apiSampleRequest /expGateway/ifHasPickUp
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"data":{"hasPickUp" //是否已经提货:true 已经被提货},"success":true}
	     *
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"openapi.SendTeskController.ifHasPickUp.001","message":"netId参数异常","success":false}
		 * @apiVersion 0.2.0
		 */
		@RequestMapping("/ifHasPickUp")
	    @ResponseBody
		public String ifHasPickUp(String expWayBillNum,Long netId) {
			try {
				if(PubMethod.isEmpty(expWayBillNum)	|| PubMethod.isEmpty(netId)	){
				    return paramsFailure();
			    }
				String result = expGatewayService.ifHasPickUp(expWayBillNum,netId);	
				return result;
			} catch (RuntimeException e) {
				return jsonFailure(e);
			}
		}
		/**
		 * @api {post} /expGateway/changSendPerson 批量转单(分派包裹)
		 * @apiPermission user
		 * @apiDescription 批量转单(分派包裹) 
		 * @apiparam {String} parcelIds 包裹Ids(用逗号分隔)
		 * @apiparam {Long} memberId 接受转发的收派员Id
		 * @apiparam {Long} oldMemberId 转发收派员Id
		 * @apiparam {String} memberPhone 接受转发的收派员电话
		 * @apiGroup 提货
		 * @apiSampleRequest /expGateway/changSendPerson
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *     {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 * @apiVersion 0.2.0
		 */
		@RequestMapping("/changSendPerson")
	    @ResponseBody
		public String changSendPerson(String parcelIds, Long memberId,Long oldMemberId,String memberPhone) {
			if(PubMethod.isEmpty(parcelIds)	|| PubMethod.isEmpty(oldMemberId) || PubMethod.isEmpty(memberPhone)){
			    return paramsFailure();
		    }
			try {
				String result = expGatewayService.changSendPerson(parcelIds, memberId,oldMemberId,memberPhone);	
			    return result;
			} catch (RuntimeException e) {
	           return jsonFailure(e);
			}
		}
		
    /**
     * <dt><span class="strong">方法描述:</span></dt><dd>批量提货</dd>
     * @param parcelId  '包裹Id',
     * @param memberId  '收派员Id'
     * @param memberPhone '收派员电话'
     * @return
     * @since v1.0
     */
		/**
		 * @api {post} /expGateway/createSendTaskBatch 批量提货
		 * @apiPermission user
		 * @apiDescription 批量提货
		 * @apiparam {String} parcelId 包裹Id
		 * @apiparam {Long} memberId 收派员Id
		 * @apiparam {String} memberPhone 收派员电话
		 * @apiparam {Long} sendTaskId 发送任务标识，可以不传
		 * @apiGroup 提货
		 * @apiSampleRequest /expGateway/createSendTaskBatch
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *     {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 * @apiVersion 0.2.0
		 */
		@RequestMapping("/createSendTaskBatch")
	    @ResponseBody
		public String createSendTaskBatch( String parcelId,Long memberId,String memberPhone,Long sendTaskId) {
			if(PubMethod.isEmpty(parcelId)){
				return paramsFailure("SendTaskController.createSendTask.parcelId","包裹ID不能为空");
			}
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("SendTaskController.createSendTask.parcelId","memberId不能为空");
			}
			if(PubMethod.isEmpty(memberPhone)){
				return paramsFailure("SendTaskController.createSendTask.parcelId","电话不能为空");
			}
			try {
				 return expGatewayService.createSendTaskBat(parcelId,memberId, memberPhone,sendTaskId);
			} catch (RuntimeException e) {
			   return jsonFailure(e);
			}
		}
		
		
		/**
		 * @api {post} /expGateway/queryParcelListBySendMemberId 根据登录人ID查询 未提包裹列表
		 * @apiPermission user
		 * @apiDescription 根据登录人ID查询 未提包裹列表 
	 	 * @apiparam {Long} memberId 收派员ID
	 	 * @apiparam {Integer} currentPage 页码
	 	 * @apiparam {Integer} pageSize 每页显示条数
	 	 * @apiGroup 提货
	 	 * @apiSampleRequest /expGateway/queryParcelListBySendMemberId
	 	 * @apiSuccess {int} count 总条数 
	 	 * @apiSuccess {String} addresseeAddress 收件人地址
	 	 * @apiSuccess {Long} addresseeAddressId 收件人地址id
	 	 * @apiSuccess {String} addresseeAddressmsg 收件人乡镇地址
	 	 * @apiSuccess {String} addresseeMobile 收件人手机号码
	 	 * @apiSuccess {String} addresseeName 收件人姓名
	 	 * @apiSuccess {String} addresseePhone 收件人电话
	 	 * @apiSuccess {Long} createTime 创建时间 
	 	 * @apiSuccess {BigDecimal} freight 应收运费 
	 	 * @apiSuccess {Long} parcelId 包裹id 
	 	 * @apiSuccess {Long} sendCustomerId 发送客户id
	 	 * @apiSuccess {BigDecimal} totalGoodsAmount 应收货款
	 	 * @apiSuccess {Long} waybillNum 运单号
	 	 * @apiError {String} result false
	 	 * @apiSuccessExample {json} Success-Response:
	 	 *     HTTP/1.1 200 OK
	 	 *{
	 	 *"count": 2,
	 	 *"data": [
	 	 *    {
	 	 *        "addresseeAddress": "北京市海淀区",
	 	 *        "addresseeAddressId": 300204920,
	 	 **        "addresseeAddressmsg": "花园北路",
	 	 *        "addresseeMobile": "13688882222",
	 	 *        "addresseeName": "",
	 	 *        "addresseePhone": "",
	 	 *        "createTime": 1431670472000,
	 	 *        "freight": "",
	 	 *        "parcelId": 146626012930048,
	 	 *        "sendCustomerId": "",
	 	 *        "totalGoodsAmount": "",
	 	 *        "waybillNum": "757575757575"
	 	 *    },
	 	 *    {
	 	 *        "addresseeAddress": "北京市海淀区",
	 	 *        "addresseeAddressId": 300204920,
	 	 *        "addresseeAddressmsg": "花园北路",
    	 *        "addresseeMobile": "13161449048",
    	 *        "addresseeName": "",
    	 *        "addresseePhone": "",
    	 *        "createTime": 1430996999000,
    	 *        "freight": 0,
    	 *        "parcelId": 145213638778880,
     	 *        "sendCustomerId": "",
    	 *        "totalGoodsAmount": 0,
    	 *        "waybillNum": "5511337799"
    	 *    }
    	 *],
    	 *"success": true
    	 *}
	 	 * @apiErrorExample {json} Error-Response:
	 	 *     HTTP/1.1 200 OK
	 	 *   {"message":"Parameter is not correct or Parameter format error","success":false}
	 	 * @apiVersion 0.2.0
	 	 */
		@ResponseBody
		@RequestMapping(value = "/queryParcelListBySendMemberId", method = { RequestMethod.POST, RequestMethod.GET })
		public String queryParcelListBySendMemberId(Long memberId,Integer currentPage,Integer pageSize){
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("queryParcelListBySendMemberId","memberId不能为空");
			}
			if(PubMethod.isEmpty(currentPage)){
				return paramsFailure("openapi.ParcelInfoController.queryParcelListBySendMemberId.002", "currentPage不能为空!");
			}
			if(PubMethod.isEmpty(pageSize)){
				return paramsFailure("openapi.ParcelInfoController.queryParcelListBySendMemberId.003", "pageSize不能为空!");
			}
			String result =  expGatewayService.queryParcelListBySendMemberId(memberId, currentPage, pageSize);
			return result;
		}
		
		/**
		 * @api {post} /expGateway/settleAccounts 包裹收款/结算
		 * @apiPermission user
		 * @apiDescription 包裹收款/结算
		 * @apiparam {Long} parcelIds 包裹ID
		 * @apiparam {String} totalCodAmount 代收货款金额
		 * @apiparam {Long} totalFreight 实收运费
		 * @apiparam {String} memberId 创建人 
		 * @apiparam {Long} type 类型 0取件 1派件
		 * @apiGroup 派件
		 * @apiSampleRequest /expGateway/settleAccounts
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"openapi.ExpressPriceServiceImpl.settleAccounts.001", "包裹收款结算异常,parcelIds参数非空异常","success":false}
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/settleAccounts", method = { RequestMethod.POST, RequestMethod.GET })
		public String  settleAccounts(String parcelIds,Double totalCodAmount,Double totalFreight,Long memberId,short type){
			if(PubMethod.isEmpty(parcelIds) || PubMethod.isEmpty(memberId) || PubMethod.isEmpty(type)){
				return paramsFailure("settleAccounts","参数不能为空");
			}	
			return expGatewayService.settleAccounts(parcelIds, totalCodAmount, totalFreight, memberId, type);
		}
		
		/**
		 * 派件调用结算,签收,完成
		 * <dt><span class="strong">方法描述:</span></dt><dd>派件任务完成(已调用(包裹收款/结算)接口)</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-11 下午01:19:23</dd>
		 * @param taskId 任务ID
		 * @param parcelIds 包裹Id串
		 * @param totalCodAmount 代收货款金额
		 * @param totalFreight 实收运费
		 * @param type   类型 0取件 1派件
		 * @param memberId 创建人
		 * @return
		 * @since v1.0
		 */
		/**
		 * @api {post} /expGateway/sendFinishTask 签收(派件详情页) 正常签收
		 * @apiPermission user
		 * @apiDescription 签收（派件详情页） 正常签收
		 * @apiparam {Long} taskId 任务ID
		 * @apiparam {String} parcelIds 包裹Id串
		 * @apiparam {Double} totalCodAmount 代收货款金额
		 * @apiparam {Double} totalFreight 实收运费
		 * @apiparam {Short} type 类型 0取件 1派件
		 * @apiparam {Long} memberId 创建人
		 * @apiGroup 派件
		 * @apiSampleRequest /expGateway/sendFinishTask
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/sendFinishTask", method = { RequestMethod.POST, RequestMethod.GET })
		public String sendFinishTask(Long taskId,String parcelIds,Double totalCodAmount,Double totalFreight,Short type,Long memberId) {
			try {
				if(PubMethod.isEmpty(taskId) || PubMethod.isEmpty(parcelIds) || PubMethod.isEmpty(memberId)){
					return paramsFailure("sendFinishTask","参数不能为空");
				}	
				String result = expGatewayService.finishTask(taskId, parcelIds, totalCodAmount, totalFreight, type, memberId);	
				return result;
			} catch (RuntimeException e) {
				return jsonFailure(e);
			}
		}
		
		
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>查询取件任务(只查询该收派员一天的取件任务)</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
		 * @param actualTakeMember 实际取件人Id
		 * @return
		 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
		 * @since v1.0
		 */
		@ResponseBody
		@RequestMapping(value ="/queryTakeTaskList", method = { RequestMethod.POST })
		public String  queryTakeTaskList(Long actualTakeMember){
			if(PubMethod.isEmpty(actualTakeMember)){
				return paramsFailure("queryTakeTaskList","memberId不能为空");
			}	
			return	expGatewayService.queryTakeTaskList(actualTakeMember);
		}
		
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>查询一个单号的包裹</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-10 下午6:04:47</dd>
		 * @param actualTakeMember 实际取件人Id
		 * @param receiptId 账单Id
		 * @return
		 *<dt><span class="strong">异常:</span></dt> <dd>openapi.ParcelInfoController.queryParcelListBySendMemberId.001 -派件人memberId不能为空！</dd>
		 * @since v1.0
		 */
		@ResponseBody
		@RequestMapping(value ="/queryTakeByWaybillNum", method = { RequestMethod.POST })
		public String  queryTakeByWaybillNum(Long actualTakeMember,Long receiptId){
			if(PubMethod.isEmpty(actualTakeMember) || PubMethod.isEmpty(receiptId)){
				return paramsFailure("queryTakeTaskList","参数不能为空");
			}	
			return	JSON.toJSONString(expGatewayService.queryTakeByWaybillNum(actualTakeMember, receiptId));
		}
		
		/**
		 * @api {post} /expGateway/modyfyTaskInfo 更改收件人	
		 * @apiPermission user
		 * @apiDescription 更改收件人		 xiangwei.liu
		 * @apiparam {Long} memberId 收派员Id
		 * @apiparam {String} parceId 包裹Id
		 * @apiparam {Long} TaskId 任务Id
		 * @apiparam {String} addressId 收件人地址Id
		 * @apiparam {Long} takePersonName 收件人姓名
		 * @apiparam {String} takePersonMoble 收件人电话
		 * @apiparam {String} takePersonAddress 收件人地址
		 * @apiGroup 派件
		 * @apiSampleRequest /expGateway/modyfyTaskInfo
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"openapi.ExpGatewayController.modyfyTaskInfo.001 -派件人memberId不能为空","success":false}
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value ="/modyfyTaskInfo", method = { RequestMethod.POST })
		 public String modyfyTaskInfo(Long memberId, Long parceId,Long TaskId,Long addressId, String takePersonName
				 					  , String takePersonMoble,String takePersonAddress) {
			if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(parceId) ||  PubMethod.isEmpty(TaskId)){
				return paramsFailure("queryTakeTaskList","参数不能为空");
			}	
			 return JSON.toJSONString(expGatewayService.modyfyTaskInfo(memberId, parceId,TaskId,addressId,takePersonName,takePersonMoble,takePersonAddress));
		 }
		
		
		/**
		 * <dt><span class="strong">方法描述:</span></dt><dd>计算包裹运费</dd>
		 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
		 * <dt><span class="strong">时间:</span></dt><dd>2014-12-9 上午11:02:59</dd>
		 * @param netId 网络ID
		 * @param sendAddressId 发件地址ID
		 * @param receiveAddressId 收件地址ID
		 * @param weight 重量
		 * @return {"data":{"price":"0.00"},"success":true}
		 * <dt><span class="strong">异常:</span></dt>
	     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.001", "计算运费异常,sendAddressId参数非空异常"</dd>
	     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.002", "计算运费异常,netId参数非空异常"</dd>
	     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.003", "计算运费异常,receiveAddressId参数非空异常"</dd>
	     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.004", "计算运费异常,weight参数异常"</dd>
	     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.005", "计算运费异常,获取发件地址异常"</dd>
	     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.006", "计算运费异常,获取收件地址异常"</dd>
	     * <dd>"openapi.ExpressPriceServiceImpl.getExpressPrice.007", "计算运费异常,获取网络信息异常"</dd>
		 * @since v1.0
		 */
		@ResponseBody
		@RequestMapping(value = "/getExpressPrice", method = { RequestMethod.POST, RequestMethod.GET })
		public String  getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight){
			if(PubMethod.isEmpty(netId) || PubMethod.isEmpty(weight) ){
				return paramsFailure("queryTakeTaskList","参数不能为空");
			}	
			return expGatewayService.getExpressPrice(netId, sendAddressId, receiveAddressId, weight);
		}
		
		/**
		 * @api {post} /expGateway/cancelSendTask 未正常签收
		 * @apiPermission user
		 * @apiDescription 未正常签收
		 * @apiparam {Long} taskId 任务ID
		 * @apiparam {Long} parcelId 包裹Id
		 * @apiparam {Long} memberId 收派员Id
		 * @apiparam {String} cancelType 取消类型 
		 * 		  		 1, "客户取消发件"
		 *			 	 2, "多次联系不上客户，上门无人"
		 *				 3, "其他原因"
		 *				 10, "超出本网点范围"
		 *				 11, "网点任务太多，忙不过来"
		 *				 12, "超出本人收派范围"
		 *				 13, "本人任务太多，忙不过来"
		 *
		 * @apiparam {Long}  compId 站点Id
		 * @apiparam {String}  textValue 其他原因(手动输入,输入内容,cancelType 传3)
		 * @apiGroup 派件
		 * @apiSampleRequest /expGateway/cancelSendTask
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"openapi.ExpGatewayController.cancelSendTask.001 -参数不能为空","success":false}
		 * @apiVersion 0.2.0
		 */
		@RequestMapping("/cancelSendTask")
	    @ResponseBody
		public String cancelSendTask(Long taskId, Long memberId, Long parcelId , Long Id ,String cancelType,Long compId,String textValue){
			if(PubMethod.isEmpty(taskId) || PubMethod.isEmpty(parcelId) ){
				return paramsFailure("queryTakeTaskList","参数不能为空");
			}	
			return expGatewayService.cancelSendTask(taskId, memberId, parcelId, Id, cancelType, compId ,textValue);
		}
		
		 /**
		  * <dt><span class="strong">方法描述:</span></dt><dd>保存收派员经纬度信息</dd>
		  * <dt><span class="strong">作者:</span></dt><dd>mengnan.zhang</dd>
		  * <dt><span class="strong">时间:</span></dt><dd>2014-11-14 上午10:07:27</dd>
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
		/**
		 * @api {post} /expGateway/saveOnLineMember 保存收派员经纬度信息
		 * @apiPermission user
		 * @apiDescription 保存收派员经纬度信息	
		 * @apiparam {Long} id 主键id
		 * @apiparam {Long} netId 网络ID
		 * @apiparam {String} netName 网络名称
		 * @apiparam {Long} compId 公司ID
		 * @apiparam {String} compName 公司名称
		 * @apiparam {Long} memberId 收派员ID
		 * @apiparam {String} memberName 收派员姓名
		 * @apiparam {String} memberMobile 收派员手机
		 * @apiparam {Double} lng 经度
		 * @apiparam {Double} lat 纬度
		 * @apiparam {String} memo 备注
		 * @apiGroup 取件
		 * @apiSampleRequest /expGateway/saveOnLineMember
		 * @apiSuccess {String} result true
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"errCode":0,"errSubcode":"saveOnLineMember","message":"参数不能为空","success":false}
		 * @apiVersion 0.2.0
		 */ 
		 @RequestMapping("/saveOnLineMember")
		 @ResponseBody
		 public String saveOnLineMember(Long id,Long netId,String netName,Long compId,
				 String compName,Long memberId,String memberName,String memberMobile,
				 Double lng,Double lat,String memo){
				if(PubMethod.isEmpty(netId) || PubMethod.isEmpty(memberId)){
					return paramsFailure("saveOnLineMember","参数不能为空");
				}
			 return expGatewayService.saveOnLineMember(id, netId, netName, compId, compName, memberId, memberName, memberMobile, lng, lat, memo);
//				return jsonSuccess(null);
		 }
		 
		 
			/**
			 * 
			 * <dt><span class="strong">方法描述:</span></dt><dd>查询网点信息</dd>
			 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
			 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午5:25:24</dd>
			 * @param loginCompId 登录网点ID
			 * @return {"data":{"address":"北京-朝阳区|安定路20号东方燕都商务楼","addressId":11000203,"auditDesc":"","compId":13847259081342976,
			 * "compName":"风驰申通快递三站","compStatus":1,"compTelephone":"010-87954421","compTypeNum":"1006",
			 * "firstSystemImg":"2014103109525312580.png","firstSystemImgUrl":"http://localhost:8080/image/comp/2014103109525312580.png",
			 * "frontImg":"2014103109524245328.png","frontImgUrl":"http://localhost:8080/image/comp/2014103109524245328.png",
			 * "holdImg":"2014103109524949228.png","holdImgUrl":"http://localhost:8080/image/comp/2014103109524949228.png",
			 * "latitude":39.921518,"longitude":116.554823,"netId":1500,"netName":"申通快递","responsible":"风驰三",
			 * "responsibleIdNum":"110101199101012798","responsibleTelephone":"18311110003","reverseImg":"2014103109524532195.png",
			 * "reverseImgUrl":"http://localhost:8080/image/comp/2014103109524532195.png","secondSystemImg":"2014103109525918867.png",
			 * "secondSystemImgUrl":"http://localhost:8080/image/comp/2014103109525918867.png","verifyType":2},"success":true}
		 		 addressId:地址id
		 		 auditDesc:审核意见
		 		 compId:公司id
		 		 compName:公司名称
		 		 compStatus:公司状态
		 		 	-1:创建（未认证）
					0:提交待审核,（未认证）
					1:审核通过,（已认证）
					2: 审核不通过（未认证）
		 		 compTelephone:公司电话
		 		 compTypeNum:公司分类代码
					1006 加盟公司
					1050 营业分部 		 
				firstSystemImg:第一张系统截图名称	
				firstSystemImgUrl:第一张系统截图url
				frontImg:正面照名称
				frontImgUrl:正面照url
			    latitude:纬度
			    longitude:经度		
			    netId:快递网络id
			    netName:快递网络名称
			    responsible:负责人
			    responsibleIdNum:负责人身份证号
			    responsibleTelephone:负责人电话
			    reverseImg:反面照名称
			    reverseImgUrl:反面照所属url
			    secondSystemImg:第二章系统截图名称
			    secondSystemImgUrl:第二章系统截图url
			    verifyType:消息类型
					0：短信
					1：邮件
			 * <dt><span class="strong">异常:</span></dt>
		     * <dd>"openapi.CompInfoServiceImpl.queryCompBasicInfo.001", "获取网点基础信息参数异常"</dd>	    
			 * @since v1.0
			 */
			@ResponseBody
			@RequestMapping(value = "/queryCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
			public String queryCompInfo(Long loginCompId) {
				return expGatewayService.queryCompInfo(loginCompId);
			}
			
			/**
			 * 
			 * <dt><span class="strong">方法描述:</span></dt><dd>个人端完成任务</dd>
			 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
			 * <dt><span class="strong">时间:</span></dt><dd>2015-1-24 上午11:26:14</dd>
			 * @param taskId  任务id
			 * @return
			 * @since v1.0
			 */
			@ResponseBody
			@RequestMapping(value = "/finish/taskPersonal", method = { RequestMethod.GET,RequestMethod.POST })
			public String finishTaskPersonal(Long taskId) {
				return  expGatewayService.finishTaskPersonal(taskId);
			}
			
			/**
			 * @api {post} /expGateway/queryAlreadySignList 已提包裹列表
			 * @apiPermission user
			 * @apiDescription 已提包裹列表
			 * @apiparam {Short} sendMemberId 实际派件人ID
			 * @apiparam {Long} currentPage 当前页
			 * @apiparam {Integer} pageSize 共多少页
			 * @apiGroup 提货
			 * @apiSampleRequest /expGateway/queryAlreadySignList
			 * @apiSuccess {String} result true
			 * @apiError {String} result false
			 * @apiSuccessExample {json} Success-Response:
			 *     HTTP/1.1 200 OK
			 *   result: {
			 * 	    "count": 1,
			 *	    "data": [
			 *	        {
			 *	            "addresseeAddress": "北京市海淀区",
			 *	            "addresseeAddressmsg": "牡丹园北里",
			 *	            "addresseeMobile": "18943103327",
	 		 * 	            "addresseeName": "一样",
		 	 *	            "createTime": 1430795556000,
			 *	            "freight": 0.00,
			 *	            "parcelId": 144791180722176,
			 *	            "sendTaskId": 144805950963712,
			 *	            "totalGoodsAmount": 12.00,
			 *	            "waybillNum": "6921734976734"
			 *	        }
			 *	    ],
			 *	    "success": true
			 *	}
			 * @apiErrorExample {json} Error-Response:
			 *     HTTP/1.1 200 OK
			 *   {"errCode":0,"errSubcode":"openapi.ParcelInfoController.queryAlreadySignList.001","message":"memberId不能为null","success":false}
			 *   {"errCode":0,"errSubcode":"openapi.ParcelInfoController.queryAlreadySignList.002","message":"currentPage当前页数不能为空","success":false}
			 *   {"errCode":0,"errSubcode":"openapi.ParcelInfoController.queryAlreadySignList.003","message":"pageSize每页条数不能为空","success":false}
			 * @apiVersion 0.2.0
			 */
			@ResponseBody
			@RequestMapping(value ="/queryAlreadySignList", method = { RequestMethod.POST })
			public String queryAlreadySignList(Long sendMemberId,Integer currentPage, Integer pageSize) {
				try {
					if(PubMethod.isEmpty(sendMemberId)){
						return paramsFailure("openapi.ParcelInfoController.queryAlreadySignList.001", "memberId不能为null");
					}
					 if(PubMethod.isEmpty(currentPage)){
						return paramsFailure("openapi.ParcelInfoController.queryAlreadySignList.002", "currentPage当前页数不能为空");
					 }
					 if(PubMethod.isEmpty(pageSize)){
						return paramsFailure("openapi.ParcelInfoController.queryAlreadySignList.003", "pageSize每页条数不能为空");
					 }
					return expGatewayService.queryAlreadySignList(sendMemberId, currentPage, pageSize);
				} catch (Exception e) {
					e.printStackTrace();
					return jsonFailure(e);
				}
			}
			
			/** @Method: cancelParcelBatche 
			 * @Description: TODO
			 * @param sendTaskIds  用,分隔
			 * @param memberId
			 * @param parcelIds 用,分隔
			 * @param Id  用,分隔
			 * @param compId
			 * @param compName
			 * @return
			 * @author xiangwei.liu
			 * @date 2015-4-21 上午11:26:48
			 * @since jdk1.6
			 */
			/**
			 * @api {post} /expGateway/cancelParcelBatche 取消提货
			 * @apiPermission user
			 * @apiDescription 取消提货
			 * @apiparam {Long} memberId 收派员Id
			 * @apiparam {String} compName 站点名称
			 * @apiparam {Long} compId 站点id
			 * @apiparam {String} parcelIds 包裹Id
			 * @apiGroup 提货
			 * @apiSampleRequest /expGateway/cancelParcelBatche
			 * @apiSuccess {String} result true
			 * @apiError {String} result false
			 * @apiSuccessExample {json} Success-Response:
			 *     HTTP/1.1 200 OK
			 * @apiErrorExample {json} Error-Response:
			 *     HTTP/1.1 200 OK
			 * @apiVersion 0.2.0
			 */
	@ResponseBody
	@RequestMapping(value ="/cancelParcelBatche", method = { RequestMethod.POST })
	public String  cancelParcelBatche(Long memberId,String parcelIds,Long compId,String compName) {
		try {
				 if(PubMethod.isEmpty(memberId)){
					return paramsFailure("openapi.ParcelInfoController.cancelParcelBatche.002", "memberId不能为空");
				 }
				 if(PubMethod.isEmpty(parcelIds)){
					return paramsFailure("openapi.ParcelInfoController.cancelParcelBatche.003", "parcelId不能为空");
				 }
				 if(PubMethod.isEmpty(compId)){
					 return paramsFailure("openapi.ParcelInfoController.cancelParcelBatche.005", "compId不能为空");
				 }
				 if(PubMethod.isEmpty(compName)){
					 return paramsFailure("openapi.ParcelInfoController.cancelParcelBatche.006", "compName不能为空");
				 }	
				 return expGatewayService.cancelParcelBatche( memberId, parcelIds, compId, compName);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @Method: scannerSendTaskSign 
	 * @Description: TODO
	 * @param parcelId 包裹Id
	 * @param expWaybillNum 运单号
	 * @param compId 站点Id
	 * @param netId 网络Id
	 * @param addressAddressName   收件人姓名
	 * @param addressAddressMobile  收件人电话
	 * @param addresseeAddressId  收件人地址Id
	 * @param addresseeAddress 收件人详细地址
	 * @param freight 应收运费
	 * @param codAmount 应收货款
	 * @param memberId  收派员Id
	 * @return
	 * @author xiangwei.liu
	 * @date 2015-4-22 下午3:57:13
	 * @since jdk1.6
	 */
	
	/**
	 * @api {post} /expGateway/scannerSendTaskSign 签收
	 * @apiPermission user
	 * @apiDescription 签收
	 * @apiparam {Long} parcelId 包裹id
	 * @apiparam {String} expWaybillNum 运单号
	 * @apiparam {Long} compId 操作网点id
	 * @apiparam {Long} netId 快递网络id
	 * @apiparam {String} addressAddressName 收件人姓名
	 * @apiparam {String} addressAddressMobile 收件人手机号码
	 * @apiparam {Long} addresseeAddressId 收件人乡镇id
	 * @apiparam {String} addresseeAddress 收件人详细地址
	 * @apiparam {Double} freight 包裹应收运费
	 * @apiparam {Double} codAmount 代收货款金额
	 * @apiparam {Long} memberId 快递员id
	 * @apiparam {String} sendTaskId 派件任务id
	 * @apiGroup 派件
	 * @apiSampleRequest /expGateway/scannerSendTaskSign
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"errSubcode":"openapi.ExpGatewayController.finishTask.001","message":"id能为空","success":false}
	 * @apiVersion 0.2.0
	 */ 
	@ResponseBody
	@RequestMapping(value ="/scannerSendTaskSign", method = { RequestMethod.POST })
	public String  scannerSendTaskSign(Long parcelId,String expWaybillNum,Long compId ,Long netId,String addressAddressName
										,String addressAddressMobile,Long addresseeAddressId,
										 String addresseeAddress,Double freight,Double codAmount,Long memberId,Long sendTaskId) {
		try {
				 if(PubMethod.isEmpty(expWaybillNum)){
					return paramsFailure("openapi.ParcelInfoController.scannerSendTaskSign.001", "memberId不能为空");
				 }
				 if(PubMethod.isEmpty(netId)){
					 return paramsFailure("openapi.ParcelInfoController.scannerSendTaskSign.002", "netId不能为空");
				 }
				 if(PubMethod.isEmpty(addressAddressMobile)){
					 return paramsFailure("openapi.ParcelInfoController.scannerSendTaskSign.003", "addressAddressMobile不能为空");
				 }
				 if(PubMethod.isEmpty(memberId)){
					 return paramsFailure("openapi.ParcelInfoController.scannerSendTaskSign.004", "memberId不能为空");
				 }
				 return expGatewayService.scannerSendTaskSign(parcelId,expWaybillNum, compId, netId, addressAddressName, addressAddressMobile, addresseeAddressId, addresseeAddress, freight, codAmount, memberId,sendTaskId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @Method: scannerSendTaskSign 
	 * @Description: 签收扫描包裹  (产品新需求,不记录日志,只记录包裹扫描签收派件任务,只记录签收后的包裹,作为以后统计包裹数据)
	 * @param parcelId 包裹Id
	 * @param expWaybillNum 运单号
	 * @param compId 站点Id
	 * @param netId 网络Id
	 * @param addressAddressName   收件人姓名
	 * @param addressAddressMobile  收件人电话
	 * @param addresseeAddressId  收件人地址Id
	 * @param addresseeAddress 收件人详细地址
	 * @param freight 应收运费
	 * @param codAmount 应收货款
	 * @param memberId 收派员Id
	 * @return
	 * @author xiangwei.liu
	 * @date 2015-4-21 下午3:49:37
	 * @since jdk1.6
	 */
	@ResponseBody
	@RequestMapping(value ="/scannerSendTaskException", method = { RequestMethod.POST })
	public String  scannerSendTaskException(Long parcelId,String expWaybillNum,Long compId ,Long netId,String addressAddressName
										,String addressAddressMobile,Long addresseeAddressId,
										 String addresseeAddress,Double freight,Double codAmount,Long memberId, String disposalDesc ,Long sendTaskId) {
		try {
			 if(PubMethod.isEmpty(expWaybillNum)){
				return paramsFailure("openapi.ParcelInfoController.scannerSendTaskException.001", "memberId不能为空");
			 }
//			 if(PubMethod.isEmpty(netId)){
//				 return paramsFailure("openapi.ParcelInfoController.scannerSendTaskException.002", "netId不能为空");
//			 }
			 if(PubMethod.isEmpty(addressAddressMobile)){
				 return paramsFailure("openapi.ParcelInfoController.scannerSendTaskException.003", "addressAddressMobile不能为空");
			 }
			 if(PubMethod.isEmpty(memberId)){
				 return paramsFailure("openapi.ParcelInfoController.scannerSendTaskException.004", "memberId不能为空");
			 }
			 return expGatewayService.scannerSendTaskException(parcelId,expWaybillNum, compId, netId, addressAddressName, addressAddressMobile, addresseeAddressId, addresseeAddress, freight, codAmount, memberId,disposalDesc,sendTaskId);
	} catch (Exception e) {
		e.printStackTrace();
		return jsonFailure(e);
	}
	}
	
	/**
	 * @api {post} /expGateway/scanSemdTaskCreate 扫描结束
	 * @apiPermission user
	 * @apiDescription 扫描结束
	 * @apiparam {Long} parcelId 包裹id
	 * @apiparam {String} expWaybillNum 运单号
	 * @apiparam {Long} compId 操作网点id
	 * @apiparam {Long} netId 快递网络id
	 * @apiparam {String} addressAddressName 收件人姓名
	 * @apiparam {String} addressAddressMobile 收件人手机号码
	 * @apiparam {Long} addresseeAddressId 收件人乡镇id
	 * @apiparam {String} addresseeAddress 收件人详细地址
	 * @apiparam {Double} freight 包裹应收运费
	 * @apiparam {Double} codAmount 代收货款金额
	 * @apiparam {Long} memberId 快递员id
	 * @apiparam {String} memberPhone 快递员电话
	 * @apiparam {String} sendTaskId 派件任务id
	 * @apiGroup 派件
	 * @apiSampleRequest /expGateway/scanSemdTaskCreate
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"errSubcode":"openapi.ExpGatewayController.finishTask.001","message":"id能为空","success":false}
	 * @apiVersion 0.2.0
	 */ 
	@ResponseBody
	@RequestMapping(value ="/scanSemdTaskCreate", method = { RequestMethod.POST })
	public String  scanSemdTaskCreate(Long parcelId,String expWaybillNum,Long compId ,Long netId,String addressAddressName
										,String addressAddressMobile,Long addresseeAddressId,
										 String addresseeAddress,Double freight,Double codAmount,Long memberId,String memberPhone,String sendTaskId) { 
		try {
			 if(PubMethod.isEmpty(expWaybillNum)){
				return paramsFailure("openapi.ParcelInfoController.scanSemdTaskCreate.001", "expWaybillNum不能为空");
			 }
			 if(PubMethod.isEmpty(addressAddressMobile)){
				 return paramsFailure("openapi.ParcelInfoController.scanSemdTaskCreate.002", "addressAddressMobile不能为空");
			 }
			 if(PubMethod.isEmpty(memberId)){
				 return paramsFailure("openapi.ParcelInfoController.scanSemdTaskCreate.003", "memberId不能为空");
			 }
			 if(PubMethod.isEmpty(memberPhone)){
				 return paramsFailure("openapi.ParcelInfoController.scanSemdTaskCreate.004", "memberPhone不能为空");
			 }
			 return expGatewayService.scanSemdTaskCreate(parcelId, expWaybillNum, compId, netId, addressAddressName, addressAddressMobile, addresseeAddressId, addresseeAddress, freight, codAmount, memberId,memberPhone,sendTaskId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value ="/verifyRelation", method = { RequestMethod.POST,RequestMethod.GET })
	public String verifyRelation(String phone){
		try {
			 if(PubMethod.isEmpty(phone)){
				return paramsFailure("openapi.ExpGatewayController.verifyRelation.001", "phone参数非空异常");
			 }
			 return expGatewayService.verifyRelation(phone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}