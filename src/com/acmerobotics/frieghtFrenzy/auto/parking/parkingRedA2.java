package com.acmerobotics.frieghtFrenzy.auto.parking;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "parkingRedA2", group = "parking")
class parkingRedA2 extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

            robot.drive.driveStraight(5);
            robot.runUntil(robot.drive::atTargetDistance);

            robot.drive.turnRight(90);
            robot.runUntil(robot.drive::atTargetAngle);

            robot.drive.driveStraight(96);
            robot.runUntil(robot.drive::atTargetDistance);

            robot.drive.stopDrive();

    }
}
