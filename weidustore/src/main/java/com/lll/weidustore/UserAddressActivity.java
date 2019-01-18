package com.lll.weidustore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.weidustore.adapter.UserAddressAdapter;
import com.lll.weidustore.bean.AddressList;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.User;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.DefautPresenter;
import com.lll.weidustore.presenter.UserAddressPresenter;
import com.lll.weidustore.presenter.UserNewPrsenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAddressActivity extends AppCompatActivity {

    @BindView(R.id.address_recy)
    RecyclerView addressRecy;
    @BindView(R.id.btn_address)
    Button btnAddress;
    @BindView(R.id.address_finish)
    TextView addressFinish;
    private long userId;
    private String sessionId;
    private UserAddressPresenter userAddressPresenter;
    private UserAddressAdapter userAddressAdapter;
    private int a;
    private DefautPresenter defautPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();
        List<User> list = userDao.loadAll();
        userId = list.get(0).getUserId();
        sessionId = list.get(0).getSessionId();
        userAddressPresenter = new UserAddressPresenter(new AddressCall());
        userAddressPresenter.reqeust(userId,sessionId);
        //自定义适配器
        userAddressAdapter = new UserAddressAdapter();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        addressRecy.setLayoutManager(manager);
        addressRecy.setAdapter(userAddressAdapter);
        userAddressAdapter.setOnItemclicks(new UserAddressAdapter.OnItemclick() {
            @Override
            public void onItem(int position) {
                a = position;
            }
        });
    }
    @OnClick({R.id.address_finish,R.id.btn_address})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.address_finish:
                defautPresenter = new DefautPresenter(new NewAddressCall());
                defautPresenter.reqeust((int)userId,sessionId,a);
                finish();
                break;
            case R.id.btn_address:
                startActivity(new Intent(UserAddressActivity.this,NewAddressActivity.class));
                finish();
                break;
        }
    }
    private class AddressCall implements DataCall<Result<List<AddressList>>> {
        @Override
        public void success(Result<List<AddressList>> data) {
            List<AddressList> result = data.getResult();
            userAddressAdapter.addList(result);
            userAddressAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class NewAddressCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")){
                Toast.makeText(UserAddressActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
