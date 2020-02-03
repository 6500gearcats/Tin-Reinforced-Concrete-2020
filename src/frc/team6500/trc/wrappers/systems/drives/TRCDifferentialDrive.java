package frc.team6500.trc.wrappers.systems.drives;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team6500.trc.wrappers.sensors.TRCEncoder;

/**
 * Extends the normal DifferentialDrive but adds auto support
 */
public class TRCDifferentialDrive extends DifferentialDrive
{
	TRCEncoder lEncoder, rEncoder;

	/**
	 *	Construct a TRCDifferentialDrive with speed controllers and encoders
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 *	@param leftEncoder	the left encoder or encoders to use
	 *	@param rightEncoder	the right encoder or encoders to use
	 */
	public TRCDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor, 
								TRCEncoder leftEncoder, TRCEncoder rightEncoder)
	{
		super(leftMotor, rightMotor);
		this.lEncoder = leftEncoder;
		this.rEncoder = rightEncoder;
	}

	/**
	 *	Drive the robot for a specified measurement
	 *	@param x measurement of forward and back
	 *	@param y measurement of rotation
	 */
	public void pidDrive(double x, double z)
	{
		PIDController fbController = new PIDController(1.0, 0.0, 0.0); // forward back controller
		PIDController rtController = new PIDController(1.0, 0.0, 0.0); // rotate controller

		fbController.setSetpoint(x);
		rtController.setSetpoint(z);

		System.out.println("Started auto movement");
		while (!fbController.atSetpoint() && !rtController.atSetpoint()) // while we still need to move
		{
			double distance, degrees;
			double fbcalc, rtcalc;

			// distance formula √((left-0)^2+(right-0)^2) [0 because we start at zero]
			distance = Math.sqrt(Math.pow(lEncoder.getDistance(), 2)+Math.pow(rEncoder.getDistance(), 2));
			degrees = 0.0/* difference between sides */;

			fbcalc = fbController.calculate(distance);
			rtcalc = rtController.calculate(degrees);

			curvatureDrive(fbcalc, rtcalc, (x == 0.0));
		}
		System.out.println("Finished auto movement");
	}
}
