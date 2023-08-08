package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.URLMethods;

import java.io.IOException;

public class ValidatorBloodPressure extends BaseClass{
    public HealthURLMethods healthURLMethods;
    String todayDate = URLMethods.todayDateString();
    @Test(priority = 0)
    public void ValidatePutBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.putDataAPI("health","bp");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }

    @Test(priority = 1)
    public void ValidateGetDailySpO2()
    {
        healthURLMethods = new HealthURLMethods();
        System.out.println("date is: "+todayDate);
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"spo2","DAY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 2)
    public void ValidateGetWeeklyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"bp","WEEK");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 3)
    public void ValidateGetMonthlyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"bp","MONTH");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }

    @Test(priority = 4)
    public void ValidatePutAndGetBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI("bp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
