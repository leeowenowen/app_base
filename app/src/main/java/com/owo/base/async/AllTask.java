package com.owo.base.async;

import com.owo.base.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangli on 15-5-28.
 */
public class AllTask<Param, Result> implements Task<List<Param>, List<Result>> {
    private List<Param> mParams;
    private List<Result> mResults = new ArrayList<Result>();
    private Task<Param, Result> mWorker;
    private Callback<List<Result>> mCallback;
    private SerialExecutor serialExecutor = new SerialExecutor();

    public AllTask(Task<Param, Result> worker) {
        mWorker = worker;
    }

    @Override
    public void run(List<Param> params, Callback<List<Result>> callback) {
        mParams = params;
        mCallback = callback;
        for (final Param param : params) {
            TaskRunner.run(new Runnable() {
                @Override
                public void run() {
                    mWorker.run(param, new Callback<Result>() {
                        @Override
                        public void run(final Result result) {
                            serialExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mResults.add(result);
                                    if (mResults.size() == mParams.size()) {
                                        mCallback.run(mResults);
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
