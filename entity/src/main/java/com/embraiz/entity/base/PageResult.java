package com.embraiz.entity.base;

import java.util.Collection;

public interface PageResult extends PageNavigator {
    int DEFAULT_PAGE_SIZE = 10;

    Collection<?> getItems();
}
