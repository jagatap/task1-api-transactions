package com.example.taskone.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskone.utils.GlobalData;
import com.example.taskone.R;
import com.example.taskone.adapter.TransactionAdapter;
import com.example.taskone.data.model.TransactionResponse;
import com.example.taskone.viewmodel.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TransactionAdapter adapter;
    private Toolbar toolbar;
    private List<TransactionResponse> transactionResponseArrayList;

    private TransactionViewModel transactionViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView.setAdapter(adapter);
        buildRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.actionSearch);
        MenuItem logout = menu.findItem(R.id.logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                GlobalData.clearEncryptedPreferences(HomeActivity.this, LoginActivity.class);
                return false;
            }
        });

        SearchView searchView = (SearchView) searchItem.getActionView();

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        ArrayList<TransactionResponse> filteredlist = new ArrayList<>();
        for (TransactionResponse item : transactionResponseArrayList) {
            if (item.getCategory().toLowerCase().contains(text.toLowerCase()) || item.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }

        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        transactionViewModel = new TransactionViewModel();

        transactionViewModel.getExpenses().observe(this, new Observer<List<TransactionResponse>>() {
            @Override
            public void onChanged(List<TransactionResponse> expenses) {
                if (expenses != null && !expenses.isEmpty()) {
                    transactionResponseArrayList = expenses;
                    adapter = new TransactionAdapter(expenses);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to load transaction", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String tokenValue = GlobalData.getSharePreferenceString(getApplicationContext(), "token", "");

        // Fetch the transaction data from the API
        transactionViewModel.fetchExpenses(tokenValue);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    finishAffinity();
                })
                .setNegativeButton("No", null)
                .show();
    }


}