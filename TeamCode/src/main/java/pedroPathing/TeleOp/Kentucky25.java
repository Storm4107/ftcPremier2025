package pedroPathing.TeleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;

import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;

import pedroPathing.Subsystem.master;

@TeleOp(name = "kentucky25")
public class Kentucky25 extends NextFTCOpMode {

    public Kentucky25() {
        super(master.INSTANCE);
    }

    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);

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
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());
        driverControlled.invoke();

        setGamePad2Commands();
    }
        public void setGamePad2Commands() {
        gamepadManager.getGamepad2().getDpadUp().getPressedCommand(master.INSTANCE::sampScore);
        gamepadManager.getGamepad2().getDpadDown().getPressedCommand(master.INSTANCE::ramSpec);
        gamepadManager.getGamepad2().getDpadLeft().getPressedCommand(master.INSTANCE::fullIn);
        gamepadManager.getGamepad2().getDpadRight().getPressedCommand(master.INSTANCE::fullOut);

        gamepadManager.getGamepad2().getX().getPressedCommand(master.INSTANCE::handOff);
        gamepadManager.getGamepad2().getA().getPressedCommand(master.INSTANCE::wallPickup);
        gamepadManager.getGamepad2().getB().getPressedCommand(master.INSTANCE::groundWrist);

        gamepadManager.getGamepad2().getLeftBumper().getPressedCommand(master.INSTANCE::open);
        gamepadManager.getGamepad2().getRightBumper().getPressedCommand(master.INSTACNE::close);

        gamepadManager.getGamepad1().getRightTrigger().setPressedCommand(master.INSTANCE::getIntakeCommand);
        gamepadManager.getGamepad1().getRightTrigger().setReleasedCommand(master.INSTANCE::getIntakeOffCommand);
        gamepadManager.getGamepad1().getLeftTrigger().setPressedCommand(master.INSTANCE::getEjectCommand);
        gamepadManager.getGamepad1().getLeftTrigger().setReleasedCommand(master.INSTANCE::getIntakeOffCommand);
        }
}