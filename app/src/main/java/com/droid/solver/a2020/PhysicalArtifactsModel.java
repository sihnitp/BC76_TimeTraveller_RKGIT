package com.droid.solver.a2020;
public class PhysicalArtifactsModel{

    public String image;
    public String description;
    public String latitude;
    public String longitude;
    public String name;

    public PhysicalArtifactsModel(){}

    public  PhysicalArtifactsModel(String image,String description,String latitude,String longitude,String name){
        this.image=image;
        this.description=description;
        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

}
