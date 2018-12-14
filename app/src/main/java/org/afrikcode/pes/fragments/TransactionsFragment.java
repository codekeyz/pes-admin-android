package org.afrikcode.pes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.afrikcode.pes.R;
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
    private AlertDialog dialog;

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
        activity.getSearchView().setQueryHint("Search manager transactions...");

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
        mAdapter.setOnclick(this);

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

        getInfoText().setText("Select Transaction to view details.");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void onTransactionsEmpty() {
        showErrorLayout("No Transactions yet");
    }

    @Override
    public void onClick(Transaction data) {
        showTransaction(data);
    }

    private void showTransaction(Transaction data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        HomeActivity activity = (HomeActivity) getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.trans_details_layout, null);

        final TextView et_name = view.findViewById(R.id.et_client_name);
        et_name.setText(data.getClientName());

        final TextView et_amount = view.findViewById(R.id.et_amount);
        et_amount.setText(getContext().getString(R.string.txt_cedi_sign).concat(String.valueOf(data.getAmount())));

        final TextView et_telephone = view.findViewById(R.id.et_client_telephone);
        et_telephone.setText(data.getClientTelephone());

        final EditText et_remarks = view.findViewById(R.id.et_remark);
        if (data.getRemarks() == null) {
            et_remarks.setText("No remarks set");
        } else {
            et_remarks.setText(data.getRemarks());
        }

        final TextView et_timestamp = view.findViewById(R.id.et_timestamp);
        et_timestamp.setText(data.getCreatedAt());

        Button cancel = view.findViewById(R.id.btn_cancel);
        cancel.setText("OKAY");
        Button okay = view.findViewById(R.id.btn_submit);
        okay.setVisibility(View.GONE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setView(view);

        if (!dialog.isShowing()) {
            dialog.show();
        }
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
