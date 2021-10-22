package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.robomatic.hardware.CachingSensor;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.Optional;

public class Drive extends Subsystem {

    public DcMotorEx[] driveMotors = new DcMotorEx[4];

    //I don't know if we are using these yet
    public DcMotorEx omniEncoderX;
    public DcMotorEx omniEncoderY;

    private CachingSensor imuSensor;

    private LinearOpMode opMode;

    public Drive(Robot robot, LinearOpMode opMode) {
        super("Drive");

        this.opMode = opMode;

        BNO055IMUImpl imu = robot.getRevHubImu(0, new Robot.Orientation(Robot.Axis.POSITIVE_X, Robot.Axis.POSITIVE_Y, Robot.Axis.POSITIVE_Z));
        imuSensor = new CachingSensor<>(() -> imu.getAngularOrientation().firstAngle);
        robot.registerCachingSensor(imuSensor);

        for (int i = 0; i < 4; i++) {
            driveMotors[i] = robot.getMotor("Motor" + i);
        }


    }

    @Override
    public void update(Canvas fieldOverlay) {

        //Motor Positions
        telemetryData.addData("Motor0 Position", driveMotors[0].getCurrentPosition());
        telemetryData.addData("Motor1 Position", driveMotors[1].getCurrentPosition());
        telemetryData.addData("Motor2 Position", driveMotors[2].getCurrentPosition());
        telemetryData.addData("Motor3 Position", driveMotors[3].getCurrentPosition());

        //Omni Positions
        telemetryData.addData("OmniX Position", omniEncoderX.getCurrentPosition());
        telemetryData.addData("OmniY Position", omniEncoderY.getCurrentPosition());

        //Motor Powers
        telemetryData.addData("Motor0 Power ", driveMotors[0].getPower());
        telemetryData.addData("Motor1 Power ", driveMotors[1].getPower());
        telemetryData.addData("Motor2 Power ", driveMotors[2].getPower());
        telemetryData.addData("Motor3 Power ", driveMotors[3].getPower());

    }


//TeleOp

///////\\\\\\\\\////\\\\\\////\\///////\\\\\\///////////\\\\\\\\////\\\\\\///////////
///////////\\////////\\////////\\///////\\///////////////\\////\\////\\///\\/////////
////////////\\////////\\\\\\////\\///////\\\\\\///\\\\\///\\////\\////\\\\\//////////
/////////////\\////////\\////////\\///////\\///////////////\\////\\////\\////////////
//////////////\\////////\\\\\\////\\\\\////\\\\\\///////////\\\\\\\\////\\///////////

    public void setPower() {


    }




}