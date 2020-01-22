package frc.team6500.trc.util;

public class TRCDriveParams
{
	private double x, y, z;  /* 3 axis of directionality for the drive */
	private double m;        /*            Axis multiplier             */
	
	/**
	 * init a new blank TRCDriveParams that is resettable later
	 */
	public TRCDriveParams()
	{
		this.x = 0.0;
		this.y = 0.0;
		this.z = 0.0;
		this.m = 1.0;
	}
	
	/**
	 * DriveParams which have user values but the default multiplier
	 */
	public TRCDriveParams(double rawX, double rawY, double rawZ)
	{
		this.x = rawX;
		this.y = rawY;
		this.z = rawZ;
		this.m = 1.0;
	}
	
	/**
	 * DriveParams which are completely setup by the user on call
	 */
	public TRCDriveParams(double rawX, double rawY, double rawZ, double multiplier)
	{
		this.x = rawX;
		this.y = rawY;
		this.z = rawZ;
		this.m = multiplier;
	}
	
	// Get the raw values for the x, y, z or multiplier for this DriveParams
	public double getRawX () { return this.x; }
	public double getRawY () { return this.y; }
	public double getRawZ () { return this.z; }
	public double getM () { return this.m; }
	
	// Set the raw values for the x, y, z or multiplier for this DriveParams
	public void setRawX (double xspeed) { this.x = xspeed; }
	public void setRawY (double yspeed) { this.y = yspeed; }
	public void setRawZ (double zspeed) { this.z = zspeed; }
	public void setM (double multiplier) { this.m = multiplier; }
	
	// Get the calculated values for the x, y, or z for this DriveParams
	public double getRealX () { return this.x * this.m; }
	public double getRealY () { return this.y * this.m; }
	public double getRealZ () { return this.z * this.m; }
}
