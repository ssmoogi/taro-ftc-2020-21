package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

// version 5 of taro's driver control code
// contains code to program robot from it's march version w/0 

@TeleOp(name="TeleOp_V5", group="Linear Opmode")
public class TeleOp_V5 extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    //name motor variables
    private DcMotor fldrive, frdrive, brdrive, bldrive, shooter, arm, intake; // conveyer;

    @Override
    public void runOpMode()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //map the motor variables to actual motors
        fldrive = hardwareMap.get(DcMotor.class, "fldrive");
        frdrive = hardwareMap.get(DcMotor.class, "frdrive");
        brdrive = hardwareMap.get(DcMotor.class, "brdrive");
        bldrive = hardwareMap.get(DcMotor.class, "bldrive");

        shooter = hardwareMap.get(DcMotor.class, "shooter");
        arm = hardwareMap.get(DcMotor.class, "arm");
        intake = hardwareMap.get(DcMotor.class, "intake");
        // conveyer = hardwareMap.get(DcMotor.class, "conveyer");

        //set direction of motors
        fldrive.setDirection(DcMotor.Direction.REVERSE);
        frdrive.setDirection(DcMotor.Direction.FORWARD);
        brdrive.setDirection(DcMotor.Direction.FORWARD);
        bldrive.setDirection(DcMotor.Direction.REVERSE);

        shooter.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);
        intake.setDirection(DcMotor.Direction.FORWARD);
        // conveyer.setDirection(DcMotor.Direction.FORWARD);

        // slow mode
        boolean slowmode = false;
        boolean xControl = false;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while (opModeIsActive())
        {
            // motion controls
            double speed = gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;
            double strafe = -gamepad1.left_stick_x;

            // arm controls
            double armfor = gamepad1.left_trigger;
            double armback = gamepad1.right_trigger;

            // shooter controls
            double shootfor = gamepad2.right_trigger;
            double shootback = gamepad2.left_trigger;

            // intake & conveyer controls
            double takein = gamepad2.right_stick_y;
            // double convey = gamepad2.left_stick_y;

            //determine power for each motor
            double fl = speed + turn + strafe;
            double fr = speed - turn - strafe;
            double br = speed - turn + strafe;
            double bl = speed + turn - strafe;

            // slow mode
            if (slowmode){
                fl /= 10;
                fr /= 10;
                bl /= 10;
                br /= 10;
            }

            //set power to drivetrain motors with range of -1 to 1
            fldrive.setPower(Range.clip(fl, -1.0, 1.0));
            frdrive.setPower(Range.clip(fr, -1.0, 1.0));
            brdrive.setPower(Range.clip(br, -1.0, 1.0));
            bldrive.setPower(Range.clip(bl, -1.0, 1.0));

            //set power and direction for other motors
            shooter.setPower(Range.clip(shootfor, -1.0, 0.0));
            shooter.setPower(Range.clip(shootback, 0.0, 1.0));

            arm.setPower(Range.clip(armfor, -1.0, 0.0));
            arm.setPower(Range.clip(armback, 0.0, 1.0));

            intake.setPower(Range.clip(takein, -1.0, 1.0));
            // conveyer.setPower(Range.clip(convey, -1.0, 1.0));

            if (gamepad1.x && xControl){
                slowmode = !slowmode;
            }

            xControl = !gamepad1.x;

            //debug messages for each motor
            telemetry.addData("fldrive",Double.toString(fldrive.getPower()));
            telemetry.addData("frdrive",Double.toString(frdrive.getPower()));
            telemetry.addData("brdrive",Double.toString(brdrive.getPower()));
            telemetry.addData("bldrive",Double.toString(bldrive.getPower()));

            telemetry.addData("shooter",Double.toString(shooter.getPower()));
            telemetry.addData("arm",Double.toString(shooter.getPower()));
            telemetry.addData("intake",Double.toString(shooter.getPower()));

            telemetry.update();
        }
    }
}
