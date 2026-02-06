package frc.robot.subsystems;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.io.ObjectInputFilter.Config;

import com.revrobotics.spark.SparkFlex;

import com.revrobotics.spark.SparkBase.*;
import com.revrobotics.spark.SparkLowLevel.MotorType;


import frc.robot.Constants.IntakeSubsystemConstants;
import frc.robot.Constants.IntakeSubsystemConstants.*;

import frc.robot.subsystems.Conveyor;

public class Intake extends SubsystemBase {
    SparkFlex IntakeMotor;
    Conveyor m_conveyor;
    public Intake(Conveyor conveyor) {
        IntakeMotor = new SparkFlex(IntakeSubsystemConstants.kIntakeMotorCanId, MotorType.kBrushless);
        
        //put tthe thinging from the constructor inside the other thingy i put at the top
        m_conveyor = conveyor;



        IntakeMotor.configure(Configs.IntakeSubsystem.intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }


    private void setIntakePower(double power) {
        IntakeMotor.set(power);
    }
    //rember its power not veolicty lock in

    //ALL COMMANDS SHOULD BE RUN ON M_CONVEYOR NOT CONVEYOR beacause i said so

    public Command runIntakeCommand() {
        return this.startEnd(
            () -> {
                this.setIntakePower(IntakeSetpoints.kIntake);
                m_conveyor.setConveyorPower(ConveyorSetpoints.kIntake);
            }, () -> {
                this.setIntakePower(0.0);
                m_conveyor.setConveyorPower(0.0);
            }).withName("Intaking");
    }

    public Command runExtakeCommand() {
        return this.startEnd(
            () -> {
                this.setIntakePower(IntakeSetpoints.kExtake);
                m_conveyor.setConveyorPower(ConveyorSetpoints.kExtake);
            }, () -> {
                this.setIntakePower(0.0);
                m_conveyor.setConveyorPower(0.0);
            }).withName("Extaking");
  }

    public Command testIntakeMotor(Double power) {
        return run(
            () -> {
              IntakeMotor.set(power);
            });
    }


    @Override
    public void periodic() {
        //pollute smart dashboard even more
        SmartDashboard.putNumber("Intake | Intake | Applied Output", IntakeMotor.getAppliedOutput());
    }
}