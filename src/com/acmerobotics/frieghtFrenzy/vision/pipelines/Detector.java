package com.acmerobotics.frieghtFrenzy.vision.pipelines;

import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public abstract class Detector {

    public Mat img;


    /***
     * Convert to binary image and morph to highlight target object.
     * @param rawImg
     */
    public abstract Mat processImg(Mat rawImg);

    /**
     * release any Mat objects to prevent memory depletion
     */
    public abstract void releaseMat();


}
