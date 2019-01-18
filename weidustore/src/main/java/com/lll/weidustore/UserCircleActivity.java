package com.lll.weidustore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lll.weidustore.adapter.CircleAdapter;
import com.lll.weidustore.bean.CircleBean;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.WDActivity;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.CirclePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserCircleActivity extends WDActivity implements XRecyclerView.LoadingListener{


    @BindView(R.id.usercircle_delete)
    ImageView userdelete;
    @BindView(R.id.usercircle_recy)
    XRecyclerView usercircleRecy;
    private CirclePresenter circlePresenter;
    private CircleAdapter circleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_circle);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        circleAdapter = new CircleAdapter(this);

        usercircleRecy.setLayoutManager(new GridLayoutManager(this,1));

        int space = getResources().getDimensionPixelSize(R.dimen.dip_20);

        //给列表加上边距
        usercircleRecy.addItemDecoration(new SpacingItemDecoration(space));
        //设置适配器
        usercircleRecy.setAdapter(circleAdapter);
        //创建对象
        circlePresenter = new CirclePresenter(new UserCircleCall());
        usercircleRecy.setLoadingListener(this);
        //刷新
        usercircleRecy.refresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_circle;
    }

    @Override
    protected void destoryData() {
        circlePresenter.unBind();
    }

    @Override
    public void onRefresh() {
        if (circlePresenter.isRunning()){
            usercircleRecy.refreshComplete();
            return;
        }
        circlePresenter.reqeust(true,LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());
    }

    @Override
    public void onLoadMore() {
        if (circlePresenter.isRunning()){
            usercircleRecy.loadMoreComplete();
            return;
        }
        circlePresenter.reqeust(false,LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());
    }

    private class UserCircleCall implements DataCall<Result<List<CircleBean>>> {
        @Override
        public void success(Result<List<CircleBean>> data) {
            usercircleRecy.refreshComplete();
            usercircleRecy.loadMoreComplete();
            if (data.getStatus().equals("0000")){
                //添加列表并刷新

                int size = data.getResult().size();
                Toast.makeText(getBaseContext(), ""+size, Toast.LENGTH_SHORT).show();
                circleAdapter.addAll(data.getResult());
                circleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            usercircleRecy.refreshComplete();
            usercircleRecy.loadMoreComplete();
            Toast.makeText(getBaseContext(), "失败", Toast.LENGTH_SHORT).show();
        }
    }
}
