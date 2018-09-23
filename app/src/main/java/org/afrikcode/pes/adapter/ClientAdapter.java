package org.afrikcode.pes.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.afrikcode.pes.R;
import org.afrikcode.pes.base.BaseAdapter;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Client;
import org.afrikcode.pes.viewholder.ClientVH;

public class ClientAdapter extends BaseAdapter<Client, OnitemClickListener<Client>, ClientVH> {

    public ClientAdapter() {
    }

    @NonNull
    @Override
    public ClientVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        return new ClientVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientVH holder, int position) {
        Client client = getItemList().get(position);
        holder.clientName.setText(client.getName());
        holder.clientContact.setText(client.getTelephone());
    }
}
