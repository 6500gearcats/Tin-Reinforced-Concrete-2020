package frc.team6500.trc.sensors;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.team6500.trc.util.TRCNetworkData;
import frc.team6500.trc.util.TRCTypes.VerbosityType;

import edu.wpi.first.networktables.NetworkTable;

/**
 * A simple class for retrieving basic information about OpenCV contours stored within NetworkTables
 */
public class TRCNetworkVision
{
	public static NetworkTableInstance tableServer;
	public static NetworkTable table;
	
	/**
	 * Create the NetworkTable server and create a "vision" table.  Do this before telling the external processor to connect
	 */
	public static void initializeVision()
	{
		tableServer = NetworkTableInstance.getDefault();
		tableServer.startServer();
		
		table = tableServer.getTable("/vision");

		TRCNetworkData.logString(VerbosityType.Log_Info, "NetworkVision is online.");
	}
	
	/**
	 * Get the x position of the current contour
	 * 
	 * @return The current contour's x position
	 */
	public static int getContourX()
	{
		NetworkTableEntry xEntry = table.getEntry("x");
		
		return xEntry.getNumber(0).intValue();
	}
	
	/**
	 * Get the y position of the current contour
	 * 
	 * @return The current contour's y position
	 */
	public static int getContourY()
	{
		NetworkTableEntry yEntry = table.getEntry("y");
		
		return yEntry.getNumber(0).intValue();
	}
	
	/**
	 * Get the width of the current contour
	 * 
	 * @return The current contour's width
	 */
	public static int getContourWidth()
	{
		NetworkTableEntry widthEntry = table.getEntry("width");
		
		return widthEntry.getNumber(0).intValue();
	}
	
	/**
	 * Get the height of the current contour
	 * 
	 * @return The current contour's height
	 */
	public static int getContourHeight()
	{
		NetworkTableEntry heightEntry = table.getEntry("height");
		
		return heightEntry.getNumber(0).intValue();
	}
}
