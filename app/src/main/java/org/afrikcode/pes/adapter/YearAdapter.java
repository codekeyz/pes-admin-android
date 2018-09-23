package org.afrikcode.pes.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;

import org.afrikcode.pes.R;
import org.afrikcode.pes.base.BaseAdapter;
import org.afrikcode.pes.base.BaseFilter;
import org.afrikcode.pes.enums.TimestampType;
import org.afrikcode.pes.impl.TimelineImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Year;
import org.afrikcode.pes.viewholder.TimelineVH;

import java.util.ArrayList;
import java.util.List;

public class YearAdapter extends BaseAdapter<Year, OnitemClickListener<Year>, TimelineVH> {

    private TimelineImpl timelineImpl;

    public YearAdapter(TimelineImpl timeline) {
        this.timelineImpl = timeline;
    }

    @NonNull
    @Override
    public TimelineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, parent, false);
        return new TimelineVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineVH holder, int position) {
        final Year year = getFilteredList().get(position);
        holder.getName().setText(year.getName());
        holder.getTotalAmount().setText(String.valueOf(year.getTotalAmount()));
        holder.getSwitch().setChecked(year.isActive());


        holder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                timelineImpl.setTimelineActiveStatus(year.getId(), TimestampType.YEAR, isActive);
            }
        });

        if (getOnclicklistener() != null) {
            //set listener
            holder.getParent().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnclicklistener().onClick(year);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new BaseFilter<Year, YearAdapter>(this) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Year> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = getItemList();
                } else {
                    for (Year b : getItemList()) {
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
