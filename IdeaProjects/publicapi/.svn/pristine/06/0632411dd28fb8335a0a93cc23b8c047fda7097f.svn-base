package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.MarkPackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 标记
 */
@Controller
@RequestMapping("/mark")
public class MarkPackageController extends BaseController{


    private @Autowired MarkPackageService markPackageService;


    /**
     * @author hang.yu
     * @api {POST} /mark/phone 运单号查询手机号
     * @apiVersion 0.4.0
     * @apiDescription 运单号查询手机号
     * @apiGroup 标记
     * @apiParam {Long} memberId  快递员id
     * @apiParam {String} waybill 运单号
     *
     * @apiSampleRequest /mark/phone
     * @apiSuccessExample Success-Response:
    {
    "data": "1xxxxxxx",
    "success":true
    }
     * @apiErrorExample Error-Response:
    {
    "errCode":0,
    "errSubcode":"",
    "message":"请求失败",
    "success":false
    }
     */
    @ResponseBody
    @RequestMapping(value = "/phone")
    public String getPhone(Long memberId, @RequestParam(value = "waybill") String wayBill) {
        if (PubMethod.isEmpty(wayBill)) {
            return paramsFailure("001", "invalid params");
        }
        if (PubMethod.isEmpty(memberId)) {
            return paramsFailure("002", "invalid params");
        }
        return markPackageService.getPhoneByOrderNo(memberId, wayBill);
    }

    /**
     * @author hang.yu
     * @api {POST} /mark/add 添加标记(新需求)
     * @apiVersion 0.4.0
     * @apiDescription 添加标记，新需求改的是首页的标记, 待派中标记没有变
     * @apiGroup 标记
     * @apiParam {Long} netid 快递公司id
     * @apiParam {Long} compid 所属站点/代收点id
     * @apiParam {String} waybill 单号
     * @apiParam {String} phone 收件人手机号
     * @apiParam {String} content 标记内容
     * @apiParam {Long} mid 当前登录人id
     * @apiParam {String} source 1: 首页标记  2: 待派中标记
     * @apiParam {Long} parid 包裹id source为2时不能传空, source为1时可传0
     * @apiParam {String} addrname 收件人姓名
     * @apiParam {String} addr 收件人地址
     * @apiSuccess {List} recommandParcels 需要选择标记的包裹, 一旦手动选定了包裹, source必须是2，将视为从待派中标记
     * @apiSuccess {Integer} type: 4: 单号查出了包裹, 但是手机号和标记填写的手机号不一致 5: 只根据手机号查出了包裹 6：正常标记
     * @apiSampleRequest /mark/add
     * @apiSuccessExample Success-Response:
    {
    "data": {
        "type": 4,
        "recommandParcels":[
            {
                "parcelId": 111,        --包裹id， 选中这个包裹后要回传给服务端, 注意这时候接口的source字段是2
                "phone": "17000000000", -- 收件人手机号
                "parNum": "h12"         -- 取件码
            }
        ]
    },
    "success":true
    }
     * @apiErrorExample Error-Response:
    {
    "errCode":0,
    "errSubcode":"",
    "message":"请求失败",
    "success":false
    }
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMark(@RequestParam("netid") Long netId, @RequestParam("compid") Long compId,
                          @RequestParam("waybill") String wayBill, String phone,
                          String content, @RequestParam("mid") Long memberId, String source,
                          @RequestParam("parid") Long parId, @RequestParam("addrname") String addrName, String addr) {

        if (!"12".contains(source)) {
            return paramsFailure("001", "invalid ops");
        }
        if ("2".equals(source) && PubMethod.isEmpty(parId)) {
            return paramsFailure("003", "invalid params");
        }
//        if (PubMethod.isEmpty(wayBill)) {
//            return paramsFailure("002", "invalid params");
//        }
        if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(memberId)) {
            return paramsFailure("003", "invalid params");
        }
        return markPackageService.addMark(netId, compId, wayBill, phone, content, memberId, source, parId, addrName, addr);
    }

    /**
     * @author hang.yu
     * @api {POST} /mark/addpar 添加标记-取消新建包裹
     * @apiVersion 0.4.0
     * @apiDescription 添加标记，取消新建包裹
     * @apiGroup 标记
     * @apiParam {Long} netid 快递公司id
     * @apiParam {Long} compid 所属站点/代收点id
     * @apiParam {String} waybill 单号
     * @apiParam {String} phone 收件人手机号
     * @apiParam {String} content 标记内容
     * @apiParam {Long} mid 当前登录人id
     * @apiParam {Long} parid 包裹id  没有可不传
     * @apiParam {String} addrname 收件人姓名
     * @apiParam {String} addr 收件人地址
     * @apiSampleRequest /mark/addpar
     * @apiSuccessExample Success-Response:
    {
        success: true
    }
     * @apiErrorExample Error-Response:
    {
    "errCode":0,
    "errSubcode":"",
    "message":"请求失败",
    "success":false
    }
     */
    @ResponseBody
    @RequestMapping(value = "/addpar", method = RequestMethod.POST)
    public String addMarkAndParcel(@RequestParam("netid") Long netId, @RequestParam("compid") Long compId,
                          @RequestParam("waybill") String wayBill, String phone,
                          String content, @RequestParam("mid") Long memberId,
                          @RequestParam("parid") Long parId, @RequestParam("addrname") String addrName, String addr) {

        return markPackageService.addMarkAndParcel(netId, compId, wayBill, phone, content, memberId, parId, addrName, addr);
    }



