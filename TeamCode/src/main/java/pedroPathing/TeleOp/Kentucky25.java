package pedroPathing.TeleOp;

import static com.rowanmcalpin.nextftc.ftc.OpModeData.telemetry;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;

import com.rowanmcalpin.nextftc.ftc.OpModeData;
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

    public IMU imu;

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
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1(), false,imu);
        driverControlled.invoke();

        setGamePad2Commands();
        update();
    }
        public void setGamePad2Commands() {
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(master.INSTANCE::sampScore);
        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(master.INSTANCE::ramSpec);
        gamepadManager.getGamepad2().getDpadLeft().setPressedCommand(master.INSTANCE::fullIn);
        gamepadManager.getGamepad2().getDpadRight().setPressedCommand(master.INSTANCE::fullOut);

        gamepadManager.getGamepad2().getStart().setPressedCommand(master.INSTANCE::handoff);
        //gamepadManager.getGamepad2().getBack().setPressedCommand(master.INSTANCE::);

        gamepadManager.getGamepad2().getA().setPressedCommand(master.INSTANCE::wallPickup);
        gamepadManager.getGamepad2().getB().setPressedCommand(master.INSTANCE::sampPickUp);
        gamepadManager.getGamepad2().getY().setPressedCommand(master.INSTANCE::specPickup);
        gamepadManager.getGamepad2().getX().setPressedCommand(master.INSTANCE::pluck);

        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(master.INSTANCE::open);
        gamepadManager.getGamepad2().getRightBumper().setPressedCommand(master.INSTANCE::close);

        gamepadManager.getGamepad2().getLeftTrigger().setPressedCommand(master.INSTANCE::getIntakeCommand);
        gamepadManager.getGamepad2().getLeftTrigger().setReleasedCommand(master.INSTANCE::getIntakeOffCommand);
        gamepadManager.getGamepad2().getRightTrigger().setPressedCommand(master.INSTANCE::getEjectCommand);
        gamepadManager.getGamepad2().getRightTrigger().setReleasedCommand(master.INSTANCE::getIntakeOffCommand);
        }

    public void update(){
        //Executed on every update of the command
        telemetry.addData("CurrentPosition", master.INSTANCE.elevator.getCurrentPosition());
        telemetry.update();
    }
}