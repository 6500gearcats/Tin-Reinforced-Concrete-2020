/*
 *	TRCDriveInput
 * 		totally static class to handle input from the joysticks
 */

package org.usfirst.frc.team6500.trc.systems;

import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team6500.trc.util.TRCController;
import org.usfirst.frc.team6500.trc.util.TRCDriveParams;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.ControllerType;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;



public class TRCDriveInput
{
	private static ArrayList<TRCController> inputSticks;
	// private static HashMap<Integer, Joystick> inputSticks; removed this because you can just <joystick>.getPort()

    private static HashMap<Integer, HashMap<Object, Runnable>> pressFuncs; 
	private static HashMap<Integer, HashMap<Object[], Runnable>> absenceFuncs; // oh god why does this work  // spelled "absence", not "absense" :P
	/* Array in a HashMap in a HashMap... */
	private static double baseSpeed = 0.0;
    private static double boostSpeed = 0.0;
    
	/**
	 * Setup the DriveInput class.  Do this before using any other methods in this class.
	 * 
	 * @param ports The ids of the USB ports the joysticks are plugged in to
	 * @param speedBase The default base speed of the robot
	 */
	public static void initializeDriveInput(int[] ports, ControllerType types[], double speedBase, double speedBoost)
	{
		inputSticks = new ArrayList<TRCController>();
		pressFuncs = new HashMap<Integer, HashMap<Object, Runnable>>();
		absenceFuncs = new HashMap<Integer, HashMap<Object[], Runnable>>();

		for (int i = 0; i < ports.length; i++)
		{
			inputSticks.add(new TRCController(ports[i], types[i]));
			pressFuncs.put(ports[i], new HashMap<Object, Runnable>());
			absenceFuncs.put(ports[i], new HashMap<Object[], Runnable>());
		}
		
        baseSpeed = speedBase;
        boostSpeed = speedBoost;
        
		//TRCNetworkData.logString(VerbosityType.Log_Info, "Driver Input is online.");
	}
	
	/**
	 * Assign a function to be run when a certain button on a certain joystick is pressed
	 * 
	 * @param joystickPort Joystick to bind to
	 * @param button Button to bind to
	 * @param func Function to be run
	 */
	public static void bindButtonPress(int joystickPort, int button, Runnable func)
	{
		pressFuncs.get(joystickPort).put(button, func);
		TRCNetworkData.logString(VerbosityType.Log_Debug, "A binding has been created for Button " + button + " on Joystick " + joystickPort);
    }
    
    /**
	 * Assign a function to be run when a certain set of buttons on a certain joystick are not being pressed
	 * 
	 * @param joystickPort Joystick to bind to
	 * @param buttons Buttons to bind to
	 * @param func Function to be run
	 */
	public static void bindButtonAbsence(int joystickPort, Object[] buttons, Runnable func)
	{
		absenceFuncs.get(joystickPort).put(buttons, func);
		TRCNetworkData.logString(VerbosityType.Log_Debug, "An absence binding has been created for " + buttons.length + "buttons on Joystick " + joystickPort);
	}
	
	/**
	 * Checks every button on every Joystick, and if the button is pressed and has a function bound to it then
	 * the function will be run
	 */
	public static void checkButtonBindings()
	{
		for (int index = 0; index < inputSticks.size(); index++) // get all input sticks (2; gunner and driver)
		{
			int stickPort = inputSticks.get(index).getPort(); // get the port of that joystick

			if (pressFuncs.containsKey(stickPort)) // if the joystick is supported on pressFuncs
			{
				for (Object button : pressFuncs.get(stickPort).keySet()) // get all supported buttons in pressFuncs
				{
					if (inputSticks.get(stickPort).getButton(button)) // if the button is pressed, run it's runnable
					{
						//System.out.println(button);
						pressFuncs.get(stickPort).get(button).run(); // RUN!
					}
				}
			}
			
			if (absenceFuncs.containsKey(stickPort)) // if the joystick is supported on absenceFuncs
			{
            	for (Object[] buttonList : absenceFuncs.get(stickPort).keySet()) // get all supported buttons in absenceFuncs
				{
					for (int i = 0; i < buttonList.length; i++) // check all buttons in the button list
					{
						if (!inputSticks.get(stickPort).getButton(buttonList[i])) // if the button is not pressed...
						{
							if (i == buttonList.length - 1)
							{
								absenceFuncs.get(inputSticks.get(stickPort).getPort()).get(buttonList).run();
								break;
							}
							continue;
						}
						break;
					} // This part of the function runs through all of the absence bindings for all joysticks,
					  // and if after going through all of them not being pressed on the last one it activates the bound function
				}
			}
		}
	}
	
	/**
	 * Get whether a certain button on a certain joystick is currently being pressed
	 * 
	 * @param joystickPort What joystick to check the button on
	 * @param button Number of button to check
	 * @return True if button on joystick is pressed, false otherwise
	 */
	public static boolean getButton(int joystickPort, int button)
	{
		return inputSticks.get(joystickPort).getButton(button);
	}

	public static TRCController getController(int joystickPort)
	{
		return inputSticks.get(joystickPort);
	}
	
	/**
	 * Get the POV (D-Pad or thumbstick) position from a joystick
	 * 
	 * @param joystickPort What joystick to check the POV on
	 * @return The position, in degrees, of the POV
	 */
	public static int getPOV(int joystickPort, int button)
	{
		return inputSticks.get(joystickPort).getPOV();
	}
	
	/**
	 * Calculates the value of the throttle in a manner which makes the number much more sensible
	 * 
	 * @param joystick The joystick get throttle value from
	 * @return The simplified throttle value
	 */
	public static double getThrottle(int joystickPort) 
	{
		double multiplier;
		
		multiplier = getRawThrottle(joystickPort) + 1;        // Range is -1 to 1, change to 0 to 2 cuz its easier to work with
		multiplier = multiplier / 2;                          // Reduce to a scale between 0 to 1
        multiplier = 1 - multiplier;                          // Throttle is backwards from expectation, flip it
        if (!inputSticks.get(joystickPort).getButton(1))
        {
            multiplier = multiplier * baseSpeed;              // Mix in some of that sweet default...
        }
        else
        {
            multiplier = multiplier * boostSpeed;             // Unless the trigger is pressed, then mix in some of that sweet boost :)
        }
		
		return multiplier;
	}
	
	/**
	 * Get the raw value of a joystick's throttle
	 * This value is kinda hard to work with, so use getThrottle for a better version
	 * 
	 * @param joystickPort
	 * @return The value from the throttle which is returned by default
	 */
	public static double getRawThrottle(int joystickPort)
	{
		return inputSticks.get(joystickPort).getThrottle();
	}
	
	/**
	 * Get a TRCDriveParams which has all the values from joystick joystickPort for use in driving the robot
	 * 
	 * @param joystickPort What joystick to get values from
	 * @return TRCDriveParams which have been set from values from the joystick joystickPort
	 */
	public static TRCDriveParams getStickDriveParams(int joystickPort)
	{
		TRCDriveParams params = new TRCDriveParams();
		
		params.setRawX(inputSticks.get(joystickPort).getX());
		params.setRawY(inputSticks.get(joystickPort).getY());
		params.setRawZ(inputSticks.get(joystickPort).getZ());
		params.setM(getThrottle(joystickPort));
		
		return params;
	}
}
