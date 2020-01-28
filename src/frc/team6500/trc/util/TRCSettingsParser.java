package frc.team6500.trc.util;

import frc.team6500.trc.hi.TRCDigitalHI;
import frc.team6500.trc.hi.TRCController;
import frc.team6500.trc.hi.TRCInterfaceObserver;
import edu.wpi.first.wpilibj.GenericHID.HIDType;
import edu.wpi.first.wpilibj.XboxController;
import java.util.HashMap;
import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *	Parse a human-readable settings file into a robot-readable button binding list
 */
public class TRCSettingsParser
{
	private HashMap<String, Runnable> binds;
	private Document doc;

	/**
	 *	Create with setting file's name
	 *	@param fileName file name of the settings file (complete path)
	 */
	public TRCSettingsParser(String fileName)
	{
		this(fileName, new HashMap<String, Runnable>());
	}

	/**
	 *	Create with setting file's name, physical controller, and name:runnable settings
	 *	@param fileName file name of the settings file (complete path)
	 *	@param bindings name:runnable settings (i.e. "liftRaise":[Runnable to liftRaise()])
	 */
	public TRCSettingsParser(String fileName, HashMap<String, Runnable> bindings)
	{
		doc = null;
		try
		{
			File f = new File(fileName);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(f);
			doc.getDocumentElement().normalize();
		} catch (Exception e) { e.printStackTrace(); }
		this.binds = bindings;
	}

	/**
	 *	Append a binding with String and Runnable
	 *	@param name the binding name identifier
	 *	@param runnable the runnable the identifier represents
	 */
	public void addBind(String name, Runnable runnable)
	{
		this.binds.put(name, runnable);
	}

	/**
	 *	Append bindings from a pre-made bindings map
	 *	@param bindings pre-made bindings map
	 */
	public void addBinds(HashMap<String, Runnable> bindings)
	{
		this.binds.putAll(bindings);
	}

	/**
	 *	Reads data from settings file and initializes a {@link TRCInterfaceController}
	 *	with that binding information and a controller
	 *	@param controller a controller to give to the {@link TRCInterfaceController}
	 *	@return a new {@link TRCInterfaceController} with the bindings specified in the file
	 */
	public TRCInterfaceObserver parse(TRCController controller)
	{
		HashMap<TRCDigitalHI, Runnable> settings = new HashMap<TRCDigitalHI, Runnable>();

		NodeList options = doc.getDocumentElement().getElementsByTagName("bind");
		for (int i = 0; i < options.getLength(); i++)
		{
			Element bind = (Element)options.item(i);
			
			XboxController.Button xbxButton = null;
			int rawButton = -1;

			NodeList bindData = bind.getChildNodes();
			for (int k = 0; k < bindData.getLength(); k++)
			{
				Node buttonData = bindData.item(k);
				buttonData.normalize();
				
				String typeName = buttonData.getNodeName();
				if (typeName.equals("xbx"))
					xbxButton = XboxController.Button.values()[Integer.parseInt(buttonData.getTextContent())];
				else if (typeName.equals("joy"))
					rawButton = Integer.parseInt(buttonData.getTextContent());
			}
			settings.put(new TRCDigitalHI(xbxButton, rawButton), this.binds.get(bind.getAttribute("name")));
		}

		return new TRCInterfaceObserver(controller, settings);
	}

	public void setBinds(HashMap<String, Runnable> bindings) { this.binds = bindings; }
	public HashMap<String, Runnable> getBinds() { return this.binds; }
}
