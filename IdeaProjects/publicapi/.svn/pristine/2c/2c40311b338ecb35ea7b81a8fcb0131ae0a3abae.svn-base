package net.okdi.apiV1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.apiV1.service.ExpressSiteService;
import net.okdi.core.base.BaseController;

/**
 * 快递站点类
 * @author jingguoqiang
 * @version V1.0
 */
@Controller
@RequestMapping("/expressSite")
public class ExpressSiteController extends BaseController {
	
	@Autowired 
	ExpressSiteService expressSiteService;



	  
	

	
	/**
	 * @api {post} /expressSite/queryCompInfo 根据登录网点ID查询(站长或收派员)网点详细信息
	 * @apiPermission user
     * @author guoqiang.jing
	 * @apiVersion 0.2.0
	 * @apiDescription  根据登录网点ID查询(站长或收派员)网点详细信息或认证信息(可供运营平台用)(静国强)
	 * @apiGroup 站点
	 * @apiParam {Long} loginCompId 登录网点ID
	 * @apiSampleRequest /expressSite/queryCompInfo
	 * @apiSuccessExample Success-Response:
		{"data":{"province":"北京市-北京市区-海淀区"
		         "address":"花园路12号",
		         "addressId":110108,	         
		         "compId":3435104,
		         "compName":"北京田村站点",
		         "compStatus":1,
		         "compTelephone":"15811583966",
		         "compTypeNum":"1006",		         
		         "latitude":40.033162,
		         "longitude":116.239678,
		         "netId":1001,
		         "netName":"顺丰速运",		         
		         "responsible":"文超",
		         "responsibleIdNum":"411522198405146316",
		         "responsibleTelephone":"","useCompId":174550617038848
		         },
		   "success":true}


	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/queryCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompVerifyInfo(Long loginCompId) {
		try {
			return expressSiteService.queryCompVerifyInfo(loginCompId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
/*-----------------------------------快递员操作的--------------------------------*/
	/**
	 * @author guoqiang.jing
	 * @api {post} /expressSite/applyJoin  快递员 --申请入站
	 * @apiVersion 0.2.0
	 * @apiDescription  快递员 --申请入站(静国强)
	 * @apiGroup 站点
	 * @apiParam {Long} member_id 人员ID
	 * @apiParam {Long} audit_comp 所属公司ID
	 * @apiParam {Integer} application_role_type 申请角色类型 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiParam {Integer} audit_item 审核项  2:归属审核

	 * @apiSampleRequest /expressSite/applyJoin
	 * @apiSuccessExample Success-Response:
		{
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
	@RequestMapping(value = "/applyJoin", method = { RequestMethod.POST, RequestMethod.GET })
	
	public String applyJoin(Long member_id,Long audit_comp,String application_role_type,
			  String audit_item) {
		try {
			return expressSiteService.applyJoin(String.valueOf(member_id),String.valueOf(audit_comp),String.valueOf(application_role_type), String.valueOf(audit_item));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/*@ResponseBody
	@RequestMapping(value = "/applyJoinNoSign", method = { RequestMethod.POST, RequestMethod.GET })
	
	public String applyJoinNoSign(Long member_id,Long belongToNetId,Integer application_role_type,
			String toMemberPhone) {
		try {
			return expressSiteService.applyJoinNoSign(String.valueOf(member_id),String.valueOf(belongToNetId), String.valueOf(application_role_type) ,
					toMemberPhone);
			
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}*/
	
	/**
	 * @author guoqiang.jing
	 * @api {post} /expressSite/queryJoinState  快递员 --通过手机号查询站长入住状态
	 * @apiVersion 0.2.0
	 * @apiDescription  快递员 --通过手机号查询站长入住状态(静国强)
	 * @apiGroup 站点
	 * @apiParam {String} member_phone 手机号

	 * @apiSampleRequest /expressSite/queryJoinState
	 * @apiSuccessExample Success-Response:
		{"data":{
			     "comp_address":"北京市-北京市区-海淀区-城区|田村路 37",
			     "comp_id":2051405073928041,
			     "comp_name":"宅急送（生产环境测试站）",
			     "reverseImgUrl":"http://expnew.okdit.net/nfs_data/comp/null"
		     },
		 "success":true
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
	@RequestMapping(value = "/queryJoinState", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryJoinState(String member_phone) {
		try {
			
			return expressSiteService.queryJoinState(member_phone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	

/*	@ResponseBody
	@RequestMapping(value = "/invite", method = { RequestMethod.POST, RequestMethod.GET })
	public String invite(Long fromMemberId, String fromMemberPhone,
			  Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId) {
		return expressSiteService.invite( fromMemberId,fromMemberPhone, fromMemberRoleid, toMemberPhone,toRoleId);
		 
	}*/
	/*-----------------------------------------------------------*/
	
