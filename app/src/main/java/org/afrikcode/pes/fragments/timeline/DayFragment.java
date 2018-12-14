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
import org.afrikcode.pes.adapter.DayAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.decorator.ItemOffsetDecoration;
import org.afrikcode.pes.fragments.TransactionsFragment;
import org.afrikcode.pes.impl.TimelineImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Day;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.views.TimeStampView;

import java.util.List;

import butterknife.BindArray;

public class DayFragment extends BaseFragment<TimelineImpl> implements OnitemClickListener<Day>, TimeStampView, SearchView.OnQueryTextListener {

    @BindArray(R.array.days_array)
    String[] days;
    private AlertDialog dialog;
    private DayAdapter mDayAdapter;
    private String branchID, branchName, yearID, monthID, weekID;

    public DayFragment() {
        setTitle("Select Day");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem search = menu.getItem(0);
        search.setVisible(true);

        HomeActivity activity = (HomeActivity) getContext();
        activity.getSearchView().setQueryHint("Search Days...");

        activity.getSearchView().setOnQueryTextListener(this);

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
            weekID = b.getString("WeekID");
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

        mDayAdapter = new DayAdapter(getImpl());
        mDayAdapter.setOnclick(this);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWeekDialog();
            }
        });

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getDaysinWeeK(yearID, monthID, weekID);
            }
        });

        getImpl().getDaysinWeeK(yearID, monthID, weekID);
    }

    @Override
    public void onDayAdded() {
        Toast.makeText(getContext(), "Day has been successfully added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ongetDaysinWeek(List<Day> dayList) {
        if (dayList.isEmpty()) {
            showErrorLayout("No Days Added yet");
            return;
        }

        hideErrorLayout();

        // create adapter and pass data to it
        mDayAdapter.setItemList(dayList);
        getRv_list().setAdapter(mDayAdapter);
        mDayAdapter.notifyDataSetChanged();

        getInfoText().setText("Select Day to view transactions made by current branch or add a new week.");
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
    public void onClick(Day data) {
        if (data.isActive()) {
            if (getFragmentListener() != null) {
                Bundle b = new Bundle();
                b.putString("BranchID", branchID);
                b.putString("YearID", yearID);
                b.putString("MonthID", monthID);
                b.putString("WeekID", weekID);
                b.putString("DayID", data.getId());
                TransactionsFragment tf = new TransactionsFragment();
                tf.setArguments(b);
                getFragmentListener().moveToFragment(tf);
            }
        } else {
            Toast.makeText(getContext(), data.getName() + " not activated,", Toast.LENGTH_SHORT).show();
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

    @Override
    public void ongetWeeksinMonth(List<Week> weekList) {

    }

    private void showAddWeekDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        HomeActivity activity = (HomeActivity) getContext();
        View view = activity.getLayoutInflater().inflate(R.layout.dialog_add_new_day, null);

        final Spinner spinner_weeks = view.findViewById(R.id.spinner_weeks);
        Button cancel = view.findViewById(R.id.btn_cancel);
        Button add = view.findViewById(R.id.btn_submit);

        spinner_weeks.setAdapter(new ArrayAdapter<>(getContext().getApplicationContext(), R.layout.spinner_item, days));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String week = days[spinner_weeks.getSelectedItemPosition()];
                Day w = new Day(week, yearID, monthID, weekID);
                getImpl().addDay(w);
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
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        mDayAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mDayAdapter.getFilter().filter(newText);
        return false;
    }
}
