

//From Here
public void robotPeriodic() {
    LimelightHelpers.Pose3d pose3d = LimelightHelpers.getBotpose_wpiBlue("")
    LimelightHelpers.PoseEstimate tagCount = LimelightHelpers.getBotpose_tagCount("")

    if (pose3d != null && tagCount > 0) {
        
        System.out.println(poseEstimate);
        System.out.println(pose3d);

        SmartDashboard.putNumber("Bot Pose X", pose3d.getX());
        SmartDashboard.putNumber("Bot Pose Y", pose3d.getY());
    } else {
        System.out.println("Bot Pose: Unknown/Invalid");
    public double getX(Pose3d DataLoc){
        return DataLoc[0];
    }
    public double getY(Pose3d DataLoc){
        return DataLoc[1];
    }

    public void robotPeriodic() {
        LimelightHelpers.Pose3d botPose3d = LimelightHelpers.getBotpose_wpiBlue("")
        LimelightHelpers.PoseEstimate tagCount = LimelightHelpers.getBotpose_tagCount("")
    
        if (pose3d != null && tagCount > 0) {

            System.out.println(botPose3d);
            System.out.println(botPose3d[0]);
            System.out.println(botPose3d[1]);
    
            SmartDashboard.putNumber("Bot Pose X", botPose3d[0]);
            SmartDashboard.putNumber("Bot Pose Y", botPose3d[1]);
        } else {
            System.out.println("Bot Pose: Unknown/Invalid");
        }
}
}
//To Here

