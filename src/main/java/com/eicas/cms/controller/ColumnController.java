package com.eicas.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.entity.CollectRule;
import com.eicas.cms.pojo.entity.Column;
import com.eicas.cms.pojo.vo.ColumnVO;
import com.eicas.cms.pojo.vo.MoveColumneV0;
import com.eicas.cms.service.IColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 栏目表 前端控制器
 * </p>
 *
 * @author jsq
 * @since 2022-03-05
 */
@Api(tags = "栏目表")
@RestController
@RequestMapping("/api/column")
public class ColumnController {

    @Resource
    private IColumnService iColumnService;

    @ApiOperation(value = "栏目表分页查询", response = Column.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "title", value = "栏目名称", dataTypeClass = String.class),
    })
    @GetMapping(value = "/list")
    public Page<Column> listColumns(ColumnVO columnVo) {

        return iColumnService.listColumns(columnVo);
    }

    @ApiOperation(value = "查询栏目树", response = Column.class)
    @GetMapping(value = "/tree")
    public List<Column> getColumnTree(Long id) {
        return iColumnService.getColumnTree(id);
    }

    @ApiOperation(value = "根据父栏目code查询子栏目列表", response = Column.class)
    @GetMapping(value = "/listByParentCode")
    public List<Column> listByParentCode(String code) {
        return iColumnService.listByParentCode(code);
    }





    @ApiOperation(value = "栏目表新增/修改")
    @PostMapping(value = "/createOrUpdate")
    public boolean createOrUpdate(@Valid @RequestBody Column entity) {
        return iColumnService.createOrUpdate(entity);
    }


    @ApiOperation(value = "根据id物理删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "id",required = true,  dataTypeClass = Long.class)})
    @PostMapping("/delete/{id}")
    public boolean logicalDelete(@PathVariable(value="id") Long id){
        return iColumnService.logicalDeleteById(id);
    }


    @ApiOperation(value = "栏目剧移动")
    @ApiImplicitParams({@ApiImplicitParam(name = "sourceid", value = "sourceid",required = true,  dataTypeClass = Long.class)})

    @PostMapping("/movecolumne")
    public boolean movecolumne(@RequestBody MoveColumneV0 moveColumneV0){

        Map<String,Object> columnMap= new HashMap<>();
        columnMap.put("columnCode",moveColumneV0.getColumnCode());
        columnMap.put("targertid",moveColumneV0.getTargetId());
        columnMap.put("sourceid",moveColumneV0.getSourceId());

        if (iColumnService.MoveColumn(columnMap)>0) return  true;
               else return  false;
    }


    @GetMapping("/getcolumneInfor")
    public Page<Column> getTreePageInfor(ColumnVO entity){

       return  iColumnService.getTreePageInfor(entity);


    }


}
