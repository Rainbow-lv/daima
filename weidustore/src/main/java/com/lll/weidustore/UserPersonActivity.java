package com.lll.weidustore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.User;
import com.lll.weidustore.bean.UserPresonBean;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.WDActivity;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.UserPersonPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPersonActivity extends WDActivity {

    @BindView(R.id.user_headimage)
    SimpleDraweeView userHeadimage;
    @BindView(R.id.user_nicktv)
    TextView userNicktv;
    @BindView(R.id.user_pwdtv)
    TextView userPwdtv;

    @Override
    protected void initView() {
        UserPersonPresenter messagePresenter=new UserPersonPresenter(new personCall());
        messagePresenter.reqeust(LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());
        UserDao userInfoDao = DaoMaster.newDevSession(this, UserDao.TABLENAME).getUserDao();
        List<User> userInfos = userInfoDao.loadAll();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_person;
    }

    @Override
    protected void destoryData() {

    }

    private class personCall implements DataCall<Result<UserPresonBean>> {
        @Override
        public void success(Result<UserPresonBean> data) {
            UserPresonBean result = data.getResult();
            userHeadimage.setImageURI(result.getHeadPic());
            userNicktv.setText(result.getNickName());
            userPwdtv.setText(result.getPhone());
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(),"查询我的资料失败",Toast.LENGTH_SHORT).show();
        }
    }
}
