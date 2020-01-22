package frc.team6500.trc.wrappers.systems.drives;

import frc.team6500.trc.util.TRCDriveParams;
import frc.team6500.trc.util.TRCTypes.DifferentialArcadeMode;
import frc.team6500.trc.util.TRCTypes;
import frc.team6500.trc.util.TRCTypes.SpeedControllerType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;


/**
 * A wrapper on top of the standard DifferentialDrive class, to make it easier to add features
 */
public class TRCDifferentialDrive
{
	// When passing in a TRCDriveParams, we need to know which axis (X or Z to use for rotation)
    protected DifferentialArcadeMode arcadeMode;

    protected final SpeedController leftMotor;
	protected final SpeedController rightMotor;
	public final SpeedController outputMotors[] = new SpeedController[2];
	
    protected DifferentialDrive drive;
	

	/**
     * Simpler constructor, takes info about the motors and turns it into objects and sets arcadeMode to ZRotation by default.
     * Motor info should ALWAYS be provided in the following order: front-left, rear-left, front-right, rear-right
     * 
     * @param motorPorts The ports the motors are plugged into
     * @param motorTypes The types of speed controllers the motors are plugged into
	 */
	public TRCDifferentialDrive(int[] motorPorts, SpeedControllerType[] motorTypes, boolean[] inversion)
	{
		SpeedController[] controllers = TRCTypes.speedControllerCreate(motorPorts, motorTypes);
		for (int i = 0; i < inversion.length; i++)
		{
			controllers[i].setInverted(inversion[i]);
		}
        this.leftMotor  = new SpeedControllerGroup(controllers[0], controllers[1]); this.outputMotors[0] = this.leftMotor;
        this.rightMotor = new SpeedControllerGroup(controllers[2], controllers[3]); this.outputMotors[1] = this.rightMotor;

		this.drive = new DifferentialDrive(this.leftMotor, this.rightMotor);
		this.arcadeMode = DifferentialArcadeMode.ZRotation;
	}
	
	/**
     * More specific constructor, takes info about the motors and turns it into objects and sets arcadeMode to what the user needs it to be
     * Motor info should ALWAYS be provided in the following order: front-left, rear-left, front-right, rear-right
     * 
     * @param motorPorts The ports the motors are plugged into
     * @param motorTypes The types of speed controllers the motors are plugged into
     * @param arcadeType The axis of controller input which should be used for rotation
	 */
	public TRCDifferentialDrive(int[] motorPorts, SpeedControllerType[] motorTypes, boolean[] inversion, DifferentialArcadeMode arcadeType)
	{
		SpeedController[] controllers = TRCTypes.speedControllerCreate(motorPorts, motorTypes);
		for (int i = 0; i < inversion.length; i++)
		{
			controllers[i].setInverted(inversion[i]);
		}
        this.leftMotor  = new SpeedControllerGroup(controllers[0], controllers[1]); this.outputMotors[0] = this.leftMotor;
        this.rightMotor = new SpeedControllerGroup(controllers[2], controllers[3]); this.outputMotors[1] = this.rightMotor;

		this.drive = new DifferentialDrive(this.leftMotor, this.rightMotor);
		this.arcadeMode = arcadeType;
	}
	
	/**
	 * Convenience method which takes a TRCDriveParams and uses it with the regular arcadeDrive
	 * 
	 * @param dps TRCDriveParams to use
	 */
	public void arcadeDrive(TRCDriveParams dps)
	{
		if (arcadeMode == DifferentialArcadeMode.XRotation)
		{
			this.drive.arcadeDrive(dps.getRealY(), dps.getRealX());
		}
		else if (arcadeMode == DifferentialArcadeMode.ZRotation)
		{
			this.drive.arcadeDrive(dps.getRealY(), dps.getRealZ());
		}
	} 
    
    /**
	 * Identical to method from DifferentialDrive
	 */
	public void arcadeDrive(double forwardSpeed, double rotationalSpeed, boolean squared)
	{
	    this.drive.arcadeDrive(forwardSpeed, rotationalSpeed, squared);
	}

	public void tankDrive(double leftSpeed, double rightSpeed, boolean squared)
	{
		this.drive.tankDrive(leftSpeed, rightSpeed, squared);
	}

	public void tankDrive(TRCDriveParams dps)
	{
		this.tankDrive(dps.getRealX(), dps.getRealY(), true);
	}
	
	/**
	 * Set which axis should be used for rotation when using the arcadeDrive method, as different drivers prefer different ways
	 * 
	 * @param mode Which type of rotation to use, xaxis or zaxis
	 */
	public void setArcadeMode (DifferentialArcadeMode mode)
	{
		this.arcadeMode = mode;
	}
	
	/**
	 * Check which axis is being used for rotation when using the arcadeDrive method
	 * 
	 * @return Which axis is currently in use with arcadeDrive
	 */
	public DifferentialArcadeMode getArcadeMode()
	{
		return this.arcadeMode;
	}

	public SpeedController[] getOutputMotors()
	{
		return this.outputMotors;
	}
}
