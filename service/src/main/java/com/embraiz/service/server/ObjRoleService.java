package com.embraiz.service.server;

import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.server.ObjRole;

import java.util.List;
import java.util.Map;

public interface ObjRoleService {

    ObjRole getObjRoleById(Integer id);

    ObjRole getObjRoleByName(String name);

    PageResult getTableList(String searchData, int currentPage, int displayCount, String sortBy, String sortOrder);

    List<ObjRole> getList();

    Map<String,Object> save(String formData);

    Boolean delete(String ids);

    Map<String,Object> getDetail(Integer roleId);

}
