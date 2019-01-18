package com.lll.weidustore.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lll.weidustore.R;
import com.lll.weidustore.bean.AddressList;

import java.util.ArrayList;
import java.util.List;

public class UserAddressAdapter extends RecyclerView.Adapter<UserAddressAdapter.MyHolder> {
    private List<AddressList> list = new ArrayList<>();
    public void addList(List<AddressList> list1){
        if (list != null){
            list.addAll(list1);
        }
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_item, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder myHolder, final int i) {
        AddressList addressList = list.get(i);
        myHolder.address_name.setText(addressList.getRealName());
        myHolder.address_phone.setText(addressList.getPhone());
        myHolder.address_address.setText(addressList.getAddress());
        myHolder.radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(i).getId();
                onItemclick.onItem(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final TextView address_name;
        private final TextView address_phone;
        private final TextView address_address;
        private final TextView address_delete;
        private final TextView address_update;
        private final RadioButton radio;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            address_name = itemView.findViewById(R.id.address_name);
            address_phone = itemView.findViewById(R.id.address_phone);
            address_address = itemView.findViewById(R.id.address_address);
            address_delete = itemView.findViewById(R.id.address_delete);
            address_update = itemView.findViewById(R.id.address_update);
            radio = itemView.findViewById(R.id.address_radio);
        }
    }
    private OnItemclick onItemclick;
    public interface OnItemclick{
        void onItem(int position);
    }
    public void setOnItemclicks(OnItemclick onItemclick){
        this.onItemclick = onItemclick;
    }
}
