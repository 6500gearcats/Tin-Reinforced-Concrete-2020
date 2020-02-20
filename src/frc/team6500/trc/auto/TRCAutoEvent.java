package frc.team6500.trc.auto;


import java.time.Duration;
import java.time.Instant;

import frc.team6500.trc.util.TRCRobotManager;


public class TRCAutoEvent extends Thread
{
    private double startTime;
    private double length;
    private Object[] params;
    private boolean nonblocking;
    private String function;
    private boolean hasRun, hasFinished;

    public TRCAutoEvent(double startTime, double length, Object[] params, boolean nonblocking, String function)
    {
        this.startTime = startTime;
        this.length = length;
        this.params = params;
        this.nonblocking = nonblocking;
        this.function = function;
        this.hasRun = false;
        this.hasFinished = false;
    }

    public double getStartTime()
    {
        return this.startTime;
    }

    public boolean getNonblocking()
    {
        return this.nonblocking;
    }

    public boolean getRun()
    {
        return this.hasRun;
    }

    public boolean getFinished()
    {
        return this.hasFinished;
    }

    public void run()
    {
        this.hasRun = true;
        Instant start = Instant.now();

        while (true)
        {
            Duration elapsedTime = Duration.between(start, Instant.now());
            double elapsedSeconds = elapsedTime.toSeconds() + (elapsedTime.toMillis() / 1000.0);

            if (this.params.length > 0)
            {
                TRCRobotManager.runObjectMethod(this.function);
            }
            else
            {
                TRCRobotManager.runObjectMethod(this.function, this.params);
            }

            if (Double.isNaN(this.length) || this.length >= elapsedSeconds)
            {
                break;
            }
        }

        this.hasFinished = true;
    }
}