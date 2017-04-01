package net.okdi.api.service;

import java.util.List;

import net.okdi.api.entity.SmsTempleAudit;
import net.okdi.core.common.page.Page;

public interface SmsTempleAuditService {

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>上传短信模板</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:06:24</dd>
	 * @param memberId		收派员Id
	 * @param compId		站点id
	 * @param compName		站点名称
	 * @param memberPhone	收派员手机号码
	 * @param realName		真实姓名
	 * @return		
	 */
	String upLoadSmsTemple(Long memberId,Long compId,String compName,String memberPhone,String realName,String templeContent);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:07:52</dd>
	 * @param memberId		收派员id
	 * @return
	 */
	List<SmsTempleAudit> findSmsTemple(Long memberId);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>编辑短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:15:08</dd>
	 * @param id			短信模板id
	 * @param smsContent	短信模板内容
	 * @return
	 */
	String editSmsTemple(Long id,String smsContent);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>删除短信模板</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:26:12</dd>
	 * @param id			短信模板id
	 * @return
	 */
	String deleteSmsTemple(Long id);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询短信模板内容</dd>
	 * @param id		短信模板id
	 * @return
	 */
	SmsTempleAudit findSmsTempleById(Long id);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd><strong>运营平台</strong>审核短信模板</dd>
	 * @param id			短信模板id
	 * @param status		审核状态 0:待审核 1:通过 2:驳回
	 * @return
	 */
	String auditSmsTemple(Long id, Short status);
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd><strong>运营平台</strong>查询短信模板</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:35:37</dd>
	 * @param phone			手机号
	 * @param realName		真实姓名(模糊查询)
	 * @param compName		站点姓名(模糊查询)
	 * @param auditCount	认证次数
	 * @param auditStatus	认证的状态
	 * @return
	 */
	Page findSmsTemplePT(String phone,String realName,String compName,String auditCount,Short auditStatus, String startTime,String endTime,Integer currentPage, Integer pageSize);
	
}
