package frc.team6500.trc.wrappers.sensors;

public interface TRCEncoder
{
	/**
	 *	Reset the encoder to zero distance
	 */
	public void reset();

	/**
	 *	Get the current distance of the encoder
	 *	@return the current distance recorded on the encoder
	 */
	public double getDistance();
}
