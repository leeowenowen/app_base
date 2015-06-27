package com.owo.base.async;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <br>=
 * ========================= <br>
 * 任务执行的最基础类，如果不满足业务逻辑要求，可在此之上封装子集的Ｅxecutor <br>=
 * =========================
 */
public class TaskRunner {
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
	static final ScheduledExecutorService SERVICE = Executors
			.newScheduledThreadPool(CORE_POOL_SIZE);

	public static void run(Runnable runnable) {
		SERVICE.execute(runnable);
	}

	public static void runAfter(Runnable runnable, long millSeconds) {
		SERVICE.schedule(runnable, millSeconds, TimeUnit.MILLISECONDS);
	}

	public static void runAtFixedRate(Runnable runnable, long intervalInMillSeconds) {
		SERVICE.scheduleAtFixedRate(runnable, 0, intervalInMillSeconds, TimeUnit.MILLISECONDS);
	}

	private static final ScheduledExecutorService NETWORKSERVICE = Executors.newSingleThreadScheduledExecutor();

	public static void runOnNetWorkThread(Runnable r)
	{
		NETWORKSERVICE.execute(r);
	}
}
