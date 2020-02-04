package frc.team6500.trc.wrappers.sensors;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class TRCADXRS450Gyro extends ADXRS450_Gyro implements Gyro
{
	/**
	 * Constructor.  Uses the onboard CS0.
	 */
	public ADXRS450_Gyro() 
	{
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param port The SPI port that the gyro is connected to
	 */
	public ADXRS450_Gyro(SPI.Port port)
	{
		super(port);
	}
}
