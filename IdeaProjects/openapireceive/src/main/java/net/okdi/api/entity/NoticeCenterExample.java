package net.okdi.api.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeCenterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NoticeCenterExample() {
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

        public Criteria andCreateCompIdIsNull() {
            addCriterion("create_comp_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdIsNotNull() {
            addCriterion("create_comp_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdEqualTo(Long value) {
            addCriterion("create_comp_id =", value, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdNotEqualTo(Long value) {
            addCriterion("create_comp_id <>", value, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdGreaterThan(Long value) {
            addCriterion("create_comp_id >", value, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdGreaterThanOrEqualTo(Long value) {
            addCriterion("create_comp_id >=", value, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdLessThan(Long value) {
            addCriterion("create_comp_id <", value, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdLessThanOrEqualTo(Long value) {
            addCriterion("create_comp_id <=", value, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdIn(List<Long> values) {
            addCriterion("create_comp_id in", values, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdNotIn(List<Long> values) {
            addCriterion("create_comp_id not in", values, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdBetween(Long value1, Long value2) {
            addCriterion("create_comp_id between", value1, value2, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCreateCompIdNotBetween(Long value1, Long value2) {
            addCriterion("create_comp_id not between", value1, value2, "createCompId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdIsNull() {
            addCriterion("comp_audit_log_id is null");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdIsNotNull() {
            addCriterion("comp_audit_log_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdEqualTo(Long value) {
            addCriterion("comp_audit_log_id =", value, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdNotEqualTo(Long value) {
            addCriterion("comp_audit_log_id <>", value, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdGreaterThan(Long value) {
            addCriterion("comp_audit_log_id >", value, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("comp_audit_log_id >=", value, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdLessThan(Long value) {
            addCriterion("comp_audit_log_id <", value, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdLessThanOrEqualTo(Long value) {
            addCriterion("comp_audit_log_id <=", value, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdIn(List<Long> values) {
            addCriterion("comp_audit_log_id in", values, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdNotIn(List<Long> values) {
            addCriterion("comp_audit_log_id not in", values, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdBetween(Long value1, Long value2) {
            addCriterion("comp_audit_log_id between", value1, value2, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andCompAuditLogIdNotBetween(Long value1, Long value2) {
            addCriterion("comp_audit_log_id not between", value1, value2, "compAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdIsNull() {
            addCriterion("member_audit_log_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdIsNotNull() {
            addCriterion("member_audit_log_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdEqualTo(Long value) {
            addCriterion("member_audit_log_id =", value, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdNotEqualTo(Long value) {
            addCriterion("member_audit_log_id <>", value, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdGreaterThan(Long value) {
            addCriterion("member_audit_log_id >", value, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("member_audit_log_id >=", value, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdLessThan(Long value) {
            addCriterion("member_audit_log_id <", value, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdLessThanOrEqualTo(Long value) {
            addCriterion("member_audit_log_id <=", value, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdIn(List<Long> values) {
            addCriterion("member_audit_log_id in", values, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdNotIn(List<Long> values) {
            addCriterion("member_audit_log_id not in", values, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdBetween(Long value1, Long value2) {
            addCriterion("member_audit_log_id between", value1, value2, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMemberAuditLogIdNotBetween(Long value1, Long value2) {
            addCriterion("member_audit_log_id not between", value1, value2, "memberAuditLogId");
            return (Criteria) this;
        }

        public Criteria andMenuTypeIsNull() {
            addCriterion("menu_type is null");
            return (Criteria) this;
        }

        public Criteria andMenuTypeIsNotNull() {
            addCriterion("menu_type is not null");
            return (Criteria) this;
        }

        public Criteria andMenuTypeEqualTo(Short value) {
            addCriterion("menu_type =", value, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeNotEqualTo(Short value) {
            addCriterion("menu_type <>", value, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeGreaterThan(Short value) {
            addCriterion("menu_type >", value, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("menu_type >=", value, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeLessThan(Short value) {
            addCriterion("menu_type <", value, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeLessThanOrEqualTo(Short value) {
            addCriterion("menu_type <=", value, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeIn(List<Short> values) {
            addCriterion("menu_type in", values, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeNotIn(List<Short> values) {
            addCriterion("menu_type not in", values, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeBetween(Short value1, Short value2) {
            addCriterion("menu_type between", value1, value2, "menuType");
            return (Criteria) this;
        }

        public Criteria andMenuTypeNotBetween(Short value1, Short value2) {
            addCriterion("menu_type not between", value1, value2, "menuType");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleIsNull() {
            addCriterion("notice_title is null");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleIsNotNull() {
            addCriterion("notice_title is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleEqualTo(String value) {
            addCriterion("notice_title =", value, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleNotEqualTo(String value) {
            addCriterion("notice_title <>", value, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleGreaterThan(String value) {
            addCriterion("notice_title >", value, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleGreaterThanOrEqualTo(String value) {
            addCriterion("notice_title >=", value, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleLessThan(String value) {
            addCriterion("notice_title <", value, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleLessThanOrEqualTo(String value) {
            addCriterion("notice_title <=", value, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleLike(String value) {
            addCriterion("notice_title like", value, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleNotLike(String value) {
            addCriterion("notice_title not like", value, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleIn(List<String> values) {
            addCriterion("notice_title in", values, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleNotIn(List<String> values) {
            addCriterion("notice_title not in", values, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleBetween(String value1, String value2) {
            addCriterion("notice_title between", value1, value2, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeTitleNotBetween(String value1, String value2) {
            addCriterion("notice_title not between", value1, value2, "noticeTitle");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIsNull() {
            addCriterion("notice_content is null");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIsNotNull() {
            addCriterion("notice_content is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeContentEqualTo(String value) {
            addCriterion("notice_content =", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotEqualTo(String value) {
            addCriterion("notice_content <>", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentGreaterThan(String value) {
            addCriterion("notice_content >", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentGreaterThanOrEqualTo(String value) {
            addCriterion("notice_content >=", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLessThan(String value) {
            addCriterion("notice_content <", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLessThanOrEqualTo(String value) {
            addCriterion("notice_content <=", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentLike(String value) {
            addCriterion("notice_content like", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotLike(String value) {
            addCriterion("notice_content not like", value, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentIn(List<String> values) {
            addCriterion("notice_content in", values, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotIn(List<String> values) {
            addCriterion("notice_content not in", values, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentBetween(String value1, String value2) {
            addCriterion("notice_content between", value1, value2, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andNoticeContentNotBetween(String value1, String value2) {
            addCriterion("notice_content not between", value1, value2, "noticeContent");
            return (Criteria) this;
        }

        public Criteria andIsReadIsNull() {
            addCriterion("is_read is null");
            return (Criteria) this;
        }

        public Criteria andIsReadIsNotNull() {
            addCriterion("is_read is not null");
            return (Criteria) this;
        }

        public Criteria andIsReadEqualTo(Short value) {
            addCriterion("is_read =", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotEqualTo(Short value) {
            addCriterion("is_read <>", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadGreaterThan(Short value) {
            addCriterion("is_read >", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadGreaterThanOrEqualTo(Short value) {
            addCriterion("is_read >=", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadLessThan(Short value) {
            addCriterion("is_read <", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadLessThanOrEqualTo(Short value) {
            addCriterion("is_read <=", value, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadIn(List<Short> values) {
            addCriterion("is_read in", values, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotIn(List<Short> values) {
            addCriterion("is_read not in", values, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadBetween(Short value1, Short value2) {
            addCriterion("is_read between", value1, value2, "isRead");
            return (Criteria) this;
        }

        public Criteria andIsReadNotBetween(Short value1, Short value2) {
            addCriterion("is_read not between", value1, value2, "isRead");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIsNull() {
            addCriterion("notice_type is null");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIsNotNull() {
            addCriterion("notice_type is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeEqualTo(Short value) {
            addCriterion("notice_type =", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotEqualTo(Short value) {
            addCriterion("notice_type <>", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeGreaterThan(Short value) {
            addCriterion("notice_type >", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("notice_type >=", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLessThan(Short value) {
            addCriterion("notice_type <", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLessThanOrEqualTo(Short value) {
            addCriterion("notice_type <=", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIn(List<Short> values) {
            addCriterion("notice_type in", values, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotIn(List<Short> values) {
            addCriterion("notice_type not in", values, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeBetween(Short value1, Short value2) {
            addCriterion("notice_type between", value1, value2, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotBetween(Short value1, Short value2) {
            addCriterion("notice_type not between", value1, value2, "noticeType");
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