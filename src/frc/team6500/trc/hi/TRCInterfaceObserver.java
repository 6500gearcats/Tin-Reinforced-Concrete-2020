package frc.team6500.trc.hi;

import java.util.Map;

/**
 *	Give a controller and bindings to auto execute tasks. Simple!
 *	For this to work, one must call update() every period.
 */
public class TRCInterfaceObserver
{
	private Map<TRCDigitalHI, Runnable> binds;
	private TRCController controller;

	/**
	 *	Create a TRCInterfaceObserver with a physical controller and pre-mapped bindings.
	 *	@param controller physical controller to track
	 *	@param bindings pre-mapped bindings to observe
	 */
	public TRCInterfaceObserver(TRCController controller, Map<TRCDigitalHI, Runnable> bindings)
	{
		this.controller = controller;
		this.binds = bindings;
	}

	/**
	 *	Check and execute binds
	 */
	public void update()
	{
		binds.forEach((dhi, runnable)->
		{
			if (controller.getButton(dhi)) runnable.run();
		});
	}
}
