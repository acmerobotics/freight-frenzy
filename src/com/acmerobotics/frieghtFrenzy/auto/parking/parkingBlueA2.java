package com.acmerobotics.frieghtFrenzy.auto.parking;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(group = "parking")
public class parkingBlueA2 extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

                robot.drive.driveStraight(26);
                robot.runUntil(robot.drive::atTargetDistance);

                robot.drive.turnLeft(90);
                robot.runUntil(robot.drive::atTargetAngle);

                robot.drive.driveStraight(84);
                robot.runUntil(robot.drive::atTargetDistance);

        robot.drive.stopDrive();

    }
}
