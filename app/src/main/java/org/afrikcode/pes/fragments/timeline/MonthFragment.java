package org.afrikcode.pes.fragments.timeline;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.afrikcode.pes.R;
import org.afrikcode.pes.activities.HomeActivity;
import org.afrikcode.pes.adapter.MonthAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.decorator.ItemOffsetDecoration;
import org.afrikcode.pes.impl.TimelineImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Day;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.views.TimeStampView;

import java.util.List;

import butterknife.BindArray;

public class MonthFragment extends BaseFragment<TimelineImpl> implements OnitemClickListener<Month>, TimeStampView, SearchView.OnQueryTextListener {

    @BindArray(R.array.months_array)
    String[] months;
    private AlertDialog dialog = null;
    private String branchID, branchName, yearID;
    private MonthAdapter monthAdapter;

    public MonthFragment() {
        setTitle("Select Month");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.getItem(0);
        search.setVisible(true);

        HomeActivity activity = (HomeActivity) getContext();
        activity.getSearchView().setQueryHint("Search Months...");

        activity.getSearchView().setOnQueryTextListener(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getArguments() != null) {
            branchName = this.getArguments().getString("BranchName");
            branchID = this.getArguments().getString("BranchID");
            yearID = this.getArguments().getString("YearID");
        }

        setImpl(new TimelineImpl(branchID, branchName));
        getImpl().setView(this);
        setHasOptionsMenu(true);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a global layout manager
        getRv_list().setLayoutManager(new GridLayoutManager(getContext(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getContext(), R.dimen.recycler_grid_item_offset);
        getRv_list().addItemDecoration(itemOffsetDecoration);

        monthAdapter = new MonthAdapter(getImpl());
        monthAdapter.setOnclick(this);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddMonthDialog();
            }
        });

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getMonthsinYear(yearID);
            }
        });

        getImpl().getMonthsinYear(yearID);
    }

    private void showAddMonthDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        HomeActivity homeActivity = (HomeActivity) getActivity();
        View view = homeActivity.getLayoutInflater().inflate(R.layout.dialog_add_new_month, null);

        final Spinner spinner_month = view.findViewById(R.id.spinner_month);

        Button cancel = view.findViewById(R.id.btn_cancel);
        Button add = view.findViewById(R.id.btn_submit);

        spinner_month.setAdapter(new ArrayAdapter<>(getContext(), R.layout.spinner_item, months));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month = months[spinner_month.getSelectedItemPosition()];
                Month m = new Month(month, yearID);
                getImpl().addMonth(m);
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
    public void onMonthAdded() {
        Toast.makeText(getContext(), "Month has been successfully added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ongetMonthsinYear(List<Month> monthList) {
        if (monthList.isEmpty()) {
            showErrorLayout("No month Added yet");
            return;
        }

        hideErrorLayout();

        // create adapter and pass data to it
        monthAdapter.setItemList(monthList);

        getRv_list().setAdapter(monthAdapter);
        monthAdapter.notifyDataSetChanged();

        getInfoText().setText("Select month to view transactions made by current branch or add a new month.");
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
        getInfoText().setText("Please wait... loading months from database");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        super.hideLoadingIndicator();
        getInfoText().setVisibility(View.GONE);
    }

    @Override
    public void onClick(Month data) {
        if (data.isActive()) {
            if (getFragmentListener() != null) {
                Bundle b = new Bundle();
                b.putString("BranchID", branchID);
                b.putString("BranchName", branchName);
                b.putString("YearID", yearID);
                b.putString("MonthID", data.getId());
                WeekFragment wf = new WeekFragment();
                wf.setArguments(b);

                getFragmentListener().moveToFragment(wf);
            }
        } else {
            Toast.makeText(getContext(), data.getName() + " not activated,", Toast.LENGTH_SHORT).show();
        }

    }

    //********************************** This callbacks won't work here *********************//

    @Override
    public void onYearAdded() {

    }

    @Override
    public void onWeekAdded() {

    }

    @Override
    public void onDayAdded() {

    }

    @Override
    public void ongetYears(List<Year> yearList) {

    }

    @Override
    public void ongetWeeksinMonth(List<Week> weekList) {

    }

    @Override
    public void ongetDaysinWeek(List<Day> dayList) {

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        monthAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        monthAdapter.getFilter().filter(newText);
        return false;
    }
}
