package com.owo.base.async;

import java.util.Iterator;
import java.util.concurrent.Executor;

import com.owo.base.common.Callback;

/**
 * <br>==========================
 * <br>  Asynchronous implementation of language level for.
 * ForTask run all parameters specified by mIterator in sequence in
 * an asynchronous loop, if the worker break on any parameter, the
 * whole loop task finished.
 * <br>==========================
 */
public class ForTask<Param, Result> {
    private Executor mExecutor;
    private Callback<Result> mCallback;
    private Iterator<Param> mIterator;
    private Worker<Param, Result> mWorker;
    private boolean mStopFlag;

    public ForTask() {
        mExecutor = new Executor() {
            @Override
            public void execute(Runnable command) {
                if (!mStopFlag) {
                    TaskRunner.run(command);
                }
            }
        };
    }

    public ForTask<Param, Result> param(Param[] params) {
        mIterator = new ArrayIterator<Param>(params);
        return this;
    }

    public ForTask<Param, Result> param(Iterable<Param> params) {
        mIterator = params.iterator();
        return this;
    }

    public ForTask<Param, Result> param(Iterator<Param> paramIterator) {
        mIterator = paramIterator;
        return this;
    }

    public ForTask<Param, Result> worker(Worker<Param, Result> worker) {
        mWorker = worker;
        return this;
    }

    public ForTask<Param, Result> callback(Callback<Result> callback) {
        mCallback = callback;
        return this;
    }

    public ForTask<Param, Result> executor(Executor executor) {
        mExecutor = executor;
        return this;
    }

    public void run() {
        runLoop(null);
    }

    public void stop() {
        mStopFlag = true;
    }

    private void notifyResult(Result result) {
        if (mCallback != null) {
            mCallback.run(result);
        }
    }

    private void runLoop(Result result) {
        if (!mIterator.hasNext()) {
            notifyResult(result);
            return;
        }
        mExecutor.execute(new Runnable() {
            public void run() {
                mWorker.run(mIterator.next(), new WorkerCallback<Result>() {

                    @Override
                    public void onBreak(Result result) {
                        notifyResult(result);
                    }

                    @Override
                    public void onFinish(Result result) {
                        runLoop(result);
                    }
                });
            }
        });
    }

    public static interface WorkerCallback<Result> {
        void onBreak(Result result);

        void onFinish(Result result);
    }

    public static interface Worker<Param, Result> {
        void run(Param param, WorkerCallback<Result> callback);
    }

}
