package net.okdi.api.entity;

public class ExpCustomerContactsInfo {
    private Long id;

	private Long customerId;
	private String customerName;
	
	private String contactsName;

	private String contactsNameSpell;

	private String contactsPhone;
	
	private String isRegist;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsNameSpell() {
		return contactsNameSpell;
	}

	public void setContactsNameSpell(String contactsNameSpell) {
		this.contactsNameSpell = contactsNameSpell;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIsRegist() {
		return isRegist;
	}

	public void setIsRegist(String isRegist) {
		this.isRegist = isRegist;
	}

	
}