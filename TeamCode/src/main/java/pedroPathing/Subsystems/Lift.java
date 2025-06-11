package pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;


public class Lift extends Subsystem {
    // BOILERPLATE
    public static final Lift INSTANCE = new Lift();

    private Lift() {
    }

    // USER CODE
    public MotorGroup elevator;

    public PIDFController controller = new PIDFController(0.03, 0.0, 0.0, new StaticFeedforward(0.0), 100);

    public String leftLift = "leftLift";
    public String rightLift = "rightLift";

    public Command toLow() {
        return new RunToPosition(elevator, // MOTOR TO MOVE
                10.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }


    public Command toMiddle(){
            return new RunToPosition(elevator, // MOTOR TO MOVE
                    -500.0, // TARGET POSITION, IN TICKS
                    controller, // CONTROLLER TO IMPLEMENT
                    this); // IMPLEMENTED SUBSYSTEM
        }

    public Command toHigh(){
           return new RunToPosition(elevator, // MOTOR TO MOVE
                    -3000.0, // TARGET POSITION, IN TICKS
                    controller, // CONTROLLER TO IMPLEMENT
                    this); // IMPLEMENTED SUBSYSTEM
}




    @Override
    public void initialize() {
        MotorEx leftLift = new MotorEx("leftLift");
        MotorEx rightLift = new MotorEx("rightLift");

        rightLift.setDirection(DcMotorSimple.Direction.FORWARD);
        leftLift.setDirection(DcMotorSimple.Direction.REVERSE);

        elevator = new MotorGroup(leftLift, rightLift);
    }
}