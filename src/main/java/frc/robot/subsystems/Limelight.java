package frc.robot.subsystems.swervedrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.swervedrive.Leds;

public class Limelight_LED_Test extends SubsystemBase {

    private final Leds ledstrip;
    private final String limelightName;
    public double angle;

    public Limelight_LED_Test(Leds led, String limelightName) {
        this.ledstrip = led;
        this.limelightName = limelightName;

        LimelightHelpers.setPipelineIndex(limelightName, 0); 
    }

    public double getDistance() {
        return LimelightHelpers.getTargetPose3d_CameraSpace(limelightName).getZ();
    }

    public Pose2d getBotPose() {
        return LimelightHelpers.getBotPose2d_wpiBlue(limelightName);
    }

    public double getX() {
        Pose2d botPose = getBotPose();
        double x = botPose.getX();
        SmartDashboard.putNumber("Bot Pose X", x);
        return x;
    }

    public double getY() {
        Pose2d botPose = getBotPose();
        double y = botPose.getY();
        SmartDashboard.putNumber("Bot Pose Y", y);
        return y;
    }


    public void robotPeriodic() {

        LimelightHelpers.LimelightResults results = LimelightHelpers.getLatestResults(limelightName);

        LimelightHelpers.PoseEstimate tagCount = LimelightHelpers.getBotpose_tagCount("")

        if (tagcount > 0) {
            this.angle = LimelightHelpers.getTX(limelightName);
            double distanceMeters = getDistance();
            Pose2d botPose = getBotPose();

            SmartDashboard.putNumber("Limelight TX", angle);
            SmartDashboard.putNumber("Limelight Distance", distanceMeters);
            SmartDashboard.putNumber("Robot X", botPose.getX());
            SmartDashboard.putNumber("Robot Y", botPose.getY());

            if (distanceMeters <= 1.0) {
                ledstrip.setYellow(); // Close to target
            } else {
                ledstrip.setWhite();  // Tracking target but far
            }  
            LimelightHelpers.setLEDMode_ForceOn(limelightName);

        } else {
            ledstrip.setOrange();
            LimelightHelpers.setLEDMode_ForceOff(limelightName);
        }
    }
}
