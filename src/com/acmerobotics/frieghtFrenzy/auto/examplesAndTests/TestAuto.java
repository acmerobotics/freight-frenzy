package com.acmerobotics.frieghtFrenzy.auto.examplesAndTests;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.acmerobotics.frieghtFrenzy.robot.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//@Autonomous(name = "Test Auto")

public class TestAuto extends LinearOpMode {

    boolean isPerformingAutoCommand = false;

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while(!isStopRequested()){

            robot.update();

            telemetry.addData("Drive P", Drive.driveP);
            telemetry.addData("Auto Mode", robot.drive.autoMode);
            telemetry.addData("InTeleop", robot.drive.inTeleop());
            telemetry.addData("Motor Speed", robot.drive.driveMotors[0].getPower());
            telemetry.addData("Correction", robot.drive.correction);
            telemetry.addData("Heading Correction", robot.drive.headingCorrection);
            telemetry.addData("Current Angle",robot.drive.getAngle());

            //
            if (gamepad1.dpad_up && !isPerformingAutoCommand) {

                robot.drive.driveStraight(40);

                telemetry.addData("Up button hit", true);

                isPerformingAutoCommand = true;

            } else {

                isPerformingAutoCommand = false;

                telemetry.addData("Up button hit", false);

            }

            //
            if (gamepad1.dpad_right && !isPerformingAutoCommand) {

                isPerformingAutoCommand = true;

                robot.drive.turnRight(90);

            } else {

                isPerformingAutoCommand = false;

            }

            //
            if (gamepad1.dpad_left && !isPerformingAutoCommand) {

                isPerformingAutoCommand = true;

                robot.drive.turnLeft(90);

            } else {

                isPerformingAutoCommand = false;

            }

            telemetry.update();

        }
    }
}
