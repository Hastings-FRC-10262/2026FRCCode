package frc.robot.subsystems.swervedrive;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotoelectricSensor extends SubsystemBase {
    private static final double VOLTAGE_THRESHOLD = 2.5;
    private static final double SAMPLING_WINDOW = 2;

    private final AnalogInput photoelectricSensor;
    private static int onCount = 0;
    
    public PhotoelectricSensor(int pin) {
        photoelectricSensor = new AnalogInput(pin);
    }
    
    @Override
    public void periodic() {
        if (photoelectricSensor.getVoltage() < VOLTAGE_THRESHOLD) {
        onCount++;
        }
        else onCount = 0;
    }
    
    public boolean isTripped() {      
        boolean isitTripped = onCount >= SAMPLING_WINDOW;
        return isitTripped;
    }

    public double getVoltage() {
        return photoelectricSensor.getVoltage();
    }
}