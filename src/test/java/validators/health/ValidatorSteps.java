package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;

import java.io.IOException;

public class ValidatorSteps extends BaseClass {
    public HealthURLMethods healthURLMethods;
    @Test
    public void ValidatePutSteps()
    {
        healthURLMethods = new HealthURLMethods();
        String filePath = "./src/main/java/putdata/PutStepsData.json";
        Response res1 = healthURLMethods.putDataHealthAPI(headers,"steps");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetDailySteps()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"steps","DAILY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetWeeklySteps()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"steps","WEEKLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetMonthlySteps()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"steps","MONTHLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidatePutAndGetSteps()
    {
        String CSVfilePath = "./xx";
        String JsonTemplateFilePath = "./src/main/java/jsontemplates/stepsTemplate.json";
        healthURLMethods = new HealthURLMethods();
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI(CSVfilePath,JsonTemplateFilePath,headers,"steps");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
