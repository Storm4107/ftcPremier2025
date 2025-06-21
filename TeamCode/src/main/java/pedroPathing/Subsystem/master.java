package pedroPathing.Subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.Controller;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

import pedroPathing.Commands.liftCommand;


public class master extends Subsystem {
    // BOILERPLATE
    public static final master INSTANCE = new master();
    private master() { }

    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0, new StaticFeedforward(0.0));

//Arm
    public Servo claw;
    public Servo rightArm;
    public Servo leftArm;
    public Servo secondArm;
    public String clawName = "claw";
    public String rightArmName = "rightArm";
    public String leftArmName = "leftArm";
    public String secondArmName = "secondArm";

//Extension
    public Servo leftExtend;
    public Servo rightExtend;
    public Servo rightWrist;
    public Servo leftWrist;
    public Servo secondWrist;
    public CRServo leftIntake;
    public CRServo rightIntake;

    public String leftExtendName = "leftExtend";
    public String rightExtendName = "rightExtend";
    public String rightWristName = "rightWrist";
    public String leftWristName = "leftWrist";
    public String secondWristName = "secondWrist";
    public String leftIntakeName = "leftIntake";
    public String rightIntakeName = "rightIntake";

//Elevator
    public MotorGroup elevator;

    public String leftLift = "leftLift";
    public String rightLift = "rightLift";

    public ServoToPosition open() {
        return new ServoToPosition(claw, // SERVO TO MOVE
                0.8, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public ServoToPosition close() {
        return new ServoToPosition(claw, // SERVO TO MOVE
                0.65, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command wallPickup() {
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm, .02, this);
        ServoToPosition rightArmCommand = new ServoToPosition(rightArm, .02, this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm, .15, this);
        ServoToPosition clawCommand = new ServoToPosition(claw, 0.85, this);
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, 0.59, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, .95, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, .95, this);
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend,-0.2,this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend, -0.2,this);
        RunToPosition elevatorCommand = new RunToPosition(elevator,1200,controller,this);

        return new ParallelGroup(
                leftArmCommand,
                rightArmCommand,
                secondArmCommand,
                clawCommand,
                secondWristCommand,
                leftWristCommand,
                rightWristCommand,
                rightExtendCommand,
                leftExtendCommand,
                elevatorCommand
        );
    }

    public Command handoff(){
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm,.365,this);
        ServoToPosition rightArmCommand = new ServoToPosition(rightArm,.365,this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm,.945,this);
        ServoToPosition clawCommand = new ServoToPosition(claw,0.9,this);
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, .47, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, .6, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, .6, this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend,-0.235,this);
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend, -0.235, this);
        RunToPosition elevatorCommand = new RunToPosition(elevator,0,controller,this);

        return new ParallelGroup(
                leftArmCommand,
                rightArmCommand,
                secondArmCommand,
                clawCommand,
                secondWristCommand,
                rightExtendCommand,
                leftExtendCommand,
                rightWristCommand,
                leftWristCommand,
                elevatorCommand
        );
    }

    public Command ramSpec(){
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm,.57,this);
        ServoToPosition rightArmCommand = new ServoToPosition(rightArm,.57,this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm,.4,this);
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, .47, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, .6, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, .6, this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend,-0.225,this);
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend, -0.225, this);
        RunToPosition elevatorCommand = new RunToPosition(elevator,1500,controller,this);

        return new ParallelGroup(
                leftArmCommand,
                rightArmCommand,
                secondArmCommand,
                secondWristCommand,
                leftWristCommand,
                rightWristCommand,
                rightExtendCommand,
                leftExtendCommand,
                elevatorCommand
        );
    }

    public Command sampScore(){
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm,.25,this);
        ServoToPosition rightArmCommand = new ServoToPosition(rightArm,.25,this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm,.4,this);
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, 0.59, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, .95, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, .95, this);
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend,-0.225,this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend, -0.225,this);
        RunToPosition elevatorCommand = new RunToPosition(elevator,4400,controller,this);

        return new ParallelGroup(
                leftArmCommand,
                rightArmCommand,
                secondArmCommand,
                secondWristCommand,
                leftWristCommand,
                rightWristCommand,
                rightExtendCommand,
                leftExtendCommand,
                elevatorCommand
        );
    }

    public Command sampPickUp(){
        // Create individual servo commands
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, 0.59, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, .95, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, .95, this);

        // Combine them into a ParallelCommandGroup
        // This command group will run all three commands simultaneously.
        // The handoff() command will finish when ALL sub-commands have finished.
        return new ParallelGroup(
                secondWristCommand,
                leftWristCommand,
                rightWristCommand
        );
    }

    public Command fullOut(){
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend,0.31,this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend,0.31,this);

        return new ParallelGroup(
                rightExtendCommand,
                leftExtendCommand
        );
    }

    public Command fullIn(){
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend,-0.225,this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend,-0.225,this);

        return new ParallelGroup(
                rightExtendCommand,
                leftExtendCommand
        );
    }

    public Command specPickup(){
        // Create individual servo commands
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, 0.9, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, .5, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, .5, this);

        // Combine them into a ParallelCommandGroup
        // This command group will run all three commands simultaneously.
        // The handoff() command will finish when ALL sub-commands have finished.
        return new ParallelGroup(
                secondWristCommand,
                leftWristCommand,
                rightWristCommand
        );
    }

    public Command pluck(){
        // Create individual servo commands
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, 1.0, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, .8, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, .8, this);

        // Combine them into a ParallelCommandGroup
        // This command group will run all three commands simultaneously.
        // The handoff() command will finish when ALL sub-commands have finished.
        return new ParallelGroup(
                secondWristCommand,
                leftWristCommand,
                rightWristCommand
        );
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

    public void initialize(){
//Arm
        claw = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,clawName);
        rightArm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,rightArmName);
        leftArm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,leftArmName);
        secondArm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,secondArmName);

        claw.setDirection(Servo.Direction.REVERSE);
        rightArm.setDirection(Servo.Direction.REVERSE);
        secondArm.setDirection(Servo.Direction.REVERSE);

//Extension
        leftExtend = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,leftExtendName);
        rightExtend = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,rightExtendName);
        rightWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,rightWristName);
        leftWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,leftWristName);
        secondWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,secondWristName);
        leftIntake = OpModeData.INSTANCE.getHardwareMap().get(CRServo.class,leftIntakeName);
        rightIntake = OpModeData.INSTANCE.getHardwareMap().get(CRServo.class,rightIntakeName);

        rightExtend.setDirection(Servo.Direction.REVERSE);
        leftWrist.setDirection(Servo.Direction.REVERSE);
        rightIntake.setDirection(CRServo.Direction.REVERSE);

//Lift
        MotorEx leftLift = new MotorEx("leftLift");
        MotorEx rightLift = new MotorEx("rightLift");

        rightLift.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLift.setDirection(DcMotorSimple.Direction.FORWARD);

        elevator = new MotorGroup(leftLift, rightLift);
    }
}
