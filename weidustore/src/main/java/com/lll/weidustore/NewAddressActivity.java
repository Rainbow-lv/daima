package com.lll.weidustore;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.User;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.UserNewPrsenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAddressActivity extends AppCompatActivity {

    @BindView(R.id.new_name)
    EditText newName;
    @BindView(R.id.new_Phone)
    EditText newPhone;
    @BindView(R.id.tv_address)
    EditText tvAddress;
    @BindView(R.id.ed_Address)
    EditText edAddress;
    @BindView(R.id.ed_Postal)
    EditText edPostal;
    @BindView(R.id.btn_sur)
    Button btnSur;
    private UserNewPrsenter userNewPrsenter;
    private CityPicker cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.tv_address,R.id.btn_sur})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_address:
                MyCityPrice();
                cp.show();
                break;
            case R.id.btn_sur:
                Toast.makeText(getBaseContext(),"点击了",Toast.LENGTH_SHORT).show();

                DaoSession daoSession = DaoMaster.newDevSession(NewAddressActivity.this, UserDao.TABLENAME);
                UserDao userDao = daoSession.getUserDao();
                List<User> list = userDao.loadAll();
                long userId = list.get(0).getUserId();
                String sessionId = list.get(0).getSessionId();
                String name = newName.getText().toString();
                String phone = newPhone.getText().toString();
                String province = tvAddress.getText().toString();
                String village = edAddress.getText().toString();
                String Postal = edPostal.getText().toString();
                userNewPrsenter = new UserNewPrsenter(new NewCall());
                userNewPrsenter.reqeust(userId,sessionId,name,phone,province+village,Postal);
                break;   
        }
    }


    private class NewCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if(data.getStatus().equals("0000")){
                Intent intent = new Intent(NewAddressActivity.this,UserAddressActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(),"失败",Toast.LENGTH_SHORT).show();
        }
    }
    private void MyCityPrice() {

        cp = new CityPicker.Builder(this)
                .textSize(20)
                //地址选择
                .title("地址选择")
                .backgroundPop(0xa0000000)
                //文字的颜色
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .district("xx区")
                //滑轮文字的颜色
                .textColor(Color.parseColor("#000000"))
                //省滑轮是否循环显示
                .provinceCyclic(true)
                //市滑轮是否循环显示
                .cityCyclic(false)
                //地区（县）滑轮是否循环显示
                .districtCyclic(false)
                //滑轮显示的item个数
                .visibleItemsCount(7)
                //滑轮item间距
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        //监听
        cp.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省
                String province = citySelected[0];
                //市
                String city = citySelected[1];
                //区。县。（两级联动，必须返回空）
                String district = citySelected[2];
                //邮证编码
                String code = citySelected[3];

                tvAddress.setText(province + city + district);
            }

            @Override
            public void onCancel() {

            }
        });
    }
}
