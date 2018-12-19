package org.afrikcode.pes.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.afrikcode.pes.R;
import org.afrikcode.pes.activities.HomeActivity;
import org.afrikcode.pes.adapter.TimelineAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.decorator.ItemOffsetDecoration;
import org.afrikcode.pes.enums.Channel;
import org.afrikcode.pes.enums.TimestampType;
import org.afrikcode.pes.fragments.timeline.YearFragment;
import org.afrikcode.pes.impl.TimelineImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Day;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Service;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.views.TimeStampView;

import java.util.List;

public class ServicesFragment extends BaseFragment<TimelineImpl> implements SearchView.OnQueryTextListener, OnitemClickListener<Service>,TimeStampView {

    private String branchID, branchName;
    private TimelineAdapter<Service> mServiceAdapter;
    private AlertDialog dialog;

    public ServicesFragment() {
        setTitle("Available Services");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.getItem(0);
        search.setVisible(true);

        HomeActivity activity = (HomeActivity) getContext();
        activity.getSearchView().setQueryHint("Search for a service...");

        activity.getSearchView().setOnQueryTextListener(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getArguments() != null) {
            branchID = this.getArguments().getString("BranchID");
            branchName = this.getArguments().getString("BranchName");
        }

        setImpl(new TimelineImpl(branchID, branchName));
        getImpl().setView(this);
        setHasOptionsMenu(true);

        getMessagingImpl().subscribeTo(Channel.TRANSACTIONS_CHANNEL);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a global layout manager
        getRv_list().setLayoutManager(new GridLayoutManager(getContext(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getContext(), R.dimen.recycler_grid_item_offset);
        getRv_list().addItemDecoration(itemOffsetDecoration);
        getRv_list().setHasFixedSize(true);

        mServiceAdapter = new TimelineAdapter<>(getImpl(), TimestampType.SERVICE);
        mServiceAdapter.setOnclick(this);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddServiceDialog();
            }
        });

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getServices();
            }
        });

        getImpl().getServices();
    }

    private void showAddServiceDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        HomeActivity homeActivity = (HomeActivity) getContext();
        View view = homeActivity.getLayoutInflater().inflate(R.layout.dialog_add_new_year, null);

        final EditText et_year = view.findViewById(R.id.et_year);
        TextView title = view.findViewById(R.id.title);
        title.setText(getString(R.string.add_service));

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
                String serviceName = et_year.getText().toString().trim();
                if (serviceName.isEmpty()) {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                Service sv = new Service(serviceName);
                sv.setBranchID(branchID);
                sv.setBranchName(branchName);
                getImpl().addService(sv);

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
        mServiceAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mServiceAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onClick(Service data) {
        if (data.isActive()) {
            if (getFragmentListener() != null) {
                Bundle b = new Bundle();
                b.putString("BranchID", branchID);
                b.putString("BranchName", branchName);
                b.putString("ServiceID", data.getId());
                YearFragment mf = new YearFragment();
                mf.setArguments(b);

                getFragmentListener().moveToFragment(mf);
            }
        } else {
            Toast.makeText(getContext(), data.getName() + " not activated,", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoadingIndicator() {
        super.showLoadingIndicator();

        getInfoText().setText("Please wait... loading data from database");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        super.hideLoadingIndicator();

        getInfoText().setVisibility(View.GONE);
    }

    @Override
    public void onServiceAdded() {
        Toast.makeText(getContext(), "Service has been successfully added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onYearAdded() {

    }

    @Override
    public void onMonthAdded() {

    }

    @Override
    public void onWeekAdded() {

    }

    @Override
    public void onDayAdded() {

    }

    @Override
    public void ongetServices(List<Service> serviceList) {
        if (serviceList.isEmpty()) {
            getFab().setVisibility(View.VISIBLE);
            showErrorLayout("No Services Added yet");
            return;
        }

        hideErrorLayout();
        getFab().setVisibility(View.VISIBLE);

        // pass data to adapter

        mServiceAdapter.setItemList(serviceList);

        getRv_list().setAdapter(mServiceAdapter);
        mServiceAdapter.notifyDataSetChanged();

        getInfoText().setText("Select service to view transactions made by current branch or add a new year.");
        getInfoText().setVisibility(View.VISIBLE);

    }

    @Override
    public void ongetYears(List<Year> yearList) {

    }

    @Override
    public void ongetMonthsinYear(List<Month> monthList) {

    }

    @Override
    public void ongetWeeksinMonth(List<Week> weekList) {

    }

    @Override
    public void ongetDaysinWeek(List<Day> dayList) {

    }

    @Override
    public void onActivate(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
