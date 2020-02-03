package frc.team6500.trc.wrappers.sensors;

import com.revrobotics.*;

public class TRCSparkMaxEncoder extends CANEncoder implements TRCEncoder
{
	/**
	 * Constructs a TRCSparkMaxEncoder like a CANEncoder.
	 *
	 * @param device The Spark Max to which the encoder is attached.
	 * @param sensorType The encoder type for the motor: kHallEffect or kQuadrature
	 * @param counts_per_rev The counts per revolution of the encoder
	 */
	public TRCSparkMaxEncoder(CANSparkMax device, EncoderType sensorType, int counts_per_rev)
	{
		super(device, sensorType, counts_per_rev);
	}
	
	/**
	 * Constructs a TRCSparkMaxEncoder like a CANEncoder.
	 *
	 * @param device The Spark Max to which the encoder is attached.
	 * @param sensorType The encoder type for the motor: kHallEffect or kQuadrature
	 * @param counts_per_rev The counts per revolution of the encoder
	 */
	public TRCSparkMaxEncoder(CANSparkMax device, AlternateEncoderType sensorType, int counts_per_rev)
	{
		super(device, sensorType, counts_per_rev);
	}

	/**
	 * Constructs a TRCSparkMaxEncoder like a CANEncoder.
	 *
	 * @param device The Spark Max to which the encoder is attached.
	 */
	public TRCSparkMaxEncoder(CANSparkMax device)
	{
		super(device);
	}
	
	public double getDistance()
	{
		return this.getPosition();
	}

	public void reset()
	{
		setPosition(0);
	}
}