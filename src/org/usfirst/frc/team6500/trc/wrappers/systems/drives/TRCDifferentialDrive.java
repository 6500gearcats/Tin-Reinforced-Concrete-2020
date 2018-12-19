package org.usfirst.frc.team6500.trc.wrappers.systems.drives;

import org.usfirst.frc.team6500.trc.util.TRCDriveParams;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DifferentialArcadeMode;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * A wrapper on top of the standard DifferentialDrive class, to make it easier to add features
 */
public class TRCDifferentialDrive extends DifferentialDrive
{
	// When passing in a TRCDriveParams, we need to know which axis (X or Z to use for rotation)
	private DifferentialArcadeMode arcadeMode;
	
	
	/**
	 * Default constructor, same as superclass plus setting arcadeMode to ZRotation by default.
	 */
	public TRCDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor)
	{
		super(leftMotor, rightMotor);
		this.arcadeMode = DifferentialArcadeMode.ZRotation;
	}
	
	/**
	 * Default constructor, same as superclass but setting arcadeMode to something specific.
	 * 
	 * @param arcadeType Which axis to use for rotation while using arcadeDrive and a TRCDriveParams
	 */
	public TRCDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor, DifferentialArcadeMode mode)
	{
		super(leftMotor, rightMotor);
		this.arcadeMode = mode;
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
			this.arcadeDrive(dps.getRealY(), dps.getRealX());
		}
		else if (arcadeMode == DifferentialArcadeMode.ZRotation)
		{
			this.arcadeDrive(dps.getRealY(), dps.getRealZ());
		}
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
}
