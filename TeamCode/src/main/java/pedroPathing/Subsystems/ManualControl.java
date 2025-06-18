package pedroPathing.Subsystems;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.gamepad.GamepadEx;
import com.rowanmcalpin.nextftc.ftc.gamepad.Joystick;
import com.rowanmcalpin.nextftc.ftc.gamepad.JoystickAxis;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.Controllable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ManualControl extends Command {

    private final Controllable[] eleMotors;
    private final JoystickAxis elevatorSupplier;


    public ManualControl(Controllable[] motors, JoystickAxis driveSupplier) {
        this.eleMotors = motors;
        this.elevatorSupplier = driveSupplier;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Set<Subsystem> getSubsystems() {
        return new HashSet<>(Arrays.asList(Lift.INSTANCE));
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
    }
}