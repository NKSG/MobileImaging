package com.example.assios.mobimaging;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.OpenCVLoader;


public class MenuActivity extends ActionBarActivity {

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);

    }

    public void buttonOnClick(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void buttonOnClick2(View v) {
        startActivity(new Intent(getApplicationContext(), UploadActivity.class));
    }

    public void buttonOnClick3(View v) {
        startActivity(new Intent(getApplicationContext(), ShootAndCropActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
