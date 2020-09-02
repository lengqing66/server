package com.embraiz.repository.server;

import com.embraiz.entity.server.ObjUserRole;
import com.embraiz.entity.vo.VoUserRole;
import com.embraiz.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjUserRoleRepository extends BaseRepository<ObjUserRole, Integer> {

    List<VoUserRole> findByUserId(Integer userId);

    ObjUserRole getByUserId(Integer userId);

    VoUserRole getVoUserRoleById(Integer userId);
}
