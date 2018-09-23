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
import android.widget.Toast;

import org.afrikcode.pes.R;
import org.afrikcode.pes.activities.HomeActivity;
import org.afrikcode.pes.adapter.BranchAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.enums.BranchErrorType;
import org.afrikcode.pes.enums.Channel;
import org.afrikcode.pes.fragments.timeline.YearFragment;
import org.afrikcode.pes.impl.BranchImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Branch;
import org.afrikcode.pes.views.BranchView;

import java.util.Date;
import java.util.List;


public class BranchsFragment extends BaseFragment<BranchImpl> implements BranchView, OnitemClickListener<Branch>, SearchView.OnQueryTextListener {

    private BranchAdapter adapter;
    private AlertDialog dialog;

    public BranchsFragment() {
        setTitle("Available Branches");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImpl(new BranchImpl());
        getImpl().setView(this);
        setHasOptionsMenu(true);

        getMessagingImpl().subscribeTo(Channel.TRANSACTIONS_CHANNEL);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.getItem(0);
        search.setVisible(true);

        HomeActivity activity = (HomeActivity) getContext();
        activity.getSearchView().setQueryHint("Search branches...");

        activity.getSearchView().setOnQueryTextListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a global layout manager
        getRv_list().setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        getRv_list().setHasFixedSize(true);

        adapter = new BranchAdapter(getContext(), getImpl());
        adapter.setOnclicklistener(this);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new branch
                showAddBranchDialog();
            }
        });

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getAllBranches();
            }
        });

        getImpl().getAllBranches();

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

    private void showAddBranchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        HomeActivity activity = (HomeActivity) getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_add_new_branch, null);

        final EditText et_branch_name = view.findViewById(R.id.et_branch_name);
        final EditText et_contact = view.findViewById(R.id.et_contact);
        final EditText et_address = view.findViewById(R.id.et_address);

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
                String branchName = et_branch_name.getText().toString().trim();
                String branchContact = et_contact.getText().toString().trim();
                String branchAddress = et_address.getText().toString().trim();

                if (branchName.isEmpty() || branchContact.isEmpty() || branchAddress.isEmpty()) {
                    Toast.makeText(getContext(), "Any of this fields can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Branch branch = new Branch();
                branch.setBranchName(branchName);
                branch.setTelephone(branchContact);
                branch.setAddress(branchAddress);
                branch.setBranchTimeStamp(new Date().getTime());

                getImpl().addBranch(branch);

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
    public void ongetBranches(List<Branch> branchList) {
        hideErrorLayout();

        adapter.setItemList(branchList);
        getRv_list().setAdapter(adapter);
        adapter.notifyDataSetChanged();

        getInfoText().setText("Select Branch to view transactions made or add a new branch.");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void onBranchesEmpty() {
        showErrorLayout("Branches are currently empty");
    }

    @Override
    public void onaddBranchSuccess() {
        Toast.makeText(getContext(), "Branch has been successfully added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ondeleteBranch() {
        Toast.makeText(getContext(), "Branch has been deleted successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ongetBranch(Branch branch) {

    }

    @Override
    public void onActivateBranch() {
        if (getActivity() != null) {
            Toast.makeText(getContext(), "Branch has been successfully activated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeactivateBranch() {
        if (getActivity() != null) {
            Toast.makeText(getContext(), "Week has been successfully deactivated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onManagerSetForBranch() {

    }

    @Override
    public void onError(BranchErrorType errorType) {
        String error = null;
        if (errorType == BranchErrorType.ADD_BRANCH_ERROR) {
            error = "Branch could not be added";
        } else if (errorType == BranchErrorType.BRANCH_DELETE_ERROR) {
            error = "Branch could not be deleted";
        } else if (errorType == BranchErrorType.BRANCH_ACTIVATE_ERROR) {
            error = "Branch could not be activated";
        } else if (errorType == BranchErrorType.BRANCH_DEACTIVATE_ERROR) {
            error = "Branch could not be deactivated";
        }

        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Branch data) {
        if (data.isBranchActive()) {
            if (getFragmentListener() != null) {
                Bundle b = new Bundle();
                b.putString("BranchID", data.getBranchID());
                b.putString("BranchName", data.getBranchName());
                YearFragment yf = new YearFragment();
                yf.setArguments(b);

                getFragmentListener().moveToFragment(yf);
            }
        } else {
            Toast.makeText(getContext(), data.getBranchName() + " is not activated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }
}
