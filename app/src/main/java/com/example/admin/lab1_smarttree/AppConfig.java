package com.example.admin.lab1_smarttree;

import com.google.gson.JsonElement;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;

public class AppConfig {

    public interface signin {
        @FormUrlEncoded
        @POST("/users/signin")
        void login(
                @Field("email") String email,
                @Field("password") String password,
                Callback<Response> callback);
    }

    public interface signup {
        @FormUrlEncoded
        @POST("/users/signup")
        void buyproduct(
                @Field("firstname") String firstname,
                @Field("lastname") String lastname,
                @Field("address") String address,
                @Field("email") String email,
                @Field("password") String password,
                Callback<Response> callback);
    }

    public interface comment {
        @FormUrlEncoded
        @POST("/usercomments")
        void usercomments(
                @Field("name") String name,
                @Field("comment") String comment,
                @Field("rating") int rating,
                Callback<Response> callback);
    }

    public interface read {
        @GET("/usercomments")
        void readData(Callback<Response> callback);
    }
}
