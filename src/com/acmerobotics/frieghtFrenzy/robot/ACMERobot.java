package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.robomatic.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ACMERobot extends Robot {

    public final Drive drive;

    public ACMERobot(LinearOpMode opMode) {
        super(opMode);

        registerHub("Expansion Hub 1"); // can't remember what the numbers are check last years code
        registerHub("Control Hub");

        drive = new Drive(this, opMode);

        registerSubsytem(drive);

    }
}
