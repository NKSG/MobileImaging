package com.example.assios.mobimaging;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.opencv.android.OpenCVLoader;

public class UploadActivity extends ActionBarActivity {

    private ImageView appImageView;
    private Drawable drawable;
    private Random random;
    private Drawable [] drawables = null; // create a Drawables array that stores location of different images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        appImageView = (ImageView) findViewById(R.id.imageview);

        drawables = new Drawable[] {
                getResources().getDrawable(R.drawable.chessboard)
        };
    }

    public void backToMenu(View v) {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    public void ChoosePicture(View v) {
        //appImageView.setImageDrawable(drawables[0]); // set the image to the ImageView

        //Get Mat file of chessboard.jpg:
        Mat m = new Mat();
        try {
            m = Utils.loadResource(this, R.drawable.sudoku);
        } catch (IOException e) {
            e.printStackTrace();
        }

        m = ProcessImage.image(m);

        // convert to bitmap:
        Bitmap bm = Bitmap.createBitmap(m.cols(), m.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(m, bm);

        // find the imageview and draw it!
        appImageView.setImageBitmap(bm);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload, menu);
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
