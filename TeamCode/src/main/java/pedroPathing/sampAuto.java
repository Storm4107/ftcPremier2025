package pedroPathing;

import static java.lang.Math.PI;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import pedroPathing.Subsystem.master;
import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "sampAuto")
    public class sampAuto extends PedroOpMode {
    public sampAuto() {super(master.INSTANCE);}

    private final Pose startPose = new Pose(12,108,Math.toRadians(-90));

    private Path curve1;
    private Path curve2;
    private Path curve3;
    private Path curve4;
    private Path curve5;

    private void buildPaths() {
        curve1 = new Path(new BezierCurve(new Point(12,108), new Point(10.9, 112.7), new Point(10.9,121.2), new Point(15,128.5)));
        curve1.setLinearHeadingInterpolation(-90, -45);

        curve2 = new Path(new BezierCurve(new Point(15,128.5), new Point(17.6, 124.5), new Point(21.2,122.4), new Point(26,121)));
        curve2.setLinearHeadingInterpolation(0, 0);

        curve3 = new Path(new BezierCurve(new Point(26,121), new Point(21.5, 123), new Point(18,125.5), new Point(15,128.5)));
        curve3.setLinearHeadingInterpolation(0, 0);

        curve4 = new Path(new BezierCurve(new Point(15,128.5), new Point(19.3, 129.8), new Point(23.2,131.1), new Point(26,131.25)));
        curve4.setLinearHeadingInterpolation(0, 0);

        curve5 = new Path(new BezierCurve(new Point(26,131.25), new Point(23.2, 131.1), new Point(19.3,129.8), new Point(15,128.5)));
        curve5.setLinearHeadingInterpolation(0, 0);
    }

    private Command secondRoutine(){
        return new SequentialGroup(
                new FollowPath(curve1)
                /*new FollowPath(curve2),
                new FollowPath(curve3),
                new FollowPath(curve4),
                new FollowPath(curve5)*/
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
