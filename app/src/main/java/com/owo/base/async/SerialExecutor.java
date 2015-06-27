package com.owo.base.async;

import java.util.concurrent.Executor;

/**
 * <br>==========================
 * <br> 序列Executor，保证按序执行任务。
 * <br> 公司：九游
 * <br> 开发：wangli
 * <br> 版本：1.0
 * <br> 创建时间：2015/05/05
 * <br>==========================
 */
public class SerialExecutor implements Executor {
    private final ArrayDequeCompat<Runnable> mTasks = new ArrayDequeCompat<Runnable>();
    private Runnable mActive;

    public synchronized void execute(final Runnable r) {
        mTasks.offer(new Runnable() {
            public void run() {
                try {
                    r.run();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                } finally {
                    scheduleNext();
                }
            }
        });
        if (mActive == null) {
            scheduleNext();
        }
    }

    protected synchronized void scheduleNext() {
        if ((mActive = mTasks.poll()) != null) {
            TaskRunner.run(mActive);
        }
    }
}
