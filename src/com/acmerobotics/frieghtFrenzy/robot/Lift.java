package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.acmerobotics.robomatic.util.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

public class Lift extends Subsystem {

    //TODO make pid vars changable in dashbaord without redeploying

    private DcMotorEx liftMotor;
    private Servo holder;
    private PIDController pidController;

    public static double P;
    public static double I;
    public static double D;

    private double error = 0;
    private double target = 0;

    public static double raisedPos = 0;
    public static double openPos = 0;
    public static double closePos = 0;

    private static double openHolderPos = 0;

    private enum LiftMode{
        UNKNOWN,
        FEEDBACK
    }

    public Lift(Robot robot) {
        super("Lift");

        liftMotor = robot.getMotor("liftMotor");
        holder = robot.getServo("liftHolder");
        pidController = new PIDController(P, I, D);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    @Override
    public void update(Canvas fieldOverlay) {
        telemetryData.addData("current lift position", liftMotor.getCurrentPosition());
        telemetryData.addData("current holder position", holder.getPosition());

        error = target - liftMotor.getCurrentPosition();

        double correction = pidController.update(error); // NOT in ticks per sec, need to convert

        liftMotor.setVelocity(correction); // set a velocity in ticks per sec

        if (liftMotor.getCurrentPosition() > openHolderPos){ // when the lift passes the openHolderPos then it is safe to open the holder
            setHolderPosition(openPos);
        }
        else{
            setHolderPosition(closePos); // when the lift goes lower then the openHolderPos then close the holder
        }

    }

    public void score(){
        setPosition(raisedPos);
    }

    public void getReadyToReceive(){
        setPosition(0);
    }


    private void setPosition(double position){
        target = position;
    }

    private void setHolderPosition(double position){
        holder.setPosition(position);
    }

}
