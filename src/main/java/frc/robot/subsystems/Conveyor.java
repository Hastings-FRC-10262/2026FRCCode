package frc.robot.subsystems;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import frc.robot.Constants;
import frc.robot.Constants.IntakeSubsystemConstants;

public class Conveyor extends SubsystemBase {
    SparkFlex conveyorMotor =new SparkFlex(IntakeSubsystemConstants.kConveyorMotorCanId, MotorType.kBrushless);

    
    public Conveyor() {
    conveyorMotor.configure(
      Configs.IntakeSubsystem.conveyorConfig,
      ResetMode.kResetSafeParameters,
      PersistMode.kPersistParameters);

    }

    public void setConveyorPower(Double power) {
        conveyorMotor.set(power);
    }


    @Override
    public void periodic() {
        SmartDashboard.putNumber("Intake | Conveyor | Applied Output", conveyorMotor.getAppliedOutput());
    }
}