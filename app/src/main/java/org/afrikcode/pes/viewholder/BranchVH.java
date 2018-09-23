package org.afrikcode.pes.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import org.afrikcode.pes.R;
import org.afrikcode.pes.models.Branch;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BranchVH extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_branch_name)
    TextView branchName;

    @BindView(R.id.tv_branch_address)
    TextView branchAddress;

    @BindView(R.id.tv_branch_contact)
    TextView branchTelephone;

    @BindView(R.id.status_switch)
    Switch aSwitch;

    @BindView(R.id.parentCard)
    CardView parent;

    public BranchVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void render(Branch branch) {
        branchName.setText(branch.getBranchName());
        branchAddress.setText(branch.getAddress());
        branchTelephone.setText(branch.getTelephone());
        aSwitch.setChecked(branch.isBranchActive());
    }

    public CardView getParent() {
        return parent;
    }

    public Switch getSwitch() {
        return aSwitch;
    }
}
