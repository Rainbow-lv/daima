package com.lll.weidustore.presenter;

import com.lll.weidustore.core.BasePresenter;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.ILogin;
import com.lll.weidustore.http.NetWork;

import io.reactivex.Observable;

public class GreatPresenter extends BasePresenter {
    public GreatPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = NetWork.netWork().create(ILogin.class);
        return iLogin.addCircleGreat((String)args[0],(String)args[1],(long)args[2]);
    }
}
