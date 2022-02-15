package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.robomatic.util.StickyGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class RedTeleOp extends LinearOpMode {

    private boolean intaking = false;
    private boolean reversing = false;

    @Override
    public void runOpMode() throws InterruptedException {


        ACMERobot robot = new ACMERobot(this);
        StickyGamepad stickyGamepad = new StickyGamepad(gamepad1);

        waitForStart();

        while (!isStopRequested()){

            // drive
            robot.drive.setPower(gamepad1.right_stick_x, -gamepad1.left_stick_y);


            // intake
            if (stickyGamepad.a){
                intaking = !intaking;

                if (intaking){
                    reversing = false;
                    robot.intake.runIntakeIn();
                }
                else{
                    robot.intake.stopIntake();
                }
            }

            if (stickyGamepad.left_bumper){
                reversing = !reversing;

                if (reversing) {
                    intaking = false;
                    robot.intake.runIntakeOut();
                }
                else {
                    robot.intake.stopIntake();
                }
            }


            // arm
            if(gamepad1.right_trigger > 0.1){
                robot.freightScorer.setPower(gamepad1.right_trigger / 3); // go to score
            }
            if(gamepad1.left_trigger > 0.1){
                robot.freightScorer.setPower(gamepad1.left_trigger / -3); // go to rest
            }
            if (gamepad1.left_trigger < 0.1 & gamepad1.right_trigger < 0.1){ // stop
                robot.freightScorer.setPower(0);
            }


            // duck wheel
            if (stickyGamepad.right_bumper){
                robot.duckWheel.rampUp(60, 25, "red");
            }

            if (stickyGamepad.b){
                robot.duckWheel.stop();
            }

            stickyGamepad.update();
            robot.update();
        }
    }
}
