package com.acmerobotics.frieghtFrenzy.auto.examplesAndTests;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(group = "testing")
public class moveForwardandBack extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();


        while(opModeIsActive()) {
            robot.drive.driveStraight(24);
            robot.runUntil(robot.drive::atTargetDistance);

            sleep(1000);

            robot.drive.turnRight(90);
            robot.runUntil(robot.drive::atTargetAngle);

            sleep(1000);

            robot.drive.driveStraight(-24);
            robot.runUntil(robot.drive::atTargetDistance);

            sleep(1000);

            robot.drive.turnLeft(90);
            robot.runUntil(robot.drive::atTargetAngle);

            sleep(1000);

        }


    }
}
