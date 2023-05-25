package url;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class Temperature extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("Health")+"temperature";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailyTemp(String date) {
        Response res = healthURLMethods.getDailyData(url, date, headers);
        return res;
    }

    public Response getWeeklyTemp(String date) {
        System.out.println(headers);
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlyTemp(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putTemp() {
        String FilePath = "./src/main/java/putData/PutTempData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}