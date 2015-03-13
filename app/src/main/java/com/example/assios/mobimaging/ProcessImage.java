package com.example.assios.mobimaging;

import android.util.Log;

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
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * Created by assios on 3/11/15.
 */
public class ProcessImage {

    public static int[][] zeros (Size size) {
        int width = (int) size.width;
        int height = (int) size.height;

        int[][] output = new int[width][height];

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                output[i][j] = 0;
            }

        return output;
    }

    public static float[][] f (float[][] gray, )

    public static Mat image(Mat input) {

        Mat blur = new Mat();
        Mat gray = new Mat();
        Mat close = new Mat();
        int[][] mask;

        int tall = 10;

        //Image preprocessing
        Imgproc.GaussianBlur(input, blur, new Size(5, 5), 100);

        Imgproc.cvtColor(blur, gray, Imgproc.COLOR_RGB2GRAY);

        mask = zeros(gray.size());

        System.out.println("HALLA");

        Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(11, 11));

        Imgproc.morphologyEx(gray, close, Imgproc.MORPH_CLOSE, kernel1);

        Log.i("yo", "Graysize: " + gray.size());

        return input;

    }

}
