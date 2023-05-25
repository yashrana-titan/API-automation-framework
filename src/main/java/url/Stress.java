package url;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class Stress extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("Health")+"stress";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailyStress(String date) {
        Response res = healthURLMethods.getDailyData(url, date, headers);
        return res;
    }

    public Response getWeeklyStress(String date) {
        System.out.println(headers);
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlyStress(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putStress() {
        String FilePath = "./src/main/java/putData/PutStressData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}