package com.lll.weidustore.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lll.weidustore.R;
import com.lll.weidustore.adapter.Take_goods_Adapter;
import com.lll.weidustore.bean.Null_Bean;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.WDFragment;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.Null_Pay_Presenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Fragment03 extends WDFragment {
    @BindView(R.id.recy)
    RecyclerView recy;
    private Null_Pay_Presenter null_pay_presenter;
    private Take_goods_Adapter take_goods_adapter;
    @Override
    public String getPageName() {
        return "待收货";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment03;
    }

    @Override
    protected void initView() {
        String sessionId = LOGIN_USER.getSessionId();
        long userId = LOGIN_USER.getUserId();

        null_pay_presenter = new Null_Pay_Presenter(new CallBack());
        null_pay_presenter.reqeust(userId,sessionId,1,5,2); //最后一个参数改0=查看全部 1=查看待付款 2=查看待收货 3=查看待评价 9=查看已完成
        take_goods_adapter = new Take_goods_Adapter(getActivity());
        recy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recy.setAdapter(take_goods_adapter);
    }

    private class CallBack implements DataCall<Result<List<Null_Bean>>> {
        @Override
        public void success(Result<List<Null_Bean>> data) {
            take_goods_adapter.setData(data.getOrderList());
            Log.e("aaa",data.getMessage()+"===");
            Log.e("aaa",data.getOrderList().size()+"===");
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
