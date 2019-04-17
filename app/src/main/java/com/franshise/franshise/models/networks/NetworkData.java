package com.franshise.franshise.models.networks;

import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultModel;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultsView;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.ResultNetworkModels.PeriodResult;
import com.franshise.franshise.models.ResultNetworkModels.SubscribtioResult;
import com.franshise.franshise.models.ResultNetworkModels.UserProfileResult;
import com.franshise.franshise.models.dataModels.CategorysModels;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.dataModels.UserProfileModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NetworkData {


    @POST("login")
    @FormUrlEncoded
    Single<ResponseBody> login(@Field("username") String username,
                               @Field("password") String password);

    @POST("update-password")
    @FormUrlEncoded
    Single<StatusModel> update_password(@Field("username") String username,
                               @Field("password") String password,@Field("api_token") String api_token,
                                        @Field("id") int id);


    @POST("reset")
    @FormUrlEncoded
    Single<StatusModel> reset(@Field("email") String email);
    @POST("register")
    @FormUrlEncoded
    Single<ResponseBody> register(@Field("name") String name, @Field("username") String username,
                                  @Field("email") String email, @Field("password") String password,
                                  @Field("phone") String phone, @Field("country") String country,
                                  @Field("city") String city);

    @POST("verfiy")
    @FormUrlEncoded
    Single<ResponseBody> verfiy(@Field("email") String email, @Field("code") String code);


    @GET("banners/{lang}")
    Single<BannersResult> getHomeBanners(@Path("lang") String lang);

    @GET("cats")
    Single<CategorysResult> getAllCategorys();


    @POST("catwithfranchise")
    @FormUrlEncoded
    Single<CategorysResult> getCategorysBanner(@Field("id") int id);

    @POST("getuserfranchises")
    @FormUrlEncoded
    Single<FranchisesResult> getMyFranchise(@Field("user_id") int user_id);

    @GET("last-franchise")
    Single<FranchisesResult> getLastFranchise();

    @GET("special-franchise")
    Single<FranchisesResult> specialFranchise();

    @POST("deletefranchise")
    @FormUrlEncoded
    Single<StatusModel> deleteFranchise(@Field("id") int user_id);


    @POST("register-token")
    @FormUrlEncoded
    Single<ResponseBody> registerToken(@Field("token") String token, @Field("platform") String platform, @Field("api_token") String api_token);


    @POST("remove-token")
    @FormUrlEncoded
    Single<StatusModel> removeToken(@Field("token") String token, @Field("api_token") String api_token);


    @GET("countries")
    Single<DataResult> getCountries();

    @GET("markets")
    Single<DataResult> getmarkets();


    @GET("getperiod")
    Single<PeriodResult> getPeriod();

    @GET("getfranchisetype")
    Single<DataResult> getfranchisetype();

    @POST("checkcompleteprofile")
    @FormUrlEncoded
    Single<StatusModel> checkCompleteProfile(@Field("id") int id, @Field("api_token") String api_token);

    @POST("completeprofile")
    @FormUrlEncoded
    Single<StatusModel> completeRegister(@Field("id") int id, @Field("company_name") String company_name,
                                         @Field("company_type") String company_type, @Field("company_phone") String company_phone,
                                         @Field("fax") String fax, @Field("cat_id") int cat_id,
                                         @Field("admin_phone") String admin_phone,
                                         @Field("admin_conversion") String admin_conversion, @Field("api_token") String api_token);

    @POST("update-profile")
    @FormUrlEncoded
    Single<StatusModel> update_profile(@Field("id") int id, @Field("api_token") String api_token,
                                       @Field("name") String name,
                                       @Field("email") String email,
                                       @Field("phone") String phone, @Field("country") String country,
                                       @Field("city") String city,
                                       @Field("company_name") String company_name,
                                         @Field("company_type") String company_type, @Field("company_phone") String company_phone,
                                         @Field("fax") String fax,
                                         @Field("admin_phone") String admin_phone,
                                         @Field("admin_conversion") String admin_conversion);

    @POST("update-profile")
    @FormUrlEncoded
    Single<StatusModel> update_profile(@Field("id") int id, @Field("api_token") String api_token,
                                        @Field("name") String name,
                                        @Field("email") String email,
                                        @Field("phone") String phone, @Field("country") String country,
                                        @Field("city") String city);







    @POST("user-profile")
    @FormUrlEncoded
    Single<ResponseBody> user_profile(@Field("id") int id, @Field("api_token") String api_token);

    @POST("franchise")
    @FormUrlEncoded
    Single<FranchiseResultsView> getFranchises(@Field("id") int id);

    @Multipart
    @POST("createfranchise")
    Single<StatusModel> createfranchise(@Part("name") RequestBody name,
                                        @Part("user_id") RequestBody user_id,
                                        @Part("details") RequestBody details,
                                        @Part("category_id") RequestBody category_id,
                                        @Part("country_id") RequestBody country_id,
                                        @Part("establish_date") RequestBody establish_date,
                                        @Part("period_id") RequestBody period_id,
                                        @Part("existing_local_branch") RequestBody existing_local_branch,
                                        @Part("undercost_local_branch") RequestBody undercost_local_branch,
                                        @Part("existing_outside_branch") RequestBody existing_outside_branch,
                                        @Part("undercost_outside_branch") RequestBody undercost_outside_branch,
                                        @Part("other_commission") RequestBody other_commission,
                                        @Part("other_commission_value") RequestBody other_commission_value,
                                        @Part List<MultipartBody.Part> banners,
                                        @Part MultipartBody.Part imagepart,
                                        @Part("franchise_type_id[]") List<Integer> franchise_type_id,
                                        @Part("value[]") List<Integer> value,
                                        @Part("owner_ship_commission") RequestBody owner_ship_commission,
                                        @Part("marketing_commission") RequestBody marketing_commission,
                                        @Part("franchise_market[]") List<Integer> franchise_market,
                                        @Part("space[]") List<String> space,
                                        @Part("total_Investment[]") List<String> total_Investment


    );


    @POST("suggestions")
    @FormUrlEncoded
    Single<StatusModel> suggestions(@Field("type") String type, @Field("from") String from,
                                    @Field("email") String email, @Field("country") String country,
                                    @Field("message") String message);

    @POST("send-for-consultant")
    @FormUrlEncoded
    Single<StatusModel> send_for_consultant(@Field("from") String from,
                                            @Field("email") String email, @Field("country") String country,
                                            @Field("message") String message);

    @POST("send-for-owner")
    @FormUrlEncoded
    Single<StatusModel> send_for_owner(@Field("from") String from,
                                       @Field("email") String email, @Field("country") String country,
                                       @Field("message") String message, @Field("id") int id);


    @GET("money")
    Single<DataResult> getMoney();


    @POST("search")
    @FormUrlEncoded
    Single<FranchisesResult> search(@Field("name") String name,
                                    @Field("min") int min, @Field("max") int max,
                                    @Field("category_id") int category_id);
    @GET("subscription-types")
    Single<SubscribtioResult> subscription_types();
    @Multipart
    @POST("new-subscription")
    Single<StatusModel> new_subscription(@Part("user_id") RequestBody user_id,
                                         @Part("subscriptiontype_id") RequestBody subscriptiontype_id,@Part MultipartBody.Part imagepart);




}