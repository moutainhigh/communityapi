package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.AttentionContactsService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/attentionContacts")
public class AttentionContactsController  extends BaseController{
	@Autowired
	private AttentionContactsService attentionContactsService;
	
	/**
	 * @author zhaohu
	 * @api {post} /attentionContacts/queryNearMemberForAttention 查询附近快递员--关注联系人使用test
	 * @apiVersion 0.2.0
	 * @apiDescription 查询附近快递员--关注联系人使用
	 * @apiGroup 快递圈
	 * @apiParam {Double} lng 经度
	 * @apiParam {Double} lat 纬度
	 * @apiParam {Integer} howFast 附近几公里？
	 * @apiParam {String} memberId 快递员id
	 * @apiSampleRequest /attentionContacts/queryNearMemberForAttention
	 * @apiSuccess {String} headImg  头像
	 * @apiSuccess {Long} memberId  快递员id
	 * @apiSuccess {String} memberMobile  手机号
	 * @apiSuccess {String} memberName  姓名
	 * @apiSuccess {String} distanceKM  千米
	 * @apiSuccess {double} distanceM  米
	 * @apiSuccessExample Success-Response:
	      {
		    "data": [
		        {
		            "compAddress": "北京市-北京市区-东城区|",
		            "headImg": "http://publicapi.okdit.net/nfs_data/mob/head//159514953588736.jpg",
		            "memberId": 159514953588736,
		            "memberMobile": "17080032629",
		            "memberName": "docker"
		        },
		        {
		            "compAddress": "北京市-北京市区-丰台区-城区|北京市丰台区玉泉营桥东东北角陶瓷城园内&amp;amp,nbsp,",
		            "headImg": "http://publicapi.okdit.net/nfs_data/mob/head//203725182648320.jpg",
		            "memberId": 203725182648320,
		            "memberMobile": "13581652368",
		            "memberName": "逆光。"
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
	@RequestMapping(value = "/queryNearMemberForAttention", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNearMemberForAttention(Double lng, Double lat,Integer howFast,String memberId){
		try {
			if(PubMethod.isEmpty(lng)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearMemberForAttention.001", "经度lng不能为空");
			}
			if(PubMethod.isEmpty(lat)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearMemberForAttention.002", "纬度lat不能为空");
			}
			if(PubMethod.isEmpty(howFast)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearMemberForAttention.003", "howFast不能为空!");
			}
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearMemberForAttention.004", "memberId不能为空!");
			}
			return this.attentionContactsService.queryNearMemberForAttention( lng,  lat, howFast,memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * @author zhaohu
	 * @api {post} /attentionContacts/queryContactsBookList 查询通讯录好友列表--关注联系人使用
	 * @apiVersion 0.2.0
	 * @apiDescription 查询通讯录好友列表--关注联系人使用
	 * @apiGroup 快递圈
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} jsonData json字符串，格式如下：{"items":[{"name":"赵虎","phone":"15011232453"},{"name":"吕布","phone":"15688888888"}]} name 姓名（String类型），phone 手机号（String类型）
	 * @apiSampleRequest /attentionContacts/queryContactsBookList
	 * @apiSuccess {Long} memberId  快递员id
	 * @apiSuccess {String} phone  手机号
	 * @apiSuccess {String} name  姓名
	 * @apiSuccess {String} firstLetter 姓名首字母
	 * @apiSuccessExample Success-Response:
	      {
		    "data": {
		        "A": [],
		        "B": [],
		        "C": [],
		        "D": [],
		        "E": [],
		        "F": [],
		        "G": [
		            {
		                "firstLetter": "G",
		                "name": "格生产",
		                "phone": "13711235888"
		            }
		        ],
		        "H": [],
		        "I": [],
		        "J": [],
		        "K": [],
		        "L": [
		            {
		                "firstLetter": "L",
		                "name": "吕布",
		                "phone": "15688888888"
		            }
		        ],
		        "M": [],
		        "N": [],
		        "O": [],
		        "OTHER": [],
		        "P": [],
		        "Q": [],
		        "R": [],
		        "S": [],
		        "T": [],
		        "U": [],
		        "V": [],
		        "W": [],
		        "X": [],
		        "Y": [],
		        "Z": []
		    },
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
	@RequestMapping(value = "/queryContactsBookList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryContactsBookList(Long memberId,String jsonData){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryContactsBookList.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(jsonData)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryContactsBookList.002", "jsonData不能为空");
			}
			return this.attentionContactsService.queryContactsBookList( memberId, jsonData);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	
}
