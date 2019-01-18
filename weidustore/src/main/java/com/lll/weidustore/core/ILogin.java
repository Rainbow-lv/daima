package com.lll.weidustore.core;

import com.lll.weidustore.bean.AddressList;
import com.lll.weidustore.bean.CircleBean;
import com.lll.weidustore.bean.Comment;
import com.lll.weidustore.bean.DetailsBean;
import com.lll.weidustore.bean.GoodsBanner;
import com.lll.weidustore.bean.GoodsList;
import com.lll.weidustore.bean.Null_Bean;
import com.lll.weidustore.bean.Result;
import com.lll.weidustore.bean.SearchBean;
import com.lll.weidustore.bean.ShopCar;
import com.lll.weidustore.bean.User;
import com.lll.weidustore.bean.UserFootBean;
import com.lll.weidustore.bean.UserPresonBean;
import com.lll.weidustore.bean.UserWallet;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ILogin {

    /**
     * 登录
     * @param mobile
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("small/user/v1/login")
    Observable<Result<User>> login(@Field("phone") String mobile,
                                   @Field("pwd") String password);

    /**
     * 注册
     * @param mobile
     * @param password
     * @return
     */
    @POST("small/user/v1/register")
    @FormUrlEncoded
    Observable<Result> reg(@Field("phone") String mobile,
                                 @Field("pwd") String password);

    /**
     * banner轮播图
     * @return
     */
    @GET("small/commodity/v1/bannerShow")
    Observable<Result<List<GoodsBanner>>> bann();

    /**
     * 首页列表展示
     * @return
     */
    @GET("small/commodity/v1/commodityList")
    Observable<Result<GoodsList>> getlist();

    /**
     * 圈子
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("small/circle/v1/findCircleList")
    Observable<Result<List<CircleBean>>> circle(
            @Header("userId") long userId,
            @Header("sessionId")String sessionId,
            @Query("page")int page,
            @Query("count")int count);

    /**
     * 搜索
     * @param keyword
     * @param page
     * @param count
     * @return
     */
    @GET("small/commodity/v1/findCommodityByKeyword")
    Observable<Result<List<SearchBean>>> search(@Query("keyword")String keyword,
                                                @Query("page")int page,@Query("count")int count);

    /**
     * 商品详情
     * @param userId
     * @param sessionId
     * @param commodityId
     * @return
     */
    @GET("small/commodity/v1/findCommodityDetailsById")
    Observable<Result<DetailsBean>> details(@Header("userId")long userId,
                                            @Header("sessionId")String sessionId,
                                            @Query("commodityId")int commodityId);

    /*
   评论
    */
    @GET("small/commodity/v1/CommodityCommentList")
    Observable<Result<List<Comment>>> comment(@Query("commodityId")int commodityId,
                                              @Query("page")int page,
                                              @Query("count")int count);

    /**
     * 个人资料
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("small/user/verify/v1/getUserById")
    Observable<Result<UserPresonBean>> person(@Header("userId")long userId,
                                              @Header("sessionId")String sessionId);

    /**
     * 我的钱包
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("small/user/verify/v1/findUserWallet")
    Observable<Result<UserWallet>> wallet(@Header("userId")long userId,
                                          @Header("sessionId")String sessionId,
                                          @Query("page")int page,
                                          @Query("count")int count);

    /**
     * 我的足迹
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("small/commodity/verify/v1/browseList")
    Observable<Result<List<UserFootBean>>> foot(@Header("userId")long userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("page")int page,
                                                @Query("count")int count);

    /**
     * 收货地址列表
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("small/user/verify/v1/receiveAddressList")
    Observable<Result<List<AddressList>>> addresslist(@Header("userId") long userId,
                                                      @Header("sessionId") String sessionId);

    /**
     * 新增收货地址
     * @param userId
     * @param sessionId
     * @param realName
     * @param phone
     * @param address
     * @param zipCode
     * @return
     */
    @POST("small/user/verify/v1/addReceiveAddress")
    @FormUrlEncoded
    Observable<Result> addAddrss(@Header("userId") long userId,
                                 @Header("sessionId") String sessionId,
                                 @Field("realName") String realName,
                                 @Field("phone") String phone,
                                 @Field("address") String address,
                                 @Field("zipCode") String zipCode);

    /**
     * 默认地址
     * @param userId
     * @param sessionId
     * @param id
     * @return
     */
    @POST("small/user/verify/v1/setDefaultReceiveAddress")
    @FormUrlEncoded
    Observable<Result> defaut(@Header("userId") long userId,
                              @Header("sessionId") String sessionId,
                              @Field("id") int id);

    /**
     * 购物车列表
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("small/order/verify/v1/findShoppingCart")
    Observable<Result<List<ShopCar>>> shopcar(@Header("userId")long userId,
                                              @Header("sessionId")String sessionId);

    /**
     * 同步购物车数据
     * @param userId
     * @param sessionId
     * @param data
     * @return
     */
    @PUT("small/order/verify/v1/syncShoppingCart")
    @FormUrlEncoded
    Observable<Result> addTo(@Header("userId") long userId,
                             @Header("sessionId") String sessionId,
                             @Field("data") String data);

    /*
   订单
    */
    @GET("small/order/verify/v1/findOrderListByStatus")
    Observable<Result<List<Null_Bean>>> order(@Header("userId") long userId,
                                              @Header("sessionId") String sessionId,
                                              @Query("page") int page,
                                              @Query("count") int count,
                                              @Query("status") int status);

    /**
     * 发布圈子
     */
    @POST("small/circle/verify/v1/releaseCircle")
    Observable<Result> releaseCircle(@Header("userId") long userId,
                                     @Header("sessionId")String sessionId,
                                     @Body MultipartBody body);
    /**
     * 圈子点赞
     */
    @FormUrlEncoded
    @POST("small/circle/verify/v1/addCircleGreat")
    Observable<Result> addCircleGreat(
            @Header("userId") String userId,
            @Header("sessionId")String sessionId,
            @Field("circleId") long circleId);
}
