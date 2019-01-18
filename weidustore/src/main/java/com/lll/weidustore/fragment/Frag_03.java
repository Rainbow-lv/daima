package com.lll.weidustore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.lll.weidustore.AccountActivity;
import com.lll.weidustore.DaoMaster;
import com.lll.weidustore.DaoSession;
import com.lll.weidustore.R;
import com.lll.weidustore.ShopCarDao;
import com.lll.weidustore.UserDao;
import com.lll.weidustore.adapter.ShopAdapter;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.ShopCar;
import com.lll.weidustore.bean.User;
import com.lll.weidustore.core.DataCall;
import com.lll.weidustore.core.exception.ApiException;
import com.lll.weidustore.presenter.ShoppingPresenter;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Frag_03 extends Fragment implements ShopAdapter.TotalPriceListener{

    @BindView(R.id.expandableListView)
    SwipeMenuRecyclerView expandableListView;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;
    @BindView(R.id.sumss)
    TextView sumss;
    @BindView(R.id.jiesuan)
    Button jiesuan;
    Unbinder unbinder;
    private UserDao userDao;
    private long userId;
    private String sessionId;
    private ShopAdapter shopAdapter;
    private ShoppingPresenter shoppingPresenter;
    private List<ShopCar> result;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_03, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaoSession daoSession1 = DaoMaster.newDevSession(getContext(), UserDao.TABLENAME);
        userDao = daoSession1.getUserDao();
        final List<User> list2 = userDao.loadAll();
        userId = (int) list2.get(0).getUserId();
        sessionId = list2.get(0).getSessionId();

        //计算总价
        sumss = view.findViewById(R.id.sumss);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        expandableListView.setLayoutManager(linearLayoutManager);
        //创建适配器
        shopAdapter = new ShopAdapter(getActivity());
        //设置适配器

        shopAdapter.setTotalPriceListener((ShopAdapter.TotalPriceListener) this);//设置总价回调接口
        //获取全选控件
        CheckBox checkboxs = view.findViewById(R.id.checkbox1);
        checkboxs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //调用adapter里面的全选/全不选方法
                shopAdapter.checkAll(isChecked);
            }
        });

        expandableListView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                SwipeMenuItem swipeMenuItem = new SwipeMenuItem(getContext());
                swipeMenuItem.setBackgroundColor(0xff00000)
                        .setText("删除")
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)//设置高，这里使用match_parent，就是与item的高相同
                        .setWidth(80);//设置宽
                rightMenu.addMenuItem(swipeMenuItem);//设置右边的侧滑
            }
        });
        expandableListView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。0是左，右是1，暂时没有用到
                //int adapterPosition = menuBridge.get; // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                //Toast.makeText(getContext(), "删除" + position, Toast.LENGTH_SHORT).show();
                result.remove(position);
                //ShopCar shoppingTrolleyAdapter = new ShopCar(getContext());
                shopAdapter.addItem(result);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                expandableListView.setLayoutManager(linearLayoutManager);
                expandableListView.setAdapter(shopAdapter);

            }
        });
        expandableListView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //Toast.makeText(getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setAdapter(shopAdapter);

        //调用P层方法
        shoppingPresenter = new ShoppingPresenter(new Shopping());
        shoppingPresenter.reqeust(userId,sessionId);
        return view;
    }

    //接口回调
    @Override
    public void totalPrice(double totalPrice) {
        sumss.setText("合计：￥" + totalPrice);
    }


    @Override
    public void onResume() {
        super.onResume();
        shoppingPresenter.reqeust(userId, sessionId);
    }


    private class Shopping implements DataCall<Result<List<ShopCar>>> {

        @Override
        public void success(Result<List<ShopCar>> data) {
            result = data.getResult();
            shopAdapter.remove();
            shopAdapter.addItem(result);
            //刷新适配器
            shopAdapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }
    @OnClick(R.id.jiesuan)
    public void jiesuan() {
        List<ShopCar> shoppingBeans = shopAdapter.close();
        DaoSession daoSession = DaoMaster.newDevSession(getActivity(), ShopCarDao.TABLENAME);
        ShopCarDao shopCarDao = daoSession.getShopCarDao();
        shopCarDao.deleteAll();
        for (int i = 0; i < shoppingBeans.size(); i++) {
            ShopCar shopCar = shoppingBeans.get(i);
            shopCar.setId(i);
            shopCarDao.insertOrReplace(shopCar);
        }
        Intent intent = new Intent(getActivity(), AccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
