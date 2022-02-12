package com.acmerobotics.frieghtFrenzy.teleop;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.robomatic.util.StickyGamepad;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp()

public class TeleOp extends LinearOpMode {

    private boolean intaking = false;
    private boolean delivering = false;

    @Override
    public void runOpMode() throws InterruptedException {


        ACMERobot robot = new ACMERobot(this);
        StickyGamepad stickyGamepad = new StickyGamepad(gamepad1);

        waitForStart();

        while (!isStopRequested()){

            // drive
            robot.drive.setPower(gamepad1.right_stick_x, gamepad1.left_stick_y);


            // intake
            if (stickyGamepad.a){
                intaking = !intaking;

                if (intaking){
                    robot.intake.runIntakeIn();
                }
                else{
                    robot.intake.stopIntake();
                }
            }

            if (gamepad1.left_trigger > 0.1){
                intaking = false;
                robot.intake.runIntakeOut();
            }
            else{
                if (!intaking){
                    robot.intake.stopIntake();
                }
            }


            // arm
            if (stickyGamepad.b){
                delivering = !delivering;

                if (delivering){
                    // move arm to delver freight to hub
                }
                else{
                    // move arm to rest position
                }
            }


            // duck wheel
            if (gamepad1.right_trigger > 0.1){ //TODO test this. it might be better to use rampUp()
//                robot.duckWheel.setPower(gamepad1.right_trigger);
                 robot.duckWheel.setVelocity(gamepad1.right_trigger * 30);
            }
            else{
                robot.duckWheel.setVelocity(0);
            }

            if (stickyGamepad.right_bumper){
                robot.duckWheel.rampUp(55, 20);
            }

            stickyGamepad.update();
            robot.update();
        }
    }
}
