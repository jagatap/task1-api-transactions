package com.example.taskone.viewmodel;

import static com.example.taskone.utils.GlobalData.setEncryptedSharedPreferences;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskone.data.model.LoginBody;
import com.example.taskone.data.model.LoginResponse;
import com.example.taskone.repository.LoginRepository;

public class LoginViewModel extends ViewModel {


    MutableLiveData<Integer> mProgressMutableData = new MutableLiveData<>();
    MutableLiveData<String> mDrinksMutableData = new MutableLiveData<>();
    MutableLiveData<String> mLoginResultMutableData = new MutableLiveData<>();

    LoginRepository mLoginRepository;

    public LoginViewModel() {
        mProgressMutableData.postValue(View.INVISIBLE);
        mDrinksMutableData.postValue("");
        mLoginResultMutableData.postValue("Not logged in");
        mLoginRepository = new LoginRepository();
    }

    public void login(Context context, String email, String password) {
        mProgressMutableData.setValue(View.VISIBLE);
        mLoginResultMutableData.setValue("Checking");
        mLoginRepository.loginRemote(new LoginBody(email, password), new LoginRepository.ILoginResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                mProgressMutableData.setValue(View.INVISIBLE);
                mLoginResultMutableData.setValue("Login Success");
                setEncryptedSharedPreferences(context, "token", loginResponse.getToken());

            }

            @Override
            public void onFailure(Throwable t) {
                mProgressMutableData.postValue(View.INVISIBLE);
                mLoginResultMutableData.setValue("Login failure: " + t.getLocalizedMessage());
            }
        });
    }


    public LiveData<String> getLoginResult() {
        return mLoginResultMutableData;
    }

    public LiveData<Integer> getProgress() {
        return mProgressMutableData;
    }

}
