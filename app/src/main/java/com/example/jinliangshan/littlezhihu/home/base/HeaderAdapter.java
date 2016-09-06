package com.example.jinliangshan.littlezhihu.home.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface HeaderAdapter<VH extends RecyclerView.ViewHolder>{

    /**
     * Creates a new ViewHolder for a header.  This works the same way onCreateViewHolder in
     * Recycler.Adapter, ViewHolders can be reused for different views.  This is usually a good place
     * to inflate the layout for the header.
     *
     * @param parent the view to create a header view holder for
     * @return the view holder
     */
    VH onCreateHeaderViewHolder(ViewGroup parent);

    /**
     * Binds an existing ViewHolder to the specified adapter position.
     *
     * @param holder the view holder
     */
    void onBindHeaderViewHolder(VH holder);

    /**
     * @return the number of views in the adapter
     */
    int getItemCount();
}
