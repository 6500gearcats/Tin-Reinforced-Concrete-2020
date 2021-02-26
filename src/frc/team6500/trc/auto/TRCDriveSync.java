package frc.team6500.trc.auto;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import frc.team6500.trc.util.TRCTypes.*;

public class TRCDriveSync
{
    public static AtomicBoolean isAutonomous;
    public static AtomicInteger syncState;
    public static AtomicBoolean isReady = new AtomicBoolean(false);

    public static void initialize()
    {
        isAutonomous = new AtomicBoolean(false);
        syncState = new AtomicInteger(DriveSyncState.Teleop.ordinal());
    }


    public static boolean requestChangeState(DriveSyncState state)
    {
        if (syncState.get() == DriveSyncState.DrivePID.ordinal())
        {
            return false;
        }

        isAutonomous.set(!(state == DriveSyncState.Teleop));
        syncState.set(state.ordinal());
        return true;
    }

    public static DriveSyncState getState()
    {
        if      (syncState.get() == DriveSyncState.Teleop.ordinal())          { return DriveSyncState.Teleop; }
        else if (syncState.get() == DriveSyncState.DriveContinuous.ordinal()) { return DriveSyncState.DriveContinuous; }
        else if (syncState.get() == DriveSyncState.DrivePID.ordinal())        { return DriveSyncState.DrivePID; }
        else                                                                  { return DriveSyncState.Teleop; }
    }

    public static void assertDrivePID() { if (syncState.get() != DriveSyncState.DrivePID.ordinal()) { throw new AssertionError("Attempted to drivePID out of sync"); } }
    public static void assertDriveContinuous() { if (syncState.get() != DriveSyncState.DriveContinuous.ordinal()) { throw new AssertionError("Attempted to driveContinuous out of sync"); } }
    public static void assertTeleop() { if (syncState.get() != DriveSyncState.Teleop.ordinal()) { throw new AssertionError("Attempted to teleop out of sync"); } }
}