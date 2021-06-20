package com.xushu.dt.log.repository.mybatis.spring;

import com.xushu.dt.client.log.repository.ActionRepository;
import com.xushu.dt.client.log.repository.ActivityRepository;
import com.xushu.dt.log.repository.mybatis.ActionMybatisRepository;
import com.xushu.dt.log.repository.mybatis.ActivityMybatisRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xushu
 */
@Configuration
@ConditionalOnProperty(prefix = "dt", name = "enable", havingValue = "true")
public class RepositoryMybatisConfiguration {

    @Bean
    public ActivityRepository activityRepository() {
        return new ActivityMybatisRepository();
    }

    @Bean
    public ActionRepository actionRepository() {
        return new ActionMybatisRepository();
    }

}
