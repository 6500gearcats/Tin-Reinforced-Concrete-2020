package org.usfirst.frc.team6500.trc.systems;

import java.util.HashMap;
import java.util.Iterator;

import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;
import org.usfirst.frc.team6500.trc.util.TRCTypes;

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
	protected boolean invertSide;
	protected HashMap<Integer, SpeedController> outputMotors;
	protected double defaultForwardSpeed, defaultReverseSpeed;
	
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
		this.outputMotors = new HashMap<Integer, SpeedController>();
		SpeedController[] controllers = TRCTypes.speedControllerCreate(motorPorts, motorTypes);

		for (int i = 0; i < motorPorts.length; i++)
		{
			this.outputMotors.put(motorPorts[i], controllers[i]);
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
		Iterator<Integer> ports = this.outputMotors.keySet().iterator();

		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			int port = ports.next();
			this.outputMotors.get(port).set(0.0);
		}
	}
	
	/**
	 * Drive all motors in the system forward, at the set default forward speed
	 */
	public void driveForward()
	{
		Iterator<Integer> ports = this.outputMotors.keySet().iterator();

		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			int port = ports.next();
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(port).set(-this.defaultForwardSpeed);
			}
			else
			{
				this.outputMotors.get(port).set(this.defaultForwardSpeed);
			}
		}
	}
	
	/**
	 * Drive all motors in the system in reverse, at the set default reverse speed
	 */
	public void driveReverse()
	{
		Iterator<Integer> ports = this.outputMotors.keySet().iterator();

		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			int port = ports.next();
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(port).set(-this.defaultReverseSpeed);
			}
			else
			{
				this.outputMotors.get(port).set(this.defaultReverseSpeed);
			}
		}
	}
	
	/**
	 * Drive all motors in the system forward, at full speed
	 */
	public void fullDriveForward()
	{
		Iterator<Integer> ports = this.outputMotors.keySet().iterator();

		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			int port = ports.next();
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(port).set(-1.0);
			}
			else
			{
				this.outputMotors.get(port).set(1.0);
			}
		}
	}
	
	/**
	 * Drive all motors in the system in reverse, at full speed
	 */
	public void fullDriveReverse()
	{
		Iterator<Integer> ports = this.outputMotors.keySet().iterator();

		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			int port = ports.next();
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(port).set(1.0);
			}
			else
			{
				this.outputMotors.get(port).set(-1.0);
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
		Iterator<Integer> ports = this.outputMotors.keySet().iterator();

		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			int port = ports.next();
			if (this.invertSide && i % 2 == 1)
			{
				this.outputMotors.get(port).set(-power);
			}
			else
			{
				this.outputMotors.get(port).set(power);
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
