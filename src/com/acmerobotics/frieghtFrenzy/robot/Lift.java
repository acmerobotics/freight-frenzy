package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.acmerobotics.robomatic.util.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Lift extends Subsystem {

    private DcMotorEx liftMotor;
    private PIDController pidController;

    public static double P = 0.001;
    public static double I = 0;
    public static double D = 0;

    private double target = 0;

    public static double restPosition = 5;

    public static double top = 475;
    public static double mid = 575;
    public static double low = 675;

    // 500 top
    // 550 mid
    // 650 low

    public boolean goToPosition = false;

    public Lift(Robot robot) {
        super("freightScorer");

        liftMotor = robot.getMotor("liftMotor");
        pidController = new PIDController(P, I, D);

        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void update(Canvas fieldOverlay) {

        double error = target - liftMotor.getCurrentPosition();

        double correction = pidController.update(error);

        if (goToPosition){
            liftMotor.setPower(correction);
        }

        telemetryData.addData("current position", liftMotor.getCurrentPosition());
        telemetryData.addData("target position", target);

        telemetryData.addData("power", liftMotor.getPower());

        telemetryData.addData("correction", correction);
    }

    public void scoreTop(){
        goToPosition = true;
        target = top;
    }

    public void scoreMiddle(){
        goToPosition = true;
        target = mid;
    }

    public void scoreLow(){
        goToPosition = true;
        target = low;
    }

    public void rest(){
        goToPosition = true;
        target = restPosition;
    }

    public boolean atPosition(){
        if (liftMotor.getCurrentPosition() == target){
            goToPosition = false;
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
