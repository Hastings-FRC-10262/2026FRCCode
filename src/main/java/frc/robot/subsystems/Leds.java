package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.Spark;


public class Leds extends SubsystemBase {
    private final Spark m_Led;
    private final PhotoelectricSensor sensor;

    public Leds(int ledPin, PhotoelectricSensor sensor) {
        this.m_Led = new Spark(ledPin);
        this.sensor = sensor;
    }
    public void setGreen() {
        m_Led.set(0.77);
    }
    public void Gray() {
        m_Led.set(0.97);
    }
    public void setRed() {
        m_Led.set(0.61);
    }
    public void setPink() {
        m_Led.set(0.57);
    }
    public void setDarkRed() {
        m_Led.set(0.59);
    }
    public void Violet() {
        m_Led.set(0.91);
    }
    public void setOrange(){
        m_Led.set(0.65);
    }
    public void setYellow() {
        m_Led.set(0.69);
    }
    public void setWhite() {
        m_Led.set(0.93);
    }
    public void strobeWhite() {
        m_Led.set(0.05);
    }
    public void turnOff() {
        m_Led.set(0.0);
    }
    public void setRainbow(){
        m_Led.set(-0.99);
    }
    @Override
    public void periodic(){
        if (sensor.isTripped()){
            //setGreen();
            
            ;
        }else if(DriverStation.isDisabled()){
            //setRainbow();
            ;
        }else{
            //setRed();
            ;
        }
    }

    
}