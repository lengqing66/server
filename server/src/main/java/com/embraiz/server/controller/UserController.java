package com.embraiz.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.embraiz.component.DesUtils;
import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.common.Constant;
import com.embraiz.entity.server.ObjUser;
import com.embraiz.entity.vo.VoUserRole;
import com.embraiz.server.config.JwtUser;
import com.embraiz.server.utils.JwtTokenUtil;
import com.embraiz.service.common.MailService;
import com.embraiz.service.server.ObjUserService;
import io.swagger.annotations.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理相关接口")
public class UserController {

    @Resource
    ObjUserService objUserService;

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    JwtTokenUtil jwtTokenUtil;

    @Resource
    MailService mailService;

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return 操作结果
     * @throws AuthenticationException 错误信息
     */
    @PostMapping(value = "/login", params = {"userName", "password"})
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataTypeClass = String.class)
    })
    public HashMap<String, Object> getToken(String userName, String password) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            VoUserRole objUser = objUserService.getObjUserByName(userName);

            map.put("success", true);
            map.put("token", jwtTokenUtil.generateToken(userDetails));
            map.put("user", objUser);
            return map;
        } catch (Exception e) {
            map.put("success", false);
            String msg = "账户或者密码输入错误";
            if (!"Bad credentials".equals(e.getMessage())) {
                msg = e.getMessage();
            }
            map.put("msg", msg);
            return map;
        }
    }

    @RequestMapping("decrypt")
    @ApiIgnore
    public String decrypt(@RequestParam String pw) {
        if ("".equals(pw) || pw == null) return "";
        try {
            // Des 解密
            return DesUtils.decrypt(pw);
        } catch (Exception e) {
            return "";
        }
    }

    @GetMapping(value = "getUser")
    @ApiOperation(value = "获取用户信息")
    public Map<String, Object> getUser() {
        Map<String, Object> dataMap = new HashMap<>();
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (jwtUser == null) {
            dataMap.put("result", false);
            dataMap.put("msg", "noLogin");
            return dataMap;
        }
        VoUserRole voUserRole = objUserService.getObjUserByNameInView(jwtUser.getUsername());
        dataMap.put("user", voUserRole);
        return dataMap;
    }

    @PostMapping("getTableList")
    @ApiOperation(value = "获取用户列表分页信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchData", value = "搜索条件，使用User对象，无条件传{}", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "page", value = "当前页", dataTypeClass = Integer.class, defaultValue = "1", example = "1"),
            @ApiImplicitParam(name = "count", value = "当前显示数目", dataTypeClass = Integer.class, defaultValue = "15", example = "15"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataTypeClass = String.class, defaultValue = "", example = ""),
            @ApiImplicitParam(name = "order", value = "升/降序", dataTypeClass = String.class, defaultValue = "desc", example = "desc")
    })
    public PageResult getTableList(
            @RequestParam(value = "searchData") String searchData,
            @RequestParam(value = "page", defaultValue = "1") int currentPage,
            @RequestParam(value = "count", defaultValue = "15") int displayCount,
            @RequestParam(value = "sort", defaultValue = "") String sortBy,
            @RequestParam(value = "order", defaultValue = "desc") String sortOrder) {
        return objUserService.getTableList(searchData, currentPage, displayCount, sortBy, sortOrder);
    }

    @PostMapping("save")
    @ApiOperation(value = "保存用户", notes = "保存成功会发送激活邮件")
    @ApiImplicitParam(name = "formData", value = "User 对象字符串", dataTypeClass = String.class)
    public Map<String, Object> save(@RequestParam String formData) {
        Map<String, Object> dataMap = new HashMap<>();
        if (formData == null || "".equals(formData)) {
            dataMap.put("result", false);
            return dataMap;
        }

        dataMap = objUserService.save(formData);
        if ((Boolean) dataMap.get("result")) {
            ObjUser objUser = JSONObject.parseObject(JSONObject.toJSONString(dataMap.get("user")), ObjUser.class);
            String password = DesUtils.decrypt(objUser.getPasswordDes());
            // 要改激活链接
            String html = "<html><body><p>Dear " + objUser.getFirstName() + " " + objUser.getLastName() + "</p><br />" + "<p>Thank you for your registration.</p>" + "<p>Account: " + objUser.getUserName() + "</p>" + "<p>Email: " + objUser.getEmail() + "</p>" + "<p>Password: " + password + "</p><br />" + "<p>Click <a href='http://" + Constant.Domain + "/server/user/active/" + objUser.getUserId() + "' target='_blank'>here</a> to activate your account.</p>" + "</body><html>";
            boolean sendResult = mailService.sendWithHtml(objUser.getEmail(), "Account Activation Notification", html);
            dataMap.put("sendResult", sendResult);
        }
        return dataMap;
    }

    @GetMapping("getDetail/{userId}")
    @ApiOperation(value = "获取用户详情")
    @ApiImplicitParam(name = "userId", value = "用户ID", dataTypeClass = Integer.class, paramType = "path", example = "0")
    public Map<String, Object> getDetail(@PathVariable Integer userId) {
        if (userId == null) {
            return null;
        }
        return objUserService.getDetail(userId);
    }

    @PutMapping("delete")
    @ApiOperation(value = "删除用户，可批量删除", notes = "只是修改状态，并不是真实删除")
    @ApiImplicitParam(name = "ids", value = "多个用户ID，逗号拼接", dataTypeClass = String.class)
    public Boolean delete(@RequestParam String ids) {
        if (ids == null || "".equals(ids)) {
            return false;
        }
        return objUserService.delete(ids);
    }

    @PostMapping("saveUserRole")
    @ApiOperation(value = "保存用户和权限关联数据")
    @ApiImplicitParam(name = "formData", value = "UserRole 对象字符串", dataTypeClass = String.class)
    public Map<String, Object> saveUserRole(@RequestParam String formData) {
        Map<String, Object> dataMap = new HashMap<>();
        if (formData == null || "".equals(formData)) {
            dataMap.put("result", false);
            return dataMap;
        }
        return objUserService.saveUserRole(formData);
    }

    @GetMapping("updateFirstLogin/{userId}")
    @ApiOperation(value = "更新是否首次登录的状态", notes = "默认是1，修改后变成0")
    @ApiImplicitParam(name = "userId", value = "User ID", dataTypeClass = Integer.class, paramType = "path", example = "0")
    public void updateFirstLogin(@PathVariable Integer userId) {
        if (userId != null) {
            objUserService.updateFirstLogin(userId);
        }
    }

    @GetMapping("active/{userId}")
    @ApiOperation(value = "激活用户", notes = "点击邮件里面的激活链接")
    @ApiImplicitParam(name = "userId", value = "User ID", dataTypeClass = Integer.class, paramType = "path", example = "0")
    public ModelAndView activeUser(@PathVariable Integer userId) {
        Boolean result = objUserService.activeUser(userId);
        if (result) {
            return new ModelAndView("redirect:https://otsukanc.dodosurvey.com:4100/#/extra/active-account?status=1");
        } else {
            return new ModelAndView("redirect:https://otsukanc.dodosurvey.com:4100/#/extra/active-account?status=0");
        }
    }

    @PutMapping("changePassword")
    @ApiOperation(value = "修改密码")
    @ApiImplicitParam(name = "password", value = "密码", dataTypeClass = String.class)
    public Map<String, Object> changePassword(@RequestParam String password) {
        Map<String, Object> dataMap = new HashMap<>();
        if (password == null || "".equals(password)) {
            dataMap.put("result", false);
            return dataMap;
        }

        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (jwtUser == null) {
            dataMap.put("result", false);
            dataMap.put("msg", "noLogin");
            return dataMap;
        }
        return objUserService.changePassword(jwtUser.getUserId(), password);
    }

    @PostMapping("comparePassword")
    @ApiOperation(value = "修改前检查原密码是否正确")
    @ApiImplicitParam(name = "password", value = "原密码", dataTypeClass = String.class)
    public Map<String, Object> comparePassword(@RequestParam String password) {
        Map<String, Object> dataMap = new HashMap<>();
        if (password == null || "".equals(password)) {
            dataMap.put("result", false);
            return dataMap;
        }

        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (jwtUser == null) {
            dataMap.put("result", false);
            dataMap.put("msg", "noLogin");
            return dataMap;
        }
        return objUserService.comparePassword(jwtUser.getUserId(), password);
    }

    @PostMapping("forgotPassword")
    @ApiOperation(value = "忘记密码", notes = "将发送一封带有密码的邮件")
    @ApiImplicitParam(name = "email", value = "Email", dataTypeClass = String.class)
    public Map<String, Object> forgotPassword(@RequestParam String email) {
        Map<String, Object> dataMap = new HashMap<>();
        if (email == null || "".equals(email)) {
            dataMap.put("result", false);
            return dataMap;
        }
        dataMap = objUserService.forgotPassword(email);
        if ((Boolean) dataMap.get("result")) {
            ObjUser objUser = JSONObject.parseObject(JSONObject.toJSONString(dataMap.get("objUser")), ObjUser.class);
            String password = DesUtils.decrypt(objUser.getPasswordDes());
            String html = "<html><body><p>Dear " + objUser.getFirstName() + " " + objUser.getLastName() + "</p><br />" + "<p>Here is you account.</p>" + "<p>User Name: " + objUser.getUserName() + "</p>" + "<p>Email: " + objUser.getEmail() + "</p>" + "<p>Password: " + password + "</p><br />" + "<p>Click <a href='http://" + Constant.Domain + ":4100/#/extra/login' target='_blank'>here</a> to login your account.</p>" + "</body><html>";
            boolean sendResult = mailService.sendWithHtml(objUser.getEmail(), "Forgot Password", html);
            dataMap.put("sendResult", sendResult);
        }
        return dataMap;
    }

}
