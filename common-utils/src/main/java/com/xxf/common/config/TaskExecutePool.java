package com.xxf.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description 线程池配置
 * @Date Created in 2020/3/19 15:32
 * @Author xxf
 */
@Configuration
@EnableAsync
public class TaskExecutePool {

    private static final int CORE_POOL_SIZE = 10;

    private static final int MAX_POOL_SIZE = 100;

    private static final int KEEPALIVE_SECONDS = 60;

    private static final int QUEUE_CAPACITY = 200;

    @Bean
    public Executor mqttSubsribe() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(KEEPALIVE_SECONDS);
        executor.setKeepAliveSeconds(QUEUE_CAPACITY);
        executor.setThreadNamePrefix("xxfThreadPool-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程(main)来执行
        // Ab：不在新线程中执行任务，而是由调用者所在的线程(main)来执行
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程(main)来执行
        // AbortPolicy：不处理任务抛异常
        // DiscardPolicy: 丢弃任务，不抛异常
        // DiscardOldestPolicy: 丢弃队列中最久的任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
