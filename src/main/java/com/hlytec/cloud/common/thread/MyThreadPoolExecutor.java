package com.hlytec.cloud.common.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @description: MyThreadPoolExecutor
 * @author: zero
 * @date: 2021/7/13 13:57
 */
public class MyThreadPoolExecutor {
    /**
     * 最大可用的CPU核数
     */
    public static final int PROCESSORS = Runtime.getRuntime().availableProcessors();

    /**
     * 最大存活事件
     */
    public static final int KEEPALIVE_TIME = 60;

    /**
     * 阻塞队列的长度
     */
    public static final int BLOCKINGQUEUE_LENGTH = 50;
    public static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("handle-thread-pool-%d").build();
    private static final ExecutorService executorService = new ThreadPoolExecutor(PROCESSORS, PROCESSORS * 2,
        KEEPALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(BLOCKINGQUEUE_LENGTH), threadFactory);

    public static ExecutorService getThreadPool() {
        return executorService;
    }
}
