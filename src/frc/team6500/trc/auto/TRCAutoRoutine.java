package frc.team6500.trc.auto;


import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;


public class TRCAutoRoutine extends Thread
{
    private ArrayList<TRCAutoEvent> eventList;
    private int completedEvents;
    private Instant start;

    public TRCAutoRoutine()
    {
        this.eventList = new ArrayList<TRCAutoEvent>();
    }

    public void addEvent(TRCAutoEvent event)
    {
        this.eventList.add(event);
    }

    private class EventSorter implements Comparator<TRCAutoEvent> 
    {
        public int compare(TRCAutoEvent a, TRCAutoEvent b)
        {
            return (int) Math.round(a.getStartTime() - b.getStartTime());
        }
    } 

    public void sortEvents()
    {
        Collections.sort(eventList, new EventSorter());
    }

    private void checkEventStatus(TRCAutoEvent event)
    {
        Duration elapsedTime = Duration.between(this.start, Instant.now());
        double elapsedSeconds = elapsedTime.toSeconds() + (elapsedTime.toMillis() / 1000.0);

        if (event.getFinished())
        {
            completedEvents++;
            return;
        }
        if (!event.getRun() && elapsedSeconds >= event.getStartTime())
        {
            event.start();
        }
        if (!event.getNonblocking() && completedEvents < eventList.size() - 1)
        {
            TRCAutoEvent nextEvent = eventList.get(completedEvents+1);
            checkEventStatus(nextEvent);
        }
    }

    public void run()
    {
        this.start = Instant.now();
        this.sortEvents();
        this.completedEvents = 0;

        while (true)
        {
            TRCAutoEvent event = eventList.get(completedEvents);
            checkEventStatus(event);

            if (this.completedEvents == eventList.size())
            {
                break;
            }
        }
    }
}