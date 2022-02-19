package com.acmerobotics.frieghtFrenzy.auto.hubDuckAndPark;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.checkerframework.checker.units.qual.A;

@Config
@Autonomous(group = "duckAndPark")
public class hubDuckParkRedA3 extends LinearOpMode {

    //Make d1 and t1 longer to reach the hub
    public static double distance1 = 24;
    public static double distance2 = -28;
    public static double distance3 = -12;
    public static double distance4 = 28;

    public static double time1 = 2500;
    public static double time2 = 2000;
    public static double time3 = 2500;
    public static double time4 = 2000;

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        robot.drive.driveStraight(distance1);
        robot.runForTime((long)time1);

        robot.drive.turnRight(90);
        robot.runUntil(robot.drive::atTargetAngle);

        //Right here is where we are next to the hub facing away from it

        robot.drive.driveStraight(distance2);
        robot.runForTime((long)time2);

        robot.drive.turnLeft(90);
        robot.runUntil(robot.drive::atTargetAngle);

        robot.drive.driveStraight(distance3);
        robot.runForTime((long)time3);

        robot.duckWheel.rampUp("red");
        robot.runUntil(robot.duckWheel::isRampingUpCompleted);

        robot.drive.driveStraight(distance4);
        robot.runForTime((long)time4);

        robot.drive.stopDrive();


    }
}
