package org.usfirst.frc.team6500.trc.systems;


import java.util.HashMap;
import java.util.Iterator;

import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;


public class TRCPneumaticSystem
{
    private HashMap<Integer, Solenoid> outputSolenoids;
    private boolean doubleReverse;
    private boolean open;

    private static int pcmId;
    private static Compressor compressor;
    

    /**
     * Setup the PCM and compressor.  Do this BEFORE initializing any TRCPneumaticSystem
     * 
     * @param id PCM's CAN id
     */
    public static void setupPneumatics(int id)
    {
        pcmId = id;
        compressor = new Compressor(pcmId);

        compressor.start();
    }

    
	/**
	 * Wrap up a game-specific pneumatic system of a robot into an easy-to-manage way.  The methods for control within the object shouldn't be used outright;
	 * for autonomous do it in a threaded way with a {@link org.usfirst.frc.team6500.trc.auto.TRCPneumaticSystemAction TRCPneumaticSystemAction}, 
	 * or for teleoperation bind them to buttons in {@link TRCDriveInput}. Only create this after setting up the pneumatics.
	 * 
	 * @param solenoidPorts A list of what ports the system's solenoids are plugged into
     * @param secondReverse If this is true, reversing will output from the second port instead of releasing pressure
	 */
	public TRCPneumaticSystem(int[] solenoidPorts, boolean secondReverse)
	{
		this.outputSolenoids = new HashMap<Integer, Solenoid>();

		for (int i = 0; i < solenoidPorts.length; i++)
		{
			this.outputSolenoids.put(solenoidPorts[i], new Solenoid(pcmId, solenoidPorts[i]));
        }
        
        
		this.doubleReverse = secondReverse;

		TRCNetworkData.logString(VerbosityType.Log_Info, "PneumaticDirectionalSystem created");
		TRCNetworkData.logString(VerbosityType.Log_Debug, "Connected to solenoids on ports");
		for (int i = 0; i < solenoidPorts.length; i++)
		{
			TRCNetworkData.logString(VerbosityType.Log_Debug, Integer.toString(solenoidPorts[i]));
        }
        
        this.open = false;
        this.fullClose();
	}
	
	
	/**
	 * Close all solenoids in the system
	 */
	public void fullClose()
	{
        Iterator<Integer> ports = this.outputSolenoids.keySet().iterator();

        if (this.outputSolenoids.size() % 2 == 0)
        {
            for (int i = 0; i < this.outputSolenoids.size(); i += 2)
            {
                int port = ports.next();
                int port2 = ports.next();
                if (this.doubleReverse)
                {
                    this.outputSolenoids.get(port).set(false);
                    this.outputSolenoids.get(port2).set(true);
                }
                else
                {
                    this.outputSolenoids.get(port).set(false);
                    this.outputSolenoids.get(port2).set(false);
                }
            }
        }
        else
        {
            for (int i = 0; i < this.outputSolenoids.size(); i++)
            {
                int port = ports.next();
                this.outputSolenoids.get(port).set(false);
            }
        }

        this.open = false;
	}
	
	/**
	 * Open all solenoids in the system
	 */
	public void fullOpen()
	{
        Iterator<Integer> ports = this.outputSolenoids.keySet().iterator();

        if (this.outputSolenoids.size() % 2 == 0)
        {
            for (int i = 0; i < this.outputSolenoids.size(); i += 2)
            {
                int port = ports.next();
                int port2 = ports.next();
                if (this.doubleReverse)
                {
                    this.outputSolenoids.get(port).set(true);
                    this.outputSolenoids.get(port2).set(false);
                }
                else
                {
                    this.outputSolenoids.get(port).set(true);
                    this.outputSolenoids.get(port2).set(true);
                }
            }
        }
        else
        {
            for (int i = 0; i < this.outputSolenoids.size(); i++)
            {
                int port = ports.next();
                this.outputSolenoids.get(port).set(true);
            }
        }

        this.open = true;
    }
    
    /**
     * Toggle the system
     */
    public void toggle()
    {
        if (this.open)
        {
            this.fullClose();
        }
        else
        {
            this.fullOpen();
        }
    }
	
	/**
	 * Set the solenoid on port port to be state
     * Be aware this will screw up toggling.
	 * 
	 * @param state What state to set the solenoid to
	 * @param port The port where the solenoid to be driven is plugged in
	 */
	public void individualSet(boolean state, int port)
	{
		this.outputSolenoids.get(port).set(state);
	}
}
