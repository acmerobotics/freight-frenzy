package com.acmerobotics.frieghtFrenzy.auto.examplesAndTests;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(group = "testing")
public class moveForwardTurnMoveBackTurn extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();


        while(opModeIsActive()) {
            robot.drive.driveStraight(12);
            robot.runUntil(robot.drive::atTargetDistance);

            robot.drive.driveStraight(-12);
            robot.runUntil(robot.drive::atTargetDistance);
        }


    }
}
