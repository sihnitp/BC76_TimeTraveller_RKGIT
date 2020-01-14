package com.droid.solver.a2020;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class RootModel {
    public RootModel(){

    }
    public String state;
    public String cityName;
    public String cityImage;
    public String cityDesctiption;
    public String cityId;
    public List<PhysicalArtifactsModel> physicalArtifactsModelList;

    public String practicesImage;
    public String practicesDescription;

    public String skillImage;
    public String skillDescription;


    public RootModel(String state,String cityName,
                     String cityImage,
                     String cityDesctiption,String cityId,
                     List<PhysicalArtifactsModel> physicalArtifactsModelList,
                     String practicesImage,
                     String practicesDescription,
                     String skillImage,
                     String skillDescription
                     ){
        this.state=state;
        this.cityName=cityName;
        this.cityImage=cityImage;
        this.cityDesctiption=cityDesctiption;
        this.cityId=cityId;
        this.physicalArtifactsModelList=physicalArtifactsModelList;
        this.practicesImage=practicesImage;
        this.practicesDescription=practicesDescription;
        this.skillImage=skillImage;
        this.skillDescription=skillDescription;

    }

    public String getCityDesctiption() {
        return cityDesctiption;
    }

    public String getCityImage() {
        return cityImage;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public String getState() {
        return state;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public String getPracticesImage() {
        return practicesImage;
    }

    public List<PhysicalArtifactsModel> getPhysicalArtifactsModelList() {
        return physicalArtifactsModelList;
    }

    public String getPracticesDescription() {
        return practicesDescription;
    }

    public String getSkillImage() {
        return skillImage;
    }


}

class Feedback{

    public String uid;
    public Map<String ,SubFeedback> feedbackMap;
    public Feedback(){}
    public Feedback(String uid,Map<String,SubFeedback> feedbackMap){
        this.uid=uid;
        this.feedbackMap=feedbackMap;
    }

    public Map<String, SubFeedback> getFeedbackMap() {
        return feedbackMap;
    }

    public String getUid() {
        return uid;
    }

}

class SubFeedback{
    public int star;
    public String message;
    public SubFeedback(){}
    public SubFeedback(int star,String message){
        this.star=star;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public int getStar() {
        return star;
    }
}

