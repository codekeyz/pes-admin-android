package org.afrikcode.pes.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.afrikcode.pes.R;
import org.afrikcode.pes.adapter.ClientAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.impl.ClientImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Client;
import org.afrikcode.pes.views.ClientView;

import java.util.List;

public class ClientsFragment extends BaseFragment<ClientImpl> implements ClientView, OnitemClickListener<Client> {

    private ClientAdapter adapter;
    private AlertDialog dialog;

    private String branchID;
    private String branchName;
    private String yearID;
    private String monthID;
    private String weekID;

    public ClientsFragment() {
        setTitle("Clients in Branch");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            Bundle b = getArguments();
            branchName = b.getString("BranchName");
            branchID = b.getString("BranchID");
            yearID = b.getString("YearID");
            monthID = b.getString("MonthID");
            weekID = b.getString("WeekID");
        }

        setImpl(new ClientImpl());
        getImpl().setView(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a global layout manager
        getRv_list().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getRv_list().setHasFixedSize(true);

        adapter = new ClientAdapter();
        adapter.setOnclicklistener(this);

        getFab().setVisibility(View.GONE);

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getClientsinBranch(branchID);
            }
        });

        getImpl().getClientsinBranch(branchID);

    }

    @Override
    public void showLoadingIndicator() {
        super.showLoadingIndicator();
        getInfoText().setVisibility(View.VISIBLE);
        getInfoText().setText("Please wait... loading clients from database");
    }

    @Override
    public void hideLoadingIndicator() {
        super.hideLoadingIndicator();
        getInfoText().setVisibility(View.GONE);
    }

    private void showAddClientDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialog_add_new_client_layout, null);

        final EditText et_name = view.findViewById(R.id.et_client_name);
        final EditText et_contact = view.findViewById(R.id.et_contact_number);
        final EditText et_address = view.findViewById(R.id.et_residential_address);

        Button cancel = view.findViewById(R.id.btn_cancel);
        Button okay = view.findViewById(R.id.btn_submit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String branchName = et_name.getText().toString().trim();
                String branchContact = et_contact.getText().toString().trim();
                String branchAddress = et_address.getText().toString().trim();

                Client c = new Client(branchName, branchContact, branchAddress, branchID);
                getImpl().addClient(c);

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
    public void ongetClients(List<Client> clientList) {
        hideErrorLayout();

        adapter.setItemList(clientList);
        getRv_list().setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getInfoText().setText("Select Client to view contributions made or add a new contribution.");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void onclientListEmpty() {
        showErrorLayout("No Clients Added yet");
    }

    @Override
    public void ongetClient(Client client) {

    }

    @Override
    public void onAddClient() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onClick(Client data) {

    }
}
