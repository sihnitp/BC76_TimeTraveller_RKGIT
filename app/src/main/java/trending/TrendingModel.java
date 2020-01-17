package trending;

public class TrendingModel {
    String cityName;
    String cityImage;
    String stateName;
    String description;
    int userCount;

    public TrendingModel(){}

    public TrendingModel(String cityName, String cityImage, String stateName, String description, int userCount) {
        this.cityName = cityName;
        this.cityImage = cityImage;
        this.stateName = stateName;
        this.description = description;
        this.userCount = userCount;
    }


    public String getCityName() {
        return cityName;
    }

    public String getCityImage() {
        return cityImage;
    }

    public String getStateName() {
        return stateName;
    }

    public String getDescription() {
        return description;
    }

    public int getUserCount() {
        return userCount;
    }
}
