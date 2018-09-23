package org.afrikcode.pes.fragments.timeline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.afrikcode.pes.R;
import org.afrikcode.pes.activities.HomeActivity;
import org.afrikcode.pes.adapter.WeekAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.decorator.ItemOffsetDecoration;
import org.afrikcode.pes.fragments.ClientsFragment;
import org.afrikcode.pes.impl.TimelineImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.views.TimeStampView;

import java.util.List;

import butterknife.BindArray;

public class WeekFragment extends BaseFragment<TimelineImpl> implements OnitemClickListener<Week>, TimeStampView {

    @BindArray(R.array.weeks_array)
    String[] weeks;
    private AlertDialog dialog;
    private WeekAdapter mWeekAdapter;
    private String branchID, branchName, yearID, monthID;

    public WeekFragment() {
        setTitle("Select Week");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getArguments() != null) {
            Bundle b = this.getArguments();
            branchName = b.getString("BranchName");
            branchID = b.getString("BranchID");
            yearID = b.getString("YearID");
            monthID = b.getString("MonthID");
        }

        setImpl(new TimelineImpl(branchID, branchName));
        getImpl().setView(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a global layout manager
        getRv_list().setLayoutManager(new GridLayoutManager(getContext(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getContext(), R.dimen.recycler_grid_item_offset);
        getRv_list().addItemDecoration(itemOffsetDecoration);

        mWeekAdapter = new WeekAdapter(getImpl());
        mWeekAdapter.setOnclicklistener(this);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWeekDialog();
            }
        });

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getWeeksinMonth(yearID, monthID);
            }
        });

        getImpl().getWeeksinMonth(yearID, monthID);
    }

    private void showAddWeekDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        HomeActivity activity = (HomeActivity) getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_add_new_week, null);

        final Spinner spinner_weeks = view.findViewById(R.id.spinner_weeks);
        Button cancel = view.findViewById(R.id.btn_cancel);
        Button add = view.findViewById(R.id.btn_submit);

        spinner_weeks.setAdapter(new ArrayAdapter<>(getContext().getApplicationContext(), R.layout.spinner_item, weeks));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String week = weeks[spinner_weeks.getSelectedItemPosition()];
                Week w = new Week(week, yearID, monthID);
                getImpl().addWeek(w);
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
    public void onWeekAdded() {
        Toast.makeText(getContext(), "Week has been successfully added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ongetWeeksinMonth(List<Week> weekList) {
        if (weekList.isEmpty()) {
            showErrorLayout("No weeks Added yet");
            return;
        }

        hideErrorLayout();

        // create adapter and pass data to it
        mWeekAdapter.setItemList(weekList);
        getRv_list().setAdapter(mWeekAdapter);
        mWeekAdapter.notifyDataSetChanged();

        getInfoText().setText("Select week to view transactions made by current branch or add a new week.");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivate(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator() {
        super.showLoadingIndicator();
        getInfoText().setText("Please wait... loading weeks from database");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        super.hideLoadingIndicator();
        getInfoText().setVisibility(View.GONE);
    }

    @Override
    public void onClick(Week data) {
        if (getFragmentListener() != null) {
            Bundle b = new Bundle();
            b.putString("BranchID", branchID);
            b.putString("YearID", yearID);
            b.putString("MonthID", data.getId());
            b.putString("WeekID", data.getId());
            ClientsFragment cf = new ClientsFragment();
            cf.setArguments(b);
            getFragmentListener().moveToFragment(cf);
        }
    }


    //***************************** This callbacks won't work in this fragment *****************//

    @Override
    public void onYearAdded() {

    }

    @Override
    public void ongetYears(List<Year> yearList) {

    }

    @Override
    public void onMonthAdded() {

    }

    @Override
    public void ongetMonthsinYear(List<Month> monthList) {

    }

}
