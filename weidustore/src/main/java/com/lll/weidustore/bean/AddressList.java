package com.lll.weidustore.bean;

public class AddressList {
    /**
     * address : 北京 海淀区 八维
     * createTime : 1547052088000
     * id : 466
     * phone : 13021281010
     * realName : 王哈哈
     * userId : 812
     * whetherDefault : 2
     * zipCode : 101010
     */

    private String address;
    private long createTime;
    private int id;
    private String phone;
    private String realName;
    private int userId;
    private int whetherDefault;
    private String zipCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWhetherDefault() {
        return whetherDefault;
    }

    public void setWhetherDefault(int whetherDefault) {
        this.whetherDefault = whetherDefault;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
