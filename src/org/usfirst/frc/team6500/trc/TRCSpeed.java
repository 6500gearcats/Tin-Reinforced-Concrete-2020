package org.usfirst.frc.team6500.trc;

/**
 * Class which filters any inputs by averaging them with their previous value to prevent sharp increases in acceleration,
 * to maintain low jerk so components do not slip or tip. Pass all drive power outputs through an instance of this class,
 * along with anything which could benefit from similar jerk limiting
 */
public class TRCSpeed
{
	private double previousSpeed = 0.0;
	
	public double calculateSpeed(double raw, double multiplier)
	{
		double calculated = raw;
		
		calculated *= multiplier;
		
		if (Math.abs(calculated - previousSpeed) > 0.1)
		{
			calculated = (calculated + previousSpeed) / 2;
		}
		
		previousSpeed = calculated;
		return calculated;
	}
}
