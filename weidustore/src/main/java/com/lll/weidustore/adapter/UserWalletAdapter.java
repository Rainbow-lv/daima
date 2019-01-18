package com.lll.weidustore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lll.weidustore.R;
import com.lll.weidustore.bean.CircleBean;
import com.lll.weidustore.bean.UserWallet;
import com.lll.weidustore.untils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserWalletAdapter extends RecyclerView.Adapter<UserWalletAdapter.MyHolder> {
    List<UserWallet.DetailListBean> list = new ArrayList<>();
    Context context;

    public UserWalletAdapter(Context context) {
        this.context = context;
    }

    public void addAll(List<UserWallet.DetailListBean> list){
        if (list!=null){
            this.list.addAll(list);
        }
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_wallet_item, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        UserWallet.DetailListBean detailListBean = list.get(i);
        myHolder.wallet_money.setText("￥"+detailListBean.getAmount());
        try {
            myHolder.wallet_time.setText(DateUtils.dateFormat(new Date(detailListBean.getCreateTime()),DateUtils.MINUTE_PATTERN));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final TextView wallet_money;
        private final TextView wallet_time;
        private final ImageView wallet_next;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            wallet_money = itemView.findViewById(R.id.wallet_money);
            wallet_time = itemView.findViewById(R.id.wallet_time);
            wallet_next = itemView.findViewById(R.id.wallet_next);
        }
    }
}
