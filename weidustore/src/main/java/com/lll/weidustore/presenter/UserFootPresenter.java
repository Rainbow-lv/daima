package com.lll.weidustore.presenter;

import com.lll.weidustore.bean.CircleBean;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.core.BasePresenter;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.ILogin;
import com.lll.weidustore.http.NetWork;

import java.util.List;

import io.reactivex.Observable;

public class UserFootPresenter extends BasePresenter {
    int page = 1;
    private boolean refresh;

    public UserFootPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iLogin = NetWork.netWork().create(ILogin.class);
        refresh = (boolean)args[0];
        if (refresh){
            page = 1;
        }else{
            page++;
        }
        return iLogin.foot((long)args[1],(String)args[2],page,5);
    }
    public boolean refresh() {
        return refresh;
    }
}
