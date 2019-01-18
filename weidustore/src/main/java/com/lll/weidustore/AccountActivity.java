package com.lll.weidustore;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lll.weidustore.adapter.AccountAdapter;
import com.lll.weidustore.adapter.PopuAddressAdapter;
import com.lll.weidustore.bean.AddressList;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.ShopCar;
import com.lll.weidustore.bean.User;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.UserAddressPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends AppCompatActivity {

    @BindView(R.id.address_text_name)
    TextView addressTextName;
    @BindView(R.id.address_text_phone)
    TextView addressTextPhone;
    @BindView(R.id.address_text_address)
    TextView addressTextAddress;
    @BindView(R.id.ti_image)
    ImageView tiImage;
    @BindView(R.id.ti_recy)
    RecyclerView tiRecy;
    @BindView(R.id.shopping_num)
    TextView shoppingNum;
    @BindView(R.id.shopping_sum)
    TextView shoppingSum;
    @BindView(R.id.tijiao)
    Button tijiao;
    private List<ShopCar> list;
    private AccountAdapter accountAdapter;
    private PopuAddressAdapter popuAddressAdapter;
    private PopupWindow popupWindow;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        DaoSession daoSession = DaoMaster.newDevSession(this, ShopCarDao.TABLENAME);
        ShopCarDao shoppingBeanDao = daoSession.getShopCarDao();
        list = shoppingBeanDao.loadAll();
        getSum();
        accountAdapter = new AccountAdapter();
        accountAdapter.setCountListener(new AccountAdapter.CountListener() {
            @Override
            public void getCount(int count) {
                getSum();
            }
        });
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        accountAdapter.addList(list);
        tiRecy.setLayoutManager(manager);
        tiRecy.setAdapter(accountAdapter);
        DaoSession daoSession1 = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession1.getUserDao();
        List<User> list1 = userDao.loadAll();
        long userId = list1.get(0).getUserId();
        String sessionId = list1.get(0).getSessionId();
        view = View.inflate(this, R.layout.popu_address_layout, null);
        new UserAddressPresenter(new My()).reqeust(userId, sessionId);
        //tiRecy = view.findViewById(R.id.address_recycle_show);
        popuAddressAdapter = new PopuAddressAdapter();
        StaggeredGridLayoutManager manager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        accountAdapter.addList(list);
        tiRecy.setLayoutManager(manager1);
        tiRecy.setAdapter(accountAdapter);
        popuAddressAdapter.setOnCheckListener(new PopuAddressAdapter.CheckListener() {
            @Override
            public void check(AddressList address) {
                String address1 = address.getAddress();
                String phone = address.getPhone();
                String realName = address.getRealName();
                addressTextName.setText(realName);
                addressTextPhone.setText(phone);
                addressTextAddress.setText(address1);
                popupWindow.dismiss();
            }
        });
    }

    @OnClick(R.id.ti_image)
    public void onViewClicked() {
        popupWindow = new PopupWindow(view, 800, 200,
                 true);
//        int height = getWindowManager().getDefaultDisplay().getHeight();
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x000000));
        popupWindow.showAsDropDown(tiImage);

    }

    @OnClick(R.id.tijiao)
    public void onViewClicked(View view) {
        Intent intent = new Intent(AccountActivity.this,ZFActivity.class);
        startActivity(intent);
    }

    class My implements DataCall<Result<List<AddressList>>> {

        @Override
        public void success(Result<List<AddressList>> data) {
            if (data.getStatus().equals("0000")) {
                List<AddressList> result = data.getResult();
                popuAddressAdapter.addList(result);
                popuAddressAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    public void getSum() {
        double sum = 0.0;
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getCount() * list.get(i).getPrice();
            count += list.get(i).getCount();
        }
        shoppingSum.setText(sum + "");
        shoppingNum.setText(count + "");
    }
}
