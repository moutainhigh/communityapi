package net.okdi.mob.vo;
import java.util.Map;

public class WechatBoundResult {

	private Map<String, String> data;
	
	private boolean success;

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
}

