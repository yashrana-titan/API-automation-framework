package url;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class Sleep extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("Health")+"sleep";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailySleep(String date) {
        Response res = healthURLMethods.getDailyData(url, date, headers);
        return res;
    }

    public Response getWeeklySleep(String date) {
        System.out.println(headers);
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlySleep(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putSleep() {
        String FilePath = "./src/main/java/putData/PutSleepData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}