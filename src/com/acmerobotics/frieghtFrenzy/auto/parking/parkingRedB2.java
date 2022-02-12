package com.acmerobotics.frieghtFrenzy.auto.parking;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(group = "parking")
public class parkingRedB2 extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        int numberCase = 0;
        int nextCase = 2;


        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while(!isStopRequested()) {
            robot.update();
            switch (numberCase) {
                case 0:

                    robot.drive.driveStraight(20);
                    robot.update();
                    numberCase=1;
                    nextCase = 2;

                    break;

                case 1:
                    robot.update();
                    if (robot.drive.atTargetDistance()){
                        numberCase = nextCase;
                    }
                    break;

                case 2:
                    robot.drive.turnRight(90);
                    nextCase = 3;
                    numberCase = 5;

                    break;
                case 3:
                    robot.drive.driveStraight(84);
                    nextCase=4;
                    numberCase=1;
                    break;
                case 4:
                    robot.drive.stopDrive();

                    break;
                case 5:
                    robot.update();
                    if (robot.drive.atTargetAngle()){
                        numberCase = nextCase;
                    }

                }

        }
    }
}
