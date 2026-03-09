package frc.robot.subsystems.swervedrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.swervedrive.Leds;

public class Limelight_LED_Test extends SubsystemBase {

    private final Leds ledstrip;
    private final String frontLimelight;
    private final String backLimelight;

    public Limelight_LED_Test(Leds led, String frontName, String backName) {
        this.ledstrip = led;
        this.frontLimelight = frontName;
        this.backLimelight = backName;

        // Set both to AprilTag pipeline (usually index 0 or your custom 9)
        LimelightHelpers.setPipelineIndex(frontLimelight, 9);
        LimelightHelpers.setPipelineIndex(backLimelight, 9);
    }

    /**
     * Gets X from a specific limelight by name
     */
    public double getX(String limelightName) {
        return LimelightHelpers.getBotPose2d_wpiBlue(limelightName).getX();
    }

    /**
     * Gets Y from a specific limelight by name
     */
    public double getY(String limelightName) {
        return LimelightHelpers.getBotPose2d_wpiBlue(limelightName).getY();
    }

    /**
     * Helper to check if a specific limelight sees a target
     */
    public boolean hasTarget(String limelightName) {
        return LimelightHelpers.getTV(limelightName);
    }

    @Override
    public void periodic() {
        // Logic: Check front first, then back
        String activeLimelight = null;

        if (hasTarget(frontLimelight)) {
            activeLimelight = frontLimelight;
        } else if (hasTarget(backLimelight)) {
            activeLimelight = backLimelight;
        }

        if (activeLimelight != null) {
            Pose2d botPose = LimelightHelpers.getBotPose2d_wpiBlue(activeLimelight);
            double x = botPose.getX();
            double y = botPose.getY();
            double distance = LimelightHelpers.getTargetPose3d_CameraSpace(activeLimelight).getZ();

            // Update Dashboard
            SmartDashboard.putString("Active Limelight", activeLimelight);
            SmartDashboard.putNumber("Bot Pose X", x);
            SmartDashboard.putNumber("Bot Pose Y", y);

            // LED Logic based on distance
            if (distance <= 1.0) {
                ledstrip.setYellow();
            } else {
                ledstrip.setWhite();
            }
        } else {
            SmartDashboard.putString("Active Limelight", "None");
            ledstrip.setOrange();
        }
    }
}
