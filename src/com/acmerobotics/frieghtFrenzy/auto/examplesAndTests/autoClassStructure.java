package com.acmerobotics.frieghtFrenzy.auto.examplesAndTests;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.checkerframework.checker.signature.qual.DotSeparatedIdentifiers;

//@Autonomous(name = "autoClassStructure", group = "examples")
public class autoClassStructure extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        robot.drive.driveStraight(1);
        robot.runUntil(robot.drive::atTargetDistance);

        robot.drive.turnLeft(1);
        robot.runUntil(robot.drive::atTargetAngle);

        robot.drive.driveStraight(1);
        robot.runUntil(robot.drive::atTargetDistance);


    }
}
