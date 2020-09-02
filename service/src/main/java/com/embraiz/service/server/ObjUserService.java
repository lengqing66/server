package com.embraiz.service.server;

import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.server.ObjUser;
import com.embraiz.entity.vo.VoUserRole;

import java.util.Map;

public interface ObjUserService {

    ObjUser getObjUserById(Integer id);

    VoUserRole getObjUserByName(String userName);

    ObjUser getObjUserByEmail(String email);

    VoUserRole getObjUserByNameInView(String userName);

    PageResult getTableList(String searchData, int currentPage, int displayCount, String sortBy, String sortOrder);

    Map<String, Object> save(String formData);

    Map<String, Object> saveUserRole(String formData);

    Map<String, Object> getDetail(Integer userId);

    Boolean delete(String ids);

    Boolean activeUser(Integer userId);

    void updateFirstLogin(Integer userId);

    Map<String,Object> changePassword(Integer userId, String password);

    Map<String,Object> comparePassword(Integer userId, String password);

    Map<String,Object> forgotPassword(String email);
}
