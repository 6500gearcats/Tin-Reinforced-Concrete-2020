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
}
