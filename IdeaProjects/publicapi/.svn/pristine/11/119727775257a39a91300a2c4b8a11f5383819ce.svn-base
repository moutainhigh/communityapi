package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.NoticeOrderService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/noticeOrderc")
public class NoticeOrderController extends BaseController {

	@Autowired
	private NoticeOrderService noticeOrderService;
	
	
	/**
	 * @author zhengjiong
	 * @api {post} /noticeOrderc/queryNoderDetailByCreateTime 查询取件订单列表--取件
	 * @apiVersion 0.2.0
	 * @apiDescription 查询取件订单列表
	 * @apiGroup 新版-取件V4
	 * @apiParam {String} startTime 开始时间(不能为空)
	 * @apiParam {String} endTime 结束时间(不能为空)
	 * @apiSampleRequest /noticeOrderc/queryNoderDetailByCreateTime
	 * @apiSuccess {String} backState  回执状态 0:未知, 1:成功, 2:失败, 3:呼叫未知, 4:呼叫成功, 5:呼叫失败
	 * @apiSuccess {String} sendtate  0:发送成功,1:呼叫成功,3:呼叫失败,repeat:重复, reject:用户退订, -99:发送失败
	 * @apiSuccess {String} createTime  创建时间
	 * @apiSuccess {String} flag 类型 1:仅短信, 2:电话不通转短信,3:仅电话
	 * @apiSuccess {String} isFree 是否扣费 0:不 ,1:是
	 * @apiSuccess {String} msgId 唯一id
	 * @apiSuccess {String} noticeId 订单id
	 * @apiSuccess {String} purchaseContentLen 采购总量(长度)
	 * @apiSuccess {String} purchaseCount 生成采购数量
	 * @apiSuccess {String} purchaseNum 采购计量(长度)
	 * @apiSuccess {String} purchasePrice 采购单价
	 * @apiSuccess {String} purchaseTotalPrice 采购总价
	 * @apiSuccess {String} realsaleTotalPrice 实际销售总价
	 * @apiSuccess {String} receiveTime 接收时间
	 * @apiSuccess {String} receiverPhone 接受手机号
	 * @apiSuccess {String} saleContentLen 
	 * @apiSuccess {String} saleCount 生成销售数量
	 * @apiSuccess {String} saleTotalPrice 预扣款
	 * @apiSuccess {String} sellPrice 出售价格
	 * @apiSuccess {String} sellUnit 出售计量
	 * @apiSuccess {String} sendAccount 账号
	 * @apiSuccess {String} serviceProvider 通道
	 * @apiSuccess {String} szdSaleTotalPrice 
	 * @apiSuccess {String} unitName 采购计量单位(字数)
	 * @apiSuccessExample Success-Response:
	   {
		    "data": [
		        {
		            "backState": "1",
		            "content": "【好递】客户您好，10分钟后上门取件，如果不在家请提前通知。短信可回",
		            "createTime": 1472544127858,
		            "flag": 1,
		            "isFree": "0",
		            "msgId": "E0383016083016020700",
		            "noticeId": "232344279891968",
		            "purchaseContentLen": "34",
		            "purchaseCount": "1",
		            "purchaseNum": "70",
		            "purchasePrice": "0.035",
		            "purchaseTotalPrice": "0.035",
		            "realsaleTotalPrice": "0.04",
		            "receiveTime": 1472544131000,
		            "receiverPhone": "13601104720",
		            "saleContentLen": "34",
		            "saleCount": "1",
		            "saleTotalPrice": "0.04",
		            "sellPrice": "0.04",
		            "sellUnit": "70",
		            "sendAccount": "admin",
		            "sendtate": "0",
		            "serviceProvider": "1",
		            "szdSaleTotalPrice": "0.04",
		            "unitName": "字数（个）"
		        },
		        {
		            "backState": "1",
		            "content": "【好递】客户您好，10分钟后上门取件，如果不在家请提前通知。短信可回",
		            "createTime": 1472544128127,
		            "flag": 1,
		            "isFree": "0",
		            "msgId": "E0392516083016020700",
		            "noticeId": "232344279891968",
		            "purchaseContentLen": "34",
		            "purchaseCount": "1",
		            "purchaseNum": "70",
		            "purchasePrice": "0.035",
		            "purchaseTotalPrice": "0.035",
		            "realsaleTotalPrice": "0.04",
		            "receiveTime": 1472544130000,
		            "receiverPhone": "13601104720",
		            "saleContentLen": "34",
		            "saleCount": "1",
		            "saleTotalPrice": "0.04",
		            "sellPrice": "0.04",
		            "sellUnit": "70",
		            "sendAccount": "admin",
		            "sendtate": "0",
		            "serviceProvider": "1",
		            "szdSaleTotalPrice": "0.04",
		            "unitName": "字数（个）"
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
	@RequestMapping(value="/queryNoderDetailByCreateTime",method={RequestMethod.POST,RequestMethod.GET})
	public String queryNoderDetailByCreateTime(String startTime, String endTime){
		
		if(PubMethod.isEmpty(startTime) && PubMethod.isEmpty(endTime)){
			return paramsFailure("001", "时间不能为空!");
		}
		try{
			String result = noticeOrderService.queryNoderDetailByCreateTime(startTime, endTime);
			return result;
		  }catch(Exception e){
				return jsonFailure(e);
		  }
	}
}
