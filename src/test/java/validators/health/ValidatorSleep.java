package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;

import java.io.IOException;

public class ValidatorSleep extends BaseClass {
    public HealthURLMethods healthURLMethods;
    @Test
    public void ValidatePutSleep()
    {
        healthURLMethods = new HealthURLMethods();
        String filePath = "./src/main/java/putdata/PutSleepData.json";
        Response res1 = healthURLMethods.putDataHealthAPI(headers,"sleep");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetDailySleep()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"sleep","DAILY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetWeeklySleep()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"sleep","WEEKLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetMonthlySleep()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"sleep","MONTHLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidatePutAndGetSleep()
    {
        healthURLMethods = new HealthURLMethods();
        String CSVfilePath = "./";
        String JsonTemplateFilePath = "./src/main/java/jsontemplates/sleepTemplate.json";
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI(CSVfilePath,JsonTemplateFilePath,headers,"sleep");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
