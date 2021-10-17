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
import java.util.List;

@Config
public class FindColoredObject extends Detector{

    // hue estimate for green
    public static double lowerHue = 30;
    public static double lowerSaturation = 75; // 100
    public static double lowerValue = 100;

    public static double upperHue = 75;
    public static double upperSaturation = 255;
    public static double upperValue = 255;

    private List<MatOfPoint> contours = new ArrayList<>();
    private ArrayList<Rect> rects = new ArrayList<>();
    private Rect targetRect = new Rect();


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

        // find contours
        Imgproc.findContours(img, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        // draw contours (only for testing)
//        Imgproc.drawContours(rawImg, contours, -1, new Scalar(255, 0, 0), 3);

        // fit rectangles around contours
        for (MatOfPoint contour: contours){
            Rect rect = Imgproc.boundingRect(contour);
            rects.add(rect);
            Imgproc.rectangle(rawImg, rect, new Scalar(0, 0, 255));
        }

        Log.i("CV:", rects.toString());

        // find target object
        for (Rect rect: rects){
            if (rect.area() > targetRect.area()){
                targetRect = rect;
            }
        }

        return rawImg;
    }

    public int getCoordinate() {
        return targetRect.x;
    }

    @Override
    public void releaseMat() {
        img.release();
    }
}
