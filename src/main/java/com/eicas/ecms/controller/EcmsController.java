package com.eicas.ecms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.bo.ListAuditBo;
import com.eicas.ecms.entity.Column;
import com.eicas.ecms.entity.Ecms;
import com.eicas.ecms.service.IColumnService;
import com.eicas.ecms.service.IEcmsService;
import com.eicas.ecms.utils.pojo.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author jsq
 * @since 2021-10-28
 */
@Api(tags = "文章表")
@ResponseResult()
@RestController
@RequestMapping("/api/ecms")
public class EcmsController {

    @Resource
    private IEcmsService iEcmsService;
    @Resource
    private IColumnService columnService;


    /**
     * 全部都必须要审核通过才行
     *
     * @param entity
     * @param page
     * @param focusImg
     * @param columnId
     * @return
     */
    @ApiOperation(value = "文章表分页列表", response = Ecms.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType = "body", dataType = "Ecms.class", required = true),
            @ApiImplicitParam(name = "page", value = "页面", dataType = "Long"),
            @ApiImplicitParam(name = "size", value = "页面数据量", dataType = "Long"),
    })
    @GetMapping(value = "/page")
    public Page<Ecms> searchPages(Ecms entity, Page<Ecms> page, Boolean focusImg, String columnId,String title) {
        if (focusImg == null && page == null) {
            return iEcmsService.page(page,
                    new QueryWrapper<Ecms>()
                            .orderByDesc("created_time")
                            .eq("column_status", 1));
        }
        if (focusImg == null) {
            return iEcmsService.selectAll(entity, page,title);
        }
        if (!focusImg) {
            return iEcmsService.selectByColumnid(entity, page);
        } else {
            Page<Ecms> ecmsPage = iEcmsService.sendFocusImgPage(entity, page, columnId);
            if (ecmsPage == null) {
                return null;
            } else {
                return ecmsPage;
            }
        }
    }


    @ApiOperation(value = "文章表新增/修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType = "body", dataType = "Ecms.class", required = true),
    })
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@RequestBody Ecms entity) {
        return iEcmsService.saveOrUpdate(entity);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value = "id") String id) {
        return iEcmsService.removeById(id);
    }

    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value = "id") String id) {
        return iEcmsService.update(new UpdateWrapper<Ecms>().eq("id", id).set("is_deleted", 1));
    }

    @ApiOperation(value = "文章表状态修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType = "body", dataType = "Ecms.class", required = true),
    })
    @PostMapping(value = "/statusUpdate")
    public boolean statusUpdate(@RequestBody Ecms entity) {
        return iEcmsService.statusUpdate(entity);
    }


    @ApiOperation(value = "获取文章")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", required = true)})
    @GetMapping(value = "/crawling")
    public BaseResponse getQ(String id) {
        String res = iEcmsService.getQ(id);
        return BaseResponse.success(res);
    }

    @ApiOperation(value = "根据获取文章")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id", required = true)})
    @GetMapping(value = "/getById")
    public Ecms getById(String id) {
        //两个查询，并且把columnName放到Ecms中
        Ecms selectEcmsById = iEcmsService.getById(id);
        if (selectEcmsById == null) {
            System.out.println("找不到Id");
            return null;
        }
        Column byId = columnService.getById(selectEcmsById.getColumnId());
        selectEcmsById.setColumnName(byId.getColumnName());
        return selectEcmsById;

    }


    @ApiOperation(value = "审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "body", value = "请求体", paramType = "body", dataType = "Column.class", required = true),
    })
    @PostMapping(value = "/audit")
    public Boolean auditEcms(@RequestBody List<Ecms> auditBos) {
        if (auditBos.size() == 0 || auditBos == null) {
            return false;
        }
        //0 暂不审核 1 审核通过 2审核不通过
        for (int i = 0; i < auditBos.size(); i++) {
            if (auditBos.get(i).getStatus().equals(0)) {
                return false;
            } else if (auditBos.get(i).getStatus().equals(1)) {
                iEcmsService.auditEcms(auditBos.get(i));
            } else if (auditBos.get(i).getStatus().equals(2)) {
                iEcmsService.auditFailedEcms(auditBos.get(i));
            }
        }
        return true;
    }

    @ApiOperation(value = "批量删除,逻辑删除")
    @PostMapping(value = "/batch/delete")
    public Boolean batchesDeletes(@RequestBody List<String> ecmsIds) {
        if (ecmsIds == null || ecmsIds.size() == 0) {
            return false;
        }
        return iEcmsService.logicalDeleted(ecmsIds);
    }

    @ApiOperation(value = "单个删除,逻辑删除")
    @PostMapping(value = "/singleDelete")
    public Boolean singleDeletes(String ecmsIds) {
        return iEcmsService.update(new UpdateWrapper<Ecms>().set("is_deleted", 1).eq("id", ecmsIds));
    }

    @ApiOperation(value = "批量删除,逻辑删除")
    @DeleteMapping(value = "/batch/delete")
    public Boolean batcheDeletes(@RequestBody List<String> ecmsIds) {
        if (ecmsIds == null || ecmsIds.size() == 0) {
            return false;
        }
        return iEcmsService.removeByReal(ecmsIds);
    }

}
