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
import org.afrikcode.pes.base.BaseTimeline;
import org.afrikcode.pes.enums.TimestampType;
import org.afrikcode.pes.impl.TimelineImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.viewholder.TimelineVH;

import java.util.ArrayList;
import java.util.List;

/***
 * Data Model
 * @param <T>
 */
public class TimelineAdapter<T extends BaseTimeline<T>> extends BaseAdapter<T, OnitemClickListener<T>, TimelineVH> {

    private TimelineImpl timelineImpl;
    private TimestampType type;

    public TimelineAdapter(TimelineImpl timeline, TimestampType type) {
        this.timelineImpl = timeline;
        this.type = type;
    }

    @NonNull
    @Override
    public TimelineVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item, parent, false);
        return new TimelineVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineVH holder, int position) {
        final T item = getFilteredList().get(position);
        holder.getName().setText(item.getName());
        holder.getTotalAmount().setText(String.valueOf(item.getTotalAmount()));
        holder.getSwitch().setChecked(item.isActive());

        holder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                timelineImpl.setTimelineActiveStatus(item.getId(), type, isActive);
            }
        });

        if (getOnclick() != null) {
            // handle click events;
            holder.getParent().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnclick().onClick(item);
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new BaseFilter<T, TimelineAdapter>(this) {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<T> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = getItemList();
                } else {
                    for (T b : getItemList()) {
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
