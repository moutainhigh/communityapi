package net.okdi.outersrv.service;

import net.okdi.api.entity.BasNetInfo;

public interface CooperationCompanyNetService {

    BasNetInfo getNetAuthInfoByNetId(Long netId);

}
