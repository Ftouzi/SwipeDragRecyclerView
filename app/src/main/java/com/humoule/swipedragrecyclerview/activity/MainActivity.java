package com.humoule.swipedragrecyclerview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.humoule.swipedragrecyclerview.R;
import com.humoule.swipedragrecyclerview.adapter.ItemAdapter;
import com.humoule.swipedragrecyclerview.callback.OnStartDragListener;
import com.humoule.swipedragrecyclerview.model.Item;
import com.humoule.swipedragrecyclerview.utils.SimpleItemTouchHelperCallback;
import com.humoule.swipedragrecyclerview.utils.Utils;

public class MainActivity extends AppCompatActivity implements OnStartDragListener {

    private ItemTouchHelper mItemTouchHelper;
    private int mNumberItems = 0;
    private TextView mNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mNumberTextView = (TextView) findViewById(R.id.tvNumber);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        final ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(), this, mNumberTextView);
        recyclerView.setAdapter(itemAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter, this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        loadItems();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        assert floatingActionButton != null;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Item item = new Item();
                mNumberItems = ItemAdapter.itemList.size();
                mNumberItems++;
                item.setItemName("item" + mNumberItems);
                linearLayoutManager.scrollToPositionWithOffset(0, Utils.dpToPx(56));
                itemAdapter.addItem(0, item);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        ItemAdapter.itemList.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent intAbout = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);

    }


    /**
     * Initial items
     */
    private void loadItems() {

        for (int i = 10; i > 0; i--) {
            Item item = new Item();
            item.setItemName("item" + i);
            ItemAdapter.itemList.add(item);
        }

        mNumberTextView.setText(String.valueOf(ItemAdapter.itemList.size()));
    }

}
