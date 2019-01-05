package org.usfirst.frc.team6500.trc.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.usfirst.frc.team6500.trc.util.TRCDriveParams;
import org.usfirst.frc.team6500.trc.util.TRCNetworkData;
import org.usfirst.frc.team6500.trc.util.TRCTypes.VerbosityType;
import org.usfirst.frc.team6500.trc.wrappers.sensors.TCRJoystick;

public class TRCDriveInput
{
	private static HashMap<Integer, TCRJoystick> inputSticks;
	private static ExecutorService executor;
	private static double baseSpeed = 0.0;
    private static double boostSpeed = 0.0;
    
	/**
	 * Setup the DriveInput class.  Do this before using any other methods in this class.
	 * 
	 * @param ports The ids of the USB ports the joysticks are plugged in to
	 * @param speedBase The default base speed of the robot
	 */
	public static void initializeDriveInput(int[] ports, double speedBase, double speedBoost)
	{
		for (int port : ports)
		{
			inputSticks.put(port, new TCRJoystick(port));
		}
		
		executor = Executors.newCachedThreadPool();
		
        baseSpeed = speedBase;
        boostSpeed = speedBoost;
        
		TRCNetworkData.logString(VerbosityType.Log_Info, "Driver Input is online.");
	}
	
	/**
	 * Assigns a function to a joystick, activated when the button states match the required values.
	 * 
	 * <p>The function is run when the state of each buttons[i] equals values[i].
	 * 
	 * @param joystickPort port number of joystick to bind to
	 * @param buttons the array of buttons
	 * @param values the array of booleans corresponding to required button states
	 * @param func function to be run
	 */
	public static void bindButton(int joystickPort, int[] buttons, boolean[] values, Runnable func)
	{
		TCRJoystick joystick = inputSticks.get(joystickPort);
		joystick.bind(buttons, values, func);
		inputSticks.put(joystickPort, joystick);
		
		TRCNetworkData.logString(VerbosityType.Log_Debug, "A binding has been created for the Buttons " + buttons.toString() + " with activation values " + values.toString() + "on Joystick " + joystickPort);
    }
	
	/**
	 * Assigns a function to a joystick, activated when the button states match the required value.
	 * 
	 * <p>The function is run when the state of each button equals the value.
	 * 
	 * @param joystickPort port number of joystick to bind to
	 * @param buttons the array of buttons
	 * @param value the boolean corresponding to the required button states
	 * @param func function to be run
	 */
	public static void bindButton(int joystickPort, int button, boolean value, Runnable func)
	{
		TCRJoystick joystick = inputSticks.get(joystickPort);
		joystick.bind(new int[] {button}, new boolean[] {value}, func);
		inputSticks.put(joystickPort, joystick);
		
		TRCNetworkData.logString(VerbosityType.Log_Debug, "A binding has been created for the Button " + button + " with activation value " + value + "on Joystick " + joystickPort);
	}
	
	/**
	 * Assigns a function to a joystick, activated when the button state matches the required value.
	 * 
	 * <p>The function is run when the state of the button equals the value.
	 * 
	 * @param joystickPort port number of joystick to bind to
	 * @param buttons the button
	 * @param value the boolean corresponding to the required button state
	 * @param func function to be run
	 */
	public static void bindButton(int joystickPort, int[] buttons, boolean value, Runnable func)
	{
		boolean[] values = new boolean[buttons.length];
		for(int i = 0; i < buttons.length; i++)
		{
			values[i] = value;
		}
		
		TCRJoystick joystick = inputSticks.get(joystickPort);
		joystick.bind(buttons, values, func);
		inputSticks.put(joystickPort, joystick);
		
		TRCNetworkData.logString(VerbosityType.Log_Debug, "A binding has been created for the Buttons " + buttons.toString() + " with activation value " + value + "on Joystick " + joystickPort);
	}
	
	/**
	 * Runs all eligible functions.
	 */
	public static void updateDriveInput()
	{
		//Fills a list with all eligible tasks
		ArrayList<Runnable> tasks = new ArrayList<>();
		for (TCRJoystick joystick : inputSticks.values())
		{
			tasks.addAll(joystick.findTasks());
		}
		
		//Submits each eligible task
		for(Runnable task : tasks)
		{
			executor.submit(task);
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
		
		multiplier = getRawThrottle(joystickPort) + 1;        // Range is -1 to 1, change to 0 to 2 cuz its easier to work with
		multiplier = multiplier / 2;                          // Reduce to a scale between 0 to 1
        multiplier = 1 - multiplier;                          // Throttle is backwards from expectation, flip it
        if (!inputSticks.get(joystickPort).getRawButton(1))
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
		TRCDriveParams drivepars = new TRCDriveParams();
		
		drivepars.setRawX(inputSticks.get(joystickPort).getX());
		drivepars.setRawY(inputSticks.get(joystickPort).getY());
		drivepars.setRawZ(inputSticks.get(joystickPort).getZ());
		drivepars.setM(getThrottle(joystickPort));
		
		return drivepars;
	}
}