package com.droid.solver.a2020;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class RootModel {
    public RootModel(){

    }
    public String cityName;
    public String cityImage;
    public String description;
    public Map<String,SkillModel> skillModelMap;
    public Map<String,PracticesModel> practicesModelMap;
    public Map<String,List<PhysicalArtifactsModel>> artifactsModelMap;
    public Map<String,Feedback> feedbackMap;

    public RootModel(String cityName,
                     String cityImage,
                     String description,
                     Map<String,SkillModel> skillModelMap,
                     Map<String,PracticesModel> practicesModelMap,
                     Map<String,List<PhysicalArtifactsModel>> artifactsModelMap,
                     Map<String,Feedback> feedbackMap){

        this.cityName=cityName;
        this.cityImage=cityImage;
        this.description=description;
        this.skillModelMap=skillModelMap;
        this.practicesModelMap=practicesModelMap;
        this.artifactsModelMap=artifactsModelMap;
        this.feedbackMap=feedbackMap;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Feedback> getFeedbackMap() {
        return feedbackMap;
    }

    public Map<String, List<PhysicalArtifactsModel>> getArtifactsModelMap() {
        return artifactsModelMap;
    }

    public Map<String, PracticesModel> getPracticesModelMap() {
        return practicesModelMap;
    }

    public Map<String, SkillModel> getSkillModelMap() {
        return skillModelMap;
    }

    public String getCityImage() {
        return cityImage;
    }

    public String getCityName() {
        return cityName;
    }

}

class SkillModel{
    public String image;
    public String description;
    public SkillModel(){}
    public SkillModel(String image,String description){
        this.image=image;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}

class PracticesModel{
    public String image;
    public String description;
    public PracticesModel(){}
    public PracticesModel(String image,String description){
        this.image=image;
        this.description=description;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}

class PhysicalArtifactsModel{
    public String image;
    public String description;
    public PhysicalArtifactsModel(){}
    public  PhysicalArtifactsModel(String image,String description){
        this.image=image;
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
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

