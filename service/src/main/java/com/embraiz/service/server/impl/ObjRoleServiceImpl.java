package com.embraiz.service.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.server.ObjRole;
import com.embraiz.repository.server.ObjRoleRepository;
import com.embraiz.service.server.ObjRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ObjRoleServiceImpl implements ObjRoleService {

    @Resource
    ObjRoleRepository objRoleRepository;


    @Override
    public ObjRole getObjRoleById(Integer id) {
        return objRoleRepository.findById(id).orElse(null);
    }

    @Override
    public ObjRole getObjRoleByName(String name) {
        return objRoleRepository.getObjRoleByName(name);
    }

    @Override
    public PageResult getTableList(String searchData, int currentPage, int displayCount, String sortBy, String sortOrder) {
        if (searchData != null && !"".equals(searchData)) {
            PageResult pageResult = objRoleRepository.getTableList(searchData, currentPage, displayCount, sortBy, sortOrder);
            return pageResult;
        } else {
            return null;
        }
    }

    @Override
    public List<ObjRole> getList() {
        return objRoleRepository.getList();
    }

    @Override
    @Transactional
    public Map<String, Object> save(String formData) {
        Map<String, Object> dataMap = new HashMap<>();
        ObjRole cmsRole = JSONObject.toJavaObject(JSONObject.parseObject(formData), ObjRole.class);
        if (cmsRole == null) {
            dataMap.put("result", false);
            return dataMap;
        }

        if (cmsRole.getRoleName() == null || "".equals(cmsRole.getRoleName())) {
            dataMap.put("result", false);
            dataMap.put("msg", "Role name is required.");
            return dataMap;
        } else if (cmsRole.getRoleId() == null) {
            // check same by create
            ObjRole cmsRole1 = objRoleRepository.getObjRoleByName(cmsRole.getRoleName());
            if (cmsRole1 != null) {
                dataMap.put("result", false);
                dataMap.put("msg", "The role name already exists.");
                return dataMap;
            }
        }

        try {
            cmsRole.setStatus(1);
            ObjRole cmsRoleDB = objRoleRepository.save(cmsRole);
            dataMap.put("result", true);
            dataMap.put("role", cmsRoleDB);
            return dataMap;
        } catch (Exception e) {
            dataMap.put("result", false);
            return dataMap;
        }
    }


    @Override
    @Transactional
    public Boolean delete(String ids) {
        try {
            return objRoleRepository.deleteByIds(ids);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<String, Object> getDetail(Integer roleId) {
        Map<String, Object> dataMap = new HashMap<>();
        ObjRole cmsRole = objRoleRepository.findById(roleId).orElse(null);
        dataMap.put("role", cmsRole);
        return dataMap;
    }
}
