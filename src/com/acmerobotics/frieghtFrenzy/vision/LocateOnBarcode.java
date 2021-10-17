package com.acmerobotics.frieghtFrenzy.vision;

import com.acmerobotics.frieghtFrenzy.vision.pipelines.Detector;

import org.opencv.core.Mat;

public class LocateOnBarcode {

    private FileProcessing fileProcessing;

    public enum Locations{
        LEFT,
        MIDDLE,
        RIGHT
    }

    public LocateOnBarcode(String color){
        fileProcessing = new FileProcessing(color);
    }

    public void takePicture(){

    }

    public void processFrames(){
        fileProcessing.onCameraFrame();
    }

    public Locations LocateShippingElement(){
        // get location from rect data from green obj Detector and red/blue tap Detector

        int objX = fileProcessing.getObjectCoordinate();
        int tape1X = fileProcessing.getTapeCoordinates().get(0);
        int tape2x = fileProcessing.getTapeCoordinates().get(1);

        if ((tape1X < objX) & (tape2x < objX)){
            return Locations.RIGHT;
        }

        if ((tape1X > objX) & (tape2x > objX)){
            return Locations.LEFT;
        }

        else { // conditional if I had to place it (((tape1X < objX) & (tape2x > objX)) || ((tape1X > objX) & (tape2x < objX)))
            return Locations.MIDDLE;
        }

    }

}
