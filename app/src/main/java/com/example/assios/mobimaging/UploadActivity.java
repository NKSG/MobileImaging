package com.example.assios.mobimaging;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class UploadActivity extends ActionBarActivity {

    private ImageView appImageView;
    private TextView moveView;
    private Drawable drawable;
    private Random random;
    private Drawable[] drawables = null; // create a Drawables array that stores location of different images
    private FEN fenString = new FEN();
    ImageView picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        // Allow network access in the main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        appImageView = (ImageView) findViewById(R.id.imageview);

        moveView = (TextView)findViewById(R.id.textv);

    }

    public void backToMenu(View v) {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    public void selectColor(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        if (v.getId() == R.id.white)
            fenString.turn = 'W';
        else
            fenString.turn = 'B';

    }

    HashMap < Integer, Integer > map = new HashMap < Integer, Integer > () {
        {
            put(1, 32);
            put(3, 31);
            put(5, 30);
            put(7, 29);
            put(8, 28);
            put(10, 27);
            put(12, 26);
            put(14, 25);
            put(17, 24);
            put(19, 23);
            put(21, 22);
            put(23, 21);
            put(24, 20);
            put(26, 19);
            put(28, 18);
            put(30, 17);
            put(33, 16);
            put(35, 15);
            put(37, 14);
            put(39, 13);
            put(40, 12);
            put(42, 11);
            put(44, 10);
            put(46, 9);
            put(49, 8);
            put(51, 7);
            put(53, 6);
            put(55, 5);
            put(56, 4);
            put(58, 3);
            put(60, 2);
            put(62, 1);
        }
    };


    public void ChoosePicture(View v) {
        Mat m;
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            m = new Mat();
            Bitmap bmap = b.getParcelable("picture");
            Bitmap bmp32 = bmap.copy(Bitmap.Config.ARGB_8888, true);

            m = new Mat(bmap.getWidth(), bmap.getHeight(), CvType.CV_8UC1);
            Utils.bitmapToMat(bmp32, m);

            Imgproc.cvtColor(m, m, Imgproc.COLOR_RGB2BGR);

        } else {
            m = new Mat();
            try {
                m = Utils.loadResource(this, R.drawable.picpic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //appImageView.setImageDrawable(drawables[0]); // set the image to the ImageView
        //Get Mat file of chessboard.jpg:

        Imgproc.GaussianBlur(m, m, new Size(5, 5), 2);
        List<Mat> mlist = ProcessImage.cut(m, 8);

        for (int key : map.keySet()) {
            Mat mat = mlist.get(key);

            //boolean circleBool = ProcessImage.findCircle(m);
            //Log.d("Circle in square: ", ("" + circleBool));
            double[] c = ProcessImage.findColor(mat);

            //Log.d("FELT: ", key + "");
            //Log.d("COLOR ", "R:"+c[0]+", G:"+c[1]+", B:"+c[2]);

            String color = ProcessImage.minColorDistance(c);

            if (color == "P1") fenString.black.add(map.get(key));
            else if (color == "P2") fenString.white.add(map.get(key));
        }

        Bitmap bm = Bitmap.createBitmap(m.cols(), m.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(m, bm);

        String fen = fenString.toString();

        String fen_url = "http://assios.no:8000/?fen=";
        fen_url = fen_url + fen;

        Log.d("FEN URL: ", fen_url);

        fenString.clear();

        String test_fen = "http://assios.no:8000/?fen=FEN%22B:W18,19,21,23,24,26,29,30,31,32:B1,2,3,4,6,7,9,10,11,12%22";

        String move = URLFetch.url(fen_url);

        moveView.setText(move);
        moveView.setTextSize(20);
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