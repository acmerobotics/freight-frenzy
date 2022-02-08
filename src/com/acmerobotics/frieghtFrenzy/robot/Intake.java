package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


public class Intake extends Subsystem {

    public DcMotorEx[] intakeMotors = new DcMotorEx[2];

    public Intake(Robot robot) {
        super("intake");

            intakeMotors[0] = robot.getMotor("intakeMotor1");
            intakeMotors[1] = robot.getMotor("intakeMotor2");

            //Directions are just place holders for now. Test intake when it's ready
            intakeMotors[0].setDirection(DcMotorSimple.Direction.FORWARD);
            intakeMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void update(Canvas fieldOverlay) {

    }

    public void runIntakeIn(){

        //Powers are also a place holder for now
        intakeMotors[0].setPower(-0.1);
        intakeMotors[1].setPower(-0.1);

    }

    public void runIntakeOut(){

        //Powers are still a place holder here too
        intakeMotors[0].setPower(0.1);
        intakeMotors[1].setPower(0.1);

    }

    public void stopIntake(){

        //These powers should work though
        intakeMotors[0].setPower(0);
        intakeMotors[1].setPower(0);

    }

}
