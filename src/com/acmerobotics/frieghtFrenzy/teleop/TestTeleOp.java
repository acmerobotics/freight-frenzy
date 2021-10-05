package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.frieghtFrenzy.vision.CVCamera;
import com.acmerobotics.frieghtFrenzy.vision.OpenCVTest;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

//        ACMERobot robot = new ACMERobot(this);

        CVCamera cvCamera = new CVCamera();

        waitForStart();

        while (!isStopRequested()){

//            robot.update();
        }
    }
}
