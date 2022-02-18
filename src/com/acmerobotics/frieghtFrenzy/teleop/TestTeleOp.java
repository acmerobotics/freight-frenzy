package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.robomatic.util.StickyGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestTeleOp extends LinearOpMode {

    private boolean intaking = false;


    @Override
    public void runOpMode() throws InterruptedException {

        StickyGamepad stickyGamepad = new StickyGamepad(gamepad1);
        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while (!isStopRequested()){

            robot.drive.setPower(gamepad1.right_stick_x, -gamepad1.left_stick_y);

            if (stickyGamepad.y){
                robot.freightScorer.scoreTop();
            }

            if (stickyGamepad.b){
                robot.freightScorer.scoreMiddle();
            }

            if (stickyGamepad.x){
                robot.freightScorer.rest();
            }

            ///////////////////////////////////////
            if (stickyGamepad.a){
                intaking = !intaking;

                if (intaking){
                    robot.intake.runIntakeIn();
                }
                else{
                    robot.intake.stopIntake();
                }
            }

            if (stickyGamepad.right_bumper){
                robot.duckWheel.continuous("red");
            }

            stickyGamepad.update();
            robot.update();

        }
    }
}
