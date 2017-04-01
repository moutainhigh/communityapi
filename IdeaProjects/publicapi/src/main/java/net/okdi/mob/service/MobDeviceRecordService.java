package net.okdi.mob.service;

public interface MobDeviceRecordService {

	String record(Long memberId, String memberPhone, String channelNo, String regip, String deviceType,
			String deviceToken);

}
