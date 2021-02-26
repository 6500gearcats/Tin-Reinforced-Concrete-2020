package frc.team6500.trc.auto;


import java.util.List;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;


public class TRCAutoPath
{
    private Pose2d startPose;
    private List<Translation2d> translationList;
    private Pose2d endPose;

    public TRCAutoPath(Pose2d startPose, List<Translation2d> translationList, Pose2d endPose)
    {
        this.startPose = startPose;
        this.translationList = translationList;
        this.endPose = endPose;
    }

    public Pose2d getStartPose()
    {
        return this.startPose;
    }

    public void setStartPose(Pose2d startPose)
    {
        this.startPose = startPose;
    }

    public Pose2d getEndPose()
    {
        return this.endPose;
    }

    public List<Translation2d> getTranslationList()
    {
        return this.translationList;
    }
}