package net.okdi.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.okdi.api.service.ExpressTaskService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 该类的主要作用是通知取件的功能，包括创建任务，取消任务，增加包裹
 * @author ccs
 * @version V1.0
 */
@Controller
@RequestMapping("/okdiTask")
public class ExpressTaskController extends BaseController {
	
	@Autowired 
	ExpressTaskService expressTaskService;


	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商管家通过电话通知取件</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-13 下午6:30:13</dd>
	 * @param phone			收派员手机号
	 * @param address		发件人地址
	 * @param packageNum	包裹数量	
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发件人电话
	 * @param senderName	发件人姓名
	 * @param erpid			电商id
	 * @param allsms		0 全部发短信 1 未注册收派员发短信，注册收派员发推送（存在推送不成功的情况）
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/createTaskByPhone", method = { RequestMethod.POST })
	public String createTaskByPhone(HttpServletRequest request, String phone, String address, String packagenum,
			String packageweight, String sendermobile, String sendername, String erpid, String allsms,String takeTime) {
		try {
			if (PubMethod.isEmpty(phone)) {
				return PubMethod.paramError("err.007", "phone(收派员手机号) 不能为空!");
			}
			if (PubMethod.isEmpty(address)) {
				return PubMethod.paramError("err.007", "address(发件人地址) 不能为空!");
			}
//			if (PubMethod.isEmpty(packagenum)) {
//				return PubMethod.paramError("err.007", "packagenum(包裹数量) 不能为空!");
//			}
//			if (PubMethod.isEmpty(packageweight)) {
//				return PubMethod.paramError("err.007", "packageweight(包裹重量) 不能为空!");
//			}
			if (PubMethod.isEmpty(sendermobile)) {
				return PubMethod.paramError("err.007", "sendermobile(发件人电话) 不能为空!");
			}
			if (PubMethod.isEmpty(sendername)) {
				return PubMethod.paramError("err.007", "sendername(发件人姓名) 不能为空!");
			}
			if (PubMethod.isEmpty(allsms)) {
				return PubMethod.paramError("err.007", "allsms(是否全短信模式) 不能为空!");
			}
			return expressTaskService.createTaskSimple("0", "124520911659008", phone, address, "0",
					"0", sendermobile, sendername,erpid,allsms,takeTime);//贺海峰修改1020 关闭oath之后auth_member_id无法获取
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商管家通过compid通知取件</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-13 下午6:30:13</dd>
	 * @param compid		站点Id
	 * @param address		发件人地址
	 * @param packageNum	包裹数量	
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发件人电话
	 * @param senderName	发件人姓名
	 * @param erpid			电商id
	 * @param allsms		0 全部发短信 1 未注册收派员发短信，注册收派员发推送（存在推送不成功的情况）
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/createTaskByCompId", method = { RequestMethod.POST })
	public String createTaskByCompId(HttpServletRequest request, String compid, String address, String packagenum,
			String packageweight, String sendermobile, String sendername, String erpid, String allsms) {
		try {
			if (PubMethod.isEmpty(compid)) {
				return PubMethod.paramError("err.007", "phone(收派员手机号) 不能为空!");
			}
			if (PubMethod.isEmpty(address)) {
				return PubMethod.paramError("err.007", "address(发件人地址) 不能为空!");
			}
			if (PubMethod.isEmpty(packagenum)) {
				return PubMethod.paramError("err.007", "packagenum(包裹数量) 不能为空!");
			}
			if (PubMethod.isEmpty(packageweight)) {
				return PubMethod.paramError("err.007", "packageweight(包裹重量) 不能为空!");
			}
			if (PubMethod.isEmpty(sendermobile)) {
				return PubMethod.paramError("err.007", "sendermobile(发件人电话) 不能为空!");
			}
			if (PubMethod.isEmpty(sendername)) {
				return PubMethod.paramError("err.007", "sendername(发件人姓名) 不能为空!");
			}
			if (PubMethod.isEmpty(allsms)) {
				return PubMethod.paramError("err.007", "allsms(短信标记) 不能为空!");
			}
			return expressTaskService.createTaskSimple("1", this.getMemberId(request), compid, address, packagenum,
					packageweight, sendermobile, sendername,erpid,allsms,null);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>电商管家根据memberId通知取件</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-13 下午6:30:13</dd>
	 * @param memberid		收派员Id
	 * @param address		发件人地址
	 * @param packageNum	包裹数量	
	 * @param packageWeight	包裹重量
	 * @param senderMobile	发件人电话
	 * @param senderName	发件人姓名
	 * @param erpid			电商id
	 * @param allsms		0 全部发短信 1 未注册收派员发短信，注册收派员发推送（存在推送不成功的情况）
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/createTaskByMemberId", method = { RequestMethod.POST })
	public String createTaskByMemberId(HttpServletRequest request, String memberid, String address, String packagenum,
			String packageweight, String sendermobile, String sendername, String erpid, String allsms) {
		try {
			if (PubMethod.isEmpty(memberid)) {
				return PubMethod.paramError("err.007", "memberid(收派员memberId) 不能为空!");
			}
			if (PubMethod.isEmpty(address)) {
				return PubMethod.paramError("err.007", "address(发件人地址) 不能为空!");
			}
			if (PubMethod.isEmpty(packagenum)) {
				return PubMethod.paramError("err.007", "packagenum(包裹数量) 不能为空!");
			}
			if (PubMethod.isEmpty(packageweight)) {
				return PubMethod.paramError("err.007", "packageweight(包裹重量) 不能为空!");
			}
			if (PubMethod.isEmpty(sendermobile)) {
				return PubMethod.paramError("err.007", "sendermobile(发件人电话) 不能为空!");
			}
			if (PubMethod.isEmpty(sendername)) {
				return PubMethod.paramError("err.007", "sendername(发件人姓名) 不能为空!");
			}
			if (PubMethod.isEmpty(sendername)) {
				return PubMethod.paramError("err.007", "sendername(发件人姓名) 不能为空!");
			}
			if (PubMethod.isEmpty(allsms)) {
				return PubMethod.paramError("err.007", "sendername(发件人姓名) 不能为空!");
			}
			return expressTaskService.createTaskSimple("2", this.getMemberId(request), memberid, address, packagenum,
					packageweight, sendermobile, sendername,erpid,allsms,null);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>取消任务</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-13 下午7:51:58</dd>
	 * @param taskId	任务Id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelTask", method = { RequestMethod.POST })
	public String cancelTask(HttpServletRequest request, String taskid) {
		try {
			if (PubMethod.isEmpty(taskid)) {
				return PubMethod.paramError("err.007", "taskId(任务Id) 不能为空!");
			}
			return expressTaskService.cancelTask(this.getMemberId(request), taskid);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
	}

	public String addParInfo(HttpServletRequest request, String taskid) {
		try {
			if (PubMethod.isEmpty(taskid)) {
				return PubMethod.paramError("err.007", "taskId(任务Id) 不能为空!");
			}
			return expressTaskService.cancelTask(this.getMemberId(request), taskid);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加包裹信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 上午10:22:38</dd>
	 * @param taskid				任务id
	 * @param expwaybillnum			运单号
	 * @param receivername			收件人姓名
	 * @param receivermobile		收件人手机号码
	 * @param receiveraddressid		收件人区县id
	 * @param receiveraddress		收件人详细地址
	 * @param sendname				发件人姓名
	 * @param sendmobile			发件人手机
	 * @param sendaddressid			发件人区县id
	 * @param sendaddress			发件人详细地址
	 * @param parweight				包裹重量
	 * @param freight				包裹应收运费
	 * @param iscod					是货到付款
	 * @param codamount				代收货款金额
	 * @param insureamount			保价金额
	 * @param pricepremium			保价费
	 * @param packingcharges		包装费
	 * @param freightpaymentmethod	运费支付方式
	 * @param goodsdesc				产品描述
	 * @param parcelremark			包裹备注
	 * @param serviceid				服务产品ID
	 * @param actualtakemember		收派员id
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/addParInfo", method = { RequestMethod.POST })
	public String saveParcelInfo(HttpServletRequest request,
			String taskid,String expwaybillnum,String receivername,String receivermobile,
			String receiveraddressId,String receiveraddress,String sendname,String sendmobile,String sendaddressid,String sendaddress,String parweight,String freight,String iscod,
			String codamount,String insureamount,String pricepremium,String packingcharges,String freightpaymentmethod,String goodsdesc,String parcelremark,String serviceid,
			String actualtakemember) {
		try {
			if (PubMethod.isEmpty(taskid)) {
				return PubMethod.paramError("err.007", "taskId(任务Id) 不能为空!");
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put(expwaybillnum,"expwaybillnum(运单号)");
			map.put(receivername,"receivername(收件人姓名)");
			map.put(receivermobile,"receivermobile(收件人手机号码)");
			map.put(sendname,"sendname(发件人姓名)");
			map.put(sendmobile,"sendmobile(发件人手机)");
			map.put(sendaddress,"sendaddress(发件人详细地址)");
			map.put(parweight,"parweight(包裹重量)");
			map.put(iscod,"iscod(是货到付款)");
			map.put(freightpaymentmethod,"freightpaymentmethod(运费支付方式)");
			String result = valEmpty(map);
			if(!"success".equals(result)){
				return result;
			}
			return expressTaskService.saveParcelInfo(taskid, expwaybillnum, receivername, receivermobile, receiveraddressId, receiveraddress, sendname, sendmobile, sendaddressid, sendaddress, parweight, freight, iscod, codamount, insureamount, pricepremium, packingcharges, freightpaymentmethod, goodsdesc, parcelremark, serviceid, actualtakemember);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
	}	
	
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>简化空值处理，统一操作</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 下午1:58:18</dd>
	 * @param map	对应的字段和提示值
	 * @return
	 * @since v1.0
	 */
	private static String valEmpty(Map<String,String> map){
		Iterator<String> its = map.keySet().iterator();
		while(its.hasNext()){
			String str = its.next();
			if(PubMethod.isEmpty(str)){
				return PubMethod.paramError("err.007", map.get(str)+" 不能为空!");
			}
		}
		return "success";
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>转换String到Long,BigDecimal,Short,忽略掉其他类型和出现异常的情况</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chuanshi.chai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-22 上午10:12:50</dd>
	 * @param str	需要转换的字符串
	 * @param t		类型类一般为Long.class，Short.class,BigDecimal.class
	 * @return
	 * @since v1.0
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getValFromStr(String str, Class<T> t) {
		if (PubMethod.isEmpty(str))
			return null;
		try {
			if (t == BigDecimal.class) {
				return (T)BigDecimal.valueOf(Double.valueOf(str));
			} else if (t == Long.class) {
				return (T)Long.valueOf(str);
			} else if (t == Short.class) {
				return (T)Short.valueOf(str);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	
	
	/**此方法测试本地小工具方法**/
	public static void main(String[] args) {
       String name = "张梦楠";
       System.out.println(name.hashCode());
	}
	
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>增加电商引流信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * @param erpJsonInfo		引流JSON格式
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/saveExpOftenData", method = { RequestMethod.POST })
	public String saveExpOftenData(String erpJsonInfo) {
		return expressTaskService.saveExpOftenData(erpJsonInfo);
	}

	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>修改电商引流信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * @param processJsonInfo		引流JSON格式
	 * @return
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/updateArticleProcessData", method = { RequestMethod.POST })
	public String updateArticleProcessData(String processJsonInfo) {
		return expressTaskService.updateArticleProcessData(processJsonInfo);
	}
	
}