package com.lll.weidustore.presenter;

import com.lll.weidustore.core.BasePresenter;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.ILogin;
import com.lll.weidustore.http.NetWork;

import io.reactivex.Observable;

public class Null_Pay_Presenter extends BasePresenter {
    public Null_Pay_Presenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = NetWork.netWork().create(ILogin.class);
        return iLogin.order((long) args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
