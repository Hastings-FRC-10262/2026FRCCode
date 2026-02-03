// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import swervelib.math.Matter;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean constants. This
 * class should not be used for any other purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants
{

  public static final double ROBOT_MASS = (148 - 20.3) * 0.453592; // 32lbs * kg per pound
  public static final Matter CHASSIS    = new Matter(new Translation3d(0, 0, Units.inchesToMeters(8)), ROBOT_MASS);
  public static final double LOOP_TIME  = 0.13; //s, 20ms + 110ms sprk max velocity lag
  public static final double MAX_SPEED  = Units.feetToMeters(14.5);
  // Maximum speed of the robot in meters per second, used to limit acceleration.

//  public static final class AutonConstants
//  {
//
//    public static final PIDConstants TRANSLATION_PID = new PIDConstants(0.7, 0, 0);
//    public static final PIDConstants ANGLE_PID       = new PIDConstants(0.4, 0, 0.01);
//  }

  public static final class DrivebaseConstants
  {

    // Hold time on motor brakes when disabled
    public static final double WHEEL_LOCK_TIME = 10; // seconds
  }

  public static final class IntakeSubsystemConstants {
    public static final int kIntakeMotorCanId = 2;    // can id
    public static final int kConveyorMotorCanId = 4;  // can id

    public static final class IntakeSetpoints {
      public static final double kIntake = 0.6;
      public static final double kExtake = -0.6;
    }

    public static final class ConveyorSetpoints {
      public static final double kIntake = 0.7;
      public static final double kExtake = -0.7;
    }
  }

  public static final class ShooterSubsystemConstants {
    public static final int kFeederMotorCanId = 5;    // SPARK Flex CAN ID
    public static final int kFlywheelMotorCanId = 6;  // SPARK Flex CAN ID (Right)
    public static final int kFlywheelFollowerMotorCanId = 7;  // SPARK Flex CAN ID (Left)

    public static final class FeederSetpoints {
      public static final double kFeed = 0.95;
    }

    public static final class FlywheelSetpoints {
      public static final double kShootRpm = 5000;
      public static final double kVelocityTolerance = 100;
    }
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
    public static final double kVortexKv = 565;   // rpm/V
  }

  public static class OperatorConstants
  {

    // Joystick Deadband
    public static final double DEADBAND        = 0.1;
    public static final double LEFT_Y_DEADBAND = 0.1;
    public static final double RIGHT_X_DEADBAND = 0.1;
    public static final double TURN_CONSTANT    = 6;
  }
}
