package com.embraiz.repository.server.impl;

import com.embraiz.entity.base.SearchMap;
import com.embraiz.entity.server.ObjUserRole;
import com.embraiz.entity.vo.VoUserRole;
import com.embraiz.repository.base.BaseRepositoryImpl;
import com.embraiz.repository.server.ObjUserRoleRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class ObjUserRoleRepositoryImpl extends BaseRepositoryImpl<ObjUserRole, Integer> implements ObjUserRoleRepository {

    private final EntityManager entityManager;

    public ObjUserRoleRepositoryImpl(EntityManager entityManager) {
        super(ObjUserRole.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<VoUserRole> findByUserId(Integer userId) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("userId", userId);
        searchMap.eq("status", 1);
        return findObjects(VoUserRole.class, searchMap);
    }

    @Override
    public ObjUserRole getByUserId(Integer userId) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("userId", userId);
        return (ObjUserRole) findObject(ObjUserRole.class, searchMap);
    }

    @Override
    public VoUserRole getVoUserRoleById(Integer userId) {
        SearchMap searchMap = new SearchMap();
        searchMap.eq("userId", userId);
        return (VoUserRole) findObject(VoUserRole.class, searchMap);
    }
}
