package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


//version 13 of team taro's autonomous code [STILL BEING WORKED ON, CURRENTLY V1]

@Autonomous(name="Auton_V3", group="Linear OpMode")

public class Auton_V3 extends LinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 385 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    private DcMotor fldrive, frdrive, bldrive, brdrive;

    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Intialized");
        telemetry.update();

        fldrive = hardwareMap.get(DcMotor.class, "fldrive");
        frdrive = hardwareMap.get(DcMotor.class, "frdrive");
        brdrive = hardwareMap.get(DcMotor.class, "brdrive");
        bldrive = hardwareMap.get(DcMotor.class, "bldrive");

        fldrive.setDirection(DcMotor.Direction.FORWARD);
        frdrive.setDirection(DcMotor.Direction.REVERSE);
        brdrive.setDirection(DcMotor.Direction.REVERSE);
        bldrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        fldrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brdrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bldrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        brdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // send telemetry message to indicate successful encoder reset
        telemetry.addData("Path0", "Starting at (fl) %7d (fr) :%7d (br) :%7d (bl) :%7d", fldrive.getCurrentPosition(), frdrive.getCurrentPosition(), brdrive.getCurrentPosition(), bldrive.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeout)
    {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = fldrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = frdrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            fldrive.setTargetPosition(newLeftTarget);
            bldrive.setTargetPosition(newLeftTarget);
            frdrive.setTargetPosition(newRightTarget);
            brdrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            fldrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            brdrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bldrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            fldrive.setPower(Math.abs(speed));
            frdrive.setPower(Math.abs(speed));
            brdrive.setPower(Math.abs(speed));
            bldrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.

            while (opModeIsActive() &&
                    (runtime.seconds() < timeout) &&
                    (fldrive.isBusy() && frdrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d", fldrive.getCurrentPosition(), frdrive.getCurrentPosition());
                telemetry.update();
            }

            // stop all motion
            fldrive.setPower(0);
            frdrive.setPower(0);
            brdrive.setPower(0);
            bldrive.setPower(0);

            // turn off RUN_TO_POSITION
            fldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            brdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bldrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // sleep(250);   // optional pause after each move
        }
    }
}