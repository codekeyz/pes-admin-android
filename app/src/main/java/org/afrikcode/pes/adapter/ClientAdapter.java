package org.afrikcode.pes.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import org.afrikcode.pes.R;
import org.afrikcode.pes.base.BaseAdapter;
import org.afrikcode.pes.base.BaseFilter;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Client;
import org.afrikcode.pes.viewholder.ClientVH;

import java.util.ArrayList;
import java.util.List;

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
        Client client = getFilteredList().get(position);
        holder.clientName.setText(client.getName());
        holder.clientContact.setText(client.getTelephone());
    }

    @Override
    public Filter getFilter() {
        return new BaseFilter<Client, ClientAdapter>(this) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Client> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = getItemList();
                } else {
                    for (Client b : getItemList()) {
                        if (b.getName().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(b);
                        }
                    }
                }

                Filter.FilterResults results = new Filter.FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }
        };
    }
}
