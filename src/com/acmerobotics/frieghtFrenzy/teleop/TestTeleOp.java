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

            if (stickyGamepad.y){
                robot.freightScorer.scoreTop();
            }

            if (stickyGamepad.b){
                robot.freightScorer.scoreMiddle();
            }

            if (stickyGamepad.a){
                robot.freightScorer.scoreLow();
            }

            if (stickyGamepad.x){
                robot.freightScorer.rest();
            }

            stickyGamepad.update();
            robot.update();

        }
    }
}
