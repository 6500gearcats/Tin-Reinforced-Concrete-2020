package frc.team6500.trc.util;


import java.util.HashMap;
import java.lang.reflect.Method;


public class TRCRobotManager
{
    private static HashMap<String, Method[]> methodRegistry;
    private static HashMap<String, Object> objectRegistry;

    public static void initialize()
    {
        methodRegistry = new HashMap<String, Method[]>();
        objectRegistry = new HashMap<String, Object>();
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

    public static void runObjectMethod(String objectName, String methodName, Object... params)
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

        if (targetMethod.getParameterTypes().length > 0)
        {
            try
            {
                targetMethod.invoke(objectRegistry.get(objectName), params);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                targetMethod.invoke(objectRegistry.get(objectName));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void runObjectMethod(String combinedName, Object... params)
    {
        runObjectMethod(combinedName.substring(0, combinedName.indexOf('.')), combinedName.substring(combinedName.indexOf(".")+1, combinedName.length()), params);
    }
}