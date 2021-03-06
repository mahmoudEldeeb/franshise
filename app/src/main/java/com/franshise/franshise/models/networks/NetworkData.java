package com.franshise.franshise.models.networks;

import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.EventsModelResults;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultModel;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultsView;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.ResultNetworkModels.FundCompanyModelResult;
import com.franshise.franshise.models.ResultNetworkModels.JobsResults;
import com.franshise.franshise.models.ResultNetworkModels.MessageResults;
import com.franshise.franshise.models.ResultNetworkModels.PeriodResult;
import com.franshise.franshise.models.ResultNetworkModels.SubscribtioResult;
import com.franshise.franshise.models.ResultNetworkModels.UserProfileResult;
import com.franshise.franshise.models.dataModels.CategorysModels;
import com.franshise.franshise.models.dataModels.PayModel;
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
import retrofit2.http.Query;

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
                                  @Field("city") String city,
                                  @Field("user_type") int user_type,
                                  @Field("company_name") String company_name,
                                  @Field("admin_name") String admin_name,
                                  @Field("admin_conversion") String admin_conversion
                                  );
    @POST("verfiy")
    @FormUrlEncoded
    Single<ResponseBody> verfiy(@Field("email") String email, @Field("code") String code);


    @GET("banners/{lang}")
    Single<BannersResult> getHomeBanners(@Path("lang") String lang);

    @GET("pay_way")
    Single<PayModel> pay_way();

    @GET("cats")
    Single<CategorysResult> getAllCategorys();


    @POST("catwithfranchise")
    @FormUrlEncoded
    Single<CategorysResult> getCategorysBanner(@Field("id") int id);

    @POST("get-franchise-by-type")
    @FormUrlEncoded
    Single<FranchiseResultModel> getFranchiseByType(@Field("type_id") int type_id,@Field("category_id") int category_id );


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
    Single<ResponseBody> registerToken(@Field("token") String token, @Field("platform") String platform, @Field("api_token") String api_token,
                                       @Field("user_id") int user_id );


    @POST("remove-token")
    @FormUrlEncoded
    Single<StatusModel> removeToken(@Field("token") String token, @Field("api_token") String api_token);


    @GET("countries")
    Single<DataResult> getCountries();


    @GET("city_with_country")
    Single<DataResult> city_with_country(@Query("country_id") int country_id);

    @GET("qualification")
    Single<DataResult> getQualification();

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
    //get-franchise-by-type

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
                                    @Field("message") String message,
                                    @Field("sender") int sender);

    @POST("send-for-consultant")
    @FormUrlEncoded
    Single<StatusModel> send_for_consultant(@Field("from") String from,
                                            @Field("email") String email, @Field("country") String country,
                                            @Field("message") String message);

    @POST("send-for-owner")
    @FormUrlEncoded
    Single<StatusModel> send_for_owner(@Field("from") String from,
                                       @Field("email") String email, @Field("country") String country,
                                       @Field("message") String message, @Field("id") int id,@Field("sender") int sender);


    @GET("money")
    Single<DataResult> getMoney();


    @POST("search")
    @FormUrlEncoded
    Single<FranchisesResult> search(@Field("name") String name,
                                    @Field("min") int min, @Field("max") int max,
                                    @Field("category_id") int category_id);

    @POST("get-userdata-by-franchise")
    @FormUrlEncoded
    Single<ResponseBody> get_userdata_by_franchise(@Field("user_id") int user_id);

    @POST("getMessages")
    @FormUrlEncoded
    Single<MessageResults> getMessages(@Field("user_id") int user_id);


    @GET("subscription-types")
    Single<SubscribtioResult> subscription_types();
    @Multipart
    @POST("new-subscription")
    Single<StatusModel> new_subscription(@Part("user_id") RequestBody user_id,
                                         @Part("subscriptiontype_id") RequestBody subscriptiontype_id,@Part MultipartBody.Part imagepart);



    @Multipart
    @POST("update-franchise")
    Single<StatusModel> updatefranchise(@Part("id") RequestBody id,
                                        @Part("name") RequestBody name,
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
                                        @Part ("ids[]")List<Integer>ids,
                                        @Part("franchise_type_id[]") List<Integer> franchise_type_id,
                                        @Part("value[]") List<Integer> value,
                                        @Part("owner_ship_commission") RequestBody owner_ship_commission,
                                        @Part("marketing_commission") RequestBody marketing_commission,
                                        @Part("franchise_market[]") List<Integer> franchise_market,
                                        @Part("space[]") List<String> space,
                                        @Part("total_Investment[]") List<String> total_Investment

    );

    @Multipart
    @POST("update-franchise")
    Single<StatusModel> updatefranchise(@Part("id") RequestBody id,
                                        @Part("name") RequestBody name,
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
                                        @Part ("ids[]")List<Integer>ids,
                                        @Part("franchise_type_id[]") List<Integer> franchise_type_id,
                                        @Part("value[]") List<Integer> value,
                                        @Part("owner_ship_commission") RequestBody owner_ship_commission,
                                        @Part("marketing_commission") RequestBody marketing_commission,
                                        @Part("franchise_market[]") List<Integer> franchise_market,
                                        @Part("space[]") List<String> space,
                                        @Part("total_Investment[]") List<String> total_Investment

    );

    @Multipart
    @POST("update-franchise")
    Single<StatusModel> updatefranchise(@Part("id") RequestBody id,
                                        @Part("name") RequestBody name,
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
                                        @Part MultipartBody.Part imagepart,
                                        @Part ("ids[]")List<Integer>ids,
                                        @Part("franchise_type_id[]") List<Integer> franchise_type_id,
                                        @Part("value[]") List<Integer> value,
                                        @Part("owner_ship_commission") RequestBody owner_ship_commission,
                                        @Part("marketing_commission") RequestBody marketing_commission,
                                        @Part("franchise_market[]") List<Integer> franchise_market,
                                        @Part("space[]") List<String> space,
                                        @Part("total_Investment[]") List<String> total_Investment

    );

    @Multipart
    @POST("update-franchise")
    Single<StatusModel> updatefranchise(@Part("id") RequestBody id,
                                        @Part("name") RequestBody name,
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
                                        @Part ("ids[]")List<Integer>ids,
                                        @Part("franchise_type_id[]") List<Integer> franchise_type_id,
                                        @Part("value[]") List<Integer> value,
                                        @Part("owner_ship_commission") RequestBody owner_ship_commission,
                                        @Part("marketing_commission") RequestBody marketing_commission,
                                        @Part("franchise_market[]") List<Integer> franchise_market,
                                        @Part("space[]") List<String> space,
                                        @Part("total_Investment[]") List<String> total_Investment

    );

    @GET("services/{lang}")
    Single<EventsModelResults> services	(@Path("lang") String lang);

    @GET("jobs/{lang}")
    Single<EventsModelResults> jobs	(@Path("lang") String lang);

    @GET("get_job")
    Single<JobsResults> get_job	();

    @GET("courses/{lang}/{country_id}")
    Single<EventsModelResults> courses(@Path("lang") String lang,@Path("country_id")int country_id);

    @GET("conferances/{lang}")
    Single<EventsModelResults> conferances(@Path("lang") String lang);
    @GET("companies")
    Single<FundCompanyModelResult> getCompanies();

    @POST("create_job")
    @FormUrlEncoded
    Single<StatusModel> createJob(
                                        @Field("name") String name,
                                        @Field("number_require") int number_require,
                                        @Field("qualification_id") int qualification_id,
                                        @Field("details") String details,
                                        @Field("country_id") int country_id,
                                        @Field("city_id[]") List<Integer> city_id,
                                        @Field("start") int start,
                                        @Field("end") int end,
                                        @Field("gender") int gender,
                                        @Field("currency") String currency,
                                        @Field("number") int number



    );


    @POST("update_job")
    @FormUrlEncoded
    Single<StatusModel> updateJob(
            @Field("id") int id,
            @Field("name") String name,
            @Field("number_require") int number_require,
            @Field("qualification_id") int qualification_id,
            @Field("details") String details,
            @Field("country_id") int country_id,
            @Field("city_id[]") List<Integer> city_id,
            @Field("start") int start,
            @Field("end") int end,
            @Field("gender") int gender,
            @Field("currency") String currency,
            @Field("number") int number

    );
    @POST("delete_job")
    @FormUrlEncoded
    Single<StatusModel> delete(
            @Field("id") int id

    );

}