	/**
	 * @author 静国强
	 * @api {post} /expressSite/submitBasCompInfo 站长--提交网点 基本信息
	 * @apiVersion 0.2.0
	 * @apiDescription 站长--提交网点 基本信息(静国强)
	 * @apiGroup 站点
	 * @apiParam {Long} loginCompId           登录网点ID
	 * @apiParam {String} compName              网点名称
	 * @apiParam {Integer} belongToNetId         归属快递id
	 * @apiParam {String} county                区县
	 * @apiParam {Double} longitude             精度
	 * @apiParam {Double} latitude              纬度
	 * @apiParam {String} address               联系地址
	 * @apiParam {String} responsibleTelephone  负责人电话
	 * @apiParam {Long} member_id             人员ID
	 * @apiSampleRequest /expressSite/submitBasCompInfo
	 * @apiSuccessExample Success-Response:
		{"data":"175272903401472","success":true}  (data是'提交网点认证信息'需要的loginCompId)
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/submitBasCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitBasCompInfo(Long loginCompId, String compName, Integer belongToNetId, String county, 
			Double longitude, Double latitude,String address,String responsibleTelephone,Long member_id) {
		try {
			return expressSiteService.submitBasCompInfo(loginCompId, compName, String.valueOf(belongToNetId), county, longitude, latitude,address,responsibleTelephone,String.valueOf(member_id));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	
	
	/**
	 * @author 静国强
	 * @api {post} /expressSite/submitCompVerifyInfo 站长--提交网点认证信息
	 * @apiVersion 0.2.0
	 * @apiDescription 站长--提交网点认证信息(静国强)
	 * @apiGroup 站点
	 * @apiParam {Long} loginCompId   登录网点ID(提交网点基本信息接口返回的data值)
	 * @apiParam {String} responsible          负责人姓名
	 * @apiParam {String} responsibleTelephone 负责人电话
	 * @apiParam {String} responsibleNum        负责人身份证号
	 * @apiParam {String} frontImg             负责人身份证正面
	 * @apiParam {String} holdImg               营业执照
	 * @apiParam {String} reverseImg              店铺照片
	 * @apiParam {Long} memberId               人员ID
	 * @apiSampleRequest /expressSite/submitCompVerifyInfo
	 * @apiSuccessExample Success-Response:
		{
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
	@RequestMapping(value = "/submitCompVerifyInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitCompVerifyInfo(Long loginCompId, String responsible, String responsibleTelephone, String responsibleNum, 
			String frontImg,String holdImg, String reverseImg,Long memberId) {
		try {
			return	expressSiteService.submitCompVerifyInfo(String.valueOf(loginCompId), responsible, responsibleTelephone, responsibleNum,
					frontImg,holdImg, reverseImg,memberId);
	
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author guoqiang.jing
	 * @api {post} /expressSite/saveCompInfo  站长--添加网点/营业部信息
	 * @apiVersion 0.2.0
	 * @apiDescription  站长--添加网点/营业部信息(静国强)
	 * @apiGroup 站点
	 * @apiParam {Long} memberId          创建人ID
	 * @apiParam {Long} netId             网络ID
	 * @apiParam {String} compTypeNum     网点类型 1006站点 1050营业分部 
	 * @apiParam {String} compName        网点名称
	 * @apiParam {String} compTelephone   网点电话
	 * @apiParam {Double} longitude         经度
	 * @apiParam {Double} latitude          纬度
	 * @apiParam {String} county          区县
	 * @apiParam {String} address         网点详细地址

	 * @apiSampleRequest /expressSite/saveCompInfo
	 * @apiSuccessExample Success-Response:
		{"data":176315183751168,"success":true}   (data是'提交网点认证信息'需要的loginCompId)
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,
			Double longitude, Double latitude, String county,String address) {
		try {
			return expressSiteService.saveCompInfo(memberId, netId, compTypeNum, compName, compTelephone,
					longitude,latitude, county,address);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
/*----------------------------------运营平台*/
	

	/*@ResponseBody
	@RequestMapping(value = "/auditSite", method = { RequestMethod.POST, RequestMethod.GET })
	public String auditSite(String compId, String compStatus) {
		try {
			return expressSiteService.auditSite(compId, compStatus);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}*/

