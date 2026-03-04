package frc.robot.subsystems;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmSubsystemConstants;

public class Arm extends SubsystemBase {
    boolean IsArmDown;
    SparkFlex ArmMotor;
    AbsoluteEncoder absoluteecoder;
    PIDController armPID;
    public Arm() {
        IsArmDown=false;
        ArmMotor = new SparkFlex(ArmSubsystemConstants.kArmMotorCanId, MotorType.kBrushless);
        ArmMotor.configure(Configs.IntakeSubsystem.intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        absoluteecoder=ArmMotor.getAbsoluteEncoder();
        armPID = new PIDController(ArmSubsystemConstants.armkP, ArmSubsystemConstants.armkI, ArmSubsystemConstants.armkD);
        armPID.setTolerance(ArmSubsystemConstants.armTolerance);
    }
    public void periodic() {
        SmartDashboard.putNumber("arm encoder", absoluteecoder.getPosition());
        SmartDashboard.putNumber("setpoint", armPID.getSetpoint());
    }
}