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

	/**
	 * Default constructor, same as superclass plus setting xyswap to off by default.
	 */
	public TRCMecanumDrive(SpeedController frontLeftMotor, SpeedController rearLeftMotor, SpeedController frontRightMotor, SpeedController rearRightMotor)
	{
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
		this.xyswap = false;
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
