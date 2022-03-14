package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
    * 根据传入entity属性值分页查询
    * @param param
    * @param page
    * @return Page<${entity}>
    */
    Page<${entity}> page(${entity} entity, Page<${entity}> page);

    /**
    * 保存或更新
    * @param entity
    * @return
    */
    boolean createOrUpdate(${entity} entity);
    /**
    * 根据id逻辑删除
    * @param id
    * @return
    */
    boolean logicalDeleteById(Long id);
}