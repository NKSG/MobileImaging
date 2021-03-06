package com.example.assios.mobimaging;

import android.util.Log;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.GaussianBlur;
import static org.opencv.imgproc.Imgproc.cvtColor;

/**
 * Created by assios on 3/11/15.
 */
public class ProcessImage {

    public static boolean findCircle (Mat input) {
        Mat src_gray = new Mat();
        Mat src = input;
        cvtColor(src, src_gray, COLOR_BGR2GRAY);
        Imgproc.Canny(src_gray, src_gray, 32, 2);
        GaussianBlur(src_gray, src_gray, new Size(9, 9), 2, 2);
        Mat circles = new Mat();
        Log.d("Columns BEFORE: ", (""+circles.rows()));

//        double iCannyUpperThreshold = 100;
//        int iMinRadius = 20;
//        int iMaxRadius = 400;
//        double iAccumulator = 300;
//
//        Imgproc.HoughCircles(src_gray, circles, Imgproc.CV_HOUGH_GRADIENT,
//                2.0, src_gray.rows() / 8, iCannyUpperThreshold, iAccumulator,
//                iMinRadius, iMaxRadius);

        Imgproc.HoughCircles(src_gray, circles, Imgproc.CV_HOUGH_GRADIENT,
                1, (double) src_gray.width()/8, 50, 45, src_gray.width()/2, src_gray.width()/8);
        Log.d("Columns of circles: ", (""+circles.rows()));

        if (circles.cols() > 0) {
            return true;
        }
        else return false;
    }

    public static String minColorDistance(double[] color) {
        double[] player1 = {86, 83, 114};
        double[] board = {171, 41, 54};
        double[] player2 = {255, 255, 255};

        int distanceP1 = (int) Math.sqrt(Math.pow(Math.abs(color[0]-player1[0]), 2) + Math.pow(Math.abs(color[1]-player1[1]), 2) + Math.pow(Math.abs(color[2]-player1[2]), 2));
        int distanceBoard = (int) Math.sqrt(Math.pow(Math.abs(color[0]-board[0]), 2) + Math.pow(Math.abs(color[1]-board[1]), 2) + Math.pow(Math.abs(color[2]-board[2]), 2));
        int distanceP2 = (int) Math.sqrt(Math.pow(Math.abs(color[0]-player2[0]), 2) + Math.pow(Math.abs(color[1]-player2[1]), 2) + Math.pow(Math.abs(color[2]-player2[2]), 2));

        int min = Math.min(distanceP1, Math.min(distanceBoard, distanceP2));

        Log.d("DISTANCE: ", distanceP1 + " " + distanceBoard + " " + distanceP2);

        //if (min>100)
        //    return "EMPTY " + min;

        if (min == distanceBoard)
            return "BOARD";
        else if (min == distanceP1)
            return "P1";
        else if (min== distanceP2)
            return "P2";

        return "NO";
    }

    public static double[] findColor(Mat input) {
        /**
         * Get the 10% innermost pixels
         * and find out if the average color
         * is WHITE, BLACK or RED
         */

        Size inputsize = input.size();

        double subSquareWidth = inputsize.width/10;

        Size patch = new Size(subSquareWidth, subSquareWidth);

        Point center = new Point(inputsize.width/2, inputsize.height/2);

        Mat subSquare = new Mat();

        Imgproc.getRectSubPix(input, patch, center, subSquare);

        int middle = (int) subSquare.size().width/2;

        //                      B  G  R
        double[] totalColors = {0, 0, 0};
        long sum = subSquare.total();

        for (int i = 0; i < subSquare.cols(); i++) {
            for (int j = 0; j < subSquare.rows(); j++) {
                double[] colors = subSquare.get(i, j);
                for (int k = 0; k < colors.length; k++) {
                    totalColors[k] += colors[k];
                }
            }
        }

        double resultRed = totalColors[0]/sum;
        double resultGreen = totalColors[1]/sum;
        double resultBlue = totalColors[2]/sum;

        double[] result = {resultBlue, resultGreen, resultRed};

        return result;
    }

