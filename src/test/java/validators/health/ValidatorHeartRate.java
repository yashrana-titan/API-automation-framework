package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;
import utility.JSONPlaceholderReplacer;

import java.io.IOException;

public class ValidatorHeartRate extends BaseClass {
    public HealthURLMethods healthURLMethods;
//    @Test
//    public void ValidatePutHeartRate()
//    {
//        healthURLMethods = new HealthURLMethods();
//        String CSVfilePath = "./src/main/java/csvdata/HRData.csv";
//        String JsonTemplateFilePath = "./src/main/java/jsontemplates/hrTemplate.json";
//        String data = JSONPlaceholderReplacer.CreateJsonFromCSV(CSVfilePath,JsonTemplateFilePath).toString();
//        Response res1 = healthURLMethods.putDataHealthAPI(data,headers,"hr");
//        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
//    }
    @Test
    public void ValidateGetDailyHeartRate()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01",headers,"hr","DAY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetWeeklyHeartRate()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01",headers,"hr","WEEK");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetMonthlyHeartRate()
    {
//        System.out.println();
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01",headers,"hr","MONTH");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidatePutAndGetHeartRate()
    {
        String CSVfilePath = "./src/main/java/csvdata/HRData.csv";
        String JsonTemplateFilePath = "./src/main/java/jsontemplates/hrTemplate.json";
        healthURLMethods = new HealthURLMethods();
        Boolean result = null;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI(CSVfilePath,JsonTemplateFilePath,headers,"hr");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
