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
    private Path straight3; //scoring
    private Path straight4;
    private Path straight5;
    private Path straight6; //scoring
    private Path straight7;
    private Path straight8;
    private Path straight9; //score
    private Path curve1;

    private void buildPaths() {
        curve1 = new Path(new BezierCurve(new Point(9,108), new Point(25, 114.6), new Point(15,127.4)));
        curve1.setLinearHeadingInterpolation(Math.toRadians(-90),Math.toRadians(-40));

        straight1 = new Path(new BezierCurve(new Point(15,127.4), new Point(9,118)));
        straight1.setLinearHeadingInterpolation(Math.toRadians(-40),Math.toRadians(0));

        straight2 = new Path(new BezierCurve(new Point(9,118), new Point(27,118)));
        straight2.setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(0));

        straight3 = new Path(new BezierCurve(new Point(27,118), new Point(15,127.4)));
        straight3.setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(-40));

        straight4 = new Path(new BezierCurve(new Point(16.7,122.4), new Point(13,128)));
        straight4.setLinearHeadingInterpolation(Math.toRadians(-40),Math.toRadians(0));

        straight5 = new Path(new BezierCurve(new Point(13,128), new Point(27,128)));
        straight5.setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(0));

        straight6 = new Path(new BezierCurve(new Point(27,128), new Point(15,127.4)));
        straight6.setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(-40));

        straight7 = new Path(new BezierCurve(new Point(15,127.4), new Point(19.5,121)));
        straight7.setLinearHeadingInterpolation(Math.toRadians(-40),Math.toRadians(27));

        straight8 = new Path(new BezierCurve(new Point(14,118.4), new Point(22,128.4)));
        straight8.setLinearHeadingInterpolation(Math.toRadians(27),Math.toRadians(33));

        straight9 = new Path(new BezierCurve(new Point(22,128.4), new Point(15,127.4)));
        straight9.setLinearHeadingInterpolation(Math.toRadians(33),Math.toRadians(-40));
    }

    private Command secondRoutine(){
        return new SequentialGroup(
                master.INSTANCE.sampScore(),
                new FollowPath(curve1),
                new Delay(.5),
                master.INSTANCE.open(),
                new Delay(.5),
                new ParallelGroup(
                        master.INSTANCE.autoPickup(),
                        master.INSTANCE.intake,
                        new FollowPath(straight1)
                ).endAfter(2),
                new FollowPath(straight2),
                master.INSTANCE.handoff(),
                master.INSTANCE.intakeOff,
                new Delay(.5),
                master.INSTANCE.close(),
                new Delay(.5),
                new ParallelGroup(
                        master.INSTANCE.sampScore(),
                        master.INSTANCE.eject
                ),
                master.INSTANCE.intakeOff,
                new FollowPath(straight3),
                new Delay(.5),
                master.INSTANCE.open(),
                new Delay(.5),
                new ParallelGroup(
                        master.INSTANCE.autoPickup(),
                        master.INSTANCE.intake,
                        new FollowPath(straight4)
                ),
                new FollowPath(straight5),
                master.INSTANCE.handoff(),
                master.INSTANCE.intakeOff,
                new Delay(.5),
                master.INSTANCE.close(),
                new Delay(.5),
                new ParallelGroup(
                        master.INSTANCE.sampScore(),
                        master.INSTANCE.eject
                ),
                master.INSTANCE.intakeOff,
                new FollowPath(straight6),
                new Delay(.5),
                master.INSTANCE.open(),
                new Delay(.5),
                new ParallelGroup(
                        master.INSTANCE.autoPickup(),
                        master.INSTANCE.intake,
                        new FollowPath(straight7)
                ).endAfter(10),
                new FollowPath(straight8),
                master.INSTANCE.handoff(),
                master.INSTANCE.intakeOff,
                new Delay(.5),
                master.INSTANCE.close(),
                new Delay(.5),
                new ParallelGroup(
                        master.INSTANCE.sampScore(),
                        master.INSTANCE.eject
                ),
                master.INSTANCE.intakeOff,
                master.INSTANCE.sampScore(),
                new FollowPath(straight9),
                new Delay(.5),
                master.INSTANCE.open(),
                new Delay(.5),
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
