package net.okdi.api.vo;

import java.io.Serializable;

public class VO_NetInfo implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @since jdk1.6
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private int total;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
