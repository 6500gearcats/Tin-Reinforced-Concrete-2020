package org.usfirst.frc.team6500.trc.auto;

import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DirectionalSystemActionType;

import edu.wpi.first.wpilibj.Timer;

public class TRCDirectionalSystemAction extends Thread
{
	private TRCDirectionalSystem system;
	private DirectionalSystemActionType type;
	private double delay;
	
	public TRCDirectionalSystemAction(TRCDirectionalSystem actionSystem, DirectionalSystemActionType action, double time)
	{
		this.system = actionSystem;
		this.type = action;
		this.delay = time;
	}
	
	@Override
	public void run()
	{
		switch(this.type)
		{
		case Stop:
			this.system.fullStop();
			break;
		case Forward:
			this.system.driveForward();
			break;
		case Reverse:
			this.system.driveReverse();
			break;
		case FullForward:
			this.system.fullDriveForward();
			break;
		case FullReverse:
			this.system.fullDriveReverse();
			break;
		default:
			break;
		}
		
		Timer.delay(this.delay);
		this.system.fullStop();
	}
}
