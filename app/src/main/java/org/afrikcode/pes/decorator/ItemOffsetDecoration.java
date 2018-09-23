package org.afrikcode.pes.decorator;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public ItemOffsetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    /**
     * mRecyclerView.setLayoutManager(new GridLayoutManager(context, NUM_COLUMNS);
     * ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
     * mRecyclerView.addItemDecoration(itemDecoration);
     * <p>
     * <android.support.v7.widget.RecyclerView
     * android:id="@+id/recyclerview_grid"
     * android:layout_width="match_parent"
     * android:layout_height="match_parent"
     * android:clipToPadding="false"
     * android:padding="@dimen/item_offset"/>
     *
     * @param context
     * @param itemOffsetId - should be half size of the actual value you want as space
     */
    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }

}
