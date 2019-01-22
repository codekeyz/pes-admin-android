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
import org.afrikcode.pes.models.Day;
import org.afrikcode.pes.models.Month;
import org.afrikcode.pes.models.Service;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.views.TimeStampView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimelineImpl extends BaseImp<TimeStampView> implements TimeStampContract {

    private CollectionReference servicesRef, yearsRef, monthsRef, mWeeksRef, mdaysRef;
    private String amountIndex, branchID, branchName;

    public TimelineImpl(String branchID, String branchName) {
        this.branchID = branchID;
        this.branchName = branchName;
        DatabaseImp databaseImp = new DatabaseImp();
        servicesRef = databaseImp.getServicesReference();
        yearsRef = databaseImp.getYearsReference();
        monthsRef = databaseImp.getMonthsReference();
        mWeeksRef = databaseImp.getWeeksReference();
        mdaysRef = databaseImp.getDaysReference();
        this.branchID = branchID;
        this.branchName = branchName;
        amountIndex = branchID.concat(branchName).concat("Total");
    }

    @Override
    public void addService(Service service) {
        getView().showLoadingIndicator();
        service.setBranchID(branchID);
        service.setBranchName(branchName);
        servicesRef.add(service.datatoMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onServiceAdded();
                } else {
                    getView().onError("Service could not be added, try again later");
                }
            }
        });
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
    public void addDay(Day day) {
        getView().showLoadingIndicator();
        mdaysRef.add(day.datatoMap()).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                getView().hideLoadingIndicator();
                if (task.isSuccessful()) {
                    getView().onDayAdded();
                } else {
                    getView().onError("Day could not be added");
                }
            }
        });
    }

    @Override
    public void getServices() {
        getView().showLoadingIndicator();
        servicesRef
                .whereEqualTo("branchID", branchID)
                .whereEqualTo("branchName", branchName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        getView().hideLoadingIndicator();
                        List<Service> serviceList = new ArrayList<>();
                        for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                            Service service = new Service().maptoData(snapshot.getData());
                            service.setId(snapshot.getId());
                            serviceList.add(service);
                        }

                        getView().ongetServices(serviceList);
                    }
                });
    }

    @Override
    public void getYears(String serviceID) {
        getView().showLoadingIndicator();
        yearsRef.whereEqualTo("serviceID", serviceID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                List<Year> yearList = new ArrayList<>();
                for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                    Year year = new Year().maptoData(snapshot.getData());
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
                    Month month = new Month().maptoData(snapshot.getData());
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
                    Week week = new Week().maptoData(snapshot.getData());
                    week.setId(snapshot.getId());
                    weekList.add(week);
                }

                getView().ongetWeeksinMonth(weekList);
            }
        });
    }

    @Override
    public void getDaysinWeeK(String yearID, String monthID, String weekID) {
        getView().showLoadingIndicator();
        mdaysRef.whereEqualTo("yearID", yearID).whereEqualTo("monthID", monthID).whereEqualTo("weekID", weekID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                getView().hideLoadingIndicator();
                List<Day> dayList = new ArrayList<>();
                for (DocumentSnapshot snapshot : documentSnapshots.getDocuments()) {
                    Day day = new Day().maptoData(snapshot.getData());
                    day.setId(snapshot.getId());
                    dayList.add(day);
                }

                getView().ongetDaysinWeek(dayList);
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
        } else if (type == TimestampType.WEEK) {
            return mWeeksRef;
        } else if (type == TimestampType.SERVICE) {
            return servicesRef;
        } else {
            return mdaysRef;
        }
    }
}
