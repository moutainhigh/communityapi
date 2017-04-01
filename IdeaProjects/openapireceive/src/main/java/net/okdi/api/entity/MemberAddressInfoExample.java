package net.okdi.api.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberAddressInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberAddressInfoExample() {
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

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(Long value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(Long value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(Long value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(Long value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<Long> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<Long> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(Long value1, Long value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdIsNull() {
            addCriterion("address_tag_id is null");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdIsNotNull() {
            addCriterion("address_tag_id is not null");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdEqualTo(Long value) {
            addCriterion("address_tag_id =", value, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdNotEqualTo(Long value) {
            addCriterion("address_tag_id <>", value, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdGreaterThan(Long value) {
            addCriterion("address_tag_id >", value, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdGreaterThanOrEqualTo(Long value) {
            addCriterion("address_tag_id >=", value, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdLessThan(Long value) {
            addCriterion("address_tag_id <", value, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdLessThanOrEqualTo(Long value) {
            addCriterion("address_tag_id <=", value, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdIn(List<Long> values) {
            addCriterion("address_tag_id in", values, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdNotIn(List<Long> values) {
            addCriterion("address_tag_id not in", values, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdBetween(Long value1, Long value2) {
            addCriterion("address_tag_id between", value1, value2, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andAddressTagIdNotBetween(Long value1, Long value2) {
            addCriterion("address_tag_id not between", value1, value2, "addressTagId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdIsNull() {
            addCriterion("detailed_addresss_id is null");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdIsNotNull() {
            addCriterion("detailed_addresss_id is not null");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdEqualTo(Long value) {
            addCriterion("detailed_addresss_id =", value, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdNotEqualTo(Long value) {
            addCriterion("detailed_addresss_id <>", value, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdGreaterThan(Long value) {
            addCriterion("detailed_addresss_id >", value, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdGreaterThanOrEqualTo(Long value) {
            addCriterion("detailed_addresss_id >=", value, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdLessThan(Long value) {
            addCriterion("detailed_addresss_id <", value, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdLessThanOrEqualTo(Long value) {
            addCriterion("detailed_addresss_id <=", value, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdIn(List<Long> values) {
            addCriterion("detailed_addresss_id in", values, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdNotIn(List<Long> values) {
            addCriterion("detailed_addresss_id not in", values, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdBetween(Long value1, Long value2) {
            addCriterion("detailed_addresss_id between", value1, value2, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIdNotBetween(Long value1, Long value2) {
            addCriterion("detailed_addresss_id not between", value1, value2, "detailedAddresssId");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIsNull() {
            addCriterion("detailed_addresss is null");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIsNotNull() {
            addCriterion("detailed_addresss is not null");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssEqualTo(String value) {
            addCriterion("detailed_addresss =", value, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssNotEqualTo(String value) {
            addCriterion("detailed_addresss <>", value, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssGreaterThan(String value) {
            addCriterion("detailed_addresss >", value, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssGreaterThanOrEqualTo(String value) {
            addCriterion("detailed_addresss >=", value, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssLessThan(String value) {
            addCriterion("detailed_addresss <", value, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssLessThanOrEqualTo(String value) {
            addCriterion("detailed_addresss <=", value, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssLike(String value) {
            addCriterion("detailed_addresss like", value, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssNotLike(String value) {
            addCriterion("detailed_addresss not like", value, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssIn(List<String> values) {
            addCriterion("detailed_addresss in", values, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssNotIn(List<String> values) {
            addCriterion("detailed_addresss not in", values, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssBetween(String value1, String value2) {
            addCriterion("detailed_addresss between", value1, value2, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andDetailedAddresssNotBetween(String value1, String value2) {
            addCriterion("detailed_addresss not between", value1, value2, "detailedAddresss");
            return (Criteria) this;
        }

        public Criteria andZipCodeIsNull() {
            addCriterion("zip_code is null");
            return (Criteria) this;
        }

        public Criteria andZipCodeIsNotNull() {
            addCriterion("zip_code is not null");
            return (Criteria) this;
        }

        public Criteria andZipCodeEqualTo(String value) {
            addCriterion("zip_code =", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotEqualTo(String value) {
            addCriterion("zip_code <>", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeGreaterThan(String value) {
            addCriterion("zip_code >", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeGreaterThanOrEqualTo(String value) {
            addCriterion("zip_code >=", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLessThan(String value) {
            addCriterion("zip_code <", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLessThanOrEqualTo(String value) {
            addCriterion("zip_code <=", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeLike(String value) {
            addCriterion("zip_code like", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotLike(String value) {
            addCriterion("zip_code not like", value, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeIn(List<String> values) {
            addCriterion("zip_code in", values, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotIn(List<String> values) {
            addCriterion("zip_code not in", values, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeBetween(String value1, String value2) {
            addCriterion("zip_code between", value1, value2, "zipCode");
            return (Criteria) this;
        }

        public Criteria andZipCodeNotBetween(String value1, String value2) {
            addCriterion("zip_code not between", value1, value2, "zipCode");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(BigDecimal value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(BigDecimal value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(BigDecimal value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(BigDecimal value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<BigDecimal> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<BigDecimal> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(BigDecimal value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(BigDecimal value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(BigDecimal value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(BigDecimal value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<BigDecimal> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<BigDecimal> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andShippingDefIsNull() {
            addCriterion("shipping_def is null");
            return (Criteria) this;
        }

        public Criteria andShippingDefIsNotNull() {
            addCriterion("shipping_def is not null");
            return (Criteria) this;
        }

        public Criteria andShippingDefEqualTo(Short value) {
            addCriterion("shipping_def =", value, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefNotEqualTo(Short value) {
            addCriterion("shipping_def <>", value, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefGreaterThan(Short value) {
            addCriterion("shipping_def >", value, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefGreaterThanOrEqualTo(Short value) {
            addCriterion("shipping_def >=", value, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefLessThan(Short value) {
            addCriterion("shipping_def <", value, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefLessThanOrEqualTo(Short value) {
            addCriterion("shipping_def <=", value, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefIn(List<Short> values) {
            addCriterion("shipping_def in", values, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefNotIn(List<Short> values) {
            addCriterion("shipping_def not in", values, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefBetween(Short value1, Short value2) {
            addCriterion("shipping_def between", value1, value2, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andShippingDefNotBetween(Short value1, Short value2) {
            addCriterion("shipping_def not between", value1, value2, "shippingDef");
            return (Criteria) this;
        }

        public Criteria andGetDefIsNull() {
            addCriterion("get_def is null");
            return (Criteria) this;
        }

        public Criteria andGetDefIsNotNull() {
            addCriterion("get_def is not null");
            return (Criteria) this;
        }

        public Criteria andGetDefEqualTo(Short value) {
            addCriterion("get_def =", value, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefNotEqualTo(Short value) {
            addCriterion("get_def <>", value, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefGreaterThan(Short value) {
            addCriterion("get_def >", value, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefGreaterThanOrEqualTo(Short value) {
            addCriterion("get_def >=", value, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefLessThan(Short value) {
            addCriterion("get_def <", value, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefLessThanOrEqualTo(Short value) {
            addCriterion("get_def <=", value, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefIn(List<Short> values) {
            addCriterion("get_def in", values, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefNotIn(List<Short> values) {
            addCriterion("get_def not in", values, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefBetween(Short value1, Short value2) {
            addCriterion("get_def between", value1, value2, "getDef");
            return (Criteria) this;
        }

        public Criteria andGetDefNotBetween(Short value1, Short value2) {
            addCriterion("get_def not between", value1, value2, "getDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefIsNull() {
            addCriterion("return_def is null");
            return (Criteria) this;
        }

        public Criteria andReturnDefIsNotNull() {
            addCriterion("return_def is not null");
            return (Criteria) this;
        }

        public Criteria andReturnDefEqualTo(Short value) {
            addCriterion("return_def =", value, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefNotEqualTo(Short value) {
            addCriterion("return_def <>", value, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefGreaterThan(Short value) {
            addCriterion("return_def >", value, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefGreaterThanOrEqualTo(Short value) {
            addCriterion("return_def >=", value, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefLessThan(Short value) {
            addCriterion("return_def <", value, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefLessThanOrEqualTo(Short value) {
            addCriterion("return_def <=", value, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefIn(List<Short> values) {
            addCriterion("return_def in", values, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefNotIn(List<Short> values) {
            addCriterion("return_def not in", values, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefBetween(Short value1, Short value2) {
            addCriterion("return_def between", value1, value2, "returnDef");
            return (Criteria) this;
        }

        public Criteria andReturnDefNotBetween(Short value1, Short value2) {
            addCriterion("return_def not between", value1, value2, "returnDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefIsNull() {
            addCriterion("invoice_def is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefIsNotNull() {
            addCriterion("invoice_def is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefEqualTo(Short value) {
            addCriterion("invoice_def =", value, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefNotEqualTo(Short value) {
            addCriterion("invoice_def <>", value, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefGreaterThan(Short value) {
            addCriterion("invoice_def >", value, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefGreaterThanOrEqualTo(Short value) {
            addCriterion("invoice_def >=", value, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefLessThan(Short value) {
            addCriterion("invoice_def <", value, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefLessThanOrEqualTo(Short value) {
            addCriterion("invoice_def <=", value, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefIn(List<Short> values) {
            addCriterion("invoice_def in", values, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefNotIn(List<Short> values) {
            addCriterion("invoice_def not in", values, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefBetween(Short value1, Short value2) {
            addCriterion("invoice_def between", value1, value2, "invoiceDef");
            return (Criteria) this;
        }

        public Criteria andInvoiceDefNotBetween(Short value1, Short value2) {
            addCriterion("invoice_def not between", value1, value2, "invoiceDef");
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

        public Criteria andDelMarkIsNull() {
            addCriterion("del_mark is null");
            return (Criteria) this;
        }

        public Criteria andDelMarkIsNotNull() {
            addCriterion("del_mark is not null");
            return (Criteria) this;
        }

        public Criteria andDelMarkEqualTo(Short value) {
            addCriterion("del_mark =", value, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkNotEqualTo(Short value) {
            addCriterion("del_mark <>", value, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkGreaterThan(Short value) {
            addCriterion("del_mark >", value, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkGreaterThanOrEqualTo(Short value) {
            addCriterion("del_mark >=", value, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkLessThan(Short value) {
            addCriterion("del_mark <", value, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkLessThanOrEqualTo(Short value) {
            addCriterion("del_mark <=", value, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkIn(List<Short> values) {
            addCriterion("del_mark in", values, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkNotIn(List<Short> values) {
            addCriterion("del_mark not in", values, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkBetween(Short value1, Short value2) {
            addCriterion("del_mark between", value1, value2, "delMark");
            return (Criteria) this;
        }

        public Criteria andDelMarkNotBetween(Short value1, Short value2) {
            addCriterion("del_mark not between", value1, value2, "delMark");
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