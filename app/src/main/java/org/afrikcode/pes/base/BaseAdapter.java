package org.afrikcode.pes.base;

import android.support.v7.widget.RecyclerView;
import android.widget.Filterable;

import org.afrikcode.pes.listeners.OnitemClickListener;

import java.util.List;

/**
 * Heterogenous Adapter;
 *
 * @param <T> - Data Object
 * @param <P> - OnclickListener
 * @param <V> - ViewHolder
 */
public abstract class BaseAdapter<T, P extends OnitemClickListener<T>, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> implements Filterable {

    private List<T> itemList;
    private List<T> filteredList;
    private P onclick;

    public BaseAdapter() {
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    protected List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
        filteredList = itemList;
    }

    protected P getOnclick() {
        return onclick;
    }

    public void setOnclick(P onclick) {
        this.onclick = onclick;
    }

    protected List<T> getFilteredList() {
        return filteredList;
    }

    void setFilteredList(List<T> filteredList) {
        this.filteredList = filteredList;
    }
}
