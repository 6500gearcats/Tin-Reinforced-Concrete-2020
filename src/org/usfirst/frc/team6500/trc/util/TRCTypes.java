package org.usfirst.frc.team6500.trc.util;

/**
 * Enumerations used to specify which type of x should be used in the creation or execution of something which has several
 *     possible x values
 */
public class TRCTypes
{
	public enum GyroType
	{
		ADXRS450,
		NavX,
	}
	
	public enum DriveType
	{
		Differential,
		Mecanum,
		Custom,
	}
	
	public enum DifferentialArcadeMode
	{
		XRotation,
		ZRotation,
	}
	
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
	
	public enum Position
	{
		Left,
		Middle,
		Right,;
		
		public static boolean toBoolean(Position side)
		{
			if(side == Position.Left)       { return true;  }
			else if(side == Position.Right) { return false; }
			else                            { return false; }
		}
	}
}
