package url;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class Sports extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("health")+"sports";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailySports(String date) {
        Response res = healthURLMethods.getDailyData(url, date, headers);
        return res;
    }

    public Response getWeeklySports(String date) {
        System.out.println(headers);
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlySports(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putSports() {
        String FilePath = "./src/main/java/putData/PutSportsData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}
