package org.afrikcode.pes.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.afrikcode.pes.listeners.OnitemClickListener;

import java.util.List;

/**
 * Heterogenous Adapter;
 *
 * @param <T> - Data Object
 * @param <P> - OnclickListener
 * @param <V> - ViewHolder
 */
public abstract class BaseAdapter<T, P extends OnitemClickListener<T>, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    private List<T> itemList;
    private P onclicklistener;

    public BaseAdapter() {
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    protected List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }

    protected P getOnclicklistener() {
        return onclicklistener;
    }

    public void setOnclicklistener(P onclicklistener) {
        this.onclicklistener = onclicklistener;
    }
}
