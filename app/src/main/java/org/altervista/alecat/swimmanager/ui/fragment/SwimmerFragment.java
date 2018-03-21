package org.altervista.alecat.swimmanager.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.altervista.alecat.swimmanager.R;
import org.altervista.alecat.swimmanager.interfaces.ItemClickListener;
import org.altervista.alecat.swimmanager.ui.adapter.SelectableFirestoreAdapter;
import org.altervista.alecat.swimmanager.ui.adapter.SwimmerFirestoreRecyclerAdapter;
import org.altervista.alecat.swimmanager.models.SwimManagerContract;
import org.altervista.alecat.swimmanager.models.Swimmer;

/**
 * Created by Alessandro Cattapan on 15/02/2018.
 */

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SwimmerFragment extends Fragment {

    private static final String ARG_FIREBASE_TEAM_ID = "firebase-team-id";

    private String mFirebaseTeamId;
    private OnListFragmentInteractionListener mListener;
    private SelectableFirestoreAdapter<Swimmer, SwimmerFirestoreRecyclerAdapter.SwimmerHolder> mSwimmerAdapter;
    private ActionModeCallback mActionModeCallback = new ActionModeCallback();
    private ActionMode mActionMode;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SwimmerFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SwimmerFragment newInstance(String teamId) {
        SwimmerFragment fragment = new SwimmerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FIREBASE_TEAM_ID, teamId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFirebaseTeamId = getArguments().getString(ARG_FIREBASE_TEAM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_swimmer_list, container, false);

        // TODO: Listener Remove Implement
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick){
                    if (mActionMode == null) {
                        mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(mActionModeCallback);
                    }
                    toggleSelection(position);
                } else {
                    if (mActionMode != null) {
                        toggleSelection(position);
                    }
                    Toast.makeText(getContext(), "Click: " + position, Toast.LENGTH_SHORT).show();
                }
            }};

        // Firebase
        Query query = FirebaseFirestore.getInstance()
                .collection(SwimManagerContract.MAIN_COLLECTION_TEAM).document(mFirebaseTeamId)
                .collection(SwimManagerContract.COLLECTION_SWIMMER)
                .orderBy("surname");

        FirestoreRecyclerOptions<Swimmer> options = new FirestoreRecyclerOptions.Builder<Swimmer>()
                .setQuery(query, Swimmer.class)
                .build();

        // TODO: Firebase adapter
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mSwimmerAdapter = new SwimmerFirestoreRecyclerAdapter(options, context, itemClickListener);
        recyclerView.setAdapter(mSwimmerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        // TODO: Check log in
        mSwimmerAdapter.startListening();
        super.onStart();
    }

    @Override

    public void onStop() {

        // TODO: Check log in
        mSwimmerAdapter.stopListening();
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnListFragmentInteractionListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Swimmer item);
    }

    /**
     * Toggle the selection state of an item.
     *
     * If the item was the last one in the selection and is unselected, the selection is stopped.
     * Note that the selection must already be started (actionMode must not be null).
     *
     * @param position Position of the item to toggle the selection state
     */
    private void toggleSelection(int position) {
        mSwimmerAdapter.toggleSelection(position);
        int count = mSwimmerAdapter.getSelectedItemCount();

        if (count == 0) {
            mActionMode.finish();
        } else {
            mActionMode.setTitle(String.valueOf(count));
            mActionMode.invalidate();
        }
    }

    // Inner class
    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate (R.menu.swim_manager_selected_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_action_delete:
                    // TODO: actually remove items
                    Toast.makeText(getContext(),"Deleted Items", Toast.LENGTH_LONG).show();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mSwimmerAdapter.clearSelection();
            mActionMode = null;
        }
    }
}
