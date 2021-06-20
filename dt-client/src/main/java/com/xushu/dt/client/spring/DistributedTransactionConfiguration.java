package com.xushu.dt.client.spring;

import com.xushu.dt.client.id.DefaultIdGenerator;
import com.xushu.dt.client.id.IdGenerator;
import com.xushu.dt.client.rm.ResourceManager;
import com.xushu.dt.client.rm.ResourceManagerImpl;
import com.xushu.dt.client.spring.properties.DistributedTransactionProperties;
import com.xushu.dt.client.tc.TransactionCoordinator;
import com.xushu.dt.client.tc.TransactionCoordinatorImpl;
import com.xushu.dt.client.tm.TransactionManager;
import com.xushu.dt.client.tm.TransactionManagerImpl;
import com.xushu.dt.client.tm.TwoPhaseTransactionSynchronization;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author xushu
 */
@Configuration
@ConditionalOnProperty(prefix = "dt", name = "enable", havingValue = "true")
@EnableConfigurationProperties(DistributedTransactionProperties.class)
public class DistributedTransactionConfiguration {

    @Resource
    private DistributedTransactionProperties distributedTransactionProperties;

    @Bean
    public TransactionManager dtTransactionManager() {
        return new TransactionManagerImpl();
    }

    @Bean
    public ResourceManager dtResourceManager() {
        return new ResourceManagerImpl();
    }

    @Bean
    public TransactionCoordinator transactionCoordinator() {
        return new TransactionCoordinatorImpl();
    }

    @Bean
    public TwoPhaseTransactionSynchronization twoPhaseTransactionSynchronization() {
        return new TwoPhaseTransactionSynchronization();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }

}
