package com.example.taskone.data.network;

import com.example.taskone.data.model.LoginBody;
import com.example.taskone.data.model.LoginResponse;
import com.example.taskone.data.model.TransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiServices {
    @POST("/login")
    Call<LoginResponse> login(@Body LoginBody loginBody);

    @GET("/transactions")
    Call<List<TransactionResponse>> getTransaction(@Header("Authorization") String Token);
}
