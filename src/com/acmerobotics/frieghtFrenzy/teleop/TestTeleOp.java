package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while (!isStopRequested()){

            if (gamepad1.right_trigger > 0.1){
                robot.duckWheel.setVelocity(gamepad1.right_trigger * 30);
            }

            else{
                robot.duckWheel.setVelocity(0);
            }


//            robot.duckWheel.setVelocity(vel);


            robot.update();
        }
    }
}
