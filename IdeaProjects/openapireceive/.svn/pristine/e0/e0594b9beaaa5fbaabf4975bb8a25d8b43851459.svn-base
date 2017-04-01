package net.okdi.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import net.okdi.api.entity.BilTemplateSet;
import net.okdi.api.service.BilTemplateSetService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

/**
 * 
 * @author dong.zhang
 * @version V1.0
 */
@Controller
@RequestMapping("/bilTemplate")
public class BilTemplateSetController extends BaseController{
	@Autowired
	private BilTemplateSetService bilTemplateSetService;
	@Autowired
	private EhcacheService ehcacheService;
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王保存短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:14:09</dd>
	 * @param templateType '模板类型 00:短信平台短信模板,01:收派员系统的短信模板'
	 * @param compId '公司ID'
	 * @param memberId '收派员ID'
	 * @param accountId 短信账户ID
	 * @param templateContent 短信内容
	 * @param templateSign 短信签名
	 * @param createUser 创建人
	 * @param deleteMark 删除标识 0:删除,1:正常'
	 * @return	JSON success：true 保存成功 false 保存失败
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.BilTemplateSetController.insert.001 -  compId为必填项 </dd>
     * <dd>openapi.BilTemplateSetController.insert.002 -  memberId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/insert", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String insert(String templateType,Long compId,Long memberId,Long accountId,
			String templateContent,String templateSign,Long createUser,
			Short deleteMark){
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("BilTemplateSetController.insert.001","compId为必填项");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("BilTemplateSetController.insert.002","memberId为必填项");
		}
		try {
				bilTemplateSetService.insert(templateType,compId,memberId,accountId,
					templateContent,templateSign,createUser,deleteMark);
				List<String> keyList=this.ehcacheService.get("bilTemplateSetCache",memberId+"", List.class);
				if(!PubMethod.isEmpty(keyList)){
					this.ehcacheService.remove("bilTemplateSetCache", keyList);
				}
				return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
		
	}
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王更新短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:15:15</dd>
	 * @param id
	 * @param templateType '模板类型 00:短信平台短信模板,01:收派员系统的短信模板'
	 * @param compId '公司ID'
	 * @param memberId '收派员ID'
	 * @param accountId 短信账户ID
	 * @param templateContent 短信内容
	 * @param templateSign 短信签名
	 * @param createUser 创建人
	 * @param deleteMark 删除标识 0:删除,1:正常'
	 * @return  JSON success：true 保存成功 false 保存失败
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.BilTemplateSetController.update.009 -  Id为必填项 </dd>
     * <dd>openapi.BilTemplateSetController.update.003 -  compId为必填项 </dd>
     * <dd>openapi.BilTemplateSetController.update.004 -  memberId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String update(Long id,String templateType,Long compId,Long memberId,Long accountId,
			String templateContent,String templateSign,Long createUser,
			Short deleteMark){
		if(PubMethod.isEmpty(id)){
			return paramsFailure("BilTemplateSetController.update.009","Id为必填项");
        }
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("BilTemplateSetController.update.003","compId为必填项");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("BilTemplateSetController.update.004","memberId为必填项");
		}
		try {
					bilTemplateSetService.update(id,templateType,compId,memberId,accountId,
					templateContent,templateSign,createUser,deleteMark);
					List<String> keyList=this.ehcacheService.get("bilTemplateSetCache",memberId+"", List.class);
					if(!PubMethod.isEmpty(keyList)){
						this.ehcacheService.remove("bilTemplateSetCache", keyList);
					}
					return this.jsonSuccess(null);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王删除短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:16:19</dd>
	 * @param id
	 * @param memberId	用户ID
	 * @return	true 删除成功 false 删除失败
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.BilTemplateSetController.delete.005 -  Id为必填项 </dd>
     * <dd>openapi.BilTemplateSetController.delete.006 -  memberId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String delete(Long id,Long memberId){
		if(PubMethod.isEmpty(id)){
			return paramsFailure("BilTemplateSetController.delete.005","id为必填项");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("BilTemplateSetController.delete.006","memberId为必填项");
		}
		try{
		bilTemplateSetService.delet(id,memberId);
		List<String> keyList=this.ehcacheService.get("bilTemplateSetCache",memberId+"", List.class);
		if(!PubMethod.isEmpty(keyList)){
			this.ehcacheService.remove("bilTemplateSetCache", keyList);
		}
		return this.jsonSuccess(null);
	} catch (Exception e) {
		return this.jsonFailure(e);
	}
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王查询短信模板列表（不分页）</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>dong.zhang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午3:17:19</dd>
	 * @param compId	公司ID
	 * @param memberId	 收派员ID
	 * @return	{"data":[{"accountId":89,---短信账户ID
	 * "compId":123456,---公司ID
	 * "createTime":"Mon Nov 24 20:29:3",----创建时间
	 * "createUser":89,-----创建人
	 * "deleteMark":1,----删除标识 0:删除,1:正常
	 * "id":14438480443867136,----主键
	 * "memberId":4646,-----'收派员ID'
	 * "templateContent":"好看",-------短信内容
	 * "templateSign":"gaga",----短信签名
	 * "templateType":"00"---'模板类型 00:短信平台短信模板,01:收派员系统的短信模板'
	 * }],"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>openapi.BilTemplateSetController.list.007 -  memberId为必填项 </dd>
     * <dd>openapi.BilTemplateSetController.list.008 -  compId为必填项 </dd>
	 * @since v1.0
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String list(Long compId,Long memberId){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("BilTemplateSetController.list.007","memberId为必填项");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("BilTemplateSetController.list.008","compId为必填项");
		}
		List list=null;
		try{
			List keyList=null;
			String myKey=compId+"_"+memberId;
			list=this.ehcacheService.get("bilTemplateSetCache",myKey, List.class);
			if(PubMethod.isEmpty(list)){
			list = bilTemplateSetService.list(compId, memberId);
			this.ehcacheService.put("bilTemplateSetCache",myKey,list);
			keyList=this.ehcacheService.get("bilTemplateSetCache", memberId+"",List.class);
			if(PubMethod.isEmpty(keyList)){
				keyList=new ArrayList();
			}
			keyList.add(myKey);
			this.ehcacheService.put("bilTemplateSetCache",memberId+"",keyList);
			}
			return this.jsonSuccess(list);
		} catch (Exception e) {
			return this.jsonFailure(e);
		}
		
	}
}
