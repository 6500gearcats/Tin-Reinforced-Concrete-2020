package org.usfirst.frc.team6500.trc.auto;

import org.usfirst.frc.team6500.trc.systems.TRCDirectionalSystem;
import org.usfirst.frc.team6500.trc.util.TRCTypes.DirectionalSystemActionType;

import edu.wpi.first.wpilibj.Timer;

import java.util.HashMap;


public class TRCDirectionalSystemAction extends Thread
{
	private TRCDirectionalSystem system;
	private DirectionalSystemActionType type;
    private double delay;
    private boolean thread;

    private static HashMap<String, TRCDirectionalSystem> systemRegistry;

    
    /**
     * Register a DirectionalSystem for later use
     */
    public void registerSystem(String systemName, TRCDirectionalSystem actionSystem)
    {
        systemRegistry.put(systemName, actionSystem);
    }

	/**
	 * Have actionSystem execute action for time seconds, optionally threaded, when the start() method is called
	 * 
	 * @param actionSystem The system to have the action execute for
	 * @param action What action the system should take, one of {@link DirectionalSystemActionType}
	 * @param time How many seconds the action should be executed for
     * @param threaded Whether the action should be executed in a thread or not
	 */
	public TRCDirectionalSystemAction(String actionSystem, DirectionalSystemActionType action, double time, boolean threaded)
	{
		this.system = systemRegistry.get(actionSystem);
		this.type = action;
        this.delay = time;
        this.thread = threaded;
    }
    
    /**
     * Jack the usual start() method so we can choose whether to be threaded
     */
    @Override
    public void start()
    {
        if (this.thread)
        {
            super.start();
        }
        else
        {
            this.run();
        }
    }
	
	/**
	 * Method run to do the action.  DO NOT call this directly, instead use .start()
	 */
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
