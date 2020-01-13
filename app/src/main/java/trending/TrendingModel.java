package trending;

public class TrendingModel {
    public String cityName;
    public String cityImage;
    public String description;
    public int rating;
    public String state;
    public int ratingCount;

    public TrendingModel(){}

    public TrendingModel(String cityName,String cityImage,String description,int rating,String state,int ratingCount){
        this.cityName=cityName;
        this.cityImage=cityImage;
        this.description=description;
        this.rating=rating;
        this.state=state;
        this.ratingCount=ratingCount;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityImage() {
        return cityImage;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public String getState() {
        return state;
    }

}
