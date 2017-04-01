package net.okdi.outersrv.service;

import net.okdi.outersrv.vo.ParcelEntireInfo;

/**
 * 同一定义所有接入快递公司接口
 * 每个接口参数 必须要有netId
 */
public interface CooperationCompanyService {

    /**
     * 国通快递接口 - 包裹签收接口
     */
    String openSignParcel(Long netId, ParcelEntireInfo parcelEntireInfo);
    /**
     * 国通快递接口 - 快递单号查询包裹接口
     * @return
     */
    String queryPackage(Long netId, ParcelEntireInfo parcelEntireInfo);

    Long getNetId();
    
}
