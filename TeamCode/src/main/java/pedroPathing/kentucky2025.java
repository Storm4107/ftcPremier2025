package pedroPathing;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
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
    public IMU imu;

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

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        imu.resetYaw();
    }

    @Override
    public void onUpdate() {
        telemetry.addData("position",Lift.INSTANCE.elevator.getCurrentPosition());
        telemetry.update();
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1(), false, imu);
        driverControlled.invoke();
        setGamePad2Commands();
        setGamePad1Commands();
    }
    public void setGamePad2Commands() {
        /*
        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(()-> new ParallelGroup(Lift.INSTANCE.toHigh(), Arm.INSTANCE.sampScore()));
        gamepadManager.getGamepad1().getRightTrigger().setPressedCommand(Value -> new SequentialGroup(Lift.INSTANCE.chamber(), Arm.INSTANCE.ramSpec()));
        //gamepadManager.getGamepad1().getB().setPressedCommand(Lift.INSTANCE::chamber);
        gamepadManager.getGamepad1().getDpadRight().setReleasedCommand(Extension.INSTANCE::fullOut);
        gamepadManager.getGamepad1().getDpadLeft().setReleasedCommand(Extension.INSTANCE::fullIn);
        gamepadManager.getGamepad1().getA().setPressedCommand(Extension.INSTANCE::groundWrist);
        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(Arm.INSTANCE::close);
        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(Arm.INSTANCE::open);
        gamepadManager.getGamepad1().getB().setPressedCommand(Arm.INSTANCE::ramSpec);
        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(Arm.INSTANCE::sampScore);
        gamepadManager.getGamepad1().getX().setPressedCommand(()-> new ParallelGroup(Arm.INSTANCE.handoff(), Extension.INSTANCE.handoff()));
        gamepadManager.getGamepad1().getY().setPressedCommand(Arm.INSTANCE::wallPickup);
       /* gamepadManager.getGamepad1().getRightTrigger().setPressedCommand(Extension.INSTANCE::getIntakeCommand);
        gamepadManager.getGamepad1().getRightTrigger().setReleasedCommand(Extension.INSTANCE::getIntakeOffCommand);
        gamepadManager.getGamepad1().getLeftTrigger().setPressedCommand(Extension.INSTANCE::getEjectCommand);
        gamepadManager.getGamepad1().getLeftTrigger().setReleasedCommand(Extension.INSTANCE::getIntakeOffCommand);
*/
        //elevator
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(Lift.INSTANCE::toHigh);
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(Lift.INSTANCE::chamber);
        gamepadManager.getGamepad2().getStart().setPressedCommand(Lift.INSTANCE::toLow);
        //Extension
        gamepadManager.getGamepad2().getDpadLeft().setPressedCommand(Extension.INSTANCE::fullIn);
        gamepadManager.getGamepad2().getDpadRight().setPressedCommand(Extension.INSTANCE::fullOut);
        //gamepadManager.getGamepad2().get(INSERT SOMETHING).setPressedCommand(Extension.INSTANCE::middle); Make this a middle preset at for the extension
        //Wrist
        gamepadManager.getGamepad2().getA().setPressedCommand(Extension.INSTANCE::groundWrist);
        gamepadManager.getGamepad2().getX().setPressedCommand(Extension.INSTANCE::handoff);
        //Intake
        gamepadManager.getGamepad1().getRightTrigger().setPressedCommand(Extension.INSTANCE::getIntakeCommand);
        gamepadManager.getGamepad1().getRightTrigger().setReleasedCommand(Extension.INSTANCE::getIntakeOffCommand);
        gamepadManager.getGamepad1().getLeftTrigger().setPressedCommand(Extension.INSTANCE::getEjectCommand);
        gamepadManager.getGamepad1().getLeftTrigger().setReleasedCommand(Extension.INSTANCE::getIntakeOffCommand);
        //claw
        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(Arm.INSTANCE::close);
        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(Arm.INSTANCE::open);
        //Arm
        gamepadManager.getGamepad2().getB().setPressedCommand(Arm.INSTANCE::ramSpec);
        gamepadManager.getGamepad2().getBack().setPressedCommand(Arm.INSTANCE::sampScore);
        gamepadManager.getGamepad2().getY().setPressedCommand(Arm.INSTANCE::wallPickup);
        gamepadManager.getGamepad2().getBack().setPressedCommand(Arm.INSTANCE::handoff);
    }
    public void setGamePad1Commands(){
        gamepadManager.getGamepad1().getX().setPressedCommand(Lift.INSTANCE::toHigh);
        gamepadManager.getGamepad1().getB().setPressedCommand(Lift.INSTANCE::chamber);
        gamepadManager.getGamepad1().getA().setPressedCommand(Lift.INSTANCE::toLow);
    }

}