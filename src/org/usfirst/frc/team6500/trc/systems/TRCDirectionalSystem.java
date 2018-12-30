package org.usfirst.frc.team6500.trc.systems;

import java.util.HashMap;

import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;

import edu.wpi.first.wpilibj.DMC60;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SD540;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

public class TRCDirectionalSystem
{
	private boolean invertSide;
	private HashMap<Integer, SpeedController> outputMotors;
	private double defaultForwardSpeed, defaultReverseSpeed;
	
	/**
	 * Wrap up a game-specific system of a robot into an easy-to-manage way.  The methods for control within the object shouldn't be used outright;
	 * for autonomous do it in a threaded way with a {@link org.usfirst.frc.team6500.trc.auto.TRCDirectionalSystemAction TRCDirectionalSystemAction}, 
	 * or for teleoperation bind them to buttons in {@link TRCDriveInput}.
	 * 
	 * @param motorPorts A list of what ports the system's motor's speed controllers are plugged into
	 * @param motorTypes A list of what types the system's speed controllers are.  See {@link SpeedControllerType}
	 * @param balanceInvert In some systems, it is necessary to not have adjacent motors go in the same direction. If this system is like that,
	 * 							this should be true, otherwise it should be false.
	 * @param dFSpeed Default speed to run the system at when going forward
	 * @param dRSpeed Default speed to run the system at when going in reverse
	 */
	public TRCDirectionalSystem(int[] motorPorts, SpeedControllerType[] motorTypes, boolean balanceInvert, double dFSpeed, double dRSpeed)
	{
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
			
			this.outputMotors.put(motorPorts[i], motor);
		}
		
		this.invertSide = balanceInvert;
		this.defaultForwardSpeed = dFSpeed;
		this.defaultReverseSpeed = dRSpeed;

		TRCNetworkData.logString(VerbosityType.Log_Info, "DirectionalSystem created");
		TRCNetworkData.logString(VerbosityType.Log_Debug, "Connected to motors on ports");
		for (int i = 0; i < motorPorts.length; i++)
		{
			TRCNetworkData.logString(VerbosityType.Log_Debug, Integer.toString(motorPorts[i]));
		}
	}
	
	/**
	 * Set whether every other motor in the system should have its output inverted.  Useful in specifc applications,
	 * and safer than doing the safe task electrically.
	 * 
	 * @param balanceInvert Should alternating motors of this system have their output inverted?
	 */
	public void setInvert(boolean balanceInvert)
	{
		this.invertSide = balanceInvert;
	}
	
	/**
	 * Stop all motors in the system
	 */
	public void fullStop()
	{
		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			this.outputMotors.get(i).set(0.0);
		}
	}
	
	/**
	 * Drive all motors in the system forward, at the set default forward speed
	 */
	public void driveForward()
	{
		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(i).set(-this.defaultForwardSpeed);
			}
			else
			{
				this.outputMotors.get(i).set(this.defaultForwardSpeed);
			}
		}
	}
	
	/**
	 * Drive all motors in the system in reverse, at the set default reverse speed
	 */
	public void driveReverse()
	{
		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(i).set(-this.defaultReverseSpeed);
			}
			else
			{
				this.outputMotors.get(i).set(this.defaultReverseSpeed);
			}
		}
	}
	
	/**
	 * Drive all motors in the system forward, at full speed
	 */
	public void fullDriveForward()
	{
		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(i).set(-1.0);
			}
			else
			{
				this.outputMotors.get(i).set(1.0);
			}
		}
	}
	
	/**
	 * Drive all motors in the system in reverse, at full speed
	 */
	public void fullDriveReverse()
	{
		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(i).set(1.0);
			}
			else
			{
				this.outputMotors.get(i).set(-1.0);
			}
		}
	}
	
	/**
	 * Drive all motors in the system at power specified
	 * 
	 * @param power What power to drive the motors at
	 */
	public void valueDrive(double power)
	{
		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(i).set(-power);
			}
			else
			{
				this.outputMotors.get(i).set(power);
			}
		}
	}
	
	/**
	 * Drive the motor on port port in the system at power specified
	 * 
	 * @param power What power to drive the motor at
	 * @param port The port where the motor to be driven is plugged in
	 */
	public void individualDrive(double power, int port)
	{
		this.outputMotors.get(port).set(power);
	}
}
