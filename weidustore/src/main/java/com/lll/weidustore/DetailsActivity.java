package com.lll.weidustore;

import android.content.Intent;
import android.graphics.Color;

import android.support.v4.view.ViewPager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import com.google.gson.Gson;
import com.lll.weidustore.adapter.ViewPageDetailsBannerAdapter;
import com.lll.weidustore.bean.DetailsBean;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.ShopCar;
import com.lll.weidustore.bean.User;
import com.lll.weidustore.bean.XQBean;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.WDActivity;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.http.myScrollview.MyScrollView;
import com.lll.weidustore.presenter.AddToCar;
import com.lll.weidustore.presenter.CommentPresenter;
import com.lll.weidustore.presenter.DetailsPresenter;
import com.lll.weidustore.presenter.ShoppingPresenter;
import com.lll.weidustore.untils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 详情页面
 */
public class DetailsActivity extends WDActivity implements View.OnClickListener {
    List<User> list = new ArrayList<>();
    @BindView(R.id.details_viewpager_show)
    ViewPager viewPager;
    @BindView(R.id.details_textview_shownum)
    TextView detailsTextviewShownum;
    @BindView(R.id.details_textview_sprice)
    TextView Sprice;
    @BindView(R.id.details_textview_sold)
    TextView Sold;
    @BindView(R.id.details_textview_title)
    TextView Title;
    @BindView(R.id.details_textview_Weight)
    TextView Weight;
    @BindView(R.id.details_scroll_changecolor)
    MyScrollView details_scroll_changecolor;
    @BindView(R.id.details_Image_details)
    SimpleDraweeView details_Image_details;
    @BindView(R.id.details_textview_describe)
    TextView Describe;//描述，形容，描绘
    @BindView(R.id.details_Image_describe)
    SimpleDraweeView details_Image_describe;
    @BindView(R.id.details_recview_comments)
    RecyclerView detailsRecviewComments;
    @BindView(R.id.details_textview_comments)
    TextView detailsTextviewComments;
    @BindView(R.id.details_image_return)
    ImageButton DetailsBeanReturn;

    @BindView(R.id.details_text_detailsT)
    TextView detailsTextDetailsT;
    @BindView(R.id.details_text_commentsT)
    TextView detailsTextCommentsT;
    @BindView(R.id.details_text_goods)
    TextView details_text_goods;
    @BindView(R.id.details_text_details)
    TextView details_text_details;
    @BindView(R.id.details_text_comments)
    TextView details_text_comments;
    @BindView(R.id.details_relative_changer)
    RelativeLayout details_relative_changer;
    @BindView(R.id.details_relat_changecolor)
    RelativeLayout details_relat_changecolor;
    @BindView(R.id.btn_cart)
    ImageView btnCart;
    @BindView(R.id.details_relative_addshoppingcar)
    RelativeLayout detailsRelativeAddshoppingcar;
    @BindView(R.id.btn_buy)
    ImageView btnBuy;
    @BindView(R.id.details_relative_pay)
    RelativeLayout detailsRelativePay;
    private ViewPageDetailsBannerAdapter viewPageDetailsBannerAdapter;
    private DetailsPresenter detailsPresenter;
    private String sessionId;
    private long userId;
    private List<XQBean> list2 = new ArrayList<>();
    private AddToCar addToCar;
    private DetailsBean detailsBean;
    private ShoppingPresenter shoppingPresenter;
    private ShopCar shopCar;
    private int commodityId;
    //    private int count;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        commodityId = intent.getIntExtra("commodityId", 0);

        DaoSession daoSession = DaoMaster.newDevSession(this, UserDao.TABLENAME);
        UserDao userDao = daoSession.getUserDao();

        List<User> users = userDao.loadAll();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            userId = user.getUserId();
            sessionId = user.getSessionId();
        }

        //详情
        detailsPresenter = new DetailsPresenter(new DetailsBeanCall());
        detailsPresenter.reqeust(userId,sessionId, commodityId);

