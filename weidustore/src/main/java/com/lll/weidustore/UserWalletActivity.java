package com.lll.weidustore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.weidustore.adapter.UserWalletAdapter;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.UserWallet;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.WDActivity;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.UserWalletPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserWalletActivity extends WDActivity {

    @BindView(R.id.user_money)
    TextView userMoney;
    @BindView(R.id.wa_record)
    RecyclerView waRecord;
    private UserWalletPresenter userWalletPresenter;
    private LinearLayoutManager linearLayoutManager;
    private UserWalletAdapter userWalletAdapter;


    @Override
    protected void initView() {
        //创建对象
        userWalletPresenter = new UserWalletPresenter(new WalletCall());
        userWalletPresenter.reqeust(LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());
        linearLayoutManager = new LinearLayoutManager(this);
        waRecord.setLayoutManager(linearLayoutManager);
        //自定义适配器
        userWalletAdapter = new UserWalletAdapter(this);
        //设置适配器
        waRecord.setAdapter(userWalletAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_wallet;
    }

    @Override
    protected void destoryData() {

    }

    private class WalletCall implements DataCall<Result<UserWallet>> {

        @Override
        public void success(Result<UserWallet> data) {
            UserWallet result = data.getResult();
            userMoney.setText(result.getBalance() + "");
            userWalletAdapter.addAll((List<UserWallet.DetailListBean>) result);
            userWalletAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(), "查询钱包失败", Toast.LENGTH_SHORT).show();
        }
    }
}