    /**
     * @author hang.yu
     * @api {POST} /mark/list 派件异常列表
     * @apiVersion 0.4.0
     * @apiDescription 派件异常列表
     * @apiGroup 标记
     * @apiParam {Long} compid 所属站点/代收点id(切换站点内全部成员时, mid传0)
     * @apiParam {Long} mid 当前登录人id(切换站点内其他成员时，mid换成成员id就行)
     * @apiParam {Integer} page 当前页码
     *
     * @apiSampleRequest /mark/list
     * @apiSuccessExample Success-Response:
    {
    "data": [
        "parId": 111,               --包裹id
        "taskId": 222,              --任务id
        "netName": "百世快递",        --快递公司
        "wayBill": "123456789",     --单号
        "code": "123456789",        --取件码
        "phone": "123456789",       --手机号
        "content": "xxxx",          --标记内容
        "addressName": ""           --收件人姓名
        "addressAddrss": "",        --地址
        "ali": 0,                   --是否阿里大于包裹 0否1是
        "time": 1111,               --标记时间
        "parcelSrc": 1              --1: 代发 10: 待派
    ]
    "success":true
    }
     * @apiErrorExample Error-Response:
    {
    "errCode":0,
    "errSubcode":"",
    "message":"请求失败",
    "success":false
    }
     */
    @ResponseBody
    @RequestMapping("list")
    public String getAssignExceptionList(@RequestParam(value = "compid") Long compId,
                                         @RequestParam("mid") Long memberId, Integer page) {

        if (PubMethod.isEmpty(page) || PubMethod.isEmpty(compId)) {
            return paramsFailure("001", "invalid params");
        }

        if (page < 1) {
            page = 1;
        }
        return markPackageService.findExcepList(compId, memberId, page);
    }

    /**
     * @author hang.yu
     * @api {POST} /mark/search 搜索手机号/单号
     * @apiVersion 0.4.0
     * @apiDescription 搜索手机号/单号
     * @apiGroup 标记
     * @apiParam {Long} compid 所属站点/代收点id
     * @apiParam {Long} mid 当前登录人id
     * @apiParam {String} word 手机号/单号
     *
     * @apiSampleRequest /mark/list
     * @apiSuccessExample Success-Response:
    {
    "data": [
        "parId": 111,               --包裹id
        "netName": "百世快递",        --快递公司
        "wayBill": "123456789",     --单号
        "code": "123456789",        --取件码
        "phone": "123456789",       --手机号
        "content": "xxxx",          --标记内容
        "time": 1111,               --标记时间
        "parcelSrc": 1              --1: 代发 10: 待派
    ]
    "success":true
    }
     * @apiErrorExample Error-Response:
    {
    "errCode":0,
    "errSubcode":"",
    "message":"请求失败",
    "success":false
    }
     */
    @ResponseBody
    @RequestMapping("/search")
    public String searchByPhoneOrOrderNo(@RequestParam("compid") Long compId,
                                         @RequestParam("mid") Long memberId,
                                         @RequestParam("word") String orderNoOrPhone, Integer type) {

        if (PubMethod.isEmpty(compId) || PubMethod.isEmpty(orderNoOrPhone)) {
            return paramsFailure("001", "invalid params");
        }
        return markPackageService.searchByPhoneOrOrderNo(compId, memberId, orderNoOrPhone, type);
    }

    /**
     * @author hang.yu
     * @api {POST} /mark/content 标记内容
     * @apiVersion 0.4.0
     * @apiDescription 标记内容
     * @apiGroup 标记
     * @apiParam {Long} netId 网络id
     *
     * @apiSampleRequest /mark/content
     * @apiSuccessExample Success-Response:
    {
    "data": [
    "id": 111,
    "netId": 123,
    "netName": "百世快递",        --快递公司
    "signContent": "123456789"  --标记的内容
    ]
    "success":true
    }
     * @apiErrorExample Error-Response:
    {
    "errCode":0,
    "errSubcode":"",
    "message":"请求失败",
    "success":false
    }
     */
    @ResponseBody
    @RequestMapping("/content")
    public String markContent(Long netId) {
        if (PubMethod.isEmpty(netId)) {
            netId = -1L;
        }
        return markPackageService.findMarkContent(netId);
    }

    /**
     * @author hang.yu
     * @api {POST} /mark/content/his 包裹历史标记
     * @apiVersion 0.4.0
     * @apiDescription 包裹历史标记
     * @apiGroup 标记
     * @apiParam {String} waybill 单号
     *
     * @apiSampleRequest /mark/content/his
     * @apiSuccessExample Success-Response:
    {
    "data": [
        {
            "name": "标记人",
            "content": "标记内容",
            "time": 1111            --标记时间
        }
    ]
    "success":true
    }
     * @apiErrorExample Error-Response:
    {
    "errCode":0,
    "errSubcode":"",
    "message":"请求失败",
    "success":false
    }
     */
    @ResponseBody
    @RequestMapping("/content/his")
    public String markContentHisByWaybill(@RequestParam("waybill") String wayBill) {
        if (PubMethod.isEmpty(wayBill)) {
            return paramsFailure("001", "invalid params");
        }
        return markPackageService.getParcelMarkHistoryByWaybill(wayBill);
    }


}









