package org.altervista.alecat.swimmanager.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.altervista.alecat.swimmanager.interfaces.ItemClickListener;

/**
 * Created by Alessandro Cattapan on 21/02/2018.
 */

public class FirebaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    private ItemClickListener mItemClickListener;

    public FirebaseViewHolder(View itemView) {
        super(itemView);
    }

    public void setItemClickListener (ItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onClick(v, getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        mItemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}
