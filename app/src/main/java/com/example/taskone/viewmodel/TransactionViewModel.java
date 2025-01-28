package com.example.taskone.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.taskone.data.model.TransactionResponse;
import com.example.taskone.data.network.ApiServices;
import com.example.taskone.data.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionViewModel extends ViewModel {
    private final MutableLiveData<List<TransactionResponse>> expenseList = new MutableLiveData<>();

    public void fetchExpenses(String token) {
        // Create Retrofit instance and call the API
        ApiServices apiServices = RetrofitClientInstance.getInstance().create(ApiServices.class);
        Call<List<TransactionResponse>> call = apiServices.getTransaction(token);
        call.enqueue(new Callback<List<TransactionResponse>>() {
            @Override
            public void onResponse(Call<List<TransactionResponse>> call, Response<List<TransactionResponse>> response) {
                if (response.isSuccessful()) {
                    List<TransactionResponse> expenses = response.body();

                    expenseList.setValue(response.body());
                    Log.d("ExpenseViewModel", "Expenses fetched: " + expenses.size());

                }
            }

            @Override
            public void onFailure(Call<List<TransactionResponse>> call, Throwable t) {
                Log.e("ExpenseViewModel", "Failure: " + t.getMessage());

                expenseList.setValue(null);
            }
        });
    }

    public LiveData<List<TransactionResponse>> getExpenses() {
        return expenseList;
    }
}

