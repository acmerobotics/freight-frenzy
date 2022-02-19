package com.acmerobotics.frieghtFrenzy.auto.duckAndPark;

import com.acmerobotics.frieghtFrenzy.robot.ACMERobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(group = "duckAndPark")
public class duckParkRedA3StateMachine extends LinearOpMode {

    public int autoCase = 0;
    public int nextCase = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        ACMERobot robot = new ACMERobot(this);

        waitForStart();

        while (opModeIsActive()) {
            switch (autoCase) {
                case 0:

                    robot.drive.driveStraight(24);
                    autoCase = 8;
                    break;

                case 1:

                    robot.drive.turnRight(90);
                    autoCase = 9;

                    break;

                case 2:

                    robot.drive.driveStraight(-24);
                    autoCase = 8;

                    break;

                case 3:

                    robot.drive.turnLeft(90);
                    autoCase = 9;

                    break;

                case 4:

                    robot.drive.driveStraight(-24);
                    autoCase=8;

                    break;

                case 5:

                    robot.duckWheel.rampUp( "red");
                    autoCase = 10;
                    break;

                case 6:

                    robot.drive.driveStraight(24);
                    autoCase = 8;

                    break;

                case 7:

                    break;

                case 8:

                    while (!robot.drive.atTargetDistance()) {
                        robot.update();
                    }
                    nextCase++;
                    autoCase = nextCase;

                    break;

                case 9:

                    while (!robot.drive.atTargetAngle()) {
                        robot.update();
                    }
                    nextCase++;
                    autoCase = nextCase;

                    break;

                case 10:

                    while (!robot.duckWheel.doneRampingUp){
                        robot.update();
                    }

                    break;


            }
        }

    }
}
