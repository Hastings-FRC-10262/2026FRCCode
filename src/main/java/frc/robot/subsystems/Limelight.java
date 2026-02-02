package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.Leds.*;


public class Limelight extends SubsystemBase {

    private Leds ledstrip;
    private String ledname;

    public Limelight(Leds led,String Ledname) {
        this.ledstrip = led;
        this.ledname=Ledname;
    }

    @Override
    public void periodic() {
        LimelightHelpers.setPipelineIndex(ledname, 9);
        LimelightHelpers.LimelightResults results =
                LimelightHelpers.getLatestResults(ledname);
        boolean seesAprilTag =
                results != null &&
                results.targets_Fiducials != null &&
                results.targets_Fiducials.length > 0;
        System.out.println(results.valid);
        System.out.println(results.targets_Fiducials.length);
        System.out.println("tv = "+LimelightHelpers.getTV(ledname));
        System.out.println(LimelightHelpers.getLatency_Pipeline(ledname));
        if (seesAprilTag) {
            ledstrip.setWhite();
            System.out.println("April tag seen!");
            LimelightHelpers.setLEDMode_ForceOn(ledname);
        } else {
            ledstrip.setOrange();
            System.out.println("April tag not seen!");
            // System.out.println("April tag NOT seen!");
            LimelightHelpers.setLEDMode_ForceOff(ledname);
        }
    }
}
