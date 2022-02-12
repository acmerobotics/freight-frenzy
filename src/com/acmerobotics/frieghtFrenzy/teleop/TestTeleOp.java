package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.robomatic.util.StickyGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//@TeleOp
public class TestTeleOp extends LinearOpMode {

    boolean isSlowMode = false;

    @Override
    public void runOpMode() throws InterruptedException {

        StickyGamepad stickyGamepad = new StickyGamepad(gamepad1);
        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while (!isStopRequested()){

            if (gamepad1.right_bumper){
                isSlowMode = true;
            } else {
                isSlowMode = false;
            }

            if (isSlowMode){

                robot.drive.setSlowModePower(gamepad1.right_stick_x, gamepad1.left_stick_y);
                telemetry.addData("Slow Mode", "On");

            } else {

                robot.drive.setPower(gamepad1.right_stick_x, gamepad1.left_stick_y);
                telemetry.addData("Slow Mode", "Off");
            }

            stickyGamepad.update();
            robot.update();

        }
    }
}
