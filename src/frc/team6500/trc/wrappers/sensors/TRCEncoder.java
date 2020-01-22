package frc.team6500.trc.wrappers.sensors;

import frc.team6500.trc.util.TRCNetworkData;

import edu.wpi.first.wpilibj.*;

public class TRCEncoder
{
	private Encoder internalEncoder;
	private double distancePerPulse;
	private boolean reverse;
	private boolean lowresolution;

	
	/**
	 * An encoder attached to a motor, used to determine its output's position compared to where it was when the encoder was last reset
	 * 
	 * @param ports What ports the encoder is plugged into
	 * @param dpp The distance per pulse of the motor's output. Equation: (distance motor's final output travels in one rotation) / (pulses per revolution)
	 * @param lowres Only use the first channel for reading encoder values. Less accurate, but uses fewer ports. If this is enabled, port's values should be duplicates.
	 * @param inverted Return distances from the encoder will be negated
	 */
	public TRCEncoder(int[] ports, double dpp, boolean lowres, boolean inverted) {
		this.lowresolution = lowres;
		this.reverse = inverted;
		this.distancePerPulse = dpp;
		
		try
		{
			DigitalInput inputA = new DigitalInput(ports[0]);
			DigitalInput inputB = new DigitalInput(ports[1]);
			
			this.internalEncoder = new Encoder(inputA, inputB);
		}
		catch (Exception e)
		{
			System.out.printf("ERROR: Encoder initialized with %d ports instead of the correct 2.  Using default ports 0 and 1, issues may occur.", ports.length);
			this.internalEncoder = new Encoder(0, 1);
		}
		
		this.setupValues();
	}
	
	/**
	 * An encoder attached to a motor, used to determine its output's position compared to where it was when the encoder was last reset
	 * 
	 * @param ports What ports the encoder is plugged into
	 * @param dpp The distance per pulse of the motor's output. Equation: (distance motor's final output travels in one rotation) / (pulses per revolution)
	 * @param lowres Only use the first channel for reading encoder values. Less accurate, but uses fewer ports. If this is enabled, port's values should be duplicates.
	 * @param inverted Return distances from the encoder will be negated
	 */
	public TRCEncoder(DigitalInput[] ports, double dpp, boolean lowres, boolean inverted) {
		this.lowresolution = lowres;
		this.reverse = inverted;
		this.distancePerPulse = dpp;
		
		try
		{
			this.internalEncoder = new Encoder(ports[0], ports[1]);
		}
		catch (Exception e)
		{
			System.out.printf("ERROR: Encoder initialized with %d ports instead of the correct 2.  Using default ports 0 and 1, issues may occur.", ports.length);
			this.internalEncoder = new Encoder(0, 1);
		}
		
		this.setupValues();
	}

	
	/**
	 * Common initialization code for both constructors
	 */
	private void setupValues()
	{
		this.internalEncoder.setReverseDirection(this.reverse);
		
		if (this.lowresolution)
		{
			this.internalEncoder.setDistancePerPulse(this.distancePerPulse * 2);
		}
		else
		{
			this.internalEncoder.setDistancePerPulse(this.distancePerPulse);
		}

		TRCNetworkData.createDataPoint("Encoder " + this.internalEncoder.toString());
	}
	
	/**
	 * Set whether this encoder is only using one input or not
	 * 
	 * @param lowres Use only one input (or not)
	 */
	public void setSingle(boolean lowres)
	{
		this.lowresolution = lowres;
		
		if (this.lowresolution)
		{
			this.internalEncoder.setDistancePerPulse(this.distancePerPulse * 2);
		}
		else
		{
			this.internalEncoder.setDistancePerPulse(this.distancePerPulse);
		}
	}
	
	/**
	 * Get the distance this encoder's motor's output has traveled
	 * 
	 * @return The distance this encoder's motor's output has traveled since it was last reset
	 */
	public double getDistance()
	{
		TRCNetworkData.updateDataPoint("Encoder " + this.internalEncoder.toString(), this.internalEncoder.getDistance());
		return this.internalEncoder.getDistance();
	}
	
	/**
	 * Reset this encoder's distance counter to 0
	 */
	public void reset()
	{
		this.internalEncoder.reset();
	}

	/**
	 * Sets the distance per pulse for this encoder's motor's output
	 * 
	 * @param dpp The distance per pulse of the motor's output. Equation: (distance motor's final output travels in one rotation) / (pulses per revolution)
	 */
	public void setDistancePerPulse(double dpp)
	{
		this.distancePerPulse = dpp;
		this.internalEncoder.setDistancePerPulse(this.distancePerPulse);
	}
	
	/**
	 * Set whether the distance values the encoder returns should be negated
	 * 
	 * @param inverted Whether the output values should be negated or not
	 */
	public void setReverse(boolean inverted)
	{
		this.reverse = inverted;
		this.internalEncoder.setReverseDirection(this.reverse);
	}

	/**
	 * Get the distance per pulse of this encoder
	 * 
	 * @return The set distance per pulse for this encoder
	 */
	public double getDistancePerPulse()
	{
		return this.distancePerPulse;
	}
	
	/**
	 * Get whether the encoder's output is inverted
	 * 
	 * @return Whether this encoder is currently configured to negate its output values
	 */
	public boolean getReverse()
	{
		return this.reverse;
	}
}
