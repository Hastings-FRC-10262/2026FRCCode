# FRC Team 10262 – Robot Code

This repository contains the robot code for **FIRST Robotics Competition (FRC) Team 10262**.  
The code is written in **Java** using **WPILib** and is organized using a command-based structure.

---

## Swerve Drive (YAGSL)

The robot uses a **swerve drive** system implemented with **YAGSL (Yet Another Generic Swerve Library)**.  
YAGSL provides a flexible and hardware-agnostic framework for controlling swerve modules, handling kinematics, odometry, and integration with other systems such as autonomous path following.

This allows the robot to move and rotate independently, providing precise control and maneuverability on the field.

---

## Vision System (Limelight)

The robot uses a **Limelight** camera for vision processing.  
Limelight is used to detect field targets and assist with robot positioning and alignment. Vision data can be used during teleoperated and autonomous modes to improve accuracy and consistency.

---

## Autonomous System (PathPlanner)

Autonomous routines are created using **PathPlanner**.  
PathPlanner allows the robot to follow pre-defined trajectories during autonomous mode and supports event markers for triggering actions while driving.

These paths are designed ahead of time and executed on the robot using the swerve drive system.

---

## Photoelectric Sensor & LED Feedback

The robot includes a **photoelectric sensor** used to detect whether an object is present in front of it.  
This sensor is connected to LEDs to provide immediate visual feedback:

- LEDs turn **on** when an object is detected
- LEDs turn **off** when no object is detected

This system is primarily used for testing, debugging, and confirming sensor functionality during operation.

---

## Software & Tools

- **Programming Language:** Java  
- **Framework:** WPILib  
- **Drive Library:** YAGSL  
- **Vision:** Limelight  
- **Autonomous:** PathPlanner  

---

## Team 10262

**FIRST Robotics Competition – Team 10262**

This repository represents the software used to develop, test, and compete with our robot during the FRC season.
