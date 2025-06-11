package pedroPathing.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Extension extends Subsystem {
    // BOILERPLATE
    public static final Extension INSTANCE = new Extension();
    private Extension() { }

    // USER CODE
    //public Servo leftExtend;
    public Servo rightExtend;

    public String name = "rightExtend";
    //public String name1 = "leftExtend";


    public Command fullOut() {
        return new ServoToPosition(rightExtend,// SERVO TO MOVE
                .5, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
        //return new ServoToPosition(leftExtend,
            //    1,
            //    this);
    }

    public Command fullIn() {
        return new ServoToPosition(rightExtend,// SERVO TO MOVE
                0, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
       // return new ServoToPosition(leftExtend,
         //       0,
          //      this);
    }

    @Override
    public void initialize() {
        rightExtend = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name);
        //leftExtend = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, name1);

        rightExtend.setDirection(Servo.Direction.REVERSE);
    }
}