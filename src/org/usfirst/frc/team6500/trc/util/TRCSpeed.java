package org.usfirst.frc.team6500.trc.util;

/**
 * Class which filters any inputs by averaging them with their previous value to prevent sharp increases in acceleration,
 * to maintain low jerk so components do not slip or tip. Pass all drive power outputs through an instance of this class,
 * along with anything which could benefit from similar jerk limiting
 */
public class TRCSpeed
{
	private static final double differenceDeadband = 0.05;
	private double previousSpeed = 0.0;
	
	public double calculateSpeed(double raw, double multiplier)
	{
		double calculated = raw;
		
		calculated *= multiplier;
		
		if (Math.abs(calculated - this.previousSpeed) > differenceDeadband)
		{
			calculated = (calculated + this.previousSpeed) / 2;
		}
		
		this.previousSpeed = calculated;
		return calculated;
	}

	public void reset()
	{
		this.previousSpeed = 0.0;
	}
}
