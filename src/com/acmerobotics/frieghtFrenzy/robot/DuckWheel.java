package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.acmerobotics.robomatic.util.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    public static double P = 1.25;

    PIDController pidController;

    
    public DuckWheel(Robot robot) { //TODO change motor direction depending on the alliance color (red is forward blue is backward)
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


        if (isRamping) { //TODO you figured out how to plan vel and acc but the position overshoots. Figure out how to fix that and land at the target and also track where I am supposed to be at all times

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

    public void rampUp(double distance_i, double maxVel_u){ // distance in inches
        // linearly accelerate the wheel to prevent it from falling from jerk

        targetDistance = inchesToTicks(distance_i) + duckMotor.getCurrentPosition();
        this.maxVel = unitsToTicks(maxVel_u);

        totalTime = 2 * targetDistance / maxVel; // totalAccTime

        maxAcc = maxVel / totalTime;
        
        isRamping = true;

        elapsedTime.reset();

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
