package com.humoule.swipedragrecyclerview.callback;

public interface ItemTouchHelperAdapter {

    public void onItemMove(int fromPosition, int toPosition);

    public void onItemDismiss(int position);
}
