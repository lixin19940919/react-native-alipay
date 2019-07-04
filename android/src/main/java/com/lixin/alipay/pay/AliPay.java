package com.lixin.alipay.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class AliPay {

    private Activity mActivity;

    AliPay(Activity activity) {
        this.mActivity = activity;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            Map<String, String> result = (Map<String, String>) msg.obj;
            EventBus.getDefault().post(result);
        }
    };

    void pay(final String orderInfo) {
        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(mActivity);
                Map<String, String> result = payTask.payV2(orderInfo, true);
                Message message = new Message();
                message.what = 1;
                message.obj = result;
                handler.handleMessage(message);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
