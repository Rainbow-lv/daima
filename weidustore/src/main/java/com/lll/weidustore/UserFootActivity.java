package com.lll.weidustore;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lll.weidustore.adapter.UserFootAdapter;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.UserFootBean;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.WDActivity;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.UserFootPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFootActivity extends WDActivity implements XRecyclerView.LoadingListener{

    @BindView(R.id.foot_recycler)
    XRecyclerView footRecycler;
    private GridLayoutManager gridLayoutManager;
    private UserFootAdapter userFootAdapter;
    private UserFootPresenter userFootPresenter;

    @Override
    protected void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userFootAdapter = new UserFootAdapter(this);

        footRecycler.setLayoutManager(gridLayoutManager);
        footRecycler.setAdapter(userFootAdapter);
        footRecycler.setLoadingListener(this);

        // 设置数据
        userFootPresenter = new UserFootPresenter(new FootCall());

        footRecycler.refresh();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_foot;
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onRefresh() {
        if (userFootPresenter.isRunning()){
            footRecycler.refreshComplete();
            return;
        }
        userFootPresenter.reqeust(true,LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());
    }

    @Override
    public void onLoadMore() {
        if (userFootPresenter.isRunning()){
            footRecycler.loadMoreComplete();
            return;
        }
        userFootPresenter.reqeust(false,LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());

    }

    private class FootCall implements DataCall<Result<List<UserFootBean>>> {

        @Override
        public void success(Result<List<UserFootBean>> data) {
            footRecycler.refreshComplete();
            footRecycler.loadMoreComplete();
            if (data.getStatus().equals("0000")){
                List<UserFootBean> result = data.getResult();
                //添加列表并刷新
                userFootAdapter.addAll(result);
                userFootAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            footRecycler.refreshComplete();
            footRecycler.loadMoreComplete();
        }
    }
}
