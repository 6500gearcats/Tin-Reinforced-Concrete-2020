package frc.team6500.trc.drive;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import frc.team6500.trc.sensor.TRCEncoder;

/**
 * Extends the normal DifferentialDrive but adds auto support
 */
public class TRCDifferentialDrive extends DifferentialDrive
{
	private final static double DEFAULT_AUTO_SPEED = 0.75;

	TRCEncoder lEncoder, rEncoder;
	Gyro gyro;
	private double maxAutoSpeed;
	private boolean hasSensors;

	/**
	 *	Construct a TRCDifferentialDrive with motors and sensors
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 *	@param leftEncoder	the left encoder or encoders to use
	 *	@param rightEncoder	the right encoder or encoders to use
	 *	@param gyroscope	the gyroscope to use for rotation (pass null if none)
	 */
	public TRCDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor, 
								TRCEncoder leftEncoder, TRCEncoder rightEncoder, Gyro gyroscope)
	{
		this(leftMotor, rightMotor, leftEncoder, rightEncoder, gyroscope, DEFAULT_AUTO_SPEED);
	}

	/**
	 *	Construct a TRCDifferentialDrive with motors and sensors
	 *	@param leftMotor	the left motor or motors to use
	 *	@param rightMotor	the right motor or motors to use
	 *	@param leftEncoder	the left encoder or encoders to use
	 *	@param rightEncoder	the right encoder or encoders to use
	 *	@param gyroscope	the gyroscope to use for rotation (pass null if none)
	 *	@param maxAutoSpeed the maximum speed the robot will drive when driving pid style
	 */
	public TRCDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor, 
								TRCEncoder leftEncoder, TRCEncoder rightEncoder, Gyro gyroscope,
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

		if (gyro != null) gyro.reset();
		System.out.println("Started auto movement");
		while (!fbController.atSetpoint() && !rtController.atSetpoint()) // while we still need to move
		{
			double distance, degrees = 0.0;
			double fbcalc, rtcalc;

			// just get average because hopefully this will calculate only when the robot has moved a little bit
			distance = (lEncoder.getDistance() + rEncoder.getDistance()) / 2.0;
			// get from gyroscope
			if (gyro != null) degrees = gyro.getAngle();

			fbcalc = fbController.calculate(distance);
			rtcalc = rtController.calculate(degrees);

			curvatureDrive(fbcalc, rtcalc, (x == 0.0));
		}
		System.out.println("Finished auto movement");
		fbController.close();
		rtController.close();
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
