/**  
 * @Project: openapi
 * @Title: VO_BroadcastInfo.java
 * @Package net.okdi.api.vo
 * @author amssy
 * @date 2015-1-14 上午11:24:31
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.vo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.okdi.api.entity.RobBroadcastInfo;
import net.okdi.core.util.PubMethod;

import com.amssy.common.util.primarykey.IdWorker;

/**
 * @author amssy
 * @version V1.0
 */
public class VO_BroadcastInfo extends RobBroadcastInfo{
    //json串包裹信息
	public List parcelValues;


	
	public List getParcelValues() {
		return parcelValues;
	}



	public void setParcelValues(List parcelValues) {
		this.parcelValues = parcelValues;
	}



	public RobBroadcastInfo changToRobBroadcast(){
		RobBroadcastInfo rob = new RobBroadcastInfo();
		rob.setBroadcastRemark(this.getBroadcastRemark());
		rob.setBroadcastStatus(this.getBroadcastStatus());
		rob.setBroadcastType(this.getBroadcastType());
		rob.setCreateTime(new Timestamp(new Date().getTime()));
		rob.setId(PubMethod.isEmpty(this.getId())?IdWorker.getIdWorker().nextId():this.getId());
		rob.setLoginMemberId(this.getLoginMemberId());
		rob.setPsnViewFlag(this.getPsnViewFlag());
        rob.setQuotationId(this.getQuotationId());
        rob.setSenderAddressId(this.getSenderAddressId());
        rob.setSenderAddressName(this.getSenderAddressName());
        rob.setSenderLatitude(this.getSenderLatitude());
        rob.setSenderLongitude(this.getSenderLongitude());
        rob.setSenderMobile(this.getSenderMobile());
        rob.setSenderName(this.getSenderName());
        rob.setTotalCount(this.getTotalCount());
        rob.setTotalWeight(this.getTotalWeight());
        rob.setAddresseeAddress(this.getAddresseeAddress());
		return rob;
	}
	
}
