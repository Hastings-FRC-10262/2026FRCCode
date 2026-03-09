package frc.robot.subsystems.swervedrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.LimelightHelpers;
import frc.robot.subsystems.swervedrive.Leds;
import edu.wpi.first.math.geometry.Pose2d;

public class Limelight_LED_Test extends SubsystemBase {

    private final Leds ledstrip;
    private final String limelightName;
    public double angle;

    public Limelight_LED_Test(Leds led, String limelightName) {
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

    
//From Here
    public double getX(Pose3d DataLoc){
        return DataLoc[0];
    }
    public double getY(Pose3d DataLoc){
        return DataLoc[1];
    }

    public void testkabeer() {
        LimelightHelpers.Pose3d botPose3d = LimelightHelpers.getBotpose_wpiBlue("")
        LimelightHelpers.PoseEstimate tagCount = LimelightHelpers.getBotpose_tagCount("")
    
        if (pose3d != null && tagCount > 0) {

            System.out.println(botPose3d);
            System.out.println(botPose3d[0]);
            System.out.println(botPose3d[1]);
    
            SmartDashboard.putNumber("Bot Pose X", botPose3d[0]);
            SmartDashboard.putNumber("Bot Pose Y", botPose3d[1]);
        } else {
            System.out.println("Bot Pose: Unknown/Invalid");
        }
    }
//To Here

    public void test() {

        Pose2d botPose = LimelightHelpers.getBotPose2d_wpiBlue(limelightName);
    
        var results = LimelightHelpers.getLatestResults(limelightName).targetingResults;
        double tagCount = results.targets_Fiducials.length;

        if (results.valid && tagCount > 0) {
            double x = botPose.getX();
            double y = botPose.getY();

            System.out.println("Robot X: " + x);
            System.out.println("Robot Y: " + y);
            SmartDashboard.putNumber("Bot Pose X", x);
            SmartDashboard.putNumber("Bot Pose Y", y);
        } else {
            System.out.println("Bot Pose: No Tags Seen");
        }
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
            } 
            else {
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
