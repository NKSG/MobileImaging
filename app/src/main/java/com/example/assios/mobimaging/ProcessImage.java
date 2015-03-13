package com.example.assios.mobimaging;

import android.util.Log;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
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

    public static float[][] f (float[][] gray) {
       return null;
    }

//    public Mat toFloat(Mat board) {
//        for (int i=0; i<board.height(); i++ ) {
//            for (int j=0; j<board.width(); j++) {
//                for (Double d :board.get(i,j)) {
//                    float temp;
//                }
//                board.put(i,j, board.get(i,j));
//            }
//        }
//    }

    public static Mat image(Mat input) {

        Mat blur = new MatOfFloat();
        Mat mask = new MatOfFloat();

        Mat gray = new MatOfFloat();
        Mat close = new MatOfFloat();

        //Image preprocessing
        Imgproc.GaussianBlur(input, blur, new Size(5.0, 5.0), 0);

        blur.convertTo(blur, CvType.CV_32F);

        Imgproc.cvtColor(blur, gray, Imgproc.COLOR_RGB2GRAY);

//        mask = zeros(gray.size());

        mask = Mat.zeros(gray.size(), CvType.CV_32F);

        Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(11.0, 11.0));
        Imgproc.morphologyEx(gray, close, Imgproc.MORPH_CLOSE, kernel1);



        Mat div = new MatOfFloat();
        Core.divide(gray, close, div);



        String dump = gray.dump();
        Log.d("Here comes the string: ", dump);

        Mat res = new MatOfFloat();
        Core.normalize(div, res, 0, 255, Core.NORM_MINMAX);

        Mat res2 = new MatOfFloat();
        Imgproc.cvtColor(res, res2, Imgproc.COLOR_GRAY2BGR);

        return res2;

    }

}
