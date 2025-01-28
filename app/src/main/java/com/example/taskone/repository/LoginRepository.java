package com.example.taskone.repository;

import com.example.taskone.data.model.LoginBody;
import com.example.taskone.data.model.LoginResponse;
import com.example.taskone.data.network.ApiServices;
import com.example.taskone.data.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    public LoginRepository() {
    }

    public void loginRemote(LoginBody loginBody, ILoginResponse loginResponse) {
        ApiServices loginService = RetrofitClientInstance.getInstance().create(ApiServices.class);
        Call<LoginResponse> initiateLogin = loginService.login(loginBody);


        initiateLogin.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    loginResponse.onResponse(response.body());
                } else {
                    loginResponse.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginResponse.onFailure(t);
            }
        });

    }


    public interface ILoginResponse {
        void onResponse(LoginResponse loginResponse);

        void onFailure(Throwable t);
    }

}
