package org.afrikcode.pes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;

import org.afrikcode.pes.R;
import org.afrikcode.pes.base.BaseAdapter;
import org.afrikcode.pes.base.BaseFilter;
import org.afrikcode.pes.impl.BranchImpl;
import org.afrikcode.pes.listeners.OnitemClickListener;
import org.afrikcode.pes.models.Branch;
import org.afrikcode.pes.viewholder.BranchVH;

import java.util.ArrayList;
import java.util.List;

public class BranchAdapter extends BaseAdapter<Branch, OnitemClickListener<Branch>, BranchVH> {

    private Context mContext;
    private BranchImpl branchImpl;

    public BranchAdapter(Context mContext, BranchImpl branchImpl) {
        this.mContext = mContext;
        this.branchImpl = branchImpl;
    }

    @NonNull
    @Override
    public BranchVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_list_item, parent, false);
        return new BranchVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchVH holder, int position) {
        final Branch b = getFilteredList().get(position);

        holder.render(b);

        if (getOnclicklistener() != null) {
            holder.getParent().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOnclicklistener().onClick(b);
                }
            });
        }

        holder.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                if (isActive) {
                    branchImpl.activateBranch(b.getBranchID());
                } else {
                    branchImpl.deactivateBranch(b.getBranchID());
                }
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new BaseFilter<Branch, BranchAdapter>(this) {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<Branch> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = getItemList();
                } else {
                    for (Branch b : getItemList()) {
                        if (b.getBranchName().toLowerCase().contains(query.toLowerCase())) {
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
