package com.acmerobotics.frieghtFrenzy.vision.pipelines;

import android.util.Log;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Config
public class FindRedTape extends Detector{

    public static double lowerHue = -5;
    public static double lowerSaturation = 50;
    public static double lowerValue = 100;

    public static double upperHue = 5;
    public static double upperSaturation = 255;
    public static double upperValue = 255;

    private List<MatOfPoint> contours = new ArrayList<>();
    private ArrayList<Rect> rects = new ArrayList<>();
    private Rect targetRect1 = new Rect(); // right most
    private Rect targetRect2 = new Rect(); // left most


    @Override
    public Mat processImg(Mat rawImg) {

        img = rawImg.clone(); // copy of rawImg for modification

        // establish lower and upper bounds
        Scalar lowerBound = new Scalar(lowerHue, lowerSaturation, lowerValue);
        Scalar upperBound = new Scalar(upperHue, upperSaturation, upperValue);

        // convert to hsv
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2HSV);

        // convert to binary img
        Core.inRange(img, lowerBound, upperBound, img);

        // morph img
        Imgproc.dilate(img, img, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8,8)));
        // dilating multiple times bc for some reason the pipeline will only partially detect the red tape and the contours are fragmented which
        // harms the software's ability to find the correct largest rect so if I dilate to make all the contours on the target touch each other then
        // they will be counted as one when looking for a rect
        Imgproc.dilate(img, img, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8,8)));
        Imgproc.dilate(img, img, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8,8)));


        // find contours
        Imgproc.findContours(img, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        // draw contours (only for testing)
//        Imgproc.drawContours(rawImg, contours, -1, new Scalar(255, 0, 0), 3);

        // fit rectangles around contours
        for (MatOfPoint contour: contours){
            Rect rect = Imgproc.boundingRect(contour);
            rects.add(rect);
            Imgproc.rectangle(rawImg, rect, new Scalar(0, 255, 0));
        }

        Log.i("CV:", rects.toString());

        // find target objects
        for (Rect rect: rects){
            Log.i("CV:", "rect: " + rect.area());

            if (rect.area() > targetRect1.area()){
                // must be new rect1
                targetRect2 = targetRect1;
                targetRect1 = rect;
            }

            else if (rect.area() > targetRect2.area()){
                // must be new rect2
                targetRect2 = rect;
            }
        }

        Log.i("CV:", "target1: " + targetRect1.area());
        Log.i("CV:", "target2: " + targetRect2.area());


        return rawImg;
    }

    public ArrayList<Integer> getCoordinates(){
        ArrayList<Integer> xCordOfBoth = new ArrayList<>(2);
        xCordOfBoth.add(targetRect2.x); // left most
        xCordOfBoth.add(targetRect1.x); // right most

        return  xCordOfBoth;
    }


    @Override
    public void releaseMat() {
        img.release();
    }
}
