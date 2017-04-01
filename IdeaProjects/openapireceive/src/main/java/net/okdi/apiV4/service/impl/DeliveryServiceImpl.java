package net.okdi.apiV4.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;

import net.okdi.apiV4.dao.BasEmployeeAuditMapperV4;
import net.okdi.apiV4.entity.*;
import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.apiV4.service.DeliveryService;
import net.okdi.apiV4.service.ReceivePackageService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.sms.NoticeHttpClient;
import net.okdi.core.util.PubMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BasEmployeeAuditMapperV4 basEmployeeAuditMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private AssignPackageService assignPackage;
    @Autowired
    private NoticeHttpClient noticeHttpClient;
    @Autowired
    private ReceivePackageService receivePackageService;
    @Override
    public String queryDeliveryList(String memberId, Long netId, Long compId) {
        Query query;
        if (netId == 0L) {
            query = Query.query(Criteria.where("controllerMemberId").is(Long.parseLong(memberId)).and("controllerTaskFlag").is((short) 1).and("pickupTime").gte(getThreeDayAgoDate()));
        } else {
            query = Query.query(Criteria.where("controllerMemberId").is(Long.parseLong(memberId)).and("controllerTaskFlag").is((short) 1).and("netId").is(netId).and("pickupTime").gte(getThreeDayAgoDate()));
        }
        if (compId != 0L) {
            addAllCompMember(compId, query);
        }
        List<ParParceladdress> parceladdressList = mongoTemplate.find(query.with(new Sort(Sort.Direction.DESC, "pickupTime")), ParParceladdress.class);
        JSONArray array = new JSONArray();
        for (ParParceladdress parParceladdress : parceladdressList) {
            Long uid = parParceladdress.getUid();
//            boolean isSign = mongoTemplate.exists(Query.query(Criteria.where("parId").is(uid)), ParParcelMark.class);
//            ParParcelMark findOne = this.mongoTemplate.findOne(Query.query(Criteria.where("parId").is(uid)), ParParcelMark.class);
            JSONObject obj = new JSONObject();
            obj.put("uid", uid);
            obj.put("sendName", PubMethod.isEmpty(parParceladdress.getSendName()) ? "" : parParceladdress.getSendName());
            obj.put("sendPhone", PubMethod.isEmpty(parParceladdress.getSendMobile()) ? "" : parParceladdress.getSendMobile());
            obj.put("sendAddress", PubMethod.isEmpty(parParceladdress.getSendAddress()) ? "" : parParceladdress.getSendAddress());
            obj.put("pickupTime", PubMethod.isEmpty(parParceladdress.getPickupTime()) ? "" : parParceladdress.getPickupTime());
            obj.put("isSign", PubMethod.isEmpty(parParceladdress.getPackageflag()) ? "" : parParceladdress.getPackageflag());
            obj.put("expWaybillNum", PubMethod.isEmpty(parParceladdress.getExpWaybillNum()) ? "" : parParceladdress.getExpWaybillNum());
            obj.put("netName", PubMethod.isEmpty(parParceladdress.getNetName()) ? "" : parParceladdress.getNetName());
            obj.put("code", PubMethod.isEmpty(parParceladdress.getCode()) ? "" : parParceladdress.getCode());
            array.add(obj);
        }
        JSONObject resultObj = new JSONObject();
        resultObj.put("deliveryList", array);
        LOGGER.info("queryDeliveryList() result >> {}", resultObj);
        return resultObj.toJSONString();
    }

    @Override
    public String queryNotPrintList(String memberId, Long netId, Long compId) {
        Query query;
        if (netId == 0L) {
            query = Query.query(Criteria.where("controllerMemberId").is(Long.parseLong(memberId)).and("controllerTaskFlag").in((short) 1, (short) 0).and("pickupTime").gte(getThreeDayAgoDate()));
        } else {
            query = Query.query(Criteria.where("controllerMemberId").is(Long.parseLong(memberId)).and("controllerTaskFlag").in((short) 1, (short) 0).and("netId").is(netId).and("pickupTime").gte(getThreeDayAgoDate()));
        }
        if (compId != 0L) {
            addAllCompMember(compId, query);
        }
        query.addCriteria(Criteria.where("code").ne(null));
        List<ParParceladdress> parceladdressList = mongoTemplate.find(query.with(new Sort(Sort.Direction.DESC, "pickupTime")), ParParceladdress.class);
        JSONArray array = new JSONArray();
        for (ParParceladdress parParceladdress : parceladdressList) {
            Long uid = parParceladdress.getUid();
            JSONObject obj = new JSONObject();
            obj.put("sendName", PubMethod.isEmpty(parParceladdress.getSendName()) ? "" : parParceladdress.getSendName());
            obj.put("uid", uid);
            obj.put("sendPhone", PubMethod.isEmpty(parParceladdress.getSendMobile()) ? "" : parParceladdress.getSendMobile());
            obj.put("sendAddress", PubMethod.isEmpty(parParceladdress.getSendAddress()) ? "" : parParceladdress.getSendAddress());
            obj.put("pickupTime", PubMethod.isEmpty(parParceladdress.getPickupTime()) ? "" : parParceladdress.getPickupTime());
            obj.put("isSign", PubMethod.isEmpty(parParceladdress.getPackageflag()) ? "" : parParceladdress.getPackageflag());
            obj.put("expWaybillNum", PubMethod.isEmpty(parParceladdress.getExpWaybillNum()) ? "" : parParceladdress.getExpWaybillNum());
            obj.put("netName", PubMethod.isEmpty(parParceladdress.getNetName()) ? "" : parParceladdress.getNetName());
            obj.put("code", PubMethod.isEmpty(parParceladdress.getCode()) ? "" : parParceladdress.getCode());
            array.add(obj);
        }
        JSONObject resultObj = new JSONObject();
        resultObj.put("notPrintList", array);
        LOGGER.info("queryNotPrintList() result >> {}", resultObj);
        return resultObj.toJSONString();
    }

    private void addAllCompMember(Long compId, Query query) {
        List<Long> memberIds = new ArrayList<>();
        for (MemberInfo memberInfo : assignPackage.queryMember(compId)) {   //站点下所有收派员
            memberIds.add(memberInfo.getMemberId());
        }
        query.addCriteria(Criteria.where("controllerMemberId").in(memberIds));
    }

    @Override
    public String queryDeliveryDetail(String uid) {
       
    	 JSONObject jsonObject=new JSONObject();
        try {
            ParParceladdress parceladdress = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(uid))), ParParceladdress.class);
            ParParceladdressVO vo = new ParParceladdressVO();
            vo.setUid(parceladdress.getUid());
            vo.setExpWaybillNum(parceladdress.getExpWaybillNum() == null ? "" : parceladdress.getExpWaybillNum());
            vo.setNetName(parceladdress.getNetName() == null ? "" : parceladdress.getNetName());
            vo.setSendName(parceladdress.getSendName());
            vo.setSendPhone(parceladdress.getSendMobile());
            vo.setSendAddress(parceladdress.getSendAddress());
            if (PubMethod.isEmpty(parceladdress.getAddresseeName())) parceladdress.setAddresseeName("");
            if (PubMethod.isEmpty(parceladdress.getAddresseePhone())) parceladdress.setAddresseePhone("");
            vo.setAddresseeName(parceladdress.getAddresseeName().equals("undefined") ? "" : parceladdress.getAddresseeName());
            vo.setAddresseePhone(parceladdress.getAddresseePhone().equals("undefined") ? "" : parceladdress.getAddresseePhone());
            vo.setAddresseeAddress(parceladdress.getAddresseeAddress());
            ParParcelMark mark = mongoTemplate.findOne(Query.query(Criteria.where("parId").is(parceladdress.getUid())), ParParcelMark.class);
            if (!PubMethod.isEmpty(mark)) {
                List<ContentHis> list = mark.getContent();
                if (!PubMethod.isEmpty(list)) {
                    vo.setContent(mark.getContent());
                }
            } else {
                vo.setContent(new ArrayList<ContentHis>());
            }
            vo.setParNumber(1);
            vo.setShipmentRemark(parceladdress.getShipmentRemark());
            vo.setShipmentTime(parceladdress.getShipmentTime());
            vo.setCode(parceladdress.getCode());

            jsonObject.put("data", JSONObject.toJSON(vo));
            jsonObject.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("success", false);
        }
        LOGGER.info("queryDeliveryDetail() result >> {}", jsonObject);
        return jsonObject.toJSONString();
    }

    @Override
    public String confirmDelivery(String uids, String deliveryAddress) {
        String[] ids = uids.split("-");
        for (String id : ids) {
            ParParceladdress parcel = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(id))), ParParceladdress.class);
            ParParcelconnection parcelconnection = new ParParcelconnection();
            parcelconnection.setUid(parcel.getUid());
            parcelconnection.setParId(parcel.getUid());
            parcelconnection.setNetId(parcel.getControllerNetId());
            parcelconnection.setNetName(parcel.getControllerNetName());
            parcelconnection.setCompId(parcel.getControllerCompId());
            parcelconnection.setExpMemberId(parcel.getControllerMemberId());
            parcelconnection.setCosignFlag((short) 3);
            parcelconnection.setExpMemberSuccessFlag((short) 0);
            parcelconnection.setCreateTime(new Date());
            parcelconnection.setDeliveryAddress(deliveryAddress);
            parcelconnection.setMobilePhone(PubMethod.isEmpty(parcel.getSendMobile()) ? parcel.getExpWaybillNum() : parcel.getSendMobile());
            parcelconnection.setExpWaybillNum(parcel.getExpWaybillNum());
            parcelconnection.setRecnetName("干线运输");
            parcelconnection.setRecCosignFlag((short) 1);
           // parcelconnection.setRecMemberSuccessFlag((short) 0);
            mongoTemplate.insert(parcelconnection);

            mongoTemplate.updateFirst(Query.query(Criteria.where("uid").is(Long.parseLong(id)).and("addresseeMobile").is(parcel.getAddresseeMobile())), Update.
                    update("controllerName", "干线运输"). //物权所有人 发货后 "干线运输"
//                            set("controllerMemberId", ""). //物权所有人id
//                            set("controllerNetName", ""). //物权网络名称
//                            set("controllerNetId", ""). //物权网络id
//                            set("controllerCompId", ""). //物权站点id
//                            set("controllerCompName", ""). //物权站点名称
        set("controllerTaskFlag", (short) 2).
                            set("shipmentRemark", "1").
                            set("shipmentTime", new Date()), ParParceladdress.class);
        }

        return "1";
    }

    @Override
    public String getParListByQRCode(String uids) {
        String[] ids = uids.split("-");
        JSONArray array = new JSONArray();
        for (String id : ids) {
            ParParceladdress parcel = mongoTemplate.findOne(Query.query(Criteria.where("uid").is(Long.parseLong(id))), ParParceladdress.class);
            JSONObject object = new JSONObject();
            object.put("uid", id);
            object.put("expWaybillNum", parcel.getExpWaybillNum());
            object.put("sendPhone", parcel.getAddresseePhone());
            array.add(object);
        }
        JSONObject result = new JSONObject();
        result.put("parNumber", array.size());
        result.put("parList", array);
        return result.toJSONString();
    }
    //确认扫码收货
    @Override
    public String confirmTakeDelivery(String memberId, String uids, String deliveryAddress, String index) {

//        String isClick = redisService.get("confirmTakeDelivery", memberId, String.class) + "";
//        if (isClick.equals(uids)) {
//            return "请勿重复点击";
//        }
        if (uids.equals(getRedisToken(String.class)+"")) {
            return "请等待其他人操作";
        }
//        redisService.put("confirmTakeDelivery", memberId, uids);
        putRedisToken(uids);

    	String[] ids = uids.split("-");
    	LOGGER.info("确认扫码收货的包裹id:"+uids+",memberId:"+memberId+",deliveryAddress:"+deliveryAddress+",index:"+index);
        for (String id : ids) {
            Query query =new Query();
            query.addCriteria(Criteria.where("uid").is(Long.parseLong(id)));
            ParParceladdress parcel = mongoTemplate.findOne(query, ParParceladdress.class);
            String addresseeMobile = parcel.getAddresseeMobile();//包裹地址表=的片键 
            query.addCriteria(Criteria.where("addresseeMobile").is(addresseeMobile));
            Long controllerMemberId = parcel.getControllerMemberId();//物权修改前的所有人
            MemberInfoVO memberInfo = getValByMemberId(memberId);
            //拿到netid和compid
            String netId = memberInfo.getNetId();
            String compId = memberInfo.getCompId();
           
            LOGGER.info("1111111111111111======netId:"+netId+",compId:"+compId);
            ParParcelconnection parcelconnection = new ParParcelconnection();
            parcelconnection.setUid(IdWorker.getIdWorker().nextId());
            parcelconnection.setParId(parcel.getUid());
            parcelconnection.setNetId(parcel.getControllerNetId());
            parcelconnection.setNetName(parcel.getControllerNetName());
            parcelconnection.setCompId(parcel.getControllerCompId());
            //parcelconnection.setExpMemberId(parcel.getControllerMemberId());
            parcelconnection.setCosignFlag((short) 2);
            parcelconnection.setExpMemberSuccessFlag((short) 0);
            parcelconnection.setCreateTime(new Date());
            parcelconnection.setMobilePhone(parcel.getAddresseeMobile()); //别改片键

            parcelconnection.setExpWaybillNum(parcel.getExpWaybillNum());
            parcelconnection.setCode(parcel.getCode());
            //交付人信息
            MemberInfoVO memberInfo1 = getValByMemberId(String.valueOf(controllerMemberId));
            parcelconnection.setExpMemberId(parcel.getControllerMemberId());//交付人的id
            parcelconnection.setDeliveryAddress(deliveryAddress);//交付人地址
            parcelconnection.setDeliveryName(memberInfo1.getMemberName());//交付人的姓名
            parcelconnection.setDeliveryMobile(memberInfo1.getMemberPhone());//交付人电话
            parcelconnection.setDeliveryUnits(parcel.getControllerCompName());//交付人的compname
            parcelconnection.setSignResult((short) 3);
           
            //接收人的信息
            parcelconnection.setRecMemberId(Long.parseLong(memberId));//memberid
            parcelconnection.setRecCompId(PubMethod.isEmpty(memberInfo.getCompId()) ? 0L : Long.parseLong(memberInfo.getCompId()));
            parcelconnection.setRecNetId(PubMethod.isEmpty(memberInfo.getNetId()) ? 0L : Long.parseLong(memberInfo.getNetId()));
            parcelconnection.setRecnetName(memberInfo.getNetName());//接收人的网络名称
            parcelconnection.setRecName(memberInfo.getMemberName());//接收人姓名
//            parcelconnection.setRecAddress(memberInfo.get);//接收人地址
            parcelconnection.setRecMobile(memberInfo.getMemberPhone());//接收人电话
            parcelconnection.setRecUnits(memberInfo.getCompName());//接收人单位
            if ("0".equals(index)) {//发货
            	   parcelconnection.setRecCosignFlag((short) 1);
            }else{//转包裹
            	   parcelconnection.setRecCosignFlag((short) 8);
            }
           // parcelconnection.setRecMemberSuccessFlag((short) 0);
            mongoTemplate.insert(parcelconnection);
            LOGGER.info("插入包裹作业记录表记录成功");
            mongoTemplate.updateFirst(query, Update.
                    update("controllerName", memberInfo.getMemberName()). //物权所有人 发货后 "干线运输"
                    set("controllerMemberId", Long.parseLong(memberId)). //物权所有人id
                    set("precontrollermemberid",controllerMemberId). //物权原有人id
                    set("controllerNetName", memberInfo.getNetName()). //物权网络名称
                    set("controllerNetId", PubMethod.isEmpty(netId)? 0L : Long.parseLong(netId)). //物权网络id
                    set("controllerCompId", PubMethod.isEmpty(compId) ? 0L : Long.parseLong(compId)). //物权站点id
                    set("controllerCompName", memberInfo.getCompName()). //物权站点名称
                    set("controllerTaskFlag", index.equals("0") ? (short)1 : (short)10), ParParceladdress.class);   //index是0表示快递员扫码，1表示代收点扫码
            LOGGER.info("修改包裹地址表成功");

            if ("0".equals(index)) {
				//发货过来的数据>扫码揽收
            	LOGGER.info("扫码收货,插入报表数据");
            	Date date = new Date(); // 时间很总要
        		SimpleDateFormat simf = new SimpleDateFormat("yyyy-MM-dd");
        		String dateString = simf.format(date);
            	receivePackageService.insertPackageReport(memberId, netId, compId, dateString);
			}else if ("1".equals(index)) {

                //修改包裹信息表
                query=new Query();
                query.addCriteria(Criteria.where("uid").is(Long.parseLong(id)));
                ParParcelinfo parParcelinfo = this.mongoTemplate.findOne(query, ParParcelinfo.class);
                String mobilePhone = parParcelinfo.getMobilePhone();//包裹信息表的片键
                query.addCriteria(Criteria.where("mobilePhone").is(mobilePhone));
                Update update=new Update();
                update.set("signResult", (short)3);
                update.set("signTime", new Date());
                this.mongoTemplate.updateFirst(query, update, ParParcelinfo.class);

				//转代收和签收过来的数据
				//：Long memberId,Date signDate,Short signResult,String compId,String netId
				Map<String, String>map= new HashMap<>();
				map.put("memberId", String.valueOf(controllerMemberId));//交付人id
				//SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1=new Date();
				Long time = d1.getTime();
				//String date = dateFormat.format(new Date());
				map.put("signDate",String.valueOf(time) );//签收日期
				map.put("signResult", "3");
				map.put("compId", compId);
				map.put("netId",PubMethod.isEmpty(netId) ? "1" : netId);
				LOGGER.info("调用派件项目====代收点扫码收货的参数:"+map);
				String courierToAgent = noticeHttpClient.courierToAgent(map);
				LOGGER.info("调用派件项目====代收点扫码收货的结果:"+courierToAgent);
			}
        }
    	
        return "1";
    }

    private Date getThreeDayAgoDate() {
        return new Date(new Date().getTime() - (3 * 24 * 60 * 60 * 1000));
    }

    private MemberInfoVO getValByMemberId(String memberId) {

        // 快递员基本信息放入缓存中--2016年5月30日19:55:09 by zhaohu
        MemberInfoVO memberInfoVO = this.redisService.get(
                "memberInfo-byzhaohu-cache", memberId, MemberInfoVO.class);
        if (PubMethod.isEmpty(memberInfoVO)) {
            memberInfoVO = this.basEmployeeAuditMapper
                    .queryMemberInfoByZhaohu(memberId);
            this.redisService.put("memberInfo-byzhaohu-cache", memberId,
                    memberInfoVO);
        }
        return memberInfoVO;
    }

    private <T> T getRedisToken(final Class<T> clazz) {
        final byte[] fKey = "uids".getBytes();
        return redis.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection conn) throws DataAccessException {
                byte[] bs = conn.get(fKey);
                return bs == null ? null : JSON.parseObject(new String(bs), clazz);
            }
        });
    }

    private void putRedisToken(Object value) {
        final byte[] fKey = "uids".getBytes();
        String jsonString = JSON.toJSONString(value);
        final byte[] fValue = jsonString.getBytes();
        redis.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection conn) throws DataAccessException {
                conn.set(fKey, fValue);
                conn.expire(fKey, 60L);
                return null;
            }
        });
    }

}
