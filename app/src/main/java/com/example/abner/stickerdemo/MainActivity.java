package com.example.abner.stickerdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.abner.stickerdemo.utils.FileUtils;
import com.example.abner.stickerdemo.view.StickerManager;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mContentRootView;

    private FloatingActionsMenu mMultipleActions;

    private View mAddSticker;

    private View mAddBubble;

    private StickerManager mStickerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentRootView = (RelativeLayout) findViewById(R.id.rl_content_root);
        mStickerManager = new StickerManager(mContentRootView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addStickerView();
//            }
//        });
        mMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        mAddSticker = findViewById(R.id.action_add_sticker);
        mAddBubble = findViewById(R.id.action_add_bubble);
        mAddSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStickerManager.addSticker();
                mMultipleActions.collapse();
            }
        });
        mAddBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStickerManager.addBubble();
                mMultipleActions.collapse();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_complete) {
            mStickerManager.commit();
            generateBitmap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void generateBitmap() {

        Bitmap bitmap = Bitmap.createBitmap(mContentRootView.getWidth(),
                mContentRootView.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mContentRootView.draw(canvas);

        String iamgePath = FileUtils.saveBitmapToLocal(bitmap, this);
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra("image", iamgePath);
        startActivity(intent);
    }

}
