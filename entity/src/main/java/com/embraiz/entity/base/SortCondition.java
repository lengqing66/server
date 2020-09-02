package com.embraiz.entity.base;

@SuppressWarnings("unused")
public class SortCondition {
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    private String sortName;
    private String sortType;

    public SortCondition() {
    }

    public SortCondition(String sortName, String sortType) {
        this.sortName = sortName;
        this.sortType = sortType;
    }

    public String getSortName() {
        return this.sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortType() {
        return this.sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
