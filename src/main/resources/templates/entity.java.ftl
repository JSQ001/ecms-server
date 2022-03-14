package ${package.Entity};
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import com.eicas.cms.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
* ${table.comment!} 实体类
*
* @author ${author}
* @since ${date}
*/

@Getter
@Setter
@Accessors(chain = true)
@TableName("${table.name!}")
@ApiModel(value = "${table.comment!}对象", description = "${table.comment!}")
public class ${entity} extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    <#list table.fields as field>
<#--    /**-->
<#--    * ${field.comment}-->
<#--    */-->
    @ApiModelProperty("${field.comment}")
    @TableField("${field.name}")
    <#if field.metaInfo != 'null' && field.metaInfo.nullable == false>
    @NotNull(message = "${'${field.capitalName}'?uncap_first}不能为空")
    </#if>
    private ${field.propertyType} ${'${field.capitalName}'?uncap_first};

    </#list>
}