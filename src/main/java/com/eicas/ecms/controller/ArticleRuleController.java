package com.eicas.ecms.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.ecms.annotation.ResponseResult;
import com.eicas.ecms.dto.NumDto;
import com.eicas.ecms.entity.Ecms;
import com.eicas.ecms.service.IArticleRuleService;
import com.eicas.ecms.dto.ArticleRuleDto;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
* <p>
* 文章规则表 前端控制器
* </p>
*
* @author jsq
* @since 2021-10-28
*/
@Api(tags = "文章规则表")
@ResponseResult()
@RestController
@RequestMapping("/api/articleRule")
public class ArticleRuleController {

    @Resource
    private IArticleRuleService iArticleRuleService;


    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @DeleteMapping("/delete/{id}")
    public boolean physicalDelete(@PathVariable(value="id") String id){

        return iArticleRuleService.removeById(id);

    }

    @ApiOperation(value = "根据id逻辑删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") String id){
        return iArticleRuleService.update(new UpdateWrapper<Ecms>().eq("id",id).set("deleted", 1));
    }

    @ApiOperation(value = "根据规则id获取")
    @ApiImplicitParams({@ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "ArticleRuleDto.class", required = true)})
    @GetMapping(value = "/page")
    public Page<Ecms> page(ArticleRuleDto articleRuleDto,Page<Ecms> page){
//        return iArticleRuleService.page(articleRuleDto);
    //依据role修改内容
        return     iArticleRuleService.getEntityMsg(articleRuleDto,page);
    }


    @ApiOperation(value = "根据栏目id和时间获取采集数量")
    @ApiImplicitParams({@ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "ArticleRuleDto.class", required = true)})
    @GetMapping(value = "/num")
    public NumDto selectNum(ArticleRuleDto articleRuleDto){
        return iArticleRuleService.selectNum(articleRuleDto);
    }

    @ApiOperation(value = "根据规则id获取所对应是否审核的数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "body", value = "请求体", paramType="body", dataType = "ArticleRuleDto.class", required = true)})
    @GetMapping(value = "/pageByStatus")
    public Page<Ecms> pagea(ArticleRuleDto articleRuleDto,Page<Ecms> page){
        return iArticleRuleService.getEntityClsMsg(articleRuleDto,page);
    }
}
