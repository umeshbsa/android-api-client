package com.app.apiclient.network;

/*
  All api code Post, Get, Put, Delete
 */

import com.app.apiclient.model.apimodel.output.User;
import com.app.apiclient.model.apimodel.output.UserProfile;
import com.app.apiclient.model.base.BaseResponse;
import com.app.apiclient.model.base.CommonApiResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiRequest {


    @FormUrlEncoded
    @POST("api/v1/user/login")
    Call<BaseResponse<User>> postMethodAPI(@Field("email") String email,
                                           @Field("password") String password,
                                           @Field("deviceId") String deviceId
    );

    @GET("api/v1/user/users")
    Call<BaseResponse<CommonApiResponse>> getMethodAPI();


    @GET("api/v1/user/user")
    Call<BaseResponse<CommonApiResponse>> getMethodWithQueryAPI(@Query("userid") String userId,
                                                                @Query("startDate") long startDate
    );

    @GET("api/v1/user/{userid}")
    Call<BaseResponse<CommonApiResponse>> getPathAPI(@Path("userid") String userId);

    @Multipart
    @POST("api/v1/user/editProfile")
    Call<BaseResponse<UserProfile>> updateProfileAPI(@Part("profilePic\"; filename=\"1.png\"") RequestBody profilePic
    );

    @POST("users/new")
    Call<User> createUser(@Body User user);

    @Multipart
    @PUT("user/photo")
    Call<User> updateUser(@Part("profilePic\"; filename=\"1.png\"") RequestBody photo, @Part("description") RequestBody description);

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("user")
    Call<User> getUser1(@Header("Authorization") String authorization);


    @GET("users")
    Call<User> getUserById(@Query("id") Integer id);
}

