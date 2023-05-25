package url.health;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class Temperature extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("Health")+"temperature";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailyTemperature(String date) {
        Response res = healthURLMethods.getDailyData(url, date, headers);
        return res;
    }

    public Response getWeeklyTemperature(String date) {
        System.out.println(headers);
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlyTemperature(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putTemperature() {
        String FilePath = "./src/main/java/putData/PutTempData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}