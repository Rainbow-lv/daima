package com.lll.weidustore.presenter;

import com.lll.weidustore.bean.CircleBean;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.core.BasePresenter;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.ILogin;
import com.lll.weidustore.http.NetWork;

import java.util.List;

import io.reactivex.Observable;

public class DetailsPresenter extends BasePresenter {
    public DetailsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = NetWork.netWork().create(ILogin.class);
        return iLogin.details((long)args[0],(String) args[1],(int)args[2]);
    }
}
