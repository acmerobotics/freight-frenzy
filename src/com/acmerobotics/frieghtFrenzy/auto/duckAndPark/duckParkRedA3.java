package com.acmerobotics.frieghtFrenzy.auto.duckAndPark;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.checkerframework.checker.units.qual.A;

@Autonomous(group = "duckAndPark")
public class duckParkRedA3 extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        robot.drive.driveStraight(25);
        robot.runUntil(robot.drive::atTargetDistance);

        robot.drive.turnRight(90);
        robot.runUntil(robot.drive::atTargetAngle);

        robot.drive.driveStraight(-24);
        robot.runUntil(robot.drive::atTargetDistance);

        robot.drive.turnLeft(90);
        robot.runUntil(robot.drive::atTargetAngle);

        robot.drive.driveStraight(-24);
        robot.runUntil(robot.drive::atTargetDistance);

        robot.duckWheel.rampUp("red");
        robot.runUntil(robot.duckWheel::isRampingUpCompleted);

        robot.drive.driveStraight(23);
        robot.runUntil(robot.drive::atTarget);

        robot.drive.stopDrive();


    }
}
