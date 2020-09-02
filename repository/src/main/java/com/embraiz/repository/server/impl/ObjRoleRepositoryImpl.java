package com.embraiz.repository.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.base.SearchMap;
import com.embraiz.entity.base.UpdateMap;
import com.embraiz.entity.server.ObjRole;
import com.embraiz.repository.base.BaseRepositoryImpl;
import com.embraiz.repository.server.ObjRoleRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class ObjRoleRepositoryImpl extends BaseRepositoryImpl<ObjRole, Integer> implements ObjRoleRepository {

    private final EntityManager entityManager;

    public ObjRoleRepositoryImpl(EntityManager entityManager) {
        super(ObjRole.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public ObjRole getObjRoleByName(String roleName) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("roleName", roleName);
        searchMap.eq("status", 1);
        return (ObjRole) findObject(ObjRole.class, searchMap);
    }

    @Override
    public PageResult getTableList(String formData, int currentPage, int displayCount, String sortBy, String sortOrder) {
        ObjRole cmsRole = JSONObject.parseObject(formData, ObjRole.class);
        SearchMap searchMap = new SearchMap();
        if (cmsRole.getRoleName() != null && !"".equals(cmsRole.getRoleName())) {
            searchMap.eq("roleName", cmsRole.getRoleName());
        }
        searchMap.eq("status", 1);
        if (sortBy != null && !"".equals(sortBy)) {
            if ("desc".equals(sortOrder)) {
                searchMap.desc(sortBy);
            } else {
                searchMap.asc(sortBy);
            }
        }
        return findObjectsForPage(ObjRole.class, searchMap, currentPage, displayCount);
    }

    @Override
    public List<ObjRole> getList() {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("status", 1);
        return findObjects(ObjRole.class, searchMap);
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
        updateMap.in("roleId", integers);
        return updateHandle(updateMap) > 0;
    }

}
