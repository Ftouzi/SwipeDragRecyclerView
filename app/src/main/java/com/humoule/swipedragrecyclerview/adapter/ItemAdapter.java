package com.humoule.swipedragrecyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.humoule.swipedragrecyclerview.R;
import com.humoule.swipedragrecyclerview.callback.ItemTouchHelperAdapter;
import com.humoule.swipedragrecyclerview.callback.ItemTouchHelperViewHolder;
import com.humoule.swipedragrecyclerview.callback.OnStartDragListener;
import com.humoule.swipedragrecyclerview.model.Item;
import com.humoule.swipedragrecyclerview.utils.Typefaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {

    private final Context mContext;
    public static List<Item> itemList = new ArrayList<>();
    private TextView mNumberTextView;

    private final OnStartDragListener mDragStartListener;

    public ItemAdapter(Context context, OnStartDragListener dragStartListener, TextView tvNumber) {
        mContext = context;
        mDragStartListener = dragStartListener;
        mNumberTextView = tvNumber;

    }

    @Override
    public void onItemDismiss(final int position) {

        final Item item = new Item();
        item.setItemName(itemList.get(position).getItemName());

        notifyItemRemoved(position);
        itemList.remove(position);
        notifyItemRangeChanged(0, getItemCount());
        mNumberTextView.setText(String.valueOf(itemList.size()));

        final Snackbar snackbar = Snackbar

                .make(mNumberTextView, mContext.getResources().getString(R.string.item_deleted), Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(mContext, R.color.white))
                .setAction(mContext.getResources().getString(R.string.item_undo), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemList.add(position, item);
                        notifyItemInserted(position);
                        mNumberTextView.setText(String.valueOf(itemList.size()));

                    }
                });


        View snackBarView = snackbar.getView();
        TextView tvSnack = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        TextView tvSnackAction = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_action);
        tvSnack.setTextColor(Color.WHITE);
        tvSnack.setTypeface(Typefaces.getRobotoMedium(mContext));
        tvSnackAction.setTypeface(Typefaces.getRobotoMedium(mContext));
        snackbar.show();

        Runnable runnableUndo = new Runnable() {

            @Override
            public void run() {
                mNumberTextView.setText(String.valueOf(itemList.size()));
                snackbar.dismiss();
            }
        };

        Handler handlerUndo = new Handler();
        handlerUndo.postDelayed(runnableUndo, 2500);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);

    }

    public void addItem(int position, Item item) {

        itemList.add(position, item);
        notifyItemInserted(position);
        mNumberTextView.setText(String.valueOf(itemList.size()));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, final int position) {

        final Item item = itemList.get(position);
        itemViewHolder.tvItemName.setText(item.getItemName());
        itemViewHolder.relativeReorder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) ==
                        MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(itemViewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grocery_adapter, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        protected RelativeLayout container;
        protected TextView tvItemName;
        protected ImageView ivReorder;
        protected RelativeLayout relativeReorder;

        public ItemViewHolder(final View v) {
            super(v);

            container = (RelativeLayout) v.findViewById(R.id.container);
            tvItemName = (TextView) v.findViewById(R.id.tvItemName);
            ivReorder = (ImageView) v.findViewById(R.id.ivReorder);
            relativeReorder = (RelativeLayout) v.findViewById(R.id.relativeReorder);
        }

        @Override
        public void onItemSelected(Context context) {

            container.setBackgroundColor(ContextCompat.getColor(context, R.color.bg));
            tvItemName.setTextColor(ContextCompat.getColor(context, R.color.white));
            ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onItemClear(Context context) {

            container.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            ivReorder.setColorFilter(ContextCompat.getColor(context, R.color.textlight), PorterDuff.Mode.SRC_IN);
            tvItemName.setTextColor(ContextCompat.getColor(context, R.color.textlight));
        }

    }

}
