package org.usfirst.frc.team6500.trc.wrappers.sensors;

import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DirectionType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.EncoderType;;

public class TRCEncoderSet
{
	private int[] ports;
	private EncoderType[] types;
	private Object[] internalEncoders;
	private double[] distancesPerPulse;
	private boolean lowresolution;
	private int numwheels;
	
	
	/**
	 * Consolidation of encoders on a robot's wheels for ease of use
	 * 
	 * @param dioports The digital input/output ports which the encoders are plugged into
	 * @param dpp The distance per pulse of the wheels. Equation: (distance robot travels in one rotation) / (pulses per revolution)
	 * @param lowres Only use the first channel for reading encoder values. Less accurate, but uses fewer ports. If this is enabled, dioports's consecutive values should be duplicates.
	 * @param totalwheels How many encoders are plugged in. This must be half the length of dioports.
	 */
	public TRCEncoderSet(int[] dioports, double[] dpp, boolean lowres, int totalwheels, EncoderType[] types)
	{
		this.internalEncoders = new Object[totalwheels];
		this.ports = dioports.clone();
		this.types = types.clone();
		this.distancesPerPulse = dpp.clone();
		this.lowresolution = lowres;
		this.numwheels = totalwheels;
		
		for (int wheel = 0; wheel < this.numwheels; wheel++)
		{
			int[] encoderPorts = new int[] {this.ports[wheel * 2], this.ports[(wheel * 2) + 1]};
			
			if (wheel >= this.numwheels / 2) // Right wheels
			{
				this.internalEncoders[wheel] = TRCTypes.encoderTypeToObject(encoderPorts, this.distancesPerPulse[wheel], this.lowresolution, true, this.types[wheel]);
			}
			else                             // Left wheels
			{
				this.internalEncoders[wheel] = TRCTypes.encoderTypeToObject(encoderPorts, this.distancesPerPulse[wheel], this.lowresolution, false, this.types[wheel]);
			}
		}

		TRCNetworkData.createDataPoint("EncoderSet " + this.toString());
	}
	
	
	/**
	 * Resets all encoders to zero distance
	 */
	public void resetAllEncoders()
	{
		for (int i = 0; i < this.internalEncoders.length; i++)
		{
			if (this.types[i] == EncoderType.Digital)
			{
				((TRCEncoder) this.internalEncoders[i]).reset();
			}
			else
			{
				((TRCTalonEncoder) this.internalEncoders[i]).reset();
			}
		}

		TRCNetworkData.logString(VerbosityType.Log_Debug, "EncoderSet " + this.toString() + " has been reset");
	}
	
	/**
	 * Resets the distance of a particular encoder to zero
	 * 
	 * @param encodernum Which encoder to reset
	 */
	public void resetIndividualEncoder(int encodernum)
	{
		if (this.types[encodernum] == EncoderType.Digital)
			{
				((TRCEncoder) this.internalEncoders[encodernum]).reset();
			}
			else
			{
				((TRCTalonEncoder) this.internalEncoders[encodernum]).reset();
			}
	}

	private static double getDirectionalDistance (DirectionType direction, double originalDistance, int wheelNum)
	{
		switch(direction)
		{
			case ForwardBackward:
				return originalDistance;
			case LeftRight:
				switch (wheelNum)
				{
					case 0:
					case 3:
						return originalDistance;
					case 1:
					case 2:
						return -originalDistance;
					default:
						return 0.0;
				}
			default:
				return 0.0;
		}
	}
	
	/**
	 * Gives the average distance all encoders have traveled
	 * 
	 * @return The average of the distance traveled by all the encoders
	 */
	public double getAverageDistanceTraveled(DirectionType direction)
	{
		double distancesum = 0.0;
		
		for (int i = 0; i < this.internalEncoders.length; i++)
		{
			if (this.types[i] == EncoderType.Digital)
			{
				distancesum += getDirectionalDistance(direction, ((TRCEncoder) this.internalEncoders[i]).getDistance(), i);
			}
			else
			{
				distancesum += getDirectionalDistance(direction, ((TRCTalonEncoder) this.internalEncoders[i]).getDistance(), i);
			}
		}
		
		TRCNetworkData.updateDataPoint("EncoderSet " + this.toString(), distancesum / this.internalEncoders.length);
		return distancesum / this.internalEncoders.length;
	}

	/**
	 * Gives the average absolute distance all encoders have traveled; useful for measuring rotation
	 * 
	 * @return The average of the absolute distances traveled by all the encoders
	 */
	public double getAverageAbsoluteDistanceTraveled()
	{
		double distancesum = 0.0;
		
		for (int i = 0; i < this.internalEncoders.length; i++)
		{
			if (this.types[i] == EncoderType.Digital)
			{
				distancesum += Math.abs(((TRCEncoder) this.internalEncoders[i]).getDistance());
			}
			else
			{
				distancesum += Math.abs(((TRCTalonEncoder) this.internalEncoders[i]).getDistance());
			}
		}
		
		TRCNetworkData.updateDataPoint("EncoderSet " + this.toString(), distancesum / this.internalEncoders.length);
		return distancesum / this.internalEncoders.length;
	}
	
	/**
	 * Get the distance traveled by an individual encoder
	 * 
	 * @param encodernum Which encoder to read distance from
	 * @return The distance traveled by the encoder encodernum
	 */
	public double getIndividualDistanceTraveled(int encodernum)
	{
		if (this.types[encodernum] == EncoderType.Digital)
		{
			return ((TRCEncoder) this.internalEncoders[encodernum]).getDistance();
		}
		else
		{
			return ((TRCTalonEncoder) this.internalEncoders[encodernum]).getDistance();
		}
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
