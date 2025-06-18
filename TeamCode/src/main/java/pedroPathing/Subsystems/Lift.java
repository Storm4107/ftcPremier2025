package pedroPathing.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.acmerobotics.dashboard.message.redux.ReceiveGamepadState;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadEx;
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadManager;
import com.rowanmcalpin.nextftc.ftc.gamepad.JoystickAxis;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;


public class Lift extends Subsystem {
    // BOILERPLATE
    public static final Lift INSTANCE = new Lift();


    private Lift() {
    }

    // USER CODE
    public MotorGroup elevator;
    public String leftLift = "leftLift";
    public String rightLift = "rightLift";

    public Command toLow(){
            return new liftCommand(this,
                    ()-> 1.0);
        }

    public Command toHigh(){
        return new liftCommand(this,
                ()-> 3000.0);
}
    public Command chamber() {
        return new liftCommand(this,
                () -> 1500.0);
    }

    @Override
    public void initialize() {
        MotorEx leftLift = new MotorEx("leftLift");
        MotorEx rightLift = new MotorEx("rightLift");

        rightLift.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLift.setDirection(DcMotorSimple.Direction.FORWARD);

        elevator = new MotorGroup(leftLift, rightLift);
    }
}