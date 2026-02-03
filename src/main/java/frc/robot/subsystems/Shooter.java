package frc.robot.subsystems;


import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.Constants.ShooterSubsystemConstants.FeederSetpoints;
import frc.robot.Constants.ShooterSubsystemConstants.FlywheelSetpoints;
import frc.robot.Constants;
import frc.robot.Constants.ShooterSubsystemConstants;
import frc.robot.Constants.IntakeSubsystemConstants.ConveyorSetpoints;
import frc.robot.subsystems.Conveyor;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase{
    
  private SparkFlex flywheelMotor = new SparkFlex(ShooterSubsystemConstants.kFlywheelMotorCanId, MotorType.kBrushless);
  private SparkClosedLoopController flywheelController = flywheelMotor.getClosedLoopController();
  private RelativeEncoder flywheelEncoder = flywheelMotor.getEncoder();

  private SparkFlex flywheelFollowerMotor = new SparkFlex(ShooterSubsystemConstants.kFlywheelFollowerMotorCanId, MotorType.kBrushless);

  // Initialize feeder SPARK. We will use open loop control for this so we don't need a closed loop
  // controller like above.
  private SparkFlex feederMotor = new SparkFlex(ShooterSubsystemConstants.kFeederMotorCanId, MotorType.kBrushless);

  private double flywheelTargetVelocity = 0.0;

  private Conveyor m_Conveyor;

  public Shooter(Conveyor conveyor) {
    
    final double nominalVoltage = 12.0;

    final SparkFlexConfig flywheelConfig = new SparkFlexConfig();
    final SparkFlexConfig flywheelFollowerConfig = new SparkFlexConfig();
    final SparkFlexConfig feederConfig = new SparkFlexConfig();
    
          flywheelConfig
        .inverted(true)
        .idleMode(IdleMode.kCoast)
        .closedLoopRampRate(1.0)
        .openLoopRampRate(1.0)
        .smartCurrentLimit(80);

      /*
       * Configure the closed loop controller. We want to make sure we set the
       * feedback sensor as the primary encoder.
       */
      flywheelConfig
        .closedLoop
          .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
          // Set PID values for position control
          .p(0.0002)
          .outputRange(-1, 1);

      flywheelConfig.closedLoop
        .maxMotion
          // Set MAXMotion parameters for MAXMotion Velocity control
          .cruiseVelocity(5000)
          .maxAcceleration(10000)
          .allowedProfileError(1);

      // Constants.NeoMotorConstants.kVortexKv is in rpm/V. feedforward.kV is in V/rpm sort we take
      // the reciprocol.
      flywheelConfig.closedLoop
        .feedForward.kV(nominalVoltage / Constants.NeoMotorConstants.kVortexKv);

      // Configure the follower flywheel motor to follow the main flywheel motor
      flywheelFollowerConfig.apply(flywheelConfig)
        .follow(Constants.ShooterSubsystemConstants.kFlywheelMotorCanId, true);

      // Configure basic setting of the feeder motor
      feederConfig
        .inverted(true)
        .idleMode(IdleMode.kCoast)
        .openLoopRampRate(1.0)
        .smartCurrentLimit(60);
    flywheelMotor.configure(
        flywheelConfig,
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);
    flywheelFollowerMotor.configure(
        flywheelFollowerConfig,
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);
    feederMotor.configure(
        feederConfig,
        ResetMode.kResetSafeParameters,
        PersistMode.kPersistParameters);

    //zero endor
    flywheelEncoder.setPosition(0);
  }

    private boolean isFlywheelAt(double velocity) {
    return MathUtil.isNear(flywheelEncoder.getVelocity(), 
            velocity, FlywheelSetpoints.kVelocityTolerance);
  }

  /** 
   * Trigger: Is the flywheel spinning at the required velocity?
   */
  public final Trigger isFlywheelSpinning = new Trigger(
      () -> isFlywheelAt(5000) || flywheelEncoder.getVelocity() > 5000
  );

  public final Trigger isFlywheelSpinningBackwards = new Trigger(
      () -> isFlywheelAt(-5000) || flywheelEncoder.getVelocity() < -5000
  );

  /** 
   * Trigger: Is the flywheel stopped?
   */
  public final Trigger isFlywheelStopped = new Trigger(() -> isFlywheelAt(0));

  /**
   * Drive the flywheels to their set velocity. This will use MAXMotion
   * velocity control which will allow for a smooth acceleration and deceleration to the mechanism's
   * setpoint.
   */
  private void setFlywheelVelocity(double velocity) {
    flywheelController.setSetpoint(velocity, ControlType.kMAXMotionVelocityControl);
    flywheelTargetVelocity = velocity;
  }

  /** Set the feeder motor power in the range of [-1, 1]. */
  private void setFeederPower(double power) {
    feederMotor.set(power);
  }
  
  /**
   * Command to run the flywheel motors. When the command is interrupted, e.g. the button is released,
   * the motors will stop.
   */
  public Command runFlywheelCommand() {
    return this.startEnd(
        () -> {
          this.setFlywheelVelocity(FlywheelSetpoints.kShootRpm);
        },
        () -> {
          this.setFlywheelVelocity(0.0);
        }).withName("Spinning Up Flywheel");
  }

  /**
   * Command to run the feeder and flywheel motors. When the command is interrupted, e.g. the button is released,
   * the motors will stop.
   */
  public Command runFeederCommand() {
    return this.startEnd(
        () -> {
          this.setFlywheelVelocity(FlywheelSetpoints.kShootRpm);
          this.setFeederPower(FeederSetpoints.kFeed);
        }, () -> {
          this.setFlywheelVelocity(0.0);
          this.setFeederPower(0.0);
        }).withName("Feeding");
  }

  /**
   * Meta-command to operate the shooter. The Flywheel starts spinning up and when it reaches
   * the desired speed it starts the Feeder.
   */
  public Command runShooterCommand() {
    return this.startEnd(
      () -> this.setFlywheelVelocity(FlywheelSetpoints.kShootRpm),
      () -> flywheelMotor.stopMotor()
    ).until(isFlywheelSpinning).andThen(
      this.startEnd(
        () -> {
          this.setFlywheelVelocity(FlywheelSetpoints.kShootRpm);
          this.setFeederPower(FeederSetpoints.kFeed);
          //run conveyor while shooter
          m_Conveyor.setConveyorPower(ConveyorSetpoints.kIntake);
        }, () -> {
          flywheelMotor.stopMotor();
          feederMotor.stopMotor();
          m_Conveyor.ConveyorMotor.stopMotor();
        })
    ).withName("Shooting");
  }
}
