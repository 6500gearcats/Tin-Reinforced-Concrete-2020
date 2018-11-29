package org.usfirst.frc.team6500.trc.systems;

import java.util.HashMap;

import edu.wpi.first.wpilibj.Joystick;

public class TRCDriveInput
{
	private static HashMap<Integer, Joystick> inputSticks;
	private static HashMap<Integer, HashMap<Integer, Runnable>> buttonFuncs; //oh god why does this work
	
	public static void initializeDriveInput(int[] ports)
	{
		for (int port : ports)
		{
			inputSticks.put(port, new Joystick(port));
		}
	}
	
	/**
	 * Assign a function to be run when a certain button on a certain joystick is pressed
	 * 
	 * @param joystickPort Joystick to bind to
	 * @param button Button to bind to
	 * @param func Function to be run
	 */
	public static void bindButton(int joystickPort, int button, Runnable func)
	{
		buttonFuncs.get(joystickPort).put(button, func);
	}
	
	/**
	 * Checks every button on every Joystick, and if the button is pressed and has a function bound to it then
	 * the function will be run
	 */
	public static void updateDriveInput()
	{
		for (Integer stickPort : inputSticks.keySet())
		{
			for (int button = 0; button < inputSticks.get(stickPort).getButtonCount(); button++)
			{
				if (inputSticks.get(stickPort).getRawButton(button) && buttonFuncs.get(stickPort).containsKey(button)) // Simply put, is the button pressed && is there are function bound to it
				{
					buttonFuncs.get(inputSticks.get(stickPort).getPort()).get(button).run();
				}
			}
		}
	}
	
	public static void getButton(int joystickPort, int button)
	{
		
	}
}
