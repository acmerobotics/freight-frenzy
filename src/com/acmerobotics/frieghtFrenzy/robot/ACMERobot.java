package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.robomatic.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ACMERobot extends Robot {

    public DuckWheel duckWheel;

    public ACMERobot(LinearOpMode opmode) {
        super(opmode);

        registerHub("Expansion Hub 1");
        registerHub("Control Hub");


        duckWheel = new DuckWheel(this);

        registerSubsytem(duckWheel);

    }
}
