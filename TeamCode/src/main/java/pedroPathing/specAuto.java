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
    private final Pose chamberPose = new Pose(43,72,Math.toRadians(0));
    private final Pose wallPose = new Pose(10,16.2,Math.toRadians(0));
    private final Pose interPose = new Pose(63.2,16.2,Math.toRadians(0));
    private PathChain path1;
    private PathChain path2;
    private Path curve1;
    private Path curve2;
    private Telemetry telemetryA;
    private void buildPaths() {
        path1 = follower.pathBuilder()
            .addPath(new BezierLine(new Point(startPose), new Point(chamberPose)))
                .setLinearHeadingInterpolation(startPose.getHeading(), chamberPose.getHeading())
                .build();
        path2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(interPose), new Point(wallPose)))
                .setLinearHeadingInterpolation(interPose.getHeading(), wallPose.getHeading())
                .build();
        curve1 = new Path(new BezierCurve(new Point(43,72), new Point(-12, 22), new Point(75,49), new Point(65.5,28)));
        curve1.setLinearHeadingInterpolation(0.0, 0.0);

        curve2 = new Path(new BezierCurve(new Point(65.5, 28), new Point(-43.4,11.5), new Point(81.6,37.4), new Point(63.2,16.2)));
        curve2.setLinearHeadingInterpolation(0.0, 0.0);

    }
    private Command secondRoutine(){
        return new SequentialGroup(
                master.INSTANCE.ramSpec(),
                new FollowPath(path1),
                master.INSTANCE.open(),
                new Delay(1),
                new FollowPath(curve1),
                new FollowPath(curve2),
                master.INSTANCE.wallPickup(),
                new FollowPath(path2)
        );
    }
    @Override
    public void onInit(){
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        master.INSTANCE.autoStart();
        buildPaths();
    }
    @Override
    public void onStartButtonPressed() {
        secondRoutine().invoke();
    }
}
