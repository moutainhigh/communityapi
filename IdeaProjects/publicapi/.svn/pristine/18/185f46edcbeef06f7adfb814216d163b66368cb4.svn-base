package net.okdi.apiV5.controller;

import net.okdi.apiV5.service.CooperationExpCompanyAuthService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/coopcomp/auth")
public class CooperationExpCompanyAuthController extends BaseController{

    private @Autowired CooperationExpCompanyAuthService cooperationExpCompanyAuthService;

    /**
     * @author hang.yu
     * @api {post} /coopcomp/auth/status 1-授权检查
     * @apiVersion 0.2.0
     * @apiDescription 授权检查
     * @apiGroup 第三方快递公司接入
     * @apiParam {Long} memberId 快递员id
     * @apiParam {Long} netId 快递员网络id
     * @apiSuccess {String} rc 响应码 <p><code>001</code> 正常响应</p>
     *                               <p><code>003</code> 快递公司未接入, 不需要授权</p>
     *                               <p><code>004</code> 快递公司接入了但是快递员没有授权, 需要弹框授权</p>
     *                               <p><code>006</code> 授权失败</p>
     *
     * @apiSuccess {String} err 错误提示
     * @apiSampleRequest /coopcomp/auth/status
     * @apiSuccessExample Success-Response:
    {
        "data": {
            "rc": "001",
            "err": ""
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
    @RequestMapping("/status")
    public String getAuthStatus(Long memberId, Long netId) {
        if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(netId)) {
            return paramsFailure("001", "invalid params");
        }
        return cooperationExpCompanyAuthService.getAuthStatusByMemberId(memberId, netId);
    }

    /**
     * @author hang.yu
     * @api {post} /coopcomp/auth/gt/add 2-国通快递员授权
     * @apiVersion 0.2.0
     * @apiDescription 国通快递员授权
     * @apiGroup 第三方快递公司接入
     * @apiParam {Long} memberId 快递员id
     * @apiParam {Long} netId 快递员网络id
     * @apiParam {String} telNum 手机号
     * @apiParam {String} orgCode 网点代码
     * @apiParam {String} userCode 国通账号
     * @apiParam {String} password 密码
     * @apiSuccess {String} rc 响应码 <p><code>001</code> 正常响应</p>
     *                               <p><code>003</code> 快递公司未接入</p>
     *                               <p><code>005</code> 快递员已经授权过</p>
     *                               <p><code>006</code> 授权失败</p>
     *
     * @apiSuccess {String} err 错误提示
     * @apiSampleRequest /coopcomp/auth/gt/add
     * @apiSuccessExample Success-Response:
    {
    "data": {
    "rc": "001",
    "err": ""
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
    @RequestMapping("/gt/add")
    public String addAuth(Long memberId, Long netId, String orgCode, String userCode, String telNum, String password) {
        if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(netId)) {
            return paramsFailure("001", "invalid params");
        }
        if (PubMethod.isEmpty(orgCode) || PubMethod.isEmpty(userCode)) {
            return paramsFailure("002", "invalid params");
        }
        if (PubMethod.isEmpty(telNum)) {
            return paramsFailure("003", "invalid params");
        }
        return cooperationExpCompanyAuthService.addAuth(memberId, netId, orgCode, userCode, telNum, password);
    }


}
