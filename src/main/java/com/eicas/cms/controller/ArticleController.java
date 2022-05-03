package com.eicas.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.entity.ArticleVisitLog;
import com.eicas.cms.entity.AttachmentEntity;
import com.eicas.cms.pojo.param.*;
import com.eicas.cms.service.IArticleService;
import com.eicas.common.ResultData;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章信息表 前端控制器
 *
 * @author osnudt
 * @since 2022-04-19
 */
@Api(tags = "文章接口")
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Resource
    IArticleService articleService;

    @Resource
    ArticleVisitLogController visitLogController;

    /**
     * 获取单个文章信息
     *
     * @param id 信息ID
     * @param createLog 是否创建访问记录
     * @return 文章信息对象
     */
    @ApiOperation(value="根据id查询文章详情",response = ArticleEntity.class)
    @ApiImplicitParam(name = "createLog", required = false, dataTypeClass = Boolean.class)
    @GetMapping(value = "/{id}")
    public ArticleEntity getArticleById(@NotNull @PathVariable(value = "id") Long id, @RequestParam(required = false, defaultValue = "true") boolean createLog, HttpServletRequest request) {
        if(createLog){
            ArticleVisitLog log = new ArticleVisitLog();
            log.setArticleId(id);
            visitLogController.create(log,request);
        }
        return articleService.getById(id);
    }


    /**
     * 根据参数查询文章信息
     *
     * @param param   查询参数
     * @param current 当前分页
     * @param size    分页大小
     * @return 文章信息分页数据
     */
    @PostMapping(value = "/list")
    public Page<ArticleEntity> listArticle(ArticleQueryParam param,
                                           @RequestParam(value = "current", defaultValue = "1") Integer current,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return articleService.listArticle(param, current, size);
    }

    /**
     * 根据指定栏目ID查询文章信息列表
     *
     * @param catalogId 栏目ID
     * @param current   当前页码
     * @param size      页面大小
     * @return 文章信息分页数据
     */
    @PostMapping(value = "/list/catalog")
    public Page<ArticleEntity> listArticleByCatalogId(@NotNull @RequestParam(value = "catalogId") Long catalogId,
                                                      @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return articleService.listArticleByCatalogId(catalogId, current, size);
    }

    /**
     * 查询指定栏目下文章
     *
     * @param param
     * @param current   当前页码
     * @param size      页面大小
     * @return 文章信息分页数据
     */
    @ApiOperation(value="查询指定栏目下文章",response = List.class)
    @ApiImplicitParam(name = "栏目code", value = "code", required = true, dataTypeClass = String.class)
    @PostMapping(value = "/list/catalogCode")
    public Page<ArticleEntity> listArticleByCatalogCode(ArticleQueryParam param,
                                                      @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return articleService.listArticleByCatalogCode(param, current, size);
    }


    /**
     * 保存新建文章，必须指定已存在的栏目
     *
     * @param entity 文章对象
     * @return 是否成功
     */
    @PostMapping(value = "/create")
    public ResultData<ArticleEntity> createArticle(@Valid @RequestBody ArticleEntity entity) {
        entity.setState(1);
        return articleService.saveArticle(entity);
    }

    /**
     * 删除文章信息
     * @param id 文章对象ID
     * @return 删除是否成功
     */
    @PostMapping("/delete/{id}")
    public Boolean deleteArticleById(@PathVariable(value = "id") Long id) {
        return articleService.removeById(id);
    }

    /**
     * 批量删除文章信息
     * @param ids 文章对象ID列表
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Boolean batchDelete(@RequestBody List<Long> ids) {
        return articleService.removeBatchByIds(ids);
    }

    /**
     * 更新指定ID的文章信息
     *
     * @param entity 文章信息对象
     * @return 更新是否成功
     */
    @PostMapping("/update")
    public ResultData<ArticleEntity> updateArticle(@RequestBody ArticleEntity entity) {
        entity.setState(1);
        return articleService.updateArticle(entity);
    }

    /**
     * 修改文章置顶、焦点、推荐、是否显示属性
     *
     * @param param 文章信息对象
     * @return 更新是否成功
     */
    @ApiOperation("修改文章置顶、焦点、推荐、显示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "文章id", value="id", required = true, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "是否显示", value="visible", dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "是否置顶", value="top", dataTypeClass = Boolean.class),
            @ApiImplicitParam(name = "是否推荐", value="recommended", dataTypeClass = Boolean.class),
    })
    @PostMapping("/update/properties")
    public ResultData updateArticleProperties(@RequestBody ModifyArticleParam param) {
        return articleService.updateArticleProperties(param);
    }

    /**
     * 审核文章
     *
     * @param param  审核参数
     * @return 是否成功
     */
    @PostMapping(value = "/audit")
    public Boolean auditArticle(@Valid @RequestBody ArticleAuditParam param) {
        return articleService.auditArticle(param);
    }


    /**
     * 将指定ID的文章信息移动至另一个栏目
     *
     * @param id        文章信息ID
     * @param catalogId 栏目ID
     * @return 移动是否成功
     */
    @PostMapping(value = "/move")
    public Boolean moveArticle(@NotNull @RequestParam(value = "id") Long id,
                               @RequestParam(value = "catalogId") Long catalogId) {
        return articleService.moveArticle(id, catalogId);
    }


    /**
     * TODO 根据条件统计文章访问计数情况
     *
     * @param startTime
     * @param endTime
     * @return 统计数据对象
     */
    @PostMapping(value = "/statistic/visit")
    public ArticleStaticsResult statisticArticleVisit(
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return articleService.statisticArticleVisit(startTime, endTime);
    }
    /**
     * TODO 根据条件统计文章采编情况
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 统计数据对象
     */
    @PostMapping(value = "/statistic/compile")
    public ArticleStaticsResult statisticArticleCompile(
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return articleService.statisticArticleVisit(startTime, endTime);
    }

    @ApiOperation("文件上传")
    @PostMapping(value = "/upload")
    @ResponseBody
    public ResultData<AttachmentEntity> uploadAttachment(@RequestParam("file") MultipartFile file) {
        AttachmentEntity entity = articleService.upload(file);
        if (entity == null)
            return ResultData.failed("上传文件失败！");
        return ResultData.success(entity);
    }

    /**
     * TODO
     * @param entity
     * @return
     */
    @PostMapping("/hits")
    public Boolean updateArticleHitNums(@Valid @RequestBody ArticleEntity entity) {
        return false;
    }

    /**
     * TODO
     * @param entity
     * @return
     */
    @GetMapping("/hits")
    public Boolean getArticleHitNums(@Valid @RequestBody ArticleEntity entity) {
        return false;
    }

    /**
     * 按栏目统计指定时间范围内文章信息
     * @param startTime
     * @param endTime
     * @return CatalogArticleStaticsResult
     */
    @PostMapping(value = "/statistic/by/catalogCode")
    @ApiOperation(value = "按栏目统计指定时间范围内文章信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "栏目code", value="code", required = true, dataTypeClass = String.class,example="majior"),
            @ApiImplicitParam(name = "开始时间", value="startTime", required = false, dataTypeClass = LocalDateTime.class,example="2022-04-25 00:00:00"),
            @ApiImplicitParam(name = "结束时间", value="endTime", required = false, dataTypeClass = LocalDateTime.class,example="2022-04-25 00:00:00")
    })
    public List<CatalogArticleStaticsResult> staticsCatalogArticle(
            @RequestParam(required = false, name = "code", defaultValue = "majior") String code,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return articleService.staticsCatalogArticle(code, startTime, endTime);
    }
}
