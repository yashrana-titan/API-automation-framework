package validators.health;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utility.BaseClass;
import utility.HealthURLMethods;

import java.io.IOException;

public class ValidatorBloodPressure{
    public HealthURLMethods healthURLMethods;
//    @Test
//    public void ValidatePutBloodPressure()
//    {
//        healthURLMethods = new HealthURLMethods();
//        String CSVfilePath = "src/main/java/csvdata/BPData.csv";
//        String TemplateFilePath = "src/main/java/jsontemplates/bpTemplate.json";
//        Response res1 = healthURLMethods.putDataHealthAPIFromCSV(CSVfilePath,TemplateFilePath,headers,"bp");
//        System.out.println(res1.asPrettyString());
//        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
//    }
    @Test
    public void ValidateGetDailyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01","bp","DAY");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetWeeklyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01","bp","WEEK");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }
    @Test
    public void ValidateGetMonthlyBloodPressure()
    {
        healthURLMethods = new HealthURLMethods();
        Response res1 = healthURLMethods.getDataHealthAPI("2023-05-01","bp","MONTH");
        Assert.assertEquals(res1.statusCode(),200,"Status Code not 200");
    }

    @Test
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
