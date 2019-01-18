package com.lll.weidustore.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lll.weidustore.R;
import com.lll.weidustore.bean.Null_Bean;

import java.util.ArrayList;
import java.util.List;
/*
代付款的adapter
 */

public class Null_Pay_Item_Adapter extends RecyclerView.Adapter<Null_Pay_Item_Adapter.ViewHolder> {
    private final FragmentActivity context;
    private List<Null_Bean.DetailListBean> list;

    public Null_Pay_Item_Adapter(FragmentActivity activity) {
        this.context=activity;
        this.list=new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.null_pay_item_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String commodityPic = list.get(position).getCommodityPic();
        String[] split = commodityPic.split(",");
        holder.img.setImageURI(split[0]);
        holder.price.setText("￥"+list.get(position).getCommodityPrice()+"");
           holder.tv_channelName.setText(list.get(position).getCommodityName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Null_Bean.DetailListBean> detailList) {
        if (this.list!=null){
            this.list.clear();
            this.list=detailList;
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView img;
        TextView tv_channelName,price;

        public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            tv_channelName=itemView.findViewById(R.id.tv_channelName);
            price=itemView.findViewById(R.id.price);

        }
    }
}
