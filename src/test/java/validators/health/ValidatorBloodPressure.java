package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;

import java.io.IOException;

public class ValidatorBloodPressure extends BaseClass {
    public HealthURLMethods healthURLMethods;
    @Test
    public void ValidatePutBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        String filePath = "src/main/java/putdata/random.xlsx";
        Response res1 = healthURLMethods.putDataHealthAPI(filePath,headers,"bp");
        System.out.println(res1.asPrettyString());
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetDailyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-11",headers,"bp","DAILY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetWeeklyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"bp","WEEKLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetMonthlyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"bp","MONTHLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }

    @Test
    public void ValidatePutAndGetBloodPressure()
    {
        String filePath = "./src/main/java/putdata/random.xlsx";
        healthURLMethods = new HealthURLMethods();
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI(filePath,headers,"bp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
