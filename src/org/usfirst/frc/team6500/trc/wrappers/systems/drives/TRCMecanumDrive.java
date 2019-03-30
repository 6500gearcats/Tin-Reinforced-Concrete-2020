package org.usfirst.frc.team6500.trc.wrappers.systems.drives;

import org.usfirst.frc.team6500.trc.auto.TRCDriveSync;
import org.usfirst.frc.team6500.trc.util.TRCDriveParams;
import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;
import org.usfirst.frc.team6500.trc.util.TRCTypes;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;

import edu.wpi.first.wpilibj.SpeedController;


/**
 * A wrapper on top of the standard MecanumDrive class, as the WPILib one has some odd behavior and so we can add features
 */
public class TRCMecanumDrive
{
	// Keeps track of whether we want the x and y to be swapped; this exists because the joystick and drive classes have
	// differing default parameter placement for x and y and it can get very confusing 
	private boolean xyswap;
	// Can't access these from the superclass so we have to keep track of them ourselves
	private final SpeedController frontLeftMotor;
	private final SpeedController rearLeftMotor;
	private final SpeedController frontRightMotor;
    private final SpeedController rearRightMotor;
    
    private MecanumDrive drive;

	/**
	 * Simpler constructor, takes info about the motors and turns it into objects and sets the xyswap to false
     * Motor info should ALWAYS be provided in the following order: front-left, rear-left, front-right, rear-right
     * 
     * @param motorPorts The ports the motors are plugged into
     * @param motorTypes The types of speed controllers the motors are plugged into
	 */
	public TRCMecanumDrive(int[] motorPorts, SpeedControllerType[] motorTypes, boolean[] inversion)
	{
		SpeedController[] controllers = TRCTypes.speedControllerCreate(motorPorts, motorTypes);
		for (int i = 0; i < inversion.length; i++)
		{
			controllers[i].setInverted(inversion[i]);
		}
        this.frontLeftMotor = controllers[0];
        this.rearLeftMotor = controllers[1];
        this.frontRightMotor = controllers[2];
        this.rearRightMotor = controllers[3];

		drive = new MecanumDrive(this.frontLeftMotor, this.rearLeftMotor, this.frontRightMotor, this.rearRightMotor);
		this.xyswap = false;
	}
	
	/**
	 * More specific constructor, takes info about the motors and turns it into objects and sets the xyswap to what the user needs it to be
	 * Motor info should ALWAYS be provided in the following order: front-left, rear-left, front-right, rear-right
     * 
     * @param motorPorts The ports the motors are plugged into
     * @param motorTypes The types of speed controllers the motors are plugged into
	 * @param swapxy Whether to swap x and y inputs for the driveCartesian method.
	 */
	public TRCMecanumDrive(int[] motorPorts, SpeedControllerType[] motorTypes, boolean[] inversion, boolean swapxy)
	{
		SpeedController[] controllers = TRCTypes.speedControllerCreate(motorPorts, motorTypes);
		for (int i = 0; i < inversion.length; i++)
		{
			controllers[i].setInverted(inversion[i]);
		}
        this.frontLeftMotor = controllers[0];
        this.rearLeftMotor = controllers[1];
        this.frontRightMotor = controllers[2];
        this.rearRightMotor = controllers[3];

		drive = new MecanumDrive(this.frontLeftMotor, this.rearLeftMotor, this.frontRightMotor, this.rearRightMotor);
		this.xyswap = swapxy;
	}
	
	
	/**
	 * Convenience method which takes a TRCDriveParams and uses it with the regular driveCartesian
	 * 
	 * @param dps TRCDriveParams to use
	 */
	public void driveCartesian(TRCDriveParams dps)
	{
		drive.feed();
		this.driveCartesian(dps.getRealY(), dps.getRealX(), dps.getRealZ());
	}
	
	/**
	 * Method on top of the default driveCartesian so we can swap x and y if needed before passing it off to the default.
	 */
	public void driveCartesian(double ySpeed, double xSpeed, double zRotation)
	{
		drive.feed();
		if (this.xyswap)
		{
			drive.driveCartesian(xSpeed, -ySpeed, zRotation);
		}
		else
		{
			drive.driveCartesian(ySpeed, xSpeed, zRotation);
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
