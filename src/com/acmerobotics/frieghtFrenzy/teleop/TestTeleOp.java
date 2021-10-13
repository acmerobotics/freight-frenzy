package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.frieghtFrenzy.vision.CVCamera;
import com.acmerobotics.frieghtFrenzy.vision.FileProcessing;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

//        ACMERobot robot = new ACMERobot(this);

//        CVCamera cvCamera = new CVCamera();
        FileProcessing fileProcessing = new FileProcessing();

        fileProcessing.onCameraFrame();

        waitForStart();

        while (!isStopRequested()){

//            robot.update();

        }
    }
}
