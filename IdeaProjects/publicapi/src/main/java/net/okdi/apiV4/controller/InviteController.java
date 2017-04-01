package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.InviteService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 包名: net.okdi.apiV4.controller
 * 创建人 : Elong
 * 时间: 2017/1/21 下午4:22
 * 描述 : 邀请
 */
@Controller
@RequestMapping("/invite")
public class InviteController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(InviteController.class);

    @Autowired
    private InviteService inviteService;

    /**
     * @api {POST} /invite/collQRCode 邀请代收点二维码
     * @apiVersion 0.4.0
     * @apiDescription 邀请代收点二维码
     * @apiGroup 邀请
     * @apiParam {String} memberId memberId
     * @apiSuccess {String} data groupId
     * @apiSampleRequest /smsTimed/timeDelaySendSms
     * @apiSuccessExample Success-Response: 正常响应
     * {"data":"http://promo.okdi.net/promo/zwk/invcourier-download.jsp?promoSecret=buk/gQXlTzN9YNxpZrdwIEomgFt19BErOl1sX4w/ZjP6ZiNq7We0IpFo3oojaY+ScIj8e8h2AMg=","success":true}
     * @apiErrorExample Error-Response: 系统级异常
     *    {
     *    	"errCode":0,
     *    	"errSubcode":"",
     *    	"message":"请求失败",
     *    	"success":false
     *    }
     */
    @ResponseBody
    @RequestMapping(value = "/collQRCode", method = {RequestMethod.GET, RequestMethod.POST})
    private String collQRCode(String memberId){
        LOGGER.info("邀请代收点collQRCode: memberId:{}", memberId);
        if (PubMethod.isEmpty(memberId)) {
            return paramsFailure("0", "memberId 不能为空");
        }
        String result;
        try {
            result = inviteService.collQRCode(memberId);
        } catch (Exception e) {
            LOGGER.error("邀请代收点collQRCode: 发生异常:{}", e);
            return jsonFailure(e);
        }
        LOGGER.info("邀请代收点collQRCode: result:{}", result);
        return jsonSuccess(result);
    }

}
