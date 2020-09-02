package com.embraiz.entity.base;

import java.io.Serializable;

@SuppressWarnings("unused")
public interface PageNavigator extends Serializable {
    long getTotalCount();

    int getPageSize();

    int getCurrentPageNum();

    boolean isHasNext();

    boolean isHasPrevious();

    int getTotalPageCount();

    int getCurrentPageCount();
}