    /**
     * @author 裴二龙
     * @api {post} /expressSite/queryMessageDetails  通过单条公告的id查询公告详情-裴二龙
     * @apiVersion 0.2.0
     * @apiDescription 通过单条公告的id查询公告详情-裴二龙
     * @apiGroup 公告管理
     * @apiParam {Long} id 单条公告的id
     * @apiParam {String} memberId memberId
     * @apiSampleRequest /expressSite/queryMessageDetails
     * @apiSuccessExample Success-Response:
    {
    "data":{
    "announceType":1,
    "content":"内容",                 //公告内容详情
    "createTime":1461567101020,
    "creator":"admin",
    "id":209323787862016,
    "modifiedTime":1461569093646,
    "pushWay":1,
    "status":1,
    "title":"标题",                   //公告标题
    "urlPics": [],                   //详情中的图片
	"isRead":0						 //0:未读  1:已读
    },
    "success":true
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
    @RequestMapping(value = "/queryMessageDetails", method = { RequestMethod.POST, RequestMethod.GET })
    public String queryMessageDetails(Long id, String memberId){
        return expressSiteService.queryMessageDetails(id, memberId);
    }

    /**
     * @author 裴二龙
     * @api {post} /expressSite/queryMyMessage  公告列表
     * @apiVersion 0.2.0
     * @apiDescription 公告列表
     * @apiGroup 公告管理
     * @apiParam {String} phone 手机号
     * @apiSampleRequest /expressSite/queryMyMessage
     * @apiSuccessExample Success-Response:
    {
    "data":{{
    "announceType":1,
    "content":"内容",                 //公告内容详情
    "createTime":1461567101020,
    "creator":"admin",
    "id":209323787862016,
    "modifiedTime":1461569093646,
    "pushWay":1,
    "status":1,
    "title":"标题",                   //公告标题
    "urlPics": [],                   //详情中的图片
	"isRead":0						 //0:未读  1:已读
    }],
    "success":true
    }
     * @apiErrorExample Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *	     "success":	false,
     *	     "errCode":	"err.001",
     *	     "message":"XXX"
     *     }
     */
	//查看我收到的公告
	@ResponseBody
	@RequestMapping(value = "/queryMyMessage", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryMyMessage(String phone) {
		
	return expressSiteService.queryMyMessage(phone);
	     
    }

	/**
	 * @author 裴二龙
	 * @api {post} /expressSite/queryMyMessage/v5  公告列表
	 * @apiVersion 0.2.0
	 * @apiDescription 公告列表
	 * @apiGroup 公告管理
	 * @apiParam {Long} memberId memberId
	 * @apiSampleRequest /expressSite/queryMyMessage/v5
	 * @apiSuccessExample Success-Response:
	{
	"data":{{
	"announceType":1,
	"content":"内容",                 //公告内容详情
	"createTime":1461567101020,
	"creator":"admin",
	"id":209323787862016,
	"modifiedTime":1461569093646,
	"pushWay":1,
	"status":1,
	"title":"标题",                   //公告标题
	"urlPics": [],                   //详情中的图片
	"isRead":0						 //0:未读  1:已读
	}],
	"success":true
	}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	//查看我收到的公告
	@ResponseBody
	@RequestMapping(value = "/queryMyMessage/v5", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryMyMessage(Long memberId) {

		return expressSiteService.queryMyMessage(memberId);

	}

	//清空所有公告
	@ResponseBody
	@RequestMapping(value = "/deleteMyAllMessage", method = { RequestMethod.POST,RequestMethod.GET })
	public String deleleMyAllMessage(String memberPhone) {  
		
		return expressSiteService.deleleMyAllMessage(memberPhone);
		
	}
	//删除我收到的某一条公告
	@ResponseBody
	@RequestMapping(value = "/deleteMyOneMessage", method = { RequestMethod.POST,RequestMethod.GET })
	public String deleleMyOneMessage(Long pushId) {  
		
		return expressSiteService.deleleMyOneMessage(pushId);
		
	}
	
	//阅读某一条公告
	@ResponseBody			  
	@RequestMapping(value = "/readOneMessage", method = { RequestMethod.POST,RequestMethod.GET })
	public String readOneMessage(Long pushId) {  
		return expressSiteService.readOneMessage(pushId);
	}
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /expressSite/queryMessageNewest  查询30天内未读的最近一条公告
	 * @apiVersion 0.2.0
	 * @apiDescription  查询30天内未读的最近一条公告
	 * @apiGroup 公告管理
	 * @apiParam {String} memberId 人员ID
	 * @apiSampleRequest /expressSite/queryMessageNewest
	 * @apiSuccessExample Success-Response:
		{"data":{
	     "announceType":1,
	     "content":"上传图片测试",
	     "createTime":1477463689247,
	     "creator":"admin",
	     "id":242661350555648,
	     "modifiedTime":1477463830421,
	     "pushWay":0,"status":1,
	     "thumbnailUrl":"",
	     "title":"公告测试",
	     "urlPics":[]
	     },
		 {"success":true}
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
	@RequestMapping(value = "/queryMessageNewest", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryMessageNewest(String memberId) {  
			return expressSiteService.queryMessageNewest(memberId);	     
    }
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /expressSite/addNoticeState  标记公告为已读11
	 * @apiVersion 0.2.0
	 * @apiDescription  标记公告为已读11
	 * @apiGroup 公告管理
	 * @apiParam {String} Id 公告ID
	 * @apiParam {String} memberId 人员ID
	 * @apiSampleRequest /expressSite/addNoticeState
	 * @apiSuccessExample Success-Response:
		{"data":
			"ture","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/addNoticeState", method = { RequestMethod.POST,RequestMethod.GET })
	public String addNoticeState(String Id,String memberId) {
			return expressSiteService.addNoticeState(Id,memberId);	     
    }


}