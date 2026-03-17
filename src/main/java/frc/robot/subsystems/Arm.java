package frc.robot.subsystems;
import edu.wpi.first.math.MathUtil;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.*;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmSubsystemConstants;
import frc.robot.Constants.IntakeSubsystemConstants.ConveyorSetpoints;
import frc.robot.Constants.IntakeSubsystemConstants.IntakeSetpoints;

public class Arm extends SubsystemBase {
    boolean IsArmDown;
    SparkFlex ArmMotor;
    RelativeEncoder encoder;
    PIDController armPID;
    public Arm() {
        IsArmDown=false;
        ArmMotor = new SparkFlex(ArmSubsystemConstants.kArmMotorCanId, MotorType.kBrushless);
        ArmMotor.configure(Configs.IntakeSubsystem.intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        encoder = ArmMotor.getEncoder();
        armPID = new PIDController(ArmSubsystemConstants.armkP, ArmSubsystemConstants.armkI, ArmSubsystemConstants.armkD);
        armPID.setTolerance(ArmSubsystemConstants.armTolerance);
    } 
    public Command Movearm(Double position) {
        return Commands.run(
        () -> {
          
          // Get the target position, clamped to (limited between) the lowest and highest arm positions
          Double target = MathUtil.clamp(position, ArmSubsystemConstants.armFrontLimit, ArmSubsystemConstants.armRearLimit);

          // Calculate the PID result, and clamp to the arm's maximum velocity limit.
          Double result =  MathUtil.clamp(armPID.calculate(encoder.getPosition(), target), -1 * ArmSubsystemConstants.armVelocityLimit, ArmSubsystemConstants.armVelocityLimit);

          ArmMotor.set(result);

          
        }).until(() -> armPID.atSetpoint());
    }
    public void periodic() {
        SmartDashboard.putNumber("arm encoder", encoder.getPosition());
        SmartDashboard.putNumber("setpoint", armPID.getSetpoint());
    }
}