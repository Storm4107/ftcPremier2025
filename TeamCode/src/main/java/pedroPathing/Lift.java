package pedroPathing;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.Controllable;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class Lift extends Subsystem {
    // BOILERPLATE
    public static final Lift INSTANCE = new Lift();

    private Lift() {}

    // USER CODE
    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0, new StaticFeedforward(0.0));

    public String leftName = "leftLift";
    public String rightName = "rightLift";

    MotorGroup elevator;

    public RunToPosition toLow() {
        return new RunToPosition(elevator, // MOTOR TO MOVE
                0.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public RunToPosition toMiddle() {
        return new RunToPosition(elevator, // MOTOR TO MOVE
                -500.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public RunToPosition toHigh() {
        return new RunToPosition(elevator, // MOTOR TO MOVE
                -1200.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    @Override
    public void initialize() {

        MotorEx rightMotor = new MotorEx("rightLift");
        MotorEx leftMotor = new MotorEx("leftLift");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        elevator = new MotorGroup(rightMotor, leftMotor);
    }
}