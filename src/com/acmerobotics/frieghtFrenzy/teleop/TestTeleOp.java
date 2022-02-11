package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.robomatic.util.StickyGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Test Tele-Op")
public class TestTeleOp extends LinearOpMode {

    private boolean isSlowMode = false;

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        robot.update();

        telemetry.addData("InTeleop", robot.drive.inTeleop());

        telemetry.update();

        waitForStart();

        while (!isStopRequested()){

            robot.update();

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

            telemetry.update();

        }
    }
}
