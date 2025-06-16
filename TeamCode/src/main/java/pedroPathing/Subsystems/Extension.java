package pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Extension extends Subsystem {
    // BOILERPLATE
    public static final Extension INSTANCE = new Extension();
    private Extension() { }

    // USER CODE
    public Servo leftExtend; // plugged into port 0 on the expansion hub
    public Servo rightExtend; // plugged into port 5 on the control hub
    public Servo rightWrist;
    public Servo leftWrist;
    public Servo secondWrist; // plugged into Port 0 on the Control hub
    public CRServo leftIntake;
    public CRServo rightIntake;

    public String name = "rightExtend";
    public String name1 = "leftExtend";
    public String name2 = "rightWrist";
    public String name3 = "leftWrist";
    public String name4 = "secondWrist";
    public String name5 = "leftIntake";
    public String name6 = "rightIntake";

//Extension Commands
    public Command fullOut(){
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend,.3,this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend,.31,this);

        return new ParallelGroup(
                rightExtendCommand,
                leftExtendCommand
        );
    }

    public Command fullIn(){
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend,.55,this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend,.55,this);

        return new ParallelGroup(
                rightExtendCommand,
                leftExtendCommand
        );
    }

//Wrist Commands
    public Command groundWrist(){
        // Create individual servo commands
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, 0.87, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, 1, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, 1, this);

        // Combine them into a ParallelCommandGroup
        // This command group will run all three commands simultaneously.
        // The handoff() command will finish when ALL sub-commands have finished.
        return new ParallelGroup(
                secondWristCommand,
                leftWristCommand,
                rightWristCommand
        );
    }
    public Command handoff(){
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, .5, this);
        ServoToPosition rightExtendCommand = new ServoToPosition(secondWrist, .55, this);
        ServoToPosition leftExtendCommand = new ServoToPosition(secondWrist, .55, this);

        return new ParallelGroup(
                secondWristCommand,
                rightExtendCommand,
                leftExtendCommand
        );
    }

    public void setIntakeSpeed(double speed){
        leftIntake.setPower(speed);
        rightIntake.setPower(speed);
    }

    public Command intake = new LambdaCommand()
            .setStart(()-> {
                leftIntake.setPower(1);
                rightIntake.setPower(1);
            });

    public Command getIntakeCommand(Float aFloat) {
        return intake;
    }

    public Command intakeOff = new LambdaCommand()
            .setStart(()-> {
                leftIntake.setPower(0);
                rightIntake.setPower(0);
            });

    public Command getIntakeOffCommand(Float aFloat) {
        return intakeOff;
    }


    public Command eject = new LambdaCommand()
            .setStart(()-> {
                leftIntake.setPower(-1);
                rightIntake.setPower(-1);
            });

    public Command getEjectCommand(Float aFloat) {
        return eject;
    }



//Combied Commands

    @Override
    public void initialize() {
        rightExtend = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);
        leftExtend = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name1);
        rightWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name2);
        leftWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name3);
        secondWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name4);
        leftIntake = OpModeData.INSTANCE.getHardwareMap().get(CRServo.class, name5);
        rightIntake = OpModeData.INSTANCE.getHardwareMap().get(CRServo.class, name6);

        rightExtend.setDirection(Servo.Direction.REVERSE);
        leftWrist.setDirection(Servo.Direction.REVERSE);
        rightIntake.setDirection(CRServo.Direction.REVERSE);
    }
}