package org.altervista.alecat.swimmanager.ui.adapter;

import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.altervista.alecat.swimmanager.interfaces.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alessandro Cattapan on 23/02/2018.
 */

public class SelectableFirestoreAdapter<T extends Selectable, VH extends FirebaseViewHolder> extends FirestoreRecyclerAdapter<T, VH> {

    protected SparseBooleanArray pSelectedItems;

    public SelectableFirestoreAdapter(@NonNull FirestoreRecyclerOptions<T> options) {
        super(options);
        pSelectedItems = new SparseBooleanArray();
    }

    /**
     * Indicates if the item at position position is selected
     * @param position Position of the item to check
     * @return true if the item is selected, false otherwise
     */
    public boolean isSelected(int position) {
        return pSelectedItems.get(position, false);
    }

    /**
     * Toggle the selection status of the item at a given position
     * @param position Position of the item to toggle the selection status for
     */
    public void toggleSelection(int position) {
        if (pSelectedItems.get(position, false)) {
            pSelectedItems.delete(position);
        } else {
            pSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    /**
     * Clear the selection status for all items
     */
    public void clearSelection() {
        List<Integer> selection = getSelectedItems();
        pSelectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    /**
     * Count the selected items
     * @return Selected items count
     */
    public int getSelectedItemCount() {
        return pSelectedItems.size();
    }

    /**
     * Indicates the list of selected items
     * @return List of selected items ids
     */
    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(pSelectedItems.size());
        for (int i = 0; i < pSelectedItems.size(); i++) {
            items.add(pSelectedItems.keyAt(i));
        }
        return items;
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, int position, @NonNull T model) {

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }
}
