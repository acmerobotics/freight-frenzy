package com.acmerobotics.frieghtFrenzy.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Config
@Autonomous
public class TestAuto extends LinearOpMode {

    public static double maxVel = 20;
    public static double dist = 55;

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        robot.duckWheel.rampUp(dist, maxVel); // 47.12 in circumference

        while(!isStopRequested()){

            if(robot.duckWheel.doneRampingUp){
                // done ramping move on to next auto step
            }

            robot.update();
        }

    }
}
