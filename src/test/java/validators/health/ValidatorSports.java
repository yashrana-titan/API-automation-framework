package validators.health;

import com.fasterxml.jackson.databind.ser.Serializers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.URLMethods;

import java.io.IOException;

public class ValidatorSports extends BaseClass {
    public HealthURLMethods healthURLMethods;
    String todayDate = URLMethods.todayDateString();

    @Test(priority = 0)
    public void ValidatePutSports()
    {
        healthURLMethods = new HealthURLMethods();
        String filePath = "./src/main/java/putdata/PutSportsData.json";
        Response res1 = healthURLMethods.putDataAPI("health","sports");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 1)
    public void ValidateGetDailySports()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"sports","DAY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 2)
    public void ValidateGetWeeklySports()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"sports","WEEK");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 3)
    public void ValidateGetMonthlySports()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI(todayDate,"sports","MONTH");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test(priority = 4)
    public void ValidatePutAndGetSports()
    {
        healthURLMethods = new HealthURLMethods();
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI("sports");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
