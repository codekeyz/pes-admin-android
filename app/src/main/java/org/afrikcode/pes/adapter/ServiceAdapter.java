package org.afrikcode.pes.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import org.afrikcode.pes.base.BaseAdapter;
import org.afrikcode.pes.base.BaseFilter;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Service;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.viewholder.ServiceVH;

import java.util.ArrayList;
import java.util.List;

public class ServiceAdapter extends BaseAdapter<Service, OnitemClickListener<Service>, ServiceVH> {

    public ServiceAdapter(){}

    @Override
    public Filter getFilter() {
        return new BaseFilter<Service, ServiceAdapter>(this) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Service> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = getItemList();
                } else {
                    for (Service b : getItemList()) {
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

    @NonNull
    @Override
    public ServiceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceVH holder, int position) {
        final Service service = getFilteredList().get(position);



        if (getOnclick() != null) {
            //sets listener
//            holder.getParent().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    getOnclick().onClick(year);
//                }
//            });
        }
    }
}
