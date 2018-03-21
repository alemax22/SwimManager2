package org.altervista.alecat.swimmanager.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.altervista.alecat.swimmanager.R;
import org.altervista.alecat.swimmanager.interfaces.ItemClickListener;
import org.altervista.alecat.swimmanager.models.Group;
import org.altervista.alecat.swimmanager.models.Swimmer;

/**
 * Created by Alessandro Cattapan on 21/02/2018.
 */

public class GroupFirestoreRecyclerAdapter extends FirestoreRecyclerAdapter<Group, GroupFirestoreRecyclerAdapter.GroupHolder> {

    private Context mContext;

    public GroupFirestoreRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Group> options, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull GroupHolder holder, int position, @NonNull Group model) {

    }

    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * Inner class
     */
    public class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView mTextView;

        private ItemClickListener mItemClickListener;

        public GroupHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
}
