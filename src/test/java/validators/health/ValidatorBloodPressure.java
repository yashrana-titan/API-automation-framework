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
        String CSVfilePath = "src/main/java/csvdata/BPData.csv";
        String TemplateFilePath = "src/main/java/jsontemplates/BPTemplate.json";
        Response res1 = healthURLMethods.putDataHealthAPIFromCSV(CSVfilePath,TemplateFilePath,headers,"bp");
        System.out.println(res1.asPrettyString());
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetDailyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01",headers,"bp","DAILY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetWeeklyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01",headers,"bp","WEEKLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetMonthlyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01",headers,"bp","MONTHLY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }

    @Test
    public void ValidatePutAndGetBloodPressure()
    {
        String CSVfilePath = "./src/main/java/csvdata/BPData.csv";
        String JsonTemplateFilePath = "./src/main/java/jsontemplates/BPTemplate.json";
        healthURLMethods = new HealthURLMethods();
        boolean result;
        try {
            result = healthURLMethods.verifyPutAndGetHealthAPI(CSVfilePath,JsonTemplateFilePath,headers,"bp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(result,"Put and Get JSONs do not match");
    }
}
