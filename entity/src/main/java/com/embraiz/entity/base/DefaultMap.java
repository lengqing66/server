package com.embraiz.entity.base;

import java.util.ArrayList;
import java.util.List;

public class DefaultMap {
    public static final String TYPE_EQ = "=";
    public static final String TYPE_NOT_EQ = "<>";
    public static final String TYPE_GT = ">";
    public static final String TYPE_GE = ">=";
    public static final String TYPE_LT = "<";
    public static final String TYPE_LE = "<=";
    public static final String TYPE_BETWEEN = "between";
    public static final String TYPE_LIKE = "like";
    public static final String TYPE_NOT_LIKE = "not like";
    public static final String TYPE_IN = "in";
    public static final String TYPE_NOT_IN = "not in";
    public static final String TYPE_NULL = "is null";
    public static final String TYPE_NOT_NULL = "is not null";
    public static final String TYPE_OR_EQ = "or eq";
    public static final String TYPE_OR_LIKE = "or like";
    private List<SearchParameter> parameters;

    public DefaultMap() {
        this.parameters = new ArrayList<>();
    }

    public void eq(String name, Object value) {
        if (value != null) {
            this.parameters.add(new SearchParameter(name, value, "="));
        }

    }

    public void notEq(String name, Object value) {
        if (value != null) {
            this.parameters.add(new SearchParameter(name, value, "<>"));
        }

    }

    public void gt(String name, Object value) {
        if (value != null) {
            this.parameters.add(new SearchParameter(name, value, ">"));
        }

    }

    public void ge(String name, Object value) {
        if (value != null) {
            this.parameters.add(new SearchParameter(name, value, ">="));
        }

    }

    public void lt(String name, Object value) {
        if (value != null) {
            this.parameters.add(new SearchParameter(name, value, "<"));
        }

    }

    public void le(String name, Object value) {
        if (value != null) {
            this.parameters.add(new SearchParameter(name, value, "<="));
        }

    }

    public void between(String name, Object startValue, Object endValue) {
        if (startValue != null && endValue != null) {
            Object[] values = new Object[]{startValue, endValue};
            this.parameters.add(new SearchParameter(name, values, "between"));
        }

    }

    public void like(String name, Object value) {
        if (value != null && isNotBlank(value.toString())) {
            this.parameters.add(new SearchParameter(name, value.toString().trim(), "like"));
        }

    }

    public void notLike(String name, Object value) {
        if (value != null && isNotBlank(value.toString())) {
            this.parameters.add(new SearchParameter(name, value.toString().trim(), "not like"));
        }

    }

    public void in(String name, Object[] values) {
        if (values != null && values.length > 0) {
            this.parameters.add(new SearchParameter(name, values, "in"));
        }

    }

    public void notIn(String name, Object[] values) {
        if (values != null && values.length > 0) {
            this.parameters.add(new SearchParameter(name, values, "not in"));
        }

    }

    public void isNull(String name) {
        this.parameters.add(new SearchParameter(name, null, "is null"));
    }

    public void notNull(String name) {
        this.parameters.add(new SearchParameter(name, null, "is not null"));
    }

    public void orEq(String name, Object[] values) {
        if (values != null && values.length > 0) {
            this.parameters.add(new SearchParameter(name, values, "or eq"));
        }

    }

    public void orLike(String name, Object[] values) {
        if (values != null && values.length > 0) {
            this.parameters.add(new SearchParameter(name, values, "or like"));
        }

    }

    public List<SearchParameter> getParameters() {
        return this.parameters;
    }

    public void setParameters(List<SearchParameter> parameters) {
        this.parameters = parameters;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        } else {
            int strLen = str.length();
            if (strLen == 0) {
                return true;
            } else {
                for (int i = 0; i < strLen; ++i) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

}
