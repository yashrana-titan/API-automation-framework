package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.URLMethods;

import java.io.IOException;

public class ValidatorStress extends BaseClass{
    public HealthURLMethods healthURLMethods;
    String todayDate = URLMethods.todayDateString();

    @Test(priority = 0)
    public void ValidatePutStress()
    {
        healthURLMethods = new HealthURLMethods();
        String filePath = "./src/main/java/putdata/PutStressData.json";
        Response res1 = healthURLMethods.putDataAPI("health","stress");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 1)
    public void ValidateGetDailyStress()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"stress","DAY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 2)
    public void ValidateGetWeeklyStress()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"stress","WEEK");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 3)
    public void ValidateGetMonthlyStress()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"stress","MONTH");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 4)
    public void ValidatePutAndGetStress()
    {
        healthURLMethods = new HealthURLMethods();
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI("stress");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
