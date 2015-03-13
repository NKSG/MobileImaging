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
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * Created by assios on 3/11/15.
 */
public class ProcessImage {

    public static Mat image(Mat input) {

        int tall = 10;

        //Image preprocessing
        Imgproc.GaussianBlur(input, input, new Size(5, 5), 100);

        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2GRAY);

        // LEGG INN mask = np.zeros((gray.shape),np.uint8) HER

        Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(11, 11));

        Imgproc.morphologyEx(input, input, Imgproc.MORPH_CLOSE, kernel1);

        return input;

    }

}
