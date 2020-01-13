package com.droid.solver.a2020.explore;

import java.util.List;

public class ExploreModel {

    String cityId;
    String cityName;
    String cityImage;
    String cityDescription;

    String skillImage;
    String skillDescription;

    String practicesImage;
    String practicesDescription;

    List<ArtifactsModel> artifactsModelList;
    List<FeedbackModel> feedbackModelList;

    ExploreModel(){}
    ExploreModel(String cityId,String cityName,String cityImage,String cityDescription,
                 String skillImage,String skillDescription,
                 String practicesImage,String practicesDescription,
                 List<ArtifactsModel> artifactsModelList,
                 List<FeedbackModel> feedbackModelList){
        this.cityId=cityId;
        this.cityName=cityName;
        this.cityDescription=cityDescription;
        this.cityImage=cityImage;
        this.skillImage=skillImage;
        this.skillDescription=skillDescription;
        this.practicesImage=practicesImage;
        this.practicesDescription=practicesDescription;
        this.artifactsModelList = artifactsModelList;
        this.feedbackModelList=feedbackModelList;

    }


    public String getCityImage() {
        return cityImage;
    }

    public String getCityName() {
        return cityName;
    }

    public List<ArtifactsModel> getArtifactsModelList() {
        return artifactsModelList;
    }

    public String getCityDescription() {
        return cityDescription;
    }

    public String getCityId() {
        return cityId;
    }

    public List<FeedbackModel> getFeedbackModelList() {
        return feedbackModelList;
    }

    public String getPracticesDescription() {
        return practicesDescription;
    }

    public String getPracticesImage() {
        return practicesImage;
    }

    public String getSkillImage() {
        return skillImage;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

}
class ArtifactsModel{
    String artifactsImage;
    String artifactsDescription;

    ArtifactsModel(){}

    ArtifactsModel(String artifactsImage,String artifactsDescription){
        this.artifactsImage=artifactsImage;
        this.artifactsDescription=artifactsDescription;
    }

    public String getArtifactsDescription() {
        return artifactsDescription;
    }

    public String getArtifactsImage() {
        return artifactsImage;
    }
}
class FeedbackModel{
    String uid;
    int ratingStar;
    String feedback;

    FeedbackModel(){

    }
    FeedbackModel(String uid,int ratingStar,String feedback){
        this.uid=uid;
        this.ratingStar=ratingStar;
        this.feedback=feedback;
    }

    public String getUid() {
        return uid;
    }

    public int getRatingStar() {
        return ratingStar;
    }

    public String getFeedback() {
        return feedback;
    }
}
