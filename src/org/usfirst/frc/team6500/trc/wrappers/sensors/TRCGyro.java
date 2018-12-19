package org.usfirst.frc.team6500.trc.wrappers.sensors;

import org.usfirst.frc.team6500.trc.util.TRCTypes.GyroType;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.I2C;

public class TRCGyro extends GyroBase
{
	private Object installedGyro;
	private GyroType internalGyroType;
	
	/**
	 * A gyro which provides a uniform way to use any possible type of gyro on the robot without extra classes
	 * 
	 * @param gyroType Which type of gyro to use
	 */
	public TRCGyro (GyroType gyroType)
	{
		internalGyroType = gyroType;
		
		switch (gyroType)
		{
			case ADXRS450:
				installedGyro = new ADXRS450_Gyro();
				((GyroBase) installedGyro).reset();
				break;
			case NavX:
				installedGyro = new AHRS(I2C.Port.kMXP);
				break;
			default:
				break;
		}
	}

	
	/**
	 * Calibrate the gyro.  Note that this can take several seconds so you must keep the robot still, or else https://j.gifs.com/PZ9O3y.gif
	 */
	@Override
	public void calibrate() {
		try
		{
			((GyroBase) installedGyro).calibrate();
		}
		catch (Exception e)
		{
			return;
		}
	}

	/**
	 * Get the current angle of the gryo, if its initial position was 0 degrees
	 * 
	 * @return The gyro's current angle modulo a full circle to ignore the number of rotations
	 */
	@Override
	public double getAngle() {
		try
		{
			if (this.internalGyroType != GyroType.NavX)
			{
				return ((GyroBase) installedGyro).getAngle() % 360;
			}
			else
			{
				return ((AHRS) installedGyro).getAngle() % 360;
			}
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * Get how fast the gyro is rotating
	 * 
	 * @return The rotation speed of the gyro in degrees/second
	 */
	@Override
	public double getRate() {
		try
		{
			if (this.internalGyroType != GyroType.NavX)
			{
				return ((GyroBase) installedGyro).getRate();
			}
			else
			{
				return ((AHRS) installedGyro).getRate();
			}
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * Reset the gyro, effectively making the current angle and position the initial one.  Do this once the robot is fully settled
	 */
	@Override
	public void reset() {
		try
		{
			if (this.internalGyroType != GyroType.NavX)
			{
				((GyroBase) installedGyro).reset();
			}
			else
			{
				((AHRS) installedGyro).reset();
				((AHRS) installedGyro).zeroYaw();
			}
		}
		catch (Exception e)
		{
			return;
		}
	}

}