    public static List<Mat> cut(Mat input, int n_squares) {

        /**
         * It takes a square chessboard
         * and returns a list of pictures
         * of all the squares on the board
         */

        List<Mat> list = new ArrayList<>();

        Size inputsize = input.size();
        double width = inputsize.width;
        double square_width = width/n_squares;

        Size patch = new Size(square_width, square_width);

        Point center = new Point();

        for (int i = 0; i < n_squares; i++) {
            for (int j = 0; j < n_squares; j++) {
                center.y = (square_width/2)+square_width*i;
                center.x = (square_width/2)+square_width*j;

                Mat new_square = new Mat();
                Imgproc.getRectSubPix(input, patch, center, new_square);
                list.add(new_square);
            }
        }

        return list;
    }

    public static Mat image2(Mat input) {
        // STEP 0 Retrieving mask
        Mat blur = new MatOfFloat();
        Mat mask = new MatOfFloat();
        Mat gray = new MatOfFloat();
        Mat close = new MatOfFloat();
        GaussianBlur(input, blur, new Size(5, 5), 0);
        cvtColor(blur, gray, Imgproc.COLOR_RGB2GRAY);

        mask = MatOfFloat.zeros(gray.size(), gray.type());

        // STEP 1 Image preprocessing:
        Mat res = new Mat();
        GaussianBlur(input, res, new Size(5, 5), 0);
        Imgproc.Canny(res, res, 100, 100);

        Mat thresh = new Mat();
        Imgproc.adaptiveThreshold(res, thresh, 255, 0, 1, 19, 2);

        //STEP 2

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.adaptiveThreshold(res, thresh, 255, 0, 1, 19, 2);
        Imgproc.findContours(thresh, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat contour = contours.get(0);
        Mat hier = contours.get(1);


        double max_area = 0;

        Mat best_cnt = new Mat();

        for (int i = 0; i < contours.size(); i++) {
            double area = Imgproc.contourArea(contours.get(i));
            if (area>1000) {
                if (area>max_area) {
                    max_area = area;
                    best_cnt = contours.get(i);
                }
            }
        }


        List<MatOfPoint> best = new ArrayList<MatOfPoint>();

        best.add((MatOfPoint) best_cnt);
        List<MatOfPoint> bestlist = new ArrayList<MatOfPoint>();
        bestlist.add((MatOfPoint) best_cnt);

        Scalar clr = new Scalar(255,255,255);
        Scalar clr2 = new Scalar(0,0,0);
        Imgproc.drawContours(mask, bestlist, 0, clr, -1);
        Imgproc.drawContours(mask, bestlist, 0, clr2, 2);

        /*
        cv2.drawContours(mask,[best_cnt],0,255,-1)
        cv2.drawContours(mask,[best_cnt],0,0,2)

        res = cv2.bitwise_and(res,mask)
        */

        Size patternsize = new Size(8,8);

        MatOfPoint2f corners = new MatOfPoint2f();

        boolean patternfound = Calib3d.findChessboardCorners(res, patternsize, corners, Calib3d.CALIB_CB_ADAPTIVE_THRESH + Calib3d.CALIB_CB_NORMALIZE_IMAGE + Calib3d.CALIB_CB_FAST_CHECK);

        Calib3d.drawChessboardCorners(res, patternsize, corners, patternfound);

        return res;
    }

    public static Mat image(Mat input) {

        Mat blur = new MatOfFloat();
        Mat mask = new MatOfFloat();
        Mat gray = new MatOfFloat();
        Mat close = new MatOfFloat();

        //Image preprocessing
        GaussianBlur(input, blur, new Size(5, 5), 0);
        cvtColor(blur, gray, Imgproc.COLOR_RGB2GRAY);

//        mask = zeros(gray.size());

        mask = MatOfFloat.zeros(gray.size(), gray.type());

        Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(11.0, 11.0));
        Imgproc.morphologyEx(gray, close, Imgproc.MORPH_CLOSE, kernel1);

        Mat div = new MatOfFloat();
        Core.divide(gray, close, div);

        Mat res = new MatOfFloat();
        Core.normalize(div, res, 0, 255, Core.NORM_MINMAX);

        Mat res2 = new MatOfFloat();
        cvtColor(res, res2, Imgproc.COLOR_GRAY2BGR);

        return res2;

    }

}
