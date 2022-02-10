package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.robomatic.util.StickyGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class TestTeleOp extends LinearOpMode {

    private boolean isSlowMode = false;

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        StickyGamepad stickyGamepad = new StickyGamepad(gamepad1);

        robot.update();

        telemetry.addData("InTeleop", robot.drive.inTeleop());

        telemetry.update();

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

            if (stickyGamepad.left_stick_button){
                isSlowMode = true;
            } else {
                isSlowMode = false;
            }

            if (isSlowMode){

                robot.drive.setSlowModePower(gamepad1.left_stick_x, gamepad1.left_stick_y);

            } else {

                robot.drive.setPower(gamepad1.left_stick_x, gamepad1.left_stick_y);

            }

        }
    }
}
