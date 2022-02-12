package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.robomatic.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ACMERobot extends Robot {

    public final DuckWheel duckWheel;
    public final Intake intake;
    public final Drive drive;
    public final Lift freightScorer;


    public ACMERobot(LinearOpMode opMode) {
        super(opMode);

        registerHub("Expansion Hub 1");
        registerHub("Control Hub");

        //Create Subsystems Here
        intake = new Intake(this);
        duckWheel = new DuckWheel(this);
        drive = new Drive(this, opMode);
        freightScorer = new Lift(this);




        //Register Subsystems here
        registerSubsytem(intake);
        registerSubsytem(duckWheel);
        registerSubsytem(drive);
        registerSubsytem(freightScorer);

    }
}
