package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.URLMethods;

import java.io.IOException;

public class ValidatorHeartRate{
    public HealthURLMethods healthURLMethods;
    String todayDate = URLMethods.todayDateString();

    @Test(priority = 0)
    public void ValidatePutHeartRate()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.putDataAPI("health","hr");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 1)
    public void ValidateGetDailyHeartRate()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"hr","DAY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 2)
    public void ValidateGetWeeklyHeartRate()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"hr","WEEK");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 3)
    public void ValidateGetMonthlyHeartRate()
    {
//        System.out.println();
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"hr","MONTH");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 4)
    public void ValidatePutAndGetHeartRate()
    {
        healthURLMethods = new HealthURLMethods();
        Boolean result = null;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI("hr");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
