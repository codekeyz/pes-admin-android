package org.afrikcode.pes.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.afrikcode.pes.R;
import org.afrikcode.pes.models.Manager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManagerVH extends RecyclerView.ViewHolder{

    @BindView(R.id.tv_manager_name)
    TextView managerName;

    @BindView(R.id.tv_manager_branch)
    TextView managerBranch;

    @BindView(R.id.tv_manager_contact)
    TextView managerContact;

    @BindView(R.id.status_switch)
    Switch status;

    @BindView(R.id.parentCard)
    CardView parent;

    public ManagerVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(final Manager manager) {
        managerName.setText(manager.getUsername());
        status.setChecked(manager.isAccountConfirmed());
        if (manager.getBranchName() != null || manager.getBranchID() != null){
            managerBranch.setText(manager.getBranchName());
            managerBranch.setOnClickListener(null);
        }else {
            String text = "<b>No branch set. </b><font color=\"#e81d84\">Set a Branch Now</font>";
            managerBranch.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        }
        if (manager.getTelephone() != null){
            managerContact.setText(manager.getTelephone());
        }else{
            managerContact.setVisibility(View.GONE);
        }
    }

    public CardView getParent() {
        return parent;
    }

    public Switch getStatusSwitch() {
        return status;
    }

    public TextView getManagerBranchText() {
        return managerBranch;
    }
}
