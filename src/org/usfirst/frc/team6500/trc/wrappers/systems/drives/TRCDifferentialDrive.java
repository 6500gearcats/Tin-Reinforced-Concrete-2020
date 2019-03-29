package org.usfirst.frc.team6500.trc.wrappers.systems.drives;

import org.usfirst.frc.team6500.trc.auto.TRCDriveSync;
import org.usfirst.frc.team6500.trc.util.TRCDriveParams;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DifferentialArcadeMode;
import org.usfirst.frc.team6500.trc.util.TRCTypes;
import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;


/**
 * A wrapper on top of the standard DifferentialDrive class, to make it easier to add features
 */
public class TRCDifferentialDrive
{
	// When passing in a TRCDriveParams, we need to know which axis (X or Z to use for rotation)
    private DifferentialArcadeMode arcadeMode;

    private final SpeedController leftMotor;
	private final SpeedController rightMotor;
    
    private DifferentialDrive drive;
	

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
        this.leftMotor = new SpeedControllerGroup(controllers[0], controllers[1]);
        this.rightMotor = new SpeedControllerGroup(controllers[2], controllers[3]);

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
        this.leftMotor = new SpeedControllerGroup(controllers[0], controllers[1]);
        this.rightMotor = new SpeedControllerGroup(controllers[2], controllers[3]);

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
		TRCDriveSync.assertTeleop();
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
		TRCDriveSync.assertTeleop();
	    this.drive.arcadeDrive(forwardSpeed, rotationalSpeed, squared);
	}
	
	/**
	 * Set which axis should be used for rotation when using the arcadeDrive method, as different drivers prefer different ways
	 * 
	 * @param mode Which type of rotation to use, xaxis or zaxis
	 */
	public void setArcadeMode (DifferentialArcadeMode mode)
	{
		TRCDriveSync.assertTeleop();
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
}
