package com.lll.weidustore;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lll.weidustore.core.WDActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends WDActivity {

    @BindView(R.id.seek_text)
    TextView seekText;

    private int count = 3;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            count--;
            seekText.setText("跳过"+count+"s");
            if(count==0){
                if(LOGIN_USER!=null){
                    intent(HomeActivity.class);
                    finish();
                }else{
                    intent(MainActivity.class);
                    finish();
                }
            }else {//消息不能复用，必须新建
                handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };
    @Override
    protected void initView() {
        seekText.setText("跳过"+count+"s");
        handler.sendEmptyMessageDelayed(1,1000);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void destoryData() {

    }
    @OnClick(R.id.seek_text)
    public void onClick(){
        handler.removeMessages(1);
        if(LOGIN_USER!=null){//有数据登录
            intent(HomeActivity.class);
            finish();
        }else{//没有数据注册
            intent(MainActivity.class);
            finish();
        }
    }
}
