package com.eicas.cms.service.impl;

import com.eicas.cms.component.ScheduleTask;
import com.eicas.cms.crawling.Craw;
import com.eicas.cms.pojo.entity.CollectRule;
import com.eicas.cms.mapper.CollectRuleMapper;
import com.eicas.cms.pojo.vo.CollectRuleVO;
import com.eicas.cms.service.ICollectRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 采集规则表 服务实现类
 * </p>
 *
 * @author jsq
 * @since 2022-03-06
*/
@Service
@Slf4j
@Transactional
public class CollectRuleServiceImpl extends ServiceImpl<CollectRuleMapper, CollectRule> implements ICollectRuleService {

    @Resource
    private CollectRuleMapper collectRuleMapper;

    @Resource
    private ScheduleTask scheduleTask;


    @Resource
    private Craw craw;


    @Override
    public Page<CollectRule> listCollectRules(CollectRuleVO collectRuleVO) {
        return collectRuleMapper.listArticles(collectRuleVO, collectRuleVO.pageFactory());
    }

    @Override
    public boolean createOrUpdate(CollectRule entity){
        boolean flag = saveOrUpdate(entity);
        scheduleTask.update(entity);
        return flag;
    }

    @Override
    public boolean logicalDeleteById(Long id){
        return removeById(id);
    }


    @Override
    public void selectCollectRuleInit(){
        List<CollectRule>  collectRuleList=collectRuleMapper.selectCollectRuleInit();
        for (int i=0;i<collectRuleList.size();i++){
            scheduleTask.update(collectRuleList.get(i));

        }


    }

    @Override
    public  boolean  updateByCollectId(Map paramap){
          try {

              CollectRule collectRule=this.getById((Long)paramap.get("id"));


              if ((int)paramap.get("isFlag")==1)
              {   log.info("手动开始爬取---" + collectRule.getId() + "---" + collectRule.getName());
                  craw.run(collectRule,null);
                  log.info("手动爬取结束---" + collectRule.getId() + "---" + collectRule.getName());}
              collectRuleMapper.updateByCollectId(paramap);
              return true;

          }catch (Exception e){

              e.printStackTrace();
              return  false;

          }


    }

}
