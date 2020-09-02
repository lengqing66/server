package com.embraiz.server.service;

import com.embraiz.entity.server.ObjUser;
import com.embraiz.entity.vo.VoUserRole;
import com.embraiz.repository.server.ObjUserRepository;
import com.embraiz.repository.server.ObjUserRoleRepository;
import com.embraiz.server.config.JwtUser;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtUserService implements UserDetailsService {

    @Resource
    ObjUserRepository objUserRepository;

    @Resource
    ObjUserRoleRepository objUserRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // 从数据库中取出用户信息
        ObjUser user = objUserRepository.findByNameLogin(userName);
        if (user == null) {
            // 根据Email查
            user = objUserRepository.findByEmailLogin(userName);
        }

        // 判断用户是否存在
        if (user == null || user.getIsRemove() == 1) {
            throw new BadCredentialsException("用户名不存在");
        }

        if(user.getStatus() == 0 && user.getActiveDate() == null){
            throw new BadCredentialsException("账户未激活");
        }

        //return new JwtUser(user.getUsername(), user.getPassword(), user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        // 添加权限
        List<VoUserRole> voUserRoleList = objUserRoleRepository.findByUserId(user.getUserId());
        for (VoUserRole voUserRole : voUserRoleList) {
            authorities.add(new SimpleGrantedAuthority(voUserRole.getRoleName()));
        }
        // 返回UserDetails实现类
        return new JwtUser(user.getUserId(), user.getUserName(), user.getPassword(), authorities);


    }
}
