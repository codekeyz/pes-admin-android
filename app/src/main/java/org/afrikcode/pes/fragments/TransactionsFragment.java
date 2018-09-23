package org.afrikcode.pes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import org.afrikcode.pes.adapter.TransactionAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.impl.TransactionImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Transaction;
import org.afrikcode.pes.views.TransactionView;

import java.util.List;

public class TransactionsFragment extends BaseFragment<TransactionImpl> implements TransactionView, OnitemClickListener<Transaction> {

    private TransactionAdapter mAdapter;

    public TransactionsFragment() {
        setTitle("Available Branches");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImpl(new TransactionImpl());
        getImpl().setView(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a global layout manager
        getRv_list().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getRv_list().setHasFixedSize(true);

        mAdapter = new TransactionAdapter();
        mAdapter.setOnclicklistener(this);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new transaction
                showAddTransactionDialog();
            }
        });

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    private void showAddTransactionDialog() {

    }

    @Override
    public void showLoadingIndicator() {
        super.showLoadingIndicator();

        getInfoText().setText("Please wait... processing current request");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        super.hideLoadingIndicator();
        getInfoText().setVisibility(View.GONE);
    }

    @Override
    public void onAddSuccess() {
        Toast.makeText(getContext(), "Transaction successfully added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddError() {
        Toast.makeText(getContext(), "Transaction could not be added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ongetTransactions(List<Transaction> transactionList) {

    }

    @Override
    public void onClick(Transaction data) {

    }
}
