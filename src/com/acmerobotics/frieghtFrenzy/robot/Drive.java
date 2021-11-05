package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.robomatic.hardware.CachingSensor;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.acmerobotics.robomatic.util.PIDController;
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

    private double leftJoystickX;
    private double leftJoystickY;

    private double angleTarget;
    private double errorAngle;
    private double error1;

    private double correction;

    private PIDController turnPIDController;
    private PIDController headingPIDController;

    //Tune these
    private final double turnP = 0;
    private final double turnI = 0;
    private final double turnD = 0;

    private enum AutoMode{
        UNKNOWN,
        TURN,
        STRAIGHT
    };

    public AutoMode autoMode;

    public Drive(Robot robot, LinearOpMode opMode) {
        super("Drive");

        this.opMode = opMode;

        BNO055IMUImpl imu = robot.getRevHubImu(0, new Robot.Orientation(Robot.Axis.POSITIVE_X, Robot.Axis.POSITIVE_Y, Robot.Axis.POSITIVE_Z));
        imuSensor = new CachingSensor<>(() -> imu.getAngularOrientation().firstAngle);
        robot.registerCachingSensor(imuSensor);

        for (int i = 0; i < 4; i++) {
            driveMotors[i] = robot.getMotor("Motor" + i);
        }

        turnPIDController = new PIDController(turnP, turnI, turnD);

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

        if (!inTeleop()){

            switch (autoMode){
                case UNKNOWN:

                    driveMotors[0].setPower(0);
                    driveMotors[1].setPower(0);
                    driveMotors[2].setPower(0);
                    driveMotors[3].setPower(0);

                    break;

                case TURN:

                    errorAngle = angleTarget - getRobotAngle();

                    correction = turnPIDController.update(errorAngle);

                    driveMotors[0].setPower(correction);
                    driveMotors[1].setPower(correction);
                    driveMotors[2].setPower(-correction);
                    driveMotors[3].setPower(-correction);

                    break;

                case STRAIGHT:



                    break;

            }






        }

    }


//TeleOp

///////\\\\\\\\\////\\\\\\////\\///////\\\\\\///////////\\\\\\\\////\\\\\\///////////
///////////\\////////\\////////\\///////\\///////////////\\////\\////\\///\\/////////
////////////\\////////\\\\\\////\\///////\\\\\\///\\\\\///\\////\\////\\\\\//////////
/////////////\\////////\\////////\\///////\\///////////////\\////\\////\\////////////
//////////////\\////////\\\\\\////\\\\\////\\\\\\///////////\\\\\\\\////\\///////////

    public void setPower(double left_stick_x, double left_stick_y) {

        //Setting joystick ramping
            //If power is odd comment the if loop and the contents of the else
            if (left_stick_x >= 0){
                leftJoystickX = Math.pow(left_stick_x, 2);
            } else {
                leftJoystickX = -Math.pow(left_stick_x, 2);
            }

            if (left_stick_y >= 0){
                leftJoystickY = Math.pow(left_stick_y, 2);
            } else {
                leftJoystickY = -Math.pow(left_stick_y, 2);
            }

        //Setting powers
            //Might have to switch the plus and minus
            //Left Motors
            driveMotors[0].setPower(leftJoystickX + leftJoystickY);
            driveMotors[1].setPower(leftJoystickX + leftJoystickY);

            //Right Motors
            driveMotors[2].setPower(leftJoystickX - leftJoystickY);
            driveMotors[3].setPower(leftJoystickX - leftJoystickY);



    }

    public void setSlowModePower(double left_stick_x, double left_stick_y) {

        //Setting joystick ramping
            //If the exponent is odd comment out the if loop and the contents of the else
            if (left_stick_x >= 0){
                leftJoystickX = Math.pow(left_stick_x, 2)/2;
            } else {
                leftJoystickX = -Math.pow(left_stick_x, 2)/2;
            }

            if (left_stick_y >= 0){
                leftJoystickY = Math.pow(left_stick_y, 2)/2;
            } else {
                leftJoystickY = -Math.pow(left_stick_y, 2)/2;
            }

        //Setting powers
            //Might have to switch the plus and minus
            //Left Motors
            driveMotors[0].setPower(leftJoystickX + leftJoystickY);
            driveMotors[1].setPower(leftJoystickX + leftJoystickY);

            //Right Motors
            driveMotors[2].setPower(leftJoystickX - leftJoystickY);
            driveMotors[3].setPower(leftJoystickX - leftJoystickY);

    }

    //Auto

    public void turnLeft(double angleFromRobot){



    }

    public void turnRight(double angleFromRobot){



    }

    public double getRobotAngle(){
        double currentAngle;

        currentAngle = Double.parseDouble(imuSensor.getValue().toString());

        return currentAngle;
    }

    public boolean inTeleop(){
        boolean isInTeleop;
        int negativeOneIfNoTeleop;
        String opMode;

        //https://www.javatpoint.com/java-string-valueof Last example shows how output works with objects
        opMode = String.valueOf(this.opMode);

        //equals -1 if teleop doesn't appear in the string
        negativeOneIfNoTeleop = opMode.indexOf("teleop");

        isInTeleop = negativeOneIfNoTeleop != -1;

        return isInTeleop;
    }

}