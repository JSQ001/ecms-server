package com.eicas.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.NoticeEntity;
import com.eicas.cms.pojo.param.NoticeQueryParam;
import com.eicas.cms.service.INoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 通知公告控制器
 *
 * @author osnudt
 * @since 2022-04-18
 */
@Api(tags = "通知公告接口")
@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Resource
    INoticeService noticeService;

    /**
     * 根据ID获取1条通知公告
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据id获取通知公告详情")
    public NoticeEntity getNotice(@PathVariable(value = "id") Long id) {
        return noticeService.getById(id);
    }

    /**
     * 根据参数查询通知公告
     * @param param 查询参数
     * @param current 当前分页
     * @param size 分页大小
     * @return 通知公告分页数据
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "条件查询通知公告（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码", required = true, defaultValue = "1", dataType = "int", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "size", value = "页码大小", required = true, defaultValue = "10", dataType = "int", dataTypeClass = Integer.class)
    })
    public Page<NoticeEntity> listNotice(@Valid NoticeQueryParam param,
                                         @RequestParam(value = "current", defaultValue = "1") Integer current,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return noticeService.listNotice(param, current, size);
    }

    /**
     * 创建一条新的通知公告
     * @param entity 通知公告对象
     * @return 是否创建成功
     */
    @PostMapping(value = "/create")
    @ApiOperation(value = "新增通知公告")
    public Boolean createNotice(@Valid @RequestBody NoticeEntity entity) {
        return noticeService.save(entity);
    }

    /**
     * 根据ID删除1条通知公告
     * @param id 通知公告编号
     * @return 是否删除成功
     */
    @PostMapping("/delete/{id}")
    @ApiOperation(value = "根据id逻辑删除通知公告")
    public Boolean deleteNotice(@PathVariable(value = "id") Long id) {
        return noticeService.removeById(id);
    }

    /**
     * 根据ID列表批量删除通知公告
     * @param ids ID列表
     * @return 是否删除成功
     */
    @PostMapping("/batchDel")
    @ApiOperation(value = "批量逻辑删除通知公告")
    public Boolean batchDelete(@RequestBody List<Long> ids) {
        return noticeService.removeBatchByIds(ids);
    }

    /**
     * 更新通知公告
     * @param entity 通知公告对象
     * @return 更新是否成功
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新通知公告")
    public Boolean updateNotice(@Valid @RequestBody NoticeEntity entity) {
        return noticeService.updateById(entity);
    }
}
