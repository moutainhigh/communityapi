package net.okdi.api.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExpCompRelationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ExpCompRelationExample() {
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

        public Criteria andParentNetIdIsNull() {
            addCriterion("parent_net_id is null");
            return (Criteria) this;
        }

        public Criteria andParentNetIdIsNotNull() {
            addCriterion("parent_net_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentNetIdEqualTo(Long value) {
            addCriterion("parent_net_id =", value, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdNotEqualTo(Long value) {
            addCriterion("parent_net_id <>", value, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdGreaterThan(Long value) {
            addCriterion("parent_net_id >", value, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_net_id >=", value, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdLessThan(Long value) {
            addCriterion("parent_net_id <", value, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_net_id <=", value, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdIn(List<Long> values) {
            addCriterion("parent_net_id in", values, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdNotIn(List<Long> values) {
            addCriterion("parent_net_id not in", values, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdBetween(Long value1, Long value2) {
            addCriterion("parent_net_id between", value1, value2, "parentNetId");
            return (Criteria) this;
        }

        public Criteria andParentNetIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_net_id not between", value1, value2, "parentNetId");
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

        public Criteria andParentCompIdIsNull() {
            addCriterion("parent_comp_id is null");
            return (Criteria) this;
        }

        public Criteria andParentCompIdIsNotNull() {
            addCriterion("parent_comp_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentCompIdEqualTo(Long value) {
            addCriterion("parent_comp_id =", value, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdNotEqualTo(Long value) {
            addCriterion("parent_comp_id <>", value, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdGreaterThan(Long value) {
            addCriterion("parent_comp_id >", value, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_comp_id >=", value, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdLessThan(Long value) {
            addCriterion("parent_comp_id <", value, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_comp_id <=", value, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdIn(List<Long> values) {
            addCriterion("parent_comp_id in", values, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdNotIn(List<Long> values) {
            addCriterion("parent_comp_id not in", values, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdBetween(Long value1, Long value2) {
            addCriterion("parent_comp_id between", value1, value2, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andParentCompIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_comp_id not between", value1, value2, "parentCompId");
            return (Criteria) this;
        }

        public Criteria andAreaColorIsNull() {
            addCriterion("area_color is null");
            return (Criteria) this;
        }

        public Criteria andAreaColorIsNotNull() {
            addCriterion("area_color is not null");
            return (Criteria) this;
        }

        public Criteria andAreaColorEqualTo(String value) {
            addCriterion("area_color =", value, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorNotEqualTo(String value) {
            addCriterion("area_color <>", value, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorGreaterThan(String value) {
            addCriterion("area_color >", value, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorGreaterThanOrEqualTo(String value) {
            addCriterion("area_color >=", value, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorLessThan(String value) {
            addCriterion("area_color <", value, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorLessThanOrEqualTo(String value) {
            addCriterion("area_color <=", value, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorLike(String value) {
            addCriterion("area_color like", value, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorNotLike(String value) {
            addCriterion("area_color not like", value, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorIn(List<String> values) {
            addCriterion("area_color in", values, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorNotIn(List<String> values) {
            addCriterion("area_color not in", values, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorBetween(String value1, String value2) {
            addCriterion("area_color between", value1, value2, "areaColor");
            return (Criteria) this;
        }

        public Criteria andAreaColorNotBetween(String value1, String value2) {
            addCriterion("area_color not between", value1, value2, "areaColor");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIsNull() {
            addCriterion("serial_number is null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIsNotNull() {
            addCriterion("serial_number is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNumberEqualTo(Byte value) {
            addCriterion("serial_number =", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotEqualTo(Byte value) {
            addCriterion("serial_number <>", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThan(Byte value) {
            addCriterion("serial_number >", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberGreaterThanOrEqualTo(Byte value) {
            addCriterion("serial_number >=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThan(Byte value) {
            addCriterion("serial_number <", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberLessThanOrEqualTo(Byte value) {
            addCriterion("serial_number <=", value, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberIn(List<Byte> values) {
            addCriterion("serial_number in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotIn(List<Byte> values) {
            addCriterion("serial_number not in", values, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberBetween(Byte value1, Byte value2) {
            addCriterion("serial_number between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andSerialNumberNotBetween(Byte value1, Byte value2) {
            addCriterion("serial_number not between", value1, value2, "serialNumber");
            return (Criteria) this;
        }

        public Criteria andCompLevelIsNull() {
            addCriterion("comp_level is null");
            return (Criteria) this;
        }

        public Criteria andCompLevelIsNotNull() {
            addCriterion("comp_level is not null");
            return (Criteria) this;
        }

        public Criteria andCompLevelEqualTo(Byte value) {
            addCriterion("comp_level =", value, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelNotEqualTo(Byte value) {
            addCriterion("comp_level <>", value, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelGreaterThan(Byte value) {
            addCriterion("comp_level >", value, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("comp_level >=", value, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelLessThan(Byte value) {
            addCriterion("comp_level <", value, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelLessThanOrEqualTo(Byte value) {
            addCriterion("comp_level <=", value, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelIn(List<Byte> values) {
            addCriterion("comp_level in", values, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelNotIn(List<Byte> values) {
            addCriterion("comp_level not in", values, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelBetween(Byte value1, Byte value2) {
            addCriterion("comp_level between", value1, value2, "compLevel");
            return (Criteria) this;
        }

        public Criteria andCompLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("comp_level not between", value1, value2, "compLevel");
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

        public Criteria andRelationStatusIsNull() {
            addCriterion("relation_status is null");
            return (Criteria) this;
        }

        public Criteria andRelationStatusIsNotNull() {
            addCriterion("relation_status is not null");
            return (Criteria) this;
        }

        public Criteria andRelationStatusEqualTo(Byte value) {
            addCriterion("relation_status =", value, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusNotEqualTo(Byte value) {
            addCriterion("relation_status <>", value, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusGreaterThan(Byte value) {
            addCriterion("relation_status >", value, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("relation_status >=", value, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusLessThan(Byte value) {
            addCriterion("relation_status <", value, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusLessThanOrEqualTo(Byte value) {
            addCriterion("relation_status <=", value, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusIn(List<Byte> values) {
            addCriterion("relation_status in", values, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusNotIn(List<Byte> values) {
            addCriterion("relation_status not in", values, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusBetween(Byte value1, Byte value2) {
            addCriterion("relation_status between", value1, value2, "relationStatus");
            return (Criteria) this;
        }

        public Criteria andRelationStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("relation_status not between", value1, value2, "relationStatus");
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