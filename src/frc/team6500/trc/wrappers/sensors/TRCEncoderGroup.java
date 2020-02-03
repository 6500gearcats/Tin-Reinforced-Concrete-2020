package frc.team6500.trc.wrappers.sensors;

import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCTypes;
import frc.team6500.trc.util.TRCTypes.VerbosityType;

import edu.wpi.first.wpilibj.SpeedController;

import frc.team6500.trc.util.TRCTypes.DirectionType;
import frc.team6500.trc.util.TRCTypes.EncoderType;

public class TRCEncoderGroup implements TRCEncoder
{
	private TRCEncoder[] encoders;

	/**
	 *	Create a group of encoders
	 *	@param encoder encoder to add
	 */
	public TRCEncoderGroup(TRCEncoder encoder, TRCEncoder... encoders)
	{
		encoders = new TRCEncoder[encoders.length + 1];
		encoders[0] = encoder;
		// SendableRegistry.addChild(this, encoder);
		for (int i = 0; i < encoders.length; i++)
		{
			TRCEncoder e = encoders[i];
			encoders[i + 1] = e;
			// SendableRegistry.addChild(this, e);
		}
	}
	
	/**
	 * Resets all encoders to zero distance
	 */
	public void reset()
	{
		for (TRCEncoder e : encoders) e.reset();
		System.out.println(this + " reset all encoders");
	}
	
	/**
	 * Gives the average distance all encoders have traveled
	 * 
	 * @return The average of the distance traveled by all the encoders
	 */
	public double getDistance()
	{
		double distancesum = 0.0;
		for (TRCEncoder e : encoders) distancesum += e.getDistance();
		return distancesum / encoders.length;
	}

	/**
	 * Gives the average absolute (absolute value) distance all encoders have traveled; useful for measuring rotation
	 * 
	 * @return The average of the absolute distances traveled by all the encoders
	 */
	public double getAbsoluteDistance()
	{
		double distancesum = 0.0;
		for (TRCEncoder e : encoders) distancesum += Math.abs(e.getDistance());
		return distancesum / encoders.length;
	}
	
	/**
	 * Use this method after driving in a straight line to calibrate them to account for mechanical irregularities
	 * 
	 * Untested.
	 */
	// public void uniformCalibrateDistancePerPulse()
	// {
	// 	double averageDistanceTraveled = this.getAverageDistanceTraveled();
		
	// 	// for (TRCEncoder encoder : this.internalEncoders)
	// 	// {
	// 	// 	encoder.setDistancePerPulse(encoder.getDistancePerPulse() * averageDistanceTraveled / encoder.getDistance());
	// 	// }

	// 	TRCNetworkData.logString(VerbosityType.Log_Debug, "EncoderSet " + this.toString() + " has been calibrated");
	// }
}
