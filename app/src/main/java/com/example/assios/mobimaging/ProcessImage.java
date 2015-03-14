package com.example.assios.mobimaging;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.MatOfPoint2f;
import org.opencv.engine.OpenCVEngineInterface;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import java.util.regex.Matcher;

import static org.opencv.imgproc.Imgproc.floodFill;

/**
 * Created by assios on 3/11/15.
 */
public class ProcessImage {

    public static Mat image(Mat input) {

        Mat board = input.clone();
        Imgproc.cvtColor(input, board, Imgproc.COLOR_RGB2GRAY);

        Mat outerBox = new Mat(board.size(), CvType.CV_8UC1);
        Imgproc.GaussianBlur(board,board,new Size(11,11), 0);
        Imgproc.adaptiveThreshold(board, outerBox, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 5, 2);

        Imgproc.Canny(board, board, 100, 100);

        Core.bitwise_not(outerBox, outerBox);
//        Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(11, 11));
//        Mat kernel1 = new Mat(8,8, CvType.CV_8U);

        Mat kernel1 = new Mat(3,3,CvType.CV_8U);
        kernel1.put(0, 0, (byte) 0);
        kernel1.put(0, 1, (byte) 1);
        kernel1.put(0, 2, (byte) 0);
        kernel1.put(1, 0, (byte) 1);
        kernel1.put(1, 1, (byte) 1);
        kernel1.put(1, 2, (byte) 1);
        kernel1.put(2, 0, (byte) 0);
        kernel1.put(2, 1, (byte) 1);
        kernel1.put(2, 2, (byte) 0);
        Imgproc.dilate(outerBox, outerBox, kernel1);

        int count=0, max = -1;
        Point maxPt = new Point();
        Mat outerBoxMask = new Mat();



        for (int y=0; y<outerBox.size().height; y++) {
            for (int x=0; x < outerBox.size().width; x++){
                if (outerBox.get(x,y)[x] >= 128){
                    int area = Imgproc.floodFill(outerBox, outerBoxMask, new Point(x, y), new Scalar(0, 0, 64));
                    if(area>max)
                    {
                        maxPt = new Point(x,y);
                        max = area;
                    }
                }
            }
        }
        floodFill(outerBox, outerBoxMask, maxPt, new Scalar(255, 255, 255));

        for(int y=0; y<outerBox.size().height; y++)
        {
            for(int x=0;x<outerBox.size().width;x++)
            {
                if(outerBox.get(x,y)[x]==64 && x!=maxPt.x && y!=maxPt.y)
                {
                    int area = floodFill(outerBox, outerBoxMask, new Point(x,y), new Scalar(0,0,0));
                }
            }
        }

        return board;

    }

}
