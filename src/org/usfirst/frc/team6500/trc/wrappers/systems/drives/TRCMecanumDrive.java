package org.usfirst.frc.team6500.trc.wrappers.systems.drives;

import org.usfirst.frc.team6500.trc.util.TRCDriveParams;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * A wrapper on top of the standard MecanumDrive class, as the WPILib one has some odd behavior and so we can add features
 */
public class TRCMecanumDrive extends MecanumDrive
{
	// Keeps track of whether we want the x and y to be swapped; this exists because the joystick and drive classes have
	// differing default parameter placement for x and y and it can get very confusing 
	private boolean xyswap;
	// Can't access these from the superclass so we have to keep track of them ourselves
	private final SpeedController frontLeftMotor;
	private final SpeedController rearLeftMotor;
	private final SpeedController frontRightMotor;
	private final SpeedController rearRightMotor;

	/**
	 * Default constructor, same as superclass plus setting xyswap to off by default.
	 */
	public TRCMecanumDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor)
	{
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.xyswap = false;
		
		this.frontLeftMotor = frontLeftMotor;
		this.rearLeftMotor = rearLeftMotor;
		this.frontRightMotor = frontRightMotor;
		this.rearRightMotor = rearRightMotor;
	}
	
	/**
	 * Default constructor, same as superclass but setting xyswap to something specific.
	 * 
	 * @param swapxy Whether to swap x and y inputs for the driveCartesian method.
	 */
	public TRCMecanumDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor, boolean swapxy)
	{
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.xyswap = swapxy;
		
		this.frontLeftMotor = frontLeftMotor;
		this.rearLeftMotor = rearLeftMotor;
		this.frontRightMotor = frontRightMotor;
		this.rearRightMotor = rearRightMotor;
	}
	
	
	/**
	 * Convenience method which takes a TRCDriveParams and uses it with the regular driveCartesian
	 * 
	 * @param dps TRCDriveParams to use
	 */
	public void driveCartesian(TRCDriveParams dps)
	{
		this.driveCartesian(dps.getRealY(), dps.getRealX(), dps.getRealZ());
	}
	
	/**
	 * Method on top of the default driveCartesian so we can swap x and y if needed before passing it off to the default.
	 */
	@Override
	public void driveCartesian(double ySpeed, double xSpeed, double zRotation)
	{
		if (this.xyswap)
		{
			super.driveCartesian(xSpeed, ySpeed, zRotation);
		}
		else
		{
			super.driveCartesian(ySpeed, xSpeed, zRotation);
		}
	}
	
	/**
	 * Drive an individual wheel of the drivetrain at a specific speed
	 * 
	 * @param wheel Which wheel to drive (one of kFrontLeft, kRearLeft, kFrontRight, or kRearRight)
	 * @param power -1.0 to 1.0
	 */
	public void driveWheel(MotorType wheel, double power)
	{
		switch(wheel)
		{
		case kFrontLeft:
			this.frontLeftMotor.set(power);
			break;
		case kRearLeft:
			this.rearLeftMotor.set(power);
			break;
		case kFrontRight:
			this.frontRightMotor.set(power);
			break;
		case kRearRight:
			this.rearRightMotor.set(power);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Set whether the driveCartesian should swap the x and y inputs.  This is done to prevent confusion, as the joystick
	 * and default driveCartesian methods use different interpretations for x and y
	 * 
	 * @param swapxy Whether to swap x and y inputs for the driveCartesian method.
	 */
	public void setXYSwap(boolean swapxy)
	{
		this.xyswap = swapxy;
	}
	
	/**
	 * Check if the x and y inputs for the driveCartesian method are currently being swapped.
	 * 
	 * @return Whether the x and y inputs for the driveCartesian method are being swappped
	 */
	public boolean getXYSwapped()
	{
		return this.xyswap;
	}
}
