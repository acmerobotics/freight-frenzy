package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.robomatic.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ACMERobot extends Robot {

    public final DuckWheel duckWheel;
    public final Intake intake;

    public ACMERobot(LinearOpMode opmode) {
        super(opmode);

        registerHub("Expansion Hub 1");
        registerHub("Control Hub");

        //Create Subsystems Here
        intake = new Intake(this);
        duckWheel = new DuckWheel(this);



        //Register Subsystems here
        registerSubsytem(intake);
        registerSubsytem(duckWheel);



    }
}
