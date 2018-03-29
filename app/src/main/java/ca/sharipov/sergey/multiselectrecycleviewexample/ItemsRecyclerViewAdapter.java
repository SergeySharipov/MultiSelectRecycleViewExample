package ca.sharipov.sergey.multiselectrecycleviewexample;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.sharipov.sergey.multiselectrecycleviewexample.data.Item;

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder>
        implements MultiSelectMode<String> {

    private final OnListInteractionListener mListener;

    private final List<Item> mItems;

    private Set<String> mSelectedItemsId;

    ItemsRecyclerViewAdapter(ArrayList<Item> items, OnListInteractionListener listener) {
        mItems = items;
        mListener = listener;
        mSelectedItemsId = new HashSet<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Item item = mItems.get(position);
        boolean selected = mSelectedItemsId.contains(item.getId());

        holder.bind(selected, item);
    }

    @Override
    public Set<String> getSelectedItemsId() {
        return mSelectedItemsId;
    }

    @Override
    public void setSelectedItemsId(Set<String> mSelectedItemsId) {
        this.mSelectedItemsId.clear();
        this.mSelectedItemsId.addAll(mSelectedItemsId);
        notifyDataSetChanged();
    }

    @Override
    public boolean isSomethingSelected() {
        return mSelectedItemsId.size() > 0;
    }

    @Override
    public int getSelectedItemsCount() {
        return mSelectedItemsId.size();
    }

    @Override
    public void addSelectedItemId(String selectedItemId) {
        mSelectedItemsId.add(selectedItemId);
    }

    @Override
    public void removeSelectedItemId(String selectedItemId) {
        mSelectedItemsId.remove(selectedItemId);
    }

    @Override
    public void removeAllSelected() {
        mSelectedItemsId.clear();
        notifyDataSetChanged();
    }

    @Override
    public void selectAll() {
        for (Item item : mItems) {
            mSelectedItemsId.add(item.getId());
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean isAllSelected() {
        return mItems.size() == getSelectedItemsCount();
    }

    public interface OnListInteractionListener {

        void onItemClick(Item item);

        void onItemLongClick();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView mView;
        private final ImageView checkCircleImageView;
        private final TextView mIdView;
        private final TextView mContentView;
        private Item mItem;
        private boolean mSelected;

        ViewHolder(View view) {
            super(view);
            mView = view.findViewById(R.id.card_view);
            checkCircleImageView = view.findViewById(R.id.iv_check_circle);
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
        }

        void bind(boolean selected, final Item item) {
            mItem = item;

            mSelected = selected;
            selectView();

            mIdView.setText(mItem.getTitle());
            mContentView.setText(mItem.getDate().toString());

            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSomethingSelected()) {
                        selectView();
                    }
                    mListener.onItemClick(mItem);
                }
            });
            mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectView();
                    mListener.onItemLongClick();
                    return true;
                }
            });
        }

        private void selectView() {
            mSelected = !mSelected;
            if (mSelected) {
                checkCircleImageView.setVisibility(View.GONE);
                mView.setCardBackgroundColor(Color.WHITE);

                removeSelectedItemId(mItem.getId());
            } else {
                checkCircleImageView.setVisibility(View.VISIBLE);
                mView.setCardBackgroundColor(Color.LTGRAY);

                addSelectedItemId(mItem.getId());
            }
        }
    }
}
