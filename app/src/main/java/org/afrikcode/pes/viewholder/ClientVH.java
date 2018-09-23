package org.afrikcode.pes.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.afrikcode.pes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientVH extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_client_name)
    public TextView clientName;

    @BindView(R.id.tv_contact)
    public TextView clientContact;

    public ClientVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
