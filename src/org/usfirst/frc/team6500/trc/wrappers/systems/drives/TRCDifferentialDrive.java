package org.usfirst.frc.team6500.trc.wrappers.systems.drives;

import org.usfirst.frc.team6500.trc.util.TRCDriveParams;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DifferentialArcadeMode;
import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.DMC60;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SD540;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

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
	
    
    private SpeedController[] speedControllerCreate(int[] motorPorts, SpeedControllerType[] motorTypes)
    {
        SpeedController newControllers[] = new SpeedController[motorPorts.length];

        for (int i = 0; i < motorPorts.length; i++)
		{
			SpeedController motor = null;
			
			switch(motorTypes[i])
			{
			case DMC60:
				motor = new DMC60(motorPorts[i]);
				break;
			case Jaguar:
				motor = new Jaguar(motorPorts[i]);
				break;
			case PWMTalonSRX:
				motor = new PWMTalonSRX(motorPorts[i]);
				break;
			case PWMVictorSPX:
				motor = new PWMVictorSPX(motorPorts[i]);
				break;
			case SD540:
				motor = new SD540(motorPorts[i]);
				break;
			case Spark:
				motor = new Spark(motorPorts[i]);
				break;
			case Talon:
				motor = new Talon(motorPorts[i]);
				break;
			case Victor:
				motor = new Victor(motorPorts[i]);
				break;
			case VictorSP:
				motor = new VictorSP(motorPorts[i]);
				break;
			default:
				motor = new Spark(motorPorts[i]);
				break;
			}
			
			((Spark) motor).close();
			newControllers[i] = motor;
		}

        return newControllers;
    }

	/**
     * Simpler constructor, takes info about the motors and turns it into objects and sets arcadeMode to ZRotation by default.
     * Motor info should ALWAYS be provided in the following order: front-left, rear-left, front-right, rear-right
     * 
     * @param motorPorts The ports the motors are plugged into
     * @param motorTypes The types of speed controllers the motors are plugged into
	 */
	public TRCDifferentialDrive(int[] motorPorts, SpeedControllerType[] motorTypes)
	{
        SpeedController[] controllers = speedControllerCreate(motorPorts, motorTypes);
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
	public TRCDifferentialDrive(int[] motorPorts, SpeedControllerType[] motorTypes, DifferentialArcadeMode arcadeType)
	{
		SpeedController[] controllers = speedControllerCreate(motorPorts, motorTypes);
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
