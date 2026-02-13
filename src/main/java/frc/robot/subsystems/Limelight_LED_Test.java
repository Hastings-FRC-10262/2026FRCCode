package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Leds;

public class Limelight_LED_Test extends SubsystemBase {

    private final Leds ledstrip;
    private final String limelightName;
    public double angle;

    public Limelight_LED_Test(Leds led, String limelightName,int PortNum) {
        this.ledstrip = led;
        this.limelightName = limelightName;

        LimelightHelpers.setPipelineIndex(limelightName, 9);
    }
    
    public double getDistance() {
        return LimelightHelpers
                .getTargetPose3d_CameraSpace(limelightName)
                .getZ();
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
            double distanceMeters = getDistance();
            Pose2d botPose = getBotPose();

            System.out.println("AprilTag seen");
            System.out.println("Robot X: " + botPose.getX());
            System.out.println("Robot Y: " + botPose.getY());
            System.out.println("Robot Heading: " + botPose.getRotation().getDegrees());
            System.out.println("Camera Angle TX: " + angle);
            System.out.println("Distance (m): " + distanceMeters);

            if (distanceMeters <= 1.0) {
                ledstrip.setYellow();
            } else {
                ledstrip.setWhite();
            }

            LimelightHelpers.setLEDMode_ForceOn(limelightName);

        } else {

            System.out.println("AprilTag NOT seen");

            ledstrip.setOrange();
            LimelightHelpers.setLEDMode_ForceOff(limelightName);
        }
    }
}