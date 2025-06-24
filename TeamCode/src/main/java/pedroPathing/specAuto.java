package pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
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

import pedroPathing.Subsystem.master;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "specAuto")
public class specAuto extends PedroOpMode {
    public specAuto() {
        super(master.INSTANCE);
    }
    private final Pose startPose = new Pose(8,72,Math.toRadians(0));
    private final Pose chamberPose = new Pose(42,72,Math.toRadians(0));
    private final Pose wallPose = new Pose(8.25,16.2,Math.toRadians(0));
    private final Pose interPose = new Pose(63.2,16.2,Math.toRadians(0));
    private final Pose humanPose = new Pose(18,16.2, Math.toRadians(0));
    private final Pose closeHumanPose = new Pose(25,16.2,Math.toRadians(0));
    private PathChain path1;
    private PathChain path2;
    private PathChain path3;
    private PathChain path4;
    private Path curve1;
    private Path curve2;
    private Path curve3;
    private Path curve4;
    private Path curve5;
    private Telemetry telemetryA;
    private void buildPaths() {
        path1 = follower.pathBuilder()
            .addPath(new BezierLine(new Point(startPose), new Point(chamberPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), chamberPose.getHeading())
                .build();
        path2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(interPose), new Point(humanPose)))
                .setLinearHeadingInterpolation(interPose.getHeading(), humanPose.getHeading())
                .build();
        path3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(humanPose), new Point(closeHumanPose)))
                .setLinearHeadingInterpolation(humanPose.getHeading(), closeHumanPose.getHeading())
                .build();
        path4 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(closeHumanPose), new Point(wallPose)))
                .setLinearHeadingInterpolation(closeHumanPose.getHeading(), wallPose.getHeading())
                .build();
        curve1 = new Path(new BezierCurve(new Point(42,72), new Point(-12, 22), new Point(75,49), new Point(65.5,28)));
        curve1.setLinearHeadingInterpolation(0.0, 0.0);

        curve2 = new Path(new BezierCurve(new Point(65.5, 28), new Point(-43.4,11.5), new Point(81.6,37.4), new Point(63.2,16.2)));
        curve2.setLinearHeadingInterpolation(0.0, 0.0);

        curve3 = new Path(new BezierCurve(new Point(7, 16.2), new Point(19,72), new Point(39,78)));
        curve3.setLinearHeadingInterpolation(0.0, 0.0);

        curve4 = new Path(new BezierCurve(new Point(39, 70), new Point(8.25,54.2), new Point(8.25,21)));
        curve4.setLinearHeadingInterpolation(0.0, 0.0);

        curve5 = new Path(new BezierCurve(new Point(8.25, 21), new Point(8.25,54.2), new Point(39,70)));
        curve5.setLinearHeadingInterpolation(0.0, 0.0);
    }
    private Command secondRoutine(){
        return new SequentialGroup(
                master.INSTANCE.ramSpec(),
                new FollowPath(path1),
                master.INSTANCE.open(),
                new Delay(1),
                new FollowPath(curve1),
                new FollowPath(curve2),
                master.INSTANCE.wallPickup().endAfter(2),
                new FollowPath(path2),
                new FollowPath(path3),
                new Delay(2),
                new FollowPath(path4),
                master.INSTANCE.close(),
                new Delay(2),
                new ParallelGroup(
                        master.INSTANCE.ramSpec().endAfter(3),
                        new FollowPath(curve3)
                ),
                new Delay(1),
                master.INSTANCE.open(),
                new Delay(1),
                new ParallelGroup(
                        new FollowPath(curve4),
                        master.INSTANCE.wallPickup().endAfter(3)
                ),
                new Delay(1),
                master.INSTANCE.close(),
                new Delay(1),
                new ParallelGroup(
                        master.INSTANCE.ramSpec(),
                        new FollowPath(curve5)
                ),
                new Delay(1),
                master.INSTANCE.open(),
                new Delay(1),
                new ParallelGroup(
                        master.INSTANCE.handoff(),
                        new FollowPath(curve4)
                )
        );
    }
    @Override
    public void onInit(){
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
    }
    @Override
    public void onStartButtonPressed() {
        secondRoutine().invoke();
    }
}
