package validators.health;

import com.codepine.api.testrail.model.Priority;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.URLMethods;

import java.io.IOException;

public class ValidatorSPO2 extends BaseClass{
    public HealthURLMethods healthURLMethods;
    String todayDate = URLMethods.todayDateString();

    @Test(priority = 0)
    public void ValidatePutSpO2()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.putDataAPI("health","spo2");
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
    public void ValidateGetWeeklySpO2()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"spo2","WEEK");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 3)
    public void ValidateGetMonthlySpO2()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"spo2","MONTH");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 4)
    public void ValidatePutAndGetSpO2()
    {
        healthURLMethods = new HealthURLMethods();
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI("spo2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
