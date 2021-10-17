package com.acmerobotics.frieghtFrenzy.vision;

import android.app.Activity;
import android.util.Log;

import com.acmerobotics.frieghtFrenzy.vision.pipelines.FindBlueTape;
import com.acmerobotics.frieghtFrenzy.vision.pipelines.FindColoredObject;
import com.acmerobotics.frieghtFrenzy.vision.pipelines.FindRedTape;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;

public class FileProcessing implements OpModeManagerNotifier.Notifications {


    private FindRedTape redPipeline = new FindRedTape();
    private FindBlueTape bluePipeline = new FindBlueTape();
    private FindColoredObject greenPipeline = new FindColoredObject();

    String color;

    private File captureDirectory = AppUtil.ROBOT_DATA_DIR;

    public FileProcessing(String color){

        this.color = color;

        Activity activity = AppUtil.getInstance().getActivity();

        // register this class as a listener so we get information regarding opMode life cycle
        OpModeManagerImpl.getOpModeManagerOfActivity(activity).registerListener(this);

        // load openCV
        if (OpenCVLoader.initDebug()){
            Log.i("CV:", "openCV loaded successfully");
        }
        else{
            Log.i("CV:", "openCV loaded unSuccessfully");
        }
    }

    public void onCameraFrame(){
        // load img file
        Mat imageGreen = Imgcodecs.imread(captureDirectory + "/webcam-img.jpg").clone();
        Mat imageRed = Imgcodecs.imread(captureDirectory + "/webcam-img.jpg").clone();
        Mat imageBlue = Imgcodecs.imread(captureDirectory + "/webcam-img.jpg").clone();

        Log.i("CV:", imageGreen.size().toString());

        // convert to rgb
        Imgproc.cvtColor(imageGreen, imageGreen, Imgproc.COLOR_BGR2RGBA);
        Imgproc.cvtColor(imageRed, imageRed, Imgproc.COLOR_BGR2RGBA);
        Imgproc.cvtColor(imageBlue, imageBlue, Imgproc.COLOR_BGR2RGBA);

        if (color.equals("RED")) {
            // process frame for detector
            Mat modRed = redPipeline.processImg(imageRed);

            // save modified img
            saveModifiedImg(modRed, "rect-red");
        }
        else {
            // process frame for detector
            Mat modBlue = bluePipeline.processImg(imageBlue);

            // save modified img
            saveModifiedImg(modBlue, "rect-blue");
        }

        // process frame for detector
        Mat modGreen = greenPipeline.processImg(imageGreen);

        // save modified img
        saveModifiedImg(modGreen, "rect-green");
    }

    private void saveModifiedImg(Mat img, String title){
        // save an img in test-images folder
        Imgcodecs.imwrite(captureDirectory + "/" + title + ".jpg", img);

    }

    public ArrayList<Integer> getTapeCoordinates(){

        if (color.equals("RED")){
            return redPipeline.getCoordinates();
        }
        else{
            return bluePipeline.getCoordinates();
        }

    }

    public int getObjectCoordinate(){
        return greenPipeline.getCoordinate();
    }

    @Override
    public void onOpModePreInit(OpMode opMode) {

    }

    @Override
    public void onOpModePreStart(OpMode opMode) {

    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        greenPipeline.releaseMat();

        if (color.equals("RED")){
            redPipeline.releaseMat();
        }
        else{
            bluePipeline.releaseMat();
        }
    }
}
