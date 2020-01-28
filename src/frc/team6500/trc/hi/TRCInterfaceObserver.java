package frc.team6500.trc.hi;

import java.util.Map;

public class TRCInterfaceObserver
{
	private Map<TRCDigitalHI, Runnable> binds;
	private TRCController controller;

	public TRCInterfaceObserver(TRCController controller, Map<TRCDigitalHI, Runnable> bindings)
	{
		this.controller = controller;
		this.binds = bindings;
	}

	public void update()
	{
		binds.forEach((dhi, runnable)->
		{
			if (controller.getButton(dhi)) runnable.run();
		});
	}
}
