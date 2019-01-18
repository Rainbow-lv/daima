package com.lll.weidustore.http.addSubView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.weidustore.R;

public class MyAddSubView extends LinearLayout implements View.OnClickListener {
    private TextView txtSub;
    private TextView txtNumber;
    private TextView txtAdd;
    int number = 1;

    public MyAddSubView(Context context) {
        this(context,null);
    }

    public MyAddSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyAddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.cart_addsub_view, this);
        txtSub = view.findViewById(R.id.txt_sub);
        txtNumber = view.findViewById(R.id.txt_number);
        txtAdd = view.findViewById(R.id.txt_add);
        txtSub.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_sub:
                //如果数量少于1就提示用户不能再减少了
                if (number<1){
                    Toast.makeText(getContext(), "不能再减少了", Toast.LENGTH_SHORT).show();
                }else {
                    number--;
                    txtNumber.setText(number+"");
                    if (mOnAddSubviewListener !=null){
                        mOnAddSubviewListener.onNumberChange(number);
                    }
                }
                break;
            case R.id.txt_add:
                ++number;
                txtNumber.setText(number+"");
                if (mOnAddSubviewListener!=null){
                    mOnAddSubviewListener.onNumberChange(number);
                }
                break;
        }
    }

    public int getnumber(){
        return number;
    }

    public void setnumber(int number){
        this.number = number;
        txtNumber.setText(number+"");
    }
    /**
     * 接口回调
     */
    public interface OnAddSubviewListener{
        void onNumberChange(int num);
    }
    public OnAddSubviewListener mOnAddSubviewListener;
    public void setOnAddSubviewListener(OnAddSubviewListener onAddSubviewListener){
        mOnAddSubviewListener = onAddSubviewListener;
    }
}
