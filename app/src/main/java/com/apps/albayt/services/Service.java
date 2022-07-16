package com.apps.albayt.services;


import com.apps.albayt.model.AddressesDataModel;
import com.apps.albayt.model.CityDataModel;
import com.apps.albayt.model.ContractorDataModel;
import com.apps.albayt.model.MainHomeDataModel;
import com.apps.albayt.model.NewsDataModel;
import com.apps.albayt.model.OrdersModel;
import com.apps.albayt.model.RecentProductDataModel;
import com.apps.albayt.model.NotificationDataModel;
import com.apps.albayt.model.PlaceGeocodeData;
import com.apps.albayt.model.SearchHomeDataModel;
import com.apps.albayt.model.SettingDataModel;
import com.apps.albayt.model.SingleAddressData;
import com.apps.albayt.model.SingleOrderDataModel;
import com.apps.albayt.model.SingleProductModel;
import com.apps.albayt.model.StatusResponse;
import com.apps.albayt.model.SupplierDataModel;
import com.apps.albayt.model.TimeDataModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.model.cart_models.CartModel;
import com.apps.albayt.model.cart_models.CartResponse;
import com.apps.albayt.model.cart_models.CartSingleModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @GET("api/home")
    Single<Response<MainHomeDataModel>> getMainHomeData(@Query(value = "lang") String lang);


    @GET("api/settings/cities")
    Single<Response<CityDataModel>> getCities(@Query("lang") String lang);

    @GET("api/supplires")
    Single<Response<SupplierDataModel>> searchForSuppliers(@Query("city_id") String city_id,
                                                           @Query("search_name") String search_name,
                                                           @Query("lang") String lang
    );


    @GET("api/contractors")
    Single<Response<SupplierDataModel>> searchForContractors(@Query("city_id") String city_id,
                                                             @Query("search_name") String search_name,
                                                             @Query("lang") String lang
    );


    @GET("api/home/products")
    Single<Response<RecentProductDataModel>> searchByCatProduct(@Query("categories[]") List<String> category_ids,
                                                                @Query("search_name") String search_name,
                                                                @Query(value = "lang") String lang
    );

    @GET("api/home/products/show")
    Single<Response<SingleProductModel>> getSingleProduct(@Query("id") String id,
                                                          @Query(value = "lang") String lang);


    @GET("api/home_search")
    Single<Response<SearchHomeDataModel>> searchProduct(@Query("user_id") String user_id,
                                                        @Query("search_word") String search_word);

    @GET("api/home/news")
    Single<Response<NewsDataModel>> getNews(@Query(value = "lang") String lang);


    @FormUrlEncoded
    @POST("api/contact_us")
    Single<Response<StatusResponse>> contactUs(@Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String subject,
                                               @Field("message") String message


    );

    @GET("geocode/json")
    Single<Response<PlaceGeocodeData>> getGeoData(@Query(value = "latlng") String latlng,
                                                  @Query(value = "language") String language,
                                                  @Query(value = "key") String key);


    @FormUrlEncoded
    @POST("api/users/login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone
    );

    @Multipart
    @POST("api/users/register/client")
    Observable<Response<UserModel>> signUp(@Part("first_name") RequestBody first_name,
                                           @Part("last_name") RequestBody last_name,
                                           @Part("phone_code") RequestBody phone_code,
                                           @Part("phone") RequestBody phone,
                                           @Part("email") RequestBody email,
                                           @Part MultipartBody.Part logo


    );

    @Multipart
    @POST("api/users/client/update")
    Observable<Response<UserModel>> updateProfile(@Part("user_id") RequestBody user_id,
                                                  @Part("first_name") RequestBody first_name,
                                                  @Part("last_name") RequestBody last_name,
                                                  @Part("phone_code") RequestBody phone_code,
                                                  @Part("phone") RequestBody phone,
                                                  @Part("email") RequestBody email,
                                                  @Part MultipartBody.Part logo


    );


    @FormUrlEncoded
    @POST("api/logout")
    Single<Response<StatusResponse>> logout(@Header("AUTHORIZATION") String token,
                                            @Field("api_key") String api_key,
                                            @Field("phone_token") String phone_token


    );

    @FormUrlEncoded
    @POST("api/firebase-tokens")
    Single<Response<StatusResponse>> updateFirebasetoken(@Header("AUTHORIZATION") String token,
                                                         @Field("api_key") String api_key,
                                                         @Field("phone_token") String phone_token,
                                                         @Field("user_id") String user_id,
                                                         @Field("software_type") String software_type


    );

    @FormUrlEncoded
    @POST("api/contact-us")
    Single<Response<StatusResponse>> contactUs(@Field("api_key") String api_key,
                                               @Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String phone,
                                               @Field("message") String message


    );


    @GET("api/notifications")
    Single<Response<NotificationDataModel>> getNotifications(@Header("AUTHORIZATION") String token,
                                                             @Query(value = "api_key") String api_key,
                                                             @Query(value = "user_id") String user_id
    );


    @GET("api/delivery_times")
    Single<Response<TimeDataModel>> getTime();

    @GET("api/users/client/address")
    Single<Response<AddressesDataModel>> getMyAddresses(@Query("user_id") String user_id);


    @FormUrlEncoded
    @POST("api/users/client/address/create")
    Single<Response<SingleAddressData>> addAddress(@Field("user_id") String user_id,
                                                   @Field("title") String title,
                                                   @Field("admin_name") String admin_name,
                                                   @Field("phone_number") String phone,
                                                   @Field("address") String address,
                                                   @Field("latitude") double latitude,
                                                   @Field("longitude") double longitude);


    @FormUrlEncoded
    @POST("api/users/client/address/update")
    Single<Response<SingleAddressData>> updateAddress(@Header("AUTHORIZATION") String token,
                                                      @Field("id") String id,
                                                      @Field("user_id") String user_id,
                                                      @Field("title") String title,
                                                      @Field("admin_name") String admin_name,
                                                      @Field("phone_number") String phone,
                                                      @Field("address") String address,
                                                      @Field("latitude") double latitude,
                                                      @Field("longitude") double longitude);

    @GET("api/users/contractor/show")
    Single<Response<ContractorDataModel>> getContractorProfile(@Query(value = "lang") String lang,
                                                               @Query("user_id") String user_id);



    @FormUrlEncoded
    @POST("api/users/client/address/delete")
    Single<Response<StatusResponse>> deleteAddress(@Header("AUTHORIZATION") String token,
                                                      @Field("id") String id
    );

    @POST("api/storeOrder")
    Single<Response<CartResponse>> sendAllOrder(@Body CartModel cartModel);

    @POST("api/storeOrder")
    Single<Response<CartResponse>> sendSingleOrder(@Body CartSingleModel cartModel);

    @GET("api/users/client/orders")
    Single<Response<OrdersModel>> getOrders(@Query("user_id") String user_id,
                                            @Query("status") String status);

    @GET("api/users/client/orders/show")
    Single<Response<SingleOrderDataModel>> getOrderDetails(@Query("id") String id);


    @FormUrlEncoded
    @POST("api/storeToList")
    Single<Response<StatusResponse>> favUnFav(@Field("user_id") String user_id,
                                              @Field("product_id") String product_id
    );

    @GET("api/myList")
    Single<Response<RecentProductDataModel>> getMyList(@Query("user_id") String user_id);


    @FormUrlEncoded
    @POST("api/updateOfferStatus")
    Single<Response<StatusResponse>> acceptRefuseOffer(@Field("offer_id") String offer_id,
                                                       @Field("status") String status);

    @GET("api/setting")
    Single<Response<SettingDataModel>> getSettings();

}