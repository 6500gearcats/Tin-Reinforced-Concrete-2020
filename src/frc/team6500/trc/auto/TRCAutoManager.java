package frc.team6500.trc.auto;

import java.util.HashMap;

public class TRCAutoManager
{
    private static HashMap<String, TRCAutoPath> pathRegistry;
    private static HashMap<String, TRCAutoRoutine> routineRegistry;

    public static void initialize()
    {
        pathRegistry = new HashMap<String, TRCAutoPath>();
        routineRegistry = new HashMap<String, TRCAutoRoutine>();
    }

    public static void registerPath(String name, TRCAutoPath path)
    {
        pathRegistry.put(name, path);
    }

    public static void registerRoutine(String name, TRCAutoRoutine registry)
    {
        routineRegistry.put(name, registry);
    }

    public static TRCAutoPath getPath(String name)
    {
        try
        {
            return pathRegistry.get(name);
        }
        catch (Exception e)
        {
            System.out.println("Attempted to retrieve non-existent path \"" + name + "\"");
            return null;
        }
    }

    public static TRCAutoRoutine getRoutine(String name)
    {
        try
        {
            return routineRegistry.get(name);
        }
        catch (Exception e)
        {
            System.out.println("Attempted to retrieve non-existent routine \"" + name + "\"");
            return null;
        }
    }
}