package com.example.assios.mobimaging;

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
import org.opencv.imgproc.Imgproc;

/**
 * Created by assios on 3/11/15.
 */
public class ProcessImage {

    public int tall = 0;

    public static Mat image(Mat input, int state) {

        Imgproc.Canny(input, input, 100, 100);

        if (state==1)
            return input;

        Imgproc.blur(input, input, new Size(20, 0));

        return input;
    }

}
