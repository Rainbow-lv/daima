package com.lll.weidustore.presenter;

import com.lll.weidustore.core.BasePresenter;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.ILogin;
import com.lll.weidustore.http.NetWork;

import io.reactivex.Observable;

public class AddToCar extends BasePresenter {
    public AddToCar(DataCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = NetWork.netWork().create(ILogin.class);
        return iLogin.addTo((long)args[0],(String) args[1],(String) args[2]);
    }
}
