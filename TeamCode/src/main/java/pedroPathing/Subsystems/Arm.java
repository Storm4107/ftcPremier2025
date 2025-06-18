package pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Arm extends Subsystem {
    // BOILERPLATE
    public static final Arm INSTANCE = new Arm();
    private Arm() { }

    // USER CODE
    public Servo claw;
    public Servo rightArm;
    public Servo leftArm;
    public Servo secondArm;

    public String name = "claw";
    public String name1 = "rightArm";
    public String name2 = "leftArm";
    public String name3 = "secondArm";


//Claw Commands
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

 //Arm Commadns
    public Command wallPickup(){
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm,.24,this);
        ServoToPosition rigthArmCommand = new ServoToPosition(rightArm,.24,this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm,-1,this);
        ServoToPosition clawCommand = new ServoToPosition(claw,0.8,this);

        return new ParallelGroup(
                leftArmCommand,
                rigthArmCommand,
                secondArmCommand,
                clawCommand
        );
    }

    public Command handoff(){
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm,.365,this);
        ServoToPosition rightArmCommand = new ServoToPosition(rightArm,.365,this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm,.945,this);
        ServoToPosition clawCommand = new ServoToPosition(claw,0.9,this);

        return new ParallelGroup(
                leftArmCommand,
                rightArmCommand,
                secondArmCommand,
                clawCommand
        );
    }

    public Command ramSpec(){
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm,.57,this);
        ServoToPosition rightArmCommand = new ServoToPosition(rightArm,.57,this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm,.4,this);

        return new ParallelGroup(
                leftArmCommand,
                rightArmCommand,
                secondArmCommand
        );
    }

    public Command sampScore(){
        ServoToPosition leftArmCommand = new ServoToPosition(leftArm,.25,this);
        ServoToPosition rightArmCommand = new ServoToPosition(rightArm,.25,this);
        ServoToPosition secondArmCommand = new ServoToPosition(secondArm,.5,this);

        return new ParallelGroup(
                leftArmCommand,
                rightArmCommand,
                secondArmCommand
        );
    }
 //Combined Commands
    @Override
    public void initialize() {
        claw = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);
        rightArm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name1);
        leftArm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name2);
        secondArm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name3);

        rightArm.setDirection(Servo.Direction.REVERSE);
        secondArm.setDirection(Servo.Direction.REVERSE);
        claw.setDirection(Servo.Direction.REVERSE);
    }
}