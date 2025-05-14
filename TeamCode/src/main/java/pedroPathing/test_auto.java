package pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "test_auto")
public class test_auto extends PedroOpMode {
    public test_auto() {
        super(Lift.INSTANCE);
    }
    private final Pose startPose = new Pose(8.0, 108.0, Math.toRadians(0.0));
    private final Pose finishPose = new Pose(24, 108.0, Math.toRadians(0.0));
    private PathChain move;
    public void buildPaths() {
        move = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(finishPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), finishPose.getHeading())
                .build();
    }
    public SequentialGroup secondRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(move),
                        Lift.INSTANCE.toHigh()
                ),
                //Lift.INSTANCE.toMiddle(),
                new Delay(1.0)
                //Lift.INSTANCE.toLow()
        );
    }

    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void onStartButtonPressed() {
        secondRoutine().invoke();
    }
}