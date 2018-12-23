package org.usfirst.frc.team6500.trc.wrappers.systems;

import java.util.HashMap;

import org.usfirst.frc.team6500.trc.util.TRCTypes.SpeedControllerType;

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

public class DirectionalSystem
{
	private boolean invertSide;
	private HashMap<Integer, SpeedController> outputMotors;
	private double defaultForwardSpeed, defaultReverseSpeed;
	
	public DirectionalSystem(int[] motorPorts, SpeedControllerType[] motorTypes, boolean balanceInvert, double dFSpeed, double dRSpeed)
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
	}
	
	public void setInvert(boolean balanceInvert)
	{
		this.invertSide = balanceInvert;
	}
	
	public void fullStop()
	{
		for (int i = 0; i < this.outputMotors.size(); i++)
		{
			this.outputMotors.get(i).set(0.0);
		}
	}
	
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
	
	public void individualDrive(double power, int port)
	{
		this.outputMotors.get(port).set(power);
	}
}
