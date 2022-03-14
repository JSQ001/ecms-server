package com.eicas.cms.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 获取spring容器
 * 当一个类实现了这个接口ApplicationContextAware之后，
 * 这个类就可以方便获得ApplicationContext中的所有bean。
 * 解决非单例对象(websocket)无法使用自动注入(@Value、@Resource)
 */
@Component
public class SpringCtxUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringCtxUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> type) {
        try {
            return applicationContext.getBean(type);
        } catch (NoUniqueBeanDefinitionException e) {   //出现多个，选第一个
            String beanName = applicationContext.getBeanNamesForType(type)[0];
            return applicationContext.getBean(beanName, type);
        }
    }

    public static <T> T getBean(String beanName, Class<T> type) {
        return applicationContext.getBean(beanName, type);
    }
}