//        DetailsBeanPresenter.reqeust(962, "1546761893454962", commodityId);
//        DetailsBeanPresenter.reqeust(commodityId);
        //评论
        CommentPresenter commentPresenter = new CommentPresenter(new CommentCall());
        commentPresenter.reqeust(commodityId, 1, 5);
        new ShoppingPresenter(new MyC()).reqeust((long) userId, sessionId);
        DetailsBeanReturn.setOnClickListener(this);
        btnCart.setOnClickListener(this);
        //scrowller的滑动方法
        details_scroll_changecolor.setScrollviewListener(new MyScrollView.ScrollviewListener() {
            @Override
            public void OnScrollChange(MyScrollView scrollView, int l, int t, int oldl, int oldt) {
                if (t <= 0) {
                    //标题显示
                    details_relative_changer.setVisibility(View.GONE);
                    //设置标题所在的背景颜色为透明
                    details_relat_changecolor.setBackgroundColor(Color.argb(0, 0, 0, 0));
                } else if (t > 0 && t < 200) {
                    details_relative_changer.setVisibility(View.VISIBLE);
                    //获取ScrollView想下滑动,图片消失部分的比例
                    float scale = (float) t / 200;
                    //根据比例,让标题的颜色由浅入深
                    float alpha = 255 * scale;
                    //设置标题布局的颜色
                    details_relat_changecolor.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));

                }
                if (0 < t && t < 700) {
                    details_text_goods.setBackgroundColor(Color.RED);
                    details_text_details.setBackgroundColor(Color.WHITE);
                    details_text_comments.setBackgroundColor(Color.WHITE);
                } else if (701 < t && t < 1500) {
                    details_text_goods.setBackgroundColor(Color.WHITE);
                    details_text_details.setBackgroundColor(Color.RED);
                    details_text_comments.setBackgroundColor(Color.WHITE);
                } else if (t > 1500) {
                    details_text_goods.setBackgroundColor(Color.WHITE);
                    details_text_details.setBackgroundColor(Color.WHITE);
                    details_text_comments.setBackgroundColor(Color.RED);
                }

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    protected void destoryData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_image_return:
                finish();
                break;
            case R.id.btn_cart:
                //Intent intent = getIntent();
                //String id = intent.getStringExtra("commodityId");
                XQBean xqBean = new XQBean();
                xqBean.setCommodityId(commodityId);
                xqBean.setCount(1);
                list2.add(xqBean);
                Gson gson = new Gson();
                String s = gson.toJson(list2);
                new AddToCar(new MyCall()).reqeust((long) userId, sessionId, s);
                break;
        }
    }


    /*
    详情
     */
    private class DetailsBeanCall implements DataCall<Result<DetailsBean>> {
        @Override
        public void success(Result<DetailsBean> data) {
            Log.i("tag","哈哈"+data.getResult().toString());
            DetailsBean result = data.getResult();
            String trim = result.getPicture().trim();
            int weight = result.getWeight();//重量
            result.getCategoryName();//类别名称
            String commodityName = result.getCommodityName();//商品名称
            String describe = result.getDescribe();//描述
            String details = result.getDetails();//商品详情html
            int commentNum = result.getCommentNum();//数量
            double price = result.getPrice();//价格
            Sold.setText(commentNum + "");
            Title.setText(commodityName);
            Describe.setText(describe);
            Weight.setText(weight + "");
            Sprice.setText(price + "");
            String[] split = trim.split(",");
            details_Image_details.setImageURI(split[0]);
            details_Image_describe.setImageURI(split[1]);
            viewPageDetailsBannerAdapter = new ViewPageDetailsBannerAdapter(split, DetailsActivity.this);
            int count = viewPageDetailsBannerAdapter.getCount();
            viewPager.setAdapter(viewPageDetailsBannerAdapter);

//            Toast.makeText(getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void fail(ApiException e) {
//            Toast.makeText(getBaseContext(),"失败",Toast.LENGTH_LONG).show();
            UIUtils.showToastSafe(e.getCode() + e.getDisplayMessage());
        }
    }

    /*
    评论
     */
    private class CommentCall implements DataCall<Result<List<DetailsBean>>> {
        @Override
        public void success(Result<List<DetailsBean>> data) {
//            Toast.makeText(getBaseContext(), "" + data.getResult(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getBaseContext(), "评论查询失败", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyCall implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(DetailsActivity.this, data.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }

    private class MyC implements DataCall<Result<List<ShopCar>>> {
        @Override
        public void success(Result<List<ShopCar>> data) {
            List<ShopCar> result = data.getResult();
            for (int i = 0; i < result.size(); i++) {
                shopCar = result.get(i);
                int commodityId = shopCar.getCommodityId();
                int count = shopCar.getCount();
                XQBean xqBean = new XQBean();
                xqBean.setCount(count);
                xqBean.setCommodityId(commodityId);
                list2.add(xqBean);
            }
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
