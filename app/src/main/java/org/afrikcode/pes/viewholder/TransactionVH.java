package org.afrikcode.pes.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class TransactionVH extends RecyclerView.ViewHolder {

    public TransactionVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
