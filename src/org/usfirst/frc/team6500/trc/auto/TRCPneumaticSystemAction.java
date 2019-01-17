package org.usfirst.frc.team6500.trc.auto;

import org.usfirst.frc.team6500.trc.systems.TRCPneumaticSystem;
import org.usfirst.frc.team6500.trc.util.TRCTypes.PneumaticSystemActionType;

import edu.wpi.first.wpilibj.Timer;

import java.util.HashMap;


public class TRCPneumaticSystemAction extends Thread
{
	private TRCPneumaticSystem system;
	private PneumaticSystemActionType type;
    private double delay;
    private boolean thread;

    private static HashMap<String, TRCPneumaticSystem> systemRegistry;

    
    /**
     * Register a Pneumatic System for later use
     */
    public static void registerSystem(String systemName, TRCPneumaticSystem actionSystem)
    {
		try
		{
			systemRegistry.put(systemName, actionSystem);
		}
		catch (Exception e)
		{
			systemRegistry = new HashMap<String, TRCPneumaticSystem>();
			systemRegistry.put(systemName, actionSystem);
		}
    }

	/**
	 * Have actionSystem execute action for time seconds, optionally threaded, when the start() method is called
	 * 
	 * @param actionSystem The system to have the action execute for
	 * @param action What action the system should take, one of {@link PneumaticSystemActionType}
	 * @param time How many seconds the action should be executed for
     * @param threaded Whether the action should be executed in a thread or not
	 */
	public TRCPneumaticSystemAction(String actionSystem, PneumaticSystemActionType action, double time, boolean threaded)
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
		case Open:
			this.system.fullOpen();
			break;
		case Close:
			this.system.fullClose();
			break;
		default:
			break;
		}
		
		Timer.delay(this.delay);
		this.system.fullClose();
	}
}
