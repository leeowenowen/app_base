package com.owo.base.async;

import java.util.concurrent.Executor;

/**
 * <br>==========================
 * <br> 同步Executor，直接在当前线程执行任务
 * <br> 公司：九游
 * <br> 开发：wangli
 * <br> 版本：1.0
 * <br> 创建时间：2015/05/05
 * <br>==========================
 */
public class SyncExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }

}
