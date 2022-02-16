package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.robomatic.robot.Robot;

public class Intake extends Subsystem {

    public DcMotorEx[] intakeMotors = new DcMotorEx[2];

    public Intake(Robot robot) {
        super("intake");

            intakeMotors[0] = robot.getMotor("intakeMotor1");
            intakeMotors[1] = robot.getMotor("intakeMotor2");

            intakeMotors[0].setDirection(DcMotorSimple.Direction.FORWARD);
            intakeMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public void update(Canvas fieldOverlay) {

    }

    public void runIntakeIn(){

        intakeMotors[0].setPower(-1);
        intakeMotors[1].setPower(-1);

    }

    public void runIntakeOut(){

        intakeMotors[0].setPower(1);
        intakeMotors[1].setPower(1);

    }

    public void stopIntake(){

        intakeMotors[0].setPower(0);
        intakeMotors[1].setPower(0);

    }

}
