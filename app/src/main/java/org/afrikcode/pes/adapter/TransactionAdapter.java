package org.afrikcode.pes.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.afrikcode.pes.base.BaseAdapter;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Transaction;
import org.afrikcode.pes.viewholder.TransactionVH;

public class TransactionAdapter extends BaseAdapter<Transaction, OnitemClickListener<Transaction>, TransactionVH> {

    @NonNull
    @Override
    public TransactionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionVH holder, int position) {

    }
}
