package com.example.assios.mobimaging;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import java.util.Arrays;
import java.util.List;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.core.MatOfPoint2f;

import static org.opencv.calib3d.Calib3d.CALIB_CB_ADAPTIVE_THRESH;
import static org.opencv.calib3d.Calib3d.findChessboardCorners;


public class MainActivity extends ActionBarActivity implements CvCameraViewListener2 {

    public int count = 0;

    private CameraBridgeViewBase mOpenCvCameraView;

    private final static String TAG = "OCV::MainActivity";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch(status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    break;
                }
                default:
                {
                    super.onManagerConnected(status);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOpenCvCameraView = (JavaCameraView) findViewById(R.id.MainActivityCameraView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this, mLoaderCallback);

    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null) {
            mOpenCvCameraView.disableView();
        }
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

    //CvCameraViewListener2 stuff
    @Override
    public void onCameraViewStarted(int width, int height) {
        // TODO stuff
    }

    @Override
    public void onCameraViewStopped() {
        // TODO stuff
    }

    //board
    int board_width = 8;
    int board_height = 8;

    //DENNE DEKLARERES MULIGENS FEIL

    public MatOfPoint2f points;

    public Size boardSize = new Size(board_width, board_height);

    public boolean found = false;

    @Override
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        Mat img = inputFrame.rgba();

        //FEILMELDINGER PÅ DEN UNDER FORELØPIG
        //boolean found = findChessboardCorners( img, boardSize, points, 15 );

        return img;
    }
}
