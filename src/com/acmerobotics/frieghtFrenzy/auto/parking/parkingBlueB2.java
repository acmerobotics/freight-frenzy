package com.acmerobotics.frieghtFrenzy.auto.parking;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(group = "parking")
public class parkingBlueB2 extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {


        ACMERobot robot = new ACMERobot(this);

        waitForStart();

                robot.drive.driveStraight(26);
                robot.runUntil(robot.drive::atTarget);


                robot.drive.turnLeft(90);
                robot.runUntil(robot.drive::atTarget);

                robot.drive.driveStraight(48);
                robot.runUntil(robot.drive::atTarget);

        robot.drive.stopDrive();


    }
}
