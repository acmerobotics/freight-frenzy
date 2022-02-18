package com.acmerobotics.frieghtFrenzy.robot;

import android.app.LauncherActivity;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.acmerobotics.robomatic.util.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Thread.sleep;

@Config
public class DuckWheel extends Subsystem {

    private double ticksPerRev = 537.6;

    private DcMotorEx duckMotor;

    private double maxVel = 0; // hardware max is 2680

    private double maxAcc = 0;

    private double totalTime = 0;
    private double currTime = 0;

    private double targetDistance = 0; // in ticks

    private double currVel;

    private ElapsedTime elapsedTime;

    private boolean isRamping = false;
    
    public boolean doneRampingUp = false; // if true then the duck should have been delivered so move on to the next game task

    private boolean atPosition = false;

    public static double delay = 1.5;

    public static double P = 1.25;

    PIDController pidController;

    public static double distance_i = 60;
    public static double maxVel_u = 25;

    private boolean isContinuous = false;

    public DuckWheel(Robot robot) {
        super("DuckWheel");

        elapsedTime = new ElapsedTime();

        duckMotor = robot.getMotor("duckMotor");

        duckMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        duckMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        duckMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pidController = new PIDController(P, 0, 0);

    }

    @Override
    public void update(Canvas fieldOverlay) {
        telemetryData.addData("maxAcc", maxAcc);
        telemetryData.addData("maxVel", maxVel);
        telemetryData.addData("currVel", duckMotor.getVelocity());
        telemetryData.addData("currVel calculated", currVel); // curr vel

        telemetryData.addData("currPower", duckMotor.getPower());

        telemetryData.addData("totalTime", totalTime);
        telemetryData.addData("currTime (sec)", currTime);

        telemetryData.addData("targetDistance (in)", ticksToInches(targetDistance));
        telemetryData.addData("currDistance (in)", getCurrPos_i());
        telemetryData.addData("currDistance (ticks)", duckMotor.getCurrentPosition());
        telemetryData.addData("currDistance calc with live time (ticks)", maxAcc * Math.pow(elapsedTime.seconds(), 2));
        telemetryData.addData("currDistance calc with currTime (ticks)", maxAcc * Math.pow(currTime, 2));

        telemetryData.addData("atPosition", atPosition);


        if (isRamping) {

            currVel = maxAcc * elapsedTime.seconds();

            if (currVel >= maxVel){
                currVel = maxVel;
            }

            double correction = pidController.update(currVel - duckMotor.getVelocity());

            setVelocity(ticksToUnits(currVel)); // convert to "units" velocity (the velocity used in setVelocity())
            // add correction to currVel, if feedback correction is desired

            if (duckMotor.getCurrentPosition() >= targetDistance){ //duckMotor.getVelocity() >= maxVel
                doneRampingUp = true;
                setVelocity(0);
                isRamping = false;
                currTime = elapsedTime.seconds();
                atPosition = true;
                duckMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                duckMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
        }

        if (isContinuous){
            if (atPosition){
                if ((currTime + delay) <= elapsedTime.seconds()){
                    continuous("red"); // set an instance field that has its value set inside of continuous
                }
            }
        }
    }

    public void setPower(double power){
        duckMotor.setPower(power);
    }

    public void setVelocity(double velocity){ // set velocity is stupid. the docs say the parameter should be in ticks per sec but when you run get vel the value will be diff from the value set
                                                // in my case 30 is the max which corresponds to a actual vel max of 2680 ticks/sec
        duckMotor.setVelocity(velocity); // in ticks/sec
    }
    
    private double getCurrPos_i(){
        return ticksToInches(duckMotor.getCurrentPosition());
    }

    public void rampUp(String color){ // distance in inches
        // linearly accelerate the wheel to prevent it from falling from jerk

        if (color.equals("red")){
            duckMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        else {
            duckMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        targetDistance = inchesToTicks(distance_i);
        this.maxVel = unitsToTicks(maxVel_u);

        totalTime = 2 * targetDistance / maxVel; // totalAccTime

        maxAcc = maxVel / totalTime;
        
        isRamping = true;

        elapsedTime.reset();

    }

    public void continuous(String color){
        atPosition = false;
        rampUp(color);
        isContinuous = true;
    }

    public void stop(){
        isRamping = false;
        setVelocity(0);
    }
    
    private double ticksToInches(double ticks){
        double cir = 4 * 3.14;
        return ticks * cir / ticksPerRev;
    }
    
    private double inchesToTicks(double inches){
        double cir = 4 * 3.14;
        return ticksPerRev * inches / cir;
    }

    private double unitsToTicks(double units){ // used to convert velocity of units/sec to ticks/sec
        return 2680 * units / 30; // 2680 is true max vel, 30 is the max vel when setting a velocity using setVelocity()
    }

    private double ticksToUnits(double ticks){ // used to convert ticks/sec velocity to units/sec velocity so it can be sent to setVelocity()
        return ticks * 30 / 2680;
    }
}
