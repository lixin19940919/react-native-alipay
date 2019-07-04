package com.lixin.alipay.pay;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

public class AliPayModule extends ReactContextBaseJavaModule {

    private final static String MODULE_NAME = "aliPay";
    private Callback successCallback;

    AliPayModule(ReactApplicationContext reactContext) {
        super(reactContext);
        EventBus.getDefault().register(this);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void aliPay(String orderInfo, Callback successCallback) {
        AliPay aliPay = new AliPay(getCurrentActivity());
        this.successCallback = successCallback;
        aliPay.pay(orderInfo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Map<String, String> map) {
        this.successCallback.invoke(map.toString());
    }
}
