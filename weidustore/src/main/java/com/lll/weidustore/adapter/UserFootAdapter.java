package com.lll.weidustore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lll.weidustore.R;
import com.lll.weidustore.app.MyApp;
import com.lll.weidustore.bean.UserFootBean;
import com.lll.weidustore.untils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserFootAdapter extends RecyclerView.Adapter<UserFootAdapter.MyHolder> {
    List<UserFootBean> list = new ArrayList<>();
    Context context;

    public UserFootAdapter(Context context) {
        this.context = context;
    }
    public void addAll(List<UserFootBean> list){
        if (list != null){
            this.list.addAll(list);
        }
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_foot_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        UserFootBean userFootBean = list.get(i);
        Glide.with(MyApp.getContext()).load(userFootBean.getMasterPic()).into(myHolder.foot_image);
        myHolder.foot_context.setText(userFootBean.getCommodityName());
        myHolder.foot_price.setText("￥"+userFootBean.getPrice());
        myHolder.foot_see.setText("已浏览"+userFootBean.getBrowseNum()+"次");
        try {
            myHolder.foot_time.setText(DateUtils.dateFormat(new Date(userFootBean.getBrowseTime()),DateUtils.MINUTE_PATTERN));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView foot_image;
        private final TextView foot_context;
        private final TextView foot_price;
        private final TextView foot_see;
        private final TextView foot_time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            foot_image = itemView.findViewById(R.id.foot_image);
            foot_context = itemView.findViewById(R.id.foot_context);
            foot_price = itemView.findViewById(R.id.foot_price);
            foot_see = itemView.findViewById(R.id.foot_see);
            foot_time = itemView.findViewById(R.id.foot_time);
        }
    }
}
