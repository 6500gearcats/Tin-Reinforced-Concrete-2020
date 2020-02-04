package frc.team6500.trc.wrappers.sensors;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;

public class TRCADXRS450Gyro extends ADXRS450_Gyro implements Gyro
{
	/**
	 * Constructor.  Uses the onboard CS0.
	 */
	public TRCADXRS450Gyro() 
	{
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param port The SPI port that the gyro is connected to
	 */
	public TRCADXRS450Gyro(SPI.Port port)
	{
		super(port);
	}
}
