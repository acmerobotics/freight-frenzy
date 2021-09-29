package com.acmerobotics.frieghtFrenzy;

import com.acmerobotics.robomatic.config.ConfigurationLoader;
import com.acmerobotics.robomatic.demo.DemoConfig;
import com.acmerobotics.robomatic.demo.DemoRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class ConfigTesting extends LinearOpMode {
    @Override
    public void runOpMode() {

        DemoConfig config = (DemoConfig) new ConfigurationLoader(hardwareMap.appContext).getConfig();

        waitForStart();

        while(!isStopRequested()){

            telemetry.addLine(config.color.toString());

            telemetry.update();
        }

    }
}
