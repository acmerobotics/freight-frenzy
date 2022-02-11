package com.acmerobotics.frieghtFrenzy.robot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.robomatic.hardware.CachingSensor;
import com.acmerobotics.robomatic.robot.Robot;
import com.acmerobotics.robomatic.robot.Subsystem;
import com.acmerobotics.robomatic.util.PIDController;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Drive extends Subsystem {
    //Tune these
    public static double turnP = 0.01;
    public static double turnI = 0.001;
    public static double turnD = 0.001;

    public static double headingP = 0.005;
    public static double headingI = 0;
    public static double headingD = 0;

    public static double driveP = 0.01;
    public static double driveI = 0;
    public static double driveD = 0;

    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();

    public DcMotorEx[] driveMotors = new DcMotorEx[4];

    //I don't know if we are using these yet
    //public DcMotorEx omniEncoderX;
    //public DcMotorEx omniEncoderY;

    private CachingSensor<Float> imuSensor;

    private LinearOpMode opMode;

    private double leftJoystickX;
    private double leftJoystickY;

    private double angleTarget;
    private double errorAngle;

    private double globalAngle;
    public double lastAngle;

    private double distanceTarget;
    private double distanceError;

    private double headingError;
    private double headingTarget;

    //Find this value when encoders are decided on. These values are accurate for the 2020-2021 robot encoder setup
    private final double wheelRadiusInches = 3.9/2;
    private final double wheelCircumference = 2 * wheelRadiusInches * Math.PI;
    private final double ticksPerRevolutionOfMotorShaft = 560;
    private final double inchesPerTick = wheelCircumference/ticksPerRevolutionOfMotorShaft;

    public double correction;
    public double headingCorrection;

    //Turn is for just turning
    //Heading is for heading while driving
    //Driving is for distances
    private PIDController turnPIDController;
    private PIDController headingPIDController;
    private PIDController drivePIDController;

    private enum AutoMode{
        UNKNOWN,
        TURN,
        STRAIGHT
    };

    public AutoMode autoMode = AutoMode.UNKNOWN;

    public Drive(Robot robot, LinearOpMode opMode) {
        super("Drive");

        this.opMode = opMode;

        BNO055IMUImpl imu = robot.getRevHubImu(0, new Robot.Orientation(Robot.Axis.POSITIVE_X, Robot.Axis.POSITIVE_Y, Robot.Axis.POSITIVE_Z));
        imuSensor = new CachingSensor<Float>(() -> imu.getAngularOrientation().firstAngle);

        robot.registerCachingSensor(imuSensor);

        for (int i = 0; i < 4; i++) {
            driveMotors[i] = robot.getMotor("Motor" + i);
            driveMotors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

        driveMotors[0].setDirection(DcMotorSimple.Direction.REVERSE);
        driveMotors[1].setDirection(DcMotorSimple.Direction.REVERSE);
        driveMotors[2].setDirection(DcMotorSimple.Direction.FORWARD);
        driveMotors[3].setDirection(DcMotorSimple.Direction.FORWARD);

        //omniEncoderX = robot.getMotor("omniX");
        //omniEncoderY = robot.getMotor("omniY");

        turnPIDController = new PIDController(turnP, turnI, turnD);
        headingPIDController = new PIDController(headingP, headingI, headingD);
        drivePIDController = new PIDController(driveP, driveI, driveD);

        //Change these later if needed (headingPID will probably need a different range)
        turnPIDController.setOutputBounds(-1,1);
        headingPIDController.setOutputBounds(-0.1,0.1);
        drivePIDController.setOutputBounds(-1,1);

    }

    @Override
    public void update(Canvas fieldOverlay) {
/*
        //Motor Positions
        dashboardTelemetry.addData("Is in Teleop", inTeleop());

        dashboardTelemetry.addData("Motor0 Position", driveMotors[0].getCurrentPosition());
        dashboardTelemetry.addData("Motor1 Position", driveMotors[1].getCurrentPosition());
        dashboardTelemetry.addData("Motor2 Position", driveMotors[2].getCurrentPosition());
        dashboardTelemetry.addData("Motor3 Position", driveMotors[3].getCurrentPosition());

        //Omni Positions
        dashboardTelemetry.addData("OmniX Position", omniEncoderX.getCurrentPosition());
        dashboardTelemetry.addData("OmniY Position", omniEncoderY.getCurrentPosition());

        //Motor Powers
        dashboardTelemetry.addData("Motor0 Power ", driveMotors[0].getPower());
        dashboardTelemetry.addData("Motor1 Power ", driveMotors[1].getPower());
        dashboardTelemetry.addData("Motor2 Power ", driveMotors[2].getPower());
        dashboardTelemetry.addData("Motor3 Power ", driveMotors[3].getPower());

        dashboardTelemetry.update();
*/

        for (int i = 0; i <=3; i++){
            telemetryData.addData("Motor" + i + " Velocity", driveMotors[i].getVelocity());
            telemetryData.addData("Motor" + i + " Power", driveMotors[i].getPower());
            telemetryData.addData("Motor " + i + " Position", inchesPerTick*driveMotors[i].getCurrentPosition());
        }

        telemetryData.addData("Correction", correction);
        telemetryData.addData("Heading", getAngle());



        if (!inTeleop()){

            switch (autoMode){
                case UNKNOWN:

                    driveMotors[0].setPower(0);
                    driveMotors[1].setPower(0);
                    driveMotors[2].setPower(0);
                    driveMotors[3].setPower(0);

                    break;

                case TURN:

                    errorAngle = angleTarget - getAngle();

                    correction = turnPIDController.update(errorAngle);

                    driveMotors[0].setPower(correction);
                    driveMotors[1].setPower(correction);
                    driveMotors[2].setPower(-correction);
                    driveMotors[3].setPower(-correction);

                    break;

                case STRAIGHT:

                    headingError = headingTarget - getAngle();

                    headingCorrection = headingPIDController.update(headingError);

                    //Convert encoder to inches
                    distanceError = distanceTarget - inchesPerTick*driveMotors[0].getCurrentPosition();


                    correction = drivePIDController.update(distanceError);

                    driveMotors[0].setPower(correction+headingCorrection);
                    driveMotors[1].setPower(correction+headingCorrection);
                    driveMotors[2].setPower(correction-headingCorrection);
                    driveMotors[3].setPower(correction-headingCorrection);

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
            driveMotors[0].setPower(leftJoystickY - leftJoystickX);
            driveMotors[1].setPower(leftJoystickY - leftJoystickX);

            //Right Motors
            driveMotors[2].setPower(leftJoystickY + leftJoystickX);
            driveMotors[3].setPower(leftJoystickY + leftJoystickX);

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
            driveMotors[0].setPower(leftJoystickX - leftJoystickY);
            driveMotors[1].setPower(leftJoystickX - leftJoystickY);

            //Right Motors
            driveMotors[2].setPower(leftJoystickX + leftJoystickY);
            driveMotors[3].setPower(leftJoystickX + leftJoystickY);

    }

    //Auto
    //
    //
    //
    //
    //
    //Auto

    public void driveStraight(double distanceInInches){

        prepareMotors();

        headingTarget = getAngle();

        distanceTarget = distanceInInches;

        autoMode = AutoMode.STRAIGHT;

    }

    public void turnLeft(double angleFromRobot){

        resetAngle();

        angleTarget = angleFromRobot;

        prepareMotors();

        autoMode = AutoMode.TURN;

    }

    public void turnRight(double angleFromRobot){

        resetAngle();

        angleTarget = -angleFromRobot;

        prepareMotors();

        autoMode = AutoMode.TURN;

    }

    public void resetAngle(){

        globalAngle = 0;

    }

    //Returns angle in degrees
    public double getAngle(){
        double currentAngle;

        currentAngle = imuSensor.getValue() * 180/Math.PI;

        double deltaAngle = currentAngle - lastAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngle = currentAngle;

        return globalAngle;
    }

    public boolean atTargetDistance(){
        return (distanceError > -1) && (distanceError < 1);
    }

    public boolean atTargetAngle(){
        return (errorAngle > -0.5) && (errorAngle < 0.5);
    }

    public void stopDrive(){
        autoMode = AutoMode.UNKNOWN;
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

    public void prepareMotors(){

        for(int i=0; i<4; i++ ){
            driveMotors[i].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        driveMotors[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        for(int i=0; i<4; i++ ){
            driveMotors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }



    }

}