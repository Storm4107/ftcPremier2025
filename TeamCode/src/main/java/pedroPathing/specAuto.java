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
    private final Pose wallPose = new Pose(9,16.2,Math.toRadians(0));
    private final Pose interPose = new Pose(63.2,16.2,Math.toRadians(0));
    private final Pose humanPose = new Pose(18,16.2, Math.toRadians(0));
    private final Pose closeHumanPose = new Pose(25,16.2,Math.toRadians(0));
    private PathChain path1;
    private PathChain path2;
    private PathChain path3;
    private PathChain path4;

    private PathChain path5;
    private Path curve1;
    private Path curve2;
    private Path curve3;
    private Path curve4;
    private Path curve5;
    private Path curve6;
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
        path5 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(humanPose), new Point(chamberPose)))
                .setLinearHeadingInterpolation(closeHumanPose.getHeading(), wallPose.getHeading())
                .build();

        curve1 = new Path(new BezierCurve(new Point(42,72), new Point(-20, 10), new Point(84.85,53.17), new Point(57.15,23.16)));
        curve1.setLinearHeadingInterpolation(0.0, 0.0);

        curve2 = new Path(new BezierCurve(new Point(57.15, 23.16),new Point(15.95,25.7)));
        curve2.setLinearHeadingInterpolation(0.0, 0.0);

        curve3 = new Path(new BezierCurve(new Point(15.95, 25.7), new Point(68.01,47.41), new Point(57.16,17.5)));
        curve3.setLinearHeadingInterpolation(0.0, 0.0);

        curve4 = new Path(new BezierCurve(new Point(57.16, 17.5),new Point(25,16.2)));
        curve4.setLinearHeadingInterpolation(0.0, 0.0);

        curve5 = new Path(new BezierCurve(new Point(25, 16.2),new Point(30,16.2)));
        curve5.setLinearHeadingInterpolation(0.0, 0.0);

        curve6 = new Path(new BezierCurve(new Point(30, 16.2),new Point(9,16.2)));
        curve6.setLinearHeadingInterpolation(0.0, 0.0);
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
                new FollowPath(curve3),
                new FollowPath(curve4),
                new FollowPath(curve5),
                new Delay(2),
                new FollowPath(curve6),
                new Delay(1),
                master.INSTANCE.close(),
                new Delay(1),
                new ParallelGroup(
                        master.INSTANCE.ramSpec(),
                        new FollowPath(path5)
                ).endAfter(3),
                new Delay(1),
                master.INSTANCE.open(),
                new Delay(1),
                master.INSTANCE.handoff()
                /*new FollowPath(path3),
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
                )*/
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
