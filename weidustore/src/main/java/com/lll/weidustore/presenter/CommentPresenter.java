package com.lll.weidustore.presenter;

import com.lll.weidustore.core.BasePresenter;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.ILogin;
import com.lll.weidustore.http.NetWork;

import io.reactivex.Observable;
/*
评论
 */
public class CommentPresenter extends BasePresenter {

    public CommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        ILogin iRequest = NetWork.netWork().create(ILogin.class);
        return iRequest.comment((int)args[0],(int)args[1],(int)args[2]);
    }
}
