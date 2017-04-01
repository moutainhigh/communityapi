package net.okdi.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.okdi.api.dao.AccSrecpayvouchersMapper;
import net.okdi.api.dao.BasExpressPriceMapper;
import net.okdi.api.dao.BasNetInfoMapper;
import net.okdi.api.dao.DicAddressaidMapper;
import net.okdi.api.entity.AccSrecpayvouchers;
import net.okdi.api.entity.BasExpressPrice;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.service.ExpressPriceService;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.ParcelInfoService;
import net.okdi.core.base.BaseDao;
import net.okdi.core.base.BaseServiceImpl;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.VerifyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

/**
 * 
 * @Description: 网络信息
 * @author 翟士贺
 * @date 2014-10-18下午3:13:31
 */
@Service
public class ExpressPriceServiceImpl extends BaseServiceImpl<BasExpressPrice> implements ExpressPriceService {
	@Autowired
	private DicAddressaidMapper dicAddressaidMapper;
	@Autowired
	private BasExpressPriceMapper basExpressPriceMapper;
	@Autowired
	private EhcacheService ehcacheService;
	@Autowired
	private BasNetInfoMapper netInfoMapper;
	@Autowired
	private AccSrecpayvouchersMapper accSrecpayvouchersMapper;
	@Autowired
	private ParcelInfoService parcelInfoService;
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Override
	public BaseDao getBaseDao() {
		return basExpressPriceMapper;
	}
	/**
	 * 计算网络运费
	 * @Method: getExpressPrice 
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param receiveAddressId 收件地址ID
	 * @param weight 重量
	 * @return 价格
	 * @see net.okdi.api.service.ExpressPriceService#getExpressPrice(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Double)
	 */
	@Override
	public BigDecimal getExpressPrice(Long netId,Long sendAddressId,Long receiveAddressId,Double weight){
		if (PubMethod.isEmpty(sendAddressId)) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressPrice.001", "计算运费异常,sendAddressId参数非空异常");
		}else if (PubMethod.isEmpty(netId)) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressPrice.002", "计算运费异常,netId参数非空异常");
		}else if (PubMethod.isEmpty(receiveAddressId)) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressPrice.003", "计算运费异常,receiveAddressId参数非空异常");
		}else if (PubMethod.isEmpty(weight) || weight<0) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressPrice.004", "计算运费异常,weight参数异常");
		}
		DicAddressaid takeAddress = ehcacheService.get("addressCache",sendAddressId.toString(), DicAddressaid.class);
		if(PubMethod.isEmpty(takeAddress)){
			takeAddress = this.dicAddressaidMapper.findById(sendAddressId);
		}
		if(PubMethod.isEmpty(takeAddress) || PubMethod.isEmpty(takeAddress.getProvinceId())){
			throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressPrice.005", "计算运费异常,获取发件地址异常");
		}
		DicAddressaid receviceAddress = ehcacheService.get("addressCache",receiveAddressId.toString(), DicAddressaid.class);
		if(PubMethod.isEmpty(receviceAddress)){
			receviceAddress = this.dicAddressaidMapper.findById(receiveAddressId);
		}
		if(PubMethod.isEmpty(receviceAddress) || PubMethod.isEmpty(receviceAddress.getProvinceId())){
			throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressPrice.006", "计算运费异常,获取收件地址异常");
		}
		BasNetInfo netInfo = ehcacheService.get("netCache", netId.toString(), BasNetInfo.class);
		if (PubMethod.isEmpty(netInfo)) {
			netInfo=this.netInfoMapper.findById(netId);
		}
		if(PubMethod.isEmpty(netInfo)){
			throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressPrice.007", "计算运费异常,获取网络信息异常");
		}
		Long takeProvince = takeAddress.getProvinceId();
		Long sendProvince = receviceAddress.getProvinceId();
		BasExpressPrice expressPrice = (BasExpressPrice)ehcacheService.get("expressPriceCache",netId+"-"+takeProvince+"-"+sendProvince, BasExpressPrice.class);
		if(PubMethod.isEmpty(expressPrice)){
			Map<String, Object> paras = new HashMap<String, Object>();
			 paras.put("netId", netId);
			 paras.put("takeProvince", takeProvince);
			 paras.put("sendProvince", sendProvince);
			 expressPrice=this.basExpressPriceMapper.queryExpressPriceByNetAndAddress(paras);
			 if(!PubMethod.isEmpty(expressPrice)){
				 ehcacheService.put("expressPriceCache", netId+"-"+sendAddressId+"-"+receiveAddressId, expressPrice);
			 }else{
				 return null;
			 }
		}
		if(PubMethod.isEmpty(expressPrice.getPrice())){
			return null;
		}else{
			BigDecimal price=BigDecimal.valueOf(expressPrice.getPrice());
			BigDecimal realWeight=BigDecimal.valueOf(weight);
			BigDecimal realPrice=null;
			if(realWeight.compareTo(new BigDecimal(1.00))!=1){//重量小于首重，直接返回价格
				realPrice=price;
			}else{
				realPrice=(realWeight.subtract(new BigDecimal(1.00))).divide(new BigDecimal(1.00),0,BigDecimal.ROUND_UP).multiply(price.divide(new BigDecimal(2))).add(price);
			}
			return realPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}
	/**
	 * 计算包裹总价格
	 * @Method: getExpressTotalPrice 
	 * @param netId 网络ID
	 * @param sendAddressId 发件地址ID
	 * @param weightAndreceiveAddressIds 重量、收件地址ID      weight-addressId,weight-addressId
	 * @return Map<String,BigDecimal>
	 * @see net.okdi.api.service.ExpressPriceService#getExpressTotalPrice(java.lang.String, java.lang.Long, java.lang.String)
	 */
	@Override
	public Map<String,BigDecimal> getExpressTotalPrice(Long netId,Long sendAddressId,String weightAndreceiveAddressIds){
		if (PubMethod.isEmpty(weightAndreceiveAddressIds)) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressTotalPrice.001", "计算运费异常,weightAndreceiveAddressIds参数非空异常");
		}
		Map<String,BigDecimal> priceMap = new HashMap<String,BigDecimal>();
		BigDecimal totalPrice = new BigDecimal(0.00);
		for(String weightAndreceiveAddressId : weightAndreceiveAddressIds.split(",")){
			if(PubMethod.isEmpty(weightAndreceiveAddressId)){
				continue;
			}else{
				String[] weightAndAddressId = weightAndreceiveAddressId.split("-");
				if(weightAndAddressId.length != 2){
					throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressTotalPrice.002", "计算运费异常,weightAndreceiveAddressIds参数格式异常");
				}else{
					if(PubMethod.isEmpty(weightAndAddressId[0]) || !VerifyUtil.isNumber(weightAndAddressId[0])){
						throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressTotalPrice.003", "计算运费异常,weight类型异常");
					}else if(PubMethod.isEmpty(weightAndAddressId[1]) || !VerifyUtil.isInteger(weightAndAddressId[1])){
						throw new ServiceException("openapi.ExpressPriceServiceImpl.getExpressTotalPrice.004", "计算运费异常,receiveAddressId类型异常");
					}else{
						BigDecimal price = this.getExpressPrice(netId, sendAddressId, Long.parseLong(weightAndAddressId[1]), Double.parseDouble(weightAndAddressId[0]));
						priceMap.put(weightAndreceiveAddressId, price);
						if(!PubMethod.isEmpty(price)){
							totalPrice.add(price);
						}
					}
				}
			}
		}
		priceMap.put("totalPrice", totalPrice);
		return priceMap;
	}
	/**
	 * 包裹收款/结算
	 * @Method: settleAccounts 
	 * @param parcelIds 包裹ID
	 * @param totalCodAmount 代收货款金额
	 * @param totalFreight 实收运费
	 * @param memberId 创建人 
	 * @param type 类型 0取件 1派件
	 * @see net.okdi.api.service.ExpressPriceService#settleAccounts(java.lang.String, double, double, java.lang.Long, short)
	 */
	@Override
	public void settleAccounts(String parcelIds,Double totalCodAmount,Double totalFreight,Long memberId,short type){
		if (PubMethod.isEmpty(parcelIds)) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.settleAccounts.001", "包裹收款结算异常,parcelIds参数非空异常");
		}else if (PubMethod.isEmpty(memberId)) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.settleAccounts.002", "包裹收款结算异常,memberId参数非空异常");
		}else if (type!=0 && type!=1) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.settleAccounts.003", "包裹收款结算异常,type参数异常");
		}else if (type == 0 && totalCodAmount!=null) {
			throw new ServiceException("openapi.ExpressPriceServiceImpl.settleAccounts.004", "包裹收款结算异常,费用异常");
		}
		AccSrecpayvouchers accSrecpayvouchers = new AccSrecpayvouchers();
		accSrecpayvouchers.setId(IdWorker.getIdWorker().nextId());
		int count=0;
		Map<String,Object> memberMap = this.memberInfoService.findMemberInfoFromAudit(memberId);
		for(String parcelId:parcelIds.split(",")){
			if(PubMethod.isEmpty(parcelId)){
				continue;
			}else if(!VerifyUtil.isInteger(parcelId)){
				throw new ServiceException("openapi.ExpressPriceServiceImpl.settleAccounts.005", "包裹收款结算异常,parcelId异常");
			}else{
				this.parcelInfoService.updateParcelSettleAccounts(Long.parseLong(parcelId),totalCodAmount,totalFreight, accSrecpayvouchers.getId(), memberId, type);
				count++;
			}
		}
		this.setAccSrecpayvouchers(accSrecpayvouchers, totalCodAmount, totalFreight, count, memberId,String.valueOf(memberMap.get("memberName")),PubMethod.isEmpty(memberMap.get("compId")) ? null : Long.valueOf(memberMap.get("compId").toString()));
		this.accSrecpayvouchersMapper.insert(accSrecpayvouchers);
	}
	public void setAccSrecpayvouchers(AccSrecpayvouchers accSrecpayvouchers,Double totalCodAmount,Double totalFreight,int count,Long memberId,String memberName,Long compId){
		accSrecpayvouchers.setPaymentWay((short)2);
		accSrecpayvouchers.setVoucherFlag((short)0);
		accSrecpayvouchers.setBillQuantity(count);
		accSrecpayvouchers.setRecePeopleId(memberId);
		accSrecpayvouchers.setRecePeopleName(memberName);
		accSrecpayvouchers.setCompId(compId);
		accSrecpayvouchers.setCreateUserId(memberId);
		accSrecpayvouchers.setCreateTime(new Date());
		accSrecpayvouchers.setTotalCodAmount(totalCodAmount==null ? null : new BigDecimal(totalCodAmount));
		accSrecpayvouchers.setTotalFreight(totalFreight == null? null :new BigDecimal(totalFreight));
		accSrecpayvouchers.setTotalAmount(new BigDecimal(totalCodAmount == null? 0 :totalCodAmount).add(new BigDecimal(totalFreight == null? 0 :totalFreight)));
		accSrecpayvouchers.setActualAmount(new BigDecimal(totalCodAmount == null? 0 : totalCodAmount).add(new BigDecimal(totalFreight == null? 0 :totalFreight)));
		accSrecpayvouchers.setVoucherStatus((short)2);
	}
	@Override
	public String parseResult(String info) {
		// TODO Auto-generated method stub
		return null;
	}

}
