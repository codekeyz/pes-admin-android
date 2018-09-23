package org.afrikcode.pes.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import org.afrikcode.pes.R;
import org.afrikcode.pes.base.BaseAdapter;
import org.afrikcode.pes.enums.TimestampType;
import org.afrikcode.pes.impl.TimelineImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Week;
import org.afrikcode.pes.viewholder.TimelineVH;

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
        final Week week = getItemList().get(position);
        holder.getName().setText(week.getName());
        holder.getTotalAmount().setText(String.valueOf(week.getTotalAmount()));
        holder.getSwitch().setChecked(week.isActive());


        holder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                timelineImpl.setTimelineActiveStatus(week.getId(), TimestampType.WEEK, isActive);
            }
        });

        if (getOnclicklistener() != null) {
            // handle click events;
            holder.getParent().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnclicklistener().onClick(week);
                }
            });
        }
    }
}
