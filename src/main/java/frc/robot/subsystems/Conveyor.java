package frc.robot.subsystems;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import frc.robot.Constants.IntakeSubsystemConstants;
import frc.robot.Constants.IntakeSubsystemConstants.*;

public class Conveyor extends SubsystemBase {
    SparkFlex ConveyorMotor;
    SparkFlexConfig conveyorMotorConfig = new SparkFlexConfig();

    public Conveyor() {
        ConveyorMotor = new SparkFlex(IntakeSubsystemConstants.kConveyorMotorCanId, MotorType.kBrushless);
        conveyorMotorConfig = new SparkFlexConfig();
        //config of conveyor motor
        conveyorMotorConfig.inverted(true);
        conveyorMotorConfig.idleMode(IdleMode.kCoast);
        conveyorMotorConfig.openLoopRampRate(0.5);
        conveyorMotorConfig.smartCurrentLimit(40);

        ConveyorMotor.configure(conveyorMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    public void setConveyorPower(Double power) {
        ConveyorMotor.set(power);
    }


    @Override
    public void periodic() {
        
    }
}