package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;

import java.io.IOException;

public class ValidatorStress extends BaseClass {
    public HealthURLMethods healthURLMethods;
    @Test
    public void ValidatePutStress()
    {
        healthURLMethods = new HealthURLMethods();
        String filePath = "./src/main/java/putdata/PutStressData.json";
        Response res1 = healthURLMethods.putDataHealthAPI(headers,"stress");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetDailyStress()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"stress","DAILY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetWeeklyStress()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"stress","WEEKLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetMonthlyStress()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-12",headers,"stress","MONTHLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidatePutAndGetStress()
    {
        healthURLMethods = new HealthURLMethods();
        String CSVfilePath = "./xx";
        String JsonTemplateFilePath = "./src/main/java/jsontemplates/stressTemplate.json";
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI(CSVfilePath,JsonTemplateFilePath,headers,"stress");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
