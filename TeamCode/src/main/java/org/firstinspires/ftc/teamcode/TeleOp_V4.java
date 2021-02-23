package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

// version 4 of taro's driver control code
// contains code to program robot from it's february version w/ arm, one-motor shooter, and intake

@TeleOp(name="TeleOp_V4", group="Linear Opmode")
public class TeleOp_V4 extends LinearOpMode
{
    private ElapsedTime runtime = new ElapsedTime();

    //name motor variables
    private DcMotor fldrive, frdrive, brdrive, bldrive, shooter, arm, intake;

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

        //set direction of motors
        fldrive.setDirection(DcMotor.Direction.REVERSE);
        frdrive.setDirection(DcMotor.Direction.FORWARD);
        brdrive.setDirection(DcMotor.Direction.FORWARD);
        bldrive.setDirection(DcMotor.Direction.REVERSE);

        shooter.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);
        intake.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        while (opModeIsActive())
        {
            double speed = gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;
            double strafe = -gamepad1.left_stick_x;
            double shoot = gamepad1.right_trigger;
            boolean y = gamepad1.y;
            double ar = gamepad1.left_trigger;
            boolean b = gamepad1.b;
            boolean up = gamepad1.dpad_up;
            boolean down = gamepad1.dpad_down;


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
                shooter.setPower(Range.clip(-shoot, -1.0, 0.0));
            }
            else
            {
                shooter.setPower(Range.clip(shoot, 0.0, 1.0));
            }

            if (b)
            {
                arm.setPower(Range.clip(-ar, -0.5, 0.0));
            }
            else
            {
                arm.setPower(Range.clip(ar, 0.0, 0.5));
            }

            if (up)
            {
                intake.setPower(0.5);
            }
            else if (down)
            {
                intake.setPower(-0.5);
            }
            else
            {
                intake.setPower(0.0);
            }


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
