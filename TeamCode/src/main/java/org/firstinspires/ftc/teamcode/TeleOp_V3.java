package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

// version 3 of taro's driver control code
// basic drivetrain opmode + shooter code (note: second version has servo code)

@TeleOp(name="TeleOp_V3", group="Linear Opmode")
public class TeleOp_V3 extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    //name motor variables
    private DcMotor fldrive, frdrive, brdrive, bldrive, lshooter, rshooter;

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

        lshooter = hardwareMap.get(DcMotor.class, "lshooter");
        rshooter = hardwareMap.get(DcMotor.class, "rshooter");

        //set direction of motors
        fldrive.setDirection(DcMotor.Direction.REVERSE);
        frdrive.setDirection(DcMotor.Direction.FORWARD);
        brdrive.setDirection(DcMotor.Direction.FORWARD);
        bldrive.setDirection(DcMotor.Direction.REVERSE);

        lshooter.setDirection(DcMotor.Direction.FORWARD);
        rshooter.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while (opModeIsActive())
        {
            double speed = gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;
            double strafe = -gamepad1.left_stick_x;
            double shooter = gamepad1.right_trigger;
            boolean y = gamepad1.y;

            //determine power for each motor
            double fl = speed+turn+strafe;
            double fr = speed-turn-strafe;
            double br = speed-turn+strafe;
            double bl = speed+turn-strafe;

            //set power to motors with range of -1 to 1
            fldrive.setPower(Range.clip(fl, -1.0, 1.0));
            frdrive.setPower(Range.clip(fr, -1.0, 1.0));
            brdrive.setPower(Range.clip(br, -1.0, 1.0));
            bldrive.setPower(Range.clip(bl, -1.0, 1.0));

            //set power and direction for shooter
            if (y)
            {
                lshooter.setPower(Range.clip(-shooter, -1.0, 0.0));
                rshooter.setPower(Range.clip(-shooter, -1.0, 0.0));
            }
            else
            {
                lshooter.setPower(Range.clip(shooter, 0.0, 1.0));
                rshooter.setPower(Range.clip(shooter, 0.0, 1.0));
            }


            //debug messages for each motor
            telemetry.addData("fldrive",Double.toString(fldrive.getPower()));
            telemetry.addData("frdrive",Double.toString(frdrive.getPower()));
            telemetry.addData("brdrive",Double.toString(brdrive.getPower()));
            telemetry.addData("bldrive",Double.toString(bldrive.getPower()));

            telemetry.addData("lshooter",Double.toString(lshooter.getPower()));
            telemetry.addData("rshooter",Double.toString(rshooter.getPower()));

            telemetry.update();
        }
    }
}
