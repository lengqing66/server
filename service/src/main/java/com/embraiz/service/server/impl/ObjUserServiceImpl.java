package com.embraiz.service.server.impl;

import com.alibaba.fastjson.JSONObject;
import com.embraiz.component.DesUtils;
import com.embraiz.component.Util;
import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.server.ObjUser;
import com.embraiz.entity.server.ObjUserRole;
import com.embraiz.entity.vo.VoUserRole;
import com.embraiz.repository.server.ObjUserRepository;
import com.embraiz.repository.server.ObjUserRoleRepository;
import com.embraiz.service.server.ObjUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class ObjUserServiceImpl implements ObjUserService {

    @Resource
    ObjUserRepository objUserRepository;

    @Resource
    ObjUserRoleRepository objUserRoleRepository;

    @Override
    public ObjUser getObjUserById(Integer userId) {
        return objUserRepository.findById(userId).orElse(null);
    }

    @Override
    public VoUserRole getObjUserByName(String userName) {
        return objUserRepository.findByName(userName);
    }

    @Override
    public ObjUser getObjUserByEmail(String email) {
        return objUserRepository.findByEmail(email);
    }

    @Override
    public VoUserRole getObjUserByNameInView(String userName) {
        return objUserRepository.findByNameInView(userName);
    }

    @Override
    public PageResult getTableList(String searchData, int currentPage, int displayCount, String sortBy, String sortOrder) {
        if (searchData != null && !"".equals(searchData)) {
            PageResult pageResult = objUserRepository.getTableList(searchData, currentPage, displayCount, sortBy, sortOrder);
            return pageResult;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public Map<String, Object> save(String formData) {
        Map<String, Object> dataMap = new HashMap<>();
        ObjUser cmsUser = JSONObject.toJavaObject(JSONObject.parseObject(formData), ObjUser.class);
        if (cmsUser == null) {
            dataMap.put("result", false);
            return dataMap;
        }

        if (cmsUser.getUserName() == null || "".equals(cmsUser.getUserName())) {
            dataMap.put("result", false);
            dataMap.put("msg", "User name is required.");
            return dataMap;
        } else if (cmsUser.getUserId() == null) {
            // check same by create
            VoUserRole cmsUser1 = objUserRepository.findByName(cmsUser.getUserName());
            if (cmsUser1 != null) {
                dataMap.put("result", false);
                dataMap.put("msg", "The user name already exists.");
                return dataMap;
            }

            // check email by create
            ObjUser cmsUser2 = objUserRepository.findByEmail(cmsUser.getEmail());
            if (cmsUser2 != null) {
                dataMap.put("result", false);
                dataMap.put("msg", "The email already exists.");
                return dataMap;
            }
        }

        try {
            // create password
            String password = Util.getRandomNumber(8);
            String passwordMD5 = Util.getMD5(password);
            cmsUser.setPassword(passwordMD5);
            cmsUser.setPasswordDes(DesUtils.encryption(password));
            cmsUser.setFirstLogin(1);
            cmsUser.setIsRemove(0);
            cmsUser.setStatus(0);
            ObjUser cmsUserDB = objUserRepository.save(cmsUser);

            // set default role
            ObjUserRole cmsUserRole = new ObjUserRole();
            cmsUserRole.setRoleId(new Integer("4"));
            cmsUserRole.setUserId(cmsUserDB.getUserId());
            objUserRoleRepository.save(cmsUserRole);

            dataMap.put("result", true);
            dataMap.put("user", cmsUserDB);
            return dataMap;
        } catch (Exception e) {
            dataMap.put("result", false);
            return dataMap;
        }
    }

    @Override
    @Transactional
    public Map<String, Object> saveUserRole(String formData) {
        Map<String, Object> dataMap = new HashMap<>();
        ObjUserRole cmsUserRole = JSONObject.parseObject(formData, ObjUserRole.class);
        if (cmsUserRole == null) {
            dataMap.put("result", false);
            return dataMap;
        }
        try {
            ObjUserRole cmsUserRoleDB = objUserRoleRepository.save(cmsUserRole);
            dataMap.put("result", true);
            dataMap.put("userRole", cmsUserRoleDB);
            return dataMap;
        } catch (Exception e) {
            dataMap.put("result", false);
            return dataMap;
        }
    }

    @Override
    public Map<String, Object> getDetail(Integer userId) {
        Map<String, Object> dataMap = new HashMap<>();
        ObjUser cmsUser = objUserRepository.findById(userId).orElse(null);
        VoUserRole voUserRole = objUserRoleRepository.getVoUserRoleById(userId);
        dataMap.put("user", cmsUser);
        dataMap.put("userRole", voUserRole);
        return dataMap;
    }

    @Override
    @Transactional
    public Boolean delete(String ids) {
        try {
            return objUserRepository.deleteByIds(ids);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean activeUser(Integer userId) {
        if (objUserRepository.checkActive(userId)) {
            // 已经激活过
            return false;
        }
        return objUserRepository.activeUser(userId);
    }

    @Override
    @Transactional
    public void updateFirstLogin(Integer userId) {
        objUserRepository.updateFirstLogin(userId);
    }

    @Override
    @Transactional
    public Map<String, Object> changePassword(Integer userId, String password) {
        Map<String, Object> dataMap = new HashMap<>();
        ObjUser cmsUser = objUserRepository.findById(userId).orElse(null);
        if (cmsUser == null) {
            dataMap.put("result", false);
            return dataMap;
        }
        try {
            cmsUser.setPassword(Util.getMD5(password));
            cmsUser.setPasswordDes(DesUtils.encryption(password));
            objUserRepository.save(cmsUser);
            dataMap.put("result", true);
        } catch (Exception e) {
            dataMap.put("result", false);

        }
        return dataMap;
    }

    @Override
    public Map<String, Object> comparePassword(Integer userId, String password) {
        Map<String, Object> dataMap = new HashMap<>();
        ObjUser cmsUser = objUserRepository.findById(userId).orElse(null);
        if (cmsUser == null) {
            dataMap.put("result", false);
        }
        String passwordMD5 = Util.getMD5(password);
        if (passwordMD5.equals(cmsUser.getPassword())) {
            dataMap.put("result", true);
        } else {
            dataMap.put("result", false);
            dataMap.put("msg", "passwordMismatch");
        }
        return dataMap;
    }

    @Override
    public Map<String, Object> forgotPassword(String email) {
        Map<String, Object> dataMap = new HashMap<>();
        ObjUser cmsUser = objUserRepository.findByEmail(email);
        if (cmsUser == null) {
            dataMap.put("result", false);
            dataMap.put("msg", "该邮箱地址找不到对应的账号");
            return dataMap;
        }

        dataMap.put("result", true);
        dataMap.put("cmsUser", cmsUser);
        return dataMap;
    }
}
