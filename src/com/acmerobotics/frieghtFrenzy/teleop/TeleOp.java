package com.acmerobotics.frieghtFrenzy.teleop;

import android.util.Log;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp()

public class TeleOp extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        // prevents error that occurs after quickly hitting stop after start
        if (isStopRequested()) {
            return;
        }

        while (!isStopRequested()){

            robot.update();
        }
    }
}
