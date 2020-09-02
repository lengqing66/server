package com.embraiz.service.server.impl;

import com.embraiz.entity.vo.VoUserRole;
import com.embraiz.repository.server.ObjUserRoleRepository;
import com.embraiz.service.server.ObjUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ObjUserRoleServiceImpl implements ObjUserRoleService {

    @Resource
    ObjUserRoleRepository objUserRoleRepository;

    @Override
    public List<VoUserRole> findByUserId(Integer userId) {
        return objUserRoleRepository.findByUserId(userId);
    }
}
