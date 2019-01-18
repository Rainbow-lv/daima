package com.lll.weidustore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lll.weidustore.AddCircleActivity;
import com.lll.weidustore.R;
import com.lll.weidustore.SpacingItemDecoration;
import com.lll.weidustore.adapter.CircleAdapter;
import com.lll.weidustore.bean.CircleBean;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.WDFragment;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.CirclePresenter;
import com.lll.weidustore.presenter.GreatPresenter;
import com.lll.weidustore.untils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Frag_02 extends WDFragment implements XRecyclerView.LoadingListener,CircleAdapter.GreatListener{

    @BindView(R.id.circle_list)
    XRecyclerView circleList;
    private CirclePresenter circlePresenter;
    private CircleAdapter circleAdapter;
    public static boolean addCircle;//如果添加成功，则为true
    private GreatPresenter greatPresenter;

    @Override
    public String getPageName() {
        return "圈子Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_02;
    }

    @Override
    protected void initView() {
        circleAdapter = new CircleAdapter(getContext());

        circleList.setLayoutManager(new GridLayoutManager(getContext(),1));

        int space = getResources().getDimensionPixelSize(R.dimen.dip_20);

        //给列表加上边距
        circleList.addItemDecoration(new SpacingItemDecoration(space));
        //设置适配器
        circleList.setAdapter(circleAdapter);
        //创建对象
        circlePresenter = new CirclePresenter(new CircleCall());
        circleList.setLoadingListener(this);

        //刷新
        circleList.refresh();
    }

    @Override
    public void onRefresh() {
        if (circlePresenter.isRunning()){
            circleList.refreshComplete();
            return;
        }
        circlePresenter.reqeust(true,LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());
    }

    @Override
    public void onLoadMore() {
        if (circlePresenter.isRunning()){
            circleList.loadMoreComplete();
            return;
        }
        circlePresenter.reqeust(false,LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());
    }

    @OnClick(R.id.add_circle)
    public void addCircle(){
        Intent intent = new Intent(getContext(),AddCircleActivity.class);
        startActivity(intent);
    }

    @Override
    public void great(int position, CircleBean circle) {
        //创建点赞的对象
        greatPresenter = new GreatPresenter(new GreatCall());
        greatPresenter.reqeust(LOGIN_USER.getUserId()+"",LOGIN_USER.getSessionId(),circle.getId(),position,circle);
    }

    private class CircleCall implements DataCall<Result<List<CircleBean>>> {
        @Override
        public void success(Result<List<CircleBean>> data) {
            circleList.refreshComplete();
            circleList.loadMoreComplete();
            if (data.getStatus().equals("0000")){
                //添加列表并刷新

                int size = data.getResult().size();
                Toast.makeText(getContext(), ""+size, Toast.LENGTH_SHORT).show();
                circleAdapter.addAll(data.getResult());
                circleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            circleList.refreshComplete();
            circleList.loadMoreComplete();
            Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
        }
    }

    private class GreatCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            int position = (int) data.getArgs()[3];
            UIUtils.showToastSafe("点击"+position+ "    adapter条目："+circleAdapter.getItemCount()
                    +"    recycler条目："+circleList.getChildCount());
            CircleBean circle = (CircleBean) data.getArgs()[4];
            if (data.getStatus().equals("0000")){
                CircleBean nowCircle = circleAdapter.getItem(position);
                if (nowCircle.getId() == circle.getId()){
                    nowCircle.setGreatNum(nowCircle.getGreatNum()+1);
                    nowCircle.setWhetherGreat(1);
                    circleAdapter.notifyItemChanged(position+1,0);
                }
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
