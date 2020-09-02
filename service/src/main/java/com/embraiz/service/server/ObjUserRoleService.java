package com.embraiz.service.server;

import com.embraiz.entity.vo.VoUserRole;

import java.util.List;

public interface ObjUserRoleService {

    List<VoUserRole> findByUserId(Integer userId);
}
