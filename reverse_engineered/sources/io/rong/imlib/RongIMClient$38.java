package io.rong.imlib;

import io.rong.common.RLog;
import java.util.concurrent.CountDownLatch;

class RongIMClient$38 extends RongIMClient$SyncCallback<Boolean> {
    final /* synthetic */ RongIMClient this$0;
    final /* synthetic */ CountDownLatch val$latch;
    final /* synthetic */ RongIMClient$ResultCallback$Result val$result;

    RongIMClient$38(RongIMClient rongIMClient, RongIMClient$ResultCallback$Result rongIMClient$ResultCallback$Result, CountDownLatch countDownLatch) {
        this.this$0 = rongIMClient;
        this.val$result = rongIMClient$ResultCallback$Result;
        this.val$latch = countDownLatch;
    }

    public void onSuccess(Boolean bool) {
        if (bool != null) {
            this.val$result.f17368t = bool;
        } else {
            RLog.m19420e("RongIMClient", "setMessageSentStatus setMessageSentStatus is failure!");
        }
        this.val$latch.countDown();
    }

    public void onError(RongIMClient$ErrorCode rongIMClient$ErrorCode) {
        this.val$latch.countDown();
    }
}
