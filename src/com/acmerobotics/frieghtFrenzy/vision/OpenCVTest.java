package com.acmerobotics.frieghtFrenzy.vision;

import android.app.Activity;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class OpenCVTest {


    public OpenCVTest (HardwareMap hardwareMap){
//        initVuforia(hardwareMap);
        initOpenCV();
    }

    // vuforia setup
//    private void initVuforia(HardwareMap hardwareMap){
//        // set camera view
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
//
//
//        // set up parameters
//        parameters.vuforiaLicenseKey = KEY;
//        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
////        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1"); // to use webcam
//
//        // create vuforia instance
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//    }

    private void initOpenCV(){

        Activity activity = AppUtil.getInstance().getActivity();

        BaseLoaderCallback loaderCallback = new BaseLoaderCallback(activity){
            @Override
            public void onManagerConnected(int status){
                super.onManagerConnected(status);
            }
        };


        if (!OpenCVLoader.initDebug()){
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, activity, loaderCallback);
        }
        else{
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);


    }

}
