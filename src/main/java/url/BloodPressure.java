package url;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class BloodPressure extends BaseClass {
    public RESTUtility restUtil;
    public String url = urls.get("Health") + "bp";

    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailyBloodPressure(String date) {
        Response res = healthURLMethods.getDailyData(url, date, headers);
        return res;
    }

    public Response getWeeklyBloodPressure(String date) {
        System.out.println(headers);
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlyBloodPressure(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putBloodPressure() {
        String FilePath = "./src/main/java/putData/PutBloodPressureData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}