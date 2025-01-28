package com.example.taskone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskone.R;
import com.example.taskone.data.model.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ExpenseViewHolder> {
    private List<TransactionResponse> transactionResponses;


    public TransactionAdapter(List<TransactionResponse> transactionResponses) {
        this.transactionResponses = transactionResponses;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        TransactionResponse expense = transactionResponses.get(position);
        holder.textViewDate.setText(expense.getDate());
        holder.textViewAmount.setText("Rs." + (expense.getAmount()) + "/-");
        holder.textViewCategory.setText(expense.getCategory());
        holder.textViewDescription.setText(expense.getDescription());
    }

    @Override
    public int getItemCount() {
        return transactionResponses.size();
    }

    public void filterList(ArrayList<TransactionResponse> filterlist) {
        transactionResponses = filterlist;
        notifyDataSetChanged();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate, textViewAmount, textViewCategory, textViewDescription;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }


}