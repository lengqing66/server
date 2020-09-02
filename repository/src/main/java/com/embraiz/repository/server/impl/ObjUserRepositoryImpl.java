package com.embraiz.repository.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.embraiz.component.DateUtil;
import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.base.SearchMap;
import com.embraiz.entity.base.UpdateMap;
import com.embraiz.entity.server.ObjUser;
import com.embraiz.entity.vo.VoUserRole;
import com.embraiz.repository.base.BaseRepositoryImpl;
import com.embraiz.repository.server.ObjUserRepository;

import javax.persistence.EntityManager;

public class ObjUserRepositoryImpl extends BaseRepositoryImpl<ObjUser, Integer> implements ObjUserRepository {

    private final EntityManager entityManager;

    public ObjUserRepositoryImpl(EntityManager entityManager) {
        super(ObjUser.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public VoUserRole findByName(String userName) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("userName", userName);
        searchMap.eq("status", 1);
        return (VoUserRole) findObject(VoUserRole.class, searchMap);
    }

    @Override
    public ObjUser findByEmail(String email) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("email", email);
        searchMap.eq("status", 1);
        return (ObjUser) findObject(ObjUser.class, searchMap);
    }

    @Override
    public ObjUser findByNameLogin(String userName) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("userName", userName);
        return (ObjUser) findObject(ObjUser.class, searchMap);
    }

    @Override
    public ObjUser findByEmailLogin(String email) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("email", email);
        return (ObjUser) findObject(ObjUser.class, searchMap);
    }

    @Override
    public VoUserRole findByNameInView(String userName) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("userName", userName);
        searchMap.eq("status", 1);
        return (VoUserRole) findObject(VoUserRole.class, searchMap);
    }

    @Override
    public PageResult getTableList(String formData, int currentPage, int displayCount, String sortBy, String sortOrder) {
        VoUserRole voUserRole = JSONObject.parseObject(formData, VoUserRole.class);
        SearchMap searchMap = new SearchMap();
        if (voUserRole.getUserName() != null && !"".equals(voUserRole.getUserName())) {
            searchMap.like("userName", voUserRole.getUserName());
        }
        if (voUserRole.getRoleId() != null && !"".equals(voUserRole.getRoleId())) {
            searchMap.like("roleId", voUserRole.getRoleId());
        }
        if (sortBy != null && !"".equals(sortBy)) {
            if ("desc".equals(sortOrder)) {
                searchMap.desc(sortBy);
            } else {
                searchMap.asc(sortBy);
            }
        }
        return findObjectsForPage(VoUserRole.class, searchMap, currentPage, displayCount);
    }

    @Override
    public Boolean deleteByIds(String ids) {
        UpdateMap updateMap = new UpdateMap();
        String[] idArr = ids.split(",");
        Integer[] integers = new Integer[idArr.length];
        for (int i = 0; i < idArr.length; i++) {
            integers[i] = new Integer(idArr[i]);
        }
        updateMap.upd("status", 0);
        updateMap.upd("isRemove", 1);
        updateMap.in("userId", integers);
        return updateHandle(updateMap) > 0;
    }

    @Override
    public Boolean checkActive(Integer userId) {
        SearchMap searchMap = new SearchMap();
        searchMap.notNull("activeDate");
        searchMap.eq("userId", userId);
        return findObjects(ObjUser.class, searchMap).size() > 0;
    }

    @Override
    public Boolean activeUser(Integer userId) {
        UpdateMap updateMap = new UpdateMap();
        updateMap.upd("status", 1);
        updateMap.upd("activeDate", DateUtil.getCurrentDate());
        updateMap.eq("userId", userId);
        return updateHandle(updateMap) > 0;
    }

    @Override
    public void updateFirstLogin(Integer userId) {
        UpdateMap updateMap = new UpdateMap();
        updateMap.upd("firstLogin", new Integer(0));
        updateMap.eq("userId", userId);
        updateHandle(updateMap);
    }


}
