package com.embraiz.repository.server;

import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.server.ObjUser;
import com.embraiz.entity.vo.VoUserRole;
import com.embraiz.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ObjUserRepository extends BaseRepository<ObjUser, Integer> {

    VoUserRole findByName(String userName);

    ObjUser findByEmail(String email);

    ObjUser findByNameLogin(String userName);

    ObjUser findByEmailLogin(String email);

    VoUserRole findByNameInView(String userName);

    PageResult getTableList(String formData, int currentPage, int displayCount, String sortBy, String sortOrder);

    Boolean deleteByIds(String ids);

    Boolean checkActive(Integer userId);

    Boolean activeUser(Integer userId);

    void updateFirstLogin(Integer userId);
}
