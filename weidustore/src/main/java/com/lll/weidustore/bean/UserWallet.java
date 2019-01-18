package com.lll.weidustore.bean;

import java.util.List;

public class UserWallet {
//"balance": 99999999,
//        "detailList": []
    private int balance;
    private List<DetailListBean> detailList;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public static class DetailListBean {
        /**
         * amount : 2
         * createTime : 1542476199000
         */
        private double amount;
        private long createTime;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
