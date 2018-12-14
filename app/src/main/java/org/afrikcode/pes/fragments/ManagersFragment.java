package org.afrikcode.pes.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.afrikcode.pes.R;
import org.afrikcode.pes.activities.HomeActivity;
import org.afrikcode.pes.adapter.ManagerAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.enums.BranchErrorType;
import org.afrikcode.pes.enums.Channel;
import org.afrikcode.pes.impl.BranchImpl;
import org.afrikcode.pes.impl.ManagerImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.listeners.SetBranchForManagerListener;
import org.afrikcode.pes.models.Branch;
import org.afrikcode.pes.models.Manager;
import org.afrikcode.pes.views.BranchView;
import org.afrikcode.pes.views.ManagerView;

import java.util.ArrayList;
import java.util.List;

public class ManagersFragment extends BaseFragment<ManagerImpl> implements ManagerView, OnitemClickListener<Manager>, BranchView, SetBranchForManagerListener, SearchView.OnQueryTextListener {

    private ManagerAdapter managerAdapter;
    private BranchImpl branchImpl;
    private ManagerImpl managerImpl;
    private String managerID;
    private AlertDialog dialog;
    private List<Branch> branchList;
    private List<String> nameList;

    public ManagersFragment() {
        setTitle("Available Managers");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.getItem(0);
        search.setVisible(true);

        HomeActivity activity = (HomeActivity) getContext();
        activity.getSearchView().setQueryHint("Search Managers...");

        activity.getSearchView().setOnQueryTextListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImpl(new ManagerImpl());
        branchImpl = new BranchImpl();
        managerImpl = new ManagerImpl();
        branchImpl.setView(this);
        managerImpl.setView(this);
        getImpl().setView(this);
        setHasOptionsMenu(true);

        getMessagingImpl().subscribeTo(Channel.TRANSACTIONS_CHANNEL);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a global layout manager
        getRv_list().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getRv_list().setHasFixedSize(true);

        managerAdapter = new ManagerAdapter(getContext(), getImpl(), this);
        managerAdapter.setOnclick(this);

        branchList = new ArrayList<>();
        nameList = new ArrayList<>();

        getFab().setVisibility(View.GONE);

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getManagers();
            }
        });

        getImpl().getManagers();
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
    public void onClick(Manager data) {
        if (getFragmentListener() != null) {
            Bundle b = new Bundle();
            b.putString("BranchID", data.getBranchID());
            ClientsFragment cf = new ClientsFragment();
            cf.setArguments(b);

            getFragmentListener().moveToFragment(cf);
        }
    }

    @Override
    public void ongetManagers(List<Manager> managersList) {
        if (managersList.isEmpty()) {
            showErrorLayout("No managers yet");
            return;
        }

        hideErrorLayout();

        // create adapter and pass data to it
        managerAdapter.setItemList(managersList);

        getRv_list().setAdapter(managerAdapter);
        managerAdapter.notifyDataSetChanged();

        getInfoText().setText("Select Manager to view his clients, or enable or disable a manager");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void onManagerStatusChange(boolean isActive) {
        if (isActive) {
            Toast.makeText(getContext(), "Manager Account is now activated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Manager Account is now deactivated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBranchSet(String managerID, String branchID) {
        branchImpl.setManagerforBranch(branchID, managerID);
    }

    @Override
    public void ongetBranches(List<Branch> branches) {
        this.branchList = branches;
        for (Branch b : branchList) {
            if (b.getBranchManagerID() == null) {        // showing only branches that don't have managers assigned;
                nameList.add(b.getBranchName());
            }
        }
    }

    @Override
    public void onBranchesEmpty() {
        Toast.makeText(getContext(), "No branches available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onaddBranchSuccess() {

    }

    @Override
    public void ondeleteBranch() {

    }

    @Override
    public void ongetBranch(Branch branch) {

    }

    @Override
    public void onActivateBranch() {

    }

    @Override
    public void onDeactivateBranch() {

    }

    @Override
    public void onManagerSetForBranch() {
        Toast.makeText(getContext(), "Manager has been successfully set for this branch", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(BranchErrorType errorType) {

    }

    @Override
    public void setBranchfor(String managerID) {
        this.managerID = managerID;
        branchImpl.getAllBranches();

        goAgain();
    }

    private void goAgain() {
        final ProgressDialog dialog = ProgressDialog.show(getContext(), "Requesting Data", "Please wait patiently...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                if (!branchList.isEmpty() && !nameList.isEmpty()) {
                    showSelectBranchDialog(nameList.toArray(), branchList);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Request Timed out");
                    builder.setMessage("Retry getting data from database ?");
                    builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            goAgain();
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }, 3000);
    }

    private void showSelectBranchDialog(final Object[] nameList, final List<Branch> branchList) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getLayoutInflater().inflate(R.layout.dialog_set_branch, null);

        final Spinner spinner_branch = view.findViewById(R.id.spinner_branch);

        Button cancel = view.findViewById(R.id.btn_cancel);
        Button add = view.findViewById(R.id.btn_submit);

        spinner_branch.setAdapter(new ArrayAdapter<>(getContext(), R.layout.spinner_item, nameList));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String branchName = String.valueOf(nameList[spinner_branch.getSelectedItemPosition()]);
                String branchID = getBranchID(branchName, branchList);
                managerImpl.setBranch(managerID, branchName, branchID);
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.setView(view);

        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    private String getBranchID(String branchName, List<Branch> branchList) {
        String id = null;
        for (Branch b : branchList) {
            if (b.getBranchName().equalsIgnoreCase(branchName)) {
                id = b.getBranchID();
            }
        }
        return id;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        managerAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        managerAdapter.getFilter().filter(newText);
        return false;
    }
}
