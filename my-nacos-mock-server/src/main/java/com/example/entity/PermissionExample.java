package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PermissionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PermissionExample() {
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

        public Criteria andPermissionIdIsNull() {
            addCriterion("PERMISSION_ID is null");
            return (Criteria) this;
        }

        public Criteria andPermissionIdIsNotNull() {
            addCriterion("PERMISSION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPermissionIdEqualTo(String value) {
            addCriterion("PERMISSION_ID =", value, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdNotEqualTo(String value) {
            addCriterion("PERMISSION_ID <>", value, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdGreaterThan(String value) {
            addCriterion("PERMISSION_ID >", value, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdGreaterThanOrEqualTo(String value) {
            addCriterion("PERMISSION_ID >=", value, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdLessThan(String value) {
            addCriterion("PERMISSION_ID <", value, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdLessThanOrEqualTo(String value) {
            addCriterion("PERMISSION_ID <=", value, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdLike(String value) {
            addCriterion("PERMISSION_ID like", value, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdNotLike(String value) {
            addCriterion("PERMISSION_ID not like", value, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdIn(List<String> values) {
            addCriterion("PERMISSION_ID in", values, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdNotIn(List<String> values) {
            addCriterion("PERMISSION_ID not in", values, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdBetween(String value1, String value2) {
            addCriterion("PERMISSION_ID between", value1, value2, "permissionId");
            return (Criteria) this;
        }

        public Criteria andPermissionIdNotBetween(String value1, String value2) {
            addCriterion("PERMISSION_ID not between", value1, value2, "permissionId");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNull() {
            addCriterion("RESOURCE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andResourceNameIsNotNull() {
            addCriterion("RESOURCE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andResourceNameEqualTo(String value) {
            addCriterion("RESOURCE_NAME =", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotEqualTo(String value) {
            addCriterion("RESOURCE_NAME <>", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThan(String value) {
            addCriterion("RESOURCE_NAME >", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameGreaterThanOrEqualTo(String value) {
            addCriterion("RESOURCE_NAME >=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThan(String value) {
            addCriterion("RESOURCE_NAME <", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLessThanOrEqualTo(String value) {
            addCriterion("RESOURCE_NAME <=", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameLike(String value) {
            addCriterion("RESOURCE_NAME like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotLike(String value) {
            addCriterion("RESOURCE_NAME not like", value, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameIn(List<String> values) {
            addCriterion("RESOURCE_NAME in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotIn(List<String> values) {
            addCriterion("RESOURCE_NAME not in", values, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameBetween(String value1, String value2) {
            addCriterion("RESOURCE_NAME between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andResourceNameNotBetween(String value1, String value2) {
            addCriterion("RESOURCE_NAME not between", value1, value2, "resourceName");
            return (Criteria) this;
        }

        public Criteria andActionIsNull() {
            addCriterion("ACTION is null");
            return (Criteria) this;
        }

        public Criteria andActionIsNotNull() {
            addCriterion("ACTION is not null");
            return (Criteria) this;
        }

        public Criteria andActionEqualTo(String value) {
            addCriterion("ACTION =", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotEqualTo(String value) {
            addCriterion("ACTION <>", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionGreaterThan(String value) {
            addCriterion("ACTION >", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionGreaterThanOrEqualTo(String value) {
            addCriterion("ACTION >=", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLessThan(String value) {
            addCriterion("ACTION <", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLessThanOrEqualTo(String value) {
            addCriterion("ACTION <=", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionLike(String value) {
            addCriterion("ACTION like", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotLike(String value) {
            addCriterion("ACTION not like", value, "action");
            return (Criteria) this;
        }

        public Criteria andActionIn(List<String> values) {
            addCriterion("ACTION in", values, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotIn(List<String> values) {
            addCriterion("ACTION not in", values, "action");
            return (Criteria) this;
        }

        public Criteria andActionBetween(String value1, String value2) {
            addCriterion("ACTION between", value1, value2, "action");
            return (Criteria) this;
        }

        public Criteria andActionNotBetween(String value1, String value2) {
            addCriterion("ACTION not between", value1, value2, "action");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("VERSION is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("VERSION is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Double value) {
            addCriterion("VERSION =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Double value) {
            addCriterion("VERSION <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Double value) {
            addCriterion("VERSION >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Double value) {
            addCriterion("VERSION >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Double value) {
            addCriterion("VERSION <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Double value) {
            addCriterion("VERSION <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Double> values) {
            addCriterion("VERSION in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Double> values) {
            addCriterion("VERSION not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Double value1, Double value2) {
            addCriterion("VERSION between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Double value1, Double value2) {
            addCriterion("VERSION not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andTimestampIsNull() {
            addCriterion("TIMESTAMP is null");
            return (Criteria) this;
        }

        public Criteria andTimestampIsNotNull() {
            addCriterion("TIMESTAMP is not null");
            return (Criteria) this;
        }

        public Criteria andTimestampEqualTo(Date value) {
            addCriterion("TIMESTAMP =", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotEqualTo(Date value) {
            addCriterion("TIMESTAMP <>", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampGreaterThan(Date value) {
            addCriterion("TIMESTAMP >", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("TIMESTAMP >=", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampLessThan(Date value) {
            addCriterion("TIMESTAMP <", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampLessThanOrEqualTo(Date value) {
            addCriterion("TIMESTAMP <=", value, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampIn(List<Date> values) {
            addCriterion("TIMESTAMP in", values, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotIn(List<Date> values) {
            addCriterion("TIMESTAMP not in", values, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampBetween(Date value1, Date value2) {
            addCriterion("TIMESTAMP between", value1, value2, "timestamp");
            return (Criteria) this;
        }

        public Criteria andTimestampNotBetween(Date value1, Date value2) {
            addCriterion("TIMESTAMP not between", value1, value2, "timestamp");
            return (Criteria) this;
        }

        public Criteria andIdParentIsNull() {
            addCriterion("ID_PARENT is null");
            return (Criteria) this;
        }

        public Criteria andIdParentIsNotNull() {
            addCriterion("ID_PARENT is not null");
            return (Criteria) this;
        }

        public Criteria andIdParentEqualTo(String value) {
            addCriterion("ID_PARENT =", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentNotEqualTo(String value) {
            addCriterion("ID_PARENT <>", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentGreaterThan(String value) {
            addCriterion("ID_PARENT >", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentGreaterThanOrEqualTo(String value) {
            addCriterion("ID_PARENT >=", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentLessThan(String value) {
            addCriterion("ID_PARENT <", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentLessThanOrEqualTo(String value) {
            addCriterion("ID_PARENT <=", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentLike(String value) {
            addCriterion("ID_PARENT like", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentNotLike(String value) {
            addCriterion("ID_PARENT not like", value, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentIn(List<String> values) {
            addCriterion("ID_PARENT in", values, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentNotIn(List<String> values) {
            addCriterion("ID_PARENT not in", values, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentBetween(String value1, String value2) {
            addCriterion("ID_PARENT between", value1, value2, "idParent");
            return (Criteria) this;
        }

        public Criteria andIdParentNotBetween(String value1, String value2) {
            addCriterion("ID_PARENT not between", value1, value2, "idParent");
            return (Criteria) this;
        }

        public Criteria andNbrLevelIsNull() {
            addCriterion("NBR_LEVEL is null");
            return (Criteria) this;
        }

        public Criteria andNbrLevelIsNotNull() {
            addCriterion("NBR_LEVEL is not null");
            return (Criteria) this;
        }

        public Criteria andNbrLevelEqualTo(Integer value) {
            addCriterion("NBR_LEVEL =", value, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelNotEqualTo(Integer value) {
            addCriterion("NBR_LEVEL <>", value, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelGreaterThan(Integer value) {
            addCriterion("NBR_LEVEL >", value, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("NBR_LEVEL >=", value, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelLessThan(Integer value) {
            addCriterion("NBR_LEVEL <", value, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelLessThanOrEqualTo(Integer value) {
            addCriterion("NBR_LEVEL <=", value, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelIn(List<Integer> values) {
            addCriterion("NBR_LEVEL in", values, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelNotIn(List<Integer> values) {
            addCriterion("NBR_LEVEL not in", values, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelBetween(Integer value1, Integer value2) {
            addCriterion("NBR_LEVEL between", value1, value2, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("NBR_LEVEL not between", value1, value2, "nbrLevel");
            return (Criteria) this;
        }

        public Criteria andNbrOrderIsNull() {
            addCriterion("NBR_ORDER is null");
            return (Criteria) this;
        }

        public Criteria andNbrOrderIsNotNull() {
            addCriterion("NBR_ORDER is not null");
            return (Criteria) this;
        }

        public Criteria andNbrOrderEqualTo(Long value) {
            addCriterion("NBR_ORDER =", value, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderNotEqualTo(Long value) {
            addCriterion("NBR_ORDER <>", value, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderGreaterThan(Long value) {
            addCriterion("NBR_ORDER >", value, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderGreaterThanOrEqualTo(Long value) {
            addCriterion("NBR_ORDER >=", value, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderLessThan(Long value) {
            addCriterion("NBR_ORDER <", value, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderLessThanOrEqualTo(Long value) {
            addCriterion("NBR_ORDER <=", value, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderIn(List<Long> values) {
            addCriterion("NBR_ORDER in", values, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderNotIn(List<Long> values) {
            addCriterion("NBR_ORDER not in", values, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderBetween(Long value1, Long value2) {
            addCriterion("NBR_ORDER between", value1, value2, "nbrOrder");
            return (Criteria) this;
        }

        public Criteria andNbrOrderNotBetween(Long value1, Long value2) {
            addCriterion("NBR_ORDER not between", value1, value2, "nbrOrder");
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