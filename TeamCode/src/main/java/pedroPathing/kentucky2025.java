package pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;

import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;


import pedroPathing.Subsystems.Arm;
import pedroPathing.Subsystems.Extension;
import pedroPathing.Subsystems.Lift;

@TeleOp(name = "kentucky2025")
public class kentucky2025 extends NextFTCOpMode {

    public kentucky2025() {
        super(Lift.INSTANCE, Extension.INSTANCE, Arm.INSTANCE);
    }

    public String frontLeftName = "leftFront";
    public String frontRightName = "rightFront";
    public String backLeftName = "leftRear";
    public String backRightName = "rightRear";

    public MotorEx frontLeftMotor;
    public MotorEx frontRightMotor;
    public MotorEx backLeftMotor;
    public MotorEx backRightMotor;
    public MotorEx[] motors;

    public Command driverControlled;

    @Override
    public void onInit() {
        frontLeftMotor = new MotorEx(frontLeftName);
        backLeftMotor = new MotorEx(backLeftName);
        backRightMotor = new MotorEx(backRightName);
        frontRightMotor = new MotorEx(frontRightName);

        // Change your motor directions to suit your robot.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        motors = new MotorEx[]{frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor};
    }

    @Override
    public void onUpdate() {
        telemetry.addData("position",Lift.INSTANCE.elevator.getCurrentPosition());
        telemetry.update();
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());
        driverControlled.invoke();
        setGamePad2Commands();
    }
    public void setGamePad2Commands() {
        //gamepadManager.getGamepad1().getDpadUp().setPressedCommand(Lift.INSTANCE::toHigh);// //
        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(()-> new ParallelGroup(Lift.INSTANCE.toHigh(), Arm.INSTANCE.sampScore()));
        //gamepadManager.getGamepad1().getDpadDown().setPressedCommand(Lift.INSTANCE::toLow);
        gamepadManager.getGamepad1().getB().setPressedCommand(Lift.INSTANCE::chamber);//
        gamepadManager.getGamepad1().getDpadRight().setReleasedCommand(Extension.INSTANCE::fullOut);
        gamepadManager.getGamepad1().getDpadLeft().setReleasedCommand(Extension.INSTANCE::fullIn);
        gamepadManager.getGamepad1().getA().setPressedCommand(Extension.INSTANCE::groundWrist);
        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(Arm.INSTANCE::close);
        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(Arm.INSTANCE::open);
        gamepadManager.getGamepad1().getB().setPressedCommand(Arm.INSTANCE::ramSpec);
       // gamepadManager.getGamepad1().getDpadUp().setPressedCommand(Arm.INSTANCE::sampScore); //
        gamepadManager.getGamepad1().getX().setPressedCommand(Arm.INSTANCE::handoff);
        gamepadManager.getGamepad1().getX().setPressedCommand(Extension.INSTANCE::handoff);
        gamepadManager.getGamepad1().getY().setPressedCommand(Arm.INSTANCE::wallPickup);
        gamepadManager.getGamepad1().getRightTrigger().setPressedCommand(Extension.INSTANCE::getIntakeCommand);
        gamepadManager.getGamepad1().getRightTrigger().setReleasedCommand(Extension.INSTANCE::getIntakeOffCommand);
        gamepadManager.getGamepad1().getLeftTrigger().setPressedCommand(Extension.INSTANCE::getEjectCommand);
        gamepadManager.getGamepad1().getLeftTrigger().setReleasedCommand(Extension.INSTANCE::getIntakeOffCommand);

    }
}