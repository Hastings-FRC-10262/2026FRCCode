package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Leds;
import edu.wpi.first.math.geometry.Pose2d;

public class Limelight extends SubsystemBase {

    private final Leds ledstrip;
    private final String limelightName;
    public double angle;

    public Limelight(Leds led, String limelightName) {
        this.ledstrip = led;
        this.limelightName = limelightName;

        LimelightHelpers.setPipelineIndex(limelightName, 9);
    }


    public Pose2d getBotPose() {
        return LimelightHelpers.getBotPose2d_wpiBlue(limelightName);
    }



    
    @Override
    public void periodic() {

        LimelightHelpers.LimelightResults results =
                LimelightHelpers.getLatestResults(limelightName);

        boolean seesAprilTag =
                results != null &&
                results.valid &&
                results.targets_Fiducials != null &&
                results.targets_Fiducials.length > 0;

        if (seesAprilTag) {

            this.angle = LimelightHelpers.getTX(limelightName);
            Pose2d botPose = getBotPose();
            System.out.println("AprilTag seen");
            System.out.println("Robot X: " + botPose.getX());
            System.out.println("Robot Y: " + botPose.getY());
            System.out.println("Robot Heading: " + botPose.getRotation().getDegrees());
            System.out.println("Camera Angle TX: " + angle);

            LimelightHelpers.setLEDMode_ForceOn(limelightName);

        } else {

            System.out.println("AprilTag NOT seen");

            ledstrip.setOrange();
            LimelightHelpers.setLEDMode_ForceOff(limelightName);
        }
    }
}