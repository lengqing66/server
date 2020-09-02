package com.embraiz.repository.server;

import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.server.ObjRole;
import com.embraiz.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjRoleRepository extends BaseRepository<ObjRole, Integer> {

    ObjRole getObjRoleByName(String roleName);

    PageResult getTableList(String formData, int currentPage, int displayCount, String sortBy, String sortOrder);

    List<ObjRole> getList();

    Boolean deleteByIds(String ids);

}
