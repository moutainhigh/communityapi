package net.okdi.apiV3.controller;

import net.okdi.apiV3.service.BillService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("bill")
public class BillController extends BaseController{
	
	@Autowired
	BillService smsBillService;
	/**
	 * @author yangkai
	 * @api {post} /bill/querySmsBill 查询账单列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询账单列表
	 * @apiGroup 快递员钱包（新版财务）
	 * @apiParam {String} accountId 账户号Id
	 * @apiParam {String} startTime 开始日期（精确到天，查全部的时候不用传）
	 * @apiParam {String} endTime 结束日期（精确到天，查全部的时候不用传）
	 * @apiParam {String} tradeCat (非必须)交易类目: 0购物 ，1提现，2充值，3通信，4返利，5转账，6扣除佣金
	 * @apiParam {String} platformId (非必须) 平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004 目前是后台写死的，前台不用传 )
	 * @apiParam {String} outerTradeType (非必须) 外部订单类型(用逗号隔开)
	 * @apiParam {String} page 当前页数
	 * @apiParam {String} rows 每页条数
	 * @apiParam {Short} flag 0 查询全部 1查询某天
	 * @apiSampleRequest /bill/querySmsBill
	 * @apiSuccess {String} items  账单列表
	 * @apiSuccess {String} buyer_account_id  买家账号ID
	 * @apiSuccess {String} created  创建时间
	 * @apiSuccess {String} delete_flag  删除标识 默认0，0：未删除 1：已删除
	 * @apiSuccess {String} end_if  账单列表
	 * @apiSuccess {String} modified  更新时间
	 * @apiSuccess {String} outer_tid  外部交易id
	 * @apiSuccess {String} outer_trade_type  交易类别  10010 群发通知-仅短信 10011 群发通知-电话优先 10012 群发通知-仅电话10013 拨打电话 10014 充值-余额充值到通讯费 
	 * 							10015 充值-微信充值到通讯费 10016 平台赠送-注册  10017 平台赠送-充值10018 平台赠送-邀请 10019 平台赠送-新手体验金 10020 平台赠送-好友充值返利 10021 平台赠送－关注好递用户（通信费账户）10023 平台赠送－关注好递用户（现金余额账户） 10022 群发通知-短信+群呼 1002  提现1006  平台赠送-邀请爱购猫用户
	 * @apiSuccess {String} platform_id  平台id(爱购猫 100001 快递员 100002 好递支付 100003 其他 100004)
	 * @apiSuccess {String} seller_account_id  卖家账号ID
	 * @apiSuccess {String} third_channel  第三方资金渠道
	 * @apiSuccess {String} tid  主键ID
	 * @apiSuccess {String} title  交易说明
	 * @apiSuccess {String} total_amount  创建订单时用户需要支付的总金额,创建后不能修改
	 * @apiSuccess {String} trade_cat  交易类别 0购物 ，1提现，2充值，3通信，4返利，5转账
	 * @apiSuccess {String} trade_status  账单交易状态：1交易成功 2交易失败 3进行中4取消支付5交易关闭
	 * @apiSuccess {String} tradeMoney  交易金额，比如+50.00;
	 * @apiSuccess {String} type 交易说明，例如：群发通知-仅短信、平台赠送-充值
	 * @apiSuccess {String} logo 银行logo
	 * @apiSuccess {String} trade_type  购物 （支付方式 0在线 1货到） 充值（0 余额 1购物卡 2通信 3好递运营） 返利（0购物卡 1个人 3店铺）转账（1.余额充值到购物卡 2.余额充值到通讯账户 3.好递运营充值到通讯账户）扣款(0 扣除到业务运营 1 扣除到中间分享账户) 分利(0 中间分享账户到个人)
	 * @apiSuccess {String} itemsName  账单列表名称
	 * @apiSuccess {String} inCome  总收入
	 * @apiSuccess {String} outCome  总支出
	 * @apiSuccess {String} smsOrPhoneCount  群呼/短信数量（outer_trade_type为10010或10012）
	 * @apiSuccess {Long} phoneCount  群呼数量（outer_trade_type为10011）
	 * @apiSuccess {Long} noticeCount  短信数量（outer_trade_type为10011）
	 * @apiSuccess {Long} weChatCount  微信数量
	 * @apiSuccessExample Success-Response:
	 * 如果是查询全部的话，先解析ltemsName 然后遍历取得listName,根据listName,对应items里边的列表
	    {
    "items": {
        "201603": [
            {
                "buyer_account_id": "181983176695808",
                "created": "2016-03-03 14:30:19",
                "delete_flag": "0",
                "end_if": "0",
                "modified": "2016-03-03 14:30:19",
                "outer_tid": "",
                "outer_trade_type": "提现",
                "platform_id": "100002",
                "seller_account_id": "181983176695808",
                "third_channel": "",
                "tid": "99858909520475136",
                "title": "",
                "total_amount": "50.0000",
                "trade_cat": "1",
                "trade_status": "交易成功",
                "trade_type": "0"
            },
            {
                "buyer_account_id": "181983176695808",
                "created": "2016-03-02 16:17:19",
                "delete_flag": "0",
                "end_if": "1",
                "modified": "2016-03-02 16:17:19",
                "outer_tid": "1111",
                "outer_trade_type": "提现",
                "platform_id": "100002",
                "seller_account_id": "100003",
                "third_channel": "",
                "tid": "99775045320061952",
                "title": "",
                "total_amount": "20.0000",
                "trade_cat": "3",
                "trade_status": "交易成功",
                "trade_type": "0"
            }
        ]
    },
    "itemsName": [
        {
            "inCome": "107.000",
            "listName": "201603",
            "outCome": "163.082"
        }
    ],
    "success": true
}
//查询某一天的，只需要对着取就行了
{
    "items": [
        {
            "buyer_account_id": "181983176695808",
            "created": "2016-03-02 15:28:48",
            "delete_flag": "0",
            "end_if": "0",
            "mch_id": "",
            "modified": "2016-03-02 15:28:48",
            "orders": "",
            "outer_tid": "",
            "outer_trade_type": "通信账户充值-微信支付",
            "pay_ways": "",
            "payment": "15.0",
            "platform_id": "100002",
            "received_payment": "0",
            "remark": "充值-微信充值到通讯费",
            "return_amount": "0",
            "seller_account_id": "181983176695808",
            "third_channel": "",
            "third_trade_num": "",
            "tid": "99771992283033600",
            "title": "充值-微信充值到通讯费",
            "total_amount": "15.0",
            "tradeMoney": "+15.",
            "trade_cat": "2",
            "trade_status": "3",
            "trade_type": "2",
            "transaction_id": "",
            "type": "通信账户充值-微信支付"
        },
        {
            "buyer_account_id": "181983176695808",
            "created": "2016-03-02 15:13:45",
            "delete_flag": "0",
            "end_if": "0",
            "mch_id": "",
            "modified": "2016-03-02 15:13:45",
            "orders": "",
            "outer_tid": "",
            "outer_trade_type": "通信账户充值-微信支付",
            "pay_ways": "",
            "payment": "15.0",
            "platform_id": "100002",
            "received_payment": "0",
            "remark": "充值-微信充值到通讯费",
            "return_amount": "0",
            "seller_account_id": "181983176695808",
            "third_channel": "",
            "third_trade_num": "",
            "tid": "99771045087555584",
            "title": "充值-微信充值到通讯费",
            "total_amount": "15.0",
            "tradeMoney": "+15.",
            "trade_cat": "2",
            "trade_status": "3",
            "trade_type": "2",
            "transaction_id": "",
            "type": "通信账户充值-微信支付"
        }
    ],
    "total": 3,
    "itemsName": [
        {
            "inCome": "0",
            "outCome": "0"
        }
    ],
    "success": true
}
	
	 * @apiErrorExample Error-Response:
	 *  {"errCode":0,"errSubcode":"net.okdi.apiV3.controller.SmsBillController.querySmsBill.002","message":"查询标识不能为空","success":false}
	 */
	@ResponseBody
	@RequestMapping(value="querySmsBill",method={RequestMethod.POST, RequestMethod.GET })
	public String querySmsBill(String accountId,String startTime,String endTime,String tradeCat,
			String platformId,String outerTradeType,String page,String rows,Short flag){
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("net.okdi.apiV3.controller.SmsBillController.querySmsBill.001", "钱包id不能为空");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.apiV3.controller.SmsBillController.querySmsBill.002", "查询标识不能为空");
		}
		if(flag!=1&&flag!=0){
			return paramsFailure("net.okdi.apiV3.controller.SmsBillController.querySmsBill.003", "查询标识只能为1或0");
		}
		if(flag==1){
			if(PubMethod.isEmpty(startTime)){
				return paramsFailure("net.okdi.apiV3.controller.SmsBillController.querySmsBill.004", "起始时间不能为空");
			}
			if(PubMethod.isEmpty(endTime)){
				return paramsFailure("net.okdi.apiV3.controller.SmsBillController.querySmsBill.005", "结束时间不能为空");
			}	
		}
		if(PubMethod.isEmpty(page)){
			page="1";
		}
		if(PubMethod.isEmpty(rows)){
			rows="20";
		}
		try {
			return this.smsBillService.querySmsBill(accountId,startTime,endTime,tradeCat,platformId,outerTradeType,page,rows,flag);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}
