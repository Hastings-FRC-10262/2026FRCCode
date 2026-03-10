package frc.robot.subsystems.swervedrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.LimelightHelpers;
import frc.robot.subsystems.swervedrive.Leds;
import edu.wpi.first.math.geometry.Pose2d;

public class Limelight_LED_Test extends SubsystemBase {

    private final Leds ledstrip;
    private final String front;
    private final String back;
    public double angle;

    public Limelight_LED_Test(Leds led, String front, String back) {
        this.ledstrip = led;
        this.front = front;
        this.back = back;
        

        LimelightHelpers.setPipelineIndex(limelightName, 9);
    }
    
    public double getDistance() {
        return LimelightHelpers
                .getTargetPose3d_CameraSpace(limelightName)
                .getZ();
    }

    
//To Here
    public void getX(String limelightName) {
        Pose2d botPose = LimelightHelpers.getBotPose2d_wpiBlue(limelightName);
        double x = botPose.getX();
        System.out.println("Robot X: " + x);
        SmartDashboard.putNumber("Bot Pose X", x);
        return x
    }

    public void getY(String limelightName) {
        Pose2d botPose = LimelightHelpers.getBotPose2d_wpiBlue(limelightName);
        double y = botPose.getY();
        System.out.println("Robot Y: " + y);
        SmartDashboard.putNumber("Bot Pose Y", y); 
        return y
    }
    
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
