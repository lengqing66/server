package com.embraiz.entity.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SearchMap extends DefaultMap {
    protected List<String> selects;
    protected List<Max> maxes;
    protected List<String> groups;
    protected List<SortCondition> sorts;

    public SearchMap() {
        super();
        this.selects = new ArrayList<>();
        this.maxes = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.sorts = new ArrayList<>();
    }

    public SearchMap groupBy(String groupName) {
        if (isNotBlank(groupName)) {
            this.groups.add(groupName);
        }

        return this;
    }

    public SearchMap orderBy(String sortName, String sortType) {
        if (isNotBlank(sortName)) {
            this.sorts.add(new SortCondition(sortName, sortType));
        }

        return this;
    }

    public SearchMap asc(String sortName) {
        if (isNotBlank(sortName)) {
            this.sorts.add(new SortCondition(sortName, "asc"));
        }

        return this;
    }

    public SearchMap desc(String sortName) {
        if (isNotBlank(sortName)) {
            this.sorts.add(new SortCondition(sortName, "desc"));
        }

        return this;
    }

    public SearchMap select(String... names) {
        if (names != null && names.length > 0) {
            this.selects.addAll(Arrays.asList(names));
        }

        return this;
    }

    public List<String> getSelects() {
        return selects;
    }

    public void setSelects(List<String> selects) {
        this.selects = selects;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<SortCondition> getSorts() {
        return this.sorts;
    }

    public void setSorts(List<SortCondition> sorts) {
        this.sorts = sorts;
    }

    public SearchMap max(Max... maxes) {
        if (maxes != null && maxes.length > 0) {
            this.maxes.addAll(Arrays.asList(maxes));
        }

        return this;
    }

    public List<Max> getMaxes() {
        return maxes;
    }

    public void setMaxes(List<Max> maxes) {
        this.maxes = maxes;
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



