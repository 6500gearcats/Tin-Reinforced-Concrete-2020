package frc.team6500.trc.drive;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;

import frc.team6500.trc.sensor.TRCEncoder;

public class TRCMecanumDrive extends MecanumDrive
{
	private final static double DEFAULT_AUTO_SPEED = 0.50;

	TRCEncoder flEncoder, rlEncoder, frEncoder, rrEncoder;
	Gyro gyro;
	private double maxAutoSpeed;
	private boolean hasSensors;

	/**
	 *	Create and initialize a new TRCMecanumDrive with sensors and a maximum
	 *	autonomous speed.		
	 */
	public TRCMecanumDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, 
						   SpeedController frontRightMotor, SpeedController rearRightMotor,
						   TRCEncoder frontLeftEncoder, TRCEncoder rearLeftEncoder,
						   TRCEncoder frontRightEncoder, TRCEncoder rearRightEncoder,
						   Gyro gyroscope, double maxAutoSpeed) // thats a lot of parameters!
	{
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.flEncoder = frontLeftEncoder;
		this.rlEncoder = rearLeftEncoder;
		this.frEncoder = frontRightEncoder;
		this.rrEncoder = rearRightEncoder;
		this.gyro = gyroscope;
		this.maxAutoSpeed = maxAutoSpeed;
		this.hasSensors = true;
	}

	/**
	 *	Create and initialize a new TRCMecanumDrive with sensors and the default 
	 *	maximum autonomous speed.	
	 */
	public TRCMecanumDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, 
						   SpeedController frontRightMotor, SpeedController rearRightMotor,
						   TRCEncoder frontLeftEncoder, TRCEncoder rearLeftEncoder,
						   TRCEncoder frontRightEncoder, TRCEncoder rearRightEncoder,
						   Gyro gyroscope) // thats a lot of parameters!
	{
		this(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor,
			 frontLeftEncoder, rearLeftEncoder, frontRightEncoder, rearRightEncoder, gyroscope, DEFAULT_AUTO_SPEED);
	}

		/**
	 *	Create and initialize a new TRCMecanumDrive with sensors and the default 
	 *	maximum autonomous speed.	
	 */
	public TRCMecanumDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, 
						   SpeedController frontRightMotor, SpeedController rearRightMotor) // thats a lot of parameters!
	{
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.hasSensors = false;
	}

	/**
	 *	Drive the robot for a specified measurement
	 *	@param y measurement of forward-back movement
	 *	@param x measurement of side to side movement
	 *	@param z measurement of rotation
	 */
	public void pidDrive(double y, double x, double z)
	{
		if (!hasSensors)
		{
			System.out.println("Sensorless drive! Initialize drive with sensors to drive without a driver.");
			return;
		}

		PIDController fbController, lrController, rtController;

		fbController = new PIDController(1.0, 0.0, 0.0);
		lrController = new PIDController(1.0, 0.0, 0.0);
		rtController = new PIDController(1.0, 0.0, 0.0);

		fbController.setSetpoint(y);
		lrController.setSetpoint(x);
		rtController.setSetpoint(z);

		while (!fbController.atSetpoint() && !lrController.atSetpoint() && !rtController.atSetpoint()) // while we still need to move
		{
			double forwardBack, leftRight, rotation = 0.0;
			double fbcalc, lrcalc, rtcalc;

			// forwardBack = 
		}

		fbController.close();
		lrController.close();
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
