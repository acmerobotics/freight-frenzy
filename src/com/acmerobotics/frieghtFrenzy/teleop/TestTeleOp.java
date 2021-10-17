package com.acmerobotics.frieghtFrenzy.teleop;

import android.util.Log;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.frieghtFrenzy.vision.FileProcessing;
import com.acmerobotics.frieghtFrenzy.vision.LocateOnBarcode;
import com.acmerobotics.frieghtFrenzy.vision.Webcam;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Webcam webcam = new Webcam(hardwareMap);
        LocateOnBarcode barcodeLocator = new LocateOnBarcode("RED");

        webcam.captureImg();

        barcodeLocator.processFrames(); // causes "stuck in stop()" error if stop is pressed before it is finished

        Log.i("CV:", barcodeLocator.LocateShippingElement().toString());

        waitForStart();

        if (isStopRequested()) {
            return;
        }

        while (!isStopRequested()){


        }
    }
}
