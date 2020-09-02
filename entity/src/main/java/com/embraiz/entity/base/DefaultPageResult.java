package com.embraiz.entity.base;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("unused")
public class DefaultPageResult implements PageResult {
    private long totalCount;
    private int pageSize;
    private int currentPageNum;
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalPageCount;
    private int currentPageCount;
    private Collection<?> items = new ArrayList();

    public DefaultPageResult(Collection<?> items) {
        long totalCount = (long) (items == null ? 0 : items.size());
        long startIndex = 0L;
        int pageSize = items == null ? 0 : items.size();
        this.initial(items, totalCount, startIndex, pageSize);
    }

    public DefaultPageResult(Collection<?> items, long totalCount, long startIndex, int pageSize) {
        this.initial(items, totalCount, startIndex, pageSize);
    }

    private void initial(Collection<?> items, long totalCount, long startIndex, int pageSize) {
        this.items = (Collection) (items == null ? new ArrayList() : items);
        this.totalCount = totalCount;
        if (pageSize <= 0) {
            pageSize = 10;
        }

        if (this.totalCount == 0L) {
            this.currentPageNum = 0;
            this.pageSize = 0;
        } else {
            this.pageSize = pageSize;
            this.currentPageNum = startIndexToPageNum(startIndex, pageSize);
        }

        this.totalPageCount = (int) ((totalCount + (long) pageSize - 1L) / (long) pageSize);
        this.hasPrevious = this.currentPageNum != 1;
        this.hasNext = this.currentPageNum != this.totalPageCount;
        this.currentPageCount = this.items.size();
    }

    public static int startIndexToPageNum(long startIndex, int pageSize) {
        if (pageSize <= 0) {
            pageSize = 10;
        }

        if (startIndex < 0L) {
            startIndex = 0L;
        }

        long totalSize = startIndex + 1L;
        return (int) (totalSize % (long) pageSize == 0L ? totalSize / (long) pageSize : totalSize / (long) pageSize + 1L);
    }

    public static long pageNumToStartIndex(long totalCount, int pageNum, int pageSize) {
        if (pageNum > 1 && totalCount > 0L) {
            int startIndex = (pageNum - 1) * pageSize;
            return (long) startIndex < totalCount ? (long) startIndex : (totalCount - 1L) / (long) pageSize * (long) pageSize;
        } else {
            return 0L;
        }
    }

    public static int adjustPageNum(long totalCount, int pageNum, int pageSize) {
        return startIndexToPageNum(pageNumToStartIndex(totalCount, pageNum, pageSize), pageSize);
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageNum() {
        return this.currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageCount() {
        return this.totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public Collection<?> getItems() {
        return this.items;
    }

    public void setItems(Collection<?> items) {
        this.items = items;
    }

    public boolean isHasNext() {
        return this.hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return this.hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public int getCurrentPageCount() {
        return this.currentPageCount;
    }

    public void setCurrentPageCount(int currentPageCount) {
        this.currentPageCount = currentPageCount;
    }
}
