package org.afrikcode.pes.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.afrikcode.pes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionVH extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_client_name)
    public TextView clientName;

    @BindView(R.id.tv_date)
    public TextView date;

    @BindView(R.id.tv_amount)
    public TextView amount;

    @BindView(R.id.parentCard)
    public CardView parentCard;

    public TransactionVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
