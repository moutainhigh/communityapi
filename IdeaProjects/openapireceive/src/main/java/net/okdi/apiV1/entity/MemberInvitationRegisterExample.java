package net.okdi.apiV1.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberInvitationRegisterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberInvitationRegisterExample() {
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

        public Criteria andFromMemberIdIsNull() {
            addCriterion("from_member_id is null");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdIsNotNull() {
            addCriterion("from_member_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdEqualTo(Long value) {
            addCriterion("from_member_id =", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdNotEqualTo(Long value) {
            addCriterion("from_member_id <>", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdGreaterThan(Long value) {
            addCriterion("from_member_id >", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdGreaterThanOrEqualTo(Long value) {
            addCriterion("from_member_id >=", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdLessThan(Long value) {
            addCriterion("from_member_id <", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdLessThanOrEqualTo(Long value) {
            addCriterion("from_member_id <=", value, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdIn(List<Long> values) {
            addCriterion("from_member_id in", values, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdNotIn(List<Long> values) {
            addCriterion("from_member_id not in", values, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdBetween(Long value1, Long value2) {
            addCriterion("from_member_id between", value1, value2, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberIdNotBetween(Long value1, Long value2) {
            addCriterion("from_member_id not between", value1, value2, "fromMemberId");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneIsNull() {
            addCriterion("from_member_phone is null");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneIsNotNull() {
            addCriterion("from_member_phone is not null");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneEqualTo(String value) {
            addCriterion("from_member_phone =", value, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneNotEqualTo(String value) {
            addCriterion("from_member_phone <>", value, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneGreaterThan(String value) {
            addCriterion("from_member_phone >", value, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("from_member_phone >=", value, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneLessThan(String value) {
            addCriterion("from_member_phone <", value, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneLessThanOrEqualTo(String value) {
            addCriterion("from_member_phone <=", value, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneLike(String value) {
            addCriterion("from_member_phone like", value, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneNotLike(String value) {
            addCriterion("from_member_phone not like", value, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneIn(List<String> values) {
            addCriterion("from_member_phone in", values, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneNotIn(List<String> values) {
            addCriterion("from_member_phone not in", values, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneBetween(String value1, String value2) {
            addCriterion("from_member_phone between", value1, value2, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberPhoneNotBetween(String value1, String value2) {
            addCriterion("from_member_phone not between", value1, value2, "fromMemberPhone");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdIsNull() {
            addCriterion("from_member_role_id is null");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdIsNotNull() {
            addCriterion("from_member_role_id is not null");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdEqualTo(Short value) {
            addCriterion("from_member_role_id =", value, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdNotEqualTo(Short value) {
            addCriterion("from_member_role_id <>", value, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdGreaterThan(Short value) {
            addCriterion("from_member_role_id >", value, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdGreaterThanOrEqualTo(Short value) {
            addCriterion("from_member_role_id >=", value, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdLessThan(Short value) {
            addCriterion("from_member_role_id <", value, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdLessThanOrEqualTo(Short value) {
            addCriterion("from_member_role_id <=", value, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdIn(List<Short> values) {
            addCriterion("from_member_role_id in", values, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdNotIn(List<Short> values) {
            addCriterion("from_member_role_id not in", values, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdBetween(Short value1, Short value2) {
            addCriterion("from_member_role_id between", value1, value2, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andFromMemberRoleIdNotBetween(Short value1, Short value2) {
            addCriterion("from_member_role_id not between", value1, value2, "fromMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneIsNull() {
            addCriterion("to_member_phone is null");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneIsNotNull() {
            addCriterion("to_member_phone is not null");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneEqualTo(String value) {
            addCriterion("to_member_phone =", value, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneNotEqualTo(String value) {
            addCriterion("to_member_phone <>", value, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneGreaterThan(String value) {
            addCriterion("to_member_phone >", value, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("to_member_phone >=", value, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneLessThan(String value) {
            addCriterion("to_member_phone <", value, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneLessThanOrEqualTo(String value) {
            addCriterion("to_member_phone <=", value, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneLike(String value) {
            addCriterion("to_member_phone like", value, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneNotLike(String value) {
            addCriterion("to_member_phone not like", value, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneIn(List<String> values) {
            addCriterion("to_member_phone in", values, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneNotIn(List<String> values) {
            addCriterion("to_member_phone not in", values, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneBetween(String value1, String value2) {
            addCriterion("to_member_phone between", value1, value2, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberPhoneNotBetween(String value1, String value2) {
            addCriterion("to_member_phone not between", value1, value2, "toMemberPhone");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdIsNull() {
            addCriterion("to_member_role_id is null");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdIsNotNull() {
            addCriterion("to_member_role_id is not null");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdEqualTo(Short value) {
            addCriterion("to_member_role_id =", value, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdNotEqualTo(Short value) {
            addCriterion("to_member_role_id <>", value, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdGreaterThan(Short value) {
            addCriterion("to_member_role_id >", value, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdGreaterThanOrEqualTo(Short value) {
            addCriterion("to_member_role_id >=", value, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdLessThan(Short value) {
            addCriterion("to_member_role_id <", value, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdLessThanOrEqualTo(Short value) {
            addCriterion("to_member_role_id <=", value, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdIn(List<Short> values) {
            addCriterion("to_member_role_id in", values, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdNotIn(List<Short> values) {
            addCriterion("to_member_role_id not in", values, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdBetween(Short value1, Short value2) {
            addCriterion("to_member_role_id between", value1, value2, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberRoleIdNotBetween(Short value1, Short value2) {
            addCriterion("to_member_role_id not between", value1, value2, "toMemberRoleId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdIsNull() {
            addCriterion("to_member_net_id is null");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdIsNotNull() {
            addCriterion("to_member_net_id is not null");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdEqualTo(Long value) {
            addCriterion("to_member_net_id =", value, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdNotEqualTo(Long value) {
            addCriterion("to_member_net_id <>", value, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdGreaterThan(Long value) {
            addCriterion("to_member_net_id >", value, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdGreaterThanOrEqualTo(Long value) {
            addCriterion("to_member_net_id >=", value, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdLessThan(Long value) {
            addCriterion("to_member_net_id <", value, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdLessThanOrEqualTo(Long value) {
            addCriterion("to_member_net_id <=", value, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdIn(List<Long> values) {
            addCriterion("to_member_net_id in", values, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdNotIn(List<Long> values) {
            addCriterion("to_member_net_id not in", values, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdBetween(Long value1, Long value2) {
            addCriterion("to_member_net_id between", value1, value2, "toMemberNetId");
            return (Criteria) this;
        }

        public Criteria andToMemberNetIdNotBetween(Long value1, Long value2) {
            addCriterion("to_member_net_id not between", value1, value2, "toMemberNetId");
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

        public Criteria andModityTimeIsNull() {
            addCriterion("modity_time is null");
            return (Criteria) this;
        }

        public Criteria andModityTimeIsNotNull() {
            addCriterion("modity_time is not null");
            return (Criteria) this;
        }

        public Criteria andModityTimeEqualTo(Date value) {
            addCriterion("modity_time =", value, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeNotEqualTo(Date value) {
            addCriterion("modity_time <>", value, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeGreaterThan(Date value) {
            addCriterion("modity_time >", value, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modity_time >=", value, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeLessThan(Date value) {
            addCriterion("modity_time <", value, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeLessThanOrEqualTo(Date value) {
            addCriterion("modity_time <=", value, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeIn(List<Date> values) {
            addCriterion("modity_time in", values, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeNotIn(List<Date> values) {
            addCriterion("modity_time not in", values, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeBetween(Date value1, Date value2) {
            addCriterion("modity_time between", value1, value2, "modityTime");
            return (Criteria) this;
        }

        public Criteria andModityTimeNotBetween(Date value1, Date value2) {
            addCriterion("modity_time not between", value1, value2, "modityTime");
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