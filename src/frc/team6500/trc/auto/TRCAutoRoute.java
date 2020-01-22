package frc.team6500.trc.auto;

/**
 * Exists to ensure compatibility with any autonomous route which may want to be run.
 * Beware that changing this will force a change in ALL other existing auto routes.
 */
public interface TRCAutoRoute
{
    /**
     * The main method to run in autonomous.
     */
    public void run();
}