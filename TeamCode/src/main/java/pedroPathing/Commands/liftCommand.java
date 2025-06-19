package pedroPathing.Commands;

import static com.rowanmcalpin.nextftc.ftc.OpModeData.telemetry;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.OpModeData;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Set;
import java.util.function.Supplier;

import pedroPathing.Subsystem.master;

public class liftCommand extends Command {

    private final Subsystem subsystem;

    private Double setpoint;

    private PIDFController Controller;
    private final boolean interruptible = true;
    public liftCommand(Subsystem subsystem, Supplier<Double> setpointSupplier) {
        this.subsystem = subsystem;
        this.setpoint = setpointSupplier.get();
    }

    @Override
    public boolean isDone() {
        return Controller.atTarget(); // whether the command is true or not
    }

    @Override
    public void start(){
        //executed when the command begins
        Controller = new PIDFController(.015,0,0.,new StaticFeedforward(0),10);
    }

    @Override
    public void update(){
        //Executed on every update of the command
        master.INSTANCE.elevator.setPower(Controller.calculate(master.INSTANCE.elevator.getCurrentPosition(), setpoint));
    }

    @Override
    public void stop(boolean interrupted){
        //executed when the command ends
        master.INSTANCE.elevator.setPower(0);
    }

}