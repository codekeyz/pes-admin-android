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
import org.afrikcode.pes.models.Day;
import org.afrikcode.pes.viewholder.TimelineVH;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends BaseAdapter<Day, OnitemClickListener<Day>, TimelineVH> {

    private TimelineImpl timelineImpl;

    public DayAdapter(TimelineImpl timeline) {
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
        final Day day = getFilteredList().get(position);
        holder.getName().setText(day.getName());
        holder.getTotalAmount().setText(String.valueOf(day.getTotalAmount()));
        holder.getSwitch().setChecked(day.isActive());


        holder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                timelineImpl.setTimelineActiveStatus(day.getId(), TimestampType.DAY, isActive);
            }
        });

        if (getOnclick() != null) {
            // handle click events;
            holder.getParent().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnclick().onClick(day);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new BaseFilter<Day, DayAdapter>(this) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Day> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = getItemList();
                } else {
                    for (Day b : getItemList()) {
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
