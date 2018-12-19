package org.usfirst.frc.team6500.trc.wrappers.sensors;

public class TRCEncoderSet
{
	private int[] ports;
	private TRCEncoder[] internalEncoders;
	private double distancePerPulse;
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
	public TRCEncoderSet(int[] dioports, double dpp, boolean lowres, int totalwheels)
	{
		this.ports = dioports.clone();
		this.distancePerPulse = dpp;
		this.lowresolution = lowres;
		this.numwheels = totalwheels;
		
		for (int wheel = 0; wheel < this.numwheels; wheel++)
		{
			int[] encoderPorts = new int[] {this.ports[wheel * 2], this.ports[(wheel * 2) + 1]};
			
			if (wheel >= this.numwheels / 2) // Right wheels
			{
				this.internalEncoders[wheel] = new TRCEncoder(encoderPorts, this.distancePerPulse, true, this.lowresolution);
			}
			else                             // Left wheels
			{
				this.internalEncoders[wheel] = new TRCEncoder(encoderPorts, this.distancePerPulse, false, this.lowresolution);
			}
		}
	}
	
	
	/**
	 * Resets all encoders to zero distance
	 */
	public void resetAllEncoders()
	{
		for (TRCEncoder encoder : this.internalEncoders)
		{
			encoder.reset();
		}
	}
	
	/**
	 * Resets the distance of a particular encoder to zero
	 * 
	 * @param encodernum Which encoder to reset
	 */
	public void resetIndividualEncoder(int encodernum)
	{
		this.internalEncoders[encodernum].reset();
	}
	
	/**
	 * Gives the average distance all encoders have traveled
	 * 
	 * @return The average of the distance traveled by all the encoders
	 */
	public double getAverageDistanceTraveled()
	{
		double distancesum = 0.0;
		
		for (TRCEncoder encoder : this.internalEncoders)
		{
			distancesum += encoder.getDistance();
		}
		
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
		return this.internalEncoders[encodernum].getDistance();
	}
	
	/**
	 * Use this method after driving in a straight line to calibrate them to account for mechanical irregularities
	 */
	public void uniformCalibrateDistancePerPulse()
	{
		double averageDistanceTraveled = this.getAverageDistanceTraveled();
		
		for (TRCEncoder encoder : this.internalEncoders)
		{
			encoder.setDistancePerPulse(encoder.getDistancePerPulse() * averageDistanceTraveled / encoder.getDistance());
		}
	}
}
