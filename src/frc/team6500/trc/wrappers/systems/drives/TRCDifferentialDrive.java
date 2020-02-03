package frc.team6500.trc.wrappers.systems.drives;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import frc.team6500.trc.wrappers.sensors.TRCEncoder;
import frc.team6500.trc.wrappers.sensors.TRCGyroBase;

/**
 * Extends the normal DifferentialDrive but adds auto support
 */
public class TRCDifferentialDrive extends DifferentialDrive
{
	private final static double DEFAULT_AUTO_SPEED = 0.75;

	TRCEncoder lEncoder, rEncoder;
	TRCGyroBase gyro;
	private double maxAutoSpeed;
	private boolean hasSensors;

	/**
	 *	Construct a TRCDifferentialDrive with motors and sensors
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 *	@param leftEncoder	the left encoder or encoders to use
	 *	@param rightEncoder	the right encoder or encoders to use
	 */
	public TRCDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor, 
								TRCEncoder leftEncoder, TRCEncoder rightEncoder, TRCGyroBase gyroscope)
	{
		this(leftMotor, rightMotor, leftEncoder, rightEncoder, gyroscope, DEFAULT_AUTO_SPEED);
	}

	/**
	 *	Construct a TRCDifferentialDrive with motors and sensors
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 *	@param leftEncoder	the left encoder or encoders to use
	 *	@param rightEncoder	the right encoder or encoders to use
	 *	@param maxAutoSpeed the maximum speed the robot will drive when driving pid style
	 */
	public TRCDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor, 
								TRCEncoder leftEncoder, TRCEncoder rightEncoder, TRCGyroBase gyroscope,
								double maxAutoSpeed)
	{
		super(leftMotor, rightMotor);
		this.lEncoder = leftEncoder;
		this.rEncoder = rightEncoder;
		this.gyro = gyroscope;
		this.maxAutoSpeed = maxAutoSpeed;
		this.hasSensors = true;
	}

	/**
	 *	Construct a TRCDifferentialDrive with motors (you cannot auto drive with this setup!)
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 */
	public TRCDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor)
	{
		super(leftMotor, rightMotor);
	}

	/**
	 *	Drive the robot for a specified measurement
	 *	@param x measurement of forward and back
	 *	@param y measurement of rotation
	 */
	public void pidDrive(double x, double z)
	{
		if (!hasSensors)
		{
			System.out.println("Sensorless drive! Initialize drive with sensors to drive without a driver.");
			return;
		}
		PIDController fbController = new PIDController(1.0, 0.0, 0.0); // forward back controller
		PIDController rtController = new PIDController(1.0, 0.0, 0.0); // rotate controller

		fbController.setSetpoint(x);
		rtController.setSetpoint(z);
		fbController.setIntegratorRange(-maxAutoSpeed, maxAutoSpeed); // set the output range
		rtController.setIntegratorRange(-maxAutoSpeed, maxAutoSpeed); // set the output range

		gyro.reset();
		System.out.println("Started auto movement");
		while (!fbController.atSetpoint() && !rtController.atSetpoint()) // while we still need to move
		{
			double distance, degrees;
			double fbcalc, rtcalc;

			// distance formula √((left-0)^2+(right-0)^2) [0 because we start at zero]
			distance = Math.sqrt(Math.pow(lEncoder.getDistance(), 2)+Math.pow(rEncoder.getDistance(), 2));
			// get from gyroscope
			degrees = gyro.getAngle();

			fbcalc = fbController.calculate(distance);
			rtcalc = rtController.calculate(degrees);

			curvatureDrive(fbcalc, rtcalc, (x == 0.0));
		}
		System.out.println("Finished auto movement");
		gyro.reset(); // be a good person and clean up our trash
	}

	/**
	 *	@return the set max autonomous speed for the drive
	 */
	public double getMaxAutoSpeed() { return this.maxAutoSpeed; }

	/**
	 *	Set the max autonomous speed of the drive
	 *	@param maxAutoSpeed maxAutoSpeed to set
	 */
	public void setMaxAutoSpeed(double maxAutoSpeed) { this.maxAutoSpeed = maxAutoSpeed; }

	/**
	 *	@return if the drive has reference to sensors
	 */
	public boolean hasSensors() { return this.hasSensors; }
}
