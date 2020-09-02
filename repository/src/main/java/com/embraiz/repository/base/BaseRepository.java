package com.embraiz.repository.base;

import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.base.SearchMap;
import com.embraiz.entity.base.UpdateMap;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    Object findObject(Class<?> cls, SearchMap searchMap);

    List findObjects(SearchMap searchMap);

    List findObjects(Class<?> cls, SearchMap searchMap);

    List findObjects(SearchMap searchMap, int pageNum, int pageSize);

    List findObjects(Class<?> cls, SearchMap searchMap, int pageNum, int pageSize);

    PageResult findObjectsForPage(SearchMap searchMap, int pageNum, int pageSize);

    PageResult findObjectsForPage(Class<?> cls, SearchMap searchMap, int pageNum, int pageSize);

    long getCount(SearchMap searchMap);

    long getCount(Class<?> cls, SearchMap searchMap);

    int updateHandle(UpdateMap updateMap);
}
