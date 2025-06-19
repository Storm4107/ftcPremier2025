package pedroPathing.Subsystem;

import android.graphics.Path;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;

import pedroPathing.Commands.liftCommand;

public class master extends Subsystem {
    // BOILERPLATE
    public static final master INSTANCE = new master();
    private master() { }

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
    public String secondWristName = "secondWrsit";
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
                0.62, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command wallPickup() {
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm, .24, this);
        ServoToPosition rightArmCommand = new ServoToPosition(rightArm, .24, this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm, -1, this);
        ServoToPosition clawCommand = new ServoToPosition(claw, 0.8, this);
        ServoToPosition secondWristCommand = new ServoToPosition(secondWrist, 0.59, this);
        ServoToPosition leftWristCommand = new ServoToPosition(leftWrist, .95, this);
        ServoToPosition rightWristCommand = new ServoToPosition(rightWrist, .95, this);
        ServoToPosition rightExtendCommand = new ServoToPosition(rightExtend,-0.2,this);
        ServoToPosition leftExtendCommand = new ServoToPosition(leftExtend, -0.2,this);
        liftCommand elevatorCommand = new liftCommand(this, ()-> 0.0);

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
        leftWrist = OpModeData.INSTANCE.getHardwareMap().get(Servo.class,leftExtendName);
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
