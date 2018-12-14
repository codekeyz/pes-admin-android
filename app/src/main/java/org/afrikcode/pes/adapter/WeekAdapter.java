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
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.viewholder.TimelineVH;

import java.util.ArrayList;
import java.util.List;

public class WeekAdapter extends BaseAdapter<Week, OnitemClickListener<Week>, TimelineVH> {

    private TimelineImpl timelineImpl;

    public WeekAdapter(TimelineImpl timeline) {
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
        final Week week = getFilteredList().get(position);
        holder.getName().setText(week.getName());
        holder.getTotalAmount().setText(String.valueOf(week.getTotalAmount()));
        holder.getSwitch().setChecked(week.isActive());


        holder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                timelineImpl.setTimelineActiveStatus(week.getId(), TimestampType.WEEK, isActive);
            }
        });

        if (getOnclick() != null) {
            // handle click events;
            holder.getParent().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnclick().onClick(week);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new BaseFilter<Week, WeekAdapter>(this) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Week> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = getItemList();
                } else {
                    for (Week b : getItemList()) {
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
