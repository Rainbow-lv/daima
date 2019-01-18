package com.lll.weidustore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lll.weidustore.DaoMaster;
import com.lll.weidustore.MainActivity;
import com.lll.weidustore.R;
import com.lll.weidustore.UserAddressActivity;
import com.lll.weidustore.UserCircleActivity;
import com.lll.weidustore.UserDao;
import com.lll.weidustore.UserFootActivity;
import com.lll.weidustore.UserPersonActivity;
import com.lll.weidustore.UserWalletActivity;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.UserPresonBean;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.WDFragment;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.UserPersonPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Frag_05 extends WDFragment implements View.OnClickListener {

    @BindView(R.id.user_person)
    TextView userPerson;
    @BindView(R.id.person_name)
    TextView personname;
    @BindView(R.id.user_circle)
    TextView userCircle;
    @BindView(R.id.user_foot)
    TextView userFoot;
    @BindView(R.id.user_wallet)
    TextView userWallet;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.logout_btn)
    Button logoutbtn;
    @BindView(R.id.head_image)
    SimpleDraweeView headimage;
    Unbinder unbinder;
    private UserPersonPresenter userPersonPresenter;


    @Override
    public String getPageName() {
        return "退出登录";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_05;
    }

    @Override
    protected void initView() {
        userPersonPresenter = new UserPersonPresenter(new PersonCall());
        userPersonPresenter.reqeust(LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());
        userPerson.setOnClickListener(this);
        userCircle.setOnClickListener(this);
        userFoot.setOnClickListener(this);
        userWallet.setOnClickListener(this);
        userAddress.setOnClickListener(this);
        logoutbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_person:
                startActivity(new Intent(getActivity(), UserPersonActivity.class));
                break;
            case R.id.user_circle:
                startActivity(new Intent(getActivity(), UserCircleActivity.class));
                break;
            case R.id.user_foot:
                startActivity(new Intent(getActivity(), UserFootActivity.class));
                break;
            case R.id.user_wallet:
                startActivity(new Intent(getActivity(), UserWalletActivity.class));
                break;
            case R.id.user_address:
                startActivity(new Intent(getActivity(), UserAddressActivity.class));
                break;
            case R.id.logout_btn:
                UserDao userInfoDao = DaoMaster.newDevSession(getActivity(), UserDao.TABLENAME).getUserDao();
                userInfoDao.delete(LOGIN_USER);//Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//重点
                startActivity(intent);//重点

                break;
        }
    }

    private class PersonCall implements DataCall<Result<UserPresonBean>> {
        @Override
        public void success(Result<UserPresonBean> data) {
            UserPresonBean result = data.getResult();
            personname.setText(result.getNickName());
            headimage.setImageURI(result.getHeadPic());
//            Log.i("图片","*****"+result.getHeadPic().toString());

        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
