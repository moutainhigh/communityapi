package net.okdi.api.vo;

import java.util.List;

import net.okdi.api.entity.ExpCustomerContactsInfo;
import net.okdi.api.entity.ExpCustomerInfo;
import net.okdi.api.entity.ExpCustomerMemberRelation;

public class VO_ExpCustomerInfo extends ExpCustomerInfo {

	public VO_ExpCustomerInfo() {
	}

	public VO_ExpCustomerInfo(ExpCustomerInfo ei) {
		this.setId(ei.getId());
		this.setCompId(ei.getCompId());

		this.setCustomerType(ei.getCustomerType());

		this.setCooperativeState(ei.getCooperativeState());

		this.setCustomerName(ei.getCustomerName());

		this.setCustomerNameSpell(ei.getCustomerNameSpell());

		this.setCustomerPhone(ei.getCustomerPhone());

		this.setTownId(ei.getTownId());

		this.setTownName(ei.getTownName());

		this.setDetailedAddress(ei.getDetailedAddress());

		this.setLongitude(ei.getLongitude());

		this.setLatitude(ei.getLatitude());

		this.setErpCustomerId(ei.getErpCustomerId());

		this.setDeleteFlag(ei.getDeleteFlag());

		this.setCreateTime(ei.getCreateTime());

		this.setUpdateTime(ei.getUpdateTime());

		this.setDiscountGroupId(ei.getDiscountGroupId());

		this.setAgencyFee(ei.getAgencyFee());

		this.setSettleType(ei.getSettleType());

		this.setCasMemberId(ei.getCasMemberId());
	}

	private List<ExpCustomerContactsInfo> listContacts;
	private List<ExpCustomerMemberRelation> listExpMembers;
	private String groupName;
	
	public List<ExpCustomerContactsInfo> getListContacts() {
		return listContacts;
	}

	public void setListContacts(List<ExpCustomerContactsInfo> listContacts) {
		this.listContacts = listContacts;
	}

	public List<ExpCustomerMemberRelation> getListExpMembers() {
		return listExpMembers;
	}

	public void setListExpMembers(List<ExpCustomerMemberRelation> listExpMembers) {
		this.listExpMembers = listExpMembers;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
}
