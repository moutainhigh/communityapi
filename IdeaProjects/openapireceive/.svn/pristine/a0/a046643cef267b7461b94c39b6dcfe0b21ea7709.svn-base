package net.okdi.api.service.impl;

import java.util.List;

import net.okdi.api.dao.BasCompInfoMapper;
import net.okdi.api.dao.BasExpressPriceMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.DicAddressaidMapper;
import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasExpressPrice;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.service.InitCacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 初始化缓存数据接口实现类
 * 
 * @ClassName InitCacheServiceImpl
 * @author feng.wang
 * @date 2014-10-30
 * @since jdk1.6
 */
@Service("initCacheService")
public class InitCacheServiceImpl implements InitCacheService {

	@Autowired
	private BasCompInfoMapper compInfoMapper;
	@Autowired
	private BasNetInfoMapper basNetInfoMapper;
	@Autowired
	private DicAddressaidMapper dicAddressaidMapper;
	@Autowired
	private BasExpressPriceMapper basExpressPriceMapper;

	/**
	 * 
	 * @Description: 查询所有公司信息
	 * @author feng.wang
	 * @date 2014-10-30下午17:34:42
	 * @return
	 */
	@Override
	public List<BasCompInfo> getCompList() {
		return compInfoMapper.getCompList();
	}

	/**
	 * 
	 * @Description: 查询所有网络信息信息
	 * @author feng.wang
	 * @date 2014-10-31下午10:15:42
	 * @return
	 */
	@Override
	public List<BasNetInfo> getNetList() {
		return this.basNetInfoMapper.queryNetInfo();
	}

	/**
	 * 
	 * @Description: 查询5级以上的地址信息
	 * @author feng.wang
	 * @date 2014-11-28 下午13:15:42
	 * @return
	 */
	@Override
	public List<DicAddressaid> getAddressList() {
		return this.dicAddressaidMapper.getAddressList();
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询所有网络报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>shihe.zhai</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-8 下午4:27:27</dd>
	 * @return List<BasExpressPrice>
	 * @since v1.0
	 */
	@Override
	public List<BasExpressPrice> queryExpressPrice(){
		return this.basExpressPriceMapper.queryExpressPrice();
	}
}
