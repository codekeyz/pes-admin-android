package org.afrikcode.pes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.afrikcode.pes.activities.HomeActivity;
import org.afrikcode.pes.adapter.TransactionAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.impl.TransactionImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Transaction;
import org.afrikcode.pes.views.TransactionView;

import java.util.List;


public class TransactionsFragment extends BaseFragment<TransactionImpl> implements TransactionView, OnitemClickListener<Transaction>, SearchView.OnQueryTextListener {

    private TransactionAdapter mAdapter;
    private String yearID, monthID, weekID, dayID, branchID;

    public TransactionsFragment() {
        setTitle("Transactions");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImpl(new TransactionImpl());
        getImpl().setView(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.getItem(0);
        search.setVisible(true);

        HomeActivity activity = (HomeActivity) getContext();
        activity.getSearchView().setQueryHint("Search client transactions...");

        activity.getSearchView().setOnQueryTextListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Bundle b = getArguments();
            branchID = b.getString("BranchID");
            yearID = b.getString("YearID");
            monthID = b.getString("MonthID");
            weekID = b.getString("WeekID");
            dayID = b.getString("DayID");
        }

        //setting a global layout manager
        getRv_list().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getRv_list().setHasFixedSize(true);

        mAdapter = new TransactionAdapter();
        mAdapter.setOnclicklistener(this);

        getFab().setVisibility(View.GONE);

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getTransactions(branchID, yearID, monthID, weekID, dayID);
            }
        });

        getImpl().getTransactions(branchID, yearID, monthID, weekID, dayID);
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
    public void ongetTransactions(List<Transaction> transactionList) {
        hideErrorLayout();

        mAdapter.setItemList(transactionList);
        getRv_list().setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        getInfoText().setText("Select Transaction to view details or add a new transaction.");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void onTransactionsEmpty() {
        showErrorLayout("No Transactions yet");
    }

    @Override
    public void onClick(Transaction data) {
        Toast.makeText(getContext(), data.getClientName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mAdapter.getFilter().filter(newText);
        return false;
    }
}
