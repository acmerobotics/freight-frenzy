package com.acmerobotics.frieghtFrenzy.vision;

import android.app.Activity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

import com.acmerobotics.frieghtFrenzy.vision.pipelines.Detector;
import com.acmerobotics.frieghtFrenzy.vision.pipelines.FindBlueTape;
import com.acmerobotics.frieghtFrenzy.vision.pipelines.FindColoredObject;
import com.acmerobotics.frieghtFrenzy.vision.pipelines.FindRedTape;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManagerNotifier;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeManagerImpl;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.R;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CVCamera implements CameraBridgeViewBase.CvCameraViewListener2, OpModeManagerNotifier.Notifications {

    private CameraBridgeViewBase openCvCameraView;

    private FindRedTape redPipeline = new FindRedTape();
    private FindBlueTape bluePipeline = new FindBlueTape();
    private FindColoredObject greenPipeline = new FindColoredObject();

    public CVCamera() {

        Activity activity = AppUtil.getInstance().getActivity();

        // register this class as a listener so we get information regarding opMode life cycle
        OpModeManagerImpl.getOpModeManagerOfActivity(activity).registerListener(this);


        // camera view setUp
        openCvCameraView = activity.findViewById(R.id.cameraViewId);
        openCvCameraView.setVisibility(SurfaceView.VISIBLE);
        openCvCameraView.setCvCameraViewListener(this);
        openCvCameraView.setCameraPermissionGranted();

        // load openCV
        BaseLoaderCallback loaderCallback = new BaseLoaderCallback(activity) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);
            }
        };

        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, activity, loaderCallback);
        } else {
            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            openCvCameraView.enableView();
        }

    }


    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

//        Mat imageRed = inputFrame.rgba().clone();
//        Mat imageBlue = inputFrame.rgba().clone();
        Mat imageGreen = inputFrame.rgba();

        // process frame for each detector here
//        Mat testImage = redPipeline.processImg(imageRed);
        Mat testImage = greenPipeline.processImg(imageGreen);

        return testImage;
    }

    @Override
    public void onOpModePreInit(OpMode opMode) {

    }

    @Override
    public void onOpModePreStart(OpMode opMode) {

    }

    @Override
    public void onOpModePostStop(OpMode opMode) {
        openCvCameraView.disableView();
        greenPipeline.releaseMat();
    }
}
