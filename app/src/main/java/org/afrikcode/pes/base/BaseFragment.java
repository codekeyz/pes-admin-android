package org.afrikcode.pes.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.afrikcode.pes.R;
import org.afrikcode.pes.activities.HomeActivity;
import org.afrikcode.pes.impl.MessagingImpl;
import org.afrikcode.pes.listeners.FragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseFragment<T extends BaseImp> extends Fragment implements BaseView {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.errorText)
    TextView errorText;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.infoText)
    TextView infoText;
    private T impl;
    private FragmentListener fragmentListener;
    private String title;
    private HomeActivity homeActivity;
    private MessagingImpl messagingImpl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity) getActivity();
        messagingImpl = new MessagingImpl();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyclerview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        swipeRefresh.setColorSchemeColors(
                getContext().getResources().getColor(R.color.colorPrimary),
                getContext().getResources().getColor(R.color.colorPrimaryDark));
    }

    public SwipeRefreshLayout getSwipeRefresh() {
        return swipeRefresh;
    }

    public RecyclerView getRv_list() {
        return rv_list;
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public TextView getInfoText() {
        return infoText;
    }

    @Override
    public void showLoadingIndicator() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoadingIndicator() {
        swipeRefresh.setRefreshing(false);
    }

    public T getImpl() {
        return impl;
    }

    public void setImpl(T impl) {
        this.impl = impl;
    }

    public FragmentListener getFragmentListener() {
        return fragmentListener;
    }

    public void setFragmentListener(FragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    public void showErrorLayout(String error) {
        rv_list.setVisibility(View.GONE);
        errorText.setText(error);
        errorText.setVisibility(View.VISIBLE);
    }

    public void hideErrorLayout() {
        rv_list.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.GONE);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private HomeActivity getParentActivity() {
        return homeActivity;
    }

    public MessagingImpl getMessagingImpl() {
        return messagingImpl;
    }

    @Nullable
    @Override
    public Context getContext() {
        return getParentActivity();
    }
}
