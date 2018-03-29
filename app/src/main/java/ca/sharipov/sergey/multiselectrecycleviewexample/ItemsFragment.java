package ca.sharipov.sergey.multiselectrecycleviewexample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;

import ca.sharipov.sergey.multiselectrecycleviewexample.data.DataGenerator;
import ca.sharipov.sergey.multiselectrecycleviewexample.data.Item;

public class ItemsFragment extends Fragment implements ItemsRecyclerViewAdapter.OnListInteractionListener {

    private static final String SELECTED_ITEMS = "ca.sharipov.sergey.actionmodeexample.SELECTED_ITEMS";

    private ActionMode mActionMode;
    private ItemsRecyclerViewAdapter mItemsRecyclerViewAdapter;

    private ActionMode.Callback mCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_items_fragment_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_select_all:
                    if (!mItemsRecyclerViewAdapter.isAllSelected()) {
                        mItemsRecyclerViewAdapter.selectAll();
                        onItemSelect();
                    } else
                        mActionMode.finish();

                    return false;
                case R.id.action_share:
                    View view = getView();
                    if (view != null) {
                        Snackbar.make(view, "Shared: " + mItemsRecyclerViewAdapter.getSelectedItemsCount(),
                                Snackbar.LENGTH_SHORT).show();
                    }
                    mActionMode.finish();
                    return true;
                case R.id.action_delete:
                    View view2 = getView();
                    if (view2 != null) {
                        Snackbar mySnackbar = Snackbar.make(getView(),
                                "Deleted: " + mItemsRecyclerViewAdapter.getSelectedItemsCount(), Snackbar.LENGTH_LONG);
                        mySnackbar.setAction("Undo", new DeleteUndoListener());
                        mySnackbar.show();
                    }
                    mActionMode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mItemsRecyclerViewAdapter.removeAllSelected();
            mActionMode = null;
        }
    };

    public ItemsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items_list, container, false);

        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        mItemsRecyclerViewAdapter =
                new ItemsRecyclerViewAdapter(DataGenerator.generateItems(30), this);
        recyclerView.setAdapter(mItemsRecyclerViewAdapter);

        if (savedInstanceState != null) {
            ArrayList<String> selectedItemsId = savedInstanceState.getStringArrayList(SELECTED_ITEMS);
            if (selectedItemsId != null) {
                mItemsRecyclerViewAdapter.setSelectedItemsId(new HashSet<>(selectedItemsId));
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onItemSelect();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SELECTED_ITEMS, new ArrayList<>(mItemsRecyclerViewAdapter.getSelectedItemsId()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_items_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_all:
                mItemsRecyclerViewAdapter.selectAll();
                onItemSelect();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Item item) {
        onItemSelect();
    }

    @Override
    public void onItemLongClick() {
        onItemSelect();
    }

    void onItemSelect() {
        if (mItemsRecyclerViewAdapter.isSomethingSelected()) {
            if (mActionMode == null) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                    mActionMode = activity.startSupportActionMode(mCallback);
                }
            }
            if (mActionMode != null)
                mActionMode.setTitle(String.valueOf(mItemsRecyclerViewAdapter.getSelectedItemsCount()));

        } else if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    public class DeleteUndoListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Code to undo the user's last action
        }
    }
}
