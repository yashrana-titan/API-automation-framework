package url;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class SpO2 extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("Health")+"spo2";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailySpO2(String date) {
        Response res = healthURLMethods.getDailyData(url, date, headers);
        return res;
    }

    public Response getWeeklySpO2(String date) {
        System.out.println(headers);
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlySpO2(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putSpO2() {
        String FilePath = "./src/main/java/putData/PutSpO2Data.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}