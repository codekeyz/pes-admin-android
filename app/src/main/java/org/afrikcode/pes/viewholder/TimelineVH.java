package org.afrikcode.pes.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.afrikcode.pes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimelineVH extends RecyclerView.ViewHolder {

    @BindView(R.id.status_switch)
    Switch aSwitch;
    @BindView(R.id.tv_timelineName)
    TextView name;
    //    @BindView(R.id.tv_editing_status) TextView statusMsg;
    @BindView(R.id.tv_total_monthly_trans)
    TextView totalAmount;
    @BindView(R.id.parentCard)
    CardView parent;

    public TimelineVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public Switch getSwitch() {
        return aSwitch;
    }

    public TextView getName() {
        return name;
    }

//    public TextView getStatusMsg() {
//        return statusMsg;
//    }

    public TextView getTotalAmount() {
        return totalAmount;
    }

    public CardView getParent() {
        return parent;
    }
}
