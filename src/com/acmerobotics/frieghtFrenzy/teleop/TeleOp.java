package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.robomatic.util.StickyGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp()

public class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {


        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while (!isStopRequested()){

        }
    }
}
