package org.afrikcode.pes.fragments.timeline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.afrikcode.pes.R;
import org.afrikcode.pes.activities.HomeActivity;
import org.afrikcode.pes.adapter.YearAdapter;
import org.afrikcode.pes.base.BaseFragment;
import org.afrikcode.pes.decorator.ItemOffsetDecoration;
import org.afrikcode.pes.impl.TimelineImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.views.TimeStampView;

import java.util.List;


public class YearFragment extends BaseFragment<TimelineImpl> implements OnitemClickListener<Year>, TimeStampView {

    private AlertDialog dialog = null;
    private YearAdapter mYearAdapter;
    private String branchID, branchName;

    public YearFragment() {
        setTitle("Select Year");
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
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting a global layout manager
        getRv_list().setLayoutManager(new GridLayoutManager(getContext(), 2));
        ItemOffsetDecoration itemOffsetDecoration = new ItemOffsetDecoration(getContext(), R.dimen.recycler_grid_item_offset);
        getRv_list().addItemDecoration(itemOffsetDecoration);
        getRv_list().setHasFixedSize(true);

        mYearAdapter = new YearAdapter(getImpl());
        mYearAdapter.setOnclicklistener(this);

        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddYearDialog();
            }
        });

        getSwipeRefresh().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getImpl().getYears();
            }
        });

        getImpl().getYears();
    }

    private void showAddYearDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        HomeActivity homeActivity = (HomeActivity) getContext();
        View view = homeActivity.getLayoutInflater().inflate(R.layout.dialog_add_new_year, null);

        final EditText et_year = view.findViewById(R.id.et_year);
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
                String year = et_year.getText().toString().trim();
                if (year.isEmpty()) {
                    Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }
                Year y = new Year(year);
                getImpl().addYear(y);

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
    public void onYearAdded() {
        Toast.makeText(getContext(), "Year has been successfully added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ongetYears(List<Year> yearList) {
        if (yearList.isEmpty()) {
            getFab().setVisibility(View.VISIBLE);
            showErrorLayout("No year Added yet");
            return;
        }

        hideErrorLayout();
        getFab().setVisibility(View.VISIBLE);

        // create adapter and pass data to it

        mYearAdapter.setItemList(yearList);

        getRv_list().setAdapter(mYearAdapter);
        mYearAdapter.notifyDataSetChanged();

        getInfoText().setText("Select year to view transactions made by current branch or add a new year.");
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

        getInfoText().setText("Please wait... loading data from database");
        getInfoText().setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        super.hideLoadingIndicator();
        getInfoText().setVisibility(View.GONE);
    }

    @Override
    public void onClick(Year data) {
        if (getFragmentListener() != null) {
            Bundle b = new Bundle();
            b.putString("BranchID", branchID);
            b.putString("BranchName", branchName);
            b.putString("YearID", data.getId());
            MonthFragment mf = new MonthFragment();
            mf.setArguments(b);

            getFragmentListener().moveToFragment(mf);
        }
    }

    // ************************* This callbacks won't work here *******************//
    @Override
    public void onMonthAdded() {

    }

    @Override
    public void onWeekAdded() {

    }

    @Override
    public void ongetMonthsinYear(List<Month> monthList) {

    }

    @Override
    public void ongetWeeksinMonth(List<Week> weekList) {

    }


}
