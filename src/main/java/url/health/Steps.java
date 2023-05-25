package url.health;

import io.restassured.response.Response;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.RESTUtility;

public class Steps extends BaseClass {
    public RESTUtility restUtil;
    public String url = (String) urls.get("health")+"steps";
    public HealthURLMethods healthURLMethods = new HealthURLMethods();

    public Response getDailySteps(String date) {
        return healthURLMethods.getDailyData(url, date, headers);
    }

    public Response getWeeklySteps(String date) {
        System.out.println(headers);
        return healthURLMethods.getWeeklyData(url, date, headers);
    }

    public Response getMonthlySteps(String date) {
        return healthURLMethods.getMonthlyData(url, date, headers);
    }

    public Response putSteps() {
        String FilePath = "./src/main/java/putData/PutStepsData.json";
        return healthURLMethods.putData(url, FilePath, headers);
    }
}