package net.okdi.outersrv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.outersrv.service.CooperationCompanyNetService;
import net.okdi.outersrv.service.CooperationCompanyService;
import net.okdi.outersrv.util.CoopCompServiceFactory;
import net.okdi.outersrv.vo.ParcelEntireInfo;

@Service
public class CooperationCompanyServiceImpl implements CooperationCompanyService, CooperationCompanyNetService{

	@Autowired
	private BasNetInfoMapper basNetInfoMapper;
	
	@Override
	public String openSignParcel(Long netId, ParcelEntireInfo parcelEntireInfo) {
		// TODO Auto-generated method stub
		
		CooperationCompanyService targetService = getService(netId);
		if (targetService == null) {
			return null;
		}
		targetService.openSignParcel(netId, parcelEntireInfo);
		return null;
	}

	@Override
	public String queryPackage(Long netId, ParcelEntireInfo parcelEntireInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasNetInfo getNetAuthInfoByNetId(Long netId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CooperationCompanyService getService(Long netId) {
		if (netId == null) {
			return null;
		}
		return CoopCompServiceFactory.getService(netId);
	}

	@Override
	public Long getNetId() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
