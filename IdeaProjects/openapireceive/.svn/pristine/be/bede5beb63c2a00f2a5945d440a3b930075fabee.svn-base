package net.okdi.api.entity;

import java.util.ArrayList;
import java.util.List;

public class NetLowestPriceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NetLowestPriceExample() {
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

        public Criteria andFromAddressIdIsNull() {
            addCriterion("from_address_id is null");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdIsNotNull() {
            addCriterion("from_address_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdEqualTo(Long value) {
            addCriterion("from_address_id =", value, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdNotEqualTo(Long value) {
            addCriterion("from_address_id <>", value, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdGreaterThan(Long value) {
            addCriterion("from_address_id >", value, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_address_id >=", value, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdLessThan(Long value) {
            addCriterion("from_address_id <", value, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdLessThanOrEqualTo(Long value) {
            addCriterion("from_address_id <=", value, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdIn(List<Long> values) {
            addCriterion("from_address_id in", values, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdNotIn(List<Long> values) {
            addCriterion("from_address_id not in", values, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdBetween(Long value1, Long value2) {
            addCriterion("from_address_id between", value1, value2, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andFromAddressIdNotBetween(Long value1, Long value2) {
            addCriterion("from_address_id not between", value1, value2, "fromAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdIsNull() {
            addCriterion("to_address_id is null");
            return (Criteria) this;
        }

        public Criteria andToAddressIdIsNotNull() {
            addCriterion("to_address_id is not null");
            return (Criteria) this;
        }

        public Criteria andToAddressIdEqualTo(Long value) {
            addCriterion("to_address_id =", value, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdNotEqualTo(Long value) {
            addCriterion("to_address_id <>", value, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdGreaterThan(Long value) {
            addCriterion("to_address_id >", value, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdGreaterThanOrEqualTo(Long value) {
            addCriterion("to_address_id >=", value, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdLessThan(Long value) {
            addCriterion("to_address_id <", value, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdLessThanOrEqualTo(Long value) {
            addCriterion("to_address_id <=", value, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdIn(List<Long> values) {
            addCriterion("to_address_id in", values, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdNotIn(List<Long> values) {
            addCriterion("to_address_id not in", values, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdBetween(Long value1, Long value2) {
            addCriterion("to_address_id between", value1, value2, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andToAddressIdNotBetween(Long value1, Long value2) {
            addCriterion("to_address_id not between", value1, value2, "toAddressId");
            return (Criteria) this;
        }

        public Criteria andFirstFreightIsNull() {
            addCriterion("first_freight is null");
            return (Criteria) this;
        }

        public Criteria andFirstFreightIsNotNull() {
            addCriterion("first_freight is not null");
            return (Criteria) this;
        }

        public Criteria andFirstFreightEqualTo(Short value) {
            addCriterion("first_freight =", value, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightNotEqualTo(Short value) {
            addCriterion("first_freight <>", value, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightGreaterThan(Short value) {
            addCriterion("first_freight >", value, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightGreaterThanOrEqualTo(Short value) {
            addCriterion("first_freight >=", value, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightLessThan(Short value) {
            addCriterion("first_freight <", value, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightLessThanOrEqualTo(Short value) {
            addCriterion("first_freight <=", value, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightIn(List<Short> values) {
            addCriterion("first_freight in", values, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightNotIn(List<Short> values) {
            addCriterion("first_freight not in", values, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightBetween(Short value1, Short value2) {
            addCriterion("first_freight between", value1, value2, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andFirstFreightNotBetween(Short value1, Short value2) {
            addCriterion("first_freight not between", value1, value2, "firstFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightIsNull() {
            addCriterion("continue_freight is null");
            return (Criteria) this;
        }

        public Criteria andContinueFreightIsNotNull() {
            addCriterion("continue_freight is not null");
            return (Criteria) this;
        }

        public Criteria andContinueFreightEqualTo(Short value) {
            addCriterion("continue_freight =", value, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightNotEqualTo(Short value) {
            addCriterion("continue_freight <>", value, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightGreaterThan(Short value) {
            addCriterion("continue_freight >", value, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightGreaterThanOrEqualTo(Short value) {
            addCriterion("continue_freight >=", value, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightLessThan(Short value) {
            addCriterion("continue_freight <", value, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightLessThanOrEqualTo(Short value) {
            addCriterion("continue_freight <=", value, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightIn(List<Short> values) {
            addCriterion("continue_freight in", values, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightNotIn(List<Short> values) {
            addCriterion("continue_freight not in", values, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightBetween(Short value1, Short value2) {
            addCriterion("continue_freight between", value1, value2, "continueFreight");
            return (Criteria) this;
        }

        public Criteria andContinueFreightNotBetween(Short value1, Short value2) {
            addCriterion("continue_freight not between", value1, value2, "continueFreight");
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