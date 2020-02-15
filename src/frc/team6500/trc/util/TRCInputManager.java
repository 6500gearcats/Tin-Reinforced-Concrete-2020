package frc.team6500.trc.util;


import java.util.HashMap;
import java.lang.reflect.Method;


public class TRCInputManager
{
    private static HashMap<String, Method[]> methodRegistry;
    private static HashMap<String, Object> objectRegistry;
    private static HashMap<TRCController, HashMap<int[], String>> buttonRegistriesActive;
    private static HashMap<TRCController, HashMap<int[], String>> buttonRegistriesInactive;
    private static HashMap<String, Object[]> axisRegistry;
    private static HashMap<String, Object[][]> dpsRegistry;

    public static void initialize()
    {
        methodRegistry = new HashMap<String, Method[]>();
        objectRegistry = new HashMap<String, Object>();
        buttonRegistriesActive = new HashMap<TRCController, HashMap<int[], String>>();
        buttonRegistriesInactive = new HashMap<TRCController, HashMap<int[], String>>();
        axisRegistry = new HashMap<String, Object[]>();
        dpsRegistry = new HashMap<String, Object[][]>();
    }

    public static void registerObject(String name, Object object)
    {
        if (methodRegistry.containsKey(name))
        {
            System.out.println("Class registry conflict with name " + name);
            return;
        }
        methodRegistry.put(name, object.getClass().getMethods());
        objectRegistry.put(name, object);
    }

    public static void registerObject(Object object)
    {
        registerObject(object.getClass().getName(), object);
    }

    public static void registerObject(String name, Class classobj)
    {
        if (methodRegistry.containsKey(name))
        {
            System.out.println("Class registry conflict with name " + name);
            return;
        }
        methodRegistry.put(name, classobj.getMethods());
        objectRegistry.put(name, classobj);
    }

    public static void registerObject(Class classobj)
    {
        registerObject(classobj.getName(), classobj);
    }

    public static void runObjectMethod(String objectName, String methodName)
    {
        Method[] methodList = methodRegistry.get(objectName);
        Method targetMethod = null;
        for (int i = 0; i < methodList.length; i++)
        {
            if (methodList[i].getName().equals(methodName))
            {
                targetMethod = methodList[i];
                break;
            }
        }
        if (targetMethod == null)
        {
            System.out.println("Method \"" + methodName + "\" from object \"" + objectName + "\"" + " could not be found.");
            return;
        }

        try
        {
            targetMethod.invoke(objectRegistry.get(objectName));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void runObjectMethod(String combinedName)
    {
        runObjectMethod(combinedName.substring(0, combinedName.indexOf('.')), combinedName.substring(combinedName.indexOf(".")+1, combinedName.length()));
    }

    public static void registerButtonBind(TRCController controller, int[] buttonList, String activeFunc, String inactiveFunc)
    {
        if (!buttonRegistriesActive.containsKey(controller))
        {
            buttonRegistriesActive.put(controller, new HashMap<int[], String>());
        }
        if (!buttonRegistriesInactive.containsKey(controller))
        {
            buttonRegistriesInactive.put(controller, new HashMap<int[], String>());
        }

        if (!activeFunc.equals("")) {
        if (!buttonRegistriesActive.get(controller).containsKey(buttonList))
        {
            HashMap<int[], String> buttonRegistry = buttonRegistriesActive.get(controller);
            buttonRegistry.put(buttonList, activeFunc);
        }}
        if (!inactiveFunc.equals("")) {
        if (!buttonRegistriesInactive.get(controller).containsKey(buttonList))
        {
            HashMap<int[], String> buttonRegistry = buttonRegistriesInactive.get(controller);
            buttonRegistry.put(buttonList, inactiveFunc);
        }}
    }

    public static void registerAxisBind(TRCController controller, int axis, String name)
    {
        if (!axisRegistry.containsKey(name))
        {
            Object axisDetails[] = {controller, axis};
            axisRegistry.put(name, axisDetails);
        }
    }

    public static void registerDPSBind(Object[][] axes, String name)
    {
        if (!dpsRegistry.containsKey(name))
        {
            dpsRegistry.put(name, axes);
        }
    }

    public static double getAxisInput(String name)
    {
        Object axisDetails[] = axisRegistry.get(name);
        TRCController controller = (TRCController) axisDetails[0];
        int axis = (int) axisDetails[1];
        return controller.getAxis(axis);
    }

    public static TRCDriveParams getDPS(String name)
    {
        Object dpsDetails[][] = dpsRegistry.get(name);
        double x = ((TRCController) dpsDetails[0][0]).getAxis((int) dpsDetails[0][1]);
        double y = ((TRCController) dpsDetails[1][0]).getAxis((int) dpsDetails[1][1]);
        double z = ((TRCController) dpsDetails[2][0]).getAxis((int) dpsDetails[2][1]);
        TRCDriveParams dps = new TRCDriveParams(x, y, z);
        return dps;
    }

    public static boolean checkButtonsMatch(TRCController controller, int[] buttons, boolean active)
    {
        for (int button : buttons)
        {
            if (controller.getButton(button) != active)
            {
                return false;
            }
        }
        return true;
    }

    public static Object[] getAxisID(String name)
    {
        Object badObject[] = new Object[2];
        badObject[0] = null; badObject[1] = -1;
        if (!axisRegistry.containsKey(name)) { return badObject; }
        return axisRegistry.get(name);
    }

    public static void checkInputs()
    {
        for (TRCController controller : buttonRegistriesActive.keySet())
        {
            HashMap<int[], String> buttonRegistry = buttonRegistriesActive.get(controller);
            for (int[] buttons : buttonRegistry.keySet())
            {
                if (checkButtonsMatch(controller, buttons, true))
                {
                    runObjectMethod(buttonRegistry.get(buttons));
                }
            }

            buttonRegistry = buttonRegistriesInactive.get(controller);
            for (int[] buttons : buttonRegistry.keySet())
            {
                if (checkButtonsMatch(controller, buttons, false))
                {
                    runObjectMethod(buttonRegistry.get(buttons));
                }
            }
        }
    }
}