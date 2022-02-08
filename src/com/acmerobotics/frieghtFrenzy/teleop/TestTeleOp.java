package com.acmerobotics.frieghtFrenzy.teleop;

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

            if (gamepad1.a){
                robot.intake.runIntakeIn();
            }

            if (gamepad1.b){
                robot.intake.runIntakeOut();
            }

            robot.update();
        }
    }
}
