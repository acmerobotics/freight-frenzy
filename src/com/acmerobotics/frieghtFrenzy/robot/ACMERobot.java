package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.robomatic.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ACMERobot extends Robot {

    public final Lift freightScorer;

    public ACMERobot(LinearOpMode opmode) {
        super(opmode);

        registerHub("Expansion Hub 1"); // can't remember what the numbers are check last years code
        registerHub("Control Hub");

        freightScorer = new Lift(this);

        registerSubsytem(freightScorer);

    }
}
