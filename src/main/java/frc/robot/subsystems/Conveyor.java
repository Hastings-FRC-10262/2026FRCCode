package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import javax.swing.Timer;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import frc.robot.Constants;
import frc.robot.Constants.IntakeSubsystemConstants;
import frc.robot.Constants.IntakeSubsystemConstants.ConveyorSetpoints;
import frc.robot.Constants.IntakeSubsystemConstants.IntakeSetpoints;

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
    public Command MoveConveyor(double speed) {
        return this.startEnd(
            () -> {
                this.setConveyorPower(-24.0);
            },  () -> {
                this.setConveyorPower(0.0);
            }).withName("conveyoring beackwards :)");
    }
    public Command alternateConveyorPower(Double power, Double time) {
        return Commands.repeatingSequence(
            this.runOnce(() -> {
                conveyorMotor.set(power);
            }).andThen(Commands.waitSeconds(time)),

            this.runOnce(() -> {
                conveyorMotor.set(0);
            }).andThen(Commands.waitSeconds(time))
        )
        .finallyDo(() -> {
            conveyorMotor.set(0);
        })
        .withName("Intaking");
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Intake | Conveyor | Applied Output", conveyorMotor.getAppliedOutput());
    }
}