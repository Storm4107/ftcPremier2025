package pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import pedroPathing.Subsystems.Lift;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "test_auto")
public class test_auto extends PedroOpMode {
    public test_auto() {
        super(Lift.INSTANCE);
    }
    private final Pose startPose = new Pose(8.0, 108.0, Math.toRadians(0.0));
    private final Pose interPose = new Pose(30, 124, Math.toRadians(0));
    private final Pose latePose = new Pose(40, 85, Math.toRadians(90));
    private final Pose finishPose = new Pose(24, 108.0, Math.toRadians(0.0));
    private PathChain firstPath;
    private PathChain secondPath;
    private PathChain thirdPath;
    private Telemetry telemetryA;
    private void buildPaths() {
        firstPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(interPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), interPose.getHeading())
                .build();

        secondPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(interPose), new Point(latePose)))
                .setLinearHeadingInterpolation(interPose.getHeading(), latePose.getHeading())
                .build();

        thirdPath = follower.pathBuilder()
                .addPath(new BezierLine(new Point(latePose), new Point(finishPose)))
                .setLinearHeadingInterpolation(latePose.getHeading(), finishPose.getHeading())
                .build();
    }
    private Command secondRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(firstPath),
                        Lift.INSTANCE.toHigh()
                ),
                new Delay(2),
                new ParallelGroup(
                        new FollowPath(secondPath)
                        //Lift.INSTANCE.toLow()
                ),
                new Delay(2),
                new ParallelGroup(
                        new FollowPath(thirdPath)
                        //Lift.INSTANCE.toMiddle()
                )
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