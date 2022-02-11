package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {

    public boolean inSlowMode = false;
    public boolean inSlowModeTogglePrimed;

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while (!isStopRequested()){



        }
    }
}
