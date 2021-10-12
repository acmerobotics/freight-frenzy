package com.acmerobotics.frieghtFrenzy.vision.pipelines;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@Config
public class FindRedTape extends Detector{

    public static double lowerHue = 0;
    public static double lowerSaturation = 100;
    public static double lowerValue = 100;

    public static double upperHue = 5;
    public static double upperSaturation = 255;
    public static double upperValue = 255;

    @Override
    public Mat processImg(Mat img) {

        // crate mat obj for modified img
        Mat modifiedImg = new Mat();

        // establish lower and upper bounds
        Scalar lowerBound = new Scalar(lowerHue, lowerSaturation, lowerValue);
        Scalar upperBound = new Scalar(upperHue, upperSaturation, upperValue);

        // convert to hsv
        Imgproc.cvtColor(img, modifiedImg, Imgproc.COLOR_RGB2HSV);

        // convert to binary img
        Core.inRange(modifiedImg, lowerBound, upperBound, modifiedImg);

        return modifiedImg;

        // morph img

        // find contours

        // draw contours (only for testing)

        // find target object

        // do something with the bounding rect obj
    }

    @Override
    public void findObject() {

    }

    @Override
    public void releaseMat() {

    }
}
