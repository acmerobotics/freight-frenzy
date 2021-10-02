package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.robomatic.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class ACMERobot extends Robot {

    public ACMERobot(LinearOpMode opmode) {
        super(opmode);

        registerHub("hub0"); // can't remember what the numbers are check last years code
        registerHub("hub1");

    }
}
