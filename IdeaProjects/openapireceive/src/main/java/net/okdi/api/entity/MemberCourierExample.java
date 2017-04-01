package net.okdi.api.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberCourierExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberCourierExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Long value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Long value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Long value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Long value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Long value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Long> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Long> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Long value1, Long value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Long value1, Long value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCourierNameIsNull() {
            addCriterion("courier_name is null");
            return (Criteria) this;
        }

        public Criteria andCourierNameIsNotNull() {
            addCriterion("courier_name is not null");
            return (Criteria) this;
        }

        public Criteria andCourierNameEqualTo(String value) {
            addCriterion("courier_name =", value, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameNotEqualTo(String value) {
            addCriterion("courier_name <>", value, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameGreaterThan(String value) {
            addCriterion("courier_name >", value, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameGreaterThanOrEqualTo(String value) {
            addCriterion("courier_name >=", value, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameLessThan(String value) {
            addCriterion("courier_name <", value, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameLessThanOrEqualTo(String value) {
            addCriterion("courier_name <=", value, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameLike(String value) {
            addCriterion("courier_name like", value, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameNotLike(String value) {
            addCriterion("courier_name not like", value, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameIn(List<String> values) {
            addCriterion("courier_name in", values, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameNotIn(List<String> values) {
            addCriterion("courier_name not in", values, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameBetween(String value1, String value2) {
            addCriterion("courier_name between", value1, value2, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierNameNotBetween(String value1, String value2) {
            addCriterion("courier_name not between", value1, value2, "courierName");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneIsNull() {
            addCriterion("courier_phone is null");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneIsNotNull() {
            addCriterion("courier_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneEqualTo(String value) {
            addCriterion("courier_phone =", value, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneNotEqualTo(String value) {
            addCriterion("courier_phone <>", value, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneGreaterThan(String value) {
            addCriterion("courier_phone >", value, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("courier_phone >=", value, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneLessThan(String value) {
            addCriterion("courier_phone <", value, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneLessThanOrEqualTo(String value) {
            addCriterion("courier_phone <=", value, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneLike(String value) {
            addCriterion("courier_phone like", value, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneNotLike(String value) {
            addCriterion("courier_phone not like", value, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneIn(List<String> values) {
            addCriterion("courier_phone in", values, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneNotIn(List<String> values) {
            addCriterion("courier_phone not in", values, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneBetween(String value1, String value2) {
            addCriterion("courier_phone between", value1, value2, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierPhoneNotBetween(String value1, String value2) {
            addCriterion("courier_phone not between", value1, value2, "courierPhone");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdIsNull() {
            addCriterion("courier_address_id is null");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdIsNotNull() {
            addCriterion("courier_address_id is not null");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdEqualTo(Long value) {
            addCriterion("courier_address_id =", value, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdNotEqualTo(Long value) {
            addCriterion("courier_address_id <>", value, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdGreaterThan(Long value) {
            addCriterion("courier_address_id >", value, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdGreaterThanOrEqualTo(Long value) {
            addCriterion("courier_address_id >=", value, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdLessThan(Long value) {
            addCriterion("courier_address_id <", value, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdLessThanOrEqualTo(Long value) {
            addCriterion("courier_address_id <=", value, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdIn(List<Long> values) {
            addCriterion("courier_address_id in", values, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdNotIn(List<Long> values) {
            addCriterion("courier_address_id not in", values, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdBetween(Long value1, Long value2) {
            addCriterion("courier_address_id between", value1, value2, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierAddressIdNotBetween(Long value1, Long value2) {
            addCriterion("courier_address_id not between", value1, value2, "courierAddressId");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayIsNull() {
            addCriterion("courier_detaile_display is null");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayIsNotNull() {
            addCriterion("courier_detaile_display is not null");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayEqualTo(String value) {
            addCriterion("courier_detaile_display =", value, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayNotEqualTo(String value) {
            addCriterion("courier_detaile_display <>", value, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayGreaterThan(String value) {
            addCriterion("courier_detaile_display >", value, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayGreaterThanOrEqualTo(String value) {
            addCriterion("courier_detaile_display >=", value, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayLessThan(String value) {
            addCriterion("courier_detaile_display <", value, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayLessThanOrEqualTo(String value) {
            addCriterion("courier_detaile_display <=", value, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayLike(String value) {
            addCriterion("courier_detaile_display like", value, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayNotLike(String value) {
            addCriterion("courier_detaile_display not like", value, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayIn(List<String> values) {
            addCriterion("courier_detaile_display in", values, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayNotIn(List<String> values) {
            addCriterion("courier_detaile_display not in", values, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayBetween(String value1, String value2) {
            addCriterion("courier_detaile_display between", value1, value2, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetaileDisplayNotBetween(String value1, String value2) {
            addCriterion("courier_detaile_display not between", value1, value2, "courierDetaileDisplay");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressIsNull() {
            addCriterion("courier_detailed_address is null");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressIsNotNull() {
            addCriterion("courier_detailed_address is not null");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressEqualTo(String value) {
            addCriterion("courier_detailed_address =", value, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressNotEqualTo(String value) {
            addCriterion("courier_detailed_address <>", value, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressGreaterThan(String value) {
            addCriterion("courier_detailed_address >", value, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressGreaterThanOrEqualTo(String value) {
            addCriterion("courier_detailed_address >=", value, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressLessThan(String value) {
            addCriterion("courier_detailed_address <", value, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressLessThanOrEqualTo(String value) {
            addCriterion("courier_detailed_address <=", value, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressLike(String value) {
            addCriterion("courier_detailed_address like", value, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressNotLike(String value) {
            addCriterion("courier_detailed_address not like", value, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressIn(List<String> values) {
            addCriterion("courier_detailed_address in", values, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressNotIn(List<String> values) {
            addCriterion("courier_detailed_address not in", values, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressBetween(String value1, String value2) {
            addCriterion("courier_detailed_address between", value1, value2, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andCourierDetailedAddressNotBetween(String value1, String value2) {
            addCriterion("courier_detailed_address not between", value1, value2, "courierDetailedAddress");
            return (Criteria) this;
        }

        public Criteria andNetIdIsNull() {
            addCriterion("net_id is null");
            return (Criteria) this;
        }

        public Criteria andNetIdIsNotNull() {
            addCriterion("net_id is not null");
            return (Criteria) this;
        }

        public Criteria andNetIdEqualTo(String value) {
            addCriterion("net_id =", value, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdNotEqualTo(String value) {
            addCriterion("net_id <>", value, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdGreaterThan(String value) {
            addCriterion("net_id >", value, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdGreaterThanOrEqualTo(String value) {
            addCriterion("net_id >=", value, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdLessThan(String value) {
            addCriterion("net_id <", value, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdLessThanOrEqualTo(String value) {
            addCriterion("net_id <=", value, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdLike(String value) {
            addCriterion("net_id like", value, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdNotLike(String value) {
            addCriterion("net_id not like", value, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdIn(List<String> values) {
            addCriterion("net_id in", values, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdNotIn(List<String> values) {
            addCriterion("net_id not in", values, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdBetween(String value1, String value2) {
            addCriterion("net_id between", value1, value2, "netId");
            return (Criteria) this;
        }

        public Criteria andNetIdNotBetween(String value1, String value2) {
            addCriterion("net_id not between", value1, value2, "netId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCompIdIsNull() {
            addCriterion("comp_id is null");
            return (Criteria) this;
        }

        public Criteria andCompIdIsNotNull() {
            addCriterion("comp_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompIdEqualTo(Long value) {
            addCriterion("comp_id =", value, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdNotEqualTo(Long value) {
            addCriterion("comp_id <>", value, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdGreaterThan(Long value) {
            addCriterion("comp_id >", value, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdGreaterThanOrEqualTo(Long value) {
            addCriterion("comp_id >=", value, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdLessThan(Long value) {
            addCriterion("comp_id <", value, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdLessThanOrEqualTo(Long value) {
            addCriterion("comp_id <=", value, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdIn(List<Long> values) {
            addCriterion("comp_id in", values, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdNotIn(List<Long> values) {
            addCriterion("comp_id not in", values, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdBetween(Long value1, Long value2) {
            addCriterion("comp_id between", value1, value2, "compId");
            return (Criteria) this;
        }

        public Criteria andCompIdNotBetween(Long value1, Long value2) {
            addCriterion("comp_id not between", value1, value2, "compId");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdIsNull() {
            addCriterion("cas_member_id is null");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdIsNotNull() {
            addCriterion("cas_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdEqualTo(Long value) {
            addCriterion("cas_member_id =", value, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdNotEqualTo(Long value) {
            addCriterion("cas_member_id <>", value, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdGreaterThan(Long value) {
            addCriterion("cas_member_id >", value, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cas_member_id >=", value, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdLessThan(Long value) {
            addCriterion("cas_member_id <", value, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("cas_member_id <=", value, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdIn(List<Long> values) {
            addCriterion("cas_member_id in", values, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdNotIn(List<Long> values) {
            addCriterion("cas_member_id not in", values, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdBetween(Long value1, Long value2) {
            addCriterion("cas_member_id between", value1, value2, "casMemberId");
            return (Criteria) this;
        }

        public Criteria andCasMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("cas_member_id not between", value1, value2, "casMemberId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}