package com.study.anti_fraud.antifaud_reptile_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 自定义线程池配置类
 */
@Component
public class ThreadPoolConfig {

    //最大的线程到底如何定义
    //1、CPU密集型     几核，就是几，可以保持CPU的效率最高 （大量的计算）   Runtime.getRuntime().availableProcessors() 获取服务器的核数+1  就是最大线程数
    //2、IO密集型 判断你程序中十分IO的线程有多少个，最大线程数就设置为多少（涉及到网络、磁盘IO的任务都是IO密集型）
    //	2.1 由于IO密集型任务线程并不是一直在执行任务，则应配置更多的线程，如：CPU核数*2
    //	2.2 IO密集型时，大部分线程都阻塞，故需要多配置线程数；   参考公式：CPU/（1-阻塞系数）     一般阻塞系数为0.8~0.9之间。   比如8核CPU：8/（1-0.9）=80个线程数
    private final static int corePoolSize = (int) (Runtime.getRuntime().availableProcessors() / (1 - 0.9));
    private static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(10000);


    /**
     * 线程初始化流程（以及参数的作用）：
     * 1.当项目初始化完成的时候，初始化自定义线程池配置类
     * 2.第三和第四参数是定义被回收线程的时间
     * 3.第五参数定义任务阻塞队列1万个
     * 线程池的工作机制：
     * 1.初始化线程池时，当前核心线程和非核心线程的个数为0，当有一个任务需要线程处理，优先创建核心线程去处理
     * 2.核心线程不会被回收，当需要处理的任务超过50个核心线程时，核心线程没有空闲，那么任务会放到消息队列中
     * 3.此时再有任务需要处理，当前核心线程没有空闲且队列中存有1万个等待处理的任务时，线程池创建非核心线程去处理请求处理的任务
     * 4.当核心线程满了，队列满了，非核心线程满了，触发拒绝策略
     * <p>
     * 拒绝策略（4种）：
     * AbortPolicy 默认拒绝策略，抛出异常
     * CallerRunsPolicy 谁调用，返回给谁执行
     * DiscardPolicy 不抛出异常，不执行，啥也不干
     * DiscardOldestPolicy 从队列中取出一个等待时间最长的任务进行处理
     */
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(corePoolSize, corePoolSize, 30, TimeUnit.SECONDS, blockingQueue, Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    /*
     * 向线程池中添加任务方法
     */
    public void addExecuteTask(Runnable task) {
        if (task != null) {
            THREAD_POOL_EXECUTOR.execute(task);
        }
    }

}
