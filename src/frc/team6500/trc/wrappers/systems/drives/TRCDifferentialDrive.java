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
	private double maxSpeed;
	private double maxAutoSpeed;

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
		this.maxSpeed = 1.00;
		this.maxAutoSpeed = 0.75;
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
		fbController.setOutputLimits();

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

	/**
	 *	@return the set max speed for the drive
	 */
	public double getMaxSpeed() { return this.maxSpeed; }

	/**
	 *	Set the max speed of the drive
	 *	@param maxSpeed maxSpeed to set
	 */
	public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }

	/**
	 *	@return the set max autonomous speed for the drive
	 */
	public double getMaxAutoSpeed() { return this.maxAutoSpeed; }

	/**
	 *	Set the max autonomous speed of the drive
	 *	@param maxAutoSpeed maxAutoSpeed to set
	 */
	public void setMaxAutoSpeed(double maxAutoSpeed) { this.maxAutoSpeed = maxAutoSpeed; }
}
