package org.usfirst.frc.team6500.trc.systems;

import java.util.HashMap;

import org.usfirst.frc.team6500.trc.util.TRCDriveParams;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;

import edu.wpi.first.wpilibj.Joystick;

public class TRCDriveInput
{
	private static HashMap<Integer, Joystick> inputSticks;
	private static HashMap<Integer, HashMap<Integer, Runnable>> buttonFuncs; //oh god why does this work
	private static double baseSpeed = 0.0;
	
	/**
	 * Setup the DriveInput class.  Do this before using any other methods in this class.
	 * 
	 * @param ports The ids of the USB ports the joysticks are plugged in to
	 * @param bspeed The default max speed of the robot
	 */
	public static void initializeDriveInput(int[] ports, double bspeed)
	{
		for (int port : ports)
		{
			inputSticks.put(port, new Joystick(port));
		}
		
		baseSpeed = bspeed;
		TRCNetworkData.logString(VerbosityType.Log_Info, "Driver Input is online.");
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
		TRCNetworkData.logString(VerbosityType.Log_Debug, "A binding has been created for Button " + button + " on Joystick " + joystickPort);
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
	
	/**
	 * Get whether a certain button on a certain joystick is currently being pressed
	 * 
	 * @param joystickPort What joystick to check the button on
	 * @param button Number of button to check
	 * @return True if button on joystick is pressed, false otherwise
	 */
	public static boolean getButton(int joystickPort, int button)
	{
		return inputSticks.get(joystickPort).getRawButton(button);
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
	public static double getThrottle(int joystickPort) {
		double multiplier;
		
		multiplier = getRawThrottle(joystickPort) + 1;  // Range is -1 to 1, change to 0 to 2 cuz its easier to work with
		multiplier = multiplier / 2;                    // Reduce to a scale between 0 to 1
		multiplier = 1 - multiplier;                    // Throttle is backwards from expectation, flip it
		multiplier = multiplier * baseSpeed;            // Mix in some of that sweet default
		
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
		TRCDriveParams drivepars = new TRCDriveParams();
		
		drivepars.setRawX(inputSticks.get(joystickPort).getX());
		drivepars.setRawY(inputSticks.get(joystickPort).getY());
		drivepars.setRawZ(inputSticks.get(joystickPort).getZ());
		drivepars.setM(getThrottle(joystickPort));
		
		return drivepars;
	}
}
