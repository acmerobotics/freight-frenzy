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

public class FileProcessing implements OpModeManagerNotifier.Notifications {


    private FindRedTape redPipeline = new FindRedTape();
    private FindBlueTape bluePipeline = new FindBlueTape();
    private FindColoredObject greenPipeline = new FindColoredObject();

    private String captureDirectory = AppUtil.FIRST_FOLDER + "/visionTest";

    public FileProcessing(){

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
        Mat imageGreen = Imgcodecs.imread(captureDirectory + "/regular.jpg");

        Log.i("CV:", imageGreen.size().toString());

        // convert to rgb
        Imgproc.cvtColor(imageGreen, imageGreen, Imgproc.COLOR_BGR2RGBA);

        // process frame for each detector here
        Mat testImage = greenPipeline.processImg(imageGreen);

        // save modified img
        saveModifiedImg(testImage);
    }

    private void saveModifiedImg(Mat img){
        // save an img in test-images folder
        Imgcodecs.imwrite(captureDirectory + "/test-img.jpg", img);

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
    }
}
