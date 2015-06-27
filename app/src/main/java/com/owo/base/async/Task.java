package com.owo.base.async;

import com.owo.base.common.Callback;

/**
 * <br>==========================
 * <br> 异步任务接口类，表示指定参数类型和结果类型的一个异步任务。
 * <br>==========================
 */
public interface Task<Param, Result> {
    void run(Param param, Callback<Result> callback);
}
