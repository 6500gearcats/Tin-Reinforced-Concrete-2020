package org.usfirst.frc.team6500.trc.util;

import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;

/**
 * Class which filters any inputs by averaging them with their previous value to prevent sharp increases in acceleration,
 * to maintain low jerk so components do not slip or tip. Pass all drive power outputs through an instance of this class,
 * along with anything which could benefit from similar jerk limiting
 */
public class TRCSpeed
{
	private static final double differenceDeadband = 0.05;
	private double previousSpeed = 0.0;
	private double calculationTime = 0.0;
	
	/**
	 * Calculate a new speed value from the raw value and multiplier, taking into account the distance from the previous
	 * values to reduce jerk
	 * 
	 * @param raw The target raw speed value from the source
	 * @param multiplier The value to multiply the raw value by before doing other calculations; if not using this for drivetrain functions use the version without it
	 * @return Smoothed speed value, equivalent to (previousSpeed + (raw * multiplier)) / 2
	 */
	public double calculateSpeed(double raw, double multiplier)
	{
		double calculated = raw;
		
		calculated *= multiplier;
		
		if (Math.abs(calculated - this.previousSpeed) > differenceDeadband && (calculationTime - System.currentTimeMillis()) > 50)
		{
			calculated = (calculated + this.previousSpeed) / 2;
			calculationTime = System.currentTimeMillis();
		}
		
		this.previousSpeed = calculated;
		return calculated;
	}

	/**
	 * Calculate a new speed value from the raw value, taking into account the distance from the previous values to
	 * reduce jerk
	 * 
	 * @param raw The raw speed value from the source
	 * @return Smoothed speed value, equivalent to (previousSpeed + raw) / 2
	 */
	public double calculateSpeed(double raw)
	{
		return this.calculateSpeed(raw, 1.0);
	}

	/**
	 * Resets the recorded previous speed from calculations.  In systems where operation is not entirely continous,
	 * use this whenever a new interval of operation is about to begin.
	 */
	public void reset()
	{
		this.previousSpeed = 0.0;
		TRCNetworkData.logString(VerbosityType.Log_Debug, "Speed object [" + this.toString() + "]'s calculations have been reset.");
	}
}
