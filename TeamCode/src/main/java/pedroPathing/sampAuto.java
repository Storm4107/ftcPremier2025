package pedroPathing;

import static java.lang.Math.PI;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import pedroPathing.Subsystem.master;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "sampAuto")
    public class sampAuto extends PedroOpMode {
    public sampAuto() {super(master.INSTANCE);}

    private final Pose startPose = new Pose(9,108,Math.toRadians(-90));

    private Path straight1;
    private Path straight2;
    private Path straight3;
    private Path straight4;
    private Path straight5;
    private Path straight6;
    private Path curve1;

    private void buildPaths() {
        curve1 = new Path(new BezierCurve(new Point(9,108), new Point(25, 114.6), new Point(14.7,120.4)));
        curve1.setLinearHeadingInterpolation(Math.toRadians(-90),Math.toRadians(-40));

        straight1 = new Path(new BezierCurve(new Point(14.7,120.4), new Point(13,118.5)));
        straight1.setLinearHeadingInterpolation(Math.toRadians(-40),Math.toRadians(0));

        straight2 = new Path(new BezierCurve(new Point(13,118.5), new Point(24,120.5)));
        straight2.setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(0));

        straight3 = new Path(new BezierCurve(new Point(24,120.5), new Point(16.7,122.4)));
        straight3.setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(-40));

        straight4 = new Path(new BezierCurve(new Point(16.7,122.4), new Point(13,128)));
        straight4.setLinearHeadingInterpolation(Math.toRadians(-40),Math.toRadians(0));

        straight5 = new Path(new BezierCurve(new Point(13,128), new Point(27,128)));
        straight5.setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(0));

        straight6 = new Path(new BezierCurve(new Point(27,128), new Point(14.7,124.4)));
        straight6.setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(-40));
    }

    private Command secondRoutine(){
        return new SequentialGroup(
                master.INSTANCE.sampScore(),
                new FollowPath(curve1),
                new Delay(1),
                master.INSTANCE.open(),
                new Delay(1),
                new ParallelGroup(
                        master.INSTANCE.autoPickup(),
                        master.INSTANCE.intake,
                        new FollowPath(straight1)
                ),
                new FollowPath(straight2),
                master.INSTANCE.handoff(),
                master.INSTANCE.intakeOff,
                new Delay(1),
                master.INSTANCE.close(),
                new Delay(1),
                master.INSTANCE.sampScore(),
                new FollowPath(straight3),
                new Delay(1),
                master.INSTANCE.open(),
                new Delay(1),
                new ParallelGroup(
                        master.INSTANCE.autoPickup(),
                        master.INSTANCE.intake,
                        new FollowPath(straight4)
                ),
                new FollowPath(straight5),
                master.INSTANCE.handoff(),
                master.INSTANCE.intakeOff,
                new Delay(1),
                master.INSTANCE.close(),
                new Delay(1),
                master.INSTANCE.sampScore(),
                new FollowPath(straight6),
                new Delay(1),
                master.INSTANCE.open(),
                new Delay(1),
                master.INSTANCE.handoff()
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
