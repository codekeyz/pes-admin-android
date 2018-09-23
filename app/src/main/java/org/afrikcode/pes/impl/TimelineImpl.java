package org.afrikcode.pes.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.afrikcode.pes.base.BaseImp;
import org.afrikcode.pes.contracts.TimeStampContract;
import org.afrikcode.pes.enums.TimestampType;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.views.TimeStampView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimelineImpl extends BaseImp<TimeStampView> implements TimeStampContract {

    private CollectionReference yearsRef, monthsRef, mWeeksRef;
    private String amountIndex, totalIndex;

    public TimelineImpl(String branchID, String branchName) {
        DatabaseImp databaseImp = new DatabaseImp();
        yearsRef = databaseImp.getYearsReference();
        monthsRef = databaseImp.getMonthsReference();
        mWeeksRef = databaseImp.getWeeksReference();
        amountIndex = branchID.concat(branchName).concat("Total");
        totalIndex = branchID.concat(branchName).concat("Number");
    }

    @Override
    public void addYear(Year year) {
        getView().showLoadingIndicator();
        yearsRef.add(year.datatoMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onYearAdded();
                } else {
                    getView().onError("Year could not be added, try again later");
                }
            }
        });
    }

    @Override
    public void addMonth(Month month) {
        getView().showLoadingIndicator();
        monthsRef.add(month.datatoMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onMonthAdded();
                } else {
                    getView().onError("Month could not be added");
                }
            }
        });
    }

    @Override
    public void addWeek(Week week) {
        getView().showLoadingIndicator();
        mWeeksRef.add(week.datatoMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onWeekAdded();
                } else {
                    getView().onError("Week could not be added");
                }
            }
        });
    }

    @Override
    public void getYears() {
        getView().showLoadingIndicator();
        yearsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                List<Year> yearList = new ArrayList<>();
                for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                    Map<String, Object> data = snapshot.getData();

                    Year year = new Year().maptoData(data);

                    if (data.get(amountIndex) != null) {
                        year.setTotalAmount(Double.valueOf(String.valueOf(data.get(amountIndex))));
                    } else {
                        year.setTotalAmount(0.0);
                    }

                    if (data.get(totalIndex) != null) {
                        year.setTotalTransactions(Double.valueOf(String.valueOf(data.get(totalIndex))));
                    } else {
                        year.setTotalTransactions(0);
                    }

                    year.setId(snapshot.getId());
                    yearList.add(year);
                }

                getView().ongetYears(yearList);
            }
        });
    }

    @Override
    public void getMonthsinYear(String yearID) {
        getView().showLoadingIndicator();
        monthsRef.whereEqualTo("yearID", yearID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                List<Month> monthList = new ArrayList<>();
                for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                    Map<String, Object> data = snapshot.getData();
                    Log.d("Message", data.toString());

                    Month month = new Month().maptoData(data);

                    if (data.get(amountIndex) != null) {
                        month.setTotalAmount(Double.valueOf(String.valueOf(data.get(amountIndex))));
                    } else {
                        month.setTotalAmount(0.0);
                    }

                    if (data.get(totalIndex) != null) {
                        month.setTotalTransactions(Double.valueOf(String.valueOf(data.get(totalIndex))));
                    } else {
                        month.setTotalTransactions(0);
                    }

                    month.setId(snapshot.getId());
                    monthList.add(month);
                }

                getView().ongetMonthsinYear(monthList);
            }
        });
    }

    @Override
    public void getWeeksinMonth(String yearID, String monthID) {
        getView().showLoadingIndicator();
        mWeeksRef.whereEqualTo("yearID", yearID).whereEqualTo("monthID", monthID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                List<Week> weekList = new ArrayList<>();
                for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                    Map<String, Object> data = snapshot.getData();
                    Log.d("Message", data.toString());

                    Week week = new Week().maptoData(data);
                    if (data.get(amountIndex) != null) {
                        week.setTotalAmount(Double.valueOf(String.valueOf(data.get(amountIndex))));
                    } else {
                        week.setTotalAmount(0.0);
                    }

                    if (data.get(totalIndex) != null) {
                        week.setTotalTransactions(Double.valueOf(String.valueOf(data.get(totalIndex))));
                    } else {
                        week.setTotalTransactions(0);
                    }

                    week.setId(snapshot.getId());
                    weekList.add(week);
                }

                getView().ongetWeeksinMonth(weekList);
            }
        });
    }


    @Override
    public void setTimelineActiveStatus(String id, TimestampType type, boolean isActive) {
        getView().showLoadingIndicator();
        getReference(type).document(id).update("isActive", isActive).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onActivate("Selected timeline status has been updated");
                } else {
                    getView().onError("Selected timeline could not be activated");
                }
            }
        });
    }

    private CollectionReference getReference(TimestampType type) {
        if (type == TimestampType.YEAR) {
            return yearsRef;
        } else if (type == TimestampType.MONTH) {
            return monthsRef;
        } else {
            return mWeeksRef;
        }
    }
}
