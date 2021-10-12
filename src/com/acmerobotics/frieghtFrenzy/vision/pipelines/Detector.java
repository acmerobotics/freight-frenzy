package com.acmerobotics.frieghtFrenzy.vision.pipelines;

import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

public abstract class Detector {

    public Mat img;

    public Rect boundingRect;


    /***
     * Convert to binary image and morph to highlight target object.
     * @param rawImg
     */
    public abstract Mat processImg(Mat rawImg);

    /***
     * Find contours of object and bound a rectangle to the region of interest.
     * Instanciate boundingRect to use for later
     */
    public abstract void findObject();

    /**
     * release any Mat objects to prevent memory depletion
     */
    public abstract void releaseMat();

    public Rect getBoundingRect(){
        return boundingRect;
    }

}
