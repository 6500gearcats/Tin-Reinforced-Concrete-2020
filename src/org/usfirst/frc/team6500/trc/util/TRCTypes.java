package org.usfirst.frc.team6500.trc.util;

/**
 * Enumerations used to specify which type of something should be used in the creation or execution of something which has several
 *     possible values
 */
public class TRCTypes
{
	// Types of Gyros
	public enum GyroType
	{
		ADXRS450,
		NavX,
	}
	
	// Types of Drivetrains
	public enum DriveType
	{
		Differential,
		Mecanum,
		Custom,
	}
	
	// Modes of joystick operation for differential drivetrain robots using arcade controls
	public enum DifferentialArcadeMode
	{
		XRotation,
		ZRotation,
	}
	
	// Types of Speed Controllers
	public enum SpeedControllerType
	{
		DMC60,
		Jaguar,
		PWMTalonSRX,
		PWMVictorSPX,
		SD540,
		Spark,
		Talon,
		Victor,
		VictorSP,
	}
	
	// Types of actions a TRCDirectionalSystems can execute
	public enum DirectionalSystemActionType
	{
		Stop,
		Forward,
		Reverse,
		FullForward,
		FullReverse,
	}

	// Types of actions a drivetrain can execute in autonomous
	public enum DriveActionType
	{
		Forward,
		Right,
		Rotate,
	}
	
	// Positions a robot can start in at the beginning of a match
	public enum Position
	{
		Left,
		Middle,
		Right,;
		
		// Legacy method for use with methods that interpret position as a boolean "isLeft"
		public static boolean toBoolean(Position side)
		{
			if(side == Position.Left)       { return true;  }
			else if(side == Position.Right) { return false; }
			else                            { return false; }
		}
	}
}
