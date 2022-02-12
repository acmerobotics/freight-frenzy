package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.robomatic.util.StickyGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        StickyGamepad stickyGamepad = new StickyGamepad(gamepad1);
        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while (!isStopRequested()){

            if(gamepad1.right_trigger > 0.1){
                robot.freightScorer.setPower(gamepad1.right_trigger / 3);
            }
            if(gamepad1.left_trigger > 0.1){
                robot.freightScorer.setPower(gamepad1.left_trigger / -3);
            }
            if (gamepad1.left_trigger < 0.1 & gamepad1.right_trigger < 0.1){
                robot.freightScorer.setPower(0);
            }

            if (stickyGamepad.a){
            }

            if (stickyGamepad.b){
                robot.freightScorer.rest();
            }

            stickyGamepad.update();
            robot.update();
        }
    }
}
