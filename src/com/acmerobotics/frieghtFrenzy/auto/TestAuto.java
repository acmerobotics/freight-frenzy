package com.acmerobotics.frieghtFrenzy.auto;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class TestAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        robot.duckWheel.rampUp(12.57, 5); // 47.12 in circumference

        while(!isStopRequested()){

            if(robot.duckWheel.doneRampingUp){
                // done ramping move on to next auto step
            }

            robot.update();
        }

    }
}
