package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.acmerobotics.robomatic.util.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Lift extends Subsystem {

    private DcMotorEx liftMotor;
    private PIDController pidController;

    public static double P = 1;
    public static double I = 0;
    public static double D = 0;

    private double error = 0;
    private double target = 0;

    public static double restPosition = 0;
    public static double scorePosition = 250;

    public Lift(Robot robot) {
        super("freightScorer");

        liftMotor = robot.getMotor("liftMotor");
        pidController = new PIDController(P, I, D);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void update(Canvas fieldOverlay) {

        error = target - liftMotor.getCurrentPosition();

        double correction = pidController.update(error);

        liftMotor.setPower(correction);

        telemetryData.addData("current position", liftMotor.getCurrentPosition());
        telemetryData.addData("target position", target);

        telemetryData.addData("power", liftMotor.getPower());

        telemetryData.addData("correction", correction);
        telemetryData.addData("error", error);
    }

    public void score(){
        target = scorePosition;
    }

    public void rest(){
        target = restPosition;
    }

    public boolean atPosition(){
        if (liftMotor.getCurrentPosition() >= target){
            return true;
        }

        else{
            return false;
        }
    }

    public void setPower(double power){
        liftMotor.setPower(power);
    }
}
