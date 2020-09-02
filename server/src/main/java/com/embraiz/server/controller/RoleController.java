package com.embraiz.server.controller;

import com.embraiz.entity.base.PageResult;
import com.embraiz.entity.server.ObjRole;
import com.embraiz.service.server.ObjRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("role")
@Api(tags = "角色管理相关接口")
public class RoleController {

    @Resource
    ObjRoleService objRoleService;

    @PostMapping("getTableList")
    @ApiOperation(value = "获取角色列表分页信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchData", value = "搜索条件，使用Role对象，无条件传{}", required = true, dataTypeClass = String.class),
            @ApiImplicitParam(name = "page", value = "当前页", dataTypeClass = Integer.class, defaultValue = "1", example = "1"),
            @ApiImplicitParam(name = "count", value = "当前显示数目", dataTypeClass = Integer.class, defaultValue = "15", example = "15"),
            @ApiImplicitParam(name = "sort", value = "排序字段", dataTypeClass = String.class, defaultValue = "", example = ""),
            @ApiImplicitParam(name = "order", value = "升/降序", dataTypeClass = String.class, defaultValue = "desc", example = "desc")
    })
    public PageResult getTableList(@RequestParam(value = "searchData") String searchData, @RequestParam(value = "page", defaultValue = "1") int currentPage, @RequestParam(value = "count", defaultValue = "15") int displayCount, @RequestParam(value = "sort", defaultValue = "") String sortBy, @RequestParam(value = "order", defaultValue = "desc") String sortOrder) {
        return objRoleService.getTableList(searchData, currentPage, displayCount, sortBy, sortOrder);
    }

    @RequestMapping("getList")
    @ApiOperation(value = "获取角色列表")
    public List<ObjRole> getList() {
        return objRoleService.getList();
    }

    @PostMapping("save")
    @ApiOperation(value = "保存角色")
    @ApiImplicitParam(name = "formData", value = "Role 对象字符串", dataTypeClass = String.class)
    public Map<String, Object> save(@RequestParam String formData) {
        Map<String, Object> dataMap = new HashMap<>();
        if (formData == null || "".equals(formData)) {
            dataMap.put("result", false);
            return dataMap;
        }
        return objRoleService.save(formData);
    }

    @GetMapping("getDetail/{roleId}")
    @ApiOperation(value = "获取角色详情")
    @ApiImplicitParam(name = "roleId", value = "ROLE ID", dataTypeClass = Integer.class, paramType = "path", example = "0")
    public Map<String, Object> getDetail(@PathVariable Integer roleId) {
        if (roleId == null) {
            return null;
        }
        return objRoleService.getDetail(roleId);
    }

    @PutMapping("delete")
    @ApiOperation(value = "删除橘色，可批量删除", notes = "只是修改状态，并不是真实删除")
    @ApiImplicitParam(name = "ids", value = "多个角色ID，逗号拼接", dataTypeClass = String.class)
    public Boolean delete(@RequestParam String ids) {
        if (ids == null || "".equals(ids)) {
            return false;
        }
        return objRoleService.delete(ids);
    }

}